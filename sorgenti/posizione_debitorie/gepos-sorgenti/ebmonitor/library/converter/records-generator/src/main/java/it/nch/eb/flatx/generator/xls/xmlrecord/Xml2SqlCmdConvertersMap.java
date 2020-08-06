/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.flatx.generator.xls.recordimpl.AliasedGenTypeModel;
import it.nch.eb.flatx.generator.xls.recordimpl.DBConvertersMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gdefacci
 *
 */
public class Xml2SqlCmdConvertersMap extends DBConvertersMap {
	
	public final static AliasedGenTypeModel EMTPY = new AliasedGenTypeModel("varchar", null,null);
	
	private Map/*<String, String>*/ dbJavaTypesMap = new HashMap();
	
	public AliasedGenTypeModel get(String typeName) {
		AliasedGenTypeModel res = super.get(typeName);
		if (res == null) {
			res = EMTPY; 
		}
		return res;
	}
	
	public void putDbJava(String dbType, String javaType) {
		dbJavaTypesMap.put(dbType.toLowerCase(), javaType);
	}
	
	public String getJavaType(String alias) {
		String dbType = get(alias).getAlias();
		return getJavaTypeByDbType(dbType);
	}
	
	public String getJavaTypeByDbType(String dbType) {
		String res = (String) dbJavaTypesMap.get(dbType.toLowerCase());
		if (res==null) throw new IllegalArgumentException("cant find a java type for " + dbType);
		return res;
	}
	
	public static Xml2SqlCmdConvertersMap defaultDbJavaMappings() {
		Xml2SqlCmdConvertersMap res = new Xml2SqlCmdConvertersMap();
		res.putDbJava("boolean"		  , "java.lang.Boolean"		);
		res.putDbJava("char"          , "java.lang.String"        );
		res.putDbJava("character"     , "java.lang.String"        );
		res.putDbJava("varchar"       , "java.lang.String"        );
		res.putDbJava("longvarchar"   , "java.lang.String"        );
		res.putDbJava("string"        , "java.lang.String"        );
		res.putDbJava("clob"          , "java.lang.String"        );
		res.putDbJava("tinyint"       , "java.lang.Integer"       );
		res.putDbJava("smallint"      , "java.lang.Integer"       );
		res.putDbJava("integer"       , "java.lang.Integer"       );
		res.putDbJava("int"           , "java.lang.Integer"       );
		res.putDbJava("bigint"        , "java.lang.Long"          );
		res.putDbJava("float"         , "java.lang.Double"        );
		res.putDbJava("double"        , "java.lang.Double"        );
		res.putDbJava("real"          , "java.lang.Double"        );
		res.putDbJava("numeric"       , "java.math.BigDecimal"    );
		res.putDbJava("decimal"       , "java.math.BigDecimal"    );
		res.putDbJava("date"          , "java.sql.Date"           );
		res.putDbJava("time"          , "java.sql.Timestamp"      );
		res.putDbJava("timestamp"     , "java.sql.Timestamp"      );
		res.putDbJava("binary"        , "byte[]"                  );
		res.putDbJava("varbinary"     , "byte[]"                  );
		res.putDbJava("longvarbinary" , "byte[]"                  );
		res.putDbJava("blob"          , "byte[]"                  );
		
		return res;
	}
	
}
