#create guicat testcases
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
cmd="java -Dguicat.conf=$guicatConfigFile -cp $classpath guicat.testcase.Generator $guiFile $AUTTESTCASE $branchDir $autGuicatTestcase"
echo $cmd
eval $cmd


./jacoco.sh $AUT guicat
