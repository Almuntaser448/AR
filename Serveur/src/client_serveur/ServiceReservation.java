package client_serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Timer;

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

	ServiceReservation(Socket socket) {
		this.client = socket;
	}

	public void run() {

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
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
			out.println("\n \n \n");
			out.println("Tapez Votre ID");
			int IdAb = Integer.parseInt(in.readLine());
			out.println("Tapez l'id d'objet");
			int IdObj = Integer.parseInt(in.readLine());

			System.out.println("Requète de reserver l'Objet n" + IdObj);
			Abonne abonne = getAbonne(IdAb);
			ObjetAEmprunter Objet = getObjetAEmprunter(IdObj);
			if (abonne != null) {
				if (Objet != null) {
					synchronized (Objet) {
						try {
							getObjetAEmprunter(IdObj).reservationPour(getAbonne(IdAb));
							out.println("vous avez bien reserve l'objet pour deux heures");
							Timer timer = new Timer();
							timer.schedule(getObjetAEmprunter(IdObj), 1000 * 60 * 120);
						} catch (ReservationException e) {
							if (getObjetAEmprunter(IdObj).reservePar() != 0
									|| getObjetAEmprunter(IdObj).EmpruntePar() != 0) {
								out.println(
										"Voules vous etre inscrite dans la liste d'attende pour cette objet? (O/n)");
								BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
								char c;
								c = (char) br.read();
								if (c == 'o' || c == 'O') {
									getObjetAEmprunter(IdObj).AjouterListeDAttend(getAbonne(IdAb));
									out.println("Vous etes bien inscrite dans la list");

								} else {
									e.getMessage();
								}

							} else
								e.getMessage();
						}
					}
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
