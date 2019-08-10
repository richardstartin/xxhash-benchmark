#!/usr/bin/env bash

filename=${1:-bench.csv}


export JAVA_HOME=${PANAMA:-/tmp/panama}
export PATH=$JAVA_HOME/bin:$PATH

mvn clean package
taskset -c 0 java -jar target/benchmarks.jar -f 1 -wi 5 -w 1 -i 5 -r 1 -rf CSV -rff ${filename} -bm thrpt .*xxhash32