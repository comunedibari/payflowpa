package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.regioneveneto.mygov.payment.mypivot.dao.OperatoreEnteTipoDovutoDao;
import it.regioneveneto.mygov.payment.mypivot.domain.po.OperatoreEnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.OperatoreEnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

@Service("operatoreEnteTipoDovutoService")
public class OperatoreEnteTipoDovutoServiceImpl implements OperatoreEnteTipoDovutoService {
	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@Autowired
	private OperatoreEnteTipoDovutoDao operatoreEnteTipoDovutoDao;

	public List<OperatoreEnteTipoDovutoTO> findActiveByCodFedUserIdAndCodIpaEnte(final String codFedUserId,
			final String codIpaEnte) {
		Assert.notNull(codFedUserId, "CodFedUserId nullo");
		Assert.notNull(codIpaEnte, "Codice IPA Ente nullo");
		List<OperatoreEnteTipoDovuto> listaOetd = operatoreEnteTipoDovutoDao.findByCodFedUserId(codFedUserId);
		if (listaOetd == null)
			throw new IllegalArgumentException();
		List<OperatoreEnteTipoDovutoTO> listaOetdTO = new ArrayList<OperatoreEnteTipoDovutoTO>();
		if (CollectionUtils.isNotEmpty(listaOetd)) {
			for (OperatoreEnteTipoDovuto oetd : listaOetd) {
				if (codIpaEnte.equals(oetd.getEnteTipoDovuto().getEnte().getCodIpaEnte()) && oetd.isFlgAttivo()) {
					OperatoreEnteTipoDovutoTO to = modelMapperUtil.map(oetd, OperatoreEnteTipoDovutoTO.class);
					listaOetdTO.add(to);
				}
			}
		}
		return listaOetdTO;
	}

	public List<EnteTipoDovutoTO> getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(final String codFedUserId,
			final String codIpaEnte) {
		Assert.notNull(codFedUserId, "CodFedUserId nullo");
		Assert.notNull(codIpaEnte, "Codice IPA Ente nullo");
		List<EnteTipoDovutoTO> listaEnteTipoDovuto = new ArrayList<EnteTipoDovutoTO>();
		List<OperatoreEnteTipoDovutoTO> listaOetd = SecurityContext.getAllOperatoreEnteTipoDovuto();
		if (listaOetd == null) {
			listaOetd = findActiveByCodFedUserIdAndCodIpaEnte(codFedUserId, codIpaEnte);
		}
		for (OperatoreEnteTipoDovutoTO oetd : listaOetd) {
			EnteTipoDovutoTO etdTO = oetd.getEnteTipoDovuto();
			listaEnteTipoDovuto.add(etdTO);
		}

		return listaEnteTipoDovuto;
	}

	public List<EnteTipoDovutoTO> getListaEnteTipoDovutoForOperatoreAndCodIpaEnteNoSession(final String codFedUserId,
			final String codIpaEnte) {
		Assert.notNull(codFedUserId, "CodFedUserId nullo");
		Assert.notNull(codIpaEnte, "Codice IPA Ente nullo");
		List<EnteTipoDovutoTO> listaEnteTipoDovuto = new ArrayList<EnteTipoDovutoTO>();
		List<OperatoreEnteTipoDovutoTO> listaOetd = findActiveByCodFedUserIdAndCodIpaEnte(codFedUserId, codIpaEnte);
		for (OperatoreEnteTipoDovutoTO oetd : listaOetd) {
			EnteTipoDovutoTO etdTO = oetd.getEnteTipoDovuto();
			listaEnteTipoDovuto.add(etdTO);
		}

		return listaEnteTipoDovuto;
	}
	
	public List<String> getListaCodTipoDovutoForOperatoreAndCodIpaEnte(final String codFedUserId,
			final String codIpaEnte) {
		Assert.notNull(codFedUserId, "CodFedUserId nullo");
		Assert.notNull(codIpaEnte, "Codice IPA Ente nullo");
		List<String> listaCodTipoDovuto = new ArrayList<String>();
		List<EnteTipoDovutoTO> listaEtdTO = getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(codFedUserId, codIpaEnte);
		for (EnteTipoDovutoTO etdTO : listaEtdTO) {
			listaCodTipoDovuto.add(etdTO.getCodTipo());
		}
		return listaCodTipoDovuto;
	}

	public boolean isCodTipoDovutoValidoPerOperatoreAndCodIpaEnte(final String codFedUserId, final String codIpaEnte,
			final String codTipoDovuto) {
		Assert.notNull(codFedUserId, "CodFedUserId nullo");
		Assert.notNull(codIpaEnte, "Codice IPA Ente nullo");
		Assert.notNull(codTipoDovuto, "CodTipoDovuto nullo");
		List<String> listaCodTipoDovuto = getListaCodTipoDovutoForOperatoreAndCodIpaEnte(codFedUserId, codIpaEnte);
		if (listaCodTipoDovuto.contains(codTipoDovuto))
			return true;
		return false;
	}
}
