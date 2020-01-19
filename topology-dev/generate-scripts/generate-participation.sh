#!/bin/bash

file="../adventurer-api-mysql/data/5-participation-data.sql"
count=1000
pCount=1000

rm $file
touch $file

echo "USE \`adventurer_api\`;" >> $file

for (( i = 0; i <= $count; i++ )); do

    echo "INSERT INTO \`adventurer_entity_participation\` (\`participants_name\`, \`participation_id\`) VALUES"
    echo "('jack-$i', '1')"
    for (( j = 2; j <= $pCount; j++ )); do
        echo ", ('jack-$i', '$j')"
    done
    echo ";"

done >> $file