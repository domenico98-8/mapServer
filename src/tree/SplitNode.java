package tree;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import data.Attribute;

import data.Data;
import data.MapSplitEmptyException;
import server.UnknownValueException;
/**
 * Classe astratta che modella l'astrazione dell'entità nodo di split che estende la superclasse Node
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
abstract class SplitNode extends Node implements Comparable<SplitNode>,Serializable{
	/**
	 *  Classe che aggrega tutte le informazioni riguardanti un nodo di split
	 */
	class SplitInfo implements Serializable{
		Object splitValue; //definisce uno split
		int beginIndex;	//indice inizio split
		int endIndex;	//indice fine split
		private int numberChild;//numero di figli originati da tale nodo
		String comparator="=";
		/**
		 * Costruttore che avvalora gli attributi di classe per split a valori discreti
		 * @param splitValue :valore di tipo Object (di un attributo indipendente) che definisce uno split
		 * @param beginIndex :indice inizio split
		 * @param endIndex :indice fine split
		 * @param numberChild :numero di split (nodi figli) originanti dal nodo corrente
		 */
		SplitInfo(Object splitValue,int beginIndex,int endIndex,int numberChild){
			this.splitValue=splitValue;
			this.beginIndex=beginIndex;
			this.endIndex=endIndex;
			this.numberChild=numberChild;
		}
		/**
		 * Costruttore che avvalora gli attributi di classe per generici split
		 * @param splitValue :valore di tipo Object (di un attributo indipendente) che definisce uno split
		 * @param beginIndex :indice inizio split
		 * @param endIndex :indice fine split
		 * @param numberChild :numero di split (nodi figli) originanti dal nodo corrente
		 * @param comparator :operatore matematico che definisce il test nel nodo corrente
		 */
		SplitInfo(Object splitValue,int beginIndex,int endIndex,int numberChild, String comparator){
			this.splitValue=splitValue;
			this.beginIndex=beginIndex;
			this.endIndex=endIndex;
			this.numberChild=numberChild;
			this.comparator=comparator;
		}
		/**
		 * restituisce il valore dell indice iniziale
		 * @return beginIndex :indice iniziale
		 */
		int getBeginindex(){
			return beginIndex;			
		}
		/**
		 * restituisce il valore del indice finale
		 * @return endIndex :indice finale
		 */
		int getEndIndex(){
			return endIndex;
		}
		/**
		 * restituisce il valore dello split
		 * @return splitValue :valore split
		 */
		Object getSplitValue(){
			return splitValue;
		}
		/**
		 * concatena in un oggetto String i valori di beginExampleIndex,endExampleIndex, child, splitValue, comparator e restituisce la stringa finale.
		 */
		public String toString(){
			return "child " + numberChild +" split value"+comparator+splitValue + "[Examples:"+beginIndex+"-"+endIndex+"]";
		}
		/**
		 * //restituisce il valore dell'operatore matematico che definisce il test
		 * @return comparator :operatore matematico
		 */
		String getComparator(){
			return comparator;
		}

	}//fine SplitInfo

	/**
	 * attributo indipendente su cui facciamo lo split
	 */
	private Attribute attribute;
	/**
	 * Array di tipo SplitInfo che conterrà i figli dell'attributo
	 */
	List<SplitInfo> mapSplit=new ArrayList<SplitInfo>();
	/**
	 * conterrà la varianza totale e scegliamo la più bassa
	 */
	private double splitVariance;

	/**
	 * Metodo abstract per generare le informazioni necessarie per ciascuno degli split candidati
	 * @param trainingSet :Indice iniziale del sotto-insieme di training
	 * @param beginExampleIndex :Indice iniziale del sotto-insieme di training
	 * @param endExampleIndex :Indice finale del sotto-insieme di training.
	 * @param attribute :Attributo sul quale si definisce lo split
	 * @throws MapSplitEmptyException :Eccezione per gestire un insieme di valori continui uguali
	 */
	abstract void setSplitInfo(Data trainingSet,int beginExampleIndex, int endExampleIndex, Attribute attribute) throws MapSplitEmptyException;
	/**
	 * Metodo abstract per modellare la condizione di test
	 * @param value :Valore dell'attributo che si vuole testare rispetto a tutti gli split
	 * @return //
	 * @throws UnknownValueException :Eccezione per gestire il caso di acqusizione di valore mancante o fuori range di un attributo
	 */
	abstract int testCondition (Object value)throws UnknownValueException;
	/**
	 * Invoca il costruttore della superclasse, ordina i valori dell'attributo di input per gli esempi beginExampleIndex-endExampleIndex e sfrutta questo ordinamento per determinare i possibili split e popolare mapSplit, computa la splitVariance per l'attributo usato nello split sulla base del partizionamento indotto dallo split
	 * @param trainingSet :training set complessivo
	 * @param beginExampleIndex :indice inizio del sotto-insieme di training
	 * @param endExampleIndex :indice fine del sotto-insieme di training
	 * @param attribute :attributo indipendente sul quale si definisce lo split
	 * @throws MapSplitEmptyException :Eccezione per gestire un insieme di valori continui uguali
	 */
	SplitNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) throws MapSplitEmptyException{
			super(trainingSet, beginExampleIndex,endExampleIndex);
			this.attribute=attribute;
			trainingSet.sort(attribute, beginExampleIndex, endExampleIndex); // order by attribute
			setSplitInfo(trainingSet, beginExampleIndex, endExampleIndex, attribute);
						
			//compute variance
			splitVariance=0;
			for(int i=0;i<mapSplit.size();i++){
					double localVariance=new LeafNode(trainingSet, mapSplit.get(i).getBeginindex(),mapSplit.get(i).getEndIndex()).getVariance();
					splitVariance+=(localVariance);
			}
	}
	/**
	 * restituisce l'oggetto per l'attributo usato per lo split
	 * @return attribute :attributo per lo split
	 */
	Attribute getAttribute(){
		return attribute;
	}
	/**
	 * restituisce l'information gain per lo split corrente
	 */
	double getVariance(){
		return splitVariance;
	}
	/**
	 * restituisce il numero dei rami originanti nel nodo corrente
	 */
	int getNumberOfChildren(){
		return mapSplit.size();
	}
	/**
	 * restituisce le informazioni per il ramo in mapSplit[] indicizzato da child.
	 * @param child :indice del ramo
	 * @return mapSplit.get(child) :informazioni sul ramo idicizzato da child
	 */
	SplitInfo getSplitInfo(int child){
		return mapSplit.get(child);
	}

	/**
	 * concatena le informazioni di ciascuno test (attributo, operatore e valore) in una String finale.
	 * @return query :istanza della classe String contenente l'informazione concatenata
	 */
	String formulateQuery(){
		String query = "";
		for(int i=0;i<mapSplit.size();i++)
			query+= (i + ":" + attribute + mapSplit.get(i).getComparator() +mapSplit.get(i).getSplitValue())+"\n";
		return query;
	}
	/**
	 * concatena le informazioni di ciascuno test (attributo, esempi coperti, varianza di Split) in una String finale.
	 */
	public String toString(){
		String v="SPLIT : attribute=" +attribute.getName() +" "+ super.toString()+  " Split Variance: " + getVariance()+ "\n" ;		
		for(int i=0;i<mapSplit.size();i++){
			v+= "\t"+mapSplit.get(i)+"\n";
		}
		
		return v;
	}

	/**
	 * Confrontare i valori di splitVariance dei due nodi e restituire l'esito
	 * @param o :Nodo di split da confrontare con il corrente nodo DiscreteNode
	 * @return 0: valori uguali, -1 gain minore, 1 gain maggiore
	 */
	public int compareTo(SplitNode o) {
		 if(this.getVariance()==o.getVariance())
			 return 0;
		 else if(this.getVariance()<o.getVariance())
			 return -1;
		 else return 1;
					 
	}

}
