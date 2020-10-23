package server;
/**
 * 
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
public class UnknownValueException extends Exception {
	public UnknownValueException(){ }
	public UnknownValueException(String x){
		super(x);
	}
}
