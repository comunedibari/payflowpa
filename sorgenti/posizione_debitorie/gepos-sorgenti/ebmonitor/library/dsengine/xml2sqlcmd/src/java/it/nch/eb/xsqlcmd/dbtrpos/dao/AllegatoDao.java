/**
 * 22/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.commands.db.KeyProviderIBatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.insert.InsertPendenzeDao;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.AllegatoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import java.util.Collection;
import java.util.Iterator;

import java.sql.Timestamp;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class AllegatoDao extends KeyProviderIBatisDao {
	
	private final String operatorDefault;

	public AllegatoDao(SqlMapClient sqlMapClient, TablesUidProvider keysProvider) {
		this(sqlMapClient, keysProvider, PendenzeConsts.OPERATOR_DEFAULT);
	}
	
	public AllegatoDao(SqlMapClient sqlMapClient, TablesUidProvider keysProvider, String opdflt) {
		super(sqlMapClient, keysProvider);
		this.operatorDefault = opdflt;
	}
	
	/**
	 * 
	 * @param pendenza
	 * @param allegato
	 * @param ts
	 */
	public void insertAllegatoPendenza(PendenzeModel pendenza, AllegatoModel allegato, Timestamp ts) {
		if (ts==null) throw new NullPointerException();
		InsertPendenzeDao.defaultInsertTimestampEffect(ts, operatorDefault, pendenza.getPrVersione()).apply(allegato);
		allegato.setIdPendenza(pendenza.getIdPendenza());
		allegato.setIdAllegato(newAllegatoId());
		allegato.setTsDecorrenza(pendenza.getTsInserimento()); 
		allegato.setFlContesto(PendenzeConsts.CONTESTO_PENDENZA);
		insert("insertAllegato", allegato);
	}

	/**
	 * 
	 * @param condizioniPagamento
	 * @param ts
	 */
	public void insertAllegatoCondizioniPagamento( CondizioniPagamentoModel condizioniPagamento, 
			Timestamp ts) {
//		AllegatoModel allegato = condizioniPagamento.getAllegato();
//		if (ts==null) throw new NullPointerException();
//		if (allegato!=null) {
//			InsertPendenzeDao.defaultInsertTimestampEffect(ts, operatorDefault, condizioniPagamento.getPrVersione()).apply(allegato);
//			allegato.setIdAllegato(newAllegatoId());
//			allegato.setTsDecorrenza(condizioniPagamento.getTsDecorrenza());
//			allegato.setFlContesto(PendenzeConsts.CONTESTO_CONDIZIONE_PAGAMENTO);
//			allegato.setIdCondizione(condizioniPagamento.getIdCondizione());
//			allegato.setIdPendenza(condizioniPagamento.getIdPendenza());
//			insert("insertAllegato", allegato);
//		}
		//cambio molteplicità da 1 ad N
		Collection allegati = condizioniPagamento.getAllegati();
		//controllo se sono presenti gli allegati
		//necessario nel caso ci troviamo nel caso updateStatus
		//probabilmente deriva da un errato mapping
		//ma, considerando che prima (vedi sopra) c'era il controllo sul 'null'
		//allora è corretto reinserirlo anche nel caso mapping 1 - N
		if (allegati!=null) {		
			Iterator iter = allegati.iterator();
			while (iter.hasNext()) {
				AllegatoModel allegato = (AllegatoModel) iter.next();			
				if (allegato!=null) {
					InsertPendenzeDao.defaultInsertTimestampEffect(ts, operatorDefault, condizioniPagamento.getPrVersione()).apply(allegato);
					allegato.setIdAllegato(newAllegatoId());
					allegato.setTsDecorrenza(condizioniPagamento.getTsDecorrenza());
					allegato.setFlContesto(PendenzeConsts.CONTESTO_CONDIZIONE_PAGAMENTO);
					allegato.setIdCondizione(condizioniPagamento.getIdCondizione());
					allegato.setIdPendenza(condizioniPagamento.getIdPendenza());
					insert("insertAllegato", allegato);
				}			
			}
		}
		
	}
	
	
//	/**
//	 * 
//	 * @param condizioniPagamento
//	 * @param ts
//	 */
//	public void updateAllegatoCondizioniPagamento( CondizioniPagamentoModel condizioniPagamento, 
//			Timestamp ts) {
//		AllegatoModel allegato = condizioniPagamento.getAllegato();
//		if (ts==null) throw new NullPointerException();
//		if (allegato!=null) {
//			InsertPendenzeDao.defaultInsertTimestampEffect(ts, operatorDefault, condizioniPagamento.getPrVersione()).apply(allegato);
//			allegato.setIdAllegato(newAllegatoId());
//			allegato.setTsDecorrenza(condizioniPagamento.getTsDecorrenza());
//			allegato.setFlContesto(PendenzeConsts.CONTESTO_CONDIZIONE_PAGAMENTO);
//			allegato.setIdCondizione(condizioniPagamento.getIdCondizione());
//			allegato.setIdPendenza(condizioniPagamento.getIdPendenza());
//			update("updateAllegato", allegato);
//		}
//	}	

}
