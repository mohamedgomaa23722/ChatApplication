package gov.iti.presistance.DataBase;


import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.FileInputStream;
import java.util.Properties;

public class DataSourceFactory {
    /**
     * @return
     */
    public static DataSource getDataSource() {
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        MysqlDataSource mysqlDataSource = null;

        try {
             properties = new Properties();
             properties.load(DataSourceFactory.class.getResourceAsStream("/db.properties"));
            mysqlDataSource = new MysqlDataSource();

            mysqlDataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            mysqlDataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            mysqlDataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
        } catch (Exception e) {
            e.printStackTrace();       
        }
        
        return mysqlDataSource;
    }
}