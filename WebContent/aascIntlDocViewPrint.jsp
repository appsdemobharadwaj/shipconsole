<%/*==============================================================================================+
|  DESCRIPTION                                                                                    |
|    JSP to Display International Shipments Documents                                             |
|    Author Y Pradeep                                                                             |
|    Version   1                                                                                  |
|    Creation 28-JAN-2015                                                                         |
History:
    02/02/2015   Y Pradeep    Added file to display International Shipment Documents.
    11/02/2015   Y Pradeep    Added code to get title from property file.
+==================================================================================================*/%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page errorPage="aascShipError.jsp" %>
<%@ page import="java.util.logging.*"%>
<%@ page import="java.util.*" %>
<%@ page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@ page import="com.aasc.erp.util.*"%>
<%@ page import="oracle.jdbc.driver.*"%>
<%@ page import="com.aasc.erp.carrier.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.lowagie.text.Document"%>
<%@ page import="com.lowagie.text.Rectangle"%>
<%@ page import="com.lowagie.text.pdf.PdfWriter"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><s:property value="getText('aascIntlDocViewPrint')"/></title>
    </head>
    <body>
    <%!
        static Logger logger = AascLogger.getLogger("aascInternationalShipments.jsp");
    %>
    <%!
        /** This method can replace the null values with nullString.
        * @return String that is ""
        * @param obj of type Object
        */   
        String nullStrToSpc(Object obj) {
            String spcStr = "";
    
            if (obj == null) {
                return spcStr;
            } else {
                return obj.toString();
            }
        }    
    %>
    <%
        AascShipmentOrderInfo aascOrderInfo=null;
        String orderNumber="";
        Date shipmentDate=null;
        String shippedDateStr="";
        AascProfileOptionsBean aascProfileOptionsInfo=null;
        AascShipMethodInfo aascShipMethodInfo ;//declare this object
        String labelPath="";
        String upsIntlDoc="";
        String fdexIntlDoc="";
        String filePath="";
       try{
            aascOrderInfo =(AascShipmentOrderInfo) session.getAttribute("AascShipmentOrderInfo");
            AascShipmentHeaderInfo headerBean = aascOrderInfo.getShipmentHeaderInfo();
            orderNumber = headerBean.getOrderNumber();
            //System.out.println("order_number:::"+order_number);
            shipmentDate=headerBean.getShipmentDate();
            shippedDateStr=shipmentDate.toString().replaceAll("-","_");
            aascProfileOptionsInfo=(AascProfileOptionsBean)session.getAttribute("ProfileOptionsInfo");
            String cloudLabelPath=(String)session.getAttribute("cloudLabelPath");
            logger.info("cloudLabelPath in aascIntlDocViewPrint "+cloudLabelPath);
            labelPath=cloudLabelPath;
            //labelPath = aascProfileOptionsInfo.getLabelPath();
            labelPath =  labelPath + "intlDocs/";
            String labelName = request.getParameter("labelName");
            String carrierName = headerBean.getCarrierName();
            String upsMode = request.getParameter("upsMode");
 
            logger.info("carrierName"+carrierName);
            if("UPS".equalsIgnoreCase(carrierName) || nullStrToSpc(headerBean.getShipMethodMeaning()).contains("UPS")){
             
             if("ShipExec".equalsIgnoreCase(upsMode)){
            Rectangle pageSize = new Rectangle(3900, 4400);
               Document pdfDocument = new Document(pageSize);
               String pdfFilePath = labelPath + orderNumber +"_"+ shippedDateStr +"_CI";
              
              try
               {
                   labelPath =  pdfFilePath+"_PDF";
                   FileOutputStream fileOutputStream = new FileOutputStream(labelPath);
                   PdfWriter writer = null;
                   writer = PdfWriter.getInstance(pdfDocument, fileOutputStream);
                   writer.open();
                   pdfDocument.open();
                   pdfDocument.add(com.lowagie.text.Image.getInstance(pdfFilePath));
                   pdfDocument.close();
                   writer.close();
               }
               catch (Exception exception)
               {
                   logger.severe("Document Exception!" + exception);
               }
             upsIntlDoc = orderNumber +"_"+ shippedDateStr +"_CI_PDF";
                filePath =labelPath;
                logger.info("filePath"+filePath);
            }
            else{
                upsIntlDoc = orderNumber + "_Shipment_" + shippedDateStr +"_UPS.pdf";
                filePath =labelPath+upsIntlDoc;
                logger.info("filePath"+filePath);
            }
            }
            else{
                fdexIntlDoc = orderNumber + "_Shipment_" + shippedDateStr +"_FDXECI.pdf";
                filePath =labelPath+fdexIntlDoc;
                logger.info("filePath"+filePath);
            }
        }catch(Exception e)
        {
            logger.info("Got Exception e = "+e.getMessage());
        }
        
        response.setContentType("application/pdf");
        response.addHeader("Pragma","no-cache");
        response.addIntHeader("Expires",-1);
        //response.addHeader("Cache-Control","no-cache, must-revalidate");
        OutputStream outStr=null;                        
        InputStream inputStream=null;
        OutputStream ouputStream=null;        
        try 
        { 
            inputStream= new FileInputStream(filePath);
            ouputStream = response.getOutputStream();
            // pipeStream(inputStream, true, out, false);
            byte[] buffer = new byte[8192];
            int count=0;
            while ((count = inputStream.read(buffer)) != -1)
            {
                ouputStream.write(buffer, 0, count);
            }
        }
        catch (FileNotFoundException e) 
        { 
            logger.info("Got Exception e  143 = "+e.getMessage());
        }
        catch (IOException e) 
        { 
            logger.info("Got Exception e  147 = "+e.getMessage());
        } 
        finally
        {
            try{
                ouputStream.flush();
                ouputStream.close();
            }catch(Exception e){
                logger.info("Got Exception e 155::"+e.getMessage());
            }
        }
    %>
     <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
</html>
