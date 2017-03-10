package com.aasc.erp.delegate;

import com.aasc.erp.bean.AascCustomerAccountNumbers;

import com.aasc.erp.model.AascCustAccountNumsDAO;
import com.aasc.erp.model.AascOracleCustAccountNumsDAO;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*========================================================================+
@(#)AascCustAccountNumsDelegate.java 28/11/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
* author Sunanda K
* 13/05/2015   Y Pradeep   Renamed locationId1 to customerLocationid to diffentiate ship from location id and ship to location id.
+===========================================================================*/

public class AascCustAccountNumsDelegate {
    public AascCustAccountNumsDelegate() {
    }
    
    
    public String saveCustAccountNumbers(HttpServletRequest request){
        
        
   AascCustAccountNumsDAO aascCustAccountNumsDAO= new AascOracleCustAccountNumsDAO();
        String size1 = request.getParameter("size");  
        String result ="";
        String loc = request.getParameter("locationId");
        

        
        int customerLocationid = Integer.parseInt(loc);
        int size= Integer.parseInt(size1);
        String status="";
        for(int i=1;i<size;i++){
            
            AascCustomerAccountNumbers acan = new AascCustomerAccountNumbers();
            String carrierCode = request.getParameter("carrierCode"+i);  
            String recipient = request.getParameter("recipient"+i);  
            String thirdParty = request.getParameter("thirdParty"+i); 
            
            
            int carrierCode1= Integer.parseInt(carrierCode);
         
           acan.setLocationId(customerLocationid);          
            acan.setCarrierCode(carrierCode1);
            acan.setRecipient(recipient);
            acan.setThirdParty(thirdParty);
            
           status =  aascCustAccountNumsDAO.saveCustAccountNums(acan) ;
            
          if("success".equalsIgnoreCase(status)){
              result="success";  
          }else{
          
              result="error";  
              break;
              
          }
            
        }
        
        
        
        
        return result;
        
    }
    
    
    public String getCustAccountNums(HttpServletRequest request,HttpSession session){
        
        AascCustAccountNumsDAO aascCustAccountNumsDAO= new AascOracleCustAccountNumsDAO();
        String loc = request.getParameter("locationId");
        int customerLocationid = Integer.parseInt(loc);
        
  LinkedList custAccNumList =    aascCustAccountNumsDAO.getCustAccountNums(customerLocationid);
        
        session.setAttribute("custAccNumList",(Object)custAccNumList);
        
        if(custAccNumList.isEmpty())
            return "error";
            else
  return "success";
  
  
    }
}
