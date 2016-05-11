package eionet.datadict.model.util;

import eionet.datadict.model.VocabularyConceptAttributeValue;
import eionet.datadict.data.ObjectKeyProvider;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class ConceptAttributeValueRelatedConceptIdProvider implements ObjectKeyProvider<VocabularyConceptAttributeValue, Pair<Long, Long>> {

    @Override
    public Pair<Long, Long> getKey(VocabularyConceptAttributeValue obj) {
        return ImmutablePair.of(obj.getRelatedConcept().getVocabulary().getId(), obj.getRelatedConcept().getId());
    }
    
}
