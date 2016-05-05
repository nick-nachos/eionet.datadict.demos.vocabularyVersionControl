package eionet.datadict.model.versioning;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class Revision {

    private Long id;
    private Long creationDate;
    private String userName;
    
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

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<VocabularyVersion> getVocabularyVersions() {
        return vocabularyVersions;
    }

    public List<ConceptAttributeVersion> getConceptAttributeVersions() {
        return conceptAttributeVersions;
    }
    
}
