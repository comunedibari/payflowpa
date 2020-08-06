package it.tasgroup.idp.enabler;

import java.sql.Types;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle9iDialect;


public class OracleDialectExtended extends Oracle9iDialect {
	

	/*** Resources ***/
	@PersistenceContext(unitName="IrisPU")
	private EntityManager entityManager;
	
 	
	private final Log log = LogFactory.getLog(this.getClass());

	    
	    
	public OracleDialectExtended() 
	{ 
		super(); // workaround for http://opensource.atlassian.com/projects/hibernate/browse/HHH-2304
		registerHibernateType( Types.NVARCHAR, Hibernate.STRING.getName() );
//		registerHibernateType( Types.INTEGER, Hibernate.INTEGER.getName() );
		registerHibernateType( Types.NUMERIC, Hibernate.INTEGER.getName() );
		registerHibernateType( Types.TIMESTAMP, Hibernate.TIMESTAMP.getName() );
//		registerHibernateType( Types.CHAR,1, Hibernate.STRING.getName() ); 
//		registerHibernateType( Types.CHAR,255, Hibernate.STRING.getName() );
//		registerHibernateType( Types.LONGVARCHAR,255, Hibernate.STRING.getName() );			
	}	    

}
