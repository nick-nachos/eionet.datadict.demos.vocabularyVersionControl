package eionet.datadict.services.impl.data;

import eionet.datadict.model.Vocabulary;
import eionet.datadict.services.data.VocabularyDataService;
import eionet.datadict.testutil.ExecutionDurationPrinter;
import static org.hamcrest.MatcherAssert.assertThat;
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
public class VocabularyDataServiceImplRegressionTest {
    
    @Autowired
    private VocabularyDataService vocabularyDataService;
    
    @Test
    public void testGetLatestVocabulary_Empty() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "some.identifier.that.does.not.exist").run();
        
        assertThat(v, is(nullValue()));
    }
    
    @Test
    public void testGetLatestVocabulary_XSmall() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "lcp.lcpcountries").run();
        
        assertThat(v, is(notNullValue()));
        assertThat(v.getConcepts().size(), is(39));
    }
    
    @Test
    public void testGetLatestVocabulary_Small() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "cdda.designations").run();
        
        assertThat(v, is(notNullValue()));
        assertThat(v.getConcepts().size(), is(614));
    }
    
    @Test
    public void testGetLatestVocabulary_Medium() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "biodiversity.n2000species").run();

        assertThat(v, is(notNullValue()));
        assertThat(v.getConcepts().size(), is(2940));
    }
    
    @Test
    public void testGetLatestVocabulary_Large() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "lau2.fr").run();
        
        assertThat(v, is(notNullValue()));
        assertThat(v.getConcepts().size(), is(36682));
    }
    
    @Test
    public void testGetLatestVocabulary_XLarge() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "wise.WaterBody").run();
        
        assertThat(v, is(notNullValue()));
        assertThat(v.getConcepts().size(), is(196696));
    }
    
    private final class GetLatestVocabularyExecutionDurationPrinter extends ExecutionDurationPrinter<Vocabulary> {

        private final VocabularyDataService vocabularyDataService;
        private final String identifier;
        
        public GetLatestVocabularyExecutionDurationPrinter(VocabularyDataService vocabularyDataService, String identifier) {
            this.vocabularyDataService = vocabularyDataService;
            this.identifier = identifier;
        }
        
        @Override
        protected String getOperationDescription() {
            return this.identifier;
        }

        @Override
        protected Vocabulary execute() throws Exception {
            return this.vocabularyDataService.getLatestVocabulary(this.identifier);
        }
        
    }    
    
}
