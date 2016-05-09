package eionet.datadict.dal.impl.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;
import org.joda.time.DateTime;

public class ResultSetUtils {

    public static Long getLong(ResultSet rs, String columnLabel) throws SQLException {
        long value = rs.getLong(columnLabel);
        
        return rs.wasNull() ? null : value;
    }
    
    public static DateTime getDateTime(ResultSet rs, String columnLabel) throws SQLException {
        java.sql.Date value = rs.getDate(columnLabel, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        
        return new DateTime(value.getTime());
    }
    
}
