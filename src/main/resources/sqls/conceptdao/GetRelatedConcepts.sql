
select
    relvc.fVocabularyId,
    relc.*
from (
    select distinct
        vcl.fRelatedVocabularyId,
        vcl.fRelatedConceptId
    from
        VocabularyConceptLinks vcl
    where
        vcl.fVocabularyId = :vocabularyId and vcl.fRelatedVocabularyId <> :vocabularyId
) cal
inner join
    VocabularyConcepts relvc
on
    relvc.fVocabularyId = cal.fRelatedVocabularyId and relvc.fConceptId = cal.fRelatedConceptId
inner join
    Concept relc
on
    relvc.fConceptId = relc.Id
order by
    relvc.fVocabularyId, relc.Id
