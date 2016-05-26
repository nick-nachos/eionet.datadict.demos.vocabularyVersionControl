
select
    vcav.fConceptId,
    vcav.fConceptAttributeId,
    cav.Id,
    cav.Language,
    cav.Value
from
    VocabularyConceptAttributeValues vcav
inner join
    ConceptAttributeValue cav
on
    vcav.fConceptAttributeValueId = cav.Id
where
    vcav.fVocabularyId = :vocabularyId
order by
    vcav.fVocabularyId, vcav.fConceptId, vcav.fConceptAttributeId
