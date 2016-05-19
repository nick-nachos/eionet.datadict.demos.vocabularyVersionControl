package eionet.datadict.services.impl.data;

import eionet.datadict.dal.ConceptAttributeDao;
import eionet.datadict.dal.ConceptAttributeValuesDao;
import eionet.datadict.dal.ConceptDao;
import eionet.datadict.dal.VocabularyDao;
import eionet.datadict.dal.versioning.RevisionDao;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.model.VocabularyConceptAttributeValue;
import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.model.testutil.VocabularyTestUtils;
import eionet.datadict.services.data.VocabularyDataService;
import eionet.datadict.testutil.ExecutionDurationHandler;
import eionet.datadict.testutil.ExecutionDurationPrinter;
import eionet.datadict.testutil.dal.ConceptAttributeDaoTestDecorator;
import eionet.datadict.testutil.dal.ConceptAttributeValuesDaoTestDecorator;
import eionet.datadict.testutil.dal.ConceptDaoTestDecorator;
import eionet.datadict.testutil.dal.VocabularyDaoTestDecorator;
import eionet.datadict.testutil.dal.versioning.RevisionDaoTestDecorator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VocabularyDataServiceImplRegressionTest {
    
    @Autowired
    private RevisionDao revisionDao;
    @Autowired
    private VocabularyDao vocabularyDao;
    @Autowired
    private ConceptDao conceptDao;
    @Autowired
    private ConceptAttributeDao conceptAttributeDao;
    @Autowired
    private ConceptAttributeValuesDao conceptAttributeValuesDao;
    
    private RevisionDaoTestDecorator revisionDaoTestDecorator;
    private VocabularyDaoTestDecorator vocabularyDaoTestDecorator;
    private ConceptDaoTestDecorator conceptDaoTestDecorator;
    private ConceptAttributeDaoTestDecorator conceptAttributeDaoTestDecorator;
    private ConceptAttributeValuesDaoTestDecorator conceptAttributeValuesDaoTestDecorator;
    
    private VocabularyDataService vocabularyDataService;
    
    @Before
    public void setUp() {
        this.revisionDaoTestDecorator = new RevisionDaoTestDecorator(revisionDao, new ExecutionDurationHandler());
        this.vocabularyDaoTestDecorator = new VocabularyDaoTestDecorator(vocabularyDao, new ExecutionDurationHandler());
        this.conceptDaoTestDecorator = new ConceptDaoTestDecorator(conceptDao, new ExecutionDurationHandler());
        this.conceptAttributeDaoTestDecorator = new ConceptAttributeDaoTestDecorator(conceptAttributeDao, new ExecutionDurationHandler());
        this.conceptAttributeValuesDaoTestDecorator = new ConceptAttributeValuesDaoTestDecorator(conceptAttributeValuesDao, new ExecutionDurationHandler());
        
        this.vocabularyDataService = new VocabularyDataServiceImpl(
                vocabularyDaoTestDecorator, conceptDaoTestDecorator, conceptAttributeDaoTestDecorator, 
                conceptAttributeValuesDaoTestDecorator, revisionDaoTestDecorator
        );
    }
    
    @After
    public void tearDown() {
        System.out.println(this.revisionDaoTestDecorator.serializeHandlerResults());
        System.out.println(this.vocabularyDaoTestDecorator.serializeHandlerResults());
        System.out.println(this.conceptDaoTestDecorator.serializeHandlerResults());
        System.out.println(this.conceptAttributeDaoTestDecorator.serializeHandlerResults());
        System.out.println(this.conceptAttributeValuesDaoTestDecorator.serializeHandlerResults());
    }
    
    @Test
    public void testGetLatestVocabulary_Empty() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "some.identifier.that.does.not.exist").run();
        
        assertThat(v, is(nullValue()));
    }
    
    @Test
    public void testGetLatestVocabulary_XSmall() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "lcp.lcpcountries").run();
        
        assertThat(v, is(notNullValue()));
        assertThat("Concept count missmatch", v.getConcepts().size(), is(39));
        assertThat("Concept attribute value sets count missmatch", VocabularyTestUtils.countConceptAttributeValueSets(v), is(28));
        assertThat("Concept attribute values count missmatch", VocabularyTestUtils.countConceptAttributeValues(v), is(28));
        this.assertEntityLinks(v);
    }
    
    @Test
    public void testGetLatestVocabulary_Small() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "cdda.designations").run();
        
        assertThat(v, is(notNullValue()));
        assertThat("Concept count missmatch", v.getConcepts().size(), is(614));
        assertThat("Concept attribute value sets count missmatch", VocabularyTestUtils.countConceptAttributeValueSets(v), is(1228));
        assertThat("Concept attribute values count missmatch", VocabularyTestUtils.countConceptAttributeValues(v), is(1228));
        this.assertEntityLinks(v);
    }
    
    @Test
    public void testGetLatestVocabulary_Medium() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "biodiversity.n2000species").run();

        assertThat(v, is(notNullValue()));
        assertThat("Concept count missmatch", v.getConcepts().size(), is(2940));
        assertThat("Concept attribute value sets count missmatch", VocabularyTestUtils.countConceptAttributeValueSets(v), is(3380));
        assertThat("Concept attribute values count missmatch", VocabularyTestUtils.countConceptAttributeValues(v), is(3406));
        this.assertEntityLinks(v);
    }
    
    @Test
    public void testGetLatestVocabulary_Large() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "lau2.fr").run();
        
        assertThat(v, is(notNullValue()));
        assertThat("Concept count missmatch", v.getConcepts().size(), is(36682));
        assertThat("Concept attribute value sets count missmatch", VocabularyTestUtils.countConceptAttributeValueSets(v), is(36682));
        assertThat("Concept attribute values count missmatch", VocabularyTestUtils.countConceptAttributeValues(v), is(36682));
        this.assertEntityLinks(v);
    }
    
    @Test
    public void testGetLatestVocabulary_XLarge() {
        Vocabulary v = new GetLatestVocabularyExecutionDurationPrinter(vocabularyDataService, "wise.WaterBody").run();
        
        assertThat(v, is(notNullValue()));
        assertThat("Concept count missmatch", v.getConcepts().size(), is(196696));
        assertThat("Concept attribute value sets count missmatch", VocabularyTestUtils.countConceptAttributeValueSets(v), is(677024));
        assertThat("Concept attribute values count missmatch", VocabularyTestUtils.countConceptAttributeValues(v), is(677024));
        this.assertEntityLinks(v);
    }
    
    private void assertEntityLinks(Vocabulary vocabulary) {
        for (VocabularyConcept concept : vocabulary.getConcepts()) {
            assertThat(concept.getVocabulary(), is(notNullValue()));
            assertThat(concept.getVocabulary(), is(sameInstance(vocabulary)));
            
            for (VocabularyConceptAttributeValueSet valueSet : concept.getAttributeValues()) {
                assertThat(valueSet.getConcept(), is(notNullValue()));
                assertThat(valueSet.getConcept(), is(sameInstance(concept)));
                
                assertThat(valueSet.getAttribute(), is(notNullValue()));
                assertThat(valueSet.getAttribute(), isIn(vocabulary.getConceptAttributes()));
                
                for (VocabularyConceptAttributeValue value : valueSet.getValues()) {
                    assertThat(value.getValueSet(), is(notNullValue()));
                    assertThat(value.getValueSet(), is(sameInstance(valueSet)));
                    
                    if (value.getValue() == null) {
                        VocabularyConcept relatedConcept = value.getRelatedConcept();
                        assertThat(relatedConcept, is(notNullValue()));
                        assertThat(relatedConcept.getId(), is(notNullValue()));
                        assertThat(relatedConcept.getIdentifier(), is(notNullValue()));
                        
                        Vocabulary relatedVocabulary = relatedConcept.getVocabulary();
                        assertThat(relatedVocabulary, is(notNullValue()));
                        assertThat(relatedVocabulary.getId(), is(notNullValue()));
                        assertThat(relatedVocabulary.getIdentifier(), is(notNullValue()));
                        
                    }
                    else {
                        assertThat(value.getRelatedConcept(), is(nullValue()));
                    }
                }
            }
        }
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
