
select
    vcel.fConceptId,
    vcel.fConceptAttributeId,
    cel.Id,
    cel.Value
from
    VocabularyConceptExternalLinks vcel
inner join
    ConceptExternalLink cel
on
    vcel.fConceptExternalLinkId = cel.Id
where
    vcel.fVocabularyId = :vocabularyId
order by
    vcel.fVocabularyId, vcel.fConceptId, vcel.fConceptAttributeId
