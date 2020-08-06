package it.tasgroup.idp.cart.core.quadratura;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Res implements Serializable{
	
	private static final long serialVersionUID = 4601647394984864541L;
	private Date data;
	private Long id;
	private Number valore;
	
	public Res() {
		super();
	}
	
	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString(){
		StringBuffer bf = new StringBuffer(super.toString());
		bf.append("\n");
		if(this.data!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			bf.append("\tData: ["+sdf.format(this.data)+"]");
		}
		else{
			bf.append("\tData: [undefined]");
		}
		return bf.toString();
	}

	public Number getValore() {
		return valore;
	}

	public void setValore(Number valore) {
		this.valore = valore;
	}
	
	
}