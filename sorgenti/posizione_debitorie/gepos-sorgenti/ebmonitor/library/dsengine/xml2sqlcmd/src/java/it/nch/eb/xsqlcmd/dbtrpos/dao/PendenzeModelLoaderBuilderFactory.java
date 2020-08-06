/**
 * 02/lug/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.common.utils.loaders.InputsFactory;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.flatmodel.ChoiceXmlRecord;
import it.nch.eb.flatx.flatmodel.MappedXmlRecord;
import it.nch.eb.flatx.flatmodel.PredicateXmlRecordPair;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathMapPositionEffect;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.ModelLoaderBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.ModelLoaderBuilderFactory;
import it.nch.eb.flatx.flatmodel.sax.ModelLoader;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.model.HeaderModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.OperationKinds;
import it.nch.eb.xsqlcmd.dbtrpos.model.OtfModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeInclusions;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.dbtrpos.record.HeaderRecord;
import it.nch.eb.xsqlcmd.dbtrpos.record.OtfRecord;
import it.nch.eb.xsqlcmd.dbtrpos.record.TipoOperazionePredicateXmlRecord;

import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;

/**
 * @author gdefacci
 */
public class PendenzeModelLoaderBuilderFactory implements ModelLoaderBuilderFactory {

	private static final Logger log = ResourcesUtil.createLogger(PendenzeModelLoaderBuilderFactory.class);
	
	private final HeaderEffect headerEffect;
	private final OtfEffect otfEffect;
	
	private final PendenzeModelEffect pendenzeEffect;
	private final PendenzeInclusions pendenzeInclusions;

	private final PendenzaXPathValidator pendenzaXPathValidator;

	public PendenzeModelLoaderBuilderFactory(HeaderEffect headerEffect, OtfEffect otfEffect,
			PendenzeModelEffect executor,
			PendenzaXPathValidator pendenzaXPathValidator,
			PendenzeInclusions pendenzeInclusions) {
		this.headerEffect = headerEffect;
		this.otfEffect = otfEffect;
		this.pendenzeEffect = executor;
		this.pendenzeInclusions = pendenzeInclusions;
		this.pendenzaXPathValidator = pendenzaXPathValidator;
	}

	protected void scopeWrap(ModelLoader modelLoader, XPathPosition pos, XPathMapPositionEffect xpmapEfct) {
		modelLoader.addArea(pos);
		modelLoader.addOnStart(pos, new XPathMapPositionEffect() {

			public void apply(XPathPosition pos,
					XPathsMapBindings xpathBindings) {
				xpathBindings.enterScope();
			}

			public String toString() {
				return "enter xpath scope";
			}

		});
		modelLoader.addOnEnd(pos,  xpmapEfct);
		modelLoader.addOnEnd(pos, new XPathMapPositionEffect() {

			public void apply(XPathPosition pos,
					XPathsMapBindings xpathBindings) {
				xpathBindings.exitScope();
			}

			public String toString() {
				return "exit xpath scope";
			}

		});
	}

	public ModelLoaderBuilder create(final InputsFactory readerFactory) {
		BaseXPathPosition[] xpathToPreserve = new BaseXPathPosition[] {
				IdPDocumentXPaths.e2eMsgId,
				IdPDocumentXPaths.e2eSrvcNm,
				IdPDocumentXPaths.xmlCrtDt,
				IdPDocumentXPaths.coVersione,
				IdPDocumentXPaths.senderE2ESndrId,
				IdPDocumentXPaths.senderE2ESndrSys,
				IdPDocumentXPaths.senderTRTSndrId,
				IdPDocumentXPaths.senderTRTSndrSys,
				IdPDocumentXPaths.urlBack,
				IdPDocumentXPaths.urlCancel,
				IdPDocumentXPaths.offlineMethods,
		};

		final Set xpSet = new TreeSet();
		for (int i = 0; i < xpathToPreserve.length; i++) {
			xpSet.add(xpathToPreserve[i]);
		}

		BaseXPathPosition tipoOperazioneXpath = IdPDocumentXPaths.pendenzaTipoOperazione;

		final ChoiceXmlRecord rec = new ChoiceXmlRecord(
				new PredicateXmlRecordPair[] {
						new TipoOperazionePredicateXmlRecord(OperationKinds.instance.insert, tipoOperazioneXpath, pendenzeInclusions),
						new TipoOperazionePredicateXmlRecord(OperationKinds.instance.update, tipoOperazioneXpath, pendenzeInclusions),
						new TipoOperazionePredicateXmlRecord(OperationKinds.instance.updateMassivo, tipoOperazioneXpath, pendenzeInclusions),
						new TipoOperazionePredicateXmlRecord(OperationKinds.instance.replace, tipoOperazioneXpath, pendenzeInclusions),
						new TipoOperazionePredicateXmlRecord(OperationKinds.instance.delete, tipoOperazioneXpath, pendenzeInclusions) });

		final MappedXmlRecord headerRecord = new MappedXmlRecord(
				HeaderModel.class, new HeaderRecord());
		
		final MappedXmlRecord otfRecord = new MappedXmlRecord(
				OtfModel.class, new OtfRecord());		
		
		log.debug("starting ModelLoaderBuilder ... " );

		ModelLoaderBuilder mdlLoaderBuilder = new ModelLoaderBuilder() {

			public ModelLoader create(IBindingManager bm) {
				ModelLoader modelLoader = new ModelLoader(xpSet);
				modelLoader.addArea(IdPDocumentXPaths.idpHeader);
				if (headerEffect != null) {					
					log.debug("Inside headerEffect ... " );
					modelLoader.addOnEnd(IdPDocumentXPaths.idpHeaderE2E,
							new XPathMapPositionEffect() {

								public void apply(XPathPosition pos,
										XPathsMapBindings xpathBindings) {
									HeaderModel mdl = (HeaderModel) headerRecord
											.getValue(pos, xpathBindings);
									headerEffect.apply(mdl, readerFactory);
								}
								public String toString() {
									return "Load a HeaderModel instance from the xml, and dispatch it to "
											+ headerEffect;
								}

							});

				}
				
				
				scopeWrap(modelLoader, IdPDocumentXPaths.idpOtf,
						new XPathMapPositionEffect() {

							public void apply(XPathPosition pos,
									XPathsMapBindings xpathBindings) {								
								log.debug("Inside apply OtfModel ... " );								
								OtfModel mdl = (OtfModel) otfRecord
										.getValue(pos, xpathBindings);
								otfEffect.apply(mdl);								
							}
							public String toString() {
								return "Load a OtfModel instance from the xml, and dispatch it to "
										+ pendenzeEffect;
							}
						});

				
				scopeWrap(modelLoader, IdPDocumentXPaths.pendenza,
						new XPathMapPositionEffect() {

							public void apply(XPathPosition pos,
									XPathsMapBindings xpathBindings) {								
								log.debug("Inside apply Pendenza ... " );								
								PendenzeModel mdl = (PendenzeModel) rec
										.getValue(pos, xpathBindings);
								DBError[] errs = null;
								//in caso di esclusione anche mdl puo' essere nullo !!
								if (mdl!=null) {
									log.debug("Calling validate on Pendenza ... " );	
									errs = pendenzaXPathValidator.validate(pos, xpathBindings, mdl);
								}
								if (mdl != null && errs == null) {
									log.debug("Calling DAO's on Pendenza ... " );
									pendenzeEffect.apply(mdl);
								}
							}
							public String toString() {
								return "Load a PendenzeModel instance from the xml, and dispatch it to "
										+ pendenzeEffect;
							}

						});

				return modelLoader;
			}

		};

		return mdlLoaderBuilder;
	}

}
