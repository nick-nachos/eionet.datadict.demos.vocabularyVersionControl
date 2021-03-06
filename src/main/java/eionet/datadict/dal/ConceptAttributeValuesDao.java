package eionet.datadict.dal;

import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import java.util.List;

public interface ConceptAttributeValuesDao {

    List<VocabularyConceptAttributeValueSet> getConceptAttributeValues(Vocabulary vocabulary);
    
    List<VocabularyConceptAttributeValueSet> getConceptLocalLinks(Vocabulary vocabulary);
    
    List<VocabularyConceptAttributeValueSet> getConceptInternalLinks(Vocabulary vocabulary);
    
    List<VocabularyConceptAttributeValueSet> getConceptExternalLinks(Vocabulary vocabulary);
    
}
