#! /usr/bin/env bash

classpath=""
for jar in `ls aut`
do
    if [[ $jar == *.jar ]]
    then
        classpath=$classpath":./aut/"$jar
    fi
done
classpath=$classpath":./lib/sym-agent.jar"

cmd="java -cp $classpath guicat.util.PrintAccessbileName $1"
#echo $cmd
eval $cmd
