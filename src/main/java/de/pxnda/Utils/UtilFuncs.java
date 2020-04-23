package de.pxnda.Utils;

import java.util.concurrent.TimeUnit;

public class UtilFuncs {
    public static String convertToTimeStamp(long millis){
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }
}
