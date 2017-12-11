
package decaf.error;

import decaf.Location;

/**
 * written by reiko
 * PA2
 */
public class ConditionNotUniqueError extends DecafError {

	public ConditionNotUniqueError(Location location) {
		super(location);
	}

	@Override
	protected String getErrMsg() {
		return "condition is not unique";
	}
 
}  
    