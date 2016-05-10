package eionet.datadict.dal.impl.versioning;

import eionet.datadict.dal.impl.JdbcRepositoryBase;
import eionet.datadict.dal.impl.util.ResultSetUtils;
import eionet.datadict.dal.versioning.RevisionDao;
import eionet.datadict.model.versioning.Revision;
import eionet.datadict.resx.EmbeddedResourceManager;
import eionet.datadict.util.IterableUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RevisionDaoImpl extends JdbcRepositoryBase implements RevisionDao {

    private final EmbeddedResourceManager resourceManager;
    
    @Autowired
    public RevisionDaoImpl(DataSource dataSource, EmbeddedResourceManager resourceManager) {
        super(dataSource);
        this.resourceManager = resourceManager;
    }
    
    @Override
    public Revision getLatestRevision() {
        String sql = resourceManager.getText("eionet.datadict.dal.impl.versioning.RevisionDaoImpl.getLatestRevision");
        
        return IterableUtils.firstOrNull(this.getNamedParameterJdbcTemplate().query(sql, new RowMapper<Revision>() {

            @Override
            public Revision mapRow(ResultSet rs, int i) throws SQLException {
                Revision revision = new Revision();
                revision.setId(rs.getLong("Id"));
                revision.setUserName(rs.getString("UserName"));
                revision.setCreationDate(ResultSetUtils.getDateTimeFromMillis(rs, "CreationDate"));
                
                Long parentId = ResultSetUtils.getLong(rs, "fParentRevisionId");
                
                if (parentId != null) {
                    Revision parent = new Revision();
                    parent.setId(parentId);
                    revision.setParent(parent);
                }
                
                return revision;
            }
        }));
    }
    
}
