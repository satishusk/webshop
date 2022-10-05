insert into roles(role_id, name) VALUES ((select next_val from role_id_seq), 'ROLE_ADMIN');
update role_id_seq set next_val = next_val + 1;
insert into roles(role_id, name) VALUES ((select next_val from role_id_seq), 'ROLE_USER');
update role_id_seq set next_val = next_val + 1;
