/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.dto.business.Pojo;

import java.math.BigDecimal;
import java.util.Set;

public interface IntestatariPojo extends Pojo{

    public String getCorporate();

    public void setCorporate(String corporate);

    public String getAbi();

    public void setAbi(String abi);

    public String getAbiaccentratore();

    public void setAbiaccentratore(String abiaccentratore);

    public String getCab();

    public void setCab(String cab);

    public String getChiavessb();

    public void setChiavessb(String chiavessb);

    public String getCodicepostel();

    public void setCodicepostel(String codicepostel);

    public String getCodicesoftware();

    public void setCodicesoftware(String codicesoftware);

    public String getDenominazione();

    public void setDenominazione(String denominazione);

    public String getGruppo();

    public void setGruppo(String gruppo);

    public String getLapl();

    public void setLapl(String lapl);

    public String getRagionesociale();

    public void setRagionesociale(String ragionesociale);

    public String getRapl();

    public void setRapl(String rapl);

    public String getRcz();

    public void setRcz(String rcz);

    public String getSia();

    public void setSia(String sia);

    public Integer getStato();

    public void setStato(Integer stato);

    public String getTipointestatario();

    public void setTipointestatario(String tipointestatario);

    public String getTiposicurezza();

    public void setTiposicurezza(String tiposicurezza);

    public String getUffpostale();

    public void setUffpostale(String uffpostale);

    public String getCodiceCuc();

    public void setCodiceCuc(String codiceCuc);

    public String getNonresidente();

    public void setNonresidente(String nonresidente);

    public IndirizzipostaliCommon getIndirizzipostaliobj();

    public void setIndirizzipostaliobj(IndirizzipostaliCommon indirizzipostaliobj);

    public Set getFunzioniIntestatari();

    public void setFunzioniIntestatari(Set funzioniIntestatari);

    public String getCategoria();

	public void setCategoria(String categoria);

	public String getSottoCategoria();

	public void setSottoCategoria(String sottoCategoria);

	public BigDecimal getImportoMaxFlusso();

	public void setImportoMaxFlusso(BigDecimal importoMaxFlusso);

}
