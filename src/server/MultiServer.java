package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
	private static int PORT=8080;
	private static ServerSocket connessione;
	public MultiServer(int port) throws IOException {
		PORT=port;
		run();
	}
	private void run() throws IOException {
		connessione= new ServerSocket(PORT);
		System.out.println("Started: " + connessione);
		try {
			while(true) {
				Socket s=connessione.accept();
				System.out.println("Connessione Accettata!\n"+ connessione);
				new ServerOneClient(s);
			}
		}finally {
			connessione.close();
		}
		
	
	}
}
