# --- !Ups


create table content_rule (
  content_id                     bigint not null,
  rule_id                        bigint not null,
  constraint pk_content_rule primary key (content_id, rule_id))
;


create table content_type_rule_type (
  content_type_id                bigint not null,
  rule_type_id                   bigint not null,
  constraint pk_content_type_rule_type primary key (content_type_id, rule_type_id))
;





# --- !Downs

drop table content_rule;
drop table content_type_rule_type;