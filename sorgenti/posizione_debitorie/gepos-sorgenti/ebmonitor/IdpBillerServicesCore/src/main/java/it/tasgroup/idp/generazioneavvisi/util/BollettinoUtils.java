package it.tasgroup.idp.generazioneavvisi.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import it.tasgroup.idp.billerservices.util.NumeroAvvisoUtils;
import it.tasgroup.idp.domain.bonifici.Intestatari;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.enti.TributiEntiPK;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

public class BollettinoUtils {
private final Log logger = LogFactory.getLog(this.getClass());	
	
	public static void main (String [] args) throws Exception {
		String data = "4159780201379624802000146523241204720239021212";
		File f = new File("barcode.png");
		FileOutputStream os = new FileOutputStream(f);
		
		BollettinoUtils util = new BollettinoUtils();
		util.generateBarCode_general(data,os); 
		os.flush();
		os.close();
		
		f = new File("qrcode.png");
		os = new FileOutputStream(f);
		util.generateQRCode_general(data,os); 
		os.flush();
		os.close();
	  }
	public void generateQRCode_general(String data,OutputStream os) throws WriterException, IOException  {
	    com.google.zxing.Writer writer = new QRCodeWriter();
	    BitMatrix bm = writer.encode(data, BarcodeFormat.QR_CODE,150, 150);
	    MatrixToImageWriter.writeToStream(bm, "png", os);
	    
	}
	public void generateBarCode_general(String data,OutputStream os) throws WriterException, IOException, BarcodeException, OutputException  {
		Barcode barcode = BarcodeFactory.createCode128C(data);
		barcode.setDrawingText(false);
		barcode.setBarHeight(60);
	    barcode.setBarWidth(2);

	    BarcodeImageHandler.writePNG(barcode, os);
	    
	}
	public  String getQRcode(CondizioniPagamento condizione,EntityManager em) {
		logger.info("BollettinoUtils::getQRcodeXml() BEGIN");
		
	    String IUV = condizione.getIdPagamento();
	    
	    DecimalFormat df = new DecimalFormat("0000000000");
		String importoTotale = df.format(condizione.getImTotale().movePointRight(2));
	    
	    Enti ente = em.find(Enti.class, condizione.getIdEnte());
	    Intestatari intest = em.find(Intestatari.class, ente.getIntestatario());
	    
	    TributiEntiPK tributiEntiPK = new TributiEntiPK();
	    tributiEntiPK.setIdEnte(condizione.getIdEnte());
	    tributiEntiPK.setCdTrbEnte(condizione.getCdTrbEnte());
	    TributiEnti tributo = em.find(TributiEnti.class, tributiEntiPK);
	    
	    String numeroAvviso = NumeroAvvisoUtils.calculateNumeroAvviso(tributo, IUV, false);
	    
	    String codiceIdentificativoEnte = intest.getLapl(); 
	    
//	    String ret ="<informazioniVersamento>"
//	            +       "<codiceIdentificativoEnte>"+codiceIdentificativoEnte+"</codiceIdentificativoEnte>"
//	            +        "<numeroAvviso>"
//	            +          "<auxDigit>"+auxDigit+"</auxDigit>"
//	            +           "<applicationCode>"+applicationCode+"</applicationCode>"
//	            +           "<IUV>"+IUV+"</IUV>"
//	            +       "</numeroAvviso>"
//	            +       "<importoVersamento>"+importoTotale+"</importoVersamento>"
//	            + "</informazioniVersamento>";

		String ret = "PAGOPA|002|" + numeroAvviso + "|" + codiceIdentificativoEnte + "|" + importoTotale;
		logger.info("BollettinoUtils::getQRcodeXml() END");
		return ret;
	    
	}

	public  String getBarCode(CondizioniPagamento condizione, EntityManager em, boolean isFormatted) {

		logger.info("BollettinoUtils::getBarCode() BEGIN");

		String IUV = condizione.getIdPagamento();

		Enti ente = em.find(Enti.class, condizione.getIdEnte());
		Intestatari intest = em.find(Intestatari.class, ente.getIntestatario());

		TributiEntiPK tributiEntiPK = new TributiEntiPK();
		tributiEntiPK.setIdEnte(condizione.getIdEnte());
		tributiEntiPK.setCdTrbEnte(condizione.getCdTrbEnte());
		TributiEnti tributo = em.find(TributiEnti.class, tributiEntiPK);

		DecimalFormat df = new DecimalFormat("0000000000");
		String importoTotale = df.format(condizione.getImTotale().movePointRight(2));

		String numeroAvviso = NumeroAvvisoUtils.calculateNumeroAvviso(tributo, IUV, false);

		String ret;
		if (isFormatted) {
			ret = "(415)" + ente.getGln() + "(8020)" + numeroAvviso + "(3902)" + importoTotale;
		} else {
			ret = "415" + ente.getGln() + "8020" + numeroAvviso + "3902" + importoTotale;
		}
			
		logger.info("BollettinoUtils::getBarCode() END");

		return ret;
	}
	/**
    *
    * @param tributo
    * @param IUV
    * @param formatted
    * @return
    */
   public static String calculateNumeroAvviso(TributiEnti tributo, String IUV,boolean formatted) {
        String auxDigit = tributo.getNdpAuxDigit();
    String codStazPa = tributo.getNdpCodStazPa();
    String separator="";
    if (formatted) separator=" ";

   String numeroAvviso = null;
   if ("0".equals(auxDigit)){
       numeroAvviso = auxDigit+
                          separator+
                          codStazPa+
                          separator+
                          IUV;
    } else
    if ("1".equals(auxDigit)){
          numeroAvviso = auxDigit+
                              separator+
                              IUV;
    } else
    if ("2".equals(auxDigit)){
          numeroAvviso = auxDigit+
                              separator+
                              IUV;
    } else
        if ("3".equals(auxDigit)){
           numeroAvviso = auxDigit+
                          separator+
                          IUV;
    }   
    return numeroAvviso;
}

}

