
select
    vc.fConceptId,
    vca.fConceptAttributeId,
    relvc.fVocabularyId as fRelatedVocabularyId,
    relvc.fConceptId as fRelatedConceptId,
    cav.Id,
    cav.Language,
    cav.fRelatedVocabularyConceptId
from
    VocabularyConcepts vc
inner join
    VocabularyConceptAttributes vca
on
    vc.fVocabularyId = vca.fVocabularyId
inner join
    ConceptAttribute ca
on
    vca.fConceptAttributeId = ca.Id
inner join
    VocabularyConceptAttributeValues vcav
on
    vc.Id = vcav.fVocabularyConceptId and vca.Id = vcav.fVocabularyConceptAttributeId
inner join
    ConceptAttributeValue cav
on
    vcav.fConceptAttributeValueId = cav.Id
inner join
    VocabularyConcepts relvc
on
    cav.fRelatedVocabularyConceptId = relvc.Id
where
    vc.fVocabularyId = :vocabularyId and ca.DataType = :dataType
order by
    vc.fConceptId, vca.fConceptAttributeId
