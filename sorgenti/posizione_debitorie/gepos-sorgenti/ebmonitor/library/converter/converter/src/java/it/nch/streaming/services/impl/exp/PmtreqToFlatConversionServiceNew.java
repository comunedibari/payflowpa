/**
 * Created on 27/feb/2009
 */
package it.nch.streaming.services.impl.exp;

import it.nch.eb.common.converter.pmtreq.parser.PmtreqParsersFactory;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.BindingManagerEffect;
import it.nch.eb.flatx.flatmodel.flatfile.LineTerminatedStringAction;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.ParserDecomposeLineParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanEffectFactory;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.FlattenModelNew;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.ModelLoaderBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.n.IRecordMdlLdrBuilder;
import it.nch.fwk.core.NamespacesInfos;
import it.nch.streaming.services.impl.BaseToFlatConversionService;


/**
 * @author gdefacci
 */
public class PmtreqToFlatConversionServiceNew extends BaseToFlatConversionService {

	private static final long	serialVersionUID	= -8849745324206343033L;
	
	private final PmtreqParsersFactory parserFactory;

	public PmtreqToFlatConversionServiceNew(String lineTerminator, NamespacesInfos queriesNss2) {
		this(lineTerminator, queriesNss2, new BindingManagerEffect[] {});
	}
	
	public PmtreqToFlatConversionServiceNew(String lineTerminator, NamespacesInfos qnss, BindingManagerEffect[] bmEffects) {
		this(new PmtreqParsersFactory(), lineTerminator, qnss, bmEffects);
	}
	
	public PmtreqToFlatConversionServiceNew(PmtreqParsersFactory parsersFactory, 
			String lineTerminator, 
			NamespacesInfos qnss, 
			BindingManagerEffect[] bmEffects) {
		super(lineTerminator, qnss, bmEffects);
		this.parserFactory = parsersFactory;
	}
	

	private IRecord[] recs;
	
	public synchronized IRecord[] getRecords() {
		if (recs==null) {
			recs = new ParserDecomposeLineParser().flatMapRecords(parserFactory.createParser());
		}
		return recs;
	}
	
	public BaseToFlatConversionService create(BindingManagerEffect[] bmEffects) {
		return new PmtreqToFlatConversionServiceNew(parserFactory, lineTerminator, queryNss, mergedEffects(bmEffects));
	}
	
	public PmtreqParsersFactory getParserFactory() {
		return parserFactory;
	}

	protected ModelLoaderBuilder createModelLoaderBuilder(
			final LineTerminatedStringAction printLineAction) {
		IParser parserTesta = getParserFactory().createRecordTestaParser();
		IParser parserCoda = getParserFactory().createCodaParser();
		IParser parserBdy = getParserFactory().createBodySequenceParser();
		
		BeanEffectFactory countRecords = new CountRecordsBeanEffectNew();
		
		IRecordMdlLdrBuilder mdlLoadrBuilder = new IRecordMdlLdrBuilder();
		
		FlattenModelNew flattenTesta = new FlattenModelNew(parserTesta, printLineAction);
		FlattenModelNew flattenCoda = new FlattenModelNew(parserCoda, printLineAction);
		FlattenModelNew flattenBody = new FlattenModelNew(parserBdy, printLineAction);
		
		mdlLoadrBuilder.add(parserTesta, new BeanEffectFactory[] { countRecords, flattenTesta });
		mdlLoadrBuilder.add(parserCoda, new BeanEffectFactory[] { countRecords, flattenCoda });
		mdlLoadrBuilder.add(parserBdy, new BeanEffectFactory[] { countRecords, flattenBody });
		
		mdlLoadrBuilder.preserveXPath(parserTesta);
		mdlLoadrBuilder.preserveXPath(parserCoda);
		
		return mdlLoadrBuilder;
	}
	
	
}
