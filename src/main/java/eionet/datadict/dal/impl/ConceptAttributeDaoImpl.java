package eionet.datadict.dal.impl;

import eionet.datadict.dal.ConceptAttributeDao;
import eionet.datadict.model.ConceptAttribute;
import eionet.datadict.model.DataType;
import eionet.datadict.model.Vocabulary;
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
public class ConceptAttributeDaoImpl extends JdbcRepositoryBase implements ConceptAttributeDao {

    private final EmbeddedResourceManager resourceManager;
    
    @Autowired
    public ConceptAttributeDaoImpl(DataSource dataSource, EmbeddedResourceManager resourceManager) {
        super(dataSource);
        this.resourceManager = resourceManager;
    }
    
    @Override
    public List<ConceptAttribute> getConceptAttributes(Vocabulary vocabulary) {
        String sql = this.resourceManager.getText("eionet.datadict.dal.impl.ConceptAttributeDaoImpl.getConceptAttributes");
        Map<String, Object> parameters = this.createParametersMap();
        parameters.put("vocabularyId", vocabulary.getId());
        
        return this.getNamedParameterJdbcTemplate().query(sql, parameters, new ConceptAttributeRowMapper());
    }
    
    protected static class ConceptAttributeRowMapper implements RowMapper<ConceptAttribute> {

        @Override
        public ConceptAttribute mapRow(ResultSet rs, int i) throws SQLException {
            ConceptAttribute ca = new ConceptAttribute();
            ca.setId(rs.getLong("Id"));
            ca.setIdentifier(rs.getString("Identifier"));
            ca.setDataType(DataType.fromValue(rs.getByte("DataType")));
            
            return ca;
        }
        
    }
    
}
