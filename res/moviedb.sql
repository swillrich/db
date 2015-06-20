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
    rating               integer,
    votes                integer,
    runtime              integer,
	CONSTRAINT pk_film PRIMARY KEY ( id )
 );
 
 CREATE TABLE moviedb.moviehasgenre (
    movie                varchar(100) NOT NULL,
    genre                integer NOT NULL,
    CONSTRAINT pk_moviehasgenre PRIMARY KEY (movie, genre),
    CONSTRAINT fk_moviehasgenre_movie FOREIGN KEY (movie) REFERENCES moviedb.movie(id),
    CONSTRAINT fk_moviehasgenre_genre FOREIGN KEY (genre) REFERENCES moviedb.genre(id)
 );

CREATE TABLE moviedb.person ( 
	id                   serial  NOT NULL,
	lastname             varchar(100)  NOT NULL,
	firstname            varchar(100),
	CONSTRAINT pk_person PRIMARY KEY ( id )
 );

CREATE TABLE moviedb.moviehasdirector ( 
	director             integer  NOT NULL,
	movie                varchar(100)  NOT NULL,
	CONSTRAINT idx_moviehasdirector PRIMARY KEY ( director, movie ),
	CONSTRAINT fk_moviehasdirector_movie FOREIGN KEY ( movie ) REFERENCES moviedb.movie( id ),
	CONSTRAINT fk_moviehasdirector_director FOREIGN KEY ( director ) REFERENCES moviedb.person( id )
 );

CREATE TABLE moviedb.moviehasactor ( 
	movie                varchar(100)  NOT NULL,
	actor               integer  NOT NULL,
	CONSTRAINT idx_filmhatperson PRIMARY KEY ( movie, actor ),
	CONSTRAINT fk_moviehasperson_movie FOREIGN KEY (movie) REFERENCES moviedb.movie(id),
	CONSTRAINT fk_moviehasperson_person FOREIGN KEY (actor) REFERENCES moviedb.person(id)
 );