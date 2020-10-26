package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * Classe che modella lo schema di una tabella nel database relazionale
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 */

public class TableSchema implements Iterable<Column>{

	/**
	 * Array che contiene lo schema modellato dalle colonne.
	 */
	private List<Column> tableSchema=new ArrayList<Column>();
	/**
	 * Costruttore
	 * @param db :database su cui creo la query
	 * @param tableName :nome della tabella
	 * @throws SQLException : Eccezione generata se si presentano errori durante l'interrogazione al database
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException{
		
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
	
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		
		
	
		 Connection con=db.getConnection();
		 DatabaseMetaData meta = con.getMetaData();
	     ResultSet res = meta.getColumns(null, null, tableName, null);
		   
	     while (res.next()) {
	         
	         if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
	        		 tableSchema.add(new Column(
	        				 res.getString("COLUMN_NAME"),
	        				 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        				 );
	
	         
	         
	      }
	      res.close();
	
	
	    
	    }

	/**
	 *
	 * @return Restituisce il numero delle colonne nella tabella
	 */
		public int getNumberOfAttributes(){
			return tableSchema.size();
		}
	/**
	 *
	 * @param index :indice colonna
	 * @return Restituisce la colonna specificata da indice
	 */
		public Column getColumn(int index){
			return tableSchema.get(index);
		}


		@Override
		public Iterator<Column> iterator() {
			// TODO Auto-generated method stub
			return tableSchema.iterator();
		}

		
	}

		     


