package it.tasgroup.idp.bean;

import it.tasgroup.idp.pojo.StoricoData;

import javax.ejb.Remote;

public abstract class AbstractExecutorRemote implements SpazioCommandExecutor { //implements ExecutorRemote {
	
	public String executeHtml(String id) throws Exception {
		return null;
    }

	@Override
	public StoricoData executeApplicationTransaction(String data) {
		return null;
	}

	@Override
	public StoricoData executeApplicationTransaction(Object data) {
		return null;
	}

	@Override
	public StoricoData executeApplicationTransaction() {
		return null;
	}

	@Override
	public String executeHtml() throws Exception {
		return null;
	};

    
}
