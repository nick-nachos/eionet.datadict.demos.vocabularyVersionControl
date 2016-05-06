package eionet.datadict.resx;

import java.io.InputStream;

public interface EmbeddedResourceStreamBuilder {

    InputStream getResourceStream(String resourcePath);
    
}
