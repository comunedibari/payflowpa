/**
 * Created on 17/ott/08
 */
package it.nch.eb.common.utils.loaders;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author gdefacci
 */
public class ReadersFactories {
	
	public static final ReadersFactories instance = new ReadersFactories();
	
	private static abstract class BaseNamedReaderFactory implements InputsFactory {

		private final String	name;

		public BaseNamedReaderFactory(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
	}

	public InputsFactory classpath(final String classpathName) {
		if (classpathName==null) throw new NullPointerException("classpathName");
		return new BaseNamedReaderFactory("classpath://" + classpathName) {

			public Reader createReader() {
				return ResourceLoaders.CLASSPATH.loadReader(classpathName);
			}

			public InputStream createStream() {
				return ResourceLoaders.CLASSPATH.loadInputStream(classpathName);
			}
			
		};
	}
	
	public InputsFactory classpathAnt(final String classpathName) {
		if (classpathName==null) throw new NullPointerException("classpathName");
		return new BaseNamedReaderFactory("classpath://" + classpathName) {

			public Reader createReader() {
				return new InputStreamReader( createStream() );
			}

			public InputStream createStream() {
				InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(classpathName);
				if (resourceAsStream==null) throw new RuntimeException("cant find the classpath resource \"" + classpathName + "\"");
				return resourceAsStream;
			}
			
		};
	}
	
	public InputsFactory fileSystem(final String fsName) {
		if (fsName==null) throw new NullPointerException("fsName");
		return new BaseNamedReaderFactory("file://" + fsName) {

			public Reader createReader() {
				return ResourceLoaders.FILESYSTEM.loadReader(fsName);
			}

			public InputStream createStream() {
				return ResourceLoaders.FILESYSTEM.loadInputStream(fsName);
			}
			
		};
	}
	
	public InputsFactory file(final File f) {
		if (f==null) throw new NullPointerException("f");
		return new BaseNamedReaderFactory("file://" + f.getAbsolutePath()) {

			public Reader createReader() {
				try {
					return new FileReader(f);
				} catch (Exception e) {
					throw new RuntimeException("error creating reader from file " + f.getAbsolutePath(), e);
				}
			}

			public InputStream createStream() {
				try {
					return new FileInputStream(f);
				} catch (Exception e) {
					throw new RuntimeException("error creating input stream from file " + f.getAbsolutePath(), e);
				}
			}
			
		};
	}

	public InputsFactory string(final String str) {
		return string(str, null);
	}
	
	public InputsFactory string(final String str, final String encoding) {
		if (str==null) throw new NullPointerException("str");
		return new BaseNamedReaderFactory("string://" + str) {

			public Reader createReader() {
				return new StringReader(str);
			}

			public InputStream createStream() {
			try {
					String enc = encoding==null ? "UTF-8" : encoding;
					InputStream is = new ByteArrayInputStream(str.getBytes(enc));
					return is;
				} catch (Exception e) {
					throw new RuntimeException("error creating input stream from string " + str + " using encoding", e);
				}
			}
			
		};
	}
	public InputsFactory uri(final URI uri) {
		if (uri==null) throw new NullPointerException("uri");
		return new BaseNamedReaderFactory("uri://" + uri) {

			public Reader createReader() {
				try {
					URLConnection  conn = new URL(uri.toString()).openConnection();
					return new InputStreamReader(conn.getInputStream());
				} catch (Exception e) {
					throw new RuntimeException(e);
				} 
			}

			public InputStream createStream() {
				try {
					URLConnection  conn = new URL(uri.toString()).openConnection();
					return conn.getInputStream();
				} catch (Exception e) {
					throw new RuntimeException("error opening stream at " + uri);
				} 
			}
			
		};
	}
		
	
}
