# --- !Ups
ALTER TABLE mission ADD COLUMN gqid bigint;


# --- !Downs

ALTER TABLE mission DROP COLUMN gqid;
