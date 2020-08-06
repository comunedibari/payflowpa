package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.tributi.IUVSequence;
import it.nch.is.fo.tributi.IUVSequencePK;
import it.nch.is.fo.tributi.JltentrId;
import it.tasgroup.iris.persistence.dao.interfaces.IUVSequenceDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "IUVSequenceDaoImpl")
public class IUVSequenceDaoImpl extends DaoImplJpaCmtJta<IUVSequence> implements IUVSequenceDao { 

	private static final Logger LOGGER = LogManager.getLogger(IUVSequenceDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public void insertOrEnable(String idEnte, String cdTrbEnte) {
		doIt( idEnte,  cdTrbEnte, true);
	}

	@Override
	public void disable(String idEnte, String cdTrbEnte) {		
		doIt( idEnte,  cdTrbEnte, false);
	}
    
	private void doIt(String idEnte, String cdTrbEnte,boolean enable) {
        IUVSequencePK pk = new IUVSequencePK(idEnte,cdTrbEnte);
        IUVSequence  iuvSeq;
		try {
			
			iuvSeq = (IUVSequence) loadById(IUVSequence.class, pk);
		    
		} catch (Exception e) {
			LOGGER.error("error on  loadById IUVSequence, idEnte = " + idEnte + " , cdTrbEnte = " + cdTrbEnte, e);
			throw new DAORuntimeException(e);
		}
		if (iuvSeq==null) {
			if (enable) {
				iuvSeq = new IUVSequence();
				iuvSeq.setId(pk);
				iuvSeq.setStRiga("V");
				iuvSeq.setOpInserimento("OPERATORE");
				iuvSeq.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				iuvSeq.setLastSeqIuv(new BigInteger("0"));
				try {
				    save(iuvSeq);
				} catch (Exception e) {
					LOGGER.error("error on  save IUVSequence, idEnte = " + idEnte + " , cdTrbEnte = " + cdTrbEnte, e);
					throw new DAORuntimeException(e);
				}
				return;
				// inserisco nuova riga
			} else {
				// non faccio nulla
				return;
			}
		} else {
			if (enable) {
				iuvSeq.setStRiga("V");				
			} else {
				iuvSeq.setStRiga("A");
			}
			iuvSeq.setOpAggiornamento("OPERATORE");
			iuvSeq.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
			try {				
			   update(iuvSeq);
			} catch (Exception e) {
				LOGGER.error("error on  update IUVSequence, idEnte = " + idEnte + " , cdTrbEnte = " + cdTrbEnte, e);
				throw new DAORuntimeException(e);
			}
		}
	}

	@Override
	public void delete(List<String> selectedIds) {
          try {
			for (String id : selectedIds){
				String[] splitId = id.split("[,]");				
				IUVSequencePK pk = new IUVSequencePK();				
				pk.setIdEnte(splitId[0]);				
				pk.setCdTrbEnte(splitId[1]);
				IUVSequence iuvSeq = (IUVSequence) loadById(IUVSequence.class, pk);
				if (iuvSeq!=null){
				  delete(iuvSeq);
				}
			}
			
		} catch(Exception e){			
			LOGGER.error("error on delete, " + selectedIds, e);			
			throw new DAORuntimeException(e);			
		}
		
	}
}
