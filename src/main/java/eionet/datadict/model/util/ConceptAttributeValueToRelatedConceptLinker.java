package eionet.datadict.model.util;

import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.model.VocabularyConceptAttributeValue;
import eionet.datadict.data.ObjectJoinLinker;

public class ConceptAttributeValueToRelatedConceptLinker implements ObjectJoinLinker<VocabularyConcept, VocabularyConceptAttributeValue> {

    @Override
    public void link(VocabularyConcept left, VocabularyConceptAttributeValue right) {
        right.setRelatedConcept(left);
    }
    
}
