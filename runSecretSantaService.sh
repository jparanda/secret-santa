#!/bin/bash

docker run -d -p 8080:8080 spring-boot-app

echo "Listing running containers..."

docker ps