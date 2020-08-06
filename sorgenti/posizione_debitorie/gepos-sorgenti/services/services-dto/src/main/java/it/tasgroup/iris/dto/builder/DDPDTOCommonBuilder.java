/**
 * 
 */
package it.tasgroup.iris.dto.builder;

import it.nch.is.fo.CommonConstant;
import it.tasgroup.iris.dto.ddp.DettaglioDDPDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.dto.ddp.DocumentoRepositoryDTO;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

/**
 * 
 * Classe che costruisce dei DTO per i DDP senza necessitare della dipendenza dalle Entity 
 * per cui si presta a lavorare sia sul FE che sul BE e percio' e' collocata nel progetto services-dto
 * 
 * @author pazzik
 *
 */
public class DDPDTOCommonBuilder {
	
	private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance("JasperReports/config.properties");

	
	public static DocumentoRepositoryDTO populateDocumentoRepositoryDTO(byte[] tmpOutBytes, DocumentoDiPagamentoDTO ddp){
			
			DocumentoRepositoryDTO docRepoDTO = new DocumentoRepositoryDTO();
			
			docRepoDTO.setContent(tmpOutBytes);
			
			docRepoDTO.setSize(tmpOutBytes.length);
			
			DettaglioDDPDTO dettaglio = (DettaglioDDPDTO) ddp.getSpecificDetail();
	
			EnumTipoDDP tipo = dettaglio.getTipo();
			
			String ddpCustomPrefix = conf.getProperty("configuration.ddp.prefix");
			
			String prefix = "";
			if(ddpCustomPrefix.length() > 0)
				prefix = ddpCustomPrefix + "_";
			String downloadFileName = prefix + CommonConstant.DDP_FILE_NAME_PREFIX + tipo + "_" + ddp.getId();
			
			docRepoDTO.setFileName(downloadFileName);
			
		    return docRepoDTO;
		
		}

}
