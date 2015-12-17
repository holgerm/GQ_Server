# --- !Ups

create table device (
  id                        bigint not null,
  name                      text,
  deviceid                      text,
  code                      text,
 questpush					bigint,

  constraint pk_device primary key (id))
;




create sequence device_seq;



create table users_device (
 users_id              bigint not null,
  device_id                 bigint not null,
  constraint pk_users_device primary key (users_id, device_id))
;

create sequence users_device_seq;


# --- !Downs


DROP TABLE users_device;
DROP TABLE device;
