# --- !Ups
ALTER TABLE `newsstreamitems` ADD COLUMN   `repost`        bigint  not null;




# --- !Downs
ALTER TABLE `newsstreamitems` DROP `repost`;

