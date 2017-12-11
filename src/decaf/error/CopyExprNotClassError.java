
package decaf.error; 

import decaf.Location;

/**
 * written by reiko
 * PA2
 */
public class CopyExprNotClassError extends DecafError {

	private String type;

	public CopyExprNotClassError(Location location, String type) {
		super(location);
		this.type = type;
	}
 
	@Override
	protected String getErrMsg() {
		return "expected class type for copy expr but " + this.type + " given";
	}
 
} 
  