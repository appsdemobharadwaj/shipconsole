package com.aasc.erp.delegate;

import com.aasc.erp.bean.AascAddressInfo;
import com.aasc.erp.bean.AascHazmatPackageInfo;
import com.aasc.erp.bean.AascIntlDataBean;
import com.aasc.erp.bean.AascIntlInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.model.AascHazmatPackageDAO;
import com.aasc.erp.model.AascOracleHazmatPackageDAO;
import com.aasc.erp.util.AascLogger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AascHazmatDelegate {
    public AascHazmatDelegate() {
    }
    static Logger logger = AascLogger.getLogger(" AascHazmatDelegate "); // logger object
   
    AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1); // object of AascDAOFactory
     AascShipmentPackageInfo aascShipmentPackageInfo=null;
     AascHazmatPackageInfo aascHazmatPackageInfo=null;
     AascHazmatPackageDAO aascHazmatPackageDAO = null;
    
        
    int userId; // to store userID
    int responsibilityId;   
    int clientId;
    int locationId;

        String error = ""; // to store error.
        // String key = "";
        int opStatus = 0;
        int opItemStatus = 0;
        int opImpStatus = 0;
        int opBrokerStatus = 0;
        int pkgSequenceId = 0;
        String orderNumber = "";
        int carrierCode;
        int returnStatus;
        
        String saveHeaderFlag="";

    public String commonAction(HttpServletRequest request,HttpSession session){
        System.out.println("Entered execute method");

      
//            carrierCode = Integer.parseInt(request.getParameter("carrierCode"));
//            System.out.println("carrierCode:::" + carrierCode);
            try {
//                if(session.isNew() || !(session.getId().equals(session.getAttribute("SessionId")))) {
//                    return "error";
//                }
                
//                userId = (Integer)session.getAttribute("user_id"); // string userid
//                System.out.println("userIdstr = "+userId);
                
//                session.removeAttribute("Error");
                            
//                clientId = (Integer)session.getAttribute("client_id");
//                System.out.println("clientId = " + clientId);
                
//                locationId = Integer.parseInt(request.getParameter("locationId"));
                
//                String shipmentType = (String)session.getAttribute("shipment");
//                System.out.println("shipmentType = " + shipmentType);

               
            }
            catch(Exception e){
                logger.severe("Got Exception in commonAction method "+e.getMessage());
                e.printStackTrace();
                return "error";
            }
            return "success";
}

        public String addAction(HttpServletRequest request, HttpSession session){
            try{
                String pkgCount=request.getParameter("packCount");
                System.out.println("pkgCount::"+pkgCount);
                int pkgSequenceId = Integer.parseInt(pkgCount);//aascHazmatPackageInfo.getPackageSequenceId();
                System.out.println("pkgSequenceId::"+pkgSequenceId);
                String orderNumber=request.getParameter("orderNumber1");
                System.out.println("orderNumber::"+orderNumber);

                AascHazmatPackageDAO aascHazmatPackageDAO=null;
                AascHazmatPackageInfo aascHazmatPackageInfo = null;
                
                  LinkedList HazmatList = new LinkedList();
                aascHazmatPackageInfo = new AascHazmatPackageInfo();
                aascHazmatPackageDAO = new AascOracleHazmatPackageDAO();
                
//                    String HazardousMaterial = request.getParameter("HazardousMaterial").trim();
//                    System.out.println("HazardousMaterialType: " + HazardousMaterial);
//                    aascHazmatPackageInfo.setHazMatType(HazardousMaterial);
                    
               
                    String HazardousMaterialType = request.getParameter("HazardousMaterialType").trim();
                    System.out.println("HazardousMaterialType: " + HazardousMaterialType);
                    aascHazmatPackageInfo.setHazMatType(HazardousMaterialType);
                
                    String HazardousMaterialId = request.getParameter("HazardousMaterialId").trim();
                    System.out.println("HazardousMaterialId: " + HazardousMaterialId);
                    aascHazmatPackageInfo.setHazMatId(HazardousMaterialId);
                    
                    String HazardousMaterialClass = request.getParameter("HazardousMaterialClass").trim();
                    System.out.println("HazardousMaterialClass: " + HazardousMaterialClass);
                    aascHazmatPackageInfo.setHazMatClass(HazardousMaterialClass);
                    
                     
                double HazardousMaterialCharges = 0.0;
                try {
                    HazardousMaterialCharges = Double.parseDouble(request.getParameter("HazardousMaterialCharges").trim());
                    System.out.println("HazardousMaterialCharges: " + HazardousMaterialCharges);
                } catch (Exception e) {
                    HazardousMaterialCharges = 0.0;
                }
                aascHazmatPackageInfo.setHazMatCharges(HazardousMaterialCharges);
                
                double HazardousMaterialQuantity = 0.0;
                try {
                    HazardousMaterialQuantity = Double.parseDouble(request.getParameter("HazardousMaterialQuantity").trim());
                    System.out.println("HazardousMaterialQuantity: " + HazardousMaterialQuantity);
                } catch (Exception e) {
                    HazardousMaterialQuantity = 0.0;
                }
                aascHazmatPackageInfo.setHazMatQty(HazardousMaterialQuantity);
                
                    String HazardousMaterialUnit = request.getParameter("HazardousMaterialUnit").trim();
                    System.out.println("HazardousMaterialUnit: " + HazardousMaterialUnit);
                    aascHazmatPackageInfo.setHazMatUnit(HazardousMaterialUnit);
                    
                    String HazMatIdentificationNo = request.getParameter("HazMatIdentificationNo").trim();
                    System.out.println("HazMatIdentificationNo: " + HazMatIdentificationNo);
                    aascHazmatPackageInfo.setHazMatIdNo(HazMatIdentificationNo);

                    String HazMatDOTLabelType = request.getParameter("HazMatDOTLabelType").trim();
                    System.out.println("HazMatDOTLabelType: " + HazMatDOTLabelType);
                    aascHazmatPackageInfo.setHazMatDOTLabel(HazMatDOTLabelType);
                    
                    String HazMatEmergencyContactNo = request.getParameter("HazMatEmergencyContactNo").trim();
                    System.out.println("HazMatEmergencyContactNo: " + HazMatEmergencyContactNo);
                    aascHazmatPackageInfo.setHazMatEmerContactNo(HazMatEmergencyContactNo);

                    
                    String HazMatEmergencyContactName = request.getParameter("HazMatEmergencyContactName").trim();
                    System.out.println("HazMatEmergencyContactName: " + HazMatEmergencyContactName);
                    aascHazmatPackageInfo.setHazMatEmerContactName(HazMatEmergencyContactName);

                    String HazardousMaterialPkgGroup = request.getParameter("HazardousMaterialPkgGroup").trim();
                    System.out.println("HazardousMaterialPkgGroup: " + HazardousMaterialPkgGroup);
                    aascHazmatPackageInfo.setHazMatPkgGroup(HazardousMaterialPkgGroup);
                    
                    double HazMatPackagingCnt = 0.0;
                    try {
                        HazMatPackagingCnt = Double.parseDouble(request.getParameter("HazMatPackagingCnt").trim());
                        System.out.println("HazMatPackagingCnt: " + HazMatPackagingCnt);
                    } catch (Exception e) {
                        HazMatPackagingCnt = 0.0;
                    }
                        aascHazmatPackageInfo.setHazmatPkgingCnt(HazMatPackagingCnt);

                String HazMatPackagingUnits = request.getParameter("HazMatPackagingUnits").trim();
                    System.out.println("CustomsValue: " + HazMatPackagingUnits);
                    aascHazmatPackageInfo.setHazmatPkgingUnits(HazMatPackagingUnits);


                String HazMatTechnicalName = request.getParameter("HazMatTechnicalName").trim();
                    System.out.println("HazMatTechnicalName: " + HazMatTechnicalName);
                aascHazmatPackageInfo.setHazmatTechnicalName(HazMatTechnicalName);

                    String HazMatSignatureName = request.getParameter("HazMatSignatureName").trim();
                        System.out.println("HazMatSignatureName: " + HazMatSignatureName);
                    aascHazmatPackageInfo.setHazmatSignatureName(HazMatSignatureName);
           
                HazmatList.add(aascHazmatPackageInfo);
               
//           session.setAttribute("aascHazmatPackageInfo",aascHazmatPackageInfo);
//                request.setAttribute("packCount",pkgSequenceId);
//                request.setAttribute("orderNum",orderNumber);
                AascShipmentPackageInfo aascShipmentPackageInfo = new AascShipmentPackageInfo();
                      aascShipmentPackageInfo.setHazmatPackageInfoList(HazmatList);
                      
                System.out.println("pkgSequenceId:save:::"+pkgSequenceId);
                System.out.println("orderNumber:save:::"+orderNumber);
                
               opStatus = aascHazmatPackageDAO.saveHazmatPackageDetails(aascHazmatPackageInfo,pkgSequenceId, orderNumber);
             System.out.println("opStatus::"+opStatus);
                if (opStatus == opStatus) {
                    System.out.println("Successfully Added Commodity Item");
                    session.setAttribute("comments", "Successfully Added Values");
                    session.setAttribute("ACTION", "ADD");
                } else {
                    System.out.println("Got Error in Adding commodity item Values");
                   
                    session.setAttribute("comments", "Error in Adding of Values");
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
           return "success";
        }   
        
        public String saveAction(HttpServletRequest request,HttpSession session){
            try{
                aascShipmentPackageInfo = new AascShipmentPackageInfo();
                aascHazmatPackageInfo = new AascHazmatPackageInfo();
                aascHazmatPackageDAO = new AascOracleHazmatPackageDAO();
                
                String pkgCount=request.getParameter("packCount");
                System.out.println("pkgCount::"+pkgCount);
                int pkgSequenceId = Integer.parseInt(pkgCount);
                System.out.println("aascHazmatPackageInfo.getPackageSequenceId()::"+aascHazmatPackageInfo.getPackageSequenceId());
                System.out.println("pkgSequenceId::"+pkgSequenceId);
                String orderNumber=request.getParameter("orderNumber1");
                System.out.println("orderNumber::"+orderNumber);
                
                String codTempFlag = "";
                try {
                    codTempFlag = request.getParameter("codTempFlag").trim();
                    System.out.println("codTempFlag: " + codTempFlag);
                } catch (Exception e) {
                    codTempFlag = "";
                }
                aascShipmentPackageInfo.setCodFlag(codTempFlag);

                float upsCodAmt = 0.0f;
                try {
                    upsCodAmt = Float.parseFloat(request.getParameter("upsCodAmt").trim());
                    System.out.println("upsCodAmt: " + upsCodAmt);
                } catch (Exception e) {
                    upsCodAmt = 0.0f;
                }
                aascShipmentPackageInfo.setCodAmt(upsCodAmt);

                String upsCodCode = "";
                try {
                    upsCodCode = request.getParameter("upsCodCode").trim();
                    System.out.println("upsCodCode: " + upsCodCode);
                } catch (Exception e) {
                    upsCodCode = "";
                }
                aascShipmentPackageInfo.setCodCode(upsCodCode);

                String upsCodFundsCode = "";
                try {
                    upsCodFundsCode = request.getParameter("upsCodFundsCode").trim();
                    System.out.println("upsCodFundsCode: " + upsCodFundsCode);
                } catch (Exception e) {
                    upsCodFundsCode = "";
                }
                aascShipmentPackageInfo.setCodFundsCode(upsCodFundsCode);

                String upsPackaging = "";
                try {
                    upsPackaging = request.getParameter("upsPackaging");
                    System.out.println("upsPackaging: " + upsPackaging);
                } catch (Exception e) {
                    upsPackaging = "";
                }
                aascShipmentPackageInfo.setPackaging(upsPackaging);

                
                String upsDelConfirm = "";
                try {
                    upsDelConfirm = request.getParameter("upsDelConfirm").trim();
                    System.out.println("upsDelConfirm: " + upsDelConfirm);
                } catch (Exception e) {
                    upsDelConfirm = "";
                }
                aascShipmentPackageInfo.setDeliveryConfirmation(upsDelConfirm);

                String upsCodCurrCode = "";
                try {
                    upsCodCurrCode = request.getParameter("upsCodCurrCode").trim();
                    System.out.println("upsCodCurrCode: " + upsCodCurrCode);
                } catch (Exception e) {
                    upsCodCurrCode = "";
                }
                aascShipmentPackageInfo.setCodCurrCode(upsCodCurrCode);

                double packageShipmentCost = 0.0;
                try {
                    packageShipmentCost = Double.parseDouble(request.getParameter("packageShipmentCost").trim());
                    System.out.println("PackageShipmentCost: " + packageShipmentCost);
                } catch (Exception e) {
                    packageShipmentCost = 0.0;
                }
                aascShipmentPackageInfo.setPkgCost(packageShipmentCost);
                
                    double packageSurcharge = 0.0;
                    try {
                        packageSurcharge = Double.parseDouble(request.getParameter("packageSurcharge").trim());
                        System.out.println("packageSurcharge: " + packageSurcharge);
                    } catch (Exception e) {
                        packageShipmentCost = 0.0;
                    }
                    aascShipmentPackageInfo.setSurCharges(packageSurcharge);
                    

                    String chDryIce = "";
                    try {
                        chDryIce = request.getParameter("chDryIce").trim();
                        System.out.println("chDryIce: " + chDryIce);
                    } catch (Exception e) {
                        chDryIce = "";
                    }
                    aascShipmentPackageInfo.setDryIceChk(chDryIce);

                    double dryIceWeight = 0.0;
                    try {
                      dryIceWeight = Double.parseDouble(request.getParameter("dryIceWeight").trim());
                    } catch (Exception e) {
                        dryIceWeight = 0.0;
                    }
                    aascShipmentPackageInfo.setDryIceWeight(dryIceWeight);

                    String dryIceUnits = "";
                    try {
                      dryIceUnits = request.getParameter("dryIceUnits").trim();
                    } catch (Exception e) {
                        dryIceUnits = "";
                    }
                    aascShipmentPackageInfo.setDryIceUnits(dryIceUnits);

                    String returnShipment = "";
                    try {
                        returnShipment = request.getParameter("returnShipment").trim();
                        System.out.println("returnShipment: " + returnShipment);
                    } catch (Exception e) {
                        returnShipment = "";
                    }
                    aascShipmentPackageInfo.setReturnShipment(returnShipment);

                    String rtnShipMethod = "";
                    try {
                        rtnShipMethod = request.getParameter("rtnShipMethod").trim();
                        System.out.println("rtnShipMethod: " + rtnShipMethod);
                    } catch (Exception e) {
                        rtnShipMethod = "";
                    }
                    aascShipmentPackageInfo.setRtnShipMethod(rtnShipMethod);

                String rtnDesc = "";
                try {
                    rtnDesc = request.getParameter("rtnDesc").trim();
                    System.out.println("rtnDesc: " + rtnDesc);
                } catch (Exception e) {
                    rtnDesc = "";
                }
                aascShipmentPackageInfo.setRtnDesc(rtnDesc);

                    String rtnPayMethod = "";
                    try {
                        rtnPayMethod = request.getParameter("rtnPayMethod").trim();
                        System.out.println("rtnPayMethod: " + rtnPayMethod);
                    } catch (Exception e) {
                        rtnPayMethod = "";
                    }
                    aascShipmentPackageInfo.setRtnPayMethod(rtnPayMethod);

                    double rtnDeclaredValue = 0.0;
                    try {
                        rtnDeclaredValue = Double.parseDouble(request.getParameter("rtnDeclaredValue").trim());
                        System.out.println("rtnDeclaredValue: " + rtnDeclaredValue);
                    } catch (Exception e) {
                        rtnDeclaredValue = 0.0;
                    }
                    aascShipmentPackageInfo.setRtnDeclaredValue(rtnDeclaredValue);

                    String rtnTrackingNumber = "";
                    try {
                        upsPackaging = request.getParameter("rtnTrackingNumber");
                        System.out.println("rtnTrackingNumber: " + rtnTrackingNumber);
                    } catch (Exception e) {
                        rtnTrackingNumber = "";
                    }
                    aascShipmentPackageInfo.setRtnTrackingNumber(rtnTrackingNumber);

                    double rtnShipmentCost = 0.0;
                    try {
                        rtnShipmentCost = Double.parseDouble(request.getParameter("rtnShipmentCost").trim());
                        System.out.println("rtnShipmentCost: " + rtnShipmentCost);
                    } catch (Exception e) {
                        rtnShipmentCost = 0.0;
                    }
                    aascShipmentPackageInfo.setRtnShipmentCost(rtnShipmentCost);
                    

                    String rtnShipToCompany = "";
                    try {
                        rtnShipToCompany = request.getParameter("rtnShipToCompany").trim();
                        System.out.println("rtnShipToCompany: " + rtnShipToCompany);
                    } catch (Exception e) {
                        rtnShipToCompany = "";
                    }
                    aascShipmentPackageInfo.setRtnShipToCompany(rtnShipToCompany);

                    String rtnShipToContact = "";
                    try {
                      rtnShipToContact = request.getParameter("rtnShipToContact").trim();
                    } catch (Exception e) {
                        rtnShipToContact = "";
                    }
                    aascShipmentPackageInfo.setRtnShipToContact(rtnShipToContact);

                    String rtnShipToLine1 = "";
                    try {
                      rtnShipToLine1 = request.getParameter("rtnShipToLine1").trim();
                    } catch (Exception e) {
                        rtnShipToLine1 = "";
                    }
                    aascShipmentPackageInfo.setRtnShipToLine1(rtnShipToLine1);

                    String rtnShipToLine2 = "";
                    try {
                      rtnShipToLine2 = request.getParameter("rtnShipToLine2").trim();
                    } catch (Exception e) {
                        rtnShipToLine2 = "";
                    }
                    aascShipmentPackageInfo.setRtnShipToLine2(rtnShipToLine2);

                    String rtnShipToCity = "";
                    try {
                      rtnShipToCity = request.getParameter("rtnShipToCity").trim();
                    } catch (Exception e) {
                        rtnShipToCity = "";
                    }
                    aascShipmentPackageInfo.setRtnShipToCity(rtnShipToCity);

                    String rtnShipToState = "";
                    try {
                        rtnShipToState = request.getParameter("rtnShipToState").trim();
                        System.out.println("rtnShipToState: " + rtnShipToState);
                    } catch (Exception e) {
                        rtnShipToState = "";
                    }
                    aascShipmentPackageInfo.setRtnShipToState(rtnShipToState);

                String rtnShipToZip = "";
                try {
                    rtnShipToZip = request.getParameter("rtnShipToZip").trim();
                    System.out.println("rtnShipToZip: " + rtnShipToZip);
                } catch (Exception e) {
                    rtnShipToZip = "";
                }
                aascShipmentPackageInfo.setRtnShipToZip(rtnShipToZip);
                
                String rtnCountrySymbol = "";
                try {
                    rtnCountrySymbol = request.getParameter("rtnCountrySymbol").trim();
                    System.out.println("rtnCountrySymbol: " + rtnCountrySymbol);
                } catch (Exception e) {
                    rtnCountrySymbol = "";
                }
                aascShipmentPackageInfo.setRtnCountrySymbol(rtnCountrySymbol);

                    String rtnShipToPhone = "";
                    try {
                        rtnShipToPhone = request.getParameter("rtnShipToPhone").trim();
                        System.out.println("rtnPayMethod: " + rtnShipToPhone);
                    } catch (Exception e) {
                        rtnShipToPhone = "";
                    }
                    aascShipmentPackageInfo.setRtnShipToPhone(rtnShipToPhone);

                  session.setAttribute("aascPkgDetails",aascShipmentPackageInfo);
                
//                request.setAttribute("packCount",pkgSequenceId);
//                request.setAttribute("orderNum",orderNumber);
                
//               opStatus = aascHazmatPackageDAO.saveHazmatPackageDetails(aascHazmatPackageInfo,pkgSequenceId, orderNumber);
  
//                if (opStatus == 120) {
//                    System.out.println("Successfully Saved details");
//                   LinkedList HazmatList=new LinkedList();
//                    HazmatList = aascHazmatPackageDAO.getHazmatShipmentDetails(pkgSequenceId, orderNumber);
//                    session.setAttribute("comments", "Successfully Saved Values");
//                    session.setAttribute("ACTION", "SAVE");
//                } else {
//                    System.out.println("Got Error in Saving commodity item Values");
//                   
//                    session.setAttribute("comments", "Error in Saving of Values");
//                } 
                  
            }
            catch (Exception e) {
                logger.severe("Got Exception In Save action" +e.getMessage());
                return "error";
            }
          
            return "success";
        }
        
        public String deleteAction(HttpServletRequest request,HttpSession session){
            try{
                aascShipmentPackageInfo = new AascShipmentPackageInfo();
                aascHazmatPackageDAO = new AascOracleHazmatPackageDAO();
                String pkgCount=request.getParameter("packCount");
                System.out.println("pkgCount::"+pkgCount);
                int pkgSequenceId = Integer.parseInt(pkgCount);  
                System.out.println("pkgSequenceId::"+pkgSequenceId);
                String orderNumber=request.getParameter("orderNumber1");
                System.out.println("orderNumber::"+orderNumber);
               
                String commodityValueStr = request.getParameter("commodityLine").trim();
                int commodityValue = Integer.parseInt(commodityValueStr);
                System.out.println("commodityValue = " + commodityValue);
        
                opStatus = aascHazmatPackageDAO.deleteHazmatPackageDetails(pkgSequenceId, orderNumber, commodityValue);
        System.out.println("opStatus::"+opStatus);
//                request.setAttribute("packCount",pkgSequenceId);
//                request.setAttribute("orderNum",orderNumber);
                request.setAttribute("commodityValue",commodityValue);
                if (opStatus == 120) {
                    System.out.println("Successfully Deleted Commodity Item");
                    
                    LinkedList HazmatList=new LinkedList();
                    HazmatList = aascHazmatPackageDAO.getHazmatShipmentDetails(pkgSequenceId, orderNumber);
                    session.setAttribute("comments", "Successfully Deleted Values");
                    session.setAttribute("ACTION", "DELETE");
                } else {
                    System.out.println("Got Error in deleting commodity item Values");
                   
                    session.setAttribute("comments", "Error in Deletion of Values");
                }
            } catch (Exception e) {
                logger.severe("Got Exception In delete Action" +e.getMessage());
                return "error";
            }

            return "success";
        }

    }
