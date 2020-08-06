/**
 * Created on 22/nov/07
 */
package it.nch.fwk.checks.errors.processing;

import java.util.Map;


/**
 * FIXME:remove
 * @deprecated
 * @author gdefacci
 */
public class TemplateException extends RuntimeException {
	
	private String	templateId;
	private Map	map;

	public TemplateException() {
		super();
	}

	public TemplateException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateException(String message) {
		super(message);
	}

	public TemplateException(Throwable cause) {
		super(cause);
	}
	
	public TemplateException(String message, Throwable cause, String templateId, Map map) {
		super(message, cause);
		this.templateId = templateId;
		this.map = map;
	}
	
	public TemplateException(Throwable cause, String templateId, Map map) {
		this(cause);
		this.templateId = templateId;
		this.map = map;
	}
	

	public TemplateException(Throwable cause, String templateId) {
		this(cause, templateId, null);
	}
	
	public String getMessage() {
		return "error processing template templateId[" + templateId + "] " +
		(map!=null  ? ("map[" +   map.toString() + "]") : ("")) + super.getMessage();
	}

	private static final long	serialVersionUID	= 7881527454496941779L;
}
