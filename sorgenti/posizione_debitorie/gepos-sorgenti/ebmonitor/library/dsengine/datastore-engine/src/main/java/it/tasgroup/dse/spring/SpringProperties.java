/**
 * 07/set/2009
 */
package it.tasgroup.dse.spring;

import it.nch.eb.common.utils.loaders.PropertiesDelegate;
import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.util.Properties;

import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author gdefacci
 */
public class SpringProperties extends PropertiesDelegate {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = 
		ResourcesUtil.createLogger(SpringProperties.class);

	public SpringProperties(String url) {
		super(newProperties(url));
	}
	
	public SpringProperties(Properties delegate) {
		super(delegate);
	}
	
	static Properties newProperties(String url) {
		try {
//			InputStream is = ResourceUtils.getURL(url).openStream();
//			Properties res = new Properties();
//			res.load(is);
			
			//nuova versione
			log.info("Reading Properties file " + url);
//			Resource resource = new ClassPathResource(url);
			Resource resource = new ClassPathResource(url, SpringProperties.class.getClassLoader());
			Properties res = new Properties();			
			res.load(resource.getInputStream());			
						
			return res;
			
		} catch (Exception e) {
			log.info("Error Reading Properties file " + url + " message " + e.getMessage());
			throw new RuntimeException(e);
		} 			


			
		
	}

}
