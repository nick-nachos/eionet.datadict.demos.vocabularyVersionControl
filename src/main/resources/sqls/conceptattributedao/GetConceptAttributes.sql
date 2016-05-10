
select
    ca.*
from
    VocabularyConceptAttributes vca
inner join
    ConceptAttribute ca
on 
    vca.fConceptAttributeId = ca.Id
where
    vca.fVocabularyId = :vocabularyId
