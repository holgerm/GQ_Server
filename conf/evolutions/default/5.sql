# --- !Ups

 ALTER TABLE attribute ADD COLUMN subactions_id bigint;

alter table attribute add constraint fk_attribute_subactions_42 foreign key (subactions_id) references action_set (id);
create index ix_attribute_subactions_42 on attribute (subactions_id);






# --- !Downs


ALTER TABLE attribute DROP CONSTRAINT IF EXISTS fk_attribute_subactions_42;

ALTER TABLE attribute DROP INDEX ix_attribute_subactions_42;



ALTER TABLE attribute DROP COLUMN subactions_id;

