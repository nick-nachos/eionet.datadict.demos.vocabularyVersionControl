package eionet.datadict.model.util;

import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.data.ObjectJoinLinker;

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
