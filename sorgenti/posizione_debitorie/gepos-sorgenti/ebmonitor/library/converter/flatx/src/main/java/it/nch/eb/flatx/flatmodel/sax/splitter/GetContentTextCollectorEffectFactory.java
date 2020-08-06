/**
 * 
 */
package it.nch.eb.flatx.flatmodel.sax.splitter;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author gdefacci
 *
 */
public class GetContentTextCollectorEffectFactory implements TextCollectorEffectFactory {
	
	private final FragmentHandler[] handlers;
	
	public GetContentTextCollectorEffectFactory(FragmentHandler handler) {
		this(new FragmentHandler[] { handler });
	}
	public GetContentTextCollectorEffectFactory(FragmentHandler[] handlers) {
		this.handlers = handlers;
	}

	public TextCollectorEffect create(final XPathPosition pos) {
		return new TextCollectorEffect(pos, new FragmentHandler[] {
			new FragmentHandler() {

				public void onFragment(String xmlFragment) {
					int tagLen = pos.getLastSegment().toString().length();
					String content = xmlFragment.substring(tagLen+1, xmlFragment.length() - (tagLen+2));
					for (int i = 0; i < handlers.length; i++) {
						handlers[i].onFragment(content);
					}
				}
				
			}
		});
	}

}
