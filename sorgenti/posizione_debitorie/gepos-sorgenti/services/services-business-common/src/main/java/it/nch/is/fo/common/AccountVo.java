package it.nch.is.fo.common;

import java.util.Collection;

public class AccountVo {
	
	public String accountNumber;
	public String rapporto;
	
	public Collection cards;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Collection getCards() {
		return cards;
	}

	public void setCards(Collection cards) {
		this.cards = cards;
	}

	public String getRapporto() {
		return rapporto;
	}

	public void setRapporto(String rapporto) {
		this.rapporto = rapporto;
	}

}
