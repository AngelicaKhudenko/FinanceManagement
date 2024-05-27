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

CREATE TABLE app.currency
(
    id uuid NOT NULL,
    dt_create timestamp(3),
    dt_update timestamp(3),
    title character varying,
    description character varying,
    CONSTRAINT currency_id_pk PRIMARY KEY (id),
    CONSTRAINT currency_dt_create_not_null CHECK (dt_create IS NOT NULL),
    CONSTRAINT currency_title_not_null CHECK (title IS NOT NULL),
    CONSTRAINT currency_description_not_null CHECK (description IS NOT NULL)
);

ALTER TABLE IF EXISTS app.currency
    OWNER to finance_app;

CREATE TABLE app.category
(
    id uuid NOT NULL,
    dt_create timestamp(3),
    dt_update timestamp(3),
    title character varying,
    CONSTRAINT category_id_pk PRIMARY KEY (id),
    CONSTRAINT category_dt_create_not_null CHECK (dt_create IS NOT NULL),
    CONSTRAINT category_title_not_null CHECK (title IS NOT NULL)
);

ALTER TABLE IF EXISTS app.category
    OWNER to finance_app;

CREATE TABLE app.account
(
    id uuid NOT NULL,
    dt_create timestamp(3),
    dt_update timestamp(3),
    title character varying,
    description character varying,
    balance double precision,
    type character varying,
    currency uuid,
    CONSTRAINT account_id_pk PRIMARY KEY (id),
    CONSTRAINT account_dt_create_not_null CHECK (dt_create IS NOT NULL),
    CONSTRAINT account_title_not_null CHECK (title IS NOT NULL),
    CONSTRAINT account_description_not_null CHECK (description IS NOT NULL),
    CONSTRAINT account_type_not_null CHECK (type IS NOT NULL),
    CONSTRAINT account_currency_not_null CHECK (currency IS NOT NULL)
);

ALTER TABLE IF EXISTS app.account
    OWNER to finance_app;