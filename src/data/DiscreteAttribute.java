package data;

import java.io.Serializable;
import java.util.Iterator;

import java.util.Set;
import java.util.TreeSet;
/**
 * Estende la classe Attribute e rappresenta un attributo discreto 
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 */
@SuppressWarnings("serial")
public class DiscreteAttribute extends Attribute implements Iterable<String>,Serializable{
	private Set<String> values=new TreeSet<>(); // order by asc
	
	/**
	 * Invoca il costruttore della superclasse e avvalora values con i valori discreti in input.
	 * @param name :nome simbolico dell'attributo
	 * @param index :identificativo numerico dell'attributo
	 * @param values :set di valori discreti
	 * @see Attribute#Attribute(String, int)    
	 */
	public DiscreteAttribute(String name, int index, Set<String> values) {
		super(name,index);
		this.values=values;
	}
	
	/**
	 * Metodo che estituisce la cardinalit� del Set Values
	 * @return Numero di valori discreti dell'attributo.
	 */
	public int getNumberOfDistinctValues(){
		return values.size();
	}

	/**
	 * Restituisce l'iteratore per l'insieme di valori.
	 * @return Un iteratore per l'insieme dei valori.
	 */
	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return values.iterator();
	}
	
	
}
