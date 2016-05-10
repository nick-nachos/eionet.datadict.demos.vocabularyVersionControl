package eionet.datadict.model.versioning;

import java.util.LinkedList;
import java.util.List;
import org.joda.time.DateTime;

public class Revision {

    private Long id;
    private DateTime creationDate;
    private String userName;
    
    private Revision parent;
    
    private final List<VocabularyVersion> vocabularyVersions;
    private final List<ConceptAttributeVersion> conceptAttributeVersions;
    
    public Revision() {
        this.vocabularyVersions = new LinkedList<>();
        this.conceptAttributeVersions = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Revision getParent() {
        return parent;
    }

    public void setParent(Revision parent) {
        this.parent = parent;
    }
    
    public List<VocabularyVersion> getVocabularyVersions() {
        return vocabularyVersions;
    }

    public List<ConceptAttributeVersion> getConceptAttributeVersions() {
        return conceptAttributeVersions;
    }
    
}
