# --- !Ups
ALTER TABLE `providerportal` ADD COLUMN   `additional_css`       longtext not null;
ALTER TABLE `providerportal` ADD COLUMN   `defaultcolor`       varchar(255) not null;



# --- !Downs
ALTER TABLE `providerportal` DROP `additional_css`;
ALTER TABLE `providerportal` DROP `defaultcolor`;

