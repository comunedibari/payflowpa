/**
 * 
 */
package it.nch.gen.xsqlcmd;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.common.utils.resource.ClasspathResources;
import it.nch.eb.common.utils.resource.StringPredicate;
import it.nch.eb.flatx.flatmodel.sax.SaxUtils;
import it.nch.eb.flatx.flatmodel.sax.transform.AttributeReplacer;
import it.nch.eb.flatx.flatmodel.sax.transform.AttributesDescriber;
import it.nch.eb.flatx.flatmodel.sax.transform.AttributesReplacer;
import it.nch.eb.flatx.flatmodel.sax.transform.DefaultAttributeDescriber;
import it.nch.eb.flatx.flatmodel.sax.transform.DefaultAttributesDescriber;
import it.nch.eb.flatx.flatmodel.sax.transform.DeleteElement;
import it.nch.eb.flatx.flatmodel.sax.transform.DeleteElementTag;
import it.nch.eb.flatx.flatmodel.sax.transform.ElementPrinterEffect;
import it.nch.eb.flatx.flatmodel.sax.transform.ElementPrinterEffectSeq;
import it.nch.eb.flatx.flatmodel.sax.transform.ElementPrinterEffectsContainer;
import it.nch.eb.flatx.flatmodel.sax.transform.InsertStringPrinterEffect;
import it.nch.eb.flatx.flatmodel.sax.transform.QNameMatch;
import it.nch.eb.flatx.flatmodel.sax.transform.ReplaceElemText;
import it.nch.eb.flatx.flatmodel.sax.transform.ReplaceTagName;
import it.nch.eb.flatx.flatmodel.sax.transform.ReplaceTagNameAndText;
import it.nch.eb.flatx.flatmodel.sax.transform.WriterSaxElementHandler;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

import org.xml.sax.Attributes;


public class RewriteXmlTest {
	
	static final String ente = "CFiglineVA";
	
	public static void main(String[] args) throws IOException {
		File sourceFolderLoc = new File("resources"); 
		StringPredicate pred = new StringPredicate() {

			public boolean match(String str) {
				return str.startsWith("testsv3/") && str.endsWith("inp.xml");
			}
			
		};
		ClasspathResources lister = new ClasspathResources(pred);
		Collection ress = lister.getResources();
		for (Iterator it = ress.iterator(); it.hasNext();) {
			String fn = (String) it.next();
			String noExt = fn.substring(0, fn.length() - 4);
			String newFileNm = noExt + "_rewr.xml";
			File inpFile = new File(sourceFolderLoc, fn);
			File newFile = new File(sourceFolderLoc, newFileNm);
			
			convert(inpFile, newFile);
			System.out.println("created " + newFile.getAbsolutePath());
		}
	}
	
	public static void mainS(String[] args) throws IOException {
		File outputFile = new File("D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/resources/testsv3/del1/inp_rewr.xml");
		File inputFile = new File("D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/resources/testsv3/del1/inp.xml");
		
		convert(inputFile, outputFile);
	}
	
	public static void mainConvertFolders(String[] args) throws IOException {
		File outputFile = new File("D:/java/projects/flowmanager-svn/converter/flatx/src/main/java/it/nch/eb/flatx/flatmodel/sax/exp/b.xml");
		File fldr = new File("D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/resources/tests");
		File inputFile = new File(fldr, "tc05u/IdP.tc05u.AllineamentoPendenzeEnte.01.02.00.xml");
		
		File source = new File("D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/resources/tests/v02");
		File target = new File("D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/resources/tests/v03");
		
		convertFolders(source, target);
	}
	
	public static void convertFolders(File source, File target) throws IOException {
		if (!source.exists()) throw new IllegalStateException(source + " doeas not exists");
		if (!target.exists()) target.mkdirs();
		if (source.isFile()) throw new IllegalStateException(source + " is a file");
		if (target.isFile()) throw new IllegalStateException(target + " is a file");
		File[] inputFiles = source.listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".xml");
			}
			
		});
		for (int i = 0; i < inputFiles.length; i++) {
			File inputFile = inputFiles[i];
			File outputFile = new File(target, inputFile.getName());
			convert(inputFile, outputFile);
		}
	}

	public static void convert(File inputFile, File outputFile) throws IOException {
		FileOutputStream out = new FileOutputStream(outputFile);
		ElementPrinterEffectsContainer efcts = newReplaceEffect();
		
		PrintStream printer =new PrintStream(out);
		WriterSaxElementHandler handler = new WriterSaxElementHandler(efcts, printer);
		
		SaxUtils.instance.saxParse(ResourceLoaders.FILESYSTEM.loadReader(inputFile), handler);
		
		printer.flush();
		printer.close();
		System.out.println("converted " + inputFile.getAbsolutePath() + " \n to " + outputFile.getAbsolutePath() );
	}

	private static ElementPrinterEffectsContainer newReplaceEffect() {
		ElementPrinterEffectsContainer efcts = new ElementPrinterEffectsContainer();
		efcts.add("/n:IdpAllineamentoPendenze", 
				new ReplaceTagName("IdpAllineamentoPendenze", 
						new AttributesReplacer( new AttributeReplacer[] {
							new AttributeReplacer(
									new QNameMatch("xsi:schemaLocation"), 
									new DefaultAttributeDescriber.With("xmlns", "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze")
										.and("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze\nfile:///D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/docs/01.03-01/xsd/IdP.AllineamentoPendenze.xsd\""))
						}  )));
		efcts.add("/n:IdpAllineamentoPendenze/IdpHeader", new ElementPrinterEffectSeq( new ElementPrinterEffect[] {
				new ReplaceTagName("IdpHeader", new DefaultAttributesDescriber() {

					public String describeAttributes(Attributes attributes) {
						String res = super.describeAttributes(attributes);
						res = " xmlns=\"http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader\"" + res;
						return res;
					}
					
				}) 
		}));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Service", new DeleteElementTag());
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Service/HE2E:ServiceName", new ReplaceTagName("E2ESrvcNm"));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Service/HE2E:E2EMsgId", new ReplaceTagName("E2EMsgId"));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Service/HE2E:XMLCrtDt", new ReplaceTagName("XMLCrtDt"));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Sender", new ReplaceTagName("Sender"));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Sender/HE2E:SenderId", new ReplaceTagNameAndText("E2ESndrId", ente));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Sender/HE2E:SenderSys", new ReplaceTagName("E2ESndrSys"));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Receiver", new ReplaceTagName("Receiver"));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Receiver/HE2E:ReceiverId", new ReplaceTagName("E2ERcvrId"));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Receiver/HE2E:ReceiverSys", new ReplaceTagName("E2ERcvrSys"));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Reference", new DeleteElement());
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Reference/HE2E:E2EMsgId", new DeleteElement());
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Reference/HE2E:XMLCrtDt", new DeleteElement());
//		
//		addOperationEffects(efcts, "Insert");
//		
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/Stato", 
//				new ReplaceTagNameAndText("Importo", "300"));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/Importo", 
//				new ReplaceTagNameAndText("Stato", "Pagato"));
//		
//		addOperationEffects(efcts, "UpdateStatus");
//		addOperationEffects(efcts, "Delete");
//		addOperationEffects(efcts, "Replace");
//		
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/Replace/InfoPagamento/DettaglioPagamento/Stato", 
//				new ReplaceTagNameAndText("Importo", "300"));
//		efcts.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/Replace/InfoPagamento/DettaglioPagamento/Importo", 
//				new ReplaceTagNameAndText("Stato", "Pagato"));
		return efcts;
	}

	private static void addOperationEffects(ElementPrinterEffectsContainer efcts, String op) {
		efcts.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/" + op + "/Riferimento", new DeleteElement());
		efcts.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/" + op + "/Allegato", new DeleteElement());
		
		
		efcts.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/" + op + "/InfoPagamento/DettaglioPagamento/DataPagamento", new DeleteElement());
		efcts.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/" + op + "/InfoPagamento/DettaglioPagamento/CanalePagamento", new DeleteElement());
		efcts.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/" + op + "/InfoPagamento/DettaglioPagamento/Allegato/Codifica", 
				new ReplaceElemText("PDF_"));
	}

}
