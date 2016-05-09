package eionet.datadict.resx;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;

public class EmbeddedResourceManager {

    private final EmbeddedResourceStreamBuilder resourceStreamBuilder;
    private final Map<String, String> resourceMappings;
    
    private final Map<String, String> resources;
    
    public EmbeddedResourceManager(Map<String, String> resourceMappings) {
        this(new EmbeddedResourceStreamBuilderImpl(), resourceMappings);
    }
    
    public EmbeddedResourceManager(EmbeddedResourceStreamBuilder resourceStreamBuilder, Map<String, String> resourceMappings) {
        if (resourceMappings == null) {
            throw new IllegalArgumentException();
        }
        
        this.resourceStreamBuilder = resourceStreamBuilder;
        this.resourceMappings = resourceMappings;
        
        this.resources = new HashMap<>();
        
        for (String resourceKey : this.resourceMappings.keySet()) {
            String textValue = this.readTextResource(resourceKey);
            this.resources.put(resourceKey, textValue);
        }
    }
    
    public String getText(String resourceKey) {
        if (!this.resources.containsKey(resourceKey)) {
            throw new ResourceUnavailableException("Undefined resource key: " + resourceKey);
        }
        
        return this.resources.get(resourceKey);
    }
    
    private String readTextResource(String resourceKey) {
        String resourcePath = this.resourceMappings.get(resourceKey);
        InputStream resourceStream = null;
        
        try {
            resourceStream = this.resourceStreamBuilder.getResourceStream(resourcePath);
            
            if (resourceStream == null) {
                throw new ResourceUnavailableException("Cannot find embedded resource at: " + resourcePath);
            }
            
            return IOUtils.toString(resourceStream, "UTF8");
        }
        catch (IOException ex) {
            throw new ResourceUnavailableException(ex);
        }
        finally {
            IOUtils.closeQuietly(resourceStream);
        }
    }
    
}
