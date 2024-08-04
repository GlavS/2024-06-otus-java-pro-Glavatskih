i=256

while [  $i -lt 4097 ]; do
    params="-Xms${i}m -Xmx${i}m"
    echo $params
    let "i=i+256"
done