if [ -z $1 ]; then
    echo "Usage: ./guicat ./conf/ticket/ticket.conf"
    exit
fi

#. ./conf/ticket/ticket.conf
. $1

timestamp=`date +%s`
dst="data/"$AUT"-"$timestamp;
log=$dst"/log"
src=$dst/"src"
conf=$dst/"conf"

#may add zip option
if [[ "$2" == "--savedata" ]]; then
    mkdir -p $log $src $conf
    cp -r $AUTDIR $log
    cp -r $AUT_SRC_DIR $src
    cp -r $AUT_CONF_DIR $conf
fi

if [[ "$2" == "--savezip" ]]; then
  mkdir -p $log $src $conf
    cp -r $AUTDIR $log
    cp -r $AUT_SRC_DIR $src
    cp -r $AUT_CONF_DIR $conf
    tar zcf $dst".tar.gz" $dst && rm -rf $dst
fi
