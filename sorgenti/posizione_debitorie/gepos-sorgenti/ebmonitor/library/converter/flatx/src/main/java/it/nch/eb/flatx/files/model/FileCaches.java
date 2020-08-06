/**
 * Created on 03/giu/08
 */
package it.nch.eb.flatx.files.model;


/**
 * @author gdefacci
 */
public class FileCaches {
	
	public static final FileCaches sharedInstance = new FileCaches(4096);
	
	private final int cacheSze;
	
	public FileCaches(int cacheSze) {
		this.cacheSze = cacheSze;
	}

	public FileCacheBuilder fixedWidth(int lineSze) {
		return new FixedWidthCacheBuilder(cacheSze, lineSze);
	}
	
	public FileCacheBuilder lineFeedSeparated() {
		return new LineFeedCacheBuilder(cacheSze);
	}
	
}


class FixedWidthCacheBuilder implements FileCacheBuilder {
	
	private final int cacheSize;
	private final int lineSze;

	public FixedWidthCacheBuilder(int cacheSize, int lineSze) {
		this.cacheSize = cacheSize;
		this.lineSze = lineSze;
	}

	public AbstractFileCache create() {
		return new FixedWidthFileCache(cacheSize, lineSze);
	}
	
}

class LineFeedCacheBuilder implements FileCacheBuilder {
	
	private final int cacheSize;

	public LineFeedCacheBuilder(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public AbstractFileCache create() {
		return new FileCache(cacheSize);
	}
	
}