package eionet.datadict.services.impl.data;

import eionet.datadict.dal.impl.util.TestAssistanceDao;
import eionet.datadict.resx.EmbeddedResourceManager;
import eionet.datadict.testutil.ExecutionDurationPrinter;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@ContextConfiguration(locations = { "classpath:spring-config.xml", "classpath:spring-test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VocabularyDataServiceImplExtendedRegressionTest extends VocabularyDataServiceImplRegressionTest {
    
    private static TestAssistanceDao testAssistanceDao;
    
    private static boolean dbSetupDone;
    
    @Autowired
    private DataSource dataSource;
    
    @Resource(name = "testResourceMap")
    private Map<String, String> testResourceMap;
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        
        if (dbSetupDone) {
            return;
        }
        
        testAssistanceDao = new TestAssistanceDao(dataSource, new EmbeddedResourceManager(testResourceMap));
        initDbData();
        dbSetupDone = true;
    }
    
    @AfterClass
    public static void tearDownClass() {
        restoreDbData();
    }
    
    private static void initDbData() {
        ExecutionDurationPrinter<Object> printer = new ExecutionDurationPrinter<Object>() {

            @Override
            protected String getOperationDescription() {
                return "test database data expansion";
            }

            @Override
            protected Object execute() throws Exception {
                int genSize = 50;
                
                for (int i = 0; i < genSize; i++) {
                    testAssistanceDao.newVocabulariesByCopy();
                    testAssistanceDao.linkVocabulariesToConcepts();
                    testAssistanceDao.linkVocabulariesToConceptAttributes();
                    testAssistanceDao.linkConceptsToAttributeValues();
                    testAssistanceDao.createVocabularyVersions();
                    testAssistanceDao.createRevisions();
                    testAssistanceDao.linkRevisions();
                    testAssistanceDao.linkRevisionsToVocabularyVersions();
                    testAssistanceDao.linkRevisionsToConceptAttributeVersions();
                    testAssistanceDao.clearWorkingState();
                    
                    System.out.format("Gen %d out of %d complete.%n", i + 1, genSize);
                }
                
                return null;
            }
        };
        
        printer.run();
    }
    
    private static void restoreDbData() {
        
    }
    
}
