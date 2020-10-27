package tree;
/**
 * Eccezione per gestire il caso di acqusizione di un file errato
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 */
public class FileErratoExcpetion extends Exception{
    /**
     * costruttore di FileErratoExcpetion che richiama il costruttore di Ecxeption
     * @see Exception#Exception()
     */
    FileErratoExcpetion(){

        }
    /**
     * costruttore di FileErratoExcpetion con parametro che richiama il costruttore di Ecxeption
     * @param e : stringa da visualizzare a run-time
     * @see Exception#Exception(String)
     */
        FileErratoExcpetion(String e){
            super(e);
        }
}
