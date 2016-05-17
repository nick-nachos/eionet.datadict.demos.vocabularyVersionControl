package eionet.datadict.dal.impl;

import eionet.datadict.dal.VocabularyDao;
import eionet.datadict.model.DataType;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyRegulationStatus;
import eionet.datadict.model.versioning.Revision;
import eionet.datadict.resx.EmbeddedResourceManager;
import eionet.datadict.util.IterableUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class VocabularyDaoImpl extends JdbcRepositoryBase implements VocabularyDao {

    private final EmbeddedResourceManager resourceManager;
    
    @Autowired
    public VocabularyDaoImpl(DataSource dataSource, EmbeddedResourceManager resourceManager) {
        super(dataSource);
        this.resourceManager = resourceManager;
    }
    
    @Override
    public List<Vocabulary> getVocabularies(Revision revision) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.VocabularyDaoImpl.getVocabularies");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("revisionId", revision.getId());
        
        return this.getNamedParameterJdbcTemplate().query(sql, parameters, new VocabularyRowMapper());
    }

    @Override
    public List<Vocabulary> getVocabularies(Set<Long> vocabularyIds) {
        final int maxBatchSize = 80;
        
        List<Long> vocabularyIdsSorted = new ArrayList<>(vocabularyIds);
        Collections.sort(vocabularyIdsSorted);
        Long[] ids = vocabularyIdsSorted.toArray(new Long[vocabularyIdsSorted.size()]);
        
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.VocabularyDaoImpl.getVocabulariesByIds");
        List<Vocabulary> result = new ArrayList<>(ids.length);
        int index = 0;
        
        while (index < ids.length) {
            int batchSize = Math.min(maxBatchSize, ids.length - index);
            Long[] vocabularyIdBatch = Arrays.copyOfRange(ids, index, index + batchSize);
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("vocabularyIds", Arrays.asList(vocabularyIdBatch));
            result.addAll(this.getNamedParameterJdbcTemplate().query(sql, parameters, new VocabularyRowMapper()));
            index += batchSize;
        }
        
        return result;
    }
    
    @Override
    public Vocabulary getVocabulary(Long id) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.VocabularyDaoImpl.getVocabularyById");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", id);
        
        return IterableUtils.firstOrNull(this.getNamedParameterJdbcTemplate().query(sql, parameters, new VocabularyRowMapper()));
    }
    
    @Override
    public Vocabulary getVocabulary(Revision revision, String identifier) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.VocabularyDaoImpl.getVocabularyByRevisionAndIdentifier");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("revisionId", revision.getId());
        parameters.put("vocabularyIdentifier", identifier);
        
        return IterableUtils.firstOrNull(this.getNamedParameterJdbcTemplate().query(sql, parameters, new VocabularyRowMapper()));
    }

    @Override
    public List<Vocabulary> getRelatedVocabularies(Vocabulary vocabulary) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.VocabularyDaoImpl.getRelatedVocabularies");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabulary.getId());
        parameters.put("refType", DataType.REFERENCE.getValue());
        
        return this.getNamedParameterJdbcTemplate().query(sql, parameters, new VocabularyRowMapper());
    }
    
    
    protected static class VocabularyRowMapper implements RowMapper<Vocabulary> {

        @Override
        public Vocabulary mapRow(ResultSet rs, int i) throws SQLException {
            Vocabulary v = new Vocabulary();
            v.setId(rs.getLong("Id"));
            v.setIdentifier(rs.getString("Identifier"));
            v.setLabel(rs.getString("Label"));
            v.setBaseUri(rs.getString("BaseUri"));
            v.setRegulationStatus(VocabularyRegulationStatus.fromValue(rs.getByte("RegulationStatus")));
            
            return v;
        }
        
    }
}
