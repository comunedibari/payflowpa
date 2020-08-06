package it.nch.is.fo.uuidgen;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.UUIDHexGenerator;
import org.hibernate.type.StringType;

public class UUIDGenerator20 extends UUIDHexGenerator {

	private static UUIDGenerator20 generator = null;

	private static boolean initialized = false;

	private static void init() {
		if (!initialized) {
			Properties props = new Properties();
			// props.setProperty("separator", "");
			generator = new UUIDGenerator20();
			generator.configure(StringType.INSTANCE, props, null);
			initialized = true;
		}
	}

	public UUIDGenerator20() {
	}

	public static UUIDGenerator20 getInstance() {
		init();
		return generator;
	}

	public static String generate() {
		if (!initialized)
			init();
		return (String) generator.generate(null, null);

	}
	
	/**
	 * This method overrides UUIDHexGenerator's generate
	 */
	public Serializable generate(SessionImplementor session, Object obj) {
		StringBuffer sb = new StringBuffer(20)
			.append( format( getIP() + getHiTime() + getLoTime() ) )
			.append( format( getJVM() ) )
			.append( format( getCount() ) );
		
		String key = sb.toString();
		key = key.toUpperCase(); // a gentile richiesta dell'HOST
		
		return key;
	}

}
