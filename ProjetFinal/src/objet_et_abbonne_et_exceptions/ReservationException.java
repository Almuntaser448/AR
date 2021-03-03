package objet_et_abbonne_et_exceptions;

public class ReservationException extends Exception {
	private static final long serialVersionUID = 1L;

	public ReservationException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return this.getMessage();
	}
}
