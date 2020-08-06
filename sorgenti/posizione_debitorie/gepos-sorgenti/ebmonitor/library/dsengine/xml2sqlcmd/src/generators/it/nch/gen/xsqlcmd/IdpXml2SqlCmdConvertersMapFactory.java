/**
 * 18/set/2009
 */
package it.nch.gen.xsqlcmd;

import it.nch.eb.flatx.generator.xls.xmlrecord.Xml2SqlCmdConvertersMap;

/**
 * @author gdefacci
 */
public class IdpXml2SqlCmdConvertersMapFactory {
	
	public static final IdpXml2SqlCmdConvertersMapFactory instance = new IdpXml2SqlCmdConvertersMapFactory();
	
	public Xml2SqlCmdConvertersMap create() {
		Xml2SqlCmdConvertersMap typeMapping = Xml2SqlCmdConvertersMap.defaultDbJavaMappings();
		typeMapping.put("date", "dateYYY_MM_DD_AsLongString", "toSqlDate");
		typeMapping.put("timestamp", "dateAsLongString", "toSqlTimestamp");
		typeMapping.put("time", "dateAsLongString", "toSqlTimestamp");  // an alias for timestamp
		typeMapping.put("integer", null, "toInt");
		typeMapping.put("decimal", null, "toBigDecimal");
		typeMapping.put("blob", null, "toBytes");
		typeMapping.put("statoPendenza", "char", "statoPendenzaConverter", null);
		typeMapping.put("modalitaPagamento", "char", "modalitaPagamentoConverter", null);
		typeMapping.put("tipoOperazione", "char", "tipoOperazioneConverter", null);
		typeMapping.put("tipoDestinatario", "char", "tipoDestinatarioConverter", null);
		typeMapping.put("statoPagamento", "char", "statoPagamentoConverter", null);
		typeMapping.put("tipoAllegato", "char", "tipoAllegatoConverter", null);
		return typeMapping;
	};

}
