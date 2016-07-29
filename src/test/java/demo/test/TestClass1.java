package demo.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author mycat
 *
 */
public class TestClass1 {

    public static void main( String args[] ) throws SQLException , ClassNotFoundException, IOException {
        String jdbcdriver="com.mysql.jdbc.Driver";
        String jdbcurl="jdbc:mysql://127.0.0.1:8066/bizexchange?useUnicode=true&characterEncoding=utf-8";
//        String jdbcurl="jdbc:mysql://172.16.18.4:8066/bizexchange?useUnicode=true&characterEncoding=utf-8";
//        String jdbcurl="jdbc:mysql://172.16.18.4:3306/biz_exchange?useUnicode=true&characterEncoding=utf-8";
        
//        String username="qd_fax_app_user";
//        String password="nNWswNnc70QZodSkF59q";
//        
        String username="mycat";
        String password="mycatpassword";
        
        System.out.println("开始连接mysql:"+jdbcurl);
        Class.forName(jdbcdriver);
        Connection connection = DriverManager.getConnection(jdbcurl,username,password);
//        connection.setAutoCommit(false);
        Statement st = connection.createStatement();
        int isolation = connection.getTransactionIsolation();
        //st.executeQuery("SET autocommit=0");
        System.out.println("isolation: " + isolation);
        st.executeQuery("start transaction");
        System.out.println("auto commit: " + connection.getAutoCommit());
        print( "test jdbc " ,  st.executeQuery("select * from assert_bal where id=1 for update"));
        //st.execute("update assert_bal set assetAmount=9000 where id=1");
        
        //st.executeQuery("commit");
        System.out.println("OK......1");

        Connection connection2 = DriverManager.getConnection(jdbcurl,username,password);
        //connection2.setAutoCommit(false);
        Statement st2 = connection2.createStatement();
        int isolation2 = connection2.getTransactionIsolation();
        //st2.executeQuery("SET autocommit=0");
        System.out.println("isolation2: " + isolation2);
        System.out.println("auto commit2: " + connection2.getAutoCommit());
        st2.executeQuery("start transaction");
        print( "test jdbc " ,  st2.executeQuery("select assetAmount from assert_bal where id=1 for update"));
        System.out.println("OK......2");

        // commit connection 1
        
        System.in.read();
    }

         static void print( String name , ResultSet res )
                    throws SQLException {
                    System.out.println( name);
                    ResultSetMetaData meta=res.getMetaData();                       
                    //System.out.println( "\t"+res.getRow()+"条记录");
                    String  str="";
                    for(int i=1;i<=meta.getColumnCount();i++){
                        str+=meta.getColumnName(i)+"   ";
                        //System.out.println( meta.getColumnName(i)+"   ");
                    }
                    System.out.println("\t"+str);
                    str="";
                    while ( res.next() ){
                        for(int i=1;i<=meta.getColumnCount();i++){  
                            str+= res.getString(i)+"   ";       
                            } 
                        System.out.println("\t"+str);
                        str="";
                    }
                }
}
