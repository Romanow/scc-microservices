#!/bin/bash
set -e

PGPASSWORD=test psql -U program -d heisenbug <<-EOSQL
    CREATE SCHEMA IF NOT EXISTS orders;
    CREATE SCHEMA IF NOT EXISTS warehouse;
    CREATE SCHEMA IF NOT EXISTS delivery;
EOSQL