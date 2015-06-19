CREATE SCHEMA moviedb;

CREATE TABLE moviedb.genre ( 
	id                   serial  NOT NULL,
	genre                varchar(255)  NOT NULL,
	CONSTRAINT pk_genre PRIMARY KEY ( id )
 );

CREATE TABLE moviedb.movie ( 
	id                   varchar(100)  NOT NULL,
	title                varchar(255)  NOT NULL,
	year                 integer  NOT NULL,
    rating               integer NOT NULL,
    votes                integer NOT NULL,
    runtime              integer NOT NULL,
	CONSTRAINT pk_film PRIMARY KEY ( id )
 );
 
 CREATE TABLE moviedb.moviehasgenre (
    movie                varchar(100) NOT NULL,
    genre                integer NOT NULL,
    CONSTRAINT pk_moviehasgenre PRIMARY KEY (movie, genre),
    CONSTRAINT fk_moviehasgenre_movie FOREIGN KEY (movie) REFERENCES moviedb.movie(id),
    CONSTRAINT fk_moviehasgenre_genre FOREIGN KEY (genre) REFERENCES moviedb.genre(id)
 );

CREATE TABLE moviedb.persontype ( 
	id                   serial NOT NULL,
	persontype           varchar(100)  NOT NULL,
	CONSTRAINT pk_persontype PRIMARY KEY ( id )
 );

CREATE TABLE moviedb.person ( 
	id                   serial  NOT NULL,
	lastname             varchar(100)  NOT NULL,
	firstname            varchar(100),
	persontype           integer  NOT NULL,
	CONSTRAINT pk_person PRIMARY KEY ( id ),
	CONSTRAINT fk_person FOREIGN KEY ( persontype ) REFERENCES moviedb.persontype( id )
 );

CREATE TABLE moviedb.moviehasdirector ( 
	director             integer  NOT NULL,
	movie                varchar(100)  NOT NULL,
	CONSTRAINT idx_moviehasdirector PRIMARY KEY ( director, movie ),
	CONSTRAINT fk_moviehasdirector_movie FOREIGN KEY ( movie ) REFERENCES moviedb.movie( id ),
	CONSTRAINT fk_moviehasdirector_director FOREIGN KEY ( director ) REFERENCES moviedb.person( id )
 );

CREATE TABLE moviedb.moviehasperson ( 
	movie                varchar(100)  NOT NULL,
	person               integer  NOT NULL,
	CONSTRAINT idx_filmhatperson PRIMARY KEY ( movie, person ),
	CONSTRAINT fk_moviehasperson_movie FOREIGN KEY (movie) REFERENCES moviedb.movie(id),
	CONSTRAINT fk_moviehasperson_person FOREIGN KEY (person) REFERENCES moviedb.person(id)
 );