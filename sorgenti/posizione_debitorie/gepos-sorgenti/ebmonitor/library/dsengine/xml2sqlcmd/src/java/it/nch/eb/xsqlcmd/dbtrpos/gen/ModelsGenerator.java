/* generated */
package it.nch.eb.xsqlcmd.dbtrpos.gen;

import java.io.File;

import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.generator.xls.xmlrecord.XmlRecordWithExtraFields;
import it.nch.eb.flatx.records.ClassesGenerator;

/* generated */
public class ModelsGenerator {

	static final File sourceFolder 			= new File("D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/src/java"); // FIXME provide a valid source folder location 
	static final String packageName			= "it.nch.eb.xsqlcmd.dbtrpos.gen.model";

	static XmlRecordWithExtraFields rec(XmlRecord rec) {
		return XmlRecordWithExtraFields.create(rec, null);
	}
	
	static XmlRecordWithExtraFields rec(XmlRecord rec, String[][] extraFlds ) {
		return XmlRecordWithExtraFields.create(rec, extraFlds);
	}
	
	static final XmlRecordWithExtraFields[] records = new XmlRecordWithExtraFields[] {
		 rec( new it.nch.eb.xsqlcmd.dbtrpos.gen.record.FlussiRecord(),
			new String[][] {
			  { "flusso", "java.sql.Timestamp" },
			  { "nomeSupporto", "java.lang.String" },
			  { "quantSicurezza", "java.lang.String" },
			  { "numDisposizioni", "java.lang.Integer" },
			  { "totImportiNeg", "java.lang.String" },
			  { "dimFlussoFirmato", "java.lang.Integer" },
			  { "firmatario1", "java.lang.String" },
			  { "firmatario2", "java.lang.String" },
			  { "mac", "java.lang.String" },
			  { "anticipoIncassi", "java.lang.String" },
			  { "segnoImpPresent", "java.lang.String" },
			  { "tracciato", "java.lang.String" },
			  { "siaMittente", "java.lang.String" },
			  { "abiRicevente", "java.lang.String" },
			  { "adisposizTesta", "java.lang.String" },
			  { "adisposizCoda", "java.lang.String" },
			  { "divisaContoOrd", "java.lang.String" },
			  { "partizionamento", "java.lang.String" },
			  { "soggVeic", "java.lang.String" },
			  { "ccF24", "java.lang.String" },
			  { "numRecord", "java.lang.Integer" },
			  { "abiAccentratore", "java.lang.String" },
			  { "tipoIncassoRid", "java.lang.String" },
			  { "cucRicevente", "java.lang.String" },
			  { "cucBancamittente", "java.lang.String" },
			  { "ide2e", "java.lang.String" },
			  { "qualifMsg", "java.lang.String" },
			  { "credtTm", "java.sql.Timestamp" },
			  { "bicRicevente", "java.lang.String" },
			  { "orgnlMsgId", "java.lang.String" },
			  { "orgnlCredtTm", "java.sql.Timestamp" },
			  { "grpStatus", "java.lang.String" },
			  { "stRiga", "java.lang.String" },
			  { "prVersione", "java.lang.Integer" },
			  { "opInserimento", "java.lang.String" },
			  { "tsInserimento", "java.sql.Timestamp" },
			  { "opAggiornamento", "java.lang.String" },
			  { "tsAggiornamento", "java.sql.Timestamp" },
		 
			} ),
		 rec( new it.nch.eb.xsqlcmd.dbtrpos.gen.record.PendenzeRecord(),
			new String[][] {
			  { "idPendenza", "java.lang.String" },
			  { "tsDecorrenza", "java.sql.Timestamp" },
			  { "idTributo", "java.lang.String" },
			  { "coAbi", "java.lang.String" },
			  { "stRiga", "java.lang.String" },
			  { "opInserimento", "java.lang.String" },
			  { "tsInserimento", "java.sql.Timestamp" },
			  { "opAggiornamento", "java.lang.String" },
			  { "tsAggiornamento", "java.sql.Timestamp" },
			  { "opAnnullamento", "java.lang.String" },
			  { "tsAnnullamento", "java.sql.Timestamp" },
		 
			} ),
		 rec( new it.nch.eb.xsqlcmd.dbtrpos.gen.record.DestinatariRecord(),
			new String[][] {
			  { "idPendenza", "java.lang.String" },
			  { "idDestinatario", "java.lang.String" },
			  { "tsDecorrenza", "java.sql.Timestamp" },
			  { "opInserimento", "java.lang.String" },
			  { "tsInserimento", "java.sql.Timestamp" },
			  { "opAggiornamento", "java.lang.String" },
			  { "tsAggiornamento", "java.sql.Timestamp" },
		 
			} ),
		 rec( new it.nch.eb.xsqlcmd.dbtrpos.gen.record.CondizioniPagamentoRecord(),
			new String[][] {
			  { "idPendenza", "java.lang.String" },
			  { "idCondizione", "java.lang.String" },
			  { "tsDecorrenza", "java.sql.Timestamp" },
			  { "idEnte", "java.lang.String" },
			  { "cdTrbEnte", "java.lang.String" },
			  { "opAggiornamento", "java.lang.String" },
			  { "tsAggiornamento", "java.sql.Timestamp" },
			  { "opAnnullamento", "java.lang.String" },
			  { "tsAnnullamento", "java.sql.Timestamp" },
		 
			} ),
		 rec( new it.nch.eb.xsqlcmd.dbtrpos.gen.record.VociBilancioRecord(),
			new String[][] {
			  { "idPendenza", "java.lang.String" },
			  { "idCondizione", "java.lang.String" },
			  { "idVoce", "java.lang.String" },
			  { "tsDecorrenza", "java.sql.Timestamp" },
			  { "stRiga", "java.lang.String" },
			  { "prVersione", "java.lang.Integer" },
			  { "opInserimento", "java.lang.String" },
			  { "tsInserimento", "java.sql.Timestamp" },
			  { "opAggiornamento", "java.lang.String" },
			  { "tsAggiornamento", "java.sql.Timestamp" },
		 
			} ),
		 rec( new it.nch.eb.xsqlcmd.dbtrpos.gen.record.AllegatoRecord(),
			new String[][] {
			  { "idPendenza", "java.lang.String" },
			  { "idAllegato", "java.lang.String" },
			  { "tsDecorrenza", "java.sql.Timestamp" },
			  { "flContesto", "java.lang.String" },
			  { "idCondizione", "java.lang.String" },
			  { "stRiga", "java.lang.String" },
			  { "prVersione", "java.lang.Integer" },
			  { "opInserimento", "java.lang.String" },
			  { "tsInserimento", "java.sql.Timestamp" },
			  { "opAggiornamento", "java.lang.String" },
			  { "tsAggiornamento", "java.sql.Timestamp" },
		 
			} ),

	};
	
	public static void main(String[] args) {
		ClassesGenerator generator = new ClassesGenerator(sourceFolder, packageName);
			//generator.generateAll(records);
			generator.generateAll(records, new String[] {  
"it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps",   
"java.io.Serializable",   });
	}
}