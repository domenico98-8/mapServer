package database;

@SuppressWarnings("serial")
public class EmptySetException extends Exception{
	
	EmptySetException(){
		super();
	}
	EmptySetException(String s){
		super(s);
	}
}
