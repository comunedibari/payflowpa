/*
 * Creato il 4-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.db;

import it.nch.ebweb.generate.core.Writer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author FelicettiA
 * 
 * TODO Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class ProcessDDLSchema {

	//String[] keyWord = new String{"VARCHAR2","NUMBER"};

	public ProcessDDLSchema() {

	}

	public void epureXMIFile(String xmifile) throws IOException {

		FileOutputStream fw = null;
		FileInputStream fi = null;
		PrintStream outputStream;
		DataInputStream reader;
		Writer writer;

		File ddl = new File(xmifile);
		String outfile = ddl.getParentFile() + "/" + "schema_epurate.xml";
		File out = new File(outfile);

		try {

			fw = new FileOutputStream(out);
			fi = new FileInputStream(ddl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		outputStream = new PrintStream(fw);
		reader = new DataInputStream(fi);
		writer = new Writer(System.out, outputStream);

		String row = reader.readLine();
		
		//<UML:Stereotype xmi.idref='_9_0_1fe00f9_1119337118921_354011_73'/>
		//<UML:AssociationEnd xmi.id='_9_5_151c03db_1141574677708_62452_1763' visibility='public' isNavigable='true' participant='_9_5_151c03db_1141574677668_425148_1668'/>
		//<UML:AssociationEnd xmi.id='_9_5_151c03db_1141574677708_837877_1764' visibility='public' stereotype='_91a0295_1096040862175_751937_0' participant='_9_5_151c03db_1141574677598_222526_1593'>

		
		boolean isLastRowClass=false;
		do {
			
			
			//System.out.println("Letto=" + row);

			
			
			/*
			 * 
			 * 
			 * 
			 * ENTITY è cablato il codice dello stereotipo è ERRATO!!!!
			 */
			if ( (row.lastIndexOf("<UML:Class xmi.id=")>0) && (row.lastIndexOf("name='Jlt"))>0 ){
				isLastRowClass= true;						
				int st = row.lastIndexOf("stereotype=");
				row = row.substring(0,st)+" stereotype='_9_0_1fe00f9_1119337118921_354011_73'>";
			}
			
			if (row.trim().startsWith("<UML:AssociationEnd xmi.id='")){
				
				if ( (row.lastIndexOf("visibility='public'")>0 )){
					
				}else{
					
					if ((row.substring((row.length() - 2), row.length())).equalsIgnoreCase("/>"))
						row = row.substring(0, row.length() - 2)+ " visibility='public' />";
					else
						row = row.substring(0, row.length() - 1)+ " visibility='public'>";
				}
				
			}
			
			/*
			if ( isLastRowClass&& (row.lastIndexOf("<UML:ModelElement.stereotype>")>0) ){
				isLastRowClass= false;					
			}			
			*/			
			
			if (row.trim().startsWith("<UML:Attribute xmi.")) {

				//System.out.println("row=" + row);
				int index = row.trim().lastIndexOf("visibility='public'");

				if (index > 0) {
					writer.println(row);
				} else {
					
					if ((row.substring((row.length() - 2), row.length())).equalsIgnoreCase("/>"))
						row = row.substring(0, row.length() - 2)+ " visibility='public' />";
					else
						row = row.substring(0, row.length() - 1)+ " visibility='public'>";
					writer.println(row);

				}

				
			} else
				writer.println(row);
			
			
			/*
			if ( isLastRowClass&& (row.lastIndexOf("<UML:ModelElement.stereotype>")>0) ){
				isLastRowClass= false;		
				writer.println("<UML:Stereotype xmi.idref='_9_0_1fe00f9_1119337118921_354011_73'/>");
			}
			*/

			row = reader.readLine();

		} while (row != null);

		System.out.println("FINE!!!!!!!");
	}

	public void epureDDLFile(String ddlfile) throws IOException {

		FileOutputStream fw = null;
		FileInputStream fi = null;
		PrintStream outputStream;
		DataInputStream reader;
		Writer writer;

		File ddl = new File(ddlfile);
		String outfile = ddl.getParentFile() + "/" + "db_epurate.sql";
		File out = new File(outfile);

		try {

			fw = new FileOutputStream(out);
			fi = new FileInputStream(ddl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outputStream = new PrintStream(fw);
		reader = new DataInputStream(fi);
		writer = new Writer(System.out, outputStream);

		String row = reader.readLine();

		boolean isAnIndex = false;
		boolean lastRowIsIndex = false;
		
		String lastRowAlter = "";
		boolean lastRowIsAlter = false;
		boolean lastPrimaryKey = false;
		do {
			lastRowIsIndex = false;
			if ((row.trim().startsWith("CREATE UNIQUE INDEX"))	|| (row.trim().startsWith("CREATE INDEX"))) {
				isAnIndex = true;
			}
			if ((row.trim().startsWith(");")) && isAnIndex) {

				isAnIndex = false;
				lastRowIsIndex = true;
			}

			if (isAnIndex) {
				//writer.println("LINE INDEX");
			} else if (!lastRowIsIndex)
				if ((row.trim().startsWith("CREATE TABLE"))||(row.trim().startsWith("ALTER TABLE"))){
					
					if (row.trim().startsWith("ALTER TABLE")){											
							lastRowIsAlter = true;	
							lastRowAlter = row;
					}else{
												
						String line = row.toLowerCase();	
						if (line.indexOf("jlt") > 0)
							line = line.replaceAll("jlt","Jlt");
						writer.println(line);
						lastRowIsAlter = false;
						
					}										
										
				}else{					
					
					//System.out.println("not create or alter="+row);
					
					if ((lastRowIsAlter)&&((row.lastIndexOf("PRIMARY KEY")>0))){
										
						
					}else{
												
						if (lastRowIsAlter){
							writer.println(lastRowAlter.toLowerCase());
						}
						
						String line = row.toLowerCase();	
						if (line.indexOf("jlt") > 0)
							line = line.replaceAll("jlt","Jlt");
						writer.println(line);
						
					}
					
					lastRowIsAlter = false;
					
				}
			else {
				//writer.println("LINE INDEX");
			}
			//System.out.println("Letto=" + row);
			row = reader.readLine();

		} while (row != null);

	}

}
