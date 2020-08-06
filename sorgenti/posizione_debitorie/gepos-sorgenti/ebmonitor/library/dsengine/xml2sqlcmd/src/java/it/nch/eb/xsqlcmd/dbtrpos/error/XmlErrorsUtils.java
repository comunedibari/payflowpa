/**
 * 23/ott/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.error;

/**
 * @author gdefacci
 */
public class XmlErrorsUtils {
	
	public static final XmlErrorsUtils instance = new XmlErrorsUtils();
	
	public String describe(StringBufferDescribing descr) {
		StringBuffer sb = new StringBuffer();
		descr.describeTo(sb);
		return sb.toString();
	}
	
	public StringBufferDescribing xmlWrap(final String tag, final Object content) {
		return xmlWrap(tag, content.toString());
	}
	
	public StringBufferDescribing xmlWrap(final String tag, final String content) {
		return xmlWrap(tag, new StringBufferDescribing() {

			public void describeTo(StringBuffer sb) {
				sb.append(content);
			}
			
		});
	}
	
	public StringBufferDescribing xmlWrap(final String tag, final StringBufferDescribing content) {
		return xmlWrap(tag, new StringBufferDescribing[] { content }); 
	}
	
	public StringBufferDescribing xmlWrap(final String tag, final StringBufferDescribing[] contents) {
		return new StringBufferDescribing() {

			public void describeTo(StringBuffer sb) {
				sb.append("<");
				sb.append(tag);
				sb.append(">");
				
				for (int i = 0; i < contents.length; i++) {
					contents[i].describeTo(sb);
				}
				
				sb.append("</");
				sb.append(tag);
				sb.append(">");
			}
			
		};
	}
	
	public String describeError(StringBufferDescribing desc) {
		return describe( xmlWrap("error", desc) );
	}
	
	public String describeError(StringBufferDescribing[] descs) {
		return describe( xmlWrap("error", descs) );
	}

}
