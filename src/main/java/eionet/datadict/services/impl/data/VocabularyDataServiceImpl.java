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
import eionet.datadict.model.util.ConceptAttributeValueMerger;
import eionet.datadict.model.util.ConceptAttributeValueRelatedConceptIdProvider;
import eionet.datadict.model.util.ConceptAttributeValueToRelatedConceptLinker;
import eionet.datadict.model.util.ConceptIdProvider;
import eionet.datadict.model.util.ConceptToAttributeValueLinker;
import eionet.datadict.model.util.ConceptVocabularyIdProvider;
import eionet.datadict.model.util.RelatedConceptIdProvider;
import eionet.datadict.model.util.VocabularyIdProvider;
import eionet.datadict.model.util.VocabularyToConceptLinker;
import eionet.datadict.model.versioning.Revision;
import eionet.datadict.services.data.VocabularyDataService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        this.linkConceptsToAttributeValues(vocabulary.getConcepts(), this.getConceptAttributeValues(vocabulary));
        
        for (VocabularyConcept concept : vocabulary.getConcepts()) {
            this.linkConceptAttributesToAttributeValues(vocabulary.getConceptAttributes(), concept.getAttributeValues());
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
        
        if (hasOther) {
            List<VocabularyConceptAttributeValueSet> simpleValues = this.conceptAttributeValuesDao.getConceptAttributeValues(vocabulary);
            this.mergeAttributeValues(simpleValues, values);
        }
        
        if (hasLocalRefs) {
            List<VocabularyConceptAttributeValueSet> localRefs = this.conceptAttributeValuesDao.getConceptLocalLinks(vocabulary);
            this.linkLocalReferences(vocabulary, localRefs);
            this.mergeAttributeValues(localRefs, values);
        }
        
        if (hasRefs) {
            List<VocabularyConceptAttributeValueSet> internalRefs = this.conceptAttributeValuesDao.getConceptInternalLinks(vocabulary);
            this.linkInternalReferences(vocabulary, internalRefs);
            this.mergeAttributeValues(internalRefs, values);
            
            List<VocabularyConceptAttributeValueSet> externalRefs = this.conceptAttributeValuesDao.getConceptExternalLinks(vocabulary);
            this.mergeAttributeValues(externalRefs, values);
        }
        
        return values;
    }
    
    private void linkLocalReferences(Vocabulary vocabulary, List<VocabularyConceptAttributeValueSet> localRefs) {
        this.linkReferences(vocabulary.getConcepts(), localRefs);
    }
    
    private void linkInternalReferences(Vocabulary vocabulary, List<VocabularyConceptAttributeValueSet> internalRefs) {
        List<VocabularyConcept> relatedConcepts = this.conceptDao.getRelatedConcepts(vocabulary);
        List<Vocabulary> relatedVocabularies = this.vocabularyDao.getRelatedVocabularies(vocabulary);
        LinkParentChildOptions linkOptions = new LinkParentChildOptions();
        linkOptions.setParentListSorted(true);
        linkOptions.setChildListSorted(true);
        DataObjects.linkParentChild(relatedVocabularies, relatedConcepts, new VocabularyToConceptLinker(), new VocabularyIdProvider(), new ConceptVocabularyIdProvider());
        
        this.linkReferences(relatedConcepts, internalRefs);
    }
    
    private void linkReferences(List<VocabularyConcept> relatedConcepts, List<VocabularyConceptAttributeValueSet> refs) {
        List<VocabularyConceptAttributeValue> disaggregatedRefs = this.disaggregate(refs);
        ConceptAttributeValueToRelatedConceptLinker linker = new ConceptAttributeValueToRelatedConceptLinker();
        RelatedConceptIdProvider parentKeyProvider = new RelatedConceptIdProvider();
        ConceptAttributeValueRelatedConceptIdProvider childKeyProvider = new ConceptAttributeValueRelatedConceptIdProvider();        
        LinkParentChildOptions linkOptions = new LinkParentChildOptions();
        linkOptions.setParentListSorted(true);
        DataObjects.linkParentChild(relatedConcepts, disaggregatedRefs, linker, parentKeyProvider, childKeyProvider);
    }
    
    private List<VocabularyConceptAttributeValue> disaggregate(List<VocabularyConceptAttributeValueSet> conceptAttributeValueSets) {
        List<VocabularyConceptAttributeValue> disaggregated = new ArrayList<>();
        
        for (VocabularyConceptAttributeValueSet valueSet : conceptAttributeValueSets) {
            disaggregated.addAll(valueSet.getValues());
        }
        
        return disaggregated;
    }
    
    private void mergeAttributeValues(List<VocabularyConceptAttributeValueSet> source, List<VocabularyConceptAttributeValueSet> destination) {
        if (destination.isEmpty()) {
            destination.addAll(source);
            return;
        }
        
        ConceptAttributeValueIdProvider idProvider = new ConceptAttributeValueIdProvider();
        DataObjects.linkParentChild(destination, source, new ConceptAttributeValueMerger(), idProvider, idProvider);
    }
    
    protected void linkConceptsToAttributeValues(List<VocabularyConcept> concepts, List<VocabularyConceptAttributeValueSet> attributeValues) {
        ConceptToAttributeValueLinker linker = new ConceptToAttributeValueLinker();
        ConceptIdProvider parentKeyProvider = new ConceptIdProvider();
        ConceptAttributeValueConceptIdProvider childKeyProvider = new ConceptAttributeValueConceptIdProvider();
        LinkParentChildOptions linkOptions = new LinkParentChildOptions();
        linkOptions.setParentListSorted(true);
        DataObjects.linkParentChild(concepts, attributeValues, linker, parentKeyProvider, childKeyProvider);
    }
    
    protected void linkConceptAttributesToAttributeValues(List<ConceptAttribute> attributes, List<VocabularyConceptAttributeValueSet> attributeValues) {
        ConceptAttributeToAttributeValueLinker linker = new ConceptAttributeToAttributeValueLinker();
        ConceptAttributeIdProvider parentKeyProvider = new ConceptAttributeIdProvider();
        ConceptAttributeValueAttributeIdProvider childKeyProvider = new ConceptAttributeValueAttributeIdProvider();
        Map<Long, ConceptAttribute> conceptAttributeMap = DataObjects.toMap(attributes, parentKeyProvider);
        
        for (VocabularyConceptAttributeValueSet valueSet : attributeValues) {
            ConceptAttribute attribute = conceptAttributeMap.get(childKeyProvider.getKey(valueSet));
            linker.link(attribute, valueSet);
        }
        
    }
    
}
