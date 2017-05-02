JC=javac
OBJ=Config.class Message.class MonogoDB.class MppDb.class MppDbConnection.class MysqlDb.class WhyCaptured.class

COMMON_JAR=common.jar
CUR_DIR=$(shell pwd)
LIB_DIR=$CUR_DIR/../lib
CONFIG_P=config.properties
BUILD_DIR=$(CUR_DIR)../build

CLASS_PATH=$(LIB_DIR)/mysql-connector-java.jar:$(LIB_DIR)/mongodb-driver-3.4.0.jar:$(LIB_DIR)/mongodb-driver-core-3.4.0.jar:$(LIB_DIR)/bson-3.4.0.jar:$(LIB_DIR)/DBrokerJdbcDriver-v3.1.6.jar:$(LIB_DIR)/log4j-1.2.12.jar:$(LIB_DIR)
           /json-20090211.jar:$(CUR_DIR)

%.class: %.java
    $(JC) -cp $(CLASS_PATH) $<

$(COMMOM_JAR): $(OBJ)
    jar cvf $(COMMON_JAR) $(OBJ)

.PHONY: clean


clean:
    rm -f $(CUR_DIR)/$(OBJ)
    rm -f $(CUR_DIR)/$(COMMON_JAR)
