<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page errorPage="aascShipError.jsp" %>
<%/*========================================================================+
@(#)aascShipIndexPage.jsp 23/07/2014
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Customer and locations Details
* @version 1
* @author       N Srisha
* @history
*
*  17-Dec-2014      Eshwari M    Merged Sunanda code  
*  22-Dec-2104      Eshwari M    Formatted the code after self audit
*  06-Jan-2015      Y Pradeep    Added code for version number.
*  07-Jan-2014      Y Pradeep    Merged Sunanda's code : Modified if conditions for Location Setup and Create Customers.
*  07-Jan-2015      Eshwari M    Modified code to get firstName and lastName of the user logged in from session to display in index page.
*  07-Jan-2015      Suman G      Modified if condition for showing customer in role2
*  15/01/2015       Y Pradeep    Merged Sunanda's code : getting title name from Application.Property file.
*  21/01/2015       Suman G      Added if condition for create customer link for role 1 and role2
*  04/02/2015       Suman G      Modified code at change password for change password functionality.
*  16/02/2015       Suman G      Modified width to show create customer and user labels in one field.
*  05/03/2015       Sanjay & Khaja Added code for new UI changes.
*  23/04/2014       Y Pradeep    Removed footer and added FedEx and FEDG to chart diagrams.
*  24/07/2015       Rakesh K     Modified Code to work application in offline.
04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
26/08/2015  Rakesh K       Added code to solve 3496.
*  10/12/2015       Suman G      Added error page for not getting version from session.
*  10/02/2015       N Srisha     Added code to display live data for FDXG shipment.
*  16/03/2016       Y Pradeep    Added code to launch QZ Tray application for only Shipping User.
*/
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<html>
  <head>
  
    <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
   
           <!-- Required scripts for QZ Tray -->
    <script type="text/javascript" src="js/dependencies/rsvp-3.1.0.min.js"></script>
    <script type="text/javascript" src="js/dependencies/sha-256.min.js"></script>
    <script type="text/javascript" src="js/qz-tray.js"></script>
    <script type="text/javascript" src="js/additional/jquery-1.11.3.min.js"></script>
   
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
     <!--<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>-->
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link href="https://kendo.cdn.telerik.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script language="javascript" src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    <script src="kendo.all.min.js" type="text/javascript"></script>
    
    <!--<style type="text/css"> 
     @font-face {   
     font-family: 'Glyphicons Halflings';   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot');   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.woff') format('woff'),  
     url('bootstrap/fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg'); } 
    </style>-->
  
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
        <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />

   
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('SCShipIndex')" /></title>
    
        
     <!--<meta name="viewport" content="width=device-width, initial-scale=1"></meta>
     <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
     <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->
  
  
    <script type="text/javascript" src="fusioncharts/fusioncharts.js"></script>
    <script type="text/javascript" src="fusioncharts/fusioncharts.charts.js"></script>
    <script type="text/javascript" src="fusioncharts/themes/fusioncharts.theme.fint.js"></script>
    <script language="javascript" type="text/javascript">
        var button = "N";
        function disableSubmit()
        {
            if(button=="Y")
            {
                alert("Request already submitted. Please Wait.");
                return false;
            }
        }
        function changePassword(){
            tpwindow =  window.open("aascChangePassword.jsp","Post",'width=500,height=350,top=100,left=100,scrollbars=yes, resizable=yes');
            tpwindow.focus();
        }
        function loader()
        {
            var pb;
            pb = document.getElementById("indexLoad");
            pb.innerHTML = '<img src="images/ajax-loader.gif" width="80" height ="80"/>';
            pb.style.display = '';
        }
        
        /// Connection ///
function launchQZ() {
    if(document.getElementById("roleId").value == 4 )
        window.location.assign('qz:launch');

}
        
    </script>
  
  
  
  <style type="text/css">

.href {color: #003399}
html{height:100%}

    </style>
    <sj:head jqueryui="true"/>
    
    
    <script type="text/javascript">
FusionCharts.ready(function(){
    var upsScount = document.getElementById("upsScount").value;
    var StampscomScount = document.getElementById("StampscomScount").value;
    var DHLScount = document.getElementById("DHLScount").value;
    var FDXEScount = document.getElementById("FDXEScount").value;
    var FDXGScount = document.getElementById("FDXGScount").value;
    var revenueChart = new FusionCharts({
      type: "pie3d",
      renderAt: "pieContainer",
      width: "500",
      height: "300",
      dataFormat: "json",
      dataSource: 
 {
    "chart": {
       // "caption": "Marketing Expenses for last Month",
        //"numberprefix": "$",
        "plotgradientcolor": "",
        "bgcolor": "FFFFFF",
        "showalternatehgridcolor": "0",
        "showplotborder": "0",
        "divlinecolor": "CCCCCC",
        "showvalues": "1",
        "showcanvasborder": "0",
        "canvasbordercolor": "CCCCCC",
        "canvasborderthickness": "1",
        "yaxismaxvalue": "30000",
        "captionpadding": "30",
        "linethickness": "3",
        "sshowanchors": "0",
        "yaxisvaluespadding": "15",
        "showlegend": "1",
        "use3dlighting": "0",
        "showshadow": "0",
        "legendshadow": "0",
        "legendborderalpha": "0",
        "showborder": "0",
        "palettecolors": "#EED17F,#97CBE7,#074868,#B0D67A,#2C560A,#DD9D82"
    },
    "data": [
        {
            "label": "UPS",
             "value": upsScount,
             "issliced": "1"
          },
          {
             "label": "FDXE",
             "value": FDXEScount
          },
          {
             "label": "FDXG",
             "value": FDXGScount
          },
          {
             "label": "DHL",
             "value": DHLScount
          },
          {
             "label": "Stamps.com",
             "value": StampscomScount
          }
    ]
}
 
  });
  revenueChart.render("pieContainer");
}); 
 
</script>
<script type="text/javascript">
 
	   FusionCharts.ready(function(){
       var upsCharges = document.getElementById("upsFcharges").value;
       var StampscomCharges = document.getElementById("StampscomFcharges").value;
       var DHLCharges = document.getElementById("DHLFcharges").value;
       var FDXECharges = document.getElementById("FDXEFcharges").value;
       var FDXGCharges = document.getElementById("FDXGFcharges").value;
    var revenueChart = new FusionCharts({
      type: "column3d",
      renderAt: "chartContainer",
      width: "500",
      height: "300",
      dataFormat: "json",
      dataSource: {
       "chart": {
         // "caption": "Monthly revenue for last month",
         // "subCaption": "Harry's SuperMart",
          "xAxisName": "Carriers",
          "yAxisName": "Freight Charges",
          //"rotateYAxisName":"0",
          "yAxisMinValue":"0",
          "showvalues":"0",
          "theme": "fint",
          "yaxisvaluesstep":"0",
          "setAdaptiveYMin":"1",
          "dynamicAxis":"1" 
          
       },
     "data": [
          {
             "label": "UPS",
             "value": upsCharges
          },
          {
             "label": "FDXE",
             "value": FDXECharges
          },
          {
             "label": "FDXG",
             "value": FDXGCharges
          },
          {
             "label": "DHL",
             "value": DHLCharges
          },
          {
             "label": "Stamps.com",
             "value": StampscomCharges
          },
 
        ]
      }
 
  });
  revenueChart.render("chartContainer");
}); 
 
</script>
  </head>
  <body onload="launchQZ();" style="height:100%;background-color:#ADADAD">
  
  <div class="container-fluid" style="background-color:#ADADAD">
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr valign="top">
        <td class="displayMessage" width="100%">
        <%  
        String url = request.getContextPath();
  if(session.isNew())
  {
   response.sendRedirect(url+"/aascShipError.jsp");
  }
  %>
            <s:set name="version" value="%{#session.version}"/>
            <s:set name="patchVersion" value="%{#session.patchVersion}"/>
            <s:set name="carrierDetails" id="carrierDetails" value="{#session.carrierDetailsHashMap}"/>
       <%
          ArrayList carrierDetails = (ArrayList)session.getAttribute("carrierMonthlyDetails");
          Iterator ordersIterator = carrierDetails.iterator();
           HashMap<String, ?> resulthm = null;
          while (ordersIterator.hasNext()) {
            resulthm = (HashMap<String, ?>)ordersIterator.next();
            String carriername =(String) resulthm.get("OP_CARRIER_NAME"); 
            carriername = carriername.replace(".", "");
            String carrierCharges = carriername.concat("Fcharges");
            String carrierSCount = carriername.concat("Scount");
            String Fcharges = resulthm.get("OP_TOTAL_FREIGHT_CHARGES").toString();
            String Scount = resulthm.get("OP_SHIPMENTS_COUNT").toString();
//            System.out.println("adsff :: "+carriername+Fcharges+Scount);  
            pageContext.setAttribute(carrierCharges,Fcharges);
            pageContext.setAttribute(carrierSCount,Scount);
          }
       %>
            
            <s:hidden name="roleId" id="roleId" value="%{#attr.role_id}"/>   
            
            <s:hidden name="upsFcharges" id="upsFcharges" value="%{#attr.UPSFcharges}" />
            <s:hidden name="StampscomFcharges" id="StampscomFcharges" value="%{#attr.StampscomFcharges}" />
            <s:hidden name="DHLFcharges" id="DHLFcharges" value="%{#attr.DHLFcharges}" />
            <s:hidden name="FDXEFcharges" id="FDXEFcharges" value="%{#attr.FDXEFcharges}" />
            <s:hidden name="FDXGFcharges" id="FDXGFcharges" value="%{#attr.FDXGFcharges}" />
            <s:hidden name="upsScount" id="upsScount" value="%{#attr.UPSScount}" />
            <s:hidden name="StampscomScount" id="StampscomScount" value="%{#attr.StampscomScount}" />
            <s:hidden name="DHLScount" id="DHLScount" value="%{#attr.DHLScount}" />
            <s:hidden name="FDXEScount" id="FDXEScount" value="%{#attr.FDXEScount}" />
            <s:hidden name="FDXGScount" id="FDXGScount" value="%{#attr.FDXGScount}" />
            
            <s:if test="%{#patchVersion != #version}">
                <c:redirect url="/aascPatchVersionError.jsp" />
            </s:if>
            
            <s:if test="%{#patchVersion == null}">
                <c:redirect url="/aascShipError.jsp" />
            </s:if>
         
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            
            <tr>
                <td colspan="3" align="center"><%@ include file="aascIndexHeader.jsp"%></td>
            </tr>
          </table> 
          
          
       <div class="container">     
           <!-- <tr> <td valign="top" style="padding:0px 0px 0px 10px;font-style: italic;"> -->
        
            
          <H3 style="color:white"> Welcome, <s:property value="%{#attr.firstName}" /> <s:property value="%{#attr.lastName}" /> </h3>
            </div>
           <!-- </td><td >
      <br><br><br><br><br><br>
      </td> </tr> -->
             
             
           <!--   <tr>
         
         
          <td height="252" width="50%"  align="center">
              <sjc:chart 
    	id="chartPie"
    	cssStyle="width: 450px; height: 350px;"
    	pie="true"
    	pieLabel="true" legendShow="false" >
    
    	<sjc:chartData
    		id="UPS"
    		label="UPS"
    		data="35"
                color="#C38B34"
    	/> 
        
        <sjc:chartData
    		id="FDXE"
    		label="FDXE"
    		data="40"
                color="#009647"
    	/>
        
        <sjc:chartData
    		id="FDXG"
    		label="FDXG"
    		data="40"
                color="#5E78B0"
    	/>
        
        
    </sjc:chart>
    
    
          </td>
          <td height="252" width="70%" valign="right">
            <sjc:chart
                id="chartAjaxTwo"
                cssStyle="width: 450px; height: 400px;" autoResize="true"
                xaxisMin="0"
                xaxisMax="5"
                xaxisLabel="CARRIERS"
                yaxisLabel="FREIGHT"
                xaxisTickDecimals="0"
                xaxisTick="[[ 1 , 'UPS' ], [ 2 , 'FedEx Express' ] ,[3, 'FedEx Ground' ]]"
                
            >
    
        <sjc:chartData
    		id="UPS"
    		
                data="[[1,125]]"
    		bars="{show : true, barWidth: 0.4}"
    		stack="stack3"
                color="#C38B34"
    	/>
        
        <sjc:chartData
    		id="FDXE"
    		
                data="[[2,115]]"
    		bars="{show : true, barWidth: 0.4}"
    		stack="stack2"
                color="#009647"
    	/>
        
        <sjc:chartData
    		id="FDXG"
    		
                data="[[3,80]]"
    		bars="{show : true, barWidth: 0.4}"
    		stack="stack1"
                color="#5E78B0"
    	/>
    	
    </sjc:chart>
          </td>
        </tr> 
        
        
            </table>
        </td>
      </tr>  -->
   
  
    <!--    <tr valign="bottom">
            <td width="50%" valign="bottom" align="right">
                <br><br><br><br><br>
                <font size="2" color="003366">
                    <b>Version : </b>
                </font>
                <font size="2" color="003366">
                    <b><s:property value="#session.version" />&nbsp;</b>
                </font>
                <br></br>
            </td>
        </tr>
    </table> -->
    
    
  
       <div class="row" >
     <div class="col-md-1"></div>
           <div class="col-md-10">
    <div id="myCarousel" class="carousel slide" data-ride="carousel" style="background-color:#F0F0F0;">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox" align="center">
      <div class="item active">
        <div id="pieContainer" style="margin-top:5%;margin-bottom:5%"></div>
    </div>

      <div class="item">
        <div id="chartContainer" style="margin-top:5%;margin-bottom:5%"></div>  
    </div>
    
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
           </div> 
     <div class="col-md-1"></div>
      </div>
     
      <div class="row">
        <div class="col-md-12">
        <br/>
        <br/>
        <br/>
        
      
        </div>
        </div>
            
    
    <br> <br> 
    <div  style="position:absolute;top:620px;left:1200px;" >
     <font size="2" color="003366">
                    <b>Version : </b>
                </font>
                <font size="2" color="003366">
                    <!--   <b><s:property value="#session.version" />&nbsp;</b> -->
                    <b>2.0 &nbsp;</b>
                </font>
    </div>
        

 </div>
  <div style="position:absolute;top:620px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
</html>
 
