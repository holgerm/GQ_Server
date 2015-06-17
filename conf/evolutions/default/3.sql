# --- !Ups

 ALTER TABLE action_type ADD COLUMN symbol varchar(255);


# --- !Downs



ALTER TABLE action_type DROP COLUMN symbol;

