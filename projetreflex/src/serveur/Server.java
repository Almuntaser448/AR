package serveur;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 
 * ce class est pour demarrer le serveur et les associer aux services concerne
 *
 */
public class Server implements Runnable {
	private ServerSocket listen_socket;

	public Server(int port) throws IOException {
		listen_socket = new ServerSocket(port);
	}

	public void run() {
		try {
			System.err.println("Lancement du serveur au port " + this.listen_socket.getLocalPort());

			while (true) {
				switch (this.listen_socket.getLocalPort()) {// ce switch est la pour differencier les services par rapport
															// aux ports associe
				case 3000:
					new Thread(new ServiceProg(listen_socket.accept())).start();
					break;
				case 4000:
					new Thread(new ServiceAma(listen_socket.accept())).start();
					break;
		
				}
			}
		} catch (IOException e) {
			try {
				this.listen_socket.close();
			} catch (IOException e1) {
			}
			System.err.println("Arrêt du serveur au port " + this.listen_socket.getLocalPort());
		}
	}

	protected void finalize() throws Throwable {
		try {
			this.listen_socket.close();
		} catch (IOException e1) {
		}
	}
}
