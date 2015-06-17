# --- !Ups
ALTER TABLE `providerportal` ADD COLUMN   `template_post_url`     longtext  not null;

# --- !Downs
ALTER TABLE `providerportal` DROP  `template_post_url`;