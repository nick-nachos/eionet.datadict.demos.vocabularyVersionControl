package eionet.datadict.model;

public class VocabularyConceptAttributeValue {

    private VocabularyConceptAttributeValueSet valueSet;
    
    private Long id;
    private String language;
    private String value;
    private VocabularyConcept relatedConcept;

    public VocabularyConceptAttributeValueSet getValueSet() {
        return valueSet;
    }

    public void setValueSet(VocabularyConceptAttributeValueSet valueSet) {
        this.valueSet = valueSet;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public VocabularyConcept getRelatedConcept() {
        return relatedConcept;
    }

    public void setRelatedConcept(VocabularyConcept relatedConcept) {
        this.relatedConcept = relatedConcept;
    }
    
}
