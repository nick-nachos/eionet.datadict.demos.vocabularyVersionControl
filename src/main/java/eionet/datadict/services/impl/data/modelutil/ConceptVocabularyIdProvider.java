package eionet.datadict.services.impl.data.modelutil;

import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.services.impl.data.util.ObjectKeyProvider;

public class ConceptVocabularyIdProvider implements ObjectKeyProvider<VocabularyConcept, Long> {

    @Override
    public Long getKey(VocabularyConcept obj) {
        return obj.getVocabulary().getId();
    }
    
}
