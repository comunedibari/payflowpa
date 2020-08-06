package it.nch.fwk.fo.dto.criteria;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

public interface ICondition extends Serializable{

	public static final int KEY 				= 0;
	public static final int OPERATOR 			= 1;
	public static final int VALUE 				= 2;

	public static final int LOGIC_TOKEN			= 0;
	public static final int CONDITION 			= 1;

	public static final int CONDITION_LENGHT 	= 3;
	public static final int ITEMS_LENGHT 		= 2;

	public void addCondition (String tokenLogico,
							  String key,
							  String operator,
							  String[] value)
	throws Exception;

	public void addInnerCondition(String		tokenLogico,
								         ICondition 	innerCondition)
	throws Exception;

	public Vector toVector();

	public Enumeration toEnumeration();

	public Object[] toArray();

	public String toString();

	public Object elaborateCondition() throws Exception;
   
   public Class getHibClass();
   
   public void setHibClass(Class hibClass);
   
}
