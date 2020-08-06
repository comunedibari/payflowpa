/**
 * 02/lug/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao.update;

import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeDataDecoder;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeValidator;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBErrorsBag;
import it.nch.eb.xsqlcmd.dbtrpos.error.DbErrorsHolderException;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class UpdatePendenzeIntegratorEffect extends PendenzeValidator implements PendenzeModelEffect {

	private final PendenzeDataDecoder decoder;

	public UpdatePendenzeIntegratorEffect(SqlMapClient sqlMapClient, PendenzeDBErrorsFactory dbErrorsFactory ) {
		super(sqlMapClient, dbErrorsFactory);
		this.decoder = new PendenzeDataDecoder(sqlMapClient, dbErrorsFactory);
	}
	
	/* @Override */
	public void apply(PendenzeModel pendenza) {
		DBErrorsBag errs = new DBErrorsBag();
		it.nch.eb.xsqlcmd.dbtrpos.gen.model.PendenzeModel updPendenza = 
			decoder.selectPendenzaToUpdate(pendenza, errs);
		
		if (updPendenza!=null && errs.isEmpty()) {
			checkCondizioniPagamentoConsitency(updPendenza, pendenza, pendenza.getCondizioniDiPagamento(), errs);
			checkCondizioniPagamentoRimborso(updPendenza, pendenza, pendenza.getCondizioniDiPagamento(), errs);
			if (errs.isEmpty()){
			
				String idPendenza = updPendenza.getIdPendenza();
				
				Integer newVersion = new Integer(updPendenza.getPrVersione().intValue() + 1);
				pendenza.setIdPendenza(idPendenza);
				pendenza.setPrVersione(newVersion);
				pendenza.setStRiga(updPendenza.getStRiga());
				
				/** probabilmente e' inutile settare i seguenti campi */
				pendenza.setIdTributo(updPendenza.getIdTributo());
				pendenza.setCoAbi(updPendenza.getCoAbi());
				pendenza.setTsInserimento(updPendenza.getTsInserimento());
				pendenza.setOpInserimento(updPendenza.getOpInserimento());
				
				if (pendenza.getStPendenza() == null) {
					pendenza.setStPendenza(updPendenza.getStPendenza());
				}
				
				setCondizioniPagamentoIds(idPendenza, pendenza.getCondizioniDiPagamento(), newVersion, errs);
			}
		}
		
		if (!errs.isEmpty()) {
			throw new DbErrorsHolderException(errs);
		}
	}

//	private void setCondizioniPagamentpIdsOld(it.nch.eb.xsqlcmd.dbtrpos.gen.model.PendenzeModel updPendenza, Collection condizioniDiPagamento,
//			Integer newVersion, DBErrorsBag errs) {
//		if (condizioniDiPagamento!=null && !condizioniDiPagamento.isEmpty()) {
//			for (Iterator it = condizioniDiPagamento.iterator(); it.hasNext();) {
//				CondizioniPagamentoModel cp = (CondizioniPagamentoModel) it.next();
//				cp.setIdPendenza(updPendenza.getIdPendenza());
//				integrateCondizioniPagamento(cp, newVersion, errs);
//			}
//		}
//	}
	
	/**
	 * 
	 * @param condizioniDiPagamento
	 * @param idPag
	 * @return
	 */
	private CondizioniPagamentoModel byIdPagamento(Collection condizioniDiPagamento, String idPag) {
		for (Iterator it = condizioniDiPagamento.iterator(); it.hasNext();) {
			CondizioniPagamentoModel  cp = (CondizioniPagamentoModel) it.next();
			if (cp.getIdPagamento()!=null && cp.getIdPagamento().trim().equals(idPag)) {
				return cp;
			}
		}
		return null;
	}
	

	/**
	 * 
	 * @param idPendenza
	 * @param condizioniDiPagamento
	 * @param newVersion
	 * @param errs
	 */
	private void setCondizioniPagamentoIds(String idPendenza, Collection condizioniDiPagamento,
			Integer newVersion, DBErrorsBag errs) {
		
		final List dbCps = queryForList("selectCondizioniPagamentoToUpdate", idPendenza);
		
		for (Iterator it = condizioniDiPagamento.iterator(); it.hasNext();) {
			CondizioniPagamentoModel  cp = (CondizioniPagamentoModel) it.next();
			
			CondizioniPagamentoModel dbcp = byIdPagamento(dbCps, cp.getIdPagamento() );
			
			if (!"insert".equalsIgnoreCase(cp.getTiOperazioneUpdate())) {				
				if (dbcp==null) {
					PendenzeDBErrorsFactory errorsFactory = decoder.getDbErrorsFactory();
					DBError dbErr = errorsFactory.error(
							errorsFactory.errorIds.condizionePagamentoToUpdateDoesNotExists, 
							cp, 
							errorsFactory.xmlErrrorsFactory.condizionePagamentoToUpdateDoesNotExists(cp));
					errs.add(dbErr);
				} else {
					cp.setIdCondizione(dbcp.getIdCondizione());
					cp.setIdPendenza(idPendenza);
					cp.setPrVersione(newVersion);
				}
			} else {
				// Caso di insert nuova pendenza. Se ne esiste gia' una con lo stesso ID
				// Sollevo errore di business
				if (dbcp!=null) {
					PendenzeDBErrorsFactory errorsFactory = decoder.getDbErrorsFactory();
					DBError dbErr = errorsFactory.error(
							errorsFactory.errorIds.condizionePagamentoIdPagamentoAlreadyExists, 
							cp, 
							errorsFactory.xmlErrrorsFactory.condizionePagamentoIdPagamentoAlreadyExists(cp));
					errs.add(dbErr);	
				}
			}
		}
	}
	
	/**
	 * 
	 * @param cp
	 * @param newVers
	 * @param errs
	 */
	private void integrateCondizioniPagamento(CondizioniPagamentoModel cp, Integer newVers, DBErrorsBag errs) {
		
		CondizioniPagamentoModel cpToUpd = 
			(CondizioniPagamentoModel) queryForObject("selectCondizionePagamentoToUpdate", cp);
		
		if (cpToUpd==null) {
			PendenzeDBErrorsFactory errorsFactory = decoder.getDbErrorsFactory();
			throw new DbErrorsHolderException( 
					errorsFactory.error(
							errorsFactory.errorIds.condizionePagamentoToUpdateDoesNotExists, 
							cp, 
							errorsFactory.xmlErrrorsFactory.condizionePagamentoToUpdateDoesNotExists(cp)) );
		} else {
			cp.setIdCondizione(cpToUpd.getIdCondizione());
			cp.setPrVersione(newVers);
		}
		
	}

}
