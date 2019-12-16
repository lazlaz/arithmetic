package com.laz.arithmetic;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import java.util.Iterator;

public class CassandraUtil {
    public static Cluster cluster = null;

    static{
        //cluster = Cluster.builder().addContactPoint("172.18.130.32").withPort(9160).withCredentials("cassandra", "cassandra").build();
        cluster = Cluster.builder().addContactPoint("172.18.130.32").withPort(9042).build();
        if(cluster == null){
            System.out.println("初始化失败。。。");
        }else{
            System.out.println("初始化成功。。。");
        }
    }

    public static Session getSession() {
        Session session = cluster.connect("cqldemo");
        return session;
    }

    public static void closeSession(Session session) {
        if (session != null) {
            session.close();
        }
    }

    public static void main(String args[]){
        CassandraUtil cassandraUtil = new CassandraUtil();
        try {
            cassandraUtil.processRow();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void processRow() throws Exception{


        Session session = null;
        try {
            session = getSession();
            System.out.println(".....");
            ResultSet rs = session.execute("select * from cqldemo.cqlusers");    // (3)
            //if(rs == null){
            //	logBasic("--------------------------------------------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            //}
            //rs.all().get()
            //Row r1 =rs.all().get(0);
            //cluster.close();
            for(Row r : rs){
                //Row r = ri.next();
                String t_user_id = String.valueOf(r.getInt("user_id"));
                String t_email = String.valueOf(r.getString("email"));
                String t_gender = String.valueOf(r.getInt("gender"));
               // String t_idss = String.valueOf(r.getInt("idss"));
                String t_lastlogin = String.valueOf(r.getTimestamp("lastlogin"));
                String t_state = String.valueOf(r.getInt("state"));
                String t_uname = String.valueOf(r.getString("uname"));

                System.out.println(t_user_id + ", " + t_email + ", " + t_gender + ", ");


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }
}
