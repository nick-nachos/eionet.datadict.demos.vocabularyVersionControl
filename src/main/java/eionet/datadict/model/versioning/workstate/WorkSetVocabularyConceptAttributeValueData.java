package eionet.datadict.model.versioning.workstate;

public class WorkSetVocabularyConceptAttributeValueData {
    
    private WorkSetVocabularyConceptAttributeValue workSetVocabularyConceptAttributeValue;
    
    private String language;
    private String value;
    private WorkSetVocabularyConcept relatedConcept;

    public WorkSetVocabularyConceptAttributeValue getWorkSetVocabularyConceptAttributeValue() {
        return workSetVocabularyConceptAttributeValue;
    }

    public void setWorkSetVocabularyConceptAttributeValue(WorkSetVocabularyConceptAttributeValue workSetVocabularyConceptAttributeValue) {
        this.workSetVocabularyConceptAttributeValue = workSetVocabularyConceptAttributeValue;
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

    public WorkSetVocabularyConcept getRelatedConcept() {
        return relatedConcept;
    }

    public void setRelatedConcept(WorkSetVocabularyConcept relatedConcept) {
        this.relatedConcept = relatedConcept;
    }
    
}
