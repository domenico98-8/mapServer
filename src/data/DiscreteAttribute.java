package data;

import java.io.Serializable;
import java.util.Iterator;

import java.util.Set;
import java.util.TreeSet;
/**
 * Estende la classe Attribute e rappresenta un attributo discreto 
 */
@SuppressWarnings("serial")
public class DiscreteAttribute extends Attribute implements Iterable<String>,Serializable{
	private Set<String> values=new TreeSet<>(); // order by asc
	
	/**
	 * Invoca il costruttore della superclasse e avvalora values con i valori discreti in input.
	 * @param name :nome simbolico dell'attributo
	 * @param index :identificativo numerico dell'attributo
	 * @param values :set di valori discreti
	 */
	public DiscreteAttribute(String name, int index, Set<String> values) {
		super(name,index);
		this.values=values;
	}
	
	/**
	 * Metodo che estituisce la cardinalità del Set Values
	 * @return values.size()
	 */
	public int getNumberOfDistinctValues(){
		return values.size();
	}

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return values.iterator();
	}
	
	
}
