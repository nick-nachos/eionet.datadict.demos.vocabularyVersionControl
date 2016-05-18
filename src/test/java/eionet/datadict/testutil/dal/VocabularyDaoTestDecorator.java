package eionet.datadict.testutil.dal;

import eionet.datadict.dal.VocabularyDao;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.versioning.Revision;
import eionet.datadict.testutil.TestDecoratorBase;
import eionet.datadict.testutil.TestDecoratorHandler;
import java.util.List;
import java.util.Set;

public class VocabularyDaoTestDecorator extends TestDecoratorBase<VocabularyDao> implements VocabularyDao {
    
    public VocabularyDaoTestDecorator(VocabularyDao targetInstance, TestDecoratorHandler... handlers) {
        super(targetInstance, handlers);
    }

    @Override
    public List<Vocabulary> getVocabularies(Revision revision) {
        return this.execute("VocabularyDao.getVocabularies(Revision)", "getVocabularies", List.class, revision);
    }

    @Override
    public List<Vocabulary> getVocabularies(Set<Long> vocabularyIds) {
        return this.execute("VocabularyDao.getVocabularies(Set<Long>)", "getVocabularies", List.class, vocabularyIds);
    }

    @Override
    public Vocabulary getVocabulary(Long id) {
        return this.execute("VocabularyDao.getVocabulary(Long)", "getVocabulary", Vocabulary.class, id);
    }

    @Override
    public Vocabulary getVocabulary(Revision revision, String identifier) {
        return this.execute("VocabularyDao.getVocabulary(Revision, String)", "getVocabulary", Vocabulary.class, revision, identifier);
    }

    @Override
    public List<Vocabulary> getRelatedVocabularies(Vocabulary vocabulary) {
        return this.execute("VocabularyDao.getRelatedVocabularies(Vocabulary)", "getRelatedVocabularies", List.class, vocabulary);
    }
    
}
