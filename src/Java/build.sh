#!/bin/bash

TOP_DIR="'pwd'"
SRC_DIR=""
SRC_PROG_NAME=""
LID_DIR=$TOP_DIR/lib
COMMON_DIR=$TOP_DIR/common
COMMON_JAR=common.jar
CONFIG_P=config.properties
BUILD_DIR=$TOP/build

function Usage(){
    echo "Usage: $0 etl|fetchsinglemsg|fetchmostharmfulmsgs|clean"
    exit 1
}

function BuildCommon(){
    make -C $COMMON_DIR &&{
        cp -f $COMMON_DIR/$COMMON_JAR $BUILD_DIR
        cp -f $COMMON_DIR/$CONFIG_P $BUILD_DIR
    }
}

function CleanupBuildDirectory(){
    make -C $COMMON_DIR clean
    make -C $TOP_DIR/ELT clean
    make -C $TOP_DIR/EFetchSingleMsg clean
    make -C $TOP_DIR/ELT FetchMostHarmfulMsgs clean

    if [ -d $BUILD_DIR ]; then
            rm -rf $BUILD_DIR.old
            mv -f $BUIcLD_DIR $BUILD_DIR.old

    fi
    mkdir $BUILD_DIR
}

function CleanupClassFiles() {
    find . -name '*.class'  -exec rm -f {} \;
}

function DoBuild() {
    BuildCommon || exit
    make -C $SRC_DIR && {
        cp -f $SRC_DIR/*.class $BUILD_DIR
    }
}

if [ $# != 1 ]; then
    Usage
fi

CleanupBuildDirectory

case $1 in
    "common")
        BuildCommon
        exit $?
        ;;
    "etl")
        SRC_DIR=$TOP_DIR/ELT
        SRC_PROG_NAME=FetchSingleMessage.java
        ;;
    "clean")
        CleanupBuildDirectory
        CleanupClassFiles
        exit $?
        ;;
    *)
        Usage
        ;;
esac

DoBuild $SRC_DIR



