package tree;
import java.io.Serializable;

import data.Data;
/**
 * estende la classe Node e modella l'entità nodo foglia
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
public class LeafNode extends Node implements Serializable{
	/**
	 * Valore dell'attributo di classe espresso nella foglia corrente
	 */
	Double predictedClassValue;
	/**
	 * istanzia un oggetto invocando il costruttore della superclasse e avvalora l'attributo predictedClassValue
	 * @param trainingSet :training set complessivo,
	 * @param beginExampleIndex :indice inizio del sotto-insieme di training
	 * @param endExampleIndex :indice finale del sotto-insieme di training
	 */
	LeafNode(Data trainingSet, int beginExampleIndex, int endExampleIndex){
		super(trainingSet,beginExampleIndex,endExampleIndex);
		double som=0;
		double med=0;
		for(int i=beginExampleIndex;i<=endExampleIndex;i++) {
			som+=trainingSet.getClassValue(i);
		}
		int range=(endExampleIndex-beginExampleIndex)+1;
		med=som/range;
		predictedClassValue=med;
	}
	/**
	 * restituisce il valore del membro predictedClassValue
	 * @return redictedClassValue :valore dell'attributo di classe espresso nella foglia corrente
	 */
	Double getPredictedClassValue() {
		return predictedClassValue;
	}
	/**
	 * restituisce il numero di split originanti dal nodo foglia, ovvero 0.
	 */
	int getNumberOfChildren() {
		return 0;
	}
	/**
	 * invoca il metodo della superclasse assegnando anche il valore di classe della foglia.
	 */
	public String toString() {
		String value="";
		value="LEAF:"+"class= "+ getPredictedClassValue()+" "+super.toString();
		return value;
	}

}
