package main.java.infraEstrutura;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
 
public class EstoqueSQLite extends UnicastRemoteObject implements IEstoque {
    
    public EstoqueSQLite() throws RemoteException {
        Connection c = null;
        Statement stmt = null;
 
        try {
        	Class.forName("org.sqlite.JDBC");
        	c = DBUtil.connection(true);
            
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS ESTOQUE " +
                    "(PRODUTO TEXT PRIMARY KEY NOT NULL," +
                    " QTD INT NOT NULL)";
 
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    private Connection getConnection(boolean autoCommit) throws ClassNotFoundException, SQLException {
    	return DBUtil.connection(autoCommit);
    }
 
    public String add(String produto) {
        int qtd =0;
        boolean response = false;
    	try {
    		Connection c = this.getConnection(false);
            if(DBUtil.exists(c, produto)) {
                qtd = DBUtil.getQtd(c, produto);
                response = DBUtil.update(c, produto, qtd + 1);
            } else {
            	response = DBUtil.insert(c, produto);
            }
            qtd = DBUtil.getQtd(c, produto);
            c.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(response) {
    		return "Add " + produto + ", qtd = " + qtd;
    	} else {
    		return "Add " + produto + " Failed.";
    	}
    }
    
	@Override
	public String remove(String produto) throws RemoteException {
		boolean response = false;
		int qtd = 0;
		try {
			Connection c = this.getConnection(false);
            if(DBUtil.exists(c, produto) && DBUtil.getQtd(c, produto) > 0) {
                qtd = DBUtil.getQtd(c, produto);
                response = DBUtil.update(c, produto, qtd - 1);
            } else {
                return "Produto " + produto + " n�o est� cadastrado";
            }
            c.close();
       } catch (Exception e) {
            // TODO: handle exception
        	e.printStackTrace();
        }
		if (response) {
			return "Produto " + produto + ", qtd = "+ (qtd-1);
		} else {
			return "Remo��o de " + produto + " falhou";
		}
		
	}

	@Override
	public String getAll() throws RemoteException {
		try {
			Connection c = this.getConnection(false);
            String list = DBUtil.list(c);
            c.close();
            return list;
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