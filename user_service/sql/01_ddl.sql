CREATE SCHEMA app
	AUTHORIZATION user_service_app;

CREATE TABLE app.users
(
    id uuid NOT NULL,
    dt_create timestamp(3),
    dt_update timestamp(3),
    mail character varying UNIQUE,
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
    OWNER to user_service_app;

CREATE TABLE app.mails
(
    id uuid NOT NULL,
    dt_create timestamp(3),
    status character varying,
    sender character varying,
    topic character varying,
    adressee character varying,
    text character varying,
    CONSTRAINT mails_id_pk PRIMARY KEY (id),
    CONSTRAINT mails_dt_create_not_null CHECK (dt_create IS NOT NULL),
    CONSTRAINT mails_status_not_null CHECK (status IS NOT NULL),
    CONSTRAINT mails_sender_not_null CHECK (sender IS NOT NULL),
    CONSTRAINT mails_topic_not_null CHECK (topic IS NOT NULL),
    CONSTRAINT mails_adressee_not_null CHECK (adressee IS NOT NULL),
    CONSTRAINT mails_text_not_null CHECK (text IS NOT NULL)
);

ALTER TABLE IF EXISTS app.mails
    OWNER to user_service_app;