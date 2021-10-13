# --- !Ups

ALTER TABLE providerportal ADD COLUMN quest_name_sg text;
ALTER TABLE providerportal ADD COLUMN quest_name_pl text;
ALTER TABLE providerportal ADD COLUMN quest_name_genus text;
ALTER TABLE providerportal ADD COLUMN formal_communication boolean;


# --- !Downs

ALTER TABLE providerportal DROP COLUMN quest_name_sg;
ALTER TABLE providerportal DROP COLUMN quest_name_pl;
ALTER TABLE providerportal DROP COLUMN quest_name_genus;
ALTER TABLE providerportal DROP COLUMN formal_communication;
