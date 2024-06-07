CREATE TABLE app.audit
(
    id uuid NOT NULL,
    dt_create timestamp(3),
    id_user uuid,
    text character varying,
    type character varying,
    id double precision,
    CONSTRAINT audit_id_pk PRIMARY KEY (id),
    CONSTRAINT audit_dt_create_not_null CHECK (dt_create IS NOT NULL),
    CONSTRAINT audit_id_user_not_null CHECK (id_user IS NOT NULL),
    CONSTRAINT audit_text_not_null CHECK (text IS NOT NULL),
    CONSTRAINT audit_type_not_null CHECK (type IS NOT NULL),
    CONSTRAINT audit_id_not_null CHECK (id IS NOT NULL)
);

ALTER TABLE IF EXISTS app.audit
    OWNER to audit_service_app;