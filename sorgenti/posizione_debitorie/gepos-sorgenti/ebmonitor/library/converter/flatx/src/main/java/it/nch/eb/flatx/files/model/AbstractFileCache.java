/**
 * Created on 03/giu/08
 */
package it.nch.eb.flatx.files.model;

import it.nch.eb.common.utils.StringUtils;

import java.io.BufferedReader;

/**
 * @author gdefacci
 */
public abstract class AbstractFileCache {
	
	protected final Object lock;

	private String[]	cache;

	protected abstract String[] readLines(BufferedReader reader, int cacheSize);

	private int	firstCacheItemPos	= 0;
	private int	cacheIdx	= 0;
	protected final int	cacheSize;
	private boolean	eof;

	public AbstractFileCache(int sze) {
		this.cacheSize = sze;
		this.lock = this;
	}

	/**
	 * read file seeting eof to true if eof is encountered
	 * @param reader
	 * @param cacheSize
	 * @return
	 */
	public void cache(BufferedReader reader) {
		synchronized (lock) {
			String[] newLines = readLines(reader, cacheSize);
			if (newLines.length < cacheSize)
				if (!isEof()) throw new IllegalStateException();
			
			if (cache==null || cache.length==0) {
				cache = newLines;			
				firstCacheItemPos = 0;
			} else if (newLines.length>0) {
				
				int firstPreservedCachePos;
				int preservedItemsInCache;
				
				int numberOfItemsToAdd = newLines.length;
				if (numberOfItemsToAdd > cacheSize / 2) {
					firstPreservedCachePos = cacheSize / 2;
				} else {
					firstPreservedCachePos = numberOfItemsToAdd;
				}
				
				preservedItemsInCache = cache.length - firstPreservedCachePos;
				
				String[] newCache;
				int newLen = preservedItemsInCache + newLines.length;
				newCache = new String[newLen];
				
				System.arraycopy(cache, firstPreservedCachePos, newCache, 0, preservedItemsInCache);
				System.arraycopy(newLines, 0, newCache, preservedItemsInCache , newLines.length);
				
				firstCacheItemPos += firstPreservedCachePos;
				cache = newCache;
				cacheIdx = cacheIdx - firstPreservedCachePos;
			} else {
				cacheIdx++;
			}	
		}
	}

	public int getPosition() {
		return cacheIdx + firstCacheItemPos;
	}

	/**
	 * true if the file has reached eof. However there could be some unread lines from cache 
	 */
	public boolean isEof() {
		return eof;
	}

	protected boolean setEof(boolean eof) {
		return this.eof = eof;
	}

	public String currentLine() {
		if (cacheIdx>=cache.length) return null; 
		return cache[cacheIdx];
	}

	public void next() {
		if (!hasNext()) throw new IllegalStateException();
		cacheIdx++;
	}

	public void prev() {
		if (hasPrev()) throw new IllegalStateException();
		cacheIdx--;
	}

	public boolean hasPrev() {
		return cacheIdx<1;
	}

	/**
	 * true if the cache (NOT the file) has some avaiable lines 
	 * @return
	 */
	public boolean hasNext() {
		return cacheIdx<cache.length-1;
	}

	/**
	 * number of lines that can be rollbacked.  
	 * @return
	 */
	public int getActualCacheSize() {
		return cacheIdx;
	}

	/**
	 * full size of the cache. 
	 * @return
	 */
	public int getFullCacheSize() {
		return cache.length;
	}
	
	protected String[] getLast(int delta) {
		if (delta<1) return new String[0];
		
		String[] res = new String[delta];
		int currentCachePos = cacheIdx;
		System.arraycopy(cache, currentCachePos - delta , res, 0, delta);
		return res;
	}
	
	public String toString() {
		return StringUtils.getSimpleName(this.getClass()) + " actual size " + getActualCacheSize() + " cache real size " + cache.length; 
	}

}