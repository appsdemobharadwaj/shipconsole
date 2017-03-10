 

<%/*==============================================================================================+
|  DESCRIPTION                                                                                    |
|    JSP to Save International Shipments options                                                  |
|    Author Y Pradeep                                                                             |
|    Version   1                                                                                  |
|    Creation 28-JAN-2015                                                                         |
History:
    28/01/2015   Y Pradeep    Added this file for Fedex Indernation page.
    02/02/2015   Y Pradeep    Modified code to remove Home and SignOut buttons in popup window.
    11/02/2015   Y Pradeep    Moved DAO calls from JSP page to Action class and modified code as required. Modified code to getlables from Property file.
    16/02/2015   Y Pradeep    Modified code to generate order number on click og Ship button in Shipping page.
    05/03/2015    Sanjay & Khaja Added code for new UI changes.
    10/03/2015   Y Pradeep    Added alt attribute for image tags.
    13/03/2015   Jagadish Jain  Added code required to implement new UI
    17/04/2015   Suman G      Moved session verification from down to up to fix #2743.
    11/06/2015   Suman G      Removed fevicon to fix #2992.
    19/06/2015   Y Pradeep    Modified code to allign UI accordingly.
    24/06/2015   Rakesh K     Modified Importer select field size for #3066
    24/07/2015   Rakesh K     Modified Code to work application in offline.
    04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
    06/08/2015  Rakesh K    Modified Code for #3302.
    10/08/2015  Dinakar G      Modified code for bug fix #3360.
    21/08/2015  N Srisha     Modified code for making packingListEnclosed value for bug #3377
    26/08/2015  Rakesh K       Added code to solve 3496.
    26/08/2015  N Srisha    Added Id's for Automation testing
    04/11/2015  Dinakar     Added css class for select fields, bug #3869
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
    
    <!--<style type="text/css"> 
     @font-face {   
     font-family: 'Glyphicons Halflings';   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot');   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.woff') format('woff'),  
     url('bootstrap/fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg'); } 
    </style>-->
    
   <!-- <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />-->
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:property value="getText('FedExInternationalShipments')"/></title>
        <link type="text/css" rel="stylesheet" href="aasc_ss.css">
        <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
        <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    
        <script type="text/javascript" language="javascript" SRC="aascInternationalShipments_js.js">
        </script>
    </head>
    <body onload="loadDetails();disableFields();totalCustomValue();">
    <div  class="container-fluid" style="background-color:#ADADAD; width:100%;">
    <%!
        static Logger logger = AascLogger.getLogger("aascAdhocInternationalShipments.jsp");
    %>
    <%
    String url = request.getContextPath();
        if(session.isNew())
        {
            response.sendRedirect(url+"/aascShipPopUpsError.jsp");
        }
        String comment = (String)session.getAttribute("comments");
        session.removeAttribute("comments");
        try{
            String commentStr = comment.substring(0,1);
        }catch(Exception e)
        {
            comment = "";
        }
    %>
    <table width="100%"  align="center" >
        <tr>
            <td width="400" align="left"></td>
            <td><font color="red" size= "2"></font></td>
            <td width="100">&nbsp;&nbsp;</td>
        </tr>
        <tr>
            <td class="international" id="displayMessage">
                <font color="Green"><%=comment%> </font>
            </td>
        </tr>
    </table>
    <%!
    /** This method can replace the null values with nullString.
     * @return String that is ""
     * @param obj of type Object
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
    <s:set name="aascIntlDataBean" value="AascIntlDataBean" />
    <s:set name="aascProfileOptionsInfo" value="%{#session.ProfileOptionsInfo}"/>
    <s:set name="intlInfoBean" value="%{#aascIntlDataBean.intlInfoBean}" />
    <s:set name="intlHeaderBean" value="%{#aascIntlDataBean.intlHeaderBean}" />
    <s:set name="commInfoBean" value="%{#aascIntlDataBean.aascCommodityBean}"/>
    <s:set name="countryHashMap" value="%{#aascIntlDataBean.countryList}"/>
    <s:set name="impNamesHashMap" value="%{#aascIntlDataBean.impNames}"/>
    <s:set name="brokerNamesHashMap" value="%{#aascIntlDataBean.brokerNames}"/>
    <s:set name="unitprice" value="%{#aascIntlDataBean.unitprice}"/>
    <s:set name="generateCI" value="%{#aascIntlDataBean.generateCI}"/>
    <s:set name="payerType" value="%{#aascIntlDataBean.payerType}"/>
    
    <%  
        
        AascIntlInfo IntlInfoBean = null;
        ArrayList<String> commItems = null;
        String shipFlagStr = request.getParameter("shipFlagStr");
        String intlSaveFlag = request.getParameter("intlSaveFlag");
        if(intlSaveFlag == null && "".equalsIgnoreCase(intlSaveFlag)){
            intlSaveFlag = "N";
        }
        int  carrierCode = Integer.parseInt(request.getParameter("carrierCode"));
        String intlCommActionFlag = (String)session.getAttribute("intlCommActionFlag");
        session.removeAttribute("intlCommActionFlag");
        if("".equalsIgnoreCase(intlCommActionFlag) && intlCommActionFlag == null){
            intlCommActionFlag = "N";
        }
        String intlSaveActionFlag = (String)session.getAttribute("intlSaveActionFlag");
        session.removeAttribute("intlSaveActionFlag");
        if("".equalsIgnoreCase(intlSaveActionFlag) && intlSaveActionFlag == null){
            intlSaveActionFlag = "N";
        }
        
        int clientId = (Integer)session.getAttribute("client_id");
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        
        AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1); // object of AascDAOFactory
        AascIntlShipmentsDAO aascIntlDAO = aascDAOFactory.getAascIntlShipmentsDAO();

        String orderNumber ="";
        String shipStatus = null;
       
        int orderNo = 0;
        orderNumber = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getOrderNumber();
        shipStatus = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getShipFlag();//Added in 6.71

        if(!shipFlagStr.equalsIgnoreCase("Y"))
        {
            commItems = aascIntlDAO.getIntlCommodityLineItems(clientId, locationId);
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
        pageContext.setAttribute("shipFlagStr",shipFlagStr);
        pageContext.setAttribute("shipStatus",shipStatus);
        pageContext.setAttribute("orderNumber",orderNumber);
    %>
    <br>
    <s:form name="aascINTLShipmentsForm" method="POST" action="AascIntlShipAction" onsubmit="return saveDetails()">
        <s:hidden name="actionType" id="actionType"/>
        <s:hidden name="commodityNo" id="commodityNo"/>
        <s:hidden name="selectLength" id="selectLength"/>
        <s:hidden name="opValue" id="opValue"/>
        <s:hidden name="intlSaveFlag" value="%{#intlSaveFlag}"/>
        <s:set name="carrierCode" value="%{#attr.carrierCode}"/>
        <s:hidden name="carrierCode" value="%{#carrierCode}"/>
        <s:set name="locationId" value="%{#attr.locationId}"/>
        <s:hidden name="locationId" id="locationId" value="%{#locationId}"/>
        <s:hidden name="actionStr" id="actionStr" value="%{#action}"/>
        <s:hidden name="shipment" id="shipment"/>
        <s:hidden name="orderNo" id="orderNo"/>
        <s:hidden name="shipFlag" id="shipFlag" value="%{#attr.shipStatus}"/>
        <s:set name="shipFlagStr" value="%{#attr.shipFlagStr}"/>
        <s:hidden name="shipFlagStr" value="%{#shipFlagStr}"/>
        <s:set name="intlSaveActionFlag" value="%{#attr.intlSaveActionFlag}"/>
        <s:hidden name="intlSaveActionFlag" value="%{#intlSaveActionFlag}"/>
        <s:hidden name="orderNumber" value="%{#attr.orderNumber}"/>
        <s:hidden name="declarationStmt" id="declarationStmt" value="%{#intlHeaderBean.declarationStmt}"/>
        <div class="row" align="center">
            <s:if test='%{#shipFlagStr == "Y" || #shipFlag == "Y"}' >
                <!--<img src="buttons/aascSaveOff1.png" alt="" name="save" id="saveId1" border="0">-->
                <button type="button" class="btn btn-success"  name="save" id="save1" disabled="disabled">Save<span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
            </s:if>
            <s:else>
                <!--<s:a  href="#" onclick="return saveDetails();"> <img src="buttons/aascSave1.png" name="save" id="saveId1" alt="" border="0"></s:a>--> 
                <button type="button" class="btn btn-success" onclick="return saveDetails();" name="save" id="save1">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
            </s:else>
                <!--<s:a href="javascript:window.close()" onclick="assignOrderNumber()"><img src="buttons/aascClose1.png"  name="close" id="closeId" alt="" border="0"  ></s:a>-->
                <button type="button" class="btn btn-danger" onclick="assignOrderNumber(); window.close();" name="close1" id="close1">Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                
            <s:if test='%{(#shipFlagStr == "Y" || #shipFlag == "Y") && #intlHeaderBean.generateCI == "Y"}' >
                <!--<s:a  href="#" onclick="viewDoc()"> <img src="buttons/aascViewPrint1.png" alt="" name="viewPrint" id="viewPrintId1" border="0"></s:a>--> 
                <button type="button" class="btn btn-primary"  onclick="viewDoc()" name="viewPrint" id="viewPrintId1">View/Print <span class="badge"><span class="glyphicon glyphicon-print"></span></span></button>
            </s:if>
            <s:else>
                <!--<img src="buttons/aascViewPrintOff1.png" name="viewPrint" id="viewPrintId1" alt="" border="0">-->
                <button type="button" class="btn btn-primary"  name="viewPrint" id="viewPrintId1" disabled="disabled">View/Print <span class="badge"><span class="glyphicon glyphicon-print"></span></span></button>
            </s:else>
        </div>
        <!-- <center>
            <s:if test='%{#shipFlagStr == "Y" || #shipFlag == "Y"}' >
                <img src="buttons/aascSaveOff1.png" alt="" name="save" id="saveId1" border="0">
            </s:if>
            <s:else>
                <s:a  href="#" onclick="return saveDetails();"> <img src="buttons/aascSave1.png" name="save" id="saveId1" alt="" border="0"></s:a> 
            </s:else>
            <s:a href="javascript:window.close()" onclick="assignOrderNumber()"><img src="buttons/aascClose1.png"  name="close" id="closeId" alt="" border="0"  ></s:a>
            <s:if test='%{(#shipFlagStr == "Y" || #shipFlag == "Y") && #intlHeaderBean.generateCI == "Y"}' >
                <s:a  href="#" onclick="viewDoc()"> <img src="buttons/aascViewPrint1.png" alt="" name="viewPrint" id="viewPrintId1" border="0"></s:a> 
            </s:if>
            <s:else>
                <img src="buttons/aascViewPrintOff1.png" name="viewPrint" id="viewPrintId1" alt="" border="0">
            </s:else>
        </center> -->
        <div class="row">
        <br/>
            <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #19b698; border-style: solid;width:98%;margin-left:1%;margin-right:1%">
                <div style="background-color:#19b698;margin-top:5px;margin-left:5px;height:30px">
                    <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;">Commodity Line Item</label>
                </div>
                <div class="row">
                <div class="col-sm-3">
                <br/>
                    <span class="dispalyFields"><s:property value="getText('SelectCommodityItem')"/> </span>
                    </div>
                    <div class="col-sm-4">
                    <br/>
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
                    </div>
                    <div class="col-sm-5">
                
                </div>
                </div>
                <br/>
                <div id = "commodityDetailDiv">
                    <div class="row">
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('Description/Product')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields">
                                <s:textfield name="description" id="description" cssClass="inputFields"  size="15" value="%{#commInfoBean.description}"/>
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('NumberOfPieces')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields">
                                <s:textfield name="NumberOfPieces" id="NumberOfPieces"  cssClass="inputFields"   size="15" maxlength="15" value="%{#commInfoBean.numberOfPieces}"/>
                            </span>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('CountryOfManufacture')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields">
                                <s:select list="#countryHashMap" name="CountryOfManufacture" id="CountryOfManufacture" listKey="key" listValue="value" headerValue="Select" headerKey=""
                                cssClass="inputFields" size="1" cssStyle="width:180px;" value="%{#commInfoBean.countryOfManufacture}" />
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('HarmonizedCode')"/></span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields">
                                <s:textfield name="HarmonizedCode" id="HarmonizedCode"  cssClass="inputFields" size="15" value="%{#commInfoBean.harmonizedCode}"/>
                            </span>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('Weight')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields">
                            <s:textfield name="Weight" cssClass="inputFields" id="Weight"  size="4"  value="%{#commInfoBean.weight}"/>
                            <select name="weightAs" class="inputFields" id="weightAs">
                                <option value=""> <s:property value="getText('Astotals')"/> </option>
                            </select>
                            lbs
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('Quantity')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields">
                                <s:textfield name="Quantity" cssClass="inputFields" id="Quantity" size="15" maxlength="15" value="%{#commInfoBean.quantity}"  onchange="calcUnitPrice()"/>
                            </span>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-sm-3">
                            <span class="dispalyFields"> <s:property value="getText('UnitOfMeasure')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields">
                                <s:select list="#{'DOZ':'Dozen','DPR':'Dozen Pair','EA':'Each','G':'Grams','GR':'Gross','KG':'Kilograms','KGM':'Kilogram','LB':'Pound','MG':'Milligram','CG':'Centigrams',
                                'NO':'Number','PCS':'Pieces','CFT':'Cubic Feet','GAL':'Gallon','AR':'Carat','CM':'Centimeters','CM3':'Cubic Centimeters','L':'Liter','M':'Meters','OZ':'Ounces',
                                'ML':'Milliliter','M2':'Square Meters','M3':'Cubic Meters'}" name="QuantityUnits" id="QuantityUnits" listKey="key" listValue="value"
                                        headerKey="" headerValue="-Select One-" cssClass="inputFields" value="%{#commInfoBean.quantityUnits}" /> 
                                <s:hidden name="UnitPrice" id="UnitPrice"  value="%{#unitprice}"/>
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('CustomsValue')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields">
                                <s:textfield name="CustomsValue" id="CustomsValue" cssClass="inputFields"  size="15"  value="%{#commInfoBean.customsValue}" onchange="calcUnitPrice()"/>
                                <select name="customsValueAs" id="customsValueAs" class="inputFields">
                                    <option value="">  <s:property value="getText('Astotals')"/></option>
                                </select>
                                <s:property value="getText('USD')"/>
                            </span>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-sm-3">
                            <span class="dispalyFields" size="15"><s:property value="getText('ExportLicenseNumber')"/></span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields">
                                <s:textfield name="ExportLicenseNumber" id="ExportLicenseNumber" cssClass="inputFields"  size="15" value="%{#commInfoBean.exportLicenseNumber}"/>
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields" size="15"><s:property value="getText('ExportLicenseExpirationDate')"/>(<s:property value="getText('YYYYMMDD')"/>)</span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields">
                                <s:textfield name="ExportLicenseExpirationDate" id="ExportLicenseExpirationDate" cssClass="inputFields"  size="15" maxlength="15" value="%{#commInfoBean.exportLicenseExpiryDate}"/>
                            </span>
                        </div>
                    </div>
                    <br/>
                    <div class="row" align="center">
                        <span class="dispalyFields">
                            <s:checkbox name="addOrEditItem" id="addOrEditItem" value="update" fieldValue="update"  />
                            <s:property value="getText('Save/updatethiscommodityitem')"/>
                        </span>
                    </div>
                    <br/>
                    <div class="row" align="center">
                        <!--<input type="BUTTON" name="addComm" id="addComm" value=" Add This Commodity Item " onclick="saveComm();">-->
                        <input type="button" class="btn btn-primary" value="  Add This Commodity Item " name="addComm" id="addComm" onclick="saveComm();">
                    </div>
                </div>                
                
                <br/>
                <div class="row">
                    <div class="col-sm-6">
                        <select name="commodityLine" id="commodityLine" size="5" class="inputs" style="width:90%;height:80px">
                        <option value="111"><s:property value="getText('Product')"/>    :     <s:property value="getText('Quantity')"/>    :     <s:property value="getText('Weight')"/>       :        <s:property value="getText('Value')"/></option>
                        <option value="222">------------------------------------------------------</option>
                        <%
                            AascIntlCommodityInfo commInfo1 = null;
                            AascIntlInfo intlInfo = null;
                            LinkedList coList = new LinkedList();
                            IntlInfoBean = (AascIntlInfo)(pageContext.getAttribute("intlInfoBean"));
                            if(shipFlagStr.equalsIgnoreCase("Y")){
                                logger.info("shipFlagStr is Y"); 
                                coList = IntlInfoBean.getIntlCommodityInfo();        
                            }else{
                                logger.info("shipFlagStr is not Y");
                                {
                                    intlInfo = aascIntlDAO.getIntlCommodityLineDetails(orderNumber, clientId, locationId);
                                    coList = intlInfo.getIntlCommodityInfo();
                                }
                            }
                            ListIterator CoInfoIterator = coList.listIterator();
                            String description = "";
                            String quantity = "";
                            double weight = 0.0;
                            double weightd=0.0;
                            double valued=0.0;
                            String value = "";
                            String descriptionStr = "";
                            int optionValue = 1;
                            double lineCustomValue = 0.0;
                            double lineWeightValue = 0.0;
                            int noPieces = 1;
     
                            while(CoInfoIterator.hasNext())
                            { 
                                AascIntlCommodityInfo aascIntlCommInfo = (AascIntlCommodityInfo)CoInfoIterator.next();
                                if(aascIntlCommInfo!=null)
                                {
                                    if(!shipFlagStr.equalsIgnoreCase("Y")){
                                        if(aascIntlCommInfo.getCarrierCode() == 0 || aascIntlCommInfo.getCarrierCode()==carrierCode)
                                        {
                                            // aascIntlCommInfo = (AascIntlCommodityInfo)CoInfoIterator.next();
                                        }
                                        else
                                        {
//                                            int opStatus = aascIntlDAO.deleteIntlShipments(orderNumber, clientId, locationId);
//                                            aascIntlCommInfo=null;
                                        }
                                    }
                                }
                                try{
                                    optionValue = aascIntlCommInfo.getCommodityNumber();
                                    logger.info("optionValue"+optionValue);
                                    description= aascIntlCommInfo.getDescription();
                                    logger.info("description"+description);
                                    // descriptionStr = description.substring(0,4);
                                    quantity = aascIntlCommInfo.getQuantity();
                                    logger.info("quantity"+quantity);
                                    weight = aascIntlCommInfo.getWeight();
                                    logger.info("weight"+weight);
                                    value=aascIntlCommInfo.getCustomsValue();
                                    valued =Double.parseDouble(value);
                                    logger.info("value"+value);
                                    noPieces = aascIntlCommInfo.getNumberOfPieces();
                                    lineCustomValue = lineCustomValue+(Double.parseDouble(value));
                                    lineWeightValue = lineWeightValue+weight;         
                               
                                if(description!=null && description!="")
                                {
                        %>
                        <option value="<%=optionValue%>"><%=description%>   :   <%=quantity%>   :   <%=(weight)%>   :  <%=(valued)%>  </option>      
                        <%
                                }
                                 }
                                catch(Exception e)
                                {
                                    logger.severe("Got exception in getting commodity values"+e.getMessage());
                                }
                            }
                            // session.setAttribute("InternationalShipments",aascIntlInfo);
                            logger.info("After Displaying Commodity values in Select Option"); 
                            logger.info("****###***#### lineCustomValue ****###***####"+lineCustomValue);
                            pageContext.setAttribute("lineCustomValue",lineCustomValue);
                            pageContext.setAttribute("lineWeightValue",lineWeightValue);
                        %>
                    </select>
                    </div>
                    <div class="col-sm-6">
                        <s:set name="lineCustomValue" value="%{#attr.lineCustomValue}"/>
                        <s:hidden name="commCustomValue" value="%{#lineCustomValue}"/>
                        <s:set name="lineWeightValue" value="%{#attr.lineWeightValue}"/>
                        <s:hidden name="commWeightValue" value="%{#lineWeightValue}"/>
                        <!--<input type="BUTTON" name="editComm" id="editComm" value="  Edit   Item  " onclick="editOptions();">
                        <input type="BUTTON" name="delComm" id="delComm" value="  Delete Item  " onclick="delOptions();">-->
                        <input type="button" class="btn btn-primary" value=" Edit   Item " name="editComm" id="editComm" onclick="editOptions();">
                        <input type="button" class="btn btn-primary" value=" Delete Item " name="delComm" id="delComm" onclick="delOptions();">
                    </div>
                </div>
                <br/>
            </div>
        </div>
      <div class="row">
        <br/>
        <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #19b698; border-style: solid;width:98%;margin-left:1%;margin-right:1%">
                <div style="background-color:#19b698;margin-top:5px;margin-left:5px;height:30px">
                    <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;"><s:property value="getText('Billingdetails')"/></label>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-2">
                        <span class="dispalyFields"><s:property value="getText('Billduties/taxes/feesto')"/></span>
                    </div>
                    <div class="col-sm-2">
                        
                            <s:select list="#{'SENDER':'SENDER','RECIPIENT':'RECIPIENT','THIRDPARTY':'THIRDPARTY'}" name="payerType" id="payerType" listKey="key" listValue="value" 
                            headerKey="" headerValue="-Select One-" cssClass="inputs" onchange="disableFields();onChange();" value="%{#payerType}" />
                        
                    </div>
                    <div class="col-sm-2">
                        <span class="dispalyFields">
                            <s:property value="getText('AccountNumber')"/>
                        </span>
                    </div>
                   <div class="col-sm-2">
                        <span class="dispalyFields">
                            <s:textfield name="AccNumber" id="AccNumber" cssClass="inputFields" size="15" disabled="disabled" value="%{#intlHeaderBean.intlMaskAccountNumber}"/>
                             <s:hidden name="AccNumberHid" id="AccNumberHid" value="%{#intlHeaderBean.intlAccountNumber}"/>
                        </span>
                    </div>      
              
                    <div class="col-sm-2" style="width: 120px">
                        <span class="dispalyFields"> <s:property value="getText('Country')"/></span>
                    </div>
                    <div class="col-sm-2">
                        <span class="dispalyFields">
                            <s:select list="#countryHashMap" name="countryCode" id="countryCode" listKey="key" listValue="value" headerValue="Select" headerKey="" 
                            cssClass="inputs" value="%{#intlHeaderBean.intlCountryCode}" />
                        </span>
                    </div>
                </div>
                <br/>
         </div>       
      </div>
        <div class="row">
        <br/>
        <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #19b698; border-style: solid;width:98%;margin-left:1%;margin-right:1%">
                <div style="background-color:#19b698;margin-top:5px;margin-left:5px;height:30px">
                    <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;"><s:property value="getText('ElectronicExportInformation')"/></label>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"> <s:property value="getText('ITN/FTRExemptionNumber')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="AESOrFTSRExemptionNumber" id="AESOrFTSRExemptionNumber" cssClass="inputFields"  size="30" value="%{#intlHeaderBean.intlExemptionNumber}"/>
                        </span>
                    </div>
                </div>
                <br/>
         </div>
         </div>
        <div class="row">
        <br/>
        <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #19b698; border-style: solid;width:98%;margin-left:1%;margin-right:1%">
                <div style="background-color:#19b698;margin-top:5px;margin-left:5px;height:30px">
                    <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;"><s:property value="getText('CustomsDocumentation')"/></label>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-4">
                        <span class="dispalyFields"  >
                            <s:checkbox name="generateCI" id="generateCI"  fieldValue="%{#intlHeaderBean.generateCI}"  value="%{#generateCI}" onchange="changeCI()"/>&nbsp;&nbsp;&nbsp;Commercial Invoice
                        </span>                        
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('TermsOfSale')"/></span>
                    </div>
                    <div class="col-sm-3">
                        
                            <s:select list="#{'FOB_OR_FCA':'Free Carrier(FCA/FOB)','CIF_OR_CIP':'Carriage Insurance Paid(CIP/CIF)','CFR_OR_CPT':'Carriage Paid To(CPT/C&F)',
                            'EXW':'Ex Works(EXW)','DDU':'Delivery Duty Unpaid(DDU)','DDP':'Delivery Duty Paid(DDP)','DAP':'Delivered at Place(DAP)','DAT':'Delivered at Terminal(DAT)'}"
                                    listKey="key" listValue="value" headerValue="--Select One--" headerKey="" name="TermsOfSale" id="TermsOfSale" cssClass="inputs" 
                                            value="%{#intlHeaderBean.intlTermsOfSale}" />
                       
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:property value="getText('TotalCustomsValue')"/>
                        </span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="TotalCustomsValue" id="TotalCustomsValue" cssClass="inputFields" size="15" readonly="true"/>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('FreightCharge')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <s:textfield name="FreightCharge" id="FreightCharge" cssClass="inputFields"  size="15" maxlength="15" value="%{#intlHeaderBean.intlFreightCharge}" onblur="totalCustomValue();"/>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('InsuranceCharge')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="InsuranceCharge" cssClass="inputFields" id="InsuranceCharge"  size="15" maxlength="15" value="%{#intlHeaderBean.intlInsuranceCharge}" onblur="totalCustomValue();"/>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('TaxesOrMiscellaneousCharge')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="TaxesOrMiscellaneousCharge" cssClass="inputFields" maxlength="15" id="TaxesOrMiscellaneousCharge" size="15" value="%{#intlHeaderBean.intlTaxMiscellaneousCharge}" onblur="totalCustomValue();"/>
                        </span>
                    </div>
                    <div class="col-sm-3"> 
                        <span class="dispalyFields" size="15"><s:property value="getText('Purpose')"/></span>
                    </div>
                    <div class="col-sm-3">
                        
                            <s:select list="#{'Sold':'Sold','Not Sold':'Not Sold','Gift':'Gift','Sample':'Sample','Repair and Return':'Repair and Return','Personal Effects':'Personal Effects'}" 
                                name="Purpose" id="Purpose" listKey="key" listValue="value" headerValue="--Select One--" headerKey="" cssClass="inputs" value="%{#intlHeaderBean.intlPurpose}" />
                        
                    </div>
                </div>
                <br/>
            </div>
         </div>
        <div class="row">
            <br/>
        <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #19b698; border-style: solid;width:98%;margin-left:1%;margin-right:1%">
                <div style="background-color:#19b698;margin-top:5px;margin-left:5px;height:30px">
                    <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;">EIN/Tax ID Details</label>
                </div>
                <div class="row">
                    <br/>
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('SenderTaxID')"/># </span>
                    </div>
                    <div class="col-sm-3">
                        <s:textfield name="SenderTINOrDUNS" id="SenderTINOrDUNS" cssClass="inputFields"  size="15" value="%{#intlHeaderBean.intlSedNumber}"/>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('RecipientTaxID')"/># </span>
                    </div>
                    <div class="col-sm-3">
                        <s:textfield name="ReceipentTINOrDUNS" id="ReceipentTINOrDUNS" cssClass="inputFields"  size="15" value="%{#intlHeaderBean.recIntlSedNumber}"/>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('SenderTaxType')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <s:select list="#{'BUSINESS_NATIONAL':'BUSINESS_NATIONAL','BUISNESS_STATE':'BUISNESS_STATE','BUSINESS_UNION':'BUSINESS_UNION',
                            'PERSONAL_NATIONAL':'PERSONAL_NATIONAL','PERSONAL_STATE':'PERSONAL_STATE'}" name="SenderTINOrDUNSType" id="SenderTINOrDUNSType" 
                                    listKey="key" listValue="value" cssClass="inputs" value="%{#intlHeaderBean.intlSedType}" />
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('RecipientTaxType')"/> </span>
                    </div>
                    <div class="col-sm-3">
                        <s:select list="#{'BUSINESS_NATIONAL':'BUSINESS_NATIONAL','BUISNESS_STATE':'BUISNESS_STATE','BUSINESS_UNION':'BUSINESS_UNION',
                            'PERSONAL_NATIONAL':'PERSONAL_NATIONAL','PERSONAL_STATE':'PERSONAL_STATE'}" name="ReceipentTINOrDUNSType" id="ReceipentTINOrDUNSType" 
                                    listKey="key" listValue="value" cssClass="inputs" value="%{#intlHeaderBean.recIntlSedType}" />
                    </div>
                </div>
                <br/>
           </div>
        </div>
        <div class="row">
            <br/>
        <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #19b698; border-style: solid;width:98%;margin-left:1%;margin-right:1%">
                <div style="background-color:#19b698;margin-top:5px;margin-left:5px;height:30px">
                    <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;"><s:property value="getText('ImporterDetails')"/></label>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('SelectImporter')"/></span>
                    </div>    
                    <div class="col-sm-3">
                        
                             <s:select list="#impNamesHashMap" name="selImporterName" id="selImporterName" listKey="key" listValue="value" headerKey="" headerValue="Select"
                            cssClass="inputs" onchange="getIntlImporterDetails();" value="%{#intlHeaderBean.importerName}" />
                                            </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('Name')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="importerName" id="importerName" cssClass="inputFields"  size="22" value="%{#intlHeaderBean.importerName}"/>
                        </span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('ImporterTaxID')"/>#</span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="ImporterTINOrDUNS" id="ImporterTINOrDUNS" cssClass="inputFields"  size="15" value="%{#intlHeaderBean.impIntlSedNumber}"/>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('CompanyName')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="importerCompName" id="importerCompName" cssClass="inputFields"  size="22" value="%{#intlHeaderBean.importerCompName}"/>
                        </span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('ImporterTaxType')"/></span>
                    </div>
                    <div class="col-sm-3">
                        
                        <s:select list="#{'BUSINESS_NATIONAL':'BUSINESS_NATIONAL','BUISNESS_STATE':'BUISNESS_STATE','BUSINESS_UNION':'BUSINESS_UNION',
                            'PERSONAL_NATIONAL':'PERSONAL_NATIONAL','PERSONAL_STATE':'PERSONAL_STATE'}" name="ImporterTINOrDUNSType" id="ImporterTINOrDUNSType" 
                                    listKey="key" listValue="value" headerValue="--Select One--" headerKey="" cssClass="inputs" value="%{#intlHeaderBean.impIntlSedType}" />
                
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('PhoneNumber')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="importerPhoneNum" id="importerPhoneNum" cssClass="inputFields"  size="22" value="%{#intlHeaderBean.importerPhoneNum}"/>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('AddressLine1')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="importerAddress1" id="importerAddress1" cssClass="inputFields"  size="22"  value="%{#intlHeaderBean.importerAddress1}"/>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('AddressLine2')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="importerAddress2" id="importerAddress2" cssClass="inputFields"  size="22" value="%{#intlHeaderBean.importerAddress2}"/>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                     <span class="dispalyFields"><s:property value="getText('City')"/>, </span>
                     <span class="dispalyFields"><s:property value="getText('State')"/>, </span>
                     <span class="dispalyFields"><s:property value="getText('PostalCode')"/>, </span>
                     <span class="dispalyFields"><s:property value="getText('CountryCode')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="importerCity" id="importerCity" cssClass="inputFields"  size="3" value="%{#intlHeaderBean.importerCity}"/>
                            <s:textfield name="importerState" id="importerState" cssClass="inputFields"  size="1" value="%{#intlHeaderBean.importerState}"/>
                            <s:textfield name="importerPostalCode" id="importerPostalCode" cssClass="inputFields"  size="3" value="%{#intlHeaderBean.importerPostalCode}"/>
                            <s:textfield name="importerCountryCode" id="importerCountryCode" cssClass="inputFields"  size="1" value="%{#intlHeaderBean.importerCountryCode}"/>
                        </span>
                    </div>
                    <div class="col-sm-3"></div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:checkbox name="addOrEditImporter" id="addOrEditImporter" value="update" fieldValue="update"  />&nbsp;&nbsp;
                            <s:property value="getText('Save/updatethisImporterDetail')"/>
                        </span>
                    </div>
                </div>
                <br/>
         </div>
         </div>
        <div class="row">
            <br/>
        <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #19b698; border-style: solid;width:98%;margin-left:1%;margin-right:1%">
                <div style="background-color:#19b698;margin-top:5px;margin-left:5px;height:30px">
                    <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;"><s:property value="getText('BrokerDetails')"/></label>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"> <s:property value="getText('SelectBroker')"/></span>
                    </div>
                    <div class="col-sm-3">
                            <s:select list="#brokerNamesHashMap" name="selBrokerName" id="selBrokerName" listKey="key" listValue="value" headerKey="" headerValue="Select"
                            cssClass="inputs" onchange="getIntlBrokerDetails();" value="%{#intlHeaderBean.brokerName}" />
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"> <s:property value="getText('Name')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="brokerName" id="brokerName" cssClass="inputFields"  size="22" value="%{#intlHeaderBean.brokerName}"/>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('CompanyName')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="brokerCompName" id="brokerCompName"  cssClass="inputFields"  size="22" value="%{#intlHeaderBean.brokerCompName}"/>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"> <s:property value="getText('PhoneNumber')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="brokerPhoneNum" id="brokerPhoneNum" cssClass="inputFields"  size="22" value="%{#intlHeaderBean.brokerPhoneNum}" />
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('AddressLine1')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="brokerAddress1" id="brokerAddress1" cssClass="inputFields"  size="22"  value="%{#intlHeaderBean.brokerAddress1}"/>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"> <s:property value="getText('AddressLine2')"/></span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="brokerAddress2" id="brokerAddress2" cssClass="inputFields"  size="22" value="%{#intlHeaderBean.brokerAddress2}"/>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                    <span class="dispalyFields"><s:property value="getText('City')"/>, </span>
                     <span class="dispalyFields"><s:property value="getText('State')"/>, </span>
                     <span class="dispalyFields"><s:property value="getText('PostalCode')"/>, </span>
                     <span class="dispalyFields"><s:property value="getText('CountryCode')"/></span></div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="brokerCity" id="brokerCity" cssClass="inputFields"  size="3"  value="%{#intlHeaderBean.brokerCity}"/>
                            <s:textfield name="brokerState" id="brokerState" cssClass="inputFields"  size="1" value="%{#intlHeaderBean.brokerState}"/>
                            <s:textfield name="brokerPostalCode" id="brokerPostalCode" cssClass="inputFields"  size="3" value="%{#intlHeaderBean.brokerPostalCode}" />
                            <s:textfield name="brokerCountryCode" id="brokerCountryCode" cssClass="inputFields"  size="1" value="%{#intlHeaderBean.brokerCountryCode}" />
                        </span>
                    </div>
                    <div class="col-sm-3"></div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:checkbox name="addOrEditBroker" id="addOrEditBroker" value="update" fieldValue="update"  />&nbsp;&nbsp;
                            <s:property value="getText('Save/updatethisbrokerDetail')"/>
                        </span>
                    </div>
                </div>
                <br/>
         </div>
         </div>
        
        <%
            String  service = request.getParameter("service");
            try{
                String str = service.substring(0,1);
            }catch(Exception e){
                service = "";
            }
            pageContext.setAttribute("service",service);
            logger.info("******************************** serivce : "+service);
        %>
        <s:hidden name="service" id="serviceID" value="%{#attr.service}"/>
        <%
            if(service.contains("FREIGHT") || service.contains("Freight") || (service.indexOf("freight") != -1))
            {
        %>
       <div class="row"> 
        </br>
        <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #19b698; border-style: solid;width:98%;margin-left:1%;margin-right:1%">
                <div style="background-color:#19b698;margin-top:5px;margin-left:5px;height:30px">
                    <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;"><s:property value="getText('CommodityLineItem')"/></label>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"> <s:property value="getText('FreightServiceDetails')"/>: </span>
                    </div>    
                </div>
                <br/>
               <!-- Srisha modified code for making packingListEnclosed value default as false for bug #3377 --> 
                <s:if test="%{#intlHeaderBean.packingListEnclosed == '' || #intlHeaderBean.packingListEnclosed == null}" >
                  <s:set name= "packingListEnclosed" value="false" />
                </s:if>
                <s:else>
                   <s:set name= "packingListEnclosed" value="%{#intlHeaderBean.packingListEnclosed}" />
                </s:else> 
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"> <s:property value="getText('PackingListEnclosed')"/>: </span>
                    </div>
                    <div class="col-sm-3">
                            <s:select list="#{'true':'True','false':'False'}" name="packingListEnclosed" id="packingListEnclosedID" listKey="key" listValue="value" headerValue="Select" 
                            headerKey="" cssClass="inputs" value="%{packingListEnclosed}" />
                    </div>
                </div>
                 <!-- code ends here --> 
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"><s:property value="getText('ShippersLoadAndCount')"/> : </span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                           <s:if test="%{#intlHeaderBean.shippersLoadAndCount == 0}" >
				<s:textfield name="shippersLoadAndCount" id="shippersLoadAndCountID"  cssClass="inputFields"  size="15" maxlength="15"/>    
                           </s:if>
			   <s:else>
			        <s:textfield name="shippersLoadAndCount" id="shippersLoadAndCountID" cssClass="inputFields"  size="15" maxlength="15" value="%{#intlHeaderBean.shippersLoadAndCount}"/>
			   </s:else>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-3">
                        <span class="dispalyFields"> <s:property value="getText('BookingConfirmationNumber')"/>: </span>
                    </div>
                    <div class="col-sm-3">
                        <span class="dispalyFields">
                            <s:textfield name="bookingConfirmationNumber" id="bookingConfirmationNumberID" cssClass="inputFields"  size="15" maxlength="15" value="%{#intlHeaderBean.bookingConfirmationNumber}"/>
                        </span>
                    </div>
                </div>
                <br/>
         </div>
       </div>  
        <%
            }
        %>
        <div class="row" align="center">
        <br/>
            <s:if test='%{#shipFlagStr == "Y" || #shipFlag == "Y"}' >
                <!--<img src="buttons/aascSaveOff1.png" alt="" name="save" border="0">-->
                <button type="button" class="btn btn-success" value=" Save " name="save" id="save2" disabled="disabled">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
            </s:if>
            <s:else>
                <!--<a  href="#" onclick="return saveDetails();"> <img src="buttons/aascSave1.png" name="save" id="saveId2" alt="" border="0"></a> -->
                <button type="button" class="btn btn-success" value=" Save " onclick="return saveDetails();" name="save" id="save2">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
            </s:else>
            <!--<a href="javascript:window.close()" onclick="assignOrderNumber()" ><img src="buttons/aascClose1.png" alt="" name="close" id="closeId2" border="0"  ></a>-->
            <button type="button" class="btn btn-danger" onclick="assignOrderNumber(); window.close();" name="close2" id="close2">Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
        </div>
    </s:form>
    </div>
     <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
</html>
