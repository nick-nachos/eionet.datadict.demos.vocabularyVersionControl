
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
    vc.fVocabularyId = :vocabularyId and ca.DataType = :refType and relvc.fVocabularyId <> :vocabularyId
order by
    vc.fConceptId, vca.fConceptAttributeId
