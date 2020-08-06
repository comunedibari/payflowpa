/**
 * Created on 04/mar/08
 */
package it.nch.eb.flatx.files.model;

import it.nch.eb.flatx.files.SavePointsManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;


/**
 * @author gdefacci
 */
public class InputFileImpl implements InputFile {
	
	private final BufferedReader reader;
	private final AbstractFileCache cache; //  = new FileCache(1024)
	private final SavePointsManager savepoints = new SavePointsManager();
	
	public InputFileImpl(Reader reader, FileCacheBuilder cacheBuilder) {
		if (reader==null) throw new NullPointerException("reader is null");
		this.reader = new BufferedReader(reader);
		cache = cacheBuilder.create();
		cache.cache(this.reader);
	}
	
	public InputFileImpl(Reader reader) {
		this(reader, FileCaches.sharedInstance.lineFeedSeparated());
	}

	public InputFileImpl(String text) {
		this(new StringReader(text));
	}

	public InputFileImpl(InputStream is) {
		this(new InputStreamReader(is));
	}
	
	public InputFileImpl(InputStream is, String encoding) {
		this(createStreamReader(is, encoding));
	}
	
	public InputFileImpl(String text, FileCacheBuilder cacheBuilder) {
		this(new StringReader(text), cacheBuilder);
	}

	public InputFileImpl(InputStream is, FileCacheBuilder cacheBuilder) {
		this(new InputStreamReader(is), cacheBuilder);
	}
	
	public InputFileImpl(InputStream is, String encoding, FileCacheBuilder cacheBuilder) {
		this(createStreamReader(is, encoding), cacheBuilder);
	}

	private static InputStreamReader createStreamReader(InputStream is, String encoding) {
		try {
			return new InputStreamReader(is, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public String nextLine() {
		if (cache.hasNext()) {
			cache.next();
			savepoints.incAll();
		} else {
			cache.cache(reader);
			if (cache.hasNext()) {
				cache.next();
			} 
			savepoints.incAll();
		}
		return cache.currentLine();
	}

	public boolean hasNext() {
		if (cache.hasNext()) return true;
		if (!cache.isEof()) return true;
		return false;
	}

	public String currentLine() {
		return cache.currentLine();
	}

	public int getLineNumber() {
		return cache.getPosition();
	}
	
	public void markSavePoint() {
		savepoints.push();
	}

	public void rollback() {
		cache.prev();
		savepoints.decAll();
	}

	public void rollback(int n) {
		if (n>cache.getActualCacheSize()) throw new IllegalArgumentException("can rollback " + n + " times ");
		for (int i=0;i<n;i++) rollback();
	}

	public void rollbackToSavePoint() {
		if (!savepoints.isSet()) throw new IllegalStateException("no savepoint has been set");
		int savepointDelta = savepoints.currentValue();
		if (savepointDelta>0) rollback(savepointDelta);
		savepointDelta = savepoints.currentValue();
		if (savepointDelta!=0) throw new IllegalStateException("after rollbacking a savepoint the counter should be 0");
		savepoints.pop();
	}

	public void commit() {
		savepoints.pop();
	}

	public String toString() {
		return getLineNumber() + ":" + currentLine();
	}

	public AbstractFileCache getCache() {
		return cache;
	}
	
}
