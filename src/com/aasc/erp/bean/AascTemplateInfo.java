/**
 * @(#)AascTemplateInfo.java        03/01/2006
 *
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.bean;

import java.util.LinkedList;
import java.util.ListIterator;


/** AascTemplateInfo is a bean class which contains getTemplateList,
 getTemplatefileName ,getTemplateName(),setTemplatefileName(),setTemplateList 
 and setTemplateName() methods to getiing and setting the infomation related to
 template.These methods are implemented using linked list (TemplateList) and 
 getTemplatefileName method is used too retrieve the  filename with path by 
 passing the name of the template name.
 * @author Renuka Aleti
 * @version - 2
 * HISTORY                                                                                                                   |
 03-JAN-2006   Renuka Aleti     Initial Version 
 28-FEB-2006   Renuka Aleti     second Version(added private to all variables)
 */
public class AascTemplateInfo {

    /** Creating the object for linked list to have list of template name and file
     path */
    private LinkedList templateList = new LinkedList();
    private String templateName;
    private String printerName;
    private String templateFileName;
    private Object template;

    /** Setting the Template information.
     * @param templateList Template List.*/
    public void setTemplateList(LinkedList templateList) {
        this.templateList = templateList;
    }

    /** Getting the template information.
     * @return templateList Template list.*/
    public LinkedList getTemplateList() {
        return templateList;
    }

    /** Getting the printer name by passing the template name.
     * @param tempName Template Name.
     * @return printerName Printer Name.*/
    public String getPrinterName(String tempName) {

        /** creating the object for ListIterator. */
        ListIterator listIterator = templateList.listIterator();

        while (listIterator.hasNext()) {
            template = listIterator.next();
            AascTemplateInfo templateInfo = (AascTemplateInfo)template;

            templateName = templateInfo.getTemplateName();
            if (templateName.equals(tempName)) {
                printerName = templateInfo.getPrinterName();
            }
        } // while(listIterator.hasNext())
        return printerName;
    }

    /** This methos will returns the template file name for corresponding
     template name passed.
     * @param tempName Template Name.
     * @return printerName Printer Name.*/
    public String getTemplateFileName(String tempName) {

        /** creating the object for ListIterator. */
        ListIterator listIterator = templateList.listIterator();

        while (listIterator.hasNext()) {
            template = listIterator.next();
            AascTemplateInfo templateInfo = (AascTemplateInfo)template;

            templateName = templateInfo.getTemplateName();
            if (templateName.equals(tempName)) {
                templateFileName = templateInfo.getTemplateFileName();
            }
        } // while(listIterator.hasNext())
        return templateFileName;
    }

    /** getting the template name.
     * @return templateName Template name.*/
    public String getTemplateName() {
        return templateName;
    }

    /** Setting the tempalte name.
     * @param templateName Template Name.*/
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /** getting the printer name.
     * @return printerName Printer Name.*/
    public String getPrinterName() {
        return printerName;
    }

    /** set the printer name.
     * @param printerName Printer Name.*/
    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    /** getting the template file name.
     * @return templateFileName Template file name.*/
    public String getTemplateFileName() {
        return templateFileName;
    }

    /** set the template file name.
     * @param templateFileName Template file name.*/
    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }
}

