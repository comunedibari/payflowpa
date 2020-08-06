/**
 * 22/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.error;


/**
 * FIXME: rename in PendenzeErrorsFactory
 * @author gdefacci
 */
public class PendenzeDBErrorsFactory {
	
	/**
	 * FIXME: rename in PendenzeErrorIds
	 * @author gdefacci
	 */
	public static class DBErrorsId {
		private DBErrorsId() {
		}
		public final String pendenzaDuplicata = "errors.pendenza.duplicated";
		public final String cantDecodeIdEnte = "errors.pendenza.decode.idente";
		public final String cantDecodeTributoID = "errors.pendenza.decode.tributoid";
		public final String pendezaToUpdateDoesNotExists = "errors.pendenza.update.doesntexists";
		public final String condizionePagamentoToUpdateDoesNotExists = "errors.condizionepagamento.update.doesntexists";
		public final String condizionePagamentoIdPagamentoAlreadyExists = "errors.condizionepagamento.idpagamento.alredyexists";
		public final String transactionRollback = "errors.db.transactionRollback"; // never used
		
		/** FIXME: maybe not the proper place */
		public final String destinatarioInvalidCodiceFiscale = "errors.business.destinatario.codicefiscale.invalid";
		public final String destinatarioInvalidpartitaiva = "errors.business.destinatario.partitaiva.invalid";
		public final String delegatoSenzaCittadino = "errors.business.delegato.cittadino.missing";
		public final String delegatoSenzaDescrizione = "errors.business.delegato.descrizione.missing";
		public final String dateDifferentiSuCartella = "errors.business.cartella.date.differenti";
		public final String causalePagamentoMissing = "errors.business.causalePagamento.missing";
		public final String statoCondizioneNonOmogeneoPerCartella = "errors.business.cartella.statoNonOmogeneo";
		
		public final String impTotaleCartellaErrato = "errors.business.importoTotale.cartella.invalid";
		public final String cartellaSoloSoluzioneUnica = "errors.business.cartellaPagamento.soluzioneUnica.missing";
		public final String cartellaSoloInsert = "errors.business.cartellaPagamento.InsertOnly.missing";
		
		public final String ibanErrato = "errors.business.ibanCartella.invalid";
		
		public final String dataEmissionePendenzaInvalid = "errors.business.pendenza.dataemissione.invalid"; 
		public final String dataPrescrizionePendenzaInvalid = "errors.business.pendenza.dataprescrizione.invalid"; 
		public final String condizionePagamentoImTotaleInvalid = "errors.business.condizionePagamento.importo.invalid";
		public final String condizionePagamentoDataFineValiditaInvalid = "errors.business.condizionePagamento.datafinevalidita.invalid";
		public final String pendenzaDivisaInvalid = "errors.business.pendenza.divisa.invalid";
		public final String xmlOperationInconsistent = "errors.xml.tipoOperazione.inconsistency";
		public final String condizionePagamentoInconsistency = "errors.xml.condizionePagamento.idPendenza.inconsistency";
		public final String condizionePagamentoNonModificabile = "errors.xml.condizionePagamento.unmodifiable";
		public final String descrizioneCausaleInvalid="errors.business.pendenza.descrizioneCausale.invalid";
		public final String allegatoContickiInvalid="errors.business.pendenza.conticki.allegato.invalid";
		public final String allegatoContickiXmlInvalid="errors.business.pendenza.conticki.allegatoXml.invalid";
		public final String condizionePagamentoRimborsoInvalid = "errors.business.condizionePagamento.rimborso.invalid";
	}
	
	public final DBErrorsId errorIds = new DBErrorsId();
	public final XmlErrorInfosFactory xmlErrrorsFactory = new XmlErrorInfosFactory();
	
	public DBError error(String code, Object model) {
		return new DBError(code, model);
	}
	
	public DBError error(String code, Object model, String xmlInfos) {
		return new DBError(code, model, xmlInfos);
	}

}
