CREATE SCHEMA moviedb;

CREATE TABLE moviedb.genre ( 
	id                   serial  NOT NULL,
	genre                varchar(255)  NOT NULL,
	CONSTRAINT pk_genre PRIMARY KEY ( id )
 );

CREATE TABLE moviedb.movie ( 
	id                   integer  NOT NULL,
	title                varchar(255)  NOT NULL,
	releaseyear          date  NOT NULL,
	genre                integer  NOT NULL,
    votes                integer NOT NULL,
    runtime              integer NOT NULL,
	CONSTRAINT pk_film PRIMARY KEY ( id )
 );

CREATE INDEX idx_film ON moviedb.movie ( genre );

CREATE TABLE moviedb.persontype ( 
	id                   serial NOT NULL,
	persontype           varchar(100)  NOT NULL,
	CONSTRAINT pk_persontype PRIMARY KEY ( id )
 );

CREATE TABLE moviedb.rating ( 
	id                   integer  NOT NULL,
	movie                integer  NOT NULL,
	rating               integer  NOT NULL,
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

CREATE TABLE moviedb.mivehasdirector ( 
	director             integer  NOT NULL,
	movie                integer  NOT NULL,
	CONSTRAINT idx_mivehasdirector PRIMARY KEY ( director, movie )
 );

CREATE INDEX idx_mivehasdirector_0 ON moviedb.mivehasdirector ( movie );

CREATE INDEX idx_mivehasdirector_1 ON moviedb.mivehasdirector ( director );

CREATE TABLE moviedb.moviehasperson ( 
	movie                integer  NOT NULL,
	person               integer  NOT NULL,
	CONSTRAINT idx_filmhatperson PRIMARY KEY ( movie, person )
 );

CREATE INDEX idx_filmhatperson_0 ON moviedb.moviehasperson ( movie );

CREATE INDEX idx_filmhatperson_1 ON moviedb.moviehasperson ( person );

ALTER TABLE moviedb.mivehasdirector ADD CONSTRAINT fk_mivehasdirector FOREIGN KEY ( movie ) REFERENCES moviedb.movie( id );

ALTER TABLE moviedb.mivehasdirector ADD CONSTRAINT fk_mivehasdirector_0 FOREIGN KEY ( director ) REFERENCES moviedb.person( id );

ALTER TABLE moviedb.movie ADD CONSTRAINT fk_film_genre FOREIGN KEY ( genre ) REFERENCES moviedb.genre( id );

ALTER TABLE moviedb.moviehasperson ADD CONSTRAINT fk_filmhatperson FOREIGN KEY ( movie ) REFERENCES moviedb.movie( id );

ALTER TABLE moviedb.moviehasperson ADD CONSTRAINT fk_filmhatperson_0 FOREIGN KEY ( person ) REFERENCES moviedb.person( id );

ALTER TABLE moviedb.person ADD CONSTRAINT fk_person FOREIGN KEY ( persontype ) REFERENCES moviedb.persontype( id );

ALTER TABLE moviedb.rating ADD CONSTRAINT fk_bewertung FOREIGN KEY ( movie ) REFERENCES moviedb.movie( id );

