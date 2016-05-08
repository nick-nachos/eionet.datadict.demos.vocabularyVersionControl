package eionet.datadict.dal;

import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.versioning.Revision;
import java.util.List;

public interface VocabularyDao {

    List<Vocabulary> getVocabularies(Revision revision);
    
    Vocabulary getVocabulary(Revision revision);
    
}
