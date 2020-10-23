package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.sql.SQLException;

import data.Data;
import data.MapSplitEmptyException;
import data.TrainingDataException;
import database.DatabaseConnectionException;
import database.EmptySetException;
import tree.FileErratoExcpetion;
import tree.RegressionTree;

public class ServerOneClient extends Thread{
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private RegressionTree tree;
	private String tableName="";
	private Data trainingSet;
	
	public ServerOneClient(Socket s) throws IOException {
		socket=s;
		in= new ObjectInputStream(socket.getInputStream());
		out= new ObjectOutputStream(socket.getOutputStream());
		start();
	}
	public void run() {
		boolean flag = false;
		try {
			Object SceltaClient=in.readObject();
			if (SceltaClient instanceof Integer) {
				SceltaClient=(Integer)SceltaClient;
				switch(((Integer)SceltaClient).intValue()) {
					case 0:do {
						tableName = (String) in.readObject();
						try {
							trainingSet = new Data(tableName);
							flag=true;
							out.writeObject("Corretto");
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (TrainingDataException e) {
							flag = false;
							out.writeObject("Ripeti");
							e.printStackTrace();
						}catch(NullPointerException e){
							System.out.println(e.toString());
						}
					}while(flag==false);
						out.writeObject("OK");
						if(((Integer)in.readObject()).intValue()==1)
						{
						tree=new RegressionTree(trainingSet);
						out.writeObject("OK");
						tree.salva(tableName+".dmp");
						}
						break;
					case 2:  do {
						tableName=(String)in.readObject();
						try {
							tree = RegressionTree.carica(tableName + ".dmp");
							flag=true;
							out.writeObject("Corretto");
						}catch (FileErratoExcpetion e) { //Gestione inseirmento errato del file(Diverso da FileNotFoundException)
							e.printStackTrace();
							flag=false;
							out.writeObject("Ripeti");
						}
					}while(flag==false);
						out.writeObject("OK");
						break;
					
					
				}
			}
			do {
				SceltaClient=in.readObject();
				if(((Integer)SceltaClient).intValue()==3) {
					//out.writeObject("QUERY");
					Double predizione;
					predizione=tree.predictClass(in, out);
					if(predizione == null) {
						System.out.println("Scelta Non Valida! Client Disconnesso...");
						out.defaultWriteObject();
					}
					else {
						out.writeObject("OK");
						out.writeObject(predizione);
						
					}
				}
			}while(((Integer)SceltaClient).intValue()==3);
		
			
		} catch(IOException e) {
			System.out.println("In Attesa di connessione...");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch ( SQLException | EmptySetException | DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		} catch (UnknownValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapSplitEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				socket.close();
			} catch(IOException e) {
				System.err.println("Socket not closed");
			}
		}
	}
}
