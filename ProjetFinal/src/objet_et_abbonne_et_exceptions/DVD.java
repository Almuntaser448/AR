package objet_et_abbonne_et_exceptions;

public class DVD extends ObjetAEmprunter {
	private boolean Adulte;

	public DVD(int num, String titre, boolean adulte) {
		super(num, titre);
		this.Adulte = adulte;
	}

	@Override
	public void reservationPour(Abonne ab) throws ReservationException {
		if (this.Adulte == true) {// this.adulte c'est pour savoir si le dvd pour les 16+ ab.adulte c'est pour
									// savoir si l'aboone est 16+
			if (ab.Adulte() == true) {
				super.reservationPour(ab);
			} else {
				throw new ReservationException("Vous devez être majeur pour réserver cette objet");
			}
		} else {
			super.reservationPour(ab);
		}
	}

	@Override
	public void empruntPar(Abonne ab) throws EmpruntException {
		if (this.Adulte == true) {
			if (ab.Adulte() == true) {
				super.empruntPar(ab);

			} else {
				throw new EmpruntException("Vous devez être majeur pour emprunter cette objet");
			}
		} else {
			super.empruntPar(ab);
		}
	}

	public boolean Adulte() {
		return this.Adulte;
	}
}