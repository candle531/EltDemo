package Java.common;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

/**
 * Created by yanzhou on 2017/4/27.
 */
public class MongoDB {

    private String md_ip;
    private int md_port;
    private String md_user;
    private String md_pass;
    private String md_dbName;

    private MongoClient mc;
    private MongoDatabase md;

    public MongoDB(String mdIp, int mdPort, String mdUser, String mdPass, String mdDbName){

        this.md_ip = mdIp;
        this.md_port = mdPort;
        this.md_user = mdUser;
        this.md_pass = mdPass;
        this.md_dbName = mdDbName;
    }

    public MongoDatabase initialize(){

        if(md_user!=null && md_pass != null){

            MongoCredential credential = MongoCredential.createCredential(md_user,md_dbName,md_pass.toCharArray());

            ServerAddress serverAddress = new ServerAddress(md_ip,md_port);

            mc = new MongoClient(serverAddress, Arrays.asList(credential));
        }else{
            ServerAddress serverAddress = new ServerAddress(md_ip,md_port);

            mc = new MongoClient(serverAddress);
        }

        md = mc.getDatabase(md_dbName);

        return md;
    }
}
