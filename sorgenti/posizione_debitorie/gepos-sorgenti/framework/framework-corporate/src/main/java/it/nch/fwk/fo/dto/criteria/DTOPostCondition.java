package it.nch.fwk.fo.dto.criteria;


import it.nch.fwk.fo.exceptions.NotImplementedException;

import java.util.Enumeration;
import java.util.Vector;

public class DTOPostCondition implements ICondition{

   private Vector		vConditions	= new Vector(); 	// tokenLogico = condizione
	private Object[]	items;	// tokenLogico|condition
	private Object[]	condition; 	// key|operator|value    @version  0.2
   private Class     hibClass;   // mapping class a cui si riferiscono le condition  @version 0.2

   public DTOPostCondition() throws NotImplementedException{
		throw new NotImplementedException(DTOPostCondition.class.toString()
				 + " has not been implemented yet.");
	}

	public DTOPostCondition(String tokenLogico,
							String key,
							String operator,
							String value)
	throws NotImplementedException{
		throw new NotImplementedException(DTOPostCondition.class.toString()
				 + " has not been implemented yet.");
	}

	public DTOPostCondition(String 		tokenLogico,
							ICondition	innerCondition)
	throws NotImplementedException
	{
//		super();
//		this.addInnerCondition(tokenLogico,innerCondition);
		throw new NotImplementedException(DTOPostCondition.class.toString()
				 + " has not been implemented yet.");
	}

	/**
	 *  [PRJ_Base] DTOPostCondition.java
	 * =============================================
	 * it.nch.fwk.fo.dto.DTOPostCondition.java
	 *		.addCondition [void]
	 * @todo	:Implement PostCondition Logic
	 *
	 * @param 	tokenLogico
	 * @param 	key
	 * @param 	operator
	 * @param 	value
	 * @throws 	NotImplementedException
	 *
	 * @deprecated		Not implemented yet.
	 *
	 * @see it.nch.fwk.fo.dto.criteria.ICondition#addCondition(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addCondition  (String tokenLogico,
								String key,
								String operator,
								String[] value)
	throws Exception
	{
//		condition 			= new String[CONDITION_LENGHT];
//		condition[KEY] 		= key;
//		condition[VALUE]	= value;
//		condition[OPERATOR] = operator;
//
//		items 				= new Object[ITEMS_LENGHT];
//		items[LOGIC_TOKEN]	= tokenLogico;
//		items[CONDITION]	= condition.clone();
//
//		vConditions.add(items.clone());
//
//		condition 		= null;
//		items			= null;

		throw new NotImplementedException(DTOPostCondition.class.toString()
				 + " has not been implemented yet.");
	}

	/**
	 *  [PRJ_Base] DTOPostCondition.java
	 * =============================================
	 * it.nch.fwk.fo.dto.criteria.DTOPostCondition.java
	 *		.addInnerCondition [void]
	 * @todo	:Implement PostCondition Logic
	 *
	 * @param 	tokenLogico
	 * @param	innerCondition
	 * @throws 	Exception
	 *
	 * @see 	it.nch.fwk.fo.dto.criteria.ICondition#addInnerCondition(java.lang.String, it.nch.fwk.fo.dto.criteria.ICondition)
	 *
	 * @deprecated		Not implemented yet.
	 */
	public void addInnerCondition(String tokenLogico, ICondition innerCondition)
	throws Exception
	{
//		items 				= new Object[ITEMS_LENGHT];
//		items[LOGIC_TOKEN]	= tokenLogico;
//		items[CONDITION]	= innerCondition;
//		vConditions.add(items.clone());
//
//		items		= null;
	}

	/**
	 *  [PRJ_Base] DTOPreCondition.java											<br>
	 * =============================================							<br>
	 * it.nch.fwk.fo.dto.DTOPreCondition.java					<br>
	 *		.elaborateCondition [Object]										<br>
	 * =============================================							<br>
	 * @todo	:Apply Business Logic to parse Post Conditions 	   	 			<br>
	 * 			and elaborate them to fill DTOs returning values;				<br>
	 *
	 * @throws Exception
	 *
	 * @see it.nch.fwk.fo.dto.criteria.ICondition#elaborateCondition()
	 *
	 * @deprecated		Not implemented yet.
	 */
	public Object elaborateCondition() throws Exception
	{
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * TODO : - Apply Business Logic to parse Post Conditions 	   	 *
		 * 			and elaborate them to fill DTOs returning values;	 *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		throw new NotImplementedException(DTOPostCondition.class.toString()
				 + " has not been implemented yet.");
	}

	public Vector toVector()
	{ return vConditions; }

	public Enumeration toEnumeration()
	{ return vConditions.elements(); }

	public Object[] toArray()
	{ return vConditions.toArray(new Object[2]); }

	public String toString()
	{ return vConditions.toString(); }
   
   public Class getHibClass() {
      return hibClass;
   }

   public void setHibClass(Class hibClass) {
      this.hibClass = hibClass;
   }

}
