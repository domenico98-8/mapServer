package tree;
import java.io.Serializable;
import java.util.ArrayList;


import data.Attribute;
import data.Data;
import data.DiscreteAttribute;
import data.MapSplitEmptyException;
import server.UnknownValueException;
/**
 * La classe DiscreteNode implementa la classe SplitNode, rappresenta un nodo corrispondente ad un attributo discreto.
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
final class DiscreteNode extends SplitNode implements Serializable {
	/**
	 * Istanzia un oggetto invocando il costruttore della superclasse con il parametro attribute
	 * @param trainingSet :training set complessivo
	 * @param beginExampleIndex :indice iniziale del sotto-insieme di training
	 * @param endExampleIndex :indice finale del sotto-insieme di training
	 * @param attribute :indipendente sul quale si definisce lo split
	 * @throws MapSplitEmptyException :Eccezione per gestire un insieme di valori continui uguali
	 */
	DiscreteNode(Data trainingSet,int beginExampleIndex, int endExampleIndex, DiscreteAttribute attribute) throws MapSplitEmptyException{
		super(trainingSet, beginExampleIndex, endExampleIndex, attribute);
	}
	/**
	 * istanzia oggetti SplitInfo con ciascuno dei valori discreti dell’attributo relativamente al sotto-insieme di training corrente, quindi popola mapSplit con tali oggetti.
	 * @param trainingSet :training set complessivo
	 * @param beginExampleIndex :indice iniziale del sotto-insieme di training
	 * @param endExampleIndex :indice finale del sotto-insieme di training
	 * @param attribute :indipendente sul quale si definisce lo split
	 */
	void setSplitInfo(Data trainingSet,int beginExampleIndex, int endExampleIndex, Attribute attribute) {
		
		int child = 0;
        int beginSplit = beginExampleIndex;
		ArrayList<SplitInfo> mapSplitCom = new ArrayList<SplitInfo>();//ArrayList di appoggio di tipo SplitInfo
        Object corSplitValue = trainingSet.getExplanatoryValue(beginExampleIndex, attribute.getIndex());
        
        for(int i = beginExampleIndex+1; i<=endExampleIndex; i++){
            if( corSplitValue.equals( trainingSet.getExplanatoryValue(i, attribute.getIndex()) ) == false ){ // determina quando cambia il valore in attribute
            	 mapSplitCom.add(child, new SplitInfo(corSplitValue, beginSplit, i-1, child ));
                corSplitValue = trainingSet.getExplanatoryValue(i, attribute.getIndex());
                beginSplit = i;
                child++;
            }
        }
        mapSplitCom.add(child, new SplitInfo(corSplitValue, beginSplit, endExampleIndex, child));
          
        mapSplit = new ArrayList<SplitInfo>(mapSplitCom.size());
        for (int i=0; i<mapSplitCom.size();i++) {
        	mapSplit.add(i,mapSplitCom.get(i));
        }
	}
	/**
	 * effettua il confronto del valore in input con il valore contenuto in splitValue di ciascuno degli oggetti SplitInfo collezionati in mapSplit e restituisce l'identificativo dello split con cui il test è positivo
	 * @param value :valore discreto dell'attributo che si vuole testare rispetto a tutti gli split
	 * @return i :numero del ramo di split
	 * @throws UnknownValueException :Eccezione per gestire il caso di acqusizione di valore mancante o fuori range di un attributo
	 */
	int testCondition(Object value)throws UnknownValueException{	//restituisce la posizione di mapSplit il cui valore � =value
		int i=0;
		while(i<=getNumberOfChildren()) {
			if(mapSplit.get(i)==value) {
				return i;
			}
			else i++;
		}
		throw new UnknownValueException("Not found!");
	}
	/**
	 * invoca il metodo della superclasse specializzandolo per discreti
	 */
	public String toString() {
		String v="DISCRETE "+ super.toString() ;
		return v;
	}
	
	
	

}
