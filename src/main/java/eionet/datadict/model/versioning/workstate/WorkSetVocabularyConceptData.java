package eionet.datadict.model.versioning.workstate;

import eionet.datadict.model.StandardGenericStatus;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class WorkSetVocabularyConceptData {

    private WorkSetVocabularyConcept workSetVocabularyConcept;
    
    private String identifier;
    private String notation;
    private String label;
    private String definition;
    private StandardGenericStatus status;

    public WorkSetVocabularyConcept getWorkSetVocabularyConcept() {
        return workSetVocabularyConcept;
    }

    public void setWorkSetVocabularyConcept(WorkSetVocabularyConcept workSetVocabularyConcept) {
        this.workSetVocabularyConcept = workSetVocabularyConcept;
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
    
}
