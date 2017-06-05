package de.in.uulm.map.tinder.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by maxka on 05.06.2017.
 */

public class DateHelper {

    /**
     * This method converts the token expire Date from the API into a Java
     * Date Object.
     *
     * @param expireDateString
     * @return the expire date as Date Object or null if there were any
     * parsing errors
     */
    public static Date convertExpireDateStringToDate(String expireDateString){
        Date date;
        DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss" +
                " z", Locale.ENGLISH);
        try {
            date = formatter.parse(expireDateString);
        }catch(ParseException e){
            e.printStackTrace();
            date=null;
        }
        return date;
    }
}
