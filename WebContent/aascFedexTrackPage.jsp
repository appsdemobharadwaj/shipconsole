 
<!-- $Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/aascFedexTrackPage.jsp,v 1.3.6.1 2016/01/05 08:06:37 gthirtham Exp $ -->
<%/*========================================================================+
|  DESCRIPTION                                                              |
|    JSP to display tracking info from Fedex                                |
|    Author Eshwari M                                                       |
|    Version - 1.0                                                          |
|    History                                                                |
|        -17/12/2014 Eshwari    Added this file from Cloud 1.2 for SC Lite  |
|        -19/01/2015 Y Pradeep  Modified code to remove unused variables, getting label names from Property file and commentes as suggested in Code Review document|
|        -20/01/2015 Y Pradeep  Modified auther and version, also removed commented code.
+===========================================================================*/%>
<%@include file ="aascHeader.jsp" %>
<%@ page errorPage="aascShipError.jsp" %>
<link type="text/css" rel="stylesheet" href="aasc_Apps.css">
<%@ taglib uri="/struts-tags" prefix="s" %>

<%!
  private static Logger logger=AascLogger.getLogger("aascFedexTrackPage");//logger object for issuing logging requests
  private String url="";//holds current context path in which error page is present
  private AascFedexTrackingInfo aascFedexTrackingInfo=null;//tracking information bean containing parsed response data  
  private String actionType="";//contains either TRACKING or POD or WAYBILL   
  private String urlPath="";//contains pod output file path to which response is redirected   
  private String tableCss="";//contains style name used to display the table  
  //contains date which is used to check the date and display information accordingly
  private String originalDate="";//scanning done on same day but diffent timings are consolidated and displayed
  private LinkedList scanNodeLinkedList=null;//linked list that contains package progress information
  private ListIterator scanNodeListIterator=null;//used to iterate through linked list 
  private AascFedexTrackingInfo aascFedexTrackingScanInfo=null;//tracking information bean containing parsed package progress information  
  private String responseStatus="";//contains status of the response
  private String lastActivityStatus="";//contains last activity status of the tracked package
  private LinkedList linkedList=null;//linked list used that contains package tracking information
  private ListIterator listIterator=null;//
  private int packageCount=0;//
%>

<%      
  url = request.getContextPath();
  if(session.isNew()|| !(session.getId().equals(session.getAttribute("SessionId"))))
  {       
       response.sendRedirect(url+"/aascShipError.jsp");   
  }//end 
     
  try
  {
    //tracking information bean object 
    aascFedexTrackingInfo=(AascFedexTrackingInfo)session.getAttribute("TrackingInformation"); 	 
  }
  catch(Exception e)
  {
    logger.severe("error in retreiving the data from session:"+e.getMessage());
  }
     
  if(aascFedexTrackingInfo==null)
  {
      logger.info("tracking information bean is null");
  %>
  <h1><font color='red'>Error!Tracking Information Bean is Null</font></h1>
  <%
  }//end 
  else
  {      
    responseStatus=aascFedexTrackingInfo.getResponseStatus();
    if(responseStatus!=null&&!responseStatus.equals("")&&!responseStatus.equalsIgnoreCase("success"))
    {
      out.println("<font color='red'><h3><center>"+responseStatus+"</center></h3></font>");    
    }//end 
    else
    {
      actionType=aascFedexTrackingInfo.getActionType();
      logger.info("actionType in tracking jsp"+actionType);
      if(actionType.equals("POD"))
      {
        logger.info("if(actionType.equals('POD'))");
        responseStatus=aascFedexTrackingInfo.getResponseStatus();
        if(!(responseStatus.equalsIgnoreCase("success")))
        {
          out.println("<font color='red'><center><h3>"+responseStatus+"</h3></center></font>");
        }//end 
             else
        {            
          String carrierMode=(String)request.getAttribute("carrierMode");
        
          response.sendRedirect(url+"/fedexPODPage.jsp?carrierMode="+carrierMode);
        }//end 
    }//end
    else if (actionType.equals("WAYBILL"))//if user clicks on waybill button
    {
      logger.info("inside else if (actionType.equals('WAYBILL'))");
      responseStatus=aascFedexTrackingInfo.getResponseStatus();
      if(!(responseStatus.equalsIgnoreCase("success")))
      {
      if(responseStatus==null || responseStatus.equals(""))
      responseStatus="No Tracking Information Available!";   
      out.println("<font color='red'><h3><center>"+responseStatus+"</center></h3></font>");
      }//end 
      else
      {
      tableCss="tableDataTrackCell";   
      lastActivityStatus=aascFedexTrackingInfo.getStatus();
      //retreiving way bill list containing information of all the packages in that order
      scanNodeLinkedList=aascFedexTrackingInfo.getScanNodeLinkedList();
      if(scanNodeLinkedList==null)
      {
    %>
    <h1>WayBill List containing package information is null</h1>
    <%
      }
      else
      {
        scanNodeListIterator=scanNodeLinkedList.listIterator();
	packageCount=0;
	while(scanNodeListIterator.hasNext())
        {
	  aascFedexTrackingScanInfo=(AascFedexTrackingInfo)scanNodeListIterator.next();
	  //displaying way bill tracking header information
	  if(packageCount==0)
          {
    %>
    <table width="100%"  border="0">
      <tr>
	<td colspan="2" bgcolor="Purple" height="20"><span class="style1"><s:property value="getText('ViewPackagesShipped')"/></span></td>
      </tr>
      <tr><td colspan="4" bgcolor="Lime" height="2"></td></tr>
      <tr>
  	 <td width="33%"><span class="style4"><s:property value="getText('PackagesInShipment')"/><br></span>
         </td>
        <td width="67%"><%=aascFedexTrackingInfo.getPackageCount()%></td>
     </tr>
     <tr>
          <td><span class="style4"><s:property value="getText('Status')"/></span></td>
          <td><%=aascFedexTrackingInfo.getStatus()%>(<%=aascFedexTrackingInfo.getPackageCount()%>)</td>
     </tr>
     <tr>
          <td><span class="style4"><s:property value="getText('ServiceType')"/></span></td>
          <td><%=aascFedexTrackingInfo.getServiceType()%></td>
      </tr>
      <tr>
          <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
          <td colspan="2">
            <table cellspacing=1 cellpadding=1 width="100%" border=0>
               <tr>
                  <td width="32%" colspan=1 align=center class=tableSmallHeaderCell><s:property value="getText('TrackingNumber')"/></td>
                  <td width="18%" colspan=1 align=center class=tableSmallHeaderCell><s:property value="getText('Status')"/></td>
                  <td class=tableSmallHeaderCell align=center colspan=2><s:property value="getText('OrderInformation')"/></td>
               </tr>
               <%
	}//if(packageCount==0)
				
                    if(tableCss.equals("tableDataTrackCell"))
                        tableCss="tableDataTrackCellChange";
                    else	
                        tableCss="tableDataTrackCell";

                %>			
               <tr>

                  <td class=<%=tableCss%> align=center colspan=1><%=packageCount+1%>&nbsp;.&nbsp;<%=aascFedexTrackingScanInfo.getTrackingNumber()%></td>
                  <td class=<%=tableCss%> align=center colspan=1><%=aascFedexTrackingScanInfo.getScanDescription()%></td>
                  <%
                  if(!lastActivityStatus.equalsIgnoreCase("Delivered"))
                  {//displaying details of packages when package is not yet delivered to customer
                     logger.info("inside if(!lastActivityStatus.equalsIgnoreCase('Delivered'))");
                  %>
                  <td class=<%=tableCss%> width="20%" colspan=1 align=center><div align="left"><s:property value="getText('Location')"/></div></td>
                  <td width="30%" align=center class=<%=tableCss%>><div align="left"><%=aascFedexTrackingScanInfo.getLocation()%></div></td>
              </tr>
 	      <tr>      
		  <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
                  <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
		  <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
		  <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
	      </tr>
	      <%
	 	  }//if(lastActivityStatus.equals("In Transit"))			
                  else
                  {//if(lastActivityStatus.equals("DELIVERED"))
                     logger.info("when waybill lastActivity status is delivered ");
 	      %>
                  <td class=<%=tableCss%> width="20%" colspan=1 align=center><div align="left"><s:property value="getText('DeliveredOn')"/> </div></td>
                  <td width="30%" align=center class=<%=tableCss%>><div align="left"><%=aascFedexTrackingScanInfo.getDate()%></div></td>
              </tr>
              <tr>
                  <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
                  <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
                  <td class=<%=tableCss%> align=center><div align="left"><s:property value="getText('DeliveredTo')"/></div></td>
                  <td class=<%=tableCss%> align=center><div align="left"><%=aascFedexTrackingScanInfo.getLocation()%></div></td>
              </tr>
  	      <tr>
                  <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
                  <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
                  <td class=<%=tableCss%> align=center><div align="left"><s:property value="getText('SignedBy')"/></div></td>
                  <td class=<%=tableCss%> align=center><div align="left"><%=aascFedexTrackingScanInfo.getSignedForBy()%></div></td>
             </tr>

		<%}//end of else if(!lastActivityStatus.equalsIgnoreCase("Delivered"))
		  packageCount++;
 	 }//end of while(scanNodeListIterator.hasNext()) loop
      }//end of else if(scanNodeLinkedList==null)   
     %>  
             <tr>
                 <td class=tableSmallHeaderCell align=center colspan=4 height=12></td>
             </tr>	
           </table>
         </td>
       </tr>
    </table>
    <%
    }//end of else of if(!(responseStatus.equalsIgnoreCase("success")))
  }//else if (actionType.equals("WAYBILL"))
  else
  {
    if(actionType.equals("TRACKING"))
    {
        logger.info("if(actionType.equals('TRACKING'))");
        responseStatus=aascFedexTrackingInfo.getResponseStatus();
        if(!(responseStatus.equalsIgnoreCase("success")))
        {
          out.println("<fonr color='red'><center><h1>"+responseStatus+"</h1></center></font>");
        }//end of if(!(responseStatus.equalsIgnoreCase("success")))
        else
        {          
          logger.info("fedex track header info is not null");
    %>
      <!--displaying package tracking details(header information)-->
  <html>
    <body>
      <table width="70%"  border="0">
      <tr><td><font color="Silver" size="4"><s:property value="getText('Track')"/>&nbsp;<s:property value="getText('Shipments')"/></font></td></tr>
      <tr><td><font color="Purple" size="6"><s:property value="getText('Detailed')"/>&nbsp;<s:property value="getText('Results')"/></font></td></tr>
      <tr><td colspan="4" bgcolor="Purple" height="20"></td></tr>
      <tr><td colspan="4" bgcolor="Lime" height="4"></td></tr>
      <tr>
        <td width="20%"><span class="style4"><s:property value="getText('TrackingNumber')"/>
        </span></td>
        <td width="20%"><%=aascFedexTrackingInfo.getTrackingNumber()%></td>   
        <td width="20%"><span class="style4"><s:property value="getText('Destination')"/>
        </span></td>
        <td width="20%"><%=aascFedexTrackingInfo.getLocation()%></td>
      </tr>
  
      <tr>    
         <td width="20%"><span class="style4"><s:property value="getText('SignedForBy')"/>
        </span></td>
        <td width="20%"><%=aascFedexTrackingInfo.getSignedForBy()%></td>
        
        <td width="20%"><span class="style4"><s:property value="getText('ServiceType')"/>
        </span></td>
        <td width="20%"><%=aascFedexTrackingInfo.getServiceType()%></td>    
      </tr>
      
      <tr>
        <td width="20%"><span class="style4"><s:property value="getText('ShipDate')"/>
        </span></td>
        <td width="20%"><%=aascFedexTrackingInfo.getShipDate()%></td>
        
       <td width="20%"><span class="style4"><s:property value="getText('Pieces')"/>
        </span></td>
        <td width="20%"><%=aascFedexTrackingInfo.getPackageCount()%></td>
      </tr>
  
      <tr>
        <td width="20%"><span class="style4"><s:property value="getText('DeliveryDate')"/>
        </span></td>
        <td width="20%"><%=aascFedexTrackingInfo.getDateTime()%></td> 
        <td width="20%"><span class="style4"><s:property value="getText('Weight')"/>
        </span></td>
        <td width="20%"><%=aascFedexTrackingInfo.getWeightStr()%></td>
      </tr>
         
      <tr>
        <td width="20%"><span class="style4"><s:property value="getText('Status')"/>
        </span></td>
        <td width="20%"><%=aascFedexTrackingInfo.getStatus()%></td>
      </tr>
 
      <tr><td colspan=4 height="12"></td></tr>
      <br><br><br><br><br>
      <tr>
        <td colspan="2">
        <table cellspacing=1 cellpadding=1 width="175%" border=0>
          <tr>
            <td class=tableSmallHeaderCell align=center colspan=1><s:property value="getText('Location')"/><s:property value="getText('DateTime')"/></td>        
            <td class=tableSmallHeaderCell align=center colspan=1><s:property value="getText('Location')"/><s:property value="getText('Activity')"/></td>
            <td class=tableSmallHeaderCell align=center colspan=1><s:property value="getText('Location')"/><s:property value="getText('Location')"/></td>
            <td class=tableSmallHeaderCell align=center colspan=1><s:property value="getText('Location')"/><s:property value="getText('Details')"/></td>
          </tr>
          <%
              logger.info("starting package progress info");
              tableCss="tableDataTrackCellChange";
              originalDate="";
              scanNodeLinkedList=aascFedexTrackingInfo.getScanNodeLinkedList();
              if(scanNodeLinkedList==null)
              {
              logger.info("inside if(scanNodeLinkedList==null)");
         %>
              <h1>package progress information is not available!</h1>
         <%   }//end of if(scanNodeLinkedList==null)
              else
              {
                  logger.info("scanNodeLinkedList is not null");
                  scanNodeListIterator=scanNodeLinkedList.listIterator();
                  //retreiving package progress information from shipper to customer's destination
                  while(scanNodeListIterator.hasNext())
                  {             
                      aascFedexTrackingScanInfo=(AascFedexTrackingInfo)scanNodeListIterator.next();
 	%>
          <tr>
	<%
                      if (originalDate.equals("") || !originalDate.equals(aascFedexTrackingScanInfo.getDate()))
                      {
                          originalDate=aascFedexTrackingScanInfo.getDate();
                        
                          if(tableCss.equals("tableDataTrackCell"))
                            tableCss="tableDataTrackCellChange";
                          else	
                            tableCss="tableDataTrackCell";
          
	%>        
          <tr>
            <td align=center colspan=1 class=<%=tableCss%>  ><%=aascFedexTrackingScanInfo.getDate()%></td>
            <td align=center colspan=1 class=<%=tableCss%> ></td>
            <td align=center colspan=1 class=<%=tableCss%> ></td>
            <td align=center colspan=1 class=<%=tableCss%> ></td>
          </tr>
          <tr>
            <td align=center colspan=1 class=<%=tableCss%> >&nbsp;&nbsp;&nbsp;<%=aascFedexTrackingScanInfo.getTime()%></td>
            <td align=center colspan=1 class=<%=tableCss%> ><%=aascFedexTrackingScanInfo.getScanDescription()%></td>
            <td align=center colspan=1 class=<%=tableCss%> ><%=aascFedexTrackingScanInfo.getLocation()%></td>
            <td align=center colspan=1 class=<%=tableCss%> ><%=nullStrToSpc(aascFedexTrackingScanInfo.getStatusExceptionDesc())%></td>
          </tr>
	<% 
        
		      }//end if (originalDate.equals("") || !originalDate.equals(aascFedexTrackingScanInfo.getDate()))
                      else{
			if(originalDate.equals(aascFedexTrackingScanInfo.getDate()))
                        {
        %>
          <tr>
            <td align=center colspan=1 class=<%=tableCss%> >&nbsp;&nbsp;&nbsp;<%=aascFedexTrackingScanInfo.getTime()%></td>
            <td align=center colspan=1 class=<%=tableCss%> ><%=aascFedexTrackingScanInfo.getScanDescription()%></td>
            <td align=center colspan=1 class=<%=tableCss%> ><%=aascFedexTrackingScanInfo.getLocation()%></td>
            <td align=center colspan=1 class=<%=tableCss%>><%=nullStrToSpc(aascFedexTrackingScanInfo.getStatusExceptionDesc())%></td>
          </tr>
        <%
        
			}// end if(originalDate.equals(aascFedexTrackingScanInfo.getDate()))		
                }// end else of if(originalDate.equals("") || !originalDate.equals(aascFedexTrackingScanInfo.getDate()))
   
                } // end of while(scanNodeListIterator.hasNext())
        %>
          <tr>
            <td class=tableSmallHeaderCell align=center colspan=4 height="15"></td> 
          </tr>
        <%
            logger.info("end of else if(scanNodeLinkedList==null)");
          }//end of else if(scanNodeLinkedList==null)
          logger.info("end of if((actionType.equals('TRACKING') || (actionType.equals('WAYBILL'))");
        }//end of else of if(!(responseStatus.equalsIgnoreCase("success")))
      }//end of if((actionType.equals("TRACKING") || (actionType.equals("WAYBILL"))
      logger.info("end of else of if(actionType.equals('POD')) ");
    }//end of else of if(actionType.equals("POD"))
   }//end of else of if(responseStatus!=null&&!responseStatus.equals(""))
   logger.info("end of else of if(aascFedexTrackingInfo==null)");
}//end of else of if(aascFedexTrackingInfo==null)

%>
<%!
 /** This method can replace the null values with nullString
     * @return String that is ""
     * @param obj-object of type Object
     */  
    String nullStrToSpc(Object obj) 
    {
            String spcStr = "";
    
            if (obj == null) {
                return spcStr;
            } else {
                return obj.toString();
            }
     }
 %>
        </table>
      </td>
    </tr>
  </table>
   <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
 </body>
</html>
