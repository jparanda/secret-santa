#!/bin/bash

docker build -t spring-boot-app .

echo "Listing Docker images..."

# List all Docker images to confirm the build
docker images