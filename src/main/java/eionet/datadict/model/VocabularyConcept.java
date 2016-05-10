package eionet.datadict.model;

import java.util.LinkedList;
import java.util.List;

public class VocabularyConcept {

    private Vocabulary vocabulary;
    
    private Long id;
    private String identifier;
    private String notation;
    private String label;
    private String definition;
    private StandardGenericStatus status;
    
    private final List<VocabularyConceptAttributeValueSet> attributeValues;
    
    public VocabularyConcept() {
        this.attributeValues = new LinkedList<>();
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public StandardGenericStatus getStatus() {
        return status;
    }

    public void setStatus(StandardGenericStatus status) {
        this.status = status;
    }

    public List<VocabularyConceptAttributeValueSet> getAttributeValues() {
        return attributeValues;
    }
    
}
