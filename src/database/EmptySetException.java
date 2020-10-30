package database;
/**
 * Eccezione per gestire la restituzione di un resultset vuoto
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
public class EmptySetException extends Exception{
	/**
	 * costruttore di EmptySetException che richiama il costruttore di Exception
	 * @see Exception#Exception()
	 */
	EmptySetException(){
		super();
	}

	/**
	 * costruttore di EmptySetException con parametro che richiama il costruttore di Exception
	 * @param s : stringa da visualizzare a run-time
	 * @see Exception#Exception(String)
	 */
	EmptySetException(String s){
		super(s);
	}
}
