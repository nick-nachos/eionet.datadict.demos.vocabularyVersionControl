
-- copy Vocabularies

insert into VersionControlTest4.Vocabulary (
	Identifier, 
	Label, 
	BaseUri, 
	RegulationStatus,
	WorkItemId
)
select
	concat(vs.IDENTIFIER, '.', v.IDENTIFIER) as IDENTIFIER,
	v.LABEL,
	v.BASE_URI,
	case 
		when v.REG_STATUS = 'Draft' then 0
		when v.REG_STATUS = 'Public Draft' then 1
		when v.REG_STATUS = 'Released' then 2
	end as REG_STATUS,
	v.VOCABULARY_ID
from
	DataDict3.VOCABULARY_SET vs
inner join
	DataDict3.VOCABULARY v
on
	vs.ID = v.FOLDER_ID
where
	v.WORKING_COPY = 0
;

-- copy concepts

insert into VersionControlTest4.Concept (
	Identifier,
	Notation,
	Label,
	Definition,
	`Status`,
	WorkItemId
)
select
	vc.IDENTIFIER,
	vc.NOTATION,
	vc.LABEL,
	vc.DEFINITION,
	vc.STATUS,
	vc.VOCABULARY_CONCEPT_ID
from
	DataDict3.VOCABULARY_CONCEPT vc
inner join
	DataDict3.VOCABULARY v
on v.VOCABULARY_ID = vc.VOCABULARY_ID
where
	v.WORKING_COPY = 0
;

-- link vocabularies to concepts

insert into VersionControlTest4.VocabularyConcepts (
	fVocabularyId,
	fConceptId
)
select
	v.Id as fVocabularyId,
	c.Id as fConceptId
from
	VersionControlTest4.Vocabulary v
inner join
	DataDict3.VOCABULARY ddv
on
	v.WorkItemId = ddv.VOCABULARY_ID
inner join
	DataDict3.VOCABULARY_CONCEPT ddvc
on
	ddv.VOCABULARY_ID = ddvc.VOCABULARY_ID
inner join
	VersionControlTest4.Concept c
on
	ddvc.VOCABULARY_CONCEPT_ID = c.WorkItemId
;

-- copy concept attributes

insert into VersionControlTest4.ConceptAttribute (
	Identifier,
	DataType,
	WorkItemId
)
select
	d.IDENTIFIER,
	case
		when a.VALUE = 'string' then 0
		when a.VALUE = 'boolean' then 1
		when a.VALUE = 'integer' then 2
		when a.VALUE = 'float' then 3
		when a.VALUE = 'double' then 4
		when a.VALUE = 'decimal' then 5
		when a.VALUE = 'date' then 6
		when a.VALUE = 'localref' then 7
		when a.VALUE = 'reference' then 8
		
	end as DataType,
	d.DATAELEM_ID
from (
	select distinct
		DATAELEM_ID
	from
		DataDict3.VOCABULARY2ELEM
) v2e
inner join
	DataDict3.DATAELEM d
on
	v2e.DATAELEM_ID = d.DATAELEM_ID
inner join
	DataDict3.ATTRIBUTE a
on
	a.DATAELEM_ID = d.DATAELEM_ID and a.PARENT_TYPE = 'E'
inner join
	DataDict3.M_ATTRIBUTE ma
on
	a.M_ATTRIBUTE_ID = ma.M_ATTRIBUTE_ID and ma.NAME = 'datatype'
;

-- link vocabularies to concept attributes

insert into VersionControlTest4.VocabularyConceptAttributes (
	fVocabularyId,
	fConceptAttributeId
)
select
	v.Id as fVocabularyId,
	ca.Id as fConceptAttributeId
from
	DataDict3.VOCABULARY2ELEM v2e
inner join
	VersionControlTest4.Vocabulary v
on
	v2e.VOCABULARY_ID = v.WorkItemId
inner join
	VersionControlTest4.ConceptAttribute ca
on
	v2e.DATAELEM_ID = ca.WorkItemId
;

-- copy concept attribute values

insert into VersionControlTest4.ConceptAttributeValue (
	`Language`,
	`Value`,
	WorkItemId
)
select
	ddvce.`LANGUAGE`,
	ddvce.ELEMENT_VALUE,
	ddvce.ID as WorkItemId
from 
	DataDict3.VOCABULARY_CONCEPT_ELEMENT ddvce
inner join
	DataDict3.VOCABULARY_CONCEPT ddvc
on
	ddvce.VOCABULARY_CONCEPT_ID = ddvc.VOCABULARY_CONCEPT_ID
inner join
	DataDict3.VOCABULARY ddv
on
	ddvc.VOCABULARY_ID = ddv.VOCABULARY_ID
inner join
	VersionControlTest4.ConceptAttribute ca
on
	ca.WorkItemId = ddvce.DATAELEM_ID
where
	ddv.WORKING_COPY = 0 and ca.DataType not in (7, 8)
;

insert into VersionControlTest4.ConceptExternalLink (
	`Value`,
	WorkItemId
)
select
	ddvce.ELEMENT_VALUE,
	ddvce.ID as WorkItemId
from 
	DataDict3.VOCABULARY_CONCEPT_ELEMENT ddvce
inner join
	DataDict3.VOCABULARY_CONCEPT ddvc
on
	ddvce.VOCABULARY_CONCEPT_ID = ddvc.VOCABULARY_CONCEPT_ID
inner join
	DataDict3.VOCABULARY ddv
on
	ddvc.VOCABULARY_ID = ddv.VOCABULARY_ID
inner join
	VersionControlTest4.ConceptAttribute ca
on
	ca.WorkItemId = ddvce.DATAELEM_ID
where
	ddv.WORKING_COPY = 0 and ddvce.RELATED_CONCEPT_ID is null and ca.DataType = 8
;

-- link concepts / concept atributes / attribute values

insert into VersionControlTest4.VocabularyConceptAttributeValues (
	fVocabularyId,
	fConceptId,
	fConceptAttributeId,
	fConceptAttributeValueId
)
select
	vc.fVocabularyId,
	vc.fConceptId,
	vca.fConceptAttributeId,
	cav.Id as fConceptAttributeValueId
from
	VersionControlTest4.ConceptAttributeValue cav
inner join	
	DataDict3.VOCABULARY_CONCEPT_ELEMENT ddvce
on
	cav.WorkItemId = ddvce.ID
inner join
	VersionControlTest4.Concept c
on
	c.WorkItemId = ddvce.VOCABULARY_CONCEPT_ID
inner join
	VersionControlTest4.VocabularyConcepts vc
on
	c.Id = vc.fConceptId
inner join
	VersionControlTest4.VocabularyConceptAttributes vca
on
	vc.fVocabularyId = vca.fVocabularyId
inner join
	VersionControlTest4.ConceptAttribute ca
on
	vca.fConceptAttributeId = ca.Id and ca.WorkItemId = ddvce.DATAELEM_ID
;

insert into VersionControlTest4.VocabularyConceptExternalLinks (
	fVocabularyId,
	fConceptId,
	fConceptAttributeId,
	fConceptExternalLinkId
)
select
	vc.fVocabularyId,
	vc.fConceptId,
	vca.fConceptAttributeId,
	cel.Id as fConceptExternalLinkId
from
	VersionControlTest4.ConceptExternalLink cel
inner join	
	DataDict3.VOCABULARY_CONCEPT_ELEMENT ddvce
on
	cel.WorkItemId = ddvce.ID
inner join
	VersionControlTest4.Concept c
on
	c.WorkItemId = ddvce.VOCABULARY_CONCEPT_ID
inner join
	VersionControlTest4.VocabularyConcepts vc
on
	c.Id = vc.fConceptId
inner join
	VersionControlTest4.VocabularyConceptAttributes vca
on
	vc.fVocabularyId = vca.fVocabularyId
inner join
	VersionControlTest4.ConceptAttribute ca
on
	vca.fConceptAttributeId = ca.Id and ca.WorkItemId = ddvce.DATAELEM_ID
;

insert into VersionControlTest4.VocabularyConceptLinks (
	fVocabularyId,
	fConceptId,
	fConceptAttributeId,
	fRelatedVocabularyId,
	fRelatedConceptId
)
select
	v.Id as fVocabularyId,
	c.Id as fConceptId,
	ca.Id as fConceptAttributeId,
	relvc.fVocabularyId as fRelatedVocabularyId,
	relvc.fConceptId as fRelatedConceptId
from 
	DataDict3.VOCABULARY_CONCEPT_ELEMENT ddvce
inner join
	DataDict3.VOCABULARY_CONCEPT ddvc
on
	ddvce.VOCABULARY_CONCEPT_ID = ddvc.VOCABULARY_CONCEPT_ID
inner join
	DataDict3.VOCABULARY ddv
on
	ddvc.VOCABULARY_ID = ddv.VOCABULARY_ID
inner join
	VersionControlTest4.ConceptAttribute ca
on
	ca.WorkItemId = ddvce.DATAELEM_ID
inner join
	VersionControlTest4.Vocabulary v
on
	ddv.VOCABULARY_ID = v.WorkItemId
inner join
	VersionControlTest4.Concept c
on
	ddvc.VOCABULARY_CONCEPT_ID = c.WorkItemId
inner join
	Concept relc
on
	ddvce.RELATED_CONCEPT_ID = relc.WorkItemId
inner join
	VocabularyConcepts relvc
on
	relc.Id = relvc.fConceptId
where
	ddv.WORKING_COPY = 0 and ddvce.RELATED_CONCEPT_ID is not null and ca.DataType in (7, 8)
;

-- create vocabulary versions

insert into VersionControlTest4.VocabularyVersion (
	fEntityId,
	UserName,
	CommitDate
)
select
	v.Id,
	'testuser',
	unix_timestamp('2016-05-12 09:30') * 1000
from
	VersionControlTest4.Vocabulary v
;

update 
	VersionControlTest4.VocabularyVersion
set 
	fRootVersionId = Id
;

-- create concept attribute versions
insert into VersionControlTest4.ConceptAttributeVersion (
	fEntityId,
	UserName,
	CommitDate
)
select
	ca.Id,
	'testuser',
	unix_timestamp('2016-05-12 09:30') * 1000
from
	VersionControlTest4.ConceptAttribute ca
;

update 
	VersionControlTest4.ConceptAttributeVersion
set 
	fRootVersionId = Id
;

-- create root revision

insert into VersionControlTest4.Revision (
	CreationDate,
	UserName
)
values (
	unix_timestamp('2016-05-12 09:30') * 1000,
	'testuser'
)
;

-- link root revision to entity versions

insert into VersionControlTest4.RevisionVocabularyVersions (
	fRevisionId,
	fVocabularyVersionId
)
select
	r.Id as fRevisionId,
	vver.Id as fVocabularyVersionId
from
	VersionControlTest4.VocabularyVersion vver
inner join (
	select 
		max(Id) as Id
	from
		VersionControlTest4.Revision
) r
;

insert into VersionControlTest4.RevisionConceptAttributeVersions (
	fRevisionId,
	fConceptAttributeVersionId
)
select
	r.Id as fRevisionId,
	caver.Id as fConceptAttributeVersionId
from
	VersionControlTest4.ConceptAttributeVersion caver
inner join (
	select 
		max(Id) as Id
	from
		VersionControlTest4.Revision
) r
;

update
	VersionControlTest4.Vocabulary
set
	WorkItemId = null
;

update
	VersionControlTest4.Concept
set
	WorkItemId = null
;

update
	VersionControlTest4.ConceptAttribute
set
	WorkItemId = null
;

update
	VersionControlTest4.ConceptAttributeValue
set
	WorkItemId = null
;

update
	VersionControlTest4.ConceptExternalLink
set
	WorkItemId = null
;
