
insert into VocabularyConceptAttributeValues (
    fVocabularyId,
    fConceptId,
    fConceptAttributeId,
    fConceptAttributeValueId
)
select
    v.Id as fVocabularyId,
    vcav.fConceptId,
    vcav.fConceptAttributeId,
    vcav.fConceptAttributeValueId
from
    Vocabulary v
inner join
    VocabularyConceptAttributeValues vcav
on
    v.WorkItemId = vcav.fVocabularyId
where
    v.WorkItemId is not null
order by
    v.Id, vcav.fConceptId, vcav.fConceptAttributeId, vcav.fConceptAttributeValueId
