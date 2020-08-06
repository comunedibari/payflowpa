package it.tasgroup.idp.util;

import java.text.*;
import java.sql.*;

public class String2Timestamp {

  private SimpleDateFormat sdf=null;

  public String2Timestamp(String pattern) {
    sdf=new SimpleDateFormat(pattern);
  }
  public Timestamp getTimestamp(String tStr)  {

    java.util.Date dt;
    Timestamp ts = null;
	try {
		dt = sdf.parse(tStr);
		ts = new Timestamp(dt.getTime());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return ts;
  }
  public static void main(String[] args) {
    String2Timestamp s2t = new String2Timestamp("hh:mm a");
    String tStr="11:35 pm";
    try {
      Timestamp ts = s2t.getTimestamp(tStr);
      System.out.println("timestamp: "+ts.toString());
    }
    catch (Exception ex) {
      System.err.println("The time string: "+tStr+" is invalid");
      ex.printStackTrace(System.err);
    }
  }
}


