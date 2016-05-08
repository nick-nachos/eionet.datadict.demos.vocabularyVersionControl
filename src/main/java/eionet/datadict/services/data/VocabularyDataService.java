package eionet.datadict.services.data;

import eionet.datadict.model.Vocabulary;
import java.util.List;

public interface VocabularyDataService {

    List<Vocabulary> getLatestVocabularyEntries();
    
    Vocabulary getLatestVocabulary(String identifier);
    
}
