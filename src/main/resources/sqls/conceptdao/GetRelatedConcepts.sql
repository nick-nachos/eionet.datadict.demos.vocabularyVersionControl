
select
    vc.fVocabularyId,
    c.*
from (
    select distinct 
        cav.fRelatedVocabularyConceptId
    from
        VocabularyConcepts vc
    inner join
        VocabularyConceptAttributes vca
    on
        vc.fVocabularyId = vca.fVocabularyId
    inner join
        ConceptAttribute ca
    on
        vca.fConceptAttributeId = ca.Id
    inner join
        VocabularyConceptAttributeValues vcav
    on 
        vc.Id = vcav.fVocabularyConceptId and vca.Id = vcav.fVocabularyConceptAttributeId
    inner join
        ConceptAttributeValue cav
    on
        vcav.fConceptAttributeValueId = cav.Id
    where
        vc.fVocabularyId = :vocabularyId and ca.DataType = :refType
) relvc
inner join
    VocabularyConcepts vc
on 
    relvc.fRelatedVocabularyConceptId = vc.Id
inner join
    Concept c
on
    vc.fConceptId = c.Id
order by
    vc.fVocabularyId, c.Id
