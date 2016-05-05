package eionet.datadict.model.versioning.workstate;

import eionet.datadict.model.Vocabulary;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class WorkSetVocabulary {
    
    private WorkSet workSet;
    
    private Long id;
    private WorkActionType action;
    
    private WorkSetVocabularyData data;
    private final List<WorkSetVocabularyConcept> workSetConcepts;
    
    private Vocabulary vocabulary;
    
    public WorkSetVocabulary() {
        this.workSetConcepts = new LinkedList<>();
    }
    
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
    
    public WorkSetVocabularyData getData() {
        return data;
    }

    public void setData(WorkSetVocabularyData data) {
        this.data = data;
    }

    public List<WorkSetVocabularyConcept> getWorkSetConcepts() {
        return workSetConcepts;
    }
    
    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }
    
}
