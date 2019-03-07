package database;
 
import org.apache.commons.dbcp2.BasicDataSource;
 
/**
 * @author ashraf
 *
 */
public class DataSource {
 
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no:3306/davidaan_bookingsystem";
    private static final String DB_USER = "fs_tdt4140_1_gruppe18";
    private static final String DB_PASSWORD = "gruppe18";
    private static final int CONN_POOL_SIZE = 5;
 
    private BasicDataSource bds = new BasicDataSource();
 
    private DataSource() {
        //Set database driver name
        bds.setDriverClassName(DRIVER_CLASS_NAME);
        //Set database url
        bds.setUrl(DB_URL);
        //Set database user
        bds.setUsername(DB_USER);
        //Set database password
        bds.setPassword(DB_PASSWORD);
        //Set the connection pool size
        bds.setInitialSize(CONN_POOL_SIZE);
    }
 
    private static class DataSourceHolder {
        private static final DataSource INSTANCE = new DataSource();
    }
 
    public static DataSource getInstance() {
        return DataSourceHolder.INSTANCE;
    }
 
    public BasicDataSource getBds() {
        return bds;
    }
 
    public void setBds(BasicDataSource bds) {
        this.bds = bds;
    }
}