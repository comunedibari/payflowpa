/**
 * Created on 03/gen/08
 */
package it.nch.eb.common.utils;

import it.nch.eb.flatx.exceptions.PropertyAccessException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author gdefacci
 */
public class ReflectionUtils {

	public static interface Setter/*<T>*/ {
		void setValue(/*<T>*/Object obj);
	}
	
	public static interface Getter/*<T>*/ {
		Object getValue();
	}
	
	public static final Object cast(Object obj, Class target) {
		if (obj != null && !target.isInstance(obj))  throw new ClassCastException();
		return obj;
	}
	
	/**
	 * return the setter on the <code>target</code> object which set the name property of type String
	 */
	public static Setter getSetterForStringProperty(final Object target, String name) {
		Class setterParamClass = String.class;
		return getSetterForProperty(target, name, setterParamClass);
	}
	
	public static boolean isSeqProperty(final Object target, String name) {
		Method mthd = getGetter(target, name);
		return List.class.isAssignableFrom( mthd.getReturnType() );
	}

	/**
	 * return the setter on the <code>target</code> object which set the name property of type setterParamClass
	 */
	public static Setter getSetterForProperty(final Object target, String name, Class setterParamClass) {
		String methodName = "set" + StringUtils.capitalized(name);
		try {
			final Method method = target.getClass().getMethod(methodName, new Class[] { setterParamClass });
//			final Method method = cache.fetch(target, methodName, new Class[] { setterParamClass });
			return new SetterImpl(target, method);
		} catch (Exception e) {
			Method method;
			Method[] mthds = target.getClass().getMethods();
			for (int i = 0; i < mthds.length; i++) {
				method = mthds[i];
				if (method.getName().equals(methodName)) {
					if (method.getParameterTypes().length==1) {
						Class parameterType = method.getParameterTypes()[0];
						if (parameterType.isAssignableFrom(setterParamClass)) {
							return new SetterImpl(target, method);
						}
					}
				}
			}

			throw new PropertyAccessException("cant get setter ",  e, name, setterParamClass, target);
		}
	}
	
	public static Object getGetterValue(Object obj, String name) {
		Method method = getGetter(obj, name);
		return getGetterValue(obj, method);
	}

	public static Method getGetter(Object obj, String name) {
		try {
			String getterMethodName = "get" + StringUtils.capitalized(name);
			Method method = obj.getClass().getMethod(getterMethodName, null);
			return method;
		} catch (Exception e) {
			throw new PropertyAccessException("error getting getter ", e, name, obj);
		}
	}
	
	public static Setter getSetterForProperty(final Object target, String name) {
		String setterName = "set" + StringUtils.capitalized(name);
		Method[] mthds = target.getClass().getMethods();
		for (int i = 0; i < mthds.length; i++) {
			Method method = mthds[i];
			if (method.getName().equals(setterName)) {
				if (hasSetterSignature(method)) return new SetterImpl(target, method);
			}
		}
		return null;
	}
	
	public static boolean hasSetterSignature(Method methd) {
		return (methd.getParameterTypes().length == 1) && methd.getReturnType() == Void.TYPE; 
	}

	public static Field[] getFields(Object target, Class klass) {
		List res = new ArrayList();
		Field[] flds = target.getClass().getFields();
		try {
			for (int i = 0; i < flds.length; i++) {
				Field field = flds[i];
				Object val = field.get(target);
				if (klass.isAssignableFrom(val.getClass())) {
					res.add(field);
				}
			}
		} catch (Exception e) {
			throw new PropertyAccessException("cant get field ", e);
		}
		return (Field[]) res.toArray(new Field[0]);
	}
	
	public static Field[] getClassFields(Object target, Class fldsKlass) {
		Class targetClass = target.getClass();
		return getClassFields(targetClass, fldsKlass);
	}

	public static Field[] getClassFields(Class targetClass, Class fldsKlass) {
		List res = new ArrayList();
		Field[] flds = targetClass.getFields();
		try {
			for (int i = 0; i < flds.length; i++) {
				Field field = flds[i];
				int modifiers = field.getModifiers();
				if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
					Object val = field.get(null);
					if (fldsKlass.isAssignableFrom(val.getClass())) {
						res.add(field);
					}
				}
			}
		} catch (Exception e) {
			throw new PropertyAccessException("cant get class field ", e);
		}
		return (Field[]) res.toArray(new Field[0]);
	}
	
	public static InputStream getInputStream(String templateGroupName) {
		try {
			InputStream res = Thread.currentThread().getContextClassLoader().getResourceAsStream(templateGroupName);
			if (res == null ) throw new FileNotFoundException("templateGroup : " + templateGroupName);
			return res;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static InputStream getInputStreamInPakage(Class klass, String templateGroupName) {
		try {
			InputStream res = klass.getResourceAsStream(templateGroupName);
			if (res == null ) throw new FileNotFoundException("templateGroup : " + templateGroupName);
			return res;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
//	try to invoke close on the give object since closeable doesnt exist
//	public static void close(Object obj) {
//		if (obj!=null) {
//			try {
//				if (obj!=null) obj.getClass().getMethod("close", null).invoke(obj, null);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

	private static final class SetterImpl implements Setter {

		private final Object	target;
		private final Method	method;

		private SetterImpl(Object target, Method method) {
			this.target = target;
			this.method = method;
		}

		public void setValue(Object obj) {
			try {
				method.invoke(target, new Object[] { obj });
			} catch (Exception e) {
				Class paramType = null;
				if (method.getParameterTypes().length>0) paramType = method.getParameterTypes()[0];
				throw new PropertyAccessException("cant set value on property ", e, method.getName(), paramType, target);
			}
		}
	}
	
	public static boolean isBeanInitial(Object obj) {
		if (obj == null) return true;
		Class klass = obj.getClass();
		Method[] methods = klass.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (isGetter(method) && (!isGetClassMethod(method))) {
				Object value = getGetterValue(obj, method);
				if (!isInitial(value)) return false;
			}
		}
		return true;
	}
	
	private static boolean isGetClassMethod(Method method) {
		String name = method.getName();
		if (name.equals("getClass") 
			&& (method.getParameterTypes().length==0)
			&& (method.getReturnType().equals(Class.class))) return true;
		return false;
	}

	public static boolean isInitial(Object obj) {
		if (obj==null) return true;
		if (obj instanceof String && "".equals(((String)obj).trim())) return true;
		if (obj instanceof Integer || obj instanceof Long) return true;
		
		
		if (obj instanceof Object[]) {
			if (((Object[])obj).length==0) return true;
		}
		if (obj instanceof Collection) 
			if (((Collection)obj).isEmpty()) return true;

		return false;
	}

	public static Object getGetterValue(Object object, Method method) {
		try {
			if (! isGetter(method)) throw new IllegalStateException("can get the value of getters");
			Object value = method.invoke(object, new Object[] {});
			return value;
		} catch (Exception e) {
			throw new PropertyAccessException("error getting value from getter ", e, method.getName(), method.getReturnType(), object);
		} 
	}

	public static boolean isGetter(Method method) {
		String name = method.getName();
		if (name.startsWith("get") 
			&& (method.getParameterTypes().length==0)
			&& (!method.getReturnType().equals(Void.TYPE))) return true;
		return false;
	}
	
	public static String getFolderNameFromPackage(String packageName) {
		String folderName = null;
		if (packageName.indexOf('.')>0) {
			folderName = StringUtils.findReplace(packageName, "\\.", "/");
		}
		return folderName;
	}

	/**
	 * returns null if the method is missing
	 * @param string
	 * @param classes
	 * @return
	 */
	public static Method getMethod(Object source, String string, Class[] classes) {
		if (source==null || string==null || "".equals(string.trim())) throw new NullPointerException();
		try {
			Method mthd = source.getClass().getMethod(string, classes);
			return mthd;
		} catch (Exception e) {
			throw new PropertyAccessException("error getting method " + string, e, string, source);
		}
		
	}

	
	
	public static Throwable getExceptionOrCause(Class/*<? extends Throwable>*/ klass, Throwable e) {
		if (e == null) return null;
		else if (klass.isAssignableFrom(e.getClass())) return e;
		else return getExceptionOrCause(klass, e.getCause());
	}
	
//	public static void flush(Object outWriter) {
//		if (outWriter!=null) {
//			try {
//				outWriter.getClass().getMethod("flush", null).invoke(outWriter, null);
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			} 
//		}
//	}
	
	public static void setProperty(Object target, String name, Object propValue) {
		String methodName = "set" + StringUtils.capitalized(name);
		Method[] mthds = target.getClass().getMethods();
		boolean invoked = false;
		for (int i = 0; i < mthds.length && !invoked; i++) {
			final Method method = mthds[i];
			if (method.getName().equals(methodName)) {
				if (method.getParameterTypes().length==1) {
					try {
						method.invoke(target, new Object[] { propValue });
						invoked = true;
					} catch (Exception e1) {
						throw new PropertyAccessException("error setting property " + name,  e1, method, name, target, propValue);
					} 
				}
			}
		}
		if (!invoked) {
			throw new PropertyAccessException("the property " + name + " does not exits on object " + target, name,  propValue, target);
		}
	}
	

	public static void setStringProperty(Object target, String name, String textValue) {
		setProperty(target, name, textValue, String.class);
	}
	
	public static void setProperty(Object target, String name, Object propValue, Class setterParamClass ) {
		String methodName = "set" + StringUtils.capitalized(name);
		try {
			final Method method = target.getClass().getMethod(methodName, new Class[] { setterParamClass });
			method.invoke(target, new Object[] { propValue });
		} catch (Exception e) {
			Method[] mthds = target.getClass().getMethods();
			for (int i = 0; i < mthds.length; i++) {
				final Method method = mthds[i];
				if (method.getName().equals(methodName)) {
					if (method.getParameterTypes().length==1) {
						Class parameterType = method.getParameterTypes()[0];
						if (parameterType.isAssignableFrom(setterParamClass)) {
							try {
								method.invoke(target, new Object[] { propValue });
							} catch (Exception e1) {
								throw new PropertyAccessException("error getting setter ",  e1, name, setterParamClass, target);
							} 
						}
					}
				}
			}

			throw new PropertyAccessException("cant get setter ",  e, name, setterParamClass, target);
		}
	}
	
	public static StackTraceElement[] lastTrace(int numb) {
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		int stckLen = stack.length - 1; // ignore last stack trace element
		int len = stckLen > numb ? numb : stckLen;
		StackTraceElement[] res = new StackTraceElement[len];
		System.arraycopy(stack, 1, res, 0, len);
		return res;
	}
}
