package eionet.datadict.dal.impl.versioning;

import eionet.datadict.dal.versioning.RevisionDao;
import eionet.datadict.model.versioning.Revision;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import org.joda.time.DateTime;
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
    public void testGetLatestRevision() {
        Revision r = this.revisionDao.getLatestRevision();
        Revision expected = this.createExpectedLatestRevision();
        assertThat(r, samePropertyValuesAs(expected));
    }
    
    private Revision createExpectedLatestRevision() {
        Revision r = new Revision();
        r.setId(1L);
        r.setUserName("testuser");
        r.setCreationDate(new DateTime(1463034600000L));
        
        return r;
    }
    
}
