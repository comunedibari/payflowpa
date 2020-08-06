package it.nch.utility;

import it.tasgroup.iris.web.Theme;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

public class LogoTag extends TagSupport {

	private String abi;

	public String getAbi() {
		return abi;
	}

	public void setAbi(String abi) {
		this.abi = abi;
	}

	
	/*
	 * <a title="<html-ext:theme code="logoTitle"/>"
	 * href="<html-ext:theme code="logoLink"/>"> <img
	 * src="<html-ext:theme code="logo"/>" alt="Tas Group"> </a>
	 */
	public int doStartTag() throws JspException {

		Theme theme = (Theme) TagUtils.getInstance().lookup(pageContext, Theme.DEFAULT_ATTRIBUTE_NAME, null);
		if (theme == null) {
			return (SKIP_BODY); // Nothing to output
		}

		String logoTitle = null;
		String logoLink = null;
		String logoSrc = null;

		if (abi != null && !abi.isEmpty()) {
			logoTitle = theme.getCode(abi + ".logoTitle");
			logoLink = theme.getCode(abi + ".logoLink");
			logoSrc = theme.getCode(abi + ".logo");
		}
		// default
		if (logoTitle == null || logoLink == null || logoSrc == null) {
			logoTitle = theme.getCode("logoTitle");
			logoLink = theme.getCode("logoLink");
			logoSrc = theme.getCode("logo");
		}

		StringBuilder htmlFragment = new StringBuilder();
		htmlFragment.append("<a title='").append(logoTitle).append("'  href='").append(logoLink).append("'>");
		htmlFragment.append("<img src='").append(logoSrc).append("' />");
		htmlFragment.append("</a>");

		TagUtils.getInstance().write(pageContext, htmlFragment.toString());

		// Continue processing this page
		return (SKIP_BODY);

	}

	public void release() {
		super.release();
		abi = null;
	}

}
