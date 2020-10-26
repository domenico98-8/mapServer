package server;
/**
 * Eccezione per gestire il caso di acqusizione di valore mancante o fuori range di un attributo
 * di un nuovo esempio da classificare.
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 */
@SuppressWarnings("serial")
public class UnknownValueException extends Exception {
	/**
	 * costruttore di UnknownValueException che richiama il costruttore di Ecxeption
	 * @see Exception#Exception()
	 */
	public UnknownValueException(){ }
	/**
	 * costruttore di UnknownValueException con parametro che richiama il costruttore di Ecxeption
	 * @param x : stringa da visualizzare a run-time
	 * @see Exception#Exception(String)
	 */
	public UnknownValueException(String x){
		super(x);
	}
}
