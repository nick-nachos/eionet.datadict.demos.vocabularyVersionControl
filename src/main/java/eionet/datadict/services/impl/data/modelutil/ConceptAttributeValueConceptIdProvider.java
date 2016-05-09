package eionet.datadict.services.impl.data.modelutil;

import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.services.impl.data.util.ObjectKeyProvider;

public class ConceptAttributeValueConceptIdProvider implements ObjectKeyProvider<VocabularyConceptAttributeValueSet, Long> {

    @Override
    public Long getKey(VocabularyConceptAttributeValueSet obj) {
        return obj.getConcept().getId();
    }

}
