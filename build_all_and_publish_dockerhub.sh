#!/bin/bash

cd bad-passwords-generator
mvn clean install
if docker build -t cgos/badpasswordsgenerator . ; then
  docker push cgos/badpasswordsgenerator
fi
cd ..

cd gateway
mvn clean install
if docker build -t cgos/gateway . ; then
  docker push cgos/gateway
fi
cd ..

