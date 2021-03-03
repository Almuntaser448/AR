package serveur;

import java.net.Socket;

public class ServiceProg implements Runnable {

	private Socket client;

	ServiceProg(Socket socket) {
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
