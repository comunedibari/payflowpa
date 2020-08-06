/**
 * Created on 17/ott/07
 */
package it.nch.eb.common.utils.test;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.common.utils.RegexpUtils;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 * @author gdefacci
 */
public class DbTestUtils {
	
	private static final String	COLUMN_SEPARATOR	= " | ";
	private static final int	MAX_SIZE	= 40;
	
	public static class RSColumn {
		String name;
		int size;
	}
	
	/**
	 * @param rs
	 * @param conn
	 */
	public static void printResultSet(ResultSet rs, Connection conn, PrintStream out) {
		try {
			if (rs!=null && conn!=null) {
				ResultSetMetaData rsMetaData = rs.getMetaData();
				RSColumn[] columns = getColumnHeaders(rsMetaData);
				printHeader(columns, out);
				while (rs.next()) {
					printResultSetRow(rs, columns, out);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static RSColumn[] getColumnHeaders(ResultSetMetaData metadata) throws SQLException {
		int cnt = metadata.getColumnCount();
		RSColumn[] columns = new RSColumn[cnt];
		for (int i = 1; i <= cnt; i++) {
			RSColumn column = new RSColumn();
			column.name = metadata.getColumnName(i);
			int sz = metadata.getColumnDisplaySize(i);
			column.size = sz < MAX_SIZE ? sz : MAX_SIZE;
			columns[i-1] = column;
		}
		return columns;
	}

	public static void printResultSetRow(ResultSet rs, RSColumn[] columns, PrintStream out) throws SQLException {
		for (int i = 1; i <= columns.length; i++) {
			String val = rs.getString(i);
			print(val, columns[i-1].size, out);
		}
		out.print("\n");
	}
	
	public static void printHeader(RSColumn[] columns, PrintStream out) {
		int sizeSum =0;
		out.print("\n");
		for (int i = 1; i <= columns.length; i++) {
			String val = columns[i-1].name;
			print(val, columns[i-1].size, out);
			sizeSum = sizeSum + columns[i-1].size + 3;
		}
		out.print("\n" + RegexpUtils.makeFiller("-", sizeSum)+ "\n");
	}

	public static void print(String val, int size, PrintStream out) {
		String cpy = val!=null ? val : "NULL";
		out.print(RegexpUtils.fill(cpy, size, " ", Alignments.LEFT) + COLUMN_SEPARATOR);
	}
}
