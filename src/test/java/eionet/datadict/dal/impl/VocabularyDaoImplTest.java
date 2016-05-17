package eionet.datadict.dal.impl;

import eionet.datadict.dal.VocabularyDao;
import eionet.datadict.dal.versioning.RevisionDao;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyRegulationStatus;
import eionet.datadict.model.versioning.Revision;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VocabularyDaoImplTest {

    @Autowired
    private VocabularyDao vocabularyDao;
    
    @Autowired
    private RevisionDao revisionDao;
    
    @Test
    public void testGetVocabularyById() {
        Vocabulary actual = this.vocabularyDao.getVocabulary(-1L);
        assertThat(actual, is(nullValue()));
        
        Vocabulary expected = this.createExpectedVocabulary();
        actual = this.vocabularyDao.getVocabulary(expected.getId());
        assertThat(actual, is(notNullValue()));
        assertThat(actual, samePropertyValuesAs(expected));
    }
    
    @Test
    public void testGetVocabularyByRevisionAndIdentifier() {
        Revision r = this.revisionDao.getLatestRevision();
        Vocabulary expected = this.createExpectedVocabulary();
        Vocabulary actual = this.vocabularyDao.getVocabulary(r, expected.getIdentifier());
        assertThat(actual, is(notNullValue()));
        assertThat(actual, samePropertyValuesAs(expected));
        
        actual = this.vocabularyDao.getVocabulary(r, "some.identifier.that.does.not.exist");
        assertThat(actual, is(nullValue()));
    }
    
    @Test
    public void testGetVocabulariesByRevision() {
        Revision r = this.revisionDao.getLatestRevision();
        List<Vocabulary> vocabularies = this.vocabularyDao.getVocabularies(r);
        assertThat(vocabularies.size(), is(842));
    }
    
    private Vocabulary createExpectedVocabulary() {
        Vocabulary expected = new Vocabulary();
        expected.setId(145L);
        expected.setIdentifier("eurostat.n_activ");
        expected.setLabel("Number of active persons");
        expected.setBaseUri("http://dd.eionet.europa.eu/vocabulary/eurostat/n_activ/");
        expected.setRegulationStatus(VocabularyRegulationStatus.fromValue((byte)2));
        
        return expected;
    }
    
}
