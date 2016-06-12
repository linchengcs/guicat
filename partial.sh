#!/usr/bin/env bash
if [ -z $1 ]; then
    echo "Usage: ./guicat ./conf/ticket/ticket.conf"
    exit
fi

#. ./conf/ticket/ticket.conf
. $1

classpath="."
for jar in `ls lib`
do
    if [[ $jar == *.jar ]]
    then
        classpath=$classpath":./lib/"$jar
    fi
done

for jar in `ls aut`
do
    if [[ $jar == *.jar ]]
    then
        classpath=$classpath":./aut/"$jar
    fi
done

for jar in `ls $libDir`
do
    if [[ $jar == *.jar ]]
    then
        classpath=$classpath":"$libDir"/"$jar
    fi
done

echo $libDir
echo $classpath



#create guicat testcases
timestring=`date --rfc-3339=seconds`
echo $timestring

if [[ "$2" == "savedata" ]]; then
   cp  $AUTDIR "data/abc"

fi

echo $0
echo $1
echo $2
echo $?
