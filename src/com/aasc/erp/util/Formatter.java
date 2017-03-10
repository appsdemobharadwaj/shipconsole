package com.aasc.erp.util;


import java.util.logging.Logger;


/**
 * 
 */

public class Formatter {
    public Formatter() {
    }

    /**Returns String value of the object or a space.
     * @param object Object of type Object class 
     * @return String equivalent of the object
     */
    public String nullStringToSpace(Object object) {
        String spcStr = "";
        if (object == null) {
            return spcStr;
        } else {
            return object.toString();
        }
    }

    public void writeLogger(Logger logger, Exception e) {
        //System.out.println("Exception Message:"+e.getMessage());
        e.printStackTrace();
        logger.severe("Exception Message:"+e.getMessage());
        StackTraceElement t[] = e.getStackTrace();
        if (t != null) {
            for (int i = 0; i < t.length; i++) {
                logger.severe(t[i].toString());
            }
        } else {
            logger.severe(e.getMessage());
        }
    }
}

