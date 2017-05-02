package Java.ELT;

import Java.common.Message;
import Java.common.MppDbConnection;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

/**
 * Created by yanzhou on 2017/4/27.
 */
public class FetchSingleMessage implements Runnable {

    private final boolean f_debug = true;
    private int f_quit = 0;
    private MppDbConnection MppDb;
    private Connection f_conn;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Long f_t_fetchSingleMsg_from;
    private Long f_t_fetchSingleMsg_to;

    private String f_msgId;
    private Long f_t_msgTimePointEnd;

    private Logger logger = Logger.getLogger(FetchSingleMessage.class);

    public FetchSingleMessage(String msgId, long msgTimePointEnd) throws SQLException,ClassNotFoundException{

        PropertyConfigurator.configure(FetchSingleMessage.class.getResource("log4j.properties"));

        this.f_msgId = msgId;
        this.f_t_msgTimePointEnd = msgTimePointEnd;

        simpleDateFormat.setCalendar(new GregorianCalendar(new SimpleTimeZone(8,"GMT")));

        MppDb = new MppDbConnection();

        f_conn = MppDb.openMpp();

    }
    public Date parseStr2Date(String date_str){

        Date date;

        try {
            date   = simpleDateFormat.parse(date_str);
        } catch(Exception e) {
            date   = new Date("1970-01-01 00:00:00");
            System.out.println("date string parse failed: " + date_str);
        }
        return date;

    }

    public Message fetchMsgFromMpp(String msgId, Long t_start, Long t_end){

        Message message = new Message();

        PreparedStatement preparedStatement = null;
        try {

            Date dt_start = new Date(t_start);
            Date dt_end = new Date(t_end);

            String sql = " se u_ch_id, m_ch_id, m_root_ch_id, "
                    + "m_root_user_id, m_content, m_url, "
                    + "m_publish_time, m_type  from tp_msg_microblog "
                    + "where m_ch_id = ? "
                    + "and m_publish_time <  unix_timestamp(?) "
                    + "and m_publish_time >= unix_timestamp(?)";
            preparedStatement = f_conn.prepareStatement(sql);

            preparedStatement.setQueryTimeout(60);
            preparedStatement.setString(1,msgId);
            preparedStatement.setString(2,simpleDateFormat.format(dt_end));
            preparedStatement.setString(3,simpleDateFormat.format(dt_start));

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                message.setM_accountId(resultSet.getString(1));
                message.setM_weiboId(resultSet.getString(2));
                message.setM_rootMsgId(resultSet.getString(3));
                message.setM_rootUserId(resultSet.getString(4));
                message.setM_content(resultSet.getString(5));
                message.setM_url(resultSet.getString(6));
                message.setM_postTime(resultSet.getString(7));
                message.setM_type(resultSet.getString(8));
            }

        }catch(SQLException e){
            logger.error("preparedStatement failed :"+ e.getMessage());
            message = null;
        }

        return message;
    }
    public void run(){
        logger.info("FetchSingleMsg process is active now");
        Long t_end = f_t_msgTimePointEnd;
        Long t_start = f_t_msgTimePointEnd-30*24*3600*1000L;

        Long start_fetch = System.currentTimeMillis();
        Message msg = fetchMsgFromMpp(f_msgId,t_start,t_end);
        Long end_fetch = System.currentTimeMillis();

        if(msg!=null){
            logger.info("msg fetch time:"+((end_fetch-start_fetch)/(1000L))+"ms");
            System.out.println("msg:"+msg.toString());

        }else{
            System.out.println("fetchMsgs failed for f_quit set or MPP slow response, wait another 10 seconds ...");
        }

        logger.info("close all db connection:");

        try {
            f_conn.close();
        }catch(SQLException e){
            logger.error("cloase connection failed:"+e.getMessage());
        }

        logger.info("FetchSingleMsg process done.");
    }
}
