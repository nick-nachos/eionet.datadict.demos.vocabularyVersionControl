
insert into VocabularyVersion (
    fEntityId,
    fRootVersionId,
    fParentVersionId,
    UserName,
    CommitDate
)
select
    :vocabularyId as fEntityId,
    vv.fRootVersionId,
    vv.Id as fParentVersionId,
    :userName as UserName,
    :commitDate as CommitDate
from
    VocabularyVersion vv
where
    vv.fEntityId = :sourceVocabularyId
