/**
 * Created on 03/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp.n;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanEffect;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanEffectFactory;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.RecordFunction;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public class XPathFunctionAndEffects {

	private final RecordFunction recordFunction;
	private final List/*<BeanEffectFactory>*/ beanEffects = new ArrayList();
	private final IRecordStructure	recordStructure;
	private final XPathPosition	position;
	
	public XPathFunctionAndEffects(IRecordStructure rec, XPathPosition pos, RecordFunction recordFunction, 
			BeanEffectFactory[] effectsFactory) {
		this.recordStructure = rec;
		this.recordFunction = recordFunction;
		for (int i = 0; i < effectsFactory.length; i++) {
			beanEffects.add(effectsFactory[i]);
		}
		this.position = pos;
	}

	public RecordFunction getRecordFunction() {
		return recordFunction;
	}

	public BeanEffectFactory[] getBeanEffects() {
		return asArray( beanEffects );
	}

	private BeanEffectFactory[] asArray(List beanEffects2) {
		return (BeanEffectFactory[]) beanEffects2.toArray(new BeanEffectFactory[0]);
	}

	public IRecordStructure getRecordStructure() {
		return recordStructure;
	}

	public XPathPosition getPosition() {
		return position;
	}
	
	public void add(BeanEffectFactory bef) {
		this.beanEffects.add(bef);
	}
	
	public void add(final BeanEffect bef) {
		add(new BeanEffectFactory() {

			public BeanEffect apply(IBindingManager bm) {
				return bef;
			}
			
		});
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("perform " );
		sb.append(recordFunction.toString());
		sb.append(" for record ");
		sb.append(recordStructure);
		if (beanEffects!=null && !beanEffects.isEmpty()) {
			sb.append("\n and then perform effects");
			int idx = 2;
			for (Iterator it = beanEffects.iterator(); it.hasNext();) {
				BeanEffectFactory bef = (BeanEffectFactory) it.next();
				sb.append("\n- ");
				sb.append(bef);
				idx++;
			}
		}
		sb.append("\n");
		return sb.toString();
	}
	
}
