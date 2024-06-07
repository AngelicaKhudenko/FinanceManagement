CREATE ROLE audit_service_app WITH
	LOGIN
	NOSUPERUSER
	CREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	NOBYPASSRLS
	CONNECTION LIMIT -1
	PASSWORD '12345';

CREATE DATABASE audit_service
	WITH
	OWNER=audit_service_app
	ENCODING='UTF8'
	CONNECTION LIMIT=-1
	IS_TEMPLATE=False;