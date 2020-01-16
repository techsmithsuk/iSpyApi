CREATE TABLE IF NOT EXISTS reports
(
    date_of_sighting date,
    description character varying(2000) NOT NULL,
    id serial NOT NULL,
    location character varying(200),
    suspect_id integer NOT NULL,
    timestamp_submitted timestamp without time zone NOT NULL,
    PRIMARY KEY (id)
);
