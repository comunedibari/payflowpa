/**
 * 19/mag/2009
 */
package it.nch.eb.xsqlcmd.utils;

import java.sql.Connection;

/**
 * @author gdefacci
 */
public interface ConnectionEffect {
	void apply(Connection conn) throws Exception;
}