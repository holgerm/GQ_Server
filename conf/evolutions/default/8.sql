# --- !Ups


ALTER TABLE mission DROP COLUMN x;
ALTER TABLE mission DROP COLUMN y;
ALTER TABLE mission ADD COLUMN top integer;
ALTER TABLE mission ADD COLUMN posleft integer;




# --- !Downs

ALTER TABLE mission DROP COLUMN top;
ALTER TABLE mission DROP COLUMN posleft;
ALTER TABLE mission ADD COLUMN x integer;
ALTER TABLE mission ADD COLUMN y integer;