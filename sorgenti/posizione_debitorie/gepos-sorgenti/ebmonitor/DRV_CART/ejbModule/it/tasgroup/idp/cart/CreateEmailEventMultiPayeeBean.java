/*******************************************************************************
 * Copyright (c) 2009 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/
/*

 * Copyright (c) 1996-2002 Sun Microsystems, Inc. All Rights Reserved.

 *

 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,

 * modify and redistribute this software in source and binary code form,

 * provided that i) this copyright notice and license appear on all copies of

 * the software; and ii) Licensee does not utilize the software in a manner

 * which is disparaging to Sun.

 *

 * This software is provided "AS IS," without a warranty of any kind. ALL

 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY

 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR

 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE

 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING

 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS

 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,

 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER

 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF

 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE

 * POSSIBILITY OF SUCH DAMAGES.

 *

 * This software is not designed or intended for use in on-line control of

 * aircraft, air traffic, aircraft navigation or aircraft communications; or in

 * the design, construction, operation or maintenance of any nuclear

 * facility. Licensee represents and warrants that it will not use or

 * redistribute the Software for such purposes.

 */
package it.tasgroup.idp.cart;

import it.tasgroup.ge.Eventi;
import it.tasgroup.ge.enums.EnumStatoEventi;
import it.tasgroup.ge.enums.EnumTipiEventi;
import it.tasgroup.idp.bean.CommandExecutor;
import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.pagamenti.DistintePagamento;
import it.tasgroup.iris2.pagamenti.Pagamenti;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */	
@Interceptors(MonitoringInterceptor.class)
@Stateless
@Remote(CommandExecutor.class)
public class CreateEmailEventMultiPayeeBean implements CommandExecutor, CommandExecutorLocal {
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager em;	
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	/**
	 * 
	 */    
	public MonitoringData executeApplicationTransaction() {
		throw new UnsupportedOperationException("Use executeApplicationTransaction(String) instead!!!");
	}

    
    
    public String executeHtml() throws Exception  {	
	   return null;
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(String idGruppo) {
		boolean mailDaInviare = true;
		Query q = em.createNamedQuery("DistintaByIdGruppo");
		q.setParameter("idGruppo", idGruppo);
		Long idDistinta = null;
		List<DistintePagamento> l = (List<DistintePagamento>)q.getResultList();
		if (l!=null && !l.isEmpty()) {
			for (DistintePagamento d: l) {
				idDistinta = d.getId();
				Pagamenti pagam=d.getPagamentis().iterator().next();
				if (!"ESEGUITO".equals(d.getStato())||
					 (!pagam.isAssociatedDocumentAvailable()) 
					) {
					// se lo stato distinta è != ESEGUITO
					// o se il documento non è disponibile (a eccezion fatta per il pagamento in corso perchè non ancora committato)
					mailDaInviare=false;
					logger.info("### generazione evento invio mail: non genero la mail in quanto non e la ricezione dell ultima RT di un carrello multibenef. ###");
					break;
				}
			}
		} else {
			mailDaInviare=false;	
		}
		if (mailDaInviare) {
			   Eventi evento = new Eventi();
			   evento.setCodiceEvento(EnumTipiEventi.AVVISO_RICEZIONE_RT_NDP.getEventoCode());
			   evento.setDatiEvento("" + idDistinta);
			   evento.setNumeroTentativi(0);
			   evento.setStato(EnumStatoEventi.INATTESA.getChiave());

			   em.persist(evento);
			   logger.debug("### generazione evento invio mail ###");
			   for (DistintePagamento d: l) {
				   d.setEmailReceiptSent((short)1);
				   d.setOpAggiornamento("CreateEmailPayee");
			   }
		} else {
			   logger.debug("### NON VIENE generato evento invio mail ###");
		}
		return null;
	}


}
