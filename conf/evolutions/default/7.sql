# --- !Ups



ALTER TABLE mission ADD COLUMN x integer;
ALTER TABLE mission ADD COLUMN y integer;




# --- !Downs

ALTER TABLE mission DROP COLUMN x;
ALTER TABLE mission DROP COLUMN y;
