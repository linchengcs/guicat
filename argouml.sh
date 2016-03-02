AUT="argouml"
AUTDIR="log/"$AUT
AUTTESTCASE=$AUTDIR"/testcases"
configurationFile="./conf/argouml/configuration.xml"

AUT_MAINCLASS="org.argouml.application.Main"
guiFile=$AUTDIR"/"$AUT".GUI"
efgFile=$AUTDIR"/"$AUT".EFG"
logFile=$AUTDIR"/"$AUT".log"
log4j="./src/main/resources/log4j.properties"

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

for jar in `ls ~/study/soft/aut/argouml/argouml-0.34/`
do
    if [[ $jar == *.jar ]]
    then
        classpath=$classpath":/home/oliver/study/soft/aut/argouml/argouml-0.34/"$jar
    fi
done

#cmd="java -cp $classpath org.argouml.application.Main"
#echo $cmd
#eval $cmd
#exit

rm -rf $AUTDIR branches/
mkdir $AUTDIR
mkdir branches/
mkdir $AUTDIR"/testcases"

ripperCmd="java -Dlog4j.configuration=$log4j -cp $classpath edu.umd.cs.guitar.ripper.JFCRipperMain -c $AUT_MAINCLASS -g $guiFile -cf $configurationFile -d 500 -i 2000 -l $logFile"

eval $ripperCmd

gui2efgCmd="java -Dlog4j.configuration=$log4j -cp $classpath  edu.umd.cs.guitar.graph.GUIStructure2GraphConverter -p EFGConverter -g $guiFile -e $efgFile"

eval $gui2efgCmd

testcaseCmd="java -Dlog4j.configuration=$logFile -cp $classpath  edu.umd.cs.guitar.testcase.TestCaseGenerator -p RandomSequenceLengthCoverage -e $efgFile -l 2 -m 200 -d $AUTTESTCASE"  #9 91
#testcaseCmd="java -Dlog4j.configuration=$log4j -cp $classpath  edu.umd.cs.guitar.testcase.TestCaseGenerator -p BytecodeAnalysis  -e $efgFile -l 2 -m 200 -d $AUTTESTCASE --scope ./aut/radioButton.jar  --method pair --shared 0"  #12
echo $testcaseCmd
eval $testcaseCmd

for testcase in `find $AUTTESTCASE -type f -name "*.tst" -printf '%f\n'`
do
    testcase_id=${testcase%????}

#   java -cp $classpath  edu.umd.cs.guitar.replayer.JFCReplayerMain -c $AUT_MAINCLASS -g $guiFile -e $efgFile -t $AUTDIR/testcases/$testcase_id.tst -i 2000 -d 200 -l $AUTDIR/logs/$testcase_id.log -gs $AUTDIR/states/$testcase_id.sta -cf $configurationFile -ts
    python concolic.py -Dguicat.conf=conf/barad-ticket/guicat.properties -cp $classpath -v 32 --autosym -t $testcase_id edu.umd.cs.guitar.replayer.JFCReplayerMain "-c $AUT_MAINCLASS -g $guiFile -e $efgFile -t $AUTDIR/testcases/$testcase_id.tst -i 2000 -d 200 -l $AUTDIR/logs/$testcase_id.log -gs $AUTDIR/states/$testcase_id.sta -cf $configurationFile -ts"
#break
done

mv branches $AUTDIR
