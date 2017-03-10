<%@include file ="aascHeader.jsp" %>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxShipMethod"); %>
<%
/*
$Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/aascAjaxShipMethod.jsp,v 1.4 2015/04/28 11:56:31 vmalluru Exp $

  DESCRIPTION                                                              
    JSP to get Ship Methods from data base
    Author Sambit  29/05/2008                                 
    Version - 2                                                            
    History             
    11/03/09   Madhavi    Modified logging code  
    19/11/14   Eshwari    Added file from Cloud 1.2 version
    16/02/15   Suman G    Modified locaiton_id to location_id_selected to fix #2532    
    28/04/15   Eshwari M  Added condition to give alert to user when carrier configuration is not configured 
*/
%>

<%
     
     String locationId = "";
     String intlFlag = (String) request.getParameter("intlFlag");
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
     ListIterator ShipMethodIterator = null;
   
     /*AascOracleShipMethodAjax aascOracleShipMethodAjax = new AascOracleShipMethodAjax();
     AascShipMethodInfo aascShipMethodInfo = aascOracleShipMethodAjax.getShipMethodInfo(locationIdInt, clientIdInt);*/
     
     LinkedList list = new LinkedList();

     session.setAttribute("location_id_selected",locationIdInt);   // For return shipmethod done this one.
     list.add(locationIdInt);
     list.add(clientIdInt);
     
     AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
     AascShipMethodDAO aascshipMethodDAO = aascDAOFactory.getAascShipMethodDAO();

     AascShipMethodInfo aascShipMethodInfo = aascshipMethodDAO.getShipMethodInfo(list); 
     session.setAttribute("ShipMethodInfo",aascShipMethodInfo);   // For return shipmethod done this one.
     if(intlFlag.equalsIgnoreCase("Y"))
     {
          ShipMethodIterator = aascShipMethodInfo.getIntlDetailList().listIterator();
     }
     else  
     {
         ListIterator shipMethodIteratorTemp=(aascShipMethodInfo.getShipMethodDetailList()).listIterator();                 
         LinkedList linkedListTemp=new LinkedList();
         while(shipMethodIteratorTemp.hasNext())
         {
           AascShipMethodInfo aascShipMethodInfoTemp=(AascShipMethodInfo)shipMethodIteratorTemp.next();
           if(!intlFlag.equalsIgnoreCase("Y") && aascShipMethodInfoTemp.getEnabledFlag().equalsIgnoreCase("Y") && !aascShipMethodInfoTemp.getIntlShipFlag().equalsIgnoreCase("Y") )
           {
             linkedListTemp.add(aascShipMethodInfoTemp); 
           }
         }
         ShipMethodIterator=linkedListTemp.listIterator();
     }
     
     String option = "@@@";
     while(ShipMethodIterator.hasNext()){
              AascShipMethodInfo  aascShipMethodInfoBeanTest = (AascShipMethodInfo)ShipMethodIterator.next();
              String shipMethodElementAltTest=(String) aascShipMethodInfoBeanTest.getAlternateShipMethod();//getShipMethodMeaning();// 
              String shipMethodElement = (String)aascShipMethodInfo.getShipMethodFromAlt(shipMethodElementAltTest);
              int carrierId=aascShipMethodInfo.getCarrierId(shipMethodElement);
              int carrierCode=aascShipMethodInfo.getCarrierCode(carrierId);
              String shipMethodValidation= aascShipMethodInfo.getShipmentValidationCode(shipMethodElement);
              String carrierAccountNumber=aascShipMethodInfo.getCarrierAccountNumber(carrierId);
              
              //5_7 Merging - Adhoc Bug fix - Passing carrier id 
              option = option+carrierCode+"||"+shipMethodValidation+"*"+shipMethodElementAltTest+"@@"+carrierId+"#######"+shipMethodElementAltTest+"^^^^^"+carrierAccountNumber+"@@@@@@@";
                                                   
     }
     if(aascShipMethodInfo.getOpStatus() == 2)  // Added this condition to give alert to user when carrier configuration is not configured
     {
        option = option+"Carrier Configuration not Configured" ;
     }
     logger.info("options in aascAjaxShipMethod.jsp ::"+option+":: options");
  
     out.write(option);
  
  %>
  
  
