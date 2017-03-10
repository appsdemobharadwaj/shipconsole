 <%       
/*==========================================================================+
|  DESCRIPTION                                                              |
|    JSP aascShipConsoleShipment for displaying the Shipment  related       | 
|    information                                                            |
|    Author Eshwari                                                         |
|    Modified Suman Gunda                                                   |
|    Version   1.1                                                          |                                                                            
|    Creation                                                               |      
|    History :                                                              |

06/01/2015      Suman G         Added code for getting flags
14/01/2015      Jagadish Jain   Added code for fix #2514
18/02/2015      Y Pradeep       Modified code from dimensionUnits to dimensionUnit to fix bug number #2526.
05/03/2015    Sanjay & Khaja Added code for new UI changes.
10/03/2015      Y Pradeep       Added missing name for param tag and replaced SOP's with logger.info.
09/03/2015      Suman G         Added code for holding package details after click on Get Rates button.
24/03/2015      Suman G         Modified code for fixing the issue #2725.
31/03/2015      Sunanda K       Modified code for bug fix #2782
01/04/2015     Suman G         Added code to fix #2763.
07/04/2015      Suman G         Added readonly for the total weight text field to fix #2805.
23/04/2015      Y Pradeep       Removed disabled = voidFlagDisable attribute for fields in this page to display fields in editable mode after voiding a order. 
24/04/2015      Suman G         Added scope attribute to packCount variable to fix the issue for MPS functionality.
14/05/2015      Y Pradeep       Modified code to disable void flag if void status is Y for packages.
27/05/2015      Suman G         Modified if condition to fix #2936
03/06/2015      Suman G         Added Padmavathi code to fix issue related to package no. font.
03/06/2015      Y Pradeep       Modified code to get weight from Weighing Scale or not.
03/06/2015      Suman G         Added code to fix #2955
09/06/2015      Sunanda K       Modified code for bug fix #2958
11/06/2015      Y Pradeep       Added onFocusWeightField() function to call weighing scale when weight field is focused. Bug #2978.
22/06/2015      Naresh          Commented the code for bug fix #3025.
17/06/2015      Y Pradeep       Removed code to get package details from sesson and changed it to get from db. Bug #3180.
04/08/2015      Y Pradeep       Added a if condition to set old pack count to 0 when a order is imported. Bug #3180.
+===========================================================================*/%>
 <%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
 <%@ page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@ page import="com.aasc.erp.util.*"%>
<%@page import="java.util.logging.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
 <script language="JavaScript" src="aasc_Shipment_JS.js" type="text/javascript"></script>
 <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>

<%! private static Logger logger=AascLogger.getLogger("aascShipmentPackageDetails"); %>
     <table class="table" style="background-color:#F0F0F0; border-radius: 5px; margin-left:5px;margin-bottom:1px"  cellpadding="0" cellspacing="0" id="PacTab">

        <tr valign="middle">
          <th width="6%">
            <div align="center">
              <s:property value="getText('PkgNo')"/>
            </div>
          </th>
          <th width="5%">
            <div align="center">
              <s:property value="getText('Void')"/>
            </div>
          </th>
          <th width="5%">
            <div align="center">
              <s:property value="getText('Weight')"/>
            </div>
          </th>
          <th width="5%">
            <div align="center">
              <s:property value="getText('UOM')"/>
            </div>
          </th>
          <th width="17%">
            <div align="center">
              <p><s:property value="getText('TrackingNumber')"/></p>
            </div>
          </th>
          <th width="17%">
            <div align="center">
              <s:property value="getText('Dimensions')"/>
            </div>
          </th>

          <th width="17%">
            <div align="center">
              <s:property value="getText('DeclaredValue')"/>
            </div>
          </th>
          <th width="11%">
            <div align="center">
              <s:property value="getText('CurrencyCode')"/>
            </div>
          </th>
          <th width="17%">
            <div align="center">
              <s:property value="getText('PackageOptions')"/>
            </div>
          </th>
        </tr>
        <jsp:useBean id="shipmentFlagsObj" class="com.aasc.erp.model.AascShipmentFlags"/>
        <c:set var="shipmentFlagsObj" value="${param.ShipmentFlags}" scope="request"/>
        <%
        
                AascShipmentFlags  shipmentFlags=(AascShipmentFlags)pageContext.getAttribute("shipmentFlagsObj");
                AascShipmentHeaderInfo  aascShipmentHeaderInfo=(AascShipmentHeaderInfo)request.getAttribute("aascShipmentHeaderInfo");
                pageContext.setAttribute("aascShipmentHeaderInfoTmp",aascShipmentHeaderInfo);
        %>
            
        
        <s:set name="shipmentFlags" value="%{#attr.shipmentFlags}"/>
        <s:set name="aascShipmentHeaderInfo" value="%{#attr.aascShipmentHeaderInfoTmp}"/>
        <s:set name="aascShipmentOrderInfo" value="%{#session.AascShipmentOrderInfo}"/>
        <s:set name="packageList" value="%{#aascShipmentOrderInfo.shipmentPackageInfo}"/>

        <c:set var="shipReadOnlyFlag" value="${param.shipReadOnlyFlag}"/>
        <s:set name="shipReadOnlyFlag" value="%{#attr.shipReadOnlyFlag}"/>
        <c:set var="flagUPSReadonly" value="${param.flagUPSReadonly}"/>
        <s:set name="flagUPSReadonly" value="%{#attr.flagUPSReadonly}"/>
		<s:set name="flagShip" value="%{#aascShipmentHeaderInfo.shipFlag}"/>
        <s:if test='%{#flagShip == "y" || #flagShip == "Y"}'>
        <s:set name="shipReadOnlyFlag" value="%{'true'}"/>
        </s:if>
        <c:catch var="exception1">
          <s:set name="aascPackageDimensionInfo" value="%{#session.aascPackageDimensionInfo}"/>
        </c:catch>
        <s:set name="packageDimensionList"  value="%{#aascPackageDimensionInfo.packageDimensionList}"/>
        <c:set var="aascCarrierProfileOptionsInfo" value="${param.aascCarrierProfileOptionsInfo}"/>
        <s:set name="aascCarrierProfileOptionsInfo" value="%{#attr.aascCarrierProfileOptionsInfo}"/>
        <s:set name="pkging" value="%{''}"/>
        <s:set name="AdditionalHandling" value="%{''}"/>
        <s:set name="LargePackage" value="%{''}"/>
        <s:set name="delConfirm" value="%{''}"/>
        <s:set name="declaredValStr" value="%{''}"/>
        <s:set name="codType" value="%{''}"/>
        <s:set name="codFundsCode" value="%{''}"/>
        <s:set name="codCurrCode" value="%{''}"/>
        <s:set name="declaredCurrCode" value="%{''}"/>
        <s:set name="LineNo" value="%{''}"/>
        <s:set name="Uom" value="%{''}"/>
        <s:set name="packCount" value="%{0}" scope="action"/>
        <s:set name="oldPackCount" value="%{0}"/>
        <s:set name="aascProfileOptionsInfo" value="%{#session.ProfileOptionsInfo}"/>
        <s:set name="orderNumber1" value="%{#aascShipmentHeaderInfo.orderNumber}"/>
        <s:set name="userId1" value="%{0}"/>
        <s:set name="roleId1" value="%{0}"/>
        <s:set name="userIdstr1" value="%{#session.UserId}"/>
        <s:set name="userId1" value="%{0}"/>
        <s:set name="userId1" value="%{#userIdstr1}"/>
        <s:set name="Error" value="%{''}" scope="session"/>
        <s:set name="wayBill" value="%{''}"/>
        <s:set name="roleIdstr" value="%{#session.role_id}"/>
        <s:set name="roleId1" value="%{#roleIdstr}"/>
        <s:set name="locationId" value="%{#session.locationId}"/>
        <s:hidden name="shipmentWarningStatus" id="shipmentWarningStatusID" value="%{''}"/>
        <%
            int countTmp=0;
            
            AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
            AascShipmentOrderInfoDAO aascShipmentOrderInfoDAO = null;
            
            aascShipmentOrderInfoDAO = aascDAOFactory.getAascShipmentOrderInfoDAO();
			String submitFlag = (String)session.getAttribute("submitFlag");
            
            if((aascShipmentHeaderInfo.getShipFlag()).equalsIgnoreCase("Y") || (aascShipmentHeaderInfo.getImportFlag()).equalsIgnoreCase("Y") || (((aascShipmentHeaderInfo.getAvFlag()).equalsIgnoreCase("Y") || (aascShipmentHeaderInfo.getFsFlag()).equalsIgnoreCase("Y"))&& !"Y".equalsIgnoreCase(submitFlag)))
            {
              Integer clientID=(Integer)session.getAttribute("client_id");
              String clientId = ""+clientID;
             
              try
              {
                String testclientId=clientId.substring(0,1);
              }
              catch(Exception e)
              {
               clientID=(Integer)session.getAttribute("clientId");
              }
              AascShipmentOrderInfo aascShipmentOrderInfoSaved =  aascShipmentOrderInfoDAO.getShipmentOrderInfo(aascShipmentHeaderInfo.getOrderNumber(),clientID);
              LinkedList savedPackageList=aascShipmentOrderInfoSaved.getShipmentPackageInfo();
                //below code added by Jagadish to display one row in package details for impoprted order instead of null
              if(savedPackageList.size()==0)
              {
              savedPackageList=new LinkedList();
              savedPackageList.add(new AascShipmentPackageInfo());              
              }
              int oldPackCount = 0;
              // Added below if condition to set old pack count to 0 when a order is imported. Bug #3180. By Y Pradeep
              if(!("Y").equalsIgnoreCase(aascShipmentHeaderInfo.getImportFlag())){
                oldPackCount = savedPackageList.size();
              }
              pageContext.setAttribute("oldPackCount",oldPackCount);
             
             pageContext.setAttribute("packageList",savedPackageList);  
            }
                        session.removeAttribute("submitFlag");
        %>
        <s:set name="oldPackCount" value="%{#attr.oldPackCount}"/>
        <s:set name="totalPackageDeclaredValue" value="%{0}"/>
        <s:iterator id="iterator" value="%{#attr.packageList}">
          <c:catch var="exception11">
            <s:set name="pb1" value="#iterator"/>
            <s:set name="packCount" value="%{#packCount+1}"/>
            <s:if test="%{#pb1.itemNumber == null}">
              <s:set name="LineNo" value="%{''}"/>
            </s:if>
            <s:else>
              <s:set name="LineNo" value="%{#pb1.itemNumber}"/>
            </s:else>
            <s:set name="ShippedQty" value="%{#pb1.shippedQuantity}"/>
            <s:set name="Weight" value="%{#pb1.weight}"/>
            <s:set name="msNo" value="%{#pb1.msn}"/>
            <s:if test="%{#pb1.uom == null}">
              <s:set name="Uom" value="%{'LB'}"/>
            </s:if>
            <s:else>
              <s:set name="Uom" value="%{#pb1.uom}"/>
            </s:else>
            <s:set name="TrackingNumber" value="%{#pb1.trackingNumber}"/>
            <s:set name="Dimensions" value="%{#pb1.dimensions}"/>
            <s:set name="cod" value="%{#pb1.codFlag}"/>
            <s:set name="hal" value="%{#pb1.halFlag}"/>
            <s:set name="halPhone" value="%{#pb1.halPhone}"/>
            <s:set name="halLine1" value="%{#pb1.halLine1}"/>
            <s:set name="halLine2" value="%{#pb1.halLine2}"/>
            <s:set name="halCity" value="%{#pb1.halCity}"/>
            <s:set name="halState" value="%{#pb1.halStateOrProvince}"/>
            <s:set name="halZip" value="%{#pb1.halPostalCode}"/>
			
			<s:set name="dryIceChk" value="%{#pb1.dryIceChk}" />
            <s:set name="dryIceUnits" value="%{#pb1.dryIceUnits}" />
            <s:set name="dryIceWeight" value="%{#pb1.dryIceWeight}" />
			
            <s:set name="HazMatFlag" value="%{#pb1.hazMatFlag}"/>
            <s:set name="HazMatType" value="%{#pb1.hazMatType}"/>
            <s:set name="HazMatClass" value="%{#pb1.hazMatClass}"/>
            <s:set name="HazMatCharges" value="%{#pb1.hazMatCharges}"/>
            <s:set name="HazMatQty" value="%{#pb1.hazMatQty}"/>
            <s:set name="HazMatUnit" value="%{#pb1.hazMatUnit}"/>
            <s:set name="HazMatIdNo" value="%{#pb1.hazMatIdNo}"/>
            <s:set name="HazMatPkgGroup" value="%{#pb1.hazMatPkgGroup}"/>
            <s:set name="HazMatDOTLabel" value="%{#pb1.hazMatDOTLabel}"/>
            <s:set name="HazMatEmerContactNo"  value="%{#pb1.hazMatEmerContactNo}"/>
            <s:set name="HazMatEmerContactName"  value="%{#pb1.hazMatEmerContactName}"/>
            <s:set name="HazMatId" value="%{#pb1.hazMatId}"/>
            <s:set name="HazmatPkgingCnt" value="%{#pb1.hazmatPkgingCnt}"/>
            <s:set name="HazmatPkgingUnits" value="%{#pb1.hazmatPkgingUnits}"/>
            <s:set name="HazMatPackInstructions" value="%{#pb1.hazmatPackInstructions}"/>
            <s:set name="hazmatTechnicalName"  value="%{#pb1.hazmatTechnicalName}"/>
            <s:set name="hazmatSignatureName" value="%{#pb1.hazmatSignatureName}"/>
            <s:set name="codAmt" value="%{#pb1.codAmt}"/>
             <s:set name="pkging" value="%{#pb1.packaging}"/>
              <s:set name="delConfirm" value="%{#pb1.signatureOptions}"/>
              <s:set name="codType" value="%{#pb1.codCode}"/>
              <s:set name="codFundsCode" value="%{#pb1.codFundsCode}"/>
              <s:set name="codCurrCode" value="%{#pb1.codCurrCode}"/>
              <s:set name="declaredCurrCode" value="%{#pb1.declaredCurrCode}"/>
            <s:set name="units" value="%{#pb1.dimensionUnits}"/>
            <s:set name="packageLength" value="%{#pb1.packageLength}"/>
            <s:set name="packageWidth" value="%{#pb1.packageWidth}"/>
            <s:set name="packageHeight" value="%{#pb1.packageHeight}"/>
            <s:set name="packageDimUnit" value="%{#pb1.dimensionUnits}"/>
            <s:set name="packageDimId" value="%{#pb1.dimensionId}"/>
            <s:set name="pkgDimensionId" value="%{#pb1.dimensionId}"/>
            <s:set name="pkgDimensionName" value="%{#pb1.dimensionName}"/>
            <s:set name="codAmtstr" value="%{''}"/>
            <s:set name="codChecked" value="%{''}"/>
            <s:set name="checkstatus" value="%{''}"/>
            <s:set name="voidFlagDisable" value="%{''}"/>
            <s:set name="packageDeclaredValue" value="%{0}"/>
            <s:set name="packageDeclaredValue"  value="%{#pb1.packageDeclaredValue}"/>
            <s:set name="totalPackageDeclaredValue" value="%{#totalPackageDeclaredValue + #packageDeclaredValue}"/>
            <s:set name="signatureOption" value="%{#pb1.signatureOptions}"/>
            <s:set name="returnShipment" value="%{#pb1.returnShipment}"/>
            <s:set name="returnDescription" value="%{#pb1.rtnDesc}"/>
            <s:set name="PackageSurcharge" value="%{#pb1.surCharges}"/>
            <s:set name="PackageShipmentCost" value="%{#pb1.pkgCost}"/>
            <s:set name="rtnShipFromCompany" value="%{''}"/>
            <s:set name="rtnShipToCompany" value="%{#pb1.rtnShipToCompany}"/>
            <s:set name="rtnShipFromContact" value="%{#pb1.rtnShipFromContact}"/>
            <s:set name="rtnShipToContact" value="%{#pb1.rtnShipToContact}"/>
            <s:set name="rtnShipFromLine1" value="%{#pb1.rtnShipFromLine1}"/>
            <s:set name="rtnShipToLine1" value="%{#pb1.rtnShipToLine1}"/>
            <s:set name="rtnShipFromLine2" value="%{#pb1.rtnShipFromLine2}"/>
            <s:set name="rtnShipToLine2" value="%{#pb1.rtnShipToLine2}"/>
            <s:set name="rtnShipFromCity" value="%{#pb1.rtnShipFromCity}"/>
            <s:set name="rtnCountrySymbol" value="%{#pb1.rtnCountrySymbol}"/>
            <s:set name="rtnShipFromSate" value="%{#pb1.rtnShipFromSate}"/>
            <s:set name="rtnShipFromZip" value="%{#pb1.rtnShipFromZip}"/>
              <s:set name="hazmatFlag" value="%{#pb1.hazmatFlag}"/>
            <s:set name="rtnShipToCity" value="%{#pb1.rtnShipToCity}"/>
            <s:set name="rtnShipToState" value="%{#pb1.rtnShipToState}"/>
            <s:set name="rtnShipToZip" value="%{#pb1.rtnShipToZip}"/>
            <s:set name="rtnShipFromPhone" value="%{#pb1.rtnShipFromPhone}"/>
            <s:set name="rtnShipToPhone" value="%{#pb1.rtnShipToPhone}"/>
            <s:set name="rtnShipMethod" value="%{#pb1.rtnShipMethod}"/>
            <s:set name="rtnDropOfType" value="%{#pb1.rtnDropOfType}"/>
            <s:set name="rtnPackageList" value="%{#pb1.rtnPackageList}"/>
            <s:set name="rtnPayMethod" value="%{#pb1.rtnPayMethod}"/>
            <s:set name="rtnPayMethodCode" value="%{''}"/>
            <s:set name="rtnACNumber" value="%{''}"/>
            <s:set name="rtnShipmentCost" value="%{0.0}"/>
            <s:set name="rtnDeclaredValue" value="%{0.0}"/>
            <c:catch var="exception10">
              <s:set name="rtnACNumber" value="%{#pb1.rtnACNumber}"/>
            </c:catch>
            <s:if test="%{#exception10 != null}">
              <s:set name="rtnACNumber" value="%{''}"/>
            </s:if>
            <s:set name="rtnRMA" value="%{#pb1.rtnRMA}"/>
            <s:set name="rtnTrackingNumber" value="%{#pb1.rtnTrackingNumber}"/>
            <c:catch var="exception9">
              <s:set name="rtnShipmentCost" value="%{#pb1.rtnShipmentCost}"/>
            </c:catch>
            <s:if test="%{#exception9 != null}">
              <s:set name="rtnShipmentCost" value="%{0.0}"/>
            </s:if>
            <c:catch var="exception8">
              <s:set name="rtnDeclaredValue" value="%{#pb1.rtnDeclaredValue}"/>
            </c:catch>
            <s:if test="%{#exception8 != null}">
              <s:set name="rtnDeclaredValue" value="%{0.0}"/>
            </s:if>
            <s:set name="voidFlagHidden" value="%{''}"/>
            <s:set name="voidFlag" value="%{#pb1.voidFlag}"/>
            <s:set name="AdditionalHandling" value="%{#pb1.AdditionalHandling}"/>
            <s:set name="LargePackage" value="%{#pb1.LargePackage}"/>
           <s:if test='%{#voidFlag == "null" || #voidFlag == "NULL"}'>
              <s:set name="voidFlagHidden" value="%{''}"/>
            </s:if>
            <s:else>
              <s:set name="voidFlagHidden" value="%{#pb1.voidFlag}"/>
            </s:else>
            <c:catch var="exception7">
              <s:if test='%{#voidFlagHidden == "null" || #voidFlagHidden == ""}'>
                <s:set name="voidFlagHidden" value="%{'N'}"/>
              </s:if>
            </c:catch>
            <s:if test="%{#exception7 != null}">
              <s:set name="voidFlagHidden" value="%{'N'}"/>
            </s:if>
            <s:if test='%{#cod == "Y" || #cod == "y"}'>
              <s:set name="codChecked" value="%{'CHECKED'}"/>
              <s:set name="codAmtstr" value="%{#codAmt}"/>
            </s:if>
            <s:if test='%{#voidFlag == "Y" || #voidFlag == "y" }'>
              <s:set name="checkstatus" value="%{'true'}"/>
              <s:set name="voidFlagDisable" value="%{'true'}"/>
            </s:if>
            <s:else>
              <s:set name="checkstatus" value="%{'false'}"/>
              <s:set name="voidFlagDisable" value="%{''}"/>
              <s:set name="voidFlag" value="%{'N'}"/>
            </s:else>
            <s:set name="voidFlagDisableTmp" value="%{#voidFlagDisable}" scope="page"/>
            <tr  >
              <td height="43%" class="innertablecss">
                <div align="center">
                <b>  <s:property value="%{#packCount}"/>  </b>
                </div>
              </td>
              <s:if test='%{#shipmentFlags != "DISABLED" && #shipmentFlags != "disabled"}'>
                <s:hidden name="chVoidHidden%{#packCount}"
                          id="chVoidHiddenID%{#packCount}"
                          value="%{#voidFlagHidden}"/>
                <td  align="center">
                  <s:checkbox name="chVoid%{#packCount}"
                              id="chVoidID%{#packCount}" cssClass="inputsPackage"
                              value="%{#checkstatus}" fieldValue="%{#voidFlag}" disabled="%{#voidFlagDisable}" />
                </td>
     
                <td  align="center">
                
                <s:hidden name="shippedQty%{#packCount}" id="shippedQtyID%{#packCount}"  />
                <s:hidden name="lineNo%{#packCount}"  id="lineNoID%{#packCount}" />
                
                <!--onfocus="onFocusWeightField(%{#packCount})"-->
                <s:textfield name="weight%{#packCount}" onfocus="openStramWeighingScale(%{#packCount});"
                                 id="weightID%{#packCount}"
                                 cssClass="inputsPackage" value="%{#Weight}"
                                 size="5" maxlength="5" onblur="closingStram();totalWeight()"
                                 readonly="%{#shipReadOnlyFlag}" />
                </td>
                <td  align="center">
                  <s:textfield name="uom%{#packCount}" id="uomID%{#packCount}"
                               cssClass="inputsPackage" value="%{#Uom}" size="3"
                               maxlength="3" readonly="%{#shipReadOnlyFlag}" />
                </td>
                <td  align="center">
                  <s:textfield name="trackingNumber%{#packCount}"
                               id="trackingNumberID%{#packCount}"
                               cssClass="inputsPackage" value="%{#TrackingNumber}"
                               size="40" maxlength="25" readonly="true" onkeydown="backspace(event);" /><!--Sunanda added for bug fix #2782-->
                </td>
                <td  nowrap="nowrap" align="center">
                  <s:hidden name="packageLength%{#packCount}" cssClass="inputsPackage"  id="packageLengthID%{#packCount}"  value="%{#packageLength}" />
                  <s:hidden name="packageWidth%{#packCount}" cssClass="inputsPackage" id="packageWidthID%{#packCount}" value="%{#packageWidth}" />
                  <s:hidden name="packageHeight%{#packCount}"  cssClass="inputsPackage" id="packageHeightID%{#packCount}" value="%{#packageHeight}" />
                  <s:hidden name="msnNo%{#packCount}" id="msnNo" value="%{#msNo}"/>
                  <s:set name="packCountTmp" value="%{#packCount}" scope="page"/>
                  <s:set name="dimensionCount" value="%{0}"/>
                  <s:iterator id="packageDimensionIterator" value="packageDimensionList">
                    <s:set name="dimensionCount" value="%{#dimensionCount+1}"/>
                    <s:set name="aascPackageDimensionInfo1"  value="#packageDimensionIterator"/>
                  </s:iterator>
                  <s:set name="defaultDimensionName" value="%{''}"/>
                  <s:set name="defaultDimensionIdStr" value="%{''}"/>
                  <s:set name="dimensionCount" value="%{0}"/>
                  <s:set name="dimensionId" value="%{0}"/>
                  <s:set name="dimensionIdStr" value="%{''}"/>
                  <s:set name="dimensionName" value="%{''}"/>
                  <s:set name="dimensionLength" value="%{0}"/>
                  <s:set name="dimensionWidth" value="%{0}"/>
                  <s:set name="dimensionHeight" value="%{0}"/>
                  <s:set name="dimensionUnits" value="%{''}"/>
                  <s:set name="dimensionDefault" value="%{''}"/>
                  <s:set name="dimensionActive" value="%{''}"/>
                  <s:set name="defaultDimeName" value="%{''}"/>
                  <%
                     int packCount=(Integer)pageContext.getAttribute("packCountTmp");
                     String voidFlagDisable=(String)pageContext.getAttribute("voidFlagDisableTmp");                    
                  %>
                  
                  <c:set var="packCount" value="${param.packCount}"/>
                  <s:set name="packCount" value="%{#attr.packCount}"/>
                  <%
                        /*int countTmp=0;
                        int packCount=(Integer)pageContext.getAttribute("packCountTmp");
                        String voidFlagDisable=(String)pageContext.getAttribute("voidFlagDisableTmp");  */
                  %>
                  <select name="dimensionName<%=packCount%>" id="dimensionNameID<%=packCount%>" <%=voidFlagDisable%> class="inputsPackage"  >
                     <s:iterator id="packageDimensionNameIterator" value="packageDimensionList">               
                        <s:set name="dimensionCount" value="%{#dimensionCount+1}"/>
                        <s:set name="aascPackageDimensionInfo1" value="#packageDimensionNameIterator"/>
                        <%
                          AascPackageDimensionInfo aascPackageDimensionInfo1=(AascPackageDimensionInfo)request.getAttribute("aascPackageDimensionInfo1");
                        %>
                        <s:set name="dimensionId" value="%{#aascPackageDimensionInfo1.dimensionId}"/>
                        <s:set name="dimensionName" value="%{#aascPackageDimensionInfo1.dimensionName}"/>
                        <s:set name="dimensionLength" value="%{#aascPackageDimensionInfo1.dimensionLength}"/>
                        <s:set name="dimensionWidth" value="%{#aascPackageDimensionInfo1.dimensionWidth}"/>
                        <s:set name="dimensionHeight" value="%{#aascPackageDimensionInfo1.dimensionHeight}"/>
                        <s:set name="dimensionUnits" value="%{#aascPackageDimensionInfo1.dimensionUnits}"/>
                        <s:set name="dimensionActive" value="%{#aascPackageDimensionInfo1.dimensionActive}"/>
                        <s:set name="dimensionDefault" value="%{#aascPackageDimensionInfo1.dimensionDefault}"/>
                        <s:set name="activeCheckstatus" value="%{''}"/>
                        <s:set name="defaultCheckstatus" value="%{''}"/>
                        <s:if test='%{#dimensionDefault == "Y" || #dimensionDefault == "y"}'>
                          <s:set name="defaultDimensionName" value="%{#aascPackageDimensionInfo1.dimensionName}"/>
                          <s:set name="defaultDimensionIdStr" value="%{#dimensionLength+'*'+#dimensionWidth+'*'+#dimensionHeight+'*'+#dimensionUnits+'*'+#dimensionId}"/>
                        </s:if>
                        <s:if test='%{#dimensionActive == "Y" || #dimensionActive == "y"}'>
                          <s:set name="dimensionActive" value="%{'Y'}"/>
                        </s:if>
                        <s:else>
                          <s:set name="dimensionActive" value="%{'N'}"/>
                        </s:else>
                        <s:if test='%{#dimensionDefault == "Y" || #dimensionDefault == "y"}'>
                          <s:set name="dimensionDefault" value='%{"Y"}'/>
                        </s:if>
                        <s:else>
                          <s:set name="dimensionDefault" value='%{"N"}'/>
                        </s:else>
                        <s:if test='%{#dimensionDefault == "Y" || #dimensionDefault == "y"}'>
                              <s:set name="defaultDimeName" value="%{#dimensionName}"/>
                        </s:if>
                        <s:if test="%{#pkgDimensionId == #dimensionId}">
                          <s:set name="dimensionIdStr" value="%{#packageLength+'*'+#packageWidth+'*'+#packageHeight+'*'+#packageDimUnit+'*'+#packageDimId}"/>
                        </s:if>
                        <s:else>
                          <s:set name="dimensionIdStr" value="%{#dimensionLength+'*'+#dimensionWidth+'*'+#dimensionHeight+'*'+#dimensionUnits+'*'+#dimensionId}"/>
                        </s:else>
                        <s:set name="pkgDimensionIdTmp" value="%{#pkgDimensionId}" scope="page"/>
                        <s:set name="dimensionIdTmp" value="%{#dimensionId}" scope="page"/>
                        <s:set name="dimensionDefaultTmp" value="%{#dimensionDefault}" scope="page"/>
                        <s:set name="dimensionIdStrTmp" value="%{#dimensionIdStr}" scope="page"/>
                        <s:set name="dimensionNameTmp" value="%{#dimensionName}" scope="page"/>
                        <s:set name="dimensionCountTmp" value="%{#dimensionCount}" scope="page"/>
                        <%
                          int pkgDimensionId=0;
                          int dimensionId=0;
                          String dimensionDefault="";
                          String dimensionIdStr="";
                          String dimensionName="";
                          int dimensionCount=0;
                          try{
                     
                           pkgDimensionId=(Integer)pageContext.getAttribute("pkgDimensionIdTmp");
                           dimensionId=(Integer)pageContext.getAttribute("dimensionIdTmp");
                           dimensionDefault=(String)pageContext.getAttribute("dimensionDefaultTmp");
                           dimensionIdStr=(String)pageContext.getAttribute("dimensionIdStrTmp");
                           dimensionName=(String)pageContext.getAttribute("dimensionNameTmp");
                           dimensionCount=(Integer)pageContext.getAttribute("dimensionCountTmp");
                          }
                          catch(Exception e){
                            e.getMessage();
                          }
                        %>
                        <%
                         if((pkgDimensionId == dimensionId) && dimensionDefault.equalsIgnoreCase("N")){
                        
                        %>
                        <option value="<%=dimensionIdStr%>" selected="selected"> <%=dimensionName%>  </option>
                        <%
                         }
                         else if(dimensionDefault.equalsIgnoreCase("Y") )
                         {
                           if(((pkgDimensionId == dimensionId) && (pkgDimensionId != 0)) || ((pkgDimensionId != dimensionId) && (pkgDimensionId == 0)))
                           {
                             countTmp++;
                        %>
                            <option value="<%=dimensionIdStr%>" selected="selected">  <%=dimensionName%>  </option>
                        <%
                           }else{
                        %>
                            <option value="<%=dimensionIdStr%>"> <%=dimensionName%>   </option>
                        <% 
                           }
                         }else
                         {
                           if((dimensionName.equalsIgnoreCase("Other") ) && (dimensionCount==2) && countTmp == 0)
                           {
                       %>
                            <option value="<%=dimensionIdStr%>">  <%=dimensionName%> </option>   <!--removed "selected" by Shiva -->
                       <%
                           }
                           else
                           {            
                       %>
                            <option value="<%=dimensionIdStr%>">  <%=dimensionName%>   </option>
                       <%
                           }
                         } %>
                     
                     </s:iterator>
                  </select>
        
        
          
                  <input type="BUTTON" name="dimButton<%=packCount%>" id="dimButtonID<%=packCount%>" value="^" class="inputsPackage" style="width:20px" onclick="openDimPopup('<%=packCount%>')" <%=voidFlagDisable%>/>
                  <s:hidden  name ="defaultDimValue" id ="defaultDimValueID"  value = "%{#defaultDimensionIdStr}" />
                  <s:hidden  name ="defaultDimText"  id ="defaultDimTextID" value = "%{#defaultDimensionName}" />
                  <s:hidden name ="defaultDimeName%{#packCount}"   id ="defaultDimeNameID%{#packCount}" value = "%{#defaultDimeName}" />
                </td>
                <s:hidden name="dimValue%{#packCount}" id="dimValueID%{#packCount}" value="%{''}"/>
                <s:hidden name="units%{#packCount}" id="unitsID%{#packCount}" value="%{''}"/>
                <s:hidden name="dimensionNameHidden%{#packCount}"  id="dimensionNameHiddenID%{#packCount}" value="%{''}"/>
                <s:hidden name="dimensionValueHidden%{#packCount}" id="dimensionValueHiddenID%{#packCount}" value="%{''}"/>
                <td width="3%"  align="center">
                  <s:textfield name="packageDeclaredValue%{#packCount}" id="packageDeclaredValueID%{#packCount}" cssClass="inputsPackage" value="%{#packageDeclaredValue}"   size="8" maxlength="10" onblur="javascript:packageDeclaredValuefn('%{#packCount}')" readonly="%{#shipReadOnlyFlag}" />
                </td>
                <td width="5%"  align="center">
            <s:hidden  name="declaredCurrCode%{#packCount}" id="declaredCurrCodeID%{#packCount}" cssClass="inputsPackage" />
             <label style="font-weight:bold; font-size:12px" id="currencyCodeId"> USD </label>
             <s:hidden name="codAmt%{#packCount}" id="codAmtID%{#packCount}"  value="%{#codAmtstr}"/>
                </td>
                <s:if test='%{(#aascCarrierProfileOptionsInfo == null || #aascCarrierProfileOptionsInfo == \"\")|| #aascShipmentHeaderInfo.shipFlag == \"Y\" || #aascShipmentHeaderInfo.shipFlag == \"y\"}'>
                  <s:hidden name="signatureOption%{#packCount}" id="signatureOptionID%{#packCount}"  value="%{#signatureOption}"/>
                  <s:hidden name="returnShipment%{#packCount}" id="returnShipmentID%{#packCount}" value="%{#returnShipment}"/>
                  <s:hidden name="holdAtLocation%{#packCount}" id="holdAtLocationID%{#packCount}" value="%{#hal}"/>
                </s:if>
                <s:hidden name="returnShipment%{#packCount}" id="returnShipmentID%{#packCount}" value="%{#returnShipment}"/>
                  <s:hidden name="returnDescription%{#packCount}" id="returnDescriptionID%{#packCount}" value="%{#returnDescription}"/>
               
                <s:hidden name="halPhone%{#packCount}" id="halPhoneID%{#packCount}" value="%{#halPhone}"/>
                <s:hidden name="halAddrLine1%{#packCount}" id="halAddrLine1ID%{#packCount}" value="%{#halLine1}"/>
                <s:hidden name="halAddrLine2%{#packCount}" id="halAddrLine2ID%{#packCount}"  value="%{#halLine2}"/>
                <s:hidden name="halCity%{#packCount}" id="halCityID%{#packCount}" value="%{#halCity}"/>
                <s:hidden name="halState%{#packCount}" id="halStateID%{#packCount}" value="%{#halState}"/>
                <s:hidden name="halZip%{#packCount}" id="halZipID%{#packCount}" value="%{#halZip}"/>
				
				<s:hidden name="chDryIce%{#packCount}" id="chDryIceID%{#packCount}" value="%{#dryIceChk}"/>
                <s:hidden name="dryIceWeight%{#packCount}" id="dryIceWeightID%{#packCount}" value="%{#dryIceWeight}"/>
                <s:hidden name="dryIceUnits%{#packCount}" id="dryIceUnitsID%{#packCount}" value="%{#dryIceUnits}"/>
                
                <s:hidden name="HazMatFlag%{#packCount}" id="HazMatFlagID%{#packCount}" value="%{#HazMatFlag}"/>
                <s:hidden name="HazMatType%{#packCount}" id="HazMatTypeID%{#packCount}" value="%{#HazMatType}"/>
                <s:hidden name="HazMatCount%{#packCount}" id="HazMatCountID%{#packCount}" value="%{#hazmatListCount}"/>
                <s:hidden name="HazMatClass%{#packCount}" id="HazMatClassID%{#packCount}" value="%{#HazMatClass}"/>
                <s:hidden name="HazMatCharges%{#packCount}" id="HazMatChargesID%{#packCount}" value="%{#HazMatCharges}"/>
                <s:hidden name="HazMatQty%{#packCount}" id="HazMatQtyID%{#packCount}" value="%{#HazMatQty}"/>
                <s:hidden name="HazMatUnit%{#packCount}" id="HazMatUnitID%{#packCount}"  value="%{#HazMatUnit}"/>
                <s:hidden name="HazMatIdNo%{#packCount}" id="HazMatIdNoID%{#packCount}"  value="%{#HazMatIdNo}"/>
                <s:hidden name="HazMatPkgGroup%{#packCount}"  id="HazMatPkgGroupID%{#packCount}" value="%{#HazMatPkgGroup}"/>
                <s:hidden name="HazMatDOTLabel%{#packCount}"  id="HazMatDOTLabelID%{#packCount}" value="%{#HazMatDOTLabel}"/>
                <s:hidden name="HazMatEmerContactNo%{#packCount}"  id="HazMatEmerContactNoID%{#packCount}"  value="%{#HazMatEmerContactNo}"/>
                <s:hidden name="HazMatEmerContactName%{#packCount}" id="HazMatEmerContactNameID%{#packCount}"  value="%{#HazMatEmerContactName}"/>
                <s:hidden name="HazMatId%{#packCount}" id="HazMatIdID%{#packCount}" value="%{#HazMatId}"/>
                <s:hidden name="HazMatPackagingCnt%{#packCount}"  id="HazMatPackagingCntID%{#packCount}" value="%{#HazmatPkgingCnt}"/>
                <s:hidden name="HazMatPackagingUnits%{#packCount}"  id="HazMatPackagingUnitsID%{#packCount}"  value="%{#HazmatPkgingUnits}"/>
                <s:hidden name="HazMatPackInstructions%{#packCount}"  id="HazMatPackInstructionsID%{#packCount}"  value="%{#HazMatPackInstructions}"/>
                <s:hidden name="HazMatTechnicalName%{#packCount}"  id="HazMatTechnicalNameID%{#packCount}"  value="%{#hazmatTechnicalName}"/>
                <s:hidden name="HazMatSignatureName%{#packCount}"  id="HazMatSignatureNameID%{#packCount}"  value="%{#hazmatSignatureName}"/>
                <s:hidden name="PackageSurcharge%{#packCount}"  id="PackageSurchargeID%{#packCount}"  value="%{#PackageSurcharge}"/>
                <s:hidden name="PackageShipmentCost%{#packCount}"  id="PackageShipmentCostID%{#packCount}"  value="%{#PackageShipmentCost}"/>
                <s:hidden name="rtnShipFromCompany%{#packCount}"  id="rtnShipFromCompanyID%{#packCount}"  value="%{#rtnShipFromCompany}"/>
                <s:hidden name="rtnShipToCompany%{#packCount}"  id="rtnShipToCompanyID%{#packCount}"  value="%{#rtnShipToCompany}"/>
                <s:hidden name="rtnShipFromContact%{#packCount}"  id="rtnShipFromContactID%{#packCount}"  value="%{#rtnShipFromContact}"/>
                <s:hidden name="rtnShipToContact%{#packCount}"  id="rtnShipToContactID%{#packCount}"  value="%{#rtnShipToContact}"/>
                <s:hidden name="rtnShipFromLine1%{#packCount}"  id="rtnShipFromLine1ID%{#packCount}"  value="%{#rtnShipFromLine1}"/>
                <s:hidden name="rtnShipToLine1%{#packCount}"  id="rtnShipToLine1ID%{#packCount}" value="%{#rtnShipToLine1}"/>
                <s:hidden name="rtnShipFromLine2%{#packCount}"  id="rtnShipFromLine2ID%{#packCount}"  value="%{#rtnShipFromLine2}"/>
                <s:hidden name="rtnShipToLine2%{#packCount}"  id="rtnShipToLine2ID%{#packCount}" value="%{#rtnShipToLine2}"/>
                <s:hidden name="rtnShipFromCity%{#packCount}"  id="rtnShipFromCityID%{#packCount}" value="%{#rtnShipFromCity}"/>
                <s:hidden name="rtnCountrySymbol%{#packCount}"  id="rtnCountrySymbolID%{#packCount}" value="%{#rtnCountrySymbol}"/>
                <s:hidden name="rtnShipFromSate%{#packCount}"  id="rtnShipFromSateID%{#packCount}" value="%{#rtnShipFromSate}"/>
                <s:hidden name="rtnShipFromZip%{#packCount}"  id="rtnShipFromZipID%{#packCount}" value="%{#rtnShipFromZip}"/>
                
                <s:hidden name="rtnShipToCity%{#packCount}"  id="rtnShipToCityID%{#packCount}" value="%{#rtnShipToCity%>"/>
                <s:hidden name="rtnShipToState%{#packCount}"  id="rtnShipToStateID%{#packCount}" value="%{#rtnShipToState}"/>
                <s:hidden name="rtnShipToZip%{#packCount}" id="rtnShipToZipID%{#packCount}"  value="%{#rtnShipToZip}"/>
                <s:hidden name="rtnShipFromPhone%{#packCount}" id="rtnShipFromPhoneID%{#packCount}" value="%{#rtnShipFromPhone}"/>
                <s:hidden name="rtnShipToPhone%{#packCount}"  id="rtnShipToPhoneID%{#packCount}" value="%{#rtnShipToPhone}"/>
                <s:hidden name="rtnShipMethod%{#packCount}"  id="rtnShipMethodID%{#packCount}" value="%{#rtnShipMethod}"/>
                <s:hidden name="rtnDropOfType%{#packCount}"  id="rtnDropOfTypeID%{#packCount}" value="%{#rtnDropOfType}"/>
                <s:hidden name="rtnPackageList%{#packCount}"  id="rtnPackageListID%{#packCount}" value="%{#rtnPackageList}"/>
                <s:hidden name="rtnPayMethod%{#packCount}" id="rtnPayMethodID%{#packCount}"  value="%{#rtnPayMethod}"/>
                <s:hidden name="rtnACNumber%{#packCount}" id="rtnACNumberID%{#packCount}" value="%{#rtnACNumber}"/>
                <s:hidden name="rtnRMA%{#packCount}" id="rtnRMAID%{#packCount}"  value="%{#rtnRMA}"/>
                <s:hidden name="rtnTrackingNumber%{#packCount}"  id="rtnTrackingNumberID%{#packCount}"  value="%{#rtnTrackingNumber}"/>
                <s:hidden name="rtnShipmentCost%{#packCount}"  id="rtnShipmentCostID%{#packCount}" value="%{#rtnShipmentCost}"/>
                <s:if test="%{#aascCarrierProfileOptionsInfo == null || #aascCarrierProfileOptionsInfo == ''}">
                  <s:hidden name="chCOD%{#packCount}" id="chCODID%{#packCount}"  value="%{#cod}"/>
                  <s:hidden name="AdditionalHandling%{#packCount}" id="AdditionalHandlingID%{#packCount}"  value="%{#AdditionalHandling}"/>
                  <s:hidden name="LargePackage%{#packCount}" id="LargePackageID%{#packCount}"  value="%{#LargePackage}"/>
                  <s:hidden name="pkging%{#packCount}" id="pkgingID%{#packCount}"  value="%{#pkging}"/>
                  <s:hidden name="codType%{#packCount}" id="codTypeID%{#packCount}"  value="%{#codType}"/>
                  <s:hidden name="delConfirm%{#packCount}" id="delConfirmID%{#packCount}"  value="%{#delConfirm}"/>
                  <s:hidden name="codFundsCode%{#packCount}"  id="codFundsCodeID%{#packCount}" value="%{#codFundsCode}"/>
                  <s:hidden name="codCurrCode%{#packCount}" id="codCurrCodeID%{#packCount}"  value="%{#codCurrCode}"/>
                </s:if>
                <s:hidden name="addlHandlingFlag%{#packCount}" id="addlHandlingFlagID%{#packCount}"  value="%{#addHandling}"/>
                  <s:hidden name="largePackageFlag%{#packCount}" id="largePackageFlagID%{#packCount}"  value="%{#LargePackage}"/>
                  
                <s:hidden name="upsSurCharge%{#packCount}" id="upsSurChargeID%{#packCount}"  value="%{#PackageSurcharge}"/>
                <s:hidden name="packageSaveCheck%{#packCount}" id="packageSaveCheckID%{#packCount}"/>
                <s:hidden name="rtnShipMethodName%{#packCount}"  id="rtnShipMethodNameID%{#packCount}" value="%{''}"/>
                <s:hidden name="rtnPayMethodCode%{#packCount}"  id="rtnPayMethodCodeID%{#packCount}"  value="%{#rtnPayMethodCode}"/>
                <s:hidden name="rtnDeclaredValue%{#packCount}"  id="rtnDeclaredValueID%{#packCount}"  value="%{#rtnDeclaredValue}"/>
                <%
             try{
                packCount=(Integer)pageContext.getAttribute("packCountTmp");
               }catch(Exception e){
               logger.info(e.getMessage());    
             packCount=1;
               }%>
               
                <td width="5%"  align="center">
                  <s:a href="#" id="pkgDetailsId%{#packCount}"  onclick="openPackPopup('%{#packCount}')">
                    <img name="packOpt<%=packCount%>" id="packOptId<%=packCount%>" src="images/PK.png"  align="center" width="40px" height="30px"  border="0"></img>
                
                  </s:a>
                </td>
              </s:if>
              <s:else>
              <td  align="center">
                <s:checkbox name="chVoid%{#packCount}" id="chVoidID%{#packCount}" cssClass="inputsPackage" value="%{#checkstatus}" fieldValue="%{#voidFlag}" disabled="%{#voidFlagDisable}"/>
              </td>
              <td width="3%"  align="center">
                <s:textfield name="lineNo%{#packCount}" id="lineNoID%{#packCount}" cssStyle="width: 309px; height: 18px;" cssClass="inputsPackage" value="%{#LineNo}" size="3" maxlength="25" readonly="%{#shipReadOnlyFlag}" />
              </td>
              <td width="3%"  align="center">
                <s:textfield name="shippedQty%{#packCount}" cssClass="inputsPackage" value="%{#ShippedQty}" size="5" maxlength="5" readonly="%{#flagUPSReadonly}" />
              </td>
              <td width="3%"  align="center">
                <s:textfield name="weight%{#packCount}" id="weightID%{#packCount}" cssClass="inputsPackage" value="%{#Weight}" size="5"  maxlength="5" onblur="totalWeight()" readonly="%{#flagUPSReadonly}" />
              </td>
              <td > <s:textfield name="uom%{#packCount}" id="uomID%{#packCount}" cssClass="inputsPackage" value="%{#Uom}" size="3" maxlength="3" readonly="%{#shipReadOnlyFlag}" /> </td>
              <td  align="center">
                <s:textfield name="trackingNumber%{#packCount}" id="trackingNumberID%{#packCount}" cssClass="inputsPackage" value="%{#TrackingNumber}" size="15" maxlength="25" readonly="%{#shipReadOnlyFlag}"  />
              </td>
              <td  nowrap="nowrap" align="center">
                <s:hidden name="packageLength%{#packCount}" cssClass="inputsPackage" id="packageLengthID%{#packCount}" value="%{#packageLength%>"/>
                <s:hidden name="packageWidth%{#packCount}" cssClass="inputsPackage" id="packageWidthID%{#packCount}" value="%{#packageWidth}"/>
                <s:hidden name="packageHeight%{#packCount}" cssClass="inputsPackage" id="packageHeightID%{#packCount}" value="%{#packageHeight}"/>
                <s:set name="dimensionCount" value="%{0}"/>
                <s:iterator id="packageDimensionIterator" value="packageDimensionList">
                  <s:set name="dimensionCount" value="%{#dimensionCount+1}"/>
                  <s:set name="aascPackageDimensionInfo1" value="#packageDimensionIterator"/>
                </s:iterator>
                <s:set name="defaultDimensionName" value="%{''}"/>
                <s:set name="defaultDimensionIdStr" value="%{''}"/>
                <s:set name="dimensionCount" value="%{0}"/>
                <s:set name="dimensionId" value="%{0}"/>
                <s:set name="dimensionIdStr" value="%{''}"/>
                <s:set name="dimensionName" value="%{''}"/>
                <s:set name="dimensionLength" value="%{0}"/>
                <s:set name="dimensionWidth" value="%{0}"/>
                <s:set name="dimensionHeight" value="%{0}"/>
                <s:set name="dimensionUnits" value="%{''}"/>
                <s:set name="dimensionDefault" value="%{''}"/>
                <s:set name="dimensionActive" value="%{''}"/>
                <s:set name="defaultDimeName" value="%{''}"/>
                <s:iterator id="packageDimensionNameIterator" value="packageDimensionList">
                  <s:set name="dimensionCount" value="%{#dimensionCount+1}"/>
                  <s:set name="aascPackageDimensionInfo1" value="#packageDimensionNameIterator"/>
                  <s:set name="dimensionId" value="%{#aascPackageDimensionInfo1.dimensionId}"/>
                  <s:set name="dimensionName" value="%{#aascPackageDimensionInfo1.dimensionName}"/>
                  <s:set name="dimensionLength"  value="%{#aascPackageDimensionInfo1.dimensionLength}"/>
                  <s:set name="dimensionWidth"  value="%{#aascPackageDimensionInfo1.dimensionWidth}"/>
                  <s:set name="dimensionHeight" value="%{#aascPackageDimensionInfo1.dimensionHeight}"/>
                  <s:set name="dimensionUnits"  value="%{#aascPackageDimensionInfo1.dimensionUnits}"/>
                  <s:set name="dimensionActive" value="%{#aascPackageDimensionInfo1.dimensionActive}"/>
                  <s:set name="dimensionDefault"  value="%{#aascPackageDimensionInfo1.dimensionDefault}"/>
                  <s:set name="activeCheckstatus" value="%{''}"/>
                  <s:set name="defaultCheckstatus" value="%{''}"/>
                  <s:if test='%{#dimensionDefault == "Y" || #dimensionDefault == "y"}'>
                    <s:set name="defaultDimensionName" value="%{#aascPackageDimensionInfo1.dimensionName}"/>
                    <s:set name="defaultDimensionIdStr" value="%{#dimensionLength+'*'+#dimensionWidth+'*'+#dimensionHeight+'*'+#dimensionUnits+'*'+#dimensionId}"/>
                  </s:if>
                  <s:if test='%{#dimensionActive == "Y" || #dimensionActive == "y"}'>
                    <s:set name="dimensionActive" value="%{'Y'}"/>
                  </s:if>
                  <s:else>
                    <s:set name="dimensionActive" value="%{'N'}"/>
                  </s:else>
                  <s:if test='%{#dimensionDefault == "Y" || #dimensionDefault == "y"}'>
                    <s:set name="dimensionDefault" value="%{'Y'}"/>
                  </s:if>
                  <s:else>
                    <s:set name="dimensionDefault" value="%{'N'}"/>
                  </s:else>
                  <s:if test='%{#dimensionDefault == "Y" || #dimensionDefault == "y"}'>
                    <s:set name="defaultDimeName" value="%{#dimensionName}"/>
                  </s:if>
                  <s:if test="%{#pkgDimensionId == #dimensionId}">
                    <s:set name="dimensionIdStr" value="%{#packageLength+'*'+#packageWidth+'*'+#packageHeight+'*'+#packageDimUnit+'*'+#packageDimId}"/>
                  </s:if>
                  <s:else>
                    <s:set name="dimensionIdStr"   value="%{#dimensionLength+'*'+#dimensionWidth+'*'+#dimensionHeight+'*'+#dimensionUnits+'*'+#dimensionId}"/>
                  </s:else>
                  <s:set name="pkgDimensionIdTmp" value="%{#pkgDimensionId}"     scope="page"/>
                  <s:set name="dimensionIdTmp" value="%{#dimensionId}" scope="page"/>
                  <s:set name="dimensionDefaultTmp" value="%{#dimensionDefault}" scope="page"/>
                  <s:set name="dimensionIdStrTmp" value="%{#dimensionIdStr}"  scope="page"/>
                  <s:set name="dimensionNameTmp" value="%{#dimensionName}"  scope="page"/>
                  <s:set name="dimensionCountTmp" value="%{#dimensionCount}"  scope="page"/>
                  <%
                          int packCount=(Integer)pageContext.getAttribute("packCountTmp");
                          String voidFlagDisable=(String)pageContext.getAttribute("voidFlagDisableTmp");
                          int pkgDimensionId=(Integer)pageContext.getAttribute("pkgDimensionIdTmp");
                          int dimensionId=(Integer)pageContext.getAttribute("dimensionIdTmp");
                          String dimensionDefault=(String)pageContext.getAttribute("dimensionDefaultTmp");
                          String dimensionIdStr=(String)pageContext.getAttribute("dimensionIdStrTmp");
                          String dimensionName=(String)pageContext.getAttribute("dimensionNameTmp");
                          int dimensionCount=(Integer)pageContext.getAttribute("dimensionCountTmp");
                  %>
                  <select name="dimensionName<%=packCount%>" id="dimensionNameID<%=packCount%>" class="inputsPackage" <%=voidFlagDisable%>>
                  <%
                  if((pkgDimensionId == dimensionId) && dimensionDefault.equalsIgnoreCase("N")){
                
                  %>
                    <option value="<%=dimensionIdStr%>" selected="selected"> <%=dimensionName%> </option>
                  <%
                  }
                  else if(dimensionDefault.equalsIgnoreCase("Y") )
                  {
                    if(((pkgDimensionId == dimensionId) && (pkgDimensionId != 0)) || ((pkgDimensionId != dimensionId) && (pkgDimensionId == 0)))
                    {
                  %>
                    <option value="<%=dimensionIdStr%>" selected="selected"> <%=dimensionName%>  </option>
                  <%
                    }else{
                  %>
                    <option value="<%=dimensionIdStr%>">  <%=dimensionName%> </option>
                  <%
                    }
                  }else
                  {
                    if((dimensionName.equalsIgnoreCase("Other") ) && (dimensionCount==2))
                    {               
                  %>
                    <option value="<%=dimensionIdStr%>" selected="selected">  <%=dimensionName%>  </option>
                  <%
                    }
                    else
                    {                             
                  %>
                    <option value="<%=dimensionIdStr%>"> <%=dimensionName%>  </option>
                  <%
                    }
                  } 
                  %>
                  </select>
                </s:iterator>
                <s:hidden name="defaultDimValue" id="defaultDimValueID" value="%{#defaultDimensionIdStr}"/>
                <s:hidden name="defaultDimText" id="defaultDimTextID"  value="%{#defaultDimensionName}"/>
                <s:hidden name="defaultDimeName%{#packCount}"  id="defaultDimeNameID%{#packCount}"  value="%{#defaultDimeName}"/>
                <%
                     int packCount=(Integer)pageContext.getAttribute("packCountTmp");
                     String voidFlagDisable=(String)pageContext.getAttribute("voidFlagDisableTmp");
                %>
                <input type="BUTTON" name="dimButton<%=packCount%>" id="dimButtonID<%=packCount%>" value="^" class="inputsPackage" style="width:20px" onclick="openDimPopup('<%=packCount%>')" <%=voidFlagDisable%>/>
              </td>
              <s:hidden name="dimValue%{#packCount}" id="dimValueID%{#packCount}" value="%{''}"/>
              <s:hidden name="units%{#packCount}" id="unitsID%{#packCount}" value="%{''}"/>
              <s:hidden name="dimensionNameHidden%{#packCount}"  id="dimensionNameHiddenID%{#packCount}" value="%{''}"/>
              <s:hidden name="dimensionValueHidden%{#packCount}" id="dimensionValueHiddenID%{#packCount}" value="%{''}"/>
              <td width="3%"  align="center"> 
                <s:textfield name="packageDeclaredValue%{#packCount}"  id="packageDeclaredValueID%{#packCount}"  cssClass="inputsPackage" value="%{#packageDeclaredValue}"  size="8" maxlength="10"  onchange="javascript:packageDeclaredValuefn('%{#packCount}')"  readonly="%{#shipReadOnlyFlag}" />
                <s:hidden name="chCOD%{#packCount}" id="chCODID%{#packCount}"  value="%{#cod}"/>
              </td>
              <td width="5%"  align="center">
                <s:hidden  name="declaredCurrCode%{#packCount}" id="declaredCurrCodeID%{#packCount}" cssClass="inputsPackage" />
             <label style="align:center"> USD </label>
                <s:hidden name="codAmt%{#packCount}" id="codAmtID%{#packCount}"  value="%{#codAmtstr}"/>
              </td>
              <s:hidden name="halPhone%{#packCount}" id="halPhoneID%{#packCount}" value="%{#halPhone}"/>
              <s:hidden name="halAddrLine1%{#packCount}" id="halAddrLine1ID%{#packCount}" value="%{#halLine1}"/>
              <s:hidden name="halAddrLine2%{#packCount}"  id="halAddrLine2ID%{#packCount}" value="%{#halLine2}"/>
              <s:hidden name="halCity%{#packCount}" id="halCityID%{#packCount}"  value="%{#halCity}"/>
              <s:hidden name="halState%{#packCount}" id="halStateID%{#packCount}"  value="%{#halState}"/>
              <s:hidden name="halZip%{#packCount}" id="halZipID%{#packCount}" value="%{#halZip}"/>
              <s:hidden name="holdAtLocation%{#packCount}" id="holdAtLocationID%{#packCount}" value="%{#hal}"/>
			  
			  <s:hidden name="chDryIce%{#packCount}" id="chDryIceID%{#packCount}" value="%{#dryIceChk}"/>
			  <s:hidden name="dryIceWeight%{#packCount}" id="dryIceWeightID%{#packCount}" value="%{#dryIceWeight}"/>
              <s:hidden name="dryIceUnits%{#packCount}" id="dryIceUnitsID%{#packCount}" value="%{#dryIceUnits}"/>
              
              <s:hidden name="HazMatFlag%{#packCount}" id="HazMatFlagID%{#packCount}" value="%{#HazMatFlag}"/>
              <s:hidden name="HazMatType%{#packCount}" id="HazMatTypeID%{#packCount}" value="%{#HazMatType}"/>
                <s:hidden name="HazMatCount%{#packCount}" id="HazMatCountID%{#packCount}" value="%{#hazmatListCount}"/>
              <s:hidden name="HazMatClass%{#packCount}" id="HazMatClassID%{#packCount}" value="%{#HazMatClass}"/>
              <s:hidden name="HazMatCharges%{#packCount}" id="HazMatChargesID%{#packCount}" value="%{#HazMatCharges}"/>
              <s:hidden name="HazMatQty%{#packCount}" id="HazMatQtyID%{#packCount}" value="%{#HazMatQty}"/>
              <s:hidden name="HazMatUnit%{#packCount}" id="HazMatUnitID%{#packCount}" value="%{#HazMatUnit}"/>
              <s:hidden name="HazMatIdNo%{#packCount}" id="HazMatIdNoID%{#packCount}" value="%{#HazMatIdNo}"/>
              <s:hidden name="HazMatPkgGroup%{#packCount}" id="HazMatPkgGroupID%{#packCount}" value="%{#HazMatPkgGroup}"/>
              <s:hidden name="HazMatDOTLabel%{#packCount}" id="HazMatDOTLabelID%{#packCount}" value="%{#HazMatDOTLabel}"/>
              <s:hidden name="HazMatEmerContactNo%{#packCount}" id="HazMatEmerContactNoID%{#packCount}" value="%{#HazMatEmerContactNo}"/>
              <s:hidden name="HazMatEmerContactName%{#packCount}" id="HazMatEmerContactNameID%{#packCount}" value="%{#HazMatEmerContactName}"/>
              <s:hidden name="HazMatId%{#packCount}" id="HazMatIdID%{#packCount}"  value="%{#HazMatId}"/>
              <s:hidden name="HazMatPackagingCnt%{#packCount}" id="HazMatPackagingCntID%{#packCount}" value="%{#HazmatPkgingCnt}"/>
              <s:hidden name="HazMatPackagingUnits%{#packCount}" id="HazMatPackagingUnitsID%{#packCount}" value="%{#HazmatPkgingUnits}"/>
              <s:hidden name="HazMatPackInstructions%{#packCount}" id="HazMatPackInstructionsID%{#packCount}" value="%{#HazMatPackInstructions}"/>
              <s:hidden name="HazMatTechnicalName%{#packCount}" id="HazMatTechnicalNameID%{#packCount}" value="%{#hazmatTechnicalName}"/>
              <s:hidden name="HazMatSignatureName%{#packCount}" id="HazMatSignatureNameID%{#packCount}" value="%{#hazmatSignatureName}"/>
              <s:hidden name="trackingNumber%{#packCount}" id="trackingNumberID%{#packCount}" value="%{#TrackingNumber}"/>
              <s:if test="%{#aascCarrierProfileOptionsInfo == null || #aascCarrierProfileOptionsInfo == ''}">
                <s:hidden name="signatureOption%{#packCount}" id="signatureOptionID%{#packCount}" value="%{#signatureOption}"/>
                <s:hidden name="returnShipment%{#packCount}" id="returnShipmentID%{#packCount}" value="%{#returnShipment}"/>
                <s:hidden name="holdAtLocation%{#packCount}" id="holdAtLocationID%{#packCount}" value="%{#hal}"/>
              </s:if>
                <s:hidden name="returnShipment%{#packCount}" id="returnShipmentID%{#packCount}" value="%{#returnShipment}"/>
              <s:hidden name="returnDescription%{#packCount}" id="returnDescriptionID%{#packCount}" value="%{#rtnDesc}"/>
               
              <s:hidden name="PackageSurcharge%{#packCount}" id="PackageSurchargeID%{#packCount}" value="%{#PackageSurcharge}"/>
              <s:hidden name="PackageShipmentCost%{#packCount}" id="PackageShipmentCostID%{#packCount}" value="%{#PackageShipmentCost}"/>
             
              <jsp:include page="/aascShipmentPackageDetailsHelp.jsp">
                  <jsp:param name="aascShipmentHeaderInfo" value="<%=aascShipmentHeaderInfo%>"/>
                
              </jsp:include>
             
              <s:if test="%{#aascCarrierProfileOptionsInfo == null || #aascCarrierProfileOptionsInfo == ''}">
                <s:hidden name="AdditionalHandling%{#packCount}" id="AdditionalHandlingID%{#packCount}"  value="%{#AdditionalHandling}"/>
                <s:hidden name="LargePackage%{#packCount}" id="LargePackageID%{#packCount}"  value="%{#LargePackage}"/>
                  <s:hidden name="pkging%{#packCount}" id="pkgingID%{#packCount}"  value="%{#pkging}"/>
                  
                <s:hidden name="delConfirm%{#packCount}" id="delConfirmID%{#packCount}" value="%{#delConfirm}"/>
                <s:hidden name="codType%{#packCount}" id="codTypeID%{#packCount}" value="%{#codType}"/>
                <s:hidden name="codFundsCode%{#packCount}" id="codFundsCodeID%{#packCount}" value="%{#codFundsCode}"/>
                <s:hidden name="codCurrCode%{#packCount}" id="codCurrCodeID%{#packCount}" value="%{#codCurrCode}"/>
                <s:hidden name="chCOD%{#packCount}" id="chCODID%{#packCount}" value="%{#cod}"/>
              </s:if>
              <s:hidden name="upsSurCharge%{#packCount}" id="upsSurChargeID%{#packCount}" value="%{#PackageSurcharge}"/>
              <s:hidden name="packageSaveCheck%{#packCount}" id="packageSaveCheckID%{#packCount}"/>
              <s:hidden name="rtnPayMethodCode%{#packCount}" id="rtnPayMethodCodeID%{#packCount}" value="%{#rtnPayMethodCode}"/>
              <s:hidden name="rtnDeclaredValue%{#packCount}" id="rtnDeclaredValueID%{#packCount}" value="%{#rtnDeclaredValue}"/>
              <s:set name="selectedCarrier" value="%{#attr.selectedCarrier}"/>
              <s:if test='%{#selectedCarrier == 110 || #selectedCarrier == 111 || (#selectedCarrier == 100 && connectionModeUPS == "UPS Direct") || (#selectedCarrier == 100 && connectionModeUPS == "ShipExec")}'>
                <td width="5%" >
                  <s:a href="#" onclick="openPackPopup('%{#packCount}')"> <img name="packOpt%{#packCount}" id="packOptId%{#packCount}" alt="" src="images/PK.png" align="center" width="40px" height="30px" border="0"> </img> </s:a>
                </td>
              </s:if>
              <s:else>
                <td width="5%" >
                  <s:a href="#" onclick="openPackPopup('%{#packCount}')"> <img name="packOpt%{#packCount}" id="packOptId%{#packCount}" alt="" src="images/PK.png" align="left" border="0"></img></s:a>
                </td>
              </s:else>
              </s:else>
          </c:catch>   
            <s:if test="%{#exception11 != null}">
              <s:property value="#exception11"/>
            </s:if>
        </s:iterator>
        <s:hidden name="totalPackageDeclaredValue" value="%{#totalPackageDeclaredValue}"/>
        <s:hidden name="shippenQuantity" value="%{#packCount}"/>
    <tr valign="middle" align="right">
  
     </tr>
        <tr valign="middle" align="right">
            
        <td colspan="2" >
         <label style="font-weight:bold; font-size:12px"> Total Weight </label>
        </td>
        <td height="43%"   align="center">

      <s:textfield name="packageWeight" disabled="%{#disableFlagTmp}" cssClass="inputsPackage" id="totalWeightText" value="%{#aascShipmentHeaderInfo.packageWeight}"   size="8" maxlength="10" readonly="true" />
        </td>
        <td height="43%" colspan="6" > <span style="font-weight: bold;"> <s:property value="getText('NumberofPackages')"/>
        
        <s:textfield name="txtPacCnt" cssClass="inputsPackage" id="txtPacCnt" value="1" size="3" maxlength="3" disabled="%{#firstDisable}"/>
          </span>
          <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "N" || #aascShipmentHeaderInfo.shipFlag == "n"}'>

             <input name="AddButton" type="image" src="images/addimg1.png" title="Add Package" alt="Add Row" id="AddButton" onclick="insRow(); return false;" align="top" width="25px" />
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <s:a href="javascript:delRow()">
             <img src="images/deleteimg.png" title="Delete Package" name="RemoveButton" width="25px" alt="Delete Row" border="0" align="middle" id="RemoveButton" ></img>
           </s:a>
          <s:a href="javascript:clearRow()">
         <% try{ %>
            <img src="images/clear.png" title="Delete all Packages" alt="Delete all Rows" name="ClearButton" width="25px" border="0" align="middle" id="ClearButton"   ></img>
         <%}catch( Exception e){
                logger.info(e.getMessage());  
            }
            %>
          </s:a>
        </s:if>
        <s:else>
            <img name="AddButton" src="images/addimgDisable.png" class="buttonDataCell" style="width:25px"  id="AddButton" align="middle" alt=""/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <img name="RemoveButton" src="images/deleteimgDisable.png" class="buttonDataCell" style="width:25px"  id="RemoveButton" align="middle" alt=""/>
            <img name="ClearButton" src="images/clearDisable.png" class="buttonDataCell" style="width:25px"  id="ClearButton" align="middle" alt=""/>
        </s:else>
      </td>
    </tr>
       <s:hidden name="countPackets" id="countPacketsID" value="%{#packCount}"/>
       <s:hidden name="HidePac" id="HidePacID" value="%{#packCount}"/>
       <s:hidden name="HidePacAdd" id="HidePacAddID" value="%{#packCount+1}"/>
       <s:hidden name="oldPackCount" id="oldPackCountID" value="%{#oldPackCount}"/>
       <s:hidden name="HideTotalWeigth" id="HideTotalWeigthID" value="%{#aascShipmentHeaderInfo.packageWeight}"/>
       <s:hidden name="shipmentStatusFlag" id="shipmentStatusFlagID"  value="%{'N'}"/>
       <s:hidden name="pacakgeId" id="pacakgeIdID" value="%{1}"/>
       <s:hidden name="RemoveButtonHidden" id="RemoveButtonHiddenID"/>
       <%

try{
       String readOnlyFlagNew = shipmentFlags.getReadOnlyFlag();
       pageContext.setAttribute("readOnlyFlagNew",readOnlyFlagNew);
    }catch(Exception e ){
logger.info("Exp :::"+e.getMessage());
    }
       %>
       <s:hidden name="isItemShipped" id="isItemShippedID" value="%{#attr.readOnlyFlagNew}"/>
       <s:hidden name="defaultCarrierSessionValuesFlagHidden" id="defaultCarrierSessionValuesFlagHiddenID" value="%{#defaultCarrierSessionValuesFlag}"/>
       <s:hidden name="errorKey" id="errorKeyID" value="%{#key1}"/>
       <s:hidden name="flagShip1" id="flagShip1" value="%{#aascShipmentHeaderInfo.shipFlag}"/>
     </table>
