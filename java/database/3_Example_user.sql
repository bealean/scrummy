-- ********************************************************************************
-- This script creates the database user and grants the necessary permissions
-- ********************************************************************************

CREATE USER some_appuser_name
WITH PASSWORD 'some_appuser_password';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO some_appuser_name;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO some_appuser_name;
