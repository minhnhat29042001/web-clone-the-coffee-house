package uit.javabackend.webclonethecoffeehouse.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final String DATETIME_FORMAT ="dd-MM-yyyy HH:mm:ss";
    public static final String DATETIME_FORMAT_VNPAY ="yyyyMMddHHmmss";
    public static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    public static String now() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }
}
