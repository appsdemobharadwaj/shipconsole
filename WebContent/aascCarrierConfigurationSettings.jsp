<%@ page contentType="text/html;charset=UTF-8"%>
<%
/*========================================================================================================+
| DESCRIPTION                                                                                             |
|   JSP aascCarrierConfigurationSettings for displaying the carrier                                       | 
|   Configuration related information                                                                     |
|   Author Shaik KHaja                                                                                  |
|   Version   1.1                                                                                         |
|   Creation 15/9/2014                                                                                    |
    
    10/12/2014  Y Pradeep   Fixed all issues relaed to UI, Roles, Validations. Addded code for missed fileds for retriving and saving. Arranged code in proper order.
    18/12/2014  Y Pradeep   Modified nullStrToSpc method.
    02/01/2015  Y Pradeep   Replaced actionType with submit11 for Go button.
    07/01/2015  Y Pradeep   Modified code to make field empty when carrier name is changed in all roles.
    07/01/2015  Y Pradeep   Renamed key1 to key as per coding standerds.
    07/01/2015  Y Pradeep   Removed unused <tr> <td> tags as suggested in code review.
    13/01/2015  Y Pradeep   Fixed bug #2509 by changing op900LabelFormat to op900Format.
    15/01/2015  Y Pradeep   Merged Sunanda's code : getting title name from Application.Property file.
    21/01/2015  K Sunanda   Added code to display success messages in green and errored in red for bug #2506 
    04/02/2015  K Sunanda   Added Id for display messages
    16/02/2015  Suman G     Commented code to fix #2610.
    17/02/2015  Suman G     Modified code to fix #2506.
    05/03/2015    Sanjay & Khaja Added code for new UI changes.
    10/03/2015  Y Pradeep   Added missing name for param tag.
    10/03/2015  Jagadish    Added code for new UI changes.
    08/04/2015  Y Pradeep   Removed code to display Location LOV enabled for all roles except role 5. Bug #2807.
    15/04/2015  Suman G     Added session to fix #2743
    23/04/2015  Y Pradeep   Removed footer.
    11/06/2015  Suman G     Removed fevicon to fix #2992
    22/06/2015  Naresh      Applied the fix for #3063
    23/06/2015   Rakesh K       Modifed code to fix #3061
    25/06/2015   Rakesh K       Added styles to fix #3064
    14/07/2015  Rakesh K        UI Aligned for Role 1
    20/07/2015  Suman G         Added code for implement Email Notification.
    21/07/2015    Suman G        Modified message id for QA
    24/07/2015  Rakesh K    Modified Code to work application in offline.
    28/07/2015  Rakesh K    Modified Code for #3262.
    04/08/2015  Suman G     Modified code for issue #3294
    04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
    26/08/2015  Rakesh K       Added code to solve 3496.
    26/08/2015  N Srisha    Added Id's for Automation testing
    01/11/2015  Mahesh V    Added code for Stamps.com new fields implemented for Stamps.com Integration.
    24/11/2015  Y Pradeep   Modified code to set proper UI in carrier page.Bug #4036.
    01/11/2015  Mahesh V    Added code for Stamps.com new fields implemented for Stamps.com Integration
+=======================================================================================================*/%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>  



<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('SCCarrierConfigurationOptions')" /></title>
   
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
   
    <!--<script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />-->
    
    <script language="JavaScript" type="text/javascript" src="aascCarrierConfiguration_js.js"></script>
    
    <link href="aasc_ss.css" rel="stylesheet" type="text/css"/>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    
    <!--<link href="kendo.common-material.min.css" rel="stylesheet" />    -->
    
    
    <script type="text/javascript">
    function redirectToLogin(){
            
        document.getElementById("aascCarrierConfigurationFormId").action = "login.action?actiontype=Logout";
        document.getElementById("aascCarrierConfigurationFormId").submit();
            
    }
    </script>
  <!--  <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />-->
        
    <style type="text/css"> 
     
  #divRow{
 margin-left:3%;
 margin-right:3%;
 }
 div{
 font-size:12px;
  /*font-weight: bold;*/
 }

 textfield {
    width: 150px;
   	border-radius:5px;
}
input[type=tel] {
    width: 150px;
   	border-radius:5px;
}
input[type=email] {
    width: 150px;
   	border-radius:5px;
}
select{
width: 150px;
   	border-radius:5px;
}

    </style> 
    <!--<style type="text/css">
        .href {color: #003399; font-weight: bold;}
        .href1 {color: #003399}
    </style>-->
  </head>
  
  <body style="background-color:#ADADAD;" onLoad="disableLocation();enableEmailNotification();disabledAdminField();disableEmailflag();enableFlagChk();">
  
   <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
  
    <s:set name="roleId" value="%{#attr.role_id}"/>
        <jsp:useBean id="locationsMap" class="java.util.HashMap"/>
        <jsp:useBean id="carrierNamesMap" class="java.util.HashMap"/>
        <jsp:useBean id="aascCarrierConfigurationBean" class="com.aasc.erp.bean.AascCarrierConfigurationBean"/>
        <jsp:useBean id="carrierValuesHashMap" class="java.util.HashMap"/>
        <s:bean name="java.util.HashMap" id="carrierModeHashMap" />
        <jsp:useBean id="labelFormatHashMap" class="java.util.HashMap" />
        <jsp:useBean id="labelStockHashMap" class="java.util.HashMap"/>
        <jsp:useBean id="docTabHashMap" class="java.util.HashMap"/>
        <s:set name="SubmitType" value='%{#session.submitType)}' /> 
        
        <!--Mahesh-->
        <jsp:useBean id="paperSizeHashMap" class="java.util.HashMap"/>
        <jsp:useBean id="intlPrintLayoutHashMap" class="java.util.HashMap"/>
        <jsp:useBean id="nonDeliveryHashMap" class="java.util.HashMap"/>
            
        <c:catch var="exception2">
            <s:set name="clientDetailsHashMap" value="%{#session.clientDetailsHashMap}"/>
        </c:catch>
        <s:if test="%{#exception2 != null || #clientDetailsHashMap == null}">
             <s:bean id="clientDetailsHashMap" name="java.util.HashMap">
                <s:param name="clientDetailsHashMap" value="%{'select'}"/>
             </s:bean>
        </s:if>
      
        
                <c:catch var="exception10">
            <s:set name="paperSizeHashMap" value="#session.paperSize"/>
        </c:catch>
        <s:if test="%{#exception10 != null}">
             <c:set target="${paperSizeHashMap}" property="select" value="select"/>
        </s:if>
                <c:catch var="exception11">
            <s:set name="intlPrintLayoutHashMap" value="#session.intlPrintLayout"/>
        </c:catch>
        <s:if test="%{#exception11 != null}">
             <c:set target="${intlPrintLayoutHashMap}" property="select" value="select"/>
        </s:if>
                <c:catch var="exception12">
            <s:set name="nonDeliveryHashMap" value="#session.nonDelivery"/>
        </c:catch>
        <s:if test="%{#exception12 != null}">
             <c:set target="${nonDeliveryHashMap}" property="select" value="select"/>
        </s:if>
        
        <!--Mahesh End-->
        <c:catch var="exception8">
            <s:set name="docTabHashMap" value="#session.docTabLocation"/>
        </c:catch>
        <s:if test="%{#exception8 != null}">
             <c:set target="${docTabHashMap}" property="select" value="select"/>
        </s:if>
      
        <c:catch var="exception7">
            <s:set name="labelStockHashMap" value="#session.labelStockOrientation"/>
        </c:catch>
        <s:if test="%{#exception7 != null}">
             <c:set target="${labelStockHashMap}" property="select" value="select"/>
        </s:if>
      
        <c:catch var="exception4">
            <s:set name="labelFormatHashMap" value="#session.labelFormats"/>
        </c:catch>
        <s:if test="%{#exception4 != null}">
             <c:set target="${labelFormatHashMap}" property="select" value="select"/>
        </s:if>
        
        <c:catch var="exception5">
            <s:set name="carrierModeHashMap" value="%{#attr.carrierModeMap}"/>
            <s:if test="%{#carrierModeHashMap == null}">
                <s:set name="exception5" value="%{'nullpointerexception'}"/>
            </s:if>
        </c:catch>
        <s:if test="%{#exception5 != null}">
               <s:bean name="java.util.HashMap" id="carrierModeHashMap" />
        </s:if>
      
        <c:catch var="exception6">
            <s:set name="carrierValuesHashMap" value="#session.carrierValuesHashMap"/>
        </c:catch>
        <s:if test="%{#exception6 != null}">
             <c:set target="${carrierValuesHashMap}" property="select" value="select"/>
        </s:if>
         
        <c:catch var="exception9">
            <s:set name="aascCarrierConfigurationBean" value="#session.aascCarrierConfiguration"/>
            <s:if test="%{#aascCarrierConfigurationBean == null}">          
                <s:set name="exception9" value="%{'NullPointerException'}"/>
            </s:if>
        </c:catch>
        
        <c:catch var="exception1">
            <s:set name="shipMethodChk" value="#session.shipMethodChk"/>
            <s:if test="%{#shipMethodChk == null}" >
                <s:set name="shipMethodChk" value="%{'N'}"/>
            </s:if> 
            <s:else>
                <s:set name="shipMethodChk" scope="session"/>
            </s:else>
        </c:catch>
        <s:if test="%{#exception1 !=null}">
            <s:set name="shipMethodChk" value="%{'N'}"/>
        </s:if>
            
         
        <c:catch var="exception3">           
            <s:set name="carrierNamesMap" value="%{#attr.carrierNames}" />
        </c:catch>
        <s:if test="%{#exception3 != null" >
            <s:bean name="java.util.HashMap" id="carrierNamesMap">
                <s:param name="carrierNamesMap" value="%{'select'}"/>
            </s:bean>
        </s:if>
               
        <c:catch var="exception2">
            <s:set name="locationsMap" value="%{#session.locationDetailsMap}" />
        </c:catch>
        <s:if test="%{#exception2 != null" >
            <s:bean name="java.util.HashMap" id="locationsMap">
                <s:param name="locationsMap" value="%{'select'}"/>
            </s:bean>
        </s:if>
        <%    
            try{
        %>
      <%@include file ="aascIndexHeader.jsp" %>
        <s:form   method="post" action="AascCarrierConfiguration" name="aascCarrierConfigurationForm" id="aascCarrierConfigurationFormId" onsubmit="return selectCarrier();">
        
                 <!--21/01/2015 sunanda added-->
                  <table>
                  <tr>
                       <c:catch var="exception1">
                        <s:set name="key" value="%{#attr.key}"/>
                            <s:if test="%{#key != null}">
                          <s:if test="%{#key=='aasc816'||#key=='aasc182'}">
                            <td width="90%" class="displayMessage1" id="displayMessage" align="center" valign="bottom" style="color:green">
                              <s:property value="getText(#key)"/> 
                            </td>
                          </s:if>
                          <s:else>
                            <td width="90%" class="displayMessage" id="displayMessage" align="center" valign="bottom" style="color:green">
                                <s:property value="getText(#key)"/> 
                            </td>
                          </s:else>
                          <s:set name="key" value="%{'null'}"/>
                            </s:if>
                        </c:catch>
                        <s:if test="%{#exception3 != null}">
          <!--  <c:redirect url="/aascShipConsoleError.jsp">  -->
                            <s:param name="errorObj" value="#exception.message}" />
           <!-- </c:redirect>-->
                        </s:if>
                        </tr>
         </table>
                        <s:hidden name="roleIdHidden" id="roleIdHiddenID" value="%{#roleId}" />
              
          
            <s:set name="session" value='#request.session'/>
             
             <br>
             
                   <!--   <div class="container-fluid" style="width:95% ;margin-left:auto; margin-right:auto;margin-top:50px;border:1px #ECE0F8;border-radius:5px;padding-left:0px;padding-right:0px;background-color:#F0F0F0">
<div  style="color: white;width:100%; height:40px;font-size:20px; background-color:#19b698; border-radius:5px;font:#f7f7ff; ">
     
                <label style="margin-left:30px;margin-top:5px;">       --> 
                <div class="col-sm-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #19b698; border-style: solid;margin-left:1%;margin-right:1%;width:98%;;margin-bottom:20px">
                          <div style="background-color:#19b698;margin-top:5px;margin-left:5px">
                          <div >
                            <div align="center" style="color:#ffffff;padding-left:0px; padding-top:0px;height:30px;font-size:20px;font-weight:bold">
                                 <s:property value="getText('CarrierConfigurations')"/></div>
                            </div>
                            </div>
             <div class="row"> <br/></div>
             
             <div class="row" id="divRow12">
            <div class="col-sm-12">
                <s:if test="%{#roleId == 1 || #roleId == 2}">
                        <div class="col-sm-1" style="margin-top:6px;">
                            <label class="control-label" for="name" ><s:property value="getText('ClientDetails')"  /></label>
                          </div>
                        <s:if test="%{#clientIdNum == null}"  >
                          <div class="col-sm-2">
                                  <s:select name="clientId" id="clientID" size="1" class="form-control" onchange="getLocationList();onCarrierClientChange();"
                                            list="#clientDetailsHashMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey=""/>
                            </div>
                        </s:if>
                        <s:else>
                               <div class="col-sm-2">
                                <s:select name="clientId" id="clientID" class="form-control" size="1" onchange="getLocationList();onCarrierClientChange();"
                                        list="#clientDetailsHashMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey=""/>
                                </div>
                        </s:else>
                     </s:if>
                      <s:else>
                        <td> <s:hidden name="clientId" id="clientId" value="%{#session.client_id}"/></td>
                      </s:else>
                      <s:hidden  name="clientIdVal" />
               </div>
             </div>
             <br/>
                <div class="row" id="divRow23">
                    <!--<div class="form-group" style="margin-top:86px;">-->
                    <div class="col-sm-12">
                            <div class="col-sm-1" style="margin-top:6px;">
                                <label  for="name"><s:property value="getText('Location')" /></label>
                            </div>
                             <div class="col-sm-2">
                                  <s:select name="locationId" id="locationID" class="form-control" size="1" 
                                    list="#locationsMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey=""  value="#aascCarrierConfigurationBean.locationId" />
                            </div>
                            <div class="col-sm-2" style="margin-top:6px;">
                                <label  for="name" style="color:solid;" ><s:property value="getText('CarrierName')"/></label>
                            </div>
                             <s:if test="%{#roleId != 5}">
                                <s:if test="%{#aascCarrierConfigurationBean.carrierName == null}"  >
                                <div class="col-sm-2">
                                     <s:select name="carrierName" id="carrierNameID" class="form-control" size="1" 
                                    list="#carrierNamesMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey="" value="" onchange="onCarrierCarrierChange();"/>
                                </div>
                                </s:if>
                                <s:else>
                                <div class="col-sm-2">
                                    <s:select name="carrierName" id="carrierNameID" class="form-control" size="1" 
                                    list="#carrierNamesMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey="" value="#aascCarrierConfigurationBean.carrierName" onchange="onCarrierCarrierChange();"/> 
                                </div>
                                </s:else>
                            </s:if>
                             <s:else>
                             <div class="col-sm-2">
                                <s:select name="carrierName" id="carrierNameID" class="form-control" size="1" 
                                list="#carrierNamesMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey="" value="#aascCarrierConfigurationBean.carrierName" /> 
                            </div>
                            </s:else>
                             <s:if test="%{#aascCarrierConfigurationBean.carrierId !=0}">
                                 <s:hidden name="carrierId" value="%{#aascCarrierConfigurationBean.carrierId}"/>
                             </s:if>
                            <s:else>
                                 <s:hidden name="carrierId" value="0"/>
                            </s:else>
                            <s:hidden name="carrierCodeVal" value="%{#request.carrierCode}"/>
                            <s:hidden name="carrierNameStr" />
                            <s:hidden name="submit11" value=""/>
                            <s:if test="%{#roleId == 1 || #roleId == 2 || #roleId == 3 || #roleId == 4}">
                                <div class="col-sm-5">
                                <div class="col-sm-2" align="left">
                                    <button  name="GoButton" id="GoButton" class="btn btn-primary" onclick="setClientIdVal();document.aascCarrierConfigurationForm.submit11.value='Go';"   >Go </button>
                                    <!--<input name="GoButton" type="image" class="form-control"  id="GoButton" onclick="setClientIdVal();document.aascCarrierConfigurationForm.submit11.value='Go';" value="Go" src="buttons/go.jpeg" alt="[submit]" align="middle">-->
                                </div>
                                <s:hidden  name="shipMethodChk" value="%{#shipMethodChk}"/>
                            <s:if test='%{#shipMethodChk == "Y"}'>
                                <div class="col-sm-10">
                               <button name="goShipImage" id="goShipImage" class="btn btn-primary" size="20"  onClick="setClientIdVal();document.aascCarrierConfigurationForm.submit11.value='ShipMethod';" value="Go to Shipping Method"  >GO TO Ship Method Mapping <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                                <!--<input name="goShipImage" id="goShipImage" type="image" size="20"  onClick="setClientIdVal();document.aascCarrierConfigurationForm.submit11.value='ShipMethod';" src="buttons/goShipmap1.png" align="middle"/>-->
                                </div>
                            </s:if>
                            <s:else>
                            <div class="col-sm-10">
                             <button name="goShipImage" id="goShipImage" class="btn btn-primary" size="20"   value="Go to Shipping Method"  disabled>GO TO Ship Method Mapping <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                              <!--  <s:a href="#" id="goShipImage" name="goShipImage"> <img  name="goShipImage" alt="" id="goShipImage" src="buttons/goShipmapOff1.png"  border=0 align="middle"/></s:a>-->
                               </div> 
                            </s:else>
                                </div>
                            </s:if>
                            
                                      
                     </div>
                        </div>
                     <br/>
                    <!-- <div class="row">
                        <div class="col-sm-12">
                            <s:hidden  name="shipMethodChk" value="%{#shipMethodChk}"/>
                            <s:if test='%{#shipMethodChk == "Y"}'>
                                <div class="col-sm-2">
                               <button name="goShipImage" id="goShipImage" class="btn btn-primary" size="20"  onClick="setClientIdVal();document.aascCarrierConfigurationForm.submit11.value='ShipMethod';" value="Go to Shipping Method"  >GO TO Ship Method Mapping <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                                </div>
                            </s:if>
                            <s:else>
                            <div class="col-sm-2">
                             <button name="goShipImage" id="goShipImage" class="btn btn-primary" size="20"   value="Go to Shipping Method"  disabled>GO TO Ship Method Mapping <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                               </div> 
                            </s:else>
                        </div>
                     </div>
                     <br/>-->
                     </div>
                  
                  
                    
                 <br/>
                
                   
                          <div class="col-sm-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #ea6153; border-style: solid;margin-left:1%;margin-right:1%;width:98%;margin-bottom:20px">
                          <div style="background-color:#ea6153;margin-top:5px;margin-left:5px">
                                    <label style="color:#ffffff;padding-left:10px; padding-top:10px;height:30px;">Carrier Server Details</label>
                                    </div>
                              <br/>
                                  
                               <div class="row">
                                    <div class="col-sm-12">                              
                                       <div class="col-sm-2" >
                                        <label><s:property value="getText('CarrierCode')"/> </label>
                                            </div>
                                              <div class="col-sm-2"> 
                                                <s:select  name="carrierCodeValue" id="carrierCodeValueId" class="form-control"  size="1" onchange="LoadLabels();"
                                                    list="carrierValuesHashMap" listKey="key" listValue="value" headerKey="0" headerValue="Select" value="#aascCarrierConfigurationBean.carrierCodeValue"/>
                                                <s:hidden name="carrierCode" id="carrierCode" value="%{#aascCarrierConfigurationBean.carrierCodeValue}"/>
                                            </div>
                                            <s:set name="flagChk" value="%{''}"/>
                                            <c:catch var="exception16">
                                                <s:if test='%{#aascCarrierConfigurationBean.enableFlag == "Y"}'>
                                                    <s:set name="flagChk" value="%{'true'}"/>
                                                </s:if>
                                            </c:catch>
                                            <s:if test="%{#exception16 != null}">
                                                <s:set name="flagChk" value="%{''}"/>
                                            </s:if>
                                            <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;"><s:property value="getText('EnableFlag')"/></div>
                                            <s:set name="enableFlag" value="%{#aascCarrierConfigurationBean.enableFlag}"/>
                                             <div class="col-sm-2">
                                                <s:checkbox name="enableFlag" id="enableFlagId" value="%{#flagChk}" onclick="enableFlagChk()"  fieldValue="%{#enableFlag}" />
                                            </div>
                                            <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;"><s:property value="getText('Protocol')"/></div>
                                          <div class="col-sm-2">
                                                <s:set name="protocol" value="%{#aascCarrierConfigurationBean.protocol}" />
                                                <s:select list="#{'http':'http','https':'https'}" name="protocol" id="protocolId" class="form-control" value="#protocol" size="1" /> 
                                            </div>
                                       </div>     
                               </div> 
                               <br/>
                               <div class="row">
                                <div class="col-sm-12">
                                           
                                            <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;"><s:property value="getText('Port')"/></div>
                                            <div class="col-sm-2">
                                                <s:textfield  name="port" id="portId"  class="form-control" size="10" value="%{#aascCarrierConfigurationBean.port}" onblur="portValid()"/>
                                            </div>
                                            <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;">
                                           <s:property value="getText('Mode')"/>
                                           </div>
                                           <div class="col-sm-2">
                                                <s:select  list="carrierModeHashMap" name="Mode" id="ModeId" listKey="key" listValue="value"  headerKey="" headerValue="Select" class="form-control"  value="%{#aascCarrierConfigurationBean.carrierMode}" />
                                            </div>
                                            <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;"><s:property value="getText('ServerIPAddress')"/></div>
                                             <div class="col-sm-2"><s:textfield name="serverIpAddress" id="serverIpAddressId" class="form-control" size="50" value="%{#aascCarrierConfigurationBean.serverIpAddress}"/></div>
                                        </div>    
                               </div>
                               <br/>
                               <div class="row" >
                                    <div class="col-sm-12">
                                            
                                             <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;"><s:property value="getText('UserName')"/></div>
                                             <div class="col-sm-2"><s:textfield name="userName" id="userNameId" class="form-control" size="50" value="%{#aascCarrierConfigurationBean.userName}"/></div>
                                            <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;"><s:property value="getText('Password')"/></div>
                                            <div class="col-sm-2"><s:textfield name="password" id="passwordId" class="form-control" size="50" value="%{#aascCarrierConfigurationBean.password}"/></div>
                                            <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;"><s:property value="getText('Prefix')"/></div>
                                            <div class="col-sm-2"><s:textfield name="prefix" id="prefixId" class="form-control" size="50" value="%{#aascCarrierConfigurationBean.prefix}"/></div>
                                            
                                    </div>
                               </div>
                               <br/>
                               <div class="row">
                                    <div class="col-sm-12">
                                            
                                             <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;"><s:property value="getText('Domain')"/></div>
                                             <div class="col-sm-2"><s:textfield name="domain" id="domainId" class="form-control" size="50" value="%{#aascCarrierConfigurationBean.domain}"/></div>
                                             <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;"><s:property value="getText('AccountNo')"/></div>
                                             <div class="col-sm-2"><s:hidden name="accountNo" id="accountNoId" cssClass="inputs" value="%{#aascCarrierConfigurationBean.accountNo}"/> 
                                                <!--<s:a href="javascript:openAccountNumbers()" onclick="return onClickAccountNumbers();"><img  name="acctNumberDetails" src="buttons/aascDetails1.png" align="middle" border=0 alt="Account Numbers" height="20" width="60"></s:a>-->
                                                <s:a  href="javascript:openAccountNumbers()" class="btn btn-primary" onclick="return onClickAccountNumbers();" name="acctNumberDetails">Account details <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></s:a>
                      <!--a href="javascript:openAccountNumbers()" onclick="return onClickAccountNumbers();"> <img  name="acctNumberDetails" src="buttons/aascDetailsOff1.png" align="middle" border=0 alt="Account Numbers" height="20" width="60"></a-->

                                             </div>
                        <div id="IntegrationIdDiv"> <!--Shiva added-->                                           
                        <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;"><s:property value="getText('IntegrationId')"/></div>
                        <div class="col-sm-2"><s:textfield name="integrationId" id="integrationId" class="form-control" size="50" value="%{#aascCarrierConfigurationBean.integrationId}"/></div>
                        </div>
                        <!--Shiva added below code for DHL -->
                        <div id="DHLRegionCodeDiv">
                            <div class="col-sm-2" style="font-weight:bold;margin-top: 6px;"><s:property value="getText('RegionCode')"/></div>
                            <div class="col-sm-2"><s:select list="#{'AM':'Americas (LatAm + US +CA)','AP':'Asia Pacific + Emerging Market','EU':'Europe (EU + Non-EU)'}" name="dhlRegionCode" id="dhlRegionCodeId" class="form-control" headerKey="" headerValue="Select" value="%{#aascCarrierConfigurationBean.dhlRegionCode}" size="1" /></div>
                        </div>
                        <!--Shiva code end -->
                        
                                          </div>   
                               </div>
                                        
                                        
        
                                        <s:set name="supportHazmatShippingTxt" value="%{#aascCarrierConfigurationBean.supportHazmatShipping}"/>
                 <!--                       <tr class="tableDataCell" id="supportHazmatLabel">
                                            <td height="26" colspan="2" class="dispalyFields"><s:property value="getText('SupportHazmatShipping')"/></td>
                                            <td>
                                                <s:select list='#{"Yes":"Yes","":"No"}' name="supportHazmatShipping" value="%{#supportHazmatShippingTxt}" cssClass="inputFields" />
                                            </td>
                                        </tr>   -->
                                <br/>
                                <div class="row" >
                                    <div class="col-sm-12">
                                           <div class="col-sm-2"><div id="hazmatOp900Label" style="font-weight:bold"><s:property value="getText('HazmatOp900LabelFormat')"/></div></div>
                                                <div class="col-sm-2" >
                                        
                                                <s:set name="op900LabelFormatTxtTemp" value="%{#aascCarrierConfigurationBean.op900Format}" />
                                                <s:set name="op900LabelFormatTxt" value="%{#op900LabelFormatTxtTemp.toUpperCase()}" />
                                                <s:if test="%{#op900LabelFormatTxtTemp.length()==0}">
                                                <s:set name="op900LabelFormatTxt" value="%{''}" />   
                                                </s:if>
                                                <s:select list="#{'PDF':'PDF','TEXT':'TEXT'}" name="Op900LabelFormat" id="Op900LabelFormatId" headerKey="--Select--" headerValue="--Select--" size="1" value="#op900LabelFormatTxt" class="form-control" />
                                            </div>
                                            <s:hidden name="accessLicenseNumber" id="accessLicenseNumberId" class="form-control" value="#aascCarrierConfigurationBean.accessLicenseNumber"/>
                                            <s:hidden name="meterNumber" id="meterNumberId" class="form-control" value="#aascCarrierConfigurationBean.meterNumber"/>
                                            <div class="col-sm-2">
                                                <c:set var="flag" value=""/>
                                                <c:catch var="exception17">
                                                    <s:if test="%{#aascCarrierConfigurationBean.acctNumNegotiatedFlag == 'Y'}">
                                                        <s:set name="flag" value="%{'checked'}"/>
                                                    </s:if>
                                                </c:catch>
                                                <s:if test="%{#exception17 != null}">
                                                    <s:set name="flag" value="%{''}"/>
                                                </s:if>
                                                <s:hidden id="acctNumNegotiatedFlagId" name="acctNumNegotiatedFlag" value="#aascCarrierConfigurationBean.acctNumNegotiatedFlag"  />  <!-- need to be checked -->
                                            </div>
                                           <div class="col-sm-2">
                                                <s:set name="flag" value="%{''}"/>
                                                <c:catch var="exception18">
                                                    <s:if test="%{#aascCarrierConfigurationBean.nonDiscountedCost == 'Y'}">
                                                        <s:set name="flag" value="%{'checked'}"/>
                                                    </s:if>
                                                </c:catch>
                                                <s:if test="%{#exception18 != null}">
                                                    <s:set name="flag" value="%{''}"/>
                                                </s:if>
                                                <s:hidden name="nonDiscountedCost" id="nonDiscountedCostId" value="#aascCarrierConfigurationBean.nonDiscountedCost"  />
                                            </div>
                                    </div>
                                </div>
                                <br/>
                        </div>
                        <br/>       
 <div class="col-sm-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #3d566d; border-style: solid;margin-left:1%;margin-right:1%;width:98%;margin-bottom:20px">
                          <div style="background-color:#3d566d;margin-top:5px;margin-left:5px">
 <label style="color:#ffffff;padding-left:10px; padding-top:10px;height:30px;">Printer Details</label>
 </div>              
         
<br/>
<div class="row">
    <div class="col-sm-12">
        <div class="col-sm-2" style="font-weight:bold;margin-top:6px;"><s:property value="getText('LabelFormat')"/></div><!--modified display name from "Port" to "Label Format"  by vandana on 11/04/2007-->
        <div class="col-sm-2">
                                                <%
                                                    if((pageContext.getAttribute("labelFormatHashMap"))==null)
                                                    {
                                                      labelFormatHashMap=new java.util.HashMap();
                                                      labelFormatHashMap.put("select","select");
                                                      pageContext.setAttribute("labelFormatHashMap",labelFormatHashMap);
                                                    }
                                                %> 
                                                <s:set name="labelFormatHashMap" value="%{#attr.labelFormatHashMap}"/>
                                                <s:set name="pdPort" value="%{#aascCarrierConfigurationBean.pdPort}" />
                                               <s:select name="pdPort" id="pdPortId" class="form-control" list="labelFormatHashMap" listKey="key" listValue="value" value="#pdPort" />
                   
        </div>
        
        <div class="col-sm-2" style="font-weight:bold;margin-top:6px;"><s:property value="getText('LabelStockType')"/></div>
        <div class="col-sm-2">
                                                <%
                                                    System.out.println("test 111111111111111");
                                                    System.out.println("Beean ::::::::::::::::::::::"+pageContext.getAttribute("aascCarrierConfigurationBean"));
                                                    System.out.println("test ######################");
                                                    try{ com.aasc.erp.bean.AascCarrierConfigurationBean info=(com.aasc.erp.bean.AascCarrierConfigurationBean)pageContext.getAttribute("aascCarrierConfigurationBean");
                                                          System.out.println("test $$$$$$$$$$$$$$$$");
                                                          if(info!=null)
                                                          {
                                                            String labelStock1= nullStrToSpc(info.getLabelStock());
                                                            System.out.println("test 222222222");
                                                            String docTab1= nullStrToSpc(info.getDocTab());
                                                            System.out.println("test 333333333333");
                                                            pageContext.setAttribute("labelStock1",labelStock1);
                                                            System.out.println("test 44444444");
                                                            pageContext.setAttribute("docTab1",docTab1);
                                                            System.out.println("test 555555555555");
                                                            
                                                            String nonDelivery = nullStrToSpc(info.getNonDelivery()); 
                                                            String paperSize = nullStrToSpc(info.getPaperSize());
                                                            String intlPrintLayout = nullStrToSpc(info.getIntlPrintLayout());
                                                            
                                                            pageContext.setAttribute("nonDelivery",nonDelivery);
                                                            pageContext.setAttribute("paperSize",paperSize);
                                                            pageContext.setAttribute("intlPrintLayout",intlPrintLayout);
                                                            //Mahesh End
                                                          }
                                                        }catch(Exception e){
                                                            e.printStackTrace();
                                                        }
                                                    if((pageContext.getAttribute("labelStockHashMap"))==null)
                                                        {
                                                            System.out.println("test 666666666");
                                                            labelStockHashMap=new java.util.HashMap();
                                                            labelStockHashMap.put("select","select");
                                                            pageContext.setAttribute("labelStockHashMap",labelStockHashMap);
                                                        }
                                                %> 
                                                <s:set name="labelStockHashMap" value="%{#attr.labelStockHashMap}"/>
                                                <s:set name="labelStock" value="%{#attr.labelStock1}"/> 
                                                <s:select name="labelStock" id="labelStockId" class="form-control" list="labelStockHashMap" listKey="key" listValue="value" value="#labelStock"  />
        </div>
        
        <div class="col-sm-2" style="font-weight:bold;margin-top:6px;"><s:property value="getText('DocTabLocation')"/></div>
        <div class="col-sm-2">
                                                <%
                                                     if((pageContext.getAttribute("docTabHashMap"))==null)
                                                        {
                                                            System.out.println("docTabHashMap is null in jsp%%%%%%%%%%%%");
                                                            docTabHashMap=new java.util.HashMap();
                                                            docTabHashMap.put("select","select");
                                                            pageContext.setAttribute("docTabHashMap",docTabHashMap);
                                                        }
                                                %> 
                                                <s:set name="docTabHashMap" value="%{#attr.docTabHashMap}"/>
                                                <s:set name="docTab" value="%{#attr.docTab1}"/> 
                                                <s:select  name="docTab" id="docTabId" class="form-control" list="docTabHashMap" listKey="key" listValue="value" value="#docTab"  />
        </div>
    </div>
     </div> 
     <br/>
    <div class="row">
      <div class="col-sm-12">                                      
        <div class="col-sm-2" style="font-weight:bold;margin-top:6px;"><s:property value="getText('PaperSize')"/></div>
        <div class="col-sm-2">
                                                <%
                                                     if((pageContext.getAttribute("paperSizeHashMap"))==null)
                                                        {
                                                            System.out.println("paperSizeHashMap is null in jsp%%%%%%%%%%%%");
                                                            paperSizeHashMap=new java.util.HashMap();
                                                            paperSizeHashMap.put("select","select");
                                                            pageContext.setAttribute("paperSizeHashMap",paperSizeHashMap);
                                                        }
                                                %> 
                                                <s:set name="paperSizeHashMap" value="%{#attr.paperSizeHashMap}"/>
                                                <s:set name="paperSize" value="%{#attr.paperSize}"/> 
                                                <s:select  name="paperSize" id="paperSizeId" class="form-control" list="paperSizeHashMap" listKey="key" listValue="value" value="#paperSize"  />
        </div>
                                            
        <div class="col-sm-2" style="font-weight:bold;margin-top:6px;"><s:property value="getText('InternationalPrintLayout')"/></div>
        <div class="col-sm-2">
                                                <%
                                                     if((pageContext.getAttribute("intlPrintLayoutHashMap"))==null)
                                                        {
                                                            System.out.println("intlPrintLayoutHashMap is null in jsp%%%%%%%%%%%%");
                                                            intlPrintLayoutHashMap=new java.util.HashMap();
                                                            intlPrintLayoutHashMap.put("select","select");
                                                            pageContext.setAttribute("intlPrintLayoutHashMap",intlPrintLayoutHashMap);
                                                        }
                                                %> 
                                                <s:set name="intlPrintLayoutHashMap" value="%{#attr.intlPrintLayoutHashMap}"/>
                                                <s:set name="intlPrintLayout" value="%{#attr.intlPrintLayout}"/> 
                                                <s:select id="intlPrintLayoutId" name="intlPrintLayout" class="form-control" list="intlPrintLayoutHashMap" listKey="key" listValue="value" value="#intlPrintLayout"  />
        </div>

        <div class="col-sm-2" style="font-weight:bold;margin-top:6px;"><s:property value="getText('NonDeliveryOption')"/></div>
        <div class="col-sm-2">
                                                <%
                                                     if((pageContext.getAttribute("nonDeliveryHashMap"))==null)
                                                        {
                                                            System.out.println("nonDeliveryHashMap is null in jsp%%%%%%%%%%%%");
                                                            nonDeliveryHashMap=new java.util.HashMap();
                                                            nonDeliveryHashMap.put("select","select");
                                                            pageContext.setAttribute("nonDeliveryHashMap",nonDeliveryHashMap);
                                                        }
                                                %> 
                                                <s:set name="nonDeliveryHashMap" value="%{#attr.nonDeliveryHashMap}"/>
                                                <s:set name="nonDelivery" value="%{#attr.nonDelivery}"/> 
                                                <s:select  name="nonDelivery" class="form-control" list="nonDeliveryHashMap" listKey="key" listValue="value" value="#nonDelivery"  />
          </div>
        </div>
    </div>
    <br/>
</div>                        
                        
                       
 
                          <!-- <div class="container-fluid" style="width:95% ;margin-left:auto; margin-right:auto;margin-top:50px;border:1px #ECE0F8;border-radius:5px;padding-left:0px;padding-right:0px;background-color:#F0F0F0">
<div  style="color: white;width:100%; height:40px;font-size:20px; background-color:#7761a7; border-radius:5px;font:#f7f7ff; ">
<label style="margin-left:30px;margin-top:5px;"> Email settings</label> -->
<br/>

 <div class="col-sm-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%;margin-bottom:20px">
                          <div style="background-color:#7761a7;margin-top:5px;margin-left:5px">
 <label style="color:#ffffff;padding-left:10px; padding-top:10px;height:30px;">Email Settings</label>
 </div>
                            
                        
                <!--<tr>
                    <td colspan="4" align="center" class="headings">
                        <s:property value="%{#cName}"/> <s:property value="getText('EmailNotificationDetails')"/>
                    </td>
                </tr>-->
                  <div class="row"> <br/></div>
                  <div class="row" id="divRow">
                <div class="col-sm-2" style="font-weight:bold"><s:property value="getText('EmailNotificationRequired')"/></div>
                    <div class="col-sm-2"> 
                        <s:set name="flag1" value="%{''}"/>
                        <c:catch var="exception19">
                            <s:if test='%{#aascCarrierConfigurationBean.emailNotificationFlag == "Y"}'>
                                <s:set name="flag1" value="%{'true'}"/>
                            </s:if>
                        </c:catch>
                        <s:if test="%{#exception19 != null}">
                            <s:set name="flag1" value="%{''}"/>
                        </s:if>
                        <s:checkbox id="emailNotificationFlagId" name="emailNotificationFlag" fieldValue="%{#aascCarrierConfigurationBean.emailNotificationFlag}" value="#flag1" onclick="emailNotificationChkVal(this);enableEmailNotification()" />
                    </div>
                </div>  
                 <div class="row"> <br/></div>
                 <!-- <div class="row" id="divRow">
                    <div class="col-sm-2" style="font-weight:bold"><s:property value="getText('SenderEmail')"/></div>
                    <div class="col-sm-2"><s:textfield name="SenderEmail" class="form-control" size="50" value="%{#aascCarrierConfigurationBean.senderEmailAddress}" /></div>
                   
                </div> -->
               <div class="row"> <br/></div>
                  <div class="row" id="divRow">
                  <div class="col-sm-2" style="font-weight:bold"><s:property value="getText('Reference1Required')"/></div>
                    <div class="col-sm-2">
                        <s:set name="flag1" value="%{''}"/>
                        <c:catch var="exception20">
                            <s:if test='%{#aascCarrierConfigurationBean.referenceFlag1 == "Y"}'>
                                <s:set name="flag1" value="%{'true'}"/>
                            </s:if>
                        </c:catch>
                        <s:if test="%{#exception20 != null}">
                            <s:set name="flag1" value="%{''}"/>
                        </s:if>
                        <s:checkbox id="IsReference1RequiredId" name="IsReference1Required" fieldValue="%{#aascCarrierConfigurationBean.referenceFlag1}" value="%{#flag1}" onclick="emailNotificationChkVal(this);" />
                    </div>
                   <div class="col-sm-2" style="font-weight:bold"><s:property value="getText('Reference2Required')"/></div>
                   <div class="col-sm-2">
                        <s:set name="flag1" value="%{''}"/>
                        <c:catch var="exception21">
                            <s:if test='%{#aascCarrierConfigurationBean.referenceFlag2 == "Y"}'>
                                <s:set name="flag1" value="%{'true'}"/>
                            </s:if>
                        </c:catch>
                        <s:if test="%{#exception21 != null}">
                            <s:set name="flag1" value="%{''}"/>
                        </s:if>
                        <s:checkbox id="IsReference2RequiredId" name="IsReference2Required" fieldValue="%{#aascCarrierConfigurationBean.referenceFlag2}" value="%{#flag1}" onclick="emailNotificationChkVal(this);"/>
                    </div>
                </div> 
                
                
                <div class="row"> <br/></div>
                  <div class="row" id="divRow">
                    <div class="col-sm-2" style="font-weight:bold"><s:property value="getText('ShipNotification')"/></div>
                  <div class="col-sm-2">
                        <s:set name="flag1" value="%{''}"/>
                        <c:catch var="exception22">
                            <s:if test='%{#aascCarrierConfigurationBean.shipNotificationFlag == "Y"}'>
                                <s:set name="flag1" value="%{'true'}"/>
                            </s:if>
                        </c:catch>
                        <s:if test="%{#exception21 != null}">
                            <s:set name="flag1" value="%{''}"/>
                        </s:if>
                        <s:checkbox name="ShipNotification" id="ShipNotificationId" fieldValue="%{#aascCarrierConfigurationBean.shipNotificationFlag}" value="%{#flag1}" onclick="emailNotificationChkVal(this);"/>
                    </div>
                    <div class="col-sm-2" style="font-weight:bold"><s:property value="getText('ExceptionNotification')"/></div>
                   <div class="col-sm-2">
                        <s:set name="flag1" value="%{''}"/>
                        <c:catch var="exception23">
                            <s:if test='%{#aascCarrierConfigurationBean.exceptionNotificationFlag == "Y"}'>
                                <s:set name="flag1" value="%{'true'}"/>
                            </s:if>
                        </c:catch>
                        <s:if test="%{#exception23 != null}">
                            <s:set name="flag1" value="%{''}"/>
                        </s:if>
                        <s:checkbox id="ExceptionNotificationId" name="ExceptionNotification" fieldValue="%{#aascCarrierConfigurationBean.exceptionNotificationFlag}" value="%{#flag1}" onclick="emailNotificationChkVal(this)"/>
                    </div>
                </div> 
                
                 
                
               <div class="row"> <br/></div>
                  <div class="row" id="divRow">
                   
                    <div class="col-sm-2" style="font-weight:bold"><s:property value="getText('DeliveryNotification')"/></div>
                     <div class="col-sm-2">
                        <s:set name="flag1" value="%{''}"/>
                        <c:catch var="exception24">
                            <s:if test='%{#aascCarrierConfigurationBean.deliveryNotificationFlag == "Y"}'>
                                <s:set name="flag1" value="%{'true'}"/>
                            </s:if>
                        </c:catch>
                        <s:if test="%{#exception24 != null}">
                            <s:set name="flag1" value="%{''}"/>
                        </s:if>
                        <s:checkbox id="DeliveryNotificationId" name="DeliveryNotification" fieldValue="%{#aascCarrierConfigurationBean.deliveryNotificationFlag}" value="%{#flag1}" onclick="emailNotificationChkVal(this)"/>
                    </div>
                   <div class="col-sm-2" style="font-weight:bold"><s:property value="getText('CustomerName')"/></div>
                   <div class="col-sm-2">
                        <s:set name="flag1" value="%{''}"/>
                        <c:catch var="exception28">
                            <s:if test='%{#aascCarrierConfigurationBean.customerName == "Y"}'>
                                <s:set name="flag1" value="%{'true'}"/>
                            </s:if>
                        </c:catch>
                        <s:if test="%{#exception28 != null}">
                            <s:set name="flag1" value="%{''}"/>
                        </s:if>
                        <s:checkbox id="CarrierCustomerNameId" name="CarrierCustomerName" fieldValue="%{#aascCarrierConfigurationBean.customerName}" value="%{#flag1}" onclick="emailNotificationChkVal(this);"/>
                    </div>
                </div>
                    
               <div class="row"> <br/></div>
                  <div class="row" id="divRow">
                    
                   <div class="col-sm-2" style="font-weight:bold"><s:property value="getText('Format')"/></div>
                   <div class="col-sm-2">
                        <div class="row" >
                           
                               <div class="col-sm-12">
                                    <s:set name="flag1" value="%{''}"/>
                                    <s:set name="flag2" value="%{''}"/>
                                    <c:catch var="exception25">
                                        <s:if test='%{#aascCarrierConfigurationBean.formatType == "HTML"}'>
                                            <s:set name="flag1" value="%{'true'}"/>
                                        </s:if>
                                    </c:catch>
                                    <s:if test="%{#exception25 != null}">
                                        <s:set name="flag1" value="%{''}"/>
                                    </s:if>
                                    <c:catch var="exception26">
                                        <s:if test='%{#aascCarrierConfigurationBean.formatType == "WIRELESS"}'>
                                            <s:set name="flag2" value="%{'true'}"/>
                                        </s:if>
                                    </c:catch>
                                    <s:if test="%{#exception26 != null}">
                                        <s:set name="flag2" value="%{''}"/>
                                    </s:if>
                                    <s:radio id="FormatId" list="#{'TEXT':'Text','HTML':'HTML','WIRELESS':'Wireless'}" name="Format" value="%{#aascCarrierConfigurationBean.formatType}"  cssClass="dispalyFields" cssStyle="font-size:10"/> 
                                </div>
                            </div>
                        
                    </div>
                </div> 
                <br/>
            </div>
           
     <br>
            <% try{ %>
                    <div class="col-sm-12" >
                        <div class="row" align="center">
                            <s:if test="%{#roleId==3 || #roleId==1}">
                               <!-- <input name="reset" id="reset1" type="image" onClick="document.aascCarrierConfigurationForm.submit11.value='Go';" src="buttons/aascClear1.png" align="bottom" />-->
                                    <button  name="reset" id="reset1" class="btn btn-warning" onClick="document.aascCarrierConfigurationForm.submit11.value='Go';"   >Clear <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                            
                                <!--<input name="saveImage" id="saveImage1" type="image" onClick="setClientIdVal();document.aascCarrierConfigurationForm.submit11.value='SaveUpdate';" src="buttons/aascSave1.png" align="bottom" />-->
                                <button  name="reset" id="saveImage1" class="btn btn-success"  onClick="setClientIdVal();document.aascCarrierConfigurationForm.submit11.value='SaveUpdate';" align="bottom">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                                <s:hidden name="submit123" value=""/>
                            </s:if>
                            <s:else>
                               <!-- <input name="reset" type="image"  src="buttons/aascClearOff1.png" align="bottom" disabled="disabled" />-->
                                <button  name="reset"  class="btn btn-warning" disabled="disabled">Clear <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                                &nbsp;&nbsp;&nbsp;
                                <button   name="saveImage" class="btn btn-success" disabled="disabled">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                               <!-- <input name="saveImage" type="image" src="buttons/aascSaveOff1.png" align="bottom" disabled="disabled" />-->
                            </s:else>
                            <span id="buttonDisplay"></span>
                        </div>
                        <br/>
                    </div>
            <% 
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            %>
    </s:form>
    <%
        }catch(Exception e){
            System.out.println("Got Exp in jsp");
            e.printStackTrace();
        }
    %>
     
    </body>
</html>
<%!
    public String nullStrToSpc(Object obj) {
        String spcStr = "";
	if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        }//closing the if block
	else {
            return obj.toString();
	}//closing the else block
    }//closing the nullStrToSpc method 
%>
