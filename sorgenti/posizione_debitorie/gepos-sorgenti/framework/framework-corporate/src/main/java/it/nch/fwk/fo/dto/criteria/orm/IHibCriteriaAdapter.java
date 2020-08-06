package it.nch.fwk.fo.dto.criteria.orm;

//import org.hibernate.criterion.Criterion;

/**
 * 
 * @author sberisso
 * @since 16-12-2005
 * @version 0.1
 *
 */
public interface IHibCriteriaAdapter 
         extends ICriteriaAdapter
{
   //public Criterion getHibExpression();
   
   //public void setHibExpression(Criterion hibExpression);

   //public String getHQL();
   
   public String dtoPreConditionToString();
}
