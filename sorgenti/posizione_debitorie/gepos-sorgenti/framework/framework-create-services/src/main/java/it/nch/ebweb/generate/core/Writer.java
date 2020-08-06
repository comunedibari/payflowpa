package it.nch.ebweb.generate.core;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

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
public class Writer {
	

    int indent = 0;
    boolean outputShellEnable = false;    
    PrintStream outputStreamShell;
    PrintStream outputStream;

    
    
        
	public Writer(PrintStream psShell,PrintStream ps){
		
		outputStreamShell = psShell;
		outputStream = ps;
	}
	
	
	 public void basePrint(String str){
        try{
            outputStream.write(str.getBytes("UTF-8"));
        }
        catch(UnsupportedEncodingException uex){
            outputStream.print(str);
        } 
        catch(IOException uex){
            outputStream.print(str);
        }
    }
	
    //
    // User-generated visitor methods below
    //
    public void println(String s) {

        for (int i = 0; i < indent; i++) {
            basePrint(" ");
            if (outputShellEnable)
                outputStreamShell.print(" ");

        }
        basePrint(s+"\n");
        if (outputShellEnable)
            outputStreamShell.println(s);

    }

    public void printIntend(String s) {

        for (int i = 0; i < indent; i++) {
            basePrint(" ");
            if (outputShellEnable)
                outputStreamShell.print(" ");

        }

        basePrint(s);
        if (outputShellEnable)
            outputStreamShell.print(s);

    }
    
    public void printRightIntend(String s, int k) {

    	indent = indent+k;
    	
        for (int i = 0; i < indent; i++) {
            basePrint(" ");
            if (outputShellEnable)
                outputStreamShell.print(" ");

        }

        basePrint(s);
        if (outputShellEnable)
            outputStreamShell.print(s);

    }
    
    public void printLeftIntend(String s,int k) {

    	indent = indent-k;
    	
        for (int i = 0; i < indent; i++) {
            basePrint(" ");
            if (outputShellEnable)
                outputStreamShell.print(" ");

        }

        basePrint(s);
        if (outputShellEnable)
            outputStreamShell.print(s);

    }
       
    public void resetIndent(){
    	indent=0;
    }
    
    public void addIndent(){
    	indent++;
    }
    
    public void subtractIndent(){
    	indent--;
    }
    
    public void print(String s) {

        basePrint(s);
        if (outputShellEnable)
            outputStreamShell.print(s);

    }

}
