
insert into Vocabulary (
    Identifier, 
    Label, 
    BaseUri, 
    RegulationStatus,
    WorkItemId
)
select
    v.Identifier, 
    v.Label, 
    v.BaseUri, 
    v.RegulationStatus,
    :sourceVocabularyId
from
    Vocabulary v
where
    Id = :sourceVocabularyId
;
