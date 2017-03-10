<%/*========================================================================+
|  DESCRIPTION                                                              |
|    JSP to display tracking info from DHL                                  |
|    Author Eshwari M                                                       |
|    Version - 1.0                                                          |
|    History                                                                |
|        
+===========================================================================*/%>
<%@include file ="aascHeader.jsp" %>
<%@ page errorPage="aascShipError.jsp" %>
<link type="text/css" rel="stylesheet" href="aasc_Apps.css">
<%@ taglib uri="/struts-tags" prefix="s" %>

<%!
  private static Logger logger=AascLogger.getLogger("aascDHLTrackPage");//logger object for issuing logging requests
  private String url="";//holds current context path in which error page is present
  private AascDHLTrackingInfo aascDHLTrackingInfo=null;//tracking information bean containing parsed response data  
  private String actionType="";//contains either TRACKING or WAYBILL   
  private String urlPath="";//contains pod output file path to which response is redirected   
  private String tableCss="";//contains style name used to display the table  
  //contains date which is used to check the date and display information accordingly
  private String originalDate="";//scanning done on same day but diffent timings are consolidated and displayed
  private LinkedList scanNodeLinkedList=null;//linked list that contains package progress information
  private ListIterator scanNodeListIterator=null;//used to iterate through linked list 
  private AascDHLTrackingInfo aascDHLTrackingScanInfo=null;//tracking information bean containing parsed package progress information  
  private String responseStatus="";//contains status of the response
  private String lastActivityStatus="";//contains last activity status of the tracked package
  private LinkedList linkedList=null;//linked list used that contains package tracking information
  private ListIterator listIterator=null;//
  private int packageCount=0;
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
    aascDHLTrackingInfo=(AascDHLTrackingInfo)session.getAttribute("TrackingInformation"); 	 
  }
  catch(Exception e)
  {
    logger.severe("error in retreiving the data from session:"+e.getMessage());
  }
  
    if(aascDHLTrackingInfo==null)
  {
      logger.info("tracking information bean is null");
  %>
  <h1><font color='red'>Error!Tracking Information Bean is Null</font></h1>
  <%
  }//end
  else
  {      
    responseStatus=aascDHLTrackingInfo.getResponseStatus();
	
	    if(responseStatus!=null&&!"".equals(responseStatus)&&!"success".equalsIgnoreCase(responseStatus))
    {
      out.println("<font color='red'><h3><center>"+responseStatus+"</center></h3></font>");    
    }//end
	
	else
    {
   LinkedList aascDHLTrackingInfoList = aascDHLTrackingInfo.getTrackingInfoList();
   Collections.reverse(aascDHLTrackingInfoList);
   ListIterator itr = aascDHLTrackingInfoList.listIterator();
   while(itr.hasNext()){
   aascDHLTrackingInfo = (AascDHLTrackingInfo)itr.next();
      if(aascDHLTrackingInfo !=null){
    
%>
 <table width="100%"  border="0">
 
 <tr>
<td colspan="2" bgcolor="Purple" height="20"><span class="style1">View Packages Shipped </span></td>
			  </tr>
                          
                          <tr><td colspan="4" bgcolor="Lime" height="2"></td></tr>
			  <tr>
				<td width="33%"><span class="style4">Time Stamp <br>
				</span></td>
				<td width="67%"><%=aascDHLTrackingInfo.getDate()%>&nbsp;&nbsp;<%=aascDHLTrackingInfo.getTimeStamp()%></td>
			  </tr>
                          
			  <tr>
				<td><span class="style4">Event</span></td>
				<td><%=aascDHLTrackingInfo.getServiceEventDesc()%></td>
			  </tr>
                          
                             <tr>
				<td><span class="style4">City - Country</span></td>
				<td><%=aascDHLTrackingInfo.getServiceAreaDesc()%></td>
			  </tr>
                          
                          <tr>
				<td><span class="style4">SignedBy</span></td>
				<td><%=aascDHLTrackingInfo.getSignatory()%></td>
			  </tr>
                          
                          <tr>
				<td width="33%"><span class="style4">Remarks: Further Details<br>
				</span></td>
				<td width="67%"><%=aascDHLTrackingInfo.getFurtherDetails()%></td>
			  </tr>
                          
                          <tr>
				<td width="33%"><span class="style4">Remarks: Next Steps<br>
				</span></td>
				<td width="67%"><%=aascDHLTrackingInfo.getNextSteps()%></td>
			  </tr>
 </table>
 <%
  }}}}
 %>
