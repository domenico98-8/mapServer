package data;

/**
 * Eccezione per gestire il caso di acquisizione errata del training set.
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
public class TrainingDataException extends Exception{
	/**
	 * costruttore di TrainingDataException che richiama il costruttore di Ecxeption
	 * @see Exception#Exception()
	 */
	TrainingDataException(){
		super();
		}
	
	/**
	 * costruttore di TrainingDataException con parametro che richiama il costruttore di Ecxeption
	 * @param x : stringa da visualizzare a run-time
	 * @see Exception#Exception(String)
	 */
	TrainingDataException(String x){
		super(x);
		}

}
