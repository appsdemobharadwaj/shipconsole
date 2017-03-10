<%@page import="com.aasc.erp.model.*"%>
<%
/*
  DESCRIPTION                                                              
    JSP to get Hazardous material all values on selecting Id from data base
    Author Suman G  27/01/2015                            
    Version - 1                                                          
    History         
    
*/
%>
<%@page import="com.aasc.erp.util.*"%>
<%@page import="java.util.logging.*"%>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxHazMatValues"); %>
  <% 
     
     String carrierCodeStr = "";
     String lookUpMeaning = "";
     String hazMatId = "";
     int locationId =0;
     int userId=0;
     int clientIdInt=0;
     logger.info("called jsp");
     
     try{
     userId=(Integer)session.getAttribute("user_id");
    
    clientIdInt=(Integer)session.getAttribute("client_id");
      String clientId = ""+clientIdInt;
     }catch(Exception e){
        logger.severe("Exception:"+e.getMessage());
     }
         
        
     carrierCodeStr = (String) request.getParameter("carrierCode");
     
     hazMatId = (String)request.getParameter("hazMatId");
     
     locationId = Integer.parseInt(request.getParameter("locationId"));
     
//     if(lookUpMeaning.equalsIgnoreCase("ACCESSIBLE"))
//     {
//       lookUpMeaning = "ACC";
//     }
//     else if(lookUpMeaning.equalsIgnoreCase("INACCESSIBLE"))
//     {
//       lookUpMeaning = "INACC";
//     }
          
     int carrierCode = Integer.parseInt(carrierCodeStr);
          
     java.util.HashMap lookupValues = null;
     String completeDetails="";

     AascOracleProfileOptionsDAO aascOracleProfileOptionsDAO = new AascOracleProfileOptionsDAO();
     completeDetails = aascOracleProfileOptionsDAO.getHazMatLookUpValues(carrierCode,hazMatId,locationId,clientIdInt,userId);
    logger.info("complete details:::::::::::"+completeDetails);
    if(completeDetails.equals(""))
    {
    out.write("**"); 
    }
    else
    {
    out.write(completeDetails);
    }
%>
  
  
