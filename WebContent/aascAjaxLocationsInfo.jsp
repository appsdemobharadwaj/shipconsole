<%
/*===========================================================================================================================================================+
|  DESCRIPTION                                                                                                                                               |
|    JSP to get locations information for the given clientId                                                                                                  |
|    Author Eshwari M                                                                                                                                        |
|    Version   1.1                                                                                                                                           |     
|    Creation  28/11/2014                                                                                                                                    | 
|                                                                                                                                                            | 
|  HISTORY                                                                                                                                                   |
|   30/11/2014   Eshwari M      Formatted the code and ran code audit                                                                                        |
+============================================================================================================================================================*/
%>

<%@include file ="aascHeader.jsp" %>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxShipMethodInfo"); %>
<%
    int roleId = (Integer)session.getAttribute("role_id"); 
    String clientIdStr = request.getParameter("clientId");
    String locationIds = "@@@";
    logger.info("clientIdStr : "+clientIdStr);
    logger.info("roleId : "+roleId);
    int clientId =0;
    try
    {
     clientId = Integer.parseInt(clientIdStr);
    }
    catch(Exception e)
    {
    //e.printStackTrace();
    clientId =0;
    }
   
    if(roleId != 4 && roleId != 5)
    {
         AascDAOFactory aascDAOFactory=AascDAOFactory.getAascDAOFactory(1);;
         AascGetLocDAO aascGetLocDAO = aascDAOFactory.getLocationDAO();
         AascGetLocInfo aascGetLocInfo =aascGetLocDAO.getLocationInfo(clientId);
         LinkedList locationDetails = aascGetLocInfo.getlocationDetails();
         AascGetLocInfo aascGetLocInfoBean = null;
         Iterator locations = locationDetails.iterator();
         while(locations.hasNext())
         {
      
        aascGetLocInfoBean = (AascGetLocInfo)locations.next();
        locationIds = locationIds + aascGetLocInfoBean.getLocationName() + "$";
        locationIds = locationIds + aascGetLocInfoBean.getlocationId() + "***";
         }
     
    }
    else
    {
    AascOracleGetTrackInfo aascOracleGetTrackInfo= new AascOracleGetTrackInfo();
    
     locationIds = aascOracleGetTrackInfo.getlocationsInfo(clientId);
    }
    //logger.info("Location IDs options=="+locationIds);
    
    out.write(locationIds);
    
%>
