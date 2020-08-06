/**
 * Created on 26/mar/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.record;

import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.ClassIdentityConverter;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;
import it.nch.eb.xsqlcmd.dbtrpos.model.OperationKinds;


/**
 * @author gdefacci
 */
public interface PendenzeConverters {

	public static class Incoming {

		public static final String STATO_PENDENZA_CHIUSA = "chiusa";
		public static final String STATO_PENDENZA_APERTA = "aperta";

		public static final String MODALITA_PAGAMENTO_ENTRAMBE = "entrambe";
		public static final String MODALITA_PAGAMENTO_RATEALE = "pagamento a rate";
		public static final String MODALITA_PAGAMENTO_SINGOLO = "pagamento unico";

		public static final String TIPO_DESTINATARIO_CITTADINO = "cittadino";
		public static final String TIPO_DESTINATARIO_DELEGATO = "delegato";  		
		public static final String TIPO_DESTINATARIO_ALTRO = "altro";

		public static final String TIPO_ALLEGATO_DOCUMENTO = "documento";
		public static final String TIPO_ALLEGATO_QUIETANZA = "quietanza";
		public static final String TIPO_ALLEGATO_RICEVUTA = "ricevuta";
		public static final String TIPO_ALLEGATO_NOTA_DI_CREDITO = "ndc";

		public static final String STATO_PAGAMENTO_NON_PAGATO = "non pagato";
		public static final String STATO_PAGAMENTO_PAGATO = "pagato";
		public static final String STATO_PAGAMENTO_NON_PAGABILE = "non pagabile";
		public static final String STATO_PAGAMENTO_IRREGOLARE = "pagamento irregolare";
        public static final String STATO_PAGAMENTO_RIMBORSATO = "pagamento rimborsato";
				
	
	}

	public Converter statoPendenzaConverter = new ClassIdentityConverter() {

		private static final long	serialVersionUID	= 6202440335267875073L;

		public String encode(String stringModel) {
			String st = stringModel.trim().toLowerCase();
			if (st.equals(Incoming.STATO_PENDENZA_APERTA)) return PendenzeConsts.STATO_PENDENZA_APERTA;
			else if (st.equals(Incoming.STATO_PENDENZA_CHIUSA)) return PendenzeConsts.STATO_PENDENZA_CHIUSA;
			else throw new IllegalStateException("invalid stato pendenza " +  stringModel);
		}

		public String toString() {
			return "statoPendenzaConverter";
		}

	};

	public Converter modalitaPagamentoConverter = new ClassIdentityConverter() {

		private static final long serialVersionUID = -9013610918453552961L;

		public String encode(String stringModel) {
			String str = stringModel.trim().toLowerCase();
			if (str.equals(Incoming.MODALITA_PAGAMENTO_SINGOLO)) return PendenzeConsts.MODALITA_PAGAMENTO_SINGOLO;
			else if (str.equals(Incoming.MODALITA_PAGAMENTO_RATEALE)) return PendenzeConsts.MODALITA_PAGAMENTO_RATEALE;
			else if (str.equals(Incoming.MODALITA_PAGAMENTO_ENTRAMBE)) return PendenzeConsts.MODALITA_PAGAMENTO_ENTRAMBE;
			else throw new IllegalArgumentException("invalid tipoPagamento:" + stringModel);
		}

		public String toString() {
			return "modalitaPagamentoConverter";
		}

	};

	public Converter tipoOperazioneConverter = OperationKinds.instance.toAbbrConverter;

	public Converter tipoDestinatarioConverter = new ClassIdentityConverter() {

		private static final long serialVersionUID = -7715107022530070011L;

		public String encode(String stringModel) {
			String str = stringModel.trim().toLowerCase();
			if (  str.equals(Incoming.TIPO_DESTINATARIO_CITTADINO)) return PendenzeConsts.TIPO_DESTINATARIO_CITTADINO;
			else if (  str.equals(Incoming.TIPO_DESTINATARIO_DELEGATO)) return PendenzeConsts.TIPO_DESTINATARIO_DELEGATO;  
			else if (str.equals(Incoming.TIPO_DESTINATARIO_ALTRO)) return PendenzeConsts.TIPO_DESTINATARIO_ALTRO;
			else throw new IllegalArgumentException("invalid tipoDestinatario:" + stringModel);
		}

		public String toString() {
			return "tipoDestinatarioConverter";
		}

	};

	public Converter tipoAllegatoConverter = new ClassIdentityConverter() {

		private static final long serialVersionUID = -7715107022530070011L;

		public String encode(String stringModel) {
			String str = stringModel.trim().toLowerCase();
			if (str.equals(Incoming.TIPO_ALLEGATO_DOCUMENTO)) return PendenzeConsts.TIPO_ALLEGATO_DOCUMENTO;
			else if (str.equals(Incoming.TIPO_ALLEGATO_QUIETANZA)) return PendenzeConsts.TIPO_ALLEGATO_QUIETANZA;
			else if (str.equals(Incoming.TIPO_ALLEGATO_RICEVUTA)) return PendenzeConsts.TIPO_ALLEGATO_RICEVUTA;
			else if (str.equals(Incoming.TIPO_ALLEGATO_NOTA_DI_CREDITO)) return PendenzeConsts.TIPO_ALLEGATO_NOTA_DI_CREDITO;
			else throw new IllegalArgumentException("invalid tipoAllegato:" + stringModel);
		}

		public String toString() {
			return "tipoAllegatoConverter";
		}

	};

	public Converter statoPagamentoConverter = new ClassIdentityConverter() {

		private static final long serialVersionUID = 6000346791272816434L;

		public String encode(String stringModel) {
			String str = stringModel.trim().toLowerCase();
			if (str.equals(Incoming.STATO_PAGAMENTO_NON_PAGATO)) return PendenzeConsts.STATO_PAGAMENTO_NON_PAGATO;
			else if (str.equals(Incoming.STATO_PAGAMENTO_PAGATO)) return PendenzeConsts.STATO_PAGAMENTO_PAGATO;
			else if (str.equals(Incoming.STATO_PAGAMENTO_NON_PAGABILE)) return PendenzeConsts.STATO_PAGAMENTO_NON_PAGABILE;
			else if (str.equals(Incoming.STATO_PAGAMENTO_IRREGOLARE)) return PendenzeConsts.STATO_PAGAMENTO_IRREGOLARE;
			else if (str.equals(Incoming.STATO_PAGAMENTO_RIMBORSATO)) return PendenzeConsts.STATO_PAGAMENTO_RIMBORSATO;
			else throw new IllegalArgumentException("invalid statoPagamento:" + stringModel);
		}

		public String toString() {
			return "statoPagamentoConverter";
		}

	};
	
}
