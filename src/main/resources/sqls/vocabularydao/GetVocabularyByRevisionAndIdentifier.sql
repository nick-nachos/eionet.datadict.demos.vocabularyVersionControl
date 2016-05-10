
select
    v.*
from
    Revision r
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
where
    r.Id = :revisionId and v.Identifier = :vocabularyIdentifier
