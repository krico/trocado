#!/bin/bash

BASE=$(cd $(dirname $0); cd ../../../; pwd)
GEN=${BASE}/target/generated-sources
OUT=${BASE}/target/angular-clients

mvn compile appengine:endpoints_get_discovery_doc

mkdir -p ${OUT}

find ${GEN} -type f -name "*-rest.discovery" -exec endpoints-angular-client-generator -s -d {} -o ${OUT} \;