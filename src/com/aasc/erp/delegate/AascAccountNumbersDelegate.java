package com.aasc.erp.delegate;
/*
 * @(#)AascAccountNumbersDelegate.java     28/11/2014
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */

import com.aasc.erp.model.AascAccountNumbersDAO;
import com.aasc.erp.bean.AascAccountNumbersBean;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.model.AascOracleAccountNumbersInfoDAO;
import com.aasc.erp.util.AascLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.LinkedList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * AascAccountNumbersDelegate class is delegate class for Account Numbers
 * @version   2
 * @author    Venkateswaramma Malluru
 * History
 * 
 * 30-Jan-2015  Y Pradeep           Ran self audit, added exception handling and added java doc for all methods.
 * 05-May-2015  Y Pradeep           Added code to save No if registrationStatus flag is null.
 */
public class AascAccountNumbersDelegate {


    String organizationStr = "";
    String actiontype = "";
    int organizationInt = 0;
    int returnStatus = 0;
    int type;
    int locationId = 0;
    int clientId = 0;
    int accountNumberId = 0;
    int carrierCode = 0;
    String accountNumber = "";
    String meterNumber = "";
    String accessLicenseNumber = "";
    String accountNumberActive = "";
    String accountNumberDefault = "";
    String accountNumberNegotiatedFlag = "";
    String accountUserName = "";
    String accountPassword = "";
    String negotiatedRates = "";
    int saveAccountNumberAllStatus = 0;
    int accountNumberCount = 0;
    int addNewAccountNumberStatus = 0;
    String statuSMsg = "";
    int editNewAccountNumberStatus = 0;
    String editAccountNumberStatusMsg = "";
    int getAccountNumberStatus = 0;
    String actionValue = "";
    int userId;
    String key = null;
    int queryTimeOutInt;
    String signatureImagePath = "";
    String logoImagePath = "";

    AascDAOFactory aascDAOFactory;
    private AascAccountNumbersDAO aascAccountNumbersDAO;
    AascAccountNumbersBean aascAccountNumbersInfo;
    AascAccountNumbersBean aascAccountNumbersInfoAddNewAccountNumber;
    AascAccountNumbersBean aascAccountNumbersInfoEditAccountNumber;

    private static Logger logger = AascLogger.getLogger("AascAccountNumbersAction");


    /** This is a common method where it loads reuired data like roleid, hserid, clientid, locationid.
     * @param session
     * @param request
     * @return success or error message to action class
     */
    public String commonAction(HttpSession session, HttpServletRequest request) {

        try {
            String userIdstr = ""+ session.getAttribute("UserId"); // string userid
            if (userIdstr != null) {
                userId = Integer.parseInt(userIdstr);
            }
            
            logger.info("userId : "+userId);
            Integer roleId = (Integer)session.getAttribute("role_id");
            type = roleId.intValue();

            String queryTimeOut = (String)session.getAttribute("queryTimeOut");
            queryTimeOutInt = Integer.parseInt(queryTimeOut);

            String clientIdStr = request.getParameter("clientIdHidden");
            logger.info("clientIdStr : " + clientIdStr);
            clientId = Integer.parseInt(clientIdStr);
            

            String carrierCodeStr = request.getParameter("carrierCodeHidden");
            logger.info("carrierCodeStr : " + carrierCodeStr);
            carrierCode = Integer.parseInt(carrierCodeStr);

            session.setAttribute("RegistrationCarrierCode", carrierCodeStr);

            locationId = (Integer)session.getAttribute("location_id");
            logger.info("locationId = " + locationId);

            actiontype = request.getParameter("actiontype");
            logger.info("actiontype in Action:" + actiontype);
            
         
            aascAccountNumbersDAO = new AascOracleAccountNumbersInfoDAO();
           
            return "successDelegate";
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            return "fail";
        }
    }

    /** This method is called from action class on load of Account Numbers page. 
     * This method loads selected account based on Client id and location id.
     * @param session
     * @param request
     */
    public void successAction(HttpSession session, HttpServletRequest request) {
        try {
            logger.info("aascAccountNumbersDAO : " + aascAccountNumbersDAO);
            int locationId=0;
            aascAccountNumbersInfo = aascAccountNumbersDAO.getAccountNumbersInfo(carrierCode,clientId,locationId,queryTimeOutInt);

            aascAccountNumbersInfo.setlocationId(locationId);
            aascAccountNumbersInfo.setClientId(clientId);
            aascAccountNumbersInfo.setActionType(actiontype);
            getAccountNumberStatus = aascAccountNumbersInfo.getErrorStatus();
            logger.info("Error status== " + getAccountNumberStatus);
            if (getAccountNumberStatus == 706) {
                session.setAttribute("aascAccountNumbersInfo", aascAccountNumbersInfo);
                key = "aasc706";
            } else if (getAccountNumberStatus == -1013) {
                key = "aasc20001";
            } else {
                key = "aasc707";
            }
            request.setAttribute("key", key);
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }
    }

    /** This method is called from action class on click of update button in Account Numbers details page. 
     * This method updates all account numbers based on client id and location id.
     * @param aascAccountNumbersInfoSave
     * @param session
     * @param request
     */
    public void updateAction(AascAccountNumbersBean aascAccountNumbersInfoSave, HttpSession session, HttpServletRequest request) {
        try {
            String accountNumbersCountStr = request.getParameter("accountNumberCount");
            logger.info("accountNumbersCountStr : " + accountNumbersCountStr);
            accountNumberCount = Integer.parseInt(accountNumbersCountStr);
            logger.info("accountNumberCount :" + accountNumberCount);
            int locationId = 0;
            LinkedList accountNumbersInfoList = new LinkedList();

            for (int aactLine = 1; aactLine <= accountNumberCount; aactLine++) {
                AascAccountNumbersBean aascAccountNumbersInfoObj = new AascAccountNumbersBean();
                    
                String locationId1 = request.getParameter("locationId");

                logger.info("locationId :: "+locationId1);
                
                locationId= Integer.parseInt(locationId1);
                                 
                String accountNumberIdStr = request.getParameter("accountNumberId" + aactLine);
                logger.info("accountNumberIdStr : " + accountNumberIdStr);
                accountNumberId = Integer.parseInt(accountNumberIdStr);
                
                String carrierCodeStr = request.getParameter("carrierCode" + aactLine);
                logger.info("carrierCodeStr : " + carrierCodeStr);
                carrierCode = Integer.parseInt(carrierCodeStr);
                
                accountNumber = request.getParameter("accountNumber" + aactLine);
                meterNumber = request.getParameter("meterNumber" + aactLine);
                accountUserName = request.getParameter("accountUserName" + aactLine);
                accountPassword = request.getParameter("accountPassword" + aactLine);
                accessLicenseNumber = request.getParameter("accessLicenseNumber" + aactLine);
                accountNumberActive = request.getParameter("accountNumberActive" + aactLine);

                if (accountNumberActive == null || 
                    accountNumberActive == "null") {
                    accountNumberActive = "N";
                }
                logger.info("accountNumberActive :" + accountNumberActive);
                
                accountNumberDefault = request.getParameter("accountNumberDefault" + aactLine);
                if (accountNumberDefault == null || 
                    accountNumberDefault == "null") {
                    accountNumberDefault = "N";
                }
                logger.info("accountNumberDefault : " + accountNumberDefault);

                accountNumberNegotiatedFlag = request.getParameter("accountNumberNegotiatedFlag" + aactLine);
                if (accountNumberNegotiatedFlag == null || 
                    accountNumberNegotiatedFlag == "null" || 
                    accountNumberNegotiatedFlag == "") {
                    accountNumberNegotiatedFlag = "N";
                }
                logger.info("accountNumberNegotiatedFlag :" + accountNumberNegotiatedFlag);

                String registrationStatus = request.getParameter("accountNumberRegistrationFlag" + aactLine);
                if (registrationStatus == null || 
                    registrationStatus == "null" || registrationStatus == "") {
                    registrationStatus = "No";
                }
                logger.info("accountNumberRegistrationFlag :" + registrationStatus);

                negotiatedRates = request.getParameter("negotiatedRates" + aactLine);
                if (negotiatedRates == null || negotiatedRates == "null" || 
                    negotiatedRates == "") {
                    negotiatedRates = "N";
                }
                logger.info("negotiatedRates :" + negotiatedRates);

                signatureImagePath = request.getParameter("signatureImagePath");
                logger.info("signatureImagePath : " + signatureImagePath);

                logoImagePath = request.getParameter("logoImagePath");
                logger.info("logoImagePath : " + logoImagePath);

               aascAccountNumbersInfoObj.setLocationId(locationId);
                aascAccountNumbersInfoObj.setClientId(clientId);
                aascAccountNumbersInfoObj.setCarrierCode(carrierCode);
                aascAccountNumbersInfoObj.setAccountNumberId(accountNumberId);
                aascAccountNumbersInfoObj.setAccountNumber(accountNumber);
                aascAccountNumbersInfoObj.setMeterNumber(meterNumber);
                aascAccountNumbersInfoObj.setAccessLicenseNumber(accessLicenseNumber);
                aascAccountNumbersInfoObj.setAccountNumberActive(accountNumberActive);
                aascAccountNumbersInfoObj.setAccountNumberDefault(accountNumberDefault);
                aascAccountNumbersInfoObj.setAccountNumberNegotiatedFlag(accountNumberNegotiatedFlag);
                aascAccountNumbersInfoObj.setNegotiatedRates(negotiatedRates);
                aascAccountNumbersInfoObj.setActionType(actiontype);
                aascAccountNumbersInfoObj.setAccountUserName(accountUserName);
                aascAccountNumbersInfoObj.setAccountPassword(accountPassword);
                aascAccountNumbersInfoObj.setAccountNumberRegistrationFlag(registrationStatus);

                accountNumbersInfoList.add(aascAccountNumbersInfoObj);
            }
           System.out.println("accountNumbersInfoList" +accountNumbersInfoList);
            aascAccountNumbersInfoSave.setAccountNumbersList(accountNumbersInfoList);

            saveAccountNumberAllStatus = aascAccountNumbersDAO.saveAllAcountNumbersInfo(userId, aascAccountNumbersInfoSave, queryTimeOutInt);

            logger.info("saveAccountNumberAllStatus from saveAllAccountNumbersInfo():" + saveAccountNumberAllStatus);
            if (saveAccountNumberAllStatus == 700 || 
                saveAccountNumberAllStatus == 704) {
                
                aascAccountNumbersInfo = aascAccountNumbersDAO.getAccountNumbersInfo(carrierCode,clientId,locationId,queryTimeOutInt);
                session.setAttribute("aascAccountNumbersInfo", aascAccountNumbersInfo);
                if (saveAccountNumberAllStatus == 700) {
                    key = "aasc700";
                } else if (saveAccountNumberAllStatus == 704) {
                    key = "aasc704";
                }
            } else if (saveAccountNumberAllStatus == -1013) {
                key = "aasc20001";
            } else {
                key = "aasc701";
            }
            request.setAttribute("key", key);
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }
    }

    /** This method is called from action class on click of create button in Account Numbers details page. 
     * This method creates account number based on client id and location id.
     * @param aascAccountNumbersInfo
     * @param session
     * @param request
     * @return success or error message to action class
     */
    public String addNewAccountNumberAction(AascAccountNumbersBean aascAccountNumbersInfo, HttpSession session, 
                                            HttpServletRequest request) {
        try {
            
            String carrierCodeStr = "" + aascAccountNumbersInfo.getCarrierCode();
            int carrierCode = Integer.parseInt(carrierCodeStr);

            String accountNumber = aascAccountNumbersInfo.getAccountNumber();

            String meterNumber = aascAccountNumbersInfo.getMeterNumber();
            String accessLicenseNumber = aascAccountNumbersInfo.getAccessLicenseNumber();
            String accountUserName = aascAccountNumbersInfo.getAccountUserName();
            String locationId1 = request.getParameter("locationId");
            
            logger.info("locationId :: "+locationId1);

            int locationId= Integer.parseInt(locationId1);
                 
            String accountPassword = aascAccountNumbersInfo.getAccountPassword();
            String accountNumberNegotiatedFlag = request.getParameter("accountNumberNegotiatedFlag");
            String negotiatedRates = request.getParameter("negotiatedRates");
            String registrationStatus = nullStringToSpace(aascAccountNumbersInfo.getAccountNumberRegistrationFlag());
logger.info("registrationStatus in delegate :: "+registrationStatus);
            if (accountNumberNegotiatedFlag == null || accountNumberNegotiatedFlag == "null" || accountNumberNegotiatedFlag == "") {
                accountNumberNegotiatedFlag = "N";
            }

            if (negotiatedRates == null || negotiatedRates == "null" || negotiatedRates == "") {
                negotiatedRates = "N";
            }

            if (registrationStatus == null || registrationStatus == "null" || registrationStatus == "") {
                registrationStatus = "No";
            }
            logger.info("accountNumberNegotiatedFlag in add number :" + registrationStatus);
            logger.info("accountNumberNegotiatedFlag :" + accountNumberNegotiatedFlag + "***");
            logger.info("negotiatedRates :" + negotiatedRates + "***");

            String signatureImagePath = nullStringToSpace(aascAccountNumbersInfo.getSignatureImagePath());
            logger.info("signatureImagePath : " + signatureImagePath);

            String logoImagePath = nullStringToSpace(aascAccountNumbersInfo.getLogoImagePath());
            logger.info("logoImagePath : " + logoImagePath);

            aascAccountNumbersInfoAddNewAccountNumber = new AascAccountNumbersBean();

            aascAccountNumbersInfoAddNewAccountNumber.setlocationId(locationId);
            aascAccountNumbersInfoAddNewAccountNumber.setClientId(clientId);
            aascAccountNumbersInfoAddNewAccountNumber.setCarrierCode(carrierCode);
            aascAccountNumbersInfoAddNewAccountNumber.setAccountNumber(accountNumber);
            aascAccountNumbersInfoAddNewAccountNumber.setMeterNumber(meterNumber);
            aascAccountNumbersInfoAddNewAccountNumber.setAccessLicenseNumber(accessLicenseNumber);
            aascAccountNumbersInfoAddNewAccountNumber.setAccountNumberActive("Y");
            aascAccountNumbersInfoAddNewAccountNumber.setAccountNumberDefault("N");
            aascAccountNumbersInfoAddNewAccountNumber.setAccountNumberNegotiatedFlag(accountNumberNegotiatedFlag);
            aascAccountNumbersInfoAddNewAccountNumber.setNegotiatedRates(negotiatedRates);
            aascAccountNumbersInfoAddNewAccountNumber.setActionType(actiontype);
            aascAccountNumbersInfoAddNewAccountNumber.setAccountNumberRegistrationFlag(registrationStatus);
            aascAccountNumbersInfoAddNewAccountNumber.setAccountUserName(accountUserName);
            aascAccountNumbersInfoAddNewAccountNumber.setAccountPassword(accountPassword);
                 
            //calling the save method of packing dimension detail information

            AascAccountNumbersBean aascAccountNumberInfoCreate =
                aascAccountNumbersDAO.getSaveAccountNumbersInfo(userId, 
                                                                locationId, 
                                                                aascAccountNumbersInfoAddNewAccountNumber, 
                                                                queryTimeOutInt);
            addNewAccountNumberStatus = aascAccountNumberInfoCreate.getErrorStatus();
            statuSMsg = aascAccountNumberInfoCreate.getErrorMessage();
            logger.info("addNewAccountNumberStatus == " + addNewAccountNumberStatus);

//            if ((signatureImagePath != null && 
//                 signatureImagePath.length() > 0) || 
//                (logoImagePath != null && logoImagePath.length() > 0)) {
//                logger.info("Uploading Signature and/or logo image");
//                logger.info("Action type 1111 " + actiontype);
//                File signatureImage = null;
//                byte[] signature_img_byte = null;
//                String signature_img_string = "";
//                File logoImage = null;
//                byte[] logo_img_byte = null;
//                String logo_img_string = "";
//
//                AascProfileOptionsInfo aascProfileOptionsInfo = 
//                    (AascProfileOptionsInfo)session.getAttribute("ProfileOptionsInfo");
//                String carrierUserName = aascProfileOptionsInfo.getUserName();
//                String carrierPwd = aascProfileOptionsInfo.getPassword();
//                String ipServerAddr = 
//                    aascProfileOptionsInfo.getServerIpAddress();
//                int portNo = aascProfileOptionsInfo.getPort();
//              //  String labelPath = aascProfileOptionsInfo.getLabelPath();
//               //Gururaj added code to get the label path from session, set in customer creation page
//                String labelPath=(String)session.getAttribute("cloudLabelPath");
//                logger.info("cloudLabelPath in AascAccountNumbersDelegate "+labelPath);
//                //Gururaj code end
//                logger.info("labelPath " + labelPath);
//
//                String accUserName = 
//                    aascAccountNumbersInfoAddNewAccountNumber.getAccountUserName();
//                String accPwd = 
//                    aascAccountNumbersInfoAddNewAccountNumber.getAccountPassword();
//                String accNumber = 
//                    aascAccountNumbersInfoAddNewAccountNumber.getAccountNumber();
//                String accMeterNumber = 
//                    aascAccountNumbersInfoAddNewAccountNumber.getMeterNumber();
//
//                UploadImageWebService uploadImageWebService = 
//                    new UploadImageWebService();
//                String reply = "";
//                try {
//                    if (signatureImagePath != null && 
//                        signatureImagePath.length() > 0) {
//                        signatureImage = new File(signatureImagePath);
//                        signature_img_byte = getBytesFromFile(signatureImage);
//                        signature_img_string = 
//                                AascBase64.encodeBytes(signature_img_byte);
//                    }
//                    if (logoImagePath != null && logoImagePath.length() > 0) {
//                        logoImage = new File(logoImagePath);
//                        logo_img_byte = getBytesFromFile(logoImage);
//                        logo_img_string = 
//                                AascBase64.encodeBytes(logo_img_byte);
//                    }
//
//
//                    if (accUserName != null && accUserName.length() > 0 && 
//                        accPwd != null && accPwd.length() > 0) {
//                        reply = 
//                                uploadImageWebService.getUploadImageInfo(ipServerAddr, 
//                                                                         portNo, 
//                                                                         accNumber, 
//                                                                         accMeterNumber, 
//                                                                         accUserName, 
//                                                                         accPwd, 
//                                                                         labelPath, 
//                                                                         signature_img_string, 
//                                                                         logo_img_string);
//                    } else {
//                        reply = 
//                                uploadImageWebService.getUploadImageInfo(ipServerAddr, 
//                                                                         portNo, 
//                                                                         accNumber, 
//                                                                         accMeterNumber, 
//                                                                         carrierUserName, 
//                                                                         carrierPwd, 
//                                                                         labelPath, 
//                                                                         signature_img_string, 
//                                                                         logo_img_string);
//                    }
//                    logger.info("Reply from Upload Image webservice => " + 
//                                reply);
//                    if (reply != null && reply.length() > 0) {
//                        session.setAttribute("uploadErrorMsg", reply);
//                    }
//                } catch (FileNotFoundException fe) {
//                    logger.severe("Given file does not exist for upload : " + 
//                                  fe.getMessage());
//                    formatter.writeLogger(logger, fe);
//                    session.setAttribute("uploadErrorMsg", 
//                                         "Uploading for Signature image failed - Given File(s) does not exist");
//                } catch (Exception e) {
//                    logger.severe("Exception while uploading : " + 
//                                  e.getMessage());
//                    formatter.writeLogger(logger, e);
//                }
//            }


            logger.info("addNewAccountNumberStatus::::::::::::"+addNewAccountNumberStatus);
            if (addNewAccountNumberStatus == 702) {
                logger.info("locationId ::"+locationId+" && carrierCode ::"+carrierCode+" && queryTimeOutInt::"+queryTimeOutInt+" && clientId"+clientId);
                aascAccountNumbersInfo = aascAccountNumbersDAO.getAccountNumbersInfo(carrierCode,clientId,locationId,queryTimeOutInt);
                aascAccountNumbersInfo.setClientId(clientId);

                session.setAttribute("aascAccountNumbersInfo", aascAccountNumbersInfo);
                key = "aasc702";
            } else {
                if (addNewAccountNumberStatus == 709) {
                    key = "aasc709";
                    // session.setAttribute("Error",statuSMsg);
                    session.setAttribute("Error", "");
                } else if (addNewAccountNumberStatus == 708) {
                    key = "aasc708";
                    session.setAttribute("Error", "");
                } else if (addNewAccountNumberStatus == -1013) {
                    key = "aasc20001";
                } else {
                    key = "aasc703";
                }
                session.setAttribute("aascAccountNumberEditInfo", aascAccountNumbersInfoAddNewAccountNumber);
                actionValue = "ErrorCondition";
                request.setAttribute("actionValue", actionValue);
                request.setAttribute("key", key);
                return "fail";
            }
            request.setAttribute("key", key);
            //session.setAttribute("aascPackageDimensionInfo",aascPackageDimensionInfo);
            //return mapping.findForward("success");
            
             return "success";
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            return "fail";
        }
    }

    /** This method is called from action class on click of save button in Account Numbers page while editing. 
     * This method updates edited account number based on client id and location id.
     * @param aascAccountNumbersInfo
     * @param session
     * @param request
     * @return success or error message to action class
     */
    public String editSaveAction(AascAccountNumbersBean aascAccountNumbersInfo, HttpSession session, HttpServletRequest request) {
        try {
            int accountNumberId = Integer.parseInt(request.getParameter("accountNumberId"));
            int locationId= Integer.parseInt(request.getParameter("locationId"));
            String carrierCodeStr = request.getParameter("carrierCode"); //+rowNo);
            int carrierCode = Integer.parseInt(carrierCodeStr);
            String accountNumber = request.getParameter("accountNumber"); //+rowNo);
            String meterNumber = request.getParameter("meterNumber"); //+rowNo);
            String accessLicenseNumber = request.getParameter("accessLicenseNumber");
            String accountNumberDefault = request.getParameter("accountNumberDefault");
            String accountNumberActive = request.getParameter("accountNumberActive");
            String accountNumberNegotiatedFlag = request.getParameter("accountNumberNegotiatedFlag");                
            String negotiatedRates = request.getParameter("negotiatedRates");
            String registrationStatus = request.getParameter("accountNumberRegistrationFlag");
            if (registrationStatus == null || registrationStatus == "null" || registrationStatus == "") {
                registrationStatus = "No";
            }
            logger.info("registrationStatus in editsaveaction  == "+registrationStatus );
            String userName = request.getParameter("accountUserName");
            String password = request.getParameter("accountPassword");

                 if (accountNumberActive == null || accountNumberActive == "null") {
                     accountNumberActive = "N";
                 }
                 else if("unchecked".equalsIgnoreCase(accountNumberActive)){
                     accountNumberActive="N";
                 } else if("checked".equalsIgnoreCase(accountNumberActive)){
                     accountNumberActive="Y";
                 }
                 
                 if (accountNumberNegotiatedFlag == null || accountNumberNegotiatedFlag == "null") {
                     accountNumberNegotiatedFlag = "N";
                 }
                 else if("unchecked".equalsIgnoreCase(accountNumberNegotiatedFlag)){
                     accountNumberNegotiatedFlag="N";
                 } else if("checked".equalsIgnoreCase(accountNumberNegotiatedFlag)){
                     accountNumberNegotiatedFlag="Y";
                 }
                 
                 if (negotiatedRates == null || negotiatedRates == "null") {
                     negotiatedRates = "N";
                 }
                 else if("unchecked".equalsIgnoreCase(negotiatedRates)){
                     negotiatedRates="N";
                 } else if("checked".equalsIgnoreCase(negotiatedRates)){
                     negotiatedRates="Y";
                 }

            String signatureImagePath = request.getParameter("signatureImagePath");
            logger.info("signatureImagePath : " + signatureImagePath);

            String logoImagePath = nullStringToSpace(aascAccountNumbersInfo.getLogoImagePath());
            logger.info("logoImagePath : " + logoImagePath);

            aascAccountNumbersInfoEditAccountNumber = new AascAccountNumbersBean();
            LinkedList accountNumbersInfoEditList = new LinkedList();
            AascAccountNumbersBean aascAccountNumbersInfoEditObj = new AascAccountNumbersBean();

            aascAccountNumbersInfoEditObj.setLocationId(locationId);
            aascAccountNumbersInfoEditObj.setClientId(clientId);
            aascAccountNumbersInfoEditObj.setAccountNumberId(accountNumberId);
            aascAccountNumbersInfoEditObj.setCarrierCode(carrierCode);
            aascAccountNumbersInfoEditObj.setAccountNumber(accountNumber);
            aascAccountNumbersInfoEditObj.setMeterNumber(meterNumber);
            aascAccountNumbersInfoEditObj.setAccessLicenseNumber(accessLicenseNumber);
            aascAccountNumbersInfoEditObj.setAccountNumberDefault(accountNumberDefault);
            aascAccountNumbersInfoEditObj.setAccountNumberActive(accountNumberActive);
            aascAccountNumbersInfoEditObj.setAccountNumberNegotiatedFlag(accountNumberNegotiatedFlag);
            aascAccountNumbersInfoEditObj.setNegotiatedRates(negotiatedRates);
            aascAccountNumbersInfoEditObj.setActionType(actiontype);
            aascAccountNumbersInfoEditObj.setAccountNumberRegistrationFlag(registrationStatus);
            aascAccountNumbersInfoEditObj.setAccountUserName(userName);
            aascAccountNumbersInfoEditObj.setAccountPassword(password);
            accountNumbersInfoEditList.add(aascAccountNumbersInfoEditObj);

            aascAccountNumbersInfoEditAccountNumber.setAccountNumbersList(accountNumbersInfoEditList);


            int saveAccountNumberStatus = aascAccountNumbersDAO.saveAllAcountNumbersInfo(userId, aascAccountNumbersInfoEditAccountNumber, queryTimeOutInt);

            /*saveEachAccountNumbersInfo(invOrgId, orgId, carrierCode, accountNumber,
                                                                                   meterNumber, accessLicenseNumber, accountDefault,
                                                                                   actiontype, userId, queryTimeOutInt);*/

            logger.info("saveAccountNumberStatus from saveAccountNumberStatus():" + saveAccountNumberStatus);

//            if ((signatureImagePath != null && 
//                 signatureImagePath.length() > 0) || 
//                (logoImagePath != null && logoImagePath.length() > 0)) {
//                logger.info("Uploading Signature and/or logo image");
//
//                File signatureImage = null;
//                byte[] signature_img_byte = null;
//                String signature_img_string = "";
//                File logoImage = null;
//                byte[] logo_img_byte = null;
//                String logo_img_string = "";
//
//                AascProfileOptionsInfo aascProfileOptionsInfo = 
//                    (AascProfileOptionsInfo)session.getAttribute("ProfileOptionsInfo");
//                String carrierUserName = aascProfileOptionsInfo.getUserName();
//                String carrierPwd = aascProfileOptionsInfo.getPassword();
//                String ipServerAddr = 
//                    aascProfileOptionsInfo.getServerIpAddress();
//                int portNo = aascProfileOptionsInfo.getPort();
//               // String labelPath = aascProfileOptionsInfo.getLabelPath();
//                
//                //Gururaj added code to get the label path from session, set in customer creation page
//                 String labelPath=(String)session.getAttribute("cloudLabelPath");
//                 logger.info("cloudLabelPath in AascAccountNumbersDelegate "+labelPath);
//                 //Gururaj code end
//                
//                logger.info("labelPath " + labelPath);
//
//                String accUserName = 
//                    aascAccountNumbersInfoEditObj.getAccountUserName();
//                String accPwd = 
//                    aascAccountNumbersInfoEditObj.getAccountPassword();
//                String accNumber = 
//                    aascAccountNumbersInfoEditObj.getAccountNumber();
//                String accMeterNumber = 
//                    aascAccountNumbersInfoEditObj.getMeterNumber();
//
//                UploadImageWebService uploadImageWebService = 
//                    new UploadImageWebService();
//                String reply = "";
//
//                try {
//                    if (signatureImagePath != null && 
//                        signatureImagePath.length() > 0) {
//                        signatureImage = new File(signatureImagePath);
//                        signature_img_byte = getBytesFromFile(signatureImage);
//                        signature_img_string = 
//                                AascBase64.encodeBytes(signature_img_byte);
//                    }
//                    if (logoImagePath != null && logoImagePath.length() > 0) {
//                        logoImage = new File(logoImagePath);
//                        logo_img_byte = getBytesFromFile(logoImage);
//                        logo_img_string = 
//                                AascBase64.encodeBytes(logo_img_byte);
//                    }
//                    if (accUserName != null && accUserName.length() > 0 && 
//                        accPwd != null && accPwd.length() > 0) {
//                        reply = 
//                                uploadImageWebService.getUploadImageInfo(ipServerAddr, 
//                                                                         portNo, 
//                                                                         accNumber, 
//                                                                         accMeterNumber, 
//                                                                         accUserName, 
//                                                                         accPwd, 
//                                                                         labelPath, 
//                                                                         signature_img_string, 
//                                                                         logo_img_string);
//                    } else {
//                        reply = 
//                                uploadImageWebService.getUploadImageInfo(ipServerAddr, 
//                                                                         portNo, 
//                                                                         accNumber, 
//                                                                         accMeterNumber, 
//                                                                         carrierUserName, 
//                                                                         carrierPwd, 
//                                                                         labelPath, 
//                                                                         signature_img_string, 
//                                                                         logo_img_string);
//                    }
//                    logger.info("Reply from Upload Image webservice => " + 
//                                reply);
//                    if (reply != null && reply.length() > 0) {
//                        session.setAttribute("uploadErrorMsg", reply);
//                    }
//                } catch (FileNotFoundException fe) {
//                    logger.severe("Given file does not exist for upload : " + 
//                                  fe.getMessage());
//                    formatter.writeLogger(logger, fe);
//                    session.setAttribute("uploadErrorMsg", 
//                                         "Uploading for Signature image failed - Given File(s) does not exist");
//                } catch (Exception e) {
//                    logger.severe("Exception while uploading : " + 
//                                  e.getMessage());
//                    formatter.writeLogger(logger, e);
//                }
//            }

            if (saveAccountNumberStatus == 704 || 
                saveAccountNumberStatus == 709) {
                aascAccountNumbersInfo = aascAccountNumbersDAO.getAccountNumbersInfo(carrierCode,clientId,locationId,queryTimeOutInt);
                session.setAttribute("aascAccountNumbersInfo", aascAccountNumbersInfo);
                if (saveAccountNumberStatus == 704) {
                    key = "aasc704";
                } else if (saveAccountNumberStatus == 709) {
                    key = "aasc709";
                }
            } else if (saveAccountNumberStatus == -1013) {
                key = "aasc20001";
            } else {
                key = "aasc705";
            }
            logger.info("key : " + key);
            request.setAttribute("key", key);

            session.setAttribute("aascAccountNumberEditInfo", aascAccountNumbersInfoEditAccountNumber);
            
             return "success";
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            return "fail";
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

    public byte[] getBytesFromFile(File file) throws IOException, 
                                                     FileNotFoundException {
        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large!");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length && 
                   (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        return bytes;
    }


}
