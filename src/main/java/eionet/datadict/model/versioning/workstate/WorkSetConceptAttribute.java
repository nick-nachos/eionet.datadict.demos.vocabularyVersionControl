package eionet.datadict.model.versioning.workstate;

import eionet.datadict.model.ConceptAttribute;

public class WorkSetConceptAttribute {

    private WorkSet workSet;
    
    private Long id;
    private WorkActionType action;
    
    private WorkSetConceptAttributeData data;
    
    private ConceptAttribute conceptAttribute;

    public WorkSet getWorkSet() {
        return workSet;
    }

    public void setWorkSet(WorkSet workSet) {
        this.workSet = workSet;
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

    public WorkSetConceptAttributeData getData() {
        return data;
    }

    public void setData(WorkSetConceptAttributeData data) {
        this.data = data;
    }
    
    public ConceptAttribute getConceptAttribute() {
        return conceptAttribute;
    }

    public void setConceptAttribute(ConceptAttribute conceptAttribute) {
        this.conceptAttribute = conceptAttribute;
    }
    
}
