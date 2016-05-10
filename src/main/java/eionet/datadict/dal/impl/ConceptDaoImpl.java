package eionet.datadict.dal.impl;

import eionet.datadict.dal.ConceptDao;
import eionet.datadict.model.StandardGenericStatus;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.resx.EmbeddedResourceManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ConceptDaoImpl extends JdbcRepositoryBase implements ConceptDao {

    private final EmbeddedResourceManager resourceManager;
    
    @Autowired
    public ConceptDaoImpl(DataSource dataSource, EmbeddedResourceManager resourceManager) {
        super(dataSource);
        this.resourceManager = resourceManager;
    }

    @Override
    public List<VocabularyConcept> getConcepts(Vocabulary vocabulary) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.ConceptDaoImpl.getConcepts");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabulary.getId());
        
        return this.getNamedParameterJdbcTemplate().query(sql, parameters, new ConceptRowMapper());
    }
    
    protected static class ConceptRowMapper implements RowMapper<VocabularyConcept> {

        @Override
        public VocabularyConcept mapRow(ResultSet rs, int i) throws SQLException {
            VocabularyConcept vc = new VocabularyConcept();
            vc.setId(rs.getLong("Id"));
            vc.setIdentifier(rs.getString("Identifier"));
            vc.setNotation(rs.getString("Notation"));
            vc.setLabel(rs.getString("Label"));
            vc.setStatus(StandardGenericStatus.fromValue(rs.getInt("Status")));
            vc.setDefinition(rs.getString("Definition"));
            
            return vc;
        }
        
    }
}
