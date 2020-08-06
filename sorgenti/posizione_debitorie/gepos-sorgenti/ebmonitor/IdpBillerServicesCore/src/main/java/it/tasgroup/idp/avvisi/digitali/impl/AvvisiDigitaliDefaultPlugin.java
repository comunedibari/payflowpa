package it.tasgroup.idp.avvisi.digitali.impl;

import java.sql.Timestamp;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.avvisi.digitali.IAvvisiDigitaliPlugin;
import it.tasgroup.idp.avvisi.digitali.PrenotazioneAvvisiDigitali;
import it.tasgroup.idp.avvisi.digitali.exceptions.AvvisiDigitaliException;
import it.tasgroup.idp.generazioneavvisi.util.*;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.enti.TributiEntiPK;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.iris2.enums.EnumStatoAvviso;
import it.tasgroup.iris2.enums.EnumTipoOperazioneAvvisiDigitali;

public class AvvisiDigitaliDefaultPlugin implements IAvvisiDigitaliPlugin {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public void deletePendenza(Pendenze p, EntityManager em) throws AvvisiDigitaliException {
		logger.info("AvvisiDigitaliDefaultPlugin::deletePendenza()  BEGIN");
		for (CondizioniPagamento c: p.getCondizioniPagamento()) {
			PrenotazioneAvvisiDigitali pren = buildPrenotazAvvDigitFromCond(em, c, EnumTipoOperazioneAvvisiDigitali.DELETED.getChiave());
			em.persist(pren);
		}
		logger.info("AvvisiDigitaliDefaultPlugin::deletePendenza()  END");
	}

	@Override
	public void insertPendenza(Pendenze p, EntityManager em) throws AvvisiDigitaliException {
		logger.info("AvvisiDigitaliDefaultPlugin::insertPendenza()  BEGIN");
		for (CondizioniPagamento c: p.getCondizioniPagamento()) {
			PrenotazioneAvvisiDigitali pren = buildPrenotazAvvDigitFromCond(em, c, EnumTipoOperazioneAvvisiDigitali.CREATED.getChiave());
			em.persist(pren);
		}
		logger.info("AvvisiDigitaliDefaultPlugin::insertPendenza()  END");
	}

	@Override
	public void updatePendenza(Pendenze p, EntityManager em) throws AvvisiDigitaliException {
		logger.info("AvvisiDigitaliDefaultPlugin::updatePendenza()  BEGIN");
		for (CondizioniPagamento c: p.getCondizioniPagamento()) {
			PrenotazioneAvvisiDigitali pren = buildPrenotazAvvDigitFromCond(em, c, EnumTipoOperazioneAvvisiDigitali.UPDATED.getChiave());
			em.persist(pren);
		}
		logger.info("AvvisiDigitaliDefaultPlugin::updatePendenza()  END");
	}

	@Override
	public void updateCondizione(CondizioniPagamento p, EntityManager em) throws AvvisiDigitaliException {
		logger.info("AvvisiDigitaliDefaultPlugin::updateCondizione()  BEGIN");
		PrenotazioneAvvisiDigitali pren = buildPrenotazAvvDigitFromCond(em, p, EnumTipoOperazioneAvvisiDigitali.UPDATED.getChiave());
		em.persist(pren);
		logger.info("AvvisiDigitaliDefaultPlugin::updateCondizione()  END");
	}
    
	private PrenotazioneAvvisiDigitali buildPrenotazAvvDigitFromCond(EntityManager em, CondizioniPagamento c, String tipoOperaz) {
		
		TributiEntiPK tePK = new TributiEntiPK();
		tePK.setIdEnte(c.getIdEnte());
		tePK.setCdTrbEnte(c.getCdTrbEnte());
		
		TributiEnti te = em.find(TributiEnti.class,tePK);
		
		PrenotazioneAvvisiDigitali p = new PrenotazioneAvvisiDigitali();
		p.setIdCondizione(c.getIdCondizione());              //- ID_CONDIZIONE
		p.setIdEnte(c.getIdEnte());                          //- ID_ENTE
		p.setIdPagamento(c.getIdPagamento());                //- ID_PAGAMENTO (IUV)
		p.setCodiceAvviso(BollettinoUtils.calculateNumeroAvviso(te, c.getIdPagamento(), false)); //- CODICE_AVVISO
		p.setTipoOperazioneOriginale(tipoOperaz);;           // - TIPO_OPERAZIONE_ORIG ('C','U','D')
		p.setIdFileAvvisatura(null);                            //- ID_FILE_NODOSPC
		p.setIdRichiestaAvviso(null);                       //- ID_RICHIESTA_NODOSPC
		p.setStatoAvviso(EnumStatoAvviso.INSERITO.getChiave());
		p.setTipoProcesso("RT");
		p.setNumTentativiAvviso(new Long(0));
		p.setOpInserimento("IDP"); 
		p.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		p.setVersion(0);
		return p;
	}

	@Override
	public void deleteCondizione(CondizioniPagamento c, EntityManager em) throws AvvisiDigitaliException {
		logger.info("AvvisiDigitaliDefaultPlugin::deleteCondizione()  BEGIN");
		PrenotazioneAvvisiDigitali pren = buildPrenotazAvvDigitFromCond(em, c, EnumTipoOperazioneAvvisiDigitali.DELETED.getChiave());
		em.persist(pren);
		logger.info("AvvisiDigitaliDefaultPlugin::deleteCondizione()  END");
	}

	@Override
	public void generaBollettinoCondizione(CondizioniPagamento c, EntityManager em) throws AvvisiDigitaliException {
		logger.info("AvvisiDigitaliDefaultPlugin::generaBollettinoCondizione()  BEGIN");
		PrenotazioneAvvisiDigitali pren = buildPrenotazAvvDigitFromCond(em, c, EnumTipoOperazioneAvvisiDigitali.CREATED.getChiave());
		em.persist(pren);		
		logger.info("AvvisiDigitaliDefaultPlugin::generaBollettinoCondizione()  END");
	}
}
