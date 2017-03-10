package com.aasc.erp.action;

import com.aasc.erp.model.AascDBAccess;
import com.aasc.erp.model.AascOracleDAOFactory;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;

import java.text.SimpleDateFormat;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

 /**
  * ReportDriverAction class extends ActionSupport class and implements ModelDriven, ServletRequestAware, ServletResponseAware.
  * This class gets the report format and displays the output using Jasper Reports
  * @version   1.1
  * @author    Eshwari M
  History
  * 07/01/2015   Y Pradeep   Modified code to get connection from datasource.
  * 30/01/2015   Eshwari M   Formatted code, added documentation and ran code audit
  * 27/03/2015  Suman G     Modified cloudLabelPath to reportsPath    
  * 08/06/2015  Suman G     Added Padmavathi's code to fix #2969
  * 17/06/2015  Suman G             Added code to fix #2986
  * */

public class ReportDriverAction extends ActionSupport implements ModelDriven, 
                                                    ServletRequestAware, 
                                                    ServletResponseAware {
    private java.sql.Date fromDate;
    private java.sql.Date toDate;
    private int locationIdstr;
    private String carrierStr = "";
    private int roleId;
    private Integer clientId;
    private String shipMethodStr = "";
    private static Logger logger = AascLogger.getLogger("ReportDriverAction");
    private String dateFrom;
    private String dateTo;
    private String testFromDate;
    private String testToDate;
    private int locationId;
    private String labelpath = "";
    private String reportsPath = "";
    private String reportFile = "";
    AascOracleDAOFactory connectionFactory = new AascOracleDAOFactory();
    Connection connection = null; // establishing the connection
    JasperPrint jasperPrint;
    private String actionType;

    protected HttpServletRequest request;
    protected HttpServletResponse response;

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
      This is the main action called from the Struts framework.
      @return returns the string object to forward the control back to the respective jsp page
    */ 
    public String execute() {
        logger.info("In  ReportDriverAction execute method");
        JasperReport jasperReport;
        JasperDesign jasperDesign;
        try {

            HttpSession session = 
                request.getSession(true); // creating the object of the HttpSession class

            if (session.isNew() || 
                !(session.getId().equals(session.getAttribute("SessionId")))) {
                logger.info("inside session");
                return "error"; // 06-June-2007 Bhanu added this code for session validation.
            }
             int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
             if(roleIdSession != 2 && roleIdSession != 5 && roleIdSession != 4){
                return "error";
             }
            String reportType = request.getParameter("reportType");
            logger.info("reportType:::::" + reportType);
            try {
                roleId = 
                        ((Integer)session.getAttribute("role_id")).intValue();
                if (roleId == 4) {
                    locationIdstr = 
                            ((Integer)session.getAttribute("location_id")).intValue();
                } else if (roleId != 4 && 
                           "HTML".equalsIgnoreCase(reportType)) {
                    locationIdstr = 
                            Integer.parseInt(request.getParameter("locationIdHidden")); 
                } else {
                    locationIdstr = 
                            Integer.parseInt(request.getParameter("locationIdSelect"));
                }
                if (locationIdstr != 0) {
                    locationId = locationIdstr;
                } else {
                    return "error";
                }
                logger.info("locationId : "+ locationIdstr);
            } catch (Exception e) {
                e.getMessage();
            }
            try {
                dateFrom = request.getParameter("fromDate");
                if (dateFrom == null) {
                    testFromDate = "";
                } else {
                    dateFrom = dateFrom.trim();
                    String convertDate = dateFrom;
                    int len = convertDate.length();
                    int index = convertDate.indexOf('-');
                    int index1 = convertDate.lastIndexOf('-');
                    String syear = 
                        convertDate.substring(index1 + 1, len).trim();
                    String smon = convertDate.substring(0, index).trim();
                    String sdate = 
                        convertDate.substring(index + 1, index1).trim();
                    if (smon.length() == 1) {
                        smon = '0' + smon;
                    }
                    if (sdate.length() == 1) {
                        sdate = '0' + sdate;
                    }
                    testFromDate = smon + '/' + sdate + '/' + syear;
                }
            } catch (Exception e) {
                testFromDate = "";
            }

            logger.info("testFromDate in controller = " + testFromDate);

            try {
                dateTo = request.getParameter("toDate");

                if (dateTo == null) {
                    testToDate = "";
                } else {
                    dateTo = dateTo.trim();
                    String convertDate = dateTo;
                    int len = convertDate.length();
                    int index = convertDate.indexOf('-');
                    int index1 = convertDate.lastIndexOf('-');
                    String syear = 
                        convertDate.substring(index1 + 1, len).trim();
                    String smon = convertDate.substring(0, index).trim();
                    String sdate = 
                        convertDate.substring(index + 1, index1).trim();
                    if (smon.length() == 1) {
                        smon = '0' + smon;
                    }
                    if (sdate.length() == 1) {
                        sdate = '0' + sdate;
                    }
                    testToDate = smon + '/' + sdate + '/' + syear;
                }
            } catch (Exception e) {
                testToDate = "";
            }

            logger.info("To Date : " + testToDate);
            
            carrierStr = request.getParameter("carrierSelect");
            request.setAttribute("carrierId", carrierStr);
            clientId = (Integer)session.getAttribute("client_id");
            request.setAttribute("clientIdNum", clientId);

            if ("HTML".equalsIgnoreCase(reportType) && roleId == 2) {
                clientId = Integer.parseInt(request.getParameter("clientId"));
            } 

            else if (clientId == null) {
                clientId = 
                        Integer.parseInt(request.getParameter("clientIdSelect"));
            }


            try {
                shipMethodStr = request.getParameter("shipMethod");
                if ("ALL".equalsIgnoreCase(shipMethodStr) || 
                    shipMethodStr == null || "".equals(shipMethodStr)) {
                    shipMethodStr = "%";
                }
                if ("-1".equals(carrierStr)) {
                    carrierStr = "%";
                }

                logger.info("shipMethod : " + shipMethodStr);
                logger.info("carrier : " + carrierStr);

            } catch (Exception e) {
                logger.severe("In exception of shipMethod : " + 
                              e.getMessage());
                shipMethodStr = null;
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                GregorianCalendar gc = new GregorianCalendar();

                java.util.Date dateFrom = sdf.parse(testFromDate);
                java.util.Date dateTo = sdf.parse(testToDate);
                gc.setTime(dateFrom);
                gc.setTime(dateTo);

                fromDate = new java.sql.Date(dateFrom.getTime());
                toDate = new java.sql.Date(dateTo.getTime());
                //toDate.setDate(toDate.getDate() + 1);

                logger.info("fromDate==" + fromDate);
                logger.info("toDate==" + toDate);

            } catch (Exception e) {
                logger.severe("Error while parsing the Date: " + 
                              e.getMessage());
            }

            /*AascProfileOptionsBean aascProfileOptionsInfo = (AascProfileOptionsBean)session.getAttribute("ProfileOptionsInfo");

            labelpath = aascProfileOptionsInfo.getLabelPath();*/
            labelpath = new AascDBAccess().getMessage("reportsPath");

            reportsPath = labelpath + "templates/";
            //reportsPath =  "E:\\Ship Console\\ShipConsole Lite\\jrxmls\\";
            
            logger.info("reportType==" + reportType);
            // load JasperDesign from XML and compile it into JasperReport
            try {

                HashMap parameters = new HashMap();
                parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
                parameters.put("IP_LOCATION_ID", locationIdstr);
                parameters.put("IP_CARRIER_CODE", carrierStr);
                parameters.put("IP_USER_METHOD_MEANING", shipMethodStr);
                parameters.put("IP_FROM_DATE", fromDate);
                parameters.put("IP_TO_DATE", toDate);
                parameters.put("IP_CLIENT_ID", clientId);

                /*logger.info("IP_LOCATION_ID==" + 
                            parameters.get("IP_LOCATION_ID"));
                logger.info("IP_CARRIER_CODE==" + 
                            parameters.get("IP_CARRIER_CODE"));
                logger.info("IP_USER_METHOD_MEANING==" + 
                            parameters.get("IP_USER_METHOD_MEANING"));
                logger.info("IP_FROM_DATE==" + parameters.get("IP_FROM_DATE"));
                logger.info("IP_TO_DATE==" + parameters.get("IP_TO_DATE"));
                logger.info("IP_CLIENT_ID==" + parameters.get("IP_CLIENT_ID"));*/

                actionType = 
                        request.getParameter("actionType"); // getting actionType from jsp
                logger.info("actionType   " + actionType);


                if (actionType.equalsIgnoreCase("CARRIERSHIPACTIVITY")) {
                    reportFile = reportsPath + "SCLyteShipConsole.jrxml";
                }

                else if (actionType.equalsIgnoreCase("CarrierSLAReport")) {
                    reportFile = reportsPath + "SCLyteCarrierSLAReport.jrxml";
                }
                logger.info("reportFile ==== " + reportFile); 
                jasperDesign = JRXmlLoader.load(reportFile);
                jasperReport = 
                        JasperCompileManager.compileReport(jasperDesign);
            
                try {
                    connection = connectionFactory.createDataSource().getConnection();
                    jasperPrint = 
                            JasperFillManager.fillReport(jasperReport, parameters, 
                                                         connection);
                } catch (Exception e) {
                    logger.severe("Got Exception in filling Report with parameters : " + 
                                  e.getMessage());
                    e.printStackTrace();

                } finally {
                    try {
                        // closing all the resources which have been opened                
                        if (connection != null) {
                            try {
                                connection.close();
                                connection = null;
                            } catch (Exception e) {
                                logger.severe("Exception::"+e.getMessage());

                            }
                        }
                    } catch (Exception e) {
                        logger.severe("Error in closing connection" + 
                                      e.getMessage());
                    }
                }
                OutputStream outputStream = response.getOutputStream();
                
                JRExporter exporter = null;
                JExcelApiExporter xlsExporter = null;
                
                //Padma code for issue #2969
                    if (actionType.equalsIgnoreCase("CARRIERSHIPACTIVITY")) {
                 
                        actionType = "Manifest Summary Report";
                    }
                else if (actionType.equalsIgnoreCase("CarrierSLAReport")) {
                    actionType="On Time Delivery Report";
                }
                //end of Padma code 
                if ("PDF".equalsIgnoreCase(reportType)) //working fine
                {
                    logger.info("In PDF report type");
                    response.setContentType("application/pdf");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, 
                                          jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, 
                                          outputStream);
                    response.setHeader("Content-disposition", 
                                       "attachment; filename=\"" + actionType + 
                                       ".pdf" + "\"");
                }

                else if ("HTML".equalsIgnoreCase(reportType)) //working fine without images
                {
                    logger.info("In HTML report type");
                    exporter = new JRHtmlExporter();
                    response.setContentType("text/html");
                    response.setHeader("Content-disposition", 
                                       "inline; filename=\"" + actionType + 
                                       ".html" + "\"");
                    request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, 
                                                      jasperPrint);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, 
                                          jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, 
                                          outputStream);
                    // gonna set url pattern given for Image servlet with a reponse parameter <url-pattern>/image</url-pattern>
                    exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, 
                                          "imageServlet?image=");

                } else if ("CSV".equalsIgnoreCase(reportType)) {
                    logger.info("In CSV report type");
                    exporter = new JRCsvExporter();
                    exporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, 
                                          jasperPrint);
                    exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, 
                                          ",");
                    exporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER, 
                                          System.getProperty("line.separator"));
                    exporter.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, 
                                          outputStream);
                    response.setContentType("text/csv");
                    response.setHeader("Content-disposition", 
                                       "inline; filename=\"" + actionType + 
                                       ".csv" + "\"");
                } else if ("XML".equalsIgnoreCase(reportType)) //working fine
                {
                    logger.info("In XML report type");
                    JasperExportManager.exportReportToXmlStream(jasperPrint, 
                                                                outputStream);
                } else if ("RTF".equalsIgnoreCase(reportType)) {
                    logger.info("In RTF report type");
                    response.setContentType("application/rtf");
                    response.setHeader("Content-disposition", 
                                       "inline; filename=\"" + actionType + 
                                       ".rtf" + "\"");
                    exporter = new JRRtfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, 
                                          jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, 
                                          outputStream);
                } else if ("XLS".equalsIgnoreCase(reportType)) {
                    logger.info("In XLS report type");
                    xlsExporter = new JExcelApiExporter();
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-disposition", 
                                       "inline; filename=\"" + actionType + 
                                       ".xls" + "\"");
                    OutputStream servletOutputStream = outputStream ; //response.getOutputStream();
                    xlsExporter.setParameter(JExcelApiExporterParameter.JASPER_PRINT, 
                                             jasperPrint);
                    xlsExporter.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, 
                                             servletOutputStream);
                    xlsExporter.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, 
                                             Boolean.TRUE);
                    xlsExporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, 
                                             Boolean.FALSE);
                    xlsExporter.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, 
                                             Boolean.TRUE);
                    xlsExporter.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, 
                                             Boolean.TRUE);
                    servletOutputStream.flush();
                }
                try {
                    if ("XLS".equalsIgnoreCase(reportType)) {
                        logger.info("Exporting to XLS report");
                        xlsExporter.exportReport();

                    } else {
                        logger.info("Exporting to report");
                        exporter.exportReport();
                    }

                } catch (Exception e) {
                
                    //throw new ServletException(e);
                    logger.info("Exception Occurred : "+e.getMessage());
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.flush();
                            outputStream.close();

                            // response.flushBuffer();
                            // response.reset();
                            logger.info("Closed both output and response");
                        } catch (IOException ex) {
                            logger.severe("Got Exception in closing outputStream" + 
                                          ex.getMessage());
                        }
                    }
                }


            } catch (Exception e) {
                logger.severe("Got exception in ReportDriverAction" + 
                              e.getMessage());
                //e.printStackTrace();
            }

        } catch (Exception sqle) {
            logger.severe("Got exception in ReportDriverAction" + 
                          sqle.getMessage());
            //sqle.printStackTrace();
        }
        if (actionType.equals("CARRIERSHIPACTIVITY")) {
            return "CarrierShipActivity";
        } else {
            return "CarrierSLAReport";
        }

    }

    public String nullStringToSpace(Object object) {
        String spcStr = "";
        if (object == null) {
            return spcStr;
        } else {
            return object.toString();
        }
    }

    public Object getModel() {
        return null;
    }
}
