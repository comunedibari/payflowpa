/**
 * Created on 26/feb/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.record;

import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.TypedXPathMapFunction;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.xsqlcmd.dbtrpos.dao.IdPDocumentXPaths;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.AllegatoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.DestinatariModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.OperationKinds;


/**
 * @author gdefacci
 */
public class PendenzeRecord extends it.nch.eb.xsqlcmd.dbtrpos.gen.record.PendenzeRecord {

	/**
	 * NON static
	 * @author gdefacci
	 */
	private final class OperationKindFlag implements TypedXPathMapFunction {
		private final String opName;

		public OperationKindFlag(String opName) {
			this.opName = opName;
		}

		public Class getTargetClass() {
			return Boolean.class;
		}

		public Object getValue(XPathPosition pos, IXPathMapScope elem) {
			if (getOperationName().equals(opName)) return Boolean.TRUE;
			return Boolean.FALSE;
		}
	}

	private static final long	serialVersionUID	= -2126733753423173284L;

	public PendenzeRecord() {
		this(IdPDocumentXPaths.PENDENZA_XPATH_STRING, "Insert");
	}

	public PendenzeRecord(String operation) {
		this(IdPDocumentXPaths.PENDENZA_XPATH_STRING, operation);
	}

	public PendenzeRecord(String basePath, String childPath) {
		super(basePath, childPath);
	}

	public String getOperationName() {
		return getChildPath();
	}

	public final IXPathToObjectConversionInfo msgid = builder.create( "../../h:IdpHeader/h:E2E/h:E2EMsgId" );
	public final IXPathToObjectConversionInfo senderId = builder.create( "../../h:IdpHeader/h:E2E/h:Sender/h:E2ESndrId" );

	public final IXPathToObjectConversionInfo tipoOperazione =
		builder.create( OperationKinds.instance.toObjectConverter, "@TipoOperazione",  true );

	public final IXPathToObjectConversionInfo condizioniDiPagamento     = builder.createSeq(
			new CondizioniPagamentoRecord(getOperationName()),
			CondizioniPagamentoModel.class );

	public final IXPathToObjectConversionInfo destinatari     = builder.createSeq(
			new DestinatariRecord( getOperationName() ),
			DestinatariModel.class );


	public final IXPathToObjectConversionInfo allegati     = builder.createSeq(
			new AllegatoRecord(getOperationName(), "Allegato"),
			AllegatoModel.class );

	public final IXPathToObjectConversionInfo insert = builder.create(
			new OperationKindFlag("Insert") );

	public final IXPathToObjectConversionInfo update = builder.create(
			new OperationKindFlag("UpdateStatus") );

	public final IXPathToObjectConversionInfo updateMassivo = builder.create(
			new OperationKindFlag("UpdateMassivo") );

	public final IXPathToObjectConversionInfo replace = builder.create(
			new OperationKindFlag("Replace") );

	public final IXPathToObjectConversionInfo delete = builder.create(
			new OperationKindFlag("Delete") );
}
