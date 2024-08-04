#!/bin/bash

mem=256

while [[ $mem -le 4096 ]]
do
params="-Xms${mem}m -Xmx${mem}m -XX:+UseSerialGC"
java -jar $params -Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=15,filesize=10m ../build/libs/hw04gc-0.1.jar | sed -n '$p'
printf "%s\n\n" "$params"
((mem=mem+256))
done
