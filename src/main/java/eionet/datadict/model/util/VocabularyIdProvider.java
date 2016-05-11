package eionet.datadict.model.util;

import eionet.datadict.model.Vocabulary;
import eionet.datadict.data.ObjectKeyProvider;

public class VocabularyIdProvider implements ObjectKeyProvider<Vocabulary, Long> {

    @Override
    public Long getKey(Vocabulary obj) {
        return obj.getId();
    }
    
}
