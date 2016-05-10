
select
    c.*
from
    Vocabulary v
inner join
    VocabularyConcepts vc
on 
    v.Id = vc.fVocabularyId
inner join
    Concept c
on
    vc.fConceptId = c.Id
where
    v.Id = :vocabularyId
