package decaf.error;  

import decaf.Location;

/**
 * written by reiko
 * PA2
 */
public class SuperMemberError extends DecafError {


	public SuperMemberError(Location location) {
		super(location);
	} 

	@Override
	protected String getErrMsg() {
		return "super.member_var is not supported";
	}
  
}   
         