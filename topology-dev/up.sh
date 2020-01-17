#!/bin/bash

# backup .env file
cp .env .env-backup

if (( $# > 0 )); then
    # remove old data
    if (( $1 == "-r" )) || (( $1 == "-l" )); then
        rm -r data-*
    fi

    # set .env to load data
    if (( $1 == "-l" )); then
        echo "LOAD_DATA=true" >> .env
    fi
else
    # set .env to not load data
    echo "LOAD_DATA=false" >> .env
fi

docker-compose up --build

# reset .env
mv .env-backup .env