/**
 * 05/giu/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.operations;

import org.slf4j.Logger;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.dbtrpos.dao.OtfEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.OtfModelValidator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.insert.InsertPendenzeDao;
import it.nch.eb.xsqlcmd.dbtrpos.model.OtfModel;

/**
 * @author gdefacci
 */
public class OtfModelExecutionDispatcher  implements OtfEffect {
	
	private static final Logger log = ResourcesUtil.createLogger(OtfModelExecutionDispatcher.class);

	//set hidden
	public boolean hidden = false;
	
	public OtfModelExecutionDispatcher(
			OtfModelValidator insert) {
		super();
	}

	public void apply(OtfModel model) {
//		OtfModelValidator executor = getExecutor(model);			
//		
//		executor.apply(model);
//		we ignore those errors, they have been are already managed
		 
		log.info(" dati OTF, urlBack " + model.getUrlBack());
		log.info(" dati OTF, urlCancel " + model.getUrlCancel());
		log.info(" dati OTF, offlineMethod  " + model.getOfflineMethod());
		
		
	}

	
	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	
}
