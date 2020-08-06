package it.tasgroup.idp.bean;

public enum StatiPagamentiIris {
	/*
	 * Stati FLUD
	 */
   ESEGUITO, 
   ESEGUITO_SBF,
   IN_CORSO, 
   IN_ERRORE, 
   ANNULLATO, 
   ANNULLATO_DA_OPERATORE, 
   NON_ESEGUITO, 
   //DISPOSTO,
   STORNATO,
   IN_CARRELLO;
   /*
    * stati PAGA
    */
   public String getPagaMapping() {
	   String mapping = null;
	   switch(this) {
	       case ESEGUITO:
	    	   mapping = "ES";
	    	   break;
	       case ESEGUITO_SBF:
	    	   mapping = "EF";
	    	   break;
	       case IN_CORSO:
	    	   mapping = "IC";
	    	   break;
	       case IN_ERRORE:
	    	   mapping = "IE";
	    	   break;
	       case ANNULLATO:
	    	   mapping = "AN";
	    	   break;
	       case NON_ESEGUITO:
	    	   mapping = "NE";
	    	   break;
	       /*case DISPOSTO:
	    	   mapping = "DI";
	    	   break;*/
	       case STORNATO:
	    	   mapping = "ST";
	    	   break;
	       case ANNULLATO_DA_OPERATORE:
	    	   mapping = "AO";
	    	   break;	    	   
	       case IN_CARRELLO:
	    	   mapping = "I";
	   }
	   
	   return mapping;
   }
   
   public String getFludMapping() {
	   String mapping = null;
	   switch(this) {
	       case ESEGUITO:
	    	   mapping = ESEGUITO.name();
	    	   break;
	       case ESEGUITO_SBF:
	    	   mapping = "ESEGUITO SBF";
	    	   break;
	       case IN_CORSO:
	    	   mapping = "IN CORSO";
	    	   break;
	       case IN_ERRORE:
	    	   mapping = "IN ERRORE";
	    	   break;
	       case ANNULLATO:
	    	   mapping = ANNULLATO.name();
	    	   break;
	       case NON_ESEGUITO:
	    	   mapping = "NON ESEGUITO";
	    	   break;
	       /*case DISPOSTO:
	    	   mapping = "DISPOSTO";
	    	   break;*/
	       case STORNATO:
	    	   mapping = "STORNATO";
	    	   break;
	       case ANNULLATO_DA_OPERATORE:
	    	   mapping = "ANNULLATO OPE";
	    	   break;
	       case IN_CARRELLO:
	    	   mapping = "IN CARRELLO";
	   }
	   
	   return mapping;
   }
   
   public static StatiPagamentiIris getStatiPagamentiIrisFromFlud(String statoFlud) {
	   for (StatiPagamentiIris singleState : StatiPagamentiIris.values()) {
		   if(singleState.getFludMapping().equals(statoFlud)) {
			   return singleState;
		   }
	   }
	   return null;
   }

   public static StatiPagamentiIris getStatiPagamentiIrisFromPaga(String statoPaga) {
	   for (StatiPagamentiIris singleState : StatiPagamentiIris.values()) {
		   if(singleState.getPagaMapping().equals(statoPaga)) {
			   return singleState;
		   }
	   }
	   return null;
   }

}
