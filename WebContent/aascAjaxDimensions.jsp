<%@include file ="aascHeader.jsp" %>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxDimensions"); %>
<%
/*
$Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/aascAjaxDimensions.jsp,v 1.4 2015/02/17 11:48:40 vmalluru Exp $

  DESCRIPTION                                                              
    JSP to get Dimensions from data base based on location selected through Ajax call
    Author Eshwari  30/12/2014                                 
    Version - 2                                                            
    History             
    
    03/02/2015      Suman G         Put "aascPackageDimensionInfo" object in session to fix #2505
*/
%>

<%
     
     String locationId = "";
     locationId = (String)request.getParameter("locationId");    
     int locationIdInt = Integer.parseInt(locationId);
     Integer clientIdInt=(Integer)session.getAttribute("client_id");
     String clientId = ""+clientIdInt;
     
     try
     {
       String testclientId=clientId.substring(0,1);
     }
     catch(Exception e)
     {
       clientIdInt=(Integer)session.getAttribute("client_id");
     }
     
     int roleId = (Integer)session.getAttribute("role_id");
     int queryTimeOut = Integer.parseInt((String)session.getAttribute("queryTimeOut"));
     
     ListIterator dimensionIterator = null;
   
     
     LinkedList list = new LinkedList();
     list.add(locationIdInt);
     list.add(clientIdInt);
     
     AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
     AascShipMethodDAO aascshipMethodDAO = aascDAOFactory.getAascShipMethodDAO();
     
     AascPackageDimensionDAO aascPackageDimensionDAO =  aascDAOFactory.getPackageDimensionDAO();
     AascPackageDimensionInfo aascPackageDimensionInfo = aascPackageDimensionDAO.getPackageDimensionInfo(locationIdInt, roleId, clientIdInt);
     session.setAttribute("aascPackageDimensionInfo", aascPackageDimensionInfo);
     session.setAttribute("unitList", (Object)aascPackageDimensionInfo.getUnitDetails());
     ListIterator dimensionIteratorTemp=(aascPackageDimensionInfo.getPackageDimensionList()).listIterator();                 
     
     LinkedList linkedListTemp=new LinkedList();
     
     AascPackageDimensionInfo aascPackageDimensionInfoTemp = null ;
     
     String option = "@@@";
     while(dimensionIteratorTemp.hasNext())
     {
         aascPackageDimensionInfoTemp =(AascPackageDimensionInfo)dimensionIteratorTemp.next();
              
         option = option + aascPackageDimensionInfoTemp.getDimensionName()+"||"+aascPackageDimensionInfoTemp.getDimensionLength()
                         +"*"+aascPackageDimensionInfoTemp.getDimensionWidth()+"*"+aascPackageDimensionInfoTemp.getDimensionHeight()
                         +"#######"+aascPackageDimensionInfoTemp.getDimensionUnits()+"^^^^^"+aascPackageDimensionInfoTemp.getDimensionDefault()
                         +"###"+aascPackageDimensionInfoTemp.getDimensionId()+"@@@@@@@";
    
     }
     
     logger.info("options in aascAjaxDimensions.jsp ::"+option+":: options");
  
     out.write(option);
  
  %>
  
  
