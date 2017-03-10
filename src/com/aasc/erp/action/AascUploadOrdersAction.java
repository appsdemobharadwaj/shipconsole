/*

 * @AascUploadOrdersAction.java        24/12/2014

 * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */

/**

 * AascUploadOrdersAction class extends Action class.

 * @author Jagadish Jain

 * @creation 24/12/2014
  
 * @History
  
 * 24/12/2014   Jagadish Jain   Added complete code required to handle actions from Import orders jsp page.

 * 11/05/2015   Suman G         Modified INPUT to "input"
 
 * 11/05/2015   Suman G         Added Code to change download sample file.
 * 
 * 13/05/2015  Suman G         Added code to fix #2771 
 * 17/06/2015  Suman G             Added code to fix #2986
 */


package com.aasc.erp.action;


import com.aasc.erp.delegate.AascUploadOrdersDelegate;

import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.FileReader;
import java.io.InputStream;

import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class AascUploadOrdersAction extends ActionSupport implements  ServletRequestAware, ServletResponseAware, ModelDriven{
    public Object getModel() {
        return null;
    }
    static Logger logger = AascLogger.getLogger("AascUploadOrderAction"); // logger object
    protected HttpServletRequest request;
    protected HttpServletResponse response;
         String actiontype="";
        // String destPath="";
    
    private File orderFile;
    private String orderFileContentType;
    private String orderFileFileName;
    private String orderFilePath;
    
    public AascUploadOrdersAction() {
    }
    
    public HttpServletRequest getServletRequest() {
        return request;
    }
    public void setServletRequest(HttpServletRequest req) {
        this.request = req;
    }

    public HttpServletResponse getServletResponse() {
        return response;
    }

    public void setServletResponse(HttpServletResponse resp) {
        this.response = resp;
    }
    public String execute()
    {
       
        actiontype = request.getParameter("actiontype");
        logger.info("actiontype.............."+actiontype);
        HttpSession session = request.getSession(true); // getting the Session object
         if(session.isNew() || 
                 !(session.getId().equals(session.getAttribute("SessionId")))){
             logger.info("in Session "+session.isNew());
             return "error";
         }
         int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
         if(roleIdSession != 4 && roleIdSession != 2){
             return "error";
         }
        if (actiontype.equalsIgnoreCase("downloadfile")) {

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", 
                               "attachment;filename=aascUploadOrdersTemplate.csv");
            try {
                ServletOutputStream out = response.getOutputStream();
                //StringBuffer sb = generateCsvFileBuffer();
                
                  String filename = "aascUploadOrdersTemplate.csv";   
                   // String filepath = "\\";
                    ServletContext sc = request.getSession().getServletContext();
                    System.out.println(sc.getRealPath("/"));
                    String applicationPath = sc.getRealPath("/");

                
                 FileReader fr = new FileReader(applicationPath+filename);
                 BufferedReader br = new BufferedReader(fr);
                 StringBuffer sb = new StringBuffer();
                 String line = br.readLine();
                 while (line!= null) {
                 sb.append(line);
                 line = br.readLine();
                 if(line!=null)
                 sb.append("\n");
                 
                 }
                
                
                InputStream in = 
                    new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));

                byte[] outputByte = new byte[4096];
                //copy binary contect to output stream
                while (in.read(outputByte, 0, 4096) != -1) {
                    out.write(outputByte, 0, 4096);
                }
                in.close();
                out.flush();
                out.close();

            } catch (Exception e) {
                e.getMessage(); 
            }

            return "input";
        }
        
        
       /* Copy file to a safe location */
         //destPath = "C:/apache-tomcat-6.0.33/work/";
       try{
          
          if(orderFileFileName.contains(".csv") ) //|| orderFileFileName.contains(".txt"))
          {
            logger.info("Src File name: " + orderFile);
            logger.info("Dst File name: " + orderFileFileName);
         
            File uploadedFile= orderFile;         
            //File destFile  = new File(destPath, orderFileFileName);
            //FileUtils.copyFile(orderFile, destFile);
            /*below code added for file upload*/
              AascUploadOrdersDelegate aascUploadOrdersDelegate=new AascUploadOrdersDelegate();
              
              if (actiontype.equalsIgnoreCase("uploadOrders")) {
                   logger.info("actiontype is uploadOrders");
              
                    String status=aascUploadOrdersDelegate.uploadOrders( request,  uploadedFile);
                    if(status.equalsIgnoreCase("success")){
                        session.setAttribute("uploadFlag","Y");
                        return "success";
                    }
                
              }
              
          } //close if(".csv")
          
          else{
             return "error"; 
              
          }
          
       }catch(Exception e){
         // e.printStackTrace();
          logger.severe(e.getMessage());
          //addActionError(e.getMessage());
         
       }

       return "input";
    }


    public void setOrderFile(File orderFile) {
        this.orderFile = orderFile;
    }

    public File getOrderFile() {
        return orderFile;
    }

    public void setOrderFileContentType(String orderFileContentType) {
        this.orderFileContentType = orderFileContentType;
    }

    public String getOrderFileContentType() {
        return orderFileContentType;
    }

    public void setOrderFileFileName(String orderFileFileName) {
        this.orderFileFileName = orderFileFileName;
    }

    public String getOrderFileFileName() {
        return orderFileFileName;
    }

    public void setOrderFilePath(String orderFilePath) {
        this.orderFilePath = orderFilePath;
    }

    public String getOrderFilePath() {
        return orderFilePath;
    }
}
