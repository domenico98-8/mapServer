import java.io.IOException;


import java.io.Serializable;
import java.sql.SQLException;
import database.DatabaseConnectionException;
import database.EmptySetException;
import server.MultiServer;


@SuppressWarnings("serial")
public class MainTest implements Serializable{
	//ciao ciao ciao
	private static int port=8080;
	/**
	 * @param args
	 * @throws DatabaseConnectionException 
	 * @throws EmptySetException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException{
		
		new MultiServer(port);
		
	}

}