package it.nch.ebweb.generate.core;
import java.util.StringTokenizer;

/*
 * Created on 19-gen-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author ee10057
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreatorCostanst {


	public static String ROOT_FOLDER="../";
	public static int DEFAULT_RETRY=0;

	public static String FOLDER_SERVICE_MODEL_INTERFACE=ROOT_FOLDER+"services-business-services-interface/src/main/java/";
	public static String FOLDER_CLIENT_CONFIGURATION=ROOT_FOLDER+"services-client-configuration/src/main/resources/";
	public static String FOLDER_CONFIGURATION=ROOT_FOLDER+"services-server-configuration/src/main/resources/";
	
		
	public static String FOLDER_BUSINESS_MODEL=ROOT_FOLDER+"services-business-model/src/main/java/";
		
	public static String FOLDER_SERVICE_MODEL=ROOT_FOLDER+"services-business-services/src/main/java/";
	

	
	public static String FOLDER_FRW_INTERNAL_SERVICE_INTERFACES=ROOT_FOLDER+"services-internal-service-interface/src/main/java/";
	public static String FOLDER_FRW_INTERNAL_SERVICE=ROOT_FOLDER+"services-internal-service/src/main/java/";	
	public static String FOLDER_FRW_INTERNAL_EJB=FOLDER_FRW_INTERNAL_SERVICE;
	public static String FOLDER_FRW_INTERNAL_EJB_INTERFACE=FOLDER_FRW_INTERNAL_SERVICE_INTERFACES;//DAV
		
	public static String FOLDER_BUSINESS_DELEGATOR=ROOT_FOLDER+"services-business-delegator/src/main/java/";
	
	public static String FOLDER_PROPERTIES=ROOT_FOLDER+"services-configuration/src/main/config/";
		
	public static String FOLDER_BUSINESS_COMMON_GENERATED=ROOT_FOLDER+"services-business-common/src/generated-sources/";
	public static String FOLDER_BUSINESS_I_FORM_GENERATED=FOLDER_BUSINESS_COMMON_GENERATED;
	public static String FOLDER_ORM_CLIENT_GENERATED=FOLDER_BUSINESS_COMMON_GENERATED;
	
	
	public static String FOLDER_BUSINESS_C_FORM_GENERATED=ROOT_FOLDER+"services-business-form/src/main/java/";	
	public static String FOLDER_ORM_GENERATED=ROOT_FOLDER+"services-orm/src/main/java/";		
	
	public static String ROOT_PACKAGE="it.nch";
	public static String FWK_PACKAGE="it.nch.fwk.fo";
	
	public static String FWK_IS_PACKAGE="it.nch.is.fo";
	
	public static String INTERFACES_IS_PACKAGE=".interfaces";

	/*
	 * 
	 * framework
	 * 
	 * 
	 */	
	
	public static String PACKAGE_BUSINESS_MODEL=".business";//+CATEGORIA DEL SERVIZIO  ---Classi bean
	public static String PACKAGE_SERVICE_MODEL=".ejbs"; //+CATEGORIA DEL SERVIZIO      ---EJB
	
	//public static String PACKAGE_SERVICE_MODEL_INTERFACE=ROOT_PACKAGE+"service."+CATEGORIA+".interfaces"; //+CATEGORIA DEL SERVIZIO ---Interfacce    
	public static String PACKAGE_SERVICE_MODEL_INTERFACE=".service"; //+CATEGORIA DEL SERVIZIO ---Interfacce
	public static String PACKAGE_BUSINESS_DELEGATOR=".delegate"; //+CATEGORIA     //Delegator
		
	
	public static String DTO="DTO";
	public static String DTO_C="DTOCollection";
	public static String MAP="Map";
	
	
    public static String DTO_RETURN="dtoReturn";
	public static String DTO_C_RETURN="dtoCollectionReturn";
	public static String MAP_RETURN="mapReturn";
	public static String ENVELOPE_RETURN="envelopeReturn";
	/*
	 * 
	 * 
	 * 
	 * CATEGORIE:
	 * 	-cbi
	 *	-cbi2	
	 *	-retail	
	 *	-custom
	 *	-authorization
	 *
	 *
	 */
	
	//public static String PROTOTYPE = ".prototype";
	public static String CORE=".core";
	public static String CBI=".cbi";
	public static String CBI2=".cbi2";
	public static String RETAIL=".retail";
	public static String CUSTOM=".custom";
	public static String AUTH=".authorization";
	public static String HELPDESK=".helpdesk";
	public static String STANTARD_TYPE="STANDARD";
	
	public static String resolveDTOType(int type){
		switch(type){
		case 1:
			return DTO;		
		case 2:
			return DTO_C;
		case 3:
			return MAP;	
		}
		return null;
	}
	
	public static String getPathSrc(String _package){
		
		StringTokenizer st = new StringTokenizer(_package,".");
		String p;
		String path="";	
		
		path=st.nextToken();
		
		while (st.hasMoreTokens())		
			path+="/"+st.nextToken();			
		return path;
	}
	
	public static String getCategoryName(int i){
		
		switch(i){
		case 0:
			return CORE;		
		case 1:
			return CBI;			
		case 2:
			return CBI2;	
		case 3:
			return RETAIL;		
		case 4:
			return CUSTOM;		
		case 5:
			return AUTH;	
		case 6:
			return HELPDESK;	
		}
		return null;
	}

	public static boolean isDTOInfoList(int type){
		switch(type){
		case 1:
			return true;		
		case 2:
			return true;			
		case 3:
			return false;				
		case 4:
			return false;				
		}
		return false;
	}
	
	public static String resolveDTOTypeInstance(int type){
		switch(type){
		case 1:
			return DTO_RETURN;		
		case 2:
			return DTO_C_RETURN;			
		case 3:
			return MAP_RETURN;				
		case 4:
			return ENVELOPE_RETURN;				
		}
		return null;
	}

}
