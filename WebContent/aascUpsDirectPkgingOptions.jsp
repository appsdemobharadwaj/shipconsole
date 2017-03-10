 
<%/*==========================================================================+
|  DESCRIPTION                                                                |
|    aascUPSDirectPkgingOptions.jsp containing ups package options fileds     |
|    Author Vandana Gangisetty                                                |
|    Version   1                                                              |                                                
|    Creation 10-May-2007                                                     |
|    History                                                                  |
|           17/05/07  Vandana Added code to display specific values by default|
|                      for packaging,codCode,codFundsCode,dcisType.           |
|           18/05/07  Vandana Added code to display specific dcis type by     |
|                      default based on codFlag                               |
|           29/05/07  Vandana Added code to restrict delivery confirmation    |
|		                  value to adult signature when ship method is|
|		                  UPS Next Day Air Early A.M                  |
|           14/06/07  Vandana Added  cod and cod currency code fields.        |
|           16/06/07  Gayaz   Added the code for help page display            |
|           18/06/07  Vandana Added code to restrict cod amt field size.      |
|           10/06/09  Madhavi Modified logging code                           |
|           20/11/14  Added this class from cloud 1.2 version                 |
|           18/12/14  Renamed adhoc to shipment              
|           15/01/2015  Y Pradeep     Merged Sunanda's code for getting title name from Application.Property file.
|           05/03/2015    Sanjay & Khaja Added code for new UI changes.
            10/03/2015  Y Pradeep     Added missing name for param tag
             11/03/2015    Jagadish Implemented the new UI changes.
            15/04/2015  Suman G     Added session to fix #2743  
            05/05/2015  Suman G     Added if condition for handling the save, cancel and close buttons before ship and after ship.
            11/05/2015  Suman G    Modified buttons to fix #2899 
            25/05/2015  Sunanda K  Modified code for bug fix #2898
            11/06/2015  Suman G    Removed fevicon to fix #2992
            23/06/2015  Rakesh G   Applied the fix #3026
            29/05/2015  Suman G    Added Padmavathi's code to fix issue #3051
            09/07/2015  Suman G    Added Padmavathi's code to fix an issue in UI
            10/07/2015  Suman G    Added Padmavathi's code to align save,cancel buttons after ship is done
            20/07/2015  Rakesh K   Modified code for UPS package options popup window #3026.
            24/07/2015  Rakesh K    Modified Code to work application in offline.
            04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
            26/08/2015  Rakesh K       Added code to solve 3496.
+===========================================================================*/%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.ListIterator"%>
<%@page import="java.util.logging.*"%>
<%@page import="com.aasc.erp.model.*"%>
<%@page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<%@ page errorPage="aascShipError.jsp" %>
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
    
    <!--<style type="text/css"> 
     @font-face {   
     font-family: 'Glyphicons Halflings';   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot');   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.woff') format('woff'),  
     url('bootstrap/fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg'); } 
    </style>-->
  
  <!--<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
  <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />-->
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:property value="getText('UPSDirectPackageOptions')" /></title>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css">
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    
    
     <script language="javascript" SRC="aascUPSPkgingOptions_js.js" type="text/javascript">
     </script>     
     <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
  </head>  
  <body class="gradientbg" style="height:100%" onload="loadPackageOptions()"> 
    <br/>
    <table width="500"  align="right" >
      <tr>
        <td width="400"> </td>
   <!--     <td width="100" ><div align="center" ><a href="javascript: openNewWindow('ShipConsoleClientShippingUser/index.html?shipmentorders.html', 'SCHelpWindow', 'WIDTH=700,HEIGHT=500,resizable=yes');"><img src="images/helpImage.png" width="20" height="20" border="0"  alt = ""></a></div></td> -->    <!-- Dinakar commented for bug #3460  -->
      </tr>
    </table>
   
     <%  
            String url = request.getContextPath();
            if(session.isNew())
            {System.out.println("Inside session");
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
        %>
    <c:set var="packCount1" value="${param.packCount}"/>
    <s:set name="packCount" value="%{#attr.packCount1}"/>
    <s:set name="codCheckedFlag" value="%{''}"/>
    
    <c:catch var="exception1">
        <s:set name="pkgingMap" value="%{#session.UpsPkging}" />
    </c:catch>
    <s:if test="%{#exception1 != null || #session.UpsPkging == null }" >  
      <s:bean name="java.util.HashMap" id="pkgingMap">
        <s:param  name="pkgingMap" value="%{'select'}"/>
      </s:bean>
    </s:if>
  
    <c:catch var="exception1">
        <s:set name="upsCodMap" value="%{#session.UpsCodCode}" />
    </c:catch>
    <s:if test="%{#exception1 != null || #session.UpsCodCode == null }" >
      <s:bean name="java.util.HashMap" id="upsCodMap">
        <s:param  name="upsCodMap" value="%{'select'}"/>
      </s:bean>
    </s:if>
  
    <c:catch var="exception1">  
      <s:set name="upsFundsCodMap" value="%{#session.UpsFundsCodCode}"/>
    </c:catch>
    <s:if test="%{#exception1 != null || #session.UpsFundsCodCode == null }" >
      <s:bean name="java.util.HashMap" id="upsFundsCodMap">
        <s:param  name="upsFundsCodMap" value="%{'select'}"/>
      </s:bean>
    </s:if>
  
    <s:set name="upsDcisMap" value="%{#session.UpsDcisType}"/>
    <c:set var="upsDcisMap" value="${sessionScope.UpsDcisType}" />
  
    <c:set var="codType" value="${param.codType}"/>
    <s:set name="codType" value="%{#attr.codType}"/>
  
    <c:set var="codFundsCode" value="${param.codFundsCode}"/>
    <s:set name="codFundsCode" value="%{#attr.codFundsCode}"/>
  
    <c:set var="delConfirm" value="${param.delConfirm}"/>
    <s:set name="delConfirm" value="%{#attr.delConfirm}"/>
    <c:set var="codFlag" value="${param.codFlag}"/>
    <s:set name="codFlag" value="%{#attr.codFlag}"/>
  
    <c:set var="upsServiceLevelCode" value="${param.upsServiceLevelCode}"/>
    <s:set name="upsServiceLevelCode" value="%{#attr.upsServiceLevelCode}"/>

    <c:set var="pkging" value="${param.pkgingTemp}" />
    <s:set name="pkgingTemp" value="%{#attr.pkging}"/>

    <s:if test='%{#codFlag == "Y" || #codFlag == "y"}'>
      <s:set name="codCheckedFlag" value="%{'Y'}"/>
      <s:set name="codCheckedValue" value="%{'true'}"/>
    </s:if>
    <s:else>
      <s:set name="codCheckedFlag" value="%{'N'}"/>
      <s:set name="codCheckedValue" value="%{'false'}"/>
    </s:else>
   
    <s:form name="aascUpsPackageOptionsForm" method="POST">
      <s:hidden name="packageCount" id="packageCount" value="%{#packCount}"/>
      <s:if test="%{#pkgingTemp.length() == 1}">
        <s:set name="pkgingTemp" value="%{0}%{#pkgingTemp}" />
      </s:if>
  
     <br>
     
     
     
     <div class="container-fluid" style="width:90% ;margin-left:auto; margin-right:auto;margin-top:40px;border-radius:10px;border-width:1px;border-style:solid;border-color:#7761a7;background-color:#F0F0F0">
                   <!--<fieldset class="scheduler-border" style="border-color:#19b698; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;">-->
                    <div align="center" style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;">
                       <label style="color:#ffffff;padding-left:10px;font-size:20px;">UPS Package Options</label>
                    </div>


                    <div class="row"><br/></div>
                    <div class="row" id="divSub" style="width:75%; margin-left:12%;">
                        <label for="inputEmail" style="padding-top: 6px;" class="control-label col-sm-4"> <span class="dispalyFields" > <s:property value="getText('Packaging')"/></span></label>
                        <div class="col-sm-8">
                            <s:if test='%{#upsServiceLevelCode == "3" || #upsServiceLevelCode == "12"}'>  
                                <s:select  list="pkgingMap" class="form-control" name="upsPackaging" id="upsPackaging" listKey="value" listValue="key"  value="%{'02'}"/>
                            </s:if>  
                            <s:else>
                                 <s:if test='%{#pkgingTemp !=null && #pkgingTemp != "" }'>
                                             <s:select  list="pkgingMap" class="form-control" name="upsPackaging" id="upsPackaging" listKey="value" listValue="key"  value="%{#pkgingTemp}"/>
                                 </s:if>
                                 <s:else>
                                     <s:select  list="pkgingMap" class="form-control" name="upsPackaging" id="upsPackaging" listKey="value" listValue="key"  value="%{'02'}"/>
                                 </s:else>
                            </s:else>
                            </div>
                     </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" style="width:75%; margin-left:12%;">
                        <label for="inputEmail" class="control-label col-sm-4"> <span class="dispalyFields" > <s:property value="getText('COD')"/></span></label>
                        <div class="col-sm-8">
                                <s:checkbox name="upsCodCheckBox" id="upsCodCheckBox" onclick="checkBoxFunc()" value="%{#codCheckedValue}" fieldValue="%{#codCheckedFlag}"/>
                                <s:hidden name="codTempFlag" id="codTempFlag"/>
                            </div>
                     </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" style="width:75%; margin-left:12%;">
                        <label for="inputEmail" style="padding-top: 6px;" class="control-label col-sm-4"> <span class="dispalyFields" > <s:property value="getText('CODAmount')"/></span></label>
                        <div class="col-sm-8">
                           <s:textfield class="form-control" name="upsCodAmt" id="upsCodAmt" value="" size="5"  maxlength="10"/>
                            </div>
                     </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub"  style="width:75%; margin-left:12%;">
                        <label for="inputEmail" style="padding-top: 6px;" class="control-label col-sm-4"> <span class="dispalyFields" > <s:property value="getText('CODType')"/></span></label>
                        <div class="col-sm-8">
                            <s:if test="%{#codType != null && #codType != ''}">  
                                <s:select class="form-control" list="upsCodMap" name="upsCodCode" id="upsCodCode" listKey="value" listValue="key" value="#codType"/>
                            </s:if>
                            <s:else>
                                <s:select class="form-control" list="upsCodMap" name="upsCodCode" id="upsCodCode" listKey="value" listValue="key" value="%{'3'}"/>
                            </s:else>
                            </div>
                     </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" style="width:75%; margin-left:12%;">
                        <label for="inputEmail" style="padding-top: 6px;" class="control-label col-sm-4"> <span class="dispalyFields" > <s:property value="getText('CODFundsCode')"/></span></label>
                        <div class="col-sm-8">
                            <s:if test="%{#codFundsCode != null && #codFundsCode != ''}">  
                                <s:select class="form-control" list="upsFundsCodMap" name="upsCodFundsCode" id="upsCodFundsCode" listKey="value" listValue="key" value="#codFundsCode"/>
                              </s:if>
                              <s:else>
                                 <s:select class="form-control" list="upsFundsCodMap" name="upsCodFundsCode" id="upsCodFundsCode" listKey="value" listValue="key" value="%{'8'}"/>
                              </s:else> 
                            </div>
                     </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" style="width:75%; margin-left:12%;">
                        <label for="inputEmail" style="padding-top: 6px;" class="control-label col-sm-4"> <span class="dispalyFields" > <s:property value="getText('CODCurrencyCode')"/></span></label>
                        <div class="col-sm-8">
                            <s:select class="form-control" name="upsCodCurrCode" id="upsCodCurrCode" list="#{'USD':'USD'}"/> 
                            </div>
                     </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" style="width:75%; margin-left:12%;">
                        <label for="inputEmail" style="padding-top: 6px;" class="control-label col-sm-4"> <span class="dispalyFields" > <s:property value="getText('DeliveryConfirmation')"/></span></label>
                        <div class="col-sm-8">
                            <select class="form-control" name="upsDelConfirm" id="upsDelConfirm">
                                  <c:forEach var="iterator" items="${upsDcisMap}">
                                
                                    <c:set var="hashMapKey" value="${iterator.key}" />
                                    <c:set var="hashMapValue" value="${iterator.value}" />
                    
                                    <c:choose>
                                    <c:when test="${fn:toLowerCase(hashMapValue) == fn:toLowerCase(delConfirm) && delConfirm != null && !(delConfirm == '')}">
                                        <option value="${hashMapValue}" selected ><c:out value='${hashMapKey}' /></option>
                                    </c:when> 
                                    
                                    <c:when test="${upsServiceLevelCode =='14' && (delConfirm==null || delConfirm == '')}">
                                        <c:if test="${hashMapValue == '3'}">
                                            <option value="${hashMapValue}" selected ><c:out value='${hashMapKey}' /></option>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${delConfirm == null || delConfirm == '' && !(upsServiceLevelCode == '14')}">
                                        <c:choose>
                                            <c:when test="${codFlag == 'Y' && (hashMapValue == '3')}">
                                                    <option value="${hashMapValue}" selected ><c:out value='${hashMapKey}' /></option>
                                            </c:when>
                                            <c:when test="${codFlag == 'N' || codFlag == ''}">
                                                <c:choose>
                                                    <c:when test="${hashMapValue == 'NA'}">
                                                        <option value="${hashMapValue}" selected ><c:out value='${hashMapKey}' /></option>
                                                    </c:when>
                                                    <c:otherwise>
                                                      <option value="${hashMapValue}"  ><c:out value='${hashMapKey}' /></option>
                                                    </c:otherwise>
                                               </c:choose>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                            <option value="${hashMapValue}"  ><c:out value='${hashMapKey}' /></option>
                                    </c:otherwise>
                                    </c:choose>
                                  </c:forEach>
                               </select>
                            </div>
                     </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" style="width:75%; margin-left:12%;">
                        <label for="inputEmail" style="padding-top: 6px;" class="control-label col-sm-4"> <span class="dispalyFields" > <s:property value="getText('PackageSurCharge')"/></span></label>
                        <div class="col-sm-8">
                            <s:textfield class="form-control" name="pkgSurCharge" id="pkgSurCharge" value="%{''}" size="4"/> 
                            </div>
                     </div>
                    <div class="row"><br/></div>
            <div class="row" id="divSub">
                <div class=" col-sm-1" >
                </div>
                <div class=" col-sm-10" align="center" id="b4Ship">
                         <button class="btn btn-success" name="Save" id="saveId" onclick="return savePackageOptionsDetails();" alt=""> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                         <button class="btn btn-warning" name="cancel" id="cancelId" onclick="javascript:loadPackageOptions();"  alt="" > Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button> 
                         <button class="btn btn-danger" name="close" id="closeId" onclick="javascript:window.close();"  alt=""> Close <span class="badge"><span class="glyphicon glyphicon-off"></span></span></button>
                </div>
                <div class=" col-sm-10" align="center" id="afterShip">
                         <button class="btn btn-success" name="Save" id="saveId1" disabled  alt=""> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button><!--Added disabled attribute to fix issue# 3051-->
                         <button class="btn btn-warning" name="cancel" id="cancelId1" onclick="javascript:loadPackageOptions()" alt=""  disabled> Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button> <!--Added disabled attribute to fix issue# 3051-->
                         <button class="btn btn-danger" name="close" id="closeId"  onclick="javascript:window.close();" alt=""> Close <span class="badge"><span class="glyphicon glyphicon-off"></span></span></button><!--Added function to close window -->
                </div> 
                <div class=" col-sm-1" >
                </div>
             </div>
             <div class="row"><br/></div>
                    
                    
                    
     </div>               
     
     
      </s:form>
       <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>

