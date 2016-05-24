package eionet.datadict.services.impl.data;

import eionet.datadict.testutil.ExecutionDurationPrinter;
import eionet.datadict.testutil.services.data.SeedDataExpanderConfiguration;
import eionet.datadict.testutil.services.data.SeedDataExpanderService;
import org.junit.Assume;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring-config.xml", "classpath:spring-test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VocabularyDataServiceImplExtendedRegressionTest extends VocabularyDataServiceImplRegressionTest {
    
    private static SeedDataExpanderService seedDataExpanderService;
    private static SeedDataExpanderConfiguration seedDataExpanderConfiguration;
    
    private static boolean dbSetupDone;
    
    @Autowired
    private SeedDataExpanderService seedDataExpanderServiceInstance;
    @Autowired
    private SeedDataExpanderConfiguration seedDataExpanderConfigurationInstance;
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        
        Assume.assumeTrue(this.seedDataExpanderConfigurationInstance.getExpansionFactor() > 0);
        
        if (dbSetupDone) {
            return;
        }
        
        seedDataExpanderService = seedDataExpanderServiceInstance;
        seedDataExpanderConfiguration = seedDataExpanderConfigurationInstance;
        initDbData();
        dbSetupDone = true;
    }
    
    private static void initDbData() {
        ExecutionDurationPrinter<Object> printer = new ExecutionDurationPrinter<Object>() {

            @Override
            protected String getOperationDescription() {
                return "test database data expansion";
            }

            @Override
            protected Object execute() throws Exception {
                int genSize = seedDataExpanderConfiguration.getExpansionFactor();
                
                for (int i = 0; i < genSize; i++) {
                    System.out.format("Expanding %d out of %d... ", i + 1, genSize);
                    seedDataExpanderService.expandSeedData();
                    System.out.format("Done.%n", i + 1, genSize);
                }
                
                return null;
            }
        };
        
        printer.run();
    }
    
}
