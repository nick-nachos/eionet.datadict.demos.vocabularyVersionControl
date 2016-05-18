package eionet.datadict.testutil.dal;

import eionet.datadict.dal.ConceptAttributeDao;
import eionet.datadict.model.ConceptAttribute;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.testutil.TestDecoratorBase;
import eionet.datadict.testutil.TestDecoratorHandler;
import java.util.List;

public class ConceptAttributeDaoTestDecorator extends TestDecoratorBase<ConceptAttributeDao> implements ConceptAttributeDao {

    public ConceptAttributeDaoTestDecorator(ConceptAttributeDao targetInstance, TestDecoratorHandler... handlers) {
        super(targetInstance, handlers);
    }

    @Override
    public List<ConceptAttribute> getConceptAttributes(Vocabulary vocabulary) {
        return this.execute("ConceptAttributeDao.getConceptAttributes(Vocabulary)", "getConceptAttributes", List.class, vocabulary);
    }
    
}
