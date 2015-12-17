# --- !Ups

ALTER TABLE device ADD COLUMN quest text;


# --- !Downs


ALTER TABLE device DROP COLUMN quest;
