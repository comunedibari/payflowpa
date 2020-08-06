package it.tasgroup.dse.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.Assert;


/**
 * @author agostino
 */
public class DataInputByte implements DataInput{
	
	private static final long serialVersionUID = -4003195043268114981L;
	private byte[] xmlData= null;
  
	private DataInputByte(){
	}
	
	/**
	 * @param xmlData
	 */
	public DataInputByte(byte[] xmlData) {
		super();
		this.xmlData = xmlData;
	}
	
	/**
	 * @return Restituisce il valore dataRetrievingMode.
	 */
//	public int getDataRetrievingMode() {
//		return DRM_MEMORY;
//	}
	
	/**
	 * @return Restituisce il valore xmlData.
	 */
	public byte[] getXmlData() {
		return xmlData;
	}
	
	/* (non Javadoc)
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException {
		DataInputByte out = new DataInputByte();
		
		if(xmlData!=null)
			out.xmlData = (byte[])this.xmlData.clone();
		
		return out;
	}
	
	public InputStream createStream() {
		return new ByteArrayInputStream(getXmlData());
	}
	
	/* (non Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("DataInputByte[");
//		buf.append(" dataRetrievingMode: " + DRM_MEMORY);
		buf.append(", xmlData: " + xmlData);
		buf.append("]");
		return buf.toString();
	}
	
	public static void main(String[] args) {
		byte[] bts = new byte[6];
		bts[1] = 12;
		bts[3] = 22;
		bts[4] = 32;
		bts[5] = 42;
		
		byte[] cl = (byte[]) bts.clone();
		bts[5] = 77;
		Assert.assertEquals(6, cl.length);
		Assert.assertEquals(42, cl[5]);
		System.out.println("ojk");
	}
}
