# --- !Ups
ALTER TABLE `newsstreamitems` ADD COLUMN   `posterid`        bigint  not null;
ALTER TABLE `newsstreamitems` ADD COLUMN   `posterclass`       varchar(255) not null;


# --- !Downs
ALTER TABLE `newsstreamitems` DROP  `posterid`;
ALTER TABLE `newsstreamitems` DROP `posterclass`;

