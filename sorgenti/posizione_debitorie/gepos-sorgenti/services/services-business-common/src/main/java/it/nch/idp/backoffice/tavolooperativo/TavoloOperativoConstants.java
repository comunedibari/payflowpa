package it.nch.idp.backoffice.tavolooperativo;

import it.nch.erbweb.constants.CommonConstants;

public interface TavoloOperativoConstants extends CommonConstants {
	final static public String SORTING_DATA_RICEZIONE="dataRicezione";
	final static public String SORTING_E2EMSGID="e2eMsgId";
	final static public String SORTING_SENDERID="senderId";
	final static public String SORTING_SENDERSYS="senderSys";
	final static public String SORTING_STATO="stato";
	final static public String SORTING_IDERRORE="idErrore";
	final static public String SORTING_DATA_INVIO="dataInvio";
	final static public String SORTING_RECEIVERID="receiverId";
	final static public String SORTING_RECEIVERSYS="receiverSys";
	final static public String SORTING_DATA_CREAZIONE="dataCreazione";
	final static public String SORTING_TIPO_FLUSSO="tipoFlusso";
	final static public String SORTING_NOME_SUPPORTO="nomeSupporto";
	final static public String SORTING_IMPORTO="importo";
	final static public String SORTING_NUMREC="numRec";
	final static public String SORTING_NOME_FILE="nomeFile";
	final static public String SORTING_NUMPROG="numProg";
	final static public String SORTING_DATA_SPED_RIC="dataSpedRic";
	final static public String SORTING_IBAN="iban";
	
	final static public String STATO_FLUSSO_REND_ACCETTATO="ACCETTATO";
	final static public String STATO_FLUSSO_REND_SCARTATO="SCARTATO";
	final static public String STATO_FLUSSO_REND_ESITATO="ESITATO";
	final static public String STATO_FLUSSO_BON_INSERITO="INSERITO";
	final static public String STATO_FLUSSO_BON_CREATO="CREATO";
	final static public String STATO_FLUSSO_BON_DACREARE="DA_CREARE";

	static final public String TIPO_ERRORE_IDP_MESSAGGI = "IdpAllineamentoPendenze";
	static final public String TIPO_ERRORE_IDP_NOTIFICHE = "IdpInformativaPagamento";
	static final public String TIPO_ERRORE_IDP_RENDICONTI = "IdpRendicontazioneEnti";
	
	static final public String LOGO_NOTIFICHE_MESSAGGI = "messaggi";

	static final public String LISTA_DISTINTE_PAGAMENTO = "listaDistintePagamento";
	static final public String LISTA_PRENOTAZIONI = "listaPrenotazioni";
	static final public String LISTA_SIZE= "listaSize";
}