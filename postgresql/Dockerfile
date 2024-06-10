FROM postgres:16.3-alpine3.19
MAINTAINER ITAcademy

ADD /ddl/account_service/ddl/00_init_account_db.sql /docker-entrypoint-initdb.d/
ADD /ddl/audit_service/ddl/00_init_audit_db.sql /docker-entrypoint-initdb.d/
ADD /ddl/classifier_service/ddl/00_init_classifier_db.sql /docker-entrypoint-initdb.d/
ADD /ddl/user_service/ddl/00_init_user_db.sql /docker-entrypoint-initdb.d/

ADD /ddl/account_service/ddl/01_ddl_account.sql /docker-entrypoint-initdb.d/
ADD /ddl/audit_service/ddl/01_ddl_audit.sql /docker-entrypoint-initdb.d/
ADD /ddl/classifier_service/ddl/01_ddl_classifier.sql /docker-entrypoint-initdb.d/
ADD /ddl/user_service/ddl/01_ddl_user.sql /docker-entrypoint-initdb.d/