package tree;
import java.io.Serializable;
import java.util.ArrayList;


import data.Attribute;
import data.Data;
import data.DiscreteAttribute;
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
final class DiscreteNode extends SplitNode implements Serializable {
	DiscreteNode(Data trainingSet,int beginExampleIndex, int endExampleIndex, DiscreteAttribute attribute) throws MapSplitEmptyException{
		super(trainingSet, beginExampleIndex, endExampleIndex, attribute);
	}
	
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
	
	int testCondition(Object value)throws UnknownValueException{	//restituisce la posizione di mapSplit il cui valore è =value
		int i=0;
		while(i<=getNumberOfChildren()) {
			if(mapSplit.get(i)==value) {
				return i;
			}
			else i++;
		}
		throw new UnknownValueException("Not found!");
	}

	public String toString() {
		String v="DISCRETE "+ super.toString() ;
		return v;
	}
	
	
	

}
