
insert into VocabularyConcepts (
    fVocabularyId,
    fConceptId
)
select
    v.Id as fVocabularyId,
    src_vc.fConceptId
from
    Vocabulary v
inner join
    VocabularyConcepts src_vc
on
    v.WorkItemId = src_vc.fVocabularyId
where
    v.WorkItemId is not null
order by
    v.Id, src_vc.fConceptId
