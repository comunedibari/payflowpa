/**
 * Created on 05/giu/08
 */
package it.nch.eb.flatx.files.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;


/**
 * @author gdefacci
 */
public class InputFileWithBuffer extends InputFileImpl {
	
	static public interface FilePositionMarker {
		int getLineNumber();
	}
	
	private static class BaseFilePositionMarker implements FilePositionMarker {
		private final int lineNumber;
		private InputFileWithBuffer from;
		
		public BaseFilePositionMarker(int lineNumber, InputFileWithBuffer from) { 
			this.lineNumber = lineNumber; 
			this.from = from;
		}
		public int getLineNumber() { 
			return lineNumber; 
		}
		
		public boolean matchInputFile(InputFileWithBuffer othr) {
			return othr == from;
		}
		
	}

	public InputFileWithBuffer(InputStream is, String encoding, FileCacheBuilder cacheBuilder) {
		super(is, encoding, cacheBuilder);
	}
	
	public InputFileWithBuffer(InputStream is, FileCacheBuilder cacheBuilder) {
		super(is, cacheBuilder);
	}

	public InputFileWithBuffer(InputStream is, String encoding) {
		super(is, encoding);
	}

	public InputFileWithBuffer(InputStream is) {
		super(is);
	}

	public InputFileWithBuffer(Reader reader, FileCacheBuilder cacheBuilder) {
		super(reader, cacheBuilder);
	}

	public InputFileWithBuffer(Reader reader) {
		super(reader);
	}

	public InputFileWithBuffer(String text, FileCacheBuilder cacheBuilder) {
		super(text, cacheBuilder);
	}

	public InputFileWithBuffer(String text) {
		super(text);
	}

	public FilePositionMarker getPositionMarker() {
		return new BaseFilePositionMarker(InputFileWithBuffer.this.getLineNumber(), this);
	}
	
	public String[] getBufferFrom(FilePositionMarker pMarker) {
		if (!(pMarker instanceof BaseFilePositionMarker)) 
			throw new IllegalStateException("the marker has not been generated from this input file");
		BaseFilePositionMarker marker = (BaseFilePositionMarker) pMarker;
		if (!marker.matchInputFile(this))
			throw new IllegalStateException("the marker has not been generated from this input file");
		
		int delta = getLineNumber() - marker.getLineNumber();
		return getCache().getLast(delta);
	}
	
	public static interface LineDecorator {
		String decorated(String line);
	}
	
	public void writeFromMarker(OutputStream os, LineDecorator ld, FilePositionMarker marker) {
		writeFromMarker(new PrintWriter(os), ld, marker);		
	}
	
	public void writeFromMarker(Writer os, LineDecorator ld, FilePositionMarker marker) {
		writeFromMarker(new PrintWriter(os), ld, marker);		
	}
	
	/**
	 * write all lines collected since marker, to os, each line separated by separatedBy
	 */
	public void writeFromMarker(OutputStream os, final String separatedBy, FilePositionMarker marker) {
		writeFromMarker(os, new SeparateByDecorator(separatedBy) , marker);
	}
	
	public void writeFromMarker(Writer os, final String separatedBy, FilePositionMarker marker) {
		writeFromMarker(os, new SeparateByDecorator(separatedBy), marker);
	}

	private void writeFromMarker(PrintWriter pw, LineDecorator ld, FilePositionMarker marker) {
		try {
			String[] strs = getBufferFrom(marker);
			for (int i = 0; i < strs.length; i++) {
				String str = strs[i];
				pw.print(ld.decorated(str));
			}
		} finally {
			try {
				if (pw!=null) pw.flush();
			} catch (Exception e) {
//				ignore exception raised during stream flushing silently
			}
		}
	}
	
	protected static class SeparateByDecorator implements LineDecorator {
		private String separator;

		public SeparateByDecorator(String separator) {
			this.separator = separator;
		}
		
		public String decorated(String line) {
			return line + separator;
		}
		
	}

}
