package eionet.datadict.model;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public enum VocabularyRegulationStatus {

    DRAFT((byte) 0),
    PUBLIC_DRAFT((byte) 1),
    RELEASED((byte) 2);
    
    private final byte value;
    
    private VocabularyRegulationStatus(byte value) {
        this.value = value;
    }
    
    public byte getValue() {
        return this.value;
    }
    
    public static VocabularyRegulationStatus fromValue(byte value) {
        for (VocabularyRegulationStatus status : VocabularyRegulationStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        
        throw new IllegalArgumentException("Invalid value for VocabularyRegulationStatus: " + value);
    }
    
}
