#!/bin/bash


mem=256
maxMem=1024
step=128
gcType="UseG1GC"

while [[ $mem -le maxMem ]]
do
  params="-Xms${mem}m -Xmx${mem}m -XX:+${gcType}"
  java -jar $params -Xlog:gc=debug:file=../logs/gc-%p-%t.log:tags,uptime,time,level:filecount=15,filesize=10m ../build/libs/hw04gc-0.1.jar | sed -n '$p'
  printf "%s\n\n" "$params"
  ((mem=mem+step))
done
