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

create table category
(
  id  BIGSERIAL PRIMARY KEY,
  category CHARACTER VARYING NOT NULL
);

CREATE TABLE contact
(
  id                          BIGSERIAL PRIMARY KEY,
  user_name_instagram        CHARACTER VARYING NOT NULL,
  registration_date          date NOT NULL default now(),
  gender                     CHARACTER VARYING NOT NULL,
  user_app_id                int not null,
  category_id                int not null ,
  foreign key (user_app_id) references user_app(id),
  foreign key (category_id) references category(id)
);

create table function
(
   id  BIGSERIAL PRIMARY KEY,
   function CHARACTER VARYING NOT NULL
);

create table contact_function
(
  id  BIGSERIAL PRIMARY KEY,
  contact_id int not null,
  function_id int not null,
  foreign key (contact_id) references contact(id),
  foreign key (function_id) references function(id)
);



