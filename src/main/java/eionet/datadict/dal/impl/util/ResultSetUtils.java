package eionet.datadict.dal.impl.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.joda.time.DateTime;

public class ResultSetUtils {

    public static Long getLong(ResultSet rs, String columnLabel) throws SQLException {
        long value = rs.getLong(columnLabel);
        
        return rs.wasNull() ? null : value;
    }
    
    public static DateTime getDateTimeFromMillis(ResultSet rs, String columnLabel) throws SQLException {
        Long value = getLong(rs, columnLabel);
        
        return value == null ? null : new DateTime(value);
    }
    
}
