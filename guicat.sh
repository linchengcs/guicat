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

rm -rf $AUTDIR branches/
mkdir $AUTDIR
mkdir branches/
mkdir $AUTDIR"/testcases"

ripperCmd="java -Dlog4j.configuration=$log4j -cp $classpath edu.umd.cs.guitar.ripper.JFCRipperMain -c $AUT_MAINCLASS -g $guiFile -cf $configurationFile -d 500 -i 2000 -l $logFile"

eval $ripperCmd

gui2efgCmd="java -Dlog4j.configuration=$log4j -cp $classpath  edu.umd.cs.guitar.graph.GUIStructure2GraphConverter -p EFGConverter -g $guiFile -e $efgFile"

eval $gui2efgCmd

#testcaseCmd="java -Dlog4j.configuration=$logFile -cp $classpath  edu.umd.cs.guitar.testcase.TestCaseGenerator -p RandomSequenceLengthCoverage -e $efgFile -l 1 -m 200 -d $AUTTESTCASE"
testcaseCmd="java -Dlog4j.configuration=$log4j -cp $classpath  edu.umd.cs.guitar.testcase.TestCaseGenerator -p BytecodeAnalysis  -e $efgFile -l 2 -m 200 -d $AUTTESTCASE --scope ./aut/radioButton.jar  --method pair --shared 0"
echo $testcaseCmd
eval $testcaseCmd

for testcase in `find $AUTTESTCASE -type f -name "*.tst" -printf '%f\n'`
do
    testcase_id=${testcase%????}
    python concolic.py -Dguicat.conf=$guicatConfigFile -v 32 --autosym -t $testcase_id edu.umd.cs.guitar.replayer.JFCReplayerMain "-c $AUT_MAINCLASS -g $guiFile -e $efgFile -t $AUTDIR/testcases/$testcase_id.tst -i 2000 -d 200 -l $AUTDIR/logs/$testcase_id.log -gs $AUTDIR/states/$testcase_id.sta -cf $configurationFile -ts"
#break
done

mv branches $AUTDIR

#create guicat testcases
cmd="java -Dguicat.conf=$guicatConfigFile -cp $classpath guicat.testcase.Generator $guiFile $AUTTESTCASE $branchDir $autGuicatTestcase"
echo $cmd
eval $cmd

./jacoco.sh $AUT guitar
./jacoco.sh $AUT guicat
