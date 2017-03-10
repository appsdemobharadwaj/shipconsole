<%/*========================================================================+
|  DESCRIPTION                                                              |
|    JSP For Showing labels to view                                         |                                                 
|    Version - 1                                                            |       
|    Author: Suman Gunda                                                    |
|    Create On: 12/03/2015
|   HISTORY
|	Date	         Resource           Modification
|  30/10/2015        Shiva              Added code related to DHL  
|  06/11/2015        Mahesh             Added code related to Stamps.com                                  |
+===========================================================================*/%>
<%@ page import="java.io.*"%>
<%@ page import="com.lowagie.text.Document"%>
<%@ page import="com.lowagie.text.Rectangle"%>
<%@ page import="com.lowagie.text.pdf.PdfWriter"%>
<%@ include file="aascHeader.jsp"%>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxGetLabel"); %>
<%
    String labelPath = "";
    String labelName = request.getParameter("labelName");
    AascShipmentOrderInfo aascShipmentOrderInfo;
    aascShipmentOrderInfo = ((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo"));
    String carrierName = aascShipmentOrderInfo.getShipmentHeaderInfo().getCarrierName();
      	String cloudLabelPath=(String)session.getAttribute("cloudLabelPath");
        
    //Mahesh Added below code for Stamps.com  
    AascShipMethodInfo aascShipMethodInfo=(AascShipMethodInfo)session.getAttribute("ShipMethodInfo");
        int carrierId = 0;
        String labelFormat = "";
        
        carrierId = aascShipmentOrderInfo.getShipmentHeaderInfo().getCarrierId();
        logger.info("carrierId::"+carrierId);
        
        labelFormat = aascShipMethodInfo.getPrinterPort(carrierId);
        logger.info("labelFormat::"+labelFormat);
   //Mahesh End             

        
      labelPath=  cloudLabelPath + labelName;

    logger.info("cloud label path:::::"+cloudLabelPath);
    logger.info("labelName:::::"+labelName);
    logger.info("labelPath:::::"+labelPath);
    logger.info("carriernMae:::::"+carrierName);
      
     String upsMode = request.getParameter("upsMode");
      
      if("UPS".equalsIgnoreCase(carrierName)){
             Rectangle pageSize;
               if ("ShipExec".equalsIgnoreCase(upsMode))
            {
              pageSize = new Rectangle(1700, 2500);
             }
             else 
             {
             pageSize = new Rectangle(1500, 1500);
             }
              Document pdfDocument = new Document(pageSize);
               String pdfFilePath = cloudLabelPath + labelName;
               try
               {
                   labelPath =  pdfFilePath+"_PDF";
                   FileOutputStream fileOutputStream = new FileOutputStream(labelPath);
                   PdfWriter writer = null;
                   writer = PdfWriter.getInstance(pdfDocument, fileOutputStream);
                   writer.open();
                   pdfDocument.open();
                   /**
                   * Proceed if the file given is a picture file
                   */
                   
                   pdfDocument.add(com.lowagie.text.Image.getInstance(pdfFilePath));
                   
                   /**
                   * Proceed if the file given is (.txt,.html,.doc etc)
                   */
        //           else
                   //{
                   //File file = new File("E:\\labels\\1Z3749X40396210972.gif");
                   //pdfDocument.add(new Paragraph(org.apache.commons.io.FileUtils.readFileToString(file)));
                   //}
        
                   pdfDocument.close();
                   writer.close();
               }
               catch (Exception exception)
               {
                   logger.severe("Document Exception!" + exception);
               }
            logger.info("Pdf conversion exit ");
       
      }
      
      //Mahesh Added below code for Stamps.com
          logger.info("labelFormat before Stamps::"+labelFormat);
          if("Stamps.com".equalsIgnoreCase(carrierName) && !"Pdf".equalsIgnoreCase(labelFormat)){
          
             Rectangle pageSize;
//             System.out.println("aascShipmentOrderInfo.getShipmentHeaderInfo().getInternationalFlag():::"+aascShipmentOrderInfo.getShipmentHeaderInfo().getInternationalFlag());
   
             if("Y".equalsIgnoreCase(aascShipmentOrderInfo.getIntlFlag()) || "Y".equalsIgnoreCase(aascShipmentOrderInfo.getShipmentHeaderInfo().getInternationalFlag())){
              pageSize = new Rectangle(2500, 4700);
              }else{
              pageSize = new Rectangle(3300, 2500);
              }
              Document pdfDocument = new Document(pageSize);
              String pdfFilePath = cloudLabelPath + labelName;
               try
               {
                   labelPath =  pdfFilePath+"_PDF";
                   FileOutputStream fileOutputStream = new FileOutputStream(labelPath);
                   PdfWriter writer = null;
                   writer = PdfWriter.getInstance(pdfDocument, fileOutputStream);
                   writer.open();
                   pdfDocument.open();
                   /**
                   * Proceed if the file given is a picture file
                   */
                   
                   pdfDocument.add(com.lowagie.text.Image.getInstance(pdfFilePath));
                   
                   /**
                   * Proceed if the file given is (.txt,.html,.doc etc)
                   */
        //           else
                   //{
                   //File file = new File("E:\\labels\\1Z3749X40396210972.gif");
                   //pdfDocument.add(new Paragraph(org.apache.commons.io.FileUtils.readFileToString(file)));
                   //}
        
                   pdfDocument.close();
                   writer.close();
               }
               catch (Exception exception)
               {
                   logger.severe("Document Exception!" + exception);
               }
            logger.info("Pdf conversion exit ");
       
      }
      //Mahesh Ended
      //Shiva added below code for DHL
      if("DHL".equalsIgnoreCase(carrierName)){
            String dhlWaybillNum= aascShipmentOrderInfo.getShipmentHeaderInfo().getWayBill();
           labelPath = cloudLabelPath+dhlWaybillNum;
      }
      //Shiva code end
        response.setContentType("application/pdf");
        response.addHeader("Pragma","no-cache");
        response.addIntHeader("Expires",-1);
              OutputStream outStr=null;                        
              InputStream inputStream=null;
                      
                try 
                { 
                    inputStream= new FileInputStream(labelPath);
                    
                    OutputStream ouputStream = response.getOutputStream();
                   
                         byte[] buffer = new byte[8192];
                         int count=0;
                         while ((count = inputStream.read(buffer)) != -1)
                        {
                             ouputStream.write(buffer, 0, count);
                        }
                        
                        ouputStream.close();
                }
                catch (FileNotFoundException e) 
                { 
                     logger.severe("Exception:::"+e.getMessage());
                }
                
%>
