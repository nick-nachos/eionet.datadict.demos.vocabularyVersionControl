package eionet.datadict.model.util;

import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.data.ObjectJoinLinker;

public class VocabularyToConceptLinker implements ObjectJoinLinker<Vocabulary, VocabularyConcept> {

    @Override
    public void link(Vocabulary left, VocabularyConcept right) {
        left.getConcepts().add(right);
        right.setVocabulary(left);
    }
    
}
