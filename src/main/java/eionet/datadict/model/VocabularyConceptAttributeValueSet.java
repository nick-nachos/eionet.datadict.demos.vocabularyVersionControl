package eionet.datadict.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class VocabularyConceptAttributeValueSet {

    private ConceptAttribute attribute;
    private Vocabulary vocabulary;
    
    private final List<VocabularyConceptAttributeValue> values;

    public VocabularyConceptAttributeValueSet() {
        this.values = new LinkedList<>();
    }
    
    public ConceptAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(ConceptAttribute attribute) {
        this.attribute = attribute;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }
    
    public List<VocabularyConceptAttributeValue> getValues() {
        return values;
    }
    
}
