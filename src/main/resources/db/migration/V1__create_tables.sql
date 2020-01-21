CREATE TABLE if not exists suspects
(
    id              serial primary key,
    title           varchar(300),
    date_of_birth   varchar(1000),
    hair            varchar(300),
    eyes            varchar(300),
    height          varchar(30),
    weight          varchar(300),
    sex             varchar(10),
    race            varchar(150),
    nationality     varchar(200),
    scars_and_marks varchar(5000),
    reward_text     varchar(2000),
    caution         varchar(15000),
    details         varchar(15000),
    warning_message varchar(3000),
    fbi_uid         varchar(300) unique,
    modified        timestamp,
    publication     timestamp
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