 

<%       
/*==========================================================================+
|  DESCRIPTION                                                              |
|    JSP aascPoppedThirdParty for displaying the Third Party related        | 
|    information                                                            |
|    Sanjay Kumar Parui                                                     |
|    Version   1.1                                                          |                                                                            
|    Creation 04/03/2015                                                    |
|    05/03/2015    Sanjay & Khaja Added code for new UI changes.            |
|    26/05/2015    Suman G        Modified labels to fix #2810     
|    24/07/2015  Rakesh K    Modified Code to work application in offline.
04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
26/08/2015  Rakesh K       Added code to solve 3496.
+===========================================================================*/%>
<%@ page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@ page import="com.aasc.erp.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.logging.*"%>
<%!  private static Logger logger=AascLogger.getLogger("aascFreightCostDetails"); %>
<%@ page errorPage="aascShipError.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
  
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
    </style>-->
  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Freight Cost Details</title>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->

  </head>
  
  
  <body class="gradientbg" style="height:100%">
  <br>
    <br>
    <br>
  
  
  
  <div class="container"> <!--container-->
  
  <div class="row" align="center"> <!--row1-->
  
  
  <div class="col-sm-3"> </div>
  
  <div class="col-sm-6"> <!--col-sm-6-->
  <%
  String freightCost = request.getParameter("freightCost");
  pageContext.setAttribute("freightCost",freightCost);
  String freightCharges = request.getParameter("freightCharges");
  pageContext.setAttribute("freightCharges",freightCharges);
  String totalWeight = request.getParameter("totalWeight");
  pageContext.setAttribute("totalWeight",totalWeight);
  %>
     
     
     <div class="well" style="border-color:#3d566d; border-radius:20px" ><!--welldiv-->
     
     <div align="center" style="font-weight:bold;font-size:small;color:white;background-color:#3d566d" >Freight Cost Details</div>
     <br>
     <div class="row"><!--mini div-->
       
    <div class="col-sm-6" align="left">   <s:property value="getText('NegotiatedRate')"/>  </div>
    <div class="col-sm-6" align="left">   <s:textfield name="freightCostName" cssClass="inputs" id="freightCostName" value="%{#attr.freightCost}"/> </div>
        
      </div> 
         
        <div class="row">
      <div class="col-sm-6" align="left">   <s:property value="getText('ListRate')"/> </div>
        
        
         
    <div class="col-sm-6" align="left">    <s:textfield name="freightChargesName" cssClass="inputs" id="freightChargesName" value="%{#attr.freightCharges}"/> </div>
       </div>
      
       <div class="row">
    <div class="col-sm-6" align="left">  <s:property value="getText('BillingWeight')"/> </div>
     
     
      
     <div class="col-sm-6" align="left">   <s:textfield name="totalWeightName" cssClass="inputs" id="totalWeightName" value="%{#attr.totalWeight}"/> </div>
      
      </div>
     
      
      
        </div><!--well div-->
        
        </div><!--col-sm-6-->
        
        <div class="col-sm-3"> </div>
        
        </div> <!--row1-->
      </div>  <!--container-->
       <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>  
    </body>
</html>
