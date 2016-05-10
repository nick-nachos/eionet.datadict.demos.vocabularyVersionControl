package eionet.datadict.model.versioning.workstate;

import eionet.datadict.model.VocabularyRegulationStatus;

public class WorkSetVocabularyData {

    private WorkSetVocabulary workSetVocabulary;
    
    private String identifier;
    private String label;
    private String baseUri;
    private VocabularyRegulationStatus regulationStatus;

    public WorkSetVocabulary getWorkSetVocabulary() {
        return workSetVocabulary;
    }

    public void setWorkSetVocabulary(WorkSetVocabulary workSetVocabulary) {
        this.workSetVocabulary = workSetVocabulary;
    }
    
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public VocabularyRegulationStatus getRegulationStatus() {
        return regulationStatus;
    }

    public void setRegulationStatus(VocabularyRegulationStatus regulationStatus) {
        this.regulationStatus = regulationStatus;
    }
    
}
