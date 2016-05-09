package eionet.datadict.services.impl.data.modelutil;

import eionet.datadict.model.ConceptAttribute;
import eionet.datadict.services.impl.data.util.ObjectKeyProvider;

public class ConceptAttributeIdProvider implements ObjectKeyProvider<ConceptAttribute, Long> {

    @Override
    public Long getKey(ConceptAttribute obj) {
        return obj.getId();
    }

}
