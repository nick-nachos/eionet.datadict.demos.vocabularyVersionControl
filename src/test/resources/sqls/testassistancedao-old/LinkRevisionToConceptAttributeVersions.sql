
insert into RevisionConceptAttributeVersions (
    fRevisionId,
    fConceptAttributeVersionId
)
select
    :revisionId as fRevisionId,
    rcav.fConceptAttributeVersionId
from
    Revision r
inner join
    RevisionConceptAttributeVersions rcav
on
    r.fParentRevisionId = rcav.fRevisionId
where
    r.Id = :revisionId
