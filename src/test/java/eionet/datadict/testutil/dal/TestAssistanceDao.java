package eionet.datadict.testutil.dal;

import eionet.datadict.dal.impl.JdbcRepositoryBase;
import eionet.datadict.resx.EmbeddedResourceManager;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestAssistanceDao extends JdbcRepositoryBase {

    private final EmbeddedResourceManager resourceManager;
    
    @Autowired
    public TestAssistanceDao(DataSource dataSource, EmbeddedResourceManager resourceManager) {
        super(dataSource);
        this.resourceManager = resourceManager;
    }
    
    public int update(String sql) {
        return this.getNamedParameterJdbcTemplate().update(sql, new HashMap<String, Object>());
    }
    
    public int update(String sql, Map<String, ?> parameters) {
        return this.getNamedParameterJdbcTemplate().update(sql, parameters);
    }
    
    public void newVocabulariesByCopy() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.newVocabulariesByCopy");
        this.getJdbcTemplate().update(sql);
    }
    
    public void linkVocabulariesToConcepts() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkVocabulariesToConcepts");
        this.getJdbcTemplate().update(sql);
    }
    
    public void linkVocabulariesToConceptAttributes() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkVocabulariesToConceptAttributes");
        this.getJdbcTemplate().update(sql);
    }
    
    public void linkConceptsToAttributeValues() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkConceptsToAttributeValues");
        this.getJdbcTemplate().update(sql);
    }
    
    public void linkConceptsToExternalConcepts() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkConceptsToExternalConcepts");
        this.getJdbcTemplate().update(sql);
    }
    
    public void linkConceptsToInternalConcepts() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkConceptsToInternalConcepts");
        this.getJdbcTemplate().update(sql);
    }
    
    public void createVocabularyVersions() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.createVocabularyVersions");
        this.getJdbcTemplate().update(sql);
    }
    
    public void createRevisions() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.createRevisions");
        this.getJdbcTemplate().update(sql);
    }
    
    public void linkRevisions() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkRevisions");
        this.getJdbcTemplate().update(sql);
    }
    
    public void linkRevisionsToVocabularyVersions() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkRevisionsToVocabularyVersions");
        this.getJdbcTemplate().update(sql);
    }
    
    public void linkRevisionsToConceptAttributeVersions() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkRevisionsToConceptAttributeVersions");
        this.getJdbcTemplate().update(sql);
    }
    
    public void clearWorkingState() {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.clearWorkingState");
        this.getJdbcTemplate().update(sql);
    }
    
    /*
    public Long newVocabularyByCopy(Long sourceVocabularyId) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.newVocabularyByCopy");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("sourceVocabularyId", sourceVocabularyId);
        this.getNamedParameterJdbcTemplate().update(sql, parameters);
        
        return this.getLastInsertId(Long.class);
    }
    
    public void linkToConcepts(Long vocabularyId, Long sourceVocabularyId) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkToConcepts");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabularyId);
        parameters.put("sourceVocabularyId", sourceVocabularyId);
        this.getNamedParameterJdbcTemplate().update(sql, parameters);
    }
    
    public void linkToConceptAttributes(Long vocabularyId, Long sourceVocabularyId) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkToConceptAttributes");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabularyId);
        parameters.put("sourceVocabularyId", sourceVocabularyId);
        this.getNamedParameterJdbcTemplate().update(sql, parameters);
    }
    
    public void linkToConceptAttributeValues(Long vocabularyId, Long sourceVocabularyId) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkToConceptAttributeValues");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabularyId);
        parameters.put("sourceVocabularyId", sourceVocabularyId);
        this.getNamedParameterJdbcTemplate().update(sql, parameters);
    }
    
    public void createVocabularyVersion(Long vocabularyId, Long sourceVocabularyId, String userName, Long commitDate) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.createVocabularyVersion");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabularyId);
        parameters.put("sourceVocabularyId", sourceVocabularyId);
        parameters.put("userName", userName);
        parameters.put("commitDate", commitDate);
        this.getNamedParameterJdbcTemplate().update(sql, parameters);
    }
    
    public Long newRevision(String userName, Long creationDate) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.newRevision");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("userName", userName);
        parameters.put("creationDate", creationDate);
        this.getNamedParameterJdbcTemplate().update(sql, parameters);
        
        return this.getLastInsertId(Long.class);
    }
    
    public void linkRevisionToVocabularyVersions(Long revisionId, Long vocabularyId, Long sourceVocabularyId) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkRevisionToVocabularyVersions");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("revisionId", revisionId);
        parameters.put("vocabularyId", vocabularyId);
        parameters.put("sourceVocabularyId", sourceVocabularyId);
        this.getNamedParameterJdbcTemplate().update(sql, parameters);
    }
    
    public void linkRevisionToConceptAttributeVersions(Long revisionId) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.linkRevisionToConceptAttributeVersions");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("revisionId", revisionId);
        this.getNamedParameterJdbcTemplate().update(sql, parameters);
    }
    
    public void resetVocabularyWorkState(Long vocabularyId) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.util.TestAssistanceDao.resetVocabularyWorkState");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabularyId);
        this.getNamedParameterJdbcTemplate().update(sql, parameters);
    }
    */
}
