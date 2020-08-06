package it.nch.ebweb.generate.core;

import it.nch.ebweb.generate.backend.service.Azione;

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
public class Syntax {
	
	public static String packageDefine(String pack){
		return "package "+pack+";";
	}
	
	public static String interfaceDefine(String _class,String suffix,String _extends){
		if (suffix==null)
			suffix="";
		String interfaceName="public interface "+firstUpperCase(_class)+suffix;
		if (_extends!=null)
			interfaceName+=" extends "+_extends;
				
		interfaceName+="{";
		return interfaceName;	
	}
	
	public static String methodLocalInterface(Azione azione){
		String method = "public "+CreatorCostanst.resolveDTOType(azione.getReturnType())+" "+azione.getNome()+"(FrontEndContext fec,"+CreatorCostanst.resolveDTOType(azione.getParamType())+" dto);";
		return method;
	}
	
	public static String methodInterface(Azione azione){
		
		String method = "public "+CreatorCostanst.resolveDTOType(azione.getReturnType())+" "+azione.getNome()+"(FrontEndContext fec,"+CreatorCostanst.resolveDTOType(azione.getParamType())+" dto) throws RemoteException;";
		return method;
	}
	
	public static String classDefine(String _class,String suffix,String _extends,String[] _implements){
		return classDefine(_class, suffix, _extends, _implements, false);
	}
	
	public static String classDefine(String _class,String suffix,String _extends,String[] _implements, boolean isAbstract){
		
		String abstr = "";
		if (isAbstract) {
			abstr = "abstract "; //lasciato lo spazio apposta
		}
		
		if (suffix==null)
			suffix="";
		//String className="public " + abstr + "class "+firstUpperCase(_class)+suffix;
		String className="public " + abstr + "class "+ getClassName(_class, suffix);
		if (_extends!=null)
			className+=" extends "+_extends;
		if (_implements!=null){
			className+=" implements";
			for (int i=0;i<_implements.length;i++){
				if (i>0)
				 className+=", "+_implements[i];
				else
				 className+=" "+_implements[i];
			}			
		}		
		className+=" {";
		return className;
	}
	
	public static String getClassName(String _class, String suffix) {
		return firstUpperCase(_class) + suffix;
	}

	public static String costractorDefine(String _class,String suffix, String[] args, String[] _throws){
		// boa costrActor?? 'costructor' (più sotto) va un po' meglio, certo ci si poteva sforzare e scrivere 'constructor'
		if (suffix==null)
			suffix="";
		
		String costructor="public "+firstUpperCase(_class)+suffix+"(";
		
		if (args!=null){
			
			for(int i=0;i<args.length;i++){
				if (i>0)
					costructor+=", "+args[i];
				else
					costructor+=args[i];
			}
		}
	    if (_throws!=null){
	    	
	    	for(int j=0;j<_throws.length;j++){
	    		if (j>0)
					costructor+=", "+_throws[j];
				else
					costructor+=") throws "+_throws[j];
	    	}
	    	costructor+="{";
	    }else
	    	costructor+="){";
		
		return costructor;
	}
	
	public static String methodDefine_abstract(Azione azione){
		String method = "public abstract " + CreatorCostanst.resolveDTOType(azione.getReturnType())+" "+azione.getNome()+"(FrontEndContext fec,"+CreatorCostanst.resolveDTOType(azione.getParamType())+" dto);";
		return method;
	}
	
	public static String methodDefine(Azione azione){
		String method = "public "+CreatorCostanst.resolveDTOType(azione.getReturnType())+" "+azione.getNome()+"(FrontEndContext fec,"+CreatorCostanst.resolveDTOType(azione.getParamType())+" dto) {";
		method+="\n   // TODO Implementami!";
		method+="\n   // Questo scheletro di metodo e' fornito solo per comodita', l'implementazione deve essere fornita";
		method+="\n   return null;";
		method+="\n  }";
		return method;
	}
	
	public static String firstUpperCase(String _class){
		String _tmp = _class.toLowerCase();
		String r = _tmp.substring(1);
		String f = (_tmp.substring(0,1)).toUpperCase();		
		return f+r;
	}
	
	public static String onlyFirstUpperCase(String _class){
		//String _tmp = _class.toLowerCase();
		String r = _class.substring(1);
		String f = (_class.substring(0,1)).toUpperCase();		
		return f+r;
	}

}
