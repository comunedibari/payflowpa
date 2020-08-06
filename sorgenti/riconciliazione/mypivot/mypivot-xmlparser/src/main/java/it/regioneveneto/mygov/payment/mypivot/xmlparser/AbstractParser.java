package it.regioneveneto.mygov.payment.mypivot.xmlparser;

import java.io.File;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class AbstractParser<E> implements XMLStreamReaderProvider, Iterable<E> {

	private final File xmlFile;
	private final File schemaFile;
	private final XMLStreamReaderIteratorFactory<E> xmlStreamReaderIteratorFactory;
	private boolean toBeValidated;
	private final XMLInputFactory xmlInputFactory;

	public AbstractParser(File xmlFile, File schemaFile,
			XMLStreamReaderIteratorFactory<E> xmlStreamReaderIteratorFactory,
			XMLInputFactory xmlInputFactory) {
		super();
		this.xmlFile = xmlFile;
		this.schemaFile = schemaFile;
		this.xmlStreamReaderIteratorFactory = xmlStreamReaderIteratorFactory;
		this.toBeValidated = schemaFile != null && schemaFile.exists();
		this.xmlInputFactory = xmlInputFactory;
	}

	private boolean validate() throws Exception {
		final SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		final Schema schema = schemaFactory.newSchema(new StreamSource(
				schemaFile));
		final Validator validator = schema.newValidator();
		validator.validate(new StAXSource(getXMLStreamReader()));
		return true;
	}

	public Iterator<E> iterator() {
		if (toBeValidated) {
			toBeValidated = false;
			// if (!validate()) {
			// return new Iterator<E>() {
			//
			// @Override
			// public boolean hasNext() {
			// return false;
			// }
			//
			// @Override
			// public E next() {
			// throw new NoSuchElementException();
			// }
			//
			// @Override
			// public void remove() {
			// throw new UnsupportedOperationException();
			// }
			// };
			// }
			try {
				validate();
			} catch (Exception e) {
				throw new NotAValidXMLException(e);
			}
		}
		return xmlStreamReaderIteratorFactory.getXMLStreamReaderIterator(this);
	}

	@Override
	public XMLStreamReader getXMLStreamReader() {
		try {
			return (XMLStreamReader) xmlInputFactory
					.createXMLStreamReader(new StreamSource(xmlFile));
		} catch (XMLStreamException e) {
			return null;
		}
	}

}
