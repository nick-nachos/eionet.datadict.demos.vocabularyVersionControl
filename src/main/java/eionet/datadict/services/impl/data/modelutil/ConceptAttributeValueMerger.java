package eionet.datadict.services.impl.data.modelutil;

import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.services.impl.data.util.ObjectJoinLinker;

public class ConceptAttributeValueMerger implements ObjectJoinLinker<VocabularyConceptAttributeValueSet, VocabularyConceptAttributeValueSet> {

    @Override
    public void link(VocabularyConceptAttributeValueSet left, VocabularyConceptAttributeValueSet right) {
        left.getValues().addAll(right.getValues());
    }
    
}
