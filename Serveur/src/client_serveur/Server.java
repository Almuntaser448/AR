package client_serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class Server implements Runnable {
    private ServerSocket listen_socket;
    
    Server(int port) throws IOException {
        listen_socket = new ServerSocket(port);
    }

    public void run() {
        try {
            System.err.println("Lancement du serveur au port "+this.listen_socket.getLocalPort());
            
            while(true)
            	switch (this.listen_socket.getLocalPort()) {
            	case 3000:
            		new Thread(new ServiceReservation(listen_socket.accept())).start();
               	 break;
            	case 4000:
            	 new Thread(new ServiceEmprunt(listen_socket.accept())).start();
            	 break;
            	case 5000:
                new Thread(new ServiceRetour(listen_socket.accept())).start();
            	}
        }
        catch (IOException e) { 
            try {this.listen_socket.close();} catch (IOException e1) {}
            System.err.println("Arrêt du serveur au port "+this.listen_socket.getLocalPort());
        }
    }

    
    protected void finalize() throws Throwable {
        try {this.listen_socket.close();} catch (IOException e1) {}
    }
}
