/**
 * Created on 29/set/2008
 */
package it.nch.streaming.services.impl.exp;

import it.nch.eb.common.converter.pmtreq.advinf.parser.AdvinfParserFactory;
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
public class AdvinfToFlatConversionServiceNew extends BaseToFlatConversionService {
	
	private static final long	serialVersionUID	= 7840714430381683340L;

	static final AdvinfParserFactory parserFactory = new AdvinfParserFactory();
	
	private IRecord[] recs; // = new ParserDecomposeLineParser().flatMapRecords(parserFactory.createParser());
	
	public synchronized IRecord[] getRecords() {
		if (recs == null) {
			recs = new ParserDecomposeLineParser().flatMapRecords(parserFactory.createParser());
		}
		return recs;
	}
	
//	static final RecordImpl rhd = new it.nch.eb.common.converter.pmtreq.advinf.records.RecorddiTesta();
//	static final RecordImpl rtl = new it.nch.eb.common.converter.pmtreq.advinf.records.RecordCoda();
	
//	private XPathPosition	root = XPathsParser.instance.parseXPathPosition("/MSG:CBIAdvInstrMsg");
//	private XPathPosition	advinfBdyPos = XPathsParser.instance.parseXPathPosition("/MSG:CBIAdvInstrMsg/MSG:CBIBdyAdvInstr/BODY:CBIEnvelAdvInstr");
//	private XPathPosition	scopePosition = advinfBdyPos;
	
	public AdvinfToFlatConversionServiceNew(String lineTerminator, NamespacesInfos qnss, BindingManagerEffect[] bmEffects) {
		super(lineTerminator, qnss, bmEffects);
	}

//	public AdvinfToFlatConversionServiceNew(String lineTerminator) {
//		this(lineTerminator, queriesNss);
//	}

	public AdvinfToFlatConversionServiceNew(String lineTerminator, NamespacesInfos qnss) {
		super(lineTerminator, qnss);
	}
	
	public BaseToFlatConversionService create(BindingManagerEffect[] bmEffect) {
		return new AdvinfToFlatConversionServiceNew(lineTerminator, queryNss, mergedEffects(bmEffect));
	}

//	private static NamespacesInfos	queriesNss = new NamespacesInfos(new String[][] { 
//			{ "MSG", 	"urn:CBI:xsd:CBIAdvInstrMsg.002.00"		},
//			{ "BODY", "urn:CBI:xsd:CBIBdyAdvInstr.002.00"   },
//			{ "HTRT", "urn:CBI:xsd:CBIHdrTrt.001.07"        },
//			{ "HE2E", "urn:CBI:xsd:CBIHdrSrv.001.07"        },
//			{ "ADIN", "urn:CBI:xsd:CBIAdvInstr.002.00"      },
//			{ "SGNT", "urn:CBI:xsd:CBISgnInf.001.04"        }, 
//		});
	
	protected AdvinfParserFactory getParserFactory() {
		return parserFactory;
	}
	
	protected ModelLoaderBuilder createModelLoaderBuilder(final LineTerminatedStringAction printLineAction) {
		IParser parserTesta = getParserFactory().createRecordTestaParser();
		IParser parserCoda = getParserFactory().createRecordCodaParser();
		IParser parserBdy = getParserFactory().createBodySequenceParser();
		
		BeanEffectFactory countRecords = new CountRecordsBeanEffectNew();
		
		IRecordMdlLdrBuilder mdlLoadrBuilder = new IRecordMdlLdrBuilder();
		
		FlattenModelNew flattenTesta = new FlattenModelNew(parserTesta, printLineAction);
		FlattenModelNew flattenCoda = new FlattenModelNew(parserCoda, printLineAction);
		FlattenModelNew flattenBody = new FlattenModelNew(parserBdy, printLineAction);
		
		mdlLoadrBuilder.add(parserTesta, new BeanEffectFactory[] { flattenTesta });
		mdlLoadrBuilder.add(parserCoda, new BeanEffectFactory[] { flattenCoda });
		mdlLoadrBuilder.add(parserBdy, new BeanEffectFactory[] { countRecords, flattenBody });
		
		mdlLoadrBuilder.preserveXPath(parserTesta);
		mdlLoadrBuilder.preserveXPath(parserCoda);
		
		return mdlLoadrBuilder;
	}

}
