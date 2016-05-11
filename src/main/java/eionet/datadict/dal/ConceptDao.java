package eionet.datadict.dal;

import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConcept;
import java.util.List;

public interface ConceptDao {
    
    List<VocabularyConcept> getConcepts(Vocabulary vocabulary);
    
    List<VocabularyConcept> getRelatedConcepts(Vocabulary vocabulary);
    
}
