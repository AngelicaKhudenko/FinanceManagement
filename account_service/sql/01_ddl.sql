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
    OWNER to account_service_app;