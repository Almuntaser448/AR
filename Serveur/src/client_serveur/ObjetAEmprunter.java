package client_serveur;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

public class ObjetAEmprunter extends TimerTask implements Document  {
	private int numero;
	private String Titre;
	private int ReservePar = 0;
	private int EmprunterPar = 0;
	private Date DateReservation;
	private List <Abonne> aboones_a_notifie=new ArrayList <Abonne>();


	ObjetAEmprunter(int num, String titre) {
		this.numero = num;
		this.Titre = titre;
	}
	public void AjouterListeDAttend(Abonne ab){
		aboones_a_notifie.add(ab);
	}
	public List<Abonne> AbooneAnotifer(){
		return this.aboones_a_notifie;
	}

	@Override
	public int numero() {
		return this.numero;
	}
	@Override
	public void reservationPour(Abonne ab) throws ReservationException {
		 if(this.ReservePar==0&&this.EmprunterPar==0&&ab.Interdit()==false) {
			this.ReservePar=ab.numero();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR,2);
			this.DateReservation=cal.getTime();
		  }else if (this.EmprunterPar!=0){
			  throw new ReservationException("l'objet n " + this.numero
						+ " ne peut pas etre reserve care il est deja emprunter");
		  }else if (this.ReservePar!=0){
			  throw new ReservationException("l'objet n " + this.numero
						+ " ne peut pas etre reserve car il est deja reserve jusqu'a"+this.DateReservation);
		  }else if(ab.Interdit()==true) {
			  throw new ReservationException("l'abonne n "+ab.numero()+ " ne peux pas réserver car il y a une pénalité sur lui");
		  }
			 

	}

	@Override
	public void empruntPar(Abonne ab) throws EmpruntException {
		if ((this.ReservePar == ab.numero() || this.ReservePar == 0)&&this.EmprunterPar==0&&ab.Interdit()==false) {
			this.EmprunterPar = ab.numero();
		} else if (this.ReservePar!=0){
			throw new EmpruntException("l'objet n " + this.numero
					+ " ne peut pas etre emprunter car il est reserve par l'abonne n "
					+ this.ReservePar+"jusqu'a"+this.DateReservation);
		}else if(ab.Interdit()==true){
			throw new EmpruntException("l'abonne n "+ab.numero()+ " ne peux pas emprunter car il y a une pénalité sur lui");
		}
		else{
			throw new EmpruntException("l'objet n " + this.numero
					+ " ne peut pas etre emprunter car il est deja emprunter par l'abonne n "+this.EmprunterPar);
		}

	}

	@Override
	public void retour() {
		this.EmprunterPar = 0;
		this.ReservePar=0;

	}

	public int reservePar() {
		return this.ReservePar;
	}

	public int EmpruntePar() {
		return this.EmprunterPar;
	}

	public void FinDeReservation() {
		this.ReservePar = 0;
	}

	public String titre() {
		return this.Titre;
	}

	@Override
	public void run() {
		this.ReservePar = 0;
		
	}
}