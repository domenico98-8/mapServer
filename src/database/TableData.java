package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;



public class TableData {

	private DbAccess db;
	

	
	public TableData(DbAccess db) {
		this.db=db;
	}

	public List<Example> getTransazioni(String table) throws SQLException, EmptySetException{
		LinkedList<Example> transSet = new LinkedList<Example>();
		Statement statement;
		TableSchema tSchema=new TableSchema(db,table);
		
		
		String query="select ";
		
		for(int i=0;i<tSchema.getNumberOfAttributes();i++){
			Column c=tSchema.getColumn(i);
			if(i>0)
				query+=",";
			query += c.getColumnName();
		}
		if(tSchema.getNumberOfAttributes()==0)
			throw new SQLException();
		query += (" FROM "+table);
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		boolean empty=true;
		while (rs.next()) {
			empty=false;
			Example currentTuple=new Example();
			for(int i=0;i<tSchema.getNumberOfAttributes();i++)
				if(tSchema.getColumn(i).isNumber())
					currentTuple.add(rs.getDouble(i+1));
				else
					currentTuple.add(rs.getString(i+1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();
		if(empty) throw new EmptySetException("Set Vuoto!");
		
		
		return transSet;

	}

	public Set<Object> getDistinctColumnValues (String table, Column column) throws SQLException{
		Set<Object> set=new TreeSet<Object>();
		String query;
		
		@SuppressWarnings("static-access")
		Statement statement = db.getConnection().createStatement();
		//query="Select DISTINCT "+column+" from "+table+" ORDER BY "+column+" asc";
		query = "SELECT DISTINCT " + column.getColumnName() + " FROM " + table;
		ResultSet rs = statement.executeQuery(query);
		
		if (column.isNumber())
			while (rs.next())
				set.add(rs.getDouble(column.getColumnName()));
		else
			while (rs.next())
				set.add(rs.getString(column.getColumnName()));
		
		return set;
	}
	

}
