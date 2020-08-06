package it.tasgroup.idp.billerservices.rest.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetStatoResultList implements Serializable {
	
	 private final static long serialVersionUID = 1L;
	 
	 private List<GetStatoResult> statoTrasmissioni;
	 
	  public List<GetStatoResult> getStatoTrasmissioni() {
        if (statoTrasmissioni == null) {
            statoTrasmissioni = new ArrayList<GetStatoResult>();
        }
        return this.statoTrasmissioni;
	  }
	 
}



