package eionet.datadict.dal.impl;

import eionet.datadict.dal.ConceptAttributeValuesDao;
import eionet.datadict.model.Vocabulary;
import eionet.datadict.model.VocabularyConceptAttributeValue;
import eionet.datadict.model.VocabularyConceptAttributeValueSet;
import java.util.List;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConceptAttributeValuesDaoImplTest {
    
    @Autowired
    private ConceptAttributeValuesDao conceptAttributeValuesDao;
    
    @Test
    public void testGetConceptAttributeValues() {
        Vocabulary v = new Vocabulary();
        v.setId(100L);
        List<VocabularyConceptAttributeValueSet> attributeValues = this.conceptAttributeValuesDao.getConceptAttributeValues(v);
        assertThat(attributeValues.size(), is(614));
        
        for (VocabularyConceptAttributeValueSet valueSet : attributeValues) {
            assertThat(valueSet.getConcept(), is(notNullValue()));
            assertThat(valueSet.getAttribute(), is(notNullValue()));
            
            assertThat(valueSet.getAttribute().getId(), is(6L));
            assertThat(valueSet.getValues().size(), is(1));
            
            for (VocabularyConceptAttributeValue value : valueSet.getValues()) {
                assertThat(value.getValueSet(), is(notNullValue()));
                assertThat(value.getValueSet(), is(sameInstance(valueSet)));
                
                assertThat(value.getId(), is(notNullValue()));
                assertThat(value.getLanguage(), is(notNullValue()));
                assertThat(value.getValue(), is(notNullValue()));
                assertThat(value.getRelatedConcept(), is(nullValue()));
            }
        }
    }
    
    @Test
    public void testGetConceptLocalLinks() {
        Vocabulary v = new Vocabulary();
        v.setId(100L);
        List<VocabularyConceptAttributeValueSet> attributeValues = this.conceptAttributeValuesDao.getConceptLocalLinks(v);
        assertThat(attributeValues.size(), is(0));
    }
    
    @Test
    public void testGetConceptInternalLinks() {
        Vocabulary v = new Vocabulary();
        v.setId(100L);
        List<VocabularyConceptAttributeValueSet> attributeValues = this.conceptAttributeValuesDao.getConceptInternalLinks(v);
        assertThat(attributeValues.size(), is(614));
        
        for (VocabularyConceptAttributeValueSet valueSet : attributeValues) {
            assertThat(valueSet.getConcept(), is(notNullValue()));
            assertThat(valueSet.getAttribute(), is(notNullValue()));
            
            assertThat(valueSet.getAttribute().getId(), is(19L));
            assertThat(valueSet.getValues().size(), is(1));
            
            for (VocabularyConceptAttributeValue value : valueSet.getValues()) {
                assertThat(value.getValueSet(), is(notNullValue()));
                assertThat(value.getValueSet(), is(sameInstance(valueSet)));
                
                assertThat(value.getId(), is(notNullValue()));
                assertThat(value.getRelatedConcept(), is(notNullValue()));
                assertThat(value.getValue(), is(nullValue()));
            }
        }
    }
    
    @Test
    public void testGetConceptExternalLinks() {
        Vocabulary v = new Vocabulary();
        v.setId(100L);
        List<VocabularyConceptAttributeValueSet> attributeValues = this.conceptAttributeValuesDao.getConceptExternalLinks(v);
        assertThat(attributeValues.size(), is(0));
    }
    
}
