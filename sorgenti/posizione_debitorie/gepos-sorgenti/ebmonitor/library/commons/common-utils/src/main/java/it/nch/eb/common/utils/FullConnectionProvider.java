/**
 * Created on 12/giu/07
 */
package it.nch.eb.common.utils;

import java.sql.Connection;


/**
 * @author gdefacci
 */
public interface FullConnectionProvider extends ConnectionProvider {

	void closeConnection(Connection connection);
}
