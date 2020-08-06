package it.regioneveneto.mygov.payment.mypivot.xmlparser.rendicontazione;

import it.regioneveneto.mygov.payment.mypivot.xmlparser.AbstractXMLStreamReaderIterator;
import it.regioneveneto.mygov.payment.mypivot.xmlparser.XMLStreamReaderProvider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class FlussoRendicontazioneIterator extends
		AbstractXMLStreamReaderIterator<Map<String, String>> {

	private static final String DATI_SINGOLI_PAGAMENTI = "datiSingoliPagamenti";
	private static final String FLUSSO_RIVERSAMENTO = "FlussoRiversamento";
	private static final String SEPARATOR = "_";

	private Map<String, String> commonAttributes;
	private Map<String, String> responseAttributes;
	private Map<String, String> nextElement;
	private Stack<String> pathStack;
	private boolean charactersToTrim;

	public FlussoRendicontazioneIterator(
			XMLStreamReaderProvider xmlStreamReaderProvider) {
		super(xmlStreamReaderProvider);
		this.pathStack = new Stack<String>();
		this.commonAttributes = new HashMap<String, String>();
	}

	@Override
	public boolean doHasNext() {
		if (nextElement != null) {
			return true;
		}
		try {
			nextElement = doNext();
			return true;
		} catch (NoSuchElementException e) {
			nextElement = null;
			return false;
		}
	}

	private String actualKey() {
		final Iterator<String> pathStackIterator = pathStack.iterator();
		if (pathStackIterator.hasNext()) {
			pathStackIterator.next();
		}
		final StringBuilder sb = new StringBuilder();
		while (pathStackIterator.hasNext()) {
			final String s = pathStackIterator.next();
			sb.append(s).append(SEPARATOR);
		}
		return sb.length() > SEPARATOR.length() ? sb.substring(0, sb.length() - SEPARATOR.length()) : "";
	}

	private static void update(Map<String, String> attributes, String key,
			String text) {
		final String value = attributes.get(key);
		if (value == null) {
			attributes.put(key, text);
		} else {
			attributes.put(key, value + text);
		}
	}

	private static void trimOrRemove(Map<String, String> attributes, String key) {
		final String value = attributes.get(key);
		if (value != null) {
			final String newValue = value.replaceAll("(^\\s+|\\s+$)", "");
			if (newValue.isEmpty()) {
				attributes.remove(key);
			} else {
				attributes.put(key, newValue);
			}
		}
	}

	@Override
	public Map<String, String> doNext() {
		if (nextElement != null) {
			final Map<String, String> toBeReturned = nextElement;
			nextElement = null;
			return toBeReturned;
		}
		try {
			final XMLStreamReader xmlStreamReader = getXmlStreamReader();
			while (xmlStreamReader.hasNext()) {
				int eventType = xmlStreamReader.next();
				switch (eventType) {
				case XMLEvent.START_ELEMENT:
					final String startLocalName = xmlStreamReader
							.getLocalName();
					pathStack.push(startLocalName);
					if (DATI_SINGOLI_PAGAMENTI.equals(startLocalName)) {
						responseAttributes = new HashMap<String, String>(
								commonAttributes);
					}
					break;
				case XMLEvent.CHARACTERS:
					final String text = xmlStreamReader.getText();
					final String key = actualKey();
					if (!key.isEmpty()) {
						if (key.contains(DATI_SINGOLI_PAGAMENTI)) {
							update(responseAttributes, key, text);
						} else {
							update(commonAttributes, key, text);
						}
					}
					charactersToTrim = true;
					break;
				case XMLEvent.END_ELEMENT:
					final String endLocalName = xmlStreamReader.getLocalName();
					if (charactersToTrim) {
						final String lastKey = actualKey();
						if (!lastKey.isEmpty()) {
							if (lastKey.contains(DATI_SINGOLI_PAGAMENTI)) {
								trimOrRemove(responseAttributes, lastKey);
							} else {
								trimOrRemove(commonAttributes, lastKey);
							}
						}
						charactersToTrim = false;
					}
					pathStack.pop();
					if (DATI_SINGOLI_PAGAMENTI.equals(endLocalName)) {
						return responseAttributes;
					}
					if (FLUSSO_RIVERSAMENTO.equals(endLocalName)) {
						throw new NoSuchElementException(FLUSSO_RIVERSAMENTO
								+ " END_ELEMENT");
					}
					break;
				default:
					break;
				}
			}
		} catch (XMLStreamException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		throw new NoSuchElementException("EOF");
	}

}
