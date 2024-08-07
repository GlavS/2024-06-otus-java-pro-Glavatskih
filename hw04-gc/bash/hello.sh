#!/bin/bash

for file in *.log
do
  tail -n1 "$file" | perl script.pl
done
