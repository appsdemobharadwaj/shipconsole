<%@include file ="aascHeader.jsp" %>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxHazMatPackingGroup"); %>
<%
/*
$Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/aascAjaxHazMatPackingGroup.jsp,v 1.2 2015/01/07 10:34:26 sgunda Exp $

  DESCRIPTION                                                              
    JSP to get Hazardous material Packing Group from data base
    Author Pavan J 09/03/2009                               
    Version - 2                                                            
    History  
    10/06/09   Madhavi    Modified logging code
*/
%>
 
  <%
     
     String carrierCodeStr = "";
     String lookUpMeaning = "";
     String hazMatId = "";
     int InvOrgId =0;
     carrierCodeStr = (String) request.getParameter("carrierCode");
     
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
     
     AascOracleProfileOptionsDAO aascOracleProfileOptionsDAO = new AascOracleProfileOptionsDAO();
     lookupValues = aascOracleProfileOptionsDAO.getHazMatPkgGroupLookUpValues();
     
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
     logger.info("Hazmat Packing Group options ::"+options);
     out.write(options);
     
  
  %>
  
  
