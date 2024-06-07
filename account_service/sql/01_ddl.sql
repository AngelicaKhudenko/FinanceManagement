CREATE TABLE app.account
(
    id uuid NOT NULL,
    id_user uuid,
    dt_create timestamp(3),
    dt_update timestamp(3),
    title character varying,
    description character varying,
    balance double precision,
    type character varying,
    currency uuid,
    CONSTRAINT account_id_pk PRIMARY KEY (id),
    CONSTRAINT account_id_user_not_null CHECK (id_user IS NOT NULL),
    CONSTRAINT account_dt_create_not_null CHECK (dt_create IS NOT NULL),
    CONSTRAINT account_title_not_null CHECK (title IS NOT NULL),
    CONSTRAINT account_description_not_null CHECK (description IS NOT NULL),
    CONSTRAINT account_type_not_null CHECK (type IS NOT NULL),
    CONSTRAINT account_currency_not_null CHECK (currency IS NOT NULL)
);

ALTER TABLE IF EXISTS app.account
    OWNER to account_service_app;

CREATE TABLE app.operation
(
    id uuid NOT NULL,
    account uuid,
    dt_create timestamp(3),
    dt_update timestamp(3),
    description character varying,
    category uuid,
    value_operation double precision,
    currency uuid,
    CONSTRAINT operation_id_pk PRIMARY KEY (id),
    CONSTRAINT operation_account_not_null CHECK (account IS NOT NULL),
    CONSTRAINT operation_dt_create_not_null CHECK (dt_create IS NOT NULL),
    CONSTRAINT operation_description_null CHECK (description IS NOT NULL),
    CONSTRAINT operation_category_not_null CHECK (category IS NOT NULL),
    CONSTRAINT operation_value_operation_not_null CHECK (value_operation IS NOT NULL),
    CONSTRAINT operation_currency_not_null CHECK (currency IS NOT NULL),
    CONSTRAINT operation_account_fk FOREIGN KEY (account) REFERENCES app.account (uuid)
);

ALTER TABLE IF EXISTS app.operation
    OWNER to account_service_app;