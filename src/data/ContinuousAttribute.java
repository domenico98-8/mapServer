package data;

import java.io.Serializable;

/**
 * Estende la classe Attribute e rappresenta un attributo continuo
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 */
@SuppressWarnings("serial")
public class ContinuousAttribute extends Attribute implements Serializable{
	
	/**
	 * Costruttore di ContinuousAttribute che invoca il costruttore della superclasse Attribute
	 * @param name :nome simbolico dell'attributo
	 * @param index :identificativo numerico dell'attributo
	 * @see Attribute#Attribute(String, int)
	 */
	public ContinuousAttribute(String name,int index) {
		super(name,index);
		
	}

}
