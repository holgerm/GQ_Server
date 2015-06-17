

# --- !Ups

create table action (
  id                        bigint not null,
  name                      varchar(255),
  type_id                   bigint,
  parent_id                 bigint,
  constraint pk_action primary key (id))
;

create table action_type (
  id                        bigint not null,
  name                      varchar(255),
  xmltype                   varchar(255),
  show                      boolean,
  constraint pk_action_type primary key (id))
;

create table attribute (
  id                        bigint not null,
  type_id                   bigint,
  value                     text,
  abstract_value_id         bigint,
  linkto_id                 bigint,
  parent_id                 bigint,
  constraint pk_attribute primary key (id))
;

create table attribute_type (
  id                        bigint not null,
  name                      varchar(255),
  xmltype                   varchar(255),
  editable                  boolean,
  show                      boolean,
  can_have_abstract_value   boolean,
  defaultvalue              varchar(255),
  linkto_id                 bigint,
  optional                  boolean,
  filetype                  varchar(255),
  editortype                varchar(255),
  mimetype                  varchar(255),
  sort                      bigint,
  showinparent              boolean,
  constraint pk_attribute_type primary key (id))
;

create table condition (
  id                        bigint not null,
  fulltext                  text,
  trigger                   varchar(255),
  var_a                     varchar(255),
  opt                       varchar(255),
  var_b                     varchar(255),
  enclosure                 varchar(255),
  is_type                   boolean,
  is_full                   boolean,
  constraint pk_condition primary key (id))
;

create table content (
  id                        bigint not null,
  name                      varchar(255),
  type_id                   bigint,
  content                   text,
  parent_id                 bigint,
  subcontent_id             bigint,
  constraint pk_content primary key (id))
;

create table content_set (
  id                        bigint not null,
  constraint pk_content_set primary key (id))
;

create table content_type (
  id                        bigint not null,
  name                      varchar(255),
  xmltype                   varchar(255),
  content                   varchar(255),
  defaultvalue              varchar(255),
  show                      boolean,
  possible_content_types_id bigint,
  default_content_id        bigint,
  constraint pk_content_type primary key (id))
;

create table content_type_occurrence (
  id                        bigint not null,
  content_id                bigint,
  min                       integer,
  max                       integer,
  sort                      integer,
  constraint pk_content_type_occurrence primary key (id))
;

create table content_type_set (
  id                        bigint not null,
  constraint pk_content_type_set primary key (id))
;

create table games (
  id                        bigint not null,
  name                      varchar(255),
  zip                       varchar(255),
  last_update               timestamp,
  publish                   boolean,
  version                   integer,
  oldversion_id             bigint,
  type_id                   bigint,
  editor_only_scene_type_id bigint,
  constraint pk_games primary key (id))
;

create table GameRights (
  id                        bigint not null,
  rights                    varchar(255),
  game_id                   bigint,
  user_id                   bigint,
  constraint pk_GameRights primary key (id))
;

create table game_type (
  id                        bigint not null,
  name                      varchar(255),
  oeffentlich               boolean,
  costs                     integer,
  mapview                   boolean,
  editor_only_scene_type_id bigint,
  multiple_only_scene_type  boolean,
  easy_editor               boolean,
  constraint pk_game_type primary key (id))
;

create table hotspot (
  id                        bigint not null,
  type_id                   bigint,
  parent_id                 bigint,
  name                      varchar(255),
  sort                      integer,
  longitude                 float,
  latitude                  float,
  constraint pk_hotspot primary key (id))
;

create table hotspot_type (
  id                        bigint not null,
  name                      varchar(255),
  xmltype                   varchar(255),
  show                      boolean,
  image                     varchar(255),
  radius                    integer,
  constraint pk_hotspot_type primary key (id))
;

create table linked_account (
  id                        bigint not null,
  user_id                   bigint,
  provider_user_id          varchar(255),
  provider_key              varchar(255),
  constraint pk_linked_account primary key (id))
;

create table mission (
  id                        bigint not null,
  name                      varchar(255),
  type_id                   bigint,
  constraint pk_mission primary key (id))
;

create table mission_type (
  id                        bigint not null,
  name                      varchar(255),
  costs                     integer,
  oeffentlich               boolean,
  xmltype                   varchar(255),
  constraint pk_mission_type primary key (id))
;

create table newsstreamitems (
  id                        bigint not null,
  title                     text,
  text                      text,
  datum                     timestamp,
  visibility                varchar(255),
  posterclass               varchar(255),
  posterid                  bigint,
  repost                    bigint,
  constraint pk_newsstreamitems primary key (id))
;

create table object_reference (
  id                        bigint not null,
  part_id                   bigint,
  rule_id                   bigint,
  content_id                bigint,
  game_id                   bigint,
  action_id                 bigint,
  attribute_id              bigint,
  hotspot_id                bigint,
  stringvalue               varchar(255),
  special                   varchar(255),
  sort                      integer,
  who                       varchar(255),
  constraint pk_object_reference primary key (id))
;

create table part (
  id                        bigint not null,
  is_scene                  boolean,
  mission_id                bigint,
  scene_id                  bigint,
  sort                      integer,
  parent_id                 bigint,
  candelete                 boolean,
  constraint pk_part primary key (id))
;

create table part_occurrence (
  id                        bigint not null,
  part_id                   bigint,
  min                       integer,
  max                       integer,
  sort                      integer,
  constraint pk_part_occurrence primary key (id))
;

create table part_type (
  id                        bigint not null,
  is_scene                  boolean,
  mission_id                bigint,
  scene_id                  bigint,
  constraint pk_part_type primary key (id))
;

create table ProviderGames (
  id                        bigint not null,
  visibility                boolean,
  game_id                   bigint,
  portal_id                 bigint,
  constraint pk_ProviderGames primary key (id))
;

create table providerportal (
  id                        bigint not null,
  name                      varchar(255),
  template_url              text,
  template_mapping_url      text,
  template_post_url         text,
  template_user_field       varchar(255),
  template_user             varchar(255),
  template_user_xpath       varchar(255),
  template_pw               varchar(255),
  template_pw_xpath         varchar(255),
  template_form             varchar(255),
  template_form_field       varchar(255),
  template_submit_button    varchar(255),
  template_after_login_url  text,
  template_server_url       text,
  additional_css            text,
  designpreset              varchar(255),
  defaultcolor              varchar(255),
  color2                    varchar(255),
  color3                    varchar(255),
  color4                    varchar(255),
  color5                    varchar(255),
  color6                    varchar(255),
  logoimg                   varchar(255),
  auto_verify_users         boolean,
  special_css               text,
  constraint pk_providerportal primary key (id))
;

create table ProviderUsers (
  id                        bigint not null,
  rights                    varchar(255),
  user_id                   bigint,
  portal_id                 bigint,
  constraint pk_ProviderUsers primary key (id))
;

create table rule (
  id                        bigint not null,
  subrules_id               bigint,
  firstaction_id            bigint,
  parent_id                 bigint,
  constraint pk_rule primary key (id))
;

create table rule_set (
  id                        bigint not null,
  constraint pk_rule_set primary key (id))
;

create table rule_type (
  id                        bigint not null,
  name                      varchar(255),
  xmltype                   varchar(255),
  show                      boolean,
  default_implementation_id bigint,
  constraint pk_rule_type primary key (id))
;

create table scene (
  id                        bigint not null,
  type_id                   bigint,
  name                      varchar(255),
  constraint pk_scene primary key (id))
;

create table scene_type (
  id                        bigint not null,
  name                      varchar(255),
  xmltype                   varchar(255),
  show                      boolean,
  icon                      varchar(255),
  icon_open                 varchar(255),
  see_children              boolean,
  can_add_missions          boolean,
  can_add_rules             boolean,
  can_add_hotspots          boolean,
  can_see_missions          boolean,
  can_see_rules             boolean,
  can_see_hotspots          boolean,
  constraint pk_scene_type primary key (id))
;

create table security_role (
  id                        bigint not null,
  role_name                 varchar(255),
  constraint pk_security_role primary key (id))
;

create table sorted_html (
  id                        bigint not null,
  wort                      text,
  zahl                      integer,
  constraint pk_sorted_html primary key (id))
;

create table sorted_string (
  id                        bigint not null,
  me                        varchar(255),
  constraint pk_sorted_string primary key (id))
;

create table template_parameter (
  id                        bigint not null,
  name                      text,
  value                     text,
  constraint pk_template_parameter primary key (id))
;

create table token_action (
  id                        bigint not null,
  token                     varchar(255),
  target_user_id            bigint,
  type                      varchar(2),
  created                   timestamp,
  expires                   timestamp,
  constraint ck_token_action_type check (type in ('EV','PR')),
  constraint uq_token_action_token unique (token),
  constraint pk_token_action primary key (id))
;

create table users (
  id                        bigint not null,
  email                     varchar(255),
  name                      varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  last_login                timestamp,
  active                    boolean,
  email_validated           boolean,
  constraint pk_users primary key (id))
;

create table user_permission (
  id                        bigint not null,
  value                     varchar(255),
  constraint pk_user_permission primary key (id))
;


create table action_attribute (
  action_id                      bigint not null,
  attribute_id                   bigint not null,
  constraint pk_action_attribute primary key (action_id, attribute_id))
;

create table action_type_attribute_type (
  action_type_id                 bigint not null,
  attribute_type_id              bigint not null,
  constraint pk_action_type_attribute_type primary key (action_type_id, attribute_type_id))
;

create table attribute_type_sorted_string (
  attribute_type_id              bigint not null,
  sorted_string_id               bigint not null,
  constraint pk_attribute_type_sorted_string primary key (attribute_type_id, sorted_string_id))
;

create table content_attribute (
  content_id                     bigint not null,
  attribute_id                   bigint not null,
  constraint pk_content_attribute primary key (content_id, attribute_id))
;

create table content_set_content (
  content_set_id                 bigint not null,
  content_id                     bigint not null,
  constraint pk_content_set_content primary key (content_set_id, content_id))
;

create table content_type_attribute_type (
  content_type_id                bigint not null,
  attribute_type_id              bigint not null,
  constraint pk_content_type_attribute_type primary key (content_type_id, attribute_type_id))
;

create table content_type_set_content_type (
  content_type_set_id            bigint not null,
  content_type_id                bigint not null,
  constraint pk_content_type_set_content_type primary key (content_type_set_id, content_type_id))
;

create table games_newsstreamitems (
  games_id                       bigint not null,
  newsstreamitems_id             bigint not null,
  constraint pk_games_newsstreamitems primary key (games_id, newsstreamitems_id))
;

create table games_part (
  games_id                       bigint not null,
  part_id                        bigint not null,
  constraint pk_games_part primary key (games_id, part_id))
;

create table games_attribute (
  games_id                       bigint not null,
  attribute_id                   bigint not null,
  constraint pk_games_attribute primary key (games_id, attribute_id))
;

create table games_hotspot (
  games_id                       bigint not null,
  hotspot_id                     bigint not null,
  constraint pk_games_hotspot primary key (games_id, hotspot_id))
;

create table game_type_part_type (
  game_type_id                   bigint not null,
  part_type_id                   bigint not null,
  constraint pk_game_type_part_type primary key (game_type_id, part_type_id))
;

create table game_type_hotspot_type (
  game_type_id                   bigint not null,
  hotspot_type_id                bigint not null,
  constraint pk_game_type_hotspot_type primary key (game_type_id, hotspot_type_id))
;

create table game_type_scene_type (
  game_type_id                   bigint not null,
  scene_type_id                  bigint not null,
  constraint pk_game_type_scene_type primary key (game_type_id, scene_type_id))
;

create table game_type_attribute_type (
  game_type_id                   bigint not null,
  attribute_type_id              bigint not null,
  constraint pk_game_type_attribute_type primary key (game_type_id, attribute_type_id))
;

create table game_type_part (
  game_type_id                   bigint not null,
  part_id                        bigint not null,
  constraint pk_game_type_part primary key (game_type_id, part_id))
;

create table game_type_hotspot (
  game_type_id                   bigint not null,
  hotspot_id                     bigint not null,
  constraint pk_game_type_hotspot primary key (game_type_id, hotspot_id))
;

create table hotspot_attribute (
  hotspot_id                     bigint not null,
  attribute_id                   bigint not null,
  constraint pk_hotspot_attribute primary key (hotspot_id, attribute_id))
;

create table hotspot_rule (
  hotspot_id                     bigint not null,
  rule_id                        bigint not null,
  constraint pk_hotspot_rule primary key (hotspot_id, rule_id))
;

create table hotspot_type_rule_type (
  hotspot_type_id                bigint not null,
  rule_type_id                   bigint not null,
  constraint pk_hotspot_type_rule_type primary key (hotspot_type_id, rule_type_id))
;

create table hotspot_type_rule (
  hotspot_type_id                bigint not null,
  rule_id                        bigint not null,
  constraint pk_hotspot_type_rule primary key (hotspot_type_id, rule_id))
;

create table hotspot_type_attribute_type (
  hotspot_type_id                bigint not null,
  attribute_type_id              bigint not null,
  constraint pk_hotspot_type_attribute_type primary key (hotspot_type_id, attribute_type_id))
;

create table mission_attribute (
  mission_id                     bigint not null,
  attribute_id                   bigint not null,
  constraint pk_mission_attribute primary key (mission_id, attribute_id))
;

create table mission_rule (
  mission_id                     bigint not null,
  rule_id                        bigint not null,
  constraint pk_mission_rule primary key (mission_id, rule_id))
;

create table mission_content (
  mission_id                     bigint not null,
  content_id                     bigint not null,
  constraint pk_mission_content primary key (mission_id, content_id))
;

create table mission_type_rule_type (
  mission_type_id                bigint not null,
  rule_type_id                   bigint not null,
  constraint pk_mission_type_rule_type primary key (mission_type_id, rule_type_id))
;

create table mission_type_content_type (
  mission_type_id                bigint not null,
  content_type_id                bigint not null,
  constraint pk_mission_type_content_type primary key (mission_type_id, content_type_id))
;

create table mission_type_attribute_type (
  mission_type_id                bigint not null,
  attribute_type_id              bigint not null,
  constraint pk_mission_type_attribute_type primary key (mission_type_id, attribute_type_id))
;

create table mission_type_rule (
  mission_type_id                bigint not null,
  rule_id                        bigint not null,
  constraint pk_mission_type_rule primary key (mission_type_id, rule_id))
;

create table mission_type_content (
  mission_type_id                bigint not null,
  content_id                     bigint not null,
  constraint pk_mission_type_content primary key (mission_type_id, content_id))
;

create table providerportal_newsstreamitems (
  providerportal_id              bigint not null,
  newsstreamitems_id             bigint not null,
  constraint pk_providerportal_newsstreamitems primary key (providerportal_id, newsstreamitems_id))
;

create table providerportal_game_type (
  providerportal_id              bigint not null,
  game_type_id                   bigint not null,
  constraint pk_providerportal_game_type primary key (providerportal_id, game_type_id))
;

create table providerportal_mission_type (
  providerportal_id              bigint not null,
  mission_type_id                bigint not null,
  constraint pk_providerportal_mission_type primary key (providerportal_id, mission_type_id))
;

create table providerportal_sorted_html (
  providerportal_id              bigint not null,
  sorted_html_id                 bigint not null,
  constraint pk_providerportal_sorted_html primary key (providerportal_id, sorted_html_id))
;

create table providerportal_template_paramete (
  providerportal_id              bigint not null,
  template_parameter_id          bigint not null,
  constraint pk_providerportal_template_paramete primary key (providerportal_id, template_parameter_id))
;

create table rule_condition (
  rule_id                        bigint not null,
  condition_id                   bigint not null,
  constraint pk_rule_condition primary key (rule_id, condition_id))
;

create table rule_action (
  rule_id                        bigint not null,
  action_id                      bigint not null,
  constraint pk_rule_action primary key (rule_id, action_id))
;

create table rule_set_rule (
  rule_set_id                    bigint not null,
  rule_id                        bigint not null,
  constraint pk_rule_set_rule primary key (rule_set_id, rule_id))
;

create table rule_type_action_type (
  rule_type_id                   bigint not null,
  action_type_id                 bigint not null,
  constraint pk_rule_type_action_type primary key (rule_type_id, action_type_id))
;

create table rule_type_condition (
  rule_type_id                   bigint not null,
  condition_id                   bigint not null,
  constraint pk_rule_type_condition primary key (rule_type_id, condition_id))
;

create table scene_part (
  scene_id                       bigint not null,
  part_id                        bigint not null,
  constraint pk_scene_part primary key (scene_id, part_id))
;

create table scene_attribute (
  scene_id                       bigint not null,
  attribute_id                   bigint not null,
  constraint pk_scene_attribute primary key (scene_id, attribute_id))
;

create table scene_rule (
  scene_id                       bigint not null,
  rule_id                        bigint not null,
  constraint pk_scene_rule primary key (scene_id, rule_id))
;

create table scene_hotspot (
  scene_id                       bigint not null,
  hotspot_id                     bigint not null,
  constraint pk_scene_hotspot primary key (scene_id, hotspot_id))
;

create table scene_type_part_type (
  scene_type_id                  bigint not null,
  part_type_id                   bigint not null,
  constraint pk_scene_type_part_type primary key (scene_type_id, part_type_id))
;

create table scene_type_hotspot_type (
  scene_type_id                  bigint not null,
  hotspot_type_id                bigint not null,
  constraint pk_scene_type_hotspot_type primary key (scene_type_id, hotspot_type_id))
;

create table scene_type_attribute_type (
  scene_type_id                  bigint not null,
  attribute_type_id              bigint not null,
  constraint pk_scene_type_attribute_type primary key (scene_type_id, attribute_type_id))
;

create table scene_type_part (
  scene_type_id                  bigint not null,
  part_id                        bigint not null,
  constraint pk_scene_type_part primary key (scene_type_id, part_id))
;

create table scene_type_part_occurrence (
  scene_type_id                  bigint not null,
  part_occurrence_id             bigint not null,
  constraint pk_scene_type_part_occurrence primary key (scene_type_id, part_occurrence_id))
;

create table scene_type_hotspot (
  scene_type_id                  bigint not null,
  hotspot_id                     bigint not null,
  constraint pk_scene_type_hotspot primary key (scene_type_id, hotspot_id))
;

create table scene_type_rule (
  scene_type_id                  bigint not null,
  rule_id                        bigint not null,
  constraint pk_scene_type_rule primary key (scene_type_id, rule_id))
;

create table scene_type_rule_type (
  scene_type_id                  bigint not null,
  rule_type_id                   bigint not null,
  constraint pk_scene_type_rule_type primary key (scene_type_id, rule_type_id))
;

create table sorted_html_template_parameter (
  sorted_html_id                 bigint not null,
  template_parameter_id          bigint not null,
  constraint pk_sorted_html_template_parameter primary key (sorted_html_id, template_parameter_id))
;

create table users_newsstreamitems (
  users_id                       bigint not null,
  newsstreamitems_id             bigint not null,
  constraint pk_users_newsstreamitems primary key (users_id, newsstreamitems_id))
;

create table users_security_role (
  users_id                       bigint not null,
  security_role_id               bigint not null,
  constraint pk_users_security_role primary key (users_id, security_role_id))
;

create table users_object_reference (
  users_id                       bigint not null,
  object_reference_id            bigint not null,
  constraint pk_users_object_reference primary key (users_id, object_reference_id))
;

create table users_user_permission (
  users_id                       bigint not null,
  user_permission_id             bigint not null,
  constraint pk_users_user_permission primary key (users_id, user_permission_id))
;
create sequence action_seq;

create sequence action_type_seq;

create sequence attribute_seq;

create sequence attribute_type_seq;

create sequence condition_seq;

create sequence content_seq;

create sequence content_set_seq;

create sequence content_type_seq;

create sequence content_type_occurrence_seq;

create sequence content_type_set_seq;

create sequence games_seq;

create sequence GameRights_seq;

create sequence game_type_seq;

create sequence hotspot_seq;

create sequence hotspot_type_seq;

create sequence linked_account_seq;

create sequence mission_seq;

create sequence mission_type_seq;

create sequence newsstreamitems_seq;

create sequence object_reference_seq;

create sequence part_seq;

create sequence part_occurrence_seq;

create sequence part_type_seq;

create sequence ProviderGames_seq;

create sequence providerportal_seq;

create sequence ProviderUsers_seq;

create sequence rule_seq;

create sequence rule_set_seq;

create sequence rule_type_seq;

create sequence scene_seq;

create sequence scene_type_seq;

create sequence security_role_seq;

create sequence sorted_html_seq;

create sequence sorted_string_seq;

create sequence template_parameter_seq;

create sequence token_action_seq;

create sequence users_seq;

create sequence user_permission_seq;

alter table action add constraint fk_action_type_1 foreign key (type_id) references action_type (id);
create index ix_action_type_1 on action (type_id);
alter table action add constraint fk_action_parent_2 foreign key (parent_id) references action (id);
create index ix_action_parent_2 on action (parent_id);
alter table attribute add constraint fk_attribute_type_3 foreign key (type_id) references attribute_type (id);
create index ix_attribute_type_3 on attribute (type_id);
alter table attribute add constraint fk_attribute_abstractValue_4 foreign key (abstract_value_id) references object_reference (id);
create index ix_attribute_abstractValue_4 on attribute (abstract_value_id);
alter table attribute add constraint fk_attribute_linkto_5 foreign key (linkto_id) references object_reference (id);
create index ix_attribute_linkto_5 on attribute (linkto_id);
alter table attribute add constraint fk_attribute_parent_6 foreign key (parent_id) references attribute (id);
create index ix_attribute_parent_6 on attribute (parent_id);
alter table attribute_type add constraint fk_attribute_type_linkto_7 foreign key (linkto_id) references object_reference (id);
create index ix_attribute_type_linkto_7 on attribute_type (linkto_id);
alter table content add constraint fk_content_type_8 foreign key (type_id) references content_type (id);
create index ix_content_type_8 on content (type_id);
alter table content add constraint fk_content_parent_9 foreign key (parent_id) references content (id);
create index ix_content_parent_9 on content (parent_id);
alter table content add constraint fk_content_subcontent_10 foreign key (subcontent_id) references content_set (id);
create index ix_content_subcontent_10 on content (subcontent_id);
alter table content_type add constraint fk_content_type_possibleConte_11 foreign key (possible_content_types_id) references content_type_set (id);
create index ix_content_type_possibleConte_11 on content_type (possible_content_types_id);
alter table content_type add constraint fk_content_type_defaultConten_12 foreign key (default_content_id) references content_set (id);
create index ix_content_type_defaultConten_12 on content_type (default_content_id);
alter table content_type_occurrence add constraint fk_content_type_occurrence_co_13 foreign key (content_id) references content_type (id);
create index ix_content_type_occurrence_co_13 on content_type_occurrence (content_id);
alter table games add constraint fk_games_oldversion_14 foreign key (oldversion_id) references games (id);
create index ix_games_oldversion_14 on games (oldversion_id);
alter table games add constraint fk_games_type_15 foreign key (type_id) references game_type (id);
create index ix_games_type_15 on games (type_id);
alter table games add constraint fk_games_editor_only_scene_ty_16 foreign key (editor_only_scene_type_id) references scene_type (id);
create index ix_games_editor_only_scene_ty_16 on games (editor_only_scene_type_id);
alter table GameRights add constraint fk_GameRights_game_17 foreign key (game_id) references games (id);
create index ix_GameRights_game_17 on GameRights (game_id);
alter table GameRights add constraint fk_GameRights_user_18 foreign key (user_id) references users (id);
create index ix_GameRights_user_18 on GameRights (user_id);
alter table game_type add constraint fk_game_type_editor_only_scen_19 foreign key (editor_only_scene_type_id) references scene_type (id);
create index ix_game_type_editor_only_scen_19 on game_type (editor_only_scene_type_id);
alter table hotspot add constraint fk_hotspot_type_20 foreign key (type_id) references hotspot_type (id);
create index ix_hotspot_type_20 on hotspot (type_id);
alter table hotspot add constraint fk_hotspot_parent_21 foreign key (parent_id) references hotspot (id);
create index ix_hotspot_parent_21 on hotspot (parent_id);
alter table linked_account add constraint fk_linked_account_user_22 foreign key (user_id) references users (id);
create index ix_linked_account_user_22 on linked_account (user_id);
alter table mission add constraint fk_mission_type_23 foreign key (type_id) references mission_type (id);
create index ix_mission_type_23 on mission (type_id);
alter table object_reference add constraint fk_object_reference_part_24 foreign key (part_id) references part (id);
create index ix_object_reference_part_24 on object_reference (part_id);
alter table object_reference add constraint fk_object_reference_rule_25 foreign key (rule_id) references rule (id);
create index ix_object_reference_rule_25 on object_reference (rule_id);
alter table object_reference add constraint fk_object_reference_content_26 foreign key (content_id) references content (id);
create index ix_object_reference_content_26 on object_reference (content_id);
alter table object_reference add constraint fk_object_reference_game_27 foreign key (game_id) references games (id);
create index ix_object_reference_game_27 on object_reference (game_id);
alter table object_reference add constraint fk_object_reference_action_28 foreign key (action_id) references action (id);
create index ix_object_reference_action_28 on object_reference (action_id);
alter table object_reference add constraint fk_object_reference_attribute_29 foreign key (attribute_id) references attribute (id);
create index ix_object_reference_attribute_29 on object_reference (attribute_id);
alter table object_reference add constraint fk_object_reference_hotspot_30 foreign key (hotspot_id) references hotspot (id);
create index ix_object_reference_hotspot_30 on object_reference (hotspot_id);
alter table part add constraint fk_part_mission_31 foreign key (mission_id) references mission (id);
create index ix_part_mission_31 on part (mission_id);
alter table part add constraint fk_part_scene_32 foreign key (scene_id) references scene (id);
create index ix_part_scene_32 on part (scene_id);
alter table part add constraint fk_part_parent_33 foreign key (parent_id) references part (id);
create index ix_part_parent_33 on part (parent_id);
alter table part_occurrence add constraint fk_part_occurrence_part_34 foreign key (part_id) references part_type (id);
create index ix_part_occurrence_part_34 on part_occurrence (part_id);
alter table part_type add constraint fk_part_type_mission_35 foreign key (mission_id) references mission_type (id);
create index ix_part_type_mission_35 on part_type (mission_id);
alter table part_type add constraint fk_part_type_scene_36 foreign key (scene_id) references scene_type (id);
create index ix_part_type_scene_36 on part_type (scene_id);
alter table ProviderGames add constraint fk_ProviderGames_game_37 foreign key (game_id) references games (id);
create index ix_ProviderGames_game_37 on ProviderGames (game_id);
alter table ProviderGames add constraint fk_ProviderGames_portal_38 foreign key (portal_id) references providerportal (id);
create index ix_ProviderGames_portal_38 on ProviderGames (portal_id);
alter table ProviderUsers add constraint fk_ProviderUsers_user_39 foreign key (user_id) references users (id);
create index ix_ProviderUsers_user_39 on ProviderUsers (user_id);
alter table ProviderUsers add constraint fk_ProviderUsers_portal_40 foreign key (portal_id) references providerportal (id);
create index ix_ProviderUsers_portal_40 on ProviderUsers (portal_id);
alter table rule add constraint fk_rule_subrules_41 foreign key (subrules_id) references rule_set (id);
create index ix_rule_subrules_41 on rule (subrules_id);
alter table rule add constraint fk_rule_firstaction_42 foreign key (firstaction_id) references action (id);
create index ix_rule_firstaction_42 on rule (firstaction_id);
alter table rule add constraint fk_rule_parent_43 foreign key (parent_id) references rule (id);
create index ix_rule_parent_43 on rule (parent_id);
alter table rule_type add constraint fk_rule_type_defaultImplement_44 foreign key (default_implementation_id) references rule (id);
create index ix_rule_type_defaultImplement_44 on rule_type (default_implementation_id);
alter table scene add constraint fk_scene_type_45 foreign key (type_id) references scene_type (id);
create index ix_scene_type_45 on scene (type_id);
alter table token_action add constraint fk_token_action_targetUser_46 foreign key (target_user_id) references users (id);
create index ix_token_action_targetUser_46 on token_action (target_user_id);



alter table action_attribute add constraint fk_action_attribute_action_01 foreign key (action_id) references action (id);

alter table action_attribute add constraint fk_action_attribute_attribute_02 foreign key (attribute_id) references attribute (id);

alter table action_type_attribute_type add constraint fk_action_type_attribute_type_01 foreign key (action_type_id) references action_type (id);

alter table action_type_attribute_type add constraint fk_action_type_attribute_type_02 foreign key (attribute_type_id) references attribute_type (id);

alter table attribute_type_sorted_string add constraint fk_attribute_type_sorted_stri_01 foreign key (attribute_type_id) references attribute_type (id);

alter table attribute_type_sorted_string add constraint fk_attribute_type_sorted_stri_02 foreign key (sorted_string_id) references sorted_string (id);

alter table content_attribute add constraint fk_content_attribute_content_01 foreign key (content_id) references content (id);

alter table content_attribute add constraint fk_content_attribute_attribut_02 foreign key (attribute_id) references attribute (id);

alter table content_set_content add constraint fk_content_set_content_conten_01 foreign key (content_set_id) references content_set (id);

alter table content_set_content add constraint fk_content_set_content_conten_02 foreign key (content_id) references content (id);

alter table content_type_attribute_type add constraint fk_content_type_attribute_typ_01 foreign key (content_type_id) references content_type (id);

alter table content_type_attribute_type add constraint fk_content_type_attribute_typ_02 foreign key (attribute_type_id) references attribute_type (id);

alter table content_type_set_content_type add constraint fk_content_type_set_content_t_01 foreign key (content_type_set_id) references content_type_set (id);

alter table content_type_set_content_type add constraint fk_content_type_set_content_t_02 foreign key (content_type_id) references content_type (id);

alter table games_newsstreamitems add constraint fk_games_newsstreamitems_game_01 foreign key (games_id) references games (id);

alter table games_newsstreamitems add constraint fk_games_newsstreamitems_news_02 foreign key (newsstreamitems_id) references newsstreamitems (id);

alter table games_part add constraint fk_games_part_games_01 foreign key (games_id) references games (id);

alter table games_part add constraint fk_games_part_part_02 foreign key (part_id) references part (id);

alter table games_attribute add constraint fk_games_attribute_games_01 foreign key (games_id) references games (id);

alter table games_attribute add constraint fk_games_attribute_attribute_02 foreign key (attribute_id) references attribute (id);

alter table games_hotspot add constraint fk_games_hotspot_games_01 foreign key (games_id) references games (id);

alter table games_hotspot add constraint fk_games_hotspot_hotspot_02 foreign key (hotspot_id) references hotspot (id);

alter table game_type_part_type add constraint fk_game_type_part_type_game_t_01 foreign key (game_type_id) references game_type (id);

alter table game_type_part_type add constraint fk_game_type_part_type_part_t_02 foreign key (part_type_id) references part_type (id);

alter table game_type_hotspot_type add constraint fk_game_type_hotspot_type_gam_01 foreign key (game_type_id) references game_type (id);

alter table game_type_hotspot_type add constraint fk_game_type_hotspot_type_hot_02 foreign key (hotspot_type_id) references hotspot_type (id);

alter table game_type_scene_type add constraint fk_game_type_scene_type_game__01 foreign key (game_type_id) references game_type (id);

alter table game_type_scene_type add constraint fk_game_type_scene_type_scene_02 foreign key (scene_type_id) references scene_type (id);

alter table game_type_attribute_type add constraint fk_game_type_attribute_type_g_01 foreign key (game_type_id) references game_type (id);

alter table game_type_attribute_type add constraint fk_game_type_attribute_type_a_02 foreign key (attribute_type_id) references attribute_type (id);

alter table game_type_part add constraint fk_game_type_part_game_type_01 foreign key (game_type_id) references game_type (id);

alter table game_type_part add constraint fk_game_type_part_part_02 foreign key (part_id) references part (id);

alter table game_type_hotspot add constraint fk_game_type_hotspot_game_typ_01 foreign key (game_type_id) references game_type (id);

alter table game_type_hotspot add constraint fk_game_type_hotspot_hotspot_02 foreign key (hotspot_id) references hotspot (id);

alter table hotspot_attribute add constraint fk_hotspot_attribute_hotspot_01 foreign key (hotspot_id) references hotspot (id);

alter table hotspot_attribute add constraint fk_hotspot_attribute_attribut_02 foreign key (attribute_id) references attribute (id);

alter table hotspot_rule add constraint fk_hotspot_rule_hotspot_01 foreign key (hotspot_id) references hotspot (id);

alter table hotspot_rule add constraint fk_hotspot_rule_rule_02 foreign key (rule_id) references rule (id);

alter table hotspot_type_rule_type add constraint fk_hotspot_type_rule_type_hot_01 foreign key (hotspot_type_id) references hotspot_type (id);

alter table hotspot_type_rule_type add constraint fk_hotspot_type_rule_type_rul_02 foreign key (rule_type_id) references rule_type (id);

alter table hotspot_type_rule add constraint fk_hotspot_type_rule_hotspot__01 foreign key (hotspot_type_id) references hotspot_type (id);

alter table hotspot_type_rule add constraint fk_hotspot_type_rule_rule_02 foreign key (rule_id) references rule (id);

alter table hotspot_type_attribute_type add constraint fk_hotspot_type_attribute_typ_01 foreign key (hotspot_type_id) references hotspot_type (id);

alter table hotspot_type_attribute_type add constraint fk_hotspot_type_attribute_typ_02 foreign key (attribute_type_id) references attribute_type (id);

alter table mission_attribute add constraint fk_mission_attribute_mission_01 foreign key (mission_id) references mission (id);

alter table mission_attribute add constraint fk_mission_attribute_attribut_02 foreign key (attribute_id) references attribute (id);

alter table mission_rule add constraint fk_mission_rule_mission_01 foreign key (mission_id) references mission (id);

alter table mission_rule add constraint fk_mission_rule_rule_02 foreign key (rule_id) references rule (id);

alter table mission_content add constraint fk_mission_content_mission_01 foreign key (mission_id) references mission (id);

alter table mission_content add constraint fk_mission_content_content_02 foreign key (content_id) references content (id);

alter table mission_type_rule_type add constraint fk_mission_type_rule_type_mis_01 foreign key (mission_type_id) references mission_type (id);

alter table mission_type_rule_type add constraint fk_mission_type_rule_type_rul_02 foreign key (rule_type_id) references rule_type (id);

alter table mission_type_content_type add constraint fk_mission_type_content_type__01 foreign key (mission_type_id) references mission_type (id);

alter table mission_type_content_type add constraint fk_mission_type_content_type__02 foreign key (content_type_id) references content_type (id);

alter table mission_type_attribute_type add constraint fk_mission_type_attribute_typ_01 foreign key (mission_type_id) references mission_type (id);

alter table mission_type_attribute_type add constraint fk_mission_type_attribute_typ_02 foreign key (attribute_type_id) references attribute_type (id);

alter table mission_type_rule add constraint fk_mission_type_rule_mission__01 foreign key (mission_type_id) references mission_type (id);

alter table mission_type_rule add constraint fk_mission_type_rule_rule_02 foreign key (rule_id) references rule (id);

alter table mission_type_content add constraint fk_mission_type_content_missi_01 foreign key (mission_type_id) references mission_type (id);

alter table mission_type_content add constraint fk_mission_type_content_conte_02 foreign key (content_id) references content (id);

alter table providerportal_newsstreamitems add constraint fk_providerportal_newsstreami_01 foreign key (providerportal_id) references providerportal (id);

alter table providerportal_newsstreamitems add constraint fk_providerportal_newsstreami_02 foreign key (newsstreamitems_id) references newsstreamitems (id);

alter table providerportal_game_type add constraint fk_providerportal_game_type_p_01 foreign key (providerportal_id) references providerportal (id);

alter table providerportal_game_type add constraint fk_providerportal_game_type_g_02 foreign key (game_type_id) references game_type (id);

alter table providerportal_mission_type add constraint fk_providerportal_mission_typ_01 foreign key (providerportal_id) references providerportal (id);

alter table providerportal_mission_type add constraint fk_providerportal_mission_typ_02 foreign key (mission_type_id) references mission_type (id);

alter table providerportal_sorted_html add constraint fk_providerportal_sorted_html_01 foreign key (providerportal_id) references providerportal (id);

alter table providerportal_sorted_html add constraint fk_providerportal_sorted_html_02 foreign key (sorted_html_id) references sorted_html (id);

alter table providerportal_template_paramete add constraint fk_providerportal_template_pa_01 foreign key (providerportal_id) references providerportal (id);

alter table providerportal_template_paramete add constraint fk_providerportal_template_pa_02 foreign key (template_parameter_id) references template_parameter (id);

alter table rule_condition add constraint fk_rule_condition_rule_01 foreign key (rule_id) references rule (id);

alter table rule_condition add constraint fk_rule_condition_condition_02 foreign key (condition_id) references condition (id);

alter table rule_action add constraint fk_rule_action_rule_01 foreign key (rule_id) references rule (id);

alter table rule_action add constraint fk_rule_action_action_02 foreign key (action_id) references action (id);

alter table rule_set_rule add constraint fk_rule_set_rule_rule_set_01 foreign key (rule_set_id) references rule_set (id);

alter table rule_set_rule add constraint fk_rule_set_rule_rule_02 foreign key (rule_id) references rule (id);

alter table rule_type_action_type add constraint fk_rule_type_action_type_rule_01 foreign key (rule_type_id) references rule_type (id);

alter table rule_type_action_type add constraint fk_rule_type_action_type_acti_02 foreign key (action_type_id) references action_type (id);

alter table rule_type_condition add constraint fk_rule_type_condition_rule_t_01 foreign key (rule_type_id) references rule_type (id);

alter table rule_type_condition add constraint fk_rule_type_condition_condit_02 foreign key (condition_id) references condition (id);

alter table scene_part add constraint fk_scene_part_scene_01 foreign key (scene_id) references scene (id);

alter table scene_part add constraint fk_scene_part_part_02 foreign key (part_id) references part (id);

alter table scene_attribute add constraint fk_scene_attribute_scene_01 foreign key (scene_id) references scene (id);

alter table scene_attribute add constraint fk_scene_attribute_attribute_02 foreign key (attribute_id) references attribute (id);

alter table scene_rule add constraint fk_scene_rule_scene_01 foreign key (scene_id) references scene (id);

alter table scene_rule add constraint fk_scene_rule_rule_02 foreign key (rule_id) references rule (id);

alter table scene_hotspot add constraint fk_scene_hotspot_scene_01 foreign key (scene_id) references scene (id);

alter table scene_hotspot add constraint fk_scene_hotspot_hotspot_02 foreign key (hotspot_id) references hotspot (id);

alter table scene_type_part_type add constraint fk_scene_type_part_type_scene_01 foreign key (scene_type_id) references scene_type (id);

alter table scene_type_part_type add constraint fk_scene_type_part_type_part__02 foreign key (part_type_id) references part_type (id);

alter table scene_type_hotspot_type add constraint fk_scene_type_hotspot_type_sc_01 foreign key (scene_type_id) references scene_type (id);

alter table scene_type_hotspot_type add constraint fk_scene_type_hotspot_type_ho_02 foreign key (hotspot_type_id) references hotspot_type (id);

alter table scene_type_attribute_type add constraint fk_scene_type_attribute_type__01 foreign key (scene_type_id) references scene_type (id);

alter table scene_type_attribute_type add constraint fk_scene_type_attribute_type__02 foreign key (attribute_type_id) references attribute_type (id);

alter table scene_type_part add constraint fk_scene_type_part_scene_type_01 foreign key (scene_type_id) references scene_type (id);

alter table scene_type_part add constraint fk_scene_type_part_part_02 foreign key (part_id) references part (id);

alter table scene_type_part_occurrence add constraint fk_scene_type_part_occurrence_01 foreign key (scene_type_id) references scene_type (id);

alter table scene_type_part_occurrence add constraint fk_scene_type_part_occurrence_02 foreign key (part_occurrence_id) references part_occurrence (id);

alter table scene_type_hotspot add constraint fk_scene_type_hotspot_scene_t_01 foreign key (scene_type_id) references scene_type (id);

alter table scene_type_hotspot add constraint fk_scene_type_hotspot_hotspot_02 foreign key (hotspot_id) references hotspot (id);

alter table scene_type_rule add constraint fk_scene_type_rule_scene_type_01 foreign key (scene_type_id) references scene_type (id);

alter table scene_type_rule add constraint fk_scene_type_rule_rule_02 foreign key (rule_id) references rule (id);

alter table scene_type_rule_type add constraint fk_scene_type_rule_type_scene_01 foreign key (scene_type_id) references scene_type (id);

alter table scene_type_rule_type add constraint fk_scene_type_rule_type_rule__02 foreign key (rule_type_id) references rule_type (id);

alter table sorted_html_template_parameter add constraint fk_sorted_html_template_param_01 foreign key (sorted_html_id) references sorted_html (id);

alter table sorted_html_template_parameter add constraint fk_sorted_html_template_param_02 foreign key (template_parameter_id) references template_parameter (id);

alter table users_newsstreamitems add constraint fk_users_newsstreamitems_user_01 foreign key (users_id) references users (id);

alter table users_newsstreamitems add constraint fk_users_newsstreamitems_news_02 foreign key (newsstreamitems_id) references newsstreamitems (id);

alter table users_security_role add constraint fk_users_security_role_users_01 foreign key (users_id) references users (id);

alter table users_security_role add constraint fk_users_security_role_securi_02 foreign key (security_role_id) references security_role (id);

alter table users_object_reference add constraint fk_users_object_reference_use_01 foreign key (users_id) references users (id);

alter table users_object_reference add constraint fk_users_object_reference_obj_02 foreign key (object_reference_id) references object_reference (id);

alter table users_user_permission add constraint fk_users_user_permission_user_01 foreign key (users_id) references users (id);

alter table users_user_permission add constraint fk_users_user_permission_user_02 foreign key (user_permission_id) references user_permission (id);

# --- !Downs

drop table if exists action cascade;

drop table if exists action_attribute cascade;

drop table if exists action_type cascade;

drop table if exists action_type_attribute_type cascade;

drop table if exists attribute cascade;

drop table if exists attribute_type cascade;

drop table if exists attribute_type_sorted_string cascade;

drop table if exists condition cascade;

drop table if exists content cascade;

drop table if exists content_attribute cascade;

drop table if exists content_set cascade;

drop table if exists content_set_content cascade;

drop table if exists content_type cascade;

drop table if exists content_type_attribute_type cascade;

drop table if exists content_type_occurrence cascade;

drop table if exists content_type_set cascade;

drop table if exists content_type_set_content_type cascade;

drop table if exists games cascade;

drop table if exists games_newsstreamitems cascade;

drop table if exists games_part cascade;

drop table if exists games_attribute cascade;

drop table if exists games_hotspot cascade;

drop table if exists GameRights cascade;

drop table if exists game_type cascade;

drop table if exists game_type_part_type cascade;

drop table if exists game_type_hotspot_type cascade;

drop table if exists game_type_scene_type cascade;

drop table if exists game_type_attribute_type cascade;

drop table if exists game_type_part cascade;

drop table if exists game_type_hotspot cascade;

drop table if exists hotspot cascade;

drop table if exists hotspot_attribute cascade;

drop table if exists hotspot_rule cascade;

drop table if exists hotspot_type cascade;

drop table if exists hotspot_type_rule_type cascade;

drop table if exists hotspot_type_rule cascade;

drop table if exists hotspot_type_attribute_type cascade;

drop table if exists linked_account cascade;

drop table if exists mission cascade;

drop table if exists mission_attribute cascade;

drop table if exists mission_rule cascade;

drop table if exists mission_content cascade;

drop table if exists mission_type cascade;

drop table if exists mission_type_rule_type cascade;

drop table if exists mission_type_content_type cascade;

drop table if exists mission_type_attribute_type cascade;

drop table if exists mission_type_rule cascade;

drop table if exists mission_type_content cascade;

drop table if exists newsstreamitems cascade;

drop table if exists object_reference cascade;

drop table if exists part cascade;

drop table if exists part_occurrence cascade;

drop table if exists part_type cascade;

drop table if exists ProviderGames cascade;

drop table if exists providerportal cascade;

drop table if exists providerportal_newsstreamitems cascade;

drop table if exists providerportal_game_type cascade;

drop table if exists providerportal_mission_type cascade;

drop table if exists providerportal_sorted_html cascade;

drop table if exists providerportal_template_paramete cascade;

drop table if exists ProviderUsers cascade;

drop table if exists rule cascade;

drop table if exists rule_condition cascade;

drop table if exists rule_action cascade;

drop table if exists rule_set cascade;

drop table if exists rule_set_rule cascade;

drop table if exists rule_type cascade;

drop table if exists rule_type_action_type cascade;

drop table if exists rule_type_condition cascade;

drop table if exists scene cascade;

drop table if exists scene_part cascade;

drop table if exists scene_attribute cascade;

drop table if exists scene_rule cascade;

drop table if exists scene_hotspot cascade;

drop table if exists scene_type cascade;

drop table if exists scene_type_part_type cascade;

drop table if exists scene_type_hotspot_type cascade;

drop table if exists scene_type_attribute_type cascade;

drop table if exists scene_type_part cascade;

drop table if exists scene_type_part_occurrence cascade;

drop table if exists scene_type_hotspot cascade;

drop table if exists scene_type_rule cascade;

drop table if exists scene_type_rule_type cascade;

drop table if exists security_role cascade;

drop table if exists sorted_html cascade;

drop table if exists sorted_html_template_parameter cascade;

drop table if exists sorted_string cascade;

drop table if exists template_parameter cascade;

drop table if exists token_action cascade;

drop table if exists users cascade;

drop table if exists users_newsstreamitems cascade;

drop table if exists users_security_role cascade;

drop table if exists users_object_reference cascade;

drop table if exists users_user_permission cascade;

drop table if exists user_permission cascade;

drop sequence if exists action_seq;

drop sequence if exists action_type_seq;

drop sequence if exists attribute_seq;

drop sequence if exists attribute_type_seq;

drop sequence if exists condition_seq;

drop sequence if exists content_seq;

drop sequence if exists content_set_seq;

drop sequence if exists content_type_seq;

drop sequence if exists content_type_occurrence_seq;

drop sequence if exists content_type_set_seq;

drop sequence if exists games_seq;

drop sequence if exists GameRights_seq;

drop sequence if exists game_type_seq;

drop sequence if exists hotspot_seq;

drop sequence if exists hotspot_type_seq;

drop sequence if exists linked_account_seq;

drop sequence if exists mission_seq;

drop sequence if exists mission_type_seq;

drop sequence if exists newsstreamitems_seq;

drop sequence if exists object_reference_seq;

drop sequence if exists part_seq;

drop sequence if exists part_occurrence_seq;

drop sequence if exists part_type_seq;

drop sequence if exists ProviderGames_seq;

drop sequence if exists providerportal_seq;

drop sequence if exists ProviderUsers_seq;

drop sequence if exists rule_seq;

drop sequence if exists rule_set_seq;

drop sequence if exists rule_type_seq;

drop sequence if exists scene_seq;

drop sequence if exists scene_type_seq;

drop sequence if exists security_role_seq;

drop sequence if exists sorted_html_seq;

drop sequence if exists sorted_string_seq;

drop sequence if exists template_parameter_seq;

drop sequence if exists token_action_seq;

drop sequence if exists users_seq;

drop sequence if exists user_permission_seq;

