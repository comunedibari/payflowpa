/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package it.regioneveneto.mygov.payment.mypivot.server;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.Assert;

import it.gov.digitpa.schemas._2011.pagamenti.CtDatiSingoliPagamenti;
import it.gov.digitpa.schemas._2011.pagamenti.CtIstitutoMittente;
import it.gov.digitpa.schemas._2011.pagamenti.CtIstitutoRicevente;
import it.regioneveneto.mybox.client.MyBoxClient;
import it.regioneveneto.mybox.domain.Errore;
import it.regioneveneto.mybox.domain.IntestazioneRisposta;
import it.regioneveneto.mybox.domain.MyBoxAuthorize;
import it.regioneveneto.mybox.domain.MyBoxAuthorizeRisposta;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaStato;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ManageFlusso;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoDettaglioService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoExportService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoRendicontazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoTesoreriaService;
import it.regioneveneto.mygov.payment.mypivot.service.ManageFlussoService;
import it.regioneveneto.mygov.payment.mypivot.service.PrenotazioneFlussoRiconciliazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.FaultConstants;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;
import it.veneto.regione.pagamenti.pivot.ente.CodiceClassificazioneType;
import it.veneto.regione.pagamenti.pivot.ente.CtAccertamento;
import it.veneto.regione.pagamenti.pivot.ente.CtBilancio;
import it.veneto.regione.pagamenti.pivot.ente.CtCapitolo;
import it.veneto.regione.pagamenti.pivot.ente.CtDatiVersamentoExport;
import it.veneto.regione.pagamenti.pivot.ente.CtExport;
import it.veneto.regione.pagamenti.pivot.ente.CtFlussoRiversamento;
import it.veneto.regione.pagamenti.pivot.ente.CtPagamentiRiconciliati;
import it.veneto.regione.pagamenti.pivot.ente.CtPagamentiRiconciliatiPerTipoDovuto;
import it.veneto.regione.pagamenti.pivot.ente.CtRiversamentiCumulativi;
import it.veneto.regione.pagamenti.pivot.ente.CtRiversamentiPuntuali;
import it.veneto.regione.pagamenti.pivot.ente.CtTipoDovuto;
import it.veneto.regione.pagamenti.pivot.ente.FaultBean;
import it.veneto.regione.pagamenti.pivot.ente.IdUnivocoRendicontazioneType;
import it.veneto.regione.pagamenti.pivot.ente.IdUnivocoVersamentoType;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILAutorizzaImportFlusso;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILAutorizzaImportFlussoRisposta;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILAutorizzaImportFlussoTesoreria;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILAutorizzaImportFlussoTesoreriaRisposta;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILChiediAccertamento;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILChiediAccertamentoRisposta;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILChiediPagatiRiconciliati;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILChiediPagatiRiconciliatiRisposta;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILChiediStatoExportFlussoRiconciliazione;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILChiediStatoExportFlussoRiconciliazioneRisposta;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILChiediStatoImportFlusso;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILChiediStatoImportFlussoRisposta;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILChiediStatoImportFlussoTesoreria;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILChiediStatoImportFlussoTesoreriaRisposta;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILPrenotaExportFlussoRiconciliazione;
import it.veneto.regione.pagamenti.pivot.ente.PivotSILPrenotaExportFlussoRiconciliazioneRisposta;
import it.veneto.regione.pagamenti.pivot.ente.RichiestaPerBolletta;
import it.veneto.regione.pagamenti.pivot.ente.RichiestaPerIUF;
import it.veneto.regione.pagamenti.pivot.ente.TipoDovutoType;
import it.veneto.regione.pagamenti.pivot.ente.pagamentitelematicipagatiriconciliati.PagamentiTelematiciPagatiRiconciliati;
import it.veneto.regione.pagamenti.pivot.ente.ppthead.IntestazionePPT;
import it.veneto.regione.schemas._2012.pagamenti.CtDatiSingoloPagamentoEsito;
import it.veneto.regione.schemas._2012.pagamenti.CtDominio;
import it.veneto.regione.schemas._2012.pagamenti.CtEnteBeneficiario;
import it.veneto.regione.schemas._2012.pagamenti.CtIdentificativoUnivoco;
import it.veneto.regione.schemas._2012.pagamenti.CtIdentificativoUnivocoPersonaFG;
import it.veneto.regione.schemas._2012.pagamenti.CtIdentificativoUnivocoPersonaG;
import it.veneto.regione.schemas._2012.pagamenti.CtIstitutoAttestante;
import it.veneto.regione.schemas._2012.pagamenti.CtSoggettoPagatore;
import it.veneto.regione.schemas._2012.pagamenti.CtSoggettoVersante;
import it.veneto.regione.schemas._2012.pagamenti.StTipoIdentificativoUnivoco;
import it.veneto.regione.schemas._2012.pagamenti.StTipoIdentificativoUnivocoPersFG;
import it.veneto.regione.schemas._2012.pagamenti.StTipoIdentificativoUnivocoPersG;

/**
 * This class was generated by Apache CXF 2.7.14 2015-09-18T12:11:56.918+02:00
 * Generated source version: 2.7.14
 * 
 */
/**
 * 
 * @author Cristiano Perin
 *
 */
@javax.jws.WebService(serviceName = "PagamentiTelematiciPagatiRiconciliatiService", portName = "PagamentiTelematiciPagatiRiconciliatiPort", targetNamespace = "http://www.regione.veneto.it/pagamenti/pivot/ente/PagamentiTelematiciPagatiRiconciliati", wsdlLocation = "classpath:it/regioneveneto/mygov/payment/mypivot/server/mypivot-per-ente.wsdl", endpointInterface = "it.veneto.regione.pagamenti.pivot.ente.pagamentitelematicipagatiriconciliati.PagamentiTelematiciPagatiRiconciliati")
public class PagamentiTelematiciPagatiRiconciliatiImpl implements PagamentiTelematiciPagatiRiconciliati {

	@Resource
	private Environment env;

	private static final Log LOG = LogFactory.getLog(PagamentiTelematiciPagatiRiconciliatiImpl.class.getName());

	private static final String querySelect = "SELECT " + "de_nome_flusso_e," + "num_riga_flusso_e, " + "cod_iud_e, "
			+ "cod_rp_silinviarp_id_univoco_versamento_e, " + "de_e_versione_oggetto_e, " + "cod_e_dom_id_dominio_e, "
			+ "cod_e_dom_id_stazione_richiedente_e, " + "cod_e_id_messaggio_ricevuta_e, "
			+ "dt_e_data_ora_messaggio_ricevuta_e, " + "cod_e_riferimento_messaggio_richiesta_e, "
			+ "dt_e_riferimento_data_richiesta_e, " + "cod_e_istit_att_id_univ_att_tipo_id_univoco_e, "
			+ "cod_e_istit_att_id_univ_att_codice_id_univoco_e, " + "de_e_istit_att_denominazione_attestante_e, "
			+ "cod_e_istit_att_codice_unit_oper_attestante_e, " + "de_e_istit_att_denom_unit_oper_attestante_e, "
			+ "de_e_istit_att_indirizzo_attestante_e, " + "de_e_istit_att_civico_attestante_e, "
			+ "cod_e_istit_att_cap_attestante_e, " + "de_e_istit_att_localita_attestante_e, "
			+ "de_e_istit_att_provincia_attestante_e, " + "cod_e_istit_att_nazione_attestante_e, "
			+ "cod_e_ente_benef_id_univ_benef_tipo_id_univoco_e, "
			+ "cod_e_ente_benef_id_univ_benef_codice_id_univoco_e, " + "de_e_ente_benef_denominazione_beneficiario_e, "
			+ "cod_e_ente_benef_codice_unit_oper_beneficiario_e, " + "de_e_ente_benef_denom_unit_oper_beneficiario_e, "
			+ "de_e_ente_benef_indirizzo_beneficiario_e, " + "de_e_ente_benef_civico_beneficiario_e, "
			+ "cod_e_ente_benef_cap_beneficiario_e, " + "de_e_ente_benef_localita_beneficiario_e, "
			+ "de_e_ente_benef_provincia_beneficiario_e, " + "cod_e_ente_benef_nazione_beneficiario_e, "
			+ "cod_e_sogg_vers_id_univ_vers_tipo_id_univoco_e, " + "cod_e_sogg_vers_id_univ_vers_codice_id_univoco_e, "
			+ "cod_e_sogg_vers_anagrafica_versante_e, " + "de_e_sogg_vers_indirizzo_versante_e, "
			+ "de_e_sogg_vers_civico_versante_e, " + "cod_e_sogg_vers_cap_versante_e, "
			+ "de_e_sogg_vers_localita_versante_e, " + "de_e_sogg_vers_provincia_versante_e, "
			+ "cod_e_sogg_vers_nazione_versante_e, " + "de_e_sogg_vers_email_versante_e, "
			+ "cod_e_sogg_pag_id_univ_pag_tipo_id_univoco_e, " + "cod_e_sogg_pag_id_univ_pag_codice_id_univoco_e, "
			+ "cod_e_sogg_pag_anagrafica_pagatore_e, " + "de_e_sogg_pag_indirizzo_pagatore_e, "
			+ "de_e_sogg_pag_civico_pagatore_e, " + "cod_e_sogg_pag_cap_pagatore_e, "
			+ "de_e_sogg_pag_localita_pagatore_e, " + "de_e_sogg_pag_provincia_pagatore_e, "
			+ "cod_e_sogg_pag_nazione_pagatore_e, " + "de_e_sogg_pag_email_pagatore_e, "
			+ "cod_e_dati_pag_codice_esito_pagamento_e, " + "num_e_dati_pag_importo_totale_pagato_e, "
			+ "cod_e_dati_pag_id_univoco_versamento_e, " + "cod_e_dati_pag_codice_contesto_pagamento_e, "
			+ "num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e, "
			+ "de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento_e, "
			+ "dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e, "
			+ "cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss_e, "
			+ "de_e_dati_pag_dati_sing_pag_causale_versamento_e, "
			+ "de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione_e, " + "cod_tipo_dovuto_e, "
			+ "dt_acquisizione_e, " + "mygov_ente_id_r, " + "mygov_manage_flusso_id_r, " + "versione_oggetto_r, "
			+ "cod_identificativo_flusso_r, " + "dt_data_ora_flusso_r, " + "cod_identificativo_univoco_regolamento_r, "
			+ "dt_data_regolamento_r, " + "cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco_r, "
			+ "cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco_r, " + "de_ist_mitt_denominazione_mittente_r, "
			+ "cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco_r, "
			+ "cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco_r, "
			+ "de_ist_ricev_denominazione_ricevente_r, " + "num_numero_totale_pagamenti_r, "
			+ "num_importo_totale_pagamenti_r, " + "cod_dati_sing_pagam_identificativo_univoco_versamento_r, "
			+ "cod_dati_sing_pagam_identificativo_univoco_riscossione_r, "
			+ "num_dati_sing_pagam_singolo_importo_pagato_r, "
			+ "cod_dati_sing_pagam_codice_esito_singolo_pagamento_r, "
			+ "dt_dati_sing_pagam_data_esito_singolo_pagamento_r, " + "dt_acquisizione_r";

	private static final String queryFrom = "FROM mygov_export_rendicontazione_completa";

	private static final String queryPerDataWhere = " WHERE mygov_ente_id_e = ? AND GREATEST(COALESCE(dt_acquisizione_e,'2099-12-31'), COALESCE(dt_acquisizione_r,'2014-01-01')) >= ? AND GREATEST(COALESCE(dt_acquisizione_e,'2099-12-31'), COALESCE(dt_acquisizione_r,'2014-01-01')) < ?";

	private static final String query_filtroTipoDovuto = "AND cod_tipo_dovuto_e IN ";

	private static final String orderByStatement = " ORDER BY cod_tipo_dovuto_e, GREATEST(dt_acquisizione_e, dt_acquisizione_r)";

	private static final String limitStatement = " LIMIT 1000";

	private static final String CODE_PIVOT_RICHIESTA_PER_IUV_IUF_NON_COMPLETA = "PIVOT_RICHIESTA_PER_IUV_IUF_NON_COMPLETA";

	private static final String DESC_PIVOT_RICHIESTA_PER_IUV_IUF_NON_COMPLETA = "Valorizzare almeno un oggetto tra 'riversamentiPuntuali' e 'riversamentiCumulativi'";

	private static final String CODE_PIVOT_RICHIESTA_NON_VALORIZZATA = "PIVOT_RICHIESTA_NON_VALORIZZATA";

	private static final String DESC_PIVOT_RICHIESTA_NON_VALORIZZATA = "Valorizzare almeno un oggetto tra 'ctRichiestaPerIUVIUF' e 'ctRichiestaPerData'";

	private static final String CODE_PIVOT_DATE_FROM_NON_VALIDO = "CODE_PIVOT_DATE_FROM_NON_VALIDO";

	private static final String DESC_PIVOT_DATE_FROM_NON_VALIDO = "Data inizio non valida";

	private static final String CODE_PIVOT_DATE_TO_NON_VALIDO = "CODE_PIVOT_DATE_TO_NON_VALIDO";

	private static final String DESC_PIVOT_DATE_TO_NON_VALIDO = "Data fine non valida";

	private static final String CODE_PIVOT_INTERVALLO_DATE_NON_VALIDO = "CODE_PIVOT_INTERVALLO_DATE_NON_VALIDO";

	private static final String DESC_PIVOT_INTERVALLO_DATE_NON_VALIDO = "L’intervallo data inizio e data fine non è valido";

	private static final String CODE_PIVOT_SYSTEM_ERROR = "CODE_PIVOT_SYSTEM_ERROR";

	private static final String CODE_PIVOT_ENTE_NON_VALIDO = "CODE_PIVOT_ENTE_NON_VALIDO";

	private static final String CODE_PIVOT_REQUEST_TOKEN_NON_VALIDO = "PIVOT_REQUEST_TOKEN_NON_VALIDO";
	
	private static final String CODE_PIVOT_TIPO_FLUSSO_NON_VALIDO = "CODE_PIVOT_TIPO_FLUSSO_NON_VALIDO";

	private static final String PIVOT_SYSTEM_ERROR = "PIVOT_SYSTEM_ERROR";

	private static final String WS_USER = "WS_USER";

	private static final String selectQueryAccertamento = "select ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo, ad.cod_accertamento, sum(ad.num_importo) as sum_importo";
	private static final String fromQueryAccertamento = "from mygov_accertamento_dettaglio ad, mygov_accertamento a, mygov_anagrafica_stato an";
	private static final String whereQueryAccertamento = "where ad.mygov_accertamento_id = a.mygov_accertamento_id and a.mygov_anagrafica_stato_id = an.mygov_anagrafica_stato_id";
	private static final String groupByQueryAccertamento = "group by ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo, ad.cod_accertamento ";
	private static final String orderByQueryAccertamento = "order by ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo, ad.cod_accertamento";

	@Autowired
	private DataSource dataSource;

	@Autowired
	private EnteService enteService;

	@Autowired
	private EnteTipoDovutoService enteTipoDovutoService;

	@Autowired
	private ManageFlussoService manageFlussoService;

	@Autowired
	private PrenotazioneFlussoRiconciliazioneService prenotazioneFlussoRiconciliazioneService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private FlussoTesoreriaService flussoTesoreriaService;

	@Autowired
	private FlussoRendicontazioneService flussoRendicontazioneService;

	@Autowired
	private FlussoExportService flussoExportService;

	@Autowired
	private AccertamentoDettaglioService accertamentoDettaglioService;

	@Autowired
	private MyBoxClient myBoxClient;

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.veneto.regione.pagamenti.pivot.ente.
	 * pagamentitelematicipagatiriconciliati.
	 * PagamentiTelematiciPagatiRiconciliati#pivotSILChiediPagatiRiconciliati(it
	 * .veneto.regione.pagamenti.pivot.ente.PivotSILChiediPagatiRiconciliati
	 * bodyrichiesta )*
	 */
	public PivotSILChiediPagatiRiconciliatiRisposta pivotSILChiediPagatiRiconciliati(
			final PivotSILChiediPagatiRiconciliati bodyrichiesta) {
		LOG.info("Executing operation pivotSILChiediPagatiRiconciliati");

		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			PivotSILChiediPagatiRiconciliatiRisposta pivotSILChiediPagatiRiconciliatiRisposta = new PivotSILChiediPagatiRiconciliatiRisposta();

			String codIpaEnte = bodyrichiesta.getCodIpaEnte();
			EnteTO ente = enteService.getByCodIpaEnte(codIpaEnte);
			if (ente == null) {
				LOG.error("pivotSILChiediPagatiRiconciliati: Ente non valido: " + codIpaEnte);

				it.veneto.regione.pagamenti.pivot.ente.FaultBean faultBean = this.getFaultBean(codIpaEnte,
						CODE_PIVOT_ENTE_NON_VALIDO, "codice IPA Ente [" + codIpaEnte + "] non valido o password errata",
						null);

				pivotSILChiediPagatiRiconciliatiRisposta.setFault(faultBean);
				return pivotSILChiediPagatiRiconciliatiRisposta;
			}

			Boolean passwordValidaPerEnte = enteService.verificaPassword(ente.getCodIpaEnte(),
					bodyrichiesta.getPassword());
			if (!passwordValidaPerEnte) {
				LOG.error("pivotSILChiediPagatiRiconciliati: Password non valida per ente: "
						+ bodyrichiesta.getCodIpaEnte());

				it.veneto.regione.pagamenti.pivot.ente.FaultBean faultBean = this.getFaultBean(
						bodyrichiesta.getCodIpaEnte(), CODE_PIVOT_ENTE_NON_VALIDO,
						"Password non valida per ente [" + bodyrichiesta.getCodIpaEnte() + "]", null);

				pivotSILChiediPagatiRiconciliatiRisposta.setFault(faultBean);
				return pivotSILChiediPagatiRiconciliatiRisposta;
			}

			PreparedStatementCreator preparedStatementCreator_QueryDataTipoDovuto = null;
			PreparedStatementCreator preparedStatementCreator_QueryIUVIUFTipoDovuto = null;

			PivotSILChiediPagatiRiconciliatiRispostaExtractor pivotSILChiediPagatiRiconciliatiRispostaExtractor = new PivotSILChiediPagatiRiconciliatiRispostaExtractor();

			if (bodyrichiesta.getRichiestaPerData() != null) {

				XMLGregorianCalendar dateExtractionFrom = bodyrichiesta.getRichiestaPerData().getDataDa();
				XMLGregorianCalendar dateExtractionTo = bodyrichiesta.getRichiestaPerData().getDataA();

				/*
				 * ************************************** CONTROLLO SULLA
				 * CORRETTEZZA DELLE DATE **************************************
				 */
				Date _dtExtractionFrom = null;
				Date _dtExtractionTo = null;
				_dtExtractionFrom = Utilities.toDate(dateExtractionFrom);
				if (_dtExtractionFrom == null) {
					LOG.error("pivotSILChiediPagatiRiconciliati: Data di inizio non valida");

					it.veneto.regione.pagamenti.pivot.ente.FaultBean faultBean = this.getFaultBean(
							bodyrichiesta.getCodIpaEnte(), CODE_PIVOT_DATE_FROM_NON_VALIDO,
							DESC_PIVOT_DATE_FROM_NON_VALIDO, null);

					pivotSILChiediPagatiRiconciliatiRisposta.setFault(faultBean);
					return pivotSILChiediPagatiRiconciliatiRisposta;
				}
				_dtExtractionFrom = new Date(_dtExtractionFrom.getTime() / 1000 * 1000);

				_dtExtractionTo = Utilities.toDate(dateExtractionTo);
				if (_dtExtractionTo == null) {
					LOG.error("pivotSILChiediPagatiRiconciliati: Data di fine non valida");

					it.veneto.regione.pagamenti.pivot.ente.FaultBean faultBean = this.getFaultBean(
							bodyrichiesta.getCodIpaEnte(), CODE_PIVOT_DATE_TO_NON_VALIDO, DESC_PIVOT_DATE_TO_NON_VALIDO,
							null);

					pivotSILChiediPagatiRiconciliatiRisposta.setFault(faultBean);
					return pivotSILChiediPagatiRiconciliatiRisposta;
				}
				_dtExtractionTo = new Date(_dtExtractionTo.getTime() / 1000 * 1000);

				/*
				 * ************************************** CONTROLLO
				 * SULL'INTERVALLO DI TEMPO
				 * **************************************
				 */
				if (_dtExtractionTo != null && _dtExtractionFrom != null) {
					if (_dtExtractionTo.before(_dtExtractionFrom)) {
						LOG.error("pivotSILChiediPagatiRiconciliati: Data di inizio successiva alla data di fine");

						it.veneto.regione.pagamenti.pivot.ente.FaultBean faultBean = this.getFaultBean(
								bodyrichiesta.getCodIpaEnte(), CODE_PIVOT_INTERVALLO_DATE_NON_VALIDO,
								DESC_PIVOT_INTERVALLO_DATE_NON_VALIDO, null);

						pivotSILChiediPagatiRiconciliatiRisposta.setFault(faultBean);
						return pivotSILChiediPagatiRiconciliatiRisposta;
					}
				}
				final Long enteId = ente.getId();
				final Date dtExtractionFrom = _dtExtractionFrom;
				final Date dtExtractionTo = _dtExtractionTo;

				preparedStatementCreator_QueryDataTipoDovuto = new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

						String statement = querySelect + " " + queryFrom + " " + queryPerDataWhere;

						if (bodyrichiesta.getRichiestaPerData().getFiltroTipiDovuto() != null && bodyrichiesta
								.getRichiestaPerData().getFiltroTipiDovuto().getIdentificativoTipoDovuto().size() > 0) {
							statement += query_filtroTipoDovuto;

							List<String> tipiDovuto = bodyrichiesta.getRichiestaPerData().getFiltroTipiDovuto()
									.getIdentificativoTipoDovuto();
							statement += "(";
							for (String string : tipiDovuto) {
								if (tipiDovuto.indexOf(string) == 0)
									statement += "?";
								else
									statement += ",?";
							}
							statement += ")";
						}

						statement += orderByStatement;

						statement += limitStatement;

						PreparedStatement ps = con.prepareStatement(statement);

						ps.setLong(1, enteId);
						ps.setDate(2, new java.sql.Date(dtExtractionFrom.getTime()));
						ps.setDate(3, new java.sql.Date(dtExtractionTo.getTime()));

						if (bodyrichiesta.getRichiestaPerData().getFiltroTipiDovuto() != null && bodyrichiesta
								.getRichiestaPerData().getFiltroTipiDovuto().getIdentificativoTipoDovuto().size() > 0) {

							List<String> tipiDovuto = bodyrichiesta.getRichiestaPerData().getFiltroTipiDovuto()
									.getIdentificativoTipoDovuto();
							for (String string : tipiDovuto) {
								ps.setObject(4 + tipiDovuto.indexOf(string), string);
							}
						}

						return ps;
					}
				};

				pivotSILChiediPagatiRiconciliatiRisposta = jdbcTemplate.query(
						preparedStatementCreator_QueryDataTipoDovuto,
						pivotSILChiediPagatiRiconciliatiRispostaExtractor);

			} else if (bodyrichiesta.getRichiestaPerIUVIUF() != null) {

				/*
				 * ************************************** CONTROLLO ALMENO UNO
				 * TRA RiversamentiCumulativi e RiversamentiPuntuali valorizzato
				 * **************************************
				 */
				CtRiversamentiCumulativi riversamentiCumulativi = bodyrichiesta.getRichiestaPerIUVIUF()
						.getRiversamentiCumulativi();
				CtRiversamentiPuntuali riversamentiPuntuali = bodyrichiesta.getRichiestaPerIUVIUF()
						.getRiversamentiPuntuali();

				if (riversamentiCumulativi == null || riversamentiCumulativi.getIdentificativoFlusso().size() == 0) {
					if (riversamentiPuntuali == null
							|| riversamentiPuntuali.getIdentificativoUnivocoVersamento().size() == 0) {
						LOG.error(
								"pivotSILChiediPagatiRiconciliati: riversamentiCumulativi e riversamentiPuntuali non presenti");

						it.veneto.regione.pagamenti.pivot.ente.FaultBean faultBean = this.getFaultBean(
								bodyrichiesta.getCodIpaEnte(), CODE_PIVOT_RICHIESTA_PER_IUV_IUF_NON_COMPLETA,
								DESC_PIVOT_RICHIESTA_PER_IUV_IUF_NON_COMPLETA, null);
						pivotSILChiediPagatiRiconciliatiRisposta.setFault(faultBean);
						return pivotSILChiediPagatiRiconciliatiRisposta;
					}
				}
				if (riversamentiPuntuali == null
						|| riversamentiPuntuali.getIdentificativoUnivocoVersamento().size() == 0) {
					if (riversamentiCumulativi == null
							|| riversamentiCumulativi.getIdentificativoFlusso().size() == 0) {
						LOG.error(
								"pivotSILChiediPagatiRiconciliati: riversamentiPuntuali e riversamentiCumulativi non presenti");

						it.veneto.regione.pagamenti.pivot.ente.FaultBean faultBean = this.getFaultBean(
								bodyrichiesta.getCodIpaEnte(), CODE_PIVOT_RICHIESTA_PER_IUV_IUF_NON_COMPLETA,
								DESC_PIVOT_RICHIESTA_PER_IUV_IUF_NON_COMPLETA, null);
						pivotSILChiediPagatiRiconciliatiRisposta.setFault(faultBean);
						return pivotSILChiediPagatiRiconciliatiRisposta;
					}
				}
				final Long enteId = ente.getId();

				preparedStatementCreator_QueryIUVIUFTipoDovuto = new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

						CtRiversamentiCumulativi riversamentiCumulativi = bodyrichiesta.getRichiestaPerIUVIUF()
								.getRiversamentiCumulativi();
						CtRiversamentiPuntuali riversamentiPuntuali = bodyrichiesta.getRichiestaPerIUVIUF()
								.getRiversamentiPuntuali();

						String statement = querySelect + " " + queryFrom + " WHERE mygov_ente_id_e = ? AND (";
						// nome_flusso_import_ente IN (?) OR codice_iuv IN (?)

						int numRiversamentiCumulativiInseriti = 0;
						int numRiversamentiPuntualiInseriti = 0;

						// RIVERSAMENTI CUMULATIVI
						if (riversamentiCumulativi != null
								&& riversamentiCumulativi.getIdentificativoFlusso().size() > 0) {
							statement += "cod_identificativo_flusso_r IN ";

							List<String> identificativoStrings = bodyrichiesta.getRichiestaPerIUVIUF()
									.getRiversamentiCumulativi().getIdentificativoFlusso();
							statement += "(";
							for (String string : identificativoStrings) {
								numRiversamentiCumulativiInseriti++;

								if (identificativoStrings.indexOf(string) == 0)
									statement += "?";
								else
									statement += ",?";
							}
							statement += ")";
						}

						// RIVERSAMENTI PUNTUALI
						if (riversamentiPuntuali != null
								&& riversamentiPuntuali.getIdentificativoUnivocoVersamento().size() > 0) {
							if (numRiversamentiCumulativiInseriti > 0) {
								statement += " OR ";
							}

							statement += "cod_e_dati_pag_id_univoco_versamento_e IN ";

							List<String> identificativoStrings = bodyrichiesta.getRichiestaPerIUVIUF()
									.getRiversamentiPuntuali().getIdentificativoUnivocoVersamento();
							statement += "(";
							for (String string : identificativoStrings) {
								numRiversamentiPuntualiInseriti++;

								if (identificativoStrings.indexOf(string) == 0)
									statement += "?";
								else
									statement += ",?";
							}
							statement += ")";
						}

						statement += ") ";

						if (bodyrichiesta.getRichiestaPerIUVIUF().getFiltroTipiDovuto() != null
								&& bodyrichiesta.getRichiestaPerIUVIUF().getFiltroTipiDovuto()
										.getIdentificativoTipoDovuto().size() > 0) {
							statement += query_filtroTipoDovuto;

							List<String> tipiDovuto = bodyrichiesta.getRichiestaPerIUVIUF().getFiltroTipiDovuto()
									.getIdentificativoTipoDovuto();
							statement += "(";
							for (String string : tipiDovuto) {
								if (tipiDovuto.indexOf(string) == 0)
									statement += "?";
								else
									statement += ",?";
							}
							statement += ")";
						}

						statement += orderByStatement;

						// statement += limitStatement;

						PreparedStatement ps = con.prepareStatement(statement);
						ps.setLong(1, enteId);

						if (numRiversamentiCumulativiInseriti > 0) {
							List<String> identificativoStrings = bodyrichiesta.getRichiestaPerIUVIUF()
									.getRiversamentiCumulativi().getIdentificativoFlusso();
							for (String string : identificativoStrings) {
								ps.setObject(2 + identificativoStrings.indexOf(string), string);
							}
						}
						if (numRiversamentiPuntualiInseriti > 0) {
							List<String> identificativoStrings = bodyrichiesta.getRichiestaPerIUVIUF()
									.getRiversamentiPuntuali().getIdentificativoUnivocoVersamento();
							for (String string : identificativoStrings) {
								ps.setObject(
										2 + numRiversamentiCumulativiInseriti + identificativoStrings.indexOf(string),
										string);
							}
						}
						if (bodyrichiesta.getRichiestaPerIUVIUF().getFiltroTipiDovuto() != null
								&& bodyrichiesta.getRichiestaPerIUVIUF().getFiltroTipiDovuto()
										.getIdentificativoTipoDovuto().size() > 0) {

							List<String> tipiDovuto = bodyrichiesta.getRichiestaPerIUVIUF().getFiltroTipiDovuto()
									.getIdentificativoTipoDovuto();
							for (String string : tipiDovuto) {
								ps.setObject(2 + numRiversamentiCumulativiInseriti + numRiversamentiPuntualiInseriti
										+ tipiDovuto.indexOf(string), string);
							}
						}
						return ps;
					}
				};

				pivotSILChiediPagatiRiconciliatiRisposta = jdbcTemplate.query(
						preparedStatementCreator_QueryIUVIUFTipoDovuto,
						pivotSILChiediPagatiRiconciliatiRispostaExtractor);
			} else {
				it.veneto.regione.pagamenti.pivot.ente.FaultBean faultBean = this.getFaultBean(
						bodyrichiesta.getCodIpaEnte(), CODE_PIVOT_RICHIESTA_NON_VALORIZZATA,
						DESC_PIVOT_RICHIESTA_NON_VALORIZZATA, null);
				pivotSILChiediPagatiRiconciliatiRisposta.setFault(faultBean);
				return pivotSILChiediPagatiRiconciliatiRisposta;
			}

			return pivotSILChiediPagatiRiconciliatiRisposta;

		} catch (java.lang.Exception ex) {

			LOG.error("pivotSILChiediPagatiRiconciliati: Error executing operation [" + ex.getLocalizedMessage() + "]");

			it.veneto.regione.pagamenti.pivot.ente.FaultBean faultBean = this.getFaultBean(
					bodyrichiesta.getCodIpaEnte(), CODE_PIVOT_SYSTEM_ERROR, ex.getLocalizedMessage(), null);

			PivotSILChiediPagatiRiconciliatiRisposta pivotSILChiediPagatiRiconciliatiRisposta = new PivotSILChiediPagatiRiconciliatiRisposta();
			pivotSILChiediPagatiRiconciliatiRisposta.setFault(faultBean);
			return pivotSILChiediPagatiRiconciliatiRisposta;
		}
	}

	private it.veneto.regione.pagamenti.pivot.ente.FaultBean getFaultBean(String faultID, String faultCode,
			String faultString, String description) {

		LOG.error(faultCode + " " + description);

		it.veneto.regione.pagamenti.pivot.ente.FaultBean faultBean = new it.veneto.regione.pagamenti.pivot.ente.FaultBean();
		faultBean.setId(faultID);
		faultBean.setFaultCode(faultCode);
		faultBean.setFaultString(faultString);
		faultBean.setDescription(description);

		return faultBean;
	}

	private static final class PivotSILChiediPagatiRiconciliatiRispostaExtractor
			implements ResultSetExtractor<PivotSILChiediPagatiRiconciliatiRisposta> {

		@Override
		public PivotSILChiediPagatiRiconciliatiRisposta extractData(ResultSet rs)
				throws SQLException, DataAccessException {
			LOG.info("pivotSILChiediPagatiRiconciliati: Ricevuto ResultSet");
			LOG.info("pivotSILChiediPagatiRiconciliati: INIZIO MAPPING RISPOSTA");

			String currentTipoDovuto = null;
			CtPagamentiRiconciliatiPerTipoDovuto currentCtPagamentiRiconciliatiPerTipoDovuto = null;

			PivotSILChiediPagatiRiconciliatiRisposta pivotSILChiediPagatiRiconciliatiRisposta = new PivotSILChiediPagatiRiconciliatiRisposta();

			int numeroTotale = 0;
			java.sql.Date dataA = null;
			while (rs.next()) {

				String tipoDovuto = rs.getString("cod_tipo_dovuto_e");
				// String codiceIUV =
				// rs.getString("cod_e_dati_pag_id_univoco_versamento_e");

				if (StringUtils.isBlank(tipoDovuto)) {
					tipoDovuto = "INCASSO_SENZA_RT";
				}

				// RAGGRUPPAMENTO TIPO DOVUTO
				if (!StringUtils.equalsIgnoreCase(currentTipoDovuto, tipoDovuto)) { // CAMBIO
																					// TIPO
																					// oppure
																					// PRIMA
																					// RIGA

					// PRIMA RIGA
					if (currentCtPagamentiRiconciliatiPerTipoDovuto != null)
						pivotSILChiediPagatiRiconciliatiRisposta.getPagamentiRiconciliatiPerTipoDovuto()
								.add(currentCtPagamentiRiconciliatiPerTipoDovuto);// aggiunge
																					// a
																					// risposta
																					// tipo
																					// precednte

					// imposta nuovo tipo corrente
					currentTipoDovuto = tipoDovuto;
					currentCtPagamentiRiconciliatiPerTipoDovuto = new CtPagamentiRiconciliatiPerTipoDovuto();
					currentCtPagamentiRiconciliatiPerTipoDovuto.setIdentificativoTipoDovuto(tipoDovuto);

				}

				CtPagamentiRiconciliati ctPagamentiRiconciliati = new CtPagamentiRiconciliati();

				ctPagamentiRiconciliati.setPagamento(extractCtExport(rs));
				ctPagamentiRiconciliati.setRiversamento(extractCtFlussoRiversamento(rs));

				// calcolo la data di aggiornamento del pagato riconciliato
				// estratto
				java.sql.Date dt_acquisizione_e = rs.getDate("dt_acquisizione_e");
				java.sql.Date dt_acquisizione_r = rs.getDate("dt_acquisizione_r");

				java.sql.Date data = null;
				if (dt_acquisizione_r != null) {
					if (dt_acquisizione_r.after(dt_acquisizione_e)) {
						data = dt_acquisizione_r;
					} else {
						data = dt_acquisizione_e;
					}
				} else {
					data = dt_acquisizione_e;
				}

				ctPagamentiRiconciliati.setData(Utilities.toXMLGregorianCalendar(data));

				// calcolo la data di aggiornamento più grande fra quelle dei
				// pagati riconciliati estratti
				if (dataA == null) {
					dataA = data;
				} else {
					// aggiorno dataA se data corrente e' piu' grande
					if (data.after(dataA)) {
						dataA = data;
					}
				}

				numeroTotale++;

				currentCtPagamentiRiconciliatiPerTipoDovuto.getPagamentiRiconciliati().add(ctPagamentiRiconciliati);
			}

			// ultimo risultato
			pivotSILChiediPagatiRiconciliatiRisposta.getPagamentiRiconciliatiPerTipoDovuto()
					.add(currentCtPagamentiRiconciliatiPerTipoDovuto);

			// numero totale dati estratti <= 1000
			pivotSILChiediPagatiRiconciliatiRisposta.setNumeroTotale(BigInteger.valueOf(numeroTotale));

			// data A
			if (dataA != null) {
				pivotSILChiediPagatiRiconciliatiRisposta.setDataA(Utilities.toXMLGregorianCalendar(dataA));
			}

			LOG.info("pivotSILChiediPagatiRiconciliati: FINE RISPOSTA");
			return pivotSILChiediPagatiRiconciliatiRisposta;
		}

		private CtExport extractCtExport(ResultSet rs) throws SQLException {
			// CtExport
			CtExport ctExport = new CtExport();

			// "versioneOggetto",
			ctExport.setVersioneOggetto(rs.getString("de_e_versione_oggetto_e"));

			// "dominio",
			ctExport.setDominio(extractCtDominio(rs));

			// "identificativoMessaggioRicevuta",
			ctExport.setIdentificativoMessaggioRicevuta(rs.getString("cod_e_id_messaggio_ricevuta_e"));

			// "dataOraMessaggioRicevuta",
			ctExport.setDataOraMessaggioRicevuta(
					Utilities.toXMLGregorianCalendar(rs.getDate("dt_e_data_ora_messaggio_ricevuta_e")));

			// "riferimentoMessaggioRichiesta",
			ctExport.setRiferimentoMessaggioRichiesta(rs.getString("cod_e_riferimento_messaggio_richiesta_e"));

			// "riferimentoDataRichiesta",
			ctExport.setRiferimentoDataRichiesta(
					Utilities.toXMLGregorianCalendar(rs.getDate("dt_e_riferimento_data_richiesta_e")));

			// "istitutoAttestante",
			if (StringUtils.isNotBlank(rs.getString("de_e_istit_att_denominazione_attestante_e"))) {
				ctExport.setIstitutoAttestante(extractCtIstitutoAttestante(rs));
			}

			// "enteBeneficiario",
			if (StringUtils.isNotBlank(rs.getString("de_e_ente_benef_denominazione_beneficiario_e"))) {
				ctExport.setEnteBeneficiario(extractCtEnteBeneficiario(rs));
			}

			// "soggettoVersante",
			if (StringUtils.isNotBlank(rs.getString("cod_e_sogg_vers_anagrafica_versante_e"))) {
				ctExport.setSoggettoVersante(extractCtSoggettoVersante(rs));
			}

			// "soggettoPagatore",
			if (StringUtils.isNotBlank(rs.getString("cod_e_sogg_pag_anagrafica_pagatore_e"))) {
				ctExport.setSoggettoPagatore(extractCtSoggettoPagatore(rs));
			}

			// "datiPagamento",
			ctExport.setDatiPagamento(extractCtDatiVersamentoExport(rs));

			// "codice_iud"
			ctExport.setIdentificativoUnivocoDovuto(rs.getString("cod_iud_e"));

			return ctExport;
		}

		private CtDominio extractCtDominio(ResultSet rs) throws SQLException {
			// @XmlType(name = "ctDominio", propOrder = {
			// "identificativoDominio",
			// "identificativoStazioneRichiedente"
			CtDominio ctDominio = new CtDominio();
			ctDominio.setIdentificativoDominio(rs.getString("cod_e_dom_id_dominio_e"));
			ctDominio.setIdentificativoStazioneRichiedente(rs.getString("cod_e_dom_id_stazione_richiedente_e"));
			return ctDominio;
		}

		private CtIstitutoAttestante extractCtIstitutoAttestante(ResultSet rs) throws SQLException {
			CtIstitutoAttestante istitutoAttestante = new CtIstitutoAttestante();

			// @XmlType(name = "ctIstitutoAttestante", propOrder = {
			// "identificativoUnivocoAttestante",
			// @XmlAccessorType(XmlAccessType.FIELD)
			// @XmlType(name = "ctIdentificativoUnivoco", propOrder = {
			// "tipoIdentificativoUnivoco",
			// "codiceIdentificativoUnivoco"
			// })
			CtIdentificativoUnivoco identificativoUnivocoAttestante = new CtIdentificativoUnivoco();
			identificativoUnivocoAttestante.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivoco
					.fromValue(rs.getString("cod_e_istit_att_id_univ_att_tipo_id_univoco_e")));
			identificativoUnivocoAttestante
					.setCodiceIdentificativoUnivoco(rs.getString("cod_e_istit_att_id_univ_att_codice_id_univoco_e"));
			istitutoAttestante.setIdentificativoUnivocoAttestante(identificativoUnivocoAttestante);

			// "denominazioneAttestante",
			istitutoAttestante.setDenominazioneAttestante(rs.getString("de_e_istit_att_denominazione_attestante_e"));

			// "codiceUnitOperAttestante",
			istitutoAttestante
					.setCodiceUnitOperAttestante(rs.getString("cod_e_istit_att_codice_unit_oper_attestante_e"));

			// "denomUnitOperAttestante",
			istitutoAttestante.setDenomUnitOperAttestante(rs.getString("de_e_istit_att_denom_unit_oper_attestante_e"));

			// "indirizzoAttestante",
			istitutoAttestante.setIndirizzoAttestante(rs.getString("de_e_istit_att_indirizzo_attestante_e"));

			// "civicoAttestante",
			istitutoAttestante.setCivicoAttestante(rs.getString("de_e_istit_att_civico_attestante_e"));

			// "capAttestante",
			istitutoAttestante.setCapAttestante(rs.getString("cod_e_istit_att_cap_attestante_e"));

			// "localitaAttestante",
			istitutoAttestante.setLocalitaAttestante(rs.getString("de_e_istit_att_localita_attestante_e"));

			// "provinciaAttestante",
			istitutoAttestante.setProvinciaAttestante(rs.getString("de_e_istit_att_provincia_attestante_e"));

			// "nazioneAttestante"
			istitutoAttestante.setNazioneAttestante(rs.getString("cod_e_istit_att_nazione_attestante_e"));

			// })
			return istitutoAttestante;
		}

		private CtEnteBeneficiario extractCtEnteBeneficiario(ResultSet rs) throws SQLException {
			CtEnteBeneficiario enteBeneficiario = new CtEnteBeneficiario();

			// @XmlType(name = "ctEnteBeneficiario", propOrder = {
			// "identificativoUnivocoBeneficiario",
			// @XmlType(name = "ctIdentificativoUnivocoPersonaG", propOrder = {
			// "tipoIdentificativoUnivoco",
			// "codiceIdentificativoUnivoco"
			// })
			CtIdentificativoUnivocoPersonaG identificativoUnivocoBeneficiario = new CtIdentificativoUnivocoPersonaG();
			identificativoUnivocoBeneficiario.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersG
					.fromValue(rs.getString("cod_e_ente_benef_id_univ_benef_tipo_id_univoco_e")));
			identificativoUnivocoBeneficiario
					.setCodiceIdentificativoUnivoco(rs.getString("cod_e_ente_benef_id_univ_benef_codice_id_univoco_e"));
			enteBeneficiario.setIdentificativoUnivocoBeneficiario(identificativoUnivocoBeneficiario);

			// "denominazioneBeneficiario",
			enteBeneficiario.setDenominazioneBeneficiario(rs.getString("de_e_ente_benef_denominazione_beneficiario_e"));

			// "codiceUnitOperBeneficiario",
			enteBeneficiario
					.setCodiceUnitOperBeneficiario(rs.getString("cod_e_ente_benef_codice_unit_oper_beneficiario_e"));

			// "denomUnitOperBeneficiario",
			enteBeneficiario
					.setDenomUnitOperBeneficiario(rs.getString("de_e_ente_benef_denom_unit_oper_beneficiario_e"));

			// "indirizzoBeneficiario",
			enteBeneficiario.setIndirizzoBeneficiario(rs.getString("de_e_ente_benef_indirizzo_beneficiario_e"));

			// "civicoBeneficiario",
			enteBeneficiario.setCivicoBeneficiario(rs.getString("de_e_ente_benef_civico_beneficiario_e"));

			// "capBeneficiario",
			enteBeneficiario.setCapBeneficiario(rs.getString("cod_e_ente_benef_cap_beneficiario_e"));

			// "localitaBeneficiario",
			enteBeneficiario.setLocalitaBeneficiario(rs.getString("de_e_ente_benef_localita_beneficiario_e"));

			// "provinciaBeneficiario",
			enteBeneficiario.setProvinciaBeneficiario(rs.getString("de_e_ente_benef_provincia_beneficiario_e"));

			// "nazioneBeneficiario"
			enteBeneficiario.setNazioneBeneficiario(rs.getString("cod_e_ente_benef_nazione_beneficiario_e"));

			// })
			return enteBeneficiario;
		}

		private CtSoggettoVersante extractCtSoggettoVersante(ResultSet rs) throws SQLException {
			CtSoggettoVersante soggettoVersante = new CtSoggettoVersante();
			// @XmlType(name = "ctSoggettoVersante", propOrder = {
			// "identificativoUnivocoVersante",
			// @XmlType(name = "ctIdentificativoUnivocoPersonaFG", propOrder = {
			// "tipoIdentificativoUnivoco",
			// "codiceIdentificativoUnivoco"
			// })
			CtIdentificativoUnivocoPersonaFG identificativoUnivocoVersante = new CtIdentificativoUnivocoPersonaFG();
			identificativoUnivocoVersante.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersFG
					.valueOf(rs.getString("cod_e_sogg_vers_id_univ_vers_tipo_id_univoco_e")));
			identificativoUnivocoVersante
					.setCodiceIdentificativoUnivoco(rs.getString("cod_e_sogg_vers_id_univ_vers_codice_id_univoco_e"));
			soggettoVersante.setIdentificativoUnivocoVersante(identificativoUnivocoVersante);

			// "anagraficaVersante",
			soggettoVersante.setAnagraficaVersante(rs.getString("cod_e_sogg_vers_anagrafica_versante_e"));

			// "indirizzoVersante",
			soggettoVersante.setIndirizzoVersante(rs.getString("de_e_sogg_vers_indirizzo_versante_e"));

			// "civicoVersante",
			soggettoVersante.setCivicoVersante(rs.getString("de_e_sogg_vers_civico_versante_e"));

			// "capVersante",
			soggettoVersante.setCapVersante(rs.getString("cod_e_sogg_vers_cap_versante_e"));

			// "localitaVersante",
			soggettoVersante.setLocalitaVersante(rs.getString("de_e_sogg_vers_localita_versante_e"));

			// "provinciaVersante",
			soggettoVersante.setProvinciaVersante(rs.getString("de_e_sogg_vers_provincia_versante_e"));

			// "nazioneVersante",
			soggettoVersante.setNazioneVersante(rs.getString("cod_e_sogg_vers_nazione_versante_e"));

			// "eMailVersante"
			soggettoVersante.setEMailVersante(rs.getString("de_e_sogg_vers_email_versante_e"));

			// })
			return soggettoVersante;
		}

		private CtSoggettoPagatore extractCtSoggettoPagatore(ResultSet rs) throws SQLException {

			CtSoggettoPagatore soggettoPagatore = new CtSoggettoPagatore();
			// @XmlType(name = "ctSoggettoPagatore", propOrder = {
			// "identificativoUnivocoPagatore",
			CtIdentificativoUnivocoPersonaFG identificativoUnivocoPagatore = new CtIdentificativoUnivocoPersonaFG();
			// @XmlType(name = "ctIdentificativoUnivocoPersonaFG", propOrder = {
			// "tipoIdentificativoUnivoco",
			// "codiceIdentificativoUnivoco"
			// })
			identificativoUnivocoPagatore.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersFG
					.valueOf(rs.getString("cod_e_sogg_pag_id_univ_pag_tipo_id_univoco_e")));
			identificativoUnivocoPagatore
					.setCodiceIdentificativoUnivoco(rs.getString("cod_e_sogg_pag_id_univ_pag_codice_id_univoco_e"));
			soggettoPagatore.setIdentificativoUnivocoPagatore(identificativoUnivocoPagatore);

			// "anagraficaPagatore",
			soggettoPagatore.setAnagraficaPagatore(rs.getString("cod_e_sogg_pag_anagrafica_pagatore_e"));

			// "indirizzoPagatore",
			soggettoPagatore.setIndirizzoPagatore(rs.getString("de_e_sogg_pag_indirizzo_pagatore_e"));

			// "civicoPagatore",
			soggettoPagatore.setCivicoPagatore(rs.getString("de_e_sogg_pag_civico_pagatore_e"));

			// "capPagatore",
			soggettoPagatore.setCapPagatore(rs.getString("cod_e_sogg_pag_cap_pagatore_e"));

			// "localitaPagatore",
			soggettoPagatore.setLocalitaPagatore(rs.getString("de_e_sogg_pag_localita_pagatore_e"));

			// "provinciaPagatore",
			soggettoPagatore.setProvinciaPagatore(rs.getString("de_e_sogg_pag_provincia_pagatore_e"));

			// "nazionePagatore",
			soggettoPagatore.setNazionePagatore(rs.getString("cod_e_sogg_pag_nazione_pagatore_e"));

			// "eMailPagatore"
			soggettoPagatore.setEMailPagatore(rs.getString("de_e_sogg_pag_email_pagatore_e"));

			// })
			return soggettoPagatore;
		}

		private CtDatiVersamentoExport extractCtDatiVersamentoExport(ResultSet rs) throws SQLException {
			CtDatiVersamentoExport ctDatiVersamentoExport = new CtDatiVersamentoExport();
			// @XmlType(name = "ctDatiVersamentoEsito", propOrder = {
			// "codiceEsitoPagamento",
			ctDatiVersamentoExport.setCodiceEsitoPagamento(rs.getString("cod_e_dati_pag_codice_esito_pagamento_e"));

			// "importoTotalePagato",
			ctDatiVersamentoExport.setImportoTotalePagato(rs.getBigDecimal("num_e_dati_pag_importo_totale_pagato_e"));

			// "identificativoUnivocoVersamento",
			ctDatiVersamentoExport
					.setIdentificativoUnivocoVersamento(rs.getString("cod_e_dati_pag_id_univoco_versamento_e"));

			// "codiceContestoPagamento",
			ctDatiVersamentoExport
					.setCodiceContestoPagamento(rs.getString("cod_e_dati_pag_codice_contesto_pagamento_e"));

			// "datiSingoloPagamento"
			ctDatiVersamentoExport.setDatiSingoloPagamento(extractCtDatiSingoloPagamentoEsito(rs));

			// })
			return ctDatiVersamentoExport;
		}

		private CtDatiSingoloPagamentoEsito extractCtDatiSingoloPagamentoEsito(ResultSet rs) throws SQLException {

			CtDatiSingoloPagamentoEsito ctDatiSingoloPagamentoEsito = new CtDatiSingoloPagamentoEsito();
			// @XmlType(name = "ctDatiSingoloPagamentoEsito", propOrder = {
			// "singoloImportoPagato",
			ctDatiSingoloPagamentoEsito
					.setSingoloImportoPagato(rs.getBigDecimal("num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e"));

			// "esitoSingoloPagamento",
			ctDatiSingoloPagamentoEsito
					.setEsitoSingoloPagamento(rs.getString("de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento_e"));

			// "dataEsitoSingoloPagamento",
			ctDatiSingoloPagamentoEsito.setDataEsitoSingoloPagamento(Utilities
					.toXMLGregorianCalendar(rs.getDate("dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e")));

			// "identificativoUnivocoRiscossione",
			ctDatiSingoloPagamentoEsito.setIdentificativoUnivocoRiscossione(
					rs.getString("cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss_e"));

			// "causaleVersamento",
			ctDatiSingoloPagamentoEsito
					.setCausaleVersamento(rs.getString("de_e_dati_pag_dati_sing_pag_causale_versamento_e"));

			// "datiSpecificiRiscossione"
			ctDatiSingoloPagamentoEsito.setDatiSpecificiRiscossione(
					rs.getString("de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione_e"));

			// })
			return ctDatiSingoloPagamentoEsito;
		}

		private CtFlussoRiversamento extractCtFlussoRiversamento(ResultSet rs) throws SQLException {
			// CtFlussoRiversamento
			CtFlussoRiversamento ctFlussoRiversamento = new CtFlussoRiversamento();

			// "versioneOggetto",
			ctFlussoRiversamento.setVersioneOggetto(rs.getString("versione_oggetto_r"));

			// "identificativoFlusso",
			ctFlussoRiversamento.setIdentificativoFlusso(rs.getString("cod_identificativo_flusso_r"));

			try {
				// "dataOraFlusso",
				ctFlussoRiversamento
						.setDataOraFlusso(Utilities.toXMLGregorianCalendar(rs.getDate("dt_data_ora_flusso_r")));
			} catch (Exception e) {
			}

			// "identificativoUnivocoRegolamento",
			ctFlussoRiversamento
					.setIdentificativoUnivocoRegolamento(rs.getString("cod_identificativo_univoco_regolamento_r"));

			try {
				// "dataRegolamento",
				ctFlussoRiversamento
						.setDataRegolamento(Utilities.toXMLGregorianCalendar(rs.getDate("dt_data_regolamento_r")));
			} catch (Exception e) {
			}

			try {
				// "istitutoMittente",
				ctFlussoRiversamento.setIstitutoMittente(extractCtIstitutoMittente(rs));
			} catch (Exception e) {
			}

			try {
				// "istitutoRicevente",
				ctFlussoRiversamento.setIstitutoRicevente(extractCtIstitutoRicevente(rs));
			} catch (Exception e) {
			}

			// "numeroTotalePagamenti",
			ctFlussoRiversamento.setNumeroTotalePagamenti(rs.getBigDecimal("num_numero_totale_pagamenti_r"));

			// "importoTotalePagamenti",
			ctFlussoRiversamento.setImportoTotalePagamenti(rs.getBigDecimal("num_importo_totale_pagamenti_r"));

			try {
				// "datiSingoliPagamenti"
				ctFlussoRiversamento.setDatiSingoliPagamenti(extractCtDatiSingoliPagamenti(rs));
			} catch (Exception e) {
			}

			return ctFlussoRiversamento;
		}

		private CtIstitutoMittente extractCtIstitutoMittente(ResultSet rs) throws SQLException {

			CtIstitutoMittente ctIstitutoMittente = new CtIstitutoMittente();
			it.gov.digitpa.schemas._2011.pagamenti.CtIdentificativoUnivoco ctIdentificativoUnivoco = new it.gov.digitpa.schemas._2011.pagamenti.CtIdentificativoUnivoco();
			ctIdentificativoUnivoco
					.setTipoIdentificativoUnivoco(it.gov.digitpa.schemas._2011.pagamenti.StTipoIdentificativoUnivoco
							.fromValue(rs.getString("cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco_r")));
			ctIdentificativoUnivoco.setCodiceIdentificativoUnivoco(
					rs.getString("cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco_r"));
			ctIstitutoMittente.setIdentificativoUnivocoMittente(ctIdentificativoUnivoco);
			ctIstitutoMittente.setDenominazioneMittente(rs.getString("de_ist_mitt_denominazione_mittente_r"));
			return ctIstitutoMittente;
		}

		private CtIstitutoRicevente extractCtIstitutoRicevente(ResultSet rs) throws SQLException {

			CtIstitutoRicevente ctIstitutoRicevente = new CtIstitutoRicevente();
			it.gov.digitpa.schemas._2011.pagamenti.CtIdentificativoUnivocoPersonaG ctIdentificativoUnivocoPersonaG = new it.gov.digitpa.schemas._2011.pagamenti.CtIdentificativoUnivocoPersonaG();
			ctIdentificativoUnivocoPersonaG.setTipoIdentificativoUnivoco(
					it.gov.digitpa.schemas._2011.pagamenti.StTipoIdentificativoUnivocoPersG
							.fromValue(rs.getString("cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco_r")));
			ctIdentificativoUnivocoPersonaG.setCodiceIdentificativoUnivoco(
					rs.getString("cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco_r"));
			ctIstitutoRicevente.setIdentificativoUnivocoRicevente(ctIdentificativoUnivocoPersonaG);
			ctIstitutoRicevente.setDenominazioneRicevente(rs.getString("de_ist_ricev_denominazione_ricevente_r"));
			return ctIstitutoRicevente;
		}

		private CtDatiSingoliPagamenti extractCtDatiSingoliPagamenti(ResultSet rs) throws SQLException {

			CtDatiSingoliPagamenti ctDatiSingoliPagamenti = new CtDatiSingoliPagamenti();
			// @XmlType(name = "ctDatiSingoliPagamenti", propOrder = {
			// "identificativoUnivocoVersamento",
			ctDatiSingoliPagamenti.setIdentificativoUnivocoVersamento(
					rs.getString("cod_dati_sing_pagam_identificativo_univoco_versamento_r"));

			// "identificativoUnivocoRiscossione",
			ctDatiSingoliPagamenti.setIdentificativoUnivocoRiscossione(
					rs.getString("cod_dati_sing_pagam_identificativo_univoco_riscossione_r"));

			// "singoloImportoPagato",
			ctDatiSingoliPagamenti
					.setSingoloImportoPagato(rs.getBigDecimal("num_dati_sing_pagam_singolo_importo_pagato_r"));

			// "codiceEsitoSingoloPagamento",
			ctDatiSingoliPagamenti.setCodiceEsitoSingoloPagamento(
					rs.getString("cod_dati_sing_pagam_codice_esito_singolo_pagamento_r"));

			// "dataEsitoSingoloPagamento"
			ctDatiSingoliPagamenti.setDataEsitoSingoloPagamento(
					Utilities.toXMLGregorianCalendar(rs.getDate("dt_dati_sing_pagam_data_esito_singolo_pagamento_r")));
			// })

			// TODO prossimamente da valorizzare con relativo campo
			// ctDatiSingoliPagamenti.setIndiceDatiSingoloPagamento(value);

			return ctDatiSingoliPagamenti;
		}

	}

	@Override
	public PivotSILAutorizzaImportFlussoRisposta pivotSILAutorizzaImportFlusso(
			PivotSILAutorizzaImportFlusso bodyrichiesta, IntestazionePPT header) {
		PivotSILAutorizzaImportFlussoRisposta pivotSILAutorizzaImportFlussoRisposta = new PivotSILAutorizzaImportFlussoRisposta();

		String codIpaEnte = header.getCodIpaEnte();
		EnteTO enteTo = enteService.getByCodIpaEnte(codIpaEnte);
		if (enteTo == null) {
			LOG.error("pivotSILAutorizzaImportFlusso: Ente non valido: " + codIpaEnte);

			FaultBean faultBean = this.getFaultBean(codIpaEnte, CODE_PIVOT_ENTE_NON_VALIDO,
					"codice IPA Ente [" + codIpaEnte + "] non valido o password errata", null);

			pivotSILAutorizzaImportFlussoRisposta.setFault(faultBean);
			return pivotSILAutorizzaImportFlussoRisposta;
		}

		Boolean passwordValidaPerEnte = enteService.verificaPassword(enteTo.getCodIpaEnte(),
				bodyrichiesta.getPassword());
		if (!passwordValidaPerEnte) {
			LOG.error("pivotSILAutorizzaImportFlusso: Password non valida per ente: " + header.getCodIpaEnte());

			FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(), CODE_PIVOT_ENTE_NON_VALIDO,
					"Password non valida per ente [" + header.getCodIpaEnte() + "]", null);

			pivotSILAutorizzaImportFlussoRisposta.setFault(faultBean);
			return pivotSILAutorizzaImportFlussoRisposta;
		}

		MyBoxAuthorizeRisposta myBoxAuthorizeRisposta = requestAuthToken(enteTo);
		IntestazioneRisposta intestazioneRisposta = myBoxAuthorizeRisposta.getIntestazioneRisposta();
		List<Errore> errori = intestazioneRisposta.getErrori();
		if (!errori.isEmpty()) {

			LOG.error("pivotSILAutorizzaImportFlusso error myBox authorization: [" + errori.get(0).getCodice() + "]"
					+ errori.get(0).getDescrizione());

			throw new RuntimeException("pivotSILAutorizzaImportFlusso error myBox authorization: ["
					+ errori.get(0).getCodice() + "]" + errori.get(0).getDescrizione());
		}

		String authorizationToken = myBoxAuthorizeRisposta.getTokenRisposta();
		String requestToken = UUID.randomUUID().toString();

		// concordare utilizzo tabella
		ManageFlusso manageFlusso = manageFlussoService.insertFlusso(Constants.COD_TIPO_FLUSSO_DOVUTI, codIpaEnte,
				enteTo.getCodIpaEnte() + "-" + WS_USER, requestToken, Constants.DE_TIPO_STATO_MANAGE,
				Constants.COD_TIPO_STATO_MANAGE_FILE_SCARICATO);

		String myBoxContextURL = env.getProperty("myBox.contextURL");

		pivotSILAutorizzaImportFlussoRisposta.setAuthorizationToken(authorizationToken);
		pivotSILAutorizzaImportFlussoRisposta.setRequestToken(requestToken);

		String pathEnvProperty = "mybox.flussi." + Constants.COD_TIPO_FLUSSO_DOVUTI + ".uploadPath";

		pivotSILAutorizzaImportFlussoRisposta.setImportPath(
				env.getProperty(pathEnvProperty) + Constants.YYYY_MM.format(manageFlusso.getDtCreazione()));
		pivotSILAutorizzaImportFlussoRisposta.setUploadUrl(myBoxContextURL + "/rest/upload.html");

		return pivotSILAutorizzaImportFlussoRisposta;
	}

	@Override
	public PivotSILChiediStatoImportFlussoRisposta pivotSILChiediStatoImportFlusso(
			PivotSILChiediStatoImportFlusso bodyrichiesta, IntestazionePPT header) {
		PivotSILChiediStatoImportFlussoRisposta pivotSILChiediStatoImportFlussoRisposta = new PivotSILChiediStatoImportFlussoRisposta();

		String codIpaEnte = header.getCodIpaEnte();
		EnteTO enteTo = enteService.getByCodIpaEnte(codIpaEnte);
		if (enteTo == null) {
			LOG.error("pivotSILChiediStatoImportFlusso: Ente non valido: " + codIpaEnte);

			FaultBean faultBean = this.getFaultBean(codIpaEnte, CODE_PIVOT_ENTE_NON_VALIDO,
					"codice IPA Ente [" + codIpaEnte + "] non valido o password errata", null);

			pivotSILChiediStatoImportFlussoRisposta.setFault(faultBean);
			return pivotSILChiediStatoImportFlussoRisposta;
		}

		Boolean passwordValidaPerEnte = enteService.verificaPassword(enteTo.getCodIpaEnte(),
				bodyrichiesta.getPassword());
		if (!passwordValidaPerEnte) {
			LOG.error("pivotSILChiediStatoImportFlusso: Password non valida per ente: " + header.getCodIpaEnte());

			FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(), CODE_PIVOT_ENTE_NON_VALIDO,
					"Password non valida per ente [" + header.getCodIpaEnte() + "]", null);

			pivotSILChiediStatoImportFlussoRisposta.setFault(faultBean);
			return pivotSILChiediStatoImportFlussoRisposta;
		}

		ManageFlusso manageFlusso = manageFlussoService.getByRequestToken(bodyrichiesta.getRequestToken());

		if (manageFlusso == null || !codIpaEnte.equals(manageFlusso.getEnte().getCodIpaEnte())) {
			LOG.error("pivotSILChiediStatoImportFlusso: Ente [" + codIpaEnte + "] non autorizzato per requestToken ["
					+ bodyrichiesta.getRequestToken() + "]");

			FaultBean faultBean = this.getFaultBean(codIpaEnte, CODE_PIVOT_REQUEST_TOKEN_NON_VALIDO,
					"requestToken [" + bodyrichiesta.getRequestToken() + "] non valido per ente [" + codIpaEnte + "] ",
					null);

			pivotSILChiediStatoImportFlussoRisposta.setFault(faultBean);
			return pivotSILChiediStatoImportFlussoRisposta;
		}

		AnagraficaStato stato = manageFlusso.getAnagraficaStato();
		pivotSILChiediStatoImportFlussoRisposta.setStato(stato.getCodStato());

		return pivotSILChiediStatoImportFlussoRisposta;
	}

	@Override
	public PivotSILAutorizzaImportFlussoTesoreriaRisposta pivotSILAutorizzaImportFlussoTesoreria(
			PivotSILAutorizzaImportFlussoTesoreria bodyrichiesta, IntestazionePPT header) {
		PivotSILAutorizzaImportFlussoTesoreriaRisposta pivotSILAutorizzaImportFlussoTesoreriaRisposta = new PivotSILAutorizzaImportFlussoTesoreriaRisposta();

		String codIpaEnte = header.getCodIpaEnte();
		EnteTO enteTo = enteService.getByCodIpaEnte(codIpaEnte);
		if (enteTo == null) {
			LOG.error("pivotSILAutorizzaImportFlussoTesoreria: Ente non valido: " + codIpaEnte);

			FaultBean faultBean = this.getFaultBean(codIpaEnte, CODE_PIVOT_ENTE_NON_VALIDO,
					"codice IPA Ente [" + codIpaEnte + "] non valido o password errata", null);

			pivotSILAutorizzaImportFlussoTesoreriaRisposta.setFault(faultBean);
			return pivotSILAutorizzaImportFlussoTesoreriaRisposta;
		}

		Boolean passwordValidaPerEnte = enteService.verificaPassword(enteTo.getCodIpaEnte(),
				bodyrichiesta.getPassword());
		if (!passwordValidaPerEnte) {
			LOG.error(
					"pivotSILAutorizzaImportFlussoTesoreria: Password non valida per ente: " + header.getCodIpaEnte());

			FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(), CODE_PIVOT_ENTE_NON_VALIDO,
					"Password non valida per ente [" + header.getCodIpaEnte() + "]", null);

			pivotSILAutorizzaImportFlussoTesoreriaRisposta.setFault(faultBean);
			return pivotSILAutorizzaImportFlussoTesoreriaRisposta;
		}
		
		Character tipoFlusso = Constants.COD_TIPO_FLUSSO_TESORERIA;
		String tipoFlussoString = bodyrichiesta.getTipoFlusso();
		if (StringUtils.isNotBlank(tipoFlussoString)) {
			if (tipoFlussoString.length() != 1 || ( 
					!Constants.COD_TIPO_FLUSSO_TESORERIA.equals(tipoFlussoString.charAt(0))
					&& !Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA.equals(tipoFlussoString.charAt(0))
					&& !Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA_OPI.equals(tipoFlussoString.charAt(0)))) {
				LOG.error("pivotSILAutorizzaImportFlussoTesoreria: Tipo Flusso Tesoreria [" + tipoFlussoString + "] non valido");

				FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(), CODE_PIVOT_TIPO_FLUSSO_NON_VALIDO,
						"Tipo Flusso Tesoreria [" + tipoFlussoString + "] non valido", null);

				pivotSILAutorizzaImportFlussoTesoreriaRisposta.setFault(faultBean);
				return pivotSILAutorizzaImportFlussoTesoreriaRisposta;
			}
			else {
				tipoFlusso = tipoFlussoString.charAt(0);
			}
		}

		MyBoxAuthorizeRisposta myBoxAuthorizeRisposta = requestAuthToken(enteTo);
		IntestazioneRisposta intestazioneRisposta = myBoxAuthorizeRisposta.getIntestazioneRisposta();
		List<Errore> errori = intestazioneRisposta.getErrori();
		if (!errori.isEmpty()) {

			LOG.error("pivotSILAutorizzaImportFlussoTesoreria error myBox authorization: [" + errori.get(0).getCodice()
					+ "]" + errori.get(0).getDescrizione());

			throw new RuntimeException("pivotSILAutorizzaImportFlussoTesoreria error myBox authorization: ["
					+ errori.get(0).getCodice() + "]" + errori.get(0).getDescrizione());
		}

		String authorizationToken = myBoxAuthorizeRisposta.getTokenRisposta();
		String requestToken = UUID.randomUUID().toString();

		// concordare utilizzo tabella
		ManageFlusso manageFlusso = manageFlussoService.insertFlusso(tipoFlusso, codIpaEnte,
				enteTo.getCodIpaEnte() + "-" + WS_USER, requestToken, Constants.DE_TIPO_STATO_MANAGE,
				Constants.COD_TIPO_STATO_MANAGE_FILE_SCARICATO);

		String myBoxContextURL = env.getProperty("myBox.contextURL");

		pivotSILAutorizzaImportFlussoTesoreriaRisposta.setAuthorizationToken(authorizationToken);
		pivotSILAutorizzaImportFlussoTesoreriaRisposta.setRequestToken(requestToken);

		String pathEnvProperty = "mybox.flussi." + tipoFlusso + ".uploadPath";

		pivotSILAutorizzaImportFlussoTesoreriaRisposta.setImportPath(
				env.getProperty(pathEnvProperty) + Constants.YYYY_MM.format(manageFlusso.getDtCreazione()));
		pivotSILAutorizzaImportFlussoTesoreriaRisposta.setUploadUrl(myBoxContextURL + "/rest/upload.html");

		return pivotSILAutorizzaImportFlussoTesoreriaRisposta;
	}

	@Override
	public PivotSILChiediStatoImportFlussoTesoreriaRisposta pivotSILChiediStatoImportFlussoTesoreria(
			PivotSILChiediStatoImportFlussoTesoreria bodyrichiesta, IntestazionePPT header) {
		PivotSILChiediStatoImportFlussoTesoreriaRisposta pivotSILChiediStatoImportFlussoTesoreriaRisposta = new PivotSILChiediStatoImportFlussoTesoreriaRisposta();

		String codIpaEnte = header.getCodIpaEnte();
		EnteTO enteTo = enteService.getByCodIpaEnte(codIpaEnte);
		if (enteTo == null) {
			LOG.error("pivotSILChiediStatoImportFlussoTesoreria: Ente non valido: " + codIpaEnte);

			FaultBean faultBean = this.getFaultBean(codIpaEnte, CODE_PIVOT_ENTE_NON_VALIDO,
					"codice IPA Ente [" + codIpaEnte + "] non valido o password errata", null);

			pivotSILChiediStatoImportFlussoTesoreriaRisposta.setFault(faultBean);
			return pivotSILChiediStatoImportFlussoTesoreriaRisposta;
		}

		Boolean passwordValidaPerEnte = enteService.verificaPassword(enteTo.getCodIpaEnte(),
				bodyrichiesta.getPassword());
		if (!passwordValidaPerEnte) {
			LOG.error("pivotSILChiediStatoImportFlussoTesoreria: Password non valida per ente: "
					+ header.getCodIpaEnte());

			FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(), CODE_PIVOT_ENTE_NON_VALIDO,
					"Password non valida per ente [" + header.getCodIpaEnte() + "]", null);

			pivotSILChiediStatoImportFlussoTesoreriaRisposta.setFault(faultBean);
			return pivotSILChiediStatoImportFlussoTesoreriaRisposta;
		}

		ManageFlusso manageFlusso = manageFlussoService.getByRequestToken(bodyrichiesta.getRequestToken());

		if (manageFlusso == null || !codIpaEnte.equals(manageFlusso.getEnte().getCodIpaEnte())) {
			LOG.error("pivotSILChiediStatoImportFlussoTesoreria: Ente [" + codIpaEnte
					+ "] non autorizzato per requestToken [" + bodyrichiesta.getRequestToken() + "]");

			FaultBean faultBean = this.getFaultBean(codIpaEnte, CODE_PIVOT_REQUEST_TOKEN_NON_VALIDO,
					"requestToken [" + bodyrichiesta.getRequestToken() + "] non valido per ente [" + codIpaEnte + "] ",
					null);

			pivotSILChiediStatoImportFlussoTesoreriaRisposta.setFault(faultBean);
			return pivotSILChiediStatoImportFlussoTesoreriaRisposta;
		}

		AnagraficaStato stato = manageFlusso.getAnagraficaStato();
		pivotSILChiediStatoImportFlussoTesoreriaRisposta.setStato(stato.getCodStato());

		return pivotSILChiediStatoImportFlussoTesoreriaRisposta;
	}

	private MyBoxAuthorizeRisposta requestAuthToken(EnteTO ente) {
		MyBoxAuthorize myboxAuthorize = new MyBoxAuthorize();
		myboxAuthorize.setClientKey(ente.getMyboxClientKey());
		myboxAuthorize.setClientSecret(ente.getMyboxClientSecret());
		MyBoxAuthorizeRisposta myboxAuthorizeRisposta = myBoxClient.myBoxAuthorize(myboxAuthorize);
		return myboxAuthorizeRisposta;
	}

	@Override
	public PivotSILPrenotaExportFlussoRiconciliazioneRisposta pivotSILPrenotaExportFlussoRiconciliazione(
			PivotSILPrenotaExportFlussoRiconciliazione bodyrichiesta, IntestazionePPT header) {

		PivotSILPrenotaExportFlussoRiconciliazioneRisposta pivotSILPrenotaExportFlussoRiconciliazioneRisposta = new PivotSILPrenotaExportFlussoRiconciliazioneRisposta();

		String codIpaEnte = header.getCodIpaEnte();
		EnteTO ente = enteService.getByCodIpaEnte(codIpaEnte);
		if (ente == null) {
			LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: Ente non valido: " + codIpaEnte);

			FaultBean faultBean = this.getFaultBean(codIpaEnte, FaultConstants.PIVOT_ENTE_NON_VALIDO,
					"codice IPA Ente [" + codIpaEnte + "] non valido", null);

			pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(faultBean);
			return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
		}

		Boolean passwordValidaPerEnte = enteService.verificaPassword(ente.getCodIpaEnte(), bodyrichiesta.getPassword());
		if (!passwordValidaPerEnte) {
			LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: Password non valida per ente: "
					+ header.getCodIpaEnte());

			FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(), FaultConstants.PIVOT_ENTE_NON_VALIDO,
					"Password non valida per ente [" + header.getCodIpaEnte() + "]", null);

			pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(faultBean);
			return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
		}

		/////////////////////////////////
		// Versione tracciato
		////////////////////////////////
		String versioneTracciato = bodyrichiesta.getVersioneTracciato();
		if (!Utilities.validaVersioneTracciatoExport(versioneTracciato)) {
			LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: Versione tracciato [ " + versioneTracciato
					+ " ] non valida");

			FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(),
					FaultConstants.PIVOT_VERSIONE_TRACCIATO_EXPORT_NON_VALIDA,
					"Versione tracciato [ " + versioneTracciato + " ] non valida", null);

			pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(faultBean);
			return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
		}

		//////////////////////////
		// Classificazioni
		/////////////////////////
		CodiceClassificazioneType ccType = bodyrichiesta.getCodiceClassificazione();
		String finalCC = "";
		if (ccType != null) {
			List<String> listaCodiciClassificazioni = ccType.getClassificazione();
			if (listaCodiciClassificazioni != null && CollectionUtils.isNotEmpty(listaCodiciClassificazioni)) {
				for (String cc : listaCodiciClassificazioni) {
					boolean valida = Utilities.checkValiditaClassificazione(cc);
					if (!valida) {
						LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: Classificazione [ " + cc
								+ " ] non valida");

						FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(),
								FaultConstants.PIVOT_CLASSIFICAZIONE_NON_VALIDA,
								"Classificazione [ " + cc + " ] non valida", null);

						pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(faultBean);
						return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
					}
					boolean abilitata = Utilities.checkAbilitazioneClassificazionePerEnte(cc, ente.getFlgPagati(),
							ente.getFlgTesoreria());
					if (!abilitata) {
						LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: Classificazione [ " + cc
								+ " ] non abilitata per ente: " + header.getCodIpaEnte());

						FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(),
								FaultConstants.PIVOT_CLASSIFICAZIONE_NON_ABILITATA, "Classificazione [ " + cc
										+ " ] non abilitata per ente [" + header.getCodIpaEnte() + "]",
								null);

						pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(faultBean);
						return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
					}
					finalCC += cc + "|";
				}
				if (finalCC.endsWith("|"))
					finalCC = finalCC.substring(0, finalCC.length() - 1);
			} else {
				LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: Nessuna classificazione inserita");

				FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(),
						FaultConstants.PIVOT_CLASSIFICAZIONE_NON_VALIDA, "Nessuna classificazione inserita", null);

				pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(faultBean);
				return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
			}
		} else {
			LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: Nessuna classificazione inserita");

			FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(),
					FaultConstants.PIVOT_CLASSIFICAZIONE_NON_VALIDA, "Nessuna classificazione inserita", null);

			pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(faultBean);
			return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
		}

		//////////////////////////
		// Tipi dovuto
		/////////////////////////
		TipoDovutoType tdType = bodyrichiesta.getTipoDovuto();
		String finalTipoDovuto = null;
		if (tdType != null) {
			List<String> listaTipiDovuto = tdType.getTipo();
			if (listaTipiDovuto != null && CollectionUtils.isNotEmpty(listaTipiDovuto)) {
				finalTipoDovuto = "";
				List<EnteTipoDovuto> listaEnteTipoDovuto = enteTipoDovutoService.getByCodIpaEnte(ente.getCodIpaEnte());
				for (String tipoDovuto : listaTipiDovuto) {
					boolean tipoDovutoValido = tipoDovutoEsistePerEnte(listaEnteTipoDovuto, tipoDovuto);
					if (!tipoDovutoValido) {
						LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: Tipo dovuto [ " + tipoDovuto
								+ " ] non valido per ente: " + header.getCodIpaEnte());

						FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(),
								FaultConstants.PIVOT_CLASSIFICAZIONE_NON_ABILITATA, "Tipo dovuto [ " + tipoDovuto
										+ " ] non valido per ente [" + header.getCodIpaEnte() + "]",
								null);

						pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(faultBean);
						return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
					}
					finalTipoDovuto += tipoDovuto + "|";
				}
				if (finalTipoDovuto.endsWith("|"))
					finalTipoDovuto = finalTipoDovuto.substring(0, finalTipoDovuto.length() - 1);
			}
		}

		//////////////////////////
		// IUV
		/////////////////////////
		IdUnivocoVersamentoType iuvType = bodyrichiesta.getIdUnivocoVersamento();
		String finalIuv = null;
		if (iuvType != null) {
			List<String> listaIuv = iuvType.getIuv();
			if (listaIuv != null && CollectionUtils.isNotEmpty(listaIuv)) {
				finalIuv = "";
				for (String iuv : listaIuv) {
					finalIuv += iuv + "|";
				}
				if (finalIuv.endsWith("|"))
					finalIuv = finalIuv.substring(0, finalIuv.length() - 1);
			}
		}

		//////////////////////////
		// IUF
		/////////////////////////
		IdUnivocoRendicontazioneType iufType = bodyrichiesta.getIdUnivocoRendicontazione();
		String finalIuf = null;
		if (iufType != null) {
			List<String> listaIuf = iufType.getIur();
			if (listaIuf != null && CollectionUtils.isNotEmpty(listaIuf)) {
				finalIuf = "";
				for (String iuf : listaIuf) {
					finalIuf += iuf + "|";
				}
				if (finalIuf.endsWith("|"))
					finalIuf = finalIuf.substring(0, finalIuf.length() - 1);
			}
		}

		/////////////////////////////////
		// Data ultimo aggiornamento
		////////////////////////////////
		XMLGregorianCalendar dataUltimoAggiornamentoDa = bodyrichiesta.getDataUltimoAggiornamentoDa();
		XMLGregorianCalendar dataUltimoAggiornamentoA = bodyrichiesta.getDataUltimoAggiornamentoA();

		Date dtUltimoAggiornamentoDa = Utilities.toDate(dataUltimoAggiornamentoDa);
		Date dtUltimoAggiornamentoA = Utilities.toDate(dataUltimoAggiornamentoA);

		FaultBean dtUltimoAggiornamentoFaultBean = checkDataUltimoAggiornamento(ente.getCodIpaEnte(),
				dtUltimoAggiornamentoDa, dtUltimoAggiornamentoA);
		if (dtUltimoAggiornamentoFaultBean != null) {
			LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: " + dtUltimoAggiornamentoFaultBean.getFaultString());

			pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(dtUltimoAggiornamentoFaultBean);
			return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
		}

		/////////////////////////////////
		// Data esecuzione
		////////////////////////////////
		XMLGregorianCalendar dataEsecuzioneDa = bodyrichiesta.getDataEsecuzioneDa();
		XMLGregorianCalendar dataEsecuzioneA = bodyrichiesta.getDataEsecuzioneA();

		Date dtEsecuzioneDa = Utilities.toDate(dataEsecuzioneDa);
		Date dtEsecuzioneA = Utilities.toDate(dataEsecuzioneA);

		FaultBean dtEsecuzioneFaultBean = checkDate(ente.getCodIpaEnte(), dtEsecuzioneDa, dtEsecuzioneA);
		if (dtEsecuzioneFaultBean != null) {
			if (dtEsecuzioneFaultBean.getFaultCode().equals(FaultConstants.PIVOT_DATE_FROM_NON_VALIDO)) {
				dtEsecuzioneFaultBean.setFaultString("Data esecuzione DA non valida");
			} else if (dtEsecuzioneFaultBean.getFaultCode().equals(FaultConstants.PIVOT_DATE_TO_NON_VALIDO)) {
				dtEsecuzioneFaultBean.setFaultString("Data esecuzione A non valida");
			} else if (dtEsecuzioneFaultBean.getFaultCode().equals(FaultConstants.PIVOT_INTERVALLO_DATE_NON_VALIDO)) {
				dtEsecuzioneFaultBean.setFaultString("Intervallo date esecuzione non valido");
			}
			LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: " + dtEsecuzioneFaultBean.getFaultString());

			pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(dtEsecuzioneFaultBean);
			return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
		}

		/////////////////////////////////
		// Data esito
		////////////////////////////////
		XMLGregorianCalendar dataEsitoDa = bodyrichiesta.getDataEsitoDa();
		XMLGregorianCalendar dataEsitoA = bodyrichiesta.getDataEsitoA();

		Date dtEsitoDa = Utilities.toDate(dataEsitoDa);
		Date dtEsitoA = Utilities.toDate(dataEsitoA);

		FaultBean dtEsitoFaultBean = checkDate(ente.getCodIpaEnte(), dtEsitoDa, dtEsitoA);
		if (dtEsitoFaultBean != null) {
			if (dtEsitoFaultBean.getFaultCode().equals(FaultConstants.PIVOT_DATE_FROM_NON_VALIDO)) {
				dtEsitoFaultBean.setFaultString("Data esito DA non valida");
			} else if (dtEsitoFaultBean.getFaultCode().equals(FaultConstants.PIVOT_DATE_TO_NON_VALIDO)) {
				dtEsitoFaultBean.setFaultString("Data esito A non valida");
			} else if (dtEsitoFaultBean.getFaultCode().equals(FaultConstants.PIVOT_INTERVALLO_DATE_NON_VALIDO)) {
				dtEsitoFaultBean.setFaultString("Intervallo date esito non valido");
			}
			LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: " + dtEsitoFaultBean.getFaultString());

			pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(dtEsitoFaultBean);
			return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
		}

		/////////////////////////////////
		// Data regolamento
		////////////////////////////////
		XMLGregorianCalendar dataRegolamentoDa = bodyrichiesta.getDataRegolamentoDa();
		XMLGregorianCalendar dataRegolamentoA = bodyrichiesta.getDataRegolamentoA();

		Date dtRegolamentoDa = Utilities.toDate(dataRegolamentoDa);
		Date dtRegolamentoA = Utilities.toDate(dataRegolamentoA);

		FaultBean dtRegolamentoFaultBean = checkDate(ente.getCodIpaEnte(), dtRegolamentoDa, dtRegolamentoA);
		if (dtRegolamentoFaultBean != null) {
			if (dtRegolamentoFaultBean.getFaultCode().equals(FaultConstants.PIVOT_DATE_FROM_NON_VALIDO)) {
				dtRegolamentoFaultBean.setFaultString("Data regolamento DA non valida");
			} else if (dtRegolamentoFaultBean.getFaultCode().equals(FaultConstants.PIVOT_DATE_TO_NON_VALIDO)) {
				dtRegolamentoFaultBean.setFaultString("Data regolamento A non valida");
			} else if (dtRegolamentoFaultBean.getFaultCode().equals(FaultConstants.PIVOT_INTERVALLO_DATE_NON_VALIDO)) {
				dtRegolamentoFaultBean.setFaultString("Intervallo date regolamento non valido");
			}
			LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: " + dtRegolamentoFaultBean.getFaultString());

			pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(dtRegolamentoFaultBean);
			return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
		}

		/////////////////////////////////
		// Data contabile
		////////////////////////////////
		XMLGregorianCalendar dataContabileDa = bodyrichiesta.getDataContabileDa();
		XMLGregorianCalendar dataContabileA = bodyrichiesta.getDataContabileA();

		Date dtContabileDa = Utilities.toDate(dataContabileDa);
		Date dtContabileA = Utilities.toDate(dataContabileA);

		FaultBean dtContabileFaultBean = checkDate(ente.getCodIpaEnte(), dtContabileDa, dtContabileA);
		if (dtContabileFaultBean != null) {
			if (dtContabileFaultBean.getFaultCode().equals(FaultConstants.PIVOT_DATE_FROM_NON_VALIDO)) {
				dtContabileFaultBean.setFaultString("Data contabile DA non valida");
			} else if (dtContabileFaultBean.getFaultCode().equals(FaultConstants.PIVOT_DATE_TO_NON_VALIDO)) {
				dtContabileFaultBean.setFaultString("Data contabile A non valida");
			} else if (dtContabileFaultBean.getFaultCode().equals(FaultConstants.PIVOT_INTERVALLO_DATE_NON_VALIDO)) {
				dtContabileFaultBean.setFaultString("Intervallo date contabili non valido");
			}
			LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: " + dtContabileFaultBean.getFaultString());

			pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(dtContabileFaultBean);
			return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
		}

		/////////////////////////////////
		// Data valuta
		////////////////////////////////
		XMLGregorianCalendar dataValutaDa = bodyrichiesta.getDataValutaDa();
		XMLGregorianCalendar dataValutaA = bodyrichiesta.getDataValutaA();

		Date dtValutaDa = Utilities.toDate(dataValutaDa);
		Date dtValutaA = Utilities.toDate(dataValutaA);

		FaultBean dtValutaFaultBean = checkDate(ente.getCodIpaEnte(), dtValutaDa, dtValutaA);
		if (dtValutaFaultBean != null) {
			if (dtValutaFaultBean.getFaultCode().equals(FaultConstants.PIVOT_DATE_FROM_NON_VALIDO)) {
				dtValutaFaultBean.setFaultString("Data valuta DA non valida");
			} else if (dtValutaFaultBean.getFaultCode().equals(FaultConstants.PIVOT_DATE_TO_NON_VALIDO)) {
				dtValutaFaultBean.setFaultString("Data valuta A non valida");
			} else if (dtValutaFaultBean.getFaultCode().equals(FaultConstants.PIVOT_INTERVALLO_DATE_NON_VALIDO)) {
				dtValutaFaultBean.setFaultString("Intervallo date valuta non valido");
			}
			LOG.error("pivotSILPrenotaExportFlussoRiconciliazione: " + dtValutaFaultBean.getFaultString());

			pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setFault(dtValutaFaultBean);
			return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
		}

		UtenteTO utente = utenteService.getUtenteWSByCodIpaEnte(codIpaEnte);

		PrenotazioneFlussoRiconciliazioneTO prenotazione = prenotazioneFlussoRiconciliazioneService
				.insertPrenotazioneFlussoRiconciliazione(codIpaEnte, utente.getCodFedUserId(),
						Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_PRENOTATO,
						Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE, finalCC, finalTipoDovuto, finalIuv,
						finalIuf, dtUltimoAggiornamentoDa, dtUltimoAggiornamentoA, dtEsecuzioneDa, dtEsecuzioneA,
						dtEsitoDa, dtEsitoA, dtRegolamentoDa, dtRegolamentoA, dtContabileDa, dtContabileA, dtValutaDa,
						dtValutaA, bodyrichiesta.getIdUnivocoDovuto(), bodyrichiesta.getIdUnivocoRiscossione(),
						bodyrichiesta.getIdUnivocoPagatore(), bodyrichiesta.getAnagraficaPagatore(),
						bodyrichiesta.getIdUnivocoVersante(), bodyrichiesta.getAnagraficaVersante(),
						bodyrichiesta.getDenominazioneAttestante(), bodyrichiesta.getOrdinante(),
						bodyrichiesta.getIdRegolamento(), bodyrichiesta.getContoTesoreria(),
						bodyrichiesta.getImportoTesoreria(), bodyrichiesta.getCausale(), versioneTracciato);

		pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setRequestToken(prenotazione.getCodRequestToken());
		if (prenotazione.getDtDataUltimoAggiornamentoDa() != null
				&& prenotazione.getDtDataUltimoAggiornamentoA() != null) {
			pivotSILPrenotaExportFlussoRiconciliazioneRisposta.setDataA(
					Utilities.toXMLGregorianCalendarWithoutTimezone(prenotazione.getDtDataUltimoAggiornamentoA()));
		}

		return pivotSILPrenotaExportFlussoRiconciliazioneRisposta;
	}

	@Override
	public PivotSILChiediStatoExportFlussoRiconciliazioneRisposta pivotSILChiediStatoExportFlussoRiconciliazione(
			PivotSILChiediStatoExportFlussoRiconciliazione bodyrichiesta, IntestazionePPT header) {

		PivotSILChiediStatoExportFlussoRiconciliazioneRisposta pivotSILChiediStatoExportFlussoRiconciliazioneRisposta = new PivotSILChiediStatoExportFlussoRiconciliazioneRisposta();

		String codIpaEnte = header.getCodIpaEnte();
		EnteTO ente = enteService.getByCodIpaEnte(codIpaEnte);
		if (ente == null) {
			LOG.error("pivotSILChiediStatoExportFlussoRiconciliazione: Ente non valido: " + codIpaEnte);

			FaultBean faultBean = this.getFaultBean(codIpaEnte, FaultConstants.PIVOT_ENTE_NON_VALIDO,
					"codice IPA Ente [" + codIpaEnte + "] non valido", null);

			pivotSILChiediStatoExportFlussoRiconciliazioneRisposta.setFault(faultBean);
			return pivotSILChiediStatoExportFlussoRiconciliazioneRisposta;
		}

		Boolean passwordValidaPerEnte = enteService.verificaPassword(ente.getCodIpaEnte(), bodyrichiesta.getPassword());
		if (!passwordValidaPerEnte) {
			LOG.error("pivotSILChiediStatoExportFlussoRiconciliazione: Password non valida per ente: "
					+ header.getCodIpaEnte());

			FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(), FaultConstants.PIVOT_ENTE_NON_VALIDO,
					"Password non valida per ente [" + header.getCodIpaEnte() + "]", null);

			pivotSILChiediStatoExportFlussoRiconciliazioneRisposta.setFault(faultBean);
			return pivotSILChiediStatoExportFlussoRiconciliazioneRisposta;
		}

		if (StringUtils.isBlank(bodyrichiesta.getRequestToken())) {
			LOG.error("pivotSILChiediStatoExportFlussoRiconciliazione: Request Token vuoto");

			FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(),
					FaultConstants.PIVOT_REQUEST_TOKEN_NON_VALIDO, "Request Token vuoto", null);

			pivotSILChiediStatoExportFlussoRiconciliazioneRisposta.setFault(faultBean);
			return pivotSILChiediStatoExportFlussoRiconciliazioneRisposta;
		}

		PrenotazioneFlussoRiconciliazioneTO prenotazione = prenotazioneFlussoRiconciliazioneService
				.getByCodRequestToken(bodyrichiesta.getRequestToken());
		if (prenotazione == null) {
			LOG.error("pivotSILChiediStatoExportFlussoRiconciliazione: Request Token non valido");

			FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(),
					FaultConstants.PIVOT_REQUEST_TOKEN_NON_VALIDO, "Request Token non valido", null);

			pivotSILChiediStatoExportFlussoRiconciliazioneRisposta.setFault(faultBean);
			return pivotSILChiediStatoExportFlussoRiconciliazioneRisposta;
		}

		if (prenotazione.getEnte() != null && !prenotazione.getEnte().getCodIpaEnte().equals(codIpaEnte)) {
			LOG.error("pivotSILChiediStatoExportFlussoRiconciliazione: Request Token non valido");

			FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(),
					FaultConstants.PIVOT_REQUEST_TOKEN_NON_VALIDO, "Request Token non valido", null);

			pivotSILChiediStatoExportFlussoRiconciliazioneRisposta.setFault(faultBean);
			return pivotSILChiediStatoExportFlussoRiconciliazioneRisposta;
		}

		if (prenotazione.getAnagraficaStato().getDeTipoStato()
				.equals(Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE)
				&& prenotazione.getAnagraficaStato().getCodStato()
						.equals(Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_EXPORT_ESEGUITO)) {

			// CHIAMATA A MYBOX
			MyBoxAuthorizeRisposta myBoxRisposta = requestAuthToken(ente);

			IntestazioneRisposta intestazioneRisposta = myBoxRisposta.getIntestazioneRisposta();
			List<Errore> errori = intestazioneRisposta.getErrori();
			if (!errori.isEmpty()) {
				LOG.error("pivotSILChiediStatoExportFlussoRiconciliazione error myBox authorization: ["
						+ errori.get(0).getCodice() + "]" + errori.get(0).getDescrizione());

				FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(), PIVOT_SYSTEM_ERROR,
						"Errore durante la chiamata a MyBox", null);

				pivotSILChiediStatoExportFlussoRiconciliazioneRisposta.setFault(faultBean);
				return pivotSILChiediStatoExportFlussoRiconciliazioneRisposta;

			}
			String myBoxContextURL = env.getProperty("myBox.contextURL");
			String authorizationToken = myBoxRisposta.getTokenRisposta();

			String downloadUrl = myBoxContextURL + "/rest/download.html?authorizationToken=" + authorizationToken
					+ "&fileName=" + prenotazione.getDeNomeFileGenerato();

			URL url;
			try {
				url = new URL(downloadUrl);
				downloadUrl = url.toURI().toASCIIString();
			} catch (MalformedURLException | URISyntaxException e) {
				LOG.error("pivotSILChiediStatoExportFlussoRiconciliazione: Error in building download URL :"
						+ downloadUrl);
				FaultBean faultBean = this.getFaultBean(header.getCodIpaEnte(), PIVOT_SYSTEM_ERROR,
						"Errore durante la creazione dell'url di download", null);

				pivotSILChiediStatoExportFlussoRiconciliazioneRisposta.setFault(faultBean);
				return pivotSILChiediStatoExportFlussoRiconciliazioneRisposta;

			}
			pivotSILChiediStatoExportFlussoRiconciliazioneRisposta.setDownloadUrl(downloadUrl);
		}

		pivotSILChiediStatoExportFlussoRiconciliazioneRisposta
				.setStato(prenotazione.getAnagraficaStato().getCodStato());

		return pivotSILChiediStatoExportFlussoRiconciliazioneRisposta;
	}

	private boolean tipoDovutoEsistePerEnte(List<EnteTipoDovuto> listaTipiDovutoPerEnte, String tipoDovuto) {
		for (EnteTipoDovuto etd : listaTipiDovutoPerEnte)
			if (etd.getCodTipo().equals(tipoDovuto))
				return true;
		return false;
	}

	private FaultBean checkDataUltimoAggiornamento(String codIpa, Date da, Date a) {
		if (da == null && a == null)
			return null;
		if (da == null && a != null) {
			return this.getFaultBean(codIpa, FaultConstants.PIVOT_DATE_FROM_NON_VALIDO,
					"Data Ultimo Aggiornamento DA non valorizzata", null);
		} else if (da != null && a == null) {
			return null;
		}
		if (da.after(a) || da.equals(a)) {
			return this.getFaultBean(codIpa, FaultConstants.PIVOT_INTERVALLO_DATE_NON_VALIDO,
					"Intervallo date ultimo aggiornamento non valido", null);
		}
		return null;
	}

	private FaultBean checkDate(String codIpa, Date da, Date a) {
		if (da == null && a == null)
			return null;
		if ((da != null && a == null) || (da == null && a != null)) {
			if (da == null)
				return this.getFaultBean(codIpa, FaultConstants.PIVOT_DATE_FROM_NON_VALIDO, null, null);
			else
				return this.getFaultBean(codIpa, FaultConstants.PIVOT_DATE_TO_NON_VALIDO, null, null);
		}
		if (da.after(a)) {
			return this.getFaultBean(codIpa, FaultConstants.PIVOT_INTERVALLO_DATE_NON_VALIDO, null, null);
		}
		return null;
	}

	@Override
	public PivotSILChiediAccertamentoRisposta pivotSILChiediAccertamento(PivotSILChiediAccertamento bodyrichiesta,
			IntestazionePPT header) {

		PivotSILChiediAccertamentoRisposta pivotSILChiediAccertamentoRisposta = new PivotSILChiediAccertamentoRisposta();

		String codIpaEnte = header.getCodIpaEnte();
		EnteTO ente = enteService.getByCodIpaEnte(codIpaEnte);
		if (ente == null) {
			LOG.error("pivotSILChiediAccertamento: Ente non valido [ " + codIpaEnte + " ]");

			FaultBean faultBean = this.getFaultBean(codIpaEnte, FaultConstants.PIVOT_ENTE_NON_VALIDO,
					"codice IPA Ente [ " + codIpaEnte + " ] non valido", null);

			pivotSILChiediAccertamentoRisposta.setFault(faultBean);
			return pivotSILChiediAccertamentoRisposta;
		}

		Boolean passwordValidaPerEnte = enteService.verificaPassword(ente.getCodIpaEnte(), bodyrichiesta.getPassword());
		if (!passwordValidaPerEnte) {
			LOG.error("pivotSILChiediAccertamento: Password non valida per ente [ " + codIpaEnte + " ]");

			FaultBean faultBean = this.getFaultBean(codIpaEnte, FaultConstants.PIVOT_ENTE_NON_VALIDO,
					"Password non valida per ente [ " + codIpaEnte + " ]", null);

			pivotSILChiediAccertamentoRisposta.setFault(faultBean);
			return pivotSILChiediAccertamentoRisposta;
		}

		RichiestaPerBolletta richiestaPerBolletta = bodyrichiesta.getRichiestaPerBolletta();
		RichiestaPerIUF richiestaPerIUF = bodyrichiesta.getRichiestaPerIUF();

		if (richiestaPerBolletta != null && richiestaPerIUF != null) {
			LOG.error(
					"pivotSILChiediAccertamento: Scegliere solo un metodo di richiesta per ente [" + codIpaEnte + " ]");

			FaultBean faultBean = this.getFaultBean(codIpaEnte, FaultConstants.PIVOT_RICHIESTA_CON_PARAMETRI_MULTIPLI,
					"Scegliere solo un metodo di richiesta per [ " + codIpaEnte + " ]", null);

			pivotSILChiediAccertamentoRisposta.setFault(faultBean);
			return pivotSILChiediAccertamentoRisposta;
		}

		if (richiestaPerBolletta != null) {
			String annoBolletta = richiestaPerBolletta.getAnnoBolletta();
			String codiceBolletta = richiestaPerBolletta.getNumeroBolletta();

			if (StringUtils.isBlank(annoBolletta)) {
				LOG.error("pivotSILChiediAccertamento: Il campo \"Anno bolletta\" è obbligatorio");

				FaultBean faultBean = this.getFaultBean(codIpaEnte, FaultConstants.PIVOT_PARAMETRO_ANNO_BOLLETTA_NULLO,
						"Il campo \"Anno bolletta\" è obbligatorio", null);

				pivotSILChiediAccertamentoRisposta.setFault(faultBean);
				return pivotSILChiediAccertamentoRisposta;
			}

			if (StringUtils.isBlank(codiceBolletta)) {
				LOG.error("pivotSILChiediAccertamento: Il campo \"Numero bolletta\" è obbligatorio");

				FaultBean faultBean = this.getFaultBean(codIpaEnte,
						FaultConstants.PIVOT_PARAMETRO_NUMERO_BOLLETTA_NULLO,
						"Il campo \"Numero bolletta\" è obbligatorio", null);

				pivotSILChiediAccertamentoRisposta.setFault(faultBean);
				return pivotSILChiediAccertamentoRisposta;
			}

			LOG.debug(
					"Chiamata al servizio pivotSILChiediAccertamento con richiesta per bolletta con parameri codIpaEnte [ "
							+ codIpaEnte + " ], annoBolletta [ " + annoBolletta + " ] e numeroBolletta [ "
							+ codiceBolletta + " ]");

			FlussoTesoreriaTO flussoTesoreriaTO = flussoTesoreriaService
					.findByCodIpaEnteDeAnnoBollettaAndCodBolletta(codIpaEnte, annoBolletta, codiceBolletta);

			if (flussoTesoreriaTO == null) {
				LOG.error(
						"pivotSILChiediAccertamento: La bolletta per codIpaEnte [ " + codIpaEnte + " ], annoBolletta [ "
								+ annoBolletta + " ] e numeroBolletta [ " + codiceBolletta + " ] non è stata trovata");

				FaultBean faultBean = this.getFaultBean(codIpaEnte, FaultConstants.PIVOT_BOLLETTA_NON_TROVATA,
						"La bolletta per codIpaEnte [ " + codIpaEnte + " ], annoBolletta [ " + annoBolletta
								+ " ] e numeroBolletta [ " + codiceBolletta + " ] non è stata trovata",
						null);

				pivotSILChiediAccertamentoRisposta.setFault(faultBean);
				return pivotSILChiediAccertamentoRisposta;
			}

			boolean hasIUF = Utilities.hasFlussoTesoreriaIUF(flussoTesoreriaTO);
			boolean hasIUV = Utilities.hasFlussoTesoreriaIUV(flussoTesoreriaTO);

			if (!hasIUF && !hasIUV) {
				LOG.error("pivotSILChiediAccertamento: La bolletta per codIpaEnte [ " + codIpaEnte
						+ " ], annoBolletta [ " + annoBolletta + " ] e numeroBolletta [ " + codiceBolletta
						+ " ] non è in standard pagoPA");

				FaultBean faultBean = this
						.getFaultBean(codIpaEnte, FaultConstants.PIVOT_BOLLETTA_NON_PAGOPA,
								"La bolletta per codIpaEnte [ " + codIpaEnte + " ], annoBolletta [ " + annoBolletta
										+ " ] e numeroBolletta [ " + codiceBolletta + " ] non è in standard pagoPA",
								null);

				pivotSILChiediAccertamentoRisposta.setFault(faultBean);
				return pivotSILChiediAccertamentoRisposta;
			}

			try {
				if (hasIUF) {
					String iuf = flussoTesoreriaTO.getCodIdUnivocoFlusso();
					List<FlussoExportTO> listaFlussiExportTO = getListaFlussiExportTOByCodIpaEnteAndIUF(codIpaEnte,
							iuf);
					if (listaFlussiExportTO == null) {
						LOG.error(
								"pivotSILChiediAccertamento: Nessuna rendicontazione associata allo IUF della bolletta");

						FaultBean faultBean = this.getFaultBean(codIpaEnte,
								FaultConstants.PIVOT_NESSUNA_RENDICONTAZIONE_TROVATA,
								"Nessuna rendicontazione associata allo IUF della bolletta", null);

						pivotSILChiediAccertamentoRisposta.setFault(faultBean);
						return pivotSILChiediAccertamentoRisposta;
					}
					pivotSILChiediAccertamentoRisposta = elaboraConResultSet(codIpaEnte, listaFlussiExportTO);
				}

				if (hasIUV) {
					String iuv = flussoTesoreriaTO.getCodIdUnivocoVersamento();
					List<FlussoExportTO> listaFlussiExportTO = flussoExportService.findByCodIpaEnteIUV(codIpaEnte, iuv);
					pivotSILChiediAccertamentoRisposta = elaboraConResultSet(codIpaEnte, listaFlussiExportTO);
				}
			} catch (Exception e) {
				LOG.error(
						"pivotSILChiediAccertamento: Si è verificato un errore durante l'esecuzione della richiesta per codIpaEnte [ "
								+ codIpaEnte + " ], annoBolletta [ " + annoBolletta + " ] e numeroBolletta [ "
								+ codiceBolletta + " ]");

				FaultBean faultBean = this.getFaultBean(codIpaEnte, PIVOT_SYSTEM_ERROR,
						"Si è verificato un errore durante l'esecuzione della richiesta per codIpaEnte [ " + codIpaEnte
								+ " ], annoBolletta [ " + annoBolletta + " ] e numeroBolletta [ " + codiceBolletta
								+ " ]",
						null);

				pivotSILChiediAccertamentoRisposta.setFault(faultBean);
				return pivotSILChiediAccertamentoRisposta;
			}
		}

		if (richiestaPerIUF != null) {
			String iuf = richiestaPerIUF.getIdentificativoUnivocoFlusso();

			if (StringUtils.isBlank(iuf)) {
				LOG.error("pivotSILChiediAccertamento: Il campo \"Identificativo univoco flusso\" è obbligatorio");

				FaultBean faultBean = this.getFaultBean(codIpaEnte, FaultConstants.PIVOT_PARAMETRO_IUF_NULLO,
						"Il campo \"Identificativo univoco flusso\" è obbligatorio", null);

				pivotSILChiediAccertamentoRisposta.setFault(faultBean);
				return pivotSILChiediAccertamentoRisposta;
			}

			try {
				List<FlussoExportTO> listaFlussiExportTO = getListaFlussiExportTOByCodIpaEnteAndIUF(codIpaEnte, iuf);
				if (listaFlussiExportTO == null) {
					LOG.error("pivotSILChiediAccertamento: Nessuna rendicontazione associata allo IUF della bolletta");

					FaultBean faultBean = this.getFaultBean(codIpaEnte,
							FaultConstants.PIVOT_NESSUNA_RENDICONTAZIONE_TROVATA,
							"Nessuna rendicontazione associata allo IUF della bolletta", null);

					pivotSILChiediAccertamentoRisposta.setFault(faultBean);
					return pivotSILChiediAccertamentoRisposta;
				}
				pivotSILChiediAccertamentoRisposta = elaboraConResultSet(codIpaEnte, listaFlussiExportTO);
			} catch (Exception e) {
				LOG.error(
						"pivotSILChiediAccertamento: Si è verificato un errore durante l'esecuzione della richiesta per codIpaEnte [ "
								+ codIpaEnte + " ], identificativoUnivocoFlusso [ " + iuf + " ]");

				FaultBean faultBean = this.getFaultBean(codIpaEnte, PIVOT_SYSTEM_ERROR,
						"Si è verificato un errore durante l'esecuzione della richiesta per codIpaEnte [ " + codIpaEnte
								+ " ], identificativoUnivocoFlusso [ " + iuf + " ]",
						null);

				pivotSILChiediAccertamentoRisposta.setFault(faultBean);
				return pivotSILChiediAccertamentoRisposta;
			}
		}
		return pivotSILChiediAccertamentoRisposta;
	}

	private List<FlussoExportTO> getListaFlussiExportTOByCodIpaEnteAndIUF(String codIpaEnte, String iuf) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.hasText(iuf, "Parametro [ iuf ] vuoto");

		List<FlussoRendicontazioneTO> listaFlussiRendicontazioneTO = flussoRendicontazioneService
				.findByCodIpaEnteAndIUF(codIpaEnte, iuf);
		if (listaFlussiRendicontazioneTO != null && !listaFlussiRendicontazioneTO.isEmpty()) {
			List<FlussoExportTO> listaFlussiExportTO = new ArrayList<FlussoExportTO>();
			for (FlussoRendicontazioneTO rend : listaFlussiRendicontazioneTO) {
				String iuv = rend.getId().getCodDatiSingPagamIdentificativoUnivocoVersamento();
				int indiceDatiSingoloPagamento = rend.getId().getIndiceDatiSingoloPagamento();
				FlussoExportTO flussoExportTO = flussoExportService
						.findByCodIpaEnteIUVAndIndiceDatiSingoloPagamento(codIpaEnte, iuv, indiceDatiSingoloPagamento);
				if (flussoExportTO != null) {
					listaFlussiExportTO.add(flussoExportTO);
				}
			}
			return listaFlussiExportTO;
		}
		return null;
	}

	private List<String> getListaCodIud(final List<FlussoExportTO> listaFlussiExportTO) {
		Assert.notEmpty(listaFlussiExportTO, "Parametro [ listaFlussiExportTO ] vuoto");

		List<String> listaCodIud = new ArrayList<String>();

		for (FlussoExportTO flussoExportTO : listaFlussiExportTO) {
			listaCodIud.add(flussoExportTO.getCodIud());
		}

		return listaCodIud;
	}

	@SuppressWarnings("unused")
	private PivotSILChiediAccertamentoRisposta elaboraExport(final String codIpaEnte,
			final List<FlussoExportTO> listaFlussiExportTO) {
		PivotSILChiediAccertamentoRisposta pivotSILChiediAccertamentoRisposta = new PivotSILChiediAccertamentoRisposta();
		if (listaFlussiExportTO != null && !listaFlussiExportTO.isEmpty()) {
			List<String> listaCodIud = getListaCodIud(listaFlussiExportTO);
			List<String> listaCodUfficio = accertamentoDettaglioService
					.getDistinctCodUfficioAccertamentiChiusiByCodIpaEnteAndListaIUD(codIpaEnte, listaCodIud);
			if (listaCodUfficio != null && !listaCodUfficio.isEmpty()) {
				for (String codUfficio : listaCodUfficio) {
					CtBilancio bilancio = new CtBilancio();
					bilancio.setUfficio(codUfficio);
					List<String> listaCodTipoDovuto = accertamentoDettaglioService
							.getDistinctCodTipoDovutoAccertamentiChiusiByCodIpaEnteListaIUDAndCodUfficio(codIpaEnte,
									listaCodIud, codUfficio);
					if (listaCodTipoDovuto != null && !listaCodTipoDovuto.isEmpty()) {
						for (String codTipoDovuto : listaCodTipoDovuto) {
							CtTipoDovuto ctTipoDovuto = new CtTipoDovuto();
							ctTipoDovuto.setCodTipoDovuto(codTipoDovuto);
							List<String> listaCodCapitolo = accertamentoDettaglioService
									.getDistinctCodCapitoloAccertamentiChiusiByCodIpaEnteListaIUDCodUfficioAndCodTipoDovuto(
											codIpaEnte, listaCodIud, codUfficio, codTipoDovuto);
							if (listaCodCapitolo != null && !listaCodCapitolo.isEmpty()) {
								for (String codCapitolo : listaCodCapitolo) {
									CtCapitolo ctCapitolo = new CtCapitolo();
									ctCapitolo.setCodCapitolo(codCapitolo);
									List<String> listaCodAccertamento = accertamentoDettaglioService
											.getDistinctCodAccertamentoAccertamentiChiusiByCodIpaEnteListaIUDCodUfficioCodTipoDovutoAndCodCapitolo(
													codIpaEnte, listaCodIud, codUfficio, codTipoDovuto, codCapitolo);
									if (listaCodAccertamento != null && !listaCodAccertamento.isEmpty()) {
										for (String codAccertamento : listaCodAccertamento) {
											CtAccertamento ctAccertamento = new CtAccertamento();
											ctAccertamento.setCodAccertamento(codAccertamento);
											BigDecimal sum = accertamentoDettaglioService.getSumImportoByAccertamento(
													codIpaEnte, listaCodIud, codUfficio, codTipoDovuto, codCapitolo,
													codAccertamento);
											ctAccertamento.setImporto(sum);
											ctCapitolo.getAccertamento().add(ctAccertamento);
										}
										ctTipoDovuto.getCapitolo().add(ctCapitolo);
									}
								}
								bilancio.getTipoDovuto().add(ctTipoDovuto);
							}
						}
						pivotSILChiediAccertamentoRisposta.getBilancio().add(bilancio);
					}
				}
			} else {
				LOG.error("pivotSILChiediAccertamento: Nessun dettaglio presente");

				FaultBean faultBean = this.getFaultBean(codIpaEnte, FaultConstants.PIVOT_DETTAGLIO_NON_PRESENTE,
						"Nessun dettaglio presente", null);

				pivotSILChiediAccertamentoRisposta.setFault(faultBean);
				return pivotSILChiediAccertamentoRisposta;
			}
		} else {
			LOG.error("pivotSILChiediAccertamento: Nessuna ricevuta trovata");

			FaultBean faultBean = this.getFaultBean(codIpaEnte, FaultConstants.PIVOT_NESSUNA_RICEVUTA_TROVATA,
					"Nessuna ricevuta trovata", null);

			pivotSILChiediAccertamentoRisposta.setFault(faultBean);
			return pivotSILChiediAccertamentoRisposta;
		}
		return pivotSILChiediAccertamentoRisposta;
	}

	private PivotSILChiediAccertamentoRisposta elaboraConResultSet(final String codIpaEnte,
			final List<FlussoExportTO> listaFlussiExportTO) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		PivotSILChiediAccertamentoRisposta pivotSILChiediAccertamentoRisposta = new PivotSILChiediAccertamentoRisposta();

		PreparedStatementCreator preparedStatementCreator_queryAccertamento = null;

		PivotSILChiediAccertamentoRispostaExtractor pivotSILChiediAccertamentoRispostaExtractor = new PivotSILChiediAccertamentoRispostaExtractor();

		preparedStatementCreator_queryAccertamento = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

				String statement = selectQueryAccertamento + " " + fromQueryAccertamento + " " + whereQueryAccertamento;

				statement += " and ad.cod_ipa_ente = ?";

				statement += " and an.cod_stato = ? and an.de_tipo_stato = ?";

				statement += " and ad.cod_iud IN ";
				statement += "(";
				for (FlussoExportTO flussoExportTO : listaFlussiExportTO) {
					if (listaFlussiExportTO.indexOf(flussoExportTO) == 0)
						statement += "?";
					else
						statement += ",?";
				}
				statement += ") ";

				statement += groupByQueryAccertamento;

				statement += orderByQueryAccertamento;

				PreparedStatement ps = con.prepareStatement(statement);

				ps.setString(1, codIpaEnte);
				ps.setString(2, Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO);
				ps.setString(3, Constants.DE_TIPO_STATO_ACCERTAMENTO);

				for (FlussoExportTO flussoExportTO : listaFlussiExportTO) {
					ps.setObject(4 + listaFlussiExportTO.indexOf(flussoExportTO), flussoExportTO.getCodIud());
				}

				return ps;
			}
		};

		pivotSILChiediAccertamentoRisposta = jdbcTemplate.query(preparedStatementCreator_queryAccertamento,
				pivotSILChiediAccertamentoRispostaExtractor);

		return pivotSILChiediAccertamentoRisposta;
	}

	private static final class PivotSILChiediAccertamentoRispostaExtractor
			implements ResultSetExtractor<PivotSILChiediAccertamentoRisposta> {

		@Override
		public PivotSILChiediAccertamentoRisposta extractData(ResultSet rs) throws SQLException, DataAccessException {
			LOG.info("pivotSILChiediAccertamento: Ricevuto ResultSet");
			LOG.info("pivotSILChiediAccertamento: INIZIO MAPPING RISPOSTA");

			PivotSILChiediAccertamentoRisposta pivotSILChiediAccertamentoRisposta = new PivotSILChiediAccertamentoRisposta();

			while (rs.next()) {
				String codUfficio = rs.getString("cod_ufficio");
				String codTipoDovuto = rs.getString("cod_tipo_dovuto");
				String codCapitolo = rs.getString("cod_capitolo");
				String codAccertamento = rs.getString("cod_accertamento");
				BigDecimal sumImporto = rs.getBigDecimal("sum_importo");

				if (CollectionUtils.isEmpty(pivotSILChiediAccertamentoRisposta.getBilancio())) {
					pivotSILChiediAccertamentoRisposta.getBilancio().add(
							populateFullBilancio(codUfficio, codTipoDovuto, codCapitolo, codAccertamento, sumImporto));
				} else {
					boolean ufficioPresente = false;
					int idxUfficio = -1;
					for (CtBilancio ctBilancio : pivotSILChiediAccertamentoRisposta.getBilancio()) {
						if (ctBilancio.getUfficio().equals(codUfficio)) {
							ufficioPresente = true;
							idxUfficio = pivotSILChiediAccertamentoRisposta.getBilancio().indexOf(ctBilancio);
							break;
						}
					}
					if (ufficioPresente) {
						boolean tipoDovutoPresente = false;
						int idxTipoDovuto = -1;
						CtBilancio ctBilancio = pivotSILChiediAccertamentoRisposta.getBilancio().get(idxUfficio);
						if (CollectionUtils.isEmpty(ctBilancio.getTipoDovuto())) {
							ctBilancio.getTipoDovuto().add(
									populateFullTipoDovuto(codTipoDovuto, codCapitolo, codAccertamento, sumImporto));
						} else {
							for (CtTipoDovuto ctTipoDovuto : ctBilancio.getTipoDovuto()) {
								if (ctTipoDovuto.getCodTipoDovuto().equals(codTipoDovuto)) {
									tipoDovutoPresente = true;
									idxTipoDovuto = ctBilancio.getTipoDovuto().indexOf(ctTipoDovuto);
									break;
								}
							}
							if (tipoDovutoPresente) {
								boolean capitoloPresente = false;
								int idxCapitolo = -1;
								CtTipoDovuto ctTipoDovuto = ctBilancio.getTipoDovuto().get(idxTipoDovuto);
								if (CollectionUtils.isEmpty(ctTipoDovuto.getCapitolo())) {
									ctTipoDovuto.getCapitolo()
											.add(populateFullCapitolo(codCapitolo, codAccertamento, sumImporto));
								} else {
									for (CtCapitolo ctCapitolo : ctTipoDovuto.getCapitolo()) {
										if (ctCapitolo.getCodCapitolo().equals(codCapitolo)) {
											capitoloPresente = true;
											idxCapitolo = ctTipoDovuto.getCapitolo().indexOf(ctCapitolo);
											break;
										}
									}
									if (capitoloPresente) {
										boolean accertamentoPresente = false;
										int idxAccertamento = -1;
										CtCapitolo ctCapitolo = ctTipoDovuto.getCapitolo().get(idxCapitolo);
										if (CollectionUtils.isEmpty(ctCapitolo.getAccertamento())) {
											ctCapitolo.getAccertamento()
													.add(populateFullAccertamento(codAccertamento, sumImporto));
										} else {
											for (CtAccertamento ctAccertamento : ctCapitolo.getAccertamento()) {
												if (ctAccertamento.getCodAccertamento().equals(codAccertamento)) {
													accertamentoPresente = true;
													idxAccertamento = ctCapitolo.getAccertamento()
															.indexOf(ctAccertamento);
													break;
												}
											}
											if (accertamentoPresente) {
												CtAccertamento ctAccertamento = ctCapitolo.getAccertamento()
														.get(idxAccertamento);

												BigDecimal accImporto = ctAccertamento.getImporto();
												BigDecimal sum = accImporto.add(sumImporto);
												ctAccertamento.setImporto(sum);
											} else {
												ctCapitolo.getAccertamento()
														.add(populateFullAccertamento(codAccertamento, sumImporto));
											}
										}
									} else {
										ctTipoDovuto.getCapitolo()
												.add(populateFullCapitolo(codCapitolo, codAccertamento, sumImporto));
									}
								}
							} else {
								ctBilancio.getTipoDovuto().add(populateFullTipoDovuto(codTipoDovuto, codCapitolo,
										codAccertamento, sumImporto));
							}
						}
					} else {
						pivotSILChiediAccertamentoRisposta.getBilancio().add(populateFullBilancio(codUfficio,
								codTipoDovuto, codCapitolo, codAccertamento, sumImporto));
					}
				}
			}
			LOG.info("pivotSILChiediAccertamento: FINE MAPPING RISPOSTA");
			return pivotSILChiediAccertamentoRisposta;
		}

		private CtBilancio populateFullBilancio(String codUfficio, String codTipoDovuto, String codCapitolo,
				String codAccertamento, BigDecimal sumImporto) {
			CtBilancio ctBilancio = new CtBilancio();
			ctBilancio.setUfficio(codUfficio);
			CtTipoDovuto ctTipoDovuto = new CtTipoDovuto();
			ctTipoDovuto.setCodTipoDovuto(codTipoDovuto);
			CtCapitolo ctCapitolo = new CtCapitolo();
			ctCapitolo.setCodCapitolo(codCapitolo);
			CtAccertamento ctAccertamento = new CtAccertamento();
			ctAccertamento.setCodAccertamento(codAccertamento);
			ctAccertamento.setImporto(sumImporto);
			ctCapitolo.getAccertamento().add(ctAccertamento);
			ctTipoDovuto.getCapitolo().add(ctCapitolo);
			ctBilancio.getTipoDovuto().add(ctTipoDovuto);
			return ctBilancio;
		}

		private CtTipoDovuto populateFullTipoDovuto(String codTipoDovuto, String codCapitolo, String codAccertamento,
				BigDecimal sumImporto) {
			CtTipoDovuto ctTipoDovuto = new CtTipoDovuto();
			ctTipoDovuto.setCodTipoDovuto(codTipoDovuto);
			CtCapitolo ctCapitolo = new CtCapitolo();
			ctCapitolo.setCodCapitolo(codCapitolo);
			CtAccertamento ctAccertamento = new CtAccertamento();
			ctAccertamento.setCodAccertamento(codAccertamento);
			ctAccertamento.setImporto(sumImporto);
			ctCapitolo.getAccertamento().add(ctAccertamento);
			ctTipoDovuto.getCapitolo().add(ctCapitolo);
			return ctTipoDovuto;
		}

		private CtCapitolo populateFullCapitolo(String codCapitolo, String codAccertamento, BigDecimal sumImporto) {
			CtCapitolo ctCapitolo = new CtCapitolo();
			ctCapitolo.setCodCapitolo(codCapitolo);
			CtAccertamento ctAccertamento = new CtAccertamento();
			ctAccertamento.setCodAccertamento(codAccertamento);
			ctAccertamento.setImporto(sumImporto);
			ctCapitolo.getAccertamento().add(ctAccertamento);
			return ctCapitolo;
		}

		private CtAccertamento populateFullAccertamento(String codAccertamento, BigDecimal sumImporto) {
			CtAccertamento ctAccertamento = new CtAccertamento();
			ctAccertamento.setCodAccertamento(codAccertamento);
			ctAccertamento.setImporto(sumImporto);
			return ctAccertamento;
		}
	}

}
