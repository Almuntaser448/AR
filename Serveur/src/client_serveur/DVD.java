package client_serveur;

public class DVD extends ObjetAEmprunter {
	private boolean Adulte;

	DVD(int num, String titre, boolean adulte) {
		super(num, titre);
		this.Adulte = adulte;
	}

	@Override
	public void reservationPour(Abonne ab) throws ReservationException {
		if (this.Adulte == true) {
			if (ab.Adulte() == true) {
				super.reservationPour(ab);
			} else {
				  throw new ReservationException("vous devez etre majeur");
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
				  throw new EmpruntException("vous devez etre majeur");
			}
		} else {
			super.empruntPar(ab);
		}
	}

	public boolean Adulte() {
		return this.Adulte;
	}
}