package eionet.datadict.services.impl.data.modelutil;

import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.services.impl.data.util.ObjectKeyProvider;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class RelatedConceptIdProvider implements ObjectKeyProvider<VocabularyConcept, Pair<Long, Long>> {

    @Override
    public Pair<Long, Long> getKey(VocabularyConcept obj) {
        return ImmutablePair.of(obj.getVocabulary().getId(), obj.getId());
    }
    
}
