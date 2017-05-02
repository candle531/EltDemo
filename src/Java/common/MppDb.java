package Java.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by yanzhou on 2017/4/27.
 */
public class MppDb {

    private String md_connectionString;
    private String md_user;
    private String md_pass;

    public MppDb(String mdConnectionString, String mdUser, String mdPass){

        this.md_connectionString = mdConnectionString;
        this.md_user = mdUser;
        this.md_pass = mdPass;
    }

    public Connection initalize() throws SQLException,ClassNotFoundException{
        Class.forName("cn.ac.iie.jdbc.DBrokerI.DBrokerDriver");
        return DriverManager.getConnection(md_connectionString,md_user,md_pass);
    }
}
