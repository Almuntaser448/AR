package objet_et_abbonne_et_exceptions;

public interface Document {
	int numero();

	void reservationPour(Abonne ab) throws ReservationException;

	void empruntPar(Abonne ab) throws EmpruntException;

	// retour document ou annulation réservation
	void retour();
}
