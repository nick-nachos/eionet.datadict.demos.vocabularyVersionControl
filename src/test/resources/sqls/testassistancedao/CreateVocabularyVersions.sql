
insert into VocabularyVersion (
    fEntityId,
    fRootVersionId,
    fParentVersionId,
    UserName,
    CommitDate
)
select
    v.Id as fEntityId,
    src_vv.fRootVersionId,
    src_vv.Id as fParentVersionId,
    'testuser' as UserName,
    (unix_timestamp() * 1000) + v.Id as CommitDate
from
    Vocabulary v
inner join
    Vocabulary src_v
on
    v.WorkItemId = src_v.Id
inner join
    VocabularyVersion src_vv
on
    src_vv.fEntityId = src_v.Id
where
    v.WorkItemId is not null
order by
    v.Id
