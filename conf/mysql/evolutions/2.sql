# --- !Ups
ALTER TABLE `providerportal` ADD COLUMN   `template_server_url`     longtext  not null;

# --- !Downs
ALTER TABLE `providerportal` DROP `template_server_url`;