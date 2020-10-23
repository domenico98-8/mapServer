package tree;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import data.Attribute;

import data.Data;
import data.MapSplitEmptyException;
import server.UnknownValueException;
/**
 * 
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
abstract class SplitNode extends Node implements Comparable<SplitNode>,Serializable{
	// Classe che collezione informazioni descrittive dello split
	class SplitInfo implements Serializable{
		Object splitValue; //definisce uno split
		int beginIndex;	//indice inizio split
		int endIndex;	//indice fine split
		private int numberChild;//numero di figli originati da tale nodo
		String comparator="=";
		SplitInfo(Object splitValue,int beginIndex,int endIndex,int numberChild){
			this.splitValue=splitValue;
			this.beginIndex=beginIndex;
			this.endIndex=endIndex;
			this.numberChild=numberChild;
		}
		SplitInfo(Object splitValue,int beginIndex,int endIndex,int numberChild, String comparator){
			this.splitValue=splitValue;
			this.beginIndex=beginIndex;
			this.endIndex=endIndex;
			this.numberChild=numberChild;
			this.comparator=comparator;
		}
		int getBeginindex(){
			return beginIndex;			
		}
		int getEndIndex(){
			return endIndex;
		}
		Object getSplitValue(){
			return splitValue;
		}
		public String toString(){
			return "child " + numberChild +" split value"+comparator+splitValue + "[Examples:"+beginIndex+"-"+endIndex+"]";
		}
		String getComparator(){
			return comparator;
		}
		 
		
		
	}//fine SplitInfo

	private Attribute attribute;	//attrubuto indipendente su cui facciamo lo split

	List<SplitInfo> mapSplit=new ArrayList<SplitInfo>(); 	//Array di tipo SplitInfo che conterrà i figli dell'attributo
	
	private double splitVariance;	//conterrà la varianza totale e scegliamo la più bassa
		
	abstract void setSplitInfo(Data trainingSet,int beginExampleIndex, int endExampleIndex, Attribute attribute) throws MapSplitEmptyException;
	
	abstract int testCondition (Object value)throws UnknownValueException;
	
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
	
	Attribute getAttribute(){
		return attribute;
	}
	
	double getVariance(){
		return splitVariance;
	}
	
	int getNumberOfChildren(){
		return mapSplit.size();
	}
	
	SplitInfo getSplitInfo(int child){
		return mapSplit.get(child);
	}

	
	String formulateQuery(){
		String query = "";
		for(int i=0;i<mapSplit.size();i++)
			query+= (i + ":" + attribute + mapSplit.get(i).getComparator() +mapSplit.get(i).getSplitValue())+"\n";
		return query;
	}
	
	public String toString(){
		String v="SPLIT : attribute=" +attribute.getName() +" "+ super.toString()+  " Split Variance: " + getVariance()+ "\n" ;		
		for(int i=0;i<mapSplit.size();i++){
			v+= "\t"+mapSplit.get(i)+"\n";
		}
		
		return v;
	}
	public int compareTo(SplitNode o) {
		 if(this.getVariance()==o.getVariance())
			 return 0;
		 else if(this.getVariance()<o.getVariance())
			 return -1;
		 else return 1;
					 
	}

}
