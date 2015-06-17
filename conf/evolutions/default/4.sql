# --- !Ups

 ALTER TABLE rule_type ADD COLUMN symbol varchar(255);


# --- !Downs



ALTER TABLE rule_type DROP COLUMN symbol;

