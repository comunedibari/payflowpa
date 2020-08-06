/*
 * Creato il 25-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.das;

import it.nch.fwk.fo.cross.AbstractDAO;
/**
 * @author 
 * @version 0.1 
 * 
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public abstract class DAONetwork extends AbstractDAO implements DataProvider {
/*
	private ITransportClient proxyClient;

	public DAONetwork() {
		
		proxyClient = TransportFactory.getProxyInstance();
		
	}
	
	public DAONetwork(ITransportClient proxyClient) {
		
		this.proxyClient=proxyClient;
		
	}
	
	public int init() {
		throw new UnsupportedOperationException();
	}
	
	
	public int destroy() {
		throw new UnsupportedOperationException();
	}
*/
	/**
	 * @return Restituisce il valore proxyClient.
	 */
	/*
	public ITransportClient getProxyClient() {
		return proxyClient;
	}
	
	public String assignUUID(){
		return getProxyClient().assignUUID();	
	}
	
*/
	
	/**
	 * @param proxyClient Il valore proxyClient da impostare.
	 */
	/*
	public void setProxyClient(ITransportClient proxyClient) {
		this.proxyClient = proxyClient;
	}
	
	
	
	
	public IOLI2Envelope createEnvelope(DTO dto,boolean assignUUID,String code) {
		
		IOLI2Envelope envelope = new IOLI2Envelope();
	
		envelope.setData(dto.getPojo());
		envelope.setExtraData(dto.getCriteria());
		envelope.setOperationInfo(dto.getDTOInfo());
		envelope.setOperationCode(code);
		
		if (assignUUID)
			envelope.setUUID(getProxyClient().assignUUID());				
		
		return envelope;	
	}
	
	
	public IOLI2Envelope createEnvelope(DTOCollection dtoCollection,boolean assignUUID,String code) {
		
		IOLI2Envelope envelope = new IOLI2Envelope();	
		envelope.setData(dtoCollection.getCollection());
		//envelope.ExtraData=dtoCollection.getCriteria();
		envelope.setOperationInfo(dtoCollection.getDTOInfo());
		envelope.setOperationCode(code);
	
		if (assignUUID)
				envelope.setUUID(getProxyClient().assignUUID());
			
		return envelope;	
	}
	
	
	public DTO extractDTO(IOLI2Envelope envelope){
		
		DTO dto = new DTOImpl();
		dto.setPojo((Pojo)envelope.getData());
		dto.setInfoList((DTOInfoList)envelope.getOperationInfo());
		dto.setCriteria((DTOCriteria)envelope.getExtraData());
		return dto;
	}			
	
*/
}