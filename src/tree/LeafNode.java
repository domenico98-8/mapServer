package tree;
import java.io.Serializable;

import data.Data;
/**
 * 
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
public class LeafNode extends Node implements Serializable{
	Double predictedClassValue; // valore dell'attributo di classe espresso nella foglia corrente
	
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
	
	Double getPredictedClassValue() {
		return predictedClassValue;
	}
	
	int getNumberOfChildren() {
		return 0;
	}
	
	public String toString() {
		String value="";
		value="LEAF:"+"class= "+ getPredictedClassValue()+" "+super.toString();
		return value;
	}

}
