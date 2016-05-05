package eionet.datadict.model.versioning;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public abstract class EntityVersion<T> {

    private Long id;
    private T entity;
    private EntityVersion rootVersion;
    private EntityVersion parentVersion;
    private String userName;
    private Long commitDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public EntityVersion getRootVersion() {
        return rootVersion;
    }

    public void setRootVersion(EntityVersion rootVersion) {
        this.rootVersion = rootVersion;
    }

    public EntityVersion getParentVersion() {
        return parentVersion;
    }

    public void setParentVersion(EntityVersion parentVersion) {
        this.parentVersion = parentVersion;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Long commitDate) {
        this.commitDate = commitDate;
    }
    
}
