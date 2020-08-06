package it.regioneveneto.mygov.payment.mypivot.dtogenerator.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public abstract class DtoTestAbstract {

	protected static List<String> compareBeans(Object newBean, Object oldBean)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<String> changesArr = new ArrayList<String>();
		compareBeans(newBean, oldBean, changesArr);
		return changesArr;
	}

	private static void compareBeans(Object newBean, Object oldBean, List<String> changesArr)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		@SuppressWarnings("unchecked")
		Iterator<String> keysIt = BeanUtils.describe(newBean).keySet().iterator();
		while (keysIt.hasNext()) {
			String key = keysIt.next();
			if (!StringUtils.equalsIgnoreCase(key, "class")) {
				Object oldValue = PropertyUtils.getProperty(oldBean, key);
				Object newValue = PropertyUtils.getProperty(newBean, key);
				if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null)) {
					changesArr.add(key);
				} else if (oldValue.getClass().getName().startsWith("it.regioneveneto")) {
					compareBeans(newValue, oldValue, changesArr);
				} else {
					if (oldValue != newValue) {// Ignores when both are "null"
						if (((oldValue != null) && !oldValue.equals(newValue))
								|| ((newValue != null) && !newValue.equals(oldValue))) {
							changesArr.add(key);
						}
					}
				}
			}
		}
	}

}
