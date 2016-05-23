
insert into VocabularyConceptAttributeValues (
    fVocabularyConceptId,
    fVocabularyConceptAttributeId,
    fConceptAttributeValueId
)
select
    vc.Id as fVocabularyConceptId,
    vca.Id as fVocabularyConceptAttributeId,
    src_vcav.fConceptAttributeValueId
from
    Vocabulary v
inner join
    VocabularyConcepts vc
on
    v.Id = vc.fVocabularyId
inner join
    VocabularyConcepts src_vc
on
    v.WorkItemId = src_vc.fVocabularyId and vc.fConceptId = src_vc.fConceptId
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
    vca.fVocabularyId = v.Id and src_vca.fConceptAttributeId = vca.fConceptAttributeId
where
    v.WorkItemId is not null
order by
    vc.Id, vca.Id, src_vcav.fConceptAttributeValueId
