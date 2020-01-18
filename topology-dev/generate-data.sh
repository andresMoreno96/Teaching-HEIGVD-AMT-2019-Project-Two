#!/bin/bash

file="user-api-mysql/data/3-user-api-data.sql"
count=800000
batch=1000

rm $file
touch $file

echo "USE \`user_api\`;" >> $file

for (( i = 0; i <= $count / $batch; i++ )); do
    start=$(( $i * $batch ))
    end=$(( $start + $batch ))

    if (( $end > $count )); then
        end=$count
    fi
    
    if (($start < $end)); then 
        echo "INSERT INTO \`user_entity\` (\`email\`, \`first_name\`, \`last_name\`, \`password\`) VALUES"
        echo "('$start@pot.ato', 'Pot', 'Ato', '\$2a\$10\$IOLKcXHPD8f.72pFK3x99.VjHihuQH0ZTb.ezr2MAO0IZTddlyr/G')"
        for (( j = $start + 1; j < $end; j++ ))
        do
            echo ", ('$j@pot.ato', 'Pot', 'Ato', '\$2a\$10\$IOLKcXHPD8f.72pFK3x99.VjHihuQH0ZTb.ezr2MAO0IZTddlyr/G')"
        done
        echo ";"
    fi

done >> $file