
insert into VocabularyConceptAttributes (
    fVocabularyId,
    fConceptAttributeId
)
select
    v.Id as fVocabularyId,
    src_vca.fConceptAttributeId
from
    Vocabulary v
inner join
    VocabularyConceptAttributes src_vca
on
    v.WorkItemId = src_vca.fVocabularyId
where
    v.WorkItemId is not null
order by
    v.Id, src_vca.fConceptAttributeId
