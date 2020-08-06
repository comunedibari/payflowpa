package it.tasgroup.rest.utils;

import javax.ws.rs.WebApplicationException;
import javax.xml.bind.DatatypeConverter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class ISO8601DateParam {
    private final Date date;

    public ISO8601DateParam(String dateStr) {
        Calendar calendar = DatatypeConverter.parseDateTime(dateStr);
        this.date = calendar.getTime();
    }


    public Timestamp getTimestamp () {
        return new Timestamp(this.date.getTime());
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date != null ? date.toString() : "";
    }
}
