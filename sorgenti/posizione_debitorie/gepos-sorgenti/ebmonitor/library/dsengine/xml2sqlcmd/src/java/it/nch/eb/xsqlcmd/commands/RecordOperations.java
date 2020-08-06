/**
 * 23/apr/2009
 */
package it.nch.eb.xsqlcmd.commands;

/**
 * @author gdefacci
 */
public interface RecordOperations {

	RecordOperationEnum insert = new RecordOperationEnum("insert");
	RecordOperationEnum update = new RecordOperationEnum("update");
	RecordOperationEnum replace = new RecordOperationEnum("replace");
	RecordOperationEnum delete = new RecordOperationEnum("delete");
	RecordOperationEnum updateMassivo = new RecordOperationEnum("updateMassivo");

}
