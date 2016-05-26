
select
    vcl.fConceptId,
    vcl.fConceptAttributeId,
    vcl.fRelatedVocabularyId,
    vcl.fRelatedConceptId
from
    VocabularyConceptLinks vcl
where
    vcl.fVocabularyId = :vocabularyId and vcl.fRelatedVocabularyId <> :vocabularyId
order by
    vcl.fVocabularyId, 
    vcl.fConceptId, 
    vcl.fConceptAttributeId, 
    vcl.fRelatedVocabularyId, 
    vcl.fRelatedConceptId
