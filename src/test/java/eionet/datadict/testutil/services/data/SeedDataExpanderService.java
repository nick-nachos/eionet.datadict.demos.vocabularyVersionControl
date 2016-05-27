package eionet.datadict.testutil.services.data;

import eionet.datadict.testutil.dal.TestAssistanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeedDataExpanderService {

    private final TestAssistanceDao testAssistanceDao;
    
    @Autowired
    public SeedDataExpanderService(TestAssistanceDao testAssistanceDao) {
        this.testAssistanceDao = testAssistanceDao;
    }
    
    public void expandSeedData() {
        this.testAssistanceDao.newVocabulariesByCopy();
        this.testAssistanceDao.linkVocabulariesToConcepts();
        this.testAssistanceDao.linkVocabulariesToConceptAttributes();
        this.testAssistanceDao.linkConceptsToAttributeValues();
        this.testAssistanceDao.linkConceptsToExternalConcepts();
        this.testAssistanceDao.linkConceptsToInternalConcepts();
        this.testAssistanceDao.createVocabularyVersions();
        this.testAssistanceDao.createRevisions();
        this.testAssistanceDao.linkRevisions();
        this.testAssistanceDao.linkRevisionsToVocabularyVersions();
        this.testAssistanceDao.linkRevisionsToConceptAttributeVersions();
        this.testAssistanceDao.clearWorkingState();
    }
    
}
