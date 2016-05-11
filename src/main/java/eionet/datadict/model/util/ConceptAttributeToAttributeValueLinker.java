package eionet.datadict.model.util;

import eionet.datadict.model.ConceptAttribute;
import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.data.ObjectJoinLinker;

public class ConceptAttributeToAttributeValueLinker implements ObjectJoinLinker<ConceptAttribute, VocabularyConceptAttributeValueSet> {

    @Override
    public void link(ConceptAttribute left, VocabularyConceptAttributeValueSet right) {
        if (!left.getId().equals(right.getAttribute().getId())) {
            throw new IllegalArgumentException();
        }

        right.setAttribute(left);
    }

}
