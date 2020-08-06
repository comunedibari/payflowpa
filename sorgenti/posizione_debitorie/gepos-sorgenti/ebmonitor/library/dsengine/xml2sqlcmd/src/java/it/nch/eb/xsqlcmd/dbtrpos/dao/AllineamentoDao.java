/**
 * 12/ott/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.commands.db.KeyProviderIBatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.FlussiModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.AllineamentiModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class AllineamentoDao extends KeyProviderIBatisDao {

	public AllineamentoDao(SqlMapClient session, TablesUidProvider keysProvider) {
		super(session, keysProvider);
	}

	private String raiseTipoOperazioneError(PendenzeModel pendenza) {
		throw new RuntimeException("invalid tipo operazione for " + pendenza);
	}

	public AllineamentiModel newAllineamento(PendenzeModel pendenza, FlussiModel flusso) {
		AllineamentiModel allineamento = new AllineamentiModel();

		allineamento.setFlusso(flusso.getFlusso());
		allineamento.setIdAlpd(newAllineamentiId());
		allineamento.setIdMittente(flusso.getIdMittente());
		allineamento.setIdPendenzaEnte(pendenza.getIdPendenzaEnte());
		allineamento.setUtenteCreatore(flusso.getUtenteCreatore());
		allineamento.setIdMittenteTrt(flusso.getIdMittenteTrt());
		allineamento.setIdSystem(flusso.getIdSystem());
		allineamento.setIdSystemTrt(flusso.getIdSystemTrt());
		allineamento.setCoVersione(flusso.getCoVersione());

		String tipoOperazione = pendenza.getInsert().booleanValue() ? "I" :
					( pendenza.getUpdate().booleanValue() ? "U" :
						(pendenza.getReplace().booleanValue() ? "R" :
							(pendenza.getUpdateMassivo().booleanValue() ? "M" :
								(pendenza.getDelete().booleanValue() ? "D" : raiseTipoOperazioneError(pendenza) ) )));
		allineamento.setTipoOperazione( tipoOperazione );
		if (pendenza.getIdPendenza()!=null)
			allineamento.setKeyPendenza(pendenza.getIdPendenza());

		if (pendenza.getReplacingPendenzaId()!=null)
			allineamento.setKeyReplaced(pendenza.getReplacingPendenzaId());

		if (pendenza.getDelete().booleanValue() && allineamento.getKeyPendenza()==null) {
			allineamento.setKeyPendenza(pendenza.getReplacingPendenzaId());
		}

		allineamento.setStRiga(pendenza.getStRiga());
		allineamento.setPrVersione(pendenza.getPrVersione());
		allineamento.setOpInserimento(pendenza.getOpInserimento());
		allineamento.setTsInserimento(pendenza.getTsInserimento());

		return allineamento;
	}


	public void insertNewAllineamento(PendenzeModel pendenza, FlussiModel flusso) {
		insert("insertAllineamenti", newAllineamento(pendenza, flusso));
	}


}
