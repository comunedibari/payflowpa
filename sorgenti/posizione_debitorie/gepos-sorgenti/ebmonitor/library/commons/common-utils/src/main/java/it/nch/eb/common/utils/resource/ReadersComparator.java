/**
 * Created on 22/gen/09
 */
package it.nch.eb.common.utils.resource;

import it.nch.eb.common.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * @author gdefacci
 */
public class ReadersComparator {

	public static interface LineComparator {
		boolean apply(String l1, String l2);
	}
	
	public static abstract class NullSafeLineComaprator implements LineComparator {

		public boolean apply(String l1, String l2) {
			if (l1==null && l2==null) return true;
			else if (l1!=null && l2!=null) return compareLines(l1, l2);
			else return false;
		}

		protected abstract boolean compareLines(String l1, String l2);
	}
	
	public static final LineComparators lineComparators = new LineComparators();
	
	static class LineComparators {
		
		public final LineComparator identity = new NullSafeLineComaprator() {

			protected boolean compareLines(String l1, String l2) {
				return l1.equals(l2);
			}
			
		};
		
		public final LineComparator ignoreTrailingSpaces = new NullSafeLineComaprator() {

			protected boolean compareLines(String l1, String l2) {
				String tl1 = StringUtils.trimTrailing(l1);
				String tl2 = StringUtils.trimTrailing(l2);
				return tl1.equals(tl2);
			}
			
		};
	}

	private final LineComparator	lineComparator;
	
	public ReadersComparator() {
		this(lineComparators.ignoreTrailingSpaces);
	}

	public ReadersComparator(final LineComparator lineComparator) {
		this.lineComparator = lineComparator;
	}

	public boolean compareFiles(String file1, String file2) {
		try {
			return compareReaders(new FileReader(file1), new FileReader(file2));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("error comparing files\n1)" + file1 + "\n2)" + file2, e);
		}
	}

	public boolean compareReaders(Reader in1, Reader in2) {
		return compareBufferedReaders(new BufferedReader(in1), new BufferedReader(in2));
	}

	private boolean compareBufferedReaders(BufferedReader in1, BufferedReader in2) {
		try {
			String line1 = in1.readLine();
			String line2 = in2.readLine();
			int lineNum = 1;
			while (line1 != null && lineComparator.apply(line1, line2)) {
				line1 = in1.readLine();
				line2 = in2.readLine();
				lineNum++;
			}
			if (line1 != null || line2 != null) return false;
			else
				return true;
		} catch (Exception e) {
			throw new RuntimeException("error while comapring readers ", e);
		} finally {
			ResourcesUtil.close(in1);
			ResourcesUtil.close(in2);
		}
	}

}
