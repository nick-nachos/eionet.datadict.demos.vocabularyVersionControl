package eionet.datadict.model;

public enum DataType {
    
    STRING((byte) 0),
    BOOLEAN((byte) 1),
    INTEGER((byte) 2),
    FLOAT((byte) 3),
    DOUBLE((byte) 4),
    DECIMAL((byte) 5),
    DATE((byte) 6),
    LOCAL_REFERENCE((byte) 7),
    REFERENCE((byte) 8);

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
