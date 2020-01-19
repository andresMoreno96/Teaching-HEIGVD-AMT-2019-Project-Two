#!/bin/bash

file="../adventurer-api-mysql/data/4-quests-data.sql"
count=1000000
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
        echo "INSERT INTO \`quest_entity\` (\`title\`, \`description\`, \`owner_name\`) VALUES"
        echo "('quest-$start', 'description-$start', 'jack-$start')"
        for (( j = $start + 1; j < $end; j++ ))
        do
            echo ", ('quest-$j', 'description-$j', 'jack-$j')"
        done
        echo ";"
    fi

done >> $file
