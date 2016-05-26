
create table Vocabulary (
	Id bigint auto_increment,
	RegulationStatus tinyint not null,
	Identifier varchar(100) not null,
	Label varchar(255) not null,
	BaseUri varchar(255) not null,
	-- more data columns here
	WorkItemId bigint,
	primary key(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table Concept (
	Id bigint auto_increment,
	`Status` int not null,
	Identifier varchar(100) not null,
	Label varchar(1000) not null,
	Notation varchar(100),
	Definition text,
	-- more data columns here
	WorkItemId bigint,
	primary key(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table VocabularyConcepts (
	fVocabularyId bigint,
	fConceptId bigint,
	primary key(fVocabularyId, fConceptId),
	foreign key(fVocabularyId) references Vocabulary(Id),
	foreign key(fConceptId) references Concept(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table ConceptAttribute (
	Id bigint auto_increment,
	DataType tinyint not null,
	Identifier varchar(100) not null,
	-- more data columns here
	WorkItemId bigint,
	primary key(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table VocabularyConceptAttributes (
	fVocabularyId bigint,
	fConceptAttributeId bigint,
	primary key(fVocabularyId, fConceptAttributeId),
	foreign key(fVocabularyId) references Vocabulary(Id),
	foreign key(fConceptAttributeId) references ConceptAttribute(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table ConceptAttributeValue (
	Id bigint auto_increment,
	`Language` varchar(10),
	`Value` text,
	-- more data columns here
	WorkItemId bigint,
	primary key(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table ConceptExternalLink (
	Id bigint auto_increment,
	`Value` varchar(500) not null,
	WorkItemId bigint,
	primary key(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table VocabularyConceptAttributeValues (
	fVocabularyId bigint,
	fConceptId bigint,
	fConceptAttributeId bigint,
	fConceptAttributeValueId bigint,
	primary key(fVocabularyId, fConceptId, fConceptAttributeId, fConceptAttributeValueId),
	foreign key(fVocabularyId, fConceptId) references VocabularyConcepts(fVocabularyId, fConceptId),
	foreign key(fVocabularyId, fConceptAttributeId) references VocabularyConceptAttributes(fVocabularyId, fConceptAttributeId),
	foreign key(fConceptAttributeValueId) references ConceptAttributeValue(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table VocabularyConceptExternalLinks (
	fVocabularyId bigint,
	fConceptId bigint,
	fConceptAttributeId bigint,
	fConceptExternalLinkId bigint,
	primary key(fVocabularyId, fConceptId, fConceptAttributeId, fConceptExternalLinkId),
	foreign key(fVocabularyId, fConceptId) references VocabularyConcepts(fVocabularyId, fConceptId),
	foreign key(fVocabularyId, fConceptAttributeId) references VocabularyConceptAttributes(fVocabularyId, fConceptAttributeId),
	foreign key(fConceptExternalLinkId) references ConceptExternalLink(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table VocabularyConceptLinks (
	fVocabularyId bigint,
	fConceptId bigint,
	fConceptAttributeId bigint,
	fRelatedVocabularyId bigint,
	fRelatedConceptId bigint,
	primary key(fVocabularyId, fConceptId, fConceptAttributeId, fRelatedVocabularyId, fRelatedConceptId),
	foreign key(fVocabularyId, fConceptId) references VocabularyConcepts(fVocabularyId, fConceptId),
	foreign key(fVocabularyId, fConceptAttributeId) references VocabularyConceptAttributes(fVocabularyId, fConceptAttributeId),
	foreign key(fRelatedVocabularyId, fRelatedConceptId) references VocabularyConcepts(fVocabularyId, fConceptId)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table VocabularyVersion (
	Id bigint auto_increment,
	fEntityId bigint not null,
	CommitDate bigint not null,
	fRootVersionId bigInt,
	fParentVersionId bigint,
	UserName varchar(100) not null,
	primary key(Id),
	foreign key(fEntityId) references Vocabulary(Id),
	foreign key(fParentVersionId) references VocabularyVersion(Id),
	foreign key(fParentVersionId) references VocabularyVersion(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table ConceptAttributeVersion (
	Id bigint auto_increment,
	fEntityId bigint not null,
	CommitDate bigint not null,
	fRootVersionId bigInt,
	fParentVersionId bigint,
	UserName varchar(100) not null,
	primary key(Id),
	foreign key(fEntityId) references ConceptAttribute(Id),
	foreign key(fParentVersionId) references ConceptAttributeVersion(Id),
	foreign key(fParentVersionId) references ConceptAttributeVersion(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table Revision (
	Id bigint auto_increment,
	CreationDate bigint not null,
	UserName varchar(100) not null,
	fParentRevisionId bigint,
	primary key(Id),
	foreign key(fParentRevisionId) references Revision(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table RevisionVocabularyVersions (
	fRevisionId bigint,
	fVocabularyVersionId bigint,
	primary key(fRevisionId, fVocabularyVersionId),
	foreign key(fRevisionId) references Revision(Id),
	foreign key(fVocabularyVersionId) references VocabularyVersion(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table RevisionConceptAttributeVersions (
	fRevisionId bigint,
	fConceptAttributeVersionId bigint,
	primary key(fRevisionId, fConceptAttributeVersionId),
	foreign key(fRevisionId) references Revision(Id),
	foreign key(fConceptAttributeVersionId) references ConceptAttributeVersion(Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
