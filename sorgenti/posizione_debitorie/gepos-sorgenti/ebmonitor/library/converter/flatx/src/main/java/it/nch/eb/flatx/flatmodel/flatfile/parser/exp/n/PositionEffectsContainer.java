/**
 * Created on 03/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp.n;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathMapPositionEffect;
import it.nch.eb.flatx.flatmodel.flatfile.parser.BeanParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanEffect;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanEffectFactory;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.RecordFunction;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.RecordFunctions;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.SeqRecordStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.SetSequencePropertyOnBindingManager;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.SetSimplePropertyOnBindingManager;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.SimpleRecordStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.XPathMapPositionEffectFactory;
import it.nch.eb.flatx.flatmodel.sax.ModelLoader;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author gdefacci
 */
public class PositionEffectsContainer {
	
	private final Map/*<XPathPosition, List<XPathFunctionAndEffects>>*/	map = new HashMap();
	protected final RecordFunctions recordFunctions; //= new IRecordFunctions(); // abstract over, to decouple from IRecord
	protected final BaseXPathFinder basexpathFinder;
	
	public PositionEffectsContainer(RecordFunctions recordFunctions, BaseXPathFinder basexpathFinder) {
		this.recordFunctions = recordFunctions;
		this.basexpathFinder = basexpathFinder;
	}

	protected XPathPosition getBasePath(IRecordStructure recordStructure) {
		return basexpathFinder.getBasePath(recordStructure);
	}
	
	public int size() {
		return map.size();
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public Set/*<XPathPosition>*/ keySet() {
		return map.keySet();
	}
	
	public List/*<XPathFunctionAndEffects>*/ get(XPathPosition pos) {
		return (List) map.get(pos);
	}
	
	private boolean doesNeedScope(XPathFunctionAndEffects[] pathFunctionAndEffects) {
		boolean found = false;
		for (int i = 0; i < pathFunctionAndEffects.length && !found; i++) {
			XPathFunctionAndEffects fae = pathFunctionAndEffects[i];
			if ((fae.getRecordStructure() instanceof BeanStructure) || fae.getRecordStructure() instanceof SeqRecordStructure) found = true;
		}
		return found;
	}
	
	private XPathFunctionAndEffects[] asXPathFunctionAndEffectsArray(List funEfcts) {
		return (XPathFunctionAndEffects[]) funEfcts.toArray(new XPathFunctionAndEffects[0]);
	}

	public void fillModelLoader(IBindingManager bm, ModelLoader modelLoader) {
		for (Iterator it = keySet().iterator(); it.hasNext();) {
			XPathPosition pos = (XPathPosition) it.next();
			List/*<XPathFunctionAndEffects>*/ funEfcts =  get(pos);
			XPathFunctionAndEffects[] xpathFunEffects = asXPathFunctionAndEffectsArray(funEfcts);
			XPathFunctionAndEffectsExecutor efct;
			modelLoader.addArea(pos);
			if (doesNeedScope(xpathFunEffects)) {
				modelLoader.addOnStart(pos, enterScope.apply(bm));
				efct = new ExitScopeXPathFunctionAndEffectsExecutor(bm, xpathFunEffects);
			} else {
				efct = new XPathFunctionAndEffectsExecutor(bm, xpathFunEffects);
			}
			modelLoader.addOnEnd(pos, efct);
		}
	}
	
	public String toString() {
		if (isEmpty()) return "empty PositionEffectsContainer";
		else {
			StringBuffer sb = new StringBuffer("\nPositionEffectsContainer ");
			for (Iterator it = map.keySet().iterator(); it.hasNext();) {
				XPathPosition pos = (XPathPosition) it.next();
				sb.append("**\non position ");
				sb.append(pos);
				List/*<XPathFunctionAndEffects>*/ funEfcts = (List) map.get(pos);
				if (funEfcts!=null && !funEfcts.isEmpty()) {
					sb.append("\n");
					sb.append("\neffects :\n");
					int idx = 1;
					for (Iterator funEfctsIt = funEfcts.iterator(); funEfctsIt.hasNext();) {
						XPathFunctionAndEffects fEfct = (XPathFunctionAndEffects) funEfctsIt.next();
						sb.append("\n");
						sb.append(idx);
						sb.append(")");
						sb.append(fEfct);
						idx++;
					}
				}
			}
			return sb.toString();			
		}
	}

	public void add(XPathPosition pos, IRecordStructure struct, BeanEffectFactory[] peffects) {
		
		if (struct instanceof BeanStructure) {
			addInnerStructures(pos, (BeanStructure) struct);
		} else if ((struct instanceof SeqRecordStructure) && ((SeqRecordStructure)struct).getItemStrucuture() instanceof BeanParser) {
			addInnerStructures(pos, (BeanStructure) ((SeqRecordStructure)struct).getItemStrucuture());
		}
		IRecordStructure istruct = (struct instanceof SeqRecordStructure) ? ((SeqRecordStructure)struct).getItemStrucuture() : struct;
		
		BeanEffectFactory bmEffect = bindingManagerEffectFactory(struct);
		RecordFunction function = recordFunction(struct, bmEffect);
		List list = (List) map.get(pos);
		if (list==null) {
			list = new ArrayList();
			map.put(pos, list);
		}
		
		if (istruct instanceof BeanStructure) {
			BeanEffectFactory[] effects = merge( bmEffect, peffects );
			list.add(new XPathFunctionAndEffects(istruct, pos, function, effects));
		} else if (istruct instanceof SimpleRecordStructure) {
			list.add(new XPathFunctionAndEffects(istruct, pos, function, peffects));
		} else {
			throw new IllegalStateException("not a valid sequence item record structure " + istruct);
		}
	}
	
	private BeanEffectFactory bindingManagerEffectFactory(IRecordStructure rec) {
		if (rec.getStructureKind().equals(IRecordStructure.seq)) return setSequencePropertyOnBindingManager;
		else return setSimplePropertyOnBindingManager;
	}
	
	private RecordFunction recordFunction(final IRecordStructure irec, final BeanEffectFactory bef) {
		final IRecordStructure rec = (irec instanceof SeqRecordStructure) ? 
				((SeqRecordStructure)irec).getItemStrucuture() : irec;
				
		if (rec instanceof BeanStructure) return recordFunctions.getBeanPropertiesSetter();
		else return 
			new RecordFunction() {
			
				RecordFunction delegate = recordFunctions.getRecordPropertiesSetter();

				public Object apply(IRecordStructure recStruct, XPathPosition pos, XPathsMapBindings xpathBindings,
						IBindingManager lbindings) {
					Object res = delegate.apply(recStruct, pos, xpathBindings, lbindings);
					bef.apply(lbindings).apply(irec, pos, res);
					return res;
				}

				public boolean canApply(IRecordStructure recStruct, XPathPosition pos, XPathsMapBindings xpathBindings,
						IBindingManager lbindings) {
					return delegate.canApply(recStruct, pos, xpathBindings, lbindings);
				}

				public String toString() {
					return "execute " + delegate + " and " + bef;
				}
				
			};
	}
	
	private static BeanEffectFactory[] merge(BeanEffectFactory hd, BeanEffectFactory[] rest) {
		if (hd==null) throw new NullPointerException("hd");
		if (rest==null || rest.length==0) return new BeanEffectFactory[] { hd };
		BeanEffectFactory[] res = new BeanEffectFactory[rest.length +1];
		res[0] = hd;
		System.arraycopy(rest, 0, res, 1, rest.length);
		return res;
	}
	
	public void add(XPathPosition pos, IRecordStructure struct) {
		add(pos, struct, new BeanEffectFactory[] { });
	}
	
	public void add(IRecordStructure struct, BeanEffectFactory[] beanEffectsFactories) {
		add(getBasePath(struct), struct, beanEffectsFactories);
	}
	
	public void add(IRecordStructure struct) {
		add(getBasePath(struct), struct, new BeanEffectFactory[] { });
	}
	
	public void intercept(PositionRecordStructurePredicate predicate, BeanEffectFactory[] effects) {
		for (Iterator it = this.map.entrySet().iterator(); it.hasNext();) {
			List/*<XPathFunctionAndEffects>*/ xpathFunctAndEfctsList = (List) it.next();
			for (Iterator xpfeIt = xpathFunctAndEfctsList.iterator(); xpfeIt.hasNext();) {
				XPathFunctionAndEffects xpfe = (XPathFunctionAndEffects) xpfeIt.next();
				if (predicate.match(xpfe.getPosition(), xpfe.getRecordStructure())) {
					for (int i = 0; i < effects.length; i++) {
						BeanEffectFactory beanEffectFactory = effects[i];
						xpfe.add(beanEffectFactory);
					}
				}
			}
		}		
	}
	
	public void onRecord(final IRecordStructure rcst, BeanEffectFactory[] befs) {
		intercept(new PositionRecordStructurePredicate() {

			public boolean match(XPathPosition pos, IRecordStructure record) {
				return rcst.equals(record);
			}
			
		}, befs);
	}
	
	private void addInnerStructures(XPathPosition pos, BeanStructure beanStruct) {
		IRecordStructure[] items = beanStruct.getItemStructures();
		for (int i = 0; i < items.length; i++) {
			IRecordStructure recordStructure = items[i];
			XPathPosition bPos = getBasePath(recordStructure);
			add(bPos, recordStructure);
		}
	}


	public static final XPathMapPositionEffectFactory enterScope = new XPathMapPositionEffectFactory() {
		
		private static final String	TO_STRING	= "BindingManager:enter scope";

		public XPathMapPositionEffect apply(final IBindingManager bindings) {
			return new XPathMapPositionEffect() {

				public void apply(XPathPosition pos, XPathsMapBindings xpathBindings) {
					bindings.enterScope();
				}

				public String toString() {
					return TO_STRING;
				}
				
			};
			
		}

		public String toString() {
			return "create "+TO_STRING;
		}
		
	};
	
	private static final BeanEffectFactory setSimplePropertyOnBindingManager = new BeanEffectFactory() {

		public BeanEffect apply(IBindingManager bm) {
			return new SetSimplePropertyOnBindingManager(bm);
		}

		public String toString() {
			return "create a bean effect that set the bean as a  simple property on the binding manager";
		}
		
	};
	
	private static final BeanEffectFactory setSequencePropertyOnBindingManager = new BeanEffectFactory() {
	
			public BeanEffect apply(IBindingManager bm) {
				return new SetSequencePropertyOnBindingManager(bm);
			}
			
			public String toString() {
				return "create a bean effect that set the bean as a sequence property on the binding manager";
			}
		
	};
}
