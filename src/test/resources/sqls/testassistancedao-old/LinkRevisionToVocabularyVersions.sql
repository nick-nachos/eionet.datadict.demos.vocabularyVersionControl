
insert into RevisionVocabularyVersions (
    fRevisionId,
    fVocabularyVersionId
)
select
    :revisionId as fRevisionId,
    vv.Id as fVocabularyVersionId
from
    Revision r
inner join
    RevisionVocabularyVersions rvv
on
    r.fParentRevisionId = rvv.fRevisionId
inner join
    VocabularyVersion vv
on
    rvv.fVocabularyVersionId = vv.Id
where
    r.Id = :revisionId and vv.fEntityId <> :sourceVocabularyId
union all
select
    :revisionId as fRevisionId,
    vv.Id as fVocabularyVersionId
from
    VocabularyVersion vv
where
    vv.fEntityId = :vocabularyId
