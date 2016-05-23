
insert into VocabularyConceptAttributes (
    fVocabularyId,
    fConceptAttributeId
)
select
    :vocabularyId,
    vca.fConceptAttributeId
from
    VocabularyConceptAttributes vca
where
    vca.fVocabularyId = :sourceVocabularyId
