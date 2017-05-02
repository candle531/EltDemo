package Java.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yanzhou on 2017/4/26.
 */
public class Config {

    private String ci_mppConnString;
    private String ci_mppUser;
    private String ci_mppPassword;

    private String ci_remoteMongoIp;
    private String ci_remoteMongoPort;
    private String ci_remoteMongoUser;
    private String ci_remoteMongoPass;
    private String ci_remoteMongoDbName;

    private String ci_localMongoIp;
    private String ci_localMongoPort;
    private String ci_localMongoUser;
    private String ci_localMongoPass;
    private String ci_localMongoDbName;

    private String ci_localMysqlPort;
    private String ci_localMysqlUser;
    private String ci_localMysqlPass;
    private String ci_localMysqlDbName;

    private boolean ci_useOnlyBlackListKeywords = true;

    public String getCi_mppConnString(){
        return ci_mppConnString;
    }

    public String getCi_mppUser(){
        return this.ci_mppUser;
    }

    public String getCi_mppPassword(){
        return this.ci_mppPassword;
    }

    public String getCi_remoteMongoIp(){

        return this.ci_remoteMongoIp;
    }

    public String getCi_remoteMongoPort(){

        return this.ci_remoteMongoPort;
    }

    public String getCi_remoteMongoUser(){

        return this.ci_remoteMongoUser;
    }

    public String getCi_remoteMongoPass(){

        return this.ci_remoteMongoPass;
    }

    public String getCi_remoteMongoDbName(){

        return this.ci_remoteMongoDbName;
    }

    public String getCi_localMongoIp(){

        return this.ci_localMongoIp;
    }

    public String getCi_localMongoPort(){

        return this.ci_localMongoPort;
    }

    public String getCi_localMongoUser(){

        return this.ci_localMongoUser;
    }

    public String getCi_localMongoPass(){

        return this.ci_localMongoPass;
    }

    public String getCi_localMongoDbName(){

        return this.ci_localMongoDbName;
    }

    public String getCi_localMysqlPort(){

        return this.ci_localMysqlPort;
    }

    public String getCi_localMysqlUser(){

        return this.ci_localMysqlUser;
    }

    public String getCi_localMysqlPass(){

        return this.ci_localMysqlPass;
    }

    public String getCi_localMysqlDbName(){

        return this.ci_localMysqlDbName;
    }

    public boolean getCi_useOnlyBlackListKeywords(){

        return this.ci_useOnlyBlackListKeywords;
    }

    private boolean prop2Config(Properties prop, Config confi){

        try{
            confi.ci_mppConnString = prop.getProperty("mppConnString");
            confi.ci_mppUser = prop.getProperty("mppUser");
            confi.ci_mppPassword = prop.getProperty("mppPassword");

            confi.ci_remoteMongoIp = prop.getProperty("remoteMongoIp");
            confi.ci_remoteMongoPort = prop.getProperty("remoteMongoPort");
            confi.ci_remoteMongoUser = prop.getProperty("remoteMongoUser");
            confi.ci_remoteMongoPass = prop.getProperty("remoteMongoPass");
            confi.ci_remoteMongoDbName = prop.getProperty("remoteMongoDbName");

            confi.ci_localMongoIp = prop.getProperty("localMongoIp");
            confi.ci_localMongoPort = prop.getProperty("localMongoPort");
            confi.ci_localMongoUser = prop.getProperty("localMongoUser");
            confi.ci_localMongoDbName = prop.getProperty("localMongoDbName");

            confi.ci_localMysqlPort = prop.getProperty("localMysqlPort");
            confi.ci_localMysqlUser = prop.getProperty("localMysqlUser");
            confi.ci_localMysqlPass = prop.getProperty("localMysqlPass");
            confi.ci_localMysqlDbName = prop.getProperty("localMysqlDbName");

            String useOnlyBlackListKeywords = prop.getProperty("userOnlyBlackListKeywords");

            if(useOnlyBlackListKeywords!=null&&useOnlyBlackListKeywords.equals("true")){

                confi.ci_useOnlyBlackListKeywords = true;
            }else{

                confi.ci_useOnlyBlackListKeywords = false;
            }

        }catch(Exception e){

            return false;
        }

        return true;
    }

    public void load() throws IOException{

        Properties prop = new Properties();
        InputStream inputStream = null;

        inputStream = Config.class.getResourceAsStream("config.properties");

        if(inputStream!=null){
            prop.load(inputStream);
            inputStream.close();
        }

        if(inputStream==null){
            throw new IOException();
        }

        prop2Config(prop,this);

        inputStream.close();
    }

    /*public static void main(String[] args) throws  IOException{
        Config conf = new Config();
        conf.load();
        System.out.println(conf.ci_localMongoDbName);
    }*/
}
