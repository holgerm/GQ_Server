# --- !Ups

 ALTER TABLE action ADD COLUMN subactions_id bigint;

create table action_set (
  id                        bigint not null,
  constraint pk_action_set primary key (id))
;

create table action_set_action (
  action_set_id                    bigint not null,
  action_id                        bigint not null,
  constraint pk_action_set_action primary key (action_set_id, action_id))
;


create sequence action_set_seq;


alter table action add constraint fk_action_subactions_41 foreign key (subactions_id) references action_set (id);
create index ix_action_subactions_41 on action (subactions_id);




alter table action_set_action add constraint fk_action_set_action_action_set_01 foreign key (action_set_id) references action_set (id);

alter table action_set_action add constraint fk_action_set_action_action_02 foreign key (action_id) references action (id);


# --- !Downs


ALTER TABLE action DROP CONSTRAINT IF EXISTS fk_action_subactions_41;

ALTER TABLE action DROP INDEX ix_action_subactions_41;


drop table if exists action_set cascade;

drop table if exists action_set_action cascade;

drop sequence if exists action_set_seq;


ALTER TABLE action DROP COLUMN subactions_id;

