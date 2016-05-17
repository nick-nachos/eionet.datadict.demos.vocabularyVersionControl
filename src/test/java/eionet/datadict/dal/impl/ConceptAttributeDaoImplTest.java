package eionet.datadict.dal.impl;

import eionet.datadict.dal.ConceptAttributeDao;
import eionet.datadict.model.ConceptAttribute;
import eionet.datadict.model.DataType;
import eionet.datadict.model.Vocabulary;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConceptAttributeDaoImplTest {

    @Autowired
    private ConceptAttributeDao conceptAttributeDao;
    
    @Test
    public void testGetConceptAttributes() {
        Vocabulary v = new Vocabulary();
        v.setId(100L);
        List<ConceptAttribute> attributes = conceptAttributeDao.getConceptAttributes(v);
        assertThat(attributes.size(), is(2));
        
        ConceptAttribute prefLabel = null;
        ConceptAttribute inCountry = null;
        
        for (ConceptAttribute attribute : attributes) {
            if (StringUtils.equals(attribute.getIdentifier(), "skos:prefLabel")) {
                prefLabel = attribute;
            }
            
            if (StringUtils.equals(attribute.getIdentifier(), "inCountry")) {
                inCountry = attribute;
            }
        }
        
        assertThat(prefLabel, is(notNullValue()));
        assertThat(prefLabel.getId(), is(6L));
        assertThat(prefLabel.getDataType(), is(DataType.STRING));
        
        assertThat(inCountry, is(notNullValue()));
        assertThat(inCountry.getId(), is(19L));
        assertThat(inCountry.getDataType(), is(DataType.REFERENCE));
    }
    
}
