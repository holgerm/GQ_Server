# --- !Ups

ALTER TABLE action_type ADD COLUMN category varchar(255);


# --- !Downs


ALTER TABLE action_type DROP COLUMN category;
