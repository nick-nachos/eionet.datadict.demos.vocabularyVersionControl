package eionet.datadict.model.util;

import eionet.datadict.model.ConceptAttribute;
import eionet.datadict.data.ObjectKeyProvider;

public class ConceptAttributeIdProvider implements ObjectKeyProvider<ConceptAttribute, Long> {

    @Override
    public Long getKey(ConceptAttribute obj) {
        return obj.getId();
    }

}
