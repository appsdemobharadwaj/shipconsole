    package com.aasc.erp.model;
    
    /**
     * AascOracleUserControlDAO is a class which interacts with the database and 
             corresponding bean file for retriving Details.
     * @author 	N Srisha
     * @version 1.0
     * @since
     * 24/07/2014   Suman Gunda     Added getAllCustomerDetails() method for create customer
     * 25/07/2014   Suman Gunda     Added getCountryCodeDetails() and getCountryNameDetails() methods for getting countries list.
     * 25/07/2014   Suman Gunda     Added saveCustomerDetails() method for saving details either create or update customers
     * 25/07/2014   N Srisha        Added createOrUpdateUser(), editUserProfile(), getAllUserDetails() method for saving details to create,update and get Userdetails
     * 17/12/2014   Eshwari M       Merged Sunanda code for SC Lite
     * 31/12/2014   Eshwari M       Renamed AASC_ERP_USER_CONTROL to AASC_ERP_USER_CONTROL_PKG
     * 06/01/2015   Y Pradeep       Added code for version number in userAuthentication method.
     * 07/01/2015   Eshwari M       Modified userAuthentication method to get the firstName and lastName of the user logged in to display in the index page.
     * 13/01/2015   Y Pradeep       Added nullStrToSpc to version number, first name, last name and modified if condition in nullStrToSpc method to check object is null.
     * 14/01/2015   Suman G         Modified from OP_CLOUD_LABEL_PATH to op_cloud_label_path for saving labels.
     * 20/01/2015  K Sunanda        Removed unused variables and unnecessary loggers
     * 21/01/2015  K Sunanda        Modified code for bug fix #2571
     * 04/02/2015  Suman G          Added forgotPassword() method for Forgot Password functionality.
     * 04/02/2015  Suman G          Added changePassword() method for Change Password functionality.
     * 09/03/2015  K Sunanda        Modified code to remove profile Option related code in createcustomer functionality
     * 16/03/2015  Eshwari M        Added editUserProfile() for EditProfile feature
     * 16/03/2015   Suman G         Modified code of createOrUpdateUser() method for send mail to Alternate Email Address.
     * 21/04/2015   Suman G        Added email Address to fix #2754
     * 18/05/2015   Y Pradeep       Modified code alignment.
     * 03/06/2015   Y Pradeep       Added getWeightScaleNames() method for Weighing Scale details.
     * 10/06/2015   Suman G         Added code to fix #2962
     * 24/08/2015   Jagadish        Added code to get and save pricing details.
     * 31/08/2015   Suman G         Added code for Trial User Changes
     * 21/10/2015   Suman G         Removed unused fields from the table.
     * 28/10/2015   Suman G         Added code to implement Transaction count functionality for Update Customer.
     * 24/11/2015   Y Pradeep       Added code to get locationName in getAllUserDetails method. Bug #4025.
     * 04/12/2015   Suman G         Added code for resending the password functionality to user.
     * 24/02/2016   Suman G         Added methods for new Transaction Management design.
     **/
    
    import com.aasc.erp.bean.AascCustomerBean;

    import com.aasc.erp.bean.AascPackageDimensionInfo;
    import com.aasc.erp.bean.AascUserBean;
    import com.aasc.erp.util.AascLogger;

	import com.aasc.erp.util.TripleDES;
    
    
    
    import java.math.BigDecimal;
    
    import java.sql.CallableStatement;
    
    import java.sql.Connection;
    
    import java.sql.Timestamp;
    import java.sql.Types;
    
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Iterator;
    import java.util.LinkedList;
    import java.util.Map;
    import java.util.logging.Logger;
    
    import javax.sql.DataSource;
    
    
    import oracle.jdbc.OracleTypes;
    
    import org.springframework.jdbc.core.SqlOutParameter;
    import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
    import org.springframework.jdbc.core.namedparam.SqlParameterSource;
    import org.springframework.jdbc.core.simple.SimpleJdbcCall;
    import org.springframework.jdbc.object.StoredProcedure;
    
   
    public class
    
    AascOracleUserControlDAO extends StoredProcedure implements AascUserControlDAO {
        private static Logger logger = 
            AascLogger.getLogger("AascUserControlDelegate");
        SimpleJdbcCall simpleJdbcCall;
    
        AascOracleDAOFactory connectionFactory = 
            new AascOracleDAOFactory(); // creating the object for AascPackageInfo
        Connection connection = 
            null; // connection object which connects to data base
        CallableStatement callableStatement, // for login details
        getCustomerCallableStatement, countryCodeCallableStatement, saveCustomerCallableStatement, // for Customer and country deatils
        saveUserCallableStatement, editUserProfileCallableStatement, getUserCallableStatement; // for User details
        HashMap userHashMap = new HashMap(); // Hashmap for storing user details
        private LinkedList userList; // userList added for storing user list
        private String status = "";
        private String emailAddress;
        private String alternateEmailAddress;
        private String firstName;
        private String lastName;
        private String userName;
        private String password;
        private int locationId;
        private int role;
        private int opStatus;
        
        // Variables for create customers are ended
    
        LinkedList countryCodelist; // Created for storing country list.
        
        HashMap countryNameHashmap = 
            new HashMap(); // Hash map for storing country names
    
    
        /**
    
         * This Method interacts with the database 
                    and retrieves User Details.
    
         * @param userName String and password String 
    
         * @return HashMap 
    
         */
        public HashMap userAuthentication(String userName, String password) {
    
            logger.info("Entered userAuthentication() in AascOracleUserControlDAO");
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            String encryptedPassword = "";
            try {
                TripleDES tripleDES = new TripleDES();
                encryptedPassword = tripleDES.encrypt(password);
                logger.info("Entered TripleDES");
            } catch (Exception e) {
                logger.severe("Excetpion:"+e.getMessage());
            }
    
            try {
                SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_USER_NAME", userName)
                                                                            .addValue("ip_password", encryptedPassword);
                logger.info("SimpleJDBCCall creation"+ datasource.toString());
                simpleJdbcCall = 
                        new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                      .withProcedureName("login_authentication")
                                                      .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                                      .declareParameters(new SqlOutParameter("op_role", Types.INTEGER))
                                                      .declareParameters(new SqlOutParameter("op_user_id", Types.INTEGER))
                                                      .declareParameters(new SqlOutParameter("op_location_id", Types.INTEGER))
                                                      .declareParameters(new SqlOutParameter("op_client_id", Types.INTEGER))
                                                      .declareParameters(new SqlOutParameter("op_version", Types.VARCHAR))
                                                      .declareParameters(new SqlOutParameter("op_cloud_label_path", Types.VARCHAR))
                                                      .declareParameters(new SqlOutParameter("op_first_name", Types.VARCHAR))
                                                      .declareParameters(new SqlOutParameter("op_last_name", Types.VARCHAR))
                                                      .declareParameters(new SqlOutParameter("op_email_address", Types.VARCHAR))
                                                      .declareParameters(new SqlOutParameter("op_trial_status",Types.VARCHAR))
                                                      .declareParameters(new SqlOutParameter("OP_CLIENT_MAIL",Types.VARCHAR))
                                                      .declareParameters(new SqlOutParameter("OP_ADDRESS_BOOK_LEVEL",Types.VARCHAR));
                logger.info("entered simpleJDBCCall Executing");
                Map<String, Object> out = simpleJdbcCall.execute(inputparams);
                logger.info("completed simpleJDBCCall Executing \n"+out);
                if (out.get("op_status") != null) {
                    opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
    
                    if (opStatus == 1) {
    
                        
                        int role_id = 
                            Integer.parseInt(String.valueOf(out.get("op_role")));
                        int user_id = 
                            Integer.parseInt(String.valueOf(out.get("op_user_id"))); // placing the output status into status variable  
                        int location_id = 0;
                        if (out.get("op_location_id") != null) {
                            location_id = 
                                    Integer.parseInt(String.valueOf(out.get("op_location_id")));
                        }
                        String cloudLabelPath = 
                            String.valueOf(out.get("op_cloud_label_path"));
                        int client_id = 
                            Integer.parseInt(String.valueOf(out.get("op_client_id")));
                        
                        String version = nullStrToSpc(String.valueOf(out.get("op_version")));
                        String firstName = nullStrToSpc(String.valueOf(out.get("op_first_name")));
                        String lastName = nullStrToSpc(String.valueOf(out.get("op_last_name")));
                        String emailAddress = nullStrToSpc(String.valueOf(out.get("op_email_address")));
                        String trialStatus = nullStrToSpc(String.valueOf(out.get("op_trial_status")));
                        String clientEmailId = nullStrToSpc(String.valueOf(out.get("OP_CLIENT_MAIL")));
                        String addressBookLevel = nullStrToSpc(String.valueOf(out.get("OP_ADDRESS_BOOK_LEVEL")));
                        logger.info("addressBookLevel === "+addressBookLevel);
                        userHashMap.put("status", "success");
                        userHashMap.put("role_id", role_id);
                        userHashMap.put("user_id", user_id);
                        userHashMap.put("location_id", location_id);
                        userHashMap.put("client_id", client_id);
                        userHashMap.put("cloudLabelPath", cloudLabelPath);
                        userHashMap.put("version", version);
                        userHashMap.put("firstName", firstName);
                        userHashMap.put("lastName", lastName);
                        userHashMap.put("emailAddress",emailAddress);
                        userHashMap.put("trialStatus",trialStatus);
                        userHashMap.put("clientMailId",clientEmailId);
                        userHashMap.put("addressBookLevel",addressBookLevel);
                        return userHashMap;
                    } else if(opStatus == 2){
                        userHashMap.put("status", "expired");
                        return userHashMap;
                    } else {
                        userHashMap.put("status", "failure");
                        return userHashMap;
                    }
                }
    
            } catch (Exception e) {
//                e.printStackTrace();
            	logger.info(e.getMessage());
                userHashMap.put("status", "failure");
                return userHashMap;
            }
    
            userHashMap.put("status", "failure");
            return userHashMap;
            
        }
    
        /**
    
         * This Method interacts with the database 
                    and retrieves customers Details.
    
         * @return LinkedList 
    
         */
        public LinkedList getAllCustomerDetails() {
     
            LinkedList customerList = null;
    
            
            try {
                DataSource datasource = 
                    connectionFactory.createDataSource(); // this method returns the datasources object
                simpleJdbcCall = 
                        new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                      .withProcedureName("get_customer_details")
                                                      .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                      .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                      .declareParameters(new SqlOutParameter("aasc_customer_detail", OracleTypes.CURSOR));
                Map<String, Object> out = simpleJdbcCall.execute();
                
                
                int opStatus = 
                    Integer.parseInt(String.valueOf(out.get("op_status")));
                String errorStatus = String.valueOf(out.get("op_error_status"));
                Iterator it = 
                    ((ArrayList)out.get("OP_CUSTOMER_DETAILS")).iterator();
                TripleDES tripleDES = null;
                try {
                    tripleDES = new TripleDES();
                } catch (Exception e) {
                    logger.severe(e.getMessage());
                }
                AascCustomerBean aascCustomerBean = null;
    
                customerList = new LinkedList();
                HashMap<String, ?> map1 = null;
                while (it.hasNext()) {
                    map1 = (HashMap<String, ?>)it.next();
                    aascCustomerBean = new AascCustomerBean();
                    
    
                    aascCustomerBean.setClientID(((BigDecimal)map1.get("CLIENT_ID")).intValue());
    
                    aascCustomerBean.setCompanyName((String)map1.get("COMPANY_NAME"));
                    aascCustomerBean.setContactName((String)map1.get("CONTACT_NAME"));
                    aascCustomerBean.setPhoneNumber((String)map1.get("CONTACT_PHONENUMBER"));
                    aascCustomerBean.setAddressLine1((String)map1.get("ADDRESSLINE1"));
                    aascCustomerBean.setAddressLine2((String)map1.get("ADDRESSLINE2"));
                    aascCustomerBean.setCity((String)map1.get("CITY"));
                    aascCustomerBean.setState((String)map1.get("STATE"));
                    aascCustomerBean.setCountryCode((String)map1.get("COUNTRY"));
                    aascCustomerBean.setPostalCode((String)map1.get("POSTALCODE"));
                    aascCustomerBean.setEmailAddress((String)map1.get("EMAIL_ID"));
                    
                    aascCustomerBean.setCloudLabelPath((String)map1.get("CLOUD_LABEL_PATH"));
                    aascCustomerBean.setStatus((String)map1.get("STATUS"));
                    aascCustomerBean.setFirstName((String)map1.get("FIRST_NAME"));
                    aascCustomerBean.setLastName((String)map1.get("LAST_NAME"));
                    
                    aascCustomerBean.setCustomerType((String)map1.get("CUSTOMER_TYPE"));
                    aascCustomerBean.setInvoiceType((String)map1.get("INVOICE_TYPE"));
                    aascCustomerBean.setMonthlyEstimatedTransactionRange((String)map1.get("TRANSACTION_RANGE"));
                    aascCustomerBean.setPricingDuration((String)map1.get("PRICING_DURATION"));
                    
                    if (map1.get("START_DATE") != null) {
                        String startDate = 
                            nullStrToSpc(((Timestamp)map1.get("START_DATE")).toString());
                        String endDate = 
                            nullStrToSpc(((Timestamp)map1.get("SUBSCRIPTION_END_DATE")).toString());
                        
    
                        aascCustomerBean.setCustomerStartDate(dateConvert(startDate.substring(0, 
                                                                                             startDate.indexOf(' '))).toString());
                        aascCustomerBean.setSubscriptionEndDate((String)dateConvert(endDate.substring(0, 
                                                                                         endDate.indexOf(' '))).toString());
                    } else {
                        aascCustomerBean.setCustomerStartDate(null);
                        aascCustomerBean.setSubscriptionEndDate(null);
                    }
         //           System.out.println("start date:::::"+aascCustomerBean.getCustomerStartDate());
//                    aascCustomerBean.setCustomerStartDate((String)map1.get("START_DATE"));
//                    aascCustomerBean.setSubscriptionEndDate((String)map1.get("SUBSCRIPTION_END_DATE"));
//                    aascCustomerBean.setTotalFee(((BigDecimal)map1.get("TOTAL_FEE")).doubleValue());
                    if(map1.get("CUMULATIVE_PKG_COUNT") != null)
                        aascCustomerBean.setCumulativePackageCount(((BigDecimal)map1.get("CUMULATIVE_PKG_COUNT")).intValue());
                    else
                        aascCustomerBean.setCumulativePackageCount(0);
                    if(map1.get("CURRENT_PKG_BALANCE") != null)
                        aascCustomerBean.setCurrentPackageBalance(((BigDecimal)map1.get("CURRENT_PKG_BALANCE")).intValue());
                    else
                        aascCustomerBean.setCurrentPackageBalance(0);
                    aascCustomerBean.setSubscriptionExpiryFlag((String)map1.get("SUBSCRIPTION_EXPIRY_FLAG"));
        //            System.out.println("curent package balance::::"+aascCustomerBean.getCurrentPackageBalance());
//                    aascCustomerBean.setTransactionCount(((BigDecimal)map1.get("TRANSACTION_COUNT")).intValue());
                    customerList.add(aascCustomerBean);
                }
                
    
                logger.info("AASC_ERP_USER_CONTROL_PKG.get_customer_details opStatus:" + 
                            opStatus + "\t errorStatus:" + errorStatus);
            }
    
            catch (Exception e) {
                logger.severe("exception in getting customer details" + 
                              e.getMessage());
//                e.printStackTrace();
            }
    
            logger.info("Exit from getAllCustomerDetails()");
            return customerList;
        }
    
        /**
         * This method for converting an Object type into a String type.
         * @param obj
         * @return String
         */
        public String nullStrToSpc(Object obj) {
    
            String spcStr = "";
    
            if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
    
                return spcStr;
    
            } // closing the if block
            else {
    
                return obj.toString();
    
            } // closing the else block
    
        } // closing the nullStrToSpc method 
    
        /**
         * This method for getting countries codes as linked list.
         * @return LinnkedList contains country codes
         */
        public LinkedList getCountryCodeDetails() {
    
            logger.info("Entered getCountryCodeDetails()");
            LinkedList countryCodelist = null;
            AascPackageDimensionInfo aascPackageDimensionInfo = null;
            aascPackageDimensionInfo = new AascPackageDimensionInfo();
            String countryValue = null;
            String countryName = null;
            
            countryCodelist = new LinkedList();
            try {
                DataSource datasource = 
                    connectionFactory.createDataSource(); // this method returns the datasources object
                simpleJdbcCall = 
                        new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                      .withProcedureName("get_country_codes")
                                                      .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                      .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                      .declareParameters(new SqlOutParameter("aasc_country_codes", OracleTypes.CURSOR));
    
                Map<String, Object> out = simpleJdbcCall.execute();
    
                
                
                int opStatus = 
                    Integer.parseInt(String.valueOf(out.get("op_status")));
                String errorStatus = String.valueOf(out.get("op_error_status"));
                Iterator it = ((ArrayList)out.get("OP_COUNTRY_CODES")).iterator();
    
    
                TripleDES tripleDES = null;
                try {
                    tripleDES = new TripleDES();
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                countryCodelist = new LinkedList();
                while (it.hasNext()) {
                    HashMap<String, ?> map1 = (HashMap<String, ?>)it.next();
    
                        
                    countryValue = (String)map1.get("country_code");
                    countryCodelist.add(countryValue);
                    countryName = (String)map1.get("country_name");
                    countryNameHashmap.put(countryName, countryValue);
                }
    
    
                logger.info("AASC_ERP_USER_CONTROL_PKG.get_country_codes opStatus:" + 
                            opStatus + "\t errorStatus:" + errorStatus);
            }
    
            catch (Exception e) {
                logger.severe("exception while closing the countryCodeCallableStatement" + 
                              e);
                e.printStackTrace();
            }
            logger.info("Exit from getCountryCodeDetails()");
            return countryCodelist;
        }
        
        /**
    
         * This Method implements by the  AascOracleUserControlDAO class.
    
         * @return HashMap
    
         * It is used for getting country names
         
         */
    
        public HashMap getCountryNameDetails() {
            logger.info("Entered getCountryNameDetails()");
            return countryNameHashmap;
        }
        
        /**
    
         * This Method implements by the  AascOracleUserControlDAO class.
    
         * @return int
    
         * It is used for save customer details(either create or update)
         
         */
    
        public int saveCustomerDetails(AascCustomerBean aascCustomerInfo) {

                logger.info("Entered saveCustomerDetails()");

                try {

                    DataSource datasource = 
                        connectionFactory.createDataSource(); // this method returns the datasources object
                    String encryptedPassword = "";
//                    String encryptedAuthPassword = "";
                    try {
                        TripleDES tripleDES = new TripleDES();
                        encryptedPassword = 
                                tripleDES.encrypt(nullStrToSpc(aascCustomerInfo.getPassword()));
//                        encryptedAuthPassword = 
//                                tripleDES.encrypt(aascCustomerInfo.getAuthPassword());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                     int clientID = aascCustomerInfo.getClientID();
                if(clientID == 0){
                    aascCustomerInfo.setClientID(-1);
                }
                    int locID;
                    if (aascCustomerInfo.getClientID() == 0) {
                        locID = -1;
                    } else {
                        locID = aascCustomerInfo.getClientID();
                    }
                    
                 
                SqlParameterSource inputparams =    new MapSqlParameterSource().addValue("ip_company_name", nullStrToSpc(aascCustomerInfo.getCompanyName()))
                                                                               .addValue("ip_contact_name", nullStrToSpc(aascCustomerInfo.getContactName()))
                                                                               .addValue("ip_address_line1", nullStrToSpc(aascCustomerInfo.getAddressLine1()))
                                                                               .addValue("ip_address_line2", nullStrToSpc(aascCustomerInfo.getAddressLine2()))
                                                                               .addValue("ip_city", nullStrToSpc(aascCustomerInfo.getCity()))
                                                                               .addValue("ip_state", nullStrToSpc(aascCustomerInfo.getState()))
                                                                               .addValue("ip_postal_code", nullStrToSpc(aascCustomerInfo.getPostalCode()))
                                                                               .addValue("ip_country", nullStrToSpc(aascCustomerInfo.getCountryCode()))
                                                                               .addValue("ip_phone_number", nullStrToSpc(aascCustomerInfo.getPhoneNumber()))
                                                                               .addValue("ip_emailid", nullStrToSpc(aascCustomerInfo.getEmailAddress()))
                                                                                .addValue("ip_make_payment", nullStrToSpc(aascCustomerInfo.getMakePayment()))
                                                                                .addValue("ip_customer_type", nullStrToSpc(aascCustomerInfo.getCustomerType()))
                                                                                .addValue("ip_invoice_type", nullStrToSpc(aascCustomerInfo.getInvoiceType()))
                                                                                .addValue("ip_transaction_range", nullStrToSpc(aascCustomerInfo.getMonthlyEstimatedTransactionRange()))
                                                                                .addValue("ip_pricing_duration", nullStrToSpc(aascCustomerInfo.getPricingDuration()))
                                                                                .addValue("ip_start_date", nullStrToSpc(aascCustomerInfo.getCustomerStartDate()))
//                                                                                .addValue("ip_subscription_end_date", nullStrToSpc(aascCustomerInfo.getSubscriptionEndDate()))
                                                                                .addValue("ip_total_fee", nullStrToSpc(aascCustomerInfo.getTotalFee()))
                                                                                .addValue("ip_cumulative_pkg_count", nullStrToSpc(aascCustomerInfo.getCumulativePackageCount()))
                                                                                .addValue("ip_current_pkg_balance", nullStrToSpc(aascCustomerInfo.getCurrentPackageBalance()))
                                                                                .addValue("ip_transaction_count", nullStrToSpc(aascCustomerInfo.getTransactionCount()))
                                                                                
                                                                                .addValue("ip_client_id", nullStrToSpc(aascCustomerInfo.getClientID()))
                                                                                .addValue("IP_USERNAME", nullStrToSpc(aascCustomerInfo.getEmailAddress()))
                                                                                .addValue("IP_PASSWORD", encryptedPassword)
                                                                                
                                                                                .addValue("IP_FIRST_NAME", nullStrToSpc(aascCustomerInfo.getFirstName()))
                                                                                .addValue("ip_last_name", nullStrToSpc(aascCustomerInfo.getLastName()))
                                                                                .addValue("IP_CLOUD_LABEL_PATH", nullStrToSpc(aascCustomerInfo.getCloudLabelPath()).trim()) 
                                                                                .addValue("ip_status", nullStrToSpc(aascCustomerInfo.getStatus())) 
                                                                                .addValue("IP_ROLE_ID", 3) 
                                                                                .addValue("IP_LOCATION_ID", locationId) ;

                    simpleJdbcCall = 
                            new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                          .withProcedureName("save_customer_details")
                                                          .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                          .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
                    
                    Map<String, Object> out = simpleJdbcCall.execute(inputparams);
                    opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
                    String error_status = String.valueOf(out.get("op_error_status"));
                    logger.info("opStatus ::::::::" + opStatus + 
                                       " && error_status ::::::::::" + error_status);
                } catch (Exception e) {
                    logger.severe("exception while closing the countryCodeCallableStatement" + 
                                  e);
                                  e.printStackTrace();
                }

                logger.info("Exit from createCustomer()" + opStatus);
                return opStatus;
            }
    
    
        /**
    
         * This Method interacts with the database 
                   for creating and updating User Details.
    
         * @param aascUserBean object of  AascUserBean Class 
    
         * @return String 
    
         */
        public String createOrUpdateUser(AascUserBean aascUserBean) {
    
            Integer role=aascUserBean.getRole();
            if (role==3){
            int user=0;
            aascUserBean.setLocationId(user);
            }//Sunanda added code for bug fix 2571
            logger.info("Entered createOrUpdateUser()");
    
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
    
            String emailId = aascUserBean.getEmailAddress();

            firstName = aascUserBean.getFirstName();


            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_userName", emailId)
                                           .addValue("ip_password", aascUserBean.getPassword())
                                           .addValue("ip_roleID", aascUserBean.getRole())
                                           .addValue("ip_status", aascUserBean.getStatus())
                                           .addValue("ip_client_id", aascUserBean.getClientID())
                                           .addValue("ip_user_id", aascUserBean.getUserID())
                                           .addValue("ip_action_type", aascUserBean.getActionType())
                                           .addValue("IP_EMAIL_ID", aascUserBean.getEmailAddress())
                                           .addValue("IP_ALTERNATE_EMAIL_ID", aascUserBean.getAlternateEmailAddress())
                                           .addValue("IP_FIRST_NAME", aascUserBean.getFirstName())
                                           .addValue("ip_last_name", aascUserBean.getLastName())
                                           .addValue("IP_LOCATION_ID", aascUserBean.getLocationId())
                                           .addValue("IP_CREATED_BY_USER",aascUserBean.getCreatedByUser())
                                           .addValue("IP_ADDRESS_BOOK_LEVEL",aascUserBean.getAddressBookLevel());
    
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                           .withProcedureName("save_user_details")
                                                           .declareParameters(new SqlOutParameter("OP_PASSWORD", Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
            
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
            String encryptPassword = String.valueOf(out.get("OP_PASSWORD"));
            int status = Integer.parseInt(String.valueOf(out.get("op_status")));
            String error_status = String.valueOf(out.get("op_error_status"));
            logger.info("opStatus ::::::::" + opStatus + " && error_status ::::::::::" + error_status);
            String statusString = String.valueOf(out.get("op_status"));
            logger.info("status tring::::"+statusString);
            if(status == 4){
                try{
                    TripleDES decode = new TripleDES();
                    password = decode.decrypt(encryptPassword);
                    logger.info("Password after decrypt:::: "+password);
                    statusString = status + "$$$" +password;
                }catch(Exception e){
                    logger.severe("Exception ::::"+e.getMessage());
                }
            }                               
            
            return statusString;
        }
    
    
        /**
    
         * This Method interacts with the database 
                    for retreiving User details.
    
         * @param clientID int
    
         * @return LinkedList 
    
         */
        public LinkedList getAllUserDetails(int clientID) {
    
            logger.info("Entered getAllUserDetails()");
    
            logger.info("clientID :::" + clientID);
            userList = new LinkedList();
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
    
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_client_id", clientID);
    
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                           .withProcedureName("get_user_details")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("aasc_user_details", OracleTypes.CURSOR));
    
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
    
            
            
            int opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            String errorStatus = String.valueOf(out.get("op_error_status"));
            Iterator it = ((ArrayList)out.get("OP_USER_DETAILS")).iterator();
            TripleDES tripleDES = null;
            try {
                tripleDES = new TripleDES();
            } catch (Exception e) {
                logger.severe("Exception:"+e.getMessage());
            }
    
            while (it.hasNext()) {
                HashMap<String, ?> map1 = (HashMap<String, ?>)it.next();
    
                AascUserBean aascUserBean = new AascUserBean();
    
                aascUserBean.setUserID(((BigDecimal)map1.get("USER_ID")).intValue());
    
                userName = nullStrToSpc((String)map1.get("USER_NAME"));
                password = nullStrToSpc((String)map1.get("PASSWORD"));
                role = ((BigDecimal)map1.get("ROLE_ID")).intValue();
                status = nullStrToSpc((String)map1.get("STATUS"));
                clientID = ((BigDecimal)map1.get("CLIENT_ID")).intValue();
    
                emailAddress = nullStrToSpc((String)map1.get("EMAIL_ID"));
    
                firstName = nullStrToSpc((String)map1.get("FIRST_NAME"));
                lastName = nullStrToSpc((String)map1.get("LAST_NAME"));
             
                
    
                if (map1.get("LOCATION_ID") != null)
                    locationId = ((BigDecimal)map1.get("LOCATION_ID")).intValue();
                else
                    locationId = 0;
                alternateEmailAddress = 
                        nullStrToSpc((String)map1.get("ALTERNATE_EMAIL_ID"));
                aascUserBean.setLocationName(nullStrToSpc((String)map1.get("LOCATION_NAME")));
                aascUserBean.setUserName(userName);
                aascUserBean.setPassword(tripleDES.decrypt(password));
                aascUserBean.setRole(role);
                aascUserBean.setLocationId(locationId);
                aascUserBean.setStatus(status);
                aascUserBean.setClientID(clientID);
                aascUserBean.setEmailAddress(emailAddress);
                aascUserBean.setAlternateEmailAddress(alternateEmailAddress);
                aascUserBean.setFirstName(firstName);
                aascUserBean.setLastName(lastName);
                aascUserBean.setAddressBookLevel((String)map1.get("ADDRESS_BOOK_LEVEL"));
                userList.add(aascUserBean);
            }
            logger.info("AASC_ERP_USER_CONTROL_PKG.get_user_details opStatus:" + 
                        opStatus + "\t errorStatus:" + errorStatus);
            logger.info("Exit from getAllUserDetails()");
            return userList;
    
        }
    
        /**
        
         * This Method converts String to java.sql.Date type.
        
         * @param date String
        
         * @return java.sql.Date 
        
     */
        private java.sql.Date dateConvert(String date) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            
    
            try {
    
                java.util.Date date1 = formatter.parse(date);
                java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
                
                return sqlDate;
    
            } catch (ParseException e) {
    
                e.printStackTrace();
            }
            return null;
        }
    
    /**

     * This Method interacts with data base to change password.
     * @param userName String type
     * @param oldPassword String type
     * @param newPassword String type
     * @return LinkedList
     * It is used for change the password and returns the status of change.
     
     */
     
    public int changePassword(String userName, String oldPassword, String newPassword){
        logger.info("Entered changePassword()");
        DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
        String encryptedOldPassword = "";
        String encryptedNewPassword = "";
        try {
            TripleDES tripleDES = new TripleDES();
            encryptedOldPassword = tripleDES.encrypt(oldPassword);
            encryptedNewPassword = tripleDES.encrypt(newPassword);
        } catch (Exception e) {
            logger.severe("Exception:"+e.getMessage());
        }
        SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_USER_NAME",  userName)
                                                                    .addValue("IP_NEW_PASSWORD", encryptedNewPassword)
                                                                    .addValue("IP_OLD_PASSWORD", encryptedOldPassword);
        simpleJdbcCall = new SimpleJdbcCall(datasource) .withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                        .withProcedureName("change_password")
                                                        .declareParameters(new SqlOutParameter("OP_STATUS",Types.INTEGER))
                                                        .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
        Map<String, Object> out = simpleJdbcCall.execute(inputparams); 
        logger.info("after change password:"+out);
        int status = 0;
        status = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));;
        String errorStatus = String.valueOf(out.get("OP_ERROR_STATUS"));
        logger.info("Status:"+status+"error Status:"+errorStatus);
        
        
        logger.info("End of changePassword()");
        return status;
    }

    /**

     * This Method interacts with data base to update new password.
     * @param userName String type
     * @return int
     * It is used for getting the password if user forgot password.
     
     */
     
    public int forgotPassword(String userName, String password) {
        logger.info("Entered forgotPassword()");
        DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
        SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_USER_NAME",  userName)
                                                                    .addValue("IP_PASSWORD", password);
                                                                    
        simpleJdbcCall = new SimpleJdbcCall(datasource) .withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                        .withProcedureName("forgot_password")
                                                        .declareParameters(new SqlOutParameter("OP_STATUS",Types.INTEGER))
                                                        .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
        Map<String, Object> out = simpleJdbcCall.execute(inputparams); 
        
        int status = 0;
        status = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));;
        String errorStatus = String.valueOf(out.get("OP_ERROR_STATUS"));
        logger.info("Status:"+status+"error Status:"+errorStatus);
        
        
        logger.info("End of forgotPassword()");
        return status;
        
    }

    /**
     * This Method implements by the  AascOracleUserControlDAO class.
     * @param 
     * @return int
     * It is used for updating the use profile details in database
     */   
    public int editUserProfile(AascUserBean aascUserInfo){
    
        logger.info("Entered editUserProfile()");
        DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
        SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id",  aascUserInfo.getClientID())
                                                                    .addValue("ip_user_id", aascUserInfo.getUserID())
              .addValue("IP_EMAIL_ID", aascUserInfo.getEmailAddress())
              .addValue("IP_FIRST_NAME", aascUserInfo.getFirstName())
              .addValue("ip_last_name", aascUserInfo.getLastName());
        simpleJdbcCall = new SimpleJdbcCall(datasource) .withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                        .withProcedureName("edit_user_profile")
                                                        .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                                        .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
        Map<String, Object> out = simpleJdbcCall.execute(inputparams); 
        
        //logger.info("out : "+out);
        int status = 0;
        status = Integer.parseInt(String.valueOf(out.get("op_status")));;
        String errorStatus = String.valueOf(out.get("op_error_status"));
        logger.info("Status:"+status+"error Status:"+errorStatus);
        
        
        logger.info("End of editUserProfile()");
        return status;
    }
    
    
    /**
     * This Method interacts with the database and retrieves weighingscale names.
     * @return HashMap 
     */
    public HashMap getWeightScaleNames() {
    
        logger.info("Entered getWeightScaleNames()");
        DataSource datasource = 
            connectionFactory.createDataSource(); // this method returns the datasources object
        
             HashMap weightScaleNames = new HashMap(); // Hashmap for storing weighingscale names
    
        try {
                
            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                  .withProcedureName("get_weight_scale_names")
                                                  .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                  .declareParameters(new SqlOutParameter("aasc_weightscale_names", OracleTypes.CURSOR));
    
            Map<String, Object> out = simpleJdbcCall.execute();
    
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            String errorStatus = String.valueOf(out.get("op_error_status"));
            
            logger.info("AASC_USER_CONTROL.get_weight_scale_names opStatus:" + 
                        opStatus + "\t errorStatus:" + errorStatus);
                
            Iterator it= ((ArrayList)out.get("OP_WEIGHT_SCALE_NAMES")).iterator();
            HashMap<String,?>  map1 = null;
            while(it.hasNext()){
                map1 =(HashMap<String,?>)it.next();
                weightScaleNames.put(map1.get("NAME"),map1.get("NAME"));
                map1.clear();
            }                
    
        } catch (Exception e) {
            logger.severe("Exception in getWeightScaleNames method == "+e.getMessage() );
        }
    
        return weightScaleNames;
        
    }
    
    /**
     * This Method implements by the  AascOracleUserControlDAO class.
     * @param 
     * @return int
     * It is used for resending user 
     */
    public String resendPassword(AascUserBean aascUserInfo) {
        logger.info("Entered resendPassword()");
        DataSource datasource = 
            connectionFactory.createDataSource(); // this method returns the datasources object
        
        String password = "";
        System.out.println("user id:::"+aascUserInfo.getUserID());
        try {
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_user_id", aascUserInfo.getUserID());
            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                  .withProcedureName("get_password")
                                                  .declareParameters(new SqlOutParameter("op_password", Types.VARCHAR))
                                                  .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
        
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            String errorStatus = String.valueOf(out.get("op_error_status"));
            
            logger.info("AASC_USER_CONTROL.get_password opStatus:" + 
                        opStatus + "\t errorStatus:" + errorStatus);
            
            password = String.valueOf(out.get("op_password"));
            
            try{
                password = new TripleDES().decrypt(password);
            }catch(Exception e){
                password = "";
                logger.info("Exception in get password::::"+e.getMessage());
            }
        
        } catch (Exception e) {
            logger.severe("Exception in resendPassword method == "+e.getMessage() );
            e.printStackTrace();
        }
        return password;
    }
            
 
    /**
    
     * This Method interacts with the database 
                and retrieves monthly carrier Details to disply graph.
    
     * @param client_id String and location_id String 
    
     * @return HashMap 
    
     */
    public ArrayList getMonthlyCarrierDetails(int client_id, int location_id) {
    
        logger.info("Entered getMonthlyCarrierDetails() in AascOracleUserControlDAO");
        logger.info("client_id:: "+client_id+"location_id:: "+location_id);
        DataSource datasource = 
            connectionFactory.createDataSource(); // this method returns the datasources object

         ArrayList ordersList = null;
        try {
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id", client_id)
                                                                        .addValue("ip_location_id", location_id);
    
            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                  .withProcedureName("GET_MONTHLY_CARRIER_DETAILS")
                                                  .declareParameters(new SqlOutParameter("aasc_monthly_carrier_details", OracleTypes.CURSOR))
//                                                  .declareParameters(new SqlOutParameter("op_shipments_count", Types.INTEGER))
//                                                  .declareParameters(new SqlOutParameter("op_total_freight_charges", Types.INTEGER))
//                                                  .declareParameters(new SqlOutParameter("op_carrier_code", Types.INTEGER))
//                                                  .declareParameters(new SqlOutParameter("op_carrier_name", Types.VARCHAR))
                                                  .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
    
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            if (out.get("op_status") != null) {
                int opStatus = 
                        Integer.parseInt(String.valueOf(out.get("op_status"))); // placing the output status into status variable  
                String error_status = 
                             String.valueOf(out.get("op_error_status"));  
    
                if (opStatus == 1) {
                     ordersList = (ArrayList)out.get("OP_MONTHLY_CARRIER_DETAILS");
//                                 logger.info("Array List map values ::::::::::::::::" + ordersList.toString());
                    Iterator ordersIterator = ordersList.iterator();
                    
                    HashMap<String, ?> resulthm = null;

                    return ordersList;
                }  
                
            }
    
        } catch (Exception e) {
            e.printStackTrace();
//            carrierHashMap.put("status", "failure");
//            return carrierHashMap;
        }
    
//        carrierHashMap.put("status", "failure");
        return ordersList;
        
    }
    
    /**
    
     * This Method interacts with the database 
                and retrieves Pricing Details
    
     * @param customerType String, transactionRange String and durationType String 
    
     * @return LinkedList 
    
     */
    public LinkedList getPricingDetails(String customerType, String transactionRange, String durationType){
        logger.info("Entered getPricingDetails");
        logger.info("customerType ::::::::"+ customerType +" && transactionRange:::::"+transactionRange +" && duration:::123::"+durationType);
//        HashMap hm=new HashMap();
        LinkedList ll = new LinkedList();        
            try{
            DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
             SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_CUSTOMER_TYPE",customerType)
                                                                         .addValue("IP_TRANSACTION_RANGE",transactionRange)
                                                                         .addValue("IP_DURATION_TYPE",durationType); 
                       
                     simpleJdbcCall = new SimpleJdbcCall(datasource)
                                    .withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                    .withProcedureName("GET_PRICING_DETAILS")
                                    .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                    .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                    .declareParameters(new SqlOutParameter("aasc_get_pricing_details", OracleTypes.CURSOR));
            
            Map<String,Object> out = simpleJdbcCall.execute(inputparams);
            
            
        //            logger.info("After Execute");
        //            logger.info("out ::: "+out.toString());
            opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));          
            String errorMessage =  String.valueOf(out.get("OP_ERROR_MESSAGE"));   
            logger.info("opstatus:::::"+opStatus+"::::::error message::::"+errorMessage);
            Iterator it= ((ArrayList)out.get("OP_PRICING_DETAILS")).iterator();
            HashMap<String,?>  map1 = null;
            while(it.hasNext()){
                map1 =(HashMap<String,?>)it.next();
             
        //             logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
         ll.add(map1.get("LOOKUP_VALUE"));
//                hm.put(map1.get("LOOKUP_VALUE"),map1.get("LOOKUP_VALUE")); //map1.get("MEANING"));
                map1.clear();
            }
        //            logger.info("carier codes values :::"+hm);
            
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exist from getPricingDetails");
        
        return ll;
    }
    
    /**
    
     * This Method interacts with the database and retrieves Transaction Ranges
    
     * @param customerType String 
    
     * @return String 
    
     */
    public String getTransactionRange(String customerType){
        logger.info("Entered getTransactionRange()");
        DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
        SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_CUSTOMER_TYPE",  userName)
                                                                    .addValue("IP_LOOKUP_TYPE", "TRANSACTION_RANGE");
                                                                    
        simpleJdbcCall = new SimpleJdbcCall(datasource) .withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                        .withProcedureName("GET_TRANSACTION_RANGE")
                                                        .declareParameters(new SqlOutParameter("OP_STATUS",Types.INTEGER))
                                                        .declareParameters(new SqlOutParameter("OP_ERROR_MESSAGE", Types.VARCHAR))
                                                        .declareParameters(new SqlOutParameter("AASC_TRANSACTION_RANGE", OracleTypes.CURSOR));
        Map<String, Object> out = simpleJdbcCall.execute(inputparams); 
        
        int status = 0;
        status = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));;
        String errorStatus = String.valueOf(out.get("OP_ERROR_MESSAGE"));
        logger.info("Status:"+status+"error Status:"+errorStatus);
        logger.info("out:::::"+out);
        String response = "";
        ArrayList transactionRangeList = null;
        if (status == 1) {
             transactionRangeList = (ArrayList)out.get("OP_TRANSACTION_RANGE");
             Iterator it = transactionRangeList.iterator();
             while(it.hasNext()){
                HashMap hm = (HashMap)it.next();
                
                 response = response + hm.get("LOOKUP_VALUE") + "$";
//                 response = response + hm.get("LOOKUP_VALUE") + "$";
             }
             

            return response;
        } 
        
        return response;
    }        
    
    /**
    
     * This Method interacts with the database and retrieves Transaction Ranges
    
     * @param customerType String and transactionCount String
    
     * @return double
    
     */
    public double getTotalFeeOnTransactionCount(String customerType, String transactionCount) {
        logger.info("Entered getTotalFeeOnTransactionCount");
        logger.info("customerType ::::::::"+ customerType +" && transactionCount:::::"+transactionCount);
        //        HashMap hm=new HashMap();
        double totalFee = 0.0;    
            try{
            DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
             SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_CUSTOMER_TYPE",customerType)
                                                                         .addValue("IP_TRANSACTION_COUNT",transactionCount);
                       
                     simpleJdbcCall = new SimpleJdbcCall(datasource)
                                    .withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                    .withProcedureName("GET_TRANSACTION_COUNT_FEE")
                                    .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                    .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                    .declareParameters(new SqlOutParameter("OP_TOTAL_FEE", Types.DOUBLE));
            
            Map<String,Object> out = simpleJdbcCall.execute(inputparams);
            
            
        //            logger.info("After Execute");
        //            logger.info("out ::: "+out.toString());
            opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));          
            String errorMessage =  String.valueOf(out.get("OP_ERROR_MESSAGE"));   
            logger.info("opstatus:::::"+opStatus+"::::::error message::::"+errorMessage);
            totalFee= (Double)out.get("OP_TOTAL_FEE");

        //            logger.info("carier codes values :::"+hm);
            
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exist from getPricingDetails");
        
        return totalFee;
    }
}   //  End of AascOracleUserControlDAO class
