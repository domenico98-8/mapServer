import java.io.IOException;


import java.io.Serializable;
import java.sql.SQLException;
import database.DatabaseConnectionException;
import database.EmptySetException;
import server.MultiServer;

/**
 * Classe contenente il main del server
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 */
@SuppressWarnings("serial")
public class MainTest implements Serializable{
	private static int port=8080;

	/**
	 * Inizializza la connessione col client
	 * @param args :Argomento del programma
	 * @throws IOException :Eccezione di I/O
	 */
	public static void main(String[] args) throws IOException{
		
		new MultiServer(port);
		
	}

}
