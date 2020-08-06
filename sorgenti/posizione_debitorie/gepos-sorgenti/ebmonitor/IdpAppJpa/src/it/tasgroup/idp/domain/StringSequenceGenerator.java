package it.tasgroup.idp.domain;

import java.io.Serializable;
import java.util.Properties;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGeneratorFactory;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

public class StringSequenceGenerator extends  org.hibernate.id.SequenceGenerator {

	public static final String MAX_LO = "max_lo";
	
//	private static final Log log = LogFactory.getLog(StringSequenceGenerator.class);
	
	private int maxLo;
	private int lo;
	private long hi;
	private Class returnClass;

	public void Mconfigure(Type type, Properties params, Dialect d) throws MappingException {
		super.configure(type, params, d);
		maxLo = PropertiesHelper.getInt(MAX_LO, params, 9);
		lo = maxLo + 1; // so we "clock over" on the first invocation
		returnClass = type.getReturnedClass();
	}

	public synchronized Serializable generate(SessionImplementor session, Object obj) 
	throws HibernateException {
		if (maxLo < 1) {
			//keep the behavior consistent even for boundary usages
			//deleted
//			long val = ( (Number) super.generate(session, obj) ).longValue();
			String val = ( (String) super.generate(session, obj) );
			//delete
//			if (val == 0) val = ( (Number) super.generate(session, obj) ).longValue();			
			//deleted
//			return IdentifierGeneratorFactory.createNumber( val, returnClass );
			
			//VERSIONE 3
			return val;				
		}
		if ( lo>maxLo ) {
			long hival = ( (Number) super.generate(session, obj) ).longValue();
			lo = (hival == 0) ? 1 : 0;
			hi = hival * ( maxLo+1 );
//			if ( log.isDebugEnabled() )
//				log.debug("new hi value: " + hival);
		}

		return IdentifierGeneratorFactory.createNumber( hi + lo++, returnClass );		
	}
	
} 
