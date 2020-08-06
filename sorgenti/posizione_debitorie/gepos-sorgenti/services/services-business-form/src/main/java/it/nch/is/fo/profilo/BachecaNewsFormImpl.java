package it.nch.is.fo.profilo;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.backoffice.bacheca.BachecaNewsVO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public class BachecaNewsFormImpl extends BaseForm implements BachecaNewsForm {
	
	private String id;
	private String titolo;
	private String priorita = "0";
	private String messaggio;
    private FormFile imgFileExt;
    private FormFile imgFileInt;
    
    
    private String imgExtFileName;
    private String imgIntFileName;
    private byte[] imgExtFileContent;
    private byte[] imgIntFileContent;
    
	private Date decorrenza;
	private Date scadenza;
	
	private String isNew = "true";
	
	private String dataScadenzaGG; 
	private String dataScadenzaMM;
	private String dataScadenzaYY;
	private String dataDecorrenzaGG; 
	private String dataDecorrenzaMM;
	private String dataDecorrenzaYY;
	
	private String resetImgExt;
	private String resetImgInt;
	
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	
	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;

	private static final long serialVersionUID = -2327108327611588483L;
	
	public BachecaNewsFormImpl() {
		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf = bfr.getFactory();
	}

	public FormFile getImgFileExt() {
		return imgFileExt;
	}

	public void setImgFileExt(FormFile imgFileExt) {
		this.imgFileExt = imgFileExt;
	}

	public FormFile getImgFileInt() {
		return imgFileInt;
	}

	public void setImgFileInt(FormFile imgFileInt) {
		this.imgFileInt = imgFileInt;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataScadenzaGG() {
		return dataScadenzaGG;
	}

	public void setDataScadenzaGG(String dataScadenzaGG) {
		this.dataScadenzaGG = dataScadenzaGG;
	}

	public String getDataScadenzaMM() {
		return dataScadenzaMM;
	}

	public String getDataScadenzaYY() {
		return dataScadenzaYY;
	}

	public String getDataDecorrenzaGG() {
		return dataDecorrenzaGG;
	}

	public String getDataDecorrenzaMM() {
		return dataDecorrenzaMM;
	}

	public String getDataDecorrenzaYY() {
		return dataDecorrenzaYY;
	}

	public void setDataScadenzaMM(String dataScadenzaMM) {
		this.dataScadenzaMM = dataScadenzaMM;
	}

	public void setDataScadenzaYY(String dataScadenzaYY) {
		this.dataScadenzaYY = dataScadenzaYY;
	}

	public void setDataDecorrenzaGG(String dataDecorrenzaGG) {
		this.dataDecorrenzaGG = dataDecorrenzaGG;
	}

	public void setDataDecorrenzaMM(String dataDecorrenzaMM) {
		this.dataDecorrenzaMM = dataDecorrenzaMM;
	}

	public void setDataDecorrenzaYY(String dataDecorrenzaYY) {
		this.dataDecorrenzaYY = dataDecorrenzaYY;
	}

	public String getPriorita() {
		return priorita;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public Date getDecorrenza() {
		return decorrenza;
	}
	public Date getScadenza() {
		return scadenza;
	}
	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public void setDecorrenza(Date decorrenza) throws ParseException {
		this.decorrenza = decorrenza;
		if (decorrenza != null) {
			String[] dateStrs = this.toWebDate(decorrenza);
			this.setDataDecorrenzaGG(dateStrs[0]);
			this.setDataDecorrenzaMM(dateStrs[0]);
			this.setDataDecorrenzaYY(dateStrs[0]);
		}
	}
	public void setScadenza(Date scadenza) throws ParseException {
		this.scadenza = scadenza;
		if (scadenza != null) {
			String[] dateStrs = this.toWebDate(scadenza);
			this.setDataScadenzaGG(dateStrs[0]);
			this.setDataScadenzaMM(dateStrs[0]);
			this.setDataScadenzaYY(dateStrs[0]);
		}
	}

	
	public String getImgExtFileName() {
		return imgExtFileName;
	}
	
	public void setImgExtFileName(String imgExtFileName) {
		this.imgExtFileName = imgExtFileName;
	}
	public String getImgIntFileName() {
		return imgIntFileName;
	}
	
	public void setImgIntFileName(String imgIntFileName) {
		this.imgIntFileName = imgIntFileName;
	}
	public String getResetImgInt() {
		return resetImgInt;
	}

	public void setResetImgInt(String resetImgInt) {
		this.resetImgInt = resetImgInt;
	}

	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public String getResetImgExt() {
		return resetImgExt;
	}

	public void setResetImgExt(String resetImgExt) {
		this.resetImgExt = resetImgExt;
	}

	@Override
	public CommonBusinessObject copy() {
		BachecaNewsFormImpl _form = this;
		BachecaNewsFormImpl _pojo = (BachecaNewsFormImpl) this.nativePojo;
		if (_pojo == null) {

			if (Tracer.isDebugEnabled(this.getClass().getName())) {
				Tracer.debug(this.getClass().getName(), "", "", null);
				Tracer.debug(this.getClass().getName(), "copy()",
						"---------------------------------------------------------------------", null);
				Tracer.debug(this.getClass().getName(), "copy()",
						"Attenzione nativeObject dentro FORM vuoto provvedo ad istanziare POJO", null);
				Tracer.debug(this.getClass().getName(), "copy()",
						"---------------------------------------------------------------------", null);
				Tracer.debug(this.getClass().getName(), "", "", null);
			}
			bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
			bfr = bfl.useBeanFactory("it.nch.orm");
			bf = bfr.getFactory();
			_pojo = (BachecaNewsFormImpl) bf.getBean("BachecaNews");
		}
		_pojo.setId(_form.getId());
		_pojo.setTitolo(_form.getTitolo());
		_pojo.setPriorita(_form.getPriorita());
		_pojo.setMessaggio(_form.getMessaggio());
		_pojo.setImgFileExt(_form.getImgFileExt());
		_pojo.setImgFileInt(_form.getImgFileInt());
		_pojo.setImgIntFileName(_form.getImgIntFileName());
		_pojo.setImgExtFileName(_form.getImgExtFileName());
		try {
			_pojo.setDecorrenza(_form.getDecorrenza());
			_pojo.setScadenza(_form.getScadenza());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return _pojo;
	}

	@Override
	public void show() {
		throw new RuntimeException("Not implemented!");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public DTO incapsulateBO() {
		return new DTOImpl(this);
	}

	@Override
	public void setNativePojo(Object nativePojo) {
		this.nativePojo = nativePojo;
	}
	
	public BachecaNewsVO toBachecaNewsVO() throws FileNotFoundException, IOException {
		BachecaNewsVO bn = new BachecaNewsVO();
		if(!this.getId().isEmpty())
			bn.setId(new Long(this.getId()));
		bn.setTitolo(titolo);
		bn.setPriorita(Long.decode(priorita));
		bn.setMessaggio(messaggio);
		if (resetImgExt != null && resetImgExt.equals("true")) {
			bn.setImgExtContent("".getBytes());
			bn.setImgExtFileName("");
		} else {
			if (imgFileExt != null) {
				bn.setImgExtContent(imgFileExt.getFileData());
				bn.setImgExtFileName(imgFileExt.getFileName());
			}
		}
		if (resetImgInt != null && resetImgInt.equals("true")) {
			bn.setImgIntContent("".getBytes());
			bn.setImgIntFileName("");
		} else {
			if (imgFileInt != null) {
				bn.setImgIntContent(imgFileInt.getFileData());
				bn.setImgIntFileName(imgFileInt.getFileName());
			}
		}
		if(decorrenza == null && dataDecorrenzaGG != null) {
			decorrenza = fromWebDate(dataDecorrenzaYY, dataDecorrenzaMM, dataDecorrenzaGG);
		}
		bn.setDecorrenza(decorrenza);
		if(scadenza == null && dataScadenzaGG != null) {
			scadenza = fromWebDate(dataScadenzaYY, dataScadenzaMM, dataScadenzaGG);
		}
		bn.setScadenza(scadenza);
		return bn;
	}
	
	public static BachecaNewsFormImpl fromBachecaNewsVO(BachecaNewsVO bnVO) throws ParseException {
		BachecaNewsFormImpl result = new BachecaNewsFormImpl();
		result.setId(String.valueOf(bnVO.getId()));
		result.setTitolo(bnVO.getTitolo());
		result.setPriorita(String.valueOf(bnVO.getPriorita()));
		result.setMessaggio(bnVO.getMessaggio());
		result.setDecorrenza(bnVO.getDecorrenza());
		result.setScadenza(bnVO.getScadenza());
		result.setImgExtFileName(bnVO.getImgExtFileName());
		result.setImgIntFileName(bnVO.getImgIntFileName());
		result.setImgExtFileContent(bnVO.getImgExtContent());
		result.setImgIntFileContent(bnVO.getImgIntContent());
		return result;
	}
	
	private String[] toWebDate(Date date) throws ParseException {
		String[] res = new String[3];
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    res[0] = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
	    res[1] = String.valueOf(cal.get(Calendar.MONTH));
	    res[2] = String.valueOf(cal.get(Calendar.YEAR));
        return res;
	}
	
	private Date fromWebDate(String year, String month, String day) {
		Calendar c = Calendar.getInstance();
		c.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), 0, 0); 
		return c.getTime();
	}
	
	
	public String getEncodedExtContent() {
		String res = "";
		if (imgExtFileContent != null) {
			res = DatatypeConverter.printBase64Binary(imgExtFileContent);
		}
		return res;
	}
	
	public String getEncodedIntContent() {
		String res = "";
		if (imgIntFileContent != null) {
			res = DatatypeConverter.printBase64Binary(imgIntFileContent);
		}
		return res;
	}

	public byte[] getImgExtFileContent() {
		return imgExtFileContent;
	}

	public void setImgExtFileContent(byte[] imgExtFileContent) {
		this.imgExtFileContent = imgExtFileContent;
	}

	public byte[] getImgIntFileContent() {
		return imgIntFileContent;
	}

	public void setImgIntFileContent(byte[] imgIntFileContent) {
		this.imgIntFileContent = imgIntFileContent;
	}
	
	
	public String getDecorrenzaVideo() {
		return df.format(decorrenza);
	}

	public String getScadenzaVideo() {
		return df.format(scadenza);
	}


}
