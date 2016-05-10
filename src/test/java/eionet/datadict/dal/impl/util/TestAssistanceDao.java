package eionet.datadict.dal.impl.util;

import eionet.datadict.dal.impl.JdbcRepositoryBase;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestAssistanceDao extends JdbcRepositoryBase {

    @Autowired
    public TestAssistanceDao(DataSource dataSource) {
        super(dataSource);
    }
    
    public int update(String sql) {
        return this.getNamedParameterJdbcTemplate().update(sql, new HashMap<String, Object>());
    }
    
    public int update(String sql, Map<String, ?> parameters) {
        return this.getNamedParameterJdbcTemplate().update(sql, parameters);
    }
    
}
