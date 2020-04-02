package il.ac.shenkar.final_project.calender;
import il.ac.shenkar.final_project.calender.view.IView;

import java.util.Vector;
import java.sql.*;

import static il.ac.shenkar.final_project.calender.log._log;

/**
 * DerbyDb Class
 *@author  Linoy Noah and Ido Kilker
 * in this class we connect to the DerbyDb data base
 */
public class DerbyDB {
    public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String protocol = "jdbc:derby:/CalendarIdoAndLinoyDB;create=true";
    public Connection connection = null;
    public Statement statement = null;
    public ResultSet rs = null;
    public DerbyDB() {

        try {
            connection = null;
            //Instantiating the driver class will indirectly register
            //this driver as an available driver for DriverManager
            Class.forName(driver);
            //Getting a connection by calling getConnection
            connection = DriverManager.getConnection(protocol);
            statement = connection.createStatement();
            synchronized(_log){
                _log.info("connected to db");
            }
            DatabaseMetaData dbmd = connection.getMetaData();
            rs = dbmd.getTables(null, "APP", "calendar".toUpperCase(), null);
            if (!rs.next()) {

                statement.execute("CREATE TABLE calendar(ID int not null generated always as identity, eventName varchar(255),startDate date,endDate date,time double,description varchar(9000) ,PRIMARY KEY ( ID))");
                statement.execute("insert into calendar (eventName, startDate, endDate, time, description) values ('abc', '2020-03-12', '2020-03-12',2.5, 'First Event')");
                statement.execute("insert into calendar (eventName, startDate, endDate, time, description) values ('Sec', '2020-03-15', '2020-03-15',6, 'Second Event')");
                statement.execute("insert into calendar (eventName, startDate, endDate, time, description) values ('Third', '2020-03-16', '2020-03-16',17, 'Third Event')");

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
            }
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * execute sql request
     * @param s sql request
     * @return true on success and false if fail
     */
    public boolean exec(String s) {
        boolean t = false;
        try {
            statement = connection.createStatement();
             statement.execute(s);
            t=true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
            }
            return t;
        }
    }

    /**
     * execute query
     * @param s sql query
     * @return vector of string whit the value of the rows we got as result from the database
     */
    public Vector<String> execQuery(String s) {
        Vector<String> text = new Vector<String>();
        try {

            statement = connection.createStatement();
            rs = statement.executeQuery(s);
            while (rs.next()) {
                String ev =rs.getInt("ID")+","+rs.getString("eventName")+","+rs.getDate("startDate")+","+rs.getDate("endDate")+",";
                double t = rs.getDouble("time");
                if(t%1==0){ev=ev+(int)t+":00";}
                else{ev=ev+(int)t+":30";}
                ev=ev+","+rs.getString("description");
                text.add(ev);
            }
        } catch (Exception e) {
            e.printStackTrace();
            text=null;
        }
         finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
            }
            if (rs != null) try {
                rs.close();
                rs=null;
            } catch (Exception e) {
            }
            return text;
        }
    }


    public static void main(String[] args) {

        DerbyDB db = new DerbyDB();

    }
}


