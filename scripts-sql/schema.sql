CREATE TABLE directors (
   id INTEGER NOT NULL AUTO_INCREMENT,
   age INTEGER,
   country VARCHAR(255),
   gender_type VARCHAR(255),
   name VARCHAR(255),
   surname VARCHAR(255),
   PRIMARY KEY (id)
);
CREATE TABLE actors (
    id INTEGER NOT NULL AUTO_INCREMENT,
    age INTEGER,
    country VARCHAR(255),
    gender_type VARCHAR(255),
    name VARCHAR(255),
    surname VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE movies (
    id INTEGER NOT NULL AUTO_INCREMENT,
    duration INTEGER,
    film_genre VARCHAR(255),
    language VARCHAR(255),
    title VARCHAR(255),
    year INTEGER,
    director_id INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (director_id) REFERENCES directors (id)
);
CREATE TABLE rel_movies_actors (
   movie_id INTEGER NOT NULL,
   actor_id INTEGER NOT NULL,
   FOREIGN KEY (actor_id) REFERENCES actors (id),
   FOREIGN KEY (movie_id) REFERENCES movies (id)
);
