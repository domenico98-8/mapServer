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
abstract class Node implements Serializable {
static int idNodeCount=0;	//contatore nodi generati nell'albero
private int idNode;//id numero nodo
private int beginExampleIndex;//primo esempio nel trainingSet 
private int endExampleIndex;//ultimo esempio del trainingSet
private double variance; //varianza calcolata in base all'attributo di classe nel sottoinsieme di training del nodo

//metodi
Node(Data trainingSet,int beginExampleIndex,int endExampleIndex){
	this.beginExampleIndex=beginExampleIndex;
	this.endExampleIndex=endExampleIndex;
	idNode=idNodeCount++;
	variance=Varianza(trainingSet,beginExampleIndex,endExampleIndex);
	
}

int getIdNode(){		//restituisce l'idnode
	return idNode;
}

int getBeginExampleIndex() {
	return beginExampleIndex;
}


int getEndExampleIndex() {
	return endExampleIndex;
}

double getVariance() {
	return variance;
}

abstract int getNumberOfChildren();

public String toString() {
	String out;
	out="Node: [Examples"+beginExampleIndex+"-"+""+endExampleIndex+"]"+"Variance:"+variance +"\n";
	return out;
}

double Varianza(Data trainingSet,int beginExampleIndex,int endExampleIndex) {
	this.beginExampleIndex=beginExampleIndex;
	this.endExampleIndex=endExampleIndex;
	double somQuad=0; //conterrà la somma dei quadrati dei valori nel range
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
