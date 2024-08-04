#!/bin/bash

i=256

while [[ $i -lt 4097 ]]
do
params="-Xms${i}m -Xmx${i}m -XX:+UseSerialGC -XX:+PrintCommandLineFlags"
java -jar $params ../build/libs/hw04gc-0.1.jar
printf "%s\n\n" "$params"
((i=i+256))
done
