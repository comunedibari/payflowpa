/**
 * 15/mag/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;

/**
 * @author gdefacci
 */
public class DbExporter {

	public static final String DTD_SYSTEM_ID = "http://www.nch.it/dtds/idp-dataset.dtd";
	public static final String DTD_CLASSPATH_LOCATION = "dtds/idp-dataset.dtd";
	
	private final IDatabaseConnection connection;
	private final String[] includedTables;
	public static final String XML_CATALOG_CLASSPATH_LOCATION = "xmlcatalog.xml";
	public static final String[] DBUNIT_DTDS_CATALOGS = 
		new String[] { "classpath:/" + XML_CATALOG_CLASSPATH_LOCATION };

	public DbExporter(IDatabaseConnection connection, String[] includedTables) {
		this.connection = connection;
		this.includedTables = includedTables;
	}

	public void export(File file) {
		if (!file.exists()) {
			ResourcesUtil.mkDirs( file.getParentFile() );
		}
		OutputStream out = null;
		try {
	        IDataSet fullDataSet = connection.createDataSet(includedTables);
			OutputStream out1 = new FileOutputStream(file);
			
			FlatXmlWriter datasetWriter = new FlatXmlWriter(out1);
			datasetWriter.setDocType(DTD_SYSTEM_ID);
			datasetWriter.setIncludeEmptyTable(true);
			datasetWriter.write(fullDataSet);

		} catch (Exception e) {
			throw new RuntimeException("error exporting db to " + file, e);
		} finally {
			ResourcesUtil.close(out);
		}
	}
	
	public void export(TableComparisonContainer compCnt, File file) {
		if (!file.exists()) {
			ResourcesUtil.mkDirs( file.getParentFile() );
		}
		OutputStream out = null;
		try {
			TableComparison[] cmps = compCnt.getTableComparisons();
//			ITable[] resTables = new ITable[cmps.length];
			List/*<ITable>*/ resTables = new ArrayList();
			
			for (int i = 0; i < cmps.length; i++) {
				TableComparison tableComparison = cmps[i];
				String query = tableComparison.getQuery();
				if (query!=null) {
					resTables.add( connection.createQueryTable(tableComparison.getTableName(), query) );
				} else {
					resTables.add(connection.createTable(tableComparison.getTableName()));
				}
			}
			
			IDataSet fullDataSet = new CompositeDataSet((ITable[]) resTables.toArray(new ITable[0]));
			OutputStream out1 = new FileOutputStream(file);
			
			FlatXmlWriter datasetWriter = new FlatXmlWriter(out1);
			datasetWriter.setDocType(DTD_SYSTEM_ID);
			datasetWriter.setIncludeEmptyTable(true);
			datasetWriter.write(fullDataSet);

		} catch (Exception e) {
			throw new RuntimeException("error exporting db to " + file, e);
		} finally {
			ResourcesUtil.close(out);
		}
	}
	
	
	
	public void createDefaultXmlCatalogAndDTD() {
		File classpathResoureFolder = new File("resources");
		createDefaultXmlCatalogAndDTD(classpathResoureFolder); 
	}

	public void createDefaultXmlCatalogAndDTD(File base) {
		createXmlCatalogAndDTD(
				new File(base, DbExporter.XML_CATALOG_CLASSPATH_LOCATION), 
				DTD_SYSTEM_ID, 
				"classpath:/" + DTD_CLASSPATH_LOCATION, 
				new File(base, DTD_CLASSPATH_LOCATION));
	}
	
	public void createXmlCatalogAndDTD(File xmlCatalog, String systemId, String uriLoc, File dtdFile) {
		FileOutputStream fos = null;
		try {
			if (!dtdFile.exists()) dtdFile.getParentFile().mkdirs();
			if (!xmlCatalog.exists()) xmlCatalog.getParentFile().mkdirs();
			FlatDtdDataSet.write(connection.createDataSet( includedTables ), new FileOutputStream(dtdFile));
			fos = new FileOutputStream(xmlCatalog);
			PrintStream printer = new PrintStream(fos);
			printer.print(xmlCatalogContent(systemId, uriLoc));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ResourcesUtil.close(fos);
		}
	}
	
	private String xmlCatalogContent(String systemId, String uriLoc) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
		"<!DOCTYPE catalog  PUBLIC \"-//OASIS//DTD Entity Resolution XML Catalog V1.0//EN\" \"http://www.oasis-open.org/committees/entity/release/1.0/catalog.dtd\">\n"+
		"\n"+
		"<!--  generated  -->\n"+
		"<catalog xmlns='urn:oasis:names:tc:entity:xmlns:xml:catalog' preferBublic='true'>\n"+ 
		"\n"+
		"<uri name='"+systemId+"' uri='"+uriLoc+"' />\n"+
		"\n"+
		"</catalog>\n";

	}

}
