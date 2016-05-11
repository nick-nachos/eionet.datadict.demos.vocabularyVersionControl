package eionet.datadict.model.util;

import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.data.ObjectKeyProvider;

public class ConceptAttributeValueAttributeIdProvider implements ObjectKeyProvider<VocabularyConceptAttributeValueSet, Long> {

    @Override
    public Long getKey(VocabularyConceptAttributeValueSet obj) {
        return obj.getAttribute().getId();
    }

}
