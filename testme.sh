AUT="Testme"
AUTDIR="log/"$AUT
AUTTESTCASE=$AUTDIR"/testcases"

AUT_MAINCLASS="examples.test.Testme"

rm -rf $AUTDIR
mkdir $AUTDIR
rm -rf branches
mkdir branches
for testcase in `find $AUTTESTCASE -type f -name "*.tst" -printf '%f\n'`
do
    testcase_id=${testcase%????}
    python concolic.py -v 3333 -t $testcase_id edu.umd.cs.guitar.replayer.JFCReplayerMain "-c $AUT_MAINCLASS -g $AUTDIR/ticket.GUI -e $AUTDIR/ticket.EFG -t $AUTDIR/testcases/$testcase_id.tst -i 2000 -d 200 -l $AUTDIR/logs/$testcase_id.log -gs $AUTDIR/states/$testcase_id.sta -cf ./src/main/java/examples/ticket/configure/configuration.xml -ts"
break
done


mv branches $AUTDIR
