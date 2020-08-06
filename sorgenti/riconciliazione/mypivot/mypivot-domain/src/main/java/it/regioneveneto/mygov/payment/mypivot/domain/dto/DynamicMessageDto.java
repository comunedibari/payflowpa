/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.domain.dto;

/**
 * @author Monaco
 * 
 */
public class DynamicMessageDto {

	/**
	 * 
	 */
	private String code;

	/**
	 * 
	 */
	private Object[] arguments;

	/**
	 * @param code
	 * @param arguments
	 */
	public DynamicMessageDto(final String code, final Object... arguments) {
		super();

		this.code = code;
		this.arguments = arguments;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the arguments
	 */
	public Object[] getArguments() {
		return arguments;
	}
}
