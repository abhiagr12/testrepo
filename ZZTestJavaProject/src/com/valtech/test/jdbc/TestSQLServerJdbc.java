package com.valtech.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSQLServerJdbc {

	// JDBC driver name and database URL
	static final String DB_URL = "jdbc:sqlserver://10.156.138.164:1433;responseBuffering=adaptive;loginTimeout=10;databaseName=westconb2b";
	static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  
	//static final String DB_URL = "jdbc:sqlserver://10.156.138.65:1433;responseBuffering=adaptive;loginTimeout=10;databaseName=westconb2b_dte";
	//static final String DB_URL = "jdbc:sqlserver://VDC01HLAP0396\\SQLEXPRESS;responseBuffering=adaptive;loginTimeout=10;databaseName=westconb2b_dte";
	//Database credentials
	static final String USER = "Hybris_Developer";
	static final String PASS = "D3v3l0p3r20!5";
	//static final String USER = "sa";
	//static final String PASS = "welcome1@";
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
			
			System.out.println("success............");
			//"SELECT name FROM sys.tables"
			//
			//stmt.executeUpdate("UPDATE SAPR3.ZOTC1001_LOCK SET LASTUPDATE='"+new Timestamp(System.currentTimeMillis())+"', ISLOCKED='N'");
			//ResultSet rs = stmt.executeQuery("SELECT count(*) FROM SAPR3.ZOTC1001 ;");
			//stmt.executeUpdate("DELETE FROM SAPR3.ZOTC1001_LOCK WHERE SYSTEM='HYBRIS' ");
			ResultSet rs = stmt.executeQuery("SELECT * FROM SAPR3.ZOTC1001_LOCK");
			//ResultSet rs = stmt.executeQuery("SELECT STATUS_AEDAT, STATUS_AENZT FROM SAPR3.ZOTC1001 order by STATUS_AEDAT desc, STATUS_AENZT desc");
			//AND POSNR='000000'
			//ResultSet rs = stmt.executeQuery("SELECT * FROM (SELECT ROW_NUMBER() OVER ( ORDER BY VBELN_ORD, POSNR, ETENR ) AS rowNum, *  FROM SAPR3.ZOTC1001 WHERE  (HD_DES_AEDAT='20160322' AND HD_DES_AENZT>'052022') OR (HD_DES_AEDAT>'20160322')  ) AS pagedResult WHERE rowNum >= 1 AND rowNum < 10 ORDER BY rowNum ;");
			int i=0;
			while(rs.next()){
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				//System.out.println(rs.getString(4));
				//System.out.println(rs.getString(5));
				//System.out.println(rs.getString(6));
				i++;
				if(i==3){
					break;
				}
				//System.out.println(rs.getString("HD_DES_AENZT"));
				//System.out.println(rs.getString("HD_DES_AEDAT"));
				//System.out.println(rs.getString("VBELN_ORD"));
				//System.out.println(rs.getString("POSNR"));
				//System.out.println(rs.getString("ETENR"));
				System.out.println("-------------------");
				
				//System.out.println(rs.getTimestamp(3));
			}
			rs.close();
			//STEP 5: Extract data from result set
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
}
