
insert into VocabularyConceptExternalLinks (
    fVocabularyId,
    fConceptId,
    fConceptAttributeId,
    fConceptExternalLinkId
)
select
    v.Id as fVocabularyId,
    vcel.fConceptId,
    vcel.fConceptAttributeId,
    vcel.fConceptExternalLinkId
from
    Vocabulary v
inner join
    VocabularyConceptExternalLinks vcel
on
    v.WorkItemId = vcel.fVocabularyId
where
    v.WorkItemId is not null
order by
    v.Id, vcel.fConceptId, vcel.fConceptAttributeId, vcel.fConceptExternalLinkId
