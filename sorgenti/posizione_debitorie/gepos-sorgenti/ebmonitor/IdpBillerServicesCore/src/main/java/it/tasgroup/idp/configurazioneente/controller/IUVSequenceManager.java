package it.tasgroup.idp.configurazioneente.controller;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.EntityManager;

import it.tasgroup.idp.domain.iuv.IUVSequence;
import it.tasgroup.idp.domain.iuv.IUVSequencePK;

public class IUVSequenceManager {
	
	
	public static void insertOrEnable(String idEnte, String cdTrbEnte, EntityManager em) {
		doIt( idEnte,  cdTrbEnte, true, em);
	}

	
	public static void disable(String idEnte, String cdTrbEnte, EntityManager em) {
		doIt( idEnte,  cdTrbEnte, false, em);
	}
 
	private static void doIt(String idEnte, String cdTrbEnte, boolean enable, EntityManager em) {
        IUVSequencePK iuvSeqPk = new IUVSequencePK(idEnte,cdTrbEnte);
        IUVSequence  iuvSeq;
		iuvSeq = em.find(IUVSequence.class, iuvSeqPk);
		if (iuvSeq == null) {
			if (enable) {
				iuvSeq = new IUVSequence();
				iuvSeq.setId(iuvSeqPk);
				iuvSeq.setStRiga("V");
				iuvSeq.setOpInserimento("OPERATORE");
				iuvSeq.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				iuvSeq.setLastSeqIuv(new BigInteger("0"));
				em.persist(iuvSeq);
				
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
			em.merge(iuvSeq);
			
		}
	}


}
