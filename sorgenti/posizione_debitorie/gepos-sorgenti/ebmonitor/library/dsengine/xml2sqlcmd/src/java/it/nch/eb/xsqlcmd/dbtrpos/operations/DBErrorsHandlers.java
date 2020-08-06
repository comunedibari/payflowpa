/**
 * 09/giu/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.operations;

import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.model.OtfModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import java.util.ArrayList;
import java.util.List;


/**
 * test DBErrorsHandler, do not use in production enviroment. 
 * Those handlers, do not work well, when processing large files, since they store in memory all
 * errors 
 * @author gdefacci
 */
public class DBErrorsHandlers {

	public static class Simple implements DBErrorsHandler {
		
		private final List allErrors = new ArrayList();
		private final DBErrorsHandler delegate; 
		
		public Simple() {
			this(null);
		}
		
		public Simple(DBErrorsHandler delegate) {
			this.delegate = delegate;
		}

		/* @Override */
		public void onError(DBError[] errors, PendenzeModel pendenza) {
			if (delegate!=null) delegate.onError(errors, pendenza);
			if (errors!=null && errors.length > 0) {
				for (int i = 0; i < errors.length; i++) {
					allErrors.add(errors[i]);
				}
			}
		}

		public List getAllErrors() {
			return allErrors;
		}

		@Override
		public void onError(DBError[] errors, OtfModel otf) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public static class ByCategory implements DBErrorsHandler {
		
		private final List insert = new ArrayList(); 
		private final List update = new ArrayList(); 
		private final List replace = new ArrayList(); 
		private final List delete = new ArrayList(); 
		
		private final DBErrorsHandler delegate;
		
		public ByCategory() {
			this(null);
		}
		
		public ByCategory(DBErrorsHandler delegate) {
			this.delegate = delegate;
		}

		/* @Override */
		public void onError(DBError[] errors, PendenzeModel pendenza) {
			if (delegate!=null) delegate.onError(errors, pendenza);
			if (errors!= null && errors.length > 0) {
				for (int i = 0; i < errors.length; i++) {
					if (pendenza.getInsert().booleanValue()) insert.add(errors[i]);
					else if (pendenza.getUpdate().booleanValue()) update.add(errors[i]);
					else if (pendenza.getReplace().booleanValue()) replace.add(errors[i]);
					else if (pendenza.getDelete().booleanValue()) delete.add(errors[i]);
					else throw new IllegalStateException("should not happen");
				}
			}
		}

		public List getInsert() {
			return insert;
		}

		public List getUpdate() {
			return update;
		}

		public List getReplace() {
			return replace;
		}

		public List getDelete() {
			return delete;
		}

		@Override
		public void onError(DBError[] errors, OtfModel otf) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	/** this DBErrorsHandler can be used even in a production environment, even if it's not so useful. */
	public static class Fake implements DBErrorsHandler {

		private int numberOfErrors = 0;
		
		public void onError(DBError[] errors, PendenzeModel pendenza) {
			if (errors!=null) numberOfErrors += errors.length;
		}

		public int getNumberOfErrors() {
			return numberOfErrors;
		}

		@Override
		public void onError(DBError[] errors, OtfModel otf) {
			// TODO Auto-generated method stub
			
		}

	}
}
