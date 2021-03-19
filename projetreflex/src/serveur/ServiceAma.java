package serveur;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.List;

import data.*;


public class ServiceAma implements Runnable {
	private static List<Amateur> lesAmateur;

	private Socket client;

	ServiceAma(Socket socket) {
		client = socket;
	}
	
	public static void setlesAmateur(List<Amateur> lesAmateur) {
		ServiceAma.lesAmateur = lesAmateur;
	}

	private static Amateur getAmateurActuel(String logAmateur,String mdp) {
		for (Amateur c : lesAmateur)
			if (c.getLogin() == logAmateur&&c.getMdp()==mdp)
				return c;
		return null;
	}
	private static Amateur getAmateurDestinater(String logAmateur) {
		for (Amateur c : lesAmateur)
			if (c.getLogin() == logAmateur)
				return c;
		return null;
	}
	@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
		PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
		out.println(ServiceRegistry.toStringue()+"Tapez le numéro de service désiré :ecrire 1 consulter 2");
		int choix = Integer.parseInt(in.readLine());
		String Login,mdp;
		switch (choix) {//le code ici c'est celui des message interne apres je vais le deplacer mais c'est fini et ca dois marcher normalment
		case 1:
			out.println("veuilez ecrire votre login");
			Login=in.readLine();
			out.println("veuilez ecrire votre mdp");
			mdp=in.readLine();
			
			Amateur Amateur=getAmateurActuel(Login,mdp);
			if(Amateur!=null) {
				synchronized (Amateur) {
				try {
					out.print("veuiliz ecrire votre destiantaire" );
					String DestLog = in.readLine();
					Amateur AmateurDest=getAmateurDestinater(DestLog);
					if(AmateurDest!=null) {
						try {
							out.print("veuiliz ecrire votre message");
							String Message=in.readLine();
							getAmateurDestinater(DestLog).AjoutMessagrie(Login, Message);
							out.println("votre message est bien envoyee");
							
						}
						catch(Exception e){
							out.println(e.getMessage());
						}
					}else {out.println("votre destinatire n'exite pas");}
					
				}
				catch(Exception e){
					out.println(e.getMessage());
				}
				}}
			else {
				out.println("votre login ou votre mdp sont incorrect");
			}
			break;
			
			
			
		case 2:
			out.println("veuilez ecrire votre login");
			Login=in.readLine();
			out.println("veuilez ecrire votre mdp");
			mdp=in.readLine();
			
			Amateur Amateur2=getAmateurActuel(Login,mdp);
			if(Amateur2!=null) {
				synchronized (Amateur2) {
				try {
					if(Amateur2.getMessagrie().isEmpty()) {
							out.println("il n'y a rien dans votre messagrie");}
					else{for(int i=0;i<Amateur2.getMessagrie().size();i++) {
						out.println(Amateur2.getMessagrie().get(i));
					}
						
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
		Class<? extends Service> classe = ServiceRegistry.getServiceClass(choix);
		
		try {
			
			Constructor<? extends Service> niou = classe.getConstructor(java.net.Socket.class);
			Service service = niou.newInstance(this.client);
			service.run();
			/*Method runne = classe.getMethod("run");
			runne.invoke(service);*/
		} catch (SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			System.out.println(e);
		}
		}
	catch (IOException e) {
		//Fin du service
	}

	try {client.close();} catch (IOException e2) {}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	
	public void start() {
		(new Thread(this)).start();		
	}
}
