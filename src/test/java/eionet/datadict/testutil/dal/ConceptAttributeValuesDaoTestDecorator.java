package eionet.datadict.testutil.dal;

import eionet.datadict.dal.ConceptAttributeValuesDao;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import eionet.datadict.testutil.TestDecoratorBase;
import eionet.datadict.testutil.TestDecoratorHandler;
import java.util.List;

public class ConceptAttributeValuesDaoTestDecorator extends TestDecoratorBase<ConceptAttributeValuesDao> implements ConceptAttributeValuesDao {

    public ConceptAttributeValuesDaoTestDecorator(ConceptAttributeValuesDao targetInstance, TestDecoratorHandler... handlers) {
        super(targetInstance, handlers);
    }

    @Override
    public List<VocabularyConceptAttributeValueSet> getConceptAttributeValues(Vocabulary vocabulary) {
        return this.execute("ConceptAttributeValuesDao.getConceptAttributeValues(Vocabulary)", "getConceptAttributeValues", List.class, vocabulary);
    }

    @Override
    public List<VocabularyConceptAttributeValueSet> getConceptLocalLinks(Vocabulary vocabulary) {
        return this.execute("ConceptAttributeValuesDao.getConceptLocalLinks(Vocabulary)", "getConceptLocalLinks", List.class, vocabulary);
    }

    @Override
    public List<VocabularyConceptAttributeValueSet> getConceptInternalLinks(Vocabulary vocabulary) {
        return this.execute("ConceptAttributeValuesDao.getConceptInternalLinks(Vocabulary)", "getConceptInternalLinks", List.class, vocabulary);
    }

    @Override
    public List<VocabularyConceptAttributeValueSet> getConceptExternalLinks(Vocabulary vocabulary) {
        return this.execute("ConceptAttributeValuesDao.getConceptExternalLinks(Vocabulary)", "getConceptExternalLinks", List.class, vocabulary);
    }
    
}
