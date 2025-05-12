#!/bin/bash

cd ..
mvn clean install
cp app/target/*.war docker/deployments/
