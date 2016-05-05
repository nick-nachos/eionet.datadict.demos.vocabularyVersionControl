package eionet.datadict.model.versioning.workstate;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class WorkSetVocabularyConceptAttribute {

    private WorkSetVocabulary workSetVocabulary;
    private WorkSetConceptAttribute workSetConceptAttribute;
    
    private WorkActionType action;

    public WorkSetVocabulary getWorkSetVocabulary() {
        return workSetVocabulary;
    }

    public void setWorkSetVocabulary(WorkSetVocabulary workSetVocabulary) {
        this.workSetVocabulary = workSetVocabulary;
    }

    public WorkSetConceptAttribute getWorkSetConceptAttribute() {
        return workSetConceptAttribute;
    }

    public void setWorkSetConceptAttribute(WorkSetConceptAttribute workSetConceptAttribute) {
        this.workSetConceptAttribute = workSetConceptAttribute;
    }

    public WorkActionType getAction() {
        return action;
    }

    public void setAction(WorkActionType action) {
        this.action = action;
    }
    
}
