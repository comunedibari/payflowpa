/*
 * Creato il 6-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.db.pojo;



import it.nch.ebweb.generate.core.MalformedAttribute;
import it.nch.ebweb.generate.core.MalformedConfigurationException;
import it.nch.ebweb.generate.core.Syntax;
import it.nch.ebweb.generate.core.Writer;
import it.nch.ebweb.generate.db.util.Attribute;
import it.nch.ebweb.generate.db.util.BusinessORM;
import it.nch.ebweb.generate.db.util.BusinessObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author FelicettiA
 * 
 * TODO Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class CreatePojo {

	Writer writerI;
	Writer writerC;
	HashMap datiPojo = new HashMap();
	HashMap datiCommon = new HashMap();

	public CreatePojo(Writer writerI, Writer writerC) {
		this.writerI = writerI;
		this.writerC = writerC;
	}

	public void generaFormFile(BusinessORM bo, BusinessObject _bo)
			throws MalformedConfigurationException {

		/*
		 * if (bo.getCompositeId()!=null){ String s =
		 * "Id"+bo.getOrmClient().substring(0,4); Attribute a = new Attribute();
		 * a.setName(s); a.setType(s); bo.getAttributes().add(a); }
		 */

		String nameClass = bo.getOrm();

		/*
		 * if ( !nameClass.substring(0,3).equals("Jlt")) throw new
		 * MalformedConfigurationException("[FOLDER POJO] Attenzione il file
		 * "+bo.getOrmClient()+".properties definisce un nome Tabella\ne Pojo
		 * che non inizia con Jlt");
		 */

		if (_bo != null) {

			Iterator itC = _bo.getCommonAttributes().iterator();
			while (itC.hasNext()) {

				Attribute a = (Attribute) itC.next();
				//System.out.println("Attribute
				// Common==="+a.getPrimitiveName());
				datiCommon.put(a.getPrimitiveName(), a);
			}
		}

		Iterator itPO = bo.getAttributes().iterator();

		while (itPO.hasNext()) {

			Attribute a = (Attribute) itPO.next();
			//System.out.println("Attribute Common==="+a.getPrimitiveName());
			datiPojo.put(a.getPrimitiveName(), a);
		}

		this.generaInterface(bo);
		this.generaClass(bo);
	}

	private void generaInterface(BusinessORM bo)throws MalformedConfigurationException {

		writerI.println("/**");
		writerI.println("*");
		writerI.println("* Interfaccia generata");
		writerI.println("*");
		writerI.println("*/");
		writerI.println("");
		writerI.println("package " + bo.get_packageCommon() + ";");
		writerI.println("");
		writerI.println("import it.nch.fwk.fo.dto.business.Pojo;");
		
		if (bo.getExtendOrm() != null)
			writerI.println("import " + bo.getExtendOrm().getPackageClass()	+ "." + bo.getExtendOrm().getNameInterface() + "Pojo;");

		if (bo.isCommonReference()) {
			writerI.println("import " + bo.get_packageCommon() + "."+ bo.getOrmClient() + "Common;");
		}

		Iterator it = bo.getAttributes().iterator();

		while (it.hasNext()) {
			
			Attribute a = (Attribute) it.next();
			
			try {

				if (a.getImportOfType(true) != null) {

					if (a.isPropImport()) {

						if (this.datiCommon.containsKey(a.getPrimitiveName())) {
							writerI.println(a.getImportOfType(true)+ "Common;");
							//writerI.println(a.getImportOfType(true)+"Pojo;");
						} else
							writerI.println(a.getImportOfType(true) + "Pojo;");
					} else
						writerI.println(a.getImportOfType(true));
				}

			} catch (MalformedAttribute e) {
				// TODO Blocco catch generato automaticamente
				throw new MalformedConfigurationException("[FOLDER POJO] Attenzione il file " + bo.getOrmClient()+ ".properties " + e.getMessage());
			}
			
		}

		writerI.println("");

		/*
		 * if (bo.isCommonReference()){ if (bo.getExtendOrm()!=null)
		 * writerI.println("public interface "+bo.getOrmClient()+"Pojo extends
		 * "+"
		 * "+bo.getExtendOrm().getNameInterface()+"Pojo,"+bo.getOrmClient()+"Common,
		 * Pojo {"); else writerI.println("public interface
		 * "+bo.getOrmClient()+"Pojo extends "+bo.getOrmClient()+"Common, Pojo
		 * {"); } else
		 */

		if (bo.getExtendOrm() != null)
			writerI.println("public interface " + bo.getOrmClient()+ "Pojo extends " + bo.getExtendOrm().getNameInterface()+ "Pojo, Pojo {");
		else
			writerI.println("public interface " + bo.getOrmClient()+ "Pojo extends Pojo{");

		writerI.println("");

		Iterator _it = bo.getAttributes().iterator();
		while (_it.hasNext()) {

			Attribute a = (Attribute) _it.next();

			if (this.datiCommon.containsKey(a.getPrimitiveName())) {
				writerI.println("    " + a.getIGetterMethod("Common"));
				writerI.println("    " + a.getISetterMethod("Common"));
			} else {
				writerI.println("    " + a.getIGetterMethod("Pojo"));
				writerI.println("    " + a.getISetterMethod("Pojo"));

			}

		}
		
		
		if (bo.isFakedObj()) {
			
			writerI.println("");
			writerI.println("    /**");
			writerI.println("     *");
			writerI.println("     * Metodo FAKE oer restituire COPIA!!!");
			writerI.println("     *");
			writerI.println("     **/");
			writerI.println("      public void populateFromNative("+bo.getRefFakeName()+" nativePojo);");		
			writerI.println("");
		}
			

		if (bo.getCompositeId() != null) {

			//String sDich = "id"+
			// Syntax.firstUpperCase(bo.getOrmClient().substring(0,4));
			//String s = "Id"+ bo.getOrmClient().substring(0,4);

			writerI.println("		/**");
			writerI.println("		  *");
			writerI.println("		  * Metodi che si appoggiano sul Composite ID");
			writerI.println("		  *");
			writerI.println("		  *");
			writerI.println("		  */");
			_it = bo.getCompositeId().iterator();

			while (_it.hasNext()) {

				Attribute a = (Attribute) _it.next();
				writerI.println("    " + a.getIGetterMethod("Pojo"));
				writerI.println("    " + a.getISetterMethod("Pojo"));

			}
		}
		
	
			

			
			

		writerI.println("");
		writerI.println("}");

	}

	private void generaClass(BusinessORM bo)throws MalformedConfigurationException {

		writerC.println("/**");
		writerC.println("*");
		writerC.println("* Classe generata");
		writerC.println("*");
		writerC.println("*/");
		writerC.println("");
		writerC.println("package " + bo.get_packageCommon() + ";");
		writerC.println("");

		writerC.println("");

		if (bo.isCommonReference()) {
			//writerC.println("import org.springframework.beans.BeansException;");
			writerC.println("import org.springframework.beans.factory.BeanFactory;");
			writerC.println("import org.springframework.beans.factory.access.BeanFactoryLocator;");
			writerC.println("import org.springframework.beans.factory.access.BeanFactoryReference;");
			writerC.println("import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;");
			writerC.println("");

			writerC.println("import it.nch.fwk.fo.common.CommonBusinessObject;");			
			writerC.println("import it.nch.fwk.fo.common.Traslator;");
			writerC.println("import " + bo.get_packageCommon() + "."
					+ bo.getOrmClient() + "Form;");
		}
		
		writerC.println("import it.nch.fwk.fo.dto.*;");
		writerC.println("import it.nch.fwk.fo.dto.business.PojoImpl;");
		
		//aggiunte per la gestione del metodo toString
		/*
		writerC.println("");
		writerC.println("import it.nch.fwk.fo.util.Tracer;");
		writerC.println("import java.beans.BeanInfo;");
		writerC.println("import java.beans.Introspector;");
		writerC.println("import java.beans.PropertyDescriptor;");
		writerC.println("import java.lang.reflect.Method;");
		writerC.println("");
		*/
		if (bo.getCompositeId() != null)
			writerC.println("import java.io.Serializable;");

		Iterator it = bo.getAttributes().iterator();

		String costructor = "";

		while (it.hasNext()) {

			Attribute a = (Attribute) it.next();
			//datiPojo.put(a.getPrimitiveName(),a);

			try {

				/*
				 * if (this.datiCommon.containsKey(a.getPrimitiveName())){
				 * writerC.println(a.getImportOfType(true)+"Common"); }
				 */
				costructor += "," + a.getType() + " " + a.getName();

				if (a.isAttributeExtended()) {

					System.out.println("ATTRIBUTO: " + a.getName()+ " E' EREDITATO non importo dichiarazione");

				} else {

					if (a.getImportOfType(true) != null)
						if (a.isPropImport()) {
							if (this.datiCommon.containsKey(a.getPrimitiveName())) {
								writerC.println(a.getImportOfType(true)	+ "Common;");
								writerC.println(a.getImportOfType(true)	+ "Pojo;");
							} else
								writerC.println(a.getImportOfType(true) + "Pojo;");
						} else
							writerC.println(a.getImportOfType(true));

					if (a.getType().equals("Collection")) {
						writerC.println("import java.util.Iterator;");
						writerC.println("import java.util.Vector;");
					}

				}

			} catch (MalformedAttribute e) {
				// TODO Blocco catch generato automaticamente
				throw new MalformedConfigurationException("[FOLDER POJO] Attenzione il file " + bo.getOrmClient()+ ".properties " + e.getMessage());
			}
		}

		writerC.println("");

		if (bo.getExtendOrm() != null) {

			if (bo.isCommonReference())
				writerC.println("public class " + bo.getOrm()+ bo.getExtendOrm().getStringCodeImport()+ " implements " + bo.getOrmClient() + "Common, "	+ bo.getOrmClient() + "Pojo {");
			else
				writerC.println("public class " + bo.getOrm()+ bo.getExtendOrm().getStringCodeImport()+ " implements " + bo.getOrmClient() + "Pojo {");
		} else {

			if (bo.isCommonReference())
				writerC.println("public class " + bo.getOrm()+ " extends PojoImpl implements " + bo.getOrmClient()+ "Common, " + bo.getOrmClient() + "Pojo {");
			else
				writerC.println("public class " + bo.getOrm()+ " extends PojoImpl implements " + bo.getOrmClient()+ "Pojo {");

		}

		writerC.println("");

		Iterator _it = bo.getAttributes().iterator();
		while (_it.hasNext()) {

			Attribute a = (Attribute) _it.next();

			if (a.isAttributeExtended()) {

				System.out.println("ATTRIBUTO: " + a.getName()+ " E' EREDITATO non definisco attributo");

			} else {

				if (a.isPropImport()) {
					if (this.datiCommon.containsKey(a.getPrimitiveName()))
						writerC.println("    private "	+ a.getTypeForAttribute("Common") + " "+ a.getName() + ";");
					else
						writerC.println("    private "	+ a.getTypeForAttribute("Pojo") + " "+ a.getName() + ";");
					//writerC.println(" private
					// "+a.getTypeForAttribute("Pojo")+" "+a.getName()+";");
				} else {
					
					/**
					 * 
					 * 
					 * AGGIUNGERE IL DEFAUL VALUE
					 * 
					 * 
					 * 
					 */	
					
					if (a.getDefaulValue()!=null){
						writerC.println("    private " + a.getType() + " "+ a.getName() + "="+a.getDefaulValue()+";");			
					}else{			
						writerC.println("    private " + a.getType() + " "+ a.getName() + ";");			
					}
					
				}
			}

		}
		writerC.println("");

		if (bo.isCommonReference()) {

			writerC.println("    private transient BeanFactoryLocator bfl;");
			writerC.println("    private transient BeanFactoryReference bfr;");
			writerC.println("    private transient BeanFactory bf;");

		}

		writerC.println("");

		if (bo.isVista()) {

			writerC.println("	public " + bo.getOrm() + "("+ costructor.substring(1) + "){");
			writerC.println("		super();");

			it = bo.getAttributes().iterator();
			
			while (it.hasNext()) {
				Attribute a = (Attribute) it.next();
				writerC.println("		this." + a.getName() + "=" + a.getName()+ ";");
			}
			writerC.println("	}");

		}
		
		
		if (bo.getOtherCostructor()!=null) {
			
			
			StringTokenizer otherCostr=new StringTokenizer(bo.getOtherCostructor(),",");
			Collection itCostr = new Vector();
			
			String cr="";
			
			
			while (otherCostr.hasMoreTokens()){
				
				String app = otherCostr.nextToken();
				
				Iterator att =  bo.getAttributes().iterator();
				boolean found =false;
				String type=null;
				
				
				while (att.hasNext()) {
					Attribute a = (Attribute) att.next();
					
					//System.err.println("ATTR ="+a.getName());
					//System.err.println("app  ="+app);
					
					if (a.getName().trim().equalsIgnoreCase(app.trim())){
						found = true;
						type = a.getType();
					}
					
				}
				if (!found)
					throw new MalformedConfigurationException ("Attenzione!!!! L'other Costructor specifica un valore:"+app+" non presente fra gli attributi dichiarati NON E' AMMESSO!!! ");
				
				cr+=type+" "+app+",";
				itCostr.add(app);
			}
			
			
			
			writerC.println("	public " + bo.getOrm() + "("+ cr.substring(0,cr.length()-1) + "){");
			writerC.println("		super();");

			Iterator itCR = itCostr.iterator();
			
			while (itCR.hasNext()) {
				String aCr = (String) itCR.next();
				writerC.println("		this." + aCr + "=" + aCr+ ";");
			}
			
			writerC.println("	}");
			
			
		}

		if (bo.isVistaWithCostrEmpty()) {

			writerC.println("	public " + bo.getOrm() + "("+ costructor.substring(1) + "){");
			writerC.println("		super();");

			it = bo.getAttributes().iterator();
			while (it.hasNext()) {
				Attribute a = (Attribute) it.next();
				writerC.println("		this." + a.getName() + "=" + a.getName()+ ";");
			}
			writerC.println("	}");

			writerC.println("");

			writerC.println("	public " + bo.getOrm() + "( ){");
			writerC.println("		");
			writerC.println("	}");

		}

		if (bo.getCompositeId() != null) {

			String s = "Id" + bo.getOrmClient().substring(0, 4);
			String sDich = "id"	+ Syntax.firstUpperCase(bo.getOrmClient().substring(0, 4));

			Iterator _idComp = bo.getCompositeId().iterator();

			writerC.println("    private " + s + " " + sDich + " = new " + s+ "();");

			writerC.println(" ");
			writerC.println("    public static class " + s+ " implements Serializable {");
			writerC.println(" ");

			String costruct = "";
			while (_idComp.hasNext()) {

				Attribute a = (Attribute) _idComp.next();
				costruct += "," + a.getType() + " " + a.getName();

				writerC.println("	     private " + a.getType() + " "+ a.getName() + ";");
			}
			writerC.println(" ");
			writerC.println("    public " + s + "(" + costruct.substring(1)+ ") {");

			writerC.println(" ");
			_idComp = bo.getCompositeId().iterator();
			while (_idComp.hasNext()) {

				Attribute a = (Attribute) _idComp.next();
				costruct = "," + a.getType() + " " + a.getName();

				writerC.println("     	this. " + a.getName() + "="	+ a.getName() + ";");
			}
			writerC.println("     }");
			writerC.println("    public " + s + "() {}");

			writerC.println(" ");
			_idComp = bo.getCompositeId().iterator();

			while (_idComp.hasNext()) {

				Attribute a = (Attribute) _idComp.next();

				writerC.println("    " + a.getCGetterMethod("Pojo", false));
				writerC.println("    " + a.getCSetterMethod("Pojo", false));

			}

			writerC.println("    }");

			writerC.println("	/**");
			writerC.println("     *");
			writerC.println("     *FINE classe Innestata compositeID");
			writerC.println("     *");
			writerC.println("    */");

			String method1 = "public " + s + " get"	+ Syntax.onlyFirstUpperCase(sDich) + "() {";
			method1 += "\n 		return this." + sDich + ";";
			method1 += "\n 	} \n";

			String method2 = "public void set"+ Syntax.onlyFirstUpperCase(sDich) + "(" + s + " " + sDich+ "){";
			method2 += "\n 		this." + sDich + "=" + sDich + ";";
			method2 += "\n 	} \n";

			writerC.println("    " + method1);
			writerC.println("    " + method2);

			_idComp = bo.getCompositeId().iterator();
			while (_idComp.hasNext()) {

				Attribute a = (Attribute) _idComp.next();
				writerC.println("    " + a.getCompositeIdGetterMethod(sDich));
				writerC.println("    " + a.getCompositeIdSetterMethod(sDich));

			}
		}

		writerC.println("");

		_it = bo.getAttributes().iterator();
		while (_it.hasNext()) {

			Attribute a = (Attribute) _it.next();
			if ((this.datiCommon.containsKey(a.getPrimitiveName()))
					&& a.isPropImport()) {
				writerC.println("    " + a.getCGetterMethod("Pojo", true));
				writerC.println("    " + a.getCSetterMethod("Pojo", true));
			} else {
				writerC.println("    " + a.getCGetterMethod("Pojo", false));
				writerC.println("    " + a.getCSetterMethod("Pojo", false));

			}

		}
		writerC.println("");
		writerC.println("");
		writerC.println("");

		writerC.println("    public void show() {");
		writerC
				.println("       System.out.println(\"Class=\"+this.getClass());");
		Iterator at = bo.getAttributes().iterator();

		while (at.hasNext()) {

			Attribute a = (Attribute) at.next();
			writerC.println("       " + a.getPrintShow());
		}

		writerC.println("    }");

		writerC.println("");
		writerC.println("");
		
		if (bo.isCommonReference()) {

			//Iterator _at = bo.getAttributes().iterator();
			Iterator _at = this.datiCommon.values().iterator();

			writerC.println("    /**");
			writerC.println("     *");
			writerC.println("     * Method Per gestione Tipi non nativi");
			writerC.println("     *");
			writerC.println("     **/");

			while (_at.hasNext()) {

				Attribute a = (Attribute) _at.next();
				Attribute aP = (Attribute) this.datiPojo.get(a
						.getPrimitiveName());

				/*
				 * System.out.println("******************************************");
				 * System.out.println("a.name="+a.getName());
				 * System.out.println("aP.name="+aP.getName());
				 * 
				 * System.out.println("a.isexte="+a.isAttributeExtended());
				 * System.out.println("aP.isexte="+aP.isAttributeExtended());
				 * System.out.println("******************************************");
				 */

				if (bo.getExtendOrm() == null) {

					if (aP == null) {

						if (a.getName().equals("idIForm")) {

							writerC.println("");
							writerC.println("    public String getIdIForm() {");
							writerC.println("    	return Traslator.longToString(this.getId());");
							writerC.println("    } ");
							writerC.println("");
							writerC.println("     public void setIdIForm(String idIForm){");
							writerC.println("    		System.err.println(\"***************************************\");");
							writerC.println("    		System.err.println(\"[ERRORE] SET ID DA FORM NON ACCETTABILE\");");
							writerC.println("    		System.err.println(\"***************************************\");");
							writerC.println("       }");
							writerC.println("");

						} else
							throw new MalformedConfigurationException(
									"Attenzione!!!! attributo: "+ a.getPrimitiveName()
											+ " presente \nnel file "
											+ bo.getOrmClient()
											+ ".properties del package Common non ha il corrispettivo\ncampo nel properties del POJO");
					} else {

						try {

							if (aP.isAttributeExtended()) {

								a.setAttributeExtended(true);
							}

							if (a.isFormRef()) {

								writerC.println("    "+ a.getIFormGetterMethod(aP.getType(),"Common"));
								writerC.println("    "+ a.getIFormSetterMethod(aP.getType(),"Common"));

							} else {

								writerC.println("    "+ a.getIFormGetterMethod(aP.getType(),"Pojo"));
								writerC.println("    "+ a.getIFormSetterMethod(aP.getType(),"Pojo"));
							}

						} catch (MalformedAttribute e) {
							// TODO Blocco catch generato automaticamente
							throw new MalformedConfigurationException("[FOLDER POJO] Attenzione il file "
											+ bo.getOrmClient()
											+ ".properties " + e.getMessage());

						}

					}

				} else {

					if (aP == null) {

						/*
						 * controllare che L'attributo � presente nel file
						 * ereditato altrimenti sollevare eccezione!!!!
						 * 
						 * 
						 *  
						 */

						if (aP == null) {

							if (a.getName().equals("idIForm")) {

								writerC.println("");
								writerC.println("    public String getIdIForm() {");
								writerC.println("    	return Traslator.longToString(this.getId());");
								writerC.println("    } ");
								writerC.println("");
								writerC.println("     public void setIdIForm(String idIForm){");
								writerC.println("    		System.err.println(\"***************************************\");");
								writerC.println("    		System.err.println(\"[ERRORE] SET ID DA FORM NON ACCETTABILE\");");
								writerC.println("    		System.err.println(\"***************************************\");");
								writerC.println("       }");
								writerC.println("");

							} else
								throw new MalformedConfigurationException("Attenzione!!!! L' attributo: "
												+ a.getPrimitiveName()
												+ " del file "
												+ bo.getOrmClient()
												+ ".properties  probabilmente e' nella super classe MA il generatore ancora non supporta questa modalit� FATTELO A MANO E LAVORA!!! :)))");
						} else {

							try {

								if (aP.isAttributeExtended()) {
									a.setAttributeExtended(true);
								}

								writerC.println("    "+ a.getIFromExtendsGetterMethod(a.getType(), "Common"));
								writerC.println("    "+ a.getIFromExtendsSetterMethod(a.getType(), "Common"));

							} catch (MalformedAttribute e) {
								// TODO Blocco catch generato automaticamente
								e.printStackTrace();
							}

						}

					} else {

						try {

							/*
							 * 
							 * if (a.isFormRef()){ writerC.println("
							 * "+a.getIFormGetterMethod(aP.getType(),"Common"));
							 * writerC.println("
							 * "+a.getIFormSetterMethod(aP.getType(),"Common"));
							 * }else{ writerC.println("
							 * "+a.getIFormGetterMethod(aP.getType(),"Pojo"));
							 * writerC.println("
							 * "+a.getIFormSetterMethod(aP.getType(),"Pojo")); }
							 */

							if (aP.isAttributeExtended()) {

								a.setAttributeExtended(true);
							}

							if (a.isFormRef()) {
								writerC.println("    "+ a.getIFormGetterMethod(aP.getType(),"Common"));
								writerC.println("    "+ a.getIFormSetterMethod(aP.getType(),"Common"));
							} else {
								writerC.println("    "+ a.getIFormGetterMethod(aP.getType(),"Pojo"));
								writerC.println("    "+ a.getIFormSetterMethod(aP.getType(),"Pojo"));
							}

						} catch (MalformedAttribute e) {
							// TODO Blocco catch generato automaticamente
							throw new MalformedConfigurationException("[FOLDER POJO] Attenzione il file "
											+ bo.getOrmClient()
											+ ".properties " + e.getMessage());

						}
					}
				}
			}

			writerC.println("    /**");
			writerC.println("     *");
			writerC.println("     *COPY Method Pojo Vs Form");
			writerC.println("     *");
			writerC.println("     **/");
			writerC.println("");
			writerC.println("      public CommonBusinessObject copy(){");
			writerC.println("");
			writerC.println("");

			/**
			 * 
			 * 
			 * 
			 * ISTANZA TRAMITE SPRING
			 */

			writerC.println("         bfl = SingletonBeanFactoryLocator.getInstance(\"server-beanRefFactory.xml\");");
			writerC.println("         bfr = bfl.useBeanFactory(\"it.nch.orm\");");
			writerC.println("         bf=bfr.getFactory();");
			/*
			 * writerC.println(" BeanFactoryLocator bfl =
			 * SingletonBeanFactoryLocator.getInstance();"); writerC.println("
			 * BeanFactoryReference bfr =
			 * bfl.useBeanFactory(\"it.nch.orm\");");
			 * writerC.println(" BeanFactory bf=bfr.getFactory();");
			 */
			writerC.println("         " + bo.getOrmClient() + "Form _form =("+ bo.getOrmClient() + "Form) bf.getBean(\""+ Syntax.onlyFirstUpperCase(bo.getOrmClient())+ "Form\");");

			/**
			 * 
			 * 
			 * 
			 * ISTANZA DISCHIARATIVA
			 */
			//writerC.println(" "+bo.getOrmClient()+"Form _form
			// =("+bo.getOrmClient()+"Form) new
			// "+bo.getOrmClient()+"FormImpl();");
			writerC.println("");
			writerC.println("          _form.setNativePojo(this);");
			writerC.println("");
			writerC.println("         " + bo.getOrmClient()+ "Common _pojo = this;");
			writerC.println("");

			_at = bo.getCommonAttributes().iterator();

			while (_at.hasNext()) {

				Attribute a = (Attribute) _at.next();
				if (a.getType().equals("Collection")) {
					writerC.println("");
					writerC.println(bo.getCopyPojoMethod(a.getName()));
					writerC.println("");
				} else {

					//String fupper = Syntax.onlyFirstUpperCase(a.getName());
					//writerC.println("
					// _form.set"+fupper+"(_pojo.get"+fupper+"());");

					if (a.isFormRef()) {

						String fupper = Syntax.onlyFirstUpperCase(a.getName());

						writerC.println("         // Oggetto innestato copio in modo ricorso relativamente");
						writerC.println("         // alla definizione delle dipendenze del properties Common");
						writerC.println("  		  if (_pojo.get" + fupper+ "()!=null && !getFlagDoNotCopy()) // SADPA - This patch was made to get rid of infinite recursions in case of bidirectional links (one-to-many-many-to-one)");
						writerC.println("        	 _form.set" + fupper + "(("	+ a.getType() + ")_pojo.get" + fupper+ "().copy());");
						//writerC.println("  		  else System.err.println(\"ATTENZIONE nel pojo l'oggetto innestato � null non lo copio!!!\");");
					} else {
						String fupper = Syntax.onlyFirstUpperCase(a.getName());
						writerC.println("         _form.set" + fupper+ "(_pojo.get" + fupper + "());");
					}
				}

			}
			writerC.println("");
			writerC.println("         return _form;");
			writerC.println("	  }");

		} else {

			writerC.println("    /**");
			writerC.println("     *");
			writerC.println("     * NON NECESSITA del Metodo COPY!!!");
			writerC.println("     *");
			writerC.println("     **/");

		}
		
		
		writerC.println("");
		writerC.println("	  public DTO incapsulateBO() {");
		// TODO Stub di metodo generato automaticamente
		if (bo.isCommonReference())			
			writerC.println("	  	return new DTOImpl((CommonBusinessObject)this);");
		else
			writerC.println("	  	return new DTOImpl(this);");
		
		writerC.println("	  }");
		writerC.println("");
	

		
		
		
		
		

		if (bo.isFakedObj()) {
			
			

			writerC.println("    /**");
			writerC.println("     *");
			writerC.println("     * Metodo FAKE oer restituire COPIA!!!");
			writerC.println("     *");
			writerC.println("     **/");

			writerC.println("      public void populateFromNative("+bo.getRefFakeName()+" nativePojo) {");
			writerC.println("");
			writerC.println("");

			Iterator _cat = bo.getAttributes().iterator();

			while (_cat.hasNext()) {
				
				Attribute a = (Attribute) _cat.next();				
				if (a.isFakeAttribute()){
	
						String fupper = Syntax.onlyFirstUpperCase(a.getName());
						writerC.println("         this.set" + fupper+ "(nativePojo.get" + fupper + "());");
					
				}	

			}
			writerC.println("         this.setId(nativePojo.getId());");
			writerC.println("");		
			writerC.println("	  }");

		}


	

		/**
		 * 
		 * 
		 * 
		 * 
		 * metodo clone!!!!!!!!!!! x pojo
		 */

		if (bo.isClonable()) {

			writerC.println("    /**");
			writerC.println("     *");
			writerC.println("     * Metodo Clone richiesto!!!");
			writerC.println("     *");
			writerC.println("     **/");

			writerC.println("      public Object clone() {");
			writerC.println("");
			writerC.println("");
			writerC.println("");
			writerC.println("         " + bo.getOrm()+ " _pojoCurrent = this;");
			writerC.println("         " + bo.getOrm() + " _pojo = new "+ bo.getOrm() + "();");
			writerC.println("");

			Iterator _cat = bo.getAttributes().iterator();

			while (_cat.hasNext()) {

				Attribute a = (Attribute) _cat.next();
				if (a.getType().equals("Collection")) {
					writerC.println("         /**");
					writerC.println("          *");
					writerC.println("          *Trovata Collection ["+ a.getName() + "] NON applico clone annidato!");
					writerC.println("          *");
					writerC.println("          */");
					//writerC.println(bo.getClonePojoMethod(a.getName()));
					writerC.println("");
				} else {
					String fupper = Syntax.onlyFirstUpperCase(a.getName());
					writerC.println("         _pojo.set" + fupper+ "(_pojoCurrent.get" + fupper + "());");
				}

			}
			writerC.println("         _pojo.setId(_pojoCurrent.getId());");
			writerC.println("");
			writerC.println("         return _pojo;");
			writerC.println("	  }");

		}
		
		//************************** INIZIO METODO CHE RITORNA LA RAPPRESENTAZIONE IN STRINGA DELL'OGGETTO *****************************
		//writerC.println("");
		//writerC.println("    /**");
		//writerC.println("     * ridefinizione del metodo toString");
		//writerC.println("     **/");
		//writerC.println("    public String toString() { ");
		//writerC.println("      StringBuffer buf = new StringBuffer(); ");
		//writerC.println("        try { ");
		//writerC.println("          BeanInfo binfo = Introspector.getBeanInfo(this.getClass()); ");
		//writerC.println("          PropertyDescriptor[] properties = binfo.getPropertyDescriptors(); ");
		//writerC.println("          if (properties != null) {");
		//writerC.println("             for (int i = 0; i < properties.length; i++) { ");
		//writerC.println("               Method readMethod = properties[i].getReadMethod(); ");
		//writerC.println("               if (readMethod != null) {");
		//writerC.println("                  buf.append(properties[i].getName()); ");
		//writerC.println("                  buf.append(\" = \"); ");
		//writerC.println("                  Object obj = readMethod.invoke(this, null); ");
		//writerC.println("                  if (obj != null) { ");
		//writerC.println("                     buf.append(obj.toString()); ");
		//writerC.println("                  } else { ");
		//writerC.println("                     buf.append(\"<empty>\"); ");
		//writerC.println("                  } ");
		//writerC.println("                  buf.append(\"\\n\"); ");
		//writerC.println("               } ");
		//writerC.println("             } ");
		//writerC.println("          } ");
		//writerC.println("        } catch (Exception e) { ");
		//writerC.println("      } ");
		//writerC.println("     return buf.toString(); ");
		//writerC.println("    } ");
		writerC.println("");
		writerC.println("");
		//************************** FINE METODO CHE RITORNA LA RAPPRESENTAZIONE IN STRINGA DELL'OGGETTO *****************************
		
		writerC.println("}");
	}

}
