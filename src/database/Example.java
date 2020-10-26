package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Classe che modella una transazione letta dalla base di dati.
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
public class Example implements Comparable<Example>, Iterable<Object>{
	/**
	 * Lista di oggetti che modella una transizione da un database.
	 */
	private List<Object> example=new ArrayList<Object>();
	/**
	 * Aggiunge l'elemento specificato agli esempi
	 * @param o : Oggetto aggiunto
	 */
	public void add(Object o){
		example.add(o);
	}

	/**
	 * Restituisce l'oggetto in posizione i sull'esempio.
	 * @param i :Indice dell'oggetto da restituire
	 * @return restituisce l'oggetto nella posizione i dall'esempio
	 */
	public Object get(int i){
		return example.get(i);
	}
	/**
	 * @param ex è l'esempio da confrontare con l'esempio corrente
	 * @return restituisce 0 se l'esempio corrente e 'ex' sono uguali,
	 * altrimenti il metodo compareTo() di oggetti nella stessa posizione degli esempio
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compareTo(Example ex) {
		
		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}
	/**
	 * @return Restituisce la stringa concatenata con gli esempi
	 */
	@Override
	public String toString(){
		String str="";
		for(Object o:example)
			str+=o.toString()+ " ";
		return str;
	}

	/**
	 * @return restituisce null
	 */
	@Override
	public Iterator<Object> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
}