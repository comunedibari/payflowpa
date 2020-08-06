package it.nch.fwk.fo.xml;

import java.io.PrintStream;
import java.io.PrintWriter;

public class XMLHelperException extends Exception {
	Throwable rootCause;
	
	XMLHelperException(String errorInfo) {
		this(errorInfo, null);
	}
	
	XMLHelperException(String errorInfo, Throwable rootCause) {
		super(errorInfo);
		this.rootCause = rootCause;
	}
	
	public void printStackTrace() {
		this.printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream ps) {
		printStackTrace(new PrintWriter(ps));
	}

	public void printStackTrace(PrintWriter pw) {
		pw.println("");
		super.printStackTrace(pw);
		if (rootCause != null) {
			pw.println();
			pw.println("Originated by:");
			rootCause.printStackTrace(pw);
		}
		pw.flush();
	}


}

