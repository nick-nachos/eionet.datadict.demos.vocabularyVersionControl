package eionet.datadict.services.impl.data.modelutil;

import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.services.impl.data.util.ObjectJoinLinker;

public class VocabularyToConceptLinker implements ObjectJoinLinker<Vocabulary, VocabularyConcept> {

    @Override
    public void link(Vocabulary left, VocabularyConcept right) {
        left.getConcepts().add(right);
        right.setVocabulary(left);
    }
    
}
