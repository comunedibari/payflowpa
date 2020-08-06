/*
 * Created on 31-mar-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.idp.posizionedebitoria.constants;

import it.nch.erbweb.constants.CommonConstants;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;

/**
 * @author Simone
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface PosizioneDebitoriaConstants extends CommonConstants {
	final static public String CODICE_SERVIZIO="PDC";

	final static public String TAB_AVVISI="Avvisi";

	final static public String FILTRO_ENTE="filtroEnte";
	final static public String FILTRO_STATO="filtroStato";
	final static public String FILTRO_TIPO_TRIBUTO="filtroTipoTributo";

	final static public String SORTING_DATA_EMISSIONE="dataEmissione";
	final static public String SORTING_DATA_SCADENZA="dataScadenza";
	final static public String SORTING_DATA_INIZIO_VALIDITA="dataInizio";
	final static public String SORTING_DATA_FINE_VALIDITA="dataFine";
	final static public String SORTING_ANNO_RIFERIMENTO="annoRiferimento";
	final static public String SORTING_ID_ENTE="idEnte";
	final static public String SORTING_COD_VERSAMENTO="codVersamento";
	final static public String SORTING_TIPOTRIBUTO="tipoTributo";
	final static public String SORTING_TRIBUTO="tributo";
	final static public String SORTING_IMPORTO="importo";
	final static public String SORTING_IMPORTO_PAGATO="importoPagato";
	final static public String SORTING_STATO = "stato";	
	final static public String SORTING_STATO_PAGAMENTO="statoPagamento";
	final static public String SORTING_STATO_INCASSO="statoIncasso";
	final static public String SORTING_DATA_PAGAMENTO="dataPagamento";
	final static public String SORTING_STRUMENTO_PAGAMENTO="strumentoPagamento";
	final static public String SORTING_IMPORTO_COMMISSIONI="importoCommissioni";
	final static public String SORTING_NOTE="note";
	
	final static public String MODALITA_POSIZIONE_DEBITORIA="PDB";
	final static public String MODALITA_POSIZIONE_CREDITORIA="PCR";
	final static public String MODALITA_AMMINISTRAZIONE="PCA";
	final static public String MODALITA_ATTRIBUTE="modalita";
	
	final static public String IDPENDENZA="idpend";
	final static public String IDALLEGATO="idalleg";
	final static public String IDDDP="docId";
	final static public String CODTRANSAZIONE="codTransazione";
	final static public String STATO_DESCRIZIONE="stato";
	final static public String TIPOSPONTANEO="tipspontaneo";
	
	final static public String STATO_CHIUSO="chiuso";
	final static public String STATO_DAPAGARE="dapagare";
	final static public String STATO_PAGAMENTODISPOSTO="pagamentodisposto";
	final static public String STATO_PARZIALMENTEPAGATO="parzialmentepagato";
	final static public String STATO_PAGATO="pagato";
	final static public String STATO_PAGATO_O_CHIUSO="pagato_o_chiuso";	
	final static public String STATO_NON_PAGABILE="nonpagabile";
	
	
	final static public String RIDONLINE= EnumTipoModalitaPagamento.RIDONLINE.getChiave();
	final static public String CARTADICREDITO= EnumTipoModalitaPagamento.CARTADICREDITO.getChiave();
	final static public String BONIFICO= EnumTipoModalitaPagamento.BONIFICOONLINE.getChiave();
	final static public String BOLLETTINO= EnumTipoModalitaPagamento.DDPATMBT.getChiave();
	
	final static public String CONTENTTYPE_POSIZIONE_DEBITORIA="ContentTypePDC";

	final static public String IMPORTO_TOTALE_PAGAMENTI="importoTotalePagamenti";
	final static public String LISTA_MEZZI_PAGAMENTO = "listaMezziPagamento";
	final static public String LISTA_CONFIGURAZIONI = "listaConfigurazioni";
	final static public String LISTA_CONFIGURAZIONI_NDP = "listaConfigurazioniNDP";
	final static public String LISTA_ID_CONFIGURAZIONI = "listaIdConfigurazioni";
	final static public String LISTA_CONFIGURAZIONI_PREFERITI = "listaConfigurazioniPreferiti";
	final static public String LISTA_CONFIGURAZIONI_TOTALECONCOMMISSIONI = "totalePagamentiConCommissioni";
	
	//
	final static public String LISTA_CFG_ALTRE_CONFIGURAZIONI = "listaCfgAltreConfigurazioni";

	
	final static public String  HIDE_COLUMN_CLASS = "hiddentd";
	final static public String  SHOW_COLUMN_CLASS = "testonormale";
	final static public String  SHOW_HEADER_COLUMN_CLASS = "intestazionetabella";
	final static public String  CF_COLUMN_STYLE_CLASS = "cfstyleclass";
	final static public String  ENTE_COLUMN_STYLE_CLASS = "entestyleclass";
	final static public String  CF_HEADER_COLUMN_STYLE_CLASS = "cfheaderstyleclass";
	final static public String  ENTE_HEADER_COLUMN_STYLE_CLASS = "enteheaderstyleclass";
	
	final static public String  OPERATORE_BACKOFFICE = "BO";
	final static public String  OPERATORE_ENTE = "EN";
	final static public String  OPERATORE_CITTADINO = "CI";
	final static public String  OPERATORE = "OP";
	
	final static public String EURO_SYMBOL ="&euro;";

	public static final String CAUSALE_COLUMN_STYLE_CLASS = "castyleclass";

	public static final String CAUSALE_HEADER_COLUMN_STYLE_CLASS = "caheaderstyleclass";

	public static final String MODALITA_PAG_COLUMN_STYLE_CLASS = "modstyleclass";

	public static final String MODALITA_PAG_HEADER_COLUMN_STYLE_CLASS = "modheaderstyleclass";
	
	final static public String MENU_FROM_POSIZIONE_DEB="menuFromPosizione";
	final static public String MENU_FROM_PAGAMENTI="menuFromPagamenti";
	
	final static public String TIPO_DEBITO_SPONTANEO="Spontaneo";
	final static public String TIPO_DEBITO_PENDENZA="Pendenza";
	
	final static public String LOGO_EURO_PREFIX="posDeb.euro";
	final static public String LOGO_CART_PREFIX="posDeb.cart";
	final static public String LOGO_PREFERITI_PREFIX="posDeb.preferiti";
	final static public String LOGO_CITTADINO_PREFIX="cittadino";
	
	static final public String LISTA_DISTINTE_RIACCREDITO = "listaDistinteRiaccredito";
	static final public String LISTA_FLUSSI = "listaFlussi";
	static final public String LISTA_ESITI = "listaEsiti";
	static final public String LISTA_SIZE= "listaSize";
	final static public String DETTAGLIO_FORM = "dettaglioForm";
	
	final static public String DETTAGLIO_DDP_FORM = "ddpForm";
	
	final static public String ESITI_NDP_FORM = "esitiNDPList";
	
	//STATI DISTINTA RIACCREDITO
	final static public String STATO_DISTINTA_RIACCREDITO_CREATO="CREATO";
	final static public String STATO_DISTINTA_RIACCREDITO_INATTESA="IN ATTESA";
	final static public String STATO_DISTINTA_RIACCREDITO_ESEGUITOSBF="ESEGUITO S.B.F.";
	final static public String STATO_DISTINTA_RIACCREDITO_INCORSO="IN CORSO";
	final static public String STATO_DISTINTA_RIACCREDITO_INERRORE="IN ERRORE";
	final static public String STATO_DISTINTA_RIACCREDITO_ESEGUITO="ESEGUITO";
	final static public String STATO_DISTINTA_RIACCREDITO_NONESEGUITO="NON ESEGUITO";
	
	final static public String STATO_BONIFICO_RIACCREDITO_DISPOSTO="DISPOSTO";
	
	static final public String LISTA_BONIFICI_RIACCREDITO = "listaBonificiRiaccredito";
	static final public String LISTA_BOZZE_BONIFICI_RIACCREDITO = "bozzeBonificiRiaccredito";

	final static public String FLUSSO_DISPOSITIVO="DISP";
	final static public String FLUSSO_INFORMATIVO="INFO";
	
	final static public String LISTA_STATI="LISTA_STATI";
	final static public String LISTA_TIPO_FLUSSI="LISTA_TIPO_FLUSSI";
	
	final static public String COD_RENDICONTAZIONE_ESITI_RCT="SL";
	
	final static public String CAUSALE_RID = "50000";
	
	final static public String COD_TRIBUTO_BOLLO_AUTO="BOLLO_AUTO";
	final static public String COD_TRIBUTO_MULTA="MULTA";
	
	final static public String LOGIN_SINGLE_PROFILE_ATTR_NAME = "loginSingleProfileForm";
	
	// PER HOME PAGE
	final static public String STATO_PAGAMENTO_IC = "IC";
	final static public String STATO_PAGAMENTO_ST = "ST";
	final static public String STATO_PAGAMENTO_ES = "ES";
	final static public String STATO_PAGAMENTO_EF = "EF";

	public static final String ERROR_LIMITE_RIGHE = "errorelimiterighe";
	public static final String NUM_RECORDS_FOUND= "numrecordsfound";
	public static final String DTO_RICERCA = "dtoricerca";
	public static final String TMP_TIPORICERCA = "tmptiporicerca";

	public static final String COLUMN_STYLE_CLASS = "columnstyleclass";
	final static public String HEADER_COLUMN_STYLE_CLASS = "columnheaderstyleclass";

	public static final String FLUSSI_NDP_FORM = "containerForm";

	public static final String FLUSSI_NDP_LIST = "flussiNDPList";

	public static final String RENDICONTAZIONI_LIST = "rendicontazioniList";

	
	
}
