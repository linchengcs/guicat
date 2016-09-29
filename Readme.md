guicat
====

symbolic execution of java swing/awt GUI software to automated generate testcases. It is an artifact of our research paper [guicat](https://cs.wmich.edu/~zijiang/pub/ASECheng.pdf) 


install dependency
----

+ CVC4
+ java 1.8 oracle
+ apache ant
+ xvfb # if run on server, no graphics

set up
----

+ git clone https://github.com/sitstone/guicat
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





