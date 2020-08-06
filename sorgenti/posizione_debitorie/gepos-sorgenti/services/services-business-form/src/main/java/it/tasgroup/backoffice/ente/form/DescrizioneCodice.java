package it.tasgroup.backoffice.ente.form;

/**
 *
 */
public class DescrizioneCodice {
  private final String descrizione;
  private final String codice;
  
  public DescrizioneCodice(String descrizione, String codice) {
    this.descrizione = descrizione;
    this.codice = codice;
  }
  
  public DescrizioneCodice(String codice) {
    this.codice = codice;
    this.descrizione = this.codice;
  }
  
  public String getDescrizione() {
    return descrizione;
  }
  
  public String getCodice() {
    return codice;
  }
}
