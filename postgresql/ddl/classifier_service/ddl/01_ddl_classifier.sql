CREATE SCHEMA app
	AUTHORIZATION classifier_service_app;

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
    OWNER to classifier_service_app;

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
    OWNER to classifier_service_app;
