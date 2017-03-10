package com.aasc.erp.model;

import com.aasc.erp.bean.AascCustomerAccountNumbers;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.StoredProcedure;

/**
 * AascOracleCustAccountNumsDAO class is DAO factory class class for Customer Account Number.
 * @version   2
 * @author    Venkateswaramma Malluru
 * History:
 *
 * 19/12/2014   Y Pradeep   Modified nullStrToSpc method
 * 03/02/2015   Suman G     Removed sop's
 * 23/03/2015   Sunanda K   Added code for newly craeted fields adress line 3 and email address.
 * 13/04/2015   Y Pradeep   Added nullStrToSpc to recipient and third party feilds while saving in saveCustAccountNums method.
 * 13/05/2015   Y Pradeep   Renamed locationId1 to customerLocationid to diffentiate ship from location id and ship to location id.
 */

public class AascOracleCustAccountNumsDAO  extends StoredProcedure implements AascCustAccountNumsDAO{
    public AascOracleCustAccountNumsDAO() {
    }
    SimpleJdbcCall simpleJdbcCall;
    AascOracleDAOFactory connectionFactory = new AascOracleDAOFactory(); 
    
    public String saveCustAccountNums(AascCustomerAccountNumbers aascCustomerAccountNumbers){
        
        
        
        DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object

        SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_CUSTOMER_LOCATION_ID",aascCustomerAccountNumbers.getLocationId())
                                                                    .addValue("ip_carrier_code",aascCustomerAccountNumbers.getCarrierCode())
                                                                    .addValue("ip_receipent",nullStrToSpc(aascCustomerAccountNumbers.getRecipient()))
                                                                    .addValue("ip_third_party",nullStrToSpc(aascCustomerAccountNumbers.getThirdParty()));
                                                                    
                  
                simpleJdbcCall = new SimpleJdbcCall(datasource)
                               .withCatalogName("AASC_ERP_CUSTOMER_LOCATION_PKG")
                               .withProcedureName("save_update_cust_acct_num")
                               .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                               .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
                             //  .declareParameters(new SqlOutParameter("aasc_account_number_details", OracleTypes.CURSOR));
        
        Map<String,Object> out = simpleJdbcCall.execute(inputparams);
       // setQueryTimeout(queryTimeOut);
//        System.out.println("After Execute");
//        System.out.println("out ::: "+out.toString());
        int opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));
        String error_status =  String.valueOf(out.get("op_error_status"));
        logger.info("opStatus :::"+opStatus+" && "+error_status);
        
        if(opStatus==900 || opStatus==901)
        return "success";
        else
            return "error";
        
    }
    
    
    public LinkedList getCustAccountNums(int customerLocationid){
     
        LinkedList custAccNumlist = new LinkedList();
       
        DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object

        SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_CUSTOMER_LOCATION_ID",customerLocationid);
                                                                    
                                                                  
                  
                simpleJdbcCall = new SimpleJdbcCall(datasource)
                               .withCatalogName("AASC_ERP_CUSTOMER_LOCATION_PKG")
                               .withProcedureName("get_cust_account_numbers")
                               .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                               .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                               .declareParameters(new SqlOutParameter("aasc_cust_account_nums", OracleTypes.CURSOR));
        
        Map<String,Object> out = simpleJdbcCall.execute(inputparams);
        // setQueryTimeout(queryTimeOut);
        logger.info("After Execute");
//        logger.info("out ::: " + out.toString());
        int opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));
        String error_status =  String.valueOf(out.get("op_error_status"));
        
        Iterator it= ((ArrayList)out.get("OP_CUST_ACCOUNT_NUMS")).iterator();
        //     HashMap hm=new HashMap();
        
        while(it.hasNext()){
        HashMap<String,?>  map1 =(HashMap<String,?>)it.next();
            if(map1.get("CUSTOMER_LOCATION_ID")!=null){
        int location = ((BigDecimal)map1.get("CUSTOMER_LOCATION_ID")).intValue();
            int carriercode = ((BigDecimal)map1.get("CARRIER_CODE")).intValue();
            String recipient = nullStrToSpc((String)map1.get("RECEIPENT"));
            String thirdParty = nullStrToSpc((String)map1.get("THIRDPARTY"));
            
            
            AascCustomerAccountNumbers acan = new AascCustomerAccountNumbers();
            
              acan.setLocationId(location);          
               acan.setCarrierCode(carriercode);
               acan.setRecipient(recipient);
               acan.setThirdParty(thirdParty);
               
               
            custAccNumlist.add(acan);
            }else
                custAccNumlist=null;
            
        }
        logger.info("opStatus :::"+opStatus+" && "+error_status); 
        
  
  return custAccNumlist;      
    }
    
    public String nullStrToSpc(Object obj) {
        String spcStr = "";
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }

}
