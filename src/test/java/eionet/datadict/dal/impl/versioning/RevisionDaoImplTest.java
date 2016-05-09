package eionet.datadict.dal.impl.versioning;

import eionet.datadict.dal.versioning.RevisionDao;
import eionet.datadict.model.versioning.Revision;
import static org.junit.Assert.assertNull;
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
    
    @Test
    public void testEmpty() {
        Revision result = this.revisionDao.getLatestRevision();
        assertNull(result);
    }
    
}
