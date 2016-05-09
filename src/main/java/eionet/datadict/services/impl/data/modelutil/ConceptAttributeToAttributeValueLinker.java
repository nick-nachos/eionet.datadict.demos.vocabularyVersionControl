package eionet.datadict.services.impl.data.modelutil;

import eionet.datadict.model.ConceptAttribute;
import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.services.impl.data.util.ObjectJoinLinker;

public class ConceptAttributeToAttributeValueLinker implements ObjectJoinLinker<ConceptAttribute, VocabularyConceptAttributeValueSet> {

    @Override
    public void link(ConceptAttribute left, VocabularyConceptAttributeValueSet right) {
        if (!left.getId().equals(right.getAttribute().getId())) {
            throw new IllegalArgumentException();
        }

        right.setAttribute(left);
    }

}
