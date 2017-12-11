
package decaf.error;   

import decaf.Location;

/**
 * written by reiko
 * PA2
 */
public class CopyExprIncompatError extends DecafError {

	private String src;
	private String dist;

	public CopyExprIncompatError(Location location, String src,String dist) {
		super(location);
		this.src = src;
		this.dist = dist;
	} 
 
	@Override
	protected String getErrMsg() {
		return "For copy expr, the source " + 
	this.src + " and the destination " + this.dist + " are not same";
	}
 
} 
  