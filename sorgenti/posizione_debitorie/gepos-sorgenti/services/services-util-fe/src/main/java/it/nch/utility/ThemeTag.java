package it.nch.utility;

import it.tasgroup.iris.web.Theme;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class ThemeTag extends TagSupport {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    /**
     * Name of the bean that contains the data we will be rendering.
     */
    protected String name = Theme.DEFAULT_ATTRIBUTE_NAME;

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Name of the property to be accessed on the specified bean.
     */
    protected String property = null;

    public String getProperty() {
        return (this.property);
    }

    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * The scope to be searched to retrieve the specified bean.
     */
    protected String scope = null;

    public String getScope() {
        return (this.scope);
    }

    public void setScope(String scope) {
        this.scope = scope;
    }



    // --------------------------------------------------------- Public Methods

    /**
     * Process the start tag.
     *
     * @exception javax.servlet.jsp.JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

        if (TagUtils.getInstance().lookup(pageContext, name, scope) == null) {
            return (SKIP_BODY); // Nothing to output
        }


        Theme theme = (Theme)TagUtils.getInstance().lookup(pageContext, name, property, scope);


        if (theme == null) {
            return (SKIP_BODY); // Nothing to output
        }



        String codeVal = theme.getCode(code);
        TagUtils.getInstance().write(pageContext, codeVal);

        // Continue processing this page
        return (SKIP_BODY);

    }


    /**
     * Release all allocated resources.
     */
    public void release() {
        super.release();
        name = null;
        property = null;
        scope = null;
    }

}
