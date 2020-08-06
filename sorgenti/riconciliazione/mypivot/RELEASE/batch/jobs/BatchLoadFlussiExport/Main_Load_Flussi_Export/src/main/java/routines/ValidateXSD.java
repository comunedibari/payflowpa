package routines;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ValidateXSD {
	
	javax.xml.validation.Validator validator = null;
	List<SAXParseException> exceptions =  new LinkedList<SAXParseException>();
	
	public ValidateXSD(String pathXsd) throws SAXException {
		String locationtXSDValidator_1 = pathXsd;
		javax.xml.validation.SchemaFactory factorytXSDValidator_1 = javax.xml.validation.SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		javax.xml.validation.Schema schematXSDValidator_1 = factorytXSDValidator_1.newSchema(new java.io.File(locationtXSDValidator_1));
		validator = schematXSDValidator_1.newValidator();
		  validator.setErrorHandler(new ErrorHandler()
		  {
		    @Override
		    public void warning(SAXParseException exception) throws SAXException
		    {
		      exceptions.add(exception);
		    }
		
		    @Override
		    public void fatalError(SAXParseException exception) throws SAXException
		    {
		      exceptions.add(exception);
		    }
		
		    @Override
		    public void error(SAXParseException exception) throws SAXException
		    {
		      exceptions.add(exception);
		    }
		  });		
	}

	public boolean validaStringa(String bilancio) throws SAXException, IOException {
		
		exceptions =  new LinkedList<SAXParseException>();
		
		java.io.InputStream xml = new ByteArrayInputStream((bilancio).getBytes("UTF-8"));		
		
		javax.xml.transform.Source xmlFile = new javax.xml.transform.stream.StreamSource(xml);
		validator.validate(xmlFile);
		return exceptions.size() == 0;
	}
	
	public static void main(String[] args) throws SAXException, IOException {
		String a = "<bilancio xmlns=\"http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/\"> " +
					  "<capitolo>" +
					 "   <codCapitolo>COD1</codCapitolo>" +
					 "   <codUfficio>UFF1</codUfficio>" +
					 "   <accertamento>" +
					 "     <codAccertamento>ACC.X</codAccertamento>" +
					 "     <importo>0.49</importo>" +
					 "   </accertamento>" +
					 " </capitolo>" +
					"</bilancioa>";
		
		String b = "<bilancio xmlns=\"http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/\"> " +
		  "<capitolo>" +
		 "   <codCapitolo>COD1</codCapitolo>" +
		 "   <codUfficio>UFF1</codUfficio>" +
		 "   <accertamento>" +
		 "     <codAccertamento>ACC.X</codAccertamento>" +
		 "     <importo>0.49</importo>" +
		 "   </accertamento>" +
		 " </capitolo>" +
		"</bilancio>";		
		
		
		ValidateXSD vxsd = new ValidateXSD("d:/PagInf_Dovuti_Pagati_6_2_0.xsd");
		try {
			System.out.println("valida  a :: " + vxsd.validaStringa(a));
		} catch (Exception e) {
			System.out.println("valida  a :: false");
		}
		
		try {
			System.out.println("valida  b :: " + vxsd.validaStringa(b));
		} catch (Exception e) {
			System.out.println("valida  b :: false");
		}
		
		
		
	}
	
}
