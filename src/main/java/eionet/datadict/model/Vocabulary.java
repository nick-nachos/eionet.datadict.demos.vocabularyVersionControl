package eionet.datadict.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class Vocabulary {

    private Long id;
    private String identifier;
    private String label;
    private String baseUri;
    private VocabularyRegulationStatus regulationStatus;
    
    private final List<ConceptAttribute> attributes;
    
    private final List<VocabularyConcept> concepts;
    
    public Vocabulary() {
        this.attributes = new LinkedList<>();
        this.concepts = new LinkedList<>();
    }

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public VocabularyRegulationStatus getRegulationStatus() {
        return regulationStatus;
    }

    public void setRegulationStatus(VocabularyRegulationStatus regulationStatus) {
        this.regulationStatus = regulationStatus;
    }

    public List<ConceptAttribute> getAttributes() {
        return attributes;
    }
    
    public List<VocabularyConcept> getConcepts() {
        return concepts;
    }
    
}
