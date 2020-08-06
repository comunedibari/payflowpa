package it.tasgroup.idp.cart.core.quadratura.pdf;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.awt.Font;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import it.tasgroup.idp.cart.core.model.MessaggioModel;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;
import it.tasgroup.idp.cart.core.quadratura.DatiQuadratura;
import it.tasgroup.idp.cart.core.quadratura.DatiQuadraturaBuilder;
import it.tasgroup.idp.cart.core.quadratura.Err;
import it.tasgroup.idp.cart.core.quadratura.Report;
import it.tasgroup.idp.cart.core.quadratura.Res;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.report.builder.chart.BarChartBuilder;
import net.sf.dynamicreports.report.builder.column.ColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.MultiPageListBuilder;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.chart.DRIChartCustomizer;
import net.sf.jasperreports.engine.JRDataSource;

public class PdfSerializer {



	public static final String LABEL_SIL = "Sil";
	public static final String LABEL_SOGGETTO = "Soggetto";
	public static final String LABEL_PERIODO_RILEVAMENTI = "Periodo rilevamenti";
	public static final String LABEL_SERVIZIO_INFORMATIVA_PAGAMENTI = "Servizio Informativa Pagamenti";
	public static final String LABEL_SERVIZIO_ALLINEAMENTO_PENDENZE = "Servizio Allineamento Pendenze";

	private static final int ALTEZZA_GRAFICO = 350;

	private static final int LUNGHEZZA_GRAFICO = 400;

	private static final boolean VISUALIZZA_LABEL_NEL_GRAFICO = true;

	public static final String LABEL_RICHIESTE = "Richieste";
	public static final String LABEL_ESITI = "Esiti";
	public static final String LABEL_ERRORE = "Fallite";
	public static final String LABEL_OK = "Ok";
	public static final String LABEL_TOTALE = "Totali";
	public static final String LABEL_NOTIFICHE = "Notifiche";
	public static final String LABEL_CONFERME = "Conferme";

	public static final String LABEL_RICHIESTE_ESITI = "Richieste/Esiti";
	public static final String LABEL_NOTIFICHE_CONFERME = "Notifiche/Conferme";

	public static final String LABEL_RICHIESTE_RICEVUTE = "Richieste Ricevute";
	public static final String LABEL_NOTIFICHE_INVIATE = "Notifiche Inviate (tentativi)";
	public static final String LABEL_NOTIFICHE_CONSEGNATE = "Notifiche Consegnate";
	public static final String LABEL_NOTIFICHE_NON_CONSEGNATE = "Notifiche Non Consegnate";
	public static final String LABEL_RICHIESTE_ACQUISITE = "Richieste Acquisite";

	public static final String LABEL_RICHIESTE_DA_ESITARE = "Richieste da Esitare";
	public static final String LABEL_NOTIFICHE_DA_CONFERMARE = "Notifiche da Confermare";

	public static final String LABEL_ESITI_CONSEGNATI = "Esiti Consegnati";
	public static final String LABEL_ESITI_INVIATI = "Esiti Inviati (tentativi)";
	public static final String LABEL_CONFERME_RICEVUTE = "Conferme Ricevute";
	public static final String LABEL_CONFERME_ACQUISITE = "Conferme Acquisite";

	public final static String CODE_FAULT_APPLICATIVO = "FF8F52";
	public final static String CODE_ERROR = "CD4A50";
	public final static String CODE_OK = "95B964";
	public final static String CODE_TOTALE = "3B83B7";
	public final static String CODE_LATENZA_BLU = "3B83B7";
	public final static String CODE_LATENZA_ARANCIO = "FF8F52";
	public final static String CODE_LATENZA_VERDE = "95B964";
	public final static String CODE_LATENZA_VIOLA = "82659C";

	public final static Color COLOR_FAULT_APPLICATIVO = Color.decode("#"+CODE_FAULT_APPLICATIVO);
	public final static Color COLOR_ERROR = Color.decode("#"+CODE_ERROR);
	public final static Color COLOR_OK = Color.decode("#"+CODE_OK);
	public final static Color COLOR_TOTALE = Color.decode("#"+CODE_TOTALE);
	public final static Color COLOR_LATENZA_BLU = Color.decode("#"+CODE_LATENZA_BLU);
	public final static Color COLOR_LATENZA_ARANCIO = Color.decode("#"+CODE_LATENZA_ARANCIO);
	public final static Color COLOR_LATENZA_VERDE = Color.decode("#"+CODE_LATENZA_VERDE);
	public final static Color COLOR_LATENZA_VIOLA = Color.decode("#"+CODE_LATENZA_VIOLA);



	public static void esportaPdf(OutputStream outputStream, DatiQuadratura reportTotale,Log log) throws Exception {

		JasperPdfExporterBuilder builder = export.pdfExporter(outputStream);

		JasperReportBuilder report = report();

		MultiPageListBuilder multiPageList = cmp.multiPageList();

		// REPORT ALLINEAMENTO PENDENZE
		String labelServizioAP = LABEL_SERVIZIO_ALLINEAMENTO_PENDENZE;

//		multiPageList.add(getPaginaGraficoRichieste(reportTotale, reportTotale.getReportAP(), labelServizioAP, log));
//
//		multiPageList.add(cmp.pageBreak());
//
//		multiPageList.add(getPaginaGraficoTempiMedi(reportTotale, reportTotale.getReportAP(), labelServizioAP, log));
//
//		multiPageList.add(cmp.pageBreak());

		multiPageList.add(getPaginaTabelle(reportTotale, reportTotale.getReportAP(), labelServizioAP,log));

		multiPageList.add(cmp.pageBreak());

		// REPORT INFORMATIVA PAGAMENTI
		String labelServizioIP = LABEL_SERVIZIO_INFORMATIVA_PAGAMENTI;

//		multiPageList.add(getPaginaGraficoRichieste(reportTotale, reportTotale.getReportIP(), labelServizioIP, log));
//
//		multiPageList.add(cmp.pageBreak());
//
//		multiPageList.add(getPaginaGraficoTempiMedi(reportTotale, reportTotale.getReportIP(), labelServizioIP, log));
//
//		multiPageList.add(cmp.pageBreak());

		multiPageList.add(getPaginaTabelle(reportTotale, reportTotale.getReportIP(), labelServizioIP,log));

		//configure report
		report.setPageFormat(PageType.A4, PageOrientation.PORTRAIT)
		.setTemplate(Templates.reportTemplate)
		//.title(Templates.dynamicReportsComponent)
		.summary(multiPageList)
		//.pageFooter(Templates.footerComponent)
		.toPdf(builder);

	}

	private static ComponentBuilder<?, ?> creaRiepilogoParametri(DatiQuadratura datiQuadratura) {

		HorizontalListBuilder listSx = cmp.horizontalList().setBaseStyle(stl.style(Templates.fontStyle12).setLeftPadding(10));
		addElementoLista(listSx, LABEL_SOGGETTO, datiQuadratura.getSoggetto(),true);
		addElementoLista(listSx, LABEL_SIL, datiQuadratura.getSil(),true);

		HorizontalListBuilder listDx = cmp.horizontalList().setBaseStyle(stl.style(Templates.fontStyle12).setLeftPadding(10).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
		addElementoLista(listDx, null, LABEL_PERIODO_RILEVAMENTI,true);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder sb = new StringBuilder();
		sb.append("dal ");
		sb.append(sdf.format(datiQuadratura.getDataInizio()));
		sb.append(" al ");
		sb.append(sdf.format(datiQuadratura.getDataFine()));
		addElementoLista(listDx, null, sb.toString(),true);

		return cmp.horizontalList(

				// colonna sx
				cmp.verticalList(	listSx),

				//colonna dx
				cmp.verticalList( listDx)		

				)
				.newRow()
				.add(cmp.line());
	}

	private static ComponentBuilder<?, ?> creaLabelServizio(String label) {
		return cmp.verticalList(
				cmp.text(label).setStyle(Templates.bold18CenteredStyle));
	}

	private static ComponentBuilder<?, ?> createRiepilogoRichieste(String label, Report report) {
		HorizontalListBuilder list1 = cmp.horizontalList().setBaseStyle(stl.style(Templates.fontStyle12).setLeftPadding(10));
		HorizontalListBuilder list2 = cmp.horizontalList().setBaseStyle(stl.style(Templates.fontStyle12).setLeftPadding(10));
//		HorizontalListBuilder list3 = cmp.horizontalList().setBaseStyle(stl.style(Templates.fontStyle12).setLeftPadding(10));

		String labelRichiesteRicevute = report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_RICHIESTE_RICEVUTE : LABEL_NOTIFICHE_INVIATE; 
		String labelEsitiRicevuti = report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_ESITI_INVIATI : LABEL_CONFERME_RICEVUTE;
		String labelRichiesteAcquisite = report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_RICHIESTE_ACQUISITE : LABEL_NOTIFICHE_CONSEGNATE; 
		String labelEsitiConsegnati = report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_ESITI_CONSEGNATI: LABEL_CONFERME_ACQUISITE;
		String labelRichiesteDaEsitare= report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_RICHIESTE_DA_ESITARE: LABEL_NOTIFICHE_DA_CONFERMARE;

		addElementoLista(list1, "", "Asincrono",false,true);
		addElementoLista(list1, null, "OTF",true,true);
		addElementoLista(list1, labelRichiesteRicevute, report.getRichiesteRicevute() +"",false);
		addElementoLista(list1, null, report.getRichiesteRicevuteOTF() +"",true);
		addElementoLista(list1, labelRichiesteAcquisite, report.getRichiesteAcquisite()+"",false);
		addElementoLista(list1, null, report.getRichiesteAcquisiteOTF()+"",true);
		if(report.getTipo().equals(TipoMessaggio.INFORMATIVA_PAGAMENTO)){
			addElementoLista(list1, LABEL_NOTIFICHE_NON_CONSEGNATE, report.getRichiesteNonConsegnate()+"",false);
			addElementoLista(list1, null, "-",true);	
		}
		
		addElementoLista(list1, labelRichiesteDaEsitare, report.getRichiesteDaEsitare()+"",false);
		addElementoLista(list1, null, "-",true);
		
		addElementoLista(list2, labelEsitiRicevuti, report.getEsitiInviati()+"",false);
		addElementoLista(list2, null, "-",true);
		addElementoLista(list2, labelEsitiConsegnati, report.getRichiesteEsitate() +"",false);
		addElementoLista(list2, null, "-",true);

		//		addCustomerAttribute(list, "City", customer.getCity());
		//		addCustomerAttribute(list, "Email", customer.getEmail());
		if(label != null)
			return cmp.verticalList(
					cmp.text(label).setStyle(Templates.boldStyle),
					list1, cmp.verticalGap(5),
					list2);
		else 
			return cmp.verticalList(
					list1, cmp.verticalGap(5),
					list2);
	}
	
	private static void addElementoLista(HorizontalListBuilder list, String label, String value, boolean newRow) {
		addElementoLista(list, label, value, newRow, false);
	}

	private static void addElementoLista(HorizontalListBuilder list, String label, String value, boolean newRow, boolean bold) {
		if (value != null) { 
			TextFieldBuilder<String> text = cmp.text(value); 
			if(bold)
				text.setStyle(Templates.boldStyle);
			
			if(label != null) { //.setFixedColumns(8)
				String labelDots = label.length() > 0 ? label + ":" : label;
				
				if(newRow)
					list.add(cmp.text(labelDots).setStyle(Templates.boldStyle), text).newRow();
				else
					list.add(cmp.text(labelDots).setStyle(Templates.boldStyle), text);
			}else {
				if(newRow)
					list.add(text).newRow();
				else
					list.add(text);
			}
		}
	}

	private static JRDataSource getDatasourceAndamentoTemporale (DatiQuadratura datiQuadratura, Report report, Log log, boolean isTempiMedi,
			boolean convertRawData) throws Exception {
		SimpleDateFormat sdf   = null;
		SimpleDateFormat sdf2 = null;
		// Scittura Intestazione
		List<String> header = new ArrayList<String>();

		header.add("data");

		DRDataSource dataSource = null; 
		if(isTempiMedi){
			header.add("richieste");
			header.add("esiti");

			dataSource =  new DRDataSource(header.toArray(new String[header.size()]));
			long incremento = DatiQuadraturaBuilder.getIncremento(datiQuadratura.getDataInizio(), datiQuadratura.getDataFine()); 

			if(incremento > 3600000){
				sdf   = new SimpleDateFormat("yyyy/MM/dd", Locale.ITALIAN);
			}else{
				sdf   = new SimpleDateFormat("HH", Locale.ITALIAN);
				sdf2 = new SimpleDateFormat("HH",Locale.ITALIAN);
			}

			List<Res> listaTempiMediRichieste = report.getTempoMedioGestioneRichieste();
			List<Res> listaTempiMediEsiti = report.getTempoMedioGestioneEsiti(); 

			Calendar c =  null;
			for (int idx = 0 ; idx < listaTempiMediRichieste.size() ; idx ++){
				Res risultato = listaTempiMediRichieste.get(idx);

				List<Object> oneLine = new ArrayList<Object>();

				// Label Data
				String label = null;

				if(incremento > 3600000){
					label = sdf.format(risultato.getData());
				}else{
					c = Calendar.getInstance();
					c.setTime((Date)risultato.getData());
					c.add(Calendar.HOUR, +1);
					label = "HH " + sdf.format(risultato.getData())+"-"+sdf2.format(c.getTime());
				}

				oneLine.add(label);

				long valEsito = -1l;

				// cerco gli esiti in base alla richieste, non dovrebbero esserci esiti non associati alle richieste...
				for (Res resEsito : listaTempiMediEsiti) {
					// cerco una data per l'esito
					if(resEsito.getData().equals(risultato.getData())){
						valEsito = resEsito.getValore() != null ? resEsito.getValore().longValue() : 0l;
						break;
					}
				}
				// non ho trovato un esito 
				if(valEsito == -1l){
					valEsito = 0l;
				}

				if(convertRawData){
					oneLine.add(convertSystemTimeIntoString_millisecondi(risultato.getValore().longValue(), true));
					oneLine.add(convertSystemTimeIntoString_millisecondi(valEsito, true));
				}
				else{
					oneLine.add(risultato.getValore());
					oneLine.add(valEsito);
				}

				log.debug("Gruppo Tempi Medi" + oneLine.toString()); 

				dataSource.add(oneLine.toArray(new Object[oneLine.size()])); 
			}

		} else {
			header.add("richiesteRicevute");
			header.add("richiesteOK");
			header.add("esitiInviati");
			header.add("esitiOK");

			dataSource =  new DRDataSource(header.toArray(new String[header.size()]));

			List<Res> listaNumeroRichiesteRicevute = report.getDistribuzioneRichiesteRicevute();
			List<Res> listaNumeroRichiesteOK = report.getDistribuzioneRichiesteAcquisite();
			List<Res> listaNumeroEsitiInviati = report.getDistribuzioneEsitiInviati();
			List<Res> listaNumeroEsitiOK = report.getDistribuzioneEsitiConsegnati();

			sdf   = new SimpleDateFormat("yyyy/MM/dd", Locale.ITALIAN);

			for (int idx = 0 ; idx < listaNumeroRichiesteOK.size() ; idx ++){
				Res risultatoRichiestaOK = listaNumeroRichiesteOK.get(idx);

				List<Object> oneLine = new ArrayList<Object>();

				// Label Data
				String label =   sdf.format(risultatoRichiestaOK.getData());
				oneLine.add(label);

				long valEsitoOK = -1l;
				long valEsitoInv = -1l;
				long valRichiesteRic = -1l;

				// cerco gli esiti OK in base alla richieste OK..
				for (Res resEsito : listaNumeroEsitiOK) {
					// cerco una data per l'esito
					if(resEsito.getData().equals(risultatoRichiestaOK.getData())){
						valEsitoOK = resEsito.getValore() != null ? resEsito.getValore().longValue() : 0l;
						break;
					}
				}
				// non ho trovato un esito OK
				if(valEsitoOK == -1l){
					valEsitoOK = 0l;
				}

				// cerco gli esiti inviati in base alla richieste OK..
				for (Res resEsito : listaNumeroEsitiInviati) {
					// cerco una data per l'esito
					if(resEsito.getData().equals(risultatoRichiestaOK.getData())){
						valEsitoInv = resEsito.getValore() != null ? resEsito.getValore().longValue() : 0l;
						break;
					}
				}
				// non ho trovato un esito inviato
				if(valEsitoInv == -1l){
					valEsitoInv = 0l;
				}

				// cerco gli esiti KO in base alla richieste OK..
				for (Res resRic : listaNumeroRichiesteRicevute) {
					// cerco una data per l'esito
					if(resRic.getData().equals(risultatoRichiestaOK.getData())){
						valRichiesteRic = resRic.getValore() != null ? resRic.getValore().longValue() : 0l;
						break;
					}
				}
				// non ho trovato un esito KO
				if(valRichiesteRic == -1l){
					valRichiesteRic = 0l;
				}

				if(convertRawData){
					oneLine.add( numberConverter( valRichiesteRic) );
					oneLine.add( numberConverter( risultatoRichiestaOK.getValore()) );
					oneLine.add( numberConverter( valEsitoInv) );
					oneLine.add( numberConverter( valEsitoOK) );

				}
				else{
					oneLine.add(valRichiesteRic);
					oneLine.add(risultatoRichiestaOK.getValore());
					oneLine.add(valEsitoInv);
					oneLine.add(valEsitoOK);
				}
				log.debug("Gruppo Richieste/Esiti" + oneLine.toString()); 

				dataSource.add(oneLine.toArray(new Object[oneLine.size()])); 
			}
		}

		return dataSource;

	}


	public static SubreportBuilder getPaginaGraficoRichieste(DatiQuadratura reportTotale, Report report ,String labelServizio, Log log ) throws Exception {

		return cmp.subreport(
				report()
				.setTemplate(Templates.reportTemplate)
				.title(Templates.dynamicReportsComponent)
				.summary(cmp.verticalList(
						//						cmp.verticalGap(20),
						creaRiepilogoParametri(reportTotale),
						cmp.verticalGap(20),
						creaLabelServizio(labelServizio),
						cmp.verticalGap(20),
						createRiepilogoRichieste(null, report),
						cmp.verticalGap(20),
						getGraficoNumeroRichieste(reportTotale,report, log)
						))
				//.pageFooter(Templates.footerComponent)
				//.setTableOfContents(true)
				);

	}

	public static SubreportBuilder getPaginaGraficoTempiMedi(DatiQuadratura reportTotale, Report report ,String labelServizio, Log log ) throws Exception {

		return cmp.subreport(
				report()
				.setTemplate(Templates.reportTemplate)
				.title(Templates.dynamicReportsComponent)
				.summary(cmp.verticalList(
						creaRiepilogoParametri(reportTotale),
						cmp.verticalGap(20),
						creaLabelServizio(labelServizio),
						cmp.verticalGap(20),
						getGraficoBarreTempiMedi(reportTotale,report, log) 
						))
				//.pageFooter(Templates.footerComponent)
				//.setTableOfContents(true)
				);

	}

	public static SubreportBuilder getPaginaTabelle(DatiQuadratura reportTotale, Report report ,String labelServizio, Log log ) throws Exception {

		return cmp.subreport(
				report()
				.setTemplate(Templates.reportTemplate)
				.title(Templates.dynamicReportsComponent)
				.summary(cmp.verticalList(
						//						cmp.verticalGap(20),
						creaRiepilogoParametri(reportTotale),
						cmp.verticalGap(20),
						creaLabelServizio(labelServizio),
						cmp.verticalGap(20),
						createRiepilogoRichieste(null, report),
						cmp.verticalGap(20),
						getTabellaRichiesteNonConsegnate(reportTotale, report, labelServizio),
						cmp.verticalGap(20),
						getTabellaRichiesteNonEsitate(reportTotale, report, labelServizio),
						cmp.verticalGap(20),
						getTabellaErroriConsegna(reportTotale, report)
						))
				//.pageFooter(Templates.footerComponent)
				//.setTableOfContents(true)
				);

	}


	public static BarChartBuilder getGraficoNumeroRichieste(DatiQuadratura datiQuadratura,Report report,Log log) throws Exception {
		TextColumnBuilder<String> dataColumn  = col.column("", "data", type.stringType()); 

		String labelAsseX = "Numero " + (report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_RICHIESTE_ESITI : LABEL_NOTIFICHE_CONFERME); 
		String titoloGrafico =  "Numero di " + (report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_RICHIESTE_ESITI : LABEL_NOTIFICHE_CONFERME);

		String labelRichiesteRicevute = report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_RICHIESTE_RICEVUTE : LABEL_NOTIFICHE_INVIATE; 
		String labelEsitiRicevuti = report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_ESITI_INVIATI : LABEL_CONFERME_RICEVUTE;
		String labelRichiesteAcquisite = report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_RICHIESTE_ACQUISITE : LABEL_NOTIFICHE_CONSEGNATE; 
		String labelEsitiConsegnati = report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_ESITI_CONSEGNATI: LABEL_CONFERME_ACQUISITE;

		TextColumnBuilder<Long> richiesteRicevuteColumn = col.column(labelRichiesteRicevute, "richiesteRicevute", type.longType());
		TextColumnBuilder<Long> richiesteOkColumn = col.column(labelRichiesteAcquisite, "richiesteOK", type.longType());
		TextColumnBuilder<Long> esitiInviatiColumn = col.column(labelEsitiRicevuti, "esitiInviati", type.longType());
		TextColumnBuilder<Long> esitiOkColumn = col.column(labelEsitiConsegnati, "esitiOK", type.longType()); 

		Map<String, Color> seriesColorsByName = new HashMap<String, Color>();

		seriesColorsByName.put(labelRichiesteRicevute, COLOR_LATENZA_BLU);
		seriesColorsByName.put(labelRichiesteAcquisite, COLOR_LATENZA_VERDE);
		seriesColorsByName.put(labelEsitiRicevuti, COLOR_LATENZA_VIOLA);
		seriesColorsByName.put(labelEsitiConsegnati, COLOR_LATENZA_ARANCIO);

		return cht.barChart()
				.customizers(new PdfSerializer().new BarChartCustomizer(ReportItemLabelGenerator.DS_NUMERO_RICHIESTE))
				.setCategory(dataColumn)
				.seriesColorsByName(seriesColorsByName)
				.series(
						cht.serie(richiesteRicevuteColumn).setLabel(labelRichiesteRicevute),
						cht.serie(richiesteOkColumn).setLabel(labelRichiesteAcquisite),
						cht.serie(esitiInviatiColumn).setLabel(labelEsitiRicevuti),
						cht.serie(esitiOkColumn).setLabel(labelEsitiConsegnati))
				.setValueAxisFormat(
						cht.axisFormat().setLabel(labelAsseX))
				.setDataSource(getDatasourceAndamentoTemporale(datiQuadratura,report, log, false, false)) 
				.setHeight(ALTEZZA_GRAFICO) 
				.setWidth(LUNGHEZZA_GRAFICO)
				.setTableOfContentsHeading(titoloGrafico)
				.setShowValues(VISUALIZZA_LABEL_NEL_GRAFICO)
				.setShowLegend(true)
				.setTitle(titoloGrafico)
				;
	}

	public static BarChartBuilder getGraficoBarreTempiMedi(DatiQuadratura datiQuadratura,Report report,Log log) throws Exception {
		TextColumnBuilder<String> dataColumn  = col.column("", "data", type.stringType()); 

		String labelAsseX = "Tempi Medi di Gestione " + (report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_RICHIESTE_ESITI : LABEL_NOTIFICHE_CONFERME) + " [ms]"; 
		String titoloGrafico =  "Tempi Medi di Gestione " + (report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_RICHIESTE_ESITI : LABEL_NOTIFICHE_CONFERME) + " [ms]";

		TextColumnBuilder<Long> richiesteColumn = col.column((report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_RICHIESTE : LABEL_NOTIFICHE)	, "richieste", type.longType()); 
		TextColumnBuilder<Long> esitiColumn = col.column((report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) ?  LABEL_ESITI : LABEL_CONFERME)	, "esiti", type.longType()); 

		Map<String, Color> seriesColorsByName = new HashMap<String, Color>();

		seriesColorsByName.put(LABEL_RICHIESTE, COLOR_LATENZA_BLU);
		seriesColorsByName.put(LABEL_ESITI, COLOR_LATENZA_ARANCIO); 


		return cht.barChart()
				.customizers(new PdfSerializer().new BarChartCustomizer(ReportItemLabelGenerator.DS_LATENZE))
				.setCategory(dataColumn)
				.seriesColorsByName(seriesColorsByName)
				.series(
						cht.serie(richiesteColumn).setLabel(LABEL_RICHIESTE),
						cht.serie(esitiColumn).setLabel(LABEL_ESITI))
				.setValueAxisFormat(cht.axisFormat().setLabel(labelAsseX))
				.setDataSource(getDatasourceAndamentoTemporale(datiQuadratura,report, log, true, false)) 
				.setHeight(ALTEZZA_GRAFICO) 
				.setWidth(LUNGHEZZA_GRAFICO)
				.setTableOfContentsHeading(titoloGrafico)
				.setShowValues(VISUALIZZA_LABEL_NEL_GRAFICO)
				.setShowLegend(true)
				.setTitle(titoloGrafico)
				;
	}

	public static SubreportBuilder getTabellaRichiesteNonEsitate(DatiQuadratura reportTotale, Report report ,String labelServizio) throws Exception{

		// Scittura Intestazione
		List<ColumnBuilder<?, ?>> colonne = new ArrayList<ColumnBuilder<?, ?>>();

		TextColumnBuilder<String> msgidColumn = col.column("MsgId", "msgid", type.stringType());
		TextColumnBuilder<String> dataColumn  = col.column("Data Richiesta", "data", type.stringType()); 

		colonne.add(msgidColumn);
		colonne.add(dataColumn);

		SimpleDateFormat sdf   = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALIAN);
		List<String> header = new ArrayList<String>();
		header.add("msgid");
		header.add("data");

		DRDataSource dataSource = new DRDataSource(header.toArray(new String[header.size()]));

		for (MessaggioModel messaggio : report.getMessaggiNonEsitati()	) {
			List<Object> oneLine = new ArrayList<Object>();

			oneLine.add(messaggio.getMsgId());
			String dataL = sdf.format(messaggio.getDataUltimaConsegnaRichiesta());
			oneLine.add(dataL);

			dataSource.add(oneLine.toArray(new Object[oneLine.size()]));
		}

		String titoloTabella = (report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE)  ? "Identificativi delle richieste non esitate" : "Identificativi delle notifiche non confermate");
		return cmp.subreport(
				report()
				.setTemplate(Templates.reportTemplate)
				.title(cmp.text(titoloTabella).setStyle(Templates.centeredStyle),cmp.verticalGap(10))
				.columns(colonne.toArray(new ColumnBuilder[colonne.size()]))
				.setDataSource(dataSource) 
				//.pageFooter(Templates.footerComponent)
				//.setTableOfContents(true)
				);
	}
	
	public static SubreportBuilder getTabellaRichiesteNonConsegnate(DatiQuadratura reportTotale, Report report ,String labelServizio) throws Exception{

		// Scittura Intestazione
		List<ColumnBuilder<?, ?>> colonne = new ArrayList<ColumnBuilder<?, ?>>();

		TextColumnBuilder<String> msgidColumn = col.column("MsgId", "msgid", type.stringType());
		TextColumnBuilder<String> dataColumn  = col.column("Data Richiesta", "data", type.stringType()); 

		colonne.add(msgidColumn);
		colonne.add(dataColumn);

		SimpleDateFormat sdf   = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALIAN);
		List<String> header = new ArrayList<String>();
		header.add("msgid");
		header.add("data");

		DRDataSource dataSource = new DRDataSource(header.toArray(new String[header.size()]));

		for (MessaggioModel messaggio : report.getMessaggiNonConsegnati()) {
			List<Object> oneLine = new ArrayList<Object>();

			oneLine.add(messaggio.getMsgId());
			String dataL = messaggio.getDataUltimaConsegnaRichiesta() != null ? sdf.format(messaggio.getDataUltimaConsegnaRichiesta()) : "--";
			oneLine.add(dataL);

			dataSource.add(oneLine.toArray(new Object[oneLine.size()]));
		}

		String titoloTabella = (report.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE)  ? "Identificativi delle richieste non consegnate" : "Identificativi delle notifiche non consegnate");
		return cmp.subreport(
				report()
				.setTemplate(Templates.reportTemplate)
				.title(cmp.text(titoloTabella).setStyle(Templates.centeredStyle),cmp.verticalGap(10))
				.columns(colonne.toArray(new ColumnBuilder[colonne.size()]))
				.setDataSource(dataSource) 
				//.pageFooter(Templates.footerComponent)
				//.setTableOfContents(true)
				);
	}

	public static SubreportBuilder getTabellaErroriConsegna(DatiQuadratura reportTotale, Report report) throws Exception{

		// Scittura Intestazione
		List<ColumnBuilder<?, ?>> colonne = new ArrayList<ColumnBuilder<?, ?>>();

		TextColumnBuilder<String> tipoColumn = col.column("Tipo Errore", "tipo", type.stringType());
		TextColumnBuilder<String> occorrenzeColumn  = col.column("Numero Occorrenze", "occorrenze", type.stringType()); 
		TextColumnBuilder<String> componenteColumn  = col.column("Componente Responsabile", "componente", type.stringType());

		colonne.add(tipoColumn);
		colonne.add(occorrenzeColumn);
		colonne.add(componenteColumn);

		List<String> header = new ArrayList<String>();
		header.add("tipo");
		header.add("occorrenze");
		header.add("componente");

		DRDataSource dataSource = new DRDataSource(header.toArray(new String[header.size()]));

		for (Err err : report.getErroriFrequenti()	) {
			List<Object> oneLine = new ArrayList<Object>();

			oneLine.add(err.getTipoErrore().getCodErrore());
			oneLine.add(err.getOccorenze() + "");
			oneLine.add(err.getTipoErrore().getCodiceComponente());

			dataSource.add(oneLine.toArray(new Object[oneLine.size()]));
		}

		return cmp.subreport(
				report()
				.setTemplate(Templates.reportTemplate)
				.title(cmp.text("Errori di consegna piu' frequenti").setStyle(Templates.centeredStyle),cmp.verticalGap(10))
				.columns(colonne.toArray(new ColumnBuilder[colonne.size()]))
				.setDataSource(dataSource) 
				);
	}

	public static String convertSystemTimeIntoString_millisecondi(long time,boolean millisecondiCheck){
		//System.out.println("VALORE PASSATO: ["+time+"]");
		long millisecondi = time % 1000;
		//System.out.println("Millisecondi (Valore%1000): ["+millisecondi+"]");
		long diff = (time)/1000;
		//System.out.println("Diff... (valore/1000) ["+diff+"]");
		long ore = diff/3600;
		//System.out.println("Ore... (diff/3600) ["+ore+"]");
		long minuti = (diff%3600) / 60;
		//System.out.println("Minuti... (diff%3600) / 60 ["+minuti+"]");
		long secondi = (diff%3600) % 60;
		//System.out.println("Secondi... (diff%3600) % 60 ["+secondi+"]");

		long giorni = ore/24;
		long oreRimaste = ore%24;


		StringBuffer bf = new StringBuffer();
		/*if(ore==1)
			bf.append(ore+" ora ");
		else if(ore>0)
			bf.append(ore+" ore ");*/
		if(giorni==1)
			bf.append(giorni+" g ");
		else if(giorni>0)
			bf.append(giorni+" g ");
		if(oreRimaste==1)
			bf.append(oreRimaste+" h ");
		else if(oreRimaste>0)
			bf.append(oreRimaste+" h ");
		if(minuti==1)
			bf.append(minuti+" m ");
		else if(minuti>0)
			bf.append(minuti+" m ");
		if(secondi==1)
			bf.append(secondi+" s ");
		else if(secondi>0)
			bf.append(secondi+" s ");
		if(millisecondiCheck){
			if(millisecondi==1)
				bf.append(millisecondi+" ms");
			else if(millisecondi>=0)
				bf.append(millisecondi+" ms");
		}
		if(bf.length()==0){
			bf.append("conversione non riuscita");
		}
		return bf.toString();
	}

	public static String numberConverter(Number bytes) {
		NumberFormat nf = NumberFormat.getInstance(Locale.ITALIAN);
		String res = "";
		res = nf.format(bytes.longValue());

		return res;
	}

	private class BarChartCustomizer implements DRIChartCustomizer, Serializable {
		private static final long serialVersionUID = 1L;

		private int tipo = ReportItemLabelGenerator.DS_TRASPARENTE;


		@SuppressWarnings("unused")
		public BarChartCustomizer() { 
			super();
			this.tipo = ReportItemLabelGenerator.DS_TRASPARENTE;
		}

		public BarChartCustomizer(int tipo){
			super();
			this.tipo = tipo;
		}

		@Override
		public void customize(JFreeChart chart, ReportParameters reportParameters) {

			RectangleInsets currentInsets = chart.getCategoryPlot().getInsets();
			chart.getCategoryPlot().setInsets(currentInsets,true);

			BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
			renderer.setMaximumBarWidth(0.05);

			if(VISUALIZZA_LABEL_NEL_GRAFICO){
				renderer.setBaseItemLabelGenerator(new ReportItemLabelGenerator(this.tipo));
				renderer.setBaseItemLabelsVisible(true);
				renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12 , TextAnchor.BOTTOM_LEFT, TextAnchor.BOTTOM_LEFT, - Math.PI / 6));

				Font labelFont = renderer.getBaseItemLabelFont();
				Font newLabelFont = new Font(labelFont.getFontName(), labelFont.getStyle(), Templates.rootFont.getFont().getFontSize());
				renderer.setBaseItemLabelFont(newLabelFont);
			}

			// Variabili per le label dell'asse y
			ValueAxis rangeAxis = chart.getCategoryPlot().getRangeAxis();
			rangeAxis.setVerticalTickLabels(false);
			//			Font labelFont = rangeAxis.getLabelFont();
			//			Font newLabelFont = new Font(labelFont.getFontName(), labelFont.getStyle(), Templates.rootFont.getFont().getFontSize());
			//			rangeAxis.setLabelFont(newLabelFont);

			// Variabili per le label dell'asse x
			CategoryAxis domainAxis = chart.getCategoryPlot().getDomainAxis();
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
			domainAxis.setVisible(true);

			//			Font categoryFont = domainAxis.getLabelFont();
			//			Font newCategoryFont = new Font(categoryFont.getFontName(), categoryFont.getStyle(), 8);
			//			domainAxis.setLabelFont(newCategoryFont);

			//domainAxis.setTickLabelsVisible(true); createDownRotationLabelPositions(- (Math.PI / 3.5))

		}
	}

	private class ReportItemLabelGenerator extends StandardCategoryItemLabelGenerator {

		public static final int DS_TRASPARENTE = 0;
		public static final int DS_NUMERO_RICHIESTE = 1;
		public static final int DS_LATENZE = 2;

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L; 

		private int tipo = DS_TRASPARENTE;

		@SuppressWarnings("unused")
		public ReportItemLabelGenerator() {
			super();
			this.tipo = DS_TRASPARENTE;
		}

		public ReportItemLabelGenerator(int tipo){
			super();
			this.tipo = tipo;
		}

		@Override
		public String generateLabel(CategoryDataset dataset, int row, int col) {

			Number value = dataset.getValue(row, col);

			switch (this.tipo) {
			case DS_LATENZE: {
				if(value.longValue() > 0)
					return getValoreTempo(value);
				else 
					return "";
			}
			case DS_NUMERO_RICHIESTE:
				return getValore(value);
			case DS_TRASPARENTE:
			default:
				return super.generateLabel(dataset, row, col);
			}
		}

		private String getValore(Number value){
			return numberConverter(value);
		}

		private String getValoreTempo(Number value){
			if(value != null){
				return convertSystemTimeIntoString_millisecondi(value.longValue(),true);
			}
			return null;
		}
	}
}
