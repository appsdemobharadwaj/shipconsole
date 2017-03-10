package com.aasc.erp.action;

/*========================================================================+
@(#)AascUploadShipToLocation.java 
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/

import com.aasc.erp.delegate.AascUploadShipToLocationDelegate;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

 /**
  * AascUploadShipToLocation extends ActionSupport.
  * @author      Jagadish Jain
  * @version 1.0
  * @since
  * History:
  * 18-Dec-2014  Sunanda K   Removed the SOPs and added logger statements.
  * 12-Dec-2014  Sunanda K   Added code to download a sample csv file.
  * 26-Dec-2014  Suman G     Merged Sunanda code.
  * 07-Jan-2015  Y Pradeep   Added proper java doc.
  * 15-Jan-2015  Y Pradeep   Merged Sunanda's code on 1.0 release bugs.
  * 20-Jan-2015  K Sunanda   Removed des path Hardcodings and replaced printStackTrace() with getMessage()
  * 20-Jan-2015  Suman G     Removed e.printStackTrace()
  * 17/06/2015  Suman G             Added code to fix #2986
  * 23-Dec-2015 Suman G     Added code for Error report for Upload Ship To Locations.
  **/

public class

AascUploadShipToLocation extends ActionSupport implements ServletRequestAware, 
                                                          ServletResponseAware, 
                                                          ModelDriven {

    public Object getModel() {
        return null;
    }

    static Logger logger = 
        AascLogger.getLogger(" AasUploadcShipToLocation"); // logger object
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    String actiontype = "";

    private File myFile;
    private String myFileContentType;
    private String myFileFileName;
    private String destPath;

    public AascUploadShipToLocation() {
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

    /**
     * This is the main action called from the Struts framework.
     * @return String 
     **/
    public String execute() {
        actiontype = request.getParameter("actiontype");
        logger.info("actiontype.............." + actiontype);
        /* Copy file to a safe location */
        //destPath = "C:/apache-tomcat-6.0.33/work/";
        HttpSession session = request.getSession(true);
        if(session.isNew() || 
                !(session.getId().equals(session.getAttribute("SessionId")))){
            logger.info("in Session "+session.isNew());
            return "sessionError";
        }
         int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
        
         if(roleIdSession != 3){
             return "sessionError";
         }
        
        logger.info("I am here" + myFileFileName);
        logger.info("This is my file" + myFile);
        try {
        
            if (actiontype.equalsIgnoreCase("downloadfile")) {

                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", 
                                   "attachment;filename=aascUploadShipToTemplate.csv");
                try {
                    ServletOutputStream out = response.getOutputStream();
                    //StringBuffer sb = generateCsvFileBuffer();
                    
                      String filename = "aascUploadShipToTemplate.csv";   
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

                    
// String filename = "aascUploadOrdersTemplate.csv";   
//  // String filepath = "\\";
//   ServletOutputStream out = response.getOutputStream();
//                ServletContext sc = request.getSession().getServletContext();
//                System.out.println(sc.getRealPath("/"));
//   String applicationPath = sc.getRealPath("/");
//   System.out.println("applicationPath"+applicationPath);
//   response.reset();
//   response.setContentType("APPLICATION/OCTET-STREAM");   
//   response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");  
//  FileInputStream fileInputStream=null;
//   try{
//    fileInputStream= new FileInputStream(applicationPath + filename);  
//   
//   int i;   
//   while ((i=fileInputStream.read()) != -1) {  
//     out.write(i);   
//   }   
//   fileInputStream.close(); 
//   }
//   catch(Exception e){
//   e.printStackTrace();
//   }
                


                return "input";
            }
        
            if(myFile==null){
                return "input";
                
            }

            if (myFileFileName != null && myFileFileName.contains(".csv")) {
                logger.info("Src File name: " + myFile);
                logger.info("Dst File name: " + myFileFileName);

                File uploadedFile = myFile;
                File destFile = new File(destPath, myFileFileName);
                FileUtils.copyFile(myFile, destFile);
                /*below code added for file upload*/
                AascUploadShipToLocationDelegate aascUploadShipToLocationDelegate = 
                    new AascUploadShipToLocationDelegate();
                if (actiontype.equalsIgnoreCase("uploadShipToLocation")) {
                    logger.info("actiontype is uploadShipToLocation");
                    String status = 
                        aascUploadShipToLocationDelegate.uploadShipToLocations(request, 
                                                                               uploadedFile);
                    if (status.equalsIgnoreCase("success")) {
                        session.setAttribute("uploadShipToFlag","Y");
                        return "success";
                    }
                }
            } //close if(".csv")
                     
         

            else {
                return "error";

            }

        } catch (Exception e) {
            
            logger.info(e.getMessage());
            //addActionError(e.getMessage());

        }

        return INPUT;
    }
    /**
     * generateCsvFileBuffer() method for appending values of ShipToLocationDetails into the sample .csv file 
     * @return    StringBuffer   contains the values to be appended into the sample .csv file
     */

    private static StringBuffer generateCsvFileBuffer() {
        StringBuffer writer = new StringBuffer();
        writer.append("customer_name");
        writer.append(',');
        writer.append("location_name");
        writer.append(',');
        writer.append("contact_name");
        writer.append(',');
        writer.append("address_line1");
        writer.append(',');
        writer.append("address_line2");
        writer.append(',');
        writer.append("city");
        writer.append(',');
        writer.append("state");
        writer.append(',');
        writer.append("postal_code");
        writer.append(',');
        writer.append("country");
        writer.append(',');
        writer.append("phone_number");
        writer.append('\n');
        writer.append("Apps");
        writer.append(',');
        writer.append("Location12");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append('\n');
        writer.append("Location123");
        writer.append(',');
        writer.append("Appsx");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append(',');
        writer.append("x");
        writer.append('\n');
        return writer;
    }
    /**
     * getMyFile() method for getting the uploaded excel file which is in .csv format.
     * @return    File   returns file containing the ShipToLocationDetails.
     */
    public File getMyFile() {
        return myFile;
    }
    /**
     * setMyFile() method for setting the uploaded excel file which is in .csv format into the file specified.
     */
    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }
    /**
     * getMyFileContentType() method for getting the content type in the uploaded excel file which is in .csv format.
     * @return    String   returns the content type of file containing the ShipToLocationDetails.
     */
    public String getMyFileContentType() {
        return myFileContentType;
    }
    /**
     * setMyFileContentType() method for setting the content type in the uploaded excel file which is in .csv format.
     */
    public void setMyFileContentType(String myFileContentType) {
        this.myFileContentType = myFileContentType;
    }
    /**
     * getMyFileFileName() method for getting the FileName of the uploaded excel file which is in .csv format.
     * @return    String   returns filename containing the ShipToLocationDetails.
     */
    public String getMyFileFileName() {
        return myFileFileName;
    }
    /**
     * setMyFileFileName() method for setting the FileName of the uploaded excel file which is in .csv format.
     */
    public void setMyFileFileName(String myFileFileName) {
        this.myFileFileName = myFileFileName;
    }


}
