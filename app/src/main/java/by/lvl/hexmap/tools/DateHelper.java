package by.lvl.hexmap.tools;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class DateHelper {

    public static final String DATE_PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_yyyy_MM_ddTHH_mm_ss = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_PATTERN_HH_mm = "HH:mm";

    public static final SimpleDateFormat DATE_FORMATTER_yyyy_MM_dd_HH_mm_ss =
            new SimpleDateFormat(DATE_PATTERN_yyyy_MM_dd_HH_mm_ss, Locale.getDefault());

    public static final SimpleDateFormat DATE_FORMATTER_HH_mm =
            new SimpleDateFormat(DATE_PATTERN_HH_mm, Locale.getDefault());
}
