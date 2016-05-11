package eionet.datadict.services.impl.data.modelutil;

import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.model.VocabularyConceptAttributeValue;
import eionet.datadict.services.impl.data.util.ObjectJoinLinker;

public class ConceptAttributeValueToRelatedConceptLinker implements ObjectJoinLinker<VocabularyConcept, VocabularyConceptAttributeValue> {

    @Override
    public void link(VocabularyConcept left, VocabularyConceptAttributeValue right) {
        right.setRelatedConcept(left);
    }
    
}
