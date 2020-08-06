/*
 * Creato il 6-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.db.util;

import it.nch.ebweb.generate.core.MalformedAttribute;
import it.nch.ebweb.generate.core.MalformedConfigurationException;
import it.nch.ebweb.generate.core.Syntax;

import java.util.StringTokenizer;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class Attribute {
	
	private String typeI="NON ASSEGNATO";
	private String typeC="NON ASSEGNATO";
	
	private String type="NON ASSEGNATO";
	
	private String name;
	private String primitiveName;
	private String importOfType;
	
	private String proprietaryImportClassType=null;
	private String proprietaryImportInterfaceType=null;
	
	private boolean propImport=false;	
	private boolean existInCommon=false;
	private boolean formRef=false;
	private boolean attributeExtended=false;
	
	private boolean decPartOf=false;	
	private boolean intPartOf=false;
	
	
	//private boolean attributeEredit=false;
	private String[] concateData=null;
	private String splitData=null;
	
	
	private boolean fakeAttribute=false;
	private String defaulValue=null;
	
	public Attribute(){
		
	}
	
	public Attribute(String attribute,boolean commonAttribute) throws MalformedAttribute{
		
		
		
		StringTokenizer st = new StringTokenizer(attribute.trim()," ");	
		
		type = st.nextToken();
		type = type.trim();
		
				
		int initParam = attribute.lastIndexOf("<");
		int endParam  = attribute.lastIndexOf(">");
		String params = null;
		
		if (initParam>0){
			
			params = attribute.substring(initParam+1,endParam);
			//System.out.println("PARAMS ="+params);
			
			if ((params.lastIndexOf("extended"))>0) {
				
				//System.out.println("Attributo EREDITATO");	
				attributeExtended = true;
			}
			
			if ((attribute.lastIndexOf("fakeAttribute"))>0) {
				
				//System.out.println("Attributo FAKE");	
				fakeAttribute = true;	
			} 
			
			if ((attribute.lastIndexOf("concat_importo "))>0) {
				
				String toConcat = attribute.substring(attribute.lastIndexOf("[")+1,attribute.lastIndexOf("]"));
				
				
				//System.out.println("Attributo toConcat="+toConcat);	
				StringTokenizer sconcat = new StringTokenizer(toConcat,"+");
				concateData = new String[2];
				
				concateData[0]=sconcat.nextToken().trim();
				concateData[1]=sconcat.nextToken().trim();	
				
			} 
			
			if ((attribute.lastIndexOf(" def="))>0) {
				
				//System.out.println("Attributo DEF=");	
				
				String todef = attribute.substring(attribute.lastIndexOf("(")+1,attribute.lastIndexOf(")"));				
				//System.out.println("DEF="+todef);	
				defaulValue = todef;				
			} 	
			if ((attribute.lastIndexOf("decPartOf"))>0) {
				
				//System.out.println("Attributo decPartOf");	
				
				String todef = attribute.substring(attribute.lastIndexOf("[")+1,attribute.lastIndexOf("]"));		
				//System.out.println("DEF="+todef);	
				decPartOf=true;	
				splitData = todef;				
			} 	
			if ((attribute.lastIndexOf("intPartOf"))>0) {
				
				//System.out.println("Attributo intPartOf");	
				
				String todef =  attribute.substring(attribute.lastIndexOf("[")+1,attribute.lastIndexOf("]"));					
				//System.out.println("DEF="+todef);	
				
				//private boolean decPartOf=false;	
				
				intPartOf=true;
				splitData = todef;						
			} 				
		
			name =st.nextToken();	
			name+=";";
			//System.out.println("NAME MOD ="+name);
			
			
			
		}else{
			
			name =st.nextToken();				
			//System.out.println("NAME ="+name);
		
		}
		
		
		
		
		if (type.startsWith("[")){
			
			String path = type.substring(1,type.lastIndexOf(".")+1);
			type = type.substring(type.lastIndexOf(".")+1);
						
			if (!commonAttribute){
				
				proprietaryImportClassType=path+type.substring(0,type.lastIndexOf("(")-1);		
			    proprietaryImportInterfaceType=path+type.substring(type.lastIndexOf("(")+1,type.lastIndexOf("]")-1);
				typeI = type.substring(type.lastIndexOf("(")+1,type.lastIndexOf(")"));	
				typeC = type.substring(type.lastIndexOf(".")+1,type.lastIndexOf("("));
			
			}else{
				
				 proprietaryImportInterfaceType=path+type.substring(type.lastIndexOf(".")+1,type.lastIndexOf("]"))+"Common;";
				 typeI = type.substring(type.lastIndexOf(".")+1,type.lastIndexOf("]"));	
				 typeC = typeI+"Common";
				 formRef=true;
				 
				 
				
				
			}
			
			propImport=true;					
			type = typeC;
			
		}
				
		
		
		
		String end =  name.trim().substring(name.trim().length()-1,name.trim().length());
		
		//String end =  attribute.trim().substring(attribute.trim().length()-1,attribute.trim().length());		
		//System.out.println("====> END ="+end);	
		
		
		if (! (end.equalsIgnoreCase(";")||(end.equalsIgnoreCase(">")))){
			
			throw new MalformedAttribute(" dichiarazione dell'attributo "+name+" NON ha un terminatore corretto");
		}
					
		primitiveName = name.trim().substring(0,name.trim().length()-1);
		
		
		
		if (commonAttribute)
			name = name.trim().substring(0,name.trim().length()-1)+"IForm";
		else
			name = name.trim().substring(0,name.trim().length()-1);	
		
		
	}
	
	
	
				
	public String getCompositeIdSetterMethod(String compositeId){		
		String code="";
		code ="public void set"+Syntax.onlyFirstUpperCase(name)+"("+type+" "+name+"){";
		
		code+="\n 		this."+compositeId+"."+name+"="+name+";";
		code+="\n    } \n";
		
		return code;				
	}
	
	public String getCompositeIdGetterMethod(String compositeId){	
		
		String code="";
		
		code ="public "+type+" get"+Syntax.onlyFirstUpperCase(name)+"() {";	
				
		code+="\n 		return this."+compositeId+"."+name+";";
		code+="\n 	} \n";
		
		return code;
	}
	
	
	/**
	 * @return Restituisce il valore name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name Il valore name da impostare.
	 */
	public void setName(String name) {	
		this.name = name;
		
	}
	/**
	 * @return Restituisce il valore type.
	 */
	public String getType() {				
		return type;	
	}
	
	public String getTypeForAttribute(String suffix){
		return typeI+suffix;
	}							
	
	public String getISetterMethod(String suffixinternaltype){		
		if (!this.isPropImport())
			return "public void set"+Syntax.onlyFirstUpperCase(name)+"("+type+" "+name+");\n";
		else
			return "public void set"+Syntax.onlyFirstUpperCase(name)+"("+typeI+suffixinternaltype+" "+name+");\n";
	}
	
	public String getIGetterMethod(String suffixinternaltype){	
		if (!this.isPropImport())
			return "public "+type+" get"+Syntax.onlyFirstUpperCase(name)+"();\n";
		else
			return "public "+typeI+suffixinternaltype+" get"+Syntax.onlyFirstUpperCase(name)+"();\n";
	}
	
	
	public String getCSetterMethod(String suffixinternaltype,boolean isRefForm){		
		String code="";
		if (!this.isPropImport())
			code ="public void set"+Syntax.onlyFirstUpperCase(name)+"("+type+" "+name+"){";
		else{
			if (isRefForm)
				 code ="public void set"+Syntax.onlyFirstUpperCase(name)+"("+typeI+"Common"+" "+name+"){";
			else
				 code ="public void set"+Syntax.onlyFirstUpperCase(name)+"("+typeI+suffixinternaltype+" "+name+"){";
			 			
		}	
		
		
		if (attributeExtended){
			
			code+="\n 		 super.set"+Syntax.onlyFirstUpperCase(name)+"("+name+");";
			
		}else{
			
			if (isRefForm)		
				code+="\n 		this."+name+"=("+typeI+"Common)"+name+";";			
			else
				code+="\n 		this."+name+"="+name+";";					
		}
		
		
		code+="\n    } \n";
		
		
		return code;				
	}
	
	public String getCGetterMethod(String suffixinternaltype,boolean isRefForm){	
		
		String code="";
		
		if (!this.isPropImport())
			code ="public "+type+" get"+Syntax.onlyFirstUpperCase(name)+"() {";	
		else{
			if (!isRefForm)
				code ="public "+typeI+suffixinternaltype+" get"+Syntax.onlyFirstUpperCase(name)+"() {";	
			else
				code ="public "+typeI+"Common"+" get"+Syntax.onlyFirstUpperCase(name)+"() {";	
		}	
		
		if (attributeExtended){
			
			code+="\n 		return super.get"+Syntax.onlyFirstUpperCase(name)+"();";
			
		}else{
			
			
			if (isRefForm)
				
				code+="\n 		return ("+typeI+"Common)this."+name+";";
			
			else{
				
				if ((this.concateData==null)&&(this.splitData==null))
					code+="\n 		return this."+name+";";
				
				else if (this.concateData!=null){
					
					code+=this.concatImport(concateData,name);					
					code+="\n 		return this."+name+";";
					
				}else if (this.splitData!=null){
					
					code+=this.splitData(name);					
					code+="\n 		return this."+name+";";
				
				}				
			}			
		}
			
		code+="\n 	} \n";
		
		return code;
	}
	
	
	
	public String getIFormSetterMethod(String currentType,String suffix) throws MalformedAttribute{		
	
		String code="";
		if (!this.isPropImport())
			code ="public void set"+Syntax.onlyFirstUpperCase(name)+"("+type+" "+name+"){";
		else
			code ="public void set"+Syntax.onlyFirstUpperCase(name)+"("+typeI+suffix+" "+name+"){";
		
		if ((!this.isNativeType(currentType))&&(!this.isPropImport())){
			
			if (attributeExtended){
				
				code+="\n 		super.set"+Syntax.onlyFirstUpperCase(name.substring(0,name.length()-5))+"("+this.getTraslType(currentType,false)+"("+name+"));";
			}else{
				
				code+="\n 		this."+name.substring(0,name.length()-5)+"="+this.getTraslType(currentType,false)+"("+name+");";
			}
			
		}
		else{
			
			if (attributeExtended){
				
				code+="\n 		super.set"+Syntax.onlyFirstUpperCase(name.substring(0,name.length()-5))+"("+name+");";
				
			}else{
				
				code+="\n 		this."+name.substring(0,name.length()-5)+"="+name+";";
			}
		}
			
		code+="\n    } \n";
		
		return code;				
	}
	
	public String getIFormGetterMethod(String currentType,String suffix) throws MalformedAttribute{	
		
		String code="";
		
		if (!this.isPropImport())
			code ="public "+type+" get"+Syntax.onlyFirstUpperCase(name)+"() {";	
		else
			code ="public "+typeI+suffix+" get"+Syntax.onlyFirstUpperCase(name)+"() {";	
					
		if ((!this.isNativeType(currentType))&&(!this.isPropImport())){
			
			
			if (attributeExtended){
				
				code+="\n 		return super.get"+Syntax.onlyFirstUpperCase(name)+"();";
				
			}else {
				
				code+="\n 		return "+this.getTraslType(currentType,true)+"(this."+name.substring(0,name.length()-5)+");";
				
			}			
		}
		else{
			
			if (attributeExtended){
				
				code+="\n 		return super.get"+Syntax.onlyFirstUpperCase(name)+"();";
				
			}else {
				code+="\n 		return this."+name.substring(0,name.length()-5)+";";
			}	
		}
		
		code+="\n 	} \n";
		
		return code;
	}
	
	
	public String getIFromExtendsSetterMethod(String currentType,String suffix) throws MalformedAttribute{		
		
			String code="";
			if (!this.isPropImport())
				code ="public void set"+Syntax.onlyFirstUpperCase(name)+"("+type+" "+name+"){";
			else
				code ="public void set"+Syntax.onlyFirstUpperCase(name)+"("+typeI+suffix+" "+name+"){";
			
			if ((!this.isNativeType(currentType))&&(!this.isPropImport())){
				
				if (attributeExtended){
					
					code+="\n 		super.set"+Syntax.onlyFirstUpperCase(name.substring(0,name.length()-5))+"("+name+");";
					
				}else{
					code+="\n 		this."+name.substring(0,name.length()-5)+"="+this.getTraslType(currentType,false)+"("+name+");";
				}
				
			}
			else{
				
				if (attributeExtended){
					
					code+="\n 		super.set"+Syntax.onlyFirstUpperCase(name.substring(0,name.length()-5))+"("+name+");";
					
				}else{
					code+="\n 		this."+name.substring(0,name.length()-5)+"="+name+";";
				}
			}
				
			code+="\n    } \n";
			
			return code;				
		}
	
	
	public String getIFromExtendsGetterMethod(String currentType,String suffix) throws MalformedAttribute{	
		
		String code="";
		
		if (!this.isPropImport())
			code ="public "+type+" get"+Syntax.onlyFirstUpperCase(name)+"() {";	
		else
			code ="public "+typeI+suffix+" get"+Syntax.onlyFirstUpperCase(name)+"() {";	
					
		if ((!this.isNativeType(currentType))&&(!this.isPropImport())){
			
			
			if (attributeExtended){
				
				code+="\n 		return "+this.getTraslType(currentType,true)+"(super."+name.substring(0,name.length()-5)+");";
				
			}else{
				code+="\n 		return "+this.getTraslType(currentType,true)+"(this."+name.substring(0,name.length()-5)+");";
			}
			
			
			
		}
		else{
			
			if (attributeExtended){
				
				code+="\n 		return ("+this.typeI+suffix+")super.get"+Syntax.onlyFirstUpperCase(name.substring(0,name.length()-5))+"();";
				
			}else{
				code+="\n 		return ("+this.typeI+suffix+")this.get"+Syntax.onlyFirstUpperCase(name.substring(0,name.length()-5))+"();";
			}
			
		}
			
		
		code+="\n 	} \n";
		
		return code;
	}
	
	public String getPrintShow(){						
		return "System.out.println(\""+name+"=\"+this.get"+Syntax.onlyFirstUpperCase(name)+"());";			
	}
	
	
	
	/**
	 * @return Restituisce il valore importOfType.
	 * @throws MalformedAttribute
	 * @throws MalformedConfigurationException
	 */
	public String getImportOfType(boolean isInterface) throws MalformedAttribute  {
				
		if (this.formRef){
			return "import "+this.proprietaryImportInterfaceType;
		}			
		if (this.propImport){
			if (isInterface)
				return "import "+this.proprietaryImportInterfaceType;
			else
				return "import "+this.proprietaryImportClassType;
		}else					
		
		if (this.type.equals("Object"))
			return "import java.lang.Object;";
		if (this.type.equals("Date"))
			return "import java.util.Date;";
		if (this.type.equals("Collection"))
			return "import java.util.Collection;";
		if (this.type.equals("String"))
			return null;
		if (this.type.equals("Double"))
			return null;
		if (this.type.equals("int"))
			return null;	
		if (this.type.equals("long"))
			return null;	
		if (this.type.equals("Float"))
			return null;
		if (this.type.equals("Long"))
			return null;	
		if (this.type.equals("Integer"))
			return null;	
		if (this.type.equals("Blob"))
			return "import java.sql.Blob;";
		if (this.type.equals("HashMap"))
			return "import java.util.HashMap;";
		if (this.type.equals("BigDecimal"))
			return "import java.math.BigDecimal;";
		if (this.type.equals("Timestamp"))
			return "import java.sql.Timestamp;";
		throw new MalformedAttribute("contiene un tipo dichiarato type "+type+" non Riconosciuto");
		//return "TYPE NOT Maped";
	}
	
	
	private boolean isNativeType(String type){
		if (type.equals("String"))
			return true;
		else
		if (type.equals("int"))
			return true;
		else
		if (type.equals("float"))
			return true;
		else
		if (type.equals("long"))		
			return true;		
		else
		if (type.equals("HashMap"))		
			return true;
		else
		if (type.equals("double"))
			return true;	
		else
			return false;
			
		
	}
	
	
	private String getTraslType(String type,boolean isGetter) throws MalformedAttribute{
		if (type.equals("Collection") || type.equals("Object")){
			return "";
		}
		if (type.equals("Date")){
			if (isGetter)
				return "Traslator.dateToString";	
			else
				return "Traslator.stringToDate";	
		}else if (type.equals("Double")){
			if (isGetter)
				return "Traslator.doubleToString";	
			else
				return "Traslator.stringToDouble";	
		}else if (type.equals("Integer")){
			if (isGetter)
				return "Traslator.integerToString";	
			else
				return "Traslator.stringToInteger";	
		}else if (type.equals("Float")){
			if (isGetter)
				return "Traslator.floatToString";	
			else
				return "Traslator.stringToFloat";	
		}else if (type.equals("Long")){
			if (isGetter)
				return "Traslator.longToString";	
			else
				return "Traslator.stringToLong";	
		}else if (type.equals("BigDecimal")){
			if (isGetter)
				return "Traslator.bigDecimalToString";	
			else
				return "Traslator.stringToBigDecimal";	
		}else if (type.equals("Timestamp")){
			if (isGetter)
				return "Traslator.timestampToString";	
			else
				return "Traslator.stringToTimestamp";	
		}else
		
			throw new MalformedAttribute("contiene un tipo "+type+" per il quale non esiste un mapping di Traslator");
			
		
	}
	
	
	/**
	 * @return Restituisce il valore existInCommon.
	 */
	public boolean isExistInCommon() {
		return existInCommon;
	}
	/**
	 * @param existInCommon Il valore existInCommon da impostare.
	 */
	public void setExistInCommon(boolean existInCommon) {
		this.existInCommon = existInCommon;
	}
	/**
	 * @return Restituisce il valore propImport.
	 */
	public boolean isPropImport() {
		return propImport;
	}
	/**
	 * @return Restituisce il valore primitiveName.
	 */
	public String getPrimitiveName() {
		return primitiveName;
	}
	/**
	 * @param type Il valore type da impostare.
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return Restituisce il valore formRef.
	 */
	public boolean isFormRef() {
		return formRef;
	}
	/**
	 * @return Restituisce il valore attributeExtended.
	 */
	public boolean isAttributeExtended() {
		return attributeExtended;
	}
	/**
	 * @param attributeExtended Il valore attributeExtended da impostare.
	 */
	public void setAttributeExtended(boolean attributeExtended) {
		this.attributeExtended = attributeExtended;
	}
	
	
	
	
	/**
	 * @return Restituisce il valore fakeAttribute.
	 */
	public boolean isFakeAttribute() {
		return fakeAttribute;
	}
	/**
	 * @param fakeAttribute Il valore fakeAttribute da impostare.
	 */
	public void setFakeAttribute(boolean fakeAttribute) {
		this.fakeAttribute = fakeAttribute;
	}
	
	private String concatImport(String[] concateData, String name){
		
		String code="\n		if("+concateData[1]+"IForm==null)\n";
		code+="			"+concateData[1]+"IForm=\"00\";\n";
		code+="			else{\n";
		code+="			if((\"\").equalsIgnoreCase("+concateData[1]+"IForm))\n";
		code+="				"+concateData[1]+"IForm=\"00\";\n";
		code+="	 		if("+concateData[1]+"IForm.length()!=2)\n";
		code+="	  			"+concateData[1]+"IForm="+concateData[1]+"IForm.concat(\"0\");\n";
		code+="			}\n";
		code+=" 		if(!(\"0\").equalsIgnoreCase("+concateData[0]+"IForm) && "+concateData[0]+"IForm!=null)\n";
		code+=" 	   		this."+name+"="+concateData[0]+"IForm.concat(\",\").concat("+concateData[1]+"IForm);\n";
		code+="		System.out.println(\"CAMPO CONCATENATO=\"+this."+name+");\n";
		
		return code;
	}
	
	
	
	
	private String splitData(String name){
		
		String code="\n if (this."+splitData+"IForm.length() >2){\n";
		
		code+="java.util.StringTokenizer st = new java.util.StringTokenizer(this."+splitData+"IForm,\",\");\n";
		code+="String intPart=st.nextToken();\n";
		code+="String decPart=st.nextToken();\n";
		
		
		if (!intPartOf){			
			code+=" 	   		this."+name+"=decPart;\n";			
		}else{
			code+=" 	   		this."+name+"=intPart;\n";			
		}
		return code+="\n}";
	}
	/**
	 * @return Restituisce il valore defaulValue.
	 */
	public String getDefaulValue() {
		return defaulValue;
	}
}
