package it.nch.fwk.fo.dto.criteria;


import it.nch.fwk.fo.exceptions.NotImplementedException;

import java.util.Enumeration;
import java.util.Vector;

/** [PRJ_Base] DTOPreCondition.java											<br>
 * =============================================							<br>
 * it.nch.fwk.fo.dto.DTOPreCondition							<br>
 * =============================================							<br>
 * <b>Esempio 1:</b>														<br>
 * Condition: "WHERE a.nome LIKE 'p%' OR b.reddito > 10000"					<br>
 * --> :																	<br>
 * DTOPreCondition pre = 													<br>
 * 		new DTOPreCondition("WHERE", "a.nome", "LIKE", "p%");				<br>
 * pre.addConditionCondition("OR", "b.reddito", ">","10000");				<br>
 * =============================================							<br>
 * <b>Esempio 2:</b>														<br>
 * Condition: 	"WHERE (a.nome LIKE 'p%' OR b.reddito > 10000") 			<br>
 * 				OR (a.cognome = 'ROSSI' AND b.datanascita 					<br>
 * 				BETWEEN '01/01/1900' AND '01/01/2000')"						<br>
 * --> :																	<br>
 * DTOPreCondition innerPre1 = 												<br>
 * 			new DTOPreCondition("","a.nome","LIKE","p%");					<br>
 * innerPre1.addCondition("OR","b.reddito",">","10000");					<br>
 * DTOPreCondition innerPre2 = 												<br>
 * 			new DTOPreCondition("","a.cognome","=","ROSSI");				<br>
 * innerPre2.addCondition("AND","b.datanascita",							<br>
 * 						  "BETWEEN","01/01/1900|01/01/2000");				<br>
 * DTOPreCondition pre = new DTOPreCondition();								<br>
 * pre.addCondition("WHERE",	innerPre1);									<br>
 * pre.addCondition("OR",		innerPre2);									<br>
 * =============================================							<br>
 *
 */
public class DTOPreCondition implements ICondition
{
   private Vector		vConditions	= new Vector(); 	// tokenLogico = condizione
	private Object[]	items;      // tokenLogico|condition
	private Object[]	condition; 	// key|operator|value  @version 0.2
   private Class     hibClass;   // mapping class a cui si riferiscono le condition  @version 0.2

	public DTOPreCondition(){
		super();
	}

	/**
	 *  [PRJ_Base]:																<br>
	 * it.nch.fwk.fo.dto.criteria.DTOPreCondition CONSTRUCTOR	<br>
	 * =============================================							<br>
	 * <b>Esempio:</b>															<br>
	 * Condition: "WHERE a.nome LIKE 'p%' OR b.reddito > 10000"					<br>
	 * --> :																	<br>
	 * DTOPreCondition pre = 													<br>
	 * 		new DTOPreCondition("WHERE", "a.nome", "LIKE", "p%");				<br>
	 * pre.addConditionCondition("OR", "b.reddito", ">","10000");				<br>
	 * =============================================							<br>
	 * @param tokenLogico
	 * @param key
	 * @param operator
	 * @param value
	 * @throws Exception
	 *
	 */
	public DTOPreCondition(String tokenLogico,
							String key,
							String operator,
							String[] value)
	throws Exception
	{
		super();
		this.addCondition(tokenLogico,
						  key,
						  operator,
						  value);
	}

	public DTOPreCondition(String tokenLogico, String key, String operator, String value) throws Exception
	{
	    super();
	    this.addCondition(tokenLogico, key, operator, value);
	}
	
	/**
	 *  [PRJ_Base]:																<br>
	 * it.nch.fwk.fo.dto.criteria.DTOPreCondition CONSTRUCTOR	<br>
	 * =============================================							<br>
	 * <b>Esempio:</b>															<br>
	 * Condition: 	"WHERE (a.nome LIKE 'p%' OR b.reddito > 10000") 			<br>
	 * 				OR (a.cognome = 'ROSSI' AND b.datanascita 					<br>
	 * 				BETWEEN '01/01/1900' AND '01/01/2000')"						<br>
	 * --> :																	<br>
	 * DTOPreCondition innerPre1 = 												<br>
	 * 			new DTOPreCondition("","a.nome","LIKE","p%");					<br>
	 * innerPre1.addCondition("OR","b.reddito",">","10000");					<br>
	 * DTOPreCondition innerPre2 = 												<br>
	 * 			new DTOPreCondition("","a.cognome","=","ROSSI");				<br>
	 * innerPre2.addCondition("AND","b.datanascita",							<br>
	 * 						  "BETWEEN","01/01/1900|01/01/2000");				<br>
	 * DTOPreCondition pre = new DTOPreCondition();								<br>
	 * pre.addCondition("WHERE",	innerPre1);									<br>
	 * pre.addCondition("OR",		innerPre2);									<br>
	 * =============================================							<br>
	 * @param tokenLogico
	 * @param innerCondition
	 * @throws Exception
	 *
	 */
	public DTOPreCondition(String 		tokenLogico,
							     ICondition	innerCondition)
	throws Exception
	{
		super();
		this.addInnerCondition(tokenLogico,innerCondition);
	}

	/**
	 *  [PRJ_Base] DTOPreCondition.java											<br>
	 * =============================================							<br>
	 * it.nch.fwk.fo.dto.DTOPreCondition.java					<br>
	 *		.addCondition [void]												<br>
	 * =============================================							<br>
	 * <b>Esempio:</b>															<br>
	 * Condition: "WHERE a.nome LIKE 'p%' OR b.reddito > 10000"					<br>
	 * --> :																	<br>
	 * DTOPreCondition pre = 													<br>
	 * 		new DTOPreCondition("WHERE", "a.nome", "LIKE", "p%");				<br>
	 * pre.addConditionCondition("OR", "b.reddito", ">","10000");				<br>
	 * @param tokenLogico
	 * @param key
	 * @param operator
	 * @param value
	 * @throws Exception
	 *
	 * @see it.nch.fwk.fo.dto.criteria.ICondition#addCondition(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addCondition  (String tokenLogico,
								String key,
								String operator,
								String[] value)
	throws Exception
	{
		condition 			  = new Object[CONDITION_LENGHT];
		condition[KEY]      = key;
		condition[OPERATOR] = operator;
		condition[VALUE]    = value.clone();

		items 				= new Object[ITEMS_LENGHT];
		items[LOGIC_TOKEN]	= tokenLogico;
		items[CONDITION]	= condition.clone();

		vConditions.add(items.clone());

		condition 		= null;
		items			= null;
	}
	
	public void addCondition (String tokenLogico, String key, String operator, String value) throws Exception
	{
	    String [] s = {value};
	    addCondition (tokenLogico, key, operator, s);
	}

	/**
	 *  [PRJ_Base] DTOPreCondition.java											<br>
	 * =============================================							<br>
	 * it.nch.fwk.fo.dto.criteria.DTOPreCondition.java			<br>
	 *		.addInnerCondition [void]											<br>
	 * =============================================							<br>
	 * <b>Esempio:</b>															<br>
	 * Condition: 	"WHERE (a.nome LIKE 'p%' OR b.reddito > 10000") 			<br>
	 * 				OR (a.cognome = 'ROSSI' AND b.datanascita 					<br>
	 * 				BETWEEN '01/01/1900' AND '01/01/2000')"						<br>
	 * --> :																	<br>
	 * DTOPreCondition innerPre1 = 												<br>
	 * 			new DTOPreCondition("","a.nome","LIKE","p%");					<br>
	 * innerPre1.addCondition("OR","b.reddito",">","10000");					<br>
	 * DTOPreCondition innerPre2 = 												<br>
	 * 			new DTOPreCondition("","a.cognome","=","ROSSI");				<br>
	 * innerPre2.addCondition("AND","b.datanascita",							<br>
	 * 						  "BETWEEN","01/01/1900|01/01/2000");				<br>
	 * DTOPreCondition pre = new DTOPreCondition();								<br>
	 * pre.addCondition("WHERE",	innerPre1);									<br>
	 * pre.addCondition("OR",		innerPre2);									<br>
	 * @param tokenLogico
	 * @param innerCondition
	 * @throws Exception
	 *
	 * @see it.nch.fwk.fo.dto.criteria.ICondition#addInnerCondition(java.lang.String, it.nch.fwk.fo.dto.criteria.ICondition)
	 */
	public void addInnerCondition(String tokenLogico, ICondition innerCondition)
	throws Exception
	{
		items 				= new Object[ITEMS_LENGHT];
		items[LOGIC_TOKEN]	= tokenLogico;
		items[CONDITION]	= innerCondition;
		vConditions.add(items.clone());

		items		= null;
	}

	/**
	 *  [PRJ_Base] DTOPreCondition.java											<br>
	 * =============================================							<br>
	 * it.nch.fwk.fo.dto.DTOPreCondition.java					<br>
	 *		.elaborateCondition [Object]										<br>
	 * =============================================							<br>
	 * // TODO
	 *
	 * @throws Exception
	 *
	 * @see it.nch.fwk.fo.dto.criteria.ICondition#elaborateCondition()
	 */
	public Object elaborateCondition() throws Exception
	{
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * TODO : - Apply Business Logic to parse DTO Conditions 	   	 *
		 * 			and elaborate them to create an Hibernate Criteria;	 *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

		throw new NotImplementedException(this.toString()
				 + ".elaboratePreCondition()"
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
   public Object clone() {
	   DTOPreCondition preCond = new DTOPreCondition();
	   if (this.vConditions != null)
		   preCond.vConditions = (Vector)this.vConditions.clone();
	   if (this.condition != null)
		   preCond.condition = (Object[])this.condition.clone();
	   //preCond.hibClass = (Class)this.hibClass.clone();
	   if (this.items != null)
		   preCond.items = (Object[])this.items.clone();
	   return preCond;	   
   }

}
