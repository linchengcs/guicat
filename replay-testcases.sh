#! /usr/bin/env bash


. ./conf/workout/workout.conf



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

#for testcase in `find $ -type f -name "*.tst" -printf '%f\n'`
for testcase in `find $AUTTESTCASE -type f -name "*.tst" -printf '%f\n'`
do
    testcase_id=${testcase%????}

    cmd="java  -Dlog4j.configuration=$log4j -cp $classpath edu.umd.cs.guitar.replayer.JFCReplayerMain -c $AUT_MAINCLASS -g $guiFile -e $efgFile -t $AUTDIR/testcases/$testcase_id.tst -i 2000 -d 200 -l $AUTDIR/logs/$testcase_id.log -gs $AUTDIR/states/$testcase_id.sta -cf $configurationFile -ts"

    echo $cmd
    eval $cmd
done
