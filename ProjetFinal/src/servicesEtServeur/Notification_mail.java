package servicesEtServeur;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class Notification_mail {
	public static void EnvoyeMail(String destinataire) throws Exception {
		// pour que ce class marche on a besoins des Jars suivatens
		// JavaBeans(TM) Activation Framework
		// https://mvnrepository.com/artifact/javax.activation/activation/1.1.1
		// Apache Commons Email
		// https://mvnrepository.com/artifact/org.apache.commons/commons-email
		// JavaMail API https://mvnrepository.com/artifact/com.sun.mail/javax.mail
		// et aussi tres importnat pour que le program envoye des mails il a besoin d'un
		// mail et un mdp
		// et une parameter dans la compte google pour autoriser l'acces a la mail d'un
		// program

		try {
			Email email = new SimpleEmail();

			// Configuration
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(
					new DefaultAuthenticator("MailduMediatheque@gmail.com", "mdp du mail du Mediatheque"));

			// Demande pour gmail
			email.setSSLOnConnect(true);

			// le mail du mediatheque
			email.setFrom("MailduMediatheque@gmail.com");

			// sujet de mail
			email.setSubject("Alerte DVD disponible");

			// la message de mail
			email.setMsg(
					"Vous avez demandé à être alerté lorsque le DVD est disponible et le voilà de retour ! \n Vous pouvez aller dès maintenant le réserver ou l'emprunter avant qu'il ne soit trop tard.\n Cordialement \n La Médiathèque");

			// Destinataire
			email.addTo(destinataire);
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}