/**
 * 
 */
package it.nch.fwk.xml.validation;

import java.io.Reader;
import java.util.Set;

/**
 * @author gdefacci
 */
public interface ValidationService {

	Set/*<QualifiedError>*/validate(Reader rdr);

}