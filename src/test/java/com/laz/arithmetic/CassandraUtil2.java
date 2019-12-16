package com.laz.arithmetic;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import java.util.Iterator;
import java.util.List;

public class CassandraUtil2 {
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
    	CassandraUtil2 cassandraUtil = new CassandraUtil2();
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
            List lists = rs.all();
            int rsIndex = lists.size();
            for (int i=0 ; i < rsIndex ; i++){
                //for(Row r1 : rs.all()){
                Row r1 = (Row)lists.get(0);
                String t_user_id = String.valueOf(r1.getInt("user_id"));
                String t_email = String.valueOf(r1.getString("email"));
                System.out.println(t_user_id + ", " + t_email  );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }
}
