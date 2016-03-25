#aut id

if [ -z $1 ]; then
    echo "Usage: $0 autId"
    exit
fi

aut=$1

#source config file
. ./conf/$aut/$aut.conf



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

for tc in `ls $AUTTESTCASE`
do
    testcase_id=${tc%????}
    testcase=$AUTTESTCASE"/"$tc
    log=$AUTLOG"/"$testcase_id".log"
    sta=$AUTSTA"/"$testcase_id".sta"
    echo "processing "$testcase
    cmd="java -javaagent:./lib/jacocoagent.jar -Dlog4j.configuration=$log4j -cp $classpath edu.umd.cs.guitar.replayer.JFCReplayerMain -c $AUT_MAINCLASS -g $guiFile -e $efgFile -t $testcase -i 500 -d 200 -l $log -gs $sta -cf $configurationFile -ts"
#    echo $cmd
    eval $cmd
    mv ./jacoco.exec  $guitarReportJacocoExecDir"/"$tc".jacoco.exec"
done

cp ./conf/jacoco-template.xml $guitarReportDir"/jacoco.xml"
cd $guitarReportDir
chmod u+wx "jacoco.xml"
sed  -i "s/<fileset dir=\"\${result.classes.dir\}\"><\/fileset>/<fileset dir=\"\${result.classes.dir\}\"><include name=\"$AUTJAR\"\/><\/fileset>/g" jacoco.xml
ant -f "jacoco.xml"
