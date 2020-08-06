/**
 * Created on 14/nov/08
 */
package it.nch.fwk.xml.validation.business;


/**
 * @author gdefacci
 */
public interface ICheckUtils {

	boolean isValidBankCode(String bankCode);

	boolean isValidIbanCode(String ibanCode);

	/**
	 * www.iso20022.org
	 * @SEE 0709PaySEG-PaySchemaLocalPayInstrumentCodes-v13c_PurposeCodes.xls
	 */
	boolean isValidExternalPurposeCode(String value);

	/**
	 * CBI-STD-001 6.03
	 */
	boolean isValidProprietaryPurposeCode(String value);

	public boolean isValidServiceCode(String serviceName);

	public boolean isAvaiableService(String serviceName);

	public String[] avaiableServicesList();

}