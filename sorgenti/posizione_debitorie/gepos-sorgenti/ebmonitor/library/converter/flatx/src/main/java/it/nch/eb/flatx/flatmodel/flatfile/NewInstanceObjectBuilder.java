/**
 * Created on 03/gen/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.StringUtils;


/**
 * @author gdefacci
 */
public class NewInstanceObjectBuilder implements ObjectBuilder {

	private static final long serialVersionUID = -7679645243036607339L;
	protected final Class concreteClass;
	private final Class	productClass;

	public NewInstanceObjectBuilder(Class klass) {
		this(klass, klass);
	}
	
	public NewInstanceObjectBuilder(Class iklass, Class klass) {
		this.concreteClass = klass;
		this.productClass = iklass;
		if (klass == null) throw new NullPointerException();
		if (productClass == null) throw new NullPointerException();
	}

	public Object create() {
		try {
			return concreteClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("error creating a[" + concreteClass.getName() + "] instance", e);
		}
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof ObjectBuilder)) return false;
		ObjectBuilder othr = (ObjectBuilder)obj;
		return othr.getProductClass().getName().equals(getProductClass().getName());
	}

	public int hashCode() {
		return concreteClass.hashCode();
	}

	public static ObjectBuilder create(Class klass) {
		return new NewInstanceObjectBuilder(klass);
	}

	public Class getProductClass() {
		return productClass;
	}

	public boolean isInitial(Object obj) {
		return ReflectionUtils.isBeanInitial(obj);
	}

	public String toString() {
		return "objectBuilder[" + StringUtils.getSimpleName(concreteClass) + "]";
	}
	
}
