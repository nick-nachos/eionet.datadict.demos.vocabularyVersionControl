package eionet.datadict.model.util;

import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.data.ObjectKeyProvider;

public class ConceptVocabularyIdProvider implements ObjectKeyProvider<VocabularyConcept, Long> {

    @Override
    public Long getKey(VocabularyConcept obj) {
        return obj.getVocabulary().getId();
    }
    
}
