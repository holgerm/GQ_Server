# --- !Ups


create table premium_access (
  id                        bigint not null,
  rights                    varchar(255),
    bill                  varchar(255), 
  validuntil				timestamp,
  constraint pk_premium_access primary key (id))
;

create sequence premium_access_seq;

create table users_premium_access (
  users_id                     bigint not null,
  premium_access_id                        bigint not null,
  constraint pk_users_premium_access primary key (users_id, premium_access_id))
;



ALTER TABLE attribute_type ADD COLUMN premiumcodes varchar(255);
ALTER TABLE mission_type ADD COLUMN premiumcodes varchar(255);
ALTER TABLE action_type ADD COLUMN premiumcodes varchar(255);
ALTER TABLE content_type ADD COLUMN premiumcodes varchar(255);
ALTER TABLE game_type ADD COLUMN premiumcodes varchar(255);
ALTER TABLE hotspot_type ADD COLUMN premiumcodes varchar(255);
ALTER TABLE scene_type ADD COLUMN premiumcodes varchar(255);

ALTER TABLE game_type ADD COLUMN show_only_hotspots_in_sidemenu  boolean;
UPDATE game_type SET show_only_hotspots_in_sidemenu = 'FALSE';


# --- !Downs


ALTER TABLE action_type DROP COLUMN premiumcodes;
ALTER TABLE content_type DROP COLUMN premiumcodes;
ALTER TABLE game_type DROP COLUMN premiumcodes;
ALTER TABLE hotspot_type DROP COLUMN premiumcodes;
ALTER TABLE scene_type DROP COLUMN premiumcodes;
ALTER TABLE game_type DROP COLUMN show_only_hotspots_in_sidemenu;



drop table if exists premium_access cascade;
drop table if exists users_premium_access cascade;