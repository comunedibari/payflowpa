package it.nch.fwk.fo.dto.criteria.orm;

import it.nch.fwk.fo.dto.criteria.DTOCriteria;
import it.nch.fwk.fo.dto.criteria.ICondition;
import it.nch.fwk.fo.util.Tracer;

import java.util.Vector;

//import org.hibernate.criterion.Expression;
//import org.hibernate.criterion.LogicalExpression;

/**
 * 
 * @author sberisso
 * @since 14-12-2005
 * @version 0.1
 *
 */
public class HibernateCriteriaAdapter 
         implements IHibCriteriaAdapter 
{
   // properties
   private DTOCriteria dtoCriteria = null;
   //private ORMCriteria ormCriteria = null;
   //private org.hibernate.criterion.Criterion hibExpression = null;
   
   // constructors
   public HibernateCriteriaAdapter() 
   {
      //ormCriteria = new ORMCriteria();
   }
   
   // methods
   public DTOCriteria getDtoCriteria() {
      return dtoCriteria;
   }

   public void setDtoCriteria(DTOCriteria dtoCriteria) {
      this.dtoCriteria = dtoCriteria;
   }
/*   public ORMCriteria getOrmCriteria() {
      return ormCriteria;
   }

   public void setOrmCriteria(ORMCriteria ormCriteria) {
      this.ormCriteria = ormCriteria;
   }*/
   
   /*private Object createInstance(String propName, String propValue)
   {
      Object retObj = null;
      
      try
      {
         ICondition cond = 
            ((dtoCriteria != null) ? dtoCriteria.getPreCondition() : null);
         
         Class hibClass = cond.getHibClass();
         
         if ((cond != null) && (propName.trim().length() > 0) && (hibClass != null))
         {  
            Class propClass = null;
            String propClassName = "";
            String metName = "";
            Method met = null;
            
            propName = propName.toLowerCase();
            StringBuffer smet = new StringBuffer("get");
            char initMet = propName.substring(0, 1).toUpperCase().charAt(0);
            smet.append(initMet).append(propName.substring(1, propName.length()));
            metName = smet.toString();
            met = hibClass.getMethod(metName, null);
            propClass = met.getReturnType();

            propClassName = propClass.getName();
            if (propClassName.equals("java.lang.Boolean") ||
                  propClassName.equals("java.lang.Byte") ||
                  propClassName.equals("java.lang.Character") ||
                  propClassName.equals("java.lang.Double") ||
                  propClassName.equals("java.lang.Float") ||
                  propClassName.equals("java.lang.Integer") ||
                  propClassName.equals("java.lang.Long") ||
                  propClassName.equals("java.lang.Short") ||
                  propClassName.equals("java.lang.String"))
            {
               Constructor[] cst = propClass.getConstructors();
               for (int i = 0; i < cst.length; i++)
               {
                  int numParm = cst[i].getParameterTypes().length;
                  boolean typeOk = false;
                  
                  if (numParm == 1)
                  {
                     if (propClassName.equals("java.lang.Character"))
                        typeOk = cst[i].getParameterTypes()[0].isAssignableFrom(char.class);
                     else
                        typeOk = cst[i].getParameterTypes()[0].isAssignableFrom(String.class);
                  }
                  
                  if (typeOk)
                  {
                     Object[] args = new Object[1];
                     if (propClassName.equals("java.lang.Character"))
                        args[0] = new Character(propValue.charAt(0));
                     else
                        args[0] = propValue;
                     retObj = cst[i].newInstance(args);
                     break;
                  }
               }
            }
            else if (propClassName.equals("java.sql.Date") || propClassName.equals("java.util.Date"))
            {
               Constructor[] cst = propClass.getConstructors();
               for (int i = 0; i < cst.length; i++)
               {
                  int numParm = cst[i].getParameterTypes().length;
                  boolean typeOk = false;
                  
                  if (numParm == 1)
                  {
                     typeOk = cst[i].getParameterTypes()[0].isAssignableFrom(long.class);
                  }
                  
                  if (typeOk)
                  {
                     propValue = propValue.trim();
                     if (propValue.indexOf("/") >= 0)
                        propValue = propValue.replaceAll("/", "-");
                     if (propValue.indexOf(".") >= 0)
                        propValue = propValue.replaceAll(".", "-");
                     String datePattern = "dd-MM-yyyy";
                     if ((propValue.length() >= 4) &&
                           Character.isDigit(propValue.charAt(0)) &&
                           Character.isDigit(propValue.charAt(1)) &&
                           Character.isDigit(propValue.charAt(2)) &&
                           Character.isDigit(propValue.charAt(3)) )
                        datePattern = "yyyy-MM-dd";
                        
                     SimpleDateFormat dtf = new SimpleDateFormat(datePattern);
                     Date dt = null;
                     try {
                        dt = dtf.parse(propValue);
                     } catch (Exception pex) {}
                     if (dt != null)
                     {
                        Object[] args = new Object[1];
                        args[0] = new Long(dt.getTime());
                        retObj = cst[i].newInstance(args);
                        
                        
                        Date dtRet = (Date) retObj;
                        Tracer.debug(getClass().getName(), "createInstance", valore data = " + dtRet);
                        
                     }
                     break;
                  }
               }
            }
            else if (propClassName.equals("java.sql.Time"))
            {
               Constructor[] cst = propClass.getConstructors();
               for (int i = 0; i < cst.length; i++)
               {
                  int numParm = cst[i].getParameterTypes().length;
                  boolean typeOk = false;
                  
                  if (numParm == 1)
                  {
                     typeOk = cst[i].getParameterTypes()[0].isAssignableFrom(long.class);
                  }
                  
                  if (typeOk)
                  {
                     propValue = propValue.trim();
                     if (propValue.indexOf(".") >= 0)
                        propValue = propValue.replaceAll(".", ":");
                     String timePattern = "HH:mm:ss";
                        
                     SimpleDateFormat dtf = new SimpleDateFormat(timePattern);
                     Date dt = null;
                     try {
                        dt = dtf.parse(propValue);
                     } catch (Exception pex) {}
                     if (dt != null)
                     {
                        Object[] args = new Object[1];
                        args[0] = new Long(dt.getTime());
                        retObj = cst[i].newInstance(args);
                        
                        
                        Time tmRet = (Time) retObj;
                        Tracer.debug(getClass().getName(), "createInstance", "valore ora = " + tmRet);
                        
                     }
                     break;
                  }
               }
            }
            else if (propClassName.equals("java.sql.Timestamp"))
            {
               Constructor[] cst = propClass.getConstructors();
               for (int i = 0; i < cst.length; i++)
               {
                  int numParm = cst[i].getParameterTypes().length;
                  boolean typeOk = false;
                  
                  if (numParm == 1)
                  {
                     typeOk = cst[i].getParameterTypes()[0].isAssignableFrom(long.class);
                  }
                  
                  if (typeOk)
                  {
                     propValue = propValue.trim();
                     if (propValue.indexOf("/") >= 0)
                        propValue = propValue.replaceAll("/", "-");
                     String timeStampPattern = "yyyy-MM-dd HH:mm:ss.S";
                        
                     SimpleDateFormat dtf = new SimpleDateFormat(timeStampPattern);
                     Date dt = null;
                     try {
                        dt = dtf.parse(propValue);
                     } catch (Exception pex) {}
                     if (dt != null)
                     {
                        Object[] args = new Object[1];
                        args[0] = new Long(dt.getTime());
                        retObj = cst[i].newInstance(args);
                        
                        
                        Timestamp tmsRet = (Timestamp) retObj;
                        Tracer.debug(getClass().getName(), "createInstance", "valore timestamp = " + tmsRet);
                        
                     }
                     break;
                  }
               }
            }
         }
         return retObj;
      }
      catch (Exception ex)
      {
      	 Tracer.error("HibernateCriteriaAdapter","createInstance err: ",ex.toString(),null);
         return retObj;
      }
   }*/
   
   /*protected org.hibernate.criterion.Criterion parseArrayConditions(ICondition cond)
   {
   	org.hibernate.criterion.Criterion hibExp = null;
   	Tracer.debug(this.getClass().toString(), "parseArrayConditions", "BREAK 1.1", null);
      if (cond != null)
      {
      	Tracer.debug(this.getClass().toString(), "parseArrayConditions", "BREAK 1.2", null);
      	 org.hibernate.criterion.Criterion prevHibExp = null;
      	 org.hibernate.criterion.Criterion tokHibExp = null;
         Vector v = cond.toVector();
         for (int i = 0; i < v.size(); i++) 
         {
         	Tracer.debug(this.getClass().toString(), "parseArrayConditions", "BREAK 1.2."+i, null);
            Object[] tokenCond = (Object[]) v.get(i);
            
            tokHibExp = parseTokenCondition(tokenCond, prevHibExp);
            
            if (tokHibExp != null) {
            	Tracer.debug(this.getClass().toString(), "parseArrayConditions", "BREAK 1.3."+i, null);            	
               prevHibExp = tokHibExp;
            }   
            else {
            	Tracer.debug(this.getClass().toString(), "parseArrayConditions", "BREAK 1.4."+i, null);
               prevHibExp = null;
            }   
         }
         if (v.size() > 0)
         {
         	Tracer.debug(this.getClass().toString(), "parseArrayConditions", "BREAK 1.5 - v.size()=" + v.size(), null);
            if (tokHibExp != null) {
            	Tracer.debug(this.getClass().toString(), "parseArrayConditions", "BREAK 1.6", null);
               hibExp = tokHibExp;
            }   
         }
      }
      return hibExp;
   }*/
   
   /*protected org.hibernate.criterion.Criterion parseCondition(Object[] cond)
   {
   	  org.hibernate.criterion.Criterion hibExp = null;
      
   	Tracer.debug(this.getClass().toString(), "parseCondition", "BREAK 3.1", null);
      try
      {
         if (cond != null)
         {
         	Tracer.debug(this.getClass().toString(), "parseCondition", "BREAK 3.2", null);
            String key = "", objName = "", propName = "";
            String operator = "";
            String[] value = null;
            org.hibernate.criterion.SimpleExpression simpExp = null;
            
            if (cond[ICondition.KEY] instanceof String)
            {
            	Tracer.debug(this.getClass().toString(), "parseCondition", "BREAK 3.3", null);
               key = (String) cond[ICondition.KEY];
            }   
            
            if (cond[ICondition.OPERATOR] instanceof String) {
            	Tracer.debug(this.getClass().toString(), "parseCondition", "BREAK 3.4", null);
               operator = (String) cond[ICondition.OPERATOR];
            }   
            
            if (cond[ICondition.VALUE] instanceof String[])
            {
            	Tracer.debug(this.getClass().toString(), "parseCondition", "BREAK 3.5", null);
               value = (String[]) cond[ICondition.VALUE];
            }   
   
            // se esiste oggetto.property (per esempio "a.nome")
            // separo nome oggetto da property
            int punto = key.lastIndexOf(".");
            if (punto <= 0)
               propName = key;
            else if (punto > 0)
            {
               objName = key.substring(0, punto);
               objName += "";
               propName = key.substring(punto + 1, key.length());
            }
            Tracer.debug(this.getClass().toString(), "parseCondition", "BREAK 3.6", null);
            // creo una SimpleExpression in base all'operatore
            if (key.trim().equals("") || operator.trim().equals("") ||
                  (value == null) || (value.length == 0))
            {
            	Tracer.debug(this.getClass().toString(), "parseCondition", "BREAK 3.7", null);
               return hibExp;
            }   
            else
            {
            	Tracer.debug(this.getClass().toString(), "parseCondition", "BREAK 3.8", null);
               operator = operator.trim().toUpperCase();
               if (operator.equals("="))
               {
               	Tracer.debug(this.getClass().toString(), "parseCondition", "BREAK 3.9", null);
                  Object expVal = createInstance(propName, value[0]);
                  if (expVal != null)
                  {
                     simpExp = Expression.eq(propName, expVal);
                  Tracer.debug(this.getClass().toString(), "parseCondition", "BREAK 3.9 [" + simpExp + "]", null);
                  }
               }
               else if ((operator.equals("<>")) || (operator.equals("!=")))
               {
               	Tracer.debug(this.getClass().toString(), "parseCondition", "BREAK 3.10", null);
                  Object expVal = createInstance(propName, value[0]);
                  if (expVal != null)
                     simpExp = Expression.ne(propName, expVal);
               }
               
               
               Expression.between()
               Expression.ge()
               Expression.gt()
               Expression.in()
               Expression.le()
               Expression.like()
               Expression.lt()
               
               
               hibExp = simpExp;
            }
         }
         return hibExp;
      }
      catch (Exception ex)
      {
      	 Tracer.warn("HibernateCriteriaAdapter","parseCondition: ",ex.toString(),null);
         return hibExp;
      }
   }*/
   
   /*protected org.hibernate.criterion.Criterion parseTokenCondition(Object[] tokenCond, org.hibernate.criterion.Criterion prevHibExp)
   {
   	Tracer.debug(this.getClass().toString(), "parseTokenConditions", "BREAK 2.1", null);  
   	org.hibernate.criterion.Criterion hibExp = null;
      
      if (tokenCond != null)
      {
      	Tracer.debug(this.getClass().toString(), "parseTokenConditions", "BREAK 2.2", null);
         String token = "";
         Object condObj = null;  
         Object[] cond1 = null;
         ICondition cond2 = null;
         LogicalExpression logExp = null;
         
         
         // creo la/le condition  key|op|value
         
         condObj = tokenCond[ICondition.CONDITION];
         
         // se è una condition  key|operator|value
         if (condObj instanceof Object[])
         {
         	Tracer.debug(this.getClass().toString(), "parseTokenConditions", "BREAK 2.3", null);
            cond1 = (Object[]) condObj;
            if (cond1 != null) {
            	Tracer.debug(this.getClass().toString(), "parseTokenConditions", "BREAK 2.4", null);	
               hibExp = parseCondition(cond1);
            }   
         }
         // se è una inner condition
         else if (condObj instanceof ICondition)
         {
         	Tracer.debug(this.getClass().toString(), "parseTokenConditions", "BREAK 2.5", null);
            cond2 = (ICondition) condObj;
            hibExp = parseArrayConditions(cond2);
         }
         
         
         // creo la condition logica
         
         if (tokenCond[ICondition.LOGIC_TOKEN] instanceof String) {
         	Tracer.debug(this.getClass().toString(), "parseTokenConditions", "BREAK 2.6", null);
         
            token = (String) tokenCond[ICondition.LOGIC_TOKEN];
         }   
         
         // verifico se è un token logico (or, and)
         token = token.trim().toUpperCase();
         Tracer.debug(getClass().getName(), "parseCondition", "token [" + token + "]");
         
         Tracer.debug(getClass().getName(), "parseCondition", "prevHibExp [" + prevHibExp + "]");
         if ((!token.equals("")) && (prevHibExp != null) && 
               (hibExp != null))
         {
         	Tracer.debug(this.getClass().toString(), "parseTokenConditions", "BREAK 2.7", null);
            // creo una LogicalExpression in base al token
            if (token.equals("OR"))
            {
            	Tracer.debug(this.getClass().toString(), "parseTokenConditions", "BREAK 2.8", null);
               logExp = Expression.or(prevHibExp, hibExp);
            }
            else if (token.equals("AND"))
            {
            	Tracer.debug(this.getClass().toString(), "parseTokenConditions", "BREAK 2.9", null);
               logExp = Expression.and(prevHibExp, hibExp);
            }
            // assegno nuova LogicalExpression creata
            hibExp = logExp;            
         }
      }
      return hibExp;   
   }*/
   
   /*public void transform2ORMCriteria() 
   {
      ICondition cond =
         ((dtoCriteria != null) ? dtoCriteria.getPreCondition() : null);
      
      Criteria hibCriteria =
         ((ormCriteria != null) ? ormCriteria.getHibCriteria() : null);
      
//      if ((cond != null) && (hibCriteria != null) &&
      		//(ormCriteria.getCriteriaType() == CriteriaAdapterFactory.HIBERNATE))
      
      Tracer.debug(this.getClass().toString(), "transform2ORMCriteria", "BREAK 0.1", null);
      
      if (cond != null)
      {
      	Tracer.debug(this.getClass().toString(), "transform2ORMCriteria", "BREAK 0.2", null);
      	org.hibernate.criterion.Criterion hibExp = null;
         hibExp = parseArrayConditions(cond);
         Tracer.debug(this.getClass().toString(), "transform2ORMCriteria", "BREAK 0.3", null);
         if (hibExp != null)
         {
         	Tracer.debug(this.getClass().toString(), "transform2ORMCriteria", "BREAK 0.4", null);
            hibExpression = hibExp;
//            hibCriteria.add(hibExp);
         }
      }
   }*/

  /* public org.hibernate.criterion.Criterion getHibExpression() {
      return hibExpression;
   }

   public void setHibExpression(org.hibernate.criterion.Criterion hibExpression) {
      this.hibExpression = hibExpression;
   }
   
   public String getHQL()
   {
      if (hibExpression != null)
         return hibExpression.toString();
      else
         return "";
   }*/
   
   public String dtoPreConditionToString() 
   {
   		Tracer.debug(this.getClass().toString(), "dtoPreConditionToString", "BREAK 0.1", null);
      ICondition cond =
         ((dtoCriteria != null) ? dtoCriteria.getPreCondition() : null);
      if (cond == null)
      	return "";
      
      String preCond = arrayCondToString(cond);
      Tracer.debug(this.getClass().toString(), "dtoPreConditionToString", "preCond["+preCond+"]", null);
      return preCond;
   }
   
   protected String arrayCondToString(ICondition cond)
   {
   		Tracer.debug(this.getClass().toString(), "arrayCondToString", "BREAK 0.1", null);
      String condStr = "";
      if (cond != null)
      {
      	 String prevCondStr = "";
      	 String tokCondStr = "";
         Vector v = cond.toVector();
         for (int i = 0; i < v.size(); i++) 
         {
            Object[] tokenCond = (Object[]) v.get(i);
            Tracer.debug(this.getClass().toString(), "arrayCondToString", "tokenCond["+tokenCond+"]", null);
            tokCondStr = tokenCondToString(tokenCond, prevCondStr);
            Tracer.debug(this.getClass().toString(), "arrayCondToString", "tokCondStr["+tokCondStr+"]", null);
            if (!tokCondStr.equals("")) {
            	prevCondStr = tokCondStr;
            }   
            else {
            	prevCondStr = "";
            }   
         }
         if (v.size() > 0)
         {
            if (!tokCondStr.equals("")) {
            	condStr = tokCondStr;
            	//condStr = "WHERE " + tokCondStr; NO ELIMINARE
            }   
         }
      }
      Tracer.debug(this.getClass().toString(), "arrayCondToString", "condStr["+condStr+"]", null);
      return condStr;
   }
   
   protected String tokenCondToString(Object[] tokenCond, String prevCondStr)
   {
   	Tracer.debug(this.getClass().toString(), "tokenCondToString", "BREAK 0.1", null);
   		String condStr = "";
      
      if (tokenCond != null)
      {
         String token = "";
         Object condObj = null;  
         Object[] cond1 = null;
         ICondition cond2 = null;
         StringBuffer bufCondStr = null;
         
         /*
         // creo la/le condition  key|op|value
         */
         condObj = tokenCond[ICondition.CONDITION];
         
         // se è una condition  key|operator|value
         if (condObj instanceof Object[])
         {
            cond1 = (Object[]) condObj;
            if (cond1 != null) {
            	condStr = condToString(cond1);
            }   
         }
         // se è una inner condition
         else if (condObj instanceof ICondition)
         {
            cond2 = (ICondition) condObj;
            condStr = arrayCondToString(cond2);
         }
         
         /*
         // creo la condition logica
         */
         if (tokenCond[ICondition.LOGIC_TOKEN] instanceof String) {
            token = (String) tokenCond[ICondition.LOGIC_TOKEN];
           
         }   
         Tracer.debug(this.getClass().toString(), "tokenCondToString", "token["+token+"]", null);
         
         // verifico se è un token logico (or, and)
         token = token.trim();
         if (!token.equals("")) 
         		if (!prevCondStr.equals("")) 
         		{         			
         			if (!condStr.equals(""))
         			{
         				Tracer.debug(this.getClass().toString(), "tokenCondToString", "["+token+"] TOKEN LOGICO", null);
         				//bufCondStr = new StringBuffer("(");
         				bufCondStr = new StringBuffer("");
         				bufCondStr.append(prevCondStr);
         	
         				//creo la stringa HQL in base al token
         				bufCondStr.append(" ");
         				bufCondStr.append(token);
         				bufCondStr.append(" ");
         				bufCondStr.append(condStr);
         				//bufCondStr.append(")");
        	
         				// 	assegno nuova stringa HQL creata
         				condStr = bufCondStr.toString();            
         			}
      			} 
         		else // prevCondStr.equals("")
      			{
      				if (!condStr.equals(""))
         			{
      					Tracer.debug(this.getClass().toString(), "tokenCondToString", "["+token+"] CLAUSOLA 'WHERE'", null);
      					
      					//bufCondStr = new StringBuffer("(");
      					bufCondStr = new StringBuffer("");      				     				
      					bufCondStr.append(token);
     					bufCondStr.append(" ");
     					bufCondStr.append(condStr);     				
     					//bufCondStr.append(")");
     					
     					//assegno nuova stringa HQL creata
     					condStr = bufCondStr.toString();
         			}
      			}
      }
      Tracer.debug(this.getClass().toString(), "tokenCondToString", "condStr["+condStr+"]", null);
      return condStr;   
   }

   protected String condToString(Object[] cond)
   {
   		Tracer.debug(this.getClass().toString(), "condToString", "BREAK 0.1", null);
   	  String condStr = "";
      StringBuffer bufCondStr = null;

   	  if (cond != null)
	  {
	    String key = "";
	    String operator = "";
	    String[] value = null;
	    //org.hibernate.criterion.SimpleExpression simpExp = null;
	    
	    if (cond[ICondition.KEY] instanceof String)
	    {
	       key = (String) cond[ICondition.KEY];
	    }   
	    
	    if (cond[ICondition.OPERATOR] instanceof String) {
	       operator = (String) cond[ICondition.OPERATOR];
	    }   
	    
	    if (cond[ICondition.VALUE] instanceof String[])
	    {
	       value = (String[]) cond[ICondition.VALUE];
	    }   
	    
	    // creo la stringa HQL che corrisponde alla ICondition
	    if (key.trim().equals("") || operator.trim().equals("") ||
	          (value == null) || (value.length == 0))
	    {
	       return condStr;
	    }   
	    else
	    {
	       bufCondStr = new StringBuffer(key);
	       bufCondStr.append(" ");
	       bufCondStr.append(operator);
	       bufCondStr.append(" ");
	       if (value.length == 1)
	         bufCondStr.append(value[0]);
	       else
	       {
	       	  bufCondStr.append("(");
	          for (int i = 0; i < value.length; i++)
	          {
	             StringBuffer buf1 = 
	             	(i > 0) ? bufCondStr.append(",") : null;
	             	
	             bufCondStr.append(value[i]);
	          }
	          bufCondStr.append(")");
	       }
	       condStr = bufCondStr.toString();	    
	    }
	  }
   	Tracer.debug(this.getClass().toString(), "condToString", "condStr["+condStr+"]", null);
	  return condStr;
   }
   
}
