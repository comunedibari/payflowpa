package it.tasgroup.idp.util;

public enum GenerationTypeEnum {
	fixed, 				//File formatted without CR/LF with every "line" padded to 120
	variable,			//File formatted with CR/LF with every line trimmed (length<=120) 
	variable_filled,     //File formatted with CR/LF with every line padded to 120
	variable_notrailing_space //File formatted with CR/LF every line. No spaces added at beginning
}
