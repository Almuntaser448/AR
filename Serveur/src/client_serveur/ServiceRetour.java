package client_serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServiceRetour implements Runnable {
	private static List<ObjetAEmprunter> lesObjetAEmprunter;

	public static void setlesObjet(List<ObjetAEmprunter> lesObjetAEmprunter) {
		ServiceRetour.lesObjetAEmprunter = lesObjetAEmprunter;
	}

	private static ObjetAEmprunter getObjetAEmprunter(int noObj) {
		for (ObjetAEmprunter c : lesObjetAEmprunter)
			if (c.numero() == noObj)
				return c;
		return null;
	}

	private final Socket client;

	ServiceRetour(Socket socket) {
		this.client = socket;
	}

	public void run() {

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			out.println("Tapez l'id d'objet");
			int IdObj = Integer.parseInt(in.readLine());

			System.out.println("Requète de retourne l'Objet n" + IdObj);

			ObjetAEmprunter Objet = getObjetAEmprunter(IdObj);
			synchronized (Objet) {
				if (Objet != null) {
					getObjetAEmprunter(IdObj).retour();
				}
				
			}
		} catch (IOException e) {
			// Fin du service d'inversion
		}
	}

	protected void finalize() throws Throwable {
		client.close();
	}

}
