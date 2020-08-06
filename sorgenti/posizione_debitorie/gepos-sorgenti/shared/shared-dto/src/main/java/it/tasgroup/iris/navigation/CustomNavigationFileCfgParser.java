package it.tasgroup.iris.navigation;

import it.tasgroup.iris.dto.anonymous.payment.AnonymousCustomTributoEnteDTO;
import it.tasgroup.iris.util.ConfigurationFileResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CustomNavigationFileCfgParser {

	public static String getFileNameByIdEnte(String idEnte) {
		return "Ente_"+idEnte+".xml";
	}
	public  List<AnonymousCustomTributoEnteDTO> parse(String filename, String contextRoot) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		File theFile = ConfigurationFileResolver.getFile(CustomNavigationFileCfgParser.class, filename);
		InputStream cfgInputStream = new FileInputStream(theFile);
		if (theFile == null)
			throw new IOException("Non e' stata trovata una configurazione relativa al file " + filename+ " nel classpath...");
		
		Document document = (Document) builder.parse(cfgInputStream);
		List<AnonymousCustomTributoEnteDTO> outList = new ArrayList<AnonymousCustomTributoEnteDTO>();
		//Normalize the XML Structure; It's just too important !!
	 
		//Here comes the root node
		Element root = document.getDocumentElement();

		NodeList nl = root.getChildNodes();
		int posiz=0;
		for (int i=0; i < nl.getLength() ;i++ ) {
			
			Node n = nl.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE) {
			    
			    // primo livello 
			    AnonymousCustomTributoEnteDTO anonym= new AnonymousCustomTributoEnteDTO();
			    // process element
			    
			    anonym=processElement(n,anonym, contextRoot);
			    anonym.setIndex(posiz);
			    outList.add(anonym);
			    posiz++;
			    
		    }
		}
		
		return outList;
	}
	private AnonymousCustomTributoEnteDTO processElement(Node n,AnonymousCustomTributoEnteDTO anon, String contextRoot) {
		anon.setType(n.getNodeName());
		NamedNodeMap nnmap = n.getAttributes();
		Node nn = nnmap.getNamedItem("order");
		if (nn!=null) {
			anon.setOrder(Integer.parseInt(nn.getNodeValue()));
			anon.setIndex(Integer.parseInt(nn.getNodeValue()));
		}
		nn = nnmap.getNamedItem("carouselOrder");
		if (nn!=null) {
			anon.setCarouselOrder(Integer.parseInt(nn.getNodeValue()));
			//anon.setIndex(Integer.parseInt(nn.getNodeValue()));
		}
		nn = nnmap.getNamedItem("label");
		if (nn!=null) {
			anon.setLabel(nn.getNodeValue());
			anon.setDeTrb(nn.getNodeValue());
		}
		nn = nnmap.getNamedItem("titolo");
		if (nn!=null) {
			anon.setTitolo(nn.getNodeValue());
		}
		
		nn = nnmap.getNamedItem("url");
		if (nn!=null) {
			anon.setUrl(nn.getNodeValue());
		}
		nn = nnmap.getNamedItem("img");
		if (nn!=null) {
			anon.setImg(contextRoot + nn.getNodeValue());
		}
		nn = nnmap.getNamedItem("cd_trb_ente");
		if (nn != null) {
			anon.setCdTrbEnte(nn.getNodeValue());
//			anon.setIdTributo(nn.getNodeValue());
		}
		// esamino eventuali figli
		NodeList nlist = n.getChildNodes();
		for (int j=0;j < nlist.getLength();j++) {
			Node child = nlist.item(j);
			if (child.getNodeType()==Node.ELEMENT_NODE) {

			    // primo livello 
			    AnonymousCustomTributoEnteDTO innerAnonym= new AnonymousCustomTributoEnteDTO();
			    
			    // process element
			    innerAnonym=processElement(child,innerAnonym, contextRoot);
			    anon.getListCustomTrib().add(innerAnonym);
			    
		    }
		}
		return anon;
	}
	
	public final static  void main(String[] args) throws Exception {
		
		System.out.println(new CustomNavigationFileCfgParser().parse("cfg_test.xml", "/public"));
		
	}
}
