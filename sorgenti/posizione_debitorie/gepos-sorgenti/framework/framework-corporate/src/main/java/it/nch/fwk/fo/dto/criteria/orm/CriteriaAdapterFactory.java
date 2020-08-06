package it.nch.fwk.fo.dto.criteria.orm;

/**
 * 
 * @author sberisso
 * @since 14-12-2005
 * @version 0.1
 *
 */
public class CriteriaAdapterFactory {
   
   // constants
   public static final int NONE = -1;
   public static final int HIBERNATE = 0;

   // properties
   private static ICriteriaAdapter instance = null;
 
   // constructors
   protected CriteriaAdapterFactory() {}
   
   // methods
   public static synchronized ICriteriaAdapter getInstance()
   {
      return getInstance(HIBERNATE);
   }

   public static synchronized ICriteriaAdapter getInstance(int ormType)
   {
      if (instance == null)
      {
         switch (ormType)
         {
            case HIBERNATE:
            default:
               instance = new HibernateCriteriaAdapter();
               break;
         }
      }
      return instance;
   }
   
}
