package it.tasgroup.iris.dto.ddp;

public abstract class DettaglioDDPBancarioDTO extends DettaglioDDPDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String nomeOrdinante;
	
	private String cognomeOrdinante;
	
	
	
	public String getNomeOrdinante() {
		return nomeOrdinante;
	}
	
	public void setNomeOrdinante(String nomeOrdinante) {
		this.nomeOrdinante = nomeOrdinante;
	}
	
	public String getCognomeOrdinante() {
		return cognomeOrdinante;
	}
	
	public void setCognomeOrdinante(String cognomeOrdinante) {
		this.cognomeOrdinante = cognomeOrdinante;
	}
	
	public String toString() {
		   StringBuffer sb = new StringBuffer();
		   sb.append("\n"+this.getClass()+"\n");
	       sb.append("[");
	       sb.append(super.toString());
	       sb.append(", nomeOrdinante="+this.getNomeOrdinante());
	       sb.append(", cognomeOrdinante="+this.getCognomeOrdinante());
	       sb.append(", beneficiario="+this.getBeneficiario());
	       sb.append(", coordinateBancarie="+this.getCoordinateBancarie());
	       sb.append("]\n");
	       return sb.toString();
	    }

}
