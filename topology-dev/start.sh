#!/bin/bash

if [ $1 = "--load_data" ]; then
    cp .env .env-backup
    echo "LOAD_DATA=true" >> .env
    rm -r ./data-*
fi

docker-compose up -d --build

if [ $1 = "--load_data" ]; then
    rm .env
    mv .env-backup .env
fi