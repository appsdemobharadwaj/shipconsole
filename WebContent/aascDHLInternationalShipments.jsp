 

<%/*==============================================================================================+
|  DESCRIPTION                                                                                    |
|    JSP to Save International Shipments options                                                  |
|    Author G S Shekar                                                                             |
|    Version   1                                                                                  |
|    Creation 27-OCT-2015                                                                         |
History:

    20/11/2015  Y Pradeep       Added width attribute for custom invoice text field and background attribute for body tag to make complete page backgroud to same color to resolve bugs #3994 and #3988 respectively.
+==================================================================================================*/%>
<%@ taglib uri="/struts-tags" prefix="s" %>

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
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:property value="getText('DHLInternationalShipments')"/></title>
        <link type="text/css" rel="stylesheet" href="aasc_ss.css">
        <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
        <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    
        <script type="text/javascript" language="javascript" SRC="aascDHLInternationalShipments_js.js">
        </script>
    </head>
    <body onload="loadDetails();" style="background-color:#ADADAD;">
    <div  class="container-fluid" style="width:100%;background-color:#ADADAD;">
    <%!
        static Logger logger = AascLogger.getLogger("aascDHLInternationalShipments.jsp");
    %>
    <%
    String url = request.getContextPath();
        
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
    
    <%
    String payerType=request.getParameter("payerType");
    pageContext.setAttribute("payerTypeTmp",payerType);
    %>
    <s:set name="aascIntlDataBean" value="AascIntlDataBean" />
    <s:set name="intlInfoBean" value="%{#aascIntlDataBean.intlInfoBean}" />
    <s:set name="intlHeaderBean" value="%{#aascIntlDataBean.intlHeaderBean}" />
    <s:set name="commInfoBean" value="%{#aascIntlDataBean.aascCommodityBean}"/>
    <s:set name="countryHashMap" value="%{#aascIntlDataBean.countryList}"/>
    
    <s:set name="unitprice" value="%{#aascIntlDataBean.unitprice}"/>
    
    <s:set name="payerType" value="%{#intlHeaderBean.intlPayerType}"/>
    
    <s:if test="%{#payerType==null||#payerType==''}">
        <s:set name="payerType" value="%{#attr.payerTypeTmp}"/>
    </s:if>
    
    <s:set name="accountNumber" value="%{#intlHeaderBean.intlAccountNumber}"/>
    
    <!--<s:if test="%{#accountNumber==null||#accountNumber==''}">
        <s:set name="accountNumber" value="%{#attr.accountNumberTmp}"/>
    </s:if>-->
    
    <s:set name="termsOfTrade" value="%{#intlHeaderBean.termsOfTrade}"/>
    
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
    <s:form name="aascDHLINTLShipmentsForm" method="POST" action="AascDHLIntlShipAction" onsubmit="return checkSaveDetails()">
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
                <button type="button" class="btn btn-success" onclick="return checkSaveDetails();" name="save" id="save1">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
            </s:else>
                <!--<s:a href="javascript:window.close()" onclick="assignOrderNumber()"><img src="buttons/aascClose1.png"  name="close" id="closeId" alt="" border="0"  ></s:a>-->
                <button type="button" class="btn btn-danger" onclick="assignOrderNumber(); window.close();" name="close1" id="close1">Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                
          <!--  <s:if test='%{(#shipFlagStr == "Y" || #shipFlag == "Y") && #intlHeaderBean.generateCI == "Y"}' >-->
                <!--<s:a  href="#" onclick="viewDoc()"> <img src="buttons/aascViewPrint1.png" alt="" name="viewPrint" id="viewPrintId1" border="0"></s:a>--> 
                <!--<button type="button" class="btn btn-primary"  onclick="viewDoc()" name="viewPrint" id="viewPrintId1">View/Print <span class="badge"><span class="glyphicon glyphicon-print"></span></span></button>
            </s:if>
            <s:else>-->
                <!--<img src="buttons/aascViewPrintOff1.png" name="viewPrint" id="viewPrintId1" alt="" border="0">-->
                <!--<button type="button" class="btn btn-primary"  name="viewPrint" id="viewPrintId1" disabled="disabled">View/Print <span class="badge"><span class="glyphicon glyphicon-print"></span></span></button>
            </s:else>
            -->
        </div>
        
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
                                cssClass="inputFields" size="1" cssStyle="width:180px;" value="%{#commInfoBean.countryOfManufacture}" onchange="onChangeAlert()"/>
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('Commodity Code')"/></span>
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
                                <s:textfield name="CustomsValue" id="CustomsValue" cssClass="inputFields" size="15" cssStyle="width:40%" value="%{#commInfoBean.customsValue}" onchange="calcUnitPrice()"/>
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
                            <s:textfield name="AccNumber" id="AccNumber" cssClass="inputFields" size="15" disabled="disabled" value="%{#intlHeaderBean.intlAccountNumber}"/>
                        </span>
                    </div>
                    <div class="col-sm-2">
                        <span class="dispalyFields"> <s:property value="getText('TermsOfTrade')"/></span>
                    </div>
                    <div class="col-sm-2">
                        <span class="dispalyFields">
                            <s:select list="#{'DDP':'Delivered Duty Paid','CIP':'Carriage and Insurance Paid To','CPT':'Carriage Paid To','DAP':'Delivered At Place','DAT':'Delivered At Terminal','EXW':'Ex Works','FCA':'Free Carrier'}" name="TermsOfTrade" id="TermsOfTrade" listKey="key" listValue="value" headerValue="Select" headerKey="" 
                            cssClass="inputs" value="%{#termsOfTrade}" />
                        </span>
                    </div>
                </div>
                <br/>
         </div>       
      </div>
      <br/>
         
        
        <div class="row" align="center" style="background-color:#ADADAD;">
        <br/>
            <s:if test='%{#shipFlagStr == "Y" || #shipFlag == "Y"}' >
                <!--<img src="buttons/aascSaveOff1.png" alt="" name="save" border="0">-->
                <button type="button" class="btn btn-success" value=" Save " name="save" id="save2" disabled="disabled">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
            </s:if>
            <s:else>
                <!--<a  href="#" onclick="return saveDetails();"> <img src="buttons/aascSave1.png" name="save" id="saveId2" alt="" border="0"></a> -->
                <button type="button" class="btn btn-success" value=" Save " onclick="return checkSaveDetails();" name="save" id="save2">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
            </s:else>
            <!--<a href="javascript:window.close()" onclick="assignOrderNumber()" ><img src="buttons/aascClose1.png" alt="" name="close" id="closeId2" border="0"  ></a>-->
            <button type="button" class="btn btn-danger" onclick="assignOrderNumber(); window.close();" name="close2" id="close2">Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
        </div>
    </s:form>
    </div>
     <div style="position:relative;top:160px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
</html>
