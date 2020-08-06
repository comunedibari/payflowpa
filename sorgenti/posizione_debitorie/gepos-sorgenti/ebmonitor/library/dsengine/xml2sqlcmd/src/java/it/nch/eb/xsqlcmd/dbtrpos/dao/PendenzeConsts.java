/**
 * 22/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;


/**
 * @author gdefacci
 */
public class PendenzeConsts {

	public static final String OPERATOR_DEFAULT = "IdP";
	public static final String ST_RIGA_VALID = "V";
	public static final String ST_RIGA_CANCELLED = "A";

	public static final String CONTESTO_PENDENZA = "P";
	public static final String CONTESTO_CONDIZIONE_PAGAMENTO = "C";

	public static final Integer VERSIONE_INITIAL_VALUE = new Integer(1);

	public static final String MODALITA_PAGAMENTO_ENTRAMBE = "E";
	public static final String MODALITA_PAGAMENTO_RATEALE = "R";
	public static final String MODALITA_PAGAMENTO_SINGOLO = "S";
	public static final String STATO_ESITO_COMPLETO = "COMPLETO";
	public static final String STATO_INSERITO = "INSERITO";
	public static final String ESITO_OK = "OK";
	public static final String ESITO_KO = "KO";

	public static final String STATO_PENDENZA_CHIUSA = "C";
	public static final String STATO_PENDENZA_APERTA = "A";

	public static final String TIPO_DESTINATARIO_CITTADINO = "CI";
	public static final String TIPO_DESTINATARIO_DELEGATO = "DE";

	public static final String TIPO_DESTINATARIO_ALTRO = "AL";

	public static final String TIPO_ALLEGATO_DOCUMENTO = "Documento";
	public static final String TIPO_ALLEGATO_QUIETANZA = "Quietanza";
	public static final String TIPO_ALLEGATO_RICEVUTA = "Ricevuta";
	public static final String TIPO_ALLEGATO_NOTA_DI_CREDITO = "NdC";

	public static final String STATO_PAGAMENTO_NON_PAGATO = "N";
	public static final String STATO_PAGAMENTO_PAGATO = "P";
	public static final String STATO_PAGAMENTO_NON_PAGABILE = "X";
	public static final String STATO_PAGAMENTO_IRREGOLARE = "E";
	public static final String STATO_PAGAMENTO_RIMBORSATO = "R";

}
