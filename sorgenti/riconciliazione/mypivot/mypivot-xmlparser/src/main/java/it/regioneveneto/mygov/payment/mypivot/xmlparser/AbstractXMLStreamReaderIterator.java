package it.regioneveneto.mygov.payment.mypivot.xmlparser;

import javax.xml.stream.XMLStreamReader;

public abstract class AbstractXMLStreamReaderIterator<E> implements
		XMLStreamReaderIterator<E> {

	private final XMLStreamReaderProvider xmlStreamReaderProvider;
	private XMLStreamReader xmlStreamReader;

	protected AbstractXMLStreamReaderIterator(
			XMLStreamReaderProvider xmlStreamReaderProvider) {
		super();
		this.xmlStreamReaderProvider = xmlStreamReaderProvider;
	}

	public XMLStreamReader getXmlStreamReader() {
		return xmlStreamReader;
	}

	@Override
	public boolean hasNext() {
		if (xmlStreamReader == null) {
			xmlStreamReader = xmlStreamReaderProvider.getXMLStreamReader();
		}

		return doHasNext();
	}

	public abstract boolean doHasNext();

	@Override
	public E next() {
		if (xmlStreamReader == null) {
			xmlStreamReader = xmlStreamReaderProvider.getXMLStreamReader();
		}

		return doNext();
	}

	public abstract E doNext();

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
