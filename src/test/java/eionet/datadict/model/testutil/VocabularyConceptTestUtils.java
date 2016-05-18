package eionet.datadict.model.testutil;

import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.model.VocabularyConceptAttributeValueSet;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class VocabularyConceptTestUtils {

    public static int countConceptAttributeValues(VocabularyConcept concept) {
        int count = 0;
        
        for (VocabularyConceptAttributeValueSet valueSet : concept.getAttributeValues()) {
            count += valueSet.getValues().size();
        }
        
        return count;
    }
    
}
