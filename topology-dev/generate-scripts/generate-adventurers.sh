#!/bin/bash

file="../adventurer-api-mysql/data/3-adventurers-data.sql"
count=1250000
batch=1000

rm $file
touch $file

echo "USE \`adventurer_api\`;" >> $file

for (( i = 0; i <= $count / $batch; i++ )); do
    start=$(( $i * $batch ))
    end=$(( $start + $batch ))

    if (( $end > $count )); then
        end=$count
    fi
    
    if (($start < $end)); then 
        echo "INSERT INTO \`adventurer_entity\` (\`name\`, \`job\`, \`user_email\`) VALUES"
        echo "('jack-$start', 'job-$start', '$j@pot.ato')"
        for (( j = $start + 1; j < $end; j++ ))
        do
            echo ", ('jack-$j', 'job-$j', '$j@pot.ato')"
        done
        echo ";"
    fi

done >> $file