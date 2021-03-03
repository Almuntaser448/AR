package client_serveur;

public class ReservationException extends Exception {
	private static final long serialVersionUID = 1L;

	ReservationException(String message){
		super(message);
	}
	@Override
	public String toString() {
		return this.getMessage();
	}
}
