package servicesEtServeur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Timer;

import objet_et_abbonne_et_exceptions.*;

public class ServiceReservation implements Runnable {
	private static List<Abonne> lesAbonnes;
	private static List<ObjetAEmprunter> lesObjetAEmprunter;

	public static void setlesAbonnes(List<Abonne> lesAbonnes) {
		ServiceReservation.lesAbonnes = lesAbonnes;
	}

	public static void setlesObjet(List<ObjetAEmprunter> lesObjetAEmprunter) {
		ServiceReservation.lesObjetAEmprunter = lesObjetAEmprunter;
	}

	private static Abonne getAbonne(int noAbonne) {
		for (Abonne c : lesAbonnes)
			if (c.numero() == noAbonne)
				return c;
		return null;
	}

	private static ObjetAEmprunter getObjetAEmprunter(int noObj) {
		for (ObjetAEmprunter c : lesObjetAEmprunter)
			if (c.numero() == noObj)
				return c;
		return null;
	}

	private final Socket client;
	private static int cpt = 1;
	private final int numCo;

	ServiceReservation(Socket socket) {
		this.numCo = cpt++;
		this.client = socket;
	}

	/**
	 * cette méthode permet au client de réserver ou non un objet en fonction de
	 * critère entrer par le client
	 * 
	 * @param id abonné et id objet vont nous permettre de savoir si l'objet est
	 *           disponible et si l'abonné peut le réserver
	 */
	public void run() {
		System.out.println("*********Connexion au service Réservation " + this.numCo + " démarrée");
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			out.println("Voici le catalogue : ");

			for (ObjetAEmprunter c : lesObjetAEmprunter) {
				out.print("le titre: " + c.titre() + " porte le numero : " + c.numero());

				if (c instanceof DVD) {
					if (((DVD) c).Adulte()) {
						out.println(" classé pour les 16 ans ou plus");
					} else {
						out.println(" classé pour tout les ages");
					}
				} else {
					out.println(" classé pour tout les ages");
				}
			}

			out.println();
			out.println();

			out.println("Quel est votre ID ?");
			int IdAb = Integer.parseInt(in.readLine());
			out.println("Quel objet voulez-vous choisir ? Ecrivez l'id de l'objet");
			int IdObj = Integer.parseInt(in.readLine());

			out.println("Vous voulez réserver l'objet :  " + IdObj);
			Abonne abonne = getAbonne(IdAb);
			ObjetAEmprunter Objet = getObjetAEmprunter(IdObj);

			if (abonne != null && Objet != null) { // cette méthode nous permet de réserver un objet durant 2h mais si
													// celui ci n'est pas disponible, le client pourra s'inscrire dans
													// une liste d'attente pour être alerté par mail s'il devient
													// disponible
				synchronized (Objet) {
					try {
						getObjetAEmprunter(IdObj).reservationPour(getAbonne(IdAb));
						out.println("Vous venez de réserver l'objet pendant deux heures. Pensez à l'emprunter!");
						Timer timer = new Timer();
						timer.schedule(getObjetAEmprunter(IdObj), 1000 * 60 * 120);
					} catch (ReservationException e) {
						if (getObjetAEmprunter(IdObj).reservePar() != 0
								|| getObjetAEmprunter(IdObj).EmpruntePar() != 0) {
							out.println(
									"Voulez vous être inscrit dans la liste d'attente pour cet objet? (1 = OUI/2 = NON)");
							int num = Integer.parseInt(in.readLine());
							if (num == 1) {
								getObjetAEmprunter(IdObj).AjouterListeDAttend(getAbonne(IdAb));
								out.println("Vous êtes bien inscrit dans la liste");

							} else {
								out.println(e.toString());
							}

						} else
							out.println(e.toString());
					}
				}
			} else
				out.println("Votre numéro d'abonné ou d'objet n'existe pas");

			System.out.println("*********Connexion au service Réservation " + this.numCo + " terminée");
		} catch (IOException e) {
		}
	}

	protected void finalize() throws Throwable {
		client.close();
	}

}
