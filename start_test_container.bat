@echo off
REM Stop Docker Compose containers
docker-compose -f docker-compose.test.yml down

REM Get latest test docker image
docker build -t userrole:latest .

REM start Docker Compose containers
docker-compose -f docker-compose.test.yml up -d

REM Show running containers
docker ps
