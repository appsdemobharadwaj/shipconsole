package com.aasc.erp.util;
/*
 * @(#)AascLogger.java        23/07/2014
 * Copyright (c) Apps Associates Pvt. Ltd.
 * All rights reserved.

 *
 */


import java.io.IOException;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * AascLogger class is used to create a static logger object 
 * This class contains a getLog method which returns 
 * logger, which is used by other classes for logging
 * @version 	- 2
 * @author 	Vandana Gangisetty
 *11/03/09   Madhavi            Modified logging code for FINE
 * 03/05/10  Madhavi            commented the  getFileHandler() method inorder not to write in logdetails.txt
 */
public class AascLogger {
    static Logger logger; // util.logging.Logger object which is used in issuing logging requests      
    static String fileName; // output file name to which logging statements are logged
    static FileHandler fileHandler = 
        null; // file handler object which specifies to which file and in which format logging statements are logged
    static ConsoleHandler consoleHandler; // used to log to console
    //static AascProfileOptionsInfo aascProfileOptionsInfo = 
     //   new AascProfileOptionsInfo(); // profile options object to retreive log output path file name

    static {
        try {
          //  fileName = aascProfileOptionsInfo.getLogPath();
            consoleHandler = new ConsoleHandler();
        } catch (Exception e) {

            logger.log(Level.SEVERE, "Got Exception in logger class :" + e);
        }
    }

    /** static method which returns logger
     * @return logger object
     */
    public static Logger getLogger(String loggerName) {
        logger = Logger.getLogger(loggerName);
     //   aascProfileOptionsInfo = new AascProfileOptionsInfo();
   //     fileName = aascProfileOptionsInfo.getLogPath();
        // fileName="E:\\MADHAVI\\SHIPCONSOLE\\logFiles\\";

        try {

            if (fileName == null) {
                logger.addHandler(consoleHandler);
            } else {
           //     if (aascProfileOptionsInfo.getLoggerMode().equalsIgnoreCase("INFO"))
                    logger.setLevel(Level.INFO);
           //     else if (aascProfileOptionsInfo.getLoggerMode().equalsIgnoreCase("SEVERE"))
                    logger.setLevel(Level.SEVERE);
           //     else if (aascProfileOptionsInfo.getLoggerMode().equalsIgnoreCase("FINE"))
                    logger.setLevel(Level.FINE);
             //   else
           //         logger.setLevel(Level.OFF);
          //      logger.addHandler(getFileHandler());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Got Exception in logger class :" + e);
        }
        return logger;
    }

    /** static method that returns FileHandler 
     * @return FileHandler object is returned that is configured
     * with fileName got from profileOptions and where messages
     * are logged in simple plain text format
     */
    public static FileHandler getFileHandler() {
        try {
            if (fileName != null) {

                logger.log(Level.INFO, 
                           "FileName to write the log :" + fileName);
                if (fileHandler == null) {
                    fileHandler = 
                            new FileHandler(fileName + "", true); // creating file handler object containing filename to which logger writes logging details                
                    fileHandler.setFormatter(new SimpleFormatter()); // logging details are logged in simple format containing details like logging level,class name,function name and logging message   
                }
            }
        } catch (IOException ioException) {
            fileHandler = null;

            logger.log(Level.SEVERE, 
                       "File Handler Exception in Logger Class :" + 
                       ioException.toString());
        }
        return fileHandler;
    }
}
