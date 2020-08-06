/**
 * 23/ott/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.error;

import java.math.BigDecimal;

import it.nch.eb.xsqlcmd.dbtrpos.gen.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.DestinatariModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

/**
 * @author gdefacci
 */
public class XmlErrorInfosFactory {
	
	private XmlErrorsUtils xh = XmlErrorsUtils.instance;

	public String condizionePagamentoToUpdateDoesNotExists(CondizioniPagamentoModel cp) {
		return xh.describeError(condizionePagamentoIdPagamento(cp));
	}
	
	public String condizionePagamentoIdPagamentoAlreadyExists(CondizioniPagamentoModel cp) {
		return xh.describeError(condizionePagamentoIdPagamento(cp));
	}
	
	public String destinatarioInvalidCodiceFiscale(DestinatariModel destinatario) {
		return xh.describeError(destinariIdDestinatario(destinatario));
	}
	
	public String condizionePagamentoDataFineValiditaInvalid(CondizioniPagamentoModel cp) {
		return xh.describeError(xh.xmlWrap("condizioniPagamento", 
				new StringBufferDescribing[] {
					xh.xmlWrap("idPagamento", cp.getIdPagamento()),
					xh.xmlWrap("dataInizioValidita", cp.getDtInizioValidita()),
					xh.xmlWrap("dataFineValidita", cp.getDtFineValidita()),
					xh.xmlWrap("dataScadenza", cp.getDtScadenza()),
				}
		));
	}
	
	public String dataEmissionePendenzaInvalid(PendenzeModel pend) {
		return xh.describeError(xh.xmlWrap("pendenza", 
				new StringBufferDescribing[] {
					xh.xmlWrap("dataEmissione", pend.getTsEmissioneEnte()),
					xh.xmlWrap("dataCreazione", pend.getTsCreazioneEnte())
				}
		));
	}
	
	public String dataPrescrizionePendenzaInvalid(PendenzeModel pend) {
		return xh.describeError(xh.xmlWrap("pendenza", 
				new StringBufferDescribing[] {
					xh.xmlWrap("dataEmissione", pend.getTsEmissioneEnte()),
					xh.xmlWrap("dataPrescrizione", pend.getTsPrescrizione())
				}
		));
	}

	public String descrizioneCausalePendenzaInvalid(PendenzeModel pend, String error) {
		return xh.describeError(xh.xmlWrap("pendenza", 
				new StringBufferDescribing[] {
					xh.xmlWrap("descrizioneCausale", pend.getDeCausale()+":"+error)
				}
		));
	}
	
	public String condizionePagamentoImTotaleInvalid(CondizioniPagamentoModel cp, BigDecimal vociAmountSum) {
		return xh.describeError(
			new StringBufferDescribing[] {
				xh.xmlWrap("condizioniPagamento", 
				new StringBufferDescribing[] {
					xh.xmlWrap("idPagamento", cp.getIdPagamento()),
					xh.xmlWrap("importo", cp.getImTotale()),
				}), 
				xh.xmlWrap("vociBilancio", 
				new StringBufferDescribing[] {
					xh.xmlWrap("sommaImporti", vociAmountSum),
				})
			}
		);
	}
	
	public String xmlOperationInconsistent(PendenzeModel pend, String invalidOp) {
		return xh.describeError(xh.xmlWrap("pendenza", 
				new StringBufferDescribing[] {
					xh.xmlWrap("tipoOperazione", pend.getOperationName()),
					xh.xmlWrap("invalidOpearazione", invalidOp)
				}
		));
	}

	private StringBufferDescribing condizionePagamentoIdPagamento(
			CondizioniPagamentoModel cp) {
		return xh.xmlWrap("condizioniPagamento", 
				xh.xmlWrap("idPagamento", cp.getIdPagamento())
		);
	}
	
	private StringBufferDescribing destinariIdDestinatario(
			DestinatariModel dst) {
		return xh.xmlWrap("destinatario", 
				xh.xmlWrap("idDestinatario", dst.getIdDestinatario())
		);
	}

	public String condizionePagamentoInconsistency(
			it.nch.eb.xsqlcmd.dbtrpos.model.CondizioniPagamentoModel cp, String idPendenzaDb) {
		return xh.describeError(
			new StringBufferDescribing[] {
				xh.xmlWrap("condizioniPagamento", 
				new StringBufferDescribing[] {
					xh.xmlWrap("idPagamento", cp.getIdPagamento()),
					xh.xmlWrap("idPendenza", idPendenzaDb),
				}) 
			}
		);
	}
	
	public String condizionePagamentoNonModificabile(String idPendenzaDb) {
		return xh.describeError(
			new StringBufferDescribing[] {
				xh.xmlWrap("condizioniPagamento", 
				new StringBufferDescribing[] {
//					xh.xmlWrap("idPagamento", cp.getIdPagamento()),
					xh.xmlWrap("idPendenza", idPendenzaDb),
				}) 
			}
		);
	}
	
	public String condizionePagamentoRimborsoInvalid(PendenzeModel pend,CondizioniPagamentoModel cp) {		
		return xh.describeError(
				new StringBufferDescribing[] {
					xh.xmlWrap("pendenza", 
					new StringBufferDescribing[] {
							xh.xmlWrap("tipoOperazione", pend.getOperationName()),
					}), 
					xh.xmlWrap("condizioniPagamento", 
					new StringBufferDescribing[] {
						xh.xmlWrap("stato", cp.getStPagamento()),
					})
				}
			);

	}
}
