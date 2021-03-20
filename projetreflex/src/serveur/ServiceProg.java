package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import data.Programeur;



public class ServiceProg implements Runnable {
	private static List<Programeur> lesProgrameur;
	private static int cpt = 1;
	private final int numCo;
	private Socket client;
	ServiceProg(Socket socket) {
		this.numCo = cpt++;
		this.client = socket;
	}
	public static void setlesProgrameur(List<Programeur> lesProgrameur) {
		ServiceProg.lesProgrameur = lesProgrameur;
	}

	private static Programeur getProgrameur(String logProgrameur,String mdp) {
		for (Programeur c : lesProgrameur)
			if (c.getLogin() == logProgrameur&&c.getMdp()==mdp)
				return c;
		return null;
	}

	
	@Override
	public void run() {
		System.out.println("*********Connexion au service Emprunt " + this.numCo + " démarrée");
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					PrintWriter out = new PrintWriter(client.getOutputStream(), true);

out.println("veuiliez choisir la service:");
out.println("pour Fournir un nouveau service tapez 1");
out.println("pour Mettre-à-jour un service tapez 2");
out.println("pour Déclarer un changement d’adresse de votre serveur ftp tapez 3");
int x = Integer.parseInt(in.readLine());

switch (x) {
case 1:
	
	
	
	
	
	
	
	break;
case 2:
	
	
	
	
	
	break;
	
	
	
	
	
case 3:
	out.println("veuliez ecrire votre login");
	String log=in.readLine();
	out.println("veuliez ecrire votre mdp");
	String mdp=in.readLine();
	Programeur programeur=getProgrameur(log,mdp);
	if(programeur!=null) {
		synchronized (programeur) {
		try {
			out.print("votre actuel serveur de ftp est : "  );
			out.println(getProgrameur(log,mdp).getFtp());
			out.println("voyez ecriver votre novelle ftp");
			String NFTP=in.readLine();
			out.print("votre novelle FTP est");
			out.println(NFTP);
			out.println("voulez vous sauvegarder la novelle FTP? pour OUI tappez 1 pour NON tappez 2");
			int Reponse=Integer.parseInt(in.readLine());
			if(Reponse==1){
				getProgrameur(log,mdp).setFtp(NFTP);
				out.println("votre FTP est bien modifie");
			}else{
				out.println("les changement sont ignorees");
			}
		}
		catch(Exception e){
			out.println(e.getMessage());
		}
		}}
	else {
		out.println("votre login ou votre mdp sont incorrect");
	}
	break;
}














					System.out.println("*********Connexion au service Emprunt " + this.numCo + " terminée");
				} catch (IOException e) {
				}
	}

	protected void finalize() throws Throwable {
		 client.close(); 
	}

	
	public void start() {
		(new Thread(this)).start();		
	}
}
