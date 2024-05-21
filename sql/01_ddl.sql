CREATE SCHEMA app
	AUTHORIZATION finance_app;

CREATE TABLE app.users
(
    id uuid NOT NULL,
    dt_create timestamp(3),
    dt_update timestamp(3),
    mail character varying,
    fio character varying,
    role character varying,
    status character varying,
    password character varying,
    version bigint,
    CONSTRAINT users_id_pk PRIMARY KEY (id),
    CONSTRAINT users_dt_create_not_null CHECK (dt_create IS NOT NULL),
    CONSTRAINT users_mail_not_null CHECK (mail IS NOT NULL),
    CONSTRAINT users_fio_not_null CHECK (fio IS NOT NULL),
    CONSTRAINT users_role_not_null CHECK (role IS NOT NULL),
    CONSTRAINT users_status_not_null CHECK (status IS NOT NULL),
    CONSTRAINT users_password_not_null CHECK (password IS NOT NULL),
    CONSTRAINT users_version_not_null CHECK (version IS NOT NULL)
);

ALTER TABLE IF EXISTS app.users
    OWNER to finance_app;