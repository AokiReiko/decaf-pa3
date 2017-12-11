
package decaf.error;   

import decaf.Location;

/**
 * written by reiko
 * PA2
 */
public class DoStmtCondTypeError extends DecafError {
  
	private String type;  

	public DoStmtCondTypeError(Location location, String type) {
		super(location);
		this.type = type; 
	}  
	@Override
	protected String getErrMsg() { 
		return "The condition of Do Stmt requestd type bool but " + 
	this.type + " given"; 
	}
  
}   
  