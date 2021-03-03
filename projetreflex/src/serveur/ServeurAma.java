package serveur;

import java.io.*;
import java.net.*;

public class ServeurAma implements Runnable{
	
	private ServerSocket listen_socket;

	public ServeurAma(int port) throws IOException {
		listen_socket = new ServerSocket(port);
	}

	
	@Override
	public void run() {
		try {
			while(true) 
				new ServiceAma(listen_socket.accept()).start();
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
