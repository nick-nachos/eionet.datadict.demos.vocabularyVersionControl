
select
    vc.fConceptId,
    vca.fConceptAttributeId,
    cav.Id,
    cav.Language,
    cav.Value
from
    VocabularyConcepts vc
inner join
    VocabularyConceptAttributeValues vcav
on 
    vc.Id = vcav.fVocabularyConceptId
inner join
    VocabularyConceptAttributes vca
on
    vcav.fVocabularyConceptAttributeId = vca.Id
inner join
    ConceptAttribute ca
on
    vca.fConceptAttributeId = ca.Id
inner join
    ConceptAttributeValue cav
on
    vcav.fConceptAttributeValueId = cav.Id
where
    vc.fVocabularyId = :vocabularyId and ca.DataType = :refType and cav.fRelatedVocabularyConceptId is null
order by
    vc.fConceptId, vca.fConceptAttributeId