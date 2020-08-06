package it.nch.utility;

import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;


public class MidnightDate {
  
  private final Date date;
  
  public MidnightDate(Date d) {
    this.date = new Date(d.getTime());
  }
  
  
  public Date early() {
    return DateUtils.truncate(this.date, Calendar.DAY_OF_MONTH);
  }
  
  public Date late() {
    return DateUtils.addDays(early(), 1);
  }
}
