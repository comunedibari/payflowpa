package it.tasgroup.idp.proxyndp.utils;

import it.tasgroup.idp.util.IrisProperties;

public class NDPConstants {

	public static final String CURRENCY = "EUR";
	public static final String ST_RIGA_V = "V";
	public static final String ST_RIGA_N = "N";
	public static final String GATEWAY_NODO_PSP = "NODO DEI PAGAMENTI-SPC";
	public static final String NDP_BUNDLE = "NDP";
	public static final String OPERATOR_NODO_CHIEDI_INFO_PSP = "nodoChiediInformativaPSP";
	public static final String ACTIVE = "ATTIVO";
	public static final String TO_ACTIVATE = "DA ATTIVARE";
	public static final String DEACTIVE = "DISATTIVO";
	public static final String DIR_FLUSSI_RIVERSAMENTO_SFTP = "dir.flussi.riversamento.sftp";


	public String getNdpWsUrl() throws Exception {
    	 return IrisProperties.getProperty("iris.nodoDeiPagamenti.wsUrl");
    };
    
	public String getIntermediarioPA() throws Exception {
		return IrisProperties.getProperty("iris.nodoDeiPagamenti.intermediarioPA");
	};
	
	public String getCodiceStazionePA() throws Exception {
		return IrisProperties.getProperty("iris.nodoDeiPagamenti.codiceStazionePA");
	};
	
	public String getPasswordNdp() throws Exception {
		return IrisProperties.getProperty("iris.nodoDeiPagamenti.passwordNdp");
	};
	
	public String getIdDominio() throws Exception {
		return IrisProperties.getProperty("iris.nodoDeiPagamenti.idDominio");
	};
	
	public boolean isUsaProxyNdp() throws Exception {
		return "true".equals(IrisProperties.getProperty("iris.nodoDeiPagamenti.usaProxyNdp"));
	}
    
	public String getHttpBasicUser() throws Exception {
		return IrisProperties.getProperty("iris.nodoDeiPagamenti.httpBasicUser");
	};
	
	public String getHttpBasicPassword() throws Exception {
		return IrisProperties.getProperty("iris.nodoDeiPagamenti.httpBasicPassword");
	};

	public String getDirFlussiRiversamentoSftp() {
		return IrisProperties.getProperty(DIR_FLUSSI_RIVERSAMENTO_SFTP);
	}

	public String getNdpInviaAvvisoWsUrl() {		
		return IrisProperties.getProperty("iris.nodoDeiPagamenti.inviaAvviso.wsUrl");
	};

	public String getIdDominioForCatalogoPsp() {
		return IrisProperties.getProperty("iris.nodoDeiPagamenti.catalogoPsp.idDominio");
	}

	public boolean isRequestByIdDominioElencoFlussiRend() {
		return "true".equals(IrisProperties.getProperty("iris.nodoDeiPagamenti.elencoFlussi.usaIdDominio"));
	}

}
