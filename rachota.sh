AUT="rachota"
configurationFile="./conf/rachota/configuration.xml"
AUT_MAINCLASS="org.cesilko.rachota.gui.MainWindow"


AUTDIR="log/"$AUT
AUTTESTCASE=$AUTDIR"/testcases"

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

rm -rf $AUTDIR branches/
mkdir $AUTDIR
mkdir branches/
mkdir $AUTDIR"/testcases"

ripperCmd="java -Dlog4j.configuration=$log4j -cp $classpath edu.umd.cs.guitar.ripper.JFCRipperMain -c $AUT_MAINCLASS -g $guiFile -cf $configurationFile -d 100 -i 2000 -l $logFile"

eval $ripperCmd
exit
gui2efgCmd="java -Dlog4j.configuration=$log4j -cp $classpath  edu.umd.cs.guitar.graph.GUIStructure2GraphConverter -p EFGConverter -g $guiFile -e $efgFile"

eval $gui2efgCmd

#testcaseCmd="java -Dlog4j.configuration=$logFile -cp $classpath  edu.umd.cs.guitar.testcase.TestCaseGenerator -p RandomSequenceLengthCoverage -e $efgFile -l 1 -m 200 -d $AUTTESTCASE"
testcaseCmd="java -Dlog4j.configuration=$log4j -cp $classpath  edu.umd.cs.guitar.testcase.TestCaseGenerator -p BytecodeAnalysis  -e $efgFile -l 2 -m 200 -d $AUTTESTCASE --scope ./aut/radioButton.jar  --method pair --shared 0"
echo $testcaseCmd
eval $testcaseCmd

for testcase in `find $AUTTESTCASE -type f -name "*.tst" -printf '%f\n'`
do
    testcase_id=${testcase%????}
    python concolic.py -Dguicat.conf=conf/ticket/guicat.properties -v 32 --autosym -t $testcase_id edu.umd.cs.guitar.replayer.JFCReplayerMain "-c $AUT_MAINCLASS -g $guiFile -e $efgFile -t $AUTDIR/testcases/$testcase_id.tst -i 2000 -d 200 -l $AUTDIR/logs/$testcase_id.log -gs $AUTDIR/states/$testcase_id.sta -cf $configurationFile -ts"
#break
done

mv branches $AUTDIR
