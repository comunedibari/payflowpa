/**
 * Created on 03/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp.n;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.flatmodel.conversioninfo.PositionCallbackPair;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathMapPositionEffect;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathsMapBindinsManagerEffect;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanEffect;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanEffectFactory;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.ModelLoaderBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.RecordFunctions;
import it.nch.eb.flatx.flatmodel.sax.ModelLoader;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * FIXME Questa classe e relative sottoclassi fanno vomito, rifatorizzare e semplificare 
 * @author gdefacci
 */
public abstract class MdlLoadrBuilder implements ModelLoaderBuilder, BaseXPathFinder {

	private final Set/*<BaseXPathPosition>*/ xPathToPreserve = new TreeSet();
	
	private final PositionEffectsContainer posContainer; 
	
	private final List/*<PositionCallbackPair>*/ endCallbacks = new ArrayList();

	private static final org.slf4j.Logger	log = ResourcesUtil.createLogger(MdlLoadrBuilder.class);
	
	private List/*<XPathPosition>*/ areas = new ArrayList();
	
	public MdlLoadrBuilder(PositionEffectsContainer posContainer) {
		this.posContainer = posContainer;
	}
	
	public MdlLoadrBuilder(RecordFunctions recFnctns) {
		this.posContainer =  new PositionEffectsContainer(recFnctns, this);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (!posContainer.isEmpty()) {
			sb.append(posContainer.toString());
		}
		for (Iterator it = endCallbacks.iterator(); it.hasNext();) {
			sb.append(it.next());
			sb.append("\n");
		}
		for (Iterator it = xPathToPreserve.iterator(); it.hasNext();) {
			sb.append(it.next());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private XPathMapPositionEffect create(final IBindingManager bm, 
			final XPathsMapBindinsManagerEffect  efct) {
		return new XPathMapPositionEffect() {

			public void apply(XPathPosition pos, XPathsMapBindings xpathBindings) {
				efct.apply(pos, xpathBindings, bm);
			}

			public String toString() {
				return efct.toString();
			}
			
		};
	}
	
	public void preserveXPath(BaseXPathPosition pos) {
		this.xPathToPreserve.add(pos);
	}
	
	public void addArea(XPathPosition xp) {
		this.areas.add(xp);
	}
	
	public ModelLoader create(IBindingManager bm) {
		ModelLoader modelLoader = new ModelLoader();
		
		for (Iterator it = endCallbacks.iterator(); it.hasNext();) {
			PositionCallbackPair posClbk = (PositionCallbackPair) it.next();
			modelLoader.addArea(posClbk.getPosition());
			modelLoader.addOnEnd(posClbk.getPosition(), create(bm, posClbk.getCallback()));
		}
		posContainer.fillModelLoader(bm, modelLoader);
		
		for (Iterator it = this.areas.iterator(); it.hasNext();) {
			XPathPosition newArea = (XPathPosition) it.next();
			modelLoader.addArea(newArea);
		}
		
		modelLoader.defineXPaths(xPathToPreserve);
		
//		log .debug("modelloader:\n" + modelLoader);
		
		return modelLoader;
	}

	
	public void add(XPathPosition pos, IRecordStructure struct, BeanEffectFactory[] beanEffectsFactories) {
		posContainer.add(pos, struct, beanEffectsFactories);
	}
	
	public void add(XPathPosition pos, IRecordStructure struct) {
		posContainer.add(pos, struct);
	}
	
	public void add(IRecordStructure struct, BeanEffectFactory[] beanEffectsFactories) {
		posContainer.add(struct, beanEffectsFactories);
	}
	
	public void add(IRecordStructure struct, final BeanEffect[] beanEffects) {
		posContainer.add(struct, mapBeanEffectArrayToBeanEffectFactoryArray(beanEffects));
	}
	
	public void add(IRecordStructure struct, final BeanEffect beanEffect) {
		posContainer.add(struct, new BeanEffectFactory[] { mapBeanEffectToBeanEffectFactory(beanEffect) });
	}
	
	private BeanEffectFactory[] mapBeanEffectArrayToBeanEffectFactoryArray(BeanEffect[] bes) {
		BeanEffectFactory[] res = new BeanEffectFactory[bes.length];
		for (int i = 0; i < bes.length; i++) {
			final BeanEffect be = bes[i];
			res[i] = mapBeanEffectToBeanEffectFactory(be);
		}
		
		return res;
	}

	
	private BeanEffectFactory mapBeanEffectToBeanEffectFactory(final BeanEffect be) {
		return new BeanEffectFactory() {

			public BeanEffect apply(IBindingManager bm) {
				return be;
			}
			
		};
	}
	
//	public void add(IRecordStructure struct) {
//		posContainer.add(struct);	
//	}
	
//	public void addOnEnd(XPathPosition position, XPathsMapBindinsManagerEffect efct) {
//		endCallbacks.add(new PositionCallbackPair(position, efct));
//	}

//	protected BeanEffectFactory[] asSingletonBeanEffects(final BeanEffect[] bes) {
//		BeanEffectFactory[] befs = new BeanEffectFactory[bes.length];
//		for (int i = 0; i < befs.length; i++) {
//			befs[i] = singletonBeanEffect(bes[i]);
//		}
//		return befs;
//	}

//	protected BeanEffectFactory singletonBeanEffect(final BeanEffect be) {
//		return new BeanEffectFactory() {
//	
//			public BeanEffect apply(IBindingManager bm) {
//				return be;
//			}
//			
//		};
//	}
	
//	public void addArea(XPathPosition pos) {
//		this.areas.add(pos);
//	}
}
