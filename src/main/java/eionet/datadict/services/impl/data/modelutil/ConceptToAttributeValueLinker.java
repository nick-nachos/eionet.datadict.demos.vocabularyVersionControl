package eionet.datadict.services.impl.data.modelutil;

import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.services.impl.data.util.ObjectJoinLinker;

public class ConceptToAttributeValueLinker implements ObjectJoinLinker<VocabularyConcept, VocabularyConceptAttributeValueSet> {

    @Override
    public void link(VocabularyConcept left, VocabularyConceptAttributeValueSet right) {
        if (!left.getId().equals(right.getConcept().getId())) {
            throw new IllegalArgumentException();
        }

        left.getAttributeValues().add(right);
        right.setConcept(left);
    }

}
