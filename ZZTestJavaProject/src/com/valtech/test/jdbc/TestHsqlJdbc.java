package com.valtech.test.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class TestHsqlJdbc {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";  
	static final String DB_URL = "jdbc:hsqldb:file:G:\\work\\testdb\\test;shutdown=true;hsqldb.cache_rows=100000;hsqldb.cache_size=20000;hsqldb.write_delay_millis=1000;hsqldb.inc_backup=true;hsqldb.defrag_limit=2;hsqldb.nio_data_file=false";
	
	//Database credentials
	static final String USER = "dbadmin";
	static final String PASS = "";
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);
			
			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			try{//CACHED MEMORY
				stmt.executeUpdate("CREATE MEMORY TABLE ZOTC1001(MANDT VARCHAR(50) NOT NULL,VBELN_ORD VARCHAR(50) NOT NULL,POSNR VARCHAR(50) NOT NULL,ETENR VARCHAR(50) NOT NULL,VKORG VARCHAR(50),KUNNR VARCHAR(50),STATUS VARCHAR(50) NOT NULL,STAT_DES VARCHAR(50) NOT NULL, STATUS_AEDAT DATE, STATUS_AENZT TIME, STAT_HD_DES VARCHAR(50) NOT NULL, HD_DES_AEDAT DATE, HD_DES_AENZT TIME)");
				stmt.executeUpdate("CREATE MEMORY TABLE ZOTC1001_LOCK(ISLOCKED VARCHAR(1) NOT NULL,SYSTEM VARCHAR(6) NOT NULL,LASTUPDATE DATETIME NOT NULL)");
				stmt.executeUpdate("INSERT INTO ZOTC1001_LOCK VALUES ('N', 'SAP', '2016-01-27 10:34:54');");
				String insert = "INSERT INTO ZOTC1001 VALUES('300','528606','0','0','','','74','Invoiced','2016-01-27','10:34:54','Invoiced','2016-01-27','10:34:54'),"+
						"('300','528606','10','0','','','74','Invoiced','2016-01-27','10:34:54','Invoiced','2016-01-27','10:34:54'),"+
						"('300','528606','10','2','','','74','Invoiced','2016-01-27','10:34:54','Invoiced','2016-01-27','10:34:54'),"+
						"('300','531848','0','0','','','74','Invoiced','2016-01-27','10:34:54','Invoiced','2016-01-27','10:34:54'),"+
						"('300','531848','10','0','','','74','Invoiced','2016-01-27','10:34:54','Invoiced','2016-01-27','10:34:54'),"+
						"('300','531848','10','1','','','74','Invoiced','2016-01-27','10:34:54','Invoiced','2016-01-27','10:34:54'),"+
						"('300','531848','20','0','','','74','Invoiced','2016-01-27','10:34:54','Invoiced','2016-01-27','10:34:54'),"+
						"('300','531848','20','1','','','74','Invoiced','2016-01-27','10:34:54','Invoiced','2016-01-27','10:34:54'),"+
						"('300','531848','30','0','','','0','Inactive','2016-01-27','10:34:54','Inactive','2016-01-27','10:34:54'),"+
						"('300','531848','30','1','','','0','Inactive','2016-01-27','10:34:54','Inactive','2016-01-27','10:34:55');";
				stmt.executeUpdate(insert);
			}catch(SQLException e){
				//e.printStackTrace();
			}
			
			String sql = "SELECT *  FROM ZOTC1001 WHERE POSNR='0' order by HD_DES_AEDAT, HD_DES_AENZT ";
			//String sql1 = "SELECT IS_LOCKED FROM LOCK_STATUS WHERE LOCK_BY='HYBRIS'";
			ResultSet rs = stmt.executeQuery(sql);
			//stmt.executeUpdate("UPDATE LOCK_STATUS SET LOCK_BY='SAP', IS_LOCKED='FALSE',LOECKED_ON='"+new Timestamp(System.currentTimeMillis())+"';");
			System.out.println("hello");
			//STEP 5: Extract data from result set
			while(rs.next()){
				System.out.println(rs.getDate("STATUS_AEDAT"));
				System.out.println(rs.getTime("STATUS_AENZT"));
				System.out.println(getUtilDate(rs.getDate("STATUS_AEDAT"), rs.getTime("STATUS_AENZT")));
				break;
			}
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
		System.out.println("Goodbye!");
	}//end main
	
	private static java.util.Date getUtilDate(final Date sqlDate, final Time sqlTime)
	{
		if (sqlDate != null && sqlTime != null)
		{
			final long tzoffset = -(Time.valueOf("00:00:00").getTime());
			final java.util.Date date2 = new java.util.Date(sqlDate.getTime() + sqlTime.getTime() + tzoffset);
			return date2;
		}
		return null;
	}
}