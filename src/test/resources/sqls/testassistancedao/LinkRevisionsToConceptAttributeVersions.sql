
insert into RevisionConceptAttributeVersions (
    fRevisionId,
    fConceptAttributeVersionId
)
select
    r.Id as fRevisionId,
    rcav.fConceptAttributeVersionId
from
    Vocabulary v
inner join
    VocabularyVersion vv
on
    v.Id = vv.fEntityId
inner join
    Revision r
on
    r.CreationDate = vv.CommitDate
join (
    select 
        min(vv.CommitDate) as CommitDate
    from 
        Vocabulary v
    inner join
        VocabularyVersion vv
    on
        v.Id = vv.fEntityId
    where 
        v.WorkItemId is not null
) rvvd
inner join
    Revision baserev
on
    baserev.CreationDate = rvvd.CommitDate
inner join
    RevisionConceptAttributeVersions rcav
on
    rcav.fRevisionId = baserev.fParentRevisionId
where 
    v.WorkItemId is not null
order by
    r.CreationDate, rcav.fConceptAttributeVersionId
