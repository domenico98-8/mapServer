package database;

@SuppressWarnings("serial")
public class DatabaseConnectionException extends Exception{
 

	DatabaseConnectionException(){
		super();
	}

	DatabaseConnectionException(String s){
		super(s);
	}
}
