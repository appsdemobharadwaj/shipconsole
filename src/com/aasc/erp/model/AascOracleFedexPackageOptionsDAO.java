package com.aasc.erp.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import com.aasc.erp.util.AascLogger;
import java.sql.Types;
import java.util.Map;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

 /**
  * AascOracleFedexPackageOptionsDAO is a class which used for fedex hazmat profile material packages saving purpose.
  * @author
  * @version 1.0
  * @since
  * 
  * @History
  * 
  * 07/011/2015  Y Pradeep      Merged Sunand's code: Converted the methods into Spring JDBC.
  * 19/01/2015   Suman G        Removed printStackTrace().
  * 27/01/2015   Suman G        Modified getDefaultHazMatId() method for #2533.
 */

public class AascOracleFedexPackageOptionsDAO implements AascFedexPackageOptionsDAO{

    SimpleJdbcCall simpleJdbcCall;

    public AascOracleFedexPackageOptionsDAO() {
    }

    Connection connection=null;
    CallableStatement callableStatement=null;
    AascOracleDAOFactory aascOracleDAOFactory=null;
    private static Logger logger = AascLogger.getLogger("AascOracleFedexPackageOptionsDAO");
    //private String schemaname = AascProps.getSchemaName();
    String defaultHazMatId="";

    public String getDefaultHazMatId(int userId, int locationID,int clientID) {
       logger.info("Entered defaultHazMatId");
      try
      {
                DataSource dataSource = aascOracleDAOFactory.createDataSource();
                SqlParameterSource inputparams = new MapSqlParameterSource()
                                              .addValue("ip_location_id", locationID)
                                              .addValue("ip_user_id", userId)
                                              .addValue("ip_client_id",clientID);
                
                simpleJdbcCall =new SimpleJdbcCall(dataSource).withCatalogName("aasc_erp_ship_console_pkg")
                .withFunctionName("get_hazmat_profile_material_id")
                .declareParameters(new SqlOutParameter("l_profile_option_value",Types.VARCHAR));
                Map<String, Object> out = simpleJdbcCall.execute(inputparams);
                defaultHazMatId = (String)out.get("l_profile_option_value"); 
      }    
      catch(Exception e)
      {  
           logger.severe("Exception :"+e.getMessage());
          defaultHazMatId="";
      }
      defaultHazMatId = "---Select Id---";   
     return defaultHazMatId;
  }
}
