# --- !Ups
create table newsstreamitems (
  id                              bigint auto_increment not null,
  title                           longtext,
  text                            longtext,
  visibility                      varchar(255),
  datum                            datetime,

  constraint pk_newsstreamitems primary key (id))
;

create table providerportal_newsstreamitems (
  providerportal_id              bigint not null,
  newsstreamitems_id                 bigint not null,
  constraint pk_providerportal_newsstreamitems primary key (providerportal_id, newsstreamitems_id))
;

create table users_newsstreamitems (
  users_id              bigint not null,
  newsstreamitems_id                 bigint not null,
  constraint pk_users_newsstreamitems primary key (users_id, newsstreamitems_id))
;

create table games_newsstreamitems (
  games_id                           bigint not null,
  newsstreamitems_id                 bigint not null,
  constraint pk_games_newsstreamitems primary key (games_id, newsstreamitems_id))
;




# --- !Downs
DROP TABLE if exists `newsstreamitems`;
DROP TABLE if exists `providerportal_newsstreamitems`;
DROP TABLE if exists `users_newsstreamitems`;
DROP TABLE if exists `games_newsstreamitems`;
