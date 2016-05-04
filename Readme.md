guicat
====

symbolic execution of GUI


install dependency
----

+ CVC4
+ java 1.8 oracle
+ apache ant
+ xvfb # if run on server, no graphics

set up
----

+ vpn to wmu intranet
+ git clone http://141.218.147.238/guicat
+ git checkout topic

run
----

+ ./guicat.sh ./conf/barad-ticket/barad-ticket.conf
+ xvfb-run ./guicat.sh ./conf/barad-ticket/barad-ticket.conf #no graphicx
+ ./guicat.sh ./conf/barad-ticket/barad-ticket.conf --savedata #copy result to ./data dir

+ ./guicat.sh ./conf/workout/workout.conf  #test workout generator

Arguments
----

+ guitar test case length : see ./conf/yourAut/yourAut.conf

changes
----

+ no support for guicat-guitar.sh guitar-catg.sh, seems no necessary?





