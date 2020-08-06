package it.tasgroup.idp.billerservices.rest.result;

import java.util.ArrayList;
import java.util.List;


public class InformativaPagamentiResultList {
	
	private final static long serialVersionUID = 1L;

    private  List<InformativaPagamentiResult> informativaPagamenti;

    
    public List<InformativaPagamentiResult> getInformativaPagamenti() {
        if (informativaPagamenti == null) {
            informativaPagamenti = new ArrayList<InformativaPagamentiResult>();
        }
        return this.informativaPagamenti;
    }

}
