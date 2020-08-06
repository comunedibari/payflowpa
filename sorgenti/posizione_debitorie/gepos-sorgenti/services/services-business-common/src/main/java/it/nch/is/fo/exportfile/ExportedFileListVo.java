package it.nch.is.fo.exportfile;

import it.nch.fwk.fo.dto.business.Pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * 
 * @author sadpa
 * Created 03/07/2007
 */
public class ExportedFileListVo implements Pojo {
	/**
	 * Contains the filters used for extracting the list exportedFileList
	 */
	public ExportFileVo sample;

	/**
	 * List of exported files. Each element is of type ExportFileVo
	 */
	public ArrayList exportedFileList;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String NL = "\r\n";
		
		if (exportedFileList == null || exportedFileList.size() == 0) {
			sb.append(NL);
			sb.append("<EMPTY LIST>");
		} else {
			Iterator it = exportedFileList.iterator();
			while (it.hasNext()) {
				ExportFileVo curr = (ExportFileVo)it.next();
				sb.append(NL);
				sb.append("fileName .................... " + curr.getFileName());
			}
		}

		return sb.toString();
	}

	// ==============================================
	//         GETTERS AND SETTERS
	// ==============================================

	public ArrayList getExportedFileList() {
		return exportedFileList;
	}

	public void setExportedFileList(ArrayList exportedFileList) {
		this.exportedFileList = exportedFileList;
	}

	public ExportFileVo getSample() {
		return sample;
	}

	public void setSample(ExportFileVo sample) {
		this.sample = sample;
	}

	// ==== POJO ====
	public Long getId() {
		return null;
	}

	public void setId(Long id) {
		return;
	}
		
}
