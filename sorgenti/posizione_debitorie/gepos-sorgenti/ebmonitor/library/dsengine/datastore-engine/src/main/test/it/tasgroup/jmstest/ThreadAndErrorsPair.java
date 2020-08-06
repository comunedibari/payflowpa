/**
 * 18/nov/2009
 */
package it.tasgroup.jmstest;

import it.nch.eb.xsqlcmd.jms.Function0;
import it.nch.fwk.checks.errors.QualifiedError;

import java.util.Set;

public class ThreadAndErrorsPair {
	private final Function0<Set<QualifiedError>> errsProvider;
	private final Thread thread;
	private final String name;
	
	public ThreadAndErrorsPair(String name, Function0<Set<QualifiedError>> errsProvider,
			Thread thread) {
		this.name = name;
		this.errsProvider = errsProvider;
		this.thread = thread;
	}

	public Set<QualifiedError> getErrors() {
		return errsProvider.apply();
	}

	public Thread getThread() {
		return thread;
	}

	public String getName() {
		return name;
	}

}