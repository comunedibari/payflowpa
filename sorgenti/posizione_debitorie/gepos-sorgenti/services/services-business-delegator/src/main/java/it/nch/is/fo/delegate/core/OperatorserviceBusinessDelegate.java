package it.nch.is.fo.delegate.core;

import it.nch.erbweb.client.FrontEndMessage;
import it.nch.fwk.fo.cross.AbstractBusinessDelegate;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOInfoInterface;
import it.nch.fwk.fo.dto.DTOInfoList;
import it.nch.fwk.fo.exceptions.BusinessDelegateRuntimeException;
import it.nch.fwk.fo.exceptions.ManageFrontEndException;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.locator.ServiceLocatorException;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.core.interfaces.OperatorserviceServiceLocal;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;

@SuppressWarnings("rawtypes")
public class OperatorserviceBusinessDelegate extends AbstractBusinessDelegate implements OperatorserviceServiceLocal {

	private OperatorserviceServiceLocal operatorserviceService;

	public OperatorserviceBusinessDelegate() throws ServiceLocatorException {

		Tracer.info(this.getClass().toString(), "Costruttore", "Inizio ", null);
	  	try {
	  		 
	  		operatorserviceService = (OperatorserviceServiceLocal)ServiceLocator.getSLSBProxy("operatorserviceBean");
	  	
	  	} catch (it.tasgroup.iris.shared.util.locator.ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceLocatorException("Nessun Servizio Operatorservice Configurato Correttamente",e);
		}
	}


	public DTO getOperatorByCodiceFiscaleInternal(FrontEndContext fec, DTO dto) {

		DTO dtoReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "getOperatorByCodiceFiscaleInternal", "esecuzione OperatorserviceService", null);
			dtoReturn = operatorserviceService.getOperatorByCodiceFiscaleInternal(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "getOperatorByCodiceFiscaleInternal", "errore durante l'esecuzione OperatorserviceService", re);
			dtoReturn = ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "getOperatorByCodiceFiscaleInternal", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoReturn;
	}

	public DTO assignNewOperator(FrontEndContext fec, DTO dto) {

		DTO dtoReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "assignNewOpertor", "esecuzione OperatorserviceService", null);
			dtoReturn = operatorserviceService.assignNewOperator(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "assignNewOpertor", "errore durante l'esecuzione OperatorserviceService", re);
			dtoReturn = ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "assignNewOpertor", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoReturn;
	}

	public DTO deAssignNewOperator(FrontEndContext fec, DTO dto) {

		DTO dtoReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "deAssignNewOperator", "esecuzione OperatorserviceService", null);
			dtoReturn = operatorserviceService.deAssignNewOperator(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "deAssignNewOperator", "errore durante l'esecuzione OperatorserviceService", re);
			dtoReturn = ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "deAssignNewOperator", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoReturn;
	}

	public DTO checkOperatorByUsernameAndCorporate(FrontEndContext fec, DTO dto) {

		DTO dtoReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "checkOperatorByUsernameAndCorporate", "esecuzione OperatorserviceService", null);
			dtoReturn = operatorserviceService.checkOperatorByUsernameAndCorporate(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "checkOperatorByUsernameAndCorporate", "errore durante l'esecuzione OperatorserviceService", re);
			dtoReturn = ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "checkOperatorByUsernameAndCorporate", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoReturn;
	}

	public DTO getOperatorByCode(FrontEndContext fec, DTO dto) {

		DTO dtoReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "getOperatorByCode", "esecuzione OperatorserviceService", null);
			dtoReturn = operatorserviceService.getOperatorByCode(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "getOperatorByCode", "errore durante l'esecuzione OperatorserviceService", re);
			dtoReturn = ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "getOperatorByCode", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoReturn;
	}

	public DTOCollection deleteOperator(FrontEndContext fec, DTO dto) {

		DTOCollection dtoCollectionReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "deleteOperator", "esecuzione OperatorserviceService", null);
			dtoCollectionReturn = operatorserviceService.deleteOperator(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "deleteOperator", "errore durante l'esecuzione OperatorserviceService", re);
			dtoCollectionReturn = ManageFrontEndException.getBusinessDTOCollectionByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoCollectionReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "deleteOperator", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoCollectionReturn;
	}

	public DTO insertAndUpdateOperator(FrontEndContext fec, DTO dto) {

		DTO dtoReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "insertAndUpdateOperator", "esecuzione OperatorserviceService", null);
			dtoReturn = operatorserviceService.insertAndUpdateOperator(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "insertAndUpdateOperator", "errore durante l'esecuzione OperatorserviceService", re);
			dtoReturn = ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "insertAndUpdateOperator", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoReturn;
	}

	public DTO insertAndUpdateOperatorFunctions(FrontEndContext fec, DTO dto) {

		DTO dtoReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "insertAndUpdateOperatorFunctions", "esecuzione OperatorserviceService", null);
			dtoReturn = operatorserviceService.insertAndUpdateOperatorFunctions(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "insertAndUpdateOperatorFunctions", "errore durante l'esecuzione OperatorserviceService", re);
			dtoReturn = ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "insertAndUpdateOperatorFunctions", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoReturn;
	}

	public DTOCollection listFunctionsByCorporate(FrontEndContext fec, DTO dto) {

		DTOCollection dtoCollectionReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "listFunctionsByCorporate", "esecuzione OperatorserviceService", null);
			dtoCollectionReturn = operatorserviceService.listFunctionsByCorporate(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "listFunctionsByCorporate", "errore durante l'esecuzione OperatorserviceService", re);
			dtoCollectionReturn = ManageFrontEndException.getBusinessDTOCollectionByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoCollectionReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "listFunctionsByCorporate", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoCollectionReturn;
	}

	public DTOCollection listOperatorsByCriteria(FrontEndContext fec, DTO dto) {

		DTOCollection dtoCollectionReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "listOperatorsByCriteria", "esecuzione OperatorserviceService", null);
			dtoCollectionReturn = operatorserviceService.listOperatorsByCriteria(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "listOperatorsByCriteria", "errore durante l'esecuzione OperatorserviceService", re);
			dtoCollectionReturn = ManageFrontEndException.getBusinessDTOCollectionByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoCollectionReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "listOperatorsByCriteria", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoCollectionReturn;
	}

	public DTO changePwd(FrontEndContext fec, DTO dto) {

		DTO dtoReturn = null;

		try {

			Tracer.info(this.getClass().toString(), "changePwd", "esecuzione OperatorserviceService", null);
			dtoReturn = operatorserviceService.changePwd(fec, dto);

		} catch (Throwable re) {

			Tracer.error(this.getClass().toString(), "changePwd", "errore durante l'esecuzione OperatorserviceService", re);
			dtoReturn = ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);

		}

		DTOInfoList infoList = dtoReturn.getInfoList();

		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {

			Tracer.error(this.getClass().toString(), "changePwd", "errore durante l'esecuzione OperatorserviceService", null);

			throw new BusinessDelegateRuntimeException(infoList);
		}

		return dtoReturn;
	}

	public DTOCollection listOperatorsByCorporate(FrontEndContext fec, DTO dto) {

		DTOCollection dtoCollectionReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "listOperatorsByCorporate", "esecuzione OperatorserviceService", null);
			dtoCollectionReturn = operatorserviceService.listOperatorsByCorporate(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "listOperatorsByCorporate", "errore durante l'esecuzione OperatorserviceService", re);
			dtoCollectionReturn = ManageFrontEndException.getBusinessDTOCollectionByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoCollectionReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "listOperatorsByCorporate", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoCollectionReturn;
	}

	public DTO updateOperator(FrontEndContext fec, DTO dto) {

		DTO dtoReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "updateOperator", "esecuzione OperatorserviceService", null);
			dtoReturn = operatorserviceService.updateOperator(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "updateOperator", "errore durante l'esecuzione OperatorserviceService", re);
			dtoReturn = ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "updateOperator", "errore durante l'esecuzione OperatorserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoReturn;
	}

}
