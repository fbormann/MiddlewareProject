package main.java.infraEstrutura.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class EstoquePostgres {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//public static final String POSTGRES_URL = "jdbc:postgresql://localhost/postgres";
	public static final String POSTGRES_URL = "jdbc:postgresql://db:5432/postgres";
	public static final String ADMIN = "postgres";
	public static final String PASSWORD = "admin";
	
	Connection c;
	Statement stmt;
	
	public EstoquePostgres() {
		this.c = null;
		this.stmt = null;

		try {
			Class.forName("org.postgresql.Driver");
			
			Properties props = new Properties();
			props.setProperty("user",ADMIN);
			props.setProperty("password",PASSWORD);
			
			this.c = DriverManager.getConnection(POSTGRES_URL, props);

			this.stmt = this.c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS ESTOQUE " +
					"(PRODUTO TEXT PRIMARY KEY NOT NULL," +
					" QTD INT NOT NULL)"; 

			this.stmt.executeUpdate(sql);
			this.stmt.close();
			this.c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
	private Connection getConnection(boolean autoCommit) throws SQLException {
		return DBUtil.connection(autoCommit, POSTGRES_URL, ADMIN, PASSWORD);
	}

	public String add(String produto) {
        int qtd =0;
        boolean response = false;
    	try {
    		this.c = this.getConnection(false);
            if(DBUtil.exists(c, produto)) {
                qtd = DBUtil.getQtd(c, produto);
                response = DBUtil.update(c, produto, qtd + 1);
            } else {
            	response = DBUtil.insert(c, produto);
            }
            qtd = DBUtil.getQtd(c, produto);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(response) {
    		return produto + " adicionado, qtd = " + qtd;
    	} else {
    		return "Adicao de " + produto + " falhou.";
    	}
    }
    
	public String remove(String produto) {
		boolean response = false;
		int qtd = 0;		
		try {       
			this.c = this.getConnection(false);
            if(DBUtil.exists(c, produto) && DBUtil.getQtd(c, produto) > 0) {
                qtd = DBUtil.getQtd(c, produto);
                response = DBUtil.update(c, produto, qtd - 1);
            } else {
                return "Produto " + produto + " nao esta cadastrado";
            }
       } catch (Exception e) {
            // TODO: handle exception
        	e.printStackTrace();
        }
		if (response) {
			return "Produto " + produto + ", qtd = "+ (qtd-1);
		} else {
			return "Remocao de " + produto + " falhou";
		}
		
	}

	public String getAll() {
		try {
			this.c = this.getConnection(false);
            return DBUtil.list(c);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
