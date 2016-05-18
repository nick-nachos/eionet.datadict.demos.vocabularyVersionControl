
select
    relvc.fVocabularyId,
    relc.*
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
) cav
inner join
    VocabularyConcepts relvc
on
    cav.fRelatedVocabularyConceptId = relvc.Id
inner join
    Concept relc
on
    relvc.fVocabularyId <> :vocabularyId and relvc.fConceptId = relc.Id
order by
    relvc.fVocabularyId, relc.Id
