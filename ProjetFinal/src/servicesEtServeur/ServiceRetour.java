package servicesEtServeur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import objet_et_abbonne_et_exceptions.ObjetAEmprunter;

public class ServiceRetour implements Runnable {
	/**
	 * cette classe permet de retourner les objet deja emprunte grace a l'id d'Objet
	 * a retourenr
	 *
	 */
	private static List<ObjetAEmprunter> lesObjetAEmprunter;

	/**
	 * dans cette methode on vais importer la liste des objet a emprunter depuis
	 * notre appli serveru avec le @param lesObjetAEmprunte et on le stock dans une
	 * liste local qui s'appelle lesObjetAEmprunter
	 */
	public static void setlesObjet(List<ObjetAEmprunter> lesObjetAEmprunter) {
		ServiceRetour.lesObjetAEmprunter = lesObjetAEmprunter;
	}

	/**
	 * dans cette methode in cherche l'objet dans notre list l'objet qui a le
	 * numero @param noObj et en suit on vais @return l'objet depuis notre list
	 */
	private static ObjetAEmprunter getObjetAEmprunter(int noObj) {
		for (ObjetAEmprunter c : lesObjetAEmprunter)
			if (c.numero() == noObj)
				return c;
		return null;
	}

	private final Socket client;
	private static int cpt = 1;
	private final int numCo;

	ServiceRetour(Socket socket) {
		this.numCo = cpt++;
		this.client = socket;
	}

	/**
	 * dans cette methode on vais avoir un id et ensuit chercher l'objet qu'a cette
	 * id et en fin retourner l'objet dans notre mediateque
	 */
	public void run() {
		System.out.println("*********Connexion au service Retour " + this.numCo + " démarrée");
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.println();
			out.println("Quel objet voulez-vous retourner ? Ecrivez l'id de l'objet ");
			int IdObj = Integer.parseInt(in.readLine());

			out.println("Vous voulez retourner l'objet : " + IdObj);

			ObjetAEmprunter Objet = getObjetAEmprunter(IdObj);
			synchronized (Objet) {
				if (Objet != null) {
					getObjetAEmprunter(IdObj).retour();
				}

			}
			System.out.println("*********Connexion au service Retour " + this.numCo + " terminée");
		} catch (IOException e) {
		}
	}

	protected void finalize() throws Throwable {
		client.close();
	}

}
