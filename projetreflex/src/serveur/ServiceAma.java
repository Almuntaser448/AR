package serveur;

import java.net.*;

public class ServiceAma implements Runnable {
	
	private Socket client;

	ServiceAma(Socket socket) {
		client = socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	
	public void start() {
		(new Thread(this)).start();		
	}
}
