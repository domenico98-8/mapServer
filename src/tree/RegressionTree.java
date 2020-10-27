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
 * modella l'entità dell'intero albero di decisione come insieme di sotto-alberi
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
@SuppressWarnings("serial")
public class RegressionTree extends Keyboard implements Serializable{
	/**
	 * Radice del sotto-albero corrente
	 */
	Node root;
	/**
	 * Array di sotto-alberi originanti nel nodo root:vi è un elemento nell’array per ogni figlio del nodo
	 */
	RegressionTree childTree[];
	/**
	 * istanzia un sotto-albero dell'intero albero
	 */
		public RegressionTree(){
			
		}
	/**
	 * istanzia un sotto-albero dell'intero albero e avvia l'induzione dell'albero dagli esempi di training in input
	 * @param trainingSet :training set complessivo
	 * @throws MapSplitEmptyException :Eccezione per gestire un insieme di valori continui uguali
	 */
		public RegressionTree(Data trainingSet) throws MapSplitEmptyException{
			
			learnTree(trainingSet,0,trainingSet.getNumberOfExamples()-1,trainingSet.getNumberOfExamples()*10/100);
		}
	/**
	 * genera un sotto-albero con il sotto-insieme di input istanziando un nodo fogliare o un nodo di split.
	 * @param trainingSet :training set complessivo
	 * @param begin :indice inizio del sotto-insieme di training
	 * @param end :indice fine del sotto-insieme di training
	 * @param numberOfExamplesPerLeaf :numero max che una foglia deve contenere
	 */
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
	/**
	 * verifica se il sotto-insieme corrente può essere coperto da un nodo foglia controllando che il numero di esempi del training set compresi tra begin ed end sia minore uguale di numberOfExamplesPerLeaf
	 * @param trainingSet :training set complessivo
	 * @param begin :indice inizio del sotto-insieme di training
	 * @param end :indice fine del sotto-insieme di training
	 * @param numberOfExamplesPerLeaf :minimo che una foglia deve contenere
	 * @return True: se il numberExample è minore uguale di numberOfExamplesPerLeaf, False se il
	 * numberOfExamplesPerLeaf è minore di numberExample
	 */
		private boolean isLeaf(Data trainingSet,int begin, int end,int numberOfExamplesPerLeaf) {
			int numberExample=(end-begin)-1;
			
			if(numberExample<= numberOfExamplesPerLeaf) {
			 return true;
			}
			else 
				return false;
		}
	/**
	 * Per ogni attributo indipendente istanzia il DiscreteNode associato e seleziona il nodo di split con minore varianza tra i DiscreteNode istanziati. Ordina la porzione di trainingSet corrente rispetto all’attributo indipendente del nodo di split selezionato.
	 * @param trainingSet :training set complessivo
	 * @param begin :indice inizio del sotto-insieme di training
	 * @param end :indice fine del sotto-insieme di training
	 * @return ts.first :il nodo selezionato
	 * @throws MapSplitEmptyException :Eccezione per gestire un insieme di valori continui uguali
	 */
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
	/**
	 * Serializza l'albero in un file
	 * @param nomeFile :Nome del file in cui salvare l'albero
	 * @throws FileNotFoundException :Errore generato quando non si trova un file
	 * @throws IOException :Errore generato quando si verifica un errore I/O
	 */
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

	/**
	 * Carica un albero di regressione salvato in un file.
	 * @param nomeFile :Nome del file in cui è salvato l'albero
	 * @return albero contenuto nel file.
	 * @throws FileNotFoundException :Errore generato quando non si trova un file
	 * @throws IOException :Errore generato quando si verifica un errore I/O
	 * @throws ClassNotFoundException :Eccezione lanciata quando un'applicazione tenta di caricare in una classe tramite il suo nome di stringa
	 * @throws FileErratoExcpetion :Eccezione lanciata quando c'è un inserimento errato del file
	 */
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

	/**
	 * Concatena in una String tutte le informazioni di root-childTree[] correnti invocando i relativi metodo toString().
	 * @return tree :oggetto String con le informazioni dell'intero albero
	 */
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


	/**
	 * Visualizza le informazioni di ciascuno split dell'albero e per il corrispondente attributo acquisisce il valore dell'esempio da predire da tastiera.
	 * @param in :Stream in entrata
	 * @param out :Stream di uscita
	 * @return restituisce le informazioni di ogni split dell'albero
	 * @throws UnknownValueException :Eccezione per gestire il caso di acqusizione di valore mancante o fuori range di un attributo
	 * @throws IOException :Errore generato quando si verifica un errore I/O
	 */
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
		
