 
<%/*========================================================================+
|  DESCRIPTION                                                              |
|    JSP For Showing list of labels to view                                 |                                                 
|    Version - 1                                                            |       
|    Author: Suman Gunda                                                    |
|    Create On: 12/03/2015       
24/07/2015  Rakesh K    Modified Code to work application in offline.
04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.|
26/08/2015  Rakesh K       Added code to solve 3496.
+===========================================================================*/%>

<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ page errorPage="aascShipPopUpsError.jsp" %>

<%@page import="java.io.*" %>
<%@page import="java.util.logging.*"%>
<%@page import="java.util.*" %>
<%@page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>

<%@ taglib uri="/struts-tags" prefix="s"%>

<%! private static Logger logger=AascLogger.getLogger("aascShowLabelList"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html style="height:100%">
  <head >
    
    <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
   
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
    <!--<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>-->
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link href="https://kendo.cdn.telerik.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script language="javascript" src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    <script src="kendo.all.min.js" type="text/javascript"></script>
    
   <!-- <style type="text/css"> 
     @font-face {   
     font-family: 'Glyphicons Halflings';   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot');   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.woff') format('woff'),  
     url('bootstrap/fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg'); } 
    </style> -->
  
    <!--<link href="kendo.common-material.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->
  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>View Labels</title>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css">
    
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    
    <script language="javascript" type="text/javascript"> 
    function showLabelPopUp(i)
    {
      var labelName = document.getElementById('trackingNo'+i).value;
      var upsMode = document.getElementById('upsModeID').value;
      tpwindow =  window.open("aascAjaxGetLabel.jsp?labelName="+labelName+"&upsMode="+upsMode,'POST');
      tpwindow.focus();
    }
    </script>
     <style type="text/css">
            html{height:100%}
    </style>
  </head>
  <body class="gradientbg" >
 <% String upsMode = request.getParameter("upsMode"); %>
 
  <input type="hidden" value="<%=upsMode%>" name="upsMode" id="upsModeID">
  <%
        AascShipmentOrderInfo aascShipmentOrderInfo;
        LinkedList  packageInfo;
        ListIterator packageInfoIterator;
        String trackingNo = "";
        int pkgNo =0 ;
        aascShipmentOrderInfo = (AascShipmentOrderInfo) session.getAttribute("AascShipmentOrderInfo");
        AascShipmentHeaderInfo headerBean = aascShipmentOrderInfo.getShipmentHeaderInfo();
        
        packageInfo = aascShipmentOrderInfo.getShipmentPackageInfo();
        packageInfoIterator = packageInfo.listIterator();
        int i = 1;
  %>
  
  <center>
  <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; margin-top:20px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:75%">
                  <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;padding-left:10px;font-size:medium;">View Labels</label>
                    </div> 
                    <div class="row"><br/></div>
                    
                    <table align="center" border="1"  >
    <tr>
        <td width="8%" align="center" noWrap height="21" style="background-color:#19b698; color:#ffffff;"><span class="dispalyFields">Pkg No</span></td>
        <td width="22%" align="center" noWrap height="21" style="background-color:#19b698; color:#ffffff;"><span class="dispalyFields">Tracking Number</span></td>
        <td align="center"  style="background-color:#19b698; color:#ffffff;"><span class="dispalyFields" >Click to View</span>
        </td>
    </tr>
    <%
        while (packageInfoIterator.hasNext())
        {        
            AascShipmentPackageInfo aascPackageBean =(AascShipmentPackageInfo)packageInfoIterator.next();
            
            logger.info("tracking no "+aascPackageBean.getTrackingNumber());
            trackingNo = aascPackageBean.getTrackingNumber();
            pkgNo = pkgNo+1;
    %>        
    <tr>
        <td width="8%" align="center" noWrap height="21" ><span class="dispalyFields"><%=pkgNo%></span></td>
        <td width="22%" align="center" noWrap height="21" ><span class="dispalyFields"><%=trackingNo%></span></td>
        <td align="center" > 
            <input type="hidden" id="trackingNo<%=i%>" name="trackingNo<%=i%>" value="<%=trackingNo%>" />
            <input  class="inputFields" type="button" value="View Label" onclick="javascript:showLabelPopUp(<%=i%>);" >
        </td>
    </tr>
    <%
    i++;
        }
    %>
  
  </table>
 <div class="row"><br/></div>
  
  </div>
  </center>
   <div style="position:relative;top:500px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
