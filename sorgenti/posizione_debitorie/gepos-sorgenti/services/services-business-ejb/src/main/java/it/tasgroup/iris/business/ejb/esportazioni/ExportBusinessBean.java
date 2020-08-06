package it.tasgroup.iris.business.ejb.esportazioni;

import it.nch.erbweb.common.PreferenzeForm;
import it.nch.erbweb.common.PreferenzeVO;
import it.nch.erbweb.orm.Jltpref;
import it.nch.erbweb.orm.JltprefId;
import it.nch.erbweb.profilo.ProfiloInputVO;
import it.nch.fwk.fo.core.exception.ManageBackEndException;
import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.posizionedebitoria.PreferenzaEsportazioneVO;
import it.nch.is.fo.BackEndMessage;
import it.nch.is.fo.common.CodiceDescrizioneVO;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.business.ejb.client.IrisCacheSingletonLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.IntestatariBusinessLocal;
import it.tasgroup.iris.business.ejb.client.ddp.DDPBusinessLocal;
import it.tasgroup.iris.business.ejb.client.esportazioni.ExportBusinessLocal;
import it.tasgroup.iris.business.ejb.client.esportazioni.ExportBusinessRemote;
import it.tasgroup.iris.domain.Esportazioni;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.ddp.DocumentoRepositoryDTO;
import it.tasgroup.iris.persistence.dao.interfaces.PrenotazioniDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoPrenotazioniDao;
import it.tasgroup.report.exporter.dynamic.DynaReportExporter;
import it.tasgroup.services.util.enumeration.EnumDynaReportFormat;
import it.tasgroup.services.util.enumeration.EnumExportSTDFormat;
import it.tasgroup.services.util.enumeration.EnumStatoExport;
import it.tasgroup.services.util.enumeration.EnumTipoExport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

@Stateless(name = "ExportBusiness") 
public class ExportBusinessBean implements ExportBusinessLocal, ExportBusinessRemote {
	
    private static final Logger LOGGER = LogManager.getLogger(ExportBusinessBean.class);

    @EJB(name = "PrenotazioniDaoImpl")
    PrenotazioniDao prenotazioniDAO;
    
    @EJB(name = "StoricoPrenotazioniDaoImpl")
    StoricoPrenotazioniDao storicoPrenotazioniDAO;

    @EJB
    IntestatariBusinessLocal intestatariBusinessBean;
    
    @EJB
    DDPBusinessLocal ddpBusinessBean;
    
    @EJB
	private IrisCacheSingletonLocal irisCache;

    @Override
    public void  esporta(Prenotazioni prenotazione, Map<String, String> mapListaCampi, String cfOperatore,String riga,String separatore,List outputDTO,
    		String myvaloreselezionato,String tipoEsportazione, EnumDynaReportFormat dynaReportFormat, EnumExportSTDFormat enumExportSTDFormat, Class clazz, Locale locale) {
    	esporta(prenotazione, mapListaCampi, cfOperatore, riga, separatore, outputDTO,
        		 myvaloreselezionato, tipoEsportazione, dynaReportFormat, enumExportSTDFormat, clazz, locale, null);
    }

    @Override
    public void  esporta(Prenotazioni prenotazione, Map<String, String> mapListaCampi, String cfOperatore,String riga,String separatore,List outputDTO,
    		String myvaloreselezionato,String tipoEsportazione, EnumDynaReportFormat dynaReportFormat, EnumExportSTDFormat enumExportSTDFormat, Class clazz, 
    		Locale locale, String customFileName) {
    	
    	EnumTipoExport tipo;
    	
    	tipo = EnumTipoExport.getByChiave(tipoEsportazione);
    	
        Map<String, String> mapEtichette = mapListaCampi;

        List<String[]> campi = new ArrayList<String[]>();

        String[] valSel = myvaloreselezionato.split(";");
        
        for (int i = 0; i < valSel.length; i++) {
        	
            if (!valSel[i].equals("")) {
            	
                String campo = (String) valSel[i];
                
                campi.add(getCampo(campo,clazz));
                
            }
        }

        String stato;

        try {
        	
            Esportazioni exp = new Esportazioni();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            DynaReportExporter.esporta(dynaReportFormat, enumExportSTDFormat, outputDTO, campi, riga != null && riga.equals("on"), separatore, baos, mapEtichette, locale);
            
            Timestamp now = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss");
            String filename = customFileName;
            if (filename == null) {
            	StringBuilder filenameSB = new StringBuilder("export-" + tipo.getDescrizione() + "-");
            	filenameSB.append(dateFormatter.format(now));
            	if(cfOperatore != null)
            		filenameSB.append("-").append(cfOperatore);
            	filename = filenameSB.toString();
            } 
            stato = EnumStatoExport.DISPONIBILE.getDescrizione();

            exp.setDati(zipBytes(filename + ".csv", baos.toByteArray()));
            exp.setCompressione("S");
            exp.setFormato("zip");
            exp.setLenDati(new Long(baos.size()));
            exp.setNomeFile(filename);
            exp.setOpInserimento("esportaGestioneFlussiFull");
            exp.setTsInserimento(now);
            exp.setPrenotazione(prenotazione);
            
            Set<Esportazioni> esportazioni = new LinkedHashSet();
            esportazioni.add(exp);
            prenotazione.setEsportazioni(esportazioni);

        } catch (Exception e) {
        	
            e.printStackTrace();
            stato = EnumStatoExport.ERRORE.getDescrizione();

        }
        
        prenotazione.setStato(stato);
        prenotazione.setTipoEsportazione(tipo.getDescrizione());
        prenotazione.setOpAggiornamento("esportaGestioneFlussiFull");
        prenotazione.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
        prenotazioniDAO.updatePrenotazione(prenotazione);
    }

    private String[] getCampo(String nomeCampo,Class clazz) {

        try {
            Field field = clazz.getDeclaredField(nomeCampo);
            String[] campo = new String[2];
            campo[0] = field.getName();
            campo[1] = field.getType().getName();
            return campo;
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prenotazioni creaPrenotazione(ContainerDTO inputDTO, IProfileManager profile, String cfOperatore) {
        
    	Prenotazioni prenotazione = new Prenotazioni();
    	
        PreferenzaEsportazioneVO vo = (PreferenzaEsportazioneVO) inputDTO.getInputDTOList().get(0);

        Intestatari intestatarioDDP = intestatariBusinessBean.readIntestatarioById(profile.getAzienda());

        prenotazione.setIntestatario(intestatarioDDP);
        prenotazione.setStato(EnumStatoExport.IN_CORSO.getDescrizione());
        prenotazione.setCodRich(vo.getExport().getSigla());
        prenotazione.setTipoServizio(vo.getTipoServizio());
        prenotazione.setTipoEsportazione(vo.getTipoEsportazione().getChiave());
        prenotazione.setOpInserimento(cfOperatore);
        prenotazione.setTsInserimento(new Timestamp(System.currentTimeMillis()));

        try {
        	
            prenotazione = prenotazioniDAO.creaPrenotazione(prenotazione);
            
        } catch (Exception e) {
        	
            e.printStackTrace();
            prenotazione = null;
            
        }
        return prenotazione;

    }

    @Override
    public Prenotazioni updatePrenotazione(ContainerDTO inputDTO) throws Exception {
        Prenotazioni pren = (Prenotazioni) inputDTO.getInputDTO();
        // TODO Auto-generated method stub
        try {
            return prenotazioniDAO.update(pren);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public boolean deletePrenotazione(Long idPrenotazione) throws Exception {
        try {
            prenotazioniDAO.deletePrenotazione(idPrenotazione);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("errore durante la cancellazione della prenotazione: ", e);
            throw e;
        }
        return true;
    }

    public static void zipBytes(ZipOutputStream zos, String filename, byte[] input) throws IOException {
        ZipEntry entry = new ZipEntry(filename);
        entry.setSize(input.length);
        zos.putNextEntry(entry);
        zos.write(input);
        zos.closeEntry();
    }

    public static byte[] zipBytes(String filename, byte[] input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        zipBytes(zos, filename, input);
        zos.close();
        return baos.toByteArray();
    }
    
    
    
    @Override
    public HashMap listaPreferenzeExport(Collection lista, ProfiloInputVO inputVo) throws Exception {
    	
    	String propertyPrefix = inputVo.getTipoEsportazione().getPropertyPrefix();
    			
    	HashMap ret = new HashMap();
		if(lista.size() == 0) return null;
		String[] fields = new String[lista.size()-2];
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			PreferenzeVO vo = (PreferenzeVO) iterator.next();
			String proprieta = vo.getProprieta();
			String valore = vo.getValore();
			if(proprieta.startsWith(propertyPrefix+"export.") ){
				if(proprieta.equals(propertyPrefix+"export.separatore") )
					ret.put("MySelectSeparatore", valore);
				else if (proprieta.equals(propertyPrefix+"export.intestazione"))
					ret.put("RigaIntestazione", valore);
				else if(!valore.equals("_"))
					try {
						int pos = new Integer(valore).intValue();
						
						fields[pos] = proprieta;
					} catch (NumberFormatException e) {
						LOGGER.info("il campo ["+proprieta+"] ha un indice di ordine inappropriato");
					}catch (Exception ex) {
						LOGGER.error("il campo ["+proprieta+"] va in eccezione",ex);
					}
			}
		}
		ret.put("Myvaloreselezionato", getFields(fields));
		
		return ret;
    }
    
    private String getFields(String[] fields) {
		if(fields == null || fields.length == 0)return null;
		
		String ret="";
		for (int i = 0; i < fields.length; i++) {
			
			if(fields[i] != null) {
				String campo = fields[i].substring(fields[i].indexOf(".")+1);
				ret += campo+";";
			}
		}
		return ret;
	}
    
    

    @Override
    public List listaPreferenze(IProfileManager profilo, ProfiloInputVO inputVO) throws Exception {
        Tracer.debug(getClass().getName(), "", " START ExportBusinessBean >>> listaPreferenze <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        List<PreferenzeVO> preferenzeList = new ArrayList<PreferenzeVO>();
        List preferenzeListFiltered = new ArrayList();
        
        String propertyPrefix = inputVO.getTipoEsportazione().getPropertyPrefix();
    	
        try {
        	
            preferenzeList = prenotazioniDAO.listPreferenze(inputVO.getIntestatario(), profilo.getUsername(), inputVO.getTipoServizio(), propertyPrefix);
            Iterator iter = preferenzeList.iterator();
            Hashtable table = new Hashtable();
            while (iter.hasNext()) {
                PreferenzeVO element = (PreferenzeVO) iter.next();
               if (table.get(element.getProprieta()) == null) {
                    table.put(element.getProprieta(), element);
                }
            }
            Enumeration tableElements = table.elements();
            while (tableElements.hasMoreElements()) {
                PreferenzeVO pref = (PreferenzeVO) tableElements.nextElement();
                String lista = pref.getListaValori();
                if (lista != null && lista.length() > 0) {
                    StringTokenizer tokens = new StringTokenizer(lista, ",");
                    List listaValori = new ArrayList();
                    while (tokens.hasMoreTokens()) {
                        String element = (String) tokens.nextElement();
                        listaValori.add(element);
                    }
                    pref.setListValori(listaValori);
                }
                preferenzeListFiltered.add(pref);
            }
        } catch (HibernateException e) {
            Tracer.error(this.getClass().getName(), "Impossibile effettuare l'operazione : listaPreferenze ", "", e);
            new ManageBackEndException().processDAOException(e, BackEndMessage.DAO_0010);
        }
        Tracer.debug(getClass().getName(), "", " END ExportBusinessBean >>> listaPreferenze <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        return preferenzeListFiltered;
    }

    @Override
    public void aggiornaPreferenze(IProfileManager profilo, PreferenzaEsportazioneVO form) throws Exception {
//    	PreferenzaEsportazioneVO rform = form.getRicercaDistintePagamentoForm().getPreferenzeVo();
    
    	
    	String propertyPrefix = form.getTipoEsportazione().getPropertyPrefix();
    	
    	
    	PreferenzeForm pform = new PreferenzeForm(); 
		String elencoPref= new String(); 
		String r = form.getRigaIntestazione();
		String valueRigaInt = r == null || !r.equals("on") ? "off" : "on" ;

		elencoPref = propertyPrefix+"export.separatore,"+form.getMySelectSeparatore()+
				    "-" +
				    propertyPrefix+"export.intestazione,"+valueRigaInt;
		String myvaloreselezionato = form.getMyvaloreselezionato();
		
		String[] fields = myvaloreselezionato.split(";");
		
		for (CodiceDescrizioneVO vo : form.getListaCampi()) {
			
			int pos = getPosizione(fields,vo.getCodice());
			
			elencoPref += pos >= 0 ? "-"+propertyPrefix+"export."+vo.getCodice()+","+pos : "-"+propertyPrefix+"export."+vo.getCodice()+",_";
		}
		
		pform.setElencoPreferenze(elencoPref);
		StringTokenizer st = new StringTokenizer(pform.getElencoPreferenze(), "-");
		List listaPreferenze = new ArrayList();
		while (st.hasMoreTokens()) {
			listaPreferenze.add(st.nextToken());
		}
		
		PreferenzeVO prefVo = new PreferenzeVO();
		prefVo.setListValori(listaPreferenze);
		String aziendaSelezionata = profilo.getAzienda();
		prefVo.setIntestatario(aziendaSelezionata);
		prefVo.setOperatore(profilo.getUsername());
		prefVo.setTiposervizio(form.getTipoServizio());
    	
        List preferenzeAggiornate = prefVo.getListValori();
        Iterator preferenzeAggiornateIter = preferenzeAggiornate.iterator();
        String proprieta, valore;
        
        ProfiloInputVO inputVO = form.getProfilo();
        
        prenotazioniDAO.deletePreferenze(inputVO.getIntestatario(), profilo.getUsername(), inputVO.getTipoServizio(), propertyPrefix);
        
        while (preferenzeAggiornateIter.hasNext()) {
            String preferenzaStr = (String) preferenzeAggiornateIter.next();
            st = new StringTokenizer(preferenzaStr, ",");
            if (st.countTokens() == 2) {
                proprieta = st.nextToken();
                valore = st.nextToken();
                Jltpref jltpref = composePO(prefVo, proprieta, valore);
                prenotazioniDAO.savePreferenza(jltpref);
            }
        }
    }
    
    private int getPosizione(String[] fields, String descrizione) {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals(descrizione))
			  return i;
		}
		return -1;
	}

    private Jltpref composePO(PreferenzeVO prefVO, String proprieta, String valore) {
        JltprefId jltprefId = new JltprefId();
        jltprefId.setCategoria("-");
        jltprefId.setIntestatario(prefVO.getIntestatario());
        jltprefId.setOperatore(prefVO.getOperatore());
        jltprefId.setProprieta(proprieta);
        jltprefId.setTiposervizio(prefVO.getTiposervizio());
        Jltpref jltpref = new Jltpref();
        jltpref.setValore(valore);
        jltpref.setJltprefId(jltprefId);
        Tracer.debug(getClass().getName(),"",
                "PREF > " + proprieta + " VAL " + valore + " tipo " + prefVO.getTiposervizio() + " OPER " + prefVO.getOperatore() + " INT "
                        + prefVO.getIntestatario());
        return jltpref;
    }

    @Override
    public void esportaQuietanze(List<DocumentoRepositoryDTO> lDoc, String cfOperatore, Prenotazioni prenotazione) throws Exception {
       String stato;
        try {
            Esportazioni exp = new Esportazioni();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);
            for (DocumentoRepositoryDTO doc : lDoc) {
            	LOGGER.debug("id pagamento: " + doc.getId() + " - id doc repository: " + doc.getId());
                zipBytes(zos, doc.getFileName() + "_" + doc.getId() + ".pdf", doc.getContent());
            }
            LOGGER.info("n. documenti: " + lDoc.size());
            zos.close();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
            StringBuilder filename = new StringBuilder("export-quietanze-");
            filename.append(dateFormatter.format(now));
            if(cfOperatore != null){
                filename.append("-").append(cfOperatore);
            }

            stato = EnumStatoExport.DISPONIBILE.getDescrizione();
            exp.setDati(baos.toByteArray());
            exp.setCompressione("S");
            exp.setFormato("zip");
            exp.setLenDati(new Long(baos.size()));
            exp.setNomeFile(filename.toString());
            exp.setOpInserimento("esportaQuietanzeGestioneFlussiFull");
            exp.setTsInserimento(now);
            exp.setPrenotazione(prenotazione);
            Set<Esportazioni> esportazioni = new LinkedHashSet();
            esportazioni.add(exp);
            prenotazione.setEsportazioni(esportazioni);

        } catch (Exception e) {
            e.printStackTrace();
            stato = EnumStatoExport.ERRORE.getDescrizione();
        }
        prenotazione.setStato(stato);
        prenotazione.setTipoEsportazione(EnumTipoExport.PAGAMENTI.getChiave());
        prenotazione.setOpAggiornamento("esportaGestioneFlussiFull");
        prenotazione.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
        prenotazioniDAO.updatePrenotazione(prenotazione);
        System.out.println("esportaGestioneFlussiFull - salvata la prenotazione");
    }

    @Override
    public List<Prenotazioni> listaPrenotazioni(ContainerDTO inputDTO) {
        List<Prenotazioni> ret = null;
        try {
            ret = prenotazioniDAO.listPrenotazioni(inputDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    @Override
    public List<Prenotazioni> listaPrenotazioniStorico(ContainerDTO inputDTO) {
        List<Prenotazioni> ret = null;
        try {
            ret = storicoPrenotazioniDAO.listPrenotazioni(inputDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public Esportazioni getEsportazioni(Long idPren) {
        Esportazioni ret = null;
        try {
            Prenotazioni pren = prenotazioniDAO.getById(Prenotazioni.class, idPren);
            if (!pren.getEsportazioni().isEmpty())
                ret = pren.getEsportazioni().iterator().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    
}
