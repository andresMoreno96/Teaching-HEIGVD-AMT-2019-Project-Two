#!/bin/sh
if [ "$LOAD_DATA" != "true" ]; then
    rm /docker-entrypoint-initdb.d/*.sql
fi