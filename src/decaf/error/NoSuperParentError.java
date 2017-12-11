
package decaf.error;

import decaf.Location;

/**
 * example：no legal Main class named 'Main' was found<br>
 * PA2
 */
public class NoSuperParentError extends DecafError {

	private String name;

	public NoSuperParentError(Location location, String name) {
		super(location);
		this.name = name;
	}
  
	@Override 
	protected String getErrMsg() { 
		return "no parent class exist for " + name;
	}  
 
}   
     