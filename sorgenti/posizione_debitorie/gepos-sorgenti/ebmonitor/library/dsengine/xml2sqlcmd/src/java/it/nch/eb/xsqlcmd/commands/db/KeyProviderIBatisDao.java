/**
 * 13/mag/2009
 */
package it.nch.eb.xsqlcmd.commands.db;

import it.nch.eb.xsqlcmd.dao.UIdProvider;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.utils.DseProperties;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.Timestamp;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class KeyProviderIBatisDao extends BaseIbatisDao {

	private final TablesUidProvider keysProvider;
	private static String STRATEGY_PROPERTY_NAME = "it.tasgroup.dse.IdGenerator";
	private static String SEQUENCE_STRATEGY_PROPERTY_VALUE = "SEQUENCE";
	private static String GUID_STRATEGY_PROPERTY_VALUE = "GUID";

	public KeyProviderIBatisDao(SqlMapClient sqlMapClient, TablesUidProvider keysProvider) {
		super(sqlMapClient);
		this.keysProvider = keysProvider;
	}
	
	public TablesUidProvider getKeysProvider() {
		return keysProvider;
	}
	
	
	public String nextStringValue(UIdProvider prvdr) {
	
		String generationStrategy=DseProperties.getProperty(STRATEGY_PROPERTY_NAME,SEQUENCE_STRATEGY_PROPERTY_VALUE);
		
		boolean useGuid = GUID_STRATEGY_PROPERTY_VALUE.equals(generationStrategy);
		
		if (useGuid) {		
			return  IpBasedGeneratoreIdUnivoci.generaId(null);
		} else {
			return String.valueOf(nextValue(prvdr));
		}
		
	}
	
	public long nextValue(UIdProvider prvdr) {
		Connection conn = null;
		try {
//			conn = getSqlMapClient().getDataSource().getConnection();
			conn = getSqlMapClient().getCurrentConnection();
			return prvdr.nextVal(conn);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
//			closeConnection(conn);
		}
	}

	public String newPendenzeId() {
		return nextStringValue( keysProvider.getPendenze());
	}
	
	public String newDestinatariId(){
		return nextStringValue( keysProvider.getDestinatari());
	}
	
	public String newAllegatoId(){
		return nextStringValue( keysProvider.getAllegato());
	}
	
	public String newVociBilancioId() {
		return nextStringValue( keysProvider.getVociBilancio());
	}
	
	public String newCondizioniPagamentoId() {
		return nextStringValue( keysProvider.getCondizioniPagamento());
	}
	
	public String newAllineamentiId() {
		return nextStringValue( keysProvider.getAllineamenti());
	}
	
	public String newEsitoPendenzaId() {
		return nextStringValue( keysProvider.getEsitiPendenza());
	}
	
	public String newErroreEsitoPendenzaId() {
		return nextStringValue( keysProvider.getErroriEsitiPendenza());
	}

	public Timestamp newFlussiId() {
		return new Timestamp( nextValue( keysProvider.getFlussi()) );
	}
	
	public long newTributoStrutturatoId() {
		return nextValue(keysProvider.getTributiStrutturati());
	}
	
	public long newPrenotazioneAvvisiDigitaliId() {
		return nextValue(keysProvider.getTributiStrutturati());
	}
	
}
