package eionet.datadict.services.impl.data.modelutil;

import eionet.datadict.model.Vocabulary;
import eionet.datadict.services.impl.data.util.ObjectKeyProvider;

public class VocabularyIdProvider implements ObjectKeyProvider<Vocabulary, Long> {

    @Override
    public Long getKey(Vocabulary obj) {
        return obj.getId();
    }
    
}
