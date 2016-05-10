package eionet.datadict.model;

public class ConceptAttribute {

    public static enum DataType {
        
        STRING((byte) 0),
        INTEGER((byte) 1);
        
        private final byte value;
        
        private DataType(byte value) {
            this.value = value;
        }
        
        public byte getValue() {
            return value;
        }
        
        public static DataType fromValue(byte value) {
            for (DataType dataType : DataType.values()) {
                if (dataType.getValue() == value) {
                    return dataType;
                }
            }
            
            throw new IllegalArgumentException("Invalid value for DataType: " + value);
        }
        
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
