/*
 * Copyright (C) 2004, 2007 Riccardo Solmi.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 */
package it.nch.eb.common.utils.bindings;


/**
 * @author Riccardo Solmi
 */
public interface IBindingManager extends IBindingScope {

	public void enterScope();
	public void exitScope();
	public void enterScope(IBindingScope scope);
}