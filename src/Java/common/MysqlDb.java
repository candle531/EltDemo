package Java.common;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by yanzhou on 2017/4/27.
 */
public class MysqlDb {

        private String md_ip;
        private String md_port;
        private String md_user;
        private String md_pass;
        private String md_dbName;

        private Logger logger = Logger.getLogger(MysqlDb.class);

        public MysqlDb(String mdIp, String mdPort, String mdUser, String mdPass, String mdDbName){

            PropertyConfigurator.configure(MysqlDb.class.getResource("log4j.properties"));
            this.md_ip = mdIp;
            this.md_port = mdPort;
            this.md_user = mdUser;
            this.md_pass = mdPass;
            this.md_dbName = mdDbName;
        }

        public Connection open(){

            try {

                Class.forName("com.mysql.jdbc.Driver");

                String dbUrl = "jdbc:mysql://" + md_ip + ":"
                        + md_port + "/" + md_dbName + "?useUnicode=true&characterEncoding=utf8";

                return DriverManager.getConnection(dbUrl,md_user,md_pass);

            }catch(ClassNotFoundException e){

                logger.error("failed to load mysql Driver"+e.getMessage());
                return null;
            }catch(SQLException e){

                logger.error("openMysqlDb: failed to open mysql db:"+e.getMessage());
                return null;
            }
        }

        public void close(Connection mysql_conn){

            try{
                mysql_conn.close();
            }catch(SQLException e){
                logger.error("closeMysqlDb: failed to close mysql db:"+e.getMessage());
            }

        }
}
