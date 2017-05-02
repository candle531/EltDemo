package Java.common;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by yanzhou on 2017/4/27.
 */
public class MppDbConnection {

    private Config conf;
    private MppDb mpp_db;
    private Connection f_connMpp;

    public static Logger logger = Logger.getLogger(MppDbConnection.class);

    public MppDbConnection(){

        PropertyConfigurator.configure(MppDbConnection.class.getResource("log4j.properties"));
    }


    public Connection openMpp()throws SQLException,ClassNotFoundException{

        conf = new Config();
        mpp_db = new MppDb(conf.getCi_mppConnString(),conf.getCi_mppUser(),conf.getCi_mppPassword());
        f_connMpp = mpp_db.initalize();
        return f_connMpp;
    }
}
