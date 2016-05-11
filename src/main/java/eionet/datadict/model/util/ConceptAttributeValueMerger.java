package eionet.datadict.model.util;

import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.data.ObjectJoinLinker;

public class ConceptAttributeValueMerger implements ObjectJoinLinker<VocabularyConceptAttributeValueSet, VocabularyConceptAttributeValueSet> {

    @Override
    public void link(VocabularyConceptAttributeValueSet left, VocabularyConceptAttributeValueSet right) {
        left.getValues().addAll(right.getValues());
    }
    
}
