package eionet.datadict.model;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class ConceptAttribute {

    public static enum DataType {
        
        STRING,
        INTEGER
    }
    
    private Long id;
    private String identifier;
    private DataType dataType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
