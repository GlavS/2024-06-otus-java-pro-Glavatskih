#!/bin/bash

i=256

while [[ $i -le 4096 ]]
do
params="-Xms${i}m -Xmx${i}m -XX:+UseSerialGC"
java -jar $params -Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=15,filesize=10m ../build/libs/hw04gc-0.1.jar | sed -n '$p'
printf "%s\n\n" "$params"
((i=i+256))
done
