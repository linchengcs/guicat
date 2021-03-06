#!/usr/bin/env bash
if [ -z $1 ]; then
    echo "Usage: ./guicat ./conf/ticket/ticket.conf"
    exit
fi

#. ./conf/ticket/ticket.conf
. $1


#if [[ -z $3 ]]; then
#    GUITAR_TC_LEN=$3
#fi

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

#echo $libDir
#echo $classpath

rm -rf $AUTDIR branches/
mkdir  -p $AUTDIR
mkdir -p branches/
mkdir -p $AUTDIR"/testcases"

./clean.sh


cmd="java -cp $classpath guicat.util.PrintAccessbileName $AUT_MAINCLASS"
eval $cmd > $AUTDIR"/accessbileName.txt"

ripperCmd="java -Dlog4j.configuration=$log4j -cp $classpath edu.umd.cs.guitar.ripper.JFCRipperMain -c $AUT_MAINCLASS -g $guiFile -cf $configurationFile -d 10 -i 200 -l $logFile"
echo $ripperCmd
eval $ripperCmd
#exit

gui2efgCmd="java -Dlog4j.configuration=$log4j -cp $classpath  edu.umd.cs.guitar.graph.GUIStructure2GraphConverter -p EFGConverter -g $guiFile -e $efgFile"
eval $gui2efgCmd
#exit

testcaseCmd="java -Dlog4j.configuration=$logFile -cp $classpath  edu.umd.cs.guitar.testcase.TestCaseGenerator -p SequenceLengthCoverage -e $efgFile -l $GUITAR_TC_LEN -m 20000 -d $AUTTESTCASE"
#testcaseCmd="java -Dlog4j.configuration=$logFile -cp $classpath  edu.umd.cs.guitar.testcase.TestCaseGenerator -p RandomSequenceLengthCoverage -e $efgFile -l $GUITAR_TC_LEN -m 200 -d $AUTTESTCASE"
#testcaseCmd="java -Dlog4j.configuration=$log4j -cp $classpath  edu.umd.cs.guitar.testcase.TestCaseGenerator -p BytecodeAnalysis  -e $efgFile -l $GUITAR_TC_LEN -m 200 -d $AUTTESTCASE --scope $CLASSPATH  --method pair --shared 0"
#echo $testcaseCmd
eval $testcaseCmd

#exit
#
#enumCmd="java  -Dguicat.conf=$guicatConfigFile  -cp $classpath guicat.testcase.EnumGenerator $AUTTESTCASE $enumGuicatTestcase $guiFile"
##echo $enumCmd
#eval $enumCmd
#
#
#for testcase in `find $enumGuicatTestcase -type f -name "*.tst" -printf '%f\n'`
#do
#    testcase_id=${testcase%????}
#    python concolic.py -Dguicat.conf=$guicatConfigFile  32 --autosym -t $testcase_id edu.umd.cs.guitar.replayer.JFCReplayerMain "-c $AUT_MAINCLASS -g $guiFile -e $efgFile -t $enumGuicatTestcase/$testcase_id.tst -i 200 -d 10 -l $AUTDIR/logs/$testcase_id.log -gs $AUTDIR/states/$testcase_id.sta -cf $configurationFile -ts"
##break
#done
#
#mv branches $AUTDIR
#
##create guicat testcases
#cmd="java -Dguicat.conf=$guicatConfigFile -cp $classpath guicat.testcase.Generator $guiFile $enumGuicatTestcase $branchDir $autGuicatTestcase"
##echo $cmd
#eval $cmd
#
./jacoco.sh $AUT guitar
./jacoco.sh $AUT guicat


./save-data.sh $1 $2
