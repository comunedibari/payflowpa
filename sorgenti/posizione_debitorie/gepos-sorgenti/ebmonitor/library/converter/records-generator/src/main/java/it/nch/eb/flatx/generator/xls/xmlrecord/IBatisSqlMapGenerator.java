/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.generator.JavaGenerator;
import it.nch.eb.flatx.generator.xls.XlsUtil;
import it.nch.eb.flatx.generator.xls.recordimpl.DBConvertersMap;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;

/**
 * @author gdefacci
 *
 */
public class IBatisSqlMapGenerator implements IXlsSheetGenerator {

	private final Xml2SqlCmdFullRecordGenerator mainGenerator;
	private DBConvertersMap typesMap;

	public IBatisSqlMapGenerator(Xml2SqlCmdFullRecordGenerator mainGenerator, DBConvertersMap typesMap) {
		this.mainGenerator = mainGenerator;
		this.typesMap = typesMap;
	}

	public void generate(String name, Sheet sheet) {
		
		final File basePkgFolder = mainGenerator.getIbatisTargetFolder() == null ? 
				JavaGenerator.createPackageFolder(mainGenerator.getBaseFolder(), mainGenerator.getPackageName()) : 
				mainGenerator.getIbatisTargetFolder();

		final XRowInfoUtils utils = XRowInfoUtils.instance;
		final String recName = utils.toHypenedLowercaseName( name ) ;
		final String baseName = utils.toJavaClassName( name );
		
		final String javaModelName = baseName + "Model";
		final String modelClassName = mainGenerator.getModelsPackageName() + "." + javaModelName;
		
		final File fsql = new File(basePkgFolder, recName+ ".xml");
		
		XmlRecord2IbatisInsertRenderer sqlGenerator = new XmlRecord2IbatisInsertRenderer();
		
		XRowInfo[] infos = utils.getAllCellsInfosArray(sheet, mainGenerator.getStartRow(), mainGenerator.getColumnMappings());
		String resultMapId = baseName + "ResultMap";
		IBatisResultMapModel resultMapModel = new IBatisResultMapModel(resultMapId, modelClassName);
		for (int i = 0; i < infos.length; i++) {
			XRowInfo rowInfo = infos[i];
			String epuredName = rowInfo.getName().replaceAll("&", "");
			IBatisResultPropertyModel item = new IBatisResultPropertyModel( rowInfo.getJavaName(), epuredName );
			resultMapModel.add(item);
		}
		
		InsertStatementModel insertStmMdl = createInsertStatementModel("insert" + baseName, sheet);
		UpdateStatementModel updtStmMdl = createUpdateStatementModel("update" + baseName, sheet);
		String typeAliasName = javaModelName + "Type";
		
		UpdateStatementModel[] updateStatementModels;
		UpdateStatementModel[] furthUpd = getFurtherUpdateStatementModel(sheet);
		if (furthUpd.length == 0) updateStatementModels = new UpdateStatementModel[] {updtStmMdl };
		else {
			updateStatementModels = new UpdateStatementModel[ furthUpd.length + 1 ];
			System.arraycopy(furthUpd, 0, updateStatementModels, 1, furthUpd.length);
			updateStatementModels[0] = updtStmMdl;
		}
		
		
		OutputStream fosSql = null;
		try {
			fosSql = XlsUtil.fileStream(fsql);
			sqlGenerator.generate(fosSql, modelClassName, typeAliasName, name, resultMapModel, insertStmMdl, updateStatementModels );
			System.out.println("created " + fsql);
		} finally {
			ResourcesUtil.close(fosSql);
		}
	}

	
	private UpdateStatementModel createUpdateStatementModel (String stmName, Sheet sheet) {
		int colIdx = mainGenerator.getColumnMappings().getDescriptionColumnIdx();
		return createUpdateStatementModel(stmName, sheet, colIdx);
	}
	
	public UpdateStatementModel[] getFurtherUpdateStatementModel(Sheet sheet) {
		int colIdx = mainGenerator.getColumnMappings().getColumnMaxValue() + 1;
		List res = new ArrayList();
		boolean finished = false;
		for (int i=colIdx ; !finished; i++) {
			if (isDescriptionColumn(sheet, i)) {
				Cell heder = XlsUtil.getCell(sheet, i, 0);
				String cont = (heder != null ) ? XlsUtil.getContent(heder) : null;
				if (cont == null) throw new NullPointerException("column header for col " + i + " sheet " + sheet.getName());
				UpdateStatementModel updtStmMdl = createUpdateStatementModel("update" + cont, sheet, i);
				res.add(updtStmMdl);
			} else {
				finished = true;
			}
		}
		if (res.isEmpty()) return new UpdateStatementModel[0];
		else return (UpdateStatementModel[]) res.toArray(new UpdateStatementModel[0]);
	}
	
	
	private boolean isDescriptionColumn(Sheet sheet, int colIdx) {
		int dscIdx = mainGenerator.getColumnMappings().getDescriptionColumnIdx();
		return XlsUtil.columnsMatchStarting(sheet, dscIdx, colIdx, mainGenerator.getStartRow());
	}

	private UpdateStatementModel createUpdateStatementModel(String stmName,
			Sheet sheet, int colIdx) {
		final int startRow = mainGenerator.getStartRow();
		XRowInfoUtils utils = XRowInfoUtils.instance;
		CellFilter updateColsFilter = new UpdateColumnsCellFilter(mainGenerator.getColumnMappings(), colIdx);
		XRowInfo[] updCols = withTypesMapped( utils.getFilteredCellsInfosArray(sheet, startRow, colIdx, updateColsFilter) );
		CellFilter pkColsFilter = new UpdateWhereClauseCellFilter(mainGenerator.getColumnMappings(), colIdx);
		XRowInfo[] pkCols = withTypesMapped( utils.getFilteredCellsInfosArray(sheet, startRow, colIdx, pkColsFilter ) );
		return new UpdateStatementModel(stmName, updCols, pkCols);
	}
	
	private InsertStatementModel createInsertStatementModel (String stmName, Sheet sheet) {
		final int startRow = mainGenerator.getStartRow();
		XRowInfo[] xris = withTypesMapped(  XRowInfoUtils.instance.getAllCellsInfosArray(sheet, startRow, mainGenerator.getColumnMappings()) );
		return new InsertStatementModel(stmName, xris);
	}
	
	XRowInfo[] withTypesMapped(XRowInfo[] xris) {
		XRowInfo[]  res = new XRowInfo [xris.length];
		for (int i = 0; i < xris.length; i++) {
			XRowInfo xri = xris[i];
			String decodedDbType = typesMap.get(xri.getTypeName()).getAlias().toUpperCase();
			res[i] = xri.withType(decodedDbType);
		}
		return res;
	}
	
}
