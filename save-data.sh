if [ -z $1 ]; then
    echo "Usage: ./guicat ./conf/ticket/ticket.conf"
    exit
fi

#. ./conf/ticket/ticket.conf
. $1

timestamp=`date +%s`
dst="data/"$AUT"-"$timestamp;

#may add zip option
if [[ "$2" == "--savedata" ]]; then
    cp -r $AUTDIR $dst
    cp -r $AUT_SRC_DIR $dst
    cp -r $AUT_CONF_DIR $dst
fi
