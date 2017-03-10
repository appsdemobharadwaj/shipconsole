<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%/*========================================================================+
@(#)aascTrackPage.jsp 16/01/2015
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* @history
*  
* 15/01/2015  Y Pradeep     Merged Sunanda's code for getting title name from Application.Property file.
* 15/03/2015  Eshwari M     Added the missing code of tracking functionality
*/
%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file ="aascHeader.jsp" %>
<%@ page errorPage="aascShipError.jsp" %>
<link type="text/css" rel="stylesheet" href="aasc_Apps.css">

<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('SCTrackPage')" /></title>
    <!--<title>UPS OnLine® Tools Tracking</title> -->
  </head>
  <body>
  <%!
  private static Logger logger=AascLogger.getLogger("aascTrackPage");//logger object for issuing logging requests
  private String url="";//holds current context path in which error page is present
  private String actionType="";//contains either TRACKING or POD or WAYBILL 
  private String lastActivityStatus="";//contains status of package whether it is delivered or it is in transit
  private String tableCss="";//contains style name used to display the table  
  //contains date which is used to check the date and display information accordingly
  private String originalDate="";//scanning done on same day but diffent timings are consolidated and displayed
  private String urlPath="";//contains pod output file path to which response is redirected 
  private String responseStatus="";//contains status of the response 
  private int packageCount=0;//indicates number of packages in delivery 
  
  private AascTrackingInfo aascTrackingInfo=null;//tracking information bean containing parsed response data  
  private AascTrackingInfo tempTrackingInfo=null;//tracking information bean containing parsed package progress information
 	
  private LinkedList linkedList=null;//linked list that contains package progress information
  private ListIterator listIterator=null;//used to iterate through linked list   
 
  %>
  <%  
  url = request.getContextPath();
  try{
    if(session.isNew()|| !(session.getId().equals(session.getAttribute("SessionId"))))
    {   
       response.sendRedirect(url+"/aascShipError.jsp");   
    }      
    //tracking information bean object 
    aascTrackingInfo=(AascTrackingInfo)session.getAttribute("TrackingInformation"); 
  }
  catch(Exception e)
  {
    logger.severe("error in retreiving the data from the session:"+e.getMessage());
  }
  if(aascTrackingInfo==null)
  {
    logger.info("tracking information bean is null");
  %>
  <h1><font color="red">Tracking Information Bean is Null</font></h1>
  <%
  }
  else
  {       
    responseStatus=aascTrackingInfo.getResponseStatus();
    if(responseStatus!=null&&!responseStatus.equals("")&&!responseStatus.equalsIgnoreCase("success"))
    {
      out.println("<font color='red'><h3><center>"+responseStatus+"</center></h3></font>");    
    }//end of if(responseStatus!=null&&!responseStatus.equals(""))
    else
    {
        actionType=aascTrackingInfo.getActionType(); //actionType which represents waybill or tracking       
        logger.info("after retreiving aasc tracking information bean and actionType");
        lastActivityStatus = null;                   //indicates the status of package whether it is delivered or in transit etc
           
	if(actionType.equals("TRACKING"))//if actionType is 'tracking' (for package detailed tracking information)
        {
          logger.info("if(actionType.equals('TRACKING')) ");
          responseStatus=aascTrackingInfo.getResponseStatus();
          if(!(responseStatus.equalsIgnoreCase("success")))
          {
          if(responseStatus==null || responseStatus.equals(""))
          responseStatus="No Tracking Information Available";   
          out.println("<font color='red'><h3><center>"+responseStatus+"</center></h3></font>");
          }//end of if(!(responseStatus.equalsIgnoreCase("success")))
          else
          {
  %>
  <table width="100%"  border="0">
  <tr>
    <td colspan="2" bgcolor="#990000"><span class="style1">View Details </span></td>
  </tr>
  <tr>
    <td width="21%"><span class="style4">Status<br>
    </span></td>
    <td width="79%">&nbsp;<%=aascTrackingInfo.getStatus()%></td>
  </tr>
  <%
            lastActivityStatus=aascTrackingInfo.getStatus();
	    if(lastActivityStatus.equalsIgnoreCase("DELIVERED"))//if actionType is tracking and if package  is delivered 
            {//getting package progress information from shipper till it reaches the customer
                logger.info("inside if(lastActivityStatus.equalsIgnoreCase('DELIVERED'))");
  %>
  <tr>
    <td><span class="style4">Delivered On </span></td>
	
    <td>&nbsp;<%=aascTrackingInfo.getDeliveredOn()%></td>
  </tr>
  <tr>
    <td><span class="style4">Delivered To</span></td>
    <td>&nbsp;<%=aascTrackingInfo.getDeliveredTo()%></td>
  </tr>
  <%
  	    }//if (lastActivityStatus.equalsIgnoreCase("DELIVERED"))
	    else{
  %>
  <tr>
    <td><span class="style4">Shipped To</span></td>
    <td>&nbsp;<%=aascTrackingInfo.getDeliveredTo()%></td>
  </tr>
  <%            }//else of if(lastActivityStatus.equalsIgnoreCase("DELIVERED"))
  %>
  <tr>
    <td><span class="style4">Tracking Number</span></td>
    <td>&nbsp;<%=aascTrackingInfo.getPackageTrackingNumber()%></td>
  </tr>
  <tr>
    <td><span class="style4">Weight</span></td>
    <td>&nbsp;<%=aascTrackingInfo.getWeight()%></td>
  </tr>
  <tr>
    <td><span class="style4">Service Type</span></td>
    <td>&nbsp;<%=aascTrackingInfo.getServiceType()%></td>
  </tr>
 
  <tr>
    <td colspan="2"><span class="style5">Package Progress </span></td>
  </tr>
  <tr>
    <td colspan="2"><table cellspacing=1 cellpadding=1 width="100%" border=0>
      <tr>
        <td class=tableSmallHeaderCell align=center colspan=1>Date/Time</td>
        <td class=tableSmallHeaderCell align=center colspan=1>Location</td>
        <td class=tableSmallHeaderCell align=center colspan=1>Activity</td>
      </tr>
      <%
                tableCss="tableDataTrackCellChange";
                originalDate="";
		linkedList=aascTrackingInfo.getPackageProgressList();
                if(linkedList==null)
                {
      %>
      <h1>package progress information is not available!</h1>
      <%        }
                else
                {
                  listIterator=linkedList.listIterator();
                  //retreiving package progress information from shipper to customer's destination
                  while(listIterator.hasNext())
                  {
                    tempTrackingInfo=(AascTrackingInfo)listIterator.next();
      %>
      <tr>
      <%
   		    if (originalDate.equals("") || !originalDate.equals(tempTrackingInfo.getDate())){
                        originalDate=tempTrackingInfo.getDate();
                        if(tableCss.equals("tableDataTrackCell"))
                                tableCss="tableDataTrackCellChange";
                        else	
                                tableCss="tableDataTrackCell";
      %>        
        <td class=<%=tableCss%> align=center colspan=1><%=tempTrackingInfo.getDate()%></td>
        <td class=<%=tableCss%> align=center colspan=1></td>
        <td class=<%=tableCss%> align=center colspan=1></td>
      </tr>
      <tr>
        <td class=<%=tableCss%> align=center colspan=1><%=tempTrackingInfo.getTime()%></td>
        <td class=<%=tableCss%> align=center colspan=1><%=tempTrackingInfo.getLocation()%></td>
        <td class=<%=tableCss%> align=center colspan=1><%=tempTrackingInfo.getActivity()%></td>
      </tr>
      <%         
	}//end if (originalDate.equals(""))
        else{
           if(originalDate.equals(tempTrackingInfo.getDate()))
           {
      %>
        <td class=<%=tableCss%> align=center colspan=1><%=tempTrackingInfo.getTime()%></td>
        <td class=<%=tableCss%> align=center colspan=1><%=tempTrackingInfo.getLocation()%></td>
        <td class=<%=tableCss%> align=center colspan=1><%=tempTrackingInfo.getActivity()%></td>
      </tr>
      <%
            }// end if(originalDate.equals(tempTrackingInfo.getDate()))
	}// end of else of if (originalDate.equals(""))
      } // end of while(listIterator.hasNext())
    }//end of if(linkedList==null)
      %>
      <tr><td class=tableSmallHeaderCell align=center colspan=3 height=12></td></tr>
      <%
  }//end of else of if(!(responseStatus.equalsIgnoreCase("success")))
  }//if (actionType.equals("TRACKING"))
	else if (actionType.equals("WAYBILL"))//if user clicks on waybill button
  {
  logger.info("inside else if (actionType.equals('WAYBILL'))");
  responseStatus=aascTrackingInfo.getResponseStatus();
 if(!(responseStatus.equalsIgnoreCase("success")))
  {
  if(responseStatus==null || responseStatus.equals(""))
  responseStatus="No Tracking Information Available!";   
  out.println("<font color='red'><h3><center>"+responseStatus+"</center></h3></font>");
  }//end of if(!(responseStatus.equalsIgnoreCase("success")))
  else
  {
  tableCss="tableDataTrackCell";   
  lastActivityStatus=aascTrackingInfo.getStatus();
  //retreiving way bill list containing information of all the packages in that delivery
  linkedList=aascTrackingInfo.getWayBillList();
  if(linkedList==null)
  {
  %>
  <h1>WayBill List containing package information is null</h1>
  <%
  }
  else
  {
  listIterator=linkedList.listIterator();
  packageCount=0;
  while(listIterator.hasNext())
  {
    tempTrackingInfo=(AascTrackingInfo)listIterator.next();
    //displaying way bill tracking header information
    if(packageCount==0)
    {
   %>
   <table width="100%"  border="0">
      <tr>
            <td colspan="2" bgcolor="#990000"><span class="style1">View Packages Shipped </span></td>
      </tr>
      <tr>
            <td width="33%"><span class="style4">Packages in ShipMent <br>
            </span></td>
            <td width="67%"><%=aascTrackingInfo.getPackageListSize()%></td>
      </tr>
      <tr>
            <td><span class="style4">Status</span></td>
            <td><%=aascTrackingInfo.getStatus()%>(<%=aascTrackingInfo.getPackageListSize()%>)</td>
      </tr>
      <tr>
            <td><span class="style4">serviceType</span></td>
            <td><%=aascTrackingInfo.getServiceType()%></td>
      </tr>
      <tr>
            <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2">
          <table cellspacing=1 cellpadding=1 width="100%" border=0>
             <tr>
                      <td width="32%" colspan=1 align=center class=tableSmallHeaderCell>Tracking Number </td>
                      <td width="18%" colspan=1 align=center class=tableSmallHeaderCell>Status</td>
                      <td class=tableSmallHeaderCell align=center colspan=2>Delivery Information </td>
             </tr>
       <%
            }//if(packageCount==0)
       %>			
            <tr>
       <%
				
            if(tableCss.equals("tableDataTrackCell"))
                    tableCss="tableDataTrackCellChange";
            else	
                    tableCss="tableDataTrackCell";

       %>
              <td class=<%=tableCss%> align=center colspan=1><%=packageCount+1%>.<%=tempTrackingInfo.getPackageTrackingNumber()%></td>
              <td class=<%=tableCss%> align=center colspan=1><%=tempTrackingInfo.getStatus()%></td>
        <%
   	   if(!lastActivityStatus.equalsIgnoreCase("Delivered"))
           {//displaying details of packages when package is not yet delivered to customer
             logger.info("inside if(!lastActivityStatus.equalsIgnoreCase('Delivered'))");
	%>
	      <td class=<%=tableCss%> width="20%" colspan=1 align=center><div align="left">Location : </div></td>
              <td width="30%" align=center class=<%=tableCss%>><div align="left"><%=tempTrackingInfo.getDeliveredTo()%></div></td>
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
              <td class=<%=tableCss%> width="20%" colspan=1 align=center><div align="left">Delivered on: </div></td>
              <td width="30%" align=center class=<%=tableCss%>><div align="left"><%=tempTrackingInfo.getDeliveredOn()%></div></td>
            </tr>
            <tr>
              <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
              <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
              <td class=<%=tableCss%> align=center><div align="left">Delivered to: </div></td>
              <td class=<%=tableCss%> align=center><div align="left"><%=tempTrackingInfo.getDeliveredTo()%></div></td>
            </tr>
	    <tr>
              <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
              <td class=<%=tableCss%> align=center colspan=1>&nbsp;</td>
              <td class=<%=tableCss%> align=center><div align="left">Signed by : </div></td>
              <td class=<%=tableCss%> align=center><div align="left"><%=tempTrackingInfo.getSignedBy()%></div></td>
   	    </tr>

	<% }//end of else if(!lastActivityStatus.equalsIgnoreCase("Delivered"))
	   packageCount++;
	}//end of while(listIterator.hasNext()) loop
    }//end of else if(linkedList==null)   
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
    if(actionType.equals("POD"))
    {
    String labelsPath = (String)session.getAttribute("cloudLabelPath") ;
    logger.info("labelsPath : "+labelsPath);   
    logger.info("if(actionType.equals('POD'))");
    responseStatus=aascTrackingInfo.getResponseStatus();
      if(!(responseStatus.equalsIgnoreCase("success")))
      {
      if(responseStatus==null || responseStatus.equals(""))
      responseStatus="No POD Information Available";      
      out.println("<font color='red'><h3><center>"+responseStatus+"</center></h3></font>");      
      }//end of if(!(responseStatus.equalsIgnoreCase("success")))
        else
        {      
           
        response.sendRedirect(labelsPath+"pod.html");
        }//end of else of if(!(responseStatus.equalsIgnoreCase("success")))
      }//end of if(actionType.equals("POD"))
    }//end of else of if (actionType.equals("TRACKING"))
  }//end of else of if(responseStatus!=null&&!responseStatus.equals(""))
  logger.info("successfully displayed all the tracking information");
}//if tracking information bean is not null
%>  
      </table>
   </td>
 </tr>
</table>
 <div style="position:relative;top:500px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
