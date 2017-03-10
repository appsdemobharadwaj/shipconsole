<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%       
/*==================================================================================================+
|  DESCRIPTION                                                                                      |
|    JSP for Advance Search of Order numbers based on ship from to to date and coustomer name.      | 
|    information                                                                                    |
|    Author Jagadish Jain                                                                           |
|    Version   1.1                                                                                  |                                                                            
|    Creation 21/01/2015                                                                            |
|    21/01/15 Jagadish Jain Added code required for Order search functionality                      |
|    05/03/2015  Sanjay & Khaja   Added code for new UI changes.                                    |
|    12/03/2015  Eshwari          Added aasc_Shipment_JS.js javascript missing tag in UI change     
|    23/03/2015  Sunanda          Added code for bug fix #2691 
|    24/03/2015  Sunanda          Removed the unnecassary s:property tag
|    27/03/2015  Sunanda          added the function validateSearch() for bug fix #2731             |
     13/04/2015  Y Pradeep        Added dateFormatCheck function call fro from date and to date for validating format of date. Bug #2775.
     11/05/2015  Y Pradeep        Modified code to hold selected date in from and to date fields after retrieving values successfully. Bug #2912.
     27/05/2015  Suman G          Modified code to fix #2818 by Vikas
     11/06/2015  Suman G          Removed fevicon to fix #2992
     24/06/2015  Rakesh K         fix #3055
     03/07/2015  Suman G          Added Padmavathi's code to fix issue #3148
     21/07/2015    Suman G        Modified message id for QA
     24/07/2015  Rakesh K         Modified Code to work application in offline.
     04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
     26/08/2015  Rakesh K       Added code to solve 3496.
     20/11/2015  Y Pradeep        Removed anchor tag for serach and clear buttons to resolve this issue. Bug ##3992.
+==================================================================================================*/%>
<%@page import="java.io.*" %>
<%@page import="java.text.*" %>
<%@page import="java.sql.*"%>
<%@page import="java.util.logging.*"%>
<%@page import="java.util.*" %>
<%@page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@page import="oracle.jdbc.driver.*"%>
<%@page import="com.aasc.erp.carrier.*" %> 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%!  private static Logger logger=AascLogger.getLogger("aascShipOrderSearch"); %>
<%@ page errorPage="aascShipError.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<HTML>
  <HEAD>
  
  <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
   
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
     <!--<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>-->
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link href="https://kendo.cdn.telerik.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script language="javascript" src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    <script src="kendo.all.min.js" type="text/javascript"></script>
    <script language="JavaScript" type="text/javascript" src="aasc_Shipment_JS.js"></script>
    
    <!--<style type="text/css"> 
     @font-face {   
     font-family: 'Glyphicons Halflings';   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot');   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.woff') format('woff'),  
     url('bootstrap/fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg'); } 
    </style>-->
  
    <!--<link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    
    <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
    <script src="kendo.all.min.js"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />-->
    
    <TITLE>SHIPMENT ORDER DETAILS</TITLE>
    <style type="text/css">
    html{ height:100%;  }
    </style>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    
    
   
    <script type="text/javascript">
            var j = jQuery.noConflict();
            j(document).ready(function() {
                // create DatePicker from input HTML element
//                $("#shipmentDate").kendoDatePicker();
                j("#fromDate").kendoDatePicker({
                parseFormats:["yyyy-MM-dd"],
                format: "yyyy-MM-dd"
            }).data("kendoDatePicker");
            
            j("#toDate").kendoDatePicker({
                parseFormats:["yyyy-MM-dd"],
                format: "yyyy-MM-dd"
            }).data("kendoDatePicker");

            });
        </script>
 
     <style type="text/css">
       html{height:100%}
       
       /*CSS for ajax loader icon on page load */
    #loading {
    width: 100%;
    height: 100%;
    top: 0px;
    left: 0px;
    position: fixed;
    display: block;
    opacity: 0.7;
    background-color: #fff;
    z-index: 99;
    text-align: center;
    }

    #loading-image {
    position: absolute;
    top: 20%;
    left: 45%;
    z-index: 100;
    }
    /* css ends here for ajax loader */
    </style>
     
  </HEAD>
   <body style="height:100%" class="gradientbg" bgcolor="#0ca3d2" >
   
     <!-- ajax loader image  -->          
            <div id="loading" style="background-color:#888888;display:none">
            <img id="loading-image" src="images/ajax-loader1.gif" alt="Loading..." />
            </div>
     <!-- code ends here -->
   
    <div  class="container-fluid" style="background-color:#ADADAD; width:100%;">
    <div class="row" align="middle">

   <table class="" width="100%"  border="0" align="left" cellpadding="2" cellspacing="0">  
     
     <s:set name="client_id" value="%{0}" />
       
     <c:catch var="Execption1">
     <s:set name="client_id" value="%{#session.client_id}" />
     </c:catch>
     
     <c:if test="${Execption1 != null}">
         <c:out value="Exception while getting object from session"/>
      </c:if>
      <% 
      String url = request.getContextPath();
      if(session.isNew())
      {
         response.sendRedirect(url+"/aascShipPopUpsError.jsp");
      }
      java.sql.Date shipmentDate = null;
    %>
    
    <s:set name="key" value="#request.key" />
   
    
     
    <jsp:useBean id="date" class="java.util.Date" />
            <fmt:formatDate var="sysdate" value="${date}" pattern="MM-dd-yyyy"/>
           
            <s:set name="shipmentFromDate" value="" />
            <s:set name="shipmentToDate" value="" />
           <!-- <c:set var="shipmentFromDate" value="${requestScope.fromDate}" />
            <c:set var="shipmentToDate" value="${requestScope.toDate}" /> -->
            
            <s:set name="shipmentfromDate" value="%{#request.fromDate}" />
         
            <s:set name="shipmenttoDate" value="%{#request.toDate}" />
          
            <s:set name="customerName" value="%{#request.customerName}" />
            
           <s:bean id="searchOrderNumberList" name="java.util.LinkedList"/> 
      <%

         LinkedList searchOrderNumberList =null;
         searchOrderNumberList = (LinkedList)session.getAttribute("SearchOrderNumberList");
         pageContext.setAttribute("searchOrderNumberList",searchOrderNumberList);
       session.removeAttribute("SearchOrderNumberList");
      %>
<s:set name="searchOrderNumberList" value="%{#attr.searchOrderNumberList}"/>
     <tr align="middle">
     <td>
      <s:form name="OrderGetDetailsForm" method="POST" action="OrderSearchAction" onsubmit="return dateFormatCheck();">

       <div class="container">  
        <br>
         <div class="row">  
          <div class="col-sm-1"> </div>
           <div class="col-sm-10"> 
             <div class="well" style="border-color:#19b698; border-radius:20px;">
             
               
                <div align="center" style="font-weight:bold;font-size:20px;color:white;background-color:#19b698" >
                 <s:property value="getText('OrderSearch')"/>
                </div>
               
                <br>
                 <s:if test="%{#key=='aasc200'}"> 
                        <div align="center">
                        <span id="tickPic"></span>
                        &nbsp;&nbsp;
                        <span class="displayMessage1" id="displayMessage">
                            <s:property value="getText(#key)"/><!--Sunanda added for bug fix #2691-->
                        </span>
                        </div>
                     </s:if>
                     <s:else>
                       <div align="center">
                        <span id="tickPic"></span>
                        &nbsp;&nbsp;
                        <span class="displayMessage" id="displayMessage">
                            <s:property value="getText(#key)"/>
                        </span>
                        </div>
                        </s:else>
               
            
             <br>
             <!--Added to fix issue #3148 -->
             <div class="row" align="left">
               <div class="col-sm-6 dispalyFields " >
             <s:property value="getText('ShipmentDate')"/></div></div>
             
             <div class="row" align="left">
               <div class="col-sm-6 dispalyFields " >
                <s:property value="getText('FromDate')"/>
               </div>
               <div class="col-sm-6">
                <span class="style7">
                 <s:textfield name="fromDate" id="fromDate" placeholder="YYYY-MM-DD" value="%{#shipmentfromDate}" onblur="dateFormatCheck(this)" size="10" maxlength="10" />
                </span>
               </div>
             </div>
             
             <div class="row" align="left">
               <div class="col-sm-6 dispalyFields" >
                <s:property value="getText('ToDate')"/>
               </div>
               <div class="col-sm-6">
                <span class="style7">
                 <s:textfield name="toDate" id="toDate" placeholder="YYYY-MM-DD" value="%{#shipmenttoDate}" onblur="dateFormatCheck(this)" size="10" maxlength="10" />
                </span>
               </div>
             </div>
             
             <s:hidden name="customerNameHidden" id="customerNameHiddenID" value="%{#customerName}"/>
             <input type="hidden" name="fromDateHidden" id="fromDateHiddenID" value="<c:out value='${shipmentFromDate}'/>"/>
             <input type="hidden" name="toDateHidden" id="toDateHiddenID" value="<c:out value='${shipmentToDate}'/>"/>
             
             <div class="row" align="left">
               <div class="col-sm-6 dispalyFields" >
                <s:property value="getText('CustomerName')"/>
               </div>
               <div class="col-sm-6">
                <span class="style7">
                 <s:textfield name="customerName"  cssClass="inputs" id="customerNameId" value="%{#customerName}" maxlength="30" width="138px"   readonly=""/>
                </span>
               </div>
             </div>
             <br>
              <div class="row">
              <s:hidden name="searchFlag" id="searchFlag" value=""/> 
              <button type="button" class="btn btn-info" onclick="return validateSearch()">Search <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
              
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <s:hidden name="reset" value=""/> 
                <span class="style7">
                 <button type="button" class="btn btn-warning" onclick="clearFields()">Clear <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                </span> 
               
             </div>
           </div>
          </div> 
          <div class="col-sm-1"> </div>
        </div>    
       </div>
            
     </s:form>    
    </td>
    </tr>
        
        <s:set name="orderNumberList" value="%{#attr.searchOrderNumberList}" />

        <s:if test="%{#orderNumberList.size() != 0}">
        <tr align="middle">
         <td height="20">
         <div class="form-group" style="width:90%">
         <fieldset style="border-width: 2px;border-color:#19b698; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;border-style: solid;"> 
           <br>
           <table class="table" id="my_table"  style="overflow-x:auto;font-size:small;">
           
            <thead align="center" style="background-color :#19b698; color:#FFFFFF"> 
               <tr>
                  <th style="font-size: 13px; font-weight: bold;"></th>
                  <th style="font-size: 13px; font-weight: bold;">Order Number</th>
                  <th style="font-size: 13px; font-weight: bold;">Customer Name</th>
                  <th style="font-size: 13px; font-weight: bold;">Shipment Date(YYYY-MM-DD)</th>    
                  <th style="font-size: 13px; font-weight: bold;">Ship Method</th>

                </tr>
             </thead>   
              <s:if test="%{#orderNumberList.size() != 0}">
              <s:set name="count" value="%{0}"/>
               <s:iterator id="orderNumberList" value="%{#orderNumberList}" >
               <s:set name="aascAdhocOrderSearchInfo" value="%{#orderNumberList}"/>
                <s:set name="count" value="%{#count+1}"/>
                 <tr>
                
                 <td class="inputs" style="width:5%" align="center"> 
                     <input name="orderNo" id="orderNo" type="image" src="images/clickHere.jpg" onclick="return populateOrderNumber()" value=""/></td>
                 
                 <td class="inputs" style="width:10%">
                 <s:hidden name="orderNoSelected%{#count}" id="orderNoSelected%{#count}" value="%{#aascAdhocOrderSearchInfo.orderNumber}"/>
                 <s:property value="%{#aascAdhocOrderSearchInfo.orderNumber}"/></td>
                 <td class="inputs" style="width:10%"><s:property value="%{#aascAdhocOrderSearchInfo.customerName}"/></td>
                 <td class="inputs" style="width:15%"><s:property value="%{#aascAdhocOrderSearchInfo.shipmentDate}"/></td>
                 <td class="inputs" style="width:20%"><s:property value="%{#aascAdhocOrderSearchInfo.shipMethod}"/></td>
                 </tr>
                </s:iterator>
               </s:if>
   
            </table>
           </fieldset>
          </div>  
        </td>
          </tr>
        </s:if>
          <s:hidden name="countHidden" id="countHidden" value="%{#count}"/> 
      </table>
      </div>
      </div>
       <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
   </body>
</HTML>
