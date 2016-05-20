select
    vc.fConceptId,
    vca.fConceptAttributeId,
    cav.Id,
    cav.Language,
    cav.fRelatedVocabularyConceptId,
    relvc.fVocabularyId as fRelatedVocabularyId,
    relvc.fConceptId as fRelatedConceptId
from
    VocabularyConcepts vc
inner join
    VocabularyConceptAttributeValues vcav
on
    vc.Id = vcav.fVocabularyConceptId
inner join
    VocabularyConceptAttributes vca
on
    vca.Id = vcav.fVocabularyConceptAttributeId
/*
inner join
    ConceptAttribute ca
on
    vca.fConceptAttributeId = ca.Id
*/
inner join
    ConceptAttributeValue cav
on
    vcav.fConceptAttributeValueId = cav.Id
inner join
    VocabularyConcepts relvc
on
    cav.fRelatedVocabularyConceptId = relvc.Id
where
    vc.fVocabularyId = :vocabularyId /* and ca.DataType in (:localRefType, :refType) */ and relvc.fVocabularyId = :vocabularyId
order by
    vc.fConceptId, vca.fConceptAttributeId
