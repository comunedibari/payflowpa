/**
 * 
 */
package it.tasgroup;

import it.nch.eb.common.utils.loaders.ResourceLoaders;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Admin
 *
 */
public class BaseXsdTest {

	 public static void main(String[] args) throws SAXException, IOException {

	        // 1. Specify you want a factory for RELAX NG
	        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
	        
	        // 2. Load the specific schema you want. 
	        // Here I load it from a java.io.File, but we could also use a 
	        // java.net.URL or a javax.xml.transform.Source
	        File schemaLocation = new File("D:/java/projects/flowmanager-svn/dsengine/datastore-engine/resources/idp/xsd/v0/idp/xsd/IdP.AllineamentoPendenze.xsd");
	        
	        // 3. Compile the schema.
	        Schema schema = factory.newSchema(schemaLocation);
	    
	        // 4. Get a validator from the schema.
	        Validator validator = schema.newValidator();
	        
	        // 5. Parse the document you want to check.
	        String input = "inputs/v03/inp.xml";

	        Source source = new SAXSource(new InputSource( ResourceLoaders.CLASSPATH.loadInputStream(input) ));
	        // 6. Check the document
	        try {
				validator.validate(source);
	            System.out.println(input + " is valid.");
	        }
	        catch (SAXException ex) {
	            System.out.println(input + " is not valid because ");
	            System.out.println(ex.getMessage());
	        }  
	        
	    }

}
