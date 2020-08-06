/**
 * Created on 20/mar/08
 */
package it.nch.eb.flatx.generator.xls;

/**
 * @author gdefacci
 */
public class RowInfo  {
	private final XlsExpression expression;
	private final String name;
	private final GenTypeModel type;
	private boolean	optional;
	
	public RowInfo(XlsExpression expression, String name, GenTypeModel type) {
		this(expression, name, type, false);
	}
	
	public RowInfo(XlsExpression expression, String name, GenTypeModel type, boolean optional) {
		this.expression = expression;
		this.name = name;
		this.type = type;
		this.optional = optional;
	}
	
	public XlsExpression getExpression() {
		return expression;
	}
	
	public String getName() {
		return name;
	}
	
	public GenTypeModel getType() {
		return type;
	}
	
	public boolean isOptional() {
		return optional;
	}
	
	public boolean isSymbol() {
		return expression instanceof XlsSymbol;
	}
	
	public boolean isXPath() {
		return expression instanceof XlsXpath;
	}
	
	public boolean isLiteral() {
		return expression instanceof XlsQuoted;
	}
	
	public boolean isCustom() {
		return expression instanceof XlsCustom;
	}
	
	public boolean isVoid() {
		return expression instanceof XlsVoid;
	}
	
	public boolean isFullElement() {
		if (isXPath()) {
			String val = ((XlsXpath)expression).getValue();
			return val==null || "".equals(val); 
		}
		return false;   
	}
	
	public RowInfo copy() {
		return new RowInfo(this.expression != null ? this.expression.copy(): null, 
				this.name, 
				this.type );
	}

	public String toString() {
		return "\nName : " + this.name 
			+ "\texpr : " + (this.expression != null ? this.expression.getValue() : "null")  
			+ "\tType : " + this.type;
	}
	
	
	
}