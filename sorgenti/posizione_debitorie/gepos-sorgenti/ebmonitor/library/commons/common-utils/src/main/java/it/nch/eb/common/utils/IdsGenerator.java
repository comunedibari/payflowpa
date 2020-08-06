/**
 * Created on 24/apr/07
 */
package it.nch.eb.common.utils;


/**
 * @author gdefacci
 */
public class IdsGenerator implements IIdsGenerator {
	
	private long lastNumber;

	public String generateId() {
		if (lastNumber == 1679615) 
			lastNumber = 0;	//36 raisedTo: 4 = 1679616
		else lastNumber++;
		String ms = new Long(System.currentTimeMillis()).toString();
		String inc = this.fillNumerico(Long.toString(lastNumber,32).toUpperCase(),4);
		return ms+inc;
	}
	
	public String fillNumerico(String num, int n)	{
		return fill(num, n, "0");
	}
	
	public String fillBlank(String num, int n)	{
		return fill(num, n, " ");
	} 

	protected String fill(String num, int n, String filler) {
		String x = num;		
		for (int i=x.length(); i<n; i++) {
			x=filler+x;
		}
		return x;
	}

}
