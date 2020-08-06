package it.nch.fwk.fo.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class ObjectUtilities {
	
	/**
	 * @param src An object
	 * @return A byte Array representation of an Object
	 * @throws IOException
	 */
	public static byte[] objectToByteArray(Object src) throws IOException {
		if (src == null)
			return null;
		if (src instanceof byte[]) {
			return (byte[]) src;
		} else if (src instanceof String) {
			return ((String) src).getBytes();
		}
		
		byte[] dest = null;
		ByteArrayOutputStream bs = null;
		ObjectOutputStream os = null;
		try {
			bs = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bs);
			os.writeObject(src);
			os.flush();
			dest = bs.toByteArray();
		} catch (IOException e) {
			os.close();
			bs.close();
			throw e;
		}
		return dest;
	}
	
	/**
	 * @param src a byte Array source object
	 * @return an Object representation for the source
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object byteArrayToObject(byte[] src) throws IOException, ClassNotFoundException {
		Object dest = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(src);
		ObjectInputStream ois = new ObjectInputStream(bais);
		dest = ois.readObject();
		ois.close();
		return dest;
	}
	
	
	/**
	 * Block of spaces used to indent the output of methods like
	 * objectToString & similar
	 */
	public static final String INDENT = "    ";
	
	/**
	 * Maximum recursion level used in objectToString method & similar
	 */
	public static final int MAX_RECURSION_LEVEL = 10;
	
	/**
	 * Used to set a maximum limit for the visualization of vector elements. 
	 * -1: all the elements are shown 
	 */
	private static int listLimit = -1;
	
	public static java.sql.Timestamp getCurrentSqlDateInstance()
	{
		
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * Performs object to String conversion.
	 * Handles Java Bean, Collection and array objects.
	 * if the object is a bean, it uses introspection in a <b>recursive</b> way. 
	 * @param o the input object
	 * @return The String representation of the input
	 */
	public static String objectToString(Object o)
	{
		try{
			// richiama la versione appropriata del metodo objectToString
			if (o != null && isPrintable(o))
				return o.toString();
			else if (o != null && o.getClass().isArray())
				return arrayToString( (Object[]) o, true, 0, new Vector());
			else
				return objectToString(o, true, 0, new Vector());
		}
		catch(Throwable e){
			return "Error in objectToString";
		}
		
	}
	
	/**
	 * Performs object to String conversion.
	 * Handles Java Bean, Collection and array objects.
	 * if the object is a bean, it uses introspection in a <b>recursive</b> way. 
	 * @param o the input object
	 * @param limit the maximum size of the list
	 * @return The String representation of the input
	 */
	public static String objectToString(Object o, int limit)
	{
		int prevLimit = getListLimit();
		setListLimit(limit);
		String strRes = objectToString(o);
		setListLimit(prevLimit);
		return strRes;
	}
	
	/**
	 * Returns the property of a bean, performing recursion.
	 * @param o the bean to examine
	 * @return A string containing bean properties
	 */
	public static String beanToString(Object o)
	{
		return beanToString(o, true, 0, new Vector());
	}
	
	public static String beanToString(Object o, boolean recursive, int level)
	{
		return beanToString(o, recursive, level, new Vector());
	}
	
	/**
	 * Among the object's field, it returns only the <b>null</b> ones
	 * (they must have a corresponding getter)
	 * 
	 * @param obj the input object
	 * @param fields the list of fields to check
	 * @return The list of <b>null</b> fields
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static List checkNull(Object obj, String[] fields) throws
	NoSuchMethodException, InvocationTargetException, IllegalAccessException
	{
		List nullFields = new LinkedList();
		
		Class c = obj.getClass();
		int size = fields.length;
		for (int i = 0; i < size; ++i)
		{
			String field = fields[i];
			String method;
			if (field.length() > 1)
			{
				char[] ch =
				{
						field.charAt(0)};
				method = "get" + new String(ch).toUpperCase() + field.substring(1);
			}
			else
			{
				method = "get" + field.toUpperCase();
			}
			Method getField = c.getMethod(method, null);
			if (getField.invoke(obj, null) == null)
			{
				nullFields.add(field);
			}
		}
		
		return nullFields;
	}
	
//	---------------------------------------------------- Metodi privati
	
	/**
	 * Stampa le proprieta' di un bean.
	 * Nel caso di oggetti composti, se viene impostata la proprieta'
	 * <code>recursive = true</code> esegue la ricorsione se l'oggetto incluso
	 * non e' un oggetto primitivo e non dichiara il metodo
	 * <code>toString()</code>.
	 * @param o         bean
	 * @param recursive indica se eseguire la ricorsione
	 * @param level     livello della ricorsione corrente; utilizzato per l'indentazione
	 * @return
	 */
	private static String beanToString(Object o, boolean recursive, int level,
			Vector visited)
	{
		if (o instanceof SimpleDateFormat)
		{
			return "\n";
		}
		if (collContains(visited, o))
		{
			return "\n";
		}
		else
		{
			visited.add(o);
		}
		if (level > MAX_RECURSION_LEVEL && o != null)
			return o.toString();
		String indent = calcolaIndent(level);
		if (o == null)
		{
			return indent + "null";
		}
		StringBuffer sb = new StringBuffer();
		Class classe = o.getClass();
		String nomeClasse = classe.getName();
		int pos = nomeClasse.lastIndexOf('.');
		if (pos > 0 && pos < nomeClasse.length() - 1)
		{
			nomeClasse = nomeClasse.substring(pos + 1);
		}
		sb.append("\n" + indent);
		sb.append("----- " + nomeClasse + " -----\n");
		try
		{
			BeanInfo info = Introspector.getBeanInfo(classe, Object.class);
			PropertyDescriptor[] propDesc = info.getPropertyDescriptors();
			String propName = null;
			Object value = null;
			for (int i = 0; i < propDesc.length; i++)
			{
				if (propDesc[i].getReadMethod() != null &&
						Modifier.isPublic(propDesc[i].getReadMethod().getModifiers()))
				{
					propName = propDesc[i].getName();
					try
					{
						value = propDesc[i].getReadMethod().invoke(o, new Object[0]);
					}
					catch (IllegalAccessException ex)
					{
						value = "IllegalAccessException";
					}
					catch (IllegalArgumentException ex)
					{
						value = "IllegalArgumentException";
					}
					catch (InvocationTargetException ex)
					{
						value = "InvocationTargetException";
					}
					sb.append(indent);
					sb.append(propName + ": ");
					if (!recursive || isPrintable(value))
					{
						sb.append(value + "\n");
					}
					else
					{
						sb.append(objectToString(value, recursive, level + 1, visited));
					}
				}
			}
		}
		catch (IntrospectionException ex)
		{
			sb.append("IntrospectionException\n");
		}
		sb.append(indent);
		sb.append("----- Fine " + nomeClasse + " -----\n");
		return sb.toString();
	}
	
	private static boolean collContains(Vector v, Object o)
	{
		for (int i = 0; i < v.size(); i++)
		{
			if (v.get(i) == o)
				return true;
		}
		return false;
	}
	
	private static boolean collContainsAll(Vector v, Object[] o)
	{
		for (int i = 0; i < o.length; i++)
		{
			if (! (collContains(v, o[i])))
				return false;
		}
		return true;
	}
	
	public static String arrayToString(Object[] objArray)
	{
		return arrayToString(objArray, false, 0, new Vector());
	}
	
	/**
	 * Converts a collection into a string.
	 * It does not use recursion.
	 * @param c the input collection
	 * @return The string representation
	 */
	public static String collectionToString(Collection c)
	{
		return collectionToString(c, false, 0, new Vector());
	}
	
	public static int getDayOfMonth(java.sql.Timestamp date)
	{
		Calendar calendar = GregorianCalendar.getInstance();
		
		calendar.setTimeInMillis(date.getTime());
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Compares two dates by month and year.
	 * @param month
	 * @param year
	 * @param t
	 * @return
	 */
	public static boolean compareYearMonth(
			int month,
			int year,
			java.sql.Timestamp t)
	{
		Calendar calt = Calendar.getInstance();
		calt.setTimeInMillis(t.getTime());
		
		return ( (month == calt.get(Calendar.MONTH)) &&
				(year == calt.get(Calendar.YEAR)));
	}
	
	/**
	 * Controlla se un oggetto e' stampabile.
	 * Viene definito "stampabile" un oggetto che ha una delle caratteristiche:
	 * <ul>
	 * <li><code>null</code></li>
	 * <li>un tipo di dato primitivo</li>
	 * <li>dichiara il metodo <code>toString()</code></li>
	 * </ul>
	 * @param o
	 * @return true se l'oggetto e' stampabile, false altrimenti.
	 */
	private static boolean isPrintable(Object o)
	{
		if (o == null)
			return true;
		Class clazz = o.getClass();
		if (clazz.isPrimitive())
			return true;
		//if(clazz.isAssignableFrom(Number.class))
		//    return true;
		if (o instanceof Collection)
			return false; // Forza l'uso di objectToString per una Collection
		try
		{
			clazz.getDeclaredMethod("toString", null);
		}
		catch (SecurityException ex)
		{
			throw ex;
		}
		catch (NoSuchMethodException ex)
		{
			return false;
		}
		return true;
	}
	
	private static String calcolaIndent(int level)
	{
		StringBuffer indent = new StringBuffer("");
		for (int l = 0; l < level; l++)
		{
			indent.append(INDENT); // indent
		}
		return indent.toString();
	}
	
	private static String arrayToString(Object[] objArray, boolean recursive,
			int level, Vector visited)
	{
		if (collContainsAll(visited, objArray))
		{
			return "\n";
		}
		else
		{
			visited.add(objArray);
		}
		if (level > MAX_RECURSION_LEVEL)
			return objArray.toString();
		String indent = calcolaIndent(level);
		if (objArray == null)
		{
			return indent + "null";
		}
		else if (objArray.length == 0)
		{
			return "\n";
		}
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < objArray.length; i++)
		{
			if (listLimit != -1)
			{
				if (i == 0)
				{
					sb.append("***** Primi " + listLimit + " elementi di " +
							objArray.length + " *****\n");
				}
				if (i >= listLimit)
				{
					break;
				}
			}
			Object item = objArray[i];
			sb.append(indent);
			if (!recursive || isPrintable(item))
				sb.append(item).append("\n");
			else
				sb.append(objectToString(item, recursive, level + 1, visited));
		}
		return sb.toString();
	}
	
	private static String collectionToString(Collection c, boolean recursive,
			int level, Vector visited)
	{
		if (collContains(visited, c))
		{
			return "\n";
		}
		else
		{
			visited.add(c);
		}
		if (level > MAX_RECURSION_LEVEL)
			return c.toString();
		String indent = calcolaIndent(level);
		if (c == null)
		{
			return indent + "null";
		}
		else if (c.size() == 0)
		{
			return "\n";
		}
		StringBuffer sb = new StringBuffer("");
		Iterator iter = c.iterator();
		int i = 0;
		while (iter.hasNext())
		{
			if (listLimit != -1)
			{
				if (i == 0)
				{
					sb.append("***** Primi " + listLimit + " elementi di " + c.size() +
					" *****\n");
				}
				if (i >= listLimit)
				{
					break;
				}
				i++;
			}
			Object item = iter.next();
			sb.append(indent);
			if (!recursive || isPrintable(item))
			{
				sb.append(item).append("\n");
			}
			else
			{
				sb.append(objectToString(item, recursive, level + 1, visited));
			}
		}
		return sb.toString();
	}
	
//	---------------- overloading di objectToString
//	Il compilatore richiamera' il metodo corretto a seconda del tipo
//	dell'oggetto
	
	private static String objectToString(Object o, boolean recursive, int level,
			Vector visited)
	{
		if (o != null && o.getClass().isArray() ){
			
			if (!o.getClass().getComponentType().isPrimitive() ){
				return arrayToString( (Object[]) o, recursive, level, visited);
			}
			else
				return "";
		}
		else if (o != null && o instanceof Collection)
		{
			return collectionToString( (Collection) o, recursive, level, visited);
		}
		else
		{
			return beanToString(o, true, level, visited);
		}
	}
	
	public static int getListLimit()
	{
		return listLimit;
	}
	
	public static void setListLimit(int limit)
	{
		listLimit = limit;
	}
	
	/**
	 * It creates a changeable list starting from an array of Objects.
	 * @param elements the input array of Objects
	 * @return the List representation of the Objects
	 */
	public static List asModifiableList(Object[] elements)
	{
		List list = new ArrayList();
		for (int i = 0; i < elements.length; i++)
		{
			list.add(elements[i]);
		}
		return list;
	}
	
	static public Object deepCopy(Object oldObj) throws Exception
	{
		ObjectOutputStream objectOutputStream = null;
		ObjectInputStream objectInputStream = null;
		try
		{
			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			// serialize and pass the object
			objectOutputStream.writeObject(oldObj);
			objectOutputStream.flush();
			ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			// return the new object
			return objectInputStream.readObject();
		}
		catch (Exception e)
		{
			System.out.println("Exception in ObjectCloner = " + e);
			throw (e);
		}
		finally
		{
			if (objectOutputStream != null)
			{
				objectOutputStream.close();
			}
			if (objectInputStream != null)
			{
				objectInputStream.close();
			}
		}
	}
	
	public static String concat(String uno, String due)
	{
		if (uno != null && due != null)
			return uno + " " + due;
		if (uno != null)
			return uno;
		if (due != null)
			return due;
		return null;
	}
	
	public static String serialize(Object obj) {
		if (obj == null)
			return "null";
		if (isPrintable(obj))
			return obj.getClass().getName() + obj.toString();
		if (obj != null && obj.getClass().isArray())
			return serializeArrayToString( (Object[]) obj, true, 0, new Vector());
		else
			return serializeObjectToString(obj, true, 0, new Vector());
		
	}
	
	public static String serializeObjectToString(Object o, boolean recursive, int level, Vector visited){
		if (o != null && o.getClass().isArray())
			return serializeArrayToString( (Object[]) o, recursive, level, visited);
		else if (o != null && o instanceof Collection)
		{
			return serializeCollectionToString( (Collection) o, recursive, level, visited);
		}
		else
		{
			return serializeBeanToString(o, true, level, visited);
		}
	}
	
	private static String serializeArrayToString(Object[] objArray, boolean recursive,
			int level, Vector visited)
	{
		if (collContainsAll(visited, objArray))
		{
			return "";
		}
		else
		{
			visited.add(objArray);
		}
		if (level > MAX_RECURSION_LEVEL)
			return objArray.toString();
		if (objArray == null)
		{
			return "null";
		}
		else if (objArray.length == 0)
		{
			return "";
		}
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < objArray.length; i++)
		{
			if (listLimit != -1)
			{
				if (i == 0)
				{
				}
				if (i >= listLimit)
				{
					break;
				}
			}
			Object item = objArray[i];
			
			if (!recursive || isPrintable(item))
				sb.append(item.getClass().getName()).append(item);
			else
				sb.append(serializeObjectToString(item, recursive, level + 1, visited));
		}
		return sb.toString();
	}
	
	private static String serializeCollectionToString(Collection c, boolean recursive,
			int level, Vector visited)
	{
		if (collContains(visited, c))
		{
			return "";
		}
		else
		{
			visited.add(c);
		}
		if (level > MAX_RECURSION_LEVEL)
			return c.toString();
		if (c == null)
		{
			return "null";
		}
		else if (c.size() == 0)
		{
			return "\n";
		}
		StringBuffer sb = new StringBuffer("");
		Iterator iter = c.iterator();
		int i = 0;
		while (iter.hasNext())
		{
			if (listLimit != -1)
			{
				if (i == 0)
				{
					
				}
				if (i >= listLimit)
				{
					break;
				}
				i++;
			}
			Object item = iter.next();
			if (!recursive || isPrintable(item))
			{
				sb.append(item);
			}
			else
			{
				sb.append(serializeObjectToString(item, recursive, level + 1, visited));
			}
		}
		return sb.toString();
	}
	private static String serializeBeanToString(Object o, boolean recursive, int level,
			Vector visited)
	{
		if (o instanceof SimpleDateFormat)
		{
			return "ù";
		}
		if (collContains(visited, o))
		{
			return "";
		}
		else
		{
			visited.add(o);
		}
		if (level > MAX_RECURSION_LEVEL && o != null)
			return o.toString();
		if (o == null)
		{
			return "null";
		}
		StringBuffer sb = new StringBuffer();
		Class classe = o.getClass();
		String nomeClasse = classe.getName();
		sb.append(nomeClasse);
		try
		{
			BeanInfo info = Introspector.getBeanInfo(classe, Object.class);
			PropertyDescriptor[] propDesc = info.getPropertyDescriptors();
			String propName = null;
			Object value = null;
			for (int i = 0; i < propDesc.length; i++)
			{
				if (propDesc[i].getReadMethod() != null &&
						Modifier.isPublic(propDesc[i].getReadMethod().getModifiers()))
				{
					propName = propDesc[i].getName();
					try
					{
						value = propDesc[i].getReadMethod().invoke(o, new Object[0]);
					}
					catch (IllegalAccessException ex)
					{
						value = "IllegalAccessException";
					}
					catch (IllegalArgumentException ex)
					{
						value = "IllegalArgumentException";
					}
					catch (InvocationTargetException ex)
					{
						value = "InvocationTargetException";
					}
					sb.append(propName + ":");
					if (!recursive || isPrintable(value))
					{
						sb.append(value);
					}
					else
					{
						sb.append(serializeObjectToString(value, recursive, level + 1, visited));
					}
				}
			}
		}
		catch (IntrospectionException ex)
		{
			sb.append("IntrospectionException\n");
		}
		return sb.toString();
	}   
	
}
