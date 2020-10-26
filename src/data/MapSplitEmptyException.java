package data;


/**
 * Eccezione per gestire un insieme di valori continui uguali
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
public class MapSplitEmptyException extends Exception {
	/**
	 * costruttore di MapSplitEmptyException che richiama il costruttore di Ecxeption
	 * @see Exception#Exception()
	 */
	public MapSplitEmptyException(){
		super();
	}
	/**
	 * costruttore di MapSplitEmptyException con parametro che richiama il costruttore di Ecxeption
	 * @param e : stringa da visualizzare a run-time
	 * @see Exception#Exception(String)
	 */
	public MapSplitEmptyException(String e){
		super(e);
	}
}
