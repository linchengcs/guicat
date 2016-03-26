#check args
if [ -z $1 ] || [ -z $2 ]; then
    echo "Usage: $0 autId guitar|guicat"
    exit
fi

#globals
aut=$1
testMethod=$2
#source config file
. ./conf/$aut/$aut.conf


#logic
if [ "$testMethod" = "guitar" ] ; then
    testcaseDir=$AUTTESTCASE
    reportDir=$guitarReportDir
    reportJacocoExecDir=$guitarReportJacocoExecDir
elif [ "$testMethod" = "guicat" ] ; then
    testcaseDir=$autGuicatTestcase
    reportDir=$guicatReportDir
    reportJacocoExecDir=$guicatReportJacocoExecDir
else
    echo "Usage: $0 autId guitar|guicat"
    exit
fi

rm -rf $reportDir
if [ ! -d "$guitarReportJacocoExecDir" ]; then
    mkdir -p "$guitarReportJacocoExecDir"
fi

if [ ! -d "$guicatReportJacocoExecDir" ]; then
    mkdir -p "$guicatReportJacocoExecDir"
fi

if [ ! -d "$AUTSTA" ]; then
    mkdir -p "$AUTSTA"
fi

#classpath libs + aut
classpath="."
for jar in `ls lib`
do
    if [[ $jar == *.jar ]]
    then
        classpath=$classpath":./lib/"$jar
    fi
done
classpath=$classpath":"$CLASSPATH


for tc in `ls $testcaseDir`
do
    testcase_id=${tc%????}
    testcase=$testcaseDir"/"$tc
    log=$AUTLOG"/"$testcase_id".log"
    sta=$AUTSTA"/"$testcase_id".sta"
    echo "processing "$testcase
    cmd="java -javaagent:./lib/jacocoagent.jar -Dlog4j.configuration=$log4j -cp $classpath edu.umd.cs.guitar.replayer.JFCReplayerMain -c $AUT_MAINCLASS -g $guiFile -e $efgFile -t $testcase -i 500 -d 200 -l $log -gs $sta -cf $configurationFile -ts"
#    echo $cmd
    eval $cmd
    mv ./jacoco.exec  $reportJacocoExecDir"/"$tc".jacoco.exec"
done

cp ./conf/jacoco-template.xml $reportDir"/jacoco.xml"
cd $reportDir
chmod u+wx "jacoco.xml"
sed  -i "s/<fileset dir=\"\${result.classes.dir\}\"><\/fileset>/<fileset dir=\"\${result.classes.dir\}\"><include name=\"$AUTJAR\"\/><\/fileset>/g" jacoco.xml
ant -f "jacoco.xml"
