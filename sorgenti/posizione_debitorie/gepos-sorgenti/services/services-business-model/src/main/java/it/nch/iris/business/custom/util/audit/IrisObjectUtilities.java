package it.nch.iris.business.custom.util.audit;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import it.nch.fwk.fo.util.ObjectUtilities;

public class IrisObjectUtilities extends ObjectUtilities {
	private static int listLimit = -1;
	private static boolean isPrintable(Object o)
	{
		if (o == null)
			return true;
		Class clazz = o.getClass();
		if (clazz.isPrimitive())
			return true;
		
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
	
	public static void objectToMap(Object o,HashMap argumentMap,String path)
	{
		try{
			// richiama la versione appropriata del metodo objectToString
			if (o != null && isPrintable(o))
				//return o.toString();
				//argumentMap.put(o.getClass().getName(), o);
				return;
			else if (o != null && o.getClass().isArray())
				arrayToMap( (Object[]) o, true, 0, new Vector(),argumentMap);
			else
				objectToMap(o, true, 0, new Vector(),argumentMap,path);
		}
		catch(Throwable e){
			//return "Error in objectToString";
		}
		
	}
	
	
	private static void arrayToMap(Object[] objArray, boolean recursive,
			int level, Vector visited,HashMap argumentMap)
	{
		 
		if (collContainsAll(visited, objArray))
		{
			//return "\n";
			return;
		}
		else
		{
			visited.add(objArray);
		}
		if (level > MAX_RECURSION_LEVEL)
			//return objArray.toString();
			return;
		String indent = calcolaIndent(level);
		if (objArray == null)
		{
			//return indent + "null";
			return;
		}
		else if (objArray.length == 0)
		{
			//return "\n";
			return;
		}
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < objArray.length; i++)
		{
			if (listLimit != -1)
			{
				if (i == 0)
				{
					//sb.append("***** Primi " + listLimit + " elementi di " +
					//		objArray.length + " *****\n");
				}
				if (i >= listLimit)
				{
					break;
				}
			}
			Object item = objArray[i];
			sb.append(indent);
			if (!recursive || isPrintable(item)){
				//sb.append(item).append("\n");
			}	
			else{
				Class classe = item.getClass();
				String nomeClasse = classe.getName();
				int pos = nomeClasse.lastIndexOf('.');
				if (pos > 0 && pos < nomeClasse.length() - 1)
				{
					nomeClasse = nomeClasse.substring(pos + 1);
				}
				objectToMap(item, recursive, level + 1, visited,argumentMap,nomeClasse);
			}
		}
		//return sb.toString();
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
	
	private static boolean collContainsAll(Vector v, Object[] o)
	{
		for (int i = 0; i < o.length; i++)
		{
			if (! (collContains(v, o[i])))
				return false;
		}
		return true;
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
	
	private static void objectToString(Object o, boolean recursive, int level,
			Vector visited,HashMap argumentMap,String path)
	{
		
		if (o != null && o.getClass().isArray() ){
			
			if (!o.getClass().getComponentType().isPrimitive() ){
				arrayToMap( (Object[]) o, recursive, level, visited,argumentMap);
			}
			//else
				//return "";
		}
		else if (o != null && o instanceof Collection)
		{
			collectionToMap( (Collection) o, recursive, level, visited,argumentMap);
		}
		else
		{
			beanToMap(o, true, level, visited,argumentMap,path);
		}
	}
	
	private static void collectionToMap(Collection c, boolean recursive,
			int level, Vector visited, HashMap argumentMap)
	{
		if (collContains(visited, c))
		{
			//return "\n";
			return;
		}
		else
		{
			visited.add(c);
		}
		if (level > MAX_RECURSION_LEVEL)
			//return c.toString();
			return;
		String indent = calcolaIndent(level);
		if (c == null)
		{
			//return indent + "null";
			return;
		}
		else if (c.size() == 0)
		{
			//return "\n";
			return;
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
					//sb.append("***** Primi " + listLimit + " elementi di " + c.size() +
					//" *****\n");
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
				//sb.append(item).append("\n");
				
			}
			else
			{
				Class classe = item.getClass();
				String nomeClasse = classe.getName();
				int pos = nomeClasse.lastIndexOf('.');
				if (pos > 0 && pos < nomeClasse.length() - 1)
				{
					nomeClasse = nomeClasse.substring(pos + 1);
				}
				objectToMap(item, recursive, level + 1, visited,argumentMap,nomeClasse);
			}
		}
		//return sb.toString();
	}
	
	private static void beanToMap(Object o, boolean recursive, int level,
			Vector visited, HashMap argumentMap,String path)
	{
		if (o instanceof SimpleDateFormat)
		{
			//return "\n";
			return;
		}
		
		if (collContains(visited, o))
		{
			//return "\n";
			return;
		}
		else
		{
			visited.add(o);
		}
		if (level > MAX_RECURSION_LEVEL && o != null)
			//return o.toString();
			return;
		String indent = calcolaIndent(level);
		if (o == null)
		{
			//return indent + "null";
			return;
		}
		//StringBuffer sb = new StringBuffer();
		Class classe = o.getClass();
		String nomeClasse = classe.getName();
		int pos = nomeClasse.lastIndexOf('.');
		if (pos > 0 && pos < nomeClasse.length() - 1)
		{
			nomeClasse = nomeClasse.substring(pos + 1);
		}
		//sb.append("\n" + indent);
		//sb.append("----- " + nomeClasse + " -----\n");
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
						value = null;
					}
					catch (IllegalArgumentException ex)
					{
						value = null;
					}
					catch (InvocationTargetException ex)
					{
						value = null;
					}
					//sb.append(indent);
					//sb.append(propName + ": ");
					String key = null;
					if (value!=null){
						if (path!=null)
							key = path+"."+nomeClasse+"."+propName;
						else
							key = nomeClasse+"."+propName;
						Collection tmpColl = (Collection)argumentMap.get(key);
						if (tmpColl==null)
							tmpColl = new ArrayList();
						tmpColl.add(value);
						argumentMap.put(key, tmpColl);
					}
					if (!recursive || isPrintable(value))
					{
						//sb.append(value + "\n");
					}
					else
					{
						objectToMap(value, recursive, level + 1, visited,argumentMap,propName);
					}
				}
			}
		}
		catch (IntrospectionException ex)
		{
			//sb.append("IntrospectionException\n");
		}
		//sb.append(indent);
		//sb.append("----- Fine " + nomeClasse + " -----\n");
		//return sb.toString();
	}
	
	private static void objectToMap(Object o, boolean recursive, int level,
			Vector visited,HashMap argumentMap, String path)
	{
		if (o != null && o.getClass().isArray() ){
			
			if (!o.getClass().getComponentType().isPrimitive() ){
				arrayToMap( (Object[]) o, recursive, level, visited,argumentMap);
			}
			//else
				//return "";
		}
		else if (o != null && o instanceof Collection)
		{
			collectionToMap( (Collection) o, recursive, level, visited,argumentMap);
		}
		else
		{
			beanToMap(o, true, level, visited,argumentMap,path);
		}
	}
}
