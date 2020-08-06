/**
 * 
 */
package it.tasgroup.iris.business.ejb.ddp;

import it.nch.is.fo.CommonConstant;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.report.ReportManager;
import it.tasgroup.report.exporter.ReportExporterFactory;
import it.tasgroup.services.util.enumeration.EnumCodificaAllegato;
import it.tasgroup.services.util.enumeration.EnumTipoAllegato;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

/**
 * @author pazzik
 *
 */
public class DocFragmentRenderer {
	
	public static AllegatiPendenza buildAttachment(List<AllegatiPendenza> allegati, Locale locale, EnumCodificaAllegato codifica){
		
		AllegatiPendenza allegato = filterAttachments(allegati, codifica);
		
		if (allegato == null &&  EnumCodificaAllegato.PDF.equals(codifica)) {
			
			AllegatiPendenza allegatoXML = filterAttachments(allegati, EnumCodificaAllegato.XML);
			
			if (allegatoXML != null){
				
				byte[] decodedXMLFlow = Base64.decodeBase64(allegatoXML.getDatiBody());
				
				byte[] reportFlow = renderAttachmentAsPDF(allegatoXML.getTitolo(), decodedXMLFlow, locale);	
				
				if (reportFlow == null)
							return null;
				
				// TODO PAZZIK: inserire qui la validazione dell'XML rispetto all'XSD
				allegato = new AllegatiPendenza();
				
				// popolo l'allegato PDF completamente (tranne l'ID) in caso un giorno lo si voglia salvare su DB
				
				allegato.setTiCodificaBody(EnumCodificaAllegato.PDF.getChiave());
				
				allegato.setTsDecorrenza(allegatoXML.getTsDecorrenza());
				
				allegato.setCondizioniPagamento(allegatoXML.getCondizioniPagamento());
				
				allegato.setIdPendenza(allegatoXML.getIdPendenza());
				
				allegato.setFlContesto(allegatoXML.getFlContesto());
									
				allegato.setIdAntifalsific("antiFalsificazione");
				
				allegato.setTiAllegato(EnumTipoAllegato.RICEVUTA.getDescrizione());
				
				allegato.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				
				allegato.setOpInserimento("GeneratedFromXML");
				
				
				allegato.setDatiBody(Base64.encodeBase64(reportFlow));
				
				allegato.setAllegatoXML(allegatoXML);
				
			}	
			
		}
		
		return allegato;
	}
	
	
	public static byte[] renderAttachmentAsPDF(String xmlFileName, byte[] xmlFlow, Locale locale){
		
		Map<String, Object> reportParametersMap = new HashMap<String, Object>();

		reportParametersMap.put("REPORT_LOCALE", locale == null ? Locale.ITALIAN : locale); // DEFAULT ITALIANO

		ReportManager reportGenerator = new ReportManager(
				xmlFileName, 
				ReportExporterFactory.PDF_EXT, 
				reportParametersMap,
				CommonConstant.RECEIPT_FOLDER_NAME);		
		
		byte[] reportFlow = reportGenerator.generateReport(xmlFlow,Locale.ENGLISH, "yyyyMMdd", "#,##0.##");

		return reportFlow;
	}
	
	private static AllegatiPendenza filterAttachments(List<AllegatiPendenza> allegati, EnumCodificaAllegato codifica){
		
		for (AllegatiPendenza allegato : allegati){
			
			if (allegato.getTiCodificaBody().equals(codifica.getChiave()))
				return allegato;
			
		}
		
		return null;
	}


}
