package main.java.aplicacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Authentication {
	//public static final String POSTGRES_URL = "jdbc:postgresql://localhost/postgres";
	public static final String POSTGRES_URL = "jdbc:postgresql://db:5432/postgres";
	public static final String ADMIN = "postgres";
	public static final String PASSWORD = "admin";
	
	Connection c;
	Statement stmt;
	
	public Authentication() {
		this.c = null;
		this.stmt = null;

		try {
			Class.forName("org.postgresql.Driver");
			
			this.c = this.getConnection();
			this.stmt = this.c.createStatement();
			
			String ext = "CREATE EXTENSION IF NOT EXISTS pgcrypto;";
			this.stmt.execute(ext);
			
			String sql = "CREATE TABLE IF NOT EXISTS USERS " +
					"(LOGIN TEXT PRIMARY KEY NOT NULL," +
					"PASSWORD TEXT NOT NULL);"; 
			this.stmt.executeUpdate(sql);
			
			this.stmt.close();
			this.c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
	private Connection getConnection() throws SQLException {
		Properties props = new Properties();
		props.setProperty("user",ADMIN);
		props.setProperty("password",PASSWORD);
		
		return DriverManager.getConnection(POSTGRES_URL, props);
	}
	
	public boolean exists(String login) throws SQLException {
		this.c = this.getConnection();
		this.stmt = this.c.createStatement();

		String sql = "SELECT * FROM USERS WHERE login = lower('"+ login +"');";
		ResultSet rs = this.stmt.executeQuery(sql);
		boolean result = rs.next();
		
		stmt.close();
		c.close();
		return result;
	}
	
	public boolean signup(String login, String password) throws SQLException {
		this.c = this.getConnection();
		this.stmt = this.c.createStatement();

		String sql = "INSERT INTO USERS (login, password) VALUES\n" + 
				"  (lower('"+ login +"'), crypt('"+ password +"', gen_salt('bf', 8)));";
		int response = this.stmt.executeUpdate(sql);
		
		stmt.close();
		c.close();	
		return response > 0;
	}
	
	public boolean signin(String login, String password) throws SQLException {
		this.c = this.getConnection();
		this.stmt = this.c.createStatement();

		String sql = "SELECT * FROM USERS WHERE\n" + 
				"  login = lower('"+ login +"') AND password=crypt('"+ password +
				"', password);";
		ResultSet rs = this.stmt.executeQuery(sql);
		boolean result = rs.next();
		
		stmt.close();
		c.close();
		return result;
	}
	
	public static void main(String[] args) {
		Authentication auth = new Authentication();
		
		try {
			//auth.signup("root", "password");
			System.out.println(auth.signin("admin", "admin"));
			System.out.println(auth.signin("root", "password"));
			
			System.out.println(auth.exists("admin"));
			System.out.println(auth.exists("root"));
			System.out.println(auth.exists("login"));			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
