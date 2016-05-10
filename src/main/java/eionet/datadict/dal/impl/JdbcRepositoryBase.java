package eionet.datadict.dal.impl;

import eionet.datadict.dal.impl.util.ResultSetUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public abstract class JdbcRepositoryBase extends NamedParameterJdbcDaoSupport {
    
    public JdbcRepositoryBase(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
    
    public Map<String, Object> createParametersMap() {
        return new HashMap<>();
    }
    
}
