
package it.nch.eb.xsqlcmd.dbtrpos.model;

import it.nch.eb.common.utils.StringUtils;


public class OtfModel  implements java.io.Serializable   {

		private static final long serialVersionUID = -666L;
		private java.lang.String urlBack;
		private java.lang.String urlCancel;
		private java.lang.String offlineMethod;
	
	
		public java.lang.String getUrlBack() {
			return urlBack;
		}


		public void setUrlBack(java.lang.String urlBack) {
			this.urlBack = urlBack;
		}


		public java.lang.String getUrlCancel() {
			return urlCancel;
		}


		public void setUrlCancel(java.lang.String urlCancel) {
			this.urlCancel = urlCancel;
		}


		public java.lang.String getOfflineMethod() {
			return offlineMethod;
		}


		public void setOfflineMethod(java.lang.String offlineMethod) {
			this.offlineMethod = offlineMethod;
		}


	public String toString() {
		return StringUtils.getSimpleName(this.getClass()) + "\n" + StringUtils.toString(this);
	}
	
}