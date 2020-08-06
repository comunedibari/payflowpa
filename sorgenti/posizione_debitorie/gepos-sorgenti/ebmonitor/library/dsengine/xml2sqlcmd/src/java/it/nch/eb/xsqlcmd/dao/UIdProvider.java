/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dao;

import java.sql.Connection;

/**
 * @author gdefacci
 */
public interface UIdProvider {
	
	long nextVal(Connection conn);

}
