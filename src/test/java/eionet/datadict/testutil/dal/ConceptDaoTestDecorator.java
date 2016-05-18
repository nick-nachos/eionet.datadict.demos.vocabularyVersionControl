package eionet.datadict.testutil.dal;

import eionet.datadict.dal.ConceptDao;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConcept;
import eionet.datadict.testutil.TestDecoratorBase;
import eionet.datadict.testutil.TestDecoratorHandler;
import java.util.List;

public class ConceptDaoTestDecorator extends TestDecoratorBase<ConceptDao> implements ConceptDao {

    public ConceptDaoTestDecorator(ConceptDao targetInstance, TestDecoratorHandler... handlers) {
        super(targetInstance, handlers);
    }

    @Override
    public List<VocabularyConcept> getConcepts(Vocabulary vocabulary) {
        return this.execute("ConceptDao.getConcepts(Vocabulary)", "getConcepts", List.class, vocabulary);
    }

    @Override
    public List<VocabularyConcept> getRelatedConcepts(Vocabulary vocabulary) {
        return this.execute("ConceptDao.getRelatedConcepts(Vocabulary)", "getRelatedConcepts", List.class, vocabulary);
    }
    
}
