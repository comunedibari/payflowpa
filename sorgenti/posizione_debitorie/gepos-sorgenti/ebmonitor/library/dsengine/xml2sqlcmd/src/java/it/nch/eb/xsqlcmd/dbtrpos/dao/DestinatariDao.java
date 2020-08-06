/**
 * 22/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.commands.db.KeyProviderIBatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.FlussiModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.DestinatariModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * @author gdefacci
 */
public class DestinatariDao extends KeyProviderIBatisDao {

	private final FlussoDao flussoDao;

	public DestinatariDao(SqlMapClient sqlMapClient, TablesUidProvider keysProvider) {
		super(sqlMapClient, keysProvider);
		this.flussoDao = new FlussoDao(sqlMapClient, keysProvider);
	}
	
//	private String raiseTipoOperazioneError(PendenzeModel pendenza) {
//		throw new RuntimeException("invalid tipo operazione for " + pendenza);
//	}
	
//	public AllineamentiModel newAllineamento(PendenzeModel pendenza, FlussiModel flusso) {
//		AllineamentiModel allineamento = new AllineamentiModel();
//		
//		allineamento.setFlusso(flusso.getFlusso());
//		allineamento.setIdAlpd(newAllineamentiId());
//		allineamento.setIdMittente(flusso.getIdMittente());
//		allineamento.setIdPendenzaEnte(pendenza.getIdPendenzaEnte());
//		allineamento.setUtenteCreatore(flusso.getUtenteCreatore());
//		allineamento.setIdMittenteTrt(flusso.getIdMittenteTrt());
//		allineamento.setIdSystem(flusso.getIdSystem());
//		allineamento.setIdSystemTrt(flusso.getIdSystemTrt());
//		allineamento.setCoVersione(flusso.getCoVersione());
//		
//		String tipoOperazione = pendenza.getInsert().booleanValue() ? "I" : 
//					( pendenza.getUpdate().booleanValue() ? "U" : 
//						(pendenza.getReplace().booleanValue() ? "R" : 
//							(pendenza.getDelete().booleanValue() ? "D" : raiseTipoOperazioneError(pendenza) ) ));
//		allineamento.setTipoOperazione( tipoOperazione );
//		if (pendenza.getIdPendenza()!=null)
//			allineamento.setKeyPendenza(pendenza.getIdPendenza());
//		
//		if (pendenza.getReplacingPendenzaId()!=null) 
//			allineamento.setKeyReplaced(pendenza.getReplacingPendenzaId());
//		
//		if (pendenza.getDelete().booleanValue() && allineamento.getKeyPendenza()==null) {
//			/**
//			 * secondo me e' sbagliato, capire che valore includere come KEY (colonna non null) durante la delete
//			 * */
//			allineamento.setKeyPendenza(pendenza.getReplacingPendenzaId());
//		}
//		
//		allineamento.setStRiga(pendenza.getStRiga());
//		allineamento.setPrVersione(pendenza.getPrVersione());
//		allineamento.setOpInserimento(pendenza.getOpInserimento());
//		allineamento.setTsInserimento(pendenza.getTsInserimento());
//		
//		return allineamento;
//	}

	
	public void insert(DestinatariModel destinatario, PendenzeModel pendenza) {
		insertDestinatario(destinatario, pendenza);

		//inserire il flusso non serve a niente, non si usa mai		
//		FlussiModel flusso = destinatario.getFlusso();
//		flussoDao.insertNewFlusso(pendenza, flusso);
	}

//	public void insertNewFlusso(PendenzeModel pendenza, FlussiModel flusso) {
//		flusso.setFlusso(newFlussiId());
//		flusso.setNumDisposizioni(new Integer(1));
//		flusso.setStRiga(PendenzeConsts.ST_RIGA_VALID);
//		
//		insert("insertFlussi", flusso);
//		insert("insertAllineamenti", newAllineamento(pendenza, flusso));
//	}

	public void insertDestinatario(DestinatariModel destinatario,
			PendenzeModel pendenza) {
		destinatario.setIdPendenza(pendenza.getIdPendenza());
		destinatario.setIdDestinatario(newDestinatariId());
		destinatario.setTsDecorrenza(pendenza.getTsInserimento());
		insert("insertDestinatari", destinatario);
	}

}
