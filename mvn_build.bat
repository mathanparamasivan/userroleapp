@echo off

REM Run Maven clean install and update dependencies, skipping tests
mvn clean install -U -Dmaven.test.skip=true
