package main.java.infraEstrutura.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class DBUtil {
	public static Connection connection(boolean autoCommit, String url, String user, String password) throws SQLException {
		Properties props = new Properties();
		props.setProperty("user",user);
		props.setProperty("password",password);

		Connection c = DriverManager.getConnection(url, props);
		
		c.setAutoCommit(autoCommit);
		return c;
	}

    public static Connection connection(boolean autoCommit) throws SQLException, ClassNotFoundException {
    	Connection c = DriverManager.getConnection("jdbc:sqlite:estoque.db");
    	c.setAutoCommit(autoCommit);
        return c;
    }
   
    public static boolean insert(Connection c, String produto) throws SQLException, ClassNotFoundException {
       //Connection c = connection(true);
    	Statement stmt = c.createStatement();
        String sql = "INSERT INTO ESTOQUE (PRODUTO, QTD) " +
                "VALUES ('" + produto + "', 1);";
        int response = stmt.executeUpdate(sql);
        c.commit();
        stmt.close();
        //c.close();
        return response > 0;
    }
 
    public static boolean update(Connection c, String produto, int qtd) throws SQLException, ClassNotFoundException {
        //Connection c = connection(false);
    	Statement stmt = c.createStatement();
        String sql = "UPDATE ESTOQUE set QTD = " + qtd + " where PRODUTO='"+ produto +"';";
        int response = stmt.executeUpdate(sql);
        c.commit();
        stmt.close();
        //c.close();
        return response > 0;
    }
 
    public static boolean exists(Connection c, String produto) throws SQLException, ClassNotFoundException {
        //Connection c = connection(false);
    	Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ESTOQUE WHERE PRODUTO='"+ produto +"';" );
        boolean response = rs.next();
        //c.close();
        return response;
    }
 
    public static int getQtd(Connection c, String produto) throws SQLException, ClassNotFoundException {
        //Connection c = connection(false);
    	Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT qtd FROM ESTOQUE WHERE PRODUTO='"+ produto +"';" );
        int qtd = rs.next() ? rs.getInt("qtd") : 0;
        rs.close();
        stmt.close();
        //c.close();
        return qtd;
    }
 
    public static String list(Connection c) throws SQLException, ClassNotFoundException {
        //Connection c = connection(false);
    	Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ESTOQUE;" );
        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            String produto = rs.getString("produto");
            int qtd  = rs.getInt("qtd");
 
            sb
            .append("Produto: " + produto)
            .append(" - ")
            .append("Quantidade: " + qtd)
            .append("\n");
        }
        rs.close();
        stmt.close();
        //c.close();
        return sb.toString();
    }
}