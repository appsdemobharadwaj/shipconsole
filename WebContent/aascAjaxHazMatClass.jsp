<%@include file ="aascHeader.jsp" %>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxHazMatClass"); %>
<%
/*
$Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/aascAjaxHazMatClass.jsp,v 1.1.1.1.6.1 2015/10/19 08:18:32 gthirtham Exp $

  DESCRIPTION                                                              
    JSP to get Hazardous material Classes from data base
    Author Sambit  26/07/2008                                 
    Version - 2                                                            
    History  
    10/06/09   Madhavi    Modified logging code
    20/11/14   Eshwari    Added this class from cloud 1.2 version
*/
%>

 

  <%
     
     String carrierCodeStr = "";
     String lookUpMeaning = "";
     
     carrierCodeStr = (String) request.getParameter("carrierCode");
    String carrierMode = request.getParameter("Mode");
     lookUpMeaning = (String)request.getParameter("lookUpMeaning");
     if(lookUpMeaning.equalsIgnoreCase("ACCESSIBLE"))
     {
       lookUpMeaning = "ACC";
     }
     else if(lookUpMeaning.equalsIgnoreCase("INACCESSIBLE"))
     {
       lookUpMeaning = "INACC";
     }
          
     int carrierCode = Integer.parseInt(carrierCodeStr);
          
     HashMap lookupValues = null;
     
     
     /*AascOracleProfileOptionsDAO aascOracleProfileOptionsDAO = new AascOracleProfileOptionsDAO();
     lookupValues = aascOracleProfileOptionsDAO.getCarrierLookUpValues(carrierCode,lookUpMeaning); */
     AascCarrierConfigurationDAO aascCarrierConfigurationDAO= new AascOracleCarrierConfigurationDAO();
     lookupValues = aascCarrierConfigurationDAO.getCarrierLookUpValues(carrierCode, lookUpMeaning, carrierMode);
     
     
     Set set = lookupValues.keySet();
     Iterator setIterator = set.iterator();
     String options = "@@@";
     String hashMapKey = "";
     String hashMapValue = "";
     while(setIterator.hasNext())
     {
           hashMapKey = (String)setIterator.next();
           hashMapValue = (String)lookupValues.get(hashMapKey);
           options = options+hashMapValue+"***";
     }
     
     logger.info("Hazmat class options ::"+options);
     //out.write(lookupValues);
     out.write(options);
     //out.write(options);
  
  %>
  
  
