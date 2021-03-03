package servicesEtServeur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import objet_et_abbonne_et_exceptions.Abonne;
import objet_et_abbonne_et_exceptions.DVD;
import objet_et_abbonne_et_exceptions.ObjetAEmprunter;

public class AppliServeur {

	private final static int[] port = { 3000, 4000, 5000 };

	public static void main(String[] args) {
		List<ObjetAEmprunter> lesDVD = mesDVD();
		List<Abonne> ab = abonnees();

		ServiceReservation.setlesObjet(lesDVD);
		ServiceEmprunt.setlesObjet(lesDVD);
		ServiceRetour.setlesObjet(lesDVD);
		ServiceReservation.setlesAbonnes(abonnees());
		ServiceEmprunt.setlesAbonnes(abonnees());

		for (int tab : port) {
			try {
				new Thread(new Server(tab)).start();
			} catch (IOException e) {
				System.err.println("Problème lors de la création du serveur : " + e);
			}
		}
	}

	/**
	 * Nous créons une liste d'objet qui sont des DVD
	 * 
	 * @return dvd
	 */
	private static List<ObjetAEmprunter> mesDVD() {
		List<ObjetAEmprunter> dvd = new ArrayList<ObjetAEmprunter>();
		dvd.add(new DVD(1, "Harry potter", true));
		dvd.add(new DVD(2, "Marvel", false));
		dvd.add(new DVD(3, "Joker", true));
		dvd.add(new DVD(4, "Walt disney", false));
		return dvd;
	}

	/**
	 * Nous créons une liste d'abonné lors du lancement de l'application
	 * 
	 * @return ab
	 */

	private static List<Abonne> abonnees() {
		List<Abonne> ab = new ArrayList<Abonne>();
		ab.add(new Abonne(1, "Jean", "Jean@gmail.com", "1918-12-31"));
		ab.add(new Abonne(2, "Julie", "Julie@gmail.com", "2009-10-06"));
		ab.add(new Abonne(3, "Michael", "Michael@gmail.com", "1956-06-11"));
		ab.add(new Abonne(4, "Marie", "Marie@gmail.com", "2015-09-24"));
		return ab;
	}
}
