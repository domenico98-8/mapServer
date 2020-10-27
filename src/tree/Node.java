package tree;
import java.io.Serializable;

import data.Data;
/**
 * classe astratta per modellare l'astrazione dell'entità nodo (fogliare o intermedio) dell'albero di decisione.
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 */
@SuppressWarnings("serial")
abstract class Node implements Serializable {
	/**
	 * contatore nodi generati nell'albero
	 */
	static int idNodeCount=0;
	/**
	 * id numero nodo
	 */
	private int idNode;
	/**
	 * primo esempio nel trainingSet
	 */
	private int beginExampleIndex;
	/**
	 * ultimo esempio del trainingSet
	 */
	private int endExampleIndex;
	/**
	 * varianza calcolata in base all'attributo di classe nel sottoinsieme di training del nodo
	 */
	private double variance;
	//metodi
	/**
	 * Avvalora gli attributi primitivi di classe e la varianza che viene calcolata rispetto all'attributo da predire nel sotto-insieme di training
	 * @param trainingSet :oggetto di classe Data contenente il training set completo
	 * @param beginExampleIndex :indice inizio del sotto-insieme di training
	 * @param endExampleIndex :indice fine del sotto-insieme di training
	 */
Node(Data trainingSet,int beginExampleIndex,int endExampleIndex){
	this.beginExampleIndex=beginExampleIndex;
	this.endExampleIndex=endExampleIndex;
	idNode=idNodeCount++;
	variance=Varianza(trainingSet,beginExampleIndex,endExampleIndex);
	
}
	/**
	 * Restituisce il valore del membro idNode
	 * @return idNode :identificativo numerico del nodo
	 */
int getIdNode(){		//restituisce l'idnode
	return idNode;
}
	/**
	 * Restituisce il valore del membro beginExampleIndex
	 * @return beginExampleIndex :indice del primo esempio del sotto-insieme rispetto al training set complessivo
	 */
int getBeginExampleIndex() {
	return beginExampleIndex;
}

	/**
	 * Restituisce il valore del membro endExampleIndex
	 * @return beginExampleIndex :indice del ultimo esempio del sotto-insieme rispetto al training set complessivo
	 */
int getEndExampleIndex() {
	return endExampleIndex;
}
	/**
	 * Restituisce il valore della variance
	 * @return variance :valore dell’attributo da predire rispetto al nodo corrente
	 */
double getVariance() {
	return variance;
}
	/**
	 * Metodo astratto che restituisce il numeri di figli
	 * @return Numero di figli
	 */
abstract int getNumberOfChildren();
	/**
	 * Concatena in un oggetto String i valori di beginExampleIndex,endExampleIndex, variance e restituisce la stringa finale.
	 * @return out :stringa finale
	 */
public String toString() {
	String out;
	out="Node: [Examples"+beginExampleIndex+"-"+""+endExampleIndex+"]"+"Variance:"+variance +"\n";
	return out;
}
	/**
	 * calcola la varianza in un intervallo del trainingSet
	 * @param trainingSet :oggetto di classe Data contenente il training set completo
	 * @param beginExampleIndex :indice inizio del sotto-insieme di training
	 * @param endExampleIndex :indice fine del sotto-insieme di training
	 * @return variance :varianza del sotto-insieme di training
	 */
double Varianza(Data trainingSet,int beginExampleIndex,int endExampleIndex) {
	this.beginExampleIndex=beginExampleIndex;
	this.endExampleIndex=endExampleIndex;
	double somQuad=0; //conterr� la somma dei quadrati dei valori nel range
	double som=0;
	int range=0;
	for(int i=beginExampleIndex; i<=endExampleIndex; i++) {
		somQuad=somQuad+Math.pow(trainingSet.getClassValue(i), 2);
		som=som+(double)trainingSet.getClassValue(i);
	}
	range=(endExampleIndex-beginExampleIndex)+1;
	variance=somQuad-((Math.pow(som, 2))/range);
	return variance;
}
}
