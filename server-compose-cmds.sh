#!/usr/bin/env bash

docker-compose -f docker-compose.yml up --detach
export TEST=testValue
