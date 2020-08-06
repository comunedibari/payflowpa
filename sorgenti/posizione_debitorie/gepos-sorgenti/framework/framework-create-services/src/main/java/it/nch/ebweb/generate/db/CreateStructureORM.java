/*
 * Creato il 5-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.db;

import it.nch.ebweb.generate.core.CreatorCostanst;
import it.nch.ebweb.generate.core.MalformedAttribute;
import it.nch.ebweb.generate.core.MalformedConfigurationException;
import it.nch.ebweb.generate.core.Writer;
import it.nch.ebweb.generate.db.common.CreateCommon;
import it.nch.ebweb.generate.db.form.CreateForm;
import it.nch.ebweb.generate.db.pojo.CreatePojo;
import it.nch.ebweb.generate.db.util.Attribute;
import it.nch.ebweb.generate.db.util.BusinessForm;
import it.nch.ebweb.generate.db.util.BusinessORM;
import it.nch.ebweb.generate.db.util.BusinessObject;
import it.nch.ebweb.generate.db.util.OrmEreditato;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
public class CreateStructureORM {

	File fileFounded = null;

	String path_common_properties = CreatorCostanst.FOLDER_PROPERTIES	+ "common/";
	String path_form_properties = CreatorCostanst.FOLDER_PROPERTIES	+ "form/";
	String path_orm_properties = CreatorCostanst.FOLDER_PROPERTIES	+ "orm/";
	

	private BusinessObject bo = null;
	private BusinessORM bOrm = null;
	private BusinessForm bof = null;
	private HashMap commons = new HashMap();
	
	private PrintStream cp;
	private PrintStream cf;

	private Vector fileCommonProcessed = new Vector();

	public void generateAllBusinessObject()	throws MalformedConfigurationException {
		try {
			
			
			processFiles();
			
			cp.close();
			cf.close();
		} catch (IOException e) {
			// TODO Blocco catch generato automaticamente
			e.printStackTrace();
		}
	}

	private void processFiles() throws IOException, MalformedConfigurationException {

		//System.out.println("PATH =" + path_common);
		
		
		
		/***
		 * 
		 * 
		 * 
		 * Configuration Client
		 * 
		 */
		Writer writerConfSpringF;
		Writer writerConfSpringP;
		/***
		 * 
		 * 
		 * 
		 * Configuration Server
		 * 
		 */
		Writer writerSConfSpringF;
		Writer writerSConfSpringP;


		String pathCS = CreatorCostanst.FOLDER_CLIENT_CONFIGURATION;
		String pathConfS = CreatorCostanst.FOLDER_CONFIGURATION;
		
		File _pathCS = new File(pathCS);
		File _pathConfS = new File(pathConfS);
		
		if (!_pathCS.exists())
			_pathCS.mkdirs();
		if (!_pathConfS.exists())
			_pathConfS.mkdirs();

		
	
		File springConfiguationF = new File(pathCS + "businessDelegate_Forms.xml");		
		cf = new PrintStream(new FileOutputStream(springConfiguationF));
		writerConfSpringF = new Writer(System.out, cf);
		
		File springSConfiguationF = new File(pathConfS + "businessDelegate_Forms.xml");		
		cf = new PrintStream(new FileOutputStream(springSConfiguationF));
		writerSConfSpringF = new Writer(System.out, cf);
		
		File springConfiguationP = new File(pathCS + "businessDelegate_Pojos.xml");		
		cp = new PrintStream(new FileOutputStream(springConfiguationP));
		writerConfSpringP = new Writer(System.out, cp);
		
		File springSConfiguationP = new File(pathConfS + "businessDelegate_Pojos.xml");		
		cp = new PrintStream(new FileOutputStream(springSConfiguationP));
		writerSConfSpringP = new Writer(System.out, cp);


		
		writerConfSpringF.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		writerConfSpringF.println("<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">");
		writerConfSpringF.println("<!--");
		writerConfSpringF.println("   - Forms context definition for \"Business Delegate\" and \"eB.Web\".");
		writerConfSpringF.println("-->");	
		writerConfSpringF.println("");	
		writerConfSpringF.println("<beans>");
		writerConfSpringF.println("");	
		
		writerConfSpringP.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		writerConfSpringP.println("<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">");
		writerConfSpringP.println("<!--");
		writerConfSpringP.println("   - Forms context definition for \"Business Delegate\" and \"Back-End\".");
		writerConfSpringP.println("-->");	
		writerConfSpringP.println("");	
		writerConfSpringP.println("<beans>");
		writerConfSpringP.println("");	
		
		
		writerSConfSpringF.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		writerSConfSpringF.println("<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">");
		writerSConfSpringF.println("<!--");
		writerSConfSpringF.println("   - Forms context definition for \"Business Delegate\" and \"eB.Web\".");
		writerSConfSpringF.println("-->");	
		writerSConfSpringF.println("");	
		writerSConfSpringF.println("<beans>");
		writerSConfSpringF.println("");	
		
		writerSConfSpringP.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		writerSConfSpringP.println("<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">");
		writerSConfSpringP.println("<!--");
		writerSConfSpringP.println("   - Forms context definition for \"Business Delegate\" and \"Back-End\".");
		writerSConfSpringP.println("-->");	
		writerSConfSpringP.println("");	
		writerSConfSpringP.println("<beans>");
		writerSConfSpringP.println("");	
		
		
		
		File _commons = new File(path_common_properties);
		File[] files = _commons.listFiles();

		for (int i = 0; i < files.length; i++) {

			File file = (File) files[i];

			System.out.println("Process Common/Form =>" + file.getPath());

			StringTokenizer st = new StringTokenizer(file.getName(), ".");
			String name = st.nextToken();

			if (st.countTokens() > 0) {

				name = name + ".properties";
				generateCommon(file, new File(path_form_properties + name));
				fileCommonProcessed.add(name);
				generateOrm(new File(path_orm_properties + name), true,true);
				
				writerConfSpringF.println("	<bean id=\""+this.bo.getNameService()+"Form\" class=\""+this.bo.get_packageCommon()+"."+this.bo.getNameService()+"FormImpl\" singleton=\"false\">");
				writerConfSpringF.println("	</bean>");
				
				writerConfSpringP.println("	<bean id=\""+this.bOrm.getOrmClient()+"\" class=\""+this.bOrm.get_packageCommon()+"."+this.bOrm.getOrm()+"\" singleton=\"false\">");
				writerConfSpringP.println("	</bean>");  
				
				writerSConfSpringF.println("	<bean id=\""+this.bo.getNameService()+"Form\" class=\""+this.bo.get_packageCommon()+"."+this.bo.getNameService()+"FormImpl\" singleton=\"false\">");
				writerSConfSpringF.println("	</bean>");
				
				writerSConfSpringP.println("	<bean id=\""+this.bOrm.getOrmClient()+"\" class=\""+this.bOrm.get_packageCommon()+"."+this.bOrm.getOrm()+"\" singleton=\"false\">");
				writerSConfSpringP.println("	</bean>");  
			
			}

		}

		/*
		 * 
		 * Fine generazione congiunta
		 *  
		 */

		/*
		 * 
		 * generazione extra orm
		 *  
		 */

		this.commons.clear();

		File _orms = new File(path_orm_properties);
		files = _orms.listFiles();

		for (int i = 0; i < files.length; i++) {

			File file = (File) files[i];
			System.out.println("Process Orm =>" + file.getPath());

			if (fileCommonProcessed.contains(file.getName())) {
				System.out.println("File " + file.getName()	+ " gia generato congiuntamente a Common");
			} else {

				StringTokenizer _st = new StringTokenizer(file.getName(), ".");
				_st.nextToken();

				if (_st.countTokens() > 0){
					generateOrm(file, false,false);							
					
					writerConfSpringP.println("	<bean id=\""+this.bOrm.getOrmClient()+"\" class=\""+this.bOrm.get_packageCommon()+"."+this.bOrm.getOrm()+"\" singleton=\"false\">");					
					writerSConfSpringP.println("	<bean id=\""+this.bOrm.getOrmClient()+"\" class=\""+this.bOrm.get_packageCommon()+"."+this.bOrm.getOrm()+"\" singleton=\"false\">");
				
					
					if (this.bOrm.isVista()){
						writerConfSpringP.println("		<constructor-arg>");
						writerConfSpringP.println("		 	<list>");	
						
						writerSConfSpringP.println("		<constructor-arg>");
						writerSConfSpringP.println("		 	<list>");			
					
						
						Iterator itPojo = this.bOrm.getAttributes().iterator();
						while (itPojo.hasNext()){
							Attribute aa = (Attribute)itPojo.next();
 							writerConfSpringP.println("				 <value>"+aa.getName()+"</value>");		
 							writerSConfSpringP.println("				 <value>"+aa.getName()+"</value>");	
						}
						
						writerConfSpringP.println("		 	</list>");
						writerConfSpringP.println("		</constructor-arg>");
						
						writerSConfSpringP.println("		 	</list>");
						writerSConfSpringP.println("		</constructor-arg>");
				
					}
					
					writerConfSpringP.println("	</bean>");  
					writerSConfSpringP.println("	</bean>");  
					
					

				}
			}
		}

		/*
		 * 
		 * generazione extra form
		 *  
		 */

		this.commons.clear();
		this.bo = new BusinessObject();
		this.bOrm =  new BusinessORM();

		File _forms = new File(path_form_properties);
		files = _forms.listFiles();

		for (int i = 0; i < files.length; i++) {

			File file = (File) files[i];
			System.out.println("Process Form =>" + file.getPath());
			

			if (fileCommonProcessed.contains(file.getName())) {
				System.out.println("File " + file.getName()	+ " gia generato congiuntamente a Common");
			} else {

				StringTokenizer _st = new StringTokenizer(file.getName(), ".");
				_st.nextToken();

				if (_st.countTokens() > 0){
					generateForm(file,false);
					
					writerConfSpringF.println("	<bean id=\""+this.bof.getNameService()+"Form\" class=\""+this.bof.get_packageCommon()+"."+this.bof.getNameService()+"FormImpl\" singleton=\"false\">");
					writerConfSpringF.println("	</bean>");  
					
					
					writerSConfSpringF.println("	<bean id=\""+this.bof.getNameService()+"Form\" class=\""+this.bof.get_packageCommon()+"."+this.bof.getNameService()+"FormImpl\" singleton=\"false\">");
					writerSConfSpringF.println("	</bean>");  


				}
			}
		}
		
		
		writerConfSpringF.println("");	
		writerConfSpringF.println("</beans>");	
		
		writerConfSpringP.println("");	
		writerConfSpringP.println("</beans>");	
		
		
		writerSConfSpringF.println("");	
		writerSConfSpringF.println("</beans>");	
		
		writerSConfSpringP.println("");	
		writerSConfSpringP.println("</beans>");	
	}

	private void generateOrm(File orm, boolean commonRef,boolean callFromCommon) throws IOException,	MalformedConfigurationException {

		/*
		 * 
		 * ORM
		 * 
		 *  
		 */

		this.populateOrm(orm);
		this.bOrm.setCommonReference(commonRef);
		this.bOrm.setCommonAttributes(this.bo.getCommonAttributes());

		Writer writerOrmI;
		Writer writerOrmC;
		FileOutputStream fw_ormI = null;
		FileOutputStream fw_ormC = null;
		PrintStream outputStreamOrmI;
		PrintStream outputStreamOrmC;

		String outfileIorm = CreatorCostanst.FOLDER_ORM_CLIENT_GENERATED + CreatorCostanst.getPathSrc(bOrm.get_packageCommon()) + "/";
		File outIorm = new File(outfileIorm);
		
		
		
		if (!outIorm.exists())
			outIorm.mkdirs();
	
		//System.exit(0);
		

		String outfileCorm = CreatorCostanst.FOLDER_ORM_GENERATED + CreatorCostanst.getPathSrc(bOrm.get_packageCommon()) + "/";
		File outCorm = new File(outfileCorm);
		if (!outCorm.exists())
			outCorm.mkdirs();

		outfileIorm = outfileIorm + bOrm.getOrmClient() + "Pojo.java";
		outfileCorm = outfileCorm + bOrm.getOrm() + ".java";

		outIorm = new File(outfileIorm);
		outCorm = new File(outfileCorm);
		fw_ormI = new FileOutputStream(outIorm);
		fw_ormC = new FileOutputStream(outCorm);

		outputStreamOrmI = new PrintStream(fw_ormI);
		outputStreamOrmC = new PrintStream(fw_ormC);

		writerOrmI = new Writer(System.out, outputStreamOrmI);
		writerOrmC = new Writer(System.out, outputStreamOrmC);
		
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("ORM="+outIorm.getPath());
		System.out.println("ORM_CLIENT="+outCorm.getPath());
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		CreatePojo cPojo = new CreatePojo(writerOrmI, writerOrmC);
		
		if (callFromCommon)
			cPojo.generaFormFile(bOrm,this.bo);
		else
			cPojo.generaFormFile(bOrm,null);
	}

	private void generateCommon(File common, File form) throws IOException, MalformedConfigurationException {

		StringTokenizer st = new StringTokenizer(common.getName(), ".");
		String nameBO = st.nextToken();
		this.commons.clear();

		/**
		 * 
		 * 
		 * 
		 * Start Process
		 */

		this.populateCommons(common);
		System.out.println("path to create=" + bo.get_packageCommon());

		/*
		 * 
		 * 
		 * 
		 * Common
		 */

		Writer writerCommon;

		String path = CreatorCostanst.FOLDER_BUSINESS_COMMON_GENERATED + CreatorCostanst.getPathSrc(bo.get_packageCommon()) + "/";
		File _path = new File(path);
		if (!_path.exists())
			_path.mkdirs();

		File interfaceFileCommon = new File(path + bo.getNameService()+ "Common.java");

		writerCommon = new Writer(System.out, new PrintStream(new FileOutputStream(interfaceFileCommon)));
		
		CreateCommon ccommon = new CreateCommon(writerCommon);
		ccommon.generaCommonFile(bo);

		/*
		 * 
		 * 
		 * FORM
		 */
		Writer writerFormI;
		Writer writerFormC;
		FileOutputStream fw_formI = null;
		FileOutputStream fw_formC = null;
		PrintStream outputStreamFormI;
		PrintStream outputStreamFormC;

		String outfileIform = CreatorCostanst.FOLDER_BUSINESS_I_FORM_GENERATED + CreatorCostanst.getPathSrc(bo.get_packageCommon()) + "/";
		File outIForm = new File(outfileIform);
		if (!outIForm.exists())
			outIForm.mkdirs();

		String outfileCform = CreatorCostanst.FOLDER_BUSINESS_C_FORM_GENERATED + CreatorCostanst.getPathSrc(bo.get_packageCommon()) + "/";
		File outCForm = new File(outfileCform);
		if (!outCForm.exists())
			outCForm.mkdirs();

		outfileIform = outfileIform + nameBO + "Form.java";
		outfileCform = outfileCform + nameBO + "FormImpl.java";

		outIForm = new File(outfileIform);
		outCForm = new File(outfileCform);
		fw_formI = new FileOutputStream(outIForm);
		fw_formC = new FileOutputStream(outCForm);

		outputStreamFormI = new PrintStream(fw_formI);
		outputStreamFormC = new PrintStream(fw_formC);

		writerFormI = new Writer(System.out, outputStreamFormI);
		writerFormC = new Writer(System.out, outputStreamFormC);

		CreateForm cform = new CreateForm(writerFormI, writerFormC);

		
		//this.commons.clear();
		this.populateForm(form);
		this.bof.commonReference=true;

		cform.generaFormFile(bo, bof);

		System.out.println("");

		//System.out.println("============== FINE ============");

	}

	private void generateForm(File form ,boolean commonRef) throws IOException, MalformedConfigurationException {

		
		if (commonRef){
			
			this.populateForm(form);
			this.bof.setCommonReference(commonRef);
		}else{
			
			this.populateForm(form);
			
			bo.setNameService(bof.getNameService());
			bo.set_packageCommon(bof.get_packageCommon());
			
			this.bof.setCommonReference(commonRef);
		}
		
		
		StringTokenizer st = new StringTokenizer(form.getName(), ".");
		String nameBO = st.nextToken();

		/*
		 * 
		 * 
		 * FORM
		 */
		Writer writerFormI;
		Writer writerFormC;
		FileOutputStream fw_formI = null;
		FileOutputStream fw_formC = null;
		PrintStream outputStreamFormI;
		PrintStream outputStreamFormC;

		String outfileIform = CreatorCostanst.FOLDER_BUSINESS_I_FORM_GENERATED + CreatorCostanst.getPathSrc(bo.get_packageCommon()) + "/";
		File outIForm = new File(outfileIform);
		if (!outIForm.exists())
			outIForm.mkdirs();

		String outfileCform = CreatorCostanst.FOLDER_BUSINESS_C_FORM_GENERATED + CreatorCostanst.getPathSrc(bo.get_packageCommon()) + "/";
		
		File outCForm = new File(outfileCform);
		if (!outCForm.exists())
			outCForm.mkdirs();

		outfileIform = outfileIform + nameBO + "Form.java";
		outfileCform = outfileCform + nameBO + "FormImpl.java";

		outIForm = new File(outfileIform);
		outCForm = new File(outfileCform);
		fw_formI = new FileOutputStream(outIForm);
		fw_formC = new FileOutputStream(outCForm);

		outputStreamFormI = new PrintStream(fw_formI);
		outputStreamFormC = new PrintStream(fw_formC);

		writerFormI = new Writer(System.out, outputStreamFormI);
		writerFormC = new Writer(System.out, outputStreamFormC);

		CreateForm cform = new CreateForm(writerFormI, writerFormC);
		
		
		/*
		bof = new BusinessForm();
		bof.commonReference = false;
		bof.setAttributes(new Vector());
		bof.setNameService(bo.getNameService());
		*/
		
		cform.generaFormFile(bo, bof);
		System.out.println("");

	}

	private void populateCommons(File common) throws IOException, MalformedConfigurationException {

		/**
		 * 
		 * Configurazione X Common
		 * 
		 * 
		 *  
		 */
		DataInputStream readerCommon = new DataInputStream(new FileInputStream(common));
		String row = readerCommon.readLine();

		this.bo = new BusinessObject();
		Vector commonAttributes = new Vector();
		//System.out.println("row="+row);

		String name = common.getName();
		StringTokenizer _st = new StringTokenizer(name.trim(), ".");
		this.bo.setNameService(_st.nextToken());

		do {
			row = row.trim();
			//System.out.println("Process orm CurrentLine=" + row + "#");

			if (row.trim().length() > 0) {

				if (row.startsWith("package")) {

					String path = row.substring(7, row.length() - 1);
					this.bo.set_packageCommon(path.trim());

				} else {

					Attribute a;
					try {
						a = new Attribute(row, true);
					} catch (MalformedAttribute e) {
						// TODO Blocco catch generato automaticamente
						throw new MalformedConfigurationException("[Folder Common] il file "+this.bo.getNameService()+".properties nella"+e.getMessage());
					}
					commonAttributes.add(a);
					this.commons.put(row.trim(), a);

				}
			}
			row = readerCommon.readLine();

		} while (row != null);
		this.bo.setCommonAttributes((Collection) commonAttributes);
	}

	private void populateForm(File form) throws MalformedConfigurationException {

		//System.out.println("??????????????????????????????????????");
		//System.out.println("Reading =" + form.getPath());
		//System.out.println("??????????????????????????????????????");
		//System.out.println("");

		DataInputStream readerForm = null;

		try {
			readerForm = new DataInputStream(new FileInputStream(form));

		} catch (FileNotFoundException e) {
			// TODO Blocco catch generato automaticamente
			throw new MalformedConfigurationException("Attenzione!!! per il file "+this.bo.getNameService()+".properties NON esiste il corrispondente\nfile nel folder dei FORM anche se vuoto va inserito!!!");
		}

		String row = null;

		try {
			row = readerForm.readLine();
			

			if (row!=null){
				
				this.bof = new BusinessForm();
				Vector formAttributes = new Vector();
				//System.out.println("row="+row);

				String name = form.getName();
				StringTokenizer _st = new StringTokenizer(name.trim(), ".");
				this.bof.setNameService(_st.nextToken());

				
				do {
					row = row.trim();
					//System.out.println("Process FORM CurrentLine=" + row + "#");

					if (row.trim().length() > 0) {

						if (row.startsWith("package")) {

							String path = row.substring(7, row.length() - 1);
							this.bof.set_packageCommon(path.trim());

						} else {

							Attribute a;
							
							//row= row.substring(0,row.length()-1)+"IForm;";
							//row= row.substring(0,row.length()-1)+"IForm;";
							
							try {
								a = new Attribute(row, true);
							} catch (MalformedAttribute e2) {
								// TODO Blocco catch generato automaticamente
								throw new MalformedConfigurationException("[Folder FORM] il file "+this.bo.getNameService()+".properties nella"+e2.getMessage());
							}
							formAttributes.add(a);

						}
					}
					row = readerForm.readLine();

				} while (row != null);

				this.bof.setAttributes((Collection) formAttributes);
				
			}

			

		} catch (IOException e1) {
			// TODO Blocco catch generato automaticamente
			e1.printStackTrace();
		}

	}

	private void populateOrm(File orm) throws IOException, MalformedConfigurationException {

		/**
		 * 
		 * Configurazione X ORM
		 * 
		 * 
		 *  
		 */

		/*
		 * String ormFileTogenerate =
		 * this.path_orm+bo.getNameService()+".properties"; System.out.println("
		 * **********************************"); System.out.println("Process
		 * FILE ORM = "+ormFileTogenerate); System.out.println("
		 * **********************************"); File orm = new
		 * File(ormFileTogenerate);
		 */

		DataInputStream readerOrm = null;
		
		
		try {
			readerOrm = new DataInputStream(new FileInputStream(orm));

		} catch (FileNotFoundException e) {
			// TODO Blocco catch generato automaticamente
			throw new MalformedConfigurationException("Attenzione!!! per il file "+this.bo.getNameService()+".properties NON esiste il corrispondente\nfile nel folder dei ORM impossibile mettere a fattor comune proprieta senza tale corrispondenza");
		}


		String ormFileTogenerate = orm.getName();

		System.out.println(" **********************************");
		System.out.println("Process FILE ORM = " + ormFileTogenerate);
		System.out.println(" **********************************");

		this.bOrm = new BusinessORM();

		String rowOrm = readerOrm.readLine();

		Vector ormattributes = new Vector();
		Vector ormextends = new Vector();
		Vector compositeId = new Vector();

		StringTokenizer _st1 = new StringTokenizer(ormFileTogenerate.trim(),".");
		this.bOrm.setOrmClient(_st1.nextToken());

		do {
			rowOrm = rowOrm.trim();
			System.out.println("Process orm CurrentLine=" + rowOrm + "#");

			if (rowOrm.equalsIgnoreCase("<vista>")) {
				
				this.bOrm.setVista(true);
				
			}else if (rowOrm.equalsIgnoreCase("<vista & costr=empty>")) {
				
				this.bOrm.setVistaWithCostrEmpty(true);
				
			}else if (rowOrm.indexOf("vista & otherCostructor")>0) {
				
				this.bOrm.setOtherCostructor(rowOrm.substring(rowOrm.indexOf("=")+1,rowOrm.indexOf(">")));
				
			}else if (rowOrm.equalsIgnoreCase("<clonable>")) {

				this.bOrm.setClonable(true);

			} else if (rowOrm.startsWith("<fakeClass=")) {

				this.bOrm.setFakedObj(true);
				
				StringTokenizer stt=new StringTokenizer(rowOrm,"=");
				
				stt.nextToken();
				String nameFake = stt.nextToken();
				nameFake=nameFake.substring(0,nameFake.length()-1);
				
				this.bOrm.setRefFakeName(nameFake);

			} else if (rowOrm.startsWith("<compositeId")) {
				
				

				StringTokenizer lst = new StringTokenizer(rowOrm, "=");

				lst.nextToken();
				String app = lst.nextToken();

				Attribute compostite;
				
				try {									
					
					compostite = new Attribute(app.trim(), false);		
					
					
					
				} catch (MalformedAttribute e) {
					// TODO Blocco catch generato automaticamente
					throw new MalformedConfigurationException("[Folder ORM] il file "+this.bOrm.getOrmClient()+".properties nel compositeID "+e.getMessage());
				}
				
				compositeId.add(compostite);
			}

			else {

				if (rowOrm.length() > 0) {

					if (rowOrm.startsWith("package")) {

						String path = rowOrm.substring(7, rowOrm.length() - 1);
						this.bOrm.set_packageCommon(path.trim());

					} else if (rowOrm.startsWith("{")) {

						System.out.println("NOME ed ESTENSIONI della classe");
						String _tmp = "";

						int index = rowOrm.indexOf("}");
						_tmp = rowOrm.substring(1, index);

						this.bOrm.setOrm(_tmp);
						_tmp = rowOrm.substring(index);

						if (_tmp.indexOf("{") > 0) {
							_tmp = _tmp.substring(_tmp.indexOf("{") + 1, _tmp.length() - 1);
							this.bOrm.setExtendOrm(new OrmEreditato(_tmp));

						}
					} else {

						Attribute a;
						try {
							
							
							/*
							if (rowOrm.indexOf("<eredit>"))>0){
								
								a = new Attribute(rowOrm, true);
								//throw new MalformedConfigurationException("[EREDIT]");
								
							}else{
								
								a = new Attribute(rowOrm, false);
							}
							//a = new Attribute(rowOrm, false);
							*/
							a = new Attribute(rowOrm, false);
							
						} catch (MalformedAttribute e) {
							// TODO Blocco catch generato automaticamente
							throw new MalformedConfigurationException("[Folder ORM] il file "+this.bOrm.getOrmClient()+".properties nella"+e.getMessage());
						}
						if (this.commons.containsKey(rowOrm.trim())) {
							System.out.println("Attibuto "+ rowOrm+ " presente in Common escludo da ORM Interface");
							a.setExistInCommon(true);
						}
						ormattributes.add(a);
					}
				}

			}

			rowOrm = readerOrm.readLine();

		} while (rowOrm != null);

		this.bOrm.setAttributes((Collection) ormattributes);

		if (compositeId.size() > 0) {
			this.bOrm.setCompositeId(compositeId);
		}

	}
}
