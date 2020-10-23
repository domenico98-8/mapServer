package data;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import database.Column;
import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.TableData;
import database.TableSchema;

/**
 * Classe che modella l'insieme di esempi di training
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 *
 */
public class Data {
	
	/**
	 * Una lista per modellare transizioni di valori.
	 */
	private List<Example> data;
	
	private int numberOfExamples;
	
	/**
	 * Una lista che contiene il tipo degli attributi di ogni tupla in data.
	 */
	private List<Attribute> explanatorySet;
	
	private ContinuousAttribute classAttribute;
	
	/**
	 * E' il costruttore di classe. Esegue i seguenti compiti:<br>
	 * 1. Avvalora explanatorySet[]. Ogni attributo indipendente è creato associando ad esso i valori discreti che esso può assumere.<br>
	 * 2. Avvalora classAttribute istanziando un oggetto di tipo ContinuousAttribute.<br>
	 * 3. Avvalora il numero di esempi.<br>
	 * 4. Popola data con gli esempi di training;
	 *
	 * @param tableName :Nome del file contenente i dati
	 * @throws TrainingDataException
	 * @throws EmptySetException 
	 * @throws SQLException 
	 * @throws DatabaseConnectionException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("static-access")
	public Data(String tableName)throws TrainingDataException, SQLException, EmptySetException, DatabaseConnectionException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		
		data = new ArrayList<Example>();
		explanatorySet = new LinkedList<Attribute>();
		
		DbAccess database=new DbAccess();
		try {
			database.initConnection();
		}catch (DatabaseConnectionException e) {
			// connessione al database fallisce
			 throw new TrainingDataException(e.getMessage());
		}
		
		TableData tableData=new TableData(database);
		TableSchema schema=new TableSchema(database,tableName);
		
		if(schema.getNumberOfAttributes()==0)
			throw new TrainingDataException("La tabella non esiste");
		if(schema.getNumberOfAttributes() < 2)
			throw new TrainingDataException("La tabella ha meno di due colonne.");
		
		
		data=tableData.getTransazioni(tableName);
		Iterator<Column> it = schema.iterator();
		int i=0;
		
		while(it.hasNext()) {
			Column column = (Column) it.next();
			if (!column.isNumber() && it.hasNext()) {
				Set<Object> distinctValues = (TreeSet<Object>) tableData.getDistinctColumnValues(tableName, column);
				Set<String> discreteValues = new TreeSet<String>();
				for (Object o : distinctValues)
					discreteValues.add((String) o);
				explanatorySet.add(new DiscreteAttribute(column.getColumnName(), i, discreteValues));
				i++;
			}
			else if (it.hasNext() && column.isNumber()) {
				explanatorySet.add(new ContinuousAttribute(column.getColumnName(), i));
				i++;
			}
			else if (!it.hasNext() && column.isNumber())
				classAttribute = new ContinuousAttribute(column.getColumnName(), i);
			else
				throw new TrainingDataException("L'attributo corrispondente all'ultima colonna della tabella non è numerico.");
		}
		// la tabella ha 0 tuple
				try {
					data = tableData.getTransazioni(tableName);
				} catch (EmptySetException e) {
					throw new TrainingDataException(e.getMessage());
				}
				numberOfExamples = data.size();
				database.closeConnection();	
	
	}
	
	/**
	 * Restituisce il valore del membro numberOfExamples
	 * @return numberOfExamples
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}
	
	/**
	 * Restituisce la lunghezza della lista explanatorySet
	 * @return explanatorySet.size()
	 */
	public int getNumberOfExplanatoryAttributes() {
		return explanatorySet.size();
	}
	
	/**
	 * Restituisce il valore dell'attributo di classe per l'esempio exampleIndex
	 * @param exampleIndex :indice di riga per uno specifico esempio
	 * @return (Double)(data[exampleIndex][(data[exampleIndex].length)-1])
	 */
	public Double getClassValue(int exampleIndex) {
		//return (Double)(data[exampleIndex][(data[exampleIndex].length)-1]);
		return (Double) data.get(exampleIndex).get(classAttribute.getIndex());
	}
	
	
	/**
	 * Restituisce il valore dell' attributo indicizzato da attributeIndex per l'esempio exampleIndex
	 * @param exampleIndex :indice di riga per la matrice data[][] per uno specifico esempio
	 * @param attributeIndex : indice della colonna per la matrice[][] per uno specifico esempio
	 * @return data[exampleIndex][attributeIndex]
	 */
	public Object getExplanatoryValue(int exampleIndex, int attributeIndex) {
		//return data[exampleIndex][attributeIndex];
		return data.get(exampleIndex).get(explanatorySet.get(attributeIndex).getIndex());
	}
	
	
	/**
	 * Restituisce l'attributo indicizzato da index in explanatorySet[]
	 * @param index :indice nell'array explanatorySet[] per uno specifico attributo indipendente
	 * @return explanatorySet.get(index)
	 */
	public Attribute getExplanatoryAttribute(int index) {
		return explanatorySet.get(index);
	
	}
	
	/**
	 * restituisce l'oggetto corrispondente all'attributo di classe
	 * @return classAttribute
	 */
	public ContinuousAttribute getClassAttribute() {
		return classAttribute;
	}
	
	/**
	 * egge i valori di tutti gli attributi per ogni esempio da data [][] e li concatena in un
	 * oggetto String che restituisce come risultato finale in forma di sequenze di testi.
	 */
	public String toString(){
		String value = "";
		for (int i = 0; i < numberOfExamples; i++) {
			value += "\n[" + i + "]";
			for (int j = 0; j < explanatorySet.size(); j++)
				value += data.get(i).get(j)+" ";
		

			value += data.get(i).get(explanatorySet.size());
		
		}
		return value;

	}

	
	/**
	 * Ordina il sottoinsieme di esempi compresi nell'intervallo in data[][] 
	 * rispetto allo specifico attributo attribute.
	 * 
	 * @param attribute :Attributo i cui valori devono essere ordinati
	 * @param beginExampleIndex :indice di inizio intervallo
	 * @param endExampleIndex :indice di fine intervallo
	 */
	public void sort(Attribute attribute, int beginExampleIndex, int endExampleIndex){
	
			quicksort(attribute, beginExampleIndex, endExampleIndex);
	}
	
	
	
	/**
	 * Effettua lo swap
	 * 
	 * @param i :indice primo valore
	 * @param j :indice secondo valore
	 */
	private void swap(int i,int j){
		Collections.swap(data, i, j);
	}
	

	
	/**
	 * Partiziona il vettore rispetto all'elemento x e restiutisce il punto di separazione
	 * 
	 * @param attribute : attributo discreto da partizionare
	 * @param inf : indice di inizio
	 * @param sup :	indice di fine
	 * @return j :punto di separazione
	 */
	private  int partition(DiscreteAttribute attribute, int inf, int sup){
		int i,j;
	
		i=inf; 
		j=sup; 
		int	med=(inf+sup)/2;
		String x=(String)getExplanatoryValue(med, attribute.getIndex());
		swap(inf,med);
	
		while (true) 
		{
			
			while(i<=sup && ((String)getExplanatoryValue(i, attribute.getIndex())).compareTo(x)<=0){ 
				i++; 
				
			}
		
			while(((String)getExplanatoryValue(j, attribute.getIndex())).compareTo(x)>0) {
				j--;
			
			}
			
			if(i<j) { 
				swap(i,j);
			}
			else break;
		}
		swap(inf,j);
		return j;

	}
	/*
	 * Partiziona il vettore rispetto all'elemento x e restiutisce il punto di separazione
	 */
	private  int partition(ContinuousAttribute attribute, int inf, int sup){
		int i,j;
	
		i=inf; 
		j=sup; 
		int	med=(inf+sup)/2;
		Double x=(Double)getExplanatoryValue(med, attribute.getIndex());
		swap(inf,med);
	
		while (true) 
		{
			
			while(i<=sup && ((Double)getExplanatoryValue(i, attribute.getIndex())).compareTo(x)<=0){ 
				i++; 
				
			}
		
			while(((Double)getExplanatoryValue(j, attribute.getIndex())).compareTo(x)>0) {
				j--;
			
			}
			
			if(i<j) { 
				swap(i,j);
			}
			else break;
		}
		swap(inf,j);
		return j;

	}
	
	
	/*
	 * Algoritmo quicksort per l'ordinamento di un array di interi 
	 * usando come relazione d'ordine totale "<="
	 * @param attribute : attributo da cui ordinare
	 * @param inf : indice inizio
	 * @param sup : indice fine
	 */
	private void quicksort(Attribute attribute, int inf, int sup){
		
		if(sup>=inf){
			
			int pos;
			if(attribute instanceof DiscreteAttribute)
				pos=partition((DiscreteAttribute)attribute, inf, sup);
			else
				pos=partition((ContinuousAttribute)attribute, inf, sup);
					
			if ((pos-inf) < (sup-pos+1)) {
				quicksort(attribute, inf, pos-1); 
				quicksort(attribute, pos+1,sup);
			}
			else
			{
				quicksort(attribute, pos+1, sup); 
				quicksort(attribute, inf, pos-1);
			}
			
			
		}
		
	}
	

	
	//Main
	/*public static void main(String args[])throws TrainingDataException, SQLException, EmptySetException, DatabaseConnectionException{
		Data trainingSet=new Data("servo");
		System.out.println(trainingSet);

		
		for(int jColumn=0;jColumn<=trainingSet.getNumberOfExplanatoryAttributes();jColumn++)
		{
			System.out.println("\nORDER BY "+trainingSet.getExplanatoryAttribute(jColumn));
			trainingSet.quicksort(trainingSet.getExplanatoryAttribute(jColumn),0 , trainingSet.getNumberOfExamples()-1);
			System.out.println(trainingSet);
		}
		


	
	
	
	}*/

}
