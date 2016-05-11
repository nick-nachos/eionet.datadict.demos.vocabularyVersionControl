package eionet.datadict.model.versioning.workstate;

import eionet.datadict.model.DataType;

public class WorkSetConceptAttributeData {
    
    private WorkSetConceptAttribute workSetConceptAttribute;
    
    private String identifier;
    private DataType dataType;

    public WorkSetConceptAttribute getWorkSetConceptAttribute() {
        return workSetConceptAttribute;
    }

    public void setWorkSetConceptAttribute(WorkSetConceptAttribute workSetConceptAttribute) {
        this.workSetConceptAttribute = workSetConceptAttribute;
    }
    
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
    
}
