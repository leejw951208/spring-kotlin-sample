CREATE TABLE t_user (
   id bigserial NOT NULL PRIMARY KEY,
   email varchar(50) NOT NULL UNIQUE,
   password varchar NOT NULL,
   name varchar(10) NOT NULL,
   status varchar(10) NOT NULL,
   created_at timestamp DEFAULT now() NOT NULL,
   created_by bigint NOT NULL,
   updated_at timestamp NULL,
   updated_by bigint NULL,
   deleted bool NULL,
   deleted_at timestamp NULL,
   deleted_by bigint NULL
);

CREATE TABLE t_user_role (
   id bigserial NOT NULL PRIMARY KEY,
   user_id bigint NOT NULL,
   role varchar(10) NOT NULL,
   created_at timestamp DEFAULT now() NOT NULL,
   created_by bigint NOT NULL,
   updated_at timestamp NULL,
   updated_by bigint NULL,
   deleted bool NULL,
   deleted_at timestamp NULL,
   deleted_by bigint NULL
);

CREATE TABLE t_token (
    id bigserial NOT NULL PRIMARY KEY,
    user_id bigint NOT NULL UNIQUE,
    refresh_token varchar NOT NULL
);
