/**
 * AascIndexRequestDelegate class to pass data to model classes to save and retrieve data from database 
 
 * @Author Suman Gunda
 *
 * @Version 1.0
 * 
 * @creation 24/07/2014
 *
 *
 *  17/12/2014   Eshwari M   Code Clean UP
 *  22/12/2014   Eshwari M   Modified code for role2 shipping
 *  31/12/2014   Eshwari M   Modified code to pull dimensions for all roles by removing the role 4 if condition
 *  07/01/2015   Eshwari M   Merged Sunanda's code of calling getAllLocationDetails() method call as this method modiifed
                             and added getPackageDimensionDetails() method for Package Dimensions in role 2
 *  29/01/2015   Suman G     Uncommented client id for Hazardous material details for #2533.
 *  18/02/2015   Sunanda k   Modified code for clearing session object for package dimension functionality.
 *  09/03/2015   Y Pradeep   Modified sessionList order of initializing clientId, locationId to load profile options details while navigating to other pages.
 *  16/03/2015   Eshwari M   Removed commented code
 *  27/03/2015   Sunanda K   modified code by removing unused variable type and added roleId for bug fix #2703
 *  15/04/2015   Eshwari M   Modified code to fix bug # 2582.
 *  13/05/2015   Y Pradeep   Removed locationId from getAllCustomerLocationDetails() method call to get Ship To Locations independent to Ship From Location.
 *  03/06/2015   Y Pradeep   Added code to call getWeightScaleNames() method for Weighing Scale details and put in session when Porfile Options action is performed.
 *  10/06/2015   Suman G     Added code to fix #2962
 *  10/06/2015   Jagadish    Added code to get original cost from looups required for pricing details.
 */ 

package

com.aasc.erp.delegate;

import com.aasc.erp.bean.AascGetLocInfo;
import com.aasc.erp.bean.AascPackageDimensionInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.util.AascLogger;
import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascSetupLocationBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascSubscriptionDetailsBean;
import com.aasc.erp.model.AascCarrierConfigurationDAO;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.model.AascGetLocDAO;
import com.aasc.erp.model.AascOracleCarrierConfigurationDAO;
import com.aasc.erp.model.AascOracleDAOFactory;
import com.aasc.erp.model.AascOracleProfileOptionsDAO;
import com.aasc.erp.model.AascOracleSetupLocationDAO;
import com.aasc.erp.model.AascOracleShipToLocationsDAO;
import com.aasc.erp.model.AascOracleUserControlDAO;
import com.aasc.erp.model.AascPackageDimensionDAO;
import com.aasc.erp.model.AascProfileOptionsDAO;
import com.aasc.erp.model.AascSetupLocationDAO;
import com.aasc.erp.model.AascShipMethodDAO;
import com.aasc.erp.model.AascShipToLocationsDAO;
import com.aasc.erp.model.AascSubscriptionDetailsDAO;
import com.aasc.erp.model.AascUserControlDAO;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class AascIndexRequestDelegate {
    public AascIndexRequestDelegate() {
    }
    private static Logger logger = 
        AascLogger.getLogger("AascIndexRequestDelegate"); // this method returns the object of the logger class
    
     
    AascDAOFactory aascDAOFactory;
    AascUserControlDAO aascUserControlDAO ;
    AascProfileOptionsDAO aascProfileOptionsDAO;
    AascPackageDimensionDAO aascPackageDimensionDAO;
    AascShipMethodInfo aascShipMethodInfo;

    AascShipMethodDAO aascShipMethodDAO;
    
    int locationId = 0;
    int clientId = 0;
    HttpSession session;
    LinkedList sessionList;
    HttpServletRequest request;
    AascProfileOptionsBean aascProfileOptionsBean;
    int queryTimeOutInt;

    AascSubscriptionDetailsBean aascSubscriptionDetailsBean = null;
    
    public String commonAction(HttpSession sess, HttpServletRequest req){ //, String requestType){  //, int type, String requestType){

       try{

          session=sess;
          request=req;
          // this.type=type;

          logger.info("request : "+request);
          int roleId = ((Integer)session.getAttribute("role_id")).intValue();
          logger.info("roleId : "+roleId);
          
          locationId =((Integer)session.getAttribute("location_id")).intValue(); // Gettin Location Id from session     
          clientId = ((Integer)session.getAttribute("client_id")).intValue();
            
          logger.info("locationId : " + locationId);
          logger.info("clientId : " + clientId);

          sessionList = new LinkedList();
            // modified session list order of initializing clientId, locationId by Y Pradeep
           sessionList.add(locationId);
           sessionList.add(clientId);
          //***********30/08/07 (end) *************
          aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);

          aascProfileOptionsDAO =  aascDAOFactory.getAascProfileOptionsDAO();
          aascProfileOptionsBean = aascProfileOptionsDAO.getProfileOptionsBean(sessionList);
          logger.info(" created aascProfileOptionsInfo  ");
           
          aascShipMethodDAO = aascDAOFactory.getAascShipMethodDAO();
          AascSetupLocationDAO  aascSetupLocationDAO = aascDAOFactory.getAascSetupLocationDAO();
           
           HashMap locationHashMap = aascSetupLocationDAO.getAllLocationDetails(clientId);     
           LinkedList shipFromList = (LinkedList)locationHashMap.get("locationList");
           session.setAttribute("locationDetailsList",shipFromList); 
           aascShipMethodInfo = aascShipMethodDAO.getShipMethodInfo(sessionList);

           aascPackageDimensionDAO =  aascDAOFactory.getPackageDimensionDAO();
           AascPackageDimensionInfo aascPackageDimensionInfo = null;
           AascPackageDimensionInfo aascPackageDimensionUnitInfo = null;

           LinkedList unitList = new LinkedList();
           //if (roleId == 4)  // 31-Dec-2014  Eshwari
           {
              if (locationId != 0) {
                 aascPackageDimensionInfo =
                      aascPackageDimensionDAO.getPackageDimensionInfo(locationId,
                                                                      roleId, clientId);
                    aascPackageDimensionInfo.setLocationId(locationId);

              } else {

                 aascPackageDimensionInfo = new AascPackageDimensionInfo();
                 aascPackageDimensionInfo.setLocationId(0);
              }
           }
           aascPackageDimensionUnitInfo = aascPackageDimensionDAO.getUnitsInfo();
           unitList = aascPackageDimensionUnitInfo.getUnitDetails();

           session.setAttribute("aascPackageDimensionInfo",
                               aascPackageDimensionInfo);
           session.setAttribute("ProfileOptionsInfo", aascProfileOptionsBean);

           session.setAttribute("unitList", (Object)unitList);

           LinkedList harzardousUnitList = new LinkedList();
           aascPackageDimensionUnitInfo =  aascPackageDimensionDAO.getHazardousUnitsInfo();
           harzardousUnitList =   aascPackageDimensionUnitInfo.getHazardousUnitDetails();

           session.setAttribute("harzardousUnitList", harzardousUnitList);


           LinkedList uomPackageList = new LinkedList();
           aascPackageDimensionUnitInfo =
                   aascPackageDimensionDAO.getPackageUOMInfo();
           uomPackageList =
                   aascPackageDimensionUnitInfo.getPackageUOMDetails();

           session.setAttribute("packageUOMDetails", uomPackageList);

           session.removeAttribute("ShipMethodInfo");

           session.setAttribute("ShipMethodInfo", aascShipMethodInfo); 
           
           AascGetLocDAO aascGetLocDAO = aascDAOFactory.getLocationDAO();
           AascGetLocInfo aascGetLocInfo = aascGetLocDAO.getLocationInfo(clientId);
           session.setAttribute("locationInfo", aascGetLocInfo);
           session.setAttribute("ProfileOptionsInfo", aascProfileOptionsBean);

           logger.info(" Cloud Label Path= " + (String)session.getAttribute("cloudLabelPath"));
       }
       catch(Exception e){
           logger.severe("Exception::"+e.getMessage());
           return "FAILURE";
       }
       return "SUCCESS";
    }
     
    
     
    public String commonActionForPSCE(HttpSession session) {
        try {
            logger.info("Inside commonActionForPSCE"); 
            AascDAOFactory aascDAOFactory = new AascOracleDAOFactory();
            clientId = ((Integer)session.getAttribute("client_id")).intValue();
            //logger.info("clientId : "+clientId);
            if(aascDAOFactory == null)
                aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
            aascProfileOptionsDAO = aascDAOFactory.getAascProfileOptionsDAO();
            HashMap clientDetailsHashMap = 
                aascProfileOptionsDAO.getClientDetailsLookUpValues();
            session.setAttribute("clientDetailsHashMap", clientDetailsHashMap);


            String message = getLocationDetails(session);
            if ("success".equalsIgnoreCase(message)) {

                //return "SUCCESS";
            } else {
                return "error";
            }
        }

        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            return "FAILURE";
        }
        return "SUCCESS";
    }
    
    /**

     * getCustomerDetails Method calls AascOracleUserControlDAO
                          to get Customer details.
     * @param session 

     * @return String
     
     */
    public String getCustomerDetails(HttpSession session) {

        try {
            LinkedList customerList = new LinkedList();
            aascUserControlDAO = new AascOracleUserControlDAO();
            customerList = aascUserControlDAO.getAllCustomerDetails();
            session.setAttribute("customerDetailsList", customerList);
            logger.info("customerList :::::::::::::::::::::" + 
                               customerList.toString());
            Map countryNameHashMap = 
                new TreeMap((HashMap)aascUserControlDAO.getCountryNameDetails());
            HashMap hashMap = null;
            
            hashMap = getLookUpDetails("ORIGINAL_COST");
            String originalCost=(String)hashMap.get("ORIGINAL_COST");    
            session.setAttribute("originalCost", originalCost);

            logger.info("countryNameHashMap ::::::::" + 
                               countryNameHashMap.toString());
            session.setAttribute("countryNameHashMap", countryNameHashMap);
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILURE";
        }
        return "SUCCESS";
    }


    /**

     * getLocationDetails Method calls aascOracleSetupLocationDAO
                          to get Location details.
     * @param session

     * @return String
     
     */
    public String getLocationDetails(HttpSession session) {
        logger.info("inside getLocationDetails");
        try {
            LinkedList locationList = null ; //new LinkedList();
            int clientId = 
                Integer.parseInt(session.getAttribute("client_id").toString());
            AascSetupLocationDAO aascSetupLocationDAO = 
                new AascOracleSetupLocationDAO();
            HashMap locationHashMap = aascSetupLocationDAO.getAllLocationDetails(clientId);     
            locationList = (LinkedList)locationHashMap.get("locationList");
                       
            HashMap locationDetailsMap = new HashMap();
            Iterator locationListIterator = locationList.iterator();
            AascSetupLocationBean aascSetupLocationBean = null ;
            for (int i = 0; i < locationList.size(); i++) {
                aascSetupLocationBean = (AascSetupLocationBean)locationListIterator.next();
                /* Storing the location Id and the location name in a hash map and storing the map in a session
                           for using it as LOV in all the settings pages */
                if("Y".equalsIgnoreCase(aascSetupLocationBean.getLocationStatus()))                      
                    locationDetailsMap.put(aascSetupLocationBean.getLocationId(), aascSetupLocationBean.getLocationName());

            }
            int roleId = ((Integer)session.getAttribute("role_id")).intValue() ;
            if(roleId == 2)
            {
                session.setAttribute("locationsList", "");
                session.setAttribute("locationDetailsMap", "");
            }
            else
            {
                session.setAttribute("locationsList", locationList);
                session.setAttribute("locationDetailsMap", locationDetailsMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    public void carrierPaymethods(HttpSession session) {
        AascProfileOptionsDAO aascProfileOptionsDAO = 
            new AascOracleProfileOptionsDAO();
        ArrayList carrierPayMethods = null;
        try {
            carrierPayMethods = 
                    aascProfileOptionsDAO.getCarrierPayMethodValues();
            //  carrierPayMethods = aascProfileOptionsDAO.getCarrierPayMethodValues();   
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute("carrierPayMethods", carrierPayMethods);
    }
    
    public String profileOptionsAction(HttpSession session, 
                                HttpServletRequest request) {

        AascProfileOptionsDAO aascProfileOptionsDAO = 
            new AascOracleProfileOptionsDAO();
        aascUserControlDAO = new AascOracleUserControlDAO();

        try{
                 
                 clientId = (Integer)session.getAttribute("client_id");
                 logger.info("clientId : "+clientId);                  
             HashMap clientDetailsHashMap =
                           aascProfileOptionsDAO.getClientDetailsLookUpValues();
                       session.setAttribute("clientDetailsHashMap", clientDetailsHashMap); 
             
                 Integer roleIdInt = 
                     (Integer)session.getAttribute("role_id");
                 int roleId = roleIdInt.intValue();

             HashMap weightScaleNamesHashMap =
                           aascUserControlDAO.getWeightScaleNames();
                       session.setAttribute("weightScaleNamesHashMap", weightScaleNamesHashMap); 
                       
                 if (roleId == 4) {
                     locationId =
                         (Integer)session.getAttribute("location_id"); // Gettin Inventory Org Id from session
                     clientId = (Integer)session.getAttribute("client_id");
                     sessionList = new LinkedList();
                     sessionList.add(locationId);  
                     sessionList.add(clientId);
                     AascProfileOptionsBean aascProfileOptionsBean= aascProfileOptionsDAO.getProfileOptionsBean(sessionList);                 
                     request.setAttribute("aascProfileOptionsBean",aascProfileOptionsBean);                       
                 }              
             }catch(Exception e){
                 logger.severe("Exception::"+e.getMessage());
                    return "FAILURE";
             }
                 return "SUCCESS";

    }
    public String getCarrierNames(HttpSession session) {

        AascCarrierConfigurationDAO aascCarrierConfigurationDAO;
        HashMap carrierNames;
        try {
            aascCarrierConfigurationDAO = 
                    new AascOracleCarrierConfigurationDAO();
            carrierNames = aascCarrierConfigurationDAO.getCarrierNames();
            session.setAttribute("carrierNames", carrierNames);

            HashMap carrierValuesHashMap = 
                aascCarrierConfigurationDAO.getCarrierCodeValues();
            session.setAttribute("carrierValuesHashMap", carrierValuesHashMap);

        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
        return "SUCCESS";
    }


    public String auditAction() {
        try {
            locationId = 
                    Integer.parseInt((String)request.getParameter("locationId"));


            session.setAttribute("respType", "AuditAction");
            session.setAttribute("location_id", locationId);


            sessionList.clear();
            sessionList.add(locationId);


        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            return "FAILURE";
        }
        return "SUCCESS";
    }
    
    
    public String shipmentAction(HttpSession session){
        logger.info("Inside shipmentAction");
        try{
            AascShipmentOrderInfo aascShipmentOrderInfo = new AascShipmentOrderInfo();
            LinkedList pkgsList = aascShipmentOrderInfo.getShipmentPackageInfo();
            LinkedList pkgLinkList = new LinkedList();

            ListIterator listingIterator = pkgsList.listIterator();
            int pkgCount = pkgsList.size();
            logger.info("pkgCount in index page= " + pkgCount);
            int pkgCtr = 0;
            while (listingIterator.hasNext()) {

                pkgCtr = pkgCtr + 1;
                AascShipmentPackageInfo pkgBean =
                    (AascShipmentPackageInfo)listingIterator.next();
                pkgBean.setPackageCount(String.valueOf(pkgCount));
                pkgBean.setPackageSequence(String.valueOf(pkgCtr));
                pkgLinkList.add(pkgBean);
            }

            aascShipmentOrderInfo.setShipmentPackageInfo(pkgLinkList);

            session.setAttribute("AascShipmentOrderInfo", aascShipmentOrderInfo);
            
            aascUserControlDAO = new AascOracleUserControlDAO();
            aascUserControlDAO.getCountryCodeDetails();
            Map countryNameHashMap = 
                new TreeMap((HashMap)aascUserControlDAO.getCountryNameDetails());
//                System.out.println("countryNameHashMap::::"+countryNameHashMap);
            session.setAttribute("countryNameHashMap", countryNameHashMap);
        }
        catch(Exception e){
             logger.severe("Exception::"+e.getMessage());
                return "FAILURE";
         }
                return "SUCCESS";
    }
    
    
    public String commonActionForMA(HttpSession session){
      logger.info("Inside commonActionForMA");
      this.session = session ;
      try{
        LinkedList harzardousMatIdList = new LinkedList();
        AascPackageDimensionInfo aascPackageDimensionMatIdInfo = null;
        if(aascDAOFactory == null)
            aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
        aascPackageDimensionDAO = aascDAOFactory.getPackageDimensionDAO();
        clientId = (Integer)session.getAttribute("client_id");
        logger.info("clientId : "+clientId);
        logger.info("locationId : "+locationId);        
        aascPackageDimensionMatIdInfo =
                aascPackageDimensionDAO.getHazardousMaterialIdInfo(clientId);
        harzardousMatIdList =
                aascPackageDimensionMatIdInfo.getHazardousMatIdDetails();
        //logger.info("harzardousMatIdList : "+harzardousMatIdList);
        session.setAttribute("harzardousMatIdList", (Object)harzardousMatIdList);
        
        HashMap hashMap = null;

        HashMap DryIceUnits = null;

        aascShipMethodDAO = aascDAOFactory.getAascShipMethodDAO(); 
        hashMap = aascShipMethodDAO.getLookUpValues(100, "PK", "");

        session.setAttribute("UpsPkging", hashMap);

        hashMap = aascShipMethodDAO.getLookUpDetails("UPS_COD_CODE");

        session.setAttribute("UpsCodCode", hashMap);

        hashMap = aascShipMethodDAO.getLookUpDetails("UPS_COD_FUNDS_CODE");

        session.setAttribute("UpsFundsCodCode", hashMap);

        hashMap = aascShipMethodDAO.getLookUpDetails("UPS_DCIS_TYPE");

        session.setAttribute("UpsDcisType", hashMap);

        hashMap =
                 aascShipMethodDAO.getLookUpDetails("UPS_CODPAYMENTMETHOD");

        session.setAttribute("UpsCodPaymentMethod", hashMap);

        //DryIceUnits = aascShipMethodDAO.getLookUpValueDetails("PKG_WT", 0);   // Changed dao method call as below Y Pradeep
            
        DryIceUnits = aascShipMethodDAO.getLookUpValues(0, "PKG_WT", "");            

        session.setAttribute("DryIceUnits", DryIceUnits);
           
        }catch(Exception e){
            logger.severe("Exception::"+e.getMessage());
               return "FAILURE";
        }
        logger.info("commonActionForMA Exited");
            return "SUCCESS";

    }
    /**

     * getUserDetails Method calls aascOracleUserControlDAO
                          to get user details.
     * @param session 

     * @return String
     
     */
    public String getUserDetails(HttpSession session) {

        try {
            LinkedList userList = new LinkedList();
            int clientId = 
                Integer.parseInt(session.getAttribute("client_id").toString());
            AascUserControlDAO aascUserControlDAO = 
                new AascOracleUserControlDAO();
            userList = aascUserControlDAO.getAllUserDetails(clientId);
            session.setAttribute("userDetailsList", userList);
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILURE";
        }
        return "SUCCESS";
    }


    public String getCustomerLocationDetails(HttpSession session) {
        this.session = session; 
        try {
            LinkedList shipToCustomersList = new LinkedList();
            int clientId = 
                Integer.parseInt(session.getAttribute("client_id").toString());
            int locationId = 
                Integer.parseInt(session.getAttribute("location_id").toString());
            int userId = Integer.parseInt(session.getAttribute("user_id").toString());
            logger.info("clientId :::::" + clientId);
            logger.info("userId :::::" + userId);
            AascShipToLocationsDAO aascShipToLocationsDAO = 
                new AascOracleShipToLocationsDAO();

            shipToCustomersList = 
                    aascShipToLocationsDAO.getAllCustomerLocationDetails(clientId, userId);

            session.setAttribute("shipToCustomersList", shipToCustomersList);

            AascCarrierConfigurationDAO aascCarrierConfigurationDAO = 
                new AascOracleCarrierConfigurationDAO();

            HashMap carrierValuesHashMap = 
                aascCarrierConfigurationDAO.getCarrierCodeValues();
            session.setAttribute("carrierValuesHashMap", carrierValuesHashMap);


            logger.info("carrierValuesHashMap :::::" + 
                               carrierValuesHashMap);

            HashMap<String, String> carrierCodes = 
                new HashMap<String, String>();
            carrierCodes.putAll(carrierValuesHashMap);
            session.setAttribute("carrierCodes", carrierCodes);
            session.removeAttribute("aascShipToLocationsInfo");
            
   
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILURE";
        }
        return "SUCCESS";
    }
    
    public String getPackageDimensionDetails(HttpSession session) { 
    try{
    AascDAOFactory aascDAOFactory = new AascOracleDAOFactory();
    LinkedList unitDetailsList = new LinkedList();
    aascPackageDimensionDAO = aascDAOFactory.getPackageDimensionDAO();
    AascPackageDimensionInfo aascPackageDimensionUnitInfo = null;
    aascPackageDimensionUnitInfo = 
            aascPackageDimensionDAO.getUnitsInfo();
    unitDetailsList = aascPackageDimensionUnitInfo.getUnitDetails();
    logger.info("the unitDetailsList value is"+unitDetailsList);
    session.setAttribute("unitList", (Object)unitDetailsList);
        int roleId = ((Integer)session.getAttribute("role_id"));
        logger.info("the role Id is::::::::"+roleId);
        if (roleId == 4) {
           locationId =((Integer)session.getAttribute("location_id")).intValue();
           AascPackageDimensionInfo aascPackageDimensionInfo = null;
           logger.info("locationId value is"+locationId);
           if (locationId != 0) {
              aascPackageDimensionInfo =
                   aascPackageDimensionDAO.getPackageDimensionInfo(locationId,
                                                                   roleId,clientId);//Sunanda modified by removing unused variable type and added roleId for bug fix #2703
                 aascPackageDimensionInfo.setLocationId(locationId);
              
           } 
         
           else {
              logger.info("Inside else block");
              aascPackageDimensionInfo = new AascPackageDimensionInfo();
              aascPackageDimensionInfo.setLocationId(0);
           }
           

             session.setAttribute("aascPackageDimensionInfo",
                                           aascPackageDimensionInfo);
        }
        else{
            session.removeAttribute("aascPackageDimensionInfo");
        }
       
    
    } catch (Exception e) {
        e.printStackTrace();
        return "FAILURE";
    }
    
    return "SUCCESS";
    }
    
    

        /** This method loads Look Up Details for the provided lookupCode
         * @param lookupCode String
         * @return hm hashmap
         */
        public HashMap getLookUpDetails(String lookupCode) {
            
            logger.info("Entered getLookUpDetails()");
            HashMap hm=new HashMap();
            AascOracleDAOFactory connectionFactory = 
                new AascOracleDAOFactory(); // for creating connection
                 SimpleJdbcCall simpleJdbcCall;
            int opStatus = 0;
            String errorStatus = "";
            try{
                DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
                
                 SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_lookup_type",lookupCode);
                            
                     //       setQueryTimeout(100);
                           
                         simpleJdbcCall = new SimpleJdbcCall(datasource)
                                        .withCatalogName("AASC_ERP_SHIP_CONSOLE_PKG")
                                        .withProcedureName("get_lookup_details")
                                        .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                        .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                        .declareParameters(new SqlOutParameter("aasc_get_lookup_details", OracleTypes.CURSOR));
                
                Map<String,Object> out = simpleJdbcCall.execute(inputparams);
                
    //            logger.info("After Execute");
    //            logger.info("out ::: "+out.toString());
                opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));          
                errorStatus =  String.valueOf(out.get("op_error_status"));   
                Iterator it= ((ArrayList)out.get("OP_LOOKUP_DETAILS")).iterator();
                HashMap<String,?>  map1 = null;
                while(it.hasNext()){
                    map1 =(HashMap<String,?>)it.next();
                 
    //             logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
                    
                    hm.put(map1.get("LOOKUP_CODE"),map1.get("MEANING"));
                    map1.clear();;
                }
    //            logger.info("carier codes values :::"+hm);
                    
            } catch(Exception e){
                logger.severe("Exception e : "+e.getMessage());
            }
            logger.info("exit from getLookUpDetails()");
            
            return hm;


        } // end of getLookUpDetails()

    public void getPricingDetails(HttpServletRequest request, HttpSession session){
        logger.info("Inside getPricingDetails()");
        aascUserControlDAO = new AascOracleUserControlDAO();
        
        AascDAOFactory aascDAOFactory = new AascOracleDAOFactory();
        AascSubscriptionDetailsDAO aascSubscriptionDetailsDAO = aascDAOFactory.getAascSubscriptionDetailsDAO();
        aascSubscriptionDetailsBean = aascSubscriptionDetailsDAO.subscriptionDetails((Integer)session.getAttribute("client_id"));
        
        session.setAttribute("aascSubscriptionDetailsBean",aascSubscriptionDetailsBean);
        
        LinkedList customerType = aascUserControlDAO.getPricingDetails("CUSTOMER_TYPE","","");
        Iterator it = customerType.iterator();
        HashMap customerTypeHM = new HashMap();
        while(it.hasNext()){
            String code = (String)it.next();
            customerTypeHM.put(code,code);
        }
        session.setAttribute("customerType",(Object)customerTypeHM);
        
        LinkedList transactionRange = aascUserControlDAO.getPricingDetails(aascSubscriptionDetailsBean.getCustomerType(),"TRANSACTION_RANGE","");
        it = transactionRange.iterator();
        HashMap transactionRangeHM = new HashMap();
        while(it.hasNext()){
            String code = (String)it.next();
            transactionRangeHM.put(code,code);
        }
        LinkedList durationType = aascUserControlDAO.getPricingDetails("","","DURATION_TYPE");
        it = durationType.iterator();
        HashMap durationTypeHM = new HashMap();
        while(it.hasNext()){
            String code = (String)it.next();
            durationTypeHM.put(code,code);
        }
        logger.info("customer type::::::"+customerType);
        logger.info("durationType::::::"+durationType);
        
        session.setAttribute("durationType",(Object)durationTypeHM);
        session.setAttribute("transactionRange",(Object)transactionRangeHM);
        
        LinkedList ratesList = aascSubscriptionDetailsDAO.getRateDetails();
     //   System.out.println("list from db:::"+ratesList);
        session.setAttribute("ratesList",(Object)ratesList);
    }
}
