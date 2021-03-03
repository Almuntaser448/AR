package servicesEtServeur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;

import objet_et_abbonne_et_exceptions.Abonne;
import objet_et_abbonne_et_exceptions.EmpruntException;
import objet_et_abbonne_et_exceptions.ObjetAEmprunter;

public class ServiceEmprunt implements Runnable {
	private static List<Abonne> lesAbonnes;
	private static List<ObjetAEmprunter> lesObjetAEmprunter;

	/**
	 * A partir de la liste d'Abonné créer dans AppliServeur on crée uns liste
	 * locale
	 * 
	 * @param lesAbonnes
	 */
	public static void setlesAbonnes(List<Abonne> lesAbonnes) {
		ServiceEmprunt.lesAbonnes = lesAbonnes;
	}

	/**
	 * 
	 * @param lesObjetAEmprunter
	 */
	public static void setlesObjet(List<ObjetAEmprunter> lesObjetAEmprunter) {
		ServiceEmprunt.lesObjetAEmprunter = lesObjetAEmprunter;
	}

	/**
	 * Nous cherchons si le numéro en paramètre est dans Abonné, si oui nous
	 * retournons le numéro sinon null
	 * 
	 * @param noAbonne
	 * @return c num abonné trouvé
	 */
	private static Abonne getAbonne(int noAbonne) {
		for (Abonne c : lesAbonnes)
			if (c.numero() == noAbonne)
				return c;
		return null;
	}

	/**
	 * 
	 * @param noObj
	 * @return c
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

	ServiceEmprunt(Socket socket) {
		this.numCo = cpt++;
		this.client = socket;
	}

	/**
	 * Cette méthode permet d'emprunter un objet tout en respectant des contraintes
	 */
	public void run() {
		System.out.println("*********Connexion au service Emprunt " + this.numCo + " démarrée");
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			out.println("Quel est votre ID ?");
			int IdAb = Integer.parseInt(in.readLine());
			out.println("Quel objet voulez-vous choisir ? Ecrivez l'id de l'objet");
			int IdObj = Integer.parseInt(in.readLine());

			out.println("Vous voulez réserver l'objet :  " + IdObj);
			Abonne abonne = getAbonne(IdAb);
			ObjetAEmprunter Objet = getObjetAEmprunter(IdObj);

			if (abonne != null && Objet != null) {

				synchronized (Objet) {
					try {
						getObjetAEmprunter(IdObj).empruntPar(getAbonne(IdAb));
						out.println("L'objet a bien été emprunté par l'abonné " + getAbonne(IdAb).nom()
								+ " qui porte le n° " + getAbonne(IdAb).numero());
						boolean DepasementDeDateLimite = true;
						Calendar DateLimite = Calendar.getInstance();
						DateLimite.add(Calendar.WEEK_OF_YEAR, 5);
						Calendar temp = Calendar.getInstance();

						while (DateLimite.after(temp)) {
							temp = Calendar.getInstance();
							if (getObjetAEmprunter(IdObj).EmpruntePar() == 0) {
								DepasementDeDateLimite = false;
								break;
							}
						}
						if (DepasementDeDateLimite) {
							getAbonne(IdAb).interdire();
						}

					} catch (EmpruntException e) {
						out.println(e.toString());
					}
				}

			} else
				out.println("Votre numéro d'abonné ou d'objet n'existe pas");

			System.out.println("*********Connexion au service Emprunt " + this.numCo + " terminée");
		} catch (IOException e) {
		}
	}

	protected void finalize() throws Throwable {
		client.close();
	}

}
