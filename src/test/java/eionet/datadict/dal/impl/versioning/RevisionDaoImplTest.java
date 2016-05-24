package eionet.datadict.dal.impl.versioning;

import eionet.datadict.dal.versioning.RevisionDao;
import eionet.datadict.model.versioning.Revision;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring-config.xml", "classpath:spring-test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RevisionDaoImplTest {
    
    @Autowired
    private RevisionDao revisionDao;
    
    @Test
    public void testGetLatestRevision() {
        Revision actual = this.revisionDao.getLatestRevision();
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getUserName(), is("testuser"));
        assertThat(actual.getCreationDate(), is(notNullValue()));
        assertThat(actual.getCreationDate().getMillis(), is(greaterThanOrEqualTo(1463034600000L)));
    }
    
}
