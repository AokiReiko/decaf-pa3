
package decaf.error;

import decaf.Location;

/**
 * By reiko
 * PA2
 */
public class SuperInStaticFuncError extends DecafError {

	public SuperInStaticFuncError(Location location) {
		super(location);
	}

	@Override
	protected String getErrMsg() {
		return "can not use super in static function";
	}

}
