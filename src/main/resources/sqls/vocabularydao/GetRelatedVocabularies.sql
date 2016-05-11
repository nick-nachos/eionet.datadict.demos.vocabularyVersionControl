
select
    v.*
from (
    select distinct 
        relvc.fVocabularyId
    from
        VocabularyConcepts vc
    inner join
        VocabularyConceptAttributeValues vcav
    on 
        vc.Id = vcav.fVocabularyConceptId
    inner join
        VocabularyConceptAttributes vca
    on
        vcav.fVocabularyConceptAttributeId = vca.Id
    inner join
        ConceptAttribute ca
    on
        vca.fConceptAttributeId = ca.Id
    inner join
        ConceptAttributeValue cav
    on
        vcav.fConceptAttributeValueId = cav.Id
    inner join
        VocabularyConcepts relvc
    on
        cav.fRelatedVocabularyConceptId = relvc.Id
    where
        vc.fVocabularyId = :vocabularyId and ca.DataType = :refType
) relvc
inner join
    Vocabulary v
on
    relvc.fVocabularyId = v.Id
order by
    v.Id
