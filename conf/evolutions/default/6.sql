# --- !Ups


create table menu_item (
  id                        bigint not null,
  title                     varchar(255),
  show                      varchar(255),
  icon                      varchar(255),
  activity                  boolean,
  priority                  integer,
  show_text                 boolean,
  on_select_id              bigint,
  constraint pk_menu_item primary key (id))
;
create sequence menu_item_seq;
alter table menu_item add constraint fk_menu_item_1 foreign key (on_select_id) references rule (id);
create index ix_menu_item_rule_1 on menu_item (on_select_id);







create table game_type_action_type (
  game_type_id                   bigint not null,
  action_type_id                 bigint not null,
  constraint pk_game_type_action_type primary key (game_type_id, action_type_id))
;

create sequence game_type_action_type_seq;
alter table game_type_action_type add constraint fk_game_type_action_type_1 foreign key (action_type_id) references action_type (id);
create index ix_game_type_action_type_1 on game_type_action_type (action_type_id);





ALTER TABLE game_type ADD COLUMN possible_menu_item_action_types bigint;

create index ix_game_type_possible_menu_item_action_types_1 on game_type (possible_menu_item_action_types);








create table games_menu_item (
  games_id                   		bigint not null,
  menu_item_id                     bigint not null,
  constraint pk_game_menu_item primary key (games_id, menu_item_id))
;


create sequence games_menu_item_seq;
alter table games_menu_item add constraint fk_games_menu_item_1 foreign key (menu_item_id) references menu_item (id);
create index ix_games_menu_item_1 on games_menu_item (menu_item_id);



ALTER TABLE games ADD COLUMN menu_items bigint;
create index ix_game_menu_items_1 on games (menu_items);



# --- !Downs

ALTER TABLE games DROP CONSTRAINT IF EXISTS fk_game_menu_items_1;
ALTER TABLE games DROP INDEX ix_game_menu_items_1;
ALTER TABLE games DROP COLUMN menu_items;


ALTER TABLE game_menu_item DROP CONSTRAINT IF EXISTS fk_game_menu_item_1;
ALTER TABLE game_menu_item DROP INDEX ix_game_menu_item_1;




ALTER TABLE game_type DROP CONSTRAINT IF EXISTS fk_game_type_possible_menu_item_action_types_1;
ALTER TABLE game_type DROP INDEX ix_game_type_possible_menu_item_action_types_1;
ALTER TABLE game_type DROP COLUMN possible_menu_item_action_types;



ALTER TABLE menu_item DROP CONSTRAINT IF EXISTS fk_menu_item_1;
ALTER TABLE menu_item DROP INDEX ix_menu_item_rule_1;



drop table if exists game_menu_item cascade;

drop table if exists game_type_action_type cascade;

drop table if exists menu_item cascade;


