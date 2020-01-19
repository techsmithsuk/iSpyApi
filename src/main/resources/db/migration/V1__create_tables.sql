CREATE TABLE if not exists suspects
(
    id                        serial primary key,
    poster_url                varchar(200),
    weight                    varchar(300),
    reward_text               varchar(2000),
    hair                      varchar(300),
    dates_of_birth_used       varchar(1000),
    nationality               varchar(200),
    aliases                   varchar(2000),
    race                      varchar(150),
    fbi_publication_timestamp timestamp,
    name                      varchar(300),
    eyes                      varchar(300),
    details                   varchar(15000),
    sex                       varchar(10),
    fbi_suspect_url           varchar(300),
    last_modified_timestamp   timestamp,
    height_min                varchar(30),
    warning_message           varchar(3000),
    fbi_json_url              varchar(300),
    image_url                 varchar(300),
    fbi_json_id               varchar(300)
);

CREATE TABLE IF NOT EXISTS reports
(
    id                  serial primary key,
    date_of_sighting    date,
    description         character varying(2000)     NOT NULL,
    location            character varying(200),
    suspect_id          integer                     NOT NULL,
    timestamp_submitted timestamp without time zone NOT NULL
);

CREATE TABLE if not exists suspect_photo_urls
(
    id        serial primary key,
    suspectId INTEGER,
    original  varchar(1000),
    thumb     varchar(1000),
    large     VARCHAR(1000),
    caption   varchar(1000)
);


