
package it.tasgroup.iris.facade.ejb.confpagamenti;

import it.tasgroup.iris.business.ejb.client.confpagamenti.ConfPagamentiBusinessLocal;
import it.tasgroup.iris.domain.*;
import it.tasgroup.iris.dto.confpagamenti.*;
import it.tasgroup.iris.facade.ejb.client.themes.ThemeFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.themes.ThemeFacadeRemote;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.EntiDTOBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Date;

@Stateless(name = "ThemeFacade")
public class ThemeFacadeBean implements ThemeFacadeLocal, ThemeFacadeRemote {

    private static final Logger LOGGER = LogManager.getLogger(ThemeFacadeBean.class);

    @EJB(name = "ConfPagamentiBusiness")
    private ConfPagamentiBusinessLocal confPagamentoBusinessBean;
    


    @Override
    public CfgEntiTemaDTO getCfgEntiTemaById(String cdEnte) {
        CfgEntiTema tema = confPagamentoBusinessBean.getCfgEntiTemaById(cdEnte);

        if (tema == null) {return null;}

        CfgEntiTemaDTO dto = EntiDTOBuilder.fillCfgEntiTemaDTO(tema);

        return dto;
    }

    public CfgEntiTemaDTO getCfgEntiTemaLiteById (String cdEnte) {
        CfgEntiTema tema = confPagamentoBusinessBean.getCfgEntiTemaLiteById(cdEnte);

        if (tema == null) {return null;}

        CfgEntiTemaDTO dto = EntiDTOBuilder.fillCfgEntiTemaDTO(tema);

        return dto;
    }

    @Override
    public CfgEntiTemaDTO createCfgEntiTema(CfgEntiTemaDTO entiTemaDTO) {

        CfgEntiTema entiTema = EntiDTOBuilder.fillCfgEntiTemaEntity(entiTemaDTO);

        CfgEntiTema newEntiTema =  confPagamentoBusinessBean.createCfgEntiTema(entiTema);

        CfgEntiTemaDTO cfgEntiTemaDTO = EntiDTOBuilder.fillCfgEntiTemaDTO(newEntiTema);

        return cfgEntiTemaDTO;
    }


    @Override
    public CfgEntiTemaDTO updateCfgEntiTema(CfgEntiTemaDTO entiTemaDTO) {

        boolean alreadyPresent = getCfgEntiTemaLiteById(entiTemaDTO.getIdEnte()) != null;
        if (!alreadyPresent) {
            return this.createCfgEntiTema(entiTemaDTO);
        } else {
            
            CfgEntiTema entiTema = EntiDTOBuilder.fillCfgEntiTemaEntity(entiTemaDTO);

            CfgEntiTema updatedTema =  confPagamentoBusinessBean.updateCfgEntiTema(entiTema);

            CfgEntiTemaDTO updatedLogoDTO = EntiDTOBuilder.fillCfgEntiTemaDTO(updatedTema);

            return updatedLogoDTO;
        }


    }

    @Override
    public Date getCfgEntiTemaTsAggiornamento(String cdEnte) {
        return confPagamentoBusinessBean.getCfgEntiTemLastModified(cdEnte);
    }

}
