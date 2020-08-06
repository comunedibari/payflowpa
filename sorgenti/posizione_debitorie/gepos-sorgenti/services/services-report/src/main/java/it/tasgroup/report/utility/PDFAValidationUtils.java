///**
// * 
// */
//package it.tasgroup.report.utility;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
//
//import org.apache.pdfbox.preflight.PreflightDocument;
//import org.apache.pdfbox.preflight.ValidationResult;
//import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
//import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
//import org.apache.pdfbox.preflight.parser.PreflightParser;
//import org.apache.pdfbox.preflight.utils.ByteArrayDataSource;
//
///**
// * @author pazzik
// *
// */
//public class PDFAValidationUtils {
//	
//	public static ValidationResult validatePDFA(byte[] pdf) throws IOException{
//
//		ByteArrayInputStream is = new ByteArrayInputStream(pdf);
//		
//		ByteArrayDataSource bads = new ByteArrayDataSource(is);
//		
//		return validatePDFA(bads);
//		
//	}	
//
//	public static ValidationResult validatePDFA(String filePath) throws IOException{
//		
//		FileDataSource fds = new FileDataSource(filePath);
//		
//		return validatePDFA(fds);
//			
//	}
//
//	public static ValidationResult validatePDFA(DataSource ds) throws IOException{
//		
//		ValidationResult result = null;
//
//		PreflightParser parser = new PreflightParser(ds);
//		
//		try {
//
//		    /* Parse the PDF file with PreflightParser that inherits from the NonSequentialParser.
//		     * Some additional controls are present to check a set of PDF/A requirements. 
//		     * (Stream length consistency, EOL after some Keyword...)
//		     */
//		    parser.parse();
//
//		    /* Once the syntax validation is done, 
//		     * the parser can provide a PreflightDocument 
//		     * (that inherits from PDDocument) 
//		     * This document process the end of PDF/A validation.
//		     */
//		    PreflightDocument document = parser.getPreflightDocument();
//		    
//		    document.validate();
//
//		    // Get validation result
//		    result = document.getResult();
//		    
//		    document.close();
//
//		} catch (SyntaxValidationException e) {
//		    /* the parse method can throw a SyntaxValidationException 
//		     * if the PDF file can't be parsed.
//		     * In this case, the exception contains an instance of ValidationResult  
//		     */
//		    result = e.getResult();
//		}
//
//		// display validation result
//		if (result.isValid()) {
//			
//		    System.out.println("The pdf is a valid PDF/A-1b file");
//		
//		} else {
//			
//		    System.out.println("The pdf is not valid, error(s) :");
//		    
//		    for (ValidationError error : result.getErrorsList()) {
//		    	
//		        System.out.println(error.getErrorCode() + " : " + error.getDetails());
//		    }
//		    
//		}
//		
//		return result;
//	}
//
//	public static void testValidatePDFA(){
//		
//		String filePath="D:/IRIS_RICEVUTA_PAGAMENTO_14222782161660019DEF.pdf";
//		
//		//String filePath1 = "D:/Projects/IRIS_NEW/services/services-resload/src/main/resources/iris-ear-container/JasperReports/design/pagamento/DDP_Ricevuta_PDF.pdf";
//		
//		//String filePath = "C:/Users/pazzik/Downloads/IRIS_RICEVUTA_PAGAMENTO_14169981222700081DEF.pdf";
//		
//		//String filePath2 = "D:/TAS/MY/DOC/SportelloAmico/RicevutaPDF_A5_Asl11_prod.pdf";
//		//+ "RicevutaPDF_A5_Asl11_prod.pdf";
//		
//		try {
//			
//			ValidationResult res = validatePDFA(filePath);
//			
//			System.out.println(filePath);
//			
//			System.out.println("XmpMetadata: " + res.getXmpMetaData());
//			
//			System.out.println("isValid: " + res.isValid());
//		
//		} catch (IOException e) {
//			
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		
//	}
//
//	public static void main(String[] args) {
//		testValidatePDFA();
//	}
//
//}
