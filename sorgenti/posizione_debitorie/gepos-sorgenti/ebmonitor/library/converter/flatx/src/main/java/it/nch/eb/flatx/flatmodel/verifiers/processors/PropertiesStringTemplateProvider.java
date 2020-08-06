/**
 * Created on 13/mar/08
 */
package it.nch.eb.flatx.flatmodel.verifiers.processors;

import java.util.Properties;

import org.antlr.stringtemplate.StringTemplate;


/**
 * @author gdefacci
 */
public class PropertiesStringTemplateProvider implements StringTemplateProvider {
	
	final Properties props;

	public PropertiesStringTemplateProvider(Properties props) {
		this.props = props;
	}

	public StringTemplate getTemplate(String name) {
		String templ = props.getProperty(name);
		if (templ==null) {
			throw new IllegalArgumentException("cant get the template named [" + name 
					+ "] inside the properties \n" + this.props);
		}
		StringTemplate stringTemplate = new StringTemplate(templ);
		return stringTemplate;
	}

}
