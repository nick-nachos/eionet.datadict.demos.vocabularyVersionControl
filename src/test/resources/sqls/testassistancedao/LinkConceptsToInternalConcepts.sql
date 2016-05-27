
insert into VocabularyConceptLinks (
    fVocabularyId,
    fConceptId,
    fConceptAttributeId,
    fRelatedVocabularyId,
    fRelatedConceptId
)
select
    v.Id as fVocabularyId,
    vcl.fConceptId,
    vcl.fConceptAttributeId,
    vcl.fRelatedVocabularyId,
    vcl.fRelatedConceptId
from
    Vocabulary v
inner join
    VocabularyConceptLinks vcl
on
    v.WorkItemId = vcl.fVocabularyId
where
    v.WorkItemId is not null
order by
    v.Id, vcl.fConceptId, vcl.fConceptAttributeId, vcl.fRelatedVocabularyId, vcl.fRelatedConceptId
