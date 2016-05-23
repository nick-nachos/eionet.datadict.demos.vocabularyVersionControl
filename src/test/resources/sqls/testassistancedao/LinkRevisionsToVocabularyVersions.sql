
insert into RevisionVocabularyVersions (
    fRevisionId,
    fVocabularyVersionId
)
select 
    res.fRevisionId,
    res.fVocabularyVersionId
from ((
    select
        r.Id as fRevisionId,
        src_vv.Id as fVocabularyVersionId
    from (
        select 
            vv.*,
            v.WorkItemId
        from
            Vocabulary v
        inner join
            VocabularyVersion vv
        on
            vv.fEntityId = v.Id
        where
            v.WorkItemId is not null
    ) vv1
    inner join (
        select 
            vv.*,
            v.WorkItemId
        from
            Vocabulary v
        inner join
            VocabularyVersion vv
        on
            vv.fEntityId = v.Id
        where
            v.WorkItemId is not null
    ) vv2
    on
        vv1.CommitDate > vv2.CommitDate
    inner join
        Revision r
    on
        vv2.CommitDate = r.CreationDate
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
        RevisionVocabularyVersions src_rvv
    on
        baserev.fParentRevisionId = src_rvv.fRevisionId
    inner join
        VocabularyVersion src_vv
    on
        src_vv.Id = src_rvv.fVocabularyVersionId and src_vv.fEntityId = vv1.WorkItemId
    order by 
        fRevisionId, fVocabularyVersionId
    )
    union all
    (
    select
        r.Id as fRevisionId,
        vv1.Id as fVocabularyVersionId
    from (
        select 
            vv.*,
            v.WorkItemId
        from
            Vocabulary v
        inner join
            VocabularyVersion vv
        on
            vv.fEntityId = v.Id
        where
            v.WorkItemId is not null
    ) vv1
    inner join (
        select 
            vv.*,
            v.WorkItemId
        from
            Vocabulary v
        inner join
            VocabularyVersion vv
        on
            vv.fEntityId = v.Id
        where
            v.WorkItemId is not null
    ) vv2
    on
        vv1.CommitDate <= vv2.CommitDate
    inner join
        Revision r
    on
        vv2.CommitDate = r.CreationDate
    order by 
        fRevisionId, fVocabularyVersionId
)) res
order by 
    res.fRevisionId, res.fVocabularyVersionId
