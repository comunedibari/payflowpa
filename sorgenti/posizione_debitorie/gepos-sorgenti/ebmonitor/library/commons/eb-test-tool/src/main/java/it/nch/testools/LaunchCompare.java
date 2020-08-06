/**
 * Created on 12/mag/08
 */
package it.nch.testools;

import it.nch.testools.CmdExecRunnable;

import java.io.File;

public class LaunchCompare {
	final String commandLine;
	final File left;
	final File right;
	public LaunchCompare(String commandLine, File left, File right) {
		super();
		this.commandLine = commandLine;
		this.left = left;
		this.right = right;
	}
	
	String replaceSlashes(String cmd ) { // FIXME: windows specific
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cmd.length(); i++) {
			char ch = cmd.charAt(i);
			if (ch == '/') sb.append("\\");
			else sb.append(ch);
		}
		return sb.toString();
	}
	
	public void exec() {
		String cmd1 = replaceSlashes(commandLine);
		String command = "\"" + cmd1 + "\"" + " \"" + left.getAbsolutePath() + "\" \"" + right.getAbsolutePath() + "\"";
//		new CmdExecRunnable(command).run();
		Thread thread = new Thread(new CmdExecRunnable(command));
		thread.setDaemon(true);
		thread.start();
	}
}