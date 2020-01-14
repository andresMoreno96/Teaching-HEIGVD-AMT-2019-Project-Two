#!/bin/sh
if [ "$LOAD_DATA" != "true" ]; then
    echo not POTATO
    rm ./*.sql
else
    echo POTATO
fi