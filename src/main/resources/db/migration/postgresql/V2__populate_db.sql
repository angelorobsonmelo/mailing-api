
insert into user_app (id, first_name, last_name, email, password, profile) values (1, 'adminstrador', 'admin', 'admin@gmail.com',
'$2a$10$6YlPqyAhIIxI2pRFROWX7ejJ3.TW4lgBZUEd3d76qIXqysC8vPB0O', 'ROLE_ADMIN');


insert into user_app (id, first_name, last_name, email, password, profile) values (2, 'usu', 'user', 'user@gmail.com',
'$2a$10$6YlPqyAhIIxI2pRFROWX7ejJ3.TW4lgBZUEd3d76qIXqysC8vPB0O', 'ROLE_USER');

insert into category(id, category) values (1, 'Parceiro');
insert into category(id, category) values (2, 'Candidato');

insert into function(id, function) values (1, 'Modelo de stories');
insert into function(id, function) values (2, 'Modelo de editorial');
insert into function(id, function) values (3, 'Fotográfo');

insert into contact(id, user_name_instagram, user_app_id, category_id, gender) values (
1, '@jose', 1, 1, 'MASCULINO');

insert into contact(id, user_name_instagram, user_app_id, category_id, gender) values (
2, '@maria', 2, 2, 'FEMININO');

insert into contact(id, user_name_instagram, user_app_id, category_id, gender) values (
3, '@manoel', 1, 1, 'MASCULINO');

INSERT INTO contact_function (contact_id, function_id) values (1, 1);
INSERT INTO contact_function (contact_id, function_id) values (1, 2);
INSERT INTO contact_function (contact_id, function_id) values (2, 1);
INSERT INTO contact_function (contact_id, function_id) values (3, 3);