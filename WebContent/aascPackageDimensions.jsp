 

<%/*========================================================================+
|  DESCRIPTION                                                              |
|    JSP For Package Dimensions                                             |                                                      |
|    Version - 1                                                            |       
|    20/11/2014  Sunanda.K      Added code from ShipConsoleCloud version1.2                         |
|    07/01/2015  Y Pradeep      Merged Sunandas code : Removed the hard codings by getting from Properties file.
|    20/01/2015  K Sunanda      Added a struts tag to fix "s:property not expected" issue
|    15/04/2015  Suman G        Added session to fix #2743
     24/07/2015  Rakesh K    Modified Code to work application in offline.
     30/07/2015  Dinakar G       added code to fix #3297 & ##3300
     04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
     04/08/2015  Dinakar G   Added code for UI alignment.
     26/08/2015  Rakesh K       Added code to solve 3496.
+===========================================================================*/%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ page errorPage="aascShipError.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
    
     <meta http-equiv="Content-Type"
              content="text/html; charset=UTF-8"/>
     <title> <s:property value="getText('PackageDimensionDetails')"/> </title>
     <link type="text/css" rel="stylesheet" href="aasc_ss.css"></link>
     <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
     
     
     <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->
  
     <script language="JavaScript" src="aasc_Package_Dimensions_JS.js" type="text/javascript"> 
     </script>
  </head>
  <body onload="loadValues();" style="background-color:#ADADAD;">
  <div  class="container-fluid" style="background-color:#ADADAD; width:100%;">
   <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
    %>
  <%
  String carrierTest = request.getParameter("carrierTest");
  %>
  <input name="carrierCode" type="HIDDEN" id="carrierCode"
           value="<%=carrierTest%>"></input><c:if test="${pageContext.session[\'new\'] || pageContext.session.id != sessionScope.SessionId}">
  <c:redirect url="/aascShipError.jsp">
  <c:param name="errorObj" value="SessionError"/>
  </c:redirect>
  </c:if>
  <c:set var="packCount" value="${0}"/>
  <c:set var="unitList" value="${sessionScope.unitList}"/>
  <c:set var="packCountStr" value="${param.packCount}"/>
  <c:set var="packCount" value="${packCountStr}"/>
  <c:set var="dimensionName" value="${param.dimensionName}"/>
  <c:catch var="exception1">
    <c:set var="subStrDimensionName" value="${fn:substring(dimensionName,0,1)}"/>
  </c:catch>
  <c:if test="${exception1 != null}">
    <c:set var="dimensionName" value=""/>
  </c:if>
  <c:set var="dimValue" value="${param.dimValueText}"/>
  <c:catch var="exception2">
    <c:set var="subStrDimValue" value="${fn:substring(dimValue,0,1)}"/>
  </c:catch>
  <c:if test="${exception2 != null}">
    <c:set var="dimValue" value=""/>
  </c:if>
  <c:set var="dimNameLength" value="${fn:length(dimensionName)}"/>
  <c:if test="${dimNameLength > 0}">
    <c:set var="firstIndex" value="${fn:indexOf(dimensionName,\'*\')}"/>
    <c:set var="secondIndex" value="${fn:indexOf(fn:substring(dimensionName,firstIndex+1,dimNameLength),\'*\')}"/>
    <c:set var="thirdIndex" value="${fn:indexOf(fn:substring(dimensionName,secondIndex+firstIndex+2,dimNameLength),\'*\')}"/>
    <c:set var="fourthIndex" value="${fn:indexOf(fn:substring(dimensionName,thirdIndex+secondIndex+firstIndex+3,dimNameLength),\'*\')}"/>
    <c:set var="length" value="${fn:substring(dimensionName,0,firstIndex)}"/>
    <c:set var="width" value="${fn:substring(fn:substring(dimensionName,firstIndex+1,dimNameLength),0,secondIndex)}"/>
    <c:set var="height" value="${fn:substring(dimensionName,secondIndex+firstIndex+2,secondIndex+firstIndex+thirdIndex+2)}"/>
    <c:set var="units" value="${fn:substring(dimensionName,thirdIndex+secondIndex+firstIndex+3,fourthIndex+thirdIndex+secondIndex+firstIndex+3)}"/>
    <c:set var="dimId" value="${fn:substring(dimensionName,fourthIndex+thirdIndex+secondIndex+firstIndex+4,dimNameLength)}"/>
  </c:if>
 
  <form name="PackageDimensionsForm" method="POST">
    
   
   
   
   
   
    <div class="container"> 
    
    
    
    <br></br>
    
    <div class="row" align="center">
    
     <div class="col-sm-2">
    </div>
    
    
    <div class="col-sm-8">
    
    <div class="well" style="border-color:#3d566d; border-radius:20px" >
   
   <div align="center" style="font-weight:bold;font-size:small;color:white;background-color:#3d566d" >Dimension Details</div> 
  
   <br> 
   
    <div class="row" align="left"> 
       <div class="col-sm-6">
           <span class="dispalyFields"><s:property value="getText('DimensionName')"/></span>
           </div>
        <div class="col-sm-6">
           <input name="packageDimensionName" align="left"    id="packageDimensionNameID" class="inputFields" type="text" value="${dimValue}" readonly="readonly"></input>
       </div>
       
       <div class="col-sm-6">
          <span class="dispalyFields"><s:property value="getText('Length')"/> </span>
        </div>
        <div class="col-sm-6">
          <input name="packageDimensionLenght" align="left" id="packageDimensionLenghtID" class="inputFields" type="text" value="${length}"></input>
       </div>
      
      <div class="col-sm-6">
          <span class="dispalyFields"><s:property value="getText('Width')"/> </span>
       </div>
       
       <div class="col-sm-6">
         <input name="packageDimensionWidth" align="left" id="packageDimensionWidthID" class="inputFields" type="text" value="${width}"></input>
     
       </div>
       <div class="col-sm-6">
          <span class="dispalyFields"><s:property value="getText('Height')"/> </span>
      </div>
      
      <div class="col-sm-6">
         <input name="packageDimensionHeight" align="left" id="packageDimensionHeightID" class="inputFields" type="text" value="${height}"></input>
      </div>
   
     <div class="col-sm-6">
         <span class="dispalyFields"><s:property value="getText('Units')"/> </span>
      </div>
      
      <div class="col-sm-6">
      
       <select name="packageDimensionUnits" id="packageDimensionUnitsID" class="inputFields" value="">
         <option value=""><s:property value="getText('SelectUnit')"/></option>
         <c:forEach var="unitItr" items="${unitList}">
              <c:set var="unit" value="${unitItr}"/>
          <c:choose>
            <c:when test="${fn:toLowerCase(units) == fn:toLowerCase(unit)}">
             <option value="${unit}" selected="selected"> ${unit} </option>
            </c:when>
            <c:otherwise>
             <option value="${unit}">  ${unit}  </option>
            </c:otherwise>
          </c:choose>
        </c:forEach>
       </select>
    </div>
     </div>
  
  <br>
  
  <div align="center">
   <input type="hidden" name="actiontype" value=""></input>
   <input type="hidden" name="location" value=""></input>
   <input type="hidden" name="packCount" value="${packCount}"></input>
   <input type="hidden" name="packageDimensionDimId" value="${dimId}"></input>
   <c:set var="dimValue" value="${fn:trim(dimValue)}"/>
   <c:if test="${fn:toLowerCase(dimValue) == fn:toLowerCase(\'other\')}">
        <button  class="btn btn-success" id="NewButton" onclick="return setValues();" value="Save"  alt="Save" align="middle">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span> </button>
   </c:if>
    <button type="button"  class="btn btn-warning" id="Cancel" onclick="javascript:window.close();" value="Cancel"  alt="Cancel" align="middle">Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span> </button>
  </div>
 
  </div>
 
 
 
 
  </div>
  
 
   <div class="col-sm-2">
    </div>
   
  
  
  
  
  
  </div>
  
  </div>
  
 
  
  
   
  
  
 </form>
</div>
 <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
 </body>
 

</html>
