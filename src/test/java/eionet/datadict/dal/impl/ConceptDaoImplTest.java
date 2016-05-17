package eionet.datadict.dal.impl;

import eionet.datadict.dal.ConceptDao;
import eionet.datadict.model.StandardGenericStatus;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConcept;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConceptDaoImplTest {

    @Autowired
    private ConceptDao conceptDao;

    @Test
    public void testGetConcepts() {
        Vocabulary v = new Vocabulary();
        v.setId(10L);
        List<VocabularyConcept> concepts = conceptDao.getConcepts(v);
        
        assertThat(concepts.size(), is(4));
        
        for (VocabularyConcept concept : concepts) {
            assertThat(concept.getVocabulary(), is(notNullValue()));
            assertThat(concept.getVocabulary().getId(), is(v.getId()));
        }
        
        VocabularyConcept actual = null;
        
        for (VocabularyConcept concept : concepts) {
            if (StringUtils.equals(concept.getIdentifier(), "model")) {
                actual = concept;
                break;
            }
        }
        
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getNotation(), is("model"));
        assertThat(actual.getLabel(), is("Modelling"));
        assertThat(actual.getDefinition(), is("Legal reference : Decision 2011/850/EU, Article 9, paragraph 5 and Annex II D (iv)"));
        assertThat(actual.getStatus(), is(StandardGenericStatus.VALID));
    }
    
}
