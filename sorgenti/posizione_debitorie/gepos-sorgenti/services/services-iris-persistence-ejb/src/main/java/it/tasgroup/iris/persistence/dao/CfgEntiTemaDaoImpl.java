package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.CfgEntiTema;
import it.tasgroup.iris.dto.confpagamenti.CfgEntiTemaDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CfgEntiTemaDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Stateless(name = "CfgEntiTemaDao")
public class CfgEntiTemaDaoImpl extends DaoImplJpaCmtJta<CfgEntiTema> implements CfgEntiTemaDao {
    private static final Logger LOGGER = LogManager.getLogger(CfgEntiTemaDaoImpl.class);

    @PersistenceContext(unitName = "IrisPU")
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public CfgEntiTema retrieveById(String id) {
        CfgEntiTema retrieved = null;
        try {
            retrieved = loadById(CfgEntiTema.class, id);
        } catch (Exception e) {
            LOGGER.error("error on  retrieveById, ID = " + id, e);
            throw new DAORuntimeException(e);
        }
        return retrieved;
    }

    @Override
    public Date retrieveLastModified(String cdEnte) {
        TypedQuery<Date> getLastModified = em.createNamedQuery("getLastModified", Date.class);
        getLastModified.setParameter("cdEnte", cdEnte);
        Date singleResult = getLastModified.getSingleResult();
        return singleResult;
    }


    @Override
    public CfgEntiTema updateCfgEntiTema(CfgEntiTema entiTema) {

        try {

            CfgEntiTema entiTemaOld = loadById(CfgEntiTema.class, entiTema.getCdEnte());

            if (entiTema.getNomeImgLogo() != null && entiTema.getLogoBlob().length > 0) {
                entiTemaOld.setLogoBlob(entiTema.getLogoBlob());
                entiTemaOld.setNomeImgLogo(entiTema.getNomeImgLogo());
            } else if (entiTema.getNomeImgLogo() == null || "".equals(entiTema.getNomeImgLogo())){
                entiTemaOld.setLogoBlob(null);
                entiTemaOld.setNomeImgLogo(null);
            }


            if (entiTema.getNomeImgHeader() != null && entiTema.getHeaderBlob().length >0) {
                entiTemaOld.setHeaderBlob(entiTema.getHeaderBlob());
                entiTemaOld.setNomeImgHeader(entiTema.getNomeImgHeader());
            } else if (entiTema.getNomeImgHeader() == null || "".equals(entiTema.getNomeImgHeader())) {
                entiTemaOld.setHeaderBlob(null);
                entiTemaOld.setNomeImgHeader(null);
            }



            entiTemaOld.setIdTema(entiTema.getIdTema());
            entiTemaOld.setInformazioni(entiTema.getInformazioni());
            entiTemaOld.setOpAggiornamento(entiTema.getOpAggiornamento());
            entiTemaOld.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));


            CfgEntiTema updated = this.update(entiTemaOld);

            return updated;

        } catch (Exception e) {
            LOGGER.error("error on  updateCfgEntiTema, " + entiTema, e);
            throw new DAORuntimeException(e);
        }
    }

    @Override
    public CfgEntiTema createCfgEntiTema(CfgEntiTema entiTema) {
        try {

            CfgEntiTema created = create(entiTema);

            return created;

        } catch (Exception e) {
            LOGGER.error("error on  createCfgEntiTema, " + entiTema, e);
            throw new DAORuntimeException(e);
        }
    }

    @Override
    public CfgEntiTema retrieveLiteById(String cdEnteParm) {
        Query getCfgEntiTemaLite = em.createNamedQuery("getCfgEntiTemaLiteWithTS");
        getCfgEntiTemaLite.setParameter("cdEnte", cdEnteParm);
        List<CfgEntiTema> results = getCfgEntiTemaLite.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
