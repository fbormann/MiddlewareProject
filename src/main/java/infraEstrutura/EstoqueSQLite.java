package infraEstrutura;

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
 
    public String add(String produto) {
        int qtd =0;
        boolean response = false;
    	try {
            if(DBUtil.exists(produto)) {
                qtd = DBUtil.getQtd(produto);
                response = DBUtil.update(produto, qtd + 1);
            } else {
            	response = DBUtil.insert(produto);
            }
            qtd = DBUtil.getQtd(produto);
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
            if(DBUtil.exists(produto) && DBUtil.getQtd(produto) > 0) {
                qtd = DBUtil.getQtd(produto);
                response = DBUtil.update(produto, qtd - 1);
            } else {
                return "Produto " + produto + " não está cadastrado";
            }
       } catch (Exception e) {
            // TODO: handle exception
        	e.printStackTrace();
        }
		if (response) {
			return "Produto " + produto + ", qtd = "+ (qtd-1);
		} else {
			return "Remoção de " + produto + " falhou";
		}
		
	}

	@Override
	public String getAll() throws RemoteException {
		try {
            return DBUtil.list();
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