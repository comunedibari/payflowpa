package it.nch.fwk.fo.core.exception;

import it.nch.fwk.fo.cross.exception.BusinessImplRuntimeException;
import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.DTOInfo;
import it.nch.fwk.fo.dto.DTOInfoInterface;
import it.nch.fwk.fo.dto.DTOInfoList;
import it.nch.fwk.fo.dto.DTOInfoSeverity;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;

public abstract class AbstractManageBackEndException {

	public void processDAOException(String code) throws DAORuntimeException{
		processDAOException(code,DTOInfoInterface.SEVERITY_GENERIC);
	}
	
	public void processDAOException(Throwable ex,String code) throws DAORuntimeException{
		processDAOException(ex,code,DTOInfoInterface.SEVERITY_GENERIC);
	}
	
	public void processDAOException(String code, int severity) throws DAORuntimeException{
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");
		if( !DTOInfoSeverity.isValidSeverity(severity))
			throw new IllegalArgumentException("Severity param is not correct");
		
		String message = retrieveInfoByCode(code);
		DTOInfo dtoInfo=new DTOInfo(code,message,severity);
		throw new DAORuntimeException(new DTOInfoList(dtoInfo));
	}
	
	public void processDAOException(Throwable ex,String code,int severity) throws DAORuntimeException{
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");
		if( !DTOInfoSeverity.isValidSeverity(severity))
			throw new IllegalArgumentException("Severity param is not correct");

		if (ex instanceof DAORuntimeException) {
			throw new DAORuntimeException(ex,((BusinessImplRuntimeException)ex).getDtoInfoList());
		}else{
			String message = retrieveInfoByCode(code);
			DTOInfo dtoInfo=new DTOInfo(code,message,severity);
			throw new DAORuntimeException(ex,new DTOInfoList(dtoInfo));
		}	
	}
	
	public void processBusinessException(String code) throws BusinessImplRuntimeException{
		processBusinessException(code,DTOInfoInterface.SEVERITY_GENERIC);
	}
	
	public void processBusinessException(Throwable ex,String code) throws BusinessImplRuntimeException{
		processBusinessException(ex,code,DTOInfoInterface.SEVERITY_GENERIC);
	}

	public void processBusinessException(String code,int severity) throws BusinessImplRuntimeException{
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");
		if( !DTOInfoSeverity.isValidSeverity(severity))
			throw new IllegalArgumentException("Severity param is not correct");

		String message = retrieveInfoByCode(code);
		DTOInfo dtoInfo=new DTOInfo(code,message,severity);
		throw new BusinessImplRuntimeException(new DTOInfoList(dtoInfo));
	}
	
	public void processBusinessException(Throwable ex,String code,int severity) throws BusinessImplRuntimeException{
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");
		if( !DTOInfoSeverity.isValidSeverity(severity))
			throw new IllegalArgumentException("Severity param is not correct");

		if (ex instanceof BusinessImplRuntimeException) {
			throw new BusinessImplRuntimeException(ex,((BusinessImplRuntimeException)ex).getDtoInfoList());
		}else{
			String message = retrieveInfoByCode(code);
			DTOInfo dtoInfo=new DTOInfo(code,message,severity);
			throw new BusinessImplRuntimeException(ex,new DTOInfoList(dtoInfo));
		}	
	}

	public DTO getBusinessDTOByError(String code,int severity) throws BusinessImplRuntimeException{
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");
		if( !DTOInfoSeverity.isValidSeverity(severity))
			throw new IllegalArgumentException("Severity param is not correct");

		DTO dtoResult= new DTOImpl();
		DTOInfo dtoi=new DTOInfo(code,retrieveInfoByCode(code),severity);
		dtoResult.setInfoList(new DTOInfoList(dtoi));
		return dtoResult;
	}

	public DTOCollection getBusinessDTOCollectionByError(String code,int severity) throws BusinessImplRuntimeException{
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");
		if( !DTOInfoSeverity.isValidSeverity(severity))
			throw new IllegalArgumentException("Severity param is not correct");

		DTOCollection dtoResult= new DTOCollectionImpl();
		DTOInfo dtoInfo=new DTOInfo(code,retrieveInfoByCode(code),severity);
		dtoResult.setInfoList(new DTOInfoList(dtoInfo));
		return dtoResult;
	}

	public DTOInfo getDTOInfoByError(String code,int severity) throws BusinessImplRuntimeException{
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");
		if( !DTOInfoSeverity.isValidSeverity(severity))
			throw new IllegalArgumentException("Severity param is not correct");

		DTOInfo dtoi=new DTOInfo(code,retrieveInfoByCode(code),severity);
		return dtoi;
	}

	public abstract String retrieveInfoByCode(String code);	
}
