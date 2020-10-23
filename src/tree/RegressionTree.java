package tree;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.TreeSet;


import data.*;
import server.UnknownValueException;
import utility.Keyboard;


/**
 * 
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
public class RegressionTree extends Keyboard implements Serializable{
		Node root; // radice del sotto-albero corrente
		RegressionTree childTree[]; // array di sotto-alberi originanti nel nodo root:vi � un elemento nell�array per ogni figlio del nodo
		
		public RegressionTree(){
			
		}
		
		public RegressionTree(Data trainingSet) throws MapSplitEmptyException{
			
			learnTree(trainingSet,0,trainingSet.getNumberOfExamples()-1,trainingSet.getNumberOfExamples()*10/100);
		}
		
		private void learnTree(Data trainingSet,int begin, int end,int numberOfExamplesPerLeaf){
			if( isLeaf(trainingSet, begin, end, numberOfExamplesPerLeaf)){
				//determina la classe che compare pi� frequentemente nella partizione corrente
				root= new LeafNode(trainingSet,begin,end);
			}
			else //split node
			{
				try {
					root=determineBestSplitNode(trainingSet, begin, end);
				
			
				if(root.getNumberOfChildren()>1){
					childTree=new RegressionTree[root.getNumberOfChildren()];
					for(int i=0;i<root.getNumberOfChildren();i++){
						childTree[i]=new RegressionTree();
						childTree[i].learnTree(trainingSet, ((SplitNode)root).getSplitInfo(i).beginIndex, ((SplitNode)root).getSplitInfo(i).endIndex, numberOfExamplesPerLeaf);
					}
				}
				else
					root=new LeafNode(trainingSet,begin,end);
				} catch (MapSplitEmptyException e) {
					root= new LeafNode(trainingSet,begin,end);
				}
				
			}
		}
			
		private boolean isLeaf(Data trainingSet,int begin, int end,int numberOfExamplesPerLeaf) {
			int numberExample=(end-begin)-1;
			
			if(numberExample<= numberOfExamplesPerLeaf) {
			 return true;
			}
			else 
				return false;
		}
		
		private SplitNode determineBestSplitNode(Data trainingSet,int begin,int end) throws MapSplitEmptyException {
			TreeSet<SplitNode> ts=new TreeSet<SplitNode>();
			for(int i=0;i<trainingSet.getNumberOfExplanatoryAttributes();i++) {
				Attribute a=trainingSet.getExplanatoryAttribute(i);
				if(a instanceof DiscreteAttribute)
						ts.add(new DiscreteNode(trainingSet,begin,end,(DiscreteAttribute)trainingSet.getExplanatoryAttribute(i)));
				else
						ts.add(new ContinuousNode(trainingSet,begin,end,(ContinuousAttribute)trainingSet.getExplanatoryAttribute(i)));

			}
			trainingSet.sort(ts.first().getAttribute(),begin,end);
			return ts.first();
		}
		
		public void salva(String nomeFile) throws FileNotFoundException, IOException{
			try {
				FileOutputStream File=new FileOutputStream(nomeFile);
				ObjectOutputStream out=new ObjectOutputStream(File);
				out.writeObject(this);
				out.close();
			}catch(FileNotFoundException e){
				System.out.println(e.toString());
			}
			catch(IOException f){
				System.out.println(f.toString());
			}
		}
		
		public static RegressionTree carica(String nomeFile) throws FileNotFoundException, IOException, ClassNotFoundException, FileErratoExcpetion {
			try {
				FileInputStream File=new FileInputStream(nomeFile);
				ObjectInputStream in=new ObjectInputStream(File);
				RegressionTree childTree=(RegressionTree)in.readObject();
				in.close();
				return childTree;
			}catch(FileNotFoundException e) {
				System.out.println(e.toString());
				throw new FileErratoExcpetion();
			}catch(IOException f) {
				System.out.println(f.toString());
			}catch(ClassNotFoundException c) {
				System.out.println(c.toString());
			}
			return null;
			
		}
		
		
		public String toString(){
			String tree=root.toString()+"\n";
			
			if( root instanceof LeafNode){
			
			}
			else //split node
			{
				for(int i=0;i<childTree.length;i++)
					tree +=childTree[i];
			}
			return tree;
		}
		
		
		
		public Double predictClass(ObjectInputStream in, ObjectOutputStream out)throws UnknownValueException, IOException {
			if(root instanceof LeafNode) {
				return ((LeafNode) root).getPredictedClassValue();
			}
			else
				{
					int risp=0;
					//System.out.println(((SplitNode)root).formulateQuery());
					//risp=Keyboard.readInt();
					out.writeObject("QUERY");
					out.writeObject(((SplitNode)root).formulateQuery());
						try {
							risp=((Integer)in.readObject()).intValue();
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						if(risp<=-1 || risp>=root.getNumberOfChildren())
							return null;//throw new UnknownValueException("The answer should be an integer between 0 and " +(root.getNumberOfChildren()-1)+"!");
						else
							return childTree[risp].predictClass(in,out);
				}
			
		}



}
		
