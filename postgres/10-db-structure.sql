CREATE DATABASE heisenbug;
CREATE ROLE program WITH PASSWORD 'test';
GRANT ALL PRIVILEGES ON DATABASE heisenbug TO program;
ALTER ROLE program WITH LOGIN;