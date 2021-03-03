package objet_et_abbonne_et_exceptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import servicesEtServeur.Notification_mail;

public class ObjetAEmprunter extends TimerTask implements Document {
	private int numero;
	private String Titre;
	private int ReservePar = 0;
	private int EmprunterPar = 0;
	private Date DateReservation;
	private List<Abonne> aboones_a_notifie = new ArrayList<Abonne>();//cette liste pour informer les aboones que l'objet est valable pour reserve

	ObjetAEmprunter(int num, String titre) {
		this.numero = num;
		this.Titre = titre;
	}

	public void AjouterListeDAttend(Abonne ab) {
		aboones_a_notifie.add(ab);
	}


	@Override
	public int numero() {
		return this.numero;
	}

	@Override
	public void reservationPour(Abonne ab) throws ReservationException {
		if (this.ReservePar == 0 && this.EmprunterPar == 0 && ab.Interdit() == false) {
			this.ReservePar = ab.numero();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, 2);
			this.DateReservation = cal.getTime();
		} else if (this.EmprunterPar != 0) {
			throw new ReservationException(
					"L'objet n° " + this.numero + " ne peut pas être réserver car il est déjà emprunter");
		} else if (this.ReservePar != 0) {
			throw new ReservationException("L'objet n° " + this.numero
					+ " ne peut pas être réserver car il est déjà réserver jusqu'au " + this.DateReservation);
		} else if (ab.Interdit() == true) {
			throw new ReservationException(
					"L'abonné n° " + ab.numero() + " ne peut pas réserver car il a une pénalité à cause d'un retard");
		}

	}

	@Override
	public void empruntPar(Abonne ab) throws EmpruntException {
		if ((this.ReservePar == ab.numero() || this.ReservePar == 0) && this.EmprunterPar == 0
				&& ab.Interdit() == false) {
			this.ReservePar=0;
			this.EmprunterPar = ab.numero();
		} else if (this.ReservePar != 0) {
			throw new EmpruntException("L'objet n° " + this.numero
					+ " ne peut pas être emprunter car il est déjà réserver par l'abonné n° " + this.ReservePar
					+ "jusqu'au " + this.DateReservation);
		} else if (ab.Interdit() == true) {
			throw new EmpruntException(
					"L'abonne n° " + ab.numero() + " ne peut pas réserver car il a une pénalité à cause d'un retard");
		} else {
			throw new EmpruntException("L'objet n° " + this.numero
					+ " ne peut pas être emprunter car il est déjà emprunter par l'abonné n° " + this.EmprunterPar);
		}

	}

	@Override
	public void retour() {
		this.EmprunterPar = 0;
		this.ReservePar = 0;
		if (this.aboones_a_notifie.isEmpty()==false) {
			for (Abonne ab : this.aboones_a_notifie) {
				try {
					Notification_mail.EnvoyeMail(ab.getMail());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		this.aboones_a_notifie.clear();

	}

	public int reservePar() { //retourner le numero d'aboone qu'a reserve l'objet ou 0 s'il y a pas de reservation
		return this.ReservePar;
	}

	public int EmpruntePar() {//retourner le numero d'aboone qu'a emprunte l'objet ou 0 s'il y a pas de emprunte
		return this.EmprunterPar;
	}

	public void FinDeReservation() {//une methode pour arreter la reservation si on veut pas de timer grace au claender et cette methode
		this.ReservePar = 0;
	}

	public String titre() {
		return this.Titre;
	}

	@Override
	public void run() { //timer  qui declanhe apres la fin de la periode de reservation
		this.ReservePar = 0;
		if (this.EmprunterPar == 0 && this.aboones_a_notifie.isEmpty()==false) {
			for (Abonne ab : this.aboones_a_notifie) {
				try {
					Notification_mail.EnvoyeMail(ab.getMail()); //notifier tout la list d'attende
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.aboones_a_notifie.clear();//vider la list d'attende pour cet objet afin que les person dans la list ne recoit plus des mail
		}
	}
}