package eionet.datadict.services.impl.data;

import eionet.datadict.dal.ConceptAttributeDao;
import eionet.datadict.dal.ConceptAttributeValuesDao;
import eionet.datadict.dal.ConceptDao;
import eionet.datadict.dal.VocabularyDao;
import eionet.datadict.dal.versioning.RevisionDao;
import eionet.datadict.data.DataObjects;
import eionet.datadict.data.LinkParentChildOptions;
import eionet.datadict.model.ConceptAttribute;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.model.VocabularyConceptAttributeValue;
import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.model.util.ConceptAttributeIdProvider;
import eionet.datadict.model.util.ConceptAttributeToAttributeValueLinker;
import eionet.datadict.model.util.ConceptAttributeValueAttributeIdProvider;
import eionet.datadict.model.util.ConceptAttributeValueConceptIdProvider;
import eionet.datadict.model.util.ConceptAttributeValueIdProvider;
import eionet.datadict.model.util.ConceptAttributeValueRelatedConceptIdProvider;
import eionet.datadict.model.util.ConceptAttributeValueToRelatedConceptLinker;
import eionet.datadict.model.util.ConceptIdProvider;
import eionet.datadict.model.util.ConceptToAttributeValueLinker;
import eionet.datadict.model.util.RelatedConceptIdProvider;
import eionet.datadict.model.util.VocabularyIdProvider;
import eionet.datadict.model.util.VocabularyToConceptLinker;
import eionet.datadict.model.versioning.Revision;
import eionet.datadict.services.data.VocabularyDataService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VocabularyDataServiceImpl implements VocabularyDataService {

    private final VocabularyDao vocabularyDao;
    private final ConceptDao conceptDao;
    private final ConceptAttributeDao conceptAttributeDao;
    private final ConceptAttributeValuesDao conceptAttributeValuesDao;
    private final RevisionDao revisionDao;
    
    @Autowired
    public VocabularyDataServiceImpl(VocabularyDao vocabularyDao, ConceptDao conceptDao, 
            ConceptAttributeDao conceptAttributeDao, ConceptAttributeValuesDao conceptAttributeValuesDao, 
            RevisionDao revisionDao) {
        this.vocabularyDao = vocabularyDao;
        this.conceptDao = conceptDao;
        this.conceptAttributeDao = conceptAttributeDao;
        this.conceptAttributeValuesDao = conceptAttributeValuesDao;
        this.revisionDao = revisionDao;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Vocabulary> getLatestVocabularyEntries() {
        Revision latestRevision = this.revisionDao.getLatestRevision();
        
        if (latestRevision == null) {
            return new ArrayList<>();
        }
        
        return this.vocabularyDao.getVocabularies(latestRevision);
    }

    @Override
    @Transactional(readOnly = true)
    public Vocabulary getLatestVocabulary(String identifier) {
        Revision latestRevision = this.revisionDao.getLatestRevision();
        
        if (latestRevision == null) {
            return null;
        }
        
        Vocabulary vocabulary = this.vocabularyDao.getVocabulary(latestRevision, identifier);
        
        if (vocabulary == null) {
            return null;
        }
        
        this.linkVocabularyToConceptAttributes(vocabulary, this.conceptAttributeDao.getConceptAttributes(vocabulary));
        this.linkVocabularyToConcepts(vocabulary, this.conceptDao.getConcepts(vocabulary));
        this.linkConceptsToAttributeValues(vocabulary, this.getConceptAttributeValues(vocabulary));
        
        Map<Long, ConceptAttribute> conceptAttributeMap = DataObjects.toMap(vocabulary.getConceptAttributes(), new ConceptAttributeIdProvider());
        
        for (VocabularyConcept concept : vocabulary.getConcepts()) {
            this.linkConceptAttributesToAttributeValues(conceptAttributeMap, concept.getAttributeValues());
        }
        
        return vocabulary;
    }

    protected void linkVocabularyToConceptAttributes(Vocabulary vocabulary, List<ConceptAttribute> conceptAttributes) {
        vocabulary.getConceptAttributes().addAll(conceptAttributes);
    }
    
    protected void linkVocabularyToConcepts(Vocabulary vocabulary, List<VocabularyConcept> concepts) {
        for (VocabularyConcept concept : concepts) {
            concept.setVocabulary(vocabulary);
            vocabulary.getConcepts().add(concept);
        }
    }
    
    private List<VocabularyConceptAttributeValueSet> getConceptAttributeValues(Vocabulary vocabulary) {
        boolean hasLocalRefs = false, hasRefs = false, hasOther = false;
        
        for (ConceptAttribute conceptAttribute : vocabulary.getConceptAttributes()) {
            switch (conceptAttribute.getDataType()) {
                case LOCAL_REFERENCE:
                    hasLocalRefs = true;
                    break;
                case REFERENCE:
                    hasRefs = true;
                    break;
                default:
                    hasOther = true;
                    break;
            }
        }
        
        List<VocabularyConceptAttributeValueSet> values = new ArrayList<>();
        
        if (hasLocalRefs || hasRefs) {
            List<VocabularyConceptAttributeValueSet> localRefs = this.conceptAttributeValuesDao.getConceptLocalLinks(vocabulary);
            this.linkLocalReferences(vocabulary, localRefs);
            values.addAll(localRefs);
        }
        
        if (hasRefs) {
            List<VocabularyConceptAttributeValueSet> refs = this.conceptAttributeValuesDao.getConceptInternalLinks(vocabulary);
            this.linkInternalReferences(vocabulary, refs);
            values.addAll(refs);
            values.addAll(this.conceptAttributeValuesDao.getConceptExternalLinks(vocabulary));
        }
        
        this.mergeAttributeValues(values);
        
        if (hasOther) {
            List<VocabularyConceptAttributeValueSet> simpleValues = this.conceptAttributeValuesDao.getConceptAttributeValues(vocabulary);
            values.addAll(simpleValues);
        }
        
        return values;
    }
    
    private void linkLocalReferences(Vocabulary vocabulary, List<VocabularyConceptAttributeValueSet> localRefs) {
        this.linkReferences(localRefs, vocabulary.getConcepts());
    }
    
    private void linkInternalReferences(Vocabulary vocabulary, List<VocabularyConceptAttributeValueSet> internalRefs) {
        List<VocabularyConcept> relatedConcepts = this.conceptDao.getRelatedConcepts(vocabulary);
        this.linkReferences(internalRefs, relatedConcepts);
        
        List<Vocabulary> relatedVocabularies = this.getRelatedVocabularies(internalRefs);
        Map<Long, Vocabulary> relatedVocabularyMap = DataObjects.toMap(relatedVocabularies, new VocabularyIdProvider());
        VocabularyToConceptLinker linker = new VocabularyToConceptLinker();
        
        for (VocabularyConceptAttributeValueSet valueSet : internalRefs) {
            for (VocabularyConceptAttributeValue attributeValue : valueSet.getValues()) {
                Long relatedVocabularyId = attributeValue.getRelatedConcept().getVocabulary().getId();
            
                if (relatedVocabularyMap.containsKey(relatedVocabularyId)) {
                    linker.link(relatedVocabularyMap.get(relatedVocabularyId), attributeValue.getRelatedConcept());
                }
            }
        }
    }
    
    private void linkReferences(List<VocabularyConceptAttributeValueSet> references, List<VocabularyConcept> relatedConcepts) {
        List<VocabularyConceptAttributeValue> disaggregatedRefs = this.disaggregate(references);
        ConceptAttributeValueToRelatedConceptLinker linker = new ConceptAttributeValueToRelatedConceptLinker();
        RelatedConceptIdProvider parentKeyProvider = new RelatedConceptIdProvider();
        ConceptAttributeValueRelatedConceptIdProvider childKeyProvider = new ConceptAttributeValueRelatedConceptIdProvider();        
        LinkParentChildOptions linkOptions = new LinkParentChildOptions();
        linkOptions.setParentListSorted(true);
        DataObjects.linkParentChild(relatedConcepts, disaggregatedRefs, linker, parentKeyProvider, childKeyProvider, linkOptions);
    }
    
    private List<VocabularyConceptAttributeValue> disaggregate(List<VocabularyConceptAttributeValueSet> conceptAttributeValueSets) {
        List<VocabularyConceptAttributeValue> disaggregated = new ArrayList<>();
        
        for (VocabularyConceptAttributeValueSet valueSet : conceptAttributeValueSets) {
            disaggregated.addAll(valueSet.getValues());
        }
        
        return disaggregated;
    }
    
    private void mergeAttributeValues(List<VocabularyConceptAttributeValueSet> values) {
        ConceptAttributeValueIdProvider keyProvider = new ConceptAttributeValueIdProvider();
        DataObjects.sort(values, new ConceptAttributeValueIdProvider());
        VocabularyConceptAttributeValueSet workingValueSet = null;
        List<VocabularyConceptAttributeValueSet> itermediateResult = new ArrayList<>();
        
        for (VocabularyConceptAttributeValueSet valueSet : values) {
            if (workingValueSet == null) {
                workingValueSet = valueSet;
                itermediateResult.add(workingValueSet);
                continue;
            }
            
            Pair<Long, Long> key1 = keyProvider.getKey(workingValueSet);
            Pair<Long, Long> key2 = keyProvider.getKey(valueSet);
            
            if (key1.compareTo(key2) == 0) {
                workingValueSet.getValues().addAll(valueSet.getValues());
            }
            else {
                workingValueSet = valueSet;
                itermediateResult.add(workingValueSet);
            }
        }
        
        values.clear();
        values.addAll(itermediateResult);
    }
    
    private List<Vocabulary> getRelatedVocabularies(List<VocabularyConceptAttributeValueSet> internalRefs) {
        Set<Long> relatedVocabularyIds = new HashSet<>();
        
        for (VocabularyConceptAttributeValueSet valueSet : internalRefs) {
            for (VocabularyConceptAttributeValue attributeValue : valueSet.getValues()) {
                relatedVocabularyIds.add(attributeValue.getRelatedConcept().getVocabulary().getId());
            }
        }
        
        return this.vocabularyDao.getVocabularies(relatedVocabularyIds);
    }
    
    protected void linkConceptsToAttributeValues(Vocabulary vocabulary, List<VocabularyConceptAttributeValueSet> attributeValues) {
        ConceptToAttributeValueLinker linker = new ConceptToAttributeValueLinker();
        ConceptIdProvider parentKeyProvider = new ConceptIdProvider();
        ConceptAttributeValueConceptIdProvider childKeyProvider = new ConceptAttributeValueConceptIdProvider();
        LinkParentChildOptions linkOptions = new LinkParentChildOptions();
        linkOptions.setParentListSorted(true);
        DataObjects.linkParentChild(vocabulary.getConcepts(), attributeValues, linker, parentKeyProvider, childKeyProvider, linkOptions);
    }
    
    protected void linkConceptAttributesToAttributeValues(Map<Long, ConceptAttribute> conceptAttributeMap, List<VocabularyConceptAttributeValueSet> attributeValues) {
        ConceptAttributeToAttributeValueLinker linker = new ConceptAttributeToAttributeValueLinker();
        ConceptAttributeValueAttributeIdProvider childKeyProvider = new ConceptAttributeValueAttributeIdProvider();
        
        for (VocabularyConceptAttributeValueSet valueSet : attributeValues) {
            ConceptAttribute attribute = conceptAttributeMap.get(childKeyProvider.getKey(valueSet));
            linker.link(attribute, valueSet);
        }
        
    }
    
}
