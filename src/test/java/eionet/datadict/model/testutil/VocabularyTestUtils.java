package eionet.datadict.model.testutil;

import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConcept;

public class VocabularyTestUtils {
    
    public static int countConceptAttributeValueSets(Vocabulary vocabulary) {
        int count = 0;
        
        for (VocabularyConcept concept : vocabulary.getConcepts()) {
            count += concept.getAttributeValues().size();
        }
        
        return count;
    }
    
    public static int countConceptAttributeValues(Vocabulary vocabulary) {
        int count = 0;
        
        for (VocabularyConcept concept : vocabulary.getConcepts()) {
            count += VocabularyConceptTestUtils.countConceptAttributeValues(concept);
        }
        
        return count;
    }
    
}
