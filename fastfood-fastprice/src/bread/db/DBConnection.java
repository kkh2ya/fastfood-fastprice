package bread.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB 연결용 클래스.
 * @author 김기홍
 * */
public class DBConnection {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String ID = "hr";
	private static final String PW = "hr";
	
	private DBConnection(){}
	
	static{
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, ID, PW);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
