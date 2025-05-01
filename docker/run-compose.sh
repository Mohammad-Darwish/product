#!/bin/bash

docker compose -f postgresql-compose.yaml up --force-recreate --build
