package client_serveur;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Abonne {
	private int Numéro;
	private String Noms;
	private String mail;
	private Date Date_de_naissance;
	private boolean est_interdi_a_emprunter=false;
	private Calendar Fin_Interdiction=Calendar.getInstance();
	private Calendar temp;
	Abonne(int num, String nom,String mail,String DateN) {
		this.mail=mail;
		this.Numéro = num;
		this.Noms = nom;
		LocalDate DateNa = LocalDate.parse(DateN);
		this.Date_de_naissance = java.sql.Date.valueOf(DateNa);
	}
	public boolean Interdit() {
		this.temp=Calendar.getInstance();
		if(Fin_Interdiction.after(temp)) {
			est_interdi_a_emprunter= true;
		}else {
			est_interdi_a_emprunter=false;
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
		cal.add(Calendar.YEAR, -16); // to get previous year add -1
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
		this.est_interdi_a_emprunter=true;
		this.Fin_Interdiction=Calendar.getInstance();
		this.Fin_Interdiction.add(Calendar.MONTH,1);
		
	}
}
