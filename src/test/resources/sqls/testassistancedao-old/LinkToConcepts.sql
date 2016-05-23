
insert into VocabularyConcepts (
    fVocabularyId,
    fConceptId
)
select
    :vocabularyId,
    vc.fConceptId
from
    VocabularyConcepts vc
where
    vc.fVocabularyId = :sourceVocabularyId
