<%/*
  DESCRIPTION                                                              
    JSP to get Fedex Package Options for Shipment                         
    Version - 1                                                            
    History    
    11/03/09   Madhavi    Modified logging code
    17/08/2010 Madhavi    Added style ClassWidth to set width to 20em to restrict size of hazmat class
    17/08/2010 Madhavi    Added code to set tooltip to HazardousMaterialClass
    02/01/2013 Tina Soni  Added code to integrate hazmat Op900 Label generation functionality.
    19/02/2013 Tina Soni  Modified code for increasing the length of State/Province field
    20/11/2014 Eshwari    Added this file from cloud 1.2. version
    18/12/2014 Y Pradeep  Modified nullStrToSpc method.
    06/01/2014 Y Pradeep  Merged Sunanda's code : Getting harzardousUnitList from session.
    13/01/2015 Suman G    Removed <s:property value="getText('ProfileOptions')"/> for fix #2504
    28/01/2015 Suman G    Added code for #2533.
    16/02/2015 Suman G    Modified locaiton_id to location_id_selected to fix #2532
    05/03/2015    Sanjay & Khaja Added code for new UI changes.
    11/03/2015    Jagadish Implemented the new UI changes.
    15/04/2015  Suman G    Added session to fix #2743
    05/05/2015  Suman G    Added code to fix #2894
    11/05/2015  Suman G    Modified buttons to fix #2899 
	25/05/2015  Sunanda K  Modified code for bug fix #2934
    25/05/2015  Sunanda K  Modified the below code for bug fix #2894
    03/06/2015  Suman G    Added code to fix #2955
    09/06/2015  Suman G    Added code to fix #2955 
    11/06/2015  Suman G    Removed fevicon to fix #2992
    17/07/2015  Dinakar G  Added code to fix #3192
    20/07/2015  Suman G    Added code to fix #3192
    23/07/2015  Dinakar G  Added code to fix #3208 -- UI aligment for shipto,shipfrom,services
    24/07/2015  Rakesh K    Modified Code to work application in offline.
    30/07/2015  Dinakar G  Added code to fix #3295
    04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
    04/08/2015  Dinakar G  modified code for  UI alignment
    06/08/2015  Dinakar G  modified code for  UI alignment #3323
    26/08/2015  Rakesh K       Added code to solve 3496.
*/
%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.logging.*"%>
<%@page import="java.util.*" %>
<%@page import="com.aasc.erp.model.*"%>
<%@page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>

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
  
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
       <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
   <link href="kendo.common-material.min.css" rel="stylesheet" />-->
   
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>ShipConsole :: Package Options </title>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css"></link>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    
     <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
   
    
    <script language="javascript" src="aascFedexPackageOptions.js"> </script>
    <style type="text/css"> 
             
              hr {
                  -moz-border-bottom-colors: none;
                  -moz-border-image: none;
                  -moz-border-left-colors: none;
                  -moz-border-right-colors: none;
                  -moz-border-top-colors: none;
                  border-color: #7761a7 ;
                  -moz-use-text-color #FFFFFF;
                  border-style: solid none;
                  border-width: 1px 0;
                  margin-left: 2%;
                  margin-right: 2%;
                }
        #formId{
        width:89%;
        margin-left:5%;
        }
            
    </style>
  </head>
   <body class="gradientbg" onload="loadPackageOptions()"><%!
     static Logger logger = AascLogger.getLogger("aascFedexPackageOptions.jsp");
  %><%!
    /** This method can replace the null values with nullString.
     * @return String 
     * @param obj Object 
     */   
    String nullStrToSpc(Object obj) {
        String spcStr = "";

        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }

%>

        <%  
            String url = request.getContextPath();
            if(session.isNew())
            {System.out.println("Inside session");
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
        %>

<%

 
        String fedexCarierMode = "";
        String fdxcarrierName = "";
        fdxcarrierName = nullStrToSpc(request.getParameter("fdxCarrierName"));
        
		String shipFlag = "";
        shipFlag = nullStrToSpc(request.getParameter("shipFlagPackages"));
		
        AascShipMethodInfo aascShipMethodInfo2=(AascShipMethodInfo)session.getAttribute("ShipMethodInfo");
        int carrierIdInt= aascShipMethodInfo2.getCarrierId(fdxcarrierName);                   
        fedexCarierMode = nullStrToSpc(aascShipMethodInfo2.getCarrierMode(carrierIdInt));
        
        AascProfileOptionsBean aascProfileOptionsInfo =(AascProfileOptionsBean)session.getAttribute("ProfileOptionsInfo");
        //String schemaname = AascProps.getSchemaName();
        String requestId="0";
        String defaultHazMatId="";
        logger.info("Before userId");
        int userId = (Integer)session.getAttribute("user_id");
        logger.info("userId : "+userId);
        //int responsibilityId=Integer.parseInt((String)session.getAttribute("responsibilityId"));
        int locationID=((Integer)session.getAttribute("location_id_selected")).intValue();
        
        AascFedexPackageOptionsDAO aascFedexPackageOptionsDAO=new AascOracleFedexPackageOptionsDAO();
        logger.info("userId********"+userId+" locationID********"+locationID);
        String exception_message="";
        int  clientIdInt=(Integer)session.getAttribute("client_id");
        String clientId = ""+clientIdInt;
     
        try
        {
            String testclientId=clientId.substring(0,1);
        }
        catch(Exception e)
        {
           clientIdInt=(Integer)session.getAttribute("clientId");
        }
        int ORACLE = 1;
       try{
               defaultHazMatId=aascFedexPackageOptionsDAO.getDefaultHazMatId(userId, locationID, clientIdInt);
       }
       catch(Exception e){
           exception_message=e.getMessage();
           e.printStackTrace();
       }
       if("".equalsIgnoreCase(defaultHazMatId)){
           
           logger.severe("Got Exception while calling aasc_erp_ship_console_pkg.get_hazmat_profile_material_id "+exception_message);
           response.sendRedirect(url+"/aascShipError.jsp");
       }
       pageContext.setAttribute("defaultHazMatId",defaultHazMatId);  
	   pageContext.setAttribute("shipFlag",shipFlag);
     %>
     <s:set name="headerInfoTemp" value="%{#session.AascShipmentOrderInfo}"/>
     <s:set name="headerInfo" value="%{#headerInfoTemp.headerInfo}"/>
     <c:set var="packCount" value="${param.packCount}"/>
     <s:set name="packCount" value="%{#attr.packCount}"/>
     <s:bean id="hazardousUnitsList" name="java.util.LinkedList"/>
     <s:set name="hazardousUnitsList" value="%{#session.harzardousUnitList}"/>
     <s:bean id="hazardousMaterialIdList" name="java.util.LinkedList"/>
     <s:set name="hazardousMaterialIdList" value="%{#session.harzardousMatIdList}"/>
     <s:bean id="templateListTemp" name="java.util.LinkedList"/>
     <c:catch var="exception">
      <s:set name="templateListTemp" value="%{#session.TemplateList}"/>
      <s:set name="templateList" value="%{#templateListTemp.templateList}"/>
      <s:set name="aascShipMethodInfoTemp" value="%{#session.ShipMethodInfo}"/>
      <s:set name="aascShipMethodInfo" value="%{#aascShipMethodInfoTemp.dropOffTypeList}"/>
    </c:catch>
    <s:if test="%{#exception != null}">
      <c:redirect url="/aascShipError.jsp"/>
    </s:if>
    <c:set var="carrierCodeStr" value="${param.carrierCode}"/>
    <s:set name="carrierCodeStr" value="%{#attr.carrierCodeStr}"/>
    <s:set name="carrierCodeValue" value="%{0}"/>
    <s:set name="carrierCodeValue" value="%{#carrierCodeStr}"/>
    <s:set name="rtnDropOfType" value="%{''}"/>
    <s:set name="rtnPackageList" value="%{''}"/>
    <s:set name="rtnPayMethod" value="%{''}"/>
    <s:set name="rtnACNumber" value="%{''}"/>
    <s:set name="carrierPayIterator" value="%{#aascShipMethodInfo.carrierPayDetails}"/>
    <s:bean name="com.aasc.erp.bean.AascShipmentOrderInfo" id="list"/>
    <s:set name="list" value="%{#session.AascShipmentOrderInfo}"/>
    <s:set name="packageList" value="%{#list.packageInfo}"/>
    <s:if test="%{#packageList == null}">
      <s:set name="packageList" value="java.util.LinkedList"/>
    </s:if>
    <s:set name="packCountFromList" value="%{1}"/>
    <s:set name="packCountint" value="%{0}"/>
    <s:set name="packCountint" value="%{#packCount}"/>
    <s:iterator value="packageList" status="packCountTemp" id="packageIterator">
      <s:if test="%{#packCountTemp.count == #packCountFromList}">
        <s:set name="packBean" value="%{#packageIterator}"/>
      </s:if>
      <s:set name="packCountFromList" value="%{#packCountFromList+1}"/>
    </s:iterator>
    <c:catch var="exception1">
      <s:set name="rtnDropOfType" value="%{#packBean.rtnDropOfType}"/>
    </c:catch>
    <s:if test="%{#exception1 != null}">
      <s:set name="rtnDropOfType" value="%{''}"/>
    </s:if>
    <c:catch var="exception2">
      <s:set name="rtnPackageList" value="%{#packBean.rtnPackageList}"/>
    </c:catch>
    <s:if test="%{#exception2 != null}">
      <s:set name="rtnPackageList" value="%{''}"/>
    </s:if>
    <c:catch var="exception3">
      <s:set name="rtnPayMethod" value="%{#packBean.rtnPayMethod}"/>
      <s:if test='%{#rtnPayMethod == "1"}'>
        <s:set name="rtnPayMethod" value="%{''}"/>
      </s:if>
    </c:catch>
    <s:if test="%{#exception3 != null}">
      <s:set name="rtnPayMethod" value="%{''}"/>
    </s:if>
    <c:catch var="exception4">
      <s:set name="rtnACNumber" value="%{#packBean.rtnACNumber}"/>
    </c:catch>
    <s:if test="%{#exception4 != null}">
      <s:set name="rtnACNumber" value="%{''}"/>
    </s:if>
    <s:set name="shipToAdd" value="%{#headerInfo.state}"/>
    <s:set name="shipToAddressLine2" value="%{#headerInfo.shipToAddrLine2}"/>
    <s:set name="shipToAddressCity" value="%{#headerInfo.shipToAddressCity}"/>
    <table  align="center">
      <tr>
        <td width="400">&nbsp;</td>
        <td width="100">
          <div align="center">
            <!--<a href="javascript: openNewWindow('ShipConsoleClientShippingUser/index.html?adhocorders.html', 'SCHelpWindow', 'WIDTH=700,HEIGHT=500,resizable=yes');">
              <img src="images/help.png" width="20" height="20" border="0"></img>
            </a> -->
          </div>
        </td>
      </tr>
    </table>
    
    <s:if test="%{#pageContext.session['new']}">
      <c:redirect url="/aascShipError.jsp">
         <c:param name="errorObj" value="SessionError"/>
      </c:redirect>
    </s:if>
    
    <%

  
  Object o=request.getAttribute("packBean");
  AascShipmentPackageInfo packBean=(AascShipmentPackageInfo)o;
  if(packBean == null)
     packBean = new AascShipmentPackageInfo();
  %>
  <br></br>
    <s:form name="aascPackageOptionsForm" method="POST" id="formId">
      <s:hidden name="packageCount" value="%{#packCount}"/>
      <s:set name="shipFlag" value="%{#attr.shipFlag}"/>
      <div class="row">
                <div class="row"><br/></div>
                    <div class="row" id="divSub">
                <div class=" col-sm-2" >
                </div>
                <s:if test='%{#shipFlag == "N" }' >
                  <div class=" col-sm-8" align="center" id="buttonDiv">
                           
                  </div>
                </s:if>
                <s:else>
                  <div class=" col-sm-8" align="center"  id="buttonDiv">
                           <button class="btn btn-primary" name="Save" id="saveId1" disabled="disabled" > Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                           <button  class="btn btn-warning" name="cancel" id="cancelId1" disabled="disabled" > Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button> 
                           <button class="btn btn-danger" name="close" id="closeId"  onclick="window.close();" > Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                  </div>
                </s:else>
                <div class=" col-sm-2" >
                </div>
             </div>
      
      
      <div class="row"><br/></div>
      
      <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;">
                   <!--<fieldset class="scheduler-border" style="border-color:#19b698; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;">-->
                    <div align="center" style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;">
                       <label style="color:#ffffff;font-size:20px;font-weight:bold;">Package Options</label>
                       <s:hidden name="packageDeclaredValue"/>
                    </div>
 
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" style="width:100%; ">
                        
                        <div class="col-sm-3">
                        <div class="col-sm-12">
                           <label  class="control-label" align="left"> <span class="dispalyFields" > <s:property value="getText('SignatureOption')"/></span></label>
                             &nbsp;
                               <s:checkbox name="signatureOptionCheck"  id="signatureOptionCheckId"  value="" onclick="javascript:signatureOptionCheckfn();"/>
                             
                             </div>
                         </div>
                        <div class="col-sm-2">
                                <input name="signatureOption" id="signatureOptionDirect" type="radio" value="DIRECT"
                                    onclick="signatureOption1()" ></input> &nbsp;
                                        
                              <label  class="control-label"> <span class="dispalyFields" > <s:property value="getText('Direct')"/></span></label>           
                            </div>
                         <div class="col-sm-2">
                            
                                <input name="signatureOption" id="signatureOptionAdult" type="radio" value="ADULT"
                                    onclick="signatureOption2()"></input> &nbsp;
                                        
                              <label  class="control-label"> <span class="dispalyFields" > <s:property value="getText('Adult')"/></span></label>           
                            </div>
                         <div class="col-sm-3">
                                <div class="col-sm-3">
                                <input name="signatureOption" id="signatureOptionWithoutSignature" type="radio"
                                    value="DELIVERWITHOUTSIGNATURE" onclick="signatureOption3()"></input>
                                &nbsp; 
                                </div>
                              <label for="inputEmail" class="control-label col-sm-9" > <span class="dispalyFields" > <s:property value="getText('DeliveryWithoutSignature')"/></span></label>           
                          
                            </div>
                         <div class="col-sm-2">
                            
                                <input name="signatureOption" id="signatureOptionIndirect" type="radio" value="INDIRECT"
                                     onclick="signatureOption4()"></input>
                                         &nbsp;
                              <label for="inputEmail" class="control-label "> <span class="dispalyFields" > <s:property value="getText('Indirect')"/></span></label>           
                            </div>
                            
                     </div>
                     <hr>
                     
                    <div class="row"><br/></div>    
                    
                        <div class="row" id="divSub" style="width:100%; ">
                        
                        <div class="col-sm-2">
                        <div class="col-sm-12">
                        <label for="inputEmail" class="control-label" align="left"> <span class="dispalyFields" > <s:property value="getText('COD')"/></span></label>
                        &nbsp;
                        <s:checkbox name="chCOD"  id="chCOD" onclick="codCheck();"/>
                        </div>                
                        </div>
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('CODAmount')"/></span></label>
                            <div class="col-sm-6">
                                <s:textfield name="codAmt" readonly="true" id="codAmt2" class="form-control" cssClass="inputs" value="%{''}" size="10" maxlength="5"/>
                                </div>
                                         
                            </div>
                         <div class="col-sm-3">
                            <label for="inputEmail" class="control-label col-sm-5" align="left"> <span class="dispalyFields" > <s:property value="getText('SurCharge')"/></span></label>
                            <div class="col-sm-7">
                                <s:textfield name="PackageSurcharge" class="form-control" cssClass="inputs" id="PackageSurchargeId" readonly="true"  size="10"/>
                                </div>
                                         
                            </div>
                         <div class="col-sm-3">
                         <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('ShipmentCost')"/></span></label>
                            <div class="col-sm-6">
                                <s:textfield name="PackageShipmentCost" id="PackageShipmentCostId" readonly="true" class="form-control" cssClass="inputs"  size="10"/>
                                </div>
                                         
                            </div>
                     </div>





<!--   Suman Code for Dry ice         -->


                  <hr>
                     
                    <div class="row"><br/></div>    
                    
           <!--             <div class="row" id="divSub" style="width:90%; margin-left:2%;">
                        
                        <div class="col-sm-2">
                        <label for="inputEmail" class="control-label col-sm-8" align="left"> <span class="dispalyFields" > <s:property value="getText('DryIce')"/></span></label>
                        <div class="col-sm-4" align="left">
                        <input type="checkbox" name="chDryIce" id="chDryIceID" onclick="chDryIceCheck()"/>
                        </div>
                        <div class="col-sm-3">
                        <label for="inputEmail" class="control-label col-sm-4" align="left"> <span class="dispalyFields" > <s:property value="getText('Weight')"/></span></label>
                            <div class="col-sm-8">
                                <s:textfield name="dryIceWeight" readonly="true" id="fedexDryIceWeightID" cssClass="inputs" value="%{''}" size="10" maxlength="5"/>
                                </div>
                                         
                            </div>
                         <div class="col-sm-3">
                            <label for="inputEmail" class="control-label col-sm-4" align="left"> <span class="dispalyFields" > <s:property value="getText('WeightUnits')"/></span></label>
                            <div class="col-sm-8">
                                <s:select list="#{\'LBS\':\'Pounds\', \'KGS\': \'Kilograms\'}"
                      cssClass="inputs" name="dryIceUnits" size="1" id="fedexDryIceUnitsID"
                      value=""/>
                                </div>
                                         
                            </div>
                         
                     </div>
</div>
-->
    <div class="row" id="divSub" style="width:100%;">
                        
                        <div class="col-sm-4">
                        <div class="col-sm-12">
                           <label for="inputEmail" class="control-label" align="left"> <span class="dispalyFields" > <s:property value="getText('DryIce')"/></span></label>
                              &nbsp;
                           <input type="checkbox" name="chDryIce" id="chDryIceID" onclick="chDryIceCheck()"/>
                         </div>               
                        </div>
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('Weight')"/></span></label>
                        <div class="col-sm-6">
                        <s:textfield name="dryIceWeight" readonly="true" class="form-control" cssClass="inputs" id="fedexDryIceWeightID"  value="%{''}" size="10" maxlength="5"/>
                         </div>               
                        </div>
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('WeightUnits')"/></span></label>
                        <div class="col-sm-6">
                        <s:select list="#{\'LBS\':\'Pounds\', \'KGS\': \'Kilograms\'}" class="form-control" cssClass="inputs"  name="dryIceUnits" size="1" id="fedexDryIceUnitsID"  value=""/>
                         </div>               
                        </div>
           
                     
</div>

                     <hr>
                    <div class="row"><br/></div>
                     <div class="row" id="divSub" style="width:100%;">
                        
                        <div class="col-sm-4">
                        <div class="col-sm-12">
                           <label for="inputEmail" class="control-label" > <span class="dispalyFields" > <s:property value="getText('HazardousMaterial')"/></span></label>
                           &nbsp;
                           <s:checkbox name="HazardousMaterial"  id="HazardousMaterial" value="N" onclick="hazardousMaterialFn();defaultProfileHazMatValues();"/>
                        </div>   
                        </div>
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('HazMatId')"/></span></label>
                            <div class="col-sm-6">
                                <s:if test="%{#carrierCodeValue != 111}"> 
                                  <s:bean id="hazardousMaterialIdListTemp" name="java.util.LinkedList"/>
                                  <s:select list="hazardousMaterialIdListTemp" headerKey="" headerValue="---Select Id---" name="HazardousMaterialId"  id="HazardousMaterialId"
                                        class="form-control" cssClass="inputs"   onchange="getHazMatPkgGroup();getHazMatValues();resetTip();"/>
                                </s:if>
                                <s:else>
                                  <s:select list="hazardousMaterialIdList" headerKey="" headerValue="---Select Id---" name="HazardousMaterialId" id="HazardousMaterialId"
                                         class="form-control" cssClass="inputs" onchange="getHazMatPkgGroup();getHazMatValues();resetTip();"/>
                                </s:else>
                                <s:set name="defaultHazMatId" value="%{#attr.defaultHazMatId}"/>
                                <s:hidden name="hazMatIdHidden" value="%{#defaultHazMatId}"/>
                                
                                </div>
                                         
                            </div>
                         
                         <div class="col-sm-4">
                           
                            </div>
                     </div>
                    <div class="row"><br/></div>    
                    
                        <div class="row" id="divSub" style="width:100%;">
                        
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('HazardousMaterialType')"/></span></label>
                        <div class="col-sm-6">
                        <s:if test="%{#carrierCodeStr == 110}">
                          <s:select list="#{'ACCESSIBLE':'ACCESSIBLE','INACCESSIBLE':'INACCESSIBLE'}" headerKey="" headerValue="--Select--" name="HazardousMaterialType" id="HazardousMaterialType" 
                                   class="form-control" cssClass="inputs" onchange="getHazMatClass();"/>
                        </s:if>
                        <s:elseif test="%{#carrierCodeStr == 111}">
                          <s:select list="#{'HAZCLASS':'HAZCLASS'}" name="HazardousMaterialType" id="HazardousMaterialType" 
                                 class="form-control" cssClass="inputs" onchange="getHazMatClass();"/>
                        </s:elseif>
                        </div>
                        </div>
                        <div class="col-sm-4">
                        <label class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('Class')"/></span></label>
                            <div class="col-sm-6">
                                <select name="HazardousMaterialClass"  class=" inputs"  id="ClassWidth"  onchange="gettip(this.value);" onmouseover="gettip(this.value);"></select>
                                </div>
                                         
                            </div>
                         <div class="col-sm-4">
                         
                            <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('HazMatCharges')"/></span></label>
                            <div class="col-sm-6">
                                <s:textfield name="HazardousMaterialCharges" id="HazardousMaterialCharges" class="form-control" cssClass="inputs" size="4" value="0" readonly="true"/>
                                </div>
                                         
                            </div>
                         </div>
                    <div class="row"><br/></div>    
                    
                        <div class="row" id="divSub" style="width:100%;">
                       
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('Weight')"/></span></label>
                        <div class="col-sm-6">
                            <s:textfield name="HazardousMaterialQuantity" id="HazardousMaterialQuantity" class="form-control" cssClass="inputs" size="4" value="0"/>
                        </div>
                        </div>
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('Unit')"/></span></label>
                            <div class="col-sm-6">
                                 <s:if test="%{#carrierCodeValue != 111}">
                                      <s:bean id="HazardousMaterialUnitTemp" name="java.util.LinkedList"/>
                                      <s:select list="HazardousMaterialUnitTemp" class="form-control" cssClass="inputs" headerKey="" headerValue="---Select Unit---" name="HazardousMaterialUnit" id="HazardousMaterialUnit" />
                                    </s:if>
                                    <s:else>
                                      <s:select list="hazardousUnitsList" class="form-control" cssClass="inputs" headerKey="" headerValue="---Select Unit---" name="HazardousMaterialUnit" id="HazardousMaterialUnit" />
                                    </s:else>
                                </div>
                                         
                            </div>
                         <div class="col-sm-4">
                            <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('IdentificationNumber')"/></span></label>
                            <div class="col-sm-6">
                                <s:textfield name="HazMatIdentificationNo" id="HazMatIdentificationNo" class="form-control" cssClass="inputs" size="6"/>
            <!-- Added on Jun-03-2011 -->
                                <input name="fdexCarrierMode" id="fdexCarrierMode" type="hidden" value="<%=fedexCarierMode%>"></input>
                                </div>
                                         
                            </div>
                         </div>
                         <div class="row"><br/></div>    
                    
                        <div class="row" id="divSub" style="width:100%;">
                        
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('DotLabelType')"/></span></label>
                        <div class="col-sm-6">
                        <s:textfield name="HazMatDOTLabelType" id="HazMatDOTLabelType" class="form-control" cssClass="inputs" size="10"/>
                        </div>
                        </div>
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('EmergencyContactNo')"/></span></label>
                            <div class="col-sm-6">
                                <s:textfield name="HazMatEmergencyContactNo" class="form-control" cssClass="inputs" id="HazMatEmergencyContactNo"  size="25"/>
                                </div>
                                         
                            </div>
                         <div class="col-sm-4">
                            <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('EmergencyContactName')"/></span></label>
                            <div class="col-sm-6">
                                <s:textfield name="HazMatEmergencyContactName" class="form-control" cssClass="inputs" id="HazMatEmergencyContactName"  size="6"/>
                                </div>
                                         
                            </div>
                         </div>
                         <div class="row"><br/></div>    
                    
                        <div class="row" id="divSub" style="width:100%;">
                        
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('PackingGroup')"/></span></label>
                        <div class="col-sm-6">
                        <select name="HazardousMaterialPkgGroup" id="HazardousMaterialPkgGroup"  class="inputs"></select>
                        </div>
                        </div>
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('PackagingCount')"/></span></label>
                            <div class="col-sm-6">
                                <s:textfield name="HazMatPackagingCnt" class="form-control" cssClass="inputs" id="HazMatPackagingCnt"  size="10"/>
                                </div>
                                         
                            </div>
                         <div class="col-sm-4">
                            <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('PackagingUnits')"/></span></label>
                            <div class="col-sm-6">
                                <s:textfield name="HazMatPackagingUnits" id="HazMatPackagingUnits" class="form-control" cssClass="inputs" size="10"/>
                                </div>
                                         
                            </div>
                         </div>
                         <div class="row"><br/></div>    
                    
                        <div class="row" id="divSub" style="width:100%;">
                        
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('TechnicalName')"/></span></label>
                        <div class="col-sm-6">
                            <s:textfield name="HazMatTechnicalName" id="HazMatTechnicalName" class="form-control" cssClass="inputs" size="10"/>
                        </div>
                        </div>
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('SignatureName')"/></span></label>
                            <div class="col-sm-6">
                                 <s:textfield name="HazMatSignatureName" id="HazMatSignatureName" class="form-control" cssClass="inputs" size="10"/>
                                </div>
                                         
                            </div>
                         <div class="col-sm-4">
                            
                         </div>
                         </div>
                         <hr>
                         <div class="row"><br/></div>
                         <div class="row"><br/></div>
                         
                         <div class="row" id="divSub" style="width:100%;">
                        
                        <div class="col-sm-4">
                            <div class="col-sm-12">
                                <label for="inputEmail" class="control-label" align="left"> <span class="dispalyFields" > <s:property value="getText('HoldAtLocation')"/></span></label>
                                        &nbsp;
                                <s:checkbox name="holdAtLocation"  id="holdAtLocation"  
                                         value="N" onclick="holdatLocationfn();"/>
                           </div>   
                        </div>
                        <div class="col-sm-4">
                          
                            </div>
                         <div class="col-sm-4">
                                
                            </div>
                         
                     </div>
                     <div class="row"><br/></div>
                     
                     <div class="row" id="divSub" style="width:100%;">
                        
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('PhoneNo')"/></span></label>
                        <div class="col-sm-6">
                            <s:textfield name="halPhone" id="halPhone" class="form-control" cssClass="inputs" size="15" onblur="halValidate();"/>
                        </div>
                        </div>
                        <div class="col-sm-4">
                        
                                         
                            </div>
                         <div class="col-sm-4">
                            
                         </div>
                         </div>
                         <div class="row"><br/></div>
                         <div class="row" id="divSub" style="width:100%;">
                        
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('Line1')"/></span></label>
                        <div class="col-sm-6">
                            <s:textfield name="halAddrLine1" class="form-control" cssClass="inputs" id="halLine1" size="35" maxlength="35"/>
                        </div>
                        </div>
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('Line2')"/></span></label>
                            <div class="col-sm-6">
                                 <s:textfield name="halAddrLine2" class="form-control" cssClass="inputs" id="halLine2" size="35" maxlength="35"/>
                                </div>
                                         
                            </div>
                         <div class="col-sm-4">
                            
                         </div>
                         </div>
                         <div class="row"><br/></div>
                         <div class="row" id="divSub" style="width:100%;">
                        
                        <div class="col-sm-4">
                        <label for="inputEmail" class="control-label col-sm-6" align="left"> <span class="dispalyFields" > <s:property value="getText('City')"/>, <s:property value="getText('State')"/>,<s:property value="getText('PostalCode')"/></span></label>
                        <div class="col-sm-6">
                        <s:textfield name="halCity" class="form-control" cssClass="inputs" maxlength="35" id="halCity" size="35"/>
                        
                        
                           </div>
                        </div>
                        <div class="col-sm-4">
                        <div class="col-sm-6">
                                 <s:textfield name="halState" id="halState"  class="form-control" cssClass="inputs" size="2"/>
                                </div>
                            <div class="col-sm-6">
                                 <s:textfield name="halZip" class="form-control" cssClass="inputs" id="halZip" size="16" maxlength="16" onblur="halValidate();"/>
                                </div>
                                         
                            </div>
                         <div class="col-sm-4">
                            
                         </div>
                         </div>
                         <hr>
                         <div class="row"><br/></div>
                         <div class="row"><br/></div>
                         
                          <div class="row" id="divSub" style="width:100%;">
                                
                            <div class="col-sm-4">
                            <div class="col-sm-12">
                                <label for="inputEmail"  align="left"> <span class="dispalyFields" > <s:property value="getText('ReturnShipment')"/></span></label>
                                 &nbsp;
                                     <s:checkbox name="returnShipment" id="returnShipment" value="%{'PRINTRETURNLABEL'}" onclick="returnShipmentfn();rmaCheck();" />
                                     
                                      </div>
                           </div>
                            
                            <div class="col-sm-4">
                          
                            </div>
                         <div class="col-sm-4">
                                
                            </div>
                         
                     </div>
                     <div class="row"><br/></div>
                         
                         <div class="row">
    <div class="col-sm-12">
        <div class="col-sm-4">
            <div style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #19b698; border-style: solid;">
                <div class="table-responsive" style="background-color:#F0F0F0; margin-top:10px; margin-bottom:10px; border-radius: 5px;  margin-left:5px; margin-right:5px;">
                    <table class="table" >
                        <thead>
                        <tr >
                            <td style="background-color:#19b698;color:#ffffff;font-size:12px;font-weight: bold;">Ship From</td>
                            <td style="background-color:#19b698"> </td>
                        </tr>
                        </thead>
                        <tbody>
                          <tr align="left" >
                            <td class="dispalyFields" nowrap="nowrap">  <s:property value="getText('CompanyName')"/> </td>
                            <td align="right">
                               <s:textfield name="rtnShipFromCompany" id="rtnShipFromCompany" cssClass="inputs"/>
                            </td>
                          </tr>
                          <tr align="left" valign="top">
                            <td class="dispalyFields" nowrap="nowrap">  <s:property value="getText('ContactName')"/>
                            </td>
                            <td align="right">
                                <s:textfield name="rtnShipFromContact" id="rtnShipFromContact" cssClass="inputs"/>
                            </td>
                            
                          </tr>
                          
                          
                          <tr align="left" valign="top">
                            <td class="dispalyFields" > <s:property value="getText('Line1')"/> </td>
                            <td align="right">
                               <s:textfield name="rtnShipFromLine1"  id="rtnShipFromLine1" cssClass="inputs" />
                            </td>
                          </tr>
                          <tr  valign="top">
                           <td class="dispalyFields" align="left">  <s:property value="getText('Line2')"/></td>
                            <td align="right">
                                <s:textfield name="rtnShipFromLine2"  id="rtnShipFromLine2" cssClass="inputs"/>
                            </td>
                          </tr>
                            <tr align="left" valign="top">
                            
                           
                                <td class="dispalyFields">  <s:property value="getText('City')"/> </td>
                                <td align="right">
                                  <s:textfield name="rtnShipFromCity"   id="rtnShipFromCity" cssClass="inputs" value="%{#shipToAddressCity}"/>
                                </td>
                                 </tr>
                                 <tr align="left" valign="top">
                                  <td class="dispalyFields"> <s:property value="getText('State')"/> </td>
                                <td align="right">
                                  <s:textfield name="rtnShipFromSate" cssClass="inputs" id="rtnShipFromSate" value="%{#headerInfo.shipToAddressState}"/> </td>
                            
                                 </tr>
                        
                              <tr align="left" valign="top">
                              <td class="dispalyFields"> <s:property value="getText('Zip')"/> </td>
                                <td align="right">
                                  <s:textfield name="rtnShipFromZip" cssClass="inputs" id="rtnShipFromZip" value="%{#headerInfo.shipToPostalCode}"/>
                                </td>
                              </tr>
                            
                          <tr align="left" valign="top">
                            <td class="dispalyFields">  <s:property value="getText('Phone')"/> </td>
                            <td align="right">
                              <s:textfield name="rtnShipFromPhone" id="rtnShipFromPhone" cssClass="inputs"/>
                            </td>
                          </tr>
                              
                        </tbody>
                     </table>   
                </div>
            </div>
        </div>
    
    
        
        <div class="col-sm-4">
            <div style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #ea6153; border-style: solid;">
               <div class="table-responsive" style="background-color:#F0F0F0; margin-top:10px; margin-bottom:10px; border-radius: 5px;  margin-left:5px;margin-right:5px;"> 
                <table class="table" >
                    <thead>
                      <tr>
                        <td style="background-color:#ea6153;color:#ffffff;font-size:12px;font-weight: bold;">Ship To</td>
                        <td style="background-color:#ea6153"> </td>
                      </tr>
                    </thead>
                <tbody>
                    <tr align="left">
                          <td align="left" valign="top" class="dispalyFields"> <s:property value="getText('CompanyName')"/> </td>
                          <td align="right" valign="top"  >
                            
                              <s:textfield name="rtnShipToCompany" id="rtnShipToCompanyID" value="%{#headerInfo.shipFromCompanyName}" cssClass="inputs" size="30" maxlength="35"/>
                          </td>
                        </tr>
                       
                        <tr align="left">
                           <td class="dispalyFields" nowrap="nowrap">  
                                <s:property value="getText('ContactName')"/>     
                           </td>
                           <td align="right" class="displayResults">
                               <s:textfield name="rtnShipToContact" id="rtnShipToContact" cssClass="inputs" size="30" maxlength="35"/>
                            </td>
                        </tr>
                        <tr align="left">
                           <td align="left" valign="top" class="dispalyFields"> <s:property value="getText('Line1')"/> </td>
                           <td align="right" valign="top" class="displayResults"> 
                             <s:textfield name="rtnShipToLine1" cssClass="inputs" id="txtShipToAddressLine32" value="%{#headerInfo.shipFromAddressLine1}" size="30" maxlength="35"/> 
                           </td>
                        </tr>
                        <tr align="left">
                             <td align="left" valign="top" class="dispalyFields"> <s:property value="getText('Line2')"/> </td>
                            <td align="right" >
                              <span class="displayResults">
                                <s:textfield name="rtnShipToLine2" cssClass="inputs" id="rtnShipToLine2" value="%{#headerInfo.shipFromAddressLine2}" size="30" maxlength="35"/>
                              </span>
                            </td>
                        </tr>
                        <tr align="left">
                            <td align="left" valign="top" class="dispalyFields"> <s:property value="getText('City')"/> </td>
                            <td align="right" >
                              <span class="displayResults">
                                <s:textfield name="rtnShipToCity" id="rtnShipToCity" cssClass="inputs" maxlength="35" value="%{#headerInfo.shipFromAddressCity}" size="12"/>
                              </span>
                            </td>
                        </tr>
                 
                                <tr align="left">
                                  <td> <span class="dispalyFields"><s:property value="getText('State')"/>  </span> </td>
                                  <td align="right"> <span class="displayResults">
                                    <s:textfield name="rtnShipToState" id="rtnShipToState" cssClass="inputs" value="%{#headerInfo.shipFromAddressState}" size="2" maxlength="10"/>
                                    </span> </td>
                                  
                                </tr>
                                <tr align="left">
                                <td align="left" valign="top" class="dispalyFields"><s:property value="getText('Zip')"/> </td>
                                  <td align="right" valign="top" class="displayResults">
                                    <s:textfield name="rtnShipToZip" cssClass="inputs" value="%{#headerInfo.shipFromAddressPostalCode}" id="rtnShipToZip2" size="6" maxlength="6"/>
                                  </td>
                                </tr>
                                <tr align="left">
                                  <td align="left" valign="top" class="dispalyFields"> <s:property value="getText('Phone')"/> </td>
                                  <td align="right" valign="top" class="displayResults"> 
                                  <s:textfield name="rtnShipToPhone" id="rtnShipToPhone" cssClass="inputs" size="15"/>
                         </td>
                                
                                </tr>
                             
                </tbody>
                </table>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
        
       
        
            <div style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #3d566d; border-style: solid;">
              <div class="table-responsive" style="background-color:#F0F0F0; margin-top:10px; margin-bottom:10px; border-radius: 5px;  margin-left:5px;margin-right:5px;"> 
               <table class="table" >
                    <thead>
                          <tr>
                            <td style="background-color:#3d566d;color:#ffffff;font-size:12px;font-weight: bold;">Shipping Service</td>
                            <td style="background-color:#3d566d"> </td>
                          </tr>
                        </thead>
                        <tbody>
                            <tr>
                <td align="left" valign="top" class="dispalyFields"  width="50%">  
                  <s:property value="getText('ShipMethod')"/> <br/>
                </td>
                <%
              AascShipmentHeaderInfo headerInfo = ((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo();
                 
              logger.info("aascProfileOptionsInfo : "+aascProfileOptionsInfo);
              String ajaxOriginRegionCode = headerInfo.getShipFromCountry();  //aascProfileOptionsInfo.getOriginRegionCode();              
              logger.info("ajaxOriginRegionCode : "+ajaxOriginRegionCode);
              pageContext.setAttribute("ajaxOriginRegionCode",ajaxOriginRegionCode);
              //String ajaxUpsMode          = aascProfileOptionsInfo.getUpsMode();
              //pageContext.setAttribute("ajaxUpsMode",ajaxUpsMode); 
                %>
                <td align="right" valign="top" width="50%">
                 
            <s:hidden name="CarrierAcHidden"/>
            <s:hidden name="ajaxShipMethod" id="ajaxShipMethod"/>
            <s:hidden name="ajaxCCodeCServiceLevel" id="ajaxCCodeCServiceLevel"/>
            <s:hidden name="ajaxCarrierCode" id="ajaxCarrierCode"/>
            <s:hidden name="ajaxcarrierservicelevel" id="ajaxcarrierservicelevel"/>
            <s:hidden name="ajaxDropOffType" id="ajaxDropOffType"/>
            <s:hidden name="ajaxPackaging" id="ajaxPackaging"/>
            <s:hidden name="ajaxCarrierPaymentTerms" id="ajaxCarrierPaymentTerms"/>
            <s:hidden name="ajaxOriginRegionCode" id="ajaxOriginRegionCode" value="%{#attr.ajaxOriginRegionCode}"/>
            <s:hidden name="ajaxUpsMode" id="ajaxUpsMode" value=""/>
            <s:hidden name="ajaxUpsServiceLevelCode" id="ajaxUpsServiceLevelCode"/>
            <s:hidden name="ajaxDimensionReq" id="ajaxDimensionReq"/>
            <s:hidden name="ajaxMaxWeight" id="ajaxMaxWeight"/>
            <s:hidden name="ajaxMaxLength" id="ajaxMaxLength"/>
            <s:hidden name="ajaxMinLength" id="ajaxMinLength"/>
            <s:hidden name="ajaxMaxWidth" id="ajaxMaxWidth"/>
            <s:hidden name="ajaxMinWidth" id="ajaxMinWidth"/>
            <s:hidden name="ajaxMaxHeight" id="ajaxMaxHeight"/>
            <s:hidden name="ajaxMinHeight" id="ajaxMinHeight"/>
            
            <%
                ListIterator shipMethodIterator= null;
                AascShipMethodInfo aascShipMethodInfo=(AascShipMethodInfo)session.getAttribute("ShipMethodInfo");

                ListIterator   carrierPayIterator=(aascShipMethodInfo.getCarrierPayDetails()).listIterator(); 

                try{
                  logger.info("setting ship method list Iterator");
                  ListIterator shipMethodIteratorTemp=(aascShipMethodInfo.getShipMethodDetailList()).listIterator();                 
                  LinkedList linkedListTemp=new LinkedList();
                  while(shipMethodIteratorTemp.hasNext())
                  {
                    AascShipMethodInfo aascShipMethodInfoTemp=(AascShipMethodInfo)shipMethodIteratorTemp.next();
                    if(aascShipMethodInfoTemp.getEnabledFlag().equalsIgnoreCase("Y")  )
                    { 
                      linkedListTemp.add(aascShipMethodInfoTemp); 
                    }
                 }
                 shipMethodIterator=linkedListTemp.listIterator();
              
            %>
                 
            <select name="rtnShipMethod" id="rtnShipMethodID" size="1"
                    class="inputs" onchange="getCcCsl('2');">
            <%
              /// fetching the shipment methods

              while(shipMethodIterator.hasNext())
              {  
                 AascShipMethodInfo  aascShipMethodInfoBean = (AascShipMethodInfo)shipMethodIterator.next();                   
                 if(aascShipMethodInfoBean.getEnabledFlag().equalsIgnoreCase("Y"))
                 {
                   String shipMethodElementAlt=(String) aascShipMethodInfoBean.getAlternateShipMethod();//getShipMethodMeaning();//
                   logger.info("shipMethodElementAlt : "+shipMethodElementAlt);
                   String shipMethodElement = (String)aascShipMethodInfo.getShipMethodFromAlt(shipMethodElementAlt);;// 
                   logger.info("shipMethodElement : "+shipMethodElement);
                   int carrierId= aascShipMethodInfo.getCarrierId(shipMethodElement);                   
                   int CarrierCode = aascShipMethodInfo.getCarrierCode(carrierId);
                   logger.info("carriercode:"+CarrierCode);
                   
                   String shipMethodValidation= aascShipMethodInfo.getShipmentValidationCode(shipMethodElement);
                   String CarreirAC = aascShipMethodInfo.getCarrierAccountNumber(carrierId);
                   String checkShipMethodrom="";

                   try
                   {
                     if(packBean != null && (!(packBean.getRtnShipMethod().equalsIgnoreCase("")) || packBean.getRtnShipMethod()!= null))
                     {
                        checkShipMethodrom=packBean.getRtnShipMethod();
                     }
                     else
                     {
                     checkShipMethodrom="";
                     }
                   }catch(Exception e)
                   {
                     checkShipMethodrom="";
                   }
                   if(checkShipMethodrom.equalsIgnoreCase("")){
                     checkShipMethodrom=headerInfo.getShipMethodMeaning();
                   }

                   if(checkShipMethodrom.equals(shipMethodElement))
                   {
                     logger.info("CarrierCode for selected one " +CarrierCode);

                     if(CarrierCode==111 || CarrierCode==110)
                     {
                 %>
              <option value='<%=CarrierCode+"||"+shipMethodValidation+"*"+shipMethodElementAlt+"@@"+carrierId%>'
                      id="<%=CarreirAC%>" selected="selected">
                <%=shipMethodElementAlt%>
              </option>
              <%
                     }
                   }
                   else
                   {
                     logger.info("CarrierCode for Non selected one " +CarrierCode);
		     if(CarrierCode==111 || CarrierCode==110)
                      {
              %>
              <option value='<%=CarrierCode+"||"+shipMethodValidation+"*"+shipMethodElementAlt+"@@"+carrierId%>'
                      id="<%=CarreirAC%>">
                <%=shipMethodElementAlt%>
              </option>
              <%
                     }
                  }
                }
              }
           }
           catch(Exception e)
           {
              logger.severe("Got exception in aascFedexPackageOptions.jsp"+e.getMessage());
           }
                  
           %>
         </select>
        </td>
            
              </tr>
              <tr>
                <td align="left" valign="top" class="dispalyFields" width="50%"> <s:property value="getText('DropofType')"/></td>
                <td align="right" width="50%">
                <select name="rtnDropOfType" id="rtnDropOfTypeID" size="1" disabled="disabled" class="inputs" onchange="getPackageList('','','');">
          
              <s:hidden name="ajaxAfterShipDropOffType"   id="ajaxAfterShipDropOffType"  value="%{#rtnDropOfType}"/>
            </select>
                </td>
              </tr>
              <tr>
              <td align="left" valign="top" class="dispalyFields" width="50%">  <s:property value="getText('PackageType')"/> </td>
              <td align="right" valign="top"  width="50%">
                <select name="rtnPackageList" id="rtnPackageListID"  disabled="disabled" class="inputs"  onchange="getCarrierPayMethod('','');">
                            
                <s:hidden name="ajaxAfterShipPackaging" id="ajaxAfterShipPackaging"  value="%{#rtnPackageList}"/>
                </select>
            
              </td>
              </tr>
              <tr>
              <td align="left" valign="top" class="dispalyFields" width="50%"> <s:property value="getText('PayMethod')"/></td>
              <td align="right" valign="top" width="50%"> 
                <select name= "rtnPayMethod"  id= "rtnPayMethodID" size=1 class="inputs" onChange="setValue();getAccountNumber();" >
       <%
       String localCarrierCode="";
       try{
            logger.info("carrierPayIterator : "+carrierPayIterator);
            while(carrierPayIterator.hasNext())
            {
              AascShipMethodInfo   aascShipMethodInfo1 =(AascShipMethodInfo)carrierPayIterator.next();
  
              logger.info("aascShipMethodInfo1 : "+aascShipMethodInfo1);
              logger.info("packBean : "+packBean);
              if(packBean.getRtnPayMethod() != null && aascShipMethodInfo1 != null && packBean.getRtnPayMethod().equals( aascShipMethodInfo1.getCarrierPaymentTerms()))
              {
                  localCarrierCode=aascShipMethodInfo1.getCarrierPayCode();
              }
            }
          }
          catch(Exception e)
          {
           logger.severe("Got exception in aascFedexPackageOptions.jsp"+e.getMessage());
           e.printStackTrace();
          }
        %>
       <option value="<%=localCarrierCode%>"><%=nullStrToSpc(packBean.getRtnPayMethod())%></option>
      </select>
            <input type="hidden" name="ajaxAfterShipCarrPayMthdValue"
                   id="ajaxAfterShipCarrPayMthdValue"
                   value="<%=localCarrierCode%>"/>
                </td>  </tr>
              
            
              <tr>
                <td align="left" valign="top" class="dispalyFields" width="50%"> <s:property value="getText('A/CNumber')"/> </td>
                <td align="right" valign="top" width="50%"> 
                    <s:textfield name="rtnACNumber" cssClass="inputs"
                           id="rtnACNumber" value="%{#rtnACNumber}" size="15"
                           maxlength="30"/>
              <s:hidden name="ajaxAfterShipCarrAccNumber"
                        id="ajaxAfterShipCarrAccNumber"
                        value="%{#rtnACNumber}"/>
              
                </td>
                </tr>
              <tr>
                <td align="left" valign="top" class="dispalyFields" width="50%"> <s:property value="getText('RMA')"/> ? 
                </td>
                <td align="right" valign="top" width="50%"> 
                     <s:textfield name="rtnRMA" cssClass="inputs" id="rtnRMA"
                           maxlength="20" size="15"/>
                </td>
                </tr>
                
              <tr>
                 <td class="dispalyFields"  width="50%">
                        <s:property value="getText('DeclaredValue')" />
                    </td>
                <td align="right" width="50%">
                    <s:textfield name="rtnDeclaredValue" cssClass="inputs"
                         id="rtnDeclaredValue" maxlength="20" size="15"/>
                </td>
              
              </tr>
              <tr>
                <td align="left" valign="top" class="dispalyFields"  width="50%"> <s:property value="getText('TrackingNumber')"/></td>
                <td align="right" valign="top" width="50%"> 
                    <s:textfield name="rtnTrackingNumber" cssClass="inputs" id="rtnTrackingNumber" size="20" readonly="true"/>
                </td>
              </tr>
              <tr>
                <td align="left" valign="middle" class="dispalyFields"  width="50%">
                  <s:property value="getText('ReturnShipmentCost')"/>
                </td>
                <td align="right" valign="top" width="50%">
                  <s:textfield name="rtnShipmentCost" cssClass="inputs" id="rtnShipmentCost" size="15" readonly="true"/>
              <s:hidden name="packageSaveCheck" id="packageSaveCheck"/>
              <s:hidden name="locationID" value="%{#session.location_id_selected}" />
                </td>
              </tr>
               
                        </tbody>
                        </table>
                        </div>
        </div>
            <br/>
    </div>

</div>
</div>
	
                          
    </div>
    <div class="row">
     <div class="row"><br/></div>
          <div class="row" id="divSub" style="width:100%;">
                <div class=" col-sm-2" >
                </div>
                <s:if test='%{#shipFlag == "N" }' >
                <div class=" col-sm-8" align="center" id="buttonDiv2">
                         
                </div>
                </s:if>
                <s:else>
                 <div class=" col-sm-8" align="center"  id="buttonDiv2">
                         <button class="btn btn-primary" name="Save2" id="saveId1" disabled="disabled" > Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                         <button class="btn btn-warning" name="cancel" id="cancelId1" disabled="disabled"> Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button> 
                         <button class="btn btn-danger" name="close" id="closeId1" onclick="window.close();" > Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
              </div>  </s:else>
                <div class=" col-sm-2" >
                </div>
             </div>
    </div>
    </div>
    </s:form>
     <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div></body>
</html>
