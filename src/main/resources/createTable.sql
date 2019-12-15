CREATE TABLE github.person
(
  first_name character(60),
  last_name character(60),
  id bigint NOT NULL,
  CONSTRAINT "personPrimaryKey" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE github.person
  OWNER TO postgres;
