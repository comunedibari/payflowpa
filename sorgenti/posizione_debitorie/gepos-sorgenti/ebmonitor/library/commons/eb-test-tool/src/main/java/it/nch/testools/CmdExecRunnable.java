/**
 * Created on 12/giu/08
 */
package it.nch.testools;

import java.io.IOException;

public class CmdExecRunnable implements Runnable {
	final String cmd;
	public CmdExecRunnable(String cmd) {
		this.cmd = cmd;
	}
	public void run() {
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}