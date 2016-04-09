if [ -z $1 ]; then
    echo "Usage: ./guicat ./conf/ticket/ticket.conf"
    exit
fi

#. ./conf/ticket/ticket.conf
. $1

timestamp=`data +%s`
dst="data/"$AUT"-"$timestamp;

#may add zip option
if [[ "$s" == "--savedata" ]]; then
    cp -r $AUTDIR $dst
    cp -r $AUT_SRC_DIR $dst
    cp -r $AUT_CONF_DIR $dst
fi
