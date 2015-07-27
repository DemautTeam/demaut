package ch.vd.demaut.commons.exceptions;

public class NotYetImplementedException extends UnsupportedOperationException {

	private static final long serialVersionUID = 7951285441580372318L;
	
	public NotYetImplementedException() {
		super("Not implemented yet");
	}

}
