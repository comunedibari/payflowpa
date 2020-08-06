package it.nch.is.fo.enti;

import it.nch.fwk.fo.dto.business.Pojo;

import java.sql.Timestamp;

public interface TipologiaEntiPojo extends Pojo {

	public String getTipoEnte();

	public void setTipoEnte(String tipoEnte);

	public String getDescrizione();

	public void setDescrizione(String descrizione);

	public String getStato();

	public void setStato(String stato);

	public int getPrVersione();

	public void setPrVersione(int prVersione);

	public String getOpAggiornamento();

	public void setOpAggiornamento(String opAggiornamento);

	public String getOpInserimento();

	public void setOpInserimento(String opInserimento);

	public Timestamp getTsAggiornamento();

	public void setTsAggiornamento(Timestamp tsAggiornamento);

	public Timestamp getTsInserimento();

	public void setTsInserimento(Timestamp tsInserimento);
}
