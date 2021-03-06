package eionet.datadict.dal.impl;

import eionet.datadict.dal.ConceptAttributeValuesDao;
import eionet.datadict.model.ConceptAttribute;
import eionet.datadict.model.DataType;
import eionet.datadict.model.StandardGenericStatus;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.model.VocabularyConceptAttributeValue;
import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.resx.EmbeddedResourceManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class ConceptAttributeValuesDaoImpl extends JdbcRepositoryBase implements ConceptAttributeValuesDao {

    private final EmbeddedResourceManager resourceManager;
    
    @Autowired
    public ConceptAttributeValuesDaoImpl(DataSource dataSource, EmbeddedResourceManager resourceManager) {
        super(dataSource);
        this.resourceManager = resourceManager;
    }

    @Override
    public List<VocabularyConceptAttributeValueSet> getConceptAttributeValues(Vocabulary vocabulary) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.ConceptAttributeValuesDaoImpl.getConceptAttributeValues");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabulary.getId());
        parameters.put("localRefType", DataType.LOCAL_REFERENCE.getValue());
        parameters.put("refType", DataType.REFERENCE.getValue());
        
        return this.getNamedParameterJdbcTemplate().query(sql, parameters, new TextValueSetExtractor());
    }

    @Override
    public List<VocabularyConceptAttributeValueSet> getConceptLocalLinks(Vocabulary vocabulary) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.ConceptAttributeValuesDaoImpl.getConceptLocalLinks");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabulary.getId());
        parameters.put("localRefType", DataType.LOCAL_REFERENCE.getValue());
        parameters.put("refType", DataType.REFERENCE.getValue());
        
        return this.getNamedParameterJdbcTemplate().query(sql, parameters, new LinkValueSetExtractor());
    }

    @Override
    public List<VocabularyConceptAttributeValueSet> getConceptInternalLinks(Vocabulary vocabulary) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.ConceptAttributeValuesDaoImpl.getConceptInternalLinks");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabulary.getId());
        parameters.put("refType", DataType.REFERENCE.getValue());
        
        return this.getNamedParameterJdbcTemplate().query(sql, parameters, new LinkValueSetExtractor());
    }

    @Override
    public List<VocabularyConceptAttributeValueSet> getConceptExternalLinks(Vocabulary vocabulary) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.ConceptAttributeValuesDaoImpl.getConceptExternalLinks");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabulary.getId());
        parameters.put("refType", DataType.REFERENCE.getValue());
        
        return this.getNamedParameterJdbcTemplate().query(sql, parameters, new ExternalLinkValueSetExtractor());
    }
    
    protected static abstract class AttributeValueSetExtractorBase implements ResultSetExtractor<List<VocabularyConceptAttributeValueSet>> {
        
        @Override
        public List<VocabularyConceptAttributeValueSet> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<VocabularyConceptAttributeValueSet> result = new ArrayList<>();
            VocabularyConceptAttributeValueSet workingSet = null;
            Map<Long, ConceptAttribute> conceptAttributeCache = new HashMap<>();
            
            while (rs.next()) {
                Long conceptId = rs.getLong("fConceptId");
                Long conceptAttributeId = rs.getLong("fConceptAttributeId");
                
                if (this.hasWorkingSetChanged(workingSet, conceptId, conceptAttributeId)) {
                    VocabularyConceptAttributeValueSet newWorkingSet = new VocabularyConceptAttributeValueSet();
                    VocabularyConcept newConcept;
                    
                    if (this.hasWorkingConceptChanged(workingSet, conceptId)) {
                        newConcept = new VocabularyConcept();
                        newConcept.setId(conceptId);
                    }
                    else {
                        newConcept = workingSet.getConcept();
                    }
                    
                    if (!conceptAttributeCache.containsKey(conceptAttributeId)) {
                        ConceptAttribute attribute = new ConceptAttribute();
                        attribute.setId(conceptAttributeId);
                        conceptAttributeCache.put(attribute.getId(), attribute);
                    }
                    
                    newWorkingSet.setConcept(newConcept);
                    newWorkingSet.setAttribute(conceptAttributeCache.get(conceptAttributeId));
                    result.add(newWorkingSet);
                    workingSet = newWorkingSet;
                }
                
                VocabularyConceptAttributeValue value = new VocabularyConceptAttributeValue();
                this.setProperties(value, rs);
                workingSet.getValues().add(value);
                value.setValueSet(workingSet);
            }
            
            return result;
        }
        
        protected abstract void setProperties(VocabularyConceptAttributeValue value, ResultSet rs)  throws SQLException, DataAccessException;
        
        private boolean hasWorkingSetChanged(VocabularyConceptAttributeValueSet workingSet, Long conceptId, Long conceptAttributeId) {
            if (this.hasWorkingConceptChanged(workingSet, conceptId)) {
                return true;
            }
            
            return this.hasWorkingAttributeChanged(workingSet, conceptAttributeId);
        }
        
        private boolean hasWorkingConceptChanged(VocabularyConceptAttributeValueSet workingSet, Long conceptId) {
            if (workingSet == null) {
                return true;
            }
            
            return !workingSet.getConcept().getId().equals(conceptId);
        }
        
        private boolean hasWorkingAttributeChanged(VocabularyConceptAttributeValueSet workingSet, Long conceptAttributeId) {
            if (workingSet == null) {
                return true;
            }
            
            return !workingSet.getAttribute().getId().equals(conceptAttributeId);
        }
        
    }
    
    protected static class TextValueSetExtractor extends AttributeValueSetExtractorBase {

        @Override
        protected void setProperties(VocabularyConceptAttributeValue value, ResultSet rs) throws SQLException, DataAccessException {
            value.setId(rs.getLong("Id"));
            value.setLanguage(rs.getString("Language"));
            value.setValue(rs.getString("Value"));
        }
        
    }
    
    protected static class ExternalLinkValueSetExtractor extends AttributeValueSetExtractorBase {

        @Override
        protected void setProperties(VocabularyConceptAttributeValue value, ResultSet rs) throws SQLException, DataAccessException {
            value.setId(rs.getLong("Id"));
            value.setValue(rs.getString("Value"));
        }
        
    }
    
    protected static class LinkValueSetExtractor extends AttributeValueSetExtractorBase {

        private final Map<Long, Vocabulary> vocabularyCache;
        private final Map<Long, VocabularyConcept> conceptCache;
        
        public LinkValueSetExtractor() {
            this.vocabularyCache = new HashMap<>();
            this.conceptCache = new HashMap<>();
        }
        
        @Override
        protected void setProperties(VocabularyConceptAttributeValue value, ResultSet rs) throws SQLException, DataAccessException {
            // value.setId(rs.getLong("Id"));
            Long relatedConceptId = rs.getLong("fRelatedConceptId");
            
            if (conceptCache.containsKey(relatedConceptId)) {
                value.setRelatedConcept(conceptCache.get(relatedConceptId));
                return;
            }
            
            VocabularyConcept relatedConcept = new VocabularyConcept();
            relatedConcept.setId(relatedConceptId);
            
            Long relatedVocabularyId = rs.getLong("fRelatedVocabularyId");
            
            if (!this.vocabularyCache.containsKey(relatedVocabularyId)) {
                Vocabulary relatedVocabulary = new Vocabulary();
                relatedVocabulary.setId(relatedVocabularyId);
                this.vocabularyCache.put(relatedVocabularyId, relatedVocabulary);
            }
            
            relatedConcept.setVocabulary(this.vocabularyCache.get(relatedVocabularyId));
            conceptCache.put(relatedConceptId, relatedConcept);
            value.setRelatedConcept(relatedConcept);
        }
        
    }
    
}
