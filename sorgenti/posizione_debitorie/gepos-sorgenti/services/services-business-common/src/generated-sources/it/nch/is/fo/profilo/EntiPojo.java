package it.nch.is.fo.profilo;

import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.is.fo.enti.TipologiaEntiCommon;

import java.sql.Timestamp;

public interface EntiPojo extends Pojo {

	public String getCodiceEnte();

	public void setCodiceEnte(String codiceEnte);

	public String getDenominazione();

	public void setDenominazione(String denominazione);

	public String getIdEnte();

	public void setIdEnte(String idEnte);

	public String getOpAggiornamento();

	public void setOpAggiornamento(String opAggiornamento);

	public String getOpInserimento();

	public void setOpInserimento(String opInserimento);

	public int getPrVersione();

	public void setPrVersione(int prVersione);

	public String getProvincia();

	public void setProvincia(String provincia);

	public String getStato();

	public void setStato(String stato);

	public TipologiaEntiCommon getTipoEnteobj();

	public void setTipoEnteobj(TipologiaEntiCommon tipoEnteobj);

	public Timestamp getTsAggiornamento();

	public void setTsAggiornamento(Timestamp tsAggiornamento);

	public Timestamp getTsInserimento();

	public void setTsInserimento(Timestamp tsInserimento);
	
	public Integer getMaxNumTributi();

	public void setMaxNumTributi(Integer maxNumTributi);
}
