CREATE SCHEMA filmdb;

CREATE TABLE filmdb.genre ( 
	id                   integer  NOT NULL,
	genre                varchar(255)  NOT NULL,
	CONSTRAINT pk_genre PRIMARY KEY ( id )
 );

CREATE TABLE filmdb.persontype ( 
	id                   integer  NOT NULL,
	persontype           varchar(100)  NOT NULL,
	CONSTRAINT pk_persontype PRIMARY KEY ( id )
 );

CREATE TABLE filmdb.film ( 
	id                   integer  NOT NULL,
	titel                varchar(255)  NOT NULL,
	erscheinungsjahr     date  NOT NULL,
	genre                integer  NOT NULL,
	CONSTRAINT pk_film PRIMARY KEY ( id )
 );

CREATE INDEX idx_film ON filmdb.film ( genre );

CREATE TABLE filmdb.person ( 
	id                   integer  NOT NULL,
	nachname             varchar(100)  NOT NULL,
	vorname              varchar(100)  NOT NULL,
	persontype           integer  NOT NULL,
	kuenstlername        varchar(100)  ,
	CONSTRAINT pk_person PRIMARY KEY ( id )
 );

CREATE INDEX idx_person ON filmdb.person ( persontype );

CREATE TABLE filmdb.bewertung ( 
	id                   integer  NOT NULL,
	film                 integer  NOT NULL,
	bewertung            integer  NOT NULL,
	CONSTRAINT pk_bewertung PRIMARY KEY ( id ),
	CONSTRAINT pk_bewertung_0 UNIQUE ( film ) 
 );

CREATE TABLE filmdb.filmhatperson ( 
	film                 integer  NOT NULL,
	person               integer  NOT NULL,
	CONSTRAINT idx_filmhatperson PRIMARY KEY ( film, person )
 );

CREATE INDEX idx_filmhatperson_0 ON filmdb.filmhatperson ( film );

CREATE INDEX idx_filmhatperson_1 ON filmdb.filmhatperson ( person );

ALTER TABLE filmdb.bewertung ADD CONSTRAINT fk_bewertung FOREIGN KEY ( film ) REFERENCES filmdb.film( id );

ALTER TABLE filmdb.film ADD CONSTRAINT fk_film_genre FOREIGN KEY ( genre ) REFERENCES filmdb.genre( id );

ALTER TABLE filmdb.filmhatperson ADD CONSTRAINT fk_filmhatperson FOREIGN KEY ( film ) REFERENCES filmdb.film( id );

ALTER TABLE filmdb.filmhatperson ADD CONSTRAINT fk_filmhatperson_0 FOREIGN KEY ( person ) REFERENCES filmdb.person( id );

ALTER TABLE filmdb.person ADD CONSTRAINT fk_person FOREIGN KEY ( persontype ) REFERENCES filmdb.persontype( id );

