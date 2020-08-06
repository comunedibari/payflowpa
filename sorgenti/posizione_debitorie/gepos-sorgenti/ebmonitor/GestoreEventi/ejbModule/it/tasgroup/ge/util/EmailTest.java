package it.tasgroup.ge.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import it.tasgroup.ge.pojo.InvioQuietanza;
import it.tasgroup.ge.pojo.MessaggioLogico;

public class EmailTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			EmailUtil eu = new EmailUtil();
			InvioQuietanza invQu = new InvioQuietanza();
			invQu.setDataPagamento(new Date("14/01/2013"));
			invQu.setImporto(new BigDecimal(100.67));
			invQu.setToken("abcdeeeee");
			invQu.setQuietanza("12345678");
			/*
			PlaceHolderData phd = new PlaceHolderData();
			HashMap subjectPhd = new HashMap();
			subjectPhd.put("#TOKEN#", "xxxxxxxxxxxxxx");
			phd.setPlaceHolderObjectMap(subjectPhd);
			HashMap messagePhd = new HashMap();
			messagePhd.put("#QUIETANZA#", "123333");
			messagePhd.put("#DATA_PAGAMENTO#", "14/01/2013");
			messagePhd.put("#IMPORTO#", "100.00");
			phd.setPlaceHolderMessageMap(messagePhd);
			phd.setObjectTemplate("T01.subjectmail");
			phd.setMessageTemplate("T01.bodymail");*/
			MessaggioLogico e = eu.createEmail(invQu,"T01");
			System.out.println(e.getSubject());
			System.out.println(e.getContent());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
