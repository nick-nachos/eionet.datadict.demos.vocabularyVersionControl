
update
    Revision r
inner join (
    select
        rev1.Id,
        max(rev2.CreationDate) as CreationDate
    from (
        select 
            r.*
        from
            Vocabulary v
        inner join
            VocabularyVersion vv
        on
            vv.fEntityId = v.Id
        inner join 
            Revision r
        on 
            r.CreationDate = vv.CommitDate
        where
            v.WorkItemId is not null
    ) rev1
    inner join 
        Revision rev2
    on 
        rev1.CreationDate > rev2.CreationDate
    group by
        rev1.Id
) r2
on r.Id = r2.Id
inner join
    Revision r3
on
    r2.CreationDate = r3.CreationDate
set
    r.fParentRevisionId = r3.Id
