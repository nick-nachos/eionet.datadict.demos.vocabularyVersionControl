
insert into Revision (
    CreationDate,
    UserName,
    fParentRevisionId
)
select
    :creationDate as CreationDate,
    :userName as UserName,
    r.Id as fParentRevisionId
from
    Revision r
inner join (
    select 
        max(CreationDate) as CreationDate
    from Revision
) maxr
on
    r.CreationDate = maxr.CreationDate
