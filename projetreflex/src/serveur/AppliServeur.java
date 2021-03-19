package serveur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import data.Amateur;
import data.Programeur;



public class AppliServeur {


		private final static int[] port = { 3000, 4000};

		public static void main(String[] args) {
			List<Amateur> lesAmateur = mesAmateur();
			List<Programeur> Programeur = Programeur();

			ServiceProg.setlesProgrameur(Programeur);
			ServiceAma.setlesAmateur(lesAmateur);
			

			for (int tab : port) {
				try {
					new Thread(new Server(tab)).start();
				} catch (IOException e) {
					System.err.println("Problème lors de la création du serveur : " + e);
				}
			}
		}

		/**
		 * Nous créons une liste d'objet qui sont des Amateur
		 * 
		 * @return Amateur
		 */
		private static List<Amateur> mesAmateur() {
			List<Amateur> Amateur = new ArrayList<Amateur>();
			Amateur.add(new Amateur( "Harry potter","mdp1"));
			Amateur.add(new Amateur( "Marvel", "mdp2"));
			Amateur.add(new Amateur( "Joker", "mdp3"));
			Amateur.add(new Amateur( "Walt disney", "mdp4"));
			return Amateur;
		}

		/**
		 * Nous créons une liste de Programeur lors du lancement de l'application
		 * 
		 * @return Programeur
		 */

		private static List<Programeur> Programeur() {
			List<Programeur> Programeur = new ArrayList<Programeur>();
			Programeur.add(new Programeur( "Jean", "mdp1", "ftp1"));
			Programeur.add(new Programeur( "Julie", "mdp2", "ftp2"));
			Programeur.add(new Programeur( "Michael", "mdp3", "ftp3"));
			Programeur.add(new Programeur( "Marie", "mdp4", "ftp4"));
			return Programeur;
		}
	}

