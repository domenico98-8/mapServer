package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Instaura la connessione col client
 * @author Alessia Laquale
 * @author Domenco Cascella
 * @author Patrizia Conte
 */
public class MultiServer {
	/**
	 * la porta che la ServerSocket userà per accettare la connessione con il clinet
	 * e costruire la Socket.
	 */
	private static int PORT=8080;
	/**
	 * La ServerSocket che gestisce il MultiServer.
	 */
	private static ServerSocket connessione;

	/**
	 * Costruttore della classe che inizializza la porta ed invoca il metodo run.
	 * @param port :porta su cui instaurare la connessione
	 * @throws IOException : Eccezione I/O
	 */
	public MultiServer(int port) throws IOException {
		PORT=port;
		run();
	}
	/**
	 * Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di richiesta di
	 * connessioni da parte del client.
	 * @throws IOException : Eccezione I/O
	 */
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
