/**
 * 12/ott/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.commands.db.KeyProviderIBatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.FlussiModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class FlussoDao extends KeyProviderIBatisDao {

	private final AllineamentoDao allineamentoDao;
	public FlussoDao(SqlMapClient session, TablesUidProvider keysProvider) {
		super(session, keysProvider);
		this.allineamentoDao = new AllineamentoDao(session, keysProvider);
	}

	/**
	 * @deprecated
	 * 
	 * @param pendenza
	 * @param flusso
	 */
	public void insertNewFlusso(PendenzeModel pendenza, FlussiModel flusso) {
		flusso.setFlusso(newFlussiId());
		flusso.setNumDisposizioni(new Integer(1));
		flusso.setStRiga(PendenzeConsts.ST_RIGA_VALID);
		
		insert("insertFlussi", flusso);
		allineamentoDao.insertNewAllineamento(pendenza, flusso);
	}

}
