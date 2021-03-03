package serveur;

import java.io.IOException;
import java.net.ServerSocket;

public class ServeurProg implements Runnable {

	private ServerSocket listen_socket;

	public ServeurProg(int port) throws IOException {
		listen_socket = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		try {
			while(true) 
				new ServiceProg(listen_socket.accept()).start();
		}
		catch (IOException e) { 
			try {this.listen_socket.close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'écoute :"+e);
		}
	}
	
	protected void finalize() throws Throwable {
		try {this.listen_socket.close();} catch (IOException e1) {}
	}
	
	public void lancer() {
		(new Thread(this)).start();		
	}
}
