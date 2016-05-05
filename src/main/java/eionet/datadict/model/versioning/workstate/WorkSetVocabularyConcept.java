package eionet.datadict.model.versioning.workstate;

import eionet.datadict.model.VocabularyConcept;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class WorkSetVocabularyConcept {

    private WorkSetVocabulary workSetVocabulary;
    
    private Long id;
    private WorkActionType action;
    
    private VocabularyConcept vocabularyConcept;

    public WorkSetVocabulary getWorkSetVocabulary() {
        return workSetVocabulary;
    }

    public void setWorkSetVocabulary(WorkSetVocabulary workSetVocabulary) {
        this.workSetVocabulary = workSetVocabulary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkActionType getAction() {
        return action;
    }

    public void setAction(WorkActionType action) {
        this.action = action;
    }

    public VocabularyConcept getVocabularyConcept() {
        return vocabularyConcept;
    }

    public void setVocabularyConcept(VocabularyConcept vocabularyConcept) {
        this.vocabularyConcept = vocabularyConcept;
    }
    
}
