package client_serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

public class ServiceEmprunt implements Runnable {
	private static List<Abonne> lesAbonnes;
	private static List<ObjetAEmprunter> lesObjetAEmprunter;

	public static void setlesAbonnes(List<Abonne> lesAbonnes) {
		ServiceEmprunt.lesAbonnes = lesAbonnes;
	}

	public static void setlesObjet(List<ObjetAEmprunter> lesObjetAEmprunter) {
		ServiceEmprunt.lesObjetAEmprunter = lesObjetAEmprunter;
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

	ServiceEmprunt(Socket socket) {
		this.client = socket;
	}

	public void run() {

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			out.println("Tapez Votre ID");
			int IdAb = Integer.parseInt(in.readLine());
			out.println("Tapez l'id d'objet");
			int IdObj = Integer.parseInt(in.readLine());

			System.out.println("Requète de retourne l'Objet n" + IdObj);
			Abonne abonne = getAbonne(IdAb);
			ObjetAEmprunter Objet = getObjetAEmprunter(IdObj);
			if (abonne != null) {
				if (Objet != null) {
					try {
						getObjetAEmprunter(IdObj).empruntPar(getAbonne(IdAb));
						System.out.println("l'objet a ete bien emprunte par l'abonne " + getAbonne(IdAb).nom()
								+ " qui porte le n " + getAbonne(IdAb).numero());
						boolean DepasementDeDateLimite=true;
						Calendar DateLimite = Calendar.getInstance();
						DateLimite.add(Calendar.WEEK_OF_YEAR, 5);
						Calendar temp = Calendar.getInstance();

						while (DateLimite.after(temp)) {
							 temp = Calendar.getInstance();
							if (getObjetAEmprunter(IdObj).EmpruntePar() == 0) {
								DepasementDeDateLimite=false;
								break;
							}
						}
						if (DepasementDeDateLimite) {
							getAbonne(IdAb).interdire();
						}

					} catch (EmpruntException e) {

						e.getMessage();
					}

				}
			}

		} catch (

		IOException e) {
			// Fin du service d'inversion
		}
	}

	protected void finalize() throws Throwable {
		client.close();
	}

}
