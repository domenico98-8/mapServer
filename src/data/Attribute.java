package data;

import java.io.Serializable;

/**
 * La classe astratta che modella un generico attributo discreto o continuo.
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 */
@SuppressWarnings("serial")
public abstract class  Attribute implements Serializable{
	
	private String name= new String();
	private int index;
	
	/**
	 * E' il costruttore di classe, inizializza i valori dei membri name, index
	 * 
	 * @param name :nome simbolico dell'attributo
	 * @param index :identificativo numerico dell'attributo
	 */
	public Attribute(String name,int index ) {
		this.name=name;
		this.index=index;
	}
	
	/**
	 * Restituisce il valore nel membro name
	 * 
	 * @return name
	 */
	public String getName(){	//restituisce il nome
		return name;
	}
	
	/**
	 * Restituisce il valore nel membro index
	 * 
	 * @return index
	 */
	public int getIndex(){		//restituisce l'index
			return index;
	}
	
	@Override
	public String toString() {
		String value="";
		value=""+ getName();
		return value;
		
	}
}
