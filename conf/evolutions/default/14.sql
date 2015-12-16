# --- !Ups

ALTER TABLE providerportal ADD COLUMN imprint text;
ALTER TABLE providerportal ADD COLUMN agbs text;
ALTER TABLE providerportal ADD COLUMN agbsversion bigint;
ALTER TABLE providerportal ADD COLUMN privacyagreement text;
ALTER TABLE providerportal ADD COLUMN privacyagreementversion bigint;


# --- !Downs


ALTER TABLE action_type DROP COLUMN imprint;
ALTER TABLE action_type DROP COLUMN agbs;
ALTER TABLE action_type DROP COLUMN privacyagreement;
ALTER TABLE action_type DROP COLUMN agbsversion;
ALTER TABLE action_type DROP COLUMN privacyagreementversion;
