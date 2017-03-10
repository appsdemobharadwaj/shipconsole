
/*
  DESCRIPTION
    AascOracleGetShipMethodInfo class is used to get the ship method details from database and
    concatenating them to a string.
    Author 
    Version - 1
    History
    
    07/01/15   Y Pradeep  Merged Sunanda's code : Converted methods into Spring JDBC.
    19/01/2015 Suman G    Removed unnecessary comments and added documentation for getShipMethodInfo() method
*/

package com.aasc.erp.model;


import com.aasc.erp.util.AascLogger;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


public class AascOracleGetShipMethodInfo {
  
    private String shipMethodIds = "@@@";
    private int carrierId;
    private int clientId;
    SimpleJdbcCall simpleJdbcCall;
    private AascOracleDAOFactory aascOracleDAOFactory = 
        new AascOracleDAOFactory(); // Creating a object of AascOracleDAOFactory class.
    private static Logger logger = 
        AascLogger.getLogger("AascOracleGetShipMethodInfo");
   
    public AascOracleGetShipMethodInfo() {
    }

    /**
    
     * This Method implements by the  AascOracleGetShipMethodInfo class.
     * 
     * It is used for get shipmethod info
     *
     * @return String
     *
     * @param carrierId int, clientId int
     */
    public String getShipMethodInfo(int carrierId, int clientId) {
        logger.info("Entered getShipMethodInfo()");
        this.carrierId = carrierId;
        this.clientId = clientId;

        try {

            DataSource dataSource = aascOracleDAOFactory.createDataSource();
            if (carrierId != 0)
            {

                SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_client_id", this.clientId)
                .addValue("ip_carrier_code", this.carrierId);
                simpleJdbcCall = new SimpleJdbcCall(dataSource).withCatalogName("aasc_erp_ship_console_pkg")
                                                               .withProcedureName("get_ship_methods")
                                                               .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                                               .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR))
                                                               .declareParameters(new SqlOutParameter("aasc_ship_methods",OracleTypes.CURSOR));
                Map<String, Object> out = simpleJdbcCall.execute(inputparams);
                
                Iterator shipMethodIt = ((ArrayList)out.get("OP_SHIP_METHODS")).iterator();
                HashMap<String, ?> map1 = null;
                while (shipMethodIt.hasNext()) {
                    map1 = (HashMap<String, ?>)shipMethodIt.next();
                    shipMethodIds = shipMethodIds + map1.get("user_shipmethod_meaning") + "***";
                }
                
             }

            
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } 
        logger.info("Exit from getShipMethodInfo()");
        return shipMethodIds;
    }

}
