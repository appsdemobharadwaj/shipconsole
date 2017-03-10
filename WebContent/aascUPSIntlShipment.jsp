
<%/*==============================================================================================+
|  DESCRIPTION                                                                                    |
|    JSP to Save UPS Direct International Shipment options                                        |
|    Author Y Pradeep                                                                             |
|    Version   1                                                                                  |                                                                              
|    Creation 28-JAN-2015                                                                         |
History:
        28/01/2015   Y Pradeep    Added this file for Fedex Indernation page.
        02/02/2015   Y Pradeep    Modified code to remove Home and SignOut buttons in popup window.
        05/02/2015   Y Pradeep    Moved DAO calls from JSP page to Action class and modified code as required.
        11/02/2015   Y Pradeep    Modified code to reduce DAO calls from JSP page. Modified code to getlables from Property file.
        16/02/2015   Y Pradeep    Modified code to generate order number on click og Ship button in Shipping page.
        10/03/2015   Y Pradeep    Added alt attribute for image tags.
        12/03/2014   Y Pradeep    Modified code to print display Additional Comments in the text area. Bug #2678
        12/03/2015   Jagadish     Added code for new UI changes.
        16/04/2015   Y Pradeep    Added code to display new calander UI for invoice date and removed readonly to that field. Bug #2861.
        17/04/2015   Suman G      Added session related code to fix #2743.
        23/04/2015   Y Pradeep    Replaced shipexec logo to apps associates logo.
        11/06/2015   Suman G      Removed fevicon to fix #2992
        23/06/2015   Rakesh       Applied the fix for #3054
        06/07/2015   Suman G      Applied fix for Padmavathi's #3150
        07/07/2015   Rakesh K     Modified screen layouts to align in Tablet.
        24/07/2015   Rakesh K     Modified Code to work application in offline.
        04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
        06/08/2015  Rakesh K    Modified Code for #3302.
        13/08/2015  Rakesh K    Modified Code for #3329.
        26/08/2015  Rakesh K       Added code to solve 3496.
        27/08/2015  N Srisha    Added Id's for Automation testing
+==================================================================================================*/%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page errorPage="aascShipError.jsp" %>
<%@ page import="java.util.logging.*"%>
<%@ page import="java.util.*" %>
<%@ page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@ page import="com.aasc.erp.util.*"%>
<%@ page import="oracle.jdbc.driver.*"%>
<%@ page import="com.aasc.erp.carrier.*" %>

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
    
     <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
       <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link href="kendo.common-material.min.css" rel="stylesheet" />
        <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
        <script src="kendo.all.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>-->
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:property value="getText('UPSInternationalShipments')"/></title>
        
        <link type="text/css" rel="stylesheet" href="aasc_ss.css">
        <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
        <script type="text/javascript" src="aascUPSIntlShipment_js.js"></script>
        <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
        
        
        
        <script type="text/javascript">
            $(document).ready(function() {
                // create DatePicker from input HTML element
                $("#InvoiceDateTextBoxID").kendoDatePicker({
                parseFormats:["yyyy-MM-dd"],
                format: "yyyy-MM-dd"
            }).data("kendoDatePicker");
    
            });
        </script>
        <style type="text/css">
            form{height:100%}
        </style>
        
    </head>
    <body onload="load();"  style="background-color:#ADADAD;">
    <div  class="container-fluid" style="background-color:#ADADAD;width:100%;">
    <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
        %>
        
    <%
        String comment = (String)session.getAttribute("comments");
        session.removeAttribute("comments");
        String strY ="";
        String strN ="";
        try{
            String commentStr = comment.substring(0,1);
        }catch(Exception e)
        {
            comment = "";
        }
    %>
    <br/><br/>
    <table width="100%"  align="center" style="padding:0px 0px 0px 0px;">
        <tr>
            <td class="international" align="center" id="displayMessage">
                <font color="Green"><%=comment%> </font>
            </td>
        </tr>
    </table>
    <%!
        static Logger logger = AascLogger.getLogger("aascUPSIntlShipments.jsp");
    %>
    <%!
        /** This method can replace the null values with nullString.
        * @return String that is ""
        * @param obj of type Object
        */   
        String nullStrToSpc(Object obj) {
        String spcStr = "";
            if (obj == null) {
                return spcStr;
            } else {
                return obj.toString();
            }
        }
    %>
    <s:set name="aascIntlDataBean" value="AascIntlDataBean"/>
    <s:set name="intlInfoBean" value="%{#aascIntlDataBean.intlInfoBean}" />
    <s:set name="intlHeaderBean" value="%{#aascIntlDataBean.intlHeaderBean}" />
    <s:set name="commInfoBean" value="%{#aascIntlDataBean.aascCommodityBean}"/>
    <s:set name="countryHashMap" value="%{#aascIntlDataBean.countryList}"/>
    <s:set name="UOMHashMap" value="%{#aascIntlDataBean.UOM}"/>
    <s:set name="preferenceCriteriaHashMap" value="%{#aascIntlDataBean.preferenceCriteria}"/>
    <s:set name="producerHashMap" value="%{#aascIntlDataBean.producer}"/>
    <s:set name="rvcCalMthdHashMap" value="%{#aascIntlDataBean.rvcCalMthd}"/>
    <s:set name="sbUOMHashMap" value="%{#aascIntlDataBean.sbUOM}"/>
    <s:set name="exportTypeHashMap" value="%{#aascIntlDataBean.exportType}"/>
    <s:set name="currencyCodeHashMap" value="%{#aascIntlDataBean.currencyCodeList}"/>
    <s:set name="reasonExportHashMap" value="%{#aascIntlDataBean.reasonExport}"/>
    <s:set name="TOMHashMap" value="%{#aascIntlDataBean.TOM}"/>
    <s:set name="modeTransportHashMap" value="%{#aascIntlDataBean.modeTransport}"/>
    <s:set name="bondCodeHashMap" value="%{#aascIntlDataBean.bondCode}"/>
    <s:set name="commercialInvCheckedChk" value="%{#aascIntlDataBean.commercialInvChecked}"/>
    <s:set name="usCertOriginChk" value="%{#aascIntlDataBean.usCertOrigin}"/>
    <s:set name="naftaCertOriginChk" value="%{#aascIntlDataBean.naftaCertOrigin}"/>
    <s:set name="shippersExportDeclChk" value="%{#aascIntlDataBean.shippersExportDecl}"/>
    <%  
        
    
        AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1); // object of AascDAOFactory
        AascIntlShipmentsDAO aascIntlShipmentsDAO = aascDAOFactory.getAascIntlShipmentsDAO();   
        
        AascIntlInfo IntlInfoBean = null;
        AascAddressInfo soldToAddressBean = null;
        AascAddressInfo sedAddressBean = null;
        AascAddressInfo forwardAgentBean=null;
        AascAddressInfo intermediateConsigneeBean=null;
        
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        int clientId = (Integer)session.getAttribute("client_id");
        ArrayList<String> commItems = null;
        HashMap UOM = null;
        Set set=null;
        Iterator iterator=null;
        
        UOM = aascIntlShipmentsDAO.getUpsLookUpValues(100,"UNIT_OF_MEASURE");
  
        String shipFlagStr = request.getParameter("shipFlagStr");
        String intlSaveFlag = request.getParameter("intlSaveFlag");
        if(intlSaveFlag == null && "".equalsIgnoreCase(intlSaveFlag)){
            intlSaveFlag = "N";
        }
        int  carrierCode = Integer.parseInt(request.getParameter("carrierCode"));
        
        String intlCommActionFlag = (String)session.getAttribute("intlCommActionFlag");
        session.removeAttribute("intlCommActionFlag");
        String intlSaveActionFlag = (String)session.getAttribute("intlSaveActionFlag");
        session.removeAttribute("intlSaveActionFlag");
       
        try{
            String intlCommActionFlagStr = intlCommActionFlag.substring(0,1);
        }catch(Exception e){
            intlCommActionFlag = "N";
        }
        
        try{
            String intlSaveActionFlagStr = intlSaveActionFlag.substring(0,1);
        }catch(Exception e){
            intlSaveActionFlag = "N";
        }
        
        try{
            String shipFlagStrStr = shipFlagStr.substring(0,1);
        }catch(Exception e){
            shipFlagStr = "";
        }
        
        logger.info("################ shipFlagStr #############"+shipFlagStr);
        logger.info("intlSaveFlag :"+intlSaveFlag);
        
        String orderNumber = "";
        String shipStatusFlag = "";
        String UOMValueStr = "";
        String UOMValue = "";

        orderNumber = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getOrderNumber();
        shipStatusFlag = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getShipFlag();
        
        if(!shipFlagStr.equalsIgnoreCase("Y"))
        {
            logger.info("....shipFlagStr is not Y....");
            logger.info("....intlShipValue....");
            try{
                commItems = aascIntlShipmentsDAO.getIntlCommodityLineItems(clientId, locationId);
            }catch(Exception e){
                logger.info("Data is not entered in the International Shipments window");
            }
        }
        
        String action = (String) session.getAttribute("ACTION");
        try{
            String actionStrStr = action.substring(0,1);
        }catch(Exception e){
            action = "";
        }
        session.removeAttribute("ACTION");
        pageContext.setAttribute("locationId", locationId);
        pageContext.setAttribute("carrierCode",carrierCode);
        pageContext.setAttribute("intlSaveFlag",intlSaveFlag);       
        pageContext.setAttribute("shipFlagStr",shipFlagStr);
        pageContext.setAttribute("intlSaveActionFlag",intlSaveActionFlag);
        pageContext.setAttribute("orderNumber",orderNumber);
    %>
    <br/>
    <s:form method="POST" name="aascUPSIntlShipmentsForm" action="AascUpsIntlShipAction" onsubmit="return saveDetails()">
        <s:hidden name="actionType" id="actionType"/>
        <s:hidden name="CIFlag" id="CIFlag"/>
        <s:hidden name="NAFTAFlag" id="NAFTAFlag"/>
        <s:hidden name="COFlag" id="COFlag"/>
        <s:hidden name="SEDFlag" id="SEDFlag"/>
        <s:hidden name="addCommodityFlag" id="addCommodityFlag"/>
        <s:hidden name="shipment" id="shipment"/>
        <s:hidden name="selectLength" id="selectLength"/>
        <s:hidden name="opValue" id="opValue"/>
        <s:hidden name="actionStr" id="actionStr" value="%{#action}"/>
        <s:set name="intlSaveFlag" value="%{#attr.intlSaveFlag}"/>
        <s:hidden name="intlSaveFlag" value="%{#intlSaveFlag}"/>
        <s:set name="carrierCode" value="%{#attr.carrierCode}"/>
        <s:hidden name="carrierCode" id="carrierCode" value="%{#carrierCode}"/>
        <s:hidden name="soldToFlag" id="soldToFlag"/>
        <s:set name="locationId" value="%{#attr.locationId}"/>
        <s:hidden name="locationId" id="locationId" value="%{#locationId}"/>
        <s:set name="shipFlagStr" value="%{#attr.shipFlagStr}"/>
        <s:hidden name="shipFlagStr" value="%{#shipFlagStr}"/>
        <s:set name="intlSaveActionFlag" value="%{#attr.intlSaveActionFlag}"/>
        <s:hidden name="intlSaveActionFlag" value="%{#intlSaveActionFlag}"/>
        <s:hidden name="orderNumber" value="%{#attr.orderNumber}"/>
        <s:set name="addCommDisabled" value="%{''}"/>
        <div class="row" id="TopSaveButtonID">
        <div class="col-sm-12">
            <div class="col-sm-3"></div>
            <div class="col-sm-6" align="center">
                <s:if test='%{#shipFlagStr == "Y"}' >
                   <!-- <img src="buttons/aascSaveOff1.png" alt="" name="save" id="saveId1" border="0"> -->
                     <button type="button" disabled="disabled" class="btn btn-success" name="save" id="saveId1">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                </s:if>
                <s:else>
                    <!--<s:a  href="#" onclick="return saveDetails();"> <img src="buttons/aascSave1.png" ></s:a> -->
                    <button type="button" class="btn btn-success" onclick="return saveDetails();" name="save" id="saveId1">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                </s:else>
                    <!--<s:a href="javascript:window.close()" onclick="assignOrderNumber()"><img src="buttons/aascClose1.png"  alt="" name="close" id="closeId1" border="0"  ></s:a>-->
                        <button type="button" class="btn btn-danger" onclick="assignOrderNumber();javascript:window.close()" name="close" id="closeId1">Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
               <s:if test='%{#shipFlagStr == "Y" && #intlHeaderBean.commercialInv == "Y"}' >
                        <button type="button" class="btn btn-info" onclick="viewPrinted()" name="viewPrint" id="viewPrintId1">View/Print <span class="badge"><span class="glyphicon glyphicon-print"></span></span></button>
                        <!--<s:a  href="#" onclick="viewPrinted()"> <img src="buttons/aascViewPrint1.png" alt="" name="viewPrint" id="viewPrintId1" border="0"></s:a> -->
                </s:if>
                <s:else>
                    <!--<img src="buttons/aascViewPrintOff1.png" name="viewPrint" id="viewPrintId1" alt="" border="0">-->
                    <button type="button" disabled="disabled" class="btn btn-info" name="viewPrint" id="viewPrintId1">View/Print <span class="badge"><span class="glyphicon glyphicon-print"></span></span></button>
                </s:else>
            </div>
            <div class="col-sm-3"></div>
        </div>
        </div>
        <!--<center id="TopSaveButtonID">
            <s:if test='%{#shipFlagStr == "Y"}' >
                <img src="buttons/aascSaveOff1.png" alt="" name="save" id="saveId1" border="0">
            </s:if>
            <s:else>
                <s:a  href="#" onclick="return saveDetails();"> <img src="buttons/aascSave1.png" alt="" name="save" id="saveId1" border="0"></s:a> 
            </s:else>
            <s:a href="javascript:window.close()" onclick="assignOrderNumber()"><img src="buttons/aascClose1.png"  alt="" name="close" id="closeId1" border="0"  ></s:a>
            <s:if test='%{#shipFlagStr == "Y" && #intlHeaderBean.commercialInv == "Y"}' >
                <s:a  href="#" onclick="viewPrinted()"> <img src="buttons/aascViewPrint1.png" alt="" name="viewPrint" id="viewPrintId1" border="0"></s:a> 
            </s:if>
            <s:else>
            <img src="buttons/aascViewPrintOff1.png" name="viewPrint" id="viewPrintId1" alt="" border="0">
            </s:else>
        </center>-->
        <!--  <table border="0" width="100%" ><tr><td align="center"><span class="dispalyFields"> Do You Want To Select FORMS  :</span> &nbsp;&nbsp;&nbsp; <input name="SelectForm"  type="CHECKBOX" onclick="SelectForms();"></td></tr> </table> -->
        
        <br/>
        <div class="row" id="SelectFromID">
        <s:set name="commercialInv" value="%{#intlHeaderBean.commercialInv}" />
            <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%;margin-bottom:10px">
                <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px">
                    <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;">Select Export Form</label>
                </div>
                <br/>
                <div class="col-sm-3">
                    <span class="dispalyFields"><s:property value="getText('CommercialInvoice')"/></span>
                    &nbsp;&nbsp;&nbsp;
                    <span class="dispalyFields">
                        <s:checkbox name="CommercialInvoice" id="CommercialInvoiceID"  onclick="enableCI(); ShowFields();" fieldValue="#commercialInv"  value="%{#commercialInvCheckedChk}"/>
                    </span>
                </div>
                <br/>
                <div class="col-sm-3">
                    <span >    
                        <s:hidden name="USCO" id="USCOID"  onclick="enableUSCO(); ShowFields();" />
                    </span>
                </div>
                <div class="col-sm-3">
                    <span >
                        <s:hidden name="NAFTACO" id="NAFTACOID"  onclick="enableNAFTA(); ShowFields();"/>
                        <!--<s:checkbox name="NAFTACO" id="NAFTACOID" cssClass="inputFields"  onclick="enableNAFTA(); ShowFields();" fieldValue="true"/>-->
                    </span>
                </div>
                <div class="col-sm-3">
                    <span >
                        <s:hidden name="SED" id="SEDID" onclick="enableSED(); ShowFields();" />
                        <!--<s:checkbox name="SED" id="SEDID" cssClass="inputFields" onclick="enableSED(); ShowFields();" fieldValue="true"/>-->
                    </span>
                </div>
                 <br/>
            </div>  
           <br/>
        </div>
       <br/> 
       <!-- <fieldset style="width:90%;margin-left: auto;margin-right: auto;" >               
            <legend><img src="images/exportForm.png"   alt="" align="middle"></legend>
        <table id="SelectFromID" width="100%"  border="0" align="center">
            <s:set name="commercialInv" value="%{#intlHeaderBean.commercialInv}" />
            <tr>
                <td colspan="2"><span class="dispalyFields"><s:property value="getText('CommercialInvoice')"/></span>
                    &nbsp;&nbsp;&nbsp;
                    <span class="dispalyFields">
                        <s:checkbox name="CommercialInvoice" id="CommercialInvoiceID"  onclick="enableCI(); ShowFields();" fieldValue="#commercialInv"  value="%{#commercialInvCheckedChk}"/>
                    </span>
                </td>
                <td colspan="2">
                    &nbsp;&nbsp;&nbsp;
                    <span class="dispalyFields">    
                        <s:hidden name="USCO" id="USCOID"  onclick="enableUSCO(); ShowFields();" />
                    </span>
                </td>
                <td colspan="2">
                    &nbsp;&nbsp;&nbsp;
                    <span class="dispalyFields">
                        <s:hidden name="NAFTACO" id="NAFTACOID"  onclick="enableNAFTA(); ShowFields();"/>
                    </span>
                </td>
                <td colspan="2">
                    &nbsp;&nbsp;&nbsp;
                    <span class="dispalyFields">
                        <s:hidden name="SED" id="SEDID" onclick="enableSED(); ShowFields();" />
                    </span>
                </td>
            </tr>
        </table>
        </fieldset>-->
        <br/>
        <div class="row">
            <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%;margin-bottom:10px">
                <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px">
                    <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;">Commodity Line Item</label>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="col-sm-3">
                            <span class="dispalyFields"> <s:property value="getText('SelectCommodityItem')"/></span> 
                         </div>  
                           <div class="col-sm-3">
                            <span>
                        <select name="selCommItems" id="selCommItems" class="inputs" onchange="getIntlCommodityLineDetails();">
                            <option value="Select"><s:property value="getText('SelectOrCreate')"/></option>
                            <%
                                if(!shipFlagStr.equalsIgnoreCase("Y") && commItems != null){
                            %>
                            <option value="Create"><s:property value="getText('AddNewCommodity')"/></option>
                            <%
                                    for(int i=0; i<commItems.size(); i++){
                                        String lineItem = commItems.get(i);
                            %>
                            <option value="<%=lineItem%>"><%=lineItem%></option>
                            <%
                                    }
                                }    
                            %>
                        </select>
                        <!-- <input name="Description" type="text" class="inputFields"  size="15" maxlength="15" value="commInfoBean.description"> -->
                    </span>
                        </div>
                    </div>
                </div>
                <br/>
                <div id="commodityDetailDiv">
                <div class="row" >
                    <div class="col-sm-12">
                        <div class="col-sm-3">
                            <span class="dispalyFields"> <s:property value="getText('ProductDescription')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span>
                                <s:textfield name="description" id="description" cssClass="inputs"   value="%{#commInfoBean.description}"/>
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('TariffCode')"/> </span>
                        </div>
                        <div class="col-sm-3">
                            <span>
                                <s:textfield name="HarmonizedCode" id="HarmonizedCode" cssClass="inputs" value="%{#commInfoBean.harmonizedCode}"/>
                            </span>
                        </div>
                     </div>
                   </div>  
                   <br/>
                   <div class="row">
                     <div class="col-sm-12">
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('CountryOfManufacture')"/> *</span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                            <s:select list="#countryHashMap" name="CountryOfManufacture" id="CountryOfManufacture" listKey="key" listValue="value" headerValue="Select" headerKey=""
                                cssClass="inputs" value="%{#commInfoBean.countryOfManufacture}" />
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"> <s:property value="getText('NumberOfUnits')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                                <s:textfield name="Quantity" id="Quantity" cssClass="inputs" value="%{#commInfoBean.quantity}"/>
                            </span>
                        </div>     
                    </div>
                   </div>
                <br/>
                
                <div class="row" id="ComInRow">
                    <div class="col-sm-12">
                        
                        <div class="col-sm-3">
                            <span class="dispalyFields"> <s:property value="getText('Priceperunit')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span>
                                <s:textfield name="UnitPrice" cssClass="inputs" id="UnitPrice" value="%{#commInfoBean.unitPrice}"/>
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('UnitOfMeasurement')"/> *</span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                                <s:select list="#UOMHashMap" name="QuantityUnits" id="QuantityUnits" listKey="key" listValue="value" headerValue="Select" headerKey=""
                                cssClass="inputs" value="%{#commInfoBean.quantityUnits}" />                        
                            </span>
                        </div>
                    </div>
                </div>
             </div>   
                <div class="row" id="USRow">
                <br/>
                    <div class="col-sm-12">
                        <div class="col-sm-3">
                            <span class="dispalyFields" > <s:property value="getText('NoOfPackagesPerCommodity')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span>
                            <s:textfield name="NumberOfPieces" id="NumberOfPieces" cssClass="inputs"  size="12" maxlength="15" value="%{#commInfoBean.numberOfPieces}"/>
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('PackageWeight')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                                <s:textfield name="Weight" id="Weight" cssClass="inputs"  size="12" maxlength="15" value="%{#commInfoBean.weight}"/></span>
                            <span>
                                <s:select list="#{'LBS':'LBS','KGS':'KGS'}" name="UOM" id="UOM" cssClass="inputs" value="%{#commInfoBean.packageWeightUom}" />
                            </span>
                        </div>
                     </div>
                </div>
                
                <div class="row" id="NAFTARow1">
                <br/>
                    <div class="col-sm-12">
                        <div class="col-sm-3">
                            <span class="dispalyFields" > <s:property value="getText('PreferenceCriteria')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                            <s:select list="#preferenceCriteriaHashMap" name="PreferenceCriteria" id="PreferenceCriteria" listKey="key" listValue="value" headerValue="Select" headerKey=""
                                cssClass="inputs" value="%{#commInfoBean.preferenceCriteria}" />
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('Producer')"/> *</span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                                <s:select list="#producerHashMap" name="Producer" id="Producer" listKey="key" listValue="value" headerValue="Select" headerKey="" 
                                cssClass="inputs" value="%{#commInfoBean.producer}" />
                            </span>
                        </div>
                    </div>
                </div>
                
                <div class="row" id="NAFTARow2">
                <br/>
                    <div class="col-sm-12">
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('NAFTACertificateProducer')"/> >></span>
                        <s:a  href="#" name="NAFTA_ProduceInfo" onclick="return openUpsIntlAddrDetailsPopup('NAFTAAddr')"> 
                           <input type="BUTTON" class="btn btn-primary" name="NAFTA_ProduceInfo" id="NAFTA_ProduceInfo" value="Details"/>
                            
                        </s:a> 
                        </div>
                        <div class="col-sm-9">
                            <s:hidden name="PCompanyName" value="%{#addressInfoBean.companyName}"/>
                            <s:hidden name="PAddressLine1" value="%{#addressInfoBean.addressLine1}"/>
                            <s:hidden name="PCity" value="%{#addressInfoBean.city}"/>
                            <s:hidden name="PStateProvinceCode" value="%{#addressInfoBean.stateProvinceCode}"/>
                            <s:hidden name="PPostalCode" value="%{#addressInfoBean.postalCode}"/>
                            <s:hidden name="PCountryCode" value="%(#addressInfoBean.countryCode}"/>
                            <s:hidden name="NTaxIdNum" cssClass="inputs" value="%{#addressInfoBean.taxId}"/>
                        </div>   
                    </div>
                </div>
                
              <div  id="NAFTARow3">  
                <div class="row">
                <br/>
                    <div class="col-sm-12">
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('RVCCalculationMethod')"/> *</span>
                        </div>
                        <div class="col-sm-3">
                            <span>
                                <s:select list="#rvcCalMthdHashMap" name="RVCCalculationMethod" id="RVCCalculationMethod" listKey="key" listValue="value" headerValue="Select" headerKey=""
                                cssClass="inputs" onchange="validateNetCost()" value="%{#commInfoBean.rvcCalculationMethod}" />
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <s:property value="getText('NetCostPeriodBeginDate')"/>(<s:property value="getText('YYYYMMDD')"/>)*
                        </div>
                        <div class="col-sm-3">
                            <span >
                                <s:textfield name="NetCostPeriodBeginDate" id="NetCostPeriodBeginDate" cssClass="inputs"  size="12" maxlength="15" value="%{#commInfoBean.netCostPeriodBeginDate}"/>
                            </span>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                    <br/>
                    <div class="col-sm-12">
                    
                        <div class="col-sm-3">
                            <s:property value="getText('NetCostPeriodEndDate')"/>(<s:property value="getText('YYYYMMDD')"/>)*
                        </div>
                        <div class="col-sm-3">
                            <span>
                                <s:textfield name="NetCostPeriodEndDate" id="NetCostPeriodEndDate" cssClass="inputs"  size="12" maxlength="15" value="%{#commInfoBean.netCostPeriodEndDate}"/>
                            </span>
                        </div>
                    </div>
                </div>
             </div>
             
             <div id="SEDRow1">
                <div class="row">
                <br/>
                    <div class="col-sm-12">
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('SEDTotalValue')"/> *</span>
                        </div>
                        <div class="col-sm-3">
                            <span>
                                <s:textfield name="SEDTotalValue" id="SEDTotalValue" cssClass="inputs"   size="12" maxlength="15" value="%{#commInfoBean.sedTotalValue}"/>
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"> <s:property value="getText('ScheduleBNumber')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                                <s:textfield name="ScheduleBNumber" id="ScheduleBNumber" cssClass="inputs"   size="12" maxlength="15" value="%{#commInfoBean.scheduleBNumber}"/>
                            </span>
                        </div>
                      </div>
                     </div>
                     <div class="row">
                     <br/>
                    <div class="col-sm-12">
                        <div class="col-sm-3">
                            <span class="dispalyFields"> <s:property value="getText('ScheduleBQuantity')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                                <s:textfield name="ScheduleBQuantity" id="ScheduleBQuantity" cssClass="inputs"   size="12" maxlength="15" value="%{#commInfoBean.scheduleBQty}"/>
                            </span>
                        </div>
                    </div>
                </div>
              </div>
              
                <div class="row" id="SEDRow2">
                <br/>   
                    <div class="col-sm-12">
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('ScheduleBUnitOfMeasure')"/> *</span>
                        </div>
                        <div class="col-sm-3">
                            <span>
                                <s:select list="#sbUOMHashMap" name="ScheduleBUnitOfMeasure" id="ScheduleBUnitOfMeasure" listKey="key" listValue="value" headerValue="Select" headerKey=""
                                cssClass="inputs" value="%{#commInfoBean.scheduleBUOM}" />
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields" > <s:property value="getText('ExportType')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                                <s:select list="#exportTypeHashMap" name="ExportType" id="ExportType" listKey="key" listValue="value" headerValue="Select" headerKey=""
                                cssClass="inputs" value="%{#commInfoBean.exportType}" />
                            </span>
                        </div>
                    </div>
                </div>
                
                <div class="row" id="updateCommodityDetailDiv">
                <br/>
                    <div class="col-sm-12" align="center">
                        <s:checkbox name="addOrEditItem" id="addOrEditItem" value="update" fieldValue="update" disabled="%{#addCommDisabled}" />
                       <span class="dispalyFields"> <s:property value="getText('Save/updatethiscommodityitem')"/></span>
                    </div>
                </div>
                <div class="row" id="addCommodityDetailDiv">
                <br/>
                    <div class="col-sm-12" align="center">
                    
                        <input type="BUTTON" class="btn btn-primary" name="addComm" id="addComm" onclick="disableChkBox();saveProductDetails();" value=" Add This Commodity Item" />
                    </div>
                </div>
                <div class="row">
                <br/>
                    <div class="col-sm-12">
                        <div class="col-sm-8">
                            <select name="commodityLine" id="commodityLine" size="5" style="width:90%;height:80px">
                            <option value="111" selected="selected">Product Description  : Unit Of Measure : Number of Units : Price Per Unit  </option>
                            <option value="222">-------------------------------------------------------------------------------</option>
                            <%
                                AascIntlCommodityInfo commInfo1 = null;
                                AascIntlInfo intlInfo = null;
                                LinkedList coList = new LinkedList();
                                IntlInfoBean = (AascIntlInfo)(pageContext.getAttribute("intlInfoBean"));
       
                                if(shipFlagStr.equalsIgnoreCase("Y")){
                                    try{  
                                        coList = IntlInfoBean.getIntlCommodityInfo();
                                    }catch(Exception e){
                                        logger.info("Data is not entered in the International Shipments window");
                                    }
                                }else{
                                    {
                                        intlInfo = aascIntlShipmentsDAO.getIntlCommodityLineDetails(orderNumber, clientId, locationId);
                                        coList = intlInfo.getIntlCommodityInfo();
                                    }
                                }
                                
                                ListIterator CoInfoIterator = coList.listIterator();
                                int optionValue = 1;
                                String description = "";
                                String noUnits = "";
                                String pricePerUnit = "";
                                String tariffCode = "";
                                String COM = "";
                                double lineCustomValue = 0.0; 
                                double value = 0.0;
            
                                while(CoInfoIterator.hasNext())
                                {
                                    AascIntlCommodityInfo aascIntlCommInfo = (AascIntlCommodityInfo)CoInfoIterator.next();
                                    if(aascIntlCommInfo!=null)
                                    {
                                        if(!shipFlagStr.equalsIgnoreCase("Y")){
                                            if(aascIntlCommInfo.getCarrierCode()==carrierCode)
                                            {
                                                // aascIntlCommInfo = (AascIntlCommodityInfo)CoInfoIterator.next();
                                            }
                                            else
                                            {
//                                                int opStatus = aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
//                                                aascIntlCommInfo=null;
                                            }
                                        }
                                    }
                                    try
                                    {
                                        optionValue = aascIntlCommInfo.getCommodityNumber();
                                        logger.info("optionValue"+optionValue);
                                        description= aascIntlCommInfo.getDescription();
                                        noUnits = aascIntlCommInfo.getQuantity();
                                        pricePerUnit = aascIntlCommInfo.getUnitPrice();
                                        UOMValue = aascIntlCommInfo.getQuantityUnits();
                                        set= UOM.keySet();
                                        iterator=set.iterator();
                                        while(iterator.hasNext())
                                        {
                                            String hashMapKey=(String)iterator.next();
                                            String hashMapValue=(String)UOM.get(hashMapKey);
                                            if(hashMapKey.equalsIgnoreCase(UOMValue) && !UOMValue.equalsIgnoreCase("")) 
                                            {  
                                                UOMValueStr = hashMapValue;
                                            }//end of
                                        }
                                        logger.info("UOMValueStr "+UOMValueStr);
                                        tariffCode = aascIntlCommInfo.getTariffCode();
                                        if(tariffCode == null)
                                        {
                                            tariffCode="-------";
                                        }
                                        COM = aascIntlCommInfo.getCountryOfManufacture();
                                        logger.info("description"+description);
                                    }catch(Exception e)
                                    {
                                        logger.info("exception in getting values."+e.getMessage());
                                    }
                                        logger.info("tariffCode::::"+tariffCode);
                       try{
                                    if(description!="" && description!=null)
                                    {
                            %>
                            <option value="<%=optionValue%>"><%=description%>  :  <%=UOMValueStr%>  :  <%=noUnits%>  :  <%=pricePerUnit%>  </option>
                            <%
                                        if(tariffCode.equalsIgnoreCase("-------"))
                                        {
                                            tariffCode=null;
                                        }
                                    }
                                    
                                    value=Double.parseDouble(aascIntlCommInfo.getQuantity())*Double.parseDouble(aascIntlCommInfo.getUnitPrice()) ;
                                    lineCustomValue = lineCustomValue + value;
                                    }catch(Exception e){
                                    logger.info("Exception e::::"+e.getMessage());
//                                  //  e.printStackTrace();
                                    }
                                }
                                logger.info("After Displaying Commodity values in Select Option");
                                logger.info("****###***#### lineCustomValue ****###***####"+lineCustomValue);
                                pageContext.setAttribute("lineCustomValue",lineCustomValue);
                            %>
                        </select>
                        <s:hidden name="commCustomValue" id="commCustomValue" value="%{#attr.lineCustomValue}"/> 
                        </div>     
                        <div class="col-sm-4">
                               <input type="BUTTON" class="btn btn-primary" name="editComm" id="editComm" value="  Edit Item  " onclick="editOptions();">
                               <input type="BUTTON" class="btn btn-primary" name="delComm" id="delComm" value="  Delete Item  " onclick="delOptions();">
                              
                        </div>
                    </div>
                </div>
                <br/>
            </div>    
        </div>
        <br/>
        <div class="row" id="GeneralDetailsID">
            <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%">
                <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px">
                    <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;">EIN/Tax ID Details</label>
                </div>
                <div class="row" id="GeneralID1">
                    <font color="Green"><span></span></font>
                </div>
                <div class="row" id="GeneralID2">
                <br/>
                    <div class="col-sm-12">
                        <div class="col-sm-2">
                            <span class="dispalyFields"><s:property value="getText('ShipFromEIN/TaxID')"/></span>
                        </div>
                        <div class="col-sm-2">
                            <span >
                                <s:if test='%{#intlHeaderBean.intlShipFromTaxId != "Y"}' >
                                    <s:textfield name="ShipFromTaxID" id="ShipFromTaxID" cssClass="inputs" maxlength="15" value="%{#intlHeaderBean.intlShipFromTaxId}" onkeypress="return chkCommodity()"/>
                                </s:if>
                                <s:else>
                                    <s:textfield name="ShipFromTaxID" id="ShipFromTaxID" cssClass="inputs"  maxlength="15" value="%{#intlHeaderBean.intlSedNumber}" onkeypress="return chkCommodity()"/>
                                </s:else>
                            </span>
                        </div>
                        <div class="col-sm-2">
                            <%
                  String  shipFromPhoneNum1 = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getShipFromPhoneNumber1();
                  pageContext.setAttribute("shipFromPhoneNum1",shipFromPhoneNum1);
                %>
                <s:set name="shipFromPhoneNum1" value="%{#attr.shipFromPhoneNum1}"/>
                <s:if test='%{#intlHeaderBean.shipFromPhone == "" && intlSaveFlag == "N" && intlSaveActionFlag == "N"}' >
                    <s:hidden name="ShipFromPhone"  cssClass="inputs"   value="%{#shipFromPhoneNum1}" onkeypress="return chkCommodity()" />
                </s:if>
                <s:else>
                    <s:hidden name="ShipFromPhone"  cssClass="inputs"   value="%{#intlHeaderBean.shipFromPhone}" onkeypress="return chkCommodity()" />
                </s:else>
                
                <%
                    String shipFromAttentionName1 = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getShipFromCompanyName();
                    pageContext.setAttribute("shipFromAttentionName1",shipFromAttentionName1);
                %>
                <s:set name="shipFromAttentionName1" value="%{#attr.shipFromAttentionName1}"/>
                <s:if test='%{#intlHeaderBean.shipFromAttention == "" && intlSaveFlag == "N" && intlSaveActionFlag == "N"}' >
                    <s:hidden name="ShipFromAttention" cssClass="inputs"   value="%{#shipFromAttentionName1}" onkeypress="return chkCommodity()"/>
                </s:if>
                <s:else>
                    <s:hidden name="ShipFromAttention"  cssClass="inputs"  value="%{#intlHeaderBean.shipFromAttention}" onkeypress="return chkCommodity()"/>
                </s:else>
                <span class="dispalyFields" > <s:property value="getText('ShipToEIN/TaxID')"/></span>
                        </div>
                        <div class="col-sm-2">
                            <s:textfield name="ShipToTaxID" id="ShipToTaxID" cssClass="inputs"  maxlength="15" value="%{#intlHeaderBean.intlShipToTaxId}" onkeypress="return chkCommodity()"/>
                        </div>
                        <div class="col-sm-2">
                            <span class="dispalyFields"><s:property value="getText('SoldTo')"/> >></span>
                        </div>
                        <div class="col-sm-2">
                            <s:hidden name="ShipTosameSoldTo" value="%{#soldToValue}"/>
                <s:a  href="#" name="SoldToAddr" onclick="return openUpsIntlAddrDetailsPopup('SoldToAddr')">
                <input type="BUTTON" class="btn btn-primary" name="SoldToAddr" id="SoldToAddr" value="Details"/>
                   
                </s:a> 
                
               <!-- <button class="btn btn-warning" name="clearButton" id="clearButton" href="javascript:clearFields()" alt="Clear" align="middle">Details </button> -->
                
                <s:hidden name="CompanyName"  value="%{#soldToAddressBean.companyName}"/>
                <s:hidden name="AddressLine1" value="%{#soldToAddressBean.addressLine1}" />
                <s:hidden name="AddressLine2" value="%{#soldToAddressBean.addressLine2}" />
                <s:hidden name="City" value="%{#soldToAddressBean.city}"/>
                <s:hidden name="StateProvinceCode"  value="%{#soldToAddressBean.stateProvinceCode}"/>
                <s:hidden name="PostalCode"  value="%{#soldToAddressBean.postalCode}"/>
                <s:hidden name="CountryCode"  value="%{#soldToAddressBean.countryCode}"/>
                <s:hidden name="TaxIdNum"  cssClass="inputs" value="%{#intlHeaderBean.soldToTaxId}"/>
                <s:hidden name="SoldToAttention"  cssClass="inputFields"  value="%{#intlHeaderBean.soldToAttention}" onkeypress="return chkCommodity()"/>
                <s:hidden name="SoldToPhone"  cssClass="inputFields"  value="%{#intlHeaderBean.soldToPhone}" onkeypress="return chkCommodity()"/>
                <s:hidden name="addOrEditImporter" value="%{''}"/>
                <s:hidden name="shipFlagStr" value="%{#shipFlagStr}"/>
                        </div>
                    </div>
                </div>
                <br/>
            </div>
        </div>
        <br/>
  <div class="row" id="ComInvoiceID">
    <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%">
          <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px">
              <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;">Commercial Invoice Details</label>
          </div>
          <div class="row" id="CMInvoice">
            <font color="Green"></font>
          </div>
        
          <div class="row" id="InvoiceDetailsID2">
          <br/>
          <div class="col-sm-12">
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('Invoice Currency Code')"/></span>
            </div>
            <div class="col-sm-3">
                <s:select list="#currencyCodeHashMap" name="InvCurCd" id="InvCurCd" listKey="key" listValue="value" headerValue="-Select One-" headerKey=""
                    cssClass="inputs" value="%{#intlHeaderBean.invoiceCurrencyCode}" />
            </div>
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('InvoiceValue')"/></span>
            </div>
            <div class="col-sm-3">
                <s:textfield name="InvVal" id="InvVal" cssClass="inputs"  maxlength="15" value="%{#intlHeaderBean.invoiceValue}" onkeypress="return chkCommodity()"/>
            </div>
            
            </div>
          </div>
          <div class="row" id="CIDetailsRow1">
            <br/>
            <div class="col-sm-12">
            <c:catch var="ex">
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('InvoiceNumber')" /></span>
            </div>
            <div class="col-sm-3">
                <s:textfield name="InvoiceNumber" id="InvoiceNumber" cssClass="inputs" maxlength="15" value="%{#intlHeaderBean.intlCustomerInvoiceNumber}"/>
            </div>
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('InvoiceDate')"/>*(<s:property value="getText('YYYYMMDD')"/>)</span>
            </div>
            <c:choose>
            <div class="col-sm-3">
                <span ><s:textfield id="InvoiceDateTextBoxID" cssClass="inputs" name="InvoiceDate" value="%{#intlHeaderBean.intlInvoiceDate}" maxlength="20" /></span>
            </div>
            </c:choose>
            </c:catch>
            </div>
          </div>
          <div class="row" id="CIDetailsRow2">
            <br/>
            <div class="col-sm-12">
            <div class="col-sm-3">
                <s:hidden name="PuchaseOrderNumber" cssClass="inputs" value="%{#intlHeaderBean.intlPurchaseOrderNumber}"/>
                <span class="dispalyFields"><s:property value="getText('CurrencyCode')"/> *</span>
            </div>
            <div class="col-sm-3">
                <span>
                <s:select list="#currencyCodeHashMap" name="CurrencyCode" id="CurrencyCode" listKey="key" listValue="value" headerValue="-Select One-" headerKey=""
                    cssClass="inputs" value="%{#intlHeaderBean.intlCurrencyCode}" />
                </span>
            </div>
            <div class="col-sm-3">
                <span class="dispalyFields"> <s:property value="getText('ReasonForExport')"/>*</span>
            </div>
            <div class="col-sm-3">
                <span >
                <s:select list="#reasonExportHashMap" name="Purpose" id="Purpose" listKey="key" listValue="value" headerValue="Select" headerKey="" 
                        cssClass="inputs" value="%{#intlHeaderBean.intlPurpose}" />
            </span>
            </div>
            </div>
          </div>
          <div class="row" id="CIDetailsRow3">
            <br/>
             <div class="col-sm-12">
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('TermsOfSale')"/></span>
            </div>
            <div class="col-sm-3">
                <span >
                <s:set name="TOMValue" id="TOMValue" value=""/>
                <s:select list="#TOMHashMap" name="TermsOfSale" id="TermsOfSale" listKey="key" listValue="value" headerValue="Select" headerKey="" 
                        cssClass="inputs" value="%{#intlHeaderBean.intlTermsOfSale}" />
                </span>
            </div>
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('AdditionalComments')"/></span>
            </div>
            <div class="col-sm-3">
                <span>
                    <s:textarea name="comments" id="comments" class="inputs" value="%{#intlHeaderBean.intlComments}" />    
                </span>
                <s:set name="decStmtInfo" value="%{#intlHeaderBean.intlDeclarationStmt}" />
                <s:set name="decStmt" value="%{#intlHeaderBean.declarationStmt}" />
                <s:if test='%{#decStmtInfo == ""}' >
                <s:hidden name="DeclarationStmt" value=" %{#decStmt}"/>
                </s:if>
                <s:else>
                <s:hidden name="DeclarationStmt" value=" %{#intlHeaderBean.intlDeclarationStmt}" />
                </s:else>
            </div>
          <br/>
     </div>   
     </div>
     <div class="row" id="CIDetailsRow4">
     <br/>
         <div class="col-sm-12">
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('Discount')"/></span>
            </div>
            <div class="col-sm-3">
                <span>
                <s:textfield name="Discount" id="Discount" cssClass="inputs" value="%{#intlHeaderBean.intlDiscount}"/>
                </span>
            </div>
            
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('FreightCharges')"/></span>
            </div>
            <div class="col-sm-3">
                <span >
                <s:textfield name="FreightCharges" id="FreightCharges" cssClass="inputs" value="%{#intlHeaderBean.intlFreightCharge}" onblur="calculateInvoiceTotal();"/>
                </span>
            </div>
            
         </div>
     </div>
     <div class="row" id="CIDetailsRow5">
     <br/>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('InsuranceCharges')"/></span>
            </div>
            <div class="col-sm-3">
                <span >
                    <s:textfield name="InsuranceCharges" id="InsuranceCharges"  cssClass="inputs"  value="%{#intlHeaderBean.intlInsuranceCharge}" onblur="calculateInvoiceTotal();"/>
                </span>
            </div>
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('OtherCharges')"/></span>
            </div>
            <div class="col-sm-3">
                <span >
                    <s:textfield name="OtherCharges" id="OtherCharges" cssClass="inputs"  value="%{#intlHeaderBean.intlTaxMiscellaneousCharge}" onblur="calculateInvoiceTotal();"/>
                </span>
          </div>         
        </div>
      
     </div>
       <br/>
     
     </div>
  </div>
  <div class="row" id="BottomSaveButtonID" align="center" style="background-color:#ADADAD;">
  <br/>
        <%
            if(shipFlagStr.equalsIgnoreCase("Y")){
        %>
       <!-- <img src="buttons/aascSaveOff1.png" name="save" alt="" border="0">-->
        <button class="btn btn-success" name="save" id="saveId2">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
        <%  
            }else{
        %>
       <!-- <s:a  href="#" onclick="return saveDetails();" > <img src="buttons/aascSave1.png" name="save" alt="" border="0"></s:a>-->
        <button type="button" class="btn btn-success" onclick="return saveDetails();" name="save" id="saveId2">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
        
        <% } %>
        
        <button class="btn btn-danger" name="close" onclick="assignOrderNumber();javascript:window.close()" id="closeId2">Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
        
  </div>
  <br/>
   
    <hr id="USCertifiHR" width="100%">
    <table id="USCertificateID" width="100%"  border="0" align="center">
        <tr><td colspan="2"><font color="Green"><span class="dispalyFields"><s:property value="getText('ForUSCertificateOfOrigin')"/>:</span></font></td></tr>
        <tr>
            <td class="dispalyFields"><s:property value="getText('ExportDate')"/>(<s:property value="getText('YYYYMMDD')"/>)*</td>
            <td><span >
                <s:textfield name="UExportDate" id="UExportDate" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.intlExportDate}"/>
            </span></td>
            <td class="dispalyFields"> <s:property value="getText('ExportingCarrier')"/>*</td>
            <td><span >
                <s:textfield name="UExportingCarrier" id="UExportingCarrier" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.intlExportCarrier}"/>
            </span></td>
        </tr>  
    </table>
             
    <hr id="NAFTACertifiHR" width="100%">
    <table id="NAFTACertificateID" width="100%"  border="0" align="center">
        <tr><td colspan="2"><font color="Green"><span ><s:property value="getText('ForNAFTACertificateOfOrigin')"/>:</span></font></td></tr>
        <tr>
            <td class="dispalyFields"><s:property value="getText('BlanketPeriodBeginDate')"/>(<s:property value="getText('YYYYMMDD')"/>)*</td>
            <td><span>
                <s:textfield name="BlanketPeriodBeginDate" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.intlBlanketPeriodBeginDate}"/>
            </span></td>
            <td class="dispalyFields"><s:property value="getText('BlanketPeriodEndDate')"/>(<s:property value="getText('YYYYMMDD')"/>)*</td>
            <td><span>
                <s:textfield name="BlanketPeriodEndDate" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.intlBlanketPeriodEndDate}"/>
            </span></td>
        </tr>
    </table> 
        
    <hr id="ShipperExpoHR" width="100%">
    <table id="ShippersExportID" width="100%"  border="0" align="center">
        <tr><td colspan="2"><font color="Green"><span class="dispalyFields"><s:property value="getText('ForShipper'sExportDeclaration')"/>:</span></font></td></tr>
        <tr>
            <td class="dispalyFields"><s:property value="getText('Point(State)OfOrigin')"/> *</td>
            <td><span >
                <s:textfield name="PointOfOrigin" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.sedPointOfOrigin}"/>
            </span></td>
            <td><span > <s:property value="getText('ModeOfTransport')"/> *</span></td>
            <td><span >
                <s:select list="#modeTransportHashMap" name="ModeOfTransport" id="ModeOfTransport" listKey="key" listValue="value" headerValue="Select" headerKey="" 
                        cssClass="inputs" value="%{#intlHeaderBean.sedModeOfTransport}" />
            </span></td>
            <td class="dispalyFields"><s:property value="getText('ExportDate')"/>(<s:property value="getText('YYYYMMDD')"/>)*</td>
            <td><span >
                <s:textfield name="SExportDate" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.sedExportDate}"/>
            </span></td>
            <td class="dispalyFields"><s:property value="getText('ExportingCarrier')"/> *</td>
            <td><span>
                <s:textfield name="SExportingCarrier" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.sedExportingCarrier}"/>
            </span></td>
        </tr>
        <tr>
            <td><span class="dispalyFields"><s:property value="getText('InBondCode')"/>*</span></td>
            <td><span >
                <s:select list="#bondCodeHashMap" name="InBondCode" id="InBondCode" listKey="key" listValue="value" headerValue="Select" headerKey="" 
                        cssClass="inputs" value="%{#intlHeaderBean.sedInBondCode}" />
            </span></td>
            <td class="dispalyFields"><s:property value="getText('EntryNumber')"/></td>
            <td><span>
                <s:textfield name="EntryNumber" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.sedEntryNumber}"/>
            </span></td>
            <td class="dispalyFields"><s:property value="getText('LoadingPier')"/></td>
            <td><span >
                <s:textfield name="LoadingPier" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.sedLoadingPier}"/>
            </span></td>
            <td class="dispalyFields"><s:property value="getText('PortOfExport')"/></td>
            <td><span >
                <s:textfield name="PortOfExport" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.sedPortOfExport}"/>
            </span></td>
        </tr>
        <tr>
            <td class="dispalyFields"><s:property value="getText('PortOfUnloading')"/></td>
            <td><span>
                <s:textfield name="PortOfUnloading" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.sedPortOfUnloading}"/>
            </span></td>
            <td class="dispalyFields"><s:property value="getText('CarrierIdentificationCode')"/></td>
            <td><span >
                <s:textfield name="CarrierIdentificationCode" cssClass="inputs"  size="12" maxlength="15" value="%{#intlHeaderBean.sedCarrierIdentificationCode}"/>
            </span></td>
            <td class="dispalyFields"><s:property value="getText('Containerized')"/></td>
            <td><span >
                <s:select list="#{'Y':'YES','N':'NO'}" name="Containerized" id="Containerized" listKey="key" listValue="value" headerValue="Select" headerKey=""
                        cssClass="inputs" value="%{#intlHeaderBean.sedContainerized}" />

            </span></td>

            <td class="dispalyFields"><s:property value="getText('HazardousMaterials')"/></td>
            <td><span>
                <s:select list="#{'Y':'YES','N':'NO'}" name="HazardousMaterials" id="HazardousMaterials" listKey="key" listValue="value" headerValue="Select" headerKey=""
                        cssClass="inputs" value="%{#intlHeaderBean.sedHazardiousMaterial}" />
            </span></td>
        </tr>
        <tr>
            <td colspan="2">
                <span class="dispalyFields"><s:property value="getText('UltimateConsignee')"/>* >></span>
            </td>
            <td>
                <s:hidden value="%{''}" name="addressType"/>
                <s:a  href="#" name="ConsigneeInfo" onclick="return openUpsIntlAddrDetailsPopup('ConsigneeInfo')"> 
                <input type="BUTTON" class="btn btn-primary" name="ConsigneeInfo" value="Details"/>
                </s:a>
                <s:hidden name="CCompanyName" value="%{#sedAddressBean.companyName}"/>
                <s:hidden name="CAddressLine1" value="%{#sedAddressBean.addressLine1}"/>
                <s:hidden name="CCity" value="%{#sedAddressBean.city}"/>
                <s:hidden name="CStateProvinceCode" value="%{#sedAddressBean.stateProvinceCode}"/>
                <s:hidden name="CPostalCode" value="%{#sedAddressBean.postalCode}"/>
                <s:hidden name="CCountryCode" value="%{#sedAddressBean.countryCode}"/>
            </td>
            <td>
                <span class="dispalyFields"><s:property value="getText('ForwardAgent')"/>>></span>
            </td>
            <td>
                <s:a  href="#" name="FAgentInfo" onclick="return openUpsIntlAddrDetailsPopup('FAgentInfo')"> 
                <input type="BUTTON" class="btn btn-primary" name="FAgentInfo" value="Details"/>
                </s:a> 
                <s:hidden name="FCompanyName" value="%{#forwardAgentBean.companyName}"/>
                <s:hidden name="FAddressLine1"  value="%{#forwardAgentBean.addressLine1}"/>
                <s:hidden name="FCity" value="%{#forwardAgentBean.city}"/>
                <s:hidden name="FStateProvinceCode"  value="%{#forwardAgentBean.stateProvinceCode}"/>
                <s:hidden name="FPostalCode" value="%{#forwardAgentBean.postalCode}"/>
                <s:hidden name="FCountryCode"  value="%{#forwardAgentBean.countryCode}"/>
                <s:hidden name="FTaxIdNum" cssClass="inputFields" value="%{#forwardAgentBean.taxId}"/>  
            </td>
            <td>
                <span class="dispalyFields"><s:property value="getText('IntermediateConsignee')"/> >></span>
            </td>
            <td>
                <s:a  href="#" name="IConsigneeInfo" onclick="return openUpsIntlAddrDetailsPopup('IConsigneeInfo')">
                <input type="BUTTON" class="btn btn-primary" name="IConsigneeInfo" value="Details"/>
                </s:a> 
                <s:hidden name="ICCompanyName" value="%{#intermediateConsigneeBean.companyName}"/>
                <s:hidden name="ICAddressLine1" value="%{#intermediateConsigneeBean.addressLine1}"/>
                <s:hidden name="ICCity" value="%{#intermediateConsigneeBean.city}"/>
                <s:hidden name="ICStateProvinceCode"  value="%{#intermediateConsigneeBean.stateProvinceCode}"/>
                <s:hidden name="ICPostalCode"   value="%{#intermediateConsigneeBean.postalCode}"/>
                <s:hidden name="ICCountryCode"  value="%{#intermediateConsigneeBean.countryCode}"/>
            </td> 
        </tr>
        <tr>
            <td class="dispalyFields"><s:property value="getText('RoutedExportTransaction')"/></td>
            <td><span>
                <s:select list="#{'Y':'YES','N':'NO'}" name="RoutedExportTransaction" id="RoutedExportTransaction" listKey="key" listValue="value" headerValue="Select" headerKey="" 
                        cssClass="inputs" value="%{#intlHeaderBean.sedModeOfTransport}" />
            </span></td>            
            <td class="dispalyFields"><s:property value="getText('PartiestoTransaction')"/> *</td>
            <td><span>
                <s:radio list="#{'N':'Non-related','R':'Related'}" name="PartiestoTransaction" id="PartiestoTransaction" value="%{#intlHeaderBean.sedPartiesToTran}"  cssClass="dispalyFields"/> 
            </span></td>
        </tr>
       
        <tr>
            <td class="dispalyFields"> <s:property value="getText('License')"/>*</td>  
            <td class="dispalyFields"><s:property value="getText('LicenseNumber')"/></td>
            <td>
                <s:if test='%{#intlHeaderBean.sedLicenceNumber != "" && intlHeaderBean.sedLicenceNumber != null}'>
                    <s:set name="licenceNumCheck" value="%{'true'}"/>
                </s:if>
                <s:else>
                    <s:set name="licenceNumCheck" value="%{''}"/>
                </s:else>
                <s:checkbox name="LicenseNumberRadio" id="LicenseNumberRadio"  onclick="checkLicNumOrExcpCode('LicenseNumber')" value="#licenceNumCheck" />
                <s:textfield name="LicenseNumber" id="LicenseNumber" cssClass="inputs"  size="10" maxlength="15" value="%{#intlHeaderBean.sedLicenceNumber}" />
            </td>
            &nbsp;
            <td class="dispalyFields"><s:property value="getText('LicenseDate')"/>(<s:property value="getText('YYYYMMDD')"/>)</td>
            <td>
                <s:textfield name="LicenseDate" cssClass="inputs"  size="10" maxlength="15" value="%{#intlHeaderBean.sedLicenceDate}"/>
            </td>
	</tr>
        <tr>
            <td></td>
            <td class="dispalyFields"><s:property value="getText('ExceptionCode')"/></td>
            <td>
                <s:if test='%{#intlHeaderBean.sedLicenceExceptionCode != "" && intlHeaderBean.sedLicenceExceptionCode != null}'>
                    <s:set name="licenceExceptionCheck" value="%{'true'}"/>
                </s:if>
                <s:else>
                    <s:set name="licenceExceptionCheck" value="%{''}"/>
                </s:else>
                <s:checkbox name="LicenseNumberRadio" id="LicenseNumberRadio"  onclick="checkLicNumOrExcpCode('ExceptionCode')" value="#licenceExceptionCheck" />
		<span >
                    <s:select list="#{'AGR':'AGR','APR':'APR','AVS':'AVS','CIV':'CIV','CTP':'CTP','ENC':'ENC','GBS':'GBS','GFT':'GFT','GOV':'GOV',
                            'KMI':'KMI','LVS':'LVS','NLR':'NLR','RPL':'RPL','TMP':'TMP','TSPA':'TSPA','TSR':'TSR','TSU':'TSU'}" 
                                    name="ExceptionCodes" listKey="key" listValue="value" headerValue="Select" headerKey="" cssClass="inputs"  
                                        onchange="disableECCNNumber()" value="%{#intlHeaderBean.sedLicenceExceptionCode}" />
                </span>
            </td>
            &nbsp;
            <td class="dispalyFields"><s:property value="getText('EccnNumber')"/></td>
            <td><s:textfield name="EccnNumber" cssClass="inputs" size="10" maxlength="15" value="%{#intlHeaderBean.sedECCN}"/></td>
        </tr>
       
    </table> 
   <!--  <center id="BottomSaveButtonID">
        <%
            if(shipFlagStr.equalsIgnoreCase("Y")){
        %>
        <img src="buttons/aascSaveOff1.png" name="save" alt="" border="0">
        <%  
            }else{
        %>
        <s:a  href="#" onclick="return saveDetails();" > <img src="buttons/aascSave1.png" name="save" alt="" border="0"></s:a> 
        <% } %>
        <s:a href="javascript:window.close()" onclick="assignOrderNumber()"><img src="buttons/aascClose1.png"  name="close" alt="" border="0"  ></s:a>
    </center>  -->
    </s:form>
    <br/>
    </div>
   <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>    
    </body>
</html>
