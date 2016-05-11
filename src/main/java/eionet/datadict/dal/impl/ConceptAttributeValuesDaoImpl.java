package eionet.datadict.dal.impl;

import eionet.datadict.dal.ConceptAttributeValuesDao;
import eionet.datadict.model.ConceptAttribute;
import eionet.datadict.model.DataType;
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
        
        return this.getNamedParameterJdbcTemplate().query(sql, parameters, new AttributeValueSetExtractor());
    }
    
    protected static class AttributeValueSetExtractor implements ResultSetExtractor<List<VocabularyConceptAttributeValueSet>> {
        
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
                value.setId(rs.getLong("Id"));
                value.setLanguage(rs.getString("Language"));
                value.setValue(rs.getString("Value"));
                workingSet.getValues().add(value);
            }
            
            return result;
        }
        
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
    
}
