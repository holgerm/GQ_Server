# --- !Ups

ALTER TABLE providerportal ADD COLUMN portalimprint text;
ALTER TABLE providerportal ADD COLUMN portalagbs text;
ALTER TABLE providerportal ADD COLUMN portalagbsversion bigint;
ALTER TABLE providerportal ADD COLUMN portalprivacyagreement text;
ALTER TABLE providerportal ADD COLUMN portalprivacyagreementversion bigint;
ALTER TABLE games ADD COLUMN userlastupdated bigint;


# --- !Downs

ALTER TABLE providerportal DROP COLUMN portalimprint;
ALTER TABLE providerportal DROP COLUMN portalagbs;
ALTER TABLE providerportal DROP COLUMN portalprivacyagreement;
ALTER TABLE providerportal DROP COLUMN portalagbsversion;
ALTER TABLE providerportal DROP COLUMN portalprivacyagreementversion;
ALTER TABLE games DROP COLUMN userlastupdated;
