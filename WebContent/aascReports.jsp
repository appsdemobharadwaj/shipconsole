 <%
/*====================================================================================================================+
|  DESCRIPTION                                                                                                        |
|    A JSP to display the reports menu                                                                                |
|    Author Eshwari                                                                                                   |
|    Modified                                                                                                         |
|    Version   1.1                                                                                                    |                                                                            
|    Creation  28/11/2014                                                                                             |      
|    History :                                                                                                        |
|                                                                                                                     |
|    15/01/2015  Y Pradeep   Merged Sunanda's code : getting title name from Application.Property file.               |
|    30/01/2015  Eshwari M   Formatted code and ran code audit   
|    05/03/2015  Sanjay & Khaja Added code for new UI changes.|
|    18/03/2015  Eshwari M    Modified reports to show BI report and Manifest report for ShipExec Demo                |
|    24/03/2015  Suman G      Added Eshwari's code.  
|    15/04/2015  Suman G      Added session to fix #2743                                                              |
     23/04/2015  Y Pradeep    Removed footer.
     25/04/2015  Rakesh K    Added black color to the report text.
     09/07/2015  Dinakar     Aligined UI as per tab
     24/07/2015  Rakesh K    Modified Code to work application in offline.
     04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
     10/08/2015  Naresh U    Modified code for JaserReports displays in popup window.
     19/08/2015  Naresh U    Modified code for JaserReports generate based on login clientID.
     26/08/2015  Rakesh K    Modified Code to solve #3450.
     01/09/2015  Ravi G      Added code to solve 3496.
     08/09/2015  Rakesh K    Added CSS code to solve 3405.
     12/11/2015  Shiva G     Modified code to fix the issue 3814
     13/11/2015  Suman G     Committed hari code to fix reports for Trial User issue.
     20/11/2015  Hari        Changed AccountNumber attribute to CUSTOMER_ID while opening Shipment Activity Report.
+=====================================================================================================================*/
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>  
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link type="text/css" rel="stylesheet" href="aasc_index.css"></link>
  <script type="text/javascript">

    function openNewWindow (URL, windowName, windowOptions)
    {
      var window = getWindow (URL, windowName, windowOptions);
    }

    function getWindow(URL, windowName, windowOptions)
    {
      var newWindow = open (URL, windowName, windowOptions)
      newWindow.focus();
      return window;
    }
  </script>
  <style type="text/css">
  .clor {
  color: red;
  text-decoration: none;
  font-family: ARIAL;
  font-size: 14px;
}
a ,iframe,label { cursor: pointer; }
#CarrierShipActivity13,#CarrierShipActivity23,#CarrierShipActivity14,#CarrierShipActivity12{
color: black;
        font-size: 14px;
    font-weight: bold;
    font-family:ARIAL;
 
 }
 
 /*.holder{
    width: 400px;
    height:300px;
    position:relative;
}
.frame{
    width: 100%;
    height:100%;
*/
.bar{
    position:absolute;
    top:0;
    left:0;
    width:100%;
    margin-top:30px;
    height:57px;
    background-color:#2B2B2B;
}
.bar1{
    position:absolute;
    top:0;
    left:0;
    width:6%;
    margin-top:6.5%;
    height:5.5%;
    margin-left:72%;
    background-color:#DEDEDC;
    transition: background-color 5s ease-out;
}
iframe{
    height:100%;
    width:100%;
    top:0;
    left:0;
    margin:0;
}
    </style>
   <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
   
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
    <!--<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>-->
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link href="https://kendo.cdn.telerik.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script language="javascript" src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    <script src="kendo.all.min.js" type="text/javascript"></script>
      <title><s:property value="getText('SCReports')" /></title>
 
    <link type="text/css" rel="stylesheet" href="aasc_ss.css"></link>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    <!--  Start styles for popup -->
    <link href="css/popupcss/bootstrap.css" rel="stylesheet" >
<link href="css/popupcss/main.css" rel="stylesheet" >
<link href="css/popupcss/style.css" rel="stylesheet" >
<link href="css/popupcss/font-awesome.min.css" rel="stylesheet" >
 <link rel="stylesheet" href="css/popupcss/reset.css">
   <link rel="stylesheet" href="css/popupcss/styleold.css">
   
    <style type="text/css">
      html {
        height: 100%;
            }
           
    </style>
     
  </head>
  <body class="gradientbg">
  <div class="container-fluid" style="background-color:#ADADAD;">
  <s:set name="clientId" value="%{#attr.client_id}"/>   
  <s:hidden name="clientIdHid" id="clientIdHid" value="%{#attr.client_id}" />
  <s:hidden name="userId" id="userId" value="%{#attr.user_id}" />
   <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
    
  <%@ include file="aascIndexHeader.jsp"%>
  <div class="row"><br/></div>
  <div class="row"><br/></div>
  <div class="row"><br/></div>
  <center>
   <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:90%;height:45%">
    <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;font-size: 20px;padding-top: 4px;">Reports Menu</label>
                    </div>
                    <div class="row"><br/></div>
                        <div class="row"><br/></div> 
                        <div class="row"><br/></div>
                        <div class="row"><br/></div>
    
        <div class="row" id="divSub" style="width:100%;">
            
              <div class="col-sm-2">
              </div>
                          
                <div class="col-sm-8" style="margin-left:2%;">
                       <!-- <img src="images/index8.png" alt="" width="25"
                           height="25"/>  -->
                    
                        <span style="color: blue;font-size: 16px;" class="glyphicon glyphicon-folder-open"></span>  &nbsp;
                       
                        <!-- <a href="javascript:" onclick="window.open('http://54.209.12.190:8082/jasperserver/flow.html?_flowId=viewReportFlow&standAlone=true&_flowId=viewReportFlow&j_username=jasperadmin&j_password=jasperadmin&ParentFolderUri=%2Freports%2FScCloud&reportUnit=%2Freports%2FScCloud%2FFrieghtCostAnalysisReport');"  id="CarrierShipActivity12" >
                           Freight Cost Analysis Report                           
                         </a> -->
                         <!--<a id="CarrierShipActivity12" href="#popupFreightCostAnalysisReport"  class="link popup-vedio video-btn"><span onclick="displayFreightCostAnalysisReport()"> Freight Cost Analysis Report </span> </a> -->
                         <a id="CarrierShipActivity12"  onclick="displayFreightCostAnalysisReport()" class="link popup-vedio video-btn"> Freight Cost Analysis Report  </a>
                </div>
                <%
                int userId = (Integer)session.getAttribute("user_id");
                pageContext.setAttribute("userId",userId);
                System.out.println("userId::" + userId);
              %>
               <script type="text/javascript">
                
function displayFreightCostAnalysisReport()
{
                if (navigator.userAgent.match(/AppleWebKit/) && ! navigator.userAgent.match(/Chrome/)) {
  customerId =document.getElementById("clientIdHid").value;
      userId=document.getElementById("userId").value;
ACarrierShipActivity12 =document.getElementById("CarrierShipActivity12");
ACarrierShipActivity12.href="#popupFreightCostAnalysisReport";
srcIframe= document.getElementById("iframeFCAReportID");
      srcIframe.src= "http://54.209.12.190:8082/jasperserver/flow.html?_flowId=viewReportFlow&standAlone=true&_flowId=viewReportFlow&j_username=jasperadmin&j_password=jasperadmin&ParentFolderUri=%2Freports%2FScCloud&reportUnit=%2Freports%2FScCloud%2FFrieghtCostAnalysisReport&CUSTOMER_ID="+customerId+"&UserId="+userId;
    }
  else {
customerId =document.getElementById("clientIdHid").value;
      userId=document.getElementById("userId").value;
ACarrierShipActivity12 =document.getElementById("CarrierShipActivity12");
ACarrierShipActivity12.href="#popupFreightCostAnalysisReport";
srcIframe= document.getElementById("iframeFCAReportID");
      srcIframe.src= "http://54.209.12.190:8082/jasperserver/flow.html?_flowId=viewReportFlow&standAlone=true&_flowId=viewReportFlow&j_username=jasperadmin&j_password=jasperadmin&ParentFolderUri=%2Freports%2FScCloud&reportUnit=%2Freports%2FScCloud%2FFrieghtCostAnalysisReport&CUSTOMER_ID="+customerId+"&UserId="+userId;
}
}
                </script>
                
                <div class="col-sm-2">
              </div>
                 
            </div>
            <div class="row"><br/></div>
<div class="row"><br/></div> 
<div class="row"><br/></div>
        <div class="row" id="divSub" style="width:100%;">
           
            <div class="col-sm-2">
              </div>
                        
                <div class="col-sm-8" style="margin-left:2%;">
                       <!-- <img src="images/index8.png" alt="" width="25"
                           height="25"/>
                       
                       <a href="javascript:" onclick="window.open('http://54.209.12.190:8082/jasperserver/flow.html?_flowId=viewReportFlow&standAlone=true&_flowId=viewReportFlow&j_username=jasperadmin&j_password=jasperadmin&ParentFolderUri=%2Freports%2FScCloud&reportUnit=%2Freports%2FScCloud%2FShipmentActivityReport');" id="CarrierShipActivity23" >
                           Shipment Activity Report            </a> -->
                        <span style="color: blue;font-size: 16px;" class="glyphicon glyphicon-folder-open"></span>  &nbsp;
                        <!-- <a id="CarrierShipActivity12" href="#popupShipmentActivityReport" class="link popup-vedio video-btn">Shipment Activity Report         -->
                         <a id="CarrierShipActivity13" onclick="displayShipmentActivityReport()" class="link popup-vedio video-btn">Shipment Activity Report &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                           
                         </a>
                </div>
                <div class="col-sm-2">
              </div>
                 <script type="text/javascript">
                // Shipment Activity report
function displayShipmentActivityReport()
{
customerId =document.getElementById("clientIdHid").value;
        userId=document.getElementById("userId").value;
ACarrierShipActivity12 =document.getElementById("CarrierShipActivity13");
ACarrierShipActivity12.href="#popupShipmentActivityReport";
srcIframe= document.getElementById("iframeSAReportID");
        srcIframe.src= "http://54.209.12.190:8082/jasperserver/flow.html?_flowId=viewReportFlow&standAlone=true&_flowId=viewReportFlow&j_username=jasperadmin&j_password=jasperadmin&ParentFolderUri=%2Freports%2FScCloud&reportUnit=%2Freports%2FScCloud%2FShipmentActivityReport&CUSTOMER_ID="+customerId+"&UserId="+userId;
}
                </script> 
            </div>  
            <div class="row"><br/></div> 
            <div class="row"><br/></div>
            <div class="row"><br/></div>
            <div class="row" id="divSub" style="width:100%;">
           
            <div class="col-sm-2">
              </div>
                        
                <div class="col-sm-8" style="margin-left:2%;">
                     <!--   <img src="images/index8.png" alt="" width="25"
                           height="25"/>
                       
                        <a href="javascript:" onclick="window.open('http://54.209.12.190:8082/jasperserver/flow.html?_flowId=viewReportFlow&standAlone=true&_flowId=viewReportFlow&j_username=jasperadmin&j_password=jasperadmin&ParentFolderUri=%2Freports%2FScCloud&reportUnit=%2Freports%2FScCloud%2FSCCloudTransportationSpendReport');"  id="CarrierShipActivity12" >
                           Transportation Spend Report                           
                         </a> -->
                        <span style="color: blue;font-size: 16px;" class="glyphicon glyphicon-folder-open"></span>  &nbsp;
                         <!--<a id="CarrierShipActivity12" href="#popupTransporatationSpendReport" class="link popup-vedio video-btn">Transportation Spend Report                           
                         </a>-->
                          <a id="CarrierShipActivity14" onclick="displayTransPortationSpendReport()" class="link popup-vedio video-btn">Transportation Spend Report                           
                         </a>
                </div>
                <div class="col-sm-2">
              </div>
              

                 <script type="text/javascript">
                // Transporation Spend Report
function displayTransPortationSpendReport()
{
customerId =document.getElementById("clientIdHid").value;
userId=document.getElementById("userId").value;
ACarrierShipActivity12 =document.getElementById("CarrierShipActivity14");
ACarrierShipActivity12.href="#popupTransporatationSpendReport";
srcIframe= document.getElementById("iframeTSRID");
srcIframe.src= "http://54.209.12.190:8082/jasperserver/flow.html?_flowId=viewReportFlow&standAlone=true&_flowId=viewReportFlow&j_username=jasperadmin&j_password=jasperadmin&ParentFolderUri=%2Freports%2FScCloud&reportUnit=%2Freports%2FScCloud%2FSCCloudTransportationSpendReport&CUSTOMER_ID="+customerId+"&UserId="+userId;
}
                </script> 
            </div>  
            <div class="row"><br/></div> 
            <div class="row"><br/></div>
            <!-- Start PopUp for to get  freightCost Analysis Jasper report  --> 
             <div class="row"> 
  <div id="popupFreightCostAnalysisReport" class="zoom-anim-dialog mfp-hide">
      <div class="pop_up">
   
	  <div class="modal-header" style="text-align:center;color:white">
          
      <h2 >Freight Cost Analysis Report</h2>

       <!--<iframe  id="iframeFCAReportID" src="http://54.209.12.190:8082/jasperserver/flow.html?_flowId=viewReportFlow&standAlone=true&_flowId=viewReportFlow&j_username=jasperadmin&j_password=jasperadmin&ParentFolderUri=%2Freports%2FScCloud&reportUnit=%2Freports%2FScCloud%2FFrieghtCostAnalysisReport"> -->
       <iframe  id="iframeFCAReportID" src="">
      </iframe>
 
  <p class="bar">
  <p class="bar1">
  </div> 

      </div>
    </div>
    </div>
             <div class="row"> 
  <div id="popupShipmentActivityReport" class="zoom-anim-dialog mfp-hide" >
      <div class="pop_up">
	  <div class="modal-header" style="text-align:center;color:white">
      <h2 >Shipment Activity Report</h2>
      <!-- <iframe   src="http://54.209.12.190:8082/jasperserver/flow.html?_flowId=viewReportFlow&standAlone=true&_flowId=viewReportFlow&j_username=jasperadmin&j_password=jasperadmin&ParentFolderUri=%2Freports%2FScCloud&reportUnit=%2Freports%2FScCloud%2FShipmentActivityReport">-->
  <iframe id="iframeSAReportID" src="">
  </iframe>
  <p class="bar">
  <p class="bar1">
  </div> 
      </div>
    </div>
    </div>
    <div class="row"> 
  <div id="popupTransporatationSpendReport" class="zoom-anim-dialog mfp-hide">
      <div class="pop_up">
	  <div class="modal-header" style="text-align:center;color:white">
      <h2 >Transportation Spend Report</h2>
      <!-- <iframe src="http://54.209.12.190:8082/jasperserver/flow.html?_flowId=viewReportFlow&standAlone=true&_flowId=viewReportFlow&j_username=jasperadmin&j_password=jasperadmin&ParentFolderUri=%2Freports%2FScCloud&reportUnit=%2Freports%2FScCloud%2FSCCloudTransportationSpendReport">
    </iframe>-->
    <iframe id="iframeTSRID" src="">  </iframe>
    <p class="bar">
    <p class="bar1">
  </div> 

      </div>
    </div>
    </div>
    <!-- End PopUp for to get  Shipment Activity Jasper report  --> 
            <!-- below code commented by naresh for jasperreports -->
    <!--<table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td width="68%" valign="bottom" align="center">
            <font size="5" color="black">
              <b><s:property value="getText('ReportsMenu')"/> </b>
            </font>
            <div align="center" class="subHeadings"></div>
          </td>
      </tr>
      <tr>
        <td colspan="3">
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>
              &nbsp;
          </p>
          <center>
            <table  border="0" align="center" cellpadding="0" cellspacing="4">
              <tr>   
                <td width="320" height="32" align="center">
                  <table align="left">
                    <tr align="center">
                      <td width="25" height="25">
                        <img src="images/index8.png" alt="" width="25"
                           height="25"/>
                      </td>                            
                      <td width="300" align="left" height="20"  valign="middle">
                         <s:url action="ReportsAction" id="url"> 
                            <s:param name="requestType"  value="%{\'CarrierShipActivity\'}"></s:param>
                         </s:url> 
                         <s:a href="%{url}" name="#CarrierShipActivity.action1">
                            &nbsp;&nbsp;
                            <s:property value="getText(\'CarrierShipActivity.action1\')"/>
                         </s:a>
        
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="320" height="32" align="center">
                  <table align="left">
                    <tr align="center">
                      <td width="25" height="25">
                        <img src="images/index8.png" alt="" width="25"  height="25"/>
                      </td>
                      <td width="300" align="left" height="20" valign="middle">
                        <s:url action="ReportsAction" id="url"> 
                            <s:param name="requestType"  value="%{\'CarrierSLAReport\'}"></s:param>
                        </s:url> 
                        <s:a href="%{url}" name="#CarrierSLAReport.action1">
                        &nbsp;&nbsp;
                            <s:property value="getText(\'OnTimeDeliveryReport.action1\')"/>
                        </s:a>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table> 
          </center>
        </td>
      </tr>
    </table>-->
    
   <!-- </fieldset>-->
        </div>
        
    </center>  
  </div>
<!-- Start to add scripts for Popup  -->
<script src="css/popupjs/jquery-1.11.0.min.js" type="text/javascript"></script> 
<script src="css/popupjs/owl.carousel.min.js" type="text/javascript" ></script> 
<!--<script src="css/popupjs/bootstrap.min.js" type="text/javascript"></script> -->
<script src="css/popupjs/jquery.lightSlider.min.js" type="text/javascript"></script> 
<script src="css/popupjs/owl.carousel.min.js" type="text/javascript"></script> 
<script src="css/popupjs/jquery.magnific-popup.min.js" type="text/javascript"></script> 
<script src="css/popupjs/oldmain.js" type="text/javascript"></script>
<!-- End PopUp -->
 <div style="position:absolute;top:620px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
