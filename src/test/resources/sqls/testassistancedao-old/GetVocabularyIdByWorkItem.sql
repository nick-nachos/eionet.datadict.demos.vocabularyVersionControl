
select
    v.Id
from
    Vocabulary v
where
    v.WorkItemId = :sourceVocabularyId
