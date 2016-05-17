package eionet.datadict.dal;

import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.versioning.Revision;
import java.util.List;
import java.util.Set;

public interface VocabularyDao {

    List<Vocabulary> getVocabularies(Revision revision);
    
    List<Vocabulary> getVocabularies(Set<Long> vocabularyIds);
    
    Vocabulary getVocabulary(Long id);
    
    Vocabulary getVocabulary(Revision revision, String identifier);
    
    List<Vocabulary> getRelatedVocabularies(Vocabulary vocabulary);
    
}
