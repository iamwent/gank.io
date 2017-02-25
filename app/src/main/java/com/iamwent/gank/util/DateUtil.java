package com.iamwent.gank.util;

import com.iamwent.gank.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

public final class DateUtil {

    // the earliest gank posted at 2015-05-18
    private static final int EARLIEST_YEAR = 2015;
    private static final int EARLIEST_MONTH = 3;
    private static final int EARLIEST_DAY = 18;

    public static int checkDateAvailability(int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.set(EARLIEST_YEAR, EARLIEST_MONTH, EARLIEST_DAY);
        Date earliest = calendar.getTime();

        calendar.set(year, month, day);
        Date choosed = calendar.getTime();

        if (choosed.before(earliest)) {
            return R.string.alert_too_early;
        } else if (choosed.after(today)) {
            return R.string.alert_too_late;
        }

        return -1;
    }


    private DateUtil() {
        throw new AssertionError("NO INSTANCE.");
    }
}
