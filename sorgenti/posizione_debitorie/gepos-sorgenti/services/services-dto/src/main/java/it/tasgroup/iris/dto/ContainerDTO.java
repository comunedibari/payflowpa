package it.tasgroup.iris.dto;

import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO di framework usato per aggregare gli oggetti da trasportare da FE a BE e viceversa
 *  
 * @author pazzik
 *
 */
public class ContainerDTO implements Serializable {
	
	private PagingCriteria pagingCriteria = new PagingCriteria();
	
	private PagingData pagingData = new PagingData();
	
	private List inputDTOList = new ArrayList();
	
	private List outputDTOList = new ArrayList();
	
	private BigDecimal sum;

	
	public ContainerDTO() {}
	

	public PagingCriteria getPagingCriteria()
	{
		return pagingCriteria;
	}

	public void setPagingCriteria(PagingCriteria pagingCriteria)
	{
		this.pagingCriteria = pagingCriteria;
	}

	public PagingData getPagingData()
	{
		return pagingData;
	}

	public void setPagingData(PagingData pagingData)
	{
		this.pagingData = pagingData;
	}

	public List getOutputDTOList() {
		return outputDTOList;
	}

	public void setOutputDTOList(List outputDTOList) {
		this.outputDTOList = outputDTOList;
	}

	public List getInputDTOList() {
		return inputDTOList;
	}

	public void setInputDTOList(List inputDTOList) {
		this.inputDTOList = inputDTOList;
	}
	
	public void addInputDTO(Object dto){
		this.inputDTOList.add(dto);
	}
	
	public void addOutputDTO(Object dto){
		this.outputDTOList.add(dto);
	}
	
	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public Object getInputDTO(){
		
		if (this.inputDTOList.size()!=1)
			throw new IllegalStateException("Impossibile invocare questo metodo quando inputDTOList.size()!=1");
		
		return this.inputDTOList.get(0);
	}
	
	public Object getOutputDTO(){
		if (this.outputDTOList.size()!=1)
			throw new IllegalStateException("Impossibile invocare questo metodo quando outputDTOList.size()!=1");
		
		return this.outputDTOList.get(0);
	}
	
	public void setInputDTO(Object dto){
		
		if (this.inputDTOList.size()>0)
			throw new IllegalStateException("Impossibile invocare questo metodo quando inputDTOList.size()>0");
		
		this.inputDTOList.add(dto);
	}
	
	public void addAllInputDTO(List dtoCollection){
		
		if (this.inputDTOList.size()>0)
			throw new IllegalStateException("Impossibile invocare questo metodo quando inputDTOList.size()>0");
		
		this.inputDTOList.addAll(dtoCollection);
	}
	
	public void setOutputDTO(Object dto){
		if (this.outputDTOList.size()>0)
			throw new IllegalStateException("Impossibile invocare questo metodo quando outputDTOList.size()>0");
		
		this.outputDTOList.add(dto);
	}
	
	public void addAllOutputDTO(List dtoCollection){
		if (this.outputDTOList.size()>0)
			throw new IllegalStateException("Impossibile invocare questo metodo quando outputDTOList.size()>0");
		
		this.outputDTOList.addAll(dtoCollection);
	}
	
	public void disablePaging(){
		
		this.pagingCriteria.setEnablePaging(false);
		
		this.pagingCriteria.setRecordPosition(-1);
		
		this.pagingCriteria.setResultsPerPage(-1); 
		
		
	}

	public String toString(){
		StringBuffer descrizione = new StringBuffer("****ContainerDTO****\n");
		descrizione.append("PagingCriteria: ").append(pagingCriteria).append("\n");
		descrizione.append("PagingData: ").append(pagingData).append("\n");
		descrizione.append("InputDTOList: ").append(inputDTOList).append("\n");
		descrizione.append("OutputDTOList: ").append(outputDTOList).append("\n");
		descrizione.append("***************\n");
		return descrizione.toString();

	}
	
	
}


