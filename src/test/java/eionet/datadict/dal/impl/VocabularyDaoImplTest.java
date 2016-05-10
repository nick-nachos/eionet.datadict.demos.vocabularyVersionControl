package eionet.datadict.dal.impl;

import eionet.datadict.dal.VocabularyDao;
import eionet.datadict.model.Vocabulary;
import org.junit.Assert;
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
    
    @Test
    public void testGetVocabularyByIdEmpty() {
        Vocabulary result = this.vocabularyDao.getVocabulary(1L);
        Assert.assertNull(result);
    }
    
}
