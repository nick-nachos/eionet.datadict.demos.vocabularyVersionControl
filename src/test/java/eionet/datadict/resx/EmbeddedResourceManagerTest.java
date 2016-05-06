package eionet.datadict.resx;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;

public class EmbeddedResourceManagerTest {

    @Test
    public void testGetTextResource() {
        final String textResourceKey = "text-1";
        
        Map<String, String> resourceMap = new HashMap<>();
        resourceMap.put(textResourceKey, "eionet/datadict/resx/test-resource-1.txt");
        EmbeddedResourceManager resourceManager = new EmbeddedResourceManager(resourceMap);
        String text = resourceManager.getText(textResourceKey);
        
        assertNotNull(text);
        assertEquals("Some text", text.trim());
    }
}
