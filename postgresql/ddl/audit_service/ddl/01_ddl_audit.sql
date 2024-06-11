\c audit_service

CREATE SCHEMA app AUTHORIZATION audit_service_app;

CREATE TABLE app.audit
(
    id_subject uuid NOT NULL,
    dt_create timestamp(3),
    id_user uuid,
    text character varying,
    type character varying,
    id_object character varying,
    CONSTRAINT audit_id_subject_pk PRIMARY KEY (id_subject),
    CONSTRAINT audit_dt_create_not_null CHECK (dt_create IS NOT NULL),
    CONSTRAINT audit_id_user_not_null CHECK (id_user IS NOT NULL),
    CONSTRAINT audit_text_not_null CHECK (text IS NOT NULL),
    CONSTRAINT audit_type_not_null CHECK (type IS NOT NULL),
    CONSTRAINT audit_id_object_not_null CHECK (id_object IS NOT NULL)
);

ALTER TABLE IF EXISTS app.audit
    OWNER to audit_service_app;