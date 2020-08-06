/**
 * 24/apr/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.record;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.xsqlcmd.dbtrpos.dao.IdPDocumentXPaths;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.AllegatoModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.VociBilancioModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.record.VociBilancioRecord;

/**
 * @author gdefacci
 */
public class CondizioniPagamentoRecord extends it.nch.eb.xsqlcmd.dbtrpos.gen.record.CondizioniPagamentoRecord{

	private static final long serialVersionUID = 639760801004292655L;

	public CondizioniPagamentoRecord() {
		this("Insert");
	}

	public CondizioniPagamentoRecord(String operation) {
		super( concatPaths(IdPDocumentXPaths.PENDENZA_XPATH_STRING, operation , "/InfoPagamento/DettaglioPagamento/") );
	}
	
	static String concatPaths(String prfx, String mdl, String sfx) {
		return StringUtils.concatPaths(new String[] {
			prfx,
			mdl,
			sfx
		});
	}
	
	public final IXPathToObjectConversionInfo allegati    = builder.createSeq( 
			new AllegatoRecord( "./Allegato" ), 
			AllegatoModel.class ); 
	
	public final IXPathToObjectConversionInfo vociImporto     = builder.createSeq(
			new VociBilancioRecord("./DettaglioImporto/Voce"), 
			VociBilancioModel.class );
	
}
