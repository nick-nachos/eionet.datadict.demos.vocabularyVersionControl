
select
    c.*
from
    VocabularyConcepts vc
inner join
    Concept c
on
    vc.fConceptId = c.Id
where
    vc.fVocabularyId = :vocabularyId
