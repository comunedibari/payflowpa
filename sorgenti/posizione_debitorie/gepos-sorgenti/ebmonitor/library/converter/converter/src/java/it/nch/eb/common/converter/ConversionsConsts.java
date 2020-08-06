/**
 * Created on 14/gen/08
 */
package it.nch.eb.common.converter;

import it.nch.eb.flatx.flatmodel.flatfile.parser.ParserDsl;


/**
 * varius business / flattening consts
 * @author gdefacci
 */
public interface ConversionsConsts {

	String TIPO_RECORD 				= ParserDsl.TIPO_RECORD_BINDINGS_ID;
	String TOTAL_ROW_NUMBER			= "TOTAL_ROW_NUMBER";
	
	/**
	 * this const is just used a placeholder: the following line (prsent in almost any record)
	 * 
	 * public final Converter	recordCount		= createGetBindingValue(numb7, REC_COUNT, true);
	 * 
	 * simply does nothing, and it is only usefull when generating models from records.
	 * TODO: make it disappear?
	 */
	String REC_COUNT				= "record count";		
	String BODY_REC_COUNT			= "body_record_count";
	
	String SIA						= "sia";
	
	String VERSION					= "version";
	
	String CUC_SIA_MAP 				= "cucSiaMap";
	String IBAN_MAP 				= "ibanMap";
	String INST_ID_MAP 				= "instIdMap";
	String CUC_CUC_LOGIC_DEST_MAP 	= "cucCucLogicDestMap";	
	
	interface Encodings {
		String ISO_LATIN = "ISO-8859-1";
	}
	
	String BLANK 					= "BLANK";
	String TIMESTAMP				= "TIMESTAMP";
	
//	String CURRENT_LINE_NUMBER		= "CURRENT_LINE_NUMBER";
	String LINES_IN_BODY_COUNTER	= "LINES_IN_BODY_COUNTER";

}
