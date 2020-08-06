/**
 * Created on 07/gen/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;


/**
 * @author gdefacci
 */
public class Quantifier {

	public static final int	UNBOUNDED	= -1;
	
	public final int min;
	public final int max;
	
	public Quantifier(int min, int max) {
		if ((max!=UNBOUNDED && max<min)|| (max!=UNBOUNDED && max<0) || (min<0)) throw new IllegalStateException(stringfy(min, max));
		
		this.min = min;
		this.max = max;
	}
	
	private static String stringfy(int min, int max) {
		return "(" + min + ".." + (max==UNBOUNDED ? "unbounded" : ""+max) + ")";
	}
	
	public boolean isOptional() {
		return min == 0;
	}
	
	public boolean can(int n) {
		return max==UNBOUNDED || n<max;
	}
	
	public boolean must(int n) {
		return n<min;
	}
	
	public boolean isIn(int n) {
		if (max == UNBOUNDED) return n>= min;
		else if (n >= min && n<=max) return true;
		else return false;
	}
	
	public boolean returnOneElementAtMax() {
		return max == 1;
	}

	public String toString() {
		return stringfy(min, max);
	}
	
}
