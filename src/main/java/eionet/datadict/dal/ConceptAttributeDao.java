package eionet.datadict.dal;

import eionet.datadict.model.ConceptAttribute;
import eionet.datadict.model.Vocabulary;
import java.util.List;

public interface ConceptAttributeDao {

    List<ConceptAttribute> getConceptAttributes(Vocabulary vocabulary);
    
}
