package eionet.datadict.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class VocabularyConceptAttributeValueSet {

    private ConceptAttribute attribute;
    private VocabularyConcept concept;
    
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

    public VocabularyConcept getConcept() {
        return concept;
    }

    public void setConcept(VocabularyConcept concept) {
        this.concept = concept;
    }

    public List<VocabularyConceptAttributeValue> getValues() {
        return values;
    }
    
}
