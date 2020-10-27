package tree;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import data.Attribute;
import data.ContinuousAttribute;
import data.Data;
import data.MapSplitEmptyException;
import server.UnknownValueException;

/**
 * La classe ContinuousNode implementa la classe SplitNode, rappresenta un nodo corrispondente ad un attributo continuo.
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 */
@SuppressWarnings("serial")
final class ContinuousNode extends SplitNode implements Serializable{
	/**
	 * Istanzia un oggetto invocando il costruttore della superclasse con il parametro attribute
	 * @param trainingSet :training set complessivo
	 * @param beginExampleIndex :indice iniziale del sotto-insieme di training
	 * @param endExampleIndex :indice finale del sotto-insieme di training
	 * @param attribute :indipendente sul quale si definisce lo split
	 * @throws MapSplitEmptyException :Eccezione per gestire un insieme di valori continui uguali
	 */
	public ContinuousNode(Data trainingSet,int beginExampleIndex, int endExampleIndex, ContinuousAttribute attribute) throws MapSplitEmptyException{
		super(trainingSet, beginExampleIndex, endExampleIndex, attribute);
	}

	/**
	 * istanzia oggetti SplitInfo con ciascuno dei valori discreti dell’attributo relativamente al sotto-insieme di training corrente, quindi popola mapSplit con tali oggetti.
	 * @param trainingSet :training set complessivo
	 * @param beginExampleIndex :indice iniziale del sotto-insieme di training
	 * @param endExampleIndex :indice finale del sotto-insieme di training
	 * @param attribute :indipendente sul quale si definisce lo split
	 * @throws MapSplitEmptyException :Eccezione per gestire un insieme di valori continui uguali
	 */
	 void setSplitInfo(Data trainingSet,int beginExampleIndex, int endExampleIndex, Attribute attribute) throws MapSplitEmptyException{
			//Update mapSplit defined in SplitNode -- contiene gli indici del partizionamento
			Double currentSplitValue= (Double)trainingSet.getExplanatoryValue(beginExampleIndex,attribute.getIndex());
			double bestInfoVariance=0;
			List <SplitInfo> bestMapSplit=null;
			
			for(int i=beginExampleIndex+1;i<=endExampleIndex;i++){
				Double value=(Double)trainingSet.getExplanatoryValue(i,attribute.getIndex());
				if(value.doubleValue()!=currentSplitValue.doubleValue()){
				//	System.out.print(currentSplitValue +" var ");
					double localVariance=new LeafNode(trainingSet, beginExampleIndex,i-1).getVariance();
					double candidateSplitVariance=localVariance;
					localVariance=new LeafNode(trainingSet, i,endExampleIndex).getVariance();
					candidateSplitVariance+=localVariance;
					//System.out.println(candidateSplitVariance);
					if(bestMapSplit==null){
						bestMapSplit=new ArrayList<SplitInfo>();
						bestMapSplit.add(new SplitInfo(currentSplitValue, beginExampleIndex, i-1,0,"<="));
						bestMapSplit.add(new SplitInfo(currentSplitValue, i, endExampleIndex,1,">"));
						bestInfoVariance=candidateSplitVariance;
					}
					else{		
												
						if(candidateSplitVariance<bestInfoVariance){
							bestInfoVariance=candidateSplitVariance;
							bestMapSplit.set(0, new SplitInfo(currentSplitValue, beginExampleIndex, i-1,0,"<="));
							bestMapSplit.set(1, new SplitInfo(currentSplitValue, i, endExampleIndex,1,">"));
						}
					}
					currentSplitValue=value;
				}
			}
			mapSplit=bestMapSplit;
			if(mapSplit==null) {
				throw new MapSplitEmptyException("Errore");
			}else {
			
			//rimuovo split inutili (che includono tutti gli esempi nella stessa partizione)
			if((mapSplit.get(1).beginIndex==mapSplit.get(1).getEndIndex())){
				mapSplit.remove(1);
				
				}
			}
			
	 }
	/**
	 * effettua il confronto del valore in input rispetto al valore contenuto in splitValue di ciascuno degli oggetti SplitInfo collezionati in mapSplit e restituisce l'identificativo dello split con cui il test è positivo
	 * @param value :valore discreto dell'attributo che si vuole testare rispetto a tutti gli split
	 * @return i :numero del ramo di split
	 * @throws UnknownValueException :Eccezione per gestire il caso di acqusizione di valore mancante o fuori range di un attributo
	 */
	 int testCondition (Object value)throws UnknownValueException{	//restituisce la posizione di mapSplit il cui valore � =value
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
	 * invoca il metodo della superclasse specializzandolo per continui
	 */
	 public String toString() {
			String v="CONTINUOUS "+ super.toString() ;
			return v;
		}
		
	 
	




}
