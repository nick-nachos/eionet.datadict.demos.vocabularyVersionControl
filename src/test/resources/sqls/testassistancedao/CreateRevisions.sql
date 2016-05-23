
insert into Revision (
    CreationDate,
    UserName
)
select
    vv.CommitDate as CreationDate,
    vv.UserName
from
    Vocabulary v
inner join
    VocabularyVersion vv
on
    vv.fEntityId = v.Id
where
    v.WorkItemId is not null
order by
    v.Id
