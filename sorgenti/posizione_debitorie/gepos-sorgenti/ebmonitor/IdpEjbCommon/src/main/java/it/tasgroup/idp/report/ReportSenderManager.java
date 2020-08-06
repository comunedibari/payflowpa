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
package it.tasgroup.idp.report;

import it.tasgroup.idp.bean.ObjectCommandExecutorLocal;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ReportSenderManager 
	implements ObjectCommandExecutorLocal {


	private final Log logger = LogFactory.getLog(this.getClass());
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;
 	
    @Resource(mappedName = "java:/Mail")
    private Session mailSession;  


	@Override
	public MonitoringData executeApplicationTransaction(Object data) {
		String report=null;
		boolean containError = false;
		String subject = "REPORT RENDICONTAZIONI";
		if (data instanceof ReportBean) {
			report = ((ReportBean)data).getReport();
			containError = ((ReportBean)data).isHasError();
			if (containError) {
				subject = "REPORT RENDICONTAZIONI: ANOMALIE PRESENTI";
			} else {
				subject = "REPORT RENDICONTAZIONI: NESSUNA ANOMALIA";
			}
		} else {
		   report = (String)data;
		}
//		LISTA PROPERTIES DISPONIBILI
//		EMAIL_ADDRESS_FROM=system.iris@tasgroup.it
//				EMAIL_ADDRESS_TO=monitor.iris@tasgroup.it
//				EMAIL_ADDRESS_CC=
//				# PRIORITY VALUES: HIGH, NORMAL, LOW 
//				EMAIL_PRIORITY=HIGH
//				# ATTACHMENTS
//				EMAIL_ADDRESS_ATTACHMENT_PAYLOAD=true
//				EMAIL_ADDRESS_ATTACHMENT_EXCEPTION=true
		
		try {
			
			String emailTO = IrisProperties.getProperty("EMAIL_ADDRESS_TO");
			String emailCC = IrisProperties.getProperty("EMAIL_ADDRESS_CC");			
			
			sendEmail(report, emailTO , emailCC, subject);
			
		} catch (Exception e) {
			logger.error(" ReportSender = " + e.getMessage());
		}		
		
		
		return null;
	}


	@Override
	public String executeHtml(String msg, String sendId, String sendSys) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * 
	 * @param report
	 * @param emailCC 
	 * @param emailTO 
	 */
	private void sendEmail(String report, String emailTO, String emailCC, String subject) {
		String to = emailTO;
	    String msg = report;
	    // Create the message object
	    Message message = new MimeMessage(mailSession);
	
	    // Adjust the recipients. Here we have only one
	    // recipient. The recipient's address must be
	    // an object of the InternetAddress class.
	    try {
			message.setRecipients(Message.RecipientType.TO,
			               InternetAddress.parse(to, false));
			
	        // Set the message's subject
	        message.setSubject(subject);
	
	        // Insert the message's body
	        message.setText(msg);
	
	        // This is not mandatory, however, it is a good
	        // practice to indicate the software which
	        // constructed the message.
	        message.setHeader("X-Mailer", "My Mailer");
	
	        // Adjust the date of sending the message
	        Date timeStamp = new Date();
	        message.setSentDate(timeStamp);
	
	        // Use the 'send' static method of the Transport
	        // class to send the message
	        Transport.send(message);
	        
		} catch (AddressException e) {
			logger.error(" ReportSender = " + e.getMessage());
		} catch (MessagingException e) {
			logger.error(" ReportSender = " + e.getMessage());
		}
	}


}
