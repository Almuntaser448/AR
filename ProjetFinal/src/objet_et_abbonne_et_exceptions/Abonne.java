package objet_et_abbonne_et_exceptions;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Abonne {
	private int Numéro;
	private String Noms;
	private String mail;
	private Date Date_de_naissance;
	private boolean est_interdi_a_emprunter = false; // une boolean pour savoir si l'abonne est penaliser pour un mois
														// ou non

	private Calendar Fin_Interdiction = Calendar.getInstance();// une date de type calender qu'est intailitier a la temp
																// de creation et qui va avoir la date de fin
																// d'interdiction d'abonne s'il y a un pénalité

	private Calendar temp;// une date de type calendar qui va avoir la temp de son appelle

	public Abonne(int num, String nom, String mail, String DateN) {
		this.mail = mail;
		this.Numéro = num;
		this.Noms = nom;
		LocalDate DateNa = LocalDate.parse(DateN);
		this.Date_de_naissance = java.sql.Date.valueOf(DateNa);// cette methode est pour convertier le string a un date
																// selon des condition specifique
	}

	public boolean Interdit() {
		this.temp = Calendar.getInstance();
		if (Fin_Interdiction.after(temp)) {
			est_interdi_a_emprunter = true;
		} else {
			est_interdi_a_emprunter = false;
		}
		return est_interdi_a_emprunter;
	}

	public String getMail() {
		return this.mail;
	}

	public int numero() {
		return this.Numéro;
	}

	public String nom() {
		return this.Noms;
	}

	public boolean Adulte() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -16); // -1 pour avoir la dernier annee
		Date adulte = cal.getTime();
		if (this.Date_de_naissance.before(adulte))
			return true;
		else
			return false;
	}

	public Date DNaissance() {
		return this.Date_de_naissance;
	}

	public void interdire() {
		this.est_interdi_a_emprunter = true;
		this.Fin_Interdiction = Calendar.getInstance();
		this.Fin_Interdiction.add(Calendar.MONTH, 1);

	}
}