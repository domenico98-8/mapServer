package database;

/**
 * Modella lo schema di una colonna all'interno di una tabella del database.
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 */
public class Column{
	/**
	 * Nome della colonna.
	 */
	private String name;

	/**
	 * Il tipo identificatore della colonna.
	 */
	private String type;

	/**
	 * Costruttore che avvalora gli attributi name e type.
	 * @param name :Nome della colonna
	 * @param type :Tipo di valore contenuto nella colonna
	 */
	Column(String name,String type){
		this.name=name;
		this.type=type;
	}


	/**
	 * Restituisce il nome della colonna.
	 * @return Nome della colonna
	 */
	public String getColumnName(){
		return name;
	}


	/**
	 * Verifica se la tabella contiene un valore numerico
	 * @return True se la colonna contiene un valore numerico,
	 * False altrimenti
	 */
	public boolean isNumber(){
		return type.equals("number");
	}

	/**
	 * Restituisce una stringa contenente il nome della tabella e il tipo di
	 * valore contenuto in essa.
	 */
	public String toString(){
		return name+":"+type;
	}
}