package it.tasgroup.idp.avvisi.digitali;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.tasgroup.ge.Eventi;

@NamedQueries({

@NamedQuery(
	name="PrenotazAvvisiDaElaborare", 
	query=
	"SELECT pren FROM PrenotazioneAvvisiDigitali pren " +
	" WHERE pren.statoAvviso IN ('I','B') " +
			" OR  ( pren.statoAvviso = 'K' AND pren.numTentativiAvviso <  :maxNumRetry ) "+
	        " OR  ( pren.statoAvviso = 'P' AND pren.tsAggiornamento < :minTsAgg ) "+ 
			" order by pren.tsInserimento desc") ,
@NamedQuery(
		name="PrenotazAvvisiDaElaborareOT", 
		query=
		"SELECT pad FROM PrenotazioneAvvisiDigitali pad " +
				" WHERE pad.idCondizione IN (" +
				"   SELECT cp.idCondizione FROM CondizioniPagamento cp " +
				"   WHERE cp.pendenze.idPendenza IN (" +
				"     SELECT dp.pendenze.idPendenza FROM DestinatariPendenze dp " +
				"     WHERE dp.coDestinatario = :lapl" +
				"   ) " +
				"   AND (" +
				"     (cp.stRiga = 'V' AND cp.tsAnnullamentoMillis = 0)" +
				"   OR" +
				"     (cp.stRiga = 'A' AND cp.tsAnnullamentoMillis <> 0)" +
				"   )" +
				"   AND NOT EXISTS (" +
				"     SELECT 1 FROM Pagamenti p " +
				"     WHERE p.idCondizione = cp.idCondizione " +
				"     AND p.stPagamento IN ('ES', 'EF', 'IC', 'ST') " +
				"   ) " +
				"   AND cp.stPagamento NOT IN ('P', 'R') " +
				" ) " +
				" AND pad.tipoOperazioneOriginale IN ('C', 'D', 'U') " +
				" AND pad.eventoAvv IS NULL"),
@NamedQuery(
		name="PrenotazAvvisiByEnteAndIUV", 
		query=
		"SELECT pren FROM PrenotazioneAvvisiDigitali pren " +
		" WHERE pren.idEnte = :idEnte " +
				" AND pren.idPagamento = :idPagamento "+
		        " AND pren.id <> :id " +
				" order by pren.tsInserimento desc"),
@NamedQuery(
		name="UpdateStatoAndTipoOperazione", 
		query=
		"update PrenotazioneAvvisiDigitali pren set pren.tsAggiornamento =:tsAggiornamento, pren.opAggiornamento =:opAggiornamento, " +
				" pren.statoAvviso          =  :statoAvviso , " +
				" pren.tipoOperazioneAvviso =  :tipoOperazioneAvviso " +
				" WHERE pren.id = :id ")
})
/**
 * The persistent class for the PRENOTA_AVVISI_DIGITALI database table.
 *
 */
@Entity
@Table(name="PRENOTA_AVVISI_DIGITALI")
public class PrenotazioneAvvisiDigitali extends it.tasgroup.idp.domain.BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long       id;
	private String     idCondizione;              // - ID_CONDIZIONE
	private String     idEnte;                    // - ID_ENTE
	private String     idPagamento;               // - ID_PAGAMENTO (IUV)
	private String     codiceAvviso;              // - CODICE_AVVISO
	private String     tipoOperazioneOriginale;   // - TIPO_OPERAZIONE_ORIG ('C','U','D')
	private String     tipoOperazioneAvviso   ;   // - TIPO_OPERAZIONE_AVVISO ('C','U','D')	
	private String     tipoProcesso;              // - TIPO_PROCESSO
	private String     idRichiestaAvviso;         //  - ID_RICHIESTA_AVVISO
	private String     statoAvviso;               //  - STATO_AVVISO
	private String     descrStatoAvviso;          //  - DESCR_STATO_AVVISO
	private Long       numTentativiAvviso;        //  - NUM_TENTATIVI_AVVISO
	private String     idFileAvvisatura;          //  - ID_FILE_AVVISATURA
	private Eventi     eventoSms;                 //  - ID_EVENTO_SMS
	private Eventi     eventoEmail;               //  - ID_EVENTO_EMAIL
	private Eventi     eventoAvv;                 //  - ID_EVENTO_EMAIL
	private Integer    version;                   //  - VERSION
	
	

	
	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="prenota_avvisi_digitali_pk_gen")
	@SequenceGenerator(
	    name="prenota_avvisi_digitali_pk_gen",
	    sequenceName="PRENOTA_AVVISI_DIGITALI_ID_SEQ",
	    allocationSize=1)
	public Long getId() {
		return id;
	}
	
	public void setId(Long newId){
		id=newId;
	}
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="ID_CONDIZIONE")
	public String getIdCondizione() {
		return this.idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	@Column(name="ID_PAGAMENTO")
	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	@Column(name="TIPO_OPERAZIONE_ORIG")
	public String getTipoOperazioneOriginale() {
		return tipoOperazioneOriginale;
	}

	public void setTipoOperazioneOriginale(String tipoOperazioneOriginale) {
		this.tipoOperazioneOriginale = tipoOperazioneOriginale;
	}
	
	@Column(name="ID_FILE_AVVISATURA")
	public String getIdFileAvvisatura() {
		return idFileAvvisatura;
	}

	public void setIdFileAvvisatura(String idFileNodoSpc) {
		this.idFileAvvisatura = idFileNodoSpc;
	}
	@Column(name="ID_RICHIESTA_AVVISO")
	public String getIdRichiestaAvviso() {
		return idRichiestaAvviso;
	}

	public void setIdRichiestaAvviso(String idRichiestaNodoSpc) {
		this.idRichiestaAvviso = idRichiestaNodoSpc;
	}		
	
	@Column(name="CODICE_AVVISO")
	public String getCodiceAvviso() {
		return codiceAvviso;
	}

	public void setCodiceAvviso(String codiceAvviso) {
		this.codiceAvviso = codiceAvviso;
	}
	
	@Column(name="TIPO_PROCESSO")
	public String getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(String tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	@Column(name="TIPO_OPERAZIONE_AVVISO")
	public String getTipoOperazioneAvviso() {
		return tipoOperazioneAvviso;
	}

	public void setTipoOperazioneAvviso(String tipoOperazioneAvviso) {
		this.tipoOperazioneAvviso = tipoOperazioneAvviso;
	}

	@Column(name="STATO_AVVISO")
	public String getStatoAvviso() {
		return statoAvviso;
	}

	public void setStatoAvviso(String statoAvviso) {
		this.statoAvviso = statoAvviso;
	}
    
	@Column(name="DESCR_STATO_AVVISO")
	public String getDescrStatoAvviso() {
		return descrStatoAvviso;
	}

	public void setDescrStatoAvviso(String descrStatoAvviso) {
		this.descrStatoAvviso = descrStatoAvviso;
	}

	@Column(name="NUM_TENTATIVI_AVVISO")
	public Long getNumTentativiAvviso() {
		return numTentativiAvviso;
	}

	public void setNumTentativiAvviso(Long numTentativiAvviso) {
		this.numTentativiAvviso = numTentativiAvviso;
	}

	//bi-directional many-to-one association to Eventi
    @OneToOne
	@JoinColumn(name="ID_EVENTO_SMS", nullable=true)
	public Eventi getEventoSms() {
		return eventoSms;
	}

	public void setEventoSms(Eventi eventoSms) {
		this.eventoSms = eventoSms;
	}

	//bi-directional many-to-one association to Eventi
    @OneToOne
	@JoinColumn(name="ID_EVENTO_EMAIL", nullable=true)
	public Eventi getEventoEmail() {
		return eventoEmail;
	}

	public void setEventoEmail(Eventi eventoEmail) {
		this.eventoEmail = eventoEmail;
	}

	//bi-directional many-to-one association to Eventi
	@OneToOne
	@JoinColumn(name="ID_EVENTO_AVV", nullable=true)
	public Eventi getEventoAvv() {
		return eventoAvv;
	}
	
	public void setEventoAvv(Eventi eventoAvv) {
		this.eventoAvv = eventoAvv;
	}
	
	@Column(name="VERSION")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PrenotazioneAvvisiDigitali ");
		builder.append("[ getId()=");
		builder.append(getId());
		builder.append("  idCondizione= "); 
		builder.append(idCondizione); 
		builder.append(", idEnte= ");       
		builder.append(idEnte);  
		builder.append(", idPagamento= ");
		builder.append(idPagamento);
		builder.append(", codiceAvviso= ");
		builder.append(codiceAvviso);		              
		builder.append(", tipoOperazioneOriginale= ");
		builder.append(tipoOperazioneOriginale);
		builder.append(", tipoOperazioneAvviso= ");
		builder.append(tipoOperazioneAvviso);
		builder.append(", tipoOperazioneAvviso= ");
		builder.append(tipoOperazioneAvviso);
		builder.append(", tipoProcesso= ");
		builder.append(tipoProcesso);
		builder.append(", idFileAvvisatura= "); 
		builder.append(idFileAvvisatura); 
		builder.append(", idRichiestaAvviso= ");  
		builder.append(idRichiestaAvviso);
		builder.append(", statoAvviso= ");  
		builder.append(statoAvviso);  
		builder.append(", descrStatoAvviso= ");  
		builder.append(descrStatoAvviso); 
		builder.append(", descrStatoAvviso= ");  
		builder.append(descrStatoAvviso); 
		builder.append(", numTentativiAvviso= ");  
		builder.append(numTentativiAvviso);
		builder.append(", eventoSms= ");  
		builder.append(eventoSms);
		builder.append(", eventoEmail= ");  
		builder.append(eventoEmail);
		builder.append(", eventoAvv= ");  
		builder.append(eventoAvv);
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		builder.append("]");
		return builder.toString();
	}
    @Override
    public boolean equals(Object obj) {
    	
    	if (obj == null) {
    		return false;
    	}
    	if (!(obj instanceof PrenotazioneAvvisiDigitali)) {
    	   return false;
    	} else {
    		PrenotazioneAvvisiDigitali myPren = (PrenotazioneAvvisiDigitali)obj;
    		if (this.id.equals(myPren.getId()) &&
    	     	 this.idCondizione.equals(myPren.getIdCondizione()) &&              // - ID_CONDIZIONE
    			 this.idEnte.equals(myPren.getIdEnte()) &&                     // - ID_ENTE
    		     this.idPagamento.equals(myPren.getIdPagamento()) &&               // - ID_PAGAMENTO (IUV)
    		     this.codiceAvviso.equals(myPren.getCodiceAvviso()) &&               // - CODICE_AVVISO
    		     this.tipoOperazioneOriginale.equals(myPren.getTipoOperazioneOriginale()) &&   // - TIPO_OPERAZIONE_ORIG ('C','U','D')
    		     this.statoAvviso.equals(myPren.getStatoAvviso()) &&                     //  - STATO_AVVISO   
    		     this.numTentativiAvviso.equals(myPren.getNumTentativiAvviso()) &&        //  - NUM_TENTATIVI_AVVISO
    		     this.tipoProcesso.equals(myPren.getTipoProcesso()))                // - TIPO_PROCESSO
    		      {
    			    if (this.idRichiestaAvviso!=null) { //  - ID_RICHIESTA_AVVISO
    			       if (!this.idRichiestaAvviso.equals(myPren.getIdRichiestaAvviso())) {
    			    	   return false;
    			       }
    			    } else {
    			    	if (myPren.getIdRichiestaAvviso()!=null) {
    			    		return false;
    			    	}
    			    }
    			       
    			    
    			    if (this.tipoOperazioneAvviso!=null) {  // - TIPO_OPERAZIONE_AVVISO ('C','U','D') 
    			    	if (!this.tipoOperazioneAvviso.equals(myPren.getTipoOperazioneAvviso())) {
    			    		return false;
    			    	}
    			    } else {
    			    	if (myPren.getTipoOperazioneAvviso()!=null) {
    			    		return false;
    			    	}
    			    }
   		             
    			    if (this.descrStatoAvviso!=null) {  // - DESCR_STATO_AVVISO
    			    	if (!this.descrStatoAvviso.equals(myPren.getDescrStatoAvviso())) {
    			    		return false;
    			    	}
    			    } else {
    			    	if (myPren.getDescrStatoAvviso()!=null) {
    			    		return false;
    			    	}
    			    }
   		            
    			    if (this.idFileAvvisatura!=null) {  // - DESCR_STATO_AVVISO
    			    	if (!this.idFileAvvisatura.equals(myPren.getIdFileAvvisatura())) {
    			    		return false;
    			    	}
    			    } else {
    			    	if (myPren.getIdFileAvvisatura()!=null) {
    			    		return false;
    			    	}
    			    }
   		            
   		            if (this.eventoSms!=null && myPren.getEventoSms()!=null) { //  - ID_EVENTO_SMS
   		            	if (!this.eventoSms.getId().equals(myPren.getEventoSms().getId())) {
    			    		return false;
    			    	}
   		            } else {
   		            	if (myPren.getEventoSms()!=null) {
    			    		return false;
    			    	}
   		            	if (this.eventoSms!=null) {
    			    		return false;
    			    	}
   		            }
    			    
   		            if (this.eventoEmail!=null && myPren.getEventoEmail()!=null) { //  - ID_EVENTO_EMAIL
   		            	if (!this.eventoEmail.getId().equals(myPren.getEventoEmail().getId())) {
   		            		return false;
   		            	}
   		            } else {
   		            	if (myPren.getEventoEmail()!=null) {
   		            		return false;
   		            	}
   		            	if (this.eventoEmail!=null) {
   		            		return false;
   		            	}
   		            }

   		            if (this.eventoAvv!=null && myPren.getEventoAvv()!=null) { //  - ID_EVENTO_AVV
   		            	if (!this.eventoAvv.getId().equals(myPren.getEventoAvv().getId())) {
   		            		return false;
   		            	}
   		            } else {
   		            	if (myPren.getEventoAvv()!=null) {
   		            		return false;
   		            	}
   		            	if (this.eventoAvv!=null) {
   		            		return false;
   		            	}
   		            }
   		            return true;               
    		} else {
    			return false;
    		}
    	}
    }

	

	
}
