package eionet.datadict.services.impl.data;

import eionet.datadict.dal.ConceptAttributeDao;
import eionet.datadict.dal.VocabularyDao;
import eionet.datadict.dal.versioning.RevisionDao;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.versioning.Revision;
import eionet.datadict.services.data.VocabularyDataService;
import java.util.List;

public class VocabularyDataServiceImpl implements VocabularyDataService {

    private final VocabularyDao vocabularyDao;
    private final ConceptAttributeDao conceptAttributeDao;
    private final RevisionDao revisionDao;
    
    public VocabularyDataServiceImpl(VocabularyDao vocabularyDao, ConceptAttributeDao conceptAttributeDao, RevisionDao revisionDao) {
        this.vocabularyDao = vocabularyDao;
        this.conceptAttributeDao = conceptAttributeDao;
        this.revisionDao = revisionDao;
    }
    
    @Override
    public List<Vocabulary> getLatestVocabularyEntries() {
        Revision latestRevision = this.revisionDao.getLatestRevision();
        
        return this.vocabularyDao.getVocabularies(latestRevision);
    }

    @Override
    public Vocabulary getLatestVocabulary(String identifier) {
        Revision latestRevision = this.revisionDao.getLatestRevision();
        Vocabulary vocabulary = this.vocabularyDao.getVocabulary(latestRevision);
        vocabulary.getAttributes().addAll(this.conceptAttributeDao.getConceptAttributes(vocabulary));
        
        throw new UnsupportedOperationException();
    }

}
