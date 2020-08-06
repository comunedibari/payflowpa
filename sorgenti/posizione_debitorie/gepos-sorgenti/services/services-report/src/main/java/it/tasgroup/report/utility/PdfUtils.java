package it.tasgroup.report.utility;

import it.tasgroup.services.util.enumeration.EnumAttachmentRendering;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.ImageIOUtil;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Jpeg;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfIndirectObject;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfNumber;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfStream;
import com.lowagie.text.pdf.SimpleBookmark;


/**
 * 
 * Classe di utilità per la manipolazzione di file PDF.
 * Consente, ad esempio, di aggiungere un testo in sovrimpressione o degli allegati ad un documento principale.
 * 
 */
public class PdfUtils {

	private static final float SIZE_TOLLERANCE = 1.001f; // 0.1%
	
	protected static final Logger LOGGER = LogManager.getLogger(PdfUtils.class);
	
	public static final String NR_INVOCE_PAGES = "NumeroPagineFattura";
	
	private PdfUtils(){
		
		//utility class, should not be instantiated.
		
	}

	public static byte[] markAsNullified(byte[] pdf) throws Exception {
		return PdfUtils.watermark(pdf, "ANNULLATO", 120, 0.1f, null, null, (float)Math.PI/4);
	}

	public static byte[] markAsPayed(byte[] pdf) throws Exception {
		return PdfUtils.watermark(pdf, "PAGATO", 90, 0.1f, null, null, null);
	}

	/**
	 * Applica la filigrana al pdf passato in input.
	 * @param pdf è il documento pdf a cui applicare la filigrana
	 * @param text testo della filigrana. (Se null viene riportato il valore di default "WATERMARK")
	 * @param fontsize dimensione font in "point" (se null viene impostato il default 70pt)
	 * @param opacity opacità della filigrana. 0 trasparente - 1 opaca (se null viene impostato il default 0.15)
	 * @param posX posizione orizzontale rispetto al margine sinistro del doc (se null viene calcolato in modo che il testo sia centrato in orizzontale)
	 * @param posY posizione verticale rispetto al margine in basso del doc (se null viene calcolato in modo che il testo sia centrato in verticale)
	 * @param angle angolo di rotazione (antioraria espresso in radianti) della filigrana posizione orizzontale rispetto al margine sinistro del doc 
	 *       (se null viene calcolato in modo che il testo sia disposto parallelamente alla diagonale del foglio di ogni pagina)
	 *        con nome NR_INVOCE_PAGES)
	 * @return l'array di byte contenente il documento comprensivo di filigrana
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public static byte[] watermark(byte[] pdf, String text, Integer fontsize, Float opacity, Float posX, Float posY, Float angle) throws DocumentException, IOException {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		text = (text == null) ? "WATERMARK" : text;
		fontsize = (fontsize == null) ? 70 : fontsize;
		opacity = (opacity == null) ? 0.15f : opacity;
		
		PdfReader reader = new PdfReader(pdf);
		
		BaseFont bf  = loadEmbeddedBaseFont(pdf);
		
		float txtWidth = bf.getWidthPoint(text, fontsize);

		PdfGState gs1 = new PdfGState();
		
		gs1.setFillOpacity(opacity);

		
		int lastPageToMark = reader.getNumberOfPages();
		
		PdfStamper stamp = new PdfStamper(reader, out);

		for (int i = 1; i <= lastPageToMark; i++) {
			
			PdfContentByte overContent = stamp.getOverContent(i);
			Rectangle recc = reader.getCropBox(i);
			float theta = (angle == null) ?  (float) Math.atan(recc.getHeight() / recc.getWidth()) : angle;
			
			float m1 = (float) Math.cos(theta);
			float m2 = (float) Math.sin(theta);
			float m3 = (float) -Math.sin(theta);
			float m4 = (float) Math.cos(theta);
			
			float x = (posX == null) ? (float) (recc.getWidth() + fontsize * Math.sin(theta) - txtWidth * Math.cos(theta)) / 2 : posX;
			float y = (posY == null) ? (float) (recc.getHeight() - fontsize * Math.cos(theta) - txtWidth * Math.sin(theta)) / 2: posY;
			
			overContent.saveState();
			overContent.setGState(gs1);
			overContent.beginText();
			overContent.setFontAndSize(bf, fontsize);
			overContent.setTextMatrix(m1, m2, m3, m4, x , y);
			overContent.showText(text);
			overContent.endText();
			overContent.restoreState();
		}
		
		stamp.close();

		return out.toByteArray();

	}
	
	/**
	 * Concatena n documenti pdf
	 * @param markFirstDocumentPage indica se aggiungere l'informazione contenente il numero di pagine del primo documento
	 * @param pdf array di documenti pdf da concatenare (viene utilizzata per distinguere il documento principale dall'allegato in fase 
	 *        di aggiunta della filigrana)
	 * @return
	 * @throws Exception
	 */
	public static byte[] concatenate(boolean markFirstDocumentPage, byte[]... pdf) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			int pageOffset = 0;
			List<HashMap<String, Object>> master = new ArrayList<HashMap<String, Object>>();
			Document document = null;
			PdfCopy writer = null;
			
			int firstDocumentNrOfPages = 0;
			
			for (int i = 0; i < pdf.length; i++) {
				// we create a reader for a certain document
				PdfReader reader = new PdfReader(pdf[i]);
				reader.consolidateNamedDestinations();
				// we retrieve the total number of pages
				int n = reader.getNumberOfPages();
				if (i == 0)
					firstDocumentNrOfPages = n;
				
				
				List<HashMap<String, Object>> bookmarks = SimpleBookmark.getBookmark(reader);
				if (bookmarks != null) {
					if (pageOffset != 0)
						SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
					master.addAll(bookmarks);
				}
				pageOffset += n;
				LOGGER.debug("There are " + n + " pages in pdf" + (i + 1));
				if (i == 0) {
					// step 1: creation of a document-object
					document = new Document(reader.getPageSizeWithRotation(1));
					// step 2: we create a writer that listens to the document
					writer = new PdfCopy(document, out);
					// step 3: we open the document
					document.open();
				}
				// step 4: we add content
				PdfImportedPage page;
				for (int p = 0; p < n;) {
					++p;
					page = writer.getImportedPage(reader, p);
					writer.addPage(page);
					LOGGER.debug("Processed page " + p);
				}
			}
			if (!master.isEmpty())
				writer.setOutlines(master);
			// step 5: we close the document
			document.close();
			
			byte[] concatPdf = out.toByteArray();
			
			if(markFirstDocumentPage)
				return addMetadataInfo(concatPdf, PdfUtils.NR_INVOCE_PAGES, "" + firstDocumentNrOfPages);
			
			return concatPdf;
			
			
		} finally {
			out.close();
		}
	}

	// serve solo per il test
	private static byte[] readFile(String filePathName) throws IOException {
		ByteArrayOutputStream out = null;
		BufferedInputStream in = null;
		try {
			File file = new File(filePathName);
			out = new ByteArrayOutputStream();
			in = new BufferedInputStream(new FileInputStream(file));

			int BUFFER_SIZE = 2048;
			byte[] bufferByte = new byte[BUFFER_SIZE];
			int bytesRead = 0;
			while ((bytesRead = in.read(bufferByte)) > 0) {
				out.write(bufferByte, 0, bytesRead);
				out.flush();
			}
			return out.toByteArray();
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}

	public static int getPdfPageNumber(byte[] pdf1) throws Exception {
		PdfReader reader1 = new PdfReader(pdf1);
		return reader1.getNumberOfPages();
	}

	/**
	 * 
	 * @param pdf
	 * @param pageSize
	 * @return
	 * @throws IOException
	 */
	public static Boolean checkPdfSize( byte[] pdf,String pformat) throws IOException{
		Boolean retValue = true;
//		TODO scommentare e sistemare
		if (pdf != null && pformat !=null){
			Rectangle pageSize = PageSize.getRectangle(pformat);
			int maxW = (int)pageSize.getWidth();
			int maxH = (int)pageSize.getHeight();

			PdfReader reader = new PdfReader(pdf);
			int numPages = reader.getNumberOfPages();
			
			for (int i=1;i <= numPages;i++){
				Rectangle rect = reader.getPageSizeWithRotation(i);	
				int pageH = (int)rect.getHeight();
				int pageW = (int)rect.getWidth();
				
				if (pageH > maxH || pageW > maxW){
					retValue = false;
					break;
				}
			}
			
		}
			
		return retValue;
	}
	
	/**
	 * 
	 * @param fileName
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public static Boolean hasAllEmbeddedFonts(String fileName, byte[] pdf) throws IOException {
		
		Set<String> fileFonts = listFonts(pdf);
				
		return areAllEmbedded(fileFonts);
	}
	
	/**
	 * 
	 * @param fileName
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public static Boolean areAllEmbedded(Set<String> fileFonts) throws IOException {
		
		Map<String, Set<String>> splitFonts = splitFonts(fileFonts);
		
		Set<String> listNotEmbeddedFonts = splitFonts.get("NOT_EMBEDDED");
		
		return listNotEmbeddedFonts.isEmpty();
		
	}
	
	

	/**
	 * @param fileFonts
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Set<String>> splitFonts(Set<String> fileFonts) throws IOException {
		
		String checkEmbedded1 = "embedded";
		
		String checkEmbedded2 = "subset";
		
		Map<String, Set<String>> splitFonts = new HashMap<String, Set<String>>();
		
		Set<String> notEmbeddedFonts = new HashSet<String>();
		
		Set<String> embeddedFonts = new HashSet<String>();
		
		for (String str : fileFonts) {
			
			LOGGER.debug("Font : " + str);
			
			if (!str.contains(checkEmbedded1) && !str.contains(checkEmbedded2))
				
				notEmbeddedFonts.add(str);
				
			else 
				
				embeddedFonts.add(str);
			
		}
				
		splitFonts.put("EMBEDDED", embeddedFonts);
		
		splitFonts.put("NOT_EMBEDDED", notEmbeddedFonts);
		
		return splitFonts;
	}

	/**
	 * 
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public static Set<String> listFonts(byte[] pdf) throws IOException {
		
		Set<String> set = new TreeSet<String>();
		
		PdfReader reader = new PdfReader(pdf);
		
		PdfDictionary resources;
		
		for (int k = 1; k <= reader.getNumberOfPages(); ++k) {
			
			resources = reader.getPageN(k).getAsDict(PdfName.RESOURCES);
			
			processResource(set, resources);
			
		}
		
		return set;
	}

	/**
	 * 
	 * @param set
	 * @param resource
	 */
	public static void processResource(Set<String> set, PdfDictionary resource) {
		if (resource == null)
			return;
		PdfDictionary xobjects = resource.getAsDict(PdfName.XOBJECT);
		if (xobjects != null) {
			for (PdfName key : (Set<PdfName>)xobjects.getKeys()) {
				processResource(set, xobjects.getAsDict(key));
			}
		}
		PdfDictionary fonts = resource.getAsDict(PdfName.FONT);
		if (fonts == null)
			return;
		PdfDictionary font;
		for (PdfName key : (Set<PdfName>)fonts.getKeys()) {
			font = fonts.getAsDict(key);
			String name = font.getAsName(PdfName.BASEFONT).toString();
			if (name.length() > 8 && name.charAt(7) == '+') {
				name = String.format("%s subset (%s)", name.substring(8), name.substring(1, 7));
			} else {
				name = name.substring(1);
				PdfDictionary desc = font.getAsDict(PdfName.FONTDESCRIPTOR);
				if (desc == null)
					name += " nofontdescriptor";
				else if (desc.get(PdfName.FONTFILE) != null)
					name += " (Type 1) embedded";
				else if (desc.get(PdfName.FONTFILE2) != null)
					name += " (TrueType) embedded";
				else if (desc.get(PdfName.FONTFILE3) != null)
					name += " ("+ font.getAsName(PdfName.SUBTYPE).toString().substring(1) + ") embedded";
			}
			set.add(name);
		}
	}
	
	public static byte[] addMetadataInfo(byte[] pdf, String name, String value) throws IOException, DocumentException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfReader reader = new PdfReader(pdf);
		reader.consolidateNamedDestinations();
		HashMap info = reader.getInfo();
		info.put(name, value);
		PdfStamper stamper = new PdfStamper(reader, out);
		stamper.setMoreInfo(info);
		stamper.close();
		return out.toByteArray();
	}
	
	
	public static boolean isPDFDocument(byte[] byteArray){
		
		PdfReader attachReader = null;
		
		try {
			
			attachReader = new PdfReader(byteArray);
			
			attachReader.close();
			
			return true;
			
		} catch (IOException e) {
			
			LOGGER.error("isPDFDocument - exception", e);
			
			return false;
			
		}
		
	}
	
	private static PDDocument embedAllFonts(byte[] attach) throws IOException {
		
		PDDocument attachDoc = null;
		
		ByteArrayInputStream bais = null;
		
		try{
			// TODO PAZZIK: per ora questo metodo è stato testato solo sugli allegati della ricevuta di Sportello Amico
			// TODO PAZZIK: estendere il test a tutti gli altri casi
			if(!hasAllEmbeddedFonts(null, attach))
				
				attachDoc = PDFAUtils.embedAllFonts(attach);
			
			else {
				
				bais = new ByteArrayInputStream(attach);
				
				
				attachDoc = PDDocument.loadNonSeq(bais,null);
			}
			
		} catch(IOException e){
			
			LOGGER.error("Error on embedAllFonts: ", e);
			
			throw e;
			
		} finally{
			
			try {
				
// il documento deve essere restituito aperto per poterlo riusare
//				if (attachDoc != null)
//					attachDoc.close();
				
				if (bais!=null)
					bais.close();
				
			} catch (IOException e1) {
				
				LOGGER.error("Error on embedAllFonts: ", e1);
			}
		}
		
		return attachDoc;
		
	}
	
	/**
	 * Creates a unique PDF document joining a PDF source and a PDF attachment.
	 * 
	 * @param src
	 * @param attach
	 * @param isTransformPDFA indica se l'allegato va convertito in immagine
	 * @return
	 * @throws Exception
	 */
	public static byte[] manipulatePdf(byte[] src, byte[] attach, EnumAttachmentRendering attachmentRendering) throws Exception {

		PdfReader srcReader = new PdfReader(src);
		
		switch (attachmentRendering) {
		
			case PDFA: 
				
				PDDocument attachDoc = embedAllFonts(attach);
			
				attach = PDFAUtils.convertPDFToPDFA(attachDoc);
				
				break;
				
			case IMAGE:
				
				attach = convertPDFToImage(attach);
				
				return addPdfImage(src,attach);
				
			case ASIS:
		
		}
		
		PdfReader attachReader = new PdfReader(attach);
	
		// L'allegato è un PDFA

		// verifiche varie sull'attach
		Rectangle attachRectangleWithRotation = attachReader.getPageSizeWithRotation(1);

		// orientamento (riferito al rettangolo con rotazione)
		boolean isPortrait = attachRectangleWithRotation.getHeight() > attachRectangleWithRotation.getWidth();

		// rettangolo da usare per il confronto delle dimensioni
		// N.B. gli assegno la stessa rotazione di quello dell'attach
		Rectangle pageSizeA5 = isPortrait ? PageSize.A5 : PageSize.A5.rotate();

		// verifico le dimensioni applicando la tolleranza
		boolean isA5 = attachRectangleWithRotation.getWidth() <= pageSizeA5.getWidth() * SIZE_TOLLERANCE
				&& attachRectangleWithRotation.getHeight() <= pageSizeA5.getHeight() * SIZE_TOLLERANCE;

		if (isA5) {
			//
			// l'attach è in formato A5.
			// Incollo la Prima pagina nella metà bassa dell'A4
			// e aggiungo altre pagine A4 incollando prima in alto e poi in
			// basso le rimanenti pagine dell'allegato A5.
			//
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(srcReader, out);

			int currentDocPage = 0;
			int attachNrPages = attachReader.getNumberOfPages();
			int totNrPage = new Double(Math.floor(attachNrPages / 2)).intValue() + 1;
			
			BaseFont bf  = loadEmbeddedBaseFont(src);
			
			for (int currentAttachPage = 1; currentAttachPage <= attachNrPages; currentAttachPage++) {

				currentDocPage = new Double(Math.floor(currentAttachPage / 2)).intValue() + 1;
				PdfImportedPage page = stamper.getImportedPage(attachReader, currentAttachPage);

				float posY;
				
				if (currentAttachPage % 2 == 0) {
					// con le pagine pari dell'attach aggiungo una pagina vuota
					// al documento risultante
					stamper.insertPage(currentDocPage, PageSize.A4);
					// le pagine pari dell'attach devono essere inserite sulla
					// metà in alto dell'a4 (A4/2 dal basso)
					posY = PageSize.A4.getHeight() / 2;
					
				} else {
					// le pagine dispari dell'attach devono essere inserite
					// sulla metà in basso dell'a4 (0 dal basso)
					posY = 0;
				}
				//
				// aggiungo al documento la pagina dell'attach su cui sto
				// ciclando
				//
				PdfContentByte underContent = stamper.getUnderContent(currentDocPage);
				
				if (isPortrait || attachRectangleWithRotation.getRotation() != 0) {
					// ruotato 90
					underContent.addTemplate(page, 0, -1, 1, 0, 0, posY + PageSize.A4.getHeight() / 2);
				} else {
					// stesso orientamento
					underContent.addTemplate(page, 0, posY);
				}

				//
				// aggiungo il numero pagina
				//
				PdfContentByte overContent = stamper.getOverContent(currentDocPage);
				// la condizione currentDocPage == 1 || currentAttachPage % 2 == 0 
				// dovrebbe garantire UNA sola stampa per pagina A4
				if (currentDocPage == 1 || currentAttachPage % 2 == 0) {
					overContent.beginText();
					overContent.setFontAndSize(bf, 10);
					overContent.setTextMatrix(500, 30);
					overContent.showText("Pagina " + currentDocPage + " di " + totNrPage);
					overContent.endText();
				}
			}

			stamper.close();
			attachReader.close();
			srcReader.close();
			return out.toByteArray();

		} else {
			//
			// l'attach non è formato A5. Lo concateno al documento così com'è.
			//
			attachReader.close();
			srcReader.close();
			// concateno l'allegato al documento sorgente
			return concatenate(false, src, attach);
		}
			
	}
	
	public static void testReplaceFonts() throws IOException{
		
		String filePath1 = "D:/TAS/MY/DOC/SportelloAmico/RicevutaPDF_A5_Asl11_prod.pdf";
		
		String filePath2 = "D:/TAS/MY/DOC/SportelloAmico/RicevutaPDFAFonts_A5_Asl11_prod.pdf";
		
		byte[] pdfBytes = readFile(filePath1);
		
		byte[] newPdfBytes = null;
		
		try {
			
			replaceNotEmbeddedFonts(pdfBytes);
			
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void testHasAllEmbeddedFonts(){
		
		String filePath1 = "D:/Projects/IRIS_NEW/services/services-resload/src/main/resources/iris-ear-container/JasperReports/design/pagamento/DDP_Ricevuta_PDF.pdf";
		
		String filePath = "C:/Users/pazzik/Downloads/IRIS_RICEVUTA_PAGAMENTO_14169981222700081DEF.pdf";
		
		String filePath2 = "D:/TAS/MY/DOC/SportelloAmico/RicevutaPDF_A5_Asl8_prod.pdf";
				//+ "RicevutaPDF_A5_Asl11_prod.pdf";
		
		try {
			
			byte[] pdfBytes = readFile(filePath2);
			
			Boolean embedded = hasAllEmbeddedFonts(filePath2, pdfBytes);
		
			System.out.println(filePath2+" "+embedded);
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}

	private static BaseFont loadFont(String fontName, String styleModifier) {
	    	
        try {
        	// N.B. il nome della cartella che contiene i font DEVE essere uguale al nome del font
        	// esempio:   /common/fonts/Lekton/Lekton-Regular.ttf
        	
        	// gestisco i casi in cui il nome del font non e' proprio uguale ...
        	// es: LiberationSans nella cartella Liberation
        	String fontFolderName;
        	if(fontName.startsWith("Liberation")) {
        		fontFolderName = "Liberation";
        	} else {
        		fontFolderName = fontName;
        	}
        	
        	StringBuilder fontFileLocation = new StringBuilder();
        	fontFileLocation.append("/common/fonts/").append(fontFolderName).append("/").append(fontName);
        	if(styleModifier != null)
        		fontFileLocation.append("-").append(styleModifier);
        	fontFileLocation.append(".ttf");
        	
//			BaseFont baseFont = BaseFont.createFont("/common/fonts/" + fontName + "/" + fontName + ".ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
			BaseFont baseFont = BaseFont.createFont(fontFileLocation.toString(), BaseFont.WINANSI, BaseFont.EMBEDDED);
            
            baseFont.setSubset(true);
            
            return baseFont;
            
        } catch (DocumentException ex) {
        	
        	LOGGER.error("Errore nel caricamento del Font :" + fontName, ex);
        	
        } catch (IOException ex) {
        	
        	LOGGER.error("Errore nel caricamento del Font :" + fontName, ex);
        	
        }
        
        return null;
    }
	
	private static BaseFont loadEmbeddedBaseFont(byte[] pdf) throws IOException, DocumentException{
		
		Set<String> fontsSet = listFonts(pdf);
		
		Map<String, Set<String>> allFonts = splitFonts(fontsSet);
		
		Set<String> embeddedFonts = allFonts.get("EMBEDDED");
		
		for(String fontName : embeddedFonts){
			
			if (!fontName.contains("-")){
				
				int indexOfBlank = fontName.indexOf(" ");
				
				if (indexOfBlank > 0)
				
				fontName = fontName.substring(0, indexOfBlank);
				
//				fontName+="Regular";
				
				BaseFont bf = loadFont(fontName, "Regular");
			
				return bf;
			}
		}
		
					
		return null;
		
	}
	
	public static void replaceNotEmbeddedFonts(byte[] doc) throws DocumentException, IOException{
		
		String filePath2 = "D:/TAS/MY/DOC/SportelloAmico/RicevutaPDFAFonts_A5_Asl11_prod.pdf";
		
		FileOutputStream fos = new FileOutputStream(filePath2);
		
		// the font file
	    RandomAccessFile raf = new RandomAccessFile("D:/Projects/IRIS_NEW/services/services-resload/src/main/resources/iris-ear-container/JasperReports/fonts/LiberationSans-Regular.ttf", "r");
	    byte fontfile[] = new byte[(int)raf.length()];
	    raf.readFully(fontfile);
	    raf.close();
	    // create a new stream for the font file
	    PdfStream stream = new PdfStream(fontfile);
	    stream.flateCompress();
	    stream.put(PdfName.LENGTH1, new PdfNumber(fontfile.length));
		
		//BaseFont embeddedFont = loadFont("LiberationSans");

		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		PdfReader reader = new PdfReader(doc);
		
	    int n = reader.getXrefSize();
	    
	    PdfObject object;
	    
	    PdfDictionary font;
	    
	    PdfStamper stamper = new PdfStamper(reader, fos);
	    
	    PdfName fontname = new PdfName(FontFactory.HELVETICA);
	    
	    for (int i = 0; i < n; i++) {
	    	
	        object = reader.getPdfObject(i);
	        
	        if (object == null || !object.isDictionary())	        	
	            continue;
	        
	        font = (PdfDictionary)object;
	        
	        if (PdfName.FONT.equals(font.get(PdfName.TYPE)))
	        	
	            if (fontname.equals(font.get(PdfName.BASEFONT))) {
	        	
	        	PdfIndirectObject objref = stamper.getWriter().addToBody(stream);
	            
	            font.put(PdfName.FONTFILE, objref.getIndirectReference());
	            
	        }
	    }
	    
	    stamper.close();
	    
	    reader.close();

	}
	
	public static void main(String[] args) throws Exception {
		
		testReplaceFonts();
	}
	
	public static byte[] convertPDFToImage(byte[] attach) throws IOException{
		
		ByteArrayInputStream bais = null;
		
		ByteArrayOutputStream baos = null;
		
		PDDocument document = null;
		
		byte[] output = null;
		
		try{
		
			baos = new ByteArrayOutputStream();
			
			bais = new ByteArrayInputStream(attach);
			
			document = PDDocument.loadNonSeq(bais, null);
			
			List<PDPage> pdPages = document.getDocumentCatalog().getAllPages();
			
			//int page = 0;
			
			for (PDPage pdPage : pdPages)
			{ 
				//page++;
			    BufferedImage bim = pdPage.convertToImage(BufferedImage.TYPE_INT_RGB, 150);
			    
			    ImageIOUtil.writeImage(bim, "jpg", baos, 150, 0.8f);
			}
			
			output = baos.toByteArray();
			
		} catch(IOException e){
			
			LOGGER.error("Error on convertPDFToImage: ", e);
			
			throw e;
			
		} finally{
			
			try {
				
				if (document != null)
					document.close();
				
				if (bais != null)
					bais.close();
				
				if (baos != null)
					baos.close();
				
			} catch (IOException e1) {
				
				LOGGER.error("Error on convertPDFToImage: ", e1);
			}
		}

		return output;
	}
	
	public static byte[] addPdfImage(byte[] pdf, byte[] attach) {
		
		ByteArrayOutputStream baos = null;
		
		PdfReader pdfReader = null;
		
		PdfStamper pdfStamper = null;
		
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
	      
	    } finally {
	    	
	    	try {
	    		
	    		if(baos != null)
		    		baos.close();
		    	
		    	if(pdfReader != null)
		    		pdfReader.close();
	    		
		    	if(pdfStamper != null)
		    			pdfStamper.close();
		    	
		    	
		    	
		    } catch (IOException e) {
		    	
		    	LOGGER.error("Error on addPdfImage: ", e);
		      
		    } catch (DocumentException e) {
		    	
		    	LOGGER.error("Error on addPdfImage: ", e);
		      
		    }
	    	
	    }
	    
	    return null;
	  }

} 