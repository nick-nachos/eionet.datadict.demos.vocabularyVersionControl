package eionet.datadict.services.impl.data;

import eionet.datadict.testutil.ExecutionDurationPrinter;
import eionet.datadict.testutil.services.data.SeedDataExpanderService;
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
    
    private static SeedDataExpanderService seedDataExpanderService;
    
    private static boolean dbSetupDone;
    
    @Autowired
    private SeedDataExpanderService seedDataExpanderServiceInstance;
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        
        if (dbSetupDone) {
            return;
        }
        
        seedDataExpanderService = seedDataExpanderServiceInstance;
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
                    seedDataExpanderService.expandSeedData();
                    
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
