CREATE SCHEMA moviedb;

CREATE TABLE moviedb.genre ( 
	id                   integer  NOT NULL,
	genre                varchar(255)  NOT NULL,
	CONSTRAINT pk_genre PRIMARY KEY ( id )
 );

CREATE TABLE moviedb.movie ( 
	id                   integer  NOT NULL,
	title                varchar(255)  NOT NULL,
	releaseyear          date  NOT NULL,
	genre                integer  NOT NULL,
	CONSTRAINT pk_film PRIMARY KEY ( id )
 );

CREATE INDEX idx_film ON moviedb.movie ( genre );

CREATE TABLE moviedb.persontype ( 
	id                   integer  NOT NULL,
	persontype           varchar(100)  NOT NULL,
	CONSTRAINT pk_persontype PRIMARY KEY ( id )
 );

CREATE TABLE moviedb.rate ( 
	id                   integer  NOT NULL,
	movie                integer  NOT NULL,
	rate                 integer  NOT NULL,
	CONSTRAINT pk_bewertung PRIMARY KEY ( id ),
	CONSTRAINT pk_bewertung_0 UNIQUE ( movie ) 
 );

CREATE TABLE moviedb.person ( 
	id                   integer  NOT NULL,
	lastname             varchar(100)  NOT NULL,
	firstname            varchar(100)  NOT NULL,
	persontype           integer  NOT NULL,
	"alias"              varchar(100)  ,
	CONSTRAINT pk_person PRIMARY KEY ( id )
 );

CREATE INDEX idx_person ON moviedb.person ( persontype );

CREATE TABLE moviedb.moviehasperson ( 
	movie                integer  NOT NULL,
	person               integer  NOT NULL,
	CONSTRAINT idx_filmhatperson PRIMARY KEY ( movie, person )
 );

CREATE INDEX idx_filmhatperson_0 ON moviedb.moviehasperson ( movie );

CREATE INDEX idx_filmhatperson_1 ON moviedb.moviehasperson ( person );

ALTER TABLE moviedb.movie ADD CONSTRAINT fk_film_genre FOREIGN KEY ( genre ) REFERENCES moviedb.genre( id );

ALTER TABLE moviedb.moviehasperson ADD CONSTRAINT fk_filmhatperson FOREIGN KEY ( movie ) REFERENCES moviedb.movie( id );

ALTER TABLE moviedb.moviehasperson ADD CONSTRAINT fk_filmhatperson_0 FOREIGN KEY ( person ) REFERENCES moviedb.person( id );

ALTER TABLE moviedb.person ADD CONSTRAINT fk_person FOREIGN KEY ( persontype ) REFERENCES moviedb.persontype( id );

ALTER TABLE moviedb.rate ADD CONSTRAINT fk_bewertung FOREIGN KEY ( movie ) REFERENCES moviedb.movie( id );
