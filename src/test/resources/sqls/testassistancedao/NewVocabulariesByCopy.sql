
insert into Vocabulary (
    Identifier, 
    Label, 
    BaseUri, 
    RegulationStatus,
    WorkItemId
)
select 
    v.Identifier, 
    v.Label, 
    v.BaseUri, 
    v.RegulationStatus,
    v.Id as WorkItemId
from
    Revision r
inner join (
    select 
        max(CreationDate) as CreationDate
    from
        Revision
) lr
on
    r.CreationDate = lr.CreationDate
inner join
    RevisionVocabularyVersions rvv
on
    r.Id = rvv.fRevisionId
inner join
    VocabularyVersion vv
on
    rvv.fVocabularyVersionId = vv.Id
inner join
    Vocabulary v
on
    vv.fEntityId = v.Id
order by
    v.Id
