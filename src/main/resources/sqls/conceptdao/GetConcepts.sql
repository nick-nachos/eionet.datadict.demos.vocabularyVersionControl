
select
    :vocabularyId as fVocabularyId,
    c.*
from
    VocabularyConcepts vc
inner join
    Concept c
on
    vc.fConceptId = c.Id
where
    vc.fVocabularyId = :vocabularyId
order by
    c.Id
