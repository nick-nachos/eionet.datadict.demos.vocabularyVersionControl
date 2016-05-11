package eionet.datadict.services.impl.data.modelutil;

import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.services.impl.data.util.ObjectKeyProvider;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class ConceptAttributeValueIdProvider implements ObjectKeyProvider<VocabularyConceptAttributeValueSet, Pair<Long, Long>> {

    @Override
    public Pair<Long, Long> getKey(VocabularyConceptAttributeValueSet obj) {
        return ImmutablePair.of(obj.getConcept().getId(), obj.getAttribute().getId());
    }
    
}
