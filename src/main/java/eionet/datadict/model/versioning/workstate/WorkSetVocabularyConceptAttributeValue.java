package eionet.datadict.model.versioning.workstate;

import eionet.datadict.model.VocabularyConceptAttributeValue;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class WorkSetVocabularyConceptAttributeValue {

    private WorkSetVocabularyConcept workSetVocabularyConcept;
    private WorkSetVocabularyConceptAttribute workSetVocabularyConceptAttribute;
    
    private WorkActionType action;
    
    private WorkSetVocabularyConceptAttributeValueData data;
    
    private VocabularyConceptAttributeValue vocabularyConceptAttributeValue;

    public WorkSetVocabularyConcept getWorkSetVocabularyConcept() {
        return workSetVocabularyConcept;
    }

    public void setWorkSetVocabularyConcept(WorkSetVocabularyConcept workSetVocabularyConcept) {
        this.workSetVocabularyConcept = workSetVocabularyConcept;
    }

    public WorkSetVocabularyConceptAttribute getWorkSetVocabularyConceptAttribute() {
        return workSetVocabularyConceptAttribute;
    }

    public void setWorkSetVocabularyConceptAttribute(WorkSetVocabularyConceptAttribute workSetVocabularyConceptAttribute) {
        this.workSetVocabularyConceptAttribute = workSetVocabularyConceptAttribute;
    }

    public WorkActionType getAction() {
        return action;
    }

    public void setAction(WorkActionType action) {
        this.action = action;
    }

    public WorkSetVocabularyConceptAttributeValueData getData() {
        return data;
    }

    public void setData(WorkSetVocabularyConceptAttributeValueData data) {
        this.data = data;
    }

    public VocabularyConceptAttributeValue getVocabularyConceptAttributeValue() {
        return vocabularyConceptAttributeValue;
    }

    public void setVocabularyConceptAttributeValue(VocabularyConceptAttributeValue vocabularyConceptAttributeValue) {
        this.vocabularyConceptAttributeValue = vocabularyConceptAttributeValue;
    }
    
}
