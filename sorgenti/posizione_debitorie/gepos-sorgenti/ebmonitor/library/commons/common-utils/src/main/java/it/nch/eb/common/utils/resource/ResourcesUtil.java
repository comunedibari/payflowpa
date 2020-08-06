/**
 * Created on 05/giu/07
 */
package it.nch.eb.common.utils.resource;

//import it.nch.eb.common.utils.loaders.ResourceLoaders;
//import it.nch.eb.common.utils.log.DefaultLoggerFactory;
//import it.nch.eb.common.utils.log.Logger;
//import it.nch.eb.common.utils.log.LoggerFactory;

import it.nch.eb.common.utils.loaders.ResourceLoaders;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gdefacci
 */
public class ResourcesUtil implements ResourceLoaders {
	
	private static final int DEFAULT_BUFFER_SIZE = 4096;
//	private static final String	CHARSET_PROPERTY	= "charset";
//	private static final String	CHARSET_FILE	= "charset.properties";
	
//	singleton charset converter
//	private static CharsetConverter charsetConversions = new CharsetConverter(CHARSET_FILE,  CHARSET_PROPERTY );
	
	public static final Logger	logger = createLogger(ResourcesUtil.class.getName());

//	public static String getCharsetName() {
//		return charsetConversions.getCharsetName();
//	}
//
//	public static void setCharsetName(String charsetName) {
//		charsetConversions = new CharsetConverter(charsetName);
//	}
	
//	private static String DEFAULT_LOGGER_FACTORY_NAME = "log4j";
//	private static String LOGGGER_NAME_SYSTEM_PROPERTY = "it.nch.logger.factory.name";
//	private static LoggerFactory loggerFactory;
//	
//	private static synchronized LoggerFactory setupLoggerFactory() {
//		String ln = System.getProperty("it.nch.logger.factory.name");
//		if (ln==null) ln = "log4j";
//		loggerFactory = getLoggerFactory(ln);
//		return loggerFactory;
//	}
	
//	private static synchronized LoggerFactory getLoggerFactory() {
//		if (loggerFactory == null) {
//			loggerFactory = new DefaultLoggerFactory();
//		}
//		return loggerFactory;
//	}
	
//	private static LoggerFactory getLoggerFactory(String nm) {
//		try {
//			Field fld = AvaiableLoggers.class.getField(nm);
//			return (LoggerFactory) fld.get(null);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} 
//	}
	
	/**
	 * FIXME: remove this method after freemarker removal
	 * return the url associtaed with the classpath resource identified by name
	 * @param name
	 * @return
	 */
	public static URL getURL(String name) {
		return getDefaultClassLoader().getResource(name);
	}
	
	public static ClassLoader getDefaultClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
	
//	public static InputStream asInputStream(String s) {
//		byte[] buf;
//		buf = charsetConversions.asByteArray(s);
//		return new ByteArrayInputStream(buf);
//	}
//	
//	public static String getContent(InputStream is) throws IOException, UnsupportedEncodingException  {
//		byte[] buf = asBytes(is);
//		return charsetConversions.asString(buf);
//	}

	public static byte[] asBytes(InputStream is) throws IOException {
		DataInputStream dis = new DataInputStream(is);
		byte[] buf = new byte[is.available()];
		dis.readFully(buf);
		return buf;
	}
	
//	public static String asString(byte[] buf) {
//		return charsetConversions.asString(buf);
//	}
	
	public static Logger createLogger(String name) {
		return LoggerFactory.getLogger(name);
	}
	
	public static Logger createLogger(Class klass) {
		return LoggerFactory.getLogger(klass);
	}
	
	public static Logger log() {
		return logger ;
	}

	public static void close(Object anytingCloseable) {
		try {
			if (anytingCloseable!=null) {
				Method mthd = anytingCloseable.getClass().getMethod("close", (Class[])null);
				mthd.invoke(anytingCloseable, (Object[])null);
			}
		} catch (Exception e) {
			logger.error("ignored exception (got closing a stream/reader/smthing else)", e);
		} 
	}
	public static void closeStream(InputStream is) {
		try {
			is.close();
		} catch (IOException e) {
			logger.error("ignored exception (got closing consumed stream)" , e);
		}
	}
	
	public static void closeStream(OutputStream os) {
		if (os!=null) {
			try {
				os.close();
			} catch (IOException e) {
				logger.error("ignored exception (got closing consumed stream)" , e);
			}
		}
	}
	
	public static void closeStream(PrintStream os) {
		if (os!=null) {
			try {
				os.close();
			} catch (Exception e) {
				logger.error("ignored exception (got closing consumed stream)" , e);
			}
		}
	}
	
	public static boolean fileCompare(File file1, File file2) {
		FileInputStream f1 = null;
		FileInputStream f2 = null;
		try {
			f1 = new FileInputStream(file1);
			f2 = new FileInputStream(file2);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return fileCompare(file1.getAbsolutePath(), file2.getAbsolutePath(), f1, f2);
	}

	public static boolean fileCompare(InputStream f1, InputStream f2) {
		return fileCompare("file1", "file2", f1, f2);
	}
	
	public static boolean fileCompare(String name1, String name2, InputStream f1, InputStream f2) {
		try {
			LineNumberReader r1 = new LineNumberReader(new InputStreamReader(f1));
			LineNumberReader r2 = new LineNumberReader(new InputStreamReader(f2));
			while (true) {
				String s1 = r1.readLine();
				String s2 = r2.readLine();
				if (s1==null && s2==null) return true;
				else if (s1!=null && s2!=null) {
					if (!s1.equals(s2)) return false;
				}
				else return false;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(f1);
			close(f2);
		}
	}
	
	

	

	public static void flush(Object anytingCloseable) {
		try {
			if (anytingCloseable!=null) {
				Method mthd = anytingCloseable.getClass().getMethod("flush", (Class[])null);
				mthd.invoke(anytingCloseable, (Object[])null);
			}
		} catch (Exception e) {
			logger.error("ignored exception (got flushing a stream/reader/smthing else)", e);
		} 
	}
	
	public static String asString(Reader  r) {
        try {
			StringBuffer content = new StringBuffer();
			char[] cs = new char[4096];
			int read = -1;
			do {
			    read = r.read(cs);
			    if (read == -1) {
			        return content.toString();
			    }
			    content.append(cs, 0, read);
			} while (true);
		} catch (IOException e) {
			throw new RuntimeException("error punting the reader in a string " , e);
		}
    }
	
	public static void copyDirectoryContent(File srcPath, File dstPath) {
		copyDirectoryContent(srcPath, dstPath, 4096);
	}
	public static void copyDirectoryContent(File srcPath, File dstPath, int buffSize) {
		
		if (srcPath.isDirectory()) {
			if (!dstPath.exists()) mkDirs(dstPath);

			String files[] = srcPath.list();
			for (int i = 0; i < files.length; i++) {
				copyDirectoryContent(new File(srcPath, files[i]), new File(dstPath, files[i]), buffSize);
			}
		} else {
			if (!srcPath.exists()) {
				throw new IllegalStateException("the source folder " + srcPath + " does not exists");
			} else {

				copyFile(srcPath, dstPath, buffSize);

			}
		}

		logger.info("copied " + srcPath + " in " + dstPath);

	}

	public static boolean mkDirs(File dstPath) {
		try {
			return dstPath.mkdirs();
		} catch (Exception e) {
			throw new RuntimeException("error creting folder " + dstPath);
		}
	}

	public static void copyFile(File srcPath, File dstPath) {
		copyFile(srcPath, dstPath, DEFAULT_BUFFER_SIZE);
	}
	
	public static void copyFile(InputStream in, File out) {
		FileOutputStream fout = null;
		try {
			if (!out.getParentFile().exists()) {
				out.getParentFile().mkdirs();
				out.createNewFile();
			}
			fout = new FileOutputStream(out);
			copyFile(in, fout);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeStream(fout);
		}
	}
	
	public static void copyFile(InputStream in, OutputStream out) {
		copyFile(in, out, DEFAULT_BUFFER_SIZE);
	}
	public static void copyFile(InputStream in, OutputStream out, int buffSize) {
		try {
			byte[] buf = new byte[buffSize];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(in);
			close(out);
		}
	}
	
	public static void copyFile(File srcPath, File dstPath, int buffSize) {
		InputStream in = null; 
		OutputStream out = null;
		try {
			in = new FileInputStream(srcPath);
			out = new FileOutputStream(dstPath);
			byte[] buf = new byte[buffSize];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			throw new RuntimeException("error coping from directory " + srcPath 
					+ " to  destination " + dstPath, e);
		} finally {
			close(in);
			close(out);
		}
	}

    public static boolean deleteFolder(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteFolder(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
    
        // The directory is now empty so delete it
        return dir.delete();
    }

	public static void store(String dataContent, File file) {
		FileOutputStream out = null;
		PrintStream ps = null;
		try {
			out = new FileOutputStream(file);
			ps = new PrintStream(out);
			ps.print(dataContent);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeStream(out);
			closeStream(ps);
		}
		
	} 
}
