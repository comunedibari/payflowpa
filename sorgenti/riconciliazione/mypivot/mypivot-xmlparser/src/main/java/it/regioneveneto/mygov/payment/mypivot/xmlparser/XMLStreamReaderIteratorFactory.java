package it.regioneveneto.mygov.payment.mypivot.xmlparser;

public interface XMLStreamReaderIteratorFactory<E> {

	XMLStreamReaderIterator<E> getXMLStreamReaderIterator(
			XMLStreamReaderProvider xmlStreamReaderProvider);

}
