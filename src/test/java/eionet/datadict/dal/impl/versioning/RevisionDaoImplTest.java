package eionet.datadict.dal.impl.versioning;

import eionet.datadict.dal.impl.util.TestAssistanceDao;
import eionet.datadict.dal.versioning.RevisionDao;
import eionet.datadict.model.versioning.Revision;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RevisionDaoImplTest {
    
    @Autowired
    private RevisionDao revisionDao;
    
    @Autowired
    private TestAssistanceDao testAssistanceDao;
    
    @After
    public void tearDown() {
        this.dropAllRevisions();
    }
    
    @Test
    public void testEmpty() {
        Revision result = this.revisionDao.getLatestRevision();
        assertNull(result);
    }
    
    @Test
    public void testSingle() {
        this.createNewRevision();
        Revision result = this.revisionDao.getLatestRevision();

        assertNotNull(result);
        assertNotNull(result.getUserName());
        assertNull(result.getParent());
    }
    
    @Test
    public void testMultiple() {
        this.createNewRevision();
        Revision rev1 = this.revisionDao.getLatestRevision();

        assertNotNull(rev1);

        this.createNewRevision(rev1);
        Revision latest = this.revisionDao.getLatestRevision();

        assertNotNull(latest);
        assertEquals(rev1.getId(), latest.getParent().getId());
    }
    
    private void createNewRevision() {
        this.createNewRevision(null);
    }
    
    private void createNewRevision(Revision parent) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("creationDate", new Date().getTime());
        
        Long parentId = parent == null ? null : parent.getId();
        
        parameters.put("fParentRevisionId", parentId);
        String sql = "insert into Revision (UserName, CreationDate, fParentRevisionId) values ('fake_user', :creationDate, :fParentRevisionId)";
        testAssistanceDao.update(sql, parameters);
    }
    
    private void dropAllRevisions() {
        testAssistanceDao.update("delete from Revision order by CreationDate desc, fParentRevisionId desc");
    }
}
