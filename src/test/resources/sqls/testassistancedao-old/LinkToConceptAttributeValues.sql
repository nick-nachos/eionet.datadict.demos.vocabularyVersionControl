
insert into VocabularyConceptAttributeValues (
    fVocabularyConceptAttributeId,
    fVocabularyConceptId,
    fConceptAttributeValueId
)
select
    vca.Id as fVocabularyConceptAttributeId,
    vc.Id as fVocabularyConceptId,
    src_vcav.fConceptAttributeValueId
from
    VocabularyConcepts src_vc

inner join
    VocabularyConceptAttributeValues src_vcav
on
    src_vc.Id = src_vcav.fVocabularyConceptId
inner join
    VocabularyConceptAttributes src_vca
on
    src_vcav.fVocabularyConceptAttributeId = src_vca.Id
inner join
    VocabularyConceptAttributes vca
on
    vca.fVocabularyId = :vocabularyId and src_vca.fConceptAttributeId = vca.fConceptAttributeId
inner join
    VocabularyConcepts vc
on
    vc.fVocabularyId = :vocabularyId and src_vc.fConceptId = vc.fConceptId
where
    src_vc.fVocabularyId = :sourceVocabularyId
