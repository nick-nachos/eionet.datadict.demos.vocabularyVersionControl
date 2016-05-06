package eionet.datadict.resx;

import java.io.InputStream;

public class EmbeddedResourceStreamBuilderImpl implements EmbeddedResourceStreamBuilder {

    @Override
    public InputStream getResourceStream(String resourcePath) {
        return this.getClass().getClassLoader().getResourceAsStream(resourcePath);
    }

}
