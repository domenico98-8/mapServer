package database;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Classe che modella l'accesso alla base di dati
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
public class DbAccess {
	/**
	 * Nome del driver
	 */
	private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	/**
	 * Nome dei DBMS
	 */
	private final String DBMS = "jdbc:mysql";
	/**
	 * Identificativo del server del database
	 */
	private String SERVER="localhost";
	/**
	 * Nome del database
	 */
	private String DATABASE = "MapDB";
	/**
	 * La porta su cui il DBMS accetta la connesione
	 */
	private final String PORT="3306";
	/**
	 * Nome dell'utente
	 */
	private String USER_ID = "MapUser";
	/**
	 * Password dell'utente
	 */
	private String PASSWORD = "map";
	/**
	 * Gestisce la connessione con il database
	 */
	private Connection conn;

	/**
	 * Impartisce al class loader l’ordine di caricare il driver mysql e inizializza la connessione riferita da conn.
	 * @throws DatabaseConnectionException :Eccezione che si verifica dopo che una connessione all'origine dati non è riuscita
	 * @throws IllegalArgumentException :Eccezione che si verifica se className o gli argomenti sono nulli
	 * @throws InvocationTargetException :Eccezione generata da un metodo o da un costruttore richiamato
	 * @throws NoSuchMethodException : Eccezione generata quando non è possibile trovare un metodo particolare.
	 * @throws SecurityException :Eccezione invocata per indicare una violazione della sicurezza
	 */
	
	public void initConnection() throws DatabaseConnectionException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		try {
			Class.forName(DRIVER_CLASS_NAME).getDeclaredConstructor().newInstance();
		} catch(ClassNotFoundException e) {
			System.out.println("[!] Driver not found: " + e.getMessage());
			throw new DatabaseConnectionException();
		} catch(InstantiationException e){
			System.out.println("[!] Error during the instantiation : " + e.getMessage());
			throw new DatabaseConnectionException();
		} catch(IllegalAccessException e){
			System.out.println("[!] Cannot access the driver : " + e.getMessage());
			throw new DatabaseConnectionException();
		}
		String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
					+ "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
			
		System.out.println("Connection's String: " + connectionString);
			
			
		try {			
			conn = DriverManager.getConnection(connectionString);
		} catch(SQLException e) {
			System.out.println("[!] SQLException: " + e.getMessage());
			System.out.println("[!] SQLState: " + e.getSQLState());
			System.out.println("[!] VendorError: " + e.getErrorCode());
			throw new DatabaseConnectionException();
		}
	}
	/**
	 * Tenta di stabilire una connessione all'URL del database specificato.
	 * @return restituisce l'attuale connessione a MySql
	 */
	Connection getConnection() {
		return conn;
	}

	/**
	 * Chiude la connessione con il database
	 * @throws SQLException :Eccezione che fornisce informazioni su un errore di accesso al database
	 */
	public void closeConnection() throws SQLException {
		conn.close();
	}

}
