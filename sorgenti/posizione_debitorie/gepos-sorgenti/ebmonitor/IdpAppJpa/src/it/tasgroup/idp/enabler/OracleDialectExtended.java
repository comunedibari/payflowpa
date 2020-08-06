package it.tasgroup.idp.enabler;

import java.sql.Types;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.hibernate.dialect.OracleDialect;


public class OracleDialectExtended extends OracleDialect {
	

	/*** Resources ***/
	@PersistenceContext(unitName="IdpAppJpa")
	private EntityManager entityManager;
	
 	
//	private final Log log = LogFactory.getLog(this.getClass());

    /***
     * Overvides the superclass method because the MySQL dialect does not support sequences.
     * We will have our own custom sequence generation that is saved within a single database
     * table.  This database table will contain 2 fields (sequence_name and sequence_value).
     * Sequence_name's that are passed in as a parameter that don't exist will be created, set
     * to zero, incremented by one, and the SQL to grab the current value will be returned to
     * the caller.  Sequence_name's that do exist will be incremented by one, and the SQL to
     * grab the current value will be returned to the caller.
     * @param sequenceName
     * @return
     * @throws org.hibernate.MappingException
     */
//    @Override
//    public String getSequenceNextValString(String sequenceName) throws MappingException {
//        Integer sequenceCount = 0; 
//        int nextSequenceValue = 0;
//        
//        Properties prop = this.getDefaultProperties();
//        System.out.println(" Functions>>> " + this.getFunctions());
//        System.out.println(" sequenceName>>> " + sequenceName);
//       
//        if (sequenceName == null)
//            System.out.println("sequenceName is null...");
//        
//        String filterName = sequenceName.substring(sequenceName.indexOf(".")+1,sequenceName.length());
//        //MYSQL
//        //String queryMini = "SELECT NEXTVAL('"+filterName+"')";	        
//        
//        String queryMini = "SELECT TEST_ID_SEQ.NEXTVAL  FROM DUAL";        
//        
//        try {	        	
//			Query query = entityManager.createNativeQuery(queryMini);
//			List resultList = (List) query.getResultList();
//			Iterator iter = resultList.iterator();
//			while (iter.hasNext()) {
//				Object object = (Object) iter.next();
//				System.out.println("TEST_ID_SEQ.NEXTVAL="+object.toString());
//			}
//			return queryMini;
//        }
//        catch(MappingException me) {
//        }
//        catch(Exception e) {
//        }	        
//        return queryMini;
//    }


//    @Override
//    public boolean supportsSequences() {
//        return true;
//    }

//	@Override
//	public boolean supportsIdentityColumns() {
////		return  super.supportsIdentityColumns();
//		return true;
//	}	
	    
	    
	    
	
	public OracleDialectExtended() 
	{ 
		super(); // workaround for http://opensource.atlassian.com/projects/hibernate/browse/HHH-2304
		registerHibernateType( Types.NVARCHAR, Hibernate.STRING.getName() );
		registerHibernateType( Types.TIMESTAMP, Hibernate.TIMESTAMP.getName() );
		
//		registerHibernateType( Types.INTEGER, Hibernate.INTEGER.getName() );
//		registerHibernateType( Types.NUMERIC, Hibernate.INTEGER.getName() );
//		registerHibernateType( Types.DECIMAL, Hibernate.BIG_DECIMAL.getName() );
//		registerHibernateType(Types.NUMERIC, 10, Hibernate.INTEGER.getName());
//		registerHibernateType(Types.NUMERIC, 15, Hibernate.BIG_DECIMAL.getName());

//		registerHibernateType( Types.CHAR,1, Hibernate.STRING.getName() ); 
//		registerHibernateType( Types.CHAR,255, Hibernate.STRING.getName() );
//		registerHibernateType( Types.LONGVARCHAR,255, Hibernate.STRING.getName() );
		
		registerHibernateType (java.sql.Types.DECIMAL,13, Hibernate.INTEGER.getName ());
		registerHibernateType (java.sql.Types.DECIMAL,17, Hibernate.BIG_DECIMAL.getName ());
		registerHibernateType (java.sql.Types.NUMERIC,13, Hibernate.INTEGER.getName ()) ;
		registerHibernateType (java.sql.Types.NUMERIC,17, Hibernate.BIG_DECIMAL.getName ()) ;
		
		registerColumnType (java.sql.Types.DECIMAL,13, Hibernate.INTEGER.getName ());
		registerColumnType (java.sql.Types.DECIMAL,17, Hibernate.BIG_DECIMAL.getName ());
		registerColumnType (java.sql.Types.NUMERIC,13, Hibernate.INTEGER.getName ()) ;
		registerColumnType (java.sql.Types.NUMERIC,17, Hibernate.BIG_DECIMAL.getName ()) ;		
	}		

}
