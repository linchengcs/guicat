#! /usr/bin/env bash

if [ -z $1 ]; then
    echo "Usage: ./guicat ./conf/ticket/ticket.conf"
    exit
fi

#. ./conf/ticket/ticket.conf
. $1

#Factoring.Factor Button
testcase_id=t_e3752782634 #t_e153172456  #calc,
#testcase_id=t_e153172456
#testcase_id= t_e1269095122 #ticket

#testcase_id=$2
testcase_file=$AUTTESTCASE"/"$testcase_id".tst"

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

# replay a test case
#cmd="java  -Dlog4j.configuration=$log4j -cp $classpath edu.umd.cs.guitar.replayer.JFCReplayerMain -c $AUT_MAINCLASS -g $guiFile -e $efgFile -t $AUTDIR/testcases/$testcase_id.tst -i 2000 -d 200 -l $AUTDIR/logs/$testcase_id.log -gs $AUTDIR/states/$testcase_id.sta -cf $configurationFile -ts"
#echo $cmd
#eval $cmd

# concolic execution a test case
# to be deleted testcase_id=${testcase%????}
 python concolic.py -Dguicat.conf=$guicatConfigFile  32 --autosym -t $testcase_id edu.umd.cs.guitar.replayer.JFCReplayerMain "-c $AUT_MAINCLASS -g $guiFile -e $efgFile -t $testcase_file -i 2000 -d 200 -l $AUTDIR/logs/$testcase_id.log -gs $AUTDIR/states/$testcase_id.sta -cf $configurationFile -ts"
