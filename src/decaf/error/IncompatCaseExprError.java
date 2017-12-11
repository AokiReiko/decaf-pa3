package decaf.error;

import decaf.Location;

/**
 * written by reiko
 * PA2
 */
public class IncompatCaseExprError extends DecafError {

	private String wrong;

	public IncompatCaseExprError(Location location, String wrong) {
		super(location);
		this.wrong = wrong;
	}

	@Override
	protected String getErrMsg() {
		return "incompatible case expr: " + wrong + " given, but int expected";
	}
 
} 
  