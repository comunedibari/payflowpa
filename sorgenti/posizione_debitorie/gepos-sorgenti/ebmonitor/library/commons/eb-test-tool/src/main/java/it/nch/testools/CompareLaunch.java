/**
 * Created on 12/mag/08
 */
package it.nch.testools;

import it.nch.TestProperties;

import java.io.File;

public class CompareLaunch extends LaunchCompare {
	
	static String getCompareApp() { return TestProperties.kit.get(TestProperties.COMPARE_APP_PROPERTY); }

	public CompareLaunch(File left, File right) {
		super(getCompareApp(), left, right);
	}
	
}