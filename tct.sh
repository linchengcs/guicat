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

main=examples.ticket.Ticket
autdir=./log/ticket
gui=$autdir"/ticket.GUI"
efg=$autdir"/ticket.EFG"

tcid11="te1269095122_1"  #name
tcid12="te1269095122_2"  #name
tcid21="te3379743782_1"  #name
tcid22="te3379743782_2"  #name
tcid3="te4175626158_1"  #cancel
tcid4="te426421920_1"  #buy
tcid=$tcid4
tc=$autdir"/branches/"$tcid".tst"

#tcid1="te1269095122"  #name
#tcid2="te3379743782"  #name
#tcid3="te4175626158"  #cancel
#tcid4="te426421920"  #buy
#tcid=$tcid4
#tc=$autdir"/testcases/"$tcid".tst"

log=$autdir"/logs/"$tcid".log"
sta=$autdir"/states/"$tcid".sta"
conf="src/main/java/examples/ticket/configure/configuration.xml"

#cmd="java -Dlog4j.configuration=src/main/resources/log4j.properties -cp $classpath edu.umd.cs.guitar.replayer.JFCReplayerMain -c examples.ticket.Ticket -g ./Demo/Demo.GUI -e ./Demo/Demo.EFG -t ./Demo/testcases/t_e2970984496_e2755731202_e3532357584.tst -i 2000 -d 200 -l ./Demo/logs/t_e2970984496_e2755731202_e3532357584.log -gs ./Demo/states/t_e2970984496_e2755731202_e3532357584.sta -cf ./jfc-aut/RadioButton//guitar-config/configuration.xml -ts"

cmd="java -javaagent:./lib/jacocoagent.jar -Dlog4j.configuration=src/main/resources/log4j.properties -cp $classpath edu.umd.cs.guitar.replayer.JFCReplayerMain -c $main -g $gui -e $efg -t $tc -i 2000 -d 200 -l $log -gs $sta -cf $conf -ts"

eval $cmd
