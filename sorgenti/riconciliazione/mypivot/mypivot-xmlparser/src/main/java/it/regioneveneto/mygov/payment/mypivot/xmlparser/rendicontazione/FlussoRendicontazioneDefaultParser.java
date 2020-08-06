package it.regioneveneto.mygov.payment.mypivot.xmlparser.rendicontazione;

import it.regioneveneto.mygov.payment.mypivot.xmlparser.AbstractParser;
import it.regioneveneto.mygov.payment.mypivot.xmlparser.XMLStreamReaderIterator;
import it.regioneveneto.mygov.payment.mypivot.xmlparser.XMLStreamReaderIteratorFactory;
import it.regioneveneto.mygov.payment.mypivot.xmlparser.XMLStreamReaderProvider;

import java.io.File;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;

public class FlussoRendicontazioneDefaultParser extends
		AbstractParser<Map<String, String>> {

	private static XMLInputFactory getXMLInputFactory() {
		return (XMLInputFactory) XMLInputFactory.newInstance();
	}

	public FlussoRendicontazioneDefaultParser(File xmlFile, File schemaFile) {
		super(xmlFile, schemaFile,
				new XMLStreamReaderIteratorFactory<Map<String, String>>() {

					@Override
					public XMLStreamReaderIterator<Map<String, String>> getXMLStreamReaderIterator(
							XMLStreamReaderProvider xmlStreamReaderProvider) {
						return new FlussoRendicontazioneIterator(
								xmlStreamReaderProvider);
					}
				}, getXMLInputFactory());
	}

}
