/**
 * 
 */
package it.tasgroup.report.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jempbox.xmp.XMPMetadata;
import org.apache.jempbox.xmp.XMPSchemaBasic;
import org.apache.jempbox.xmp.XMPSchemaDublinCore;
import org.apache.jempbox.xmp.XMPSchemaPDF;
import org.apache.jempbox.xmp.pdfa.XMPSchemaPDFAId;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptorAFM;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptorDictionary;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.util.ImageIOUtil;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Jpeg;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;



/**
 * Classe di supporto nella creazione, conversione e manipolazione di PDFA mediante PDFBox.
 * 
 * @author pazzik
 *
 */
public class PDFAUtils {
	
	protected static final Logger LOGGER = LogManager.getLogger(PDFAUtils.class);
	
	private PDFAUtils(){
		
		//utility class, should not be instantiated.
		
	 }
	
	/**
	 * @param pdf
	 * @return
	 */
	public static byte[] convertPDFToPDFA(PDDocument doc) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
				
				//PDDocument doc = embedAllFonts(pdf);
				
				manageXMPMetaData(doc);
				
				manageOutputIntent(doc);
				
				doc.save(baos);
				
		} catch (Exception ex) {
			
			LOGGER.error("Error on convertPDFToPDFA: ", ex);
		 	
		} finally {
			
			try {
				
				if (doc != null)
					doc.close();
				
				baos.close();
				
				
			} catch (IOException e1) {
				
				LOGGER.error("Error on convertPDFToPDFA: ", e1);
			}
		}
		
		return baos.toByteArray();
		
	}

	/**
	 * @param doc
	 * @throws Exception
	 */
	private static void manageOutputIntent(PDDocument doc) throws Exception {
		
		InputStream colorProfile = PDFAUtils.class.getResourceAsStream("/common/icc/AdobeRGB1998.icc");
		
		PDOutputIntent oi = new PDOutputIntent(doc, colorProfile); 
		
		oi.setInfo("Adobe RGB (1998)"); 
		
		oi.setOutputCondition("Adobe RGB (1998)"); 
		
		oi.setOutputConditionIdentifier("Adobe RGB (1998)"); 
		
		oi.setRegistryName("http://www.color.org");
		
		PDDocumentCatalog cat = doc.getDocumentCatalog();
		
		cat.addOutputIntent(oi);
		
	}

	/**
	 * @param doc
	 * @throws Exception 
	 */
	private static void manageXMPMetaData(PDDocument doc) throws Exception{
		
		PDMetadata metadata = new PDMetadata(doc);
		
		PDDocumentCatalog cat = doc.getDocumentCatalog();
		
		cat.setMetadata(metadata);

		XMPMetadata xmp = new XMPMetadata();
		
		PDDocumentInformation info = doc.getDocumentInformation();
		
		XMPSchemaPDF pdfSchema = xmp.addPDFSchema();
				
		if(info.getKeywords()!=null)
			pdfSchema.setKeywords(info.getKeywords());
		
		if(info.getProducer()!=null)
			pdfSchema.setProducer(info.getProducer());
		
		LOGGER.debug("manageXMPMetaData XMPSchemaPDF " + pdfSchema);
		
		XMPSchemaBasic basicSchema = xmp.addBasicSchema();
		
		if(info.getModificationDate()!=null)
			basicSchema.setModifyDate(info.getModificationDate());
		
		if(info.getCreationDate()!=null)
			basicSchema.setCreateDate(info.getCreationDate());
		
		if(info.getCreator()!=null)
			basicSchema.setCreatorTool(info.getCreator());
		
		basicSchema.setMetadataDate(new GregorianCalendar());
		
		LOGGER.debug("manageXMPMetaData XMPSchemaBasic " + basicSchema);
		
		XMPSchemaDublinCore dcSchema = xmp.addDublinCoreSchema();
		
		if (info.getTitle()!=null)
			dcSchema.setTitle(info.getTitle());
		
		if (info.getAuthor()!=null)
			dcSchema.addCreator(info.getAuthor());
		
		if (info.getSubject()!=null)
			dcSchema.setDescription(info.getSubject());
		
		LOGGER.debug("manageXMPMetaData XMPSchemaDublinCore " + dcSchema);
		
		XMPSchemaPDFAId pdfaid = new XMPSchemaPDFAId(xmp);
		
		xmp.addSchema(pdfaid);
		
		pdfaid.setConformance("B");
		
		pdfaid.setPart(1);
		
		pdfaid.setAbout("");	
		
		LOGGER.debug("manageXMPMetaData XMPSchemaPDFAId " + pdfaid);
		
		LOGGER.debug("manageXMPMetaData before importXMPMetadata");
		
		xmp.save("XMPMetadata.txt");
		
		metadata.importXMPMetadata(xmp.asByteArray());
		
		LOGGER.debug("manageXMPMetaData after importXMPMetadata");
			
	}
	
	
	/**
	 * Metodo che interviene sulla ricevuta per predisporre alla stampa i link presenti 
	 * sulla ricevuta IRIS in aderenza allo standard PDFA.
	 * 
	 * @param pdf  i bytes del pdf da portare in formato PDFA
	 * 
	 * @return i bytes del pdf convertiti in formato PDFA
	 */
	public static byte[] fixPrintFlag(byte[] pdf) {
		
		byte[] output = null;
		
		ByteArrayInputStream bais = null;
				
		ByteArrayOutputStream baos = null;
			
		PDDocument document = null;
		
		try {

			bais = new ByteArrayInputStream(pdf);
			
			baos = new ByteArrayOutputStream();
			
			document = PDDocument.loadNonSeq(bais, null);
			
			List<PDPage> allPages = document.getDocumentCatalog().getAllPages();
			
			for (PDPage page : allPages) {
				
				List<PDAnnotation>	annotations = page.getAnnotations();
			
				for (PDAnnotation annot: annotations) {
				
					if (annot instanceof PDAnnotationLink) {
						
							PDAnnotationLink link = (PDAnnotationLink)annot;
							
							link.setPrinted(true);
						
						}
			 		}
		 		}
		
			document.save(baos);
			
			output = baos.toByteArray();
		
		 } catch (Exception ex) {
			 
			 LOGGER.error("Error on fixPrintFlag: ", ex);
			 	
		 } finally {
				
				try {
					
					if (document != null)
							document.close();
					
					if (baos != null)
							baos.close();
					
					if (bais != null)
							bais.close();
					
				} catch (IOException e1) {
					
					LOGGER.error("Error on fixPrintFlag: ", e1);
				}
			}
		
		return output;
		
		
	}


	public static void main(String[] args) throws Exception {
		
		String filePath1 = "D:/TAS/MY/DOC/SportelloAmico/RicevutaPDF_A5_Asl8_prod.pdf";
		
		String filePath2 = "D:/TAS/MY/DOC/SportelloAmico/RicevutaPDFA_A5_Asl8_prod.pdf";
		
		PDDocument doc = null;
		
		try {

				doc = PDDocument.load(filePath1);
				//convertPDFToPDFA(null);
				
				manageXMPMetaData(doc);
				
				manageOutputIntent(doc);
				
				doc.save(filePath2);
		
		}catch(Exception e){
			
			 LOGGER.error("Error on main: ", e);
			
		} finally {
			
			try {
				
				if (doc != null)
					doc.close();
				
				
			} catch (IOException e1) {
				
				LOGGER.error("Error on main: ", e1);
			}
		}
	}
	
//	public static byte[] convertPDFToImageItext(byte[] attach) throws IOException, DocumentException{
//	
//	//ByteArrayInputStream bais = new ByteArrayInputStream(attach);
//	
//	ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	
//	PdfReader pdfReader = new PdfReader(attach);
//	
//
////	Image jpeg = Jpeg.getInstance(pdfStamper.getImportedPage(pdfReader, 1));
////	
////	float i = jpeg.getPlainWidth();
////	
////	float y = jpeg.getPlainHeight();
//	
//	int npages = pdfReader.getNumberOfPages();
//
//	
//	//int page = 0;
//	
//	for (int i = 0; i <= npages; i++)
//	{ 
//		PdfPage pdPage =  (PdfPage) pdfReader.getPageN(i);
//		
//	    BufferedImage bim = pdPage.getImage(BufferedImage.TYPE_INT_RGB, 300);
//	    
//	    ImageIO.write(bim, "jpg", baos);
//	}
//
//	return jpeg.getRawData();
//}


	
	/**
	 * Aggiunge in coda ad un PDF un'immagine JPEG in formato A5.
	 * 
	 * @param pdf lo stream del documento
	 * 
	 * @param attach lo stream dell'immagine
	 * 
	 * @return lo stream del documento con l'immagine appesa in coda.
	 */
	public static byte[] addPdfImage(byte[] pdf, byte[] attach) {
		
		PdfReader pdfReader = null;
		
		PdfStamper pdfStamper = null;
		
		ByteArrayOutputStream baos = null;
		
		 byte[] output = null;
		
	    try {
	    	
	      baos = new ByteArrayOutputStream();
	    	
	      pdfReader = new PdfReader(pdf);

	      pdfStamper = new PdfStamper(pdfReader,baos);

	      Image image = Jpeg.getInstance(attach);
	      
//	      float h = image.getPlainHeight();
//	      
//	      float w = image.getPlainHeight();
	      
	      image.scaleAbsolute(PageSize.A5.getHeight(), PageSize.A5.getWidth());

	      //for (int i=1; i <= pdfReader.getNumberOfPages(); i++){

	          PdfContentByte content = pdfStamper.getUnderContent(pdfReader.getNumberOfPages());

	          image.setAbsolutePosition(0f, 0f);

	          content.addImage(image);
	      //}
	          
	      pdfStamper.close();
	     
	      output = baos.toByteArray();
	      
	      return output;

	    } catch (IOException e) {
	    	
	    	 LOGGER.error("Error on addPdfImage: ", e);
	  	      
	    } catch (DocumentException e) {
  	    	
	    	 LOGGER.error("Error on addPdfImage: ", e);
	  	      
	    } finally{
	    	 	
	    	try{
	    		
	    		if (pdfReader!= null)
	    			pdfReader.close();
	    		
	    		if (baos!=null)
	    			baos.close();
	    		
		    	if (pdfStamper!= null)
		    			pdfStamper.close();
		    	
	    	} catch (IOException e) {
		    	
	    		 LOGGER.error("Error on addPdfImage: ", e);
	  	      
	  	    } catch (DocumentException e) {
	  	    	
	  	    	 LOGGER.error("Error on addPdfImage: ", e);
	  	    }
	    }
	    
	    return null;
	  }
	
	/**
	 * Converte un PDF in un'immagine JPEG.
	 * 
	 * @param attach
	 * @return
	 * @throws IOException
	 */
	public static byte[] convertPDFToImage(byte[] attach) throws IOException{
		
	
		PDDocument document = null;
		
		ByteArrayOutputStream baos = null;
		
		ByteArrayInputStream bais = null;
		
		byte[] output = null;
	
		try {
			
			baos = new ByteArrayOutputStream();
					
			bais = new ByteArrayInputStream(attach);
			
			document = PDDocument.loadNonSeq(bais, null);
	
			List<PDPage> pdPages = document.getDocumentCatalog().getAllPages();
			
			//int page = 0;
			
			for (PDPage pdPage : pdPages) { 
				//page++;
			    BufferedImage bim = pdPage.convertToImage(BufferedImage.TYPE_INT_RGB, 150);
			    
			    ImageIOUtil.writeImage(bim, "jpg", baos, 150, 0.8f);
			}
			
			output = baos.toByteArray();
			
		}catch(IOException e){
			
			LOGGER.error("Error on convertPDFToImage: ", e);
			
			throw e;
			
		} finally {
			
			try {
				
				if (document != null)
						document.close();
				
				if (baos != null)
						baos.close();
				
				if (bais != null)
					bais.close();
				
			} catch (IOException e1) {
				
				LOGGER.error("Error on convertPDFToImage: ", e1);
			}
		}
		
		return output;
	}
	
//public static byte[] manipulateAttachment(byte[] attach) throws IOException, DocumentException {
//
//ValidationResult validation = validatePDFA(attach);
//
//if (!validation.isValid()) {
//	
//	attach = convertPDFToImage(attach);
//
//}
//
//return attach;
//
//}
//


	

	 
	/**
	 * Crea una copia del documento PDF 
	 * 
	 * in cui sostituisce tutti i font con i font Liberation embedded di IRIS.
	 * 
	 * @param pdf lo stream del documento originale
	 * @return lo stream del documento copia con i fonts sostituiti da quelli embedded
	 * 
	 * @throws COSVisitorException
	 * @throws IOException
	 */
	public static PDDocument embedAllFonts(byte[] pdf) {
		
		PDDocument doc = null;
		
		//PDDocument docCopy = null;
		
		try{
		
			ByteArrayInputStream bais = new ByteArrayInputStream(pdf);
					
			doc = PDDocument.loadNonSeq(bais,null);
			
			//docCopy = new PDDocument(doc.getDocument());
			
			//docCopy.setDocumentInformation(doc.getDocumentInformation());
			
			// Create the new font objects by loading some TrueType fonts into the document
			InputStream regularFontIS = PDFAUtils.class.getClassLoader().getResourceAsStream("/common/fonts/Liberation/LiberationSans-Regular.ttf");
			
			InputStream boldFontIS = PDFAUtils.class.getClassLoader().getResourceAsStream("/common/fonts/Liberation/LiberationSans-Bold.ttf");
					
			PDFont regularFont = PDTrueTypeFont.loadTTF(doc, regularFontIS);
	
			PDFont boldFont = PDTrueTypeFont.loadTTF(doc, boldFontIS);
					
			// copies each page of the original document in a new document
			List<PDPage> allPages = doc.getDocumentCatalog().getAllPages();
		
			for(PDPage page : allPages){
	
//				COSDictionary pageDict = page.getCOSDictionary();
				
//				COSDictionary newPageDict = new COSDictionary(pageDict);
//				
//				PDPage newPage = new PDPage(newPageDict);
//				
//				docCopy.addPage(newPage);
				
				Map<String,PDFont> pageFonts = page.getResources().getFonts();
				
				Map<String,PDFont> newFonts= new HashMap<String, PDFont>();
				
				for(Entry<String, PDFont> entry : pageFonts.entrySet()){
					
					String newFontKey = entry.getKey();
					
					PDFont oldFont = entry.getValue();
							
					if(!isEmbedded(oldFont)) {
						
						PDFontDescriptor oldFontDescriptor = oldFont.getFontDescriptor();
						
						String oldFontName = oldFontDescriptor.getFontName();
						
						if (oldFontName.endsWith("Bold"))
							
							newFonts.put(newFontKey, boldFont);
					
						else 
							
							newFonts.put(newFontKey, regularFont);
						
					} else
						
						newFonts.put(newFontKey, oldFont);
			          
				}
				
				page.getResources().setFonts(newFonts);
				
			}
			
		} catch (Exception e){
			
			LOGGER.error("Error on embedAllFonts: ", e);
			
		} finally {
			
			try {
				
				if (doc != null)
						doc.close();
				
//				if (docCopy!=null)
//					docCopy.close();
				
			} catch (IOException e1) {
				
				LOGGER.error("Error on embedAllFonts: ", e1);
			}
		}
		
		
		return doc;
	}
	
	private static boolean isEmbedded(PDFont font){
		
		PDFontDescriptor fontDescriptor = font.getFontDescriptor();
		
		if (null != fontDescriptor && fontDescriptor instanceof PDFontDescriptorDictionary) {
			
			PDFontDescriptorDictionary fontDescriptorDictionary = (PDFontDescriptorDictionary)fontDescriptor;
            
            if(null == fontDescriptorDictionary.getFontFile() && null == fontDescriptorDictionary.getFontFile2() && null == fontDescriptorDictionary.getFontFile3())
               
            	return false;
		}
		
		if (null != fontDescriptor && fontDescriptor instanceof PDFontDescriptorAFM)
			
			return false;
		
		return true;
	}

}
