CREATE SEQUENCE hibernate_sequence START 1;

CREATE TABLE user_app
(
  id                BIGSERIAL PRIMARY KEY,
  first_name        CHARACTER VARYING NOT NULL,
  last_name         CHARACTER VARYING NOT NULL,
  email             CHARACTER VARYING NOT NULL,
  password          CHARACTER VARYING NOT NULL,
  profile           CHARACTER VARYING NOT NULL
);

insert into user_app (id, first_name, last_name, email, password, profile) values (1, 'adminstrador', 'admin', 'admin@gmail.com',
'$2a$10$6YlPqyAhIIxI2pRFROWX7ejJ3.TW4lgBZUEd3d76qIXqysC8vPB0O', 'ROLE_ADMIN');

