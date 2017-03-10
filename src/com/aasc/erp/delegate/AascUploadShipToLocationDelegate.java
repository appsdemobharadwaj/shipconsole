package com.aasc.erp.delegate;


/************************************************************************************
History

17-Dec-2014   Eshwari M    Merged Sunanda Code for SC Lite

************************************************************************************/

import au.com.bytecode.opencsv.CSVReader;

import com.aasc.erp.bean.AascERPImportShipToLocationTBL;
import com.aasc.erp.bean.AascShipToLocationsInfo;

import com.aasc.erp.model.AascOracleShipToLocationsDAO;
import com.aasc.erp.model.AascOracleUserControlDAO;
import com.aasc.erp.model.AascUserControlDAO;
import com.aasc.erp.util.AascLogger;


import java.io.File;

import java.io.FileReader;

import java.util.HashMap;


import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/************************************************************************************
History

17-Dec-2014   Eshwari M    Merged Sunanda Code for SC Lite.
18-Dec-2014   Sunanda K    Removed SOPs and added logger statements.
23-Dec-2014   Sunanda K    Changed code for reading values into bean properly.
26-Dec-2014   Suman G      Merged sunanda code
23/03/2015    Jagadish Jain  Changed the logic to read comma delimited excel sheet file and store them in table object and pass that table object to db.
23/03/2015    Jagadish Jain  Also handled error cases.  
11/05/2015    Y Pradeep     Modified code to save email address while uploading from csv file. Bug #2845.
13/05/2015    Y Pradeep     Removed locationId from saveImportedShipToLocations() method call to get Ship To Locations independent to Ship From Location.
13/08/2015    N Srisha      Added code for number of rows validation bug #3393
23/12/2015    Suman G     Added code for Error report for Upload Ship To Locations.
 ************************************************************************************/
public class

AascUploadShipToLocationDelegate {

    static Logger logger = 
        AascLogger.getLogger(" AascUploadShipToLocationDelegate "); // logger object
    public AascUploadShipToLocationDelegate() {
    }
    /**
     * uploadShipToLocations() method for uploading ShipTo Loaction Details from excel sheet which is in .csv format.
     * @return    String   returns "success" or "fail" of Uploading ShipToLocations.
     */
    public String uploadShipToLocations(HttpServletRequest request, 
                                        File uploadedFile) {
        logger.info("Entered into loadShiptolocations");
        int status = 0;
        try {
            CSVReader csvReader = null;
            AascOracleShipToLocationsDAO aascOracleShipToLocationsDAO = 
                new AascOracleShipToLocationsDAO();
            int clientId = 
                Integer.parseInt((String)request.getParameter("client_Id"));
            int userId = 
                Integer.parseInt((String)request.getParameter("user_Id"));
            int locationId = 
                Integer.parseInt((String)request.getParameter("location_Id"));
            
            try {

                csvReader =new CSVReader(new FileReader(uploadedFile.getPath()),','); //Sunanda changed for each value differentiation in csv file
            }
            catch (Exception e) {
                e.printStackTrace();
                logger.info("Error Occured while executing the file");
            }
            int noOfRecords=0;
            String[] Lines;   
            while ((Lines = csvReader.readNext()) != null){
            noOfRecords++;
            }
            logger.info("Number of record in import ship to location file:"+noOfRecords);
            csvReader.close();
            
            csvReader = new CSVReader(new FileReader(uploadedFile.getPath()),',');
            String[] headerRow = csvReader.readNext();
            String[] beanData = new String[headerRow.length];
            if (null == headerRow) {
                logger.info("No columns defined in given CSV file." + 
                            "Please check the CSV file format.");
            }
           // Srisha added code for no. of rows validation bug #3393  
            if(noOfRecords <= 1)
            {
                logger.info("noOfRecords is empty");
                request.setAttribute("key","UploadFileEmpty");   
                return "error";
            } // srisha code ends here
            
            AascShipToLocationsInfo[] aascShipToLocationsInfo = 
                new AascShipToLocationsInfo[noOfRecords-1];
            AascERPImportShipToLocationTBL aascERPImportShipToLocationTBL=new AascERPImportShipToLocationTBL();    
            String[] nextLine;
            int recordNumber=0;
            while ((nextLine = csvReader.readNext()) != null) {
                if(headerRow.length != 12)
                {
                    request.setAttribute("key","ShipToImportError");   
                    return "error";
                }
                
                if (null != nextLine) {
                    int index = 0;
                    for (String string: nextLine) {
                        /*         date = DateUtil.convertToDate(string);
                                      if (null != date) {
                                           ps.setDate(index++, new java.sql.Date(date
                                                   .getTime()));
                                       } else {*/
                        beanData[index] = string.trim();
                        index++;
                        //}
                    }
                    aascShipToLocationsInfo[recordNumber]=new AascShipToLocationsInfo();
                    aascShipToLocationsInfo[recordNumber].setShipToCustomerName(beanData[0]);
                    aascShipToLocationsInfo[recordNumber].setShipToCustLocation(beanData[1]);
                    aascShipToLocationsInfo[recordNumber].setShipToContactName(beanData[2]);
                    aascShipToLocationsInfo[recordNumber].setAddressLine1(beanData[3]);
                    aascShipToLocationsInfo[recordNumber].setAddressLine2(beanData[4]);
                    aascShipToLocationsInfo[recordNumber].setAddressLine3(beanData[5]);
                    aascShipToLocationsInfo[recordNumber].setCity(beanData[6]);
                    aascShipToLocationsInfo[recordNumber].setState(beanData[7]);
                    aascShipToLocationsInfo[recordNumber].setPostalCode(beanData[8]);
                    aascShipToLocationsInfo[recordNumber].setCountryCode(beanData[9]);
                    aascShipToLocationsInfo[recordNumber].setPhoneNumber(beanData[10]);
                    aascShipToLocationsInfo[recordNumber].setEmailAddress(beanData[11]);
                    aascShipToLocationsInfo[recordNumber].setClientId(clientId);
                    aascShipToLocationsInfo[recordNumber].setUserId(userId);
                    aascShipToLocationsInfo[recordNumber].setLocationId(locationId);

                }
                recordNumber++;
            } //end of while loop
            aascERPImportShipToLocationTBL.setAascShipToLocationsInfo(aascShipToLocationsInfo);
            HashMap resultHM=aascOracleShipToLocationsDAO.saveImportedShipToLocations(clientId,userId,aascERPImportShipToLocationTBL);
            LinkedList shipToErrorList=((LinkedList)resultHM.get("errorList"));
            int uploadId=((Integer)resultHM.get("uploadId")).intValue();
            
            request.getSession().setAttribute("shipToErrorList",(Object)shipToErrorList);
            request.setAttribute("key","ShipToImport");
        } catch (Exception e) {
            logger.severe(e.getMessage());
            request.setAttribute("key","ShipToImportFail");
//            return "error";
        }
        
            return "success";

        

    }
    AascUserControlDAO aascUserControlDAO = new AascOracleUserControlDAO();
    /**
     * setCountryDetails() method for setting country name details into session attribute countryNameHashMap.
     * @return    String   returns "success" or "fail" of Setting CountryNamevalues.
     */
    public String setCountryDetails(HttpSession session) {
        try {
            session.setAttribute("countryCodelist",session.getAttribute("locationDetailsList"));
            logger.info("in setCountryDetails setting countryNameHashMap");
            Map countryNameHashMap =new TreeMap((HashMap<String,String>)aascUserControlDAO.getCountryNameDetails());
            session.setAttribute("countryNameHashMap", 
                                 (Object)countryNameHashMap);
            return "success";
        } catch (Exception e) {
            return "failure";
        }

    }

}
