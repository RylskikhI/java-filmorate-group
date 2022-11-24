CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    name VARCHAR NOT NULL ,
    login VARCHAR NOT NULL ,
    email VARCHAR NOT NULL ,
    birthday DATE ,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS friends (
    friend_one BIGINT NOT NULL ,
    friend_two BIGINT NOT NULL ,
    confirmed BOOLEAN NOT NULL ,
    CONSTRAINT friend_one_fk FOREIGN KEY (friend_one) REFERENCES users (id) ON DELETE CASCADE ,
    CONSTRAINT friend_two_fk FOREIGN KEY (friend_two) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS mpa (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    title VARCHAR NOT NULL ,
    CONSTRAINT mpa_id_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS film (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    name VARCHAR NOT NULL ,
    description VARCHAR NOT NULL ,
    releaseDate DATE NOT NULL ,
    duration INTEGER NOT NULL ,
    rate INTEGER NOT NULL ,
    mpa BIGINT NOT NULL,
    CONSTRAINT film_pk PRIMARY KEY (id),
    CONSTRAINT mpa_id_fk FOREIGN KEY (mpa) REFERENCES mpa (id)
);

CREATE TABLE IF NOT EXISTS genre (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    title VARCHAR NOT NULL ,
    CONSTRAINT genre_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS film_genre (
    film BIGINT NOT NULL ,
    genre BIGINT NOT NULL ,
    CONSTRAINT film_fk FOREIGN KEY (film) REFERENCES film (id) ON DELETE CASCADE ,
    CONSTRAINT genre_fk FOREIGN KEY (genre) REFERENCES genre (id)
);

CREATE TABLE IF NOT EXISTS likes (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    user_id BIGINT NOT NULL ,
    film_id BIGINT NOT NULL ,
    CONSTRAINT like_pk PRIMARY KEY (id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ,
    CONSTRAINT film_id_fk FOREIGN KEY (film_id) REFERENCES film (id) ON DELETE CASCADE
 );
CREATE TABLE IF NOT EXISTS reviews (
     id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
     content VARCHAR NOT NULL,
     is_positive BOOLEAN NOT NULL,
     user_id BIGINT NOT NULL,
     film_id BIGINT NOT NULL,
     useful BIGINT NOT NULL,
    CONSTRAINT reviews_pk PRIMARY KEY (id),
    CONSTRAINT user_id_reviews_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT film_id_reviews_fk FOREIGN KEY (film_id) REFERENCES film (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS activity (
    event_id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    event_type VARCHAR,
    operation  VARCHAR,
    entity_id  BIGINT,
    timestamp  BIGINT
);


CREATE TABLE IF NOT EXISTS directors (
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name  VARCHAR(64) NOT NULL
    );

create table IF NOT EXISTS FILM_DIRECTOR
(
    FILM_ID     BIGINT not null,
    DIRECTOR_ID BIGINT not null,
    constraint DIRECTOR_FK
        foreign key (DIRECTOR_ID) references DIRECTORS (id)  on delete cascade,
    constraint FILMS_FK
        foreign key (FILM_ID) references FILM (id)
            on delete cascade
);