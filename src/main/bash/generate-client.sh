#!/bin/bash

BASE=$(cd $(dirname $0); cd ../../../; pwd)
GEN=${BASE}/target/generated-sources
OUT=${BASE}/src/main/client/endpoints

if [ "$1" != "--no-compile" ];
then
  mvn compile appengine:endpoints_get_discovery_doc
fi
mkdir -p ${OUT}

find ${GEN} -type f -name "*-rest.discovery" -exec endpoints-angular-client-generator -s -d {} -o ${OUT} \;