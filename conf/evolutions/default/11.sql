# --- !Ups
ALTER TABLE part ADD COLUMN gqid bigint;


# --- !Downs

ALTER TABLE part DROP COLUMN gqid;
