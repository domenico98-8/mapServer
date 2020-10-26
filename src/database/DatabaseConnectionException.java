package database;
/**
 * Eccezione per gestire il fallimento nella connessione al database
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
public class DatabaseConnectionException extends Exception{

	/**
	 * costruttore di DatabaseConnectionException che richiama il costruttore di Ecxeption
	 * @see Exception#Exception()
	 */
	DatabaseConnectionException(){
		super();
	}
	/**
	 * costruttore di DatabaseException con parametro che richiama il costruttore di Ecxeption
	 * @param s : stringa da visualizzare a run-time
	 * @see Exception#Exception(String)
	 */
	DatabaseConnectionException(String s){
		super(s);
	}
}
