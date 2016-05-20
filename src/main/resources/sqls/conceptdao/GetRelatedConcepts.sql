
select
    relvc.fVocabularyId,
    relc.*
from (
    select distinct
        cav.fRelatedVocabularyConceptId
    from
        VocabularyConcepts vc
    inner join
        VocabularyConceptAttributeValues vcav
    on
        vc.Id = vcav.fVocabularyConceptId
    inner join
        VocabularyConceptAttributes vca
    on
        vca.Id = vcav.fVocabularyConceptAttributeId
    inner join
        ConceptAttribute ca
    on
        vca.fConceptAttributeId = ca.Id
    inner join
        ConceptAttributeValue cav
    on
        vcav.fConceptAttributeValueId = cav.Id
    where
        vc.fVocabularyId = :vocabularyId and ca.DataType = :refType
) cav
inner join
    VocabularyConcepts relvc
on
    relvc.fVocabularyId <> :vocabularyId and cav.fRelatedVocabularyConceptId = relvc.Id
inner join
    Concept relc
on
    relvc.fConceptId = relc.Id
order by
    relvc.fVocabularyId, relc.Id
