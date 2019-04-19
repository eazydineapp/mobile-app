package com.eazydineapp.backend.util;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by ravisha on 12/28/16.
 */
public class AppUtil {

    public static boolean isNull(String value){
        if("null".equals(value) || null == value || "".equals(value.trim()))
            return true;
        return  false;
    }

    public static float round(float value) {
        float retVal = Math.round(value * 100.0f)/100.0f;
        return retVal;
    }
}
