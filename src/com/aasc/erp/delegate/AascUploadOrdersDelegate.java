 /*

  * @AascUploadOrdersDelegate.java        24/12/2014

  * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.

  * All rights reserved.

  */

 /**

  * AascUploadOrdersDelegate class extends Action class. This delegate class call the DAO class to load the imported orders into the SC lite tables. 

  * @author Jagadish Jain

  * @creation 24/12/2014
   
  * @History
   
  * 24/12/2014   Jagadish Jain   Added complete code required to handle actions from Import orders jsp page.

  * 11/05/2015  Suman G         Added code to fix #2846   
  * 26/05/2015  Suman G         Modified indexes to fix #2946
  * 13/08/2015  N Srisha        Added code for number of rows validation while reading file bug #3393
  
  */



 package com.aasc.erp.delegate;

 import au.com.bytecode.opencsv.CSVReader;

 import com.aasc.erp.bean.AascERPImportOrdHREC;
 import com.aasc.erp.bean.AascERPImportOrdHTBL;
 import com.aasc.erp.model.AascOracleSaveOrderImport;
 import com.aasc.erp.model.AascSaveOrderImportDAO;
 import com.aasc.erp.util.AascLogger;

 import java.io.File;
 import java.io.FileReader;

 import java.io.IOException;


 import java.util.HashMap;
 import java.util.LinkedList;
 import java.util.logging.Logger;

 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;

 public class AascUploadOrdersDelegate {
     public AascUploadOrdersDelegate() {
     }
     static Logger logger = AascLogger.getLogger(" AascUploadOrdersDelegate "); // logger object
     
     
     /**
     * uploadOrders() method of AascUploadOrdersDelegate class has two parameters HttpServletRequest and File
     * object and returns a String object with success or failure message.
     * This method is used to parse the import order file uploaded by user and calls the required DAO class to store order data in tables.
     * @param  request HttpServletRequest,  uploadedFile File
     * @return "success" or "error" String
     */
     public String uploadOrders(HttpServletRequest request, File uploadedFile) throws IOException {
     
         logger.info("Inside AascUploadOrderDelegate uploadOrders method");
         try{
         CSVReader csvReader = null;
         AascSaveOrderImportDAO aascOracleSaveOrderImport = new AascOracleSaveOrderImport();
         int importId=0;
         LinkedList errorList=new LinkedList();
         int clientId= Integer.parseInt((String)request.getParameter("client_Id"));
         int userId= Integer.parseInt((String)request.getParameter("user_Id"));
         int locationId= Integer.parseInt((String)request.getParameter("location_Id"));
         HttpSession session=request.getSession();       
         
         try {
              
             csvReader = new CSVReader(new FileReader(uploadedFile.getPath()),',');
         
             } catch (Exception e) {
                 
                 logger.severe("Error Occured while executing the order file"+e.getMessage());
             }
         
         int noOfRecords=0;
         String[] Lines;   
         while ((Lines = csvReader.readNext()) != null){
         noOfRecords++;
         }
         logger.info("Number of record in import orders file:"+noOfRecords);
         csvReader.close();
         
             csvReader = new CSVReader(new FileReader(uploadedFile.getPath()),',');
         
              
         String[] headerRow = csvReader.readNext();
         //String [] beanData=new String [headerRow.length];
          //System.out.println(headerRow.toString());
         
 //    boolean empty = true;
 //    for (int i=0; i<headerRow.length; i++) {
 //      if (headerRow[i] != "") {
 //        empty = false;
 //        break;
 //      }
 //    }
 //        System.out.println(empty);
 //        if(empty){
 //                        headerRow=csvReader.readNext();
 //                        System.out.println("Hee empty");
 //        }
         
         if (null == headerRow) {
             logger.info(
                     "First row is blank line" +
                     "Please check the CSV file format.");
 //            headerRow=csvReader.readNext();
 //            System.out.println(headerRow.toString());
         }
        // Srisha added code for no. of rows validation bug #3393 
       if(noOfRecords <= 1)
       {
          logger.info("noOfRecords is empty");
          request.setAttribute("key","UploadFileEmpty");   
          return "error";
       } // Srisha code emds
     
     AascERPImportOrdHREC[] aascERPImportOrdHREC=new AascERPImportOrdHREC[noOfRecords-1];
     
     AascERPImportOrdHTBL AascERPImportOrdHTBL=new AascERPImportOrdHTBL();
         String[] nextLine; 
         int recordNumber=0;
         while ((nextLine = csvReader.readNext()) != null) {
             
             String [] beanData=new String [headerRow.length];
             if(headerRow.length!=20)
             {
                 request.setAttribute("key","OrdersImportError");   
                 return "fail";
             }
            
             if (null != nextLine) {
                 int index = 0;
                 for (String string : nextLine) {
         //                               date = DateUtil.convertToDate(string);
         //                               if (null != date) {
         //                                   ps.setDate(index++, new java.sql.Date(date
         //                                           .getTime()));
         //                               } else {
                         beanData[index]=string.trim();
 //                         System.out.println("index"+index);
 //                        System.out.println("Stringg:"+string);
                         index++;
                     //}
                      }
                 aascERPImportOrdHREC[recordNumber]=new AascERPImportOrdHREC();
                 aascERPImportOrdHREC[recordNumber].setOrderNumber(beanData[0]);
                 aascERPImportOrdHREC[recordNumber].setCustomerName(beanData[1]);
                 aascERPImportOrdHREC[recordNumber].setContactName(beanData[3]);
                 aascERPImportOrdHREC[recordNumber].setAddress1(beanData[4]);
                 aascERPImportOrdHREC[recordNumber].setAddress2(beanData[5]);
                 aascERPImportOrdHREC[recordNumber].setAddress3(beanData[6]);
                 aascERPImportOrdHREC[recordNumber].setCity(beanData[7]);
                 aascERPImportOrdHREC[recordNumber].setState(beanData[8]);
                 aascERPImportOrdHREC[recordNumber].setPostalCode(beanData[9]);
                 aascERPImportOrdHREC[recordNumber].setCountryCode(beanData[10]);                
                 aascERPImportOrdHREC[recordNumber].setPhoneNumber(beanData[11]);
                 aascERPImportOrdHREC[recordNumber].setShipFromLocation(beanData[13]);
                 aascERPImportOrdHREC[recordNumber].setCompanyName(beanData[14]);
                 aascERPImportOrdHREC[recordNumber].setShipMethodMeaning(beanData[15]);
                 aascERPImportOrdHREC[recordNumber].setCarrierPayCode(beanData[16]);
                 aascERPImportOrdHREC[recordNumber].setCarrierAccountNumber(beanData[17]);
                 aascERPImportOrdHREC[recordNumber].setRefernce1(beanData[18]);
                 aascERPImportOrdHREC[recordNumber].setRefernce2(beanData[19]);
                 
                 aascERPImportOrdHREC[recordNumber].setShipToLocation(beanData[2]);
                 aascERPImportOrdHREC[recordNumber].setEmail(beanData[12]);


             }
             
             recordNumber++;
         } //end of while loop
                 
          AascERPImportOrdHTBL.setAascERPImportOrdHREC(aascERPImportOrdHREC);
          
          HashMap resultHM=aascOracleSaveOrderImport.saveImportOrders(clientId,locationId,userId,AascERPImportOrdHTBL);
          
          errorList=((LinkedList)resultHM.get("errorList"));
          importId=((Integer)resultHM.get("importId")).intValue();
          
          session.setAttribute("errorList",(Object)errorList);
          
 //         if(returnCode==100){
 //             System.out.println("Successfully imported orders");
 //         }
 //         else{
 //             System.out.println("Problem uploadin csv file");
 //         }
          logger.info("Uploaded Successfully");
          request.setAttribute("key","OrdersImport");
          
          //System.out.println("ErrorStatus"+errorStatus);
          

 }catch(Exception e){
              logger.severe(e.getMessage());
              request.setAttribute("key","OrdersImportError");   
              return "fail";
              
          }       
         
         return "success";
     
     
     
     }
 }
