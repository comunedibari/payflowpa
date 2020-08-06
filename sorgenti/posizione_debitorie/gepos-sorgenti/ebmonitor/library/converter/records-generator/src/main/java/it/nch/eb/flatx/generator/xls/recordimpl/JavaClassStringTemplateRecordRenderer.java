/**
 * Created on 20/mar/08
 */
package it.nch.eb.flatx.generator.xls.recordimpl;




/**
 * @author gdefacci
 */
public abstract class JavaClassStringTemplateRecordRenderer extends StringTemplateRecordRenderer { 

	private final String	targetPackageName;

	public JavaClassStringTemplateRecordRenderer(String pkgName) {
		this.targetPackageName = pkgName;
	}
	
	public final String getTargetPackageName() {
		return targetPackageName;
	} 

}
