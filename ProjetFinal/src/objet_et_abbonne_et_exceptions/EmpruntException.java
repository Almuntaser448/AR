package objet_et_abbonne_et_exceptions;

public class EmpruntException extends Exception {
	private static final long serialVersionUID = 1L;

	EmpruntException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return this.getMessage();
	}
}
