<%       
/*==========================================================================+
|  DESCRIPTION                                                              |
|    JSP aascShipmentGetRates for hidden fields of the Shipment  related    | 
|    page                                                                   |
|    Author Suman Gunda                                                     |
|    Modified                                                               |
|    Version   1.1                                                          |                                                                            
|    Creation  10/03/2015  
|      
|    History :                                                              |
    11/03/2015  Suman G     Removed unnecessary hidden fields
    13/03/2015  Suman G     Added field for Ajay Demo.
    24/03/2015  Suman G     Moved hidden fields for page too large issue in aascShiipment.jsp
    24/04/2015  Suman G     Added code to fix the issue for MPS functionality.
    28/04/2015  Y Pradeep   Added hidden fields related to shipFrom fields for avoiding page too large issue in aascShiipment.jsp
    27/05/2015  Suman G     Added avFlag, fsFlag hidden fields to fix #2936
    03/06/2015  Y Pradeep   Added code from  aascShipment.jsp to avoide page too large issue.
    13/07/2015  Y Pradeep   Added hidden variables.
    20/07/2015  Suman G     Added code for implement Email Notification.
    04/08/2015  Suman G     Modified code for issue #3294
    05/11/2015  Suman G     Added two hidden fields for Get Rates functionality of DHL and Stamps.
    27/11/2015  Mahesh V    Added code for UPS and FedEx Recepient and Third party development
    02/12/2015  Suman G     Added code to edit the ship to fields based on given profile options after error case
    16/12/2015  Suman G     Added hidden fields to implement get rates feature for DHL
+===========================================================================*/%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
 <%@ page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@ page import="com.aasc.erp.util.*"%>
<%@page import="java.util.logging.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
        
        <s:set name="aascShipmentHeaderInfo" value="%{#attr.aascShipmentHeaderInfo}"/>
        <s:set name="aascCarrierProfileOptionsInfo" value="{attr.aascCarrierProfileOptionsInfo}"/>
        <s:set name="aascProfileOptionsInfo" value="{attr.aascProfileOptionsInfo}"/>
        
        
        <!--   Start for Get Rates              -->
        <s:set name="ratesFromGetRates" id="ratesFromGetRatesId" value="%{#attr.ratesFromGetRates}" />
        <s:hidden name="decision" id="decisionId" />
        
        
        <s:hidden name="ProfileDropOffTypeHidden" id="ProfileDropOffTypeHiddenID" value="%{#aascCarrierProfileOptionsInfo.dropOffType}"/>
        <s:hidden name="ProfileCarrierPaymethodHidden" id="ProfileCarrierPaymethodHiddenID" value="%{#aascCarrierProfileOptionsInfo.carrierPaymentTerms}"/>
        <s:hidden name="ProfileCarrierPackagingHidden" id="ProfileCarrierPackagingHiddenID" value="%{#aascCarrierProfileOptionsInfo.packaging}"/>
        <s:hidden name="sigOptionsHidden" id="sigOptionsHiddenID" value="%{#aascCarrierProfileOptionsInfo.signatureOptions}"/>
        <s:hidden name="CODFlagHidden" id="CODFlagHiddenID" value="%{#aascCarrierProfileOptionsInfo.codFlag}"/>
        <s:hidden name="HALFlagHidden" id="HALFlagHiddenID" value="%{#aascCarrierProfileOptionsInfo.halFlag}"/>
        <s:hidden name="ReturnFlagHidden" id="ReturnFlagHiddenID" value="%{#aascCarrierProfileOptionsInfo.returnShipmentFlag}"/>
        <s:hidden name="CODFundsCodeHidden" id="CODFundsCodeHiddenID" value="%{#aascCarrierProfileOptionsInfo.codFundsCode}"/>
        <s:hidden name="CODCurrCodeHidden" id="CODCurrCodeHiddenID" value="%{#aascCarrierProfileOptionsInfo.codCurrCode}"/>
        <s:hidden name="CODTypeHidden" id="CODTypeHiddenID" value="%{#aascCarrierProfileOptionsInfo.codType}"/>
        <s:hidden name="ProfileHazmatFlagHidden" id="ProfileHazmatFlagHiddenID" value="%{#aascCarrierProfileOptionsInfo.hazmatFlag}"/>
        
        <s:hidden name="fromAdressLine1" id="fromAdressLine1Id" />
        <s:hidden name="fromAdressLine2" id="fromAdressLine2Id" />
        <s:hidden name="fromCity" id="fromCityId" />
        <s:hidden name="fromState" id="fromStateId" />
        <s:hidden name="fromZipCode" id="fromZipCodeId" />
        <s:hidden name="fromCountryCode" id="fromCountryCodeId" />
        
        <s:hidden name="toAdressLine1" id="toAdressLine1Id" />
        <s:hidden name="toAdressLine2" id="toAdressLine2Id" />
        <s:hidden name="toCity" id="toCityId" />
        <s:hidden name="toState" id="toStateId" />
        <s:hidden name="toZipCode" id="toZipCodeId" />
        <s:hidden name="toCountryCode" id="toCountryCodeId" />
        
        <s:hidden name="UPSCheckBox" id="UPSCheckBoxId" />
        <s:hidden name="FedExCheckBox" id="FedExCheckBoxId" />
        <s:hidden name="DHLCheckBox" id="DHLCheckBoxId" />
        <s:hidden name="StampsCheckBox" id="StampsCheckBoxId" />
        
        <s:hidden name="shipDate" id="shipmentDateId" />
        <s:hidden name="countPacks" id="countPacksId" />
        <s:hidden name="decision" id="decisionId" />
        <s:hidden name="getRateShipMethod" id="getRateShipMethodId" />
        
        <s:set name="index" value="%{1}"/> 
        <%
            
            String pkCountStr = request.getAttribute("packCount").toString();
            int pkCount = 0;
            try{
            
            pkCount = Integer.parseInt(pkCountStr);
            
            }catch(Exception e){
                e.printStackTrace();
                
            }
            int index = 1;
            for(;index<=pkCount;index++)
            {
        %>
        
        <s:hidden name="dimUOM%{#index}" id="dimUOMId%{#index}" />
        <s:hidden name="dimValueFreight%{#index}" id="dimValueId%{#index}" />
        <s:hidden name="weightUOM%{#index}" id="weightUOMId%{#index}" />
        <s:hidden name="weightValue%{#index}" id="weightValueId%{#index}" />
        <s:set name="index" value="%{#index+1}"/>
        <%
            }
        %>       
        <s:hidden name="freightShopDecision" id="freightShopDecisionId" />
        
        
        <s:hidden name="checkSubmit" id="checkSubmitId" /> <!-- For Ajay demo  -->
        
        <!--   End for Get Rates              -->
        
        <input type="HIDDEN" name="chkSatShipmentHidden" id="chkSatShipmentHidden"/>
        
        <s:hidden name="shipMethodValue" id="shipMethodValueId"/>
        <s:hidden name="intlFlag" id="intlFlag"/>
        <s:hidden name="intlWeightValue"/>
        <s:hidden name="shipMethodText"/>
        
        <s:hidden name="receipentAccNo" id = "receipentAccNoId"/>
        <s:hidden name="thirdPartyAccNo" id = "thirdPartyAccNoId"/>
                
        <s:hidden name="ajaxShipMethod" id="ajaxShipMethod"/>
        <s:hidden name="ajaxCCodeCServiceLevel" id="ajaxCCodeCServiceLevel"/>
        <s:hidden name="ajaxCarrierCode" id="ajaxCarrierCode"/>
        <s:hidden name="ajaxCarrierMode" id="ajaxCarrierMode"/>
        <s:hidden name="ajaxcarrierservicelevel" id="ajaxcarrierservicelevel"/>
        <s:hidden name="ajaxDropOffType" id="ajaxDropOffType"/>
        <s:hidden name="ajaxPackaging" id="ajaxPackaging"/>
        <s:hidden name="ajaxCarrierPaymentTerms" id="ajaxCarrierPaymentTerms"/>
        
        <s:hidden name="ajaxUpsServiceLevelCode" id="ajaxUpsServiceLevelCode"/>
        <s:hidden name="ajaxDimensionReq" id="ajaxDimensionReq"/>
        <s:hidden name="ajaxMaxWeight" id="ajaxMaxWeight"/>
        <s:hidden name="ajaxMaxLength" id="ajaxMaxLength"/>
        <s:hidden name="ajaxMinLength" id="ajaxMinLength"/>
        <s:hidden name="ajaxMaxWidth" id="ajaxMaxWidth"/>
        <s:hidden name="ajaxMinWidth" id="ajaxMinWidth"/>
        <s:hidden name="ajaxMaxHeight" id="ajaxMaxHeight"/>
        <s:hidden name="ajaxMinHeight" id="ajaxMinHeight"/>
        
        <s:hidden name="ltlHazmatShippingSupport" id="ltlHazmatShippingSupport"/>
        <s:hidden name="hazmatSupportFlag" id="hazmatSupportFlag"/>
        
        <s:hidden name="shipFromLocation" id="shipFromLocation" value="%{#aascShipmentHeaderInfo.shipFromLocation}"/>
        <s:hidden name="shipFromAddressLine1" id="shipFromAddressLine1" value="%{#aascShipmentHeaderInfo.shipFromAddressLine1}"/>
        <s:hidden name="shipFromAddressLine2" id="shipFromAddressLine2" value="%{#aascShipmentHeaderInfo.shipFromAddressLine2}"/>
        <s:hidden name="shipFromCity" id="shipFromCity" value="%{#aascShipmentHeaderInfo.shipFromCity}"/>
        <s:hidden name="shipFromState" id="shipFromState" value="%{#aascShipmentHeaderInfo.shipFromState}"/>
        <s:hidden name="shipFromPostalCode" id="shipFromPostalCode" value="%{#aascShipmentHeaderInfo.shipFromPostalCode}"/>
        <s:hidden name="shipFromCountry" id="shipFromCountry" value="%{#aascShipmentHeaderInfo.shipFromCountry}"/>
        <s:hidden name="shipFromPhoneNumber1" id="shipFromPhoneNumber1" value="%{#aascShipmentHeaderInfo.shipFromPhoneNumber1}"/>
        <s:hidden name="shipFromPhoneNumber2" id="shipFromPhoneNumber2" value="%{#aascShipmentHeaderInfo.shipFromPhoneNumber2}"/>
        
        <s:hidden name="avFlag" id="avFlag" value="%{#aascShipmentHeaderInfo.avFlag}" />
        <s:hidden name="fsFlag" id="fsFlag" value="%{#aascShipmentHeaderInfo.fsFlag}" />
        
        <s:hidden name="submitFlag" id="submitFlag" />
        
        <!--// Email Notification related fields -->
        <s:hidden name="emailNotificationFlag" id="emailNotificationFlagId" value="%{#aascShipmentHeaderInfo.emailNotificationFlag}" />
       <!-- <s:hidden name="senderMail" id="senderMailId" value="%{#aascShipmentHeaderInfo.senderMail}" />-->
        <s:hidden name="reference1Flag" id="reference1FlagId" value="%{#aascShipmentHeaderInfo.reference1Flag}" />
        <s:hidden name="reference2Flag" id="reference2FlagId" value="%{#aascShipmentHeaderInfo.reference2Flag}" />
        <s:hidden name="shipNotificationFlag" id="shipNotificationFlagId"  value="%{#aascShipmentHeaderInfo.shipNotificationFlag}"/>
        <s:hidden name="exceptionNotification" id="exceptionNotificationId" value="%{#aascShipmentHeaderInfo.exceptionNotification}" />
        <s:hidden name="deliveryNotification" id="deliveryNotificationId" value="%{#aascShipmentHeaderInfo.deliveryNotification}" />
        <s:hidden name="formatType" id="formatTypeId" value="%{#aascShipmentHeaderInfo.formatType}" />
        <s:hidden name="salesOrderNumber" id="salesOrderNumberId" value="%{#aascShipmentHeaderInfo.salesOrderNumber}" />
        <s:hidden name="emailCustomerName" id="emailCustomerNameId" value="%{#aascShipmentHeaderInfo.emailCustomerName}" />
        <s:hidden name="deliveryItemNumber" id="deliveryItemNumberId" value="%{#aascShipmentHeaderInfo.deliveryItemNumber}" />
        <!--Mahesh added below 2 fields for FedEx Recepient-->
        <s:hidden name="rcCompanyName" id="rcCompanyNameId" value="%{#aascShipmentHeaderInfo.recCompanyName}" />
        <s:hidden name="rcAcctPostalCode" id="rcAcctPostalCodeId" value="%{#aascShipmentHeaderInfo.recPostalCode}" />
        
        <s:hidden name="shipToCityEditFlag" id="shipToCityEditFlagId" value="%{#attr.shipToCityEditFlag}" />
        <s:hidden name="shipToStateEditFlag" id="shipToStateEditFlagId" value="%{#attr.shipToStateEditFlag}" />
        <s:hidden name="shipToCountryEditFlag" id="shipToCountryEditFlagId" value="%{#attr.shipToCountryEditFlag}" />
        <s:hidden name="shipToZipEditFlag" id="shipToZipEditFlagId" value="%{#attr.shipToZipEditFlag}" />
<!-- For Get Rates  DHL-->
        <s:hidden name="dhlDeclaredCurrency" id="dhlDeclaredCurrencyId" />
        <s:hidden name="dhlDeclaredValue" id="dhlDeclaredValueId" />
        <s:hidden name="isDutiable" id="isDutiableId" />

        <s:hidden name="checkValidation" id="checkValidationID" value="" />  <!-- Added by Jagadish for tolerance and balance check enhancement.-->
        <s:hidden name="portName" id="portName"/>
        <s:hidden name="voidStatusFlag" id="voidStatusFlagId" value="%{''}"/>
        <s:hidden name = "numOfVoidedPackages" id="numOfVoidedPackages" value="%{''}"/>
        <s:hidden name = "stampsMode" id="stampsMode" value="%{''}"/>
        <!--Start of code for weighing scale -->
        <s:hidden name="weightClassInMain" id="weightClassInMainId" />
        <s:hidden name="vendorIdInMain" id="vendorIdInMainId" />
        <s:hidden name="productId1InMain" id="productId1InMainId" />
        <s:hidden name="productId2InMain" id="productId2InMainId" />
        <s:hidden name="wsTopologyInMain" id="wsTopologyInMainId" />
        <s:hidden name="wighingScaleFlag" id="wighingScaleFlagId" />
        
        <s:hidden name="carrierNameHide" id="carrierNameHideId" value="%{''}"/>
        <s:hidden name="CarrierAcHidden" id="CarrierAcHiddenId" value="%{''}"/>
        <s:hidden name="wayBill"   id="wayBillId"  cssClass="inputFields"  value="%{#aascShipmentHeaderInfo.wayBill}"/>
        <s:hidden cssClass="dispalyDBFields" name="orderNum" value="%{#aascShipmentHeaderInfo.orderNumber}"/>
        <s:hidden name="importFlagId" id="importFlagId"  value="%{#aascShipmentHeaderInfo.importFlag}"/>
        <s:hidden name="orderNumberHid" id="orderNumberHidID" value="%{#aascShipmentHeaderInfo.orderNumber}"/>
        
    <s:hidden name="internationalFlag" id="internationalFlagId" value="%{#aascProfileOptionsInfo.international}"/>
    <s:hidden name="referenceFlag1" id="referenceFlag1Id" value="%{#aascProfileOptionsInfo.reference1}" />
    <s:hidden name="referenceFlag2" id="referenceFlag2Id" value="%{#aascProfileOptionsInfo.reference1}" />
    <s:hidden name="defaultPayMethod" id="defaultPayMethodId" value="%{#aascProfileOptionsInfo.defaultPayMethod}" />
    <s:hidden name="maskAccountCheck"  id="maskAccountCheckID"  value="%{#aascProfileOptionsInfo.maskAccount}"/> 
    <s:hidden name="carrierAccountNumberHid"  id="carrierAccountNumberHidId"  value="%{#aascShipmentHeaderInfo.carrierAccountNumber}" />
    <s:hidden name="carrierAccNumHid"  id="carrierAccNumHidId"  value="%{#aascShipmentHeaderInfo.maskCarrierAccountNumber}" />
    <s:hidden name="ajaxAfterShipCarrAccNumber"  id="ajaxAfterShipCarrAccNumber"  value="%{#aascShipmentHeaderInfo.carrierAccountNumber}"/>
                  
    <s:hidden name = "labelFormat" id="labelFormat" value="%{#aascShipmentHeaderInfo.labelFormat}"/>      
    <s:hidden name="flagShip" id="flagShipID" value="%{#aascShipmentHeaderInfo.shipFlag}"/>
    <s:hidden name="packaging" id="packaging" value="%{#aascShipmentHeaderInfo.packaging}"/>
    <s:hidden name="dropOfType" id="dropOfType" value="%{#aascShipmentHeaderInfo.dropOfType}"/>
    <s:hidden name="carrierNameHidden" id="carrierNameHidden" value="%{#aascShipmentHeaderInfo.carrierName}" />
    <s:hidden name="kDropOffType" id="kDropOffTypeID" value="%{#aascShipmentHeaderInfo.dropOfType}"/>
    <s:hidden name="acctPostalCode" id="acctPostalCode" value="%{#aascShipmentHeaderInfo.acctPostalCode}"/>
    <s:hidden name="countryCodeVal" id="countryCodeVal" value="%{#aascShipmentHeaderInfo.countrySymbol}"/>
    <s:hidden name="tpCompanyName" id="tpCompanyNameID" value="%{#aascShipmentHeaderInfo.tpCompanyName}"/>
    <s:hidden name="tpAddress" id="tpAddressID" value="%{#aascShipmentHeaderInfo.tpAddress}"/>
    <s:hidden name="tpState" id="tpStateID" value="%{#aascShipmentHeaderInfo.tpState}"/>
    <s:hidden name="tpCity" id="tpCityID" value="%{#aascShipmentHeaderInfo.tpCity}"/>
    <s:hidden name="tpPostalCode" id="tpPostalCodeID" value="%{#aascShipmentHeaderInfo.tpPostalCode}"/>
    <s:hidden name="tpCountrySymbol" id="tpCountrySymbolID" value="%{#aascShipmentHeaderInfo.tpCountrySymbol}"/>
    <s:hidden name="tpDDU" id="tpDDUID" value="%{#aascShipmentHeaderInfo.tpDDUCheck}"/>
              
