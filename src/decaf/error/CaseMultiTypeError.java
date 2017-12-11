
package decaf.error;

import decaf.Location;

/**
 * written by reiko
 * PA2
 */
public class CaseMultiTypeError extends DecafError {

	private String right;
	private String wrong;
	public CaseMultiTypeError(Location location, String wrong, String right) {
		super(location);
		this.right = right;
		this.wrong = wrong;
	}
	@Override
	protected String getErrMsg() {
		return "type: " + this.wrong + " is different with other expr's type " + this.right;
	}
  
}    
  