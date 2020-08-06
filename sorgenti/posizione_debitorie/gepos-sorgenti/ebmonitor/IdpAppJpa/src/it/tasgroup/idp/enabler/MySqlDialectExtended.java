package it.tasgroup.idp.enabler;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.MappingException;
import org.hibernate.dialect.MySQLDialect;


public class MySqlDialectExtended extends MySQLDialect {
	
	

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
	    @Override
	    public String getSequenceNextValString(String sequenceName) throws MappingException {

	        if (sequenceName == null)
	            System.out.println("sequenceName is null...");
	        
	        String filterName = sequenceName.substring(sequenceName.indexOf(".")+1,sequenceName.length());
	        String queryMini = "SELECT NEXTVAL('"+filterName+"')";	        
	                
	        return queryMini;
	    }

	    @Override
	    protected String getDropSequenceString(String sequenceName) throws MappingException {
	        return "";
	    }

	    @Override
	    public boolean supportsSequences() {
	        return true;
	    }	
	    
	    
	    
		public MySqlDialectExtended() 
		{ 
			super(); // workaround for http://opensource.atlassian.com/projects/hibernate/browse/HHH-2304
			registerHibernateType( Types.CHAR, Hibernate.STRING.getName() ); 
			registerHibernateType( Types.CHAR,1, Hibernate.STRING.getName() ); 
			registerHibernateType( Types.CHAR,255, Hibernate.STRING.getName() );
			registerHibernateType( Types.LONGVARCHAR,255, Hibernate.STRING.getName() );			
		}	    

}
