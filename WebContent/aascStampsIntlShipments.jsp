
<%/*==============================================================================================+
|  DESCRIPTION                                                                                    |
|    JSP to Save Stamps International Shipment options                                        |
|    Author Mahesh Reddy V                                                                            |
|    Version   1                                                                                  |                                                                              
|    Creation 27-10-2015                                                                         |
History:
    23/11/2015      Suman G     Added <br/> to fix #3999
    24/11/2015      Mahesh V    Added code for validations of international fields
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
  
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:property value="getText('StampsInternationalShipments')"/></title>
        
        <link type="text/css" rel="stylesheet" href="aasc_ss.css">
        <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
        <script type="text/javascript" src="aascStampsIntlShipments_js.js"></script>
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
        static Logger logger = AascLogger.getLogger("aascStampsIntlShipments.jsp");
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
    <s:set name="currencyCodeHashMap" value="%{#aascIntlDataBean.currencyCodeList}"/>
    <%  
        
    
        AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1); // object of AascDAOFactory
        AascIntlShipmentsDAO aascIntlShipmentsDAO = aascDAOFactory.getAascIntlShipmentsDAO();   
        
        AascIntlInfo IntlInfoBean = null;
        
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        int clientId = (Integer)session.getAttribute("client_id");
        ArrayList<String> commItems = null;
        HashMap UOM = null;
        Set set=null;
        Iterator iterator=null;
        
  
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
    <s:form method="POST" name="aascStampsIntlShipmentsForm" action="AascStampsIntlShipAction" onsubmit="return saveDetails()">
        <s:hidden name="actionType" id="actionType"/>
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
                        <select name="selCommItems" id="selCommItems" class="inputFields" onchange="getIntlCommodityLineDetails();">
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
                                <s:textfield name="Description" id="DescriptionID" cssClass="inputFields"   value="%{#commInfoBean.description}"/>
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('HarmonizedCode')"/> </span>
                        </div>
                        <div class="col-sm-3">
                            <span>
                                <s:textfield name="HarmonizedCode" id="HarmonizedCodeID" cssClass="inputFields" value="%{#commInfoBean.harmonizedCode}"/>
                            </span>
                        </div>
                     </div>
                   </div>  
                   <br/>
                   <div class="row">
                     <div class="col-sm-12">
                        <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('CountryOfOrigin')"/> </span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                            <s:select list="#countryHashMap" name="CountryOfManufacture" id="CountryOfManufacture" listKey="key" listValue="value" headerValue="Select" headerKey=""
                                cssClass="inputFields" value="%{#commInfoBean.countryOfManufacture}" />
                            </span>
                        </div>
                        <div class="col-sm-3">
                            <span class="dispalyFields"> <s:property value="getText('NumberOfUnits')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                                <s:textfield name="Quantity" id="Quantity" cssClass="inputFields" value="%{#commInfoBean.quantity}"/>
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
                                <s:textfield name="UnitPrice" cssClass="inputFields" id="UnitPrice" value="%{#commInfoBean.unitPrice}"/>
                            </span>
                        </div>
                         <div class="col-sm-3">
                            <span class="dispalyFields"><s:property value="getText('PackageWeight')"/>*</span>
                        </div>
                        <div class="col-sm-3">
                            <span >
                                <s:textfield name="Weight" id="Weight" cssClass="inputFields"  size="12" value="%{#commInfoBean.weight}"/></span>
                            <span>
                                <s:select list="#{'LBS':'LBS','OZS':'OZS'}" name="UOM" id="UOM" cssClass="inputFields" value="%{#commInfoBean.packageWeightUom}" />
                            </span>
                        </div>
                    </div>
                </div>
             </div>   
                <div class="row" id="USRow">
                <br/>
                    <div class="col-sm-12">
                        <div class="col-sm-3">
                        </div>
                        <div class="col-sm-3">
                            
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
                    
                        <input type="BUTTON" class="btn btn-primary" name="addComm" id="addComm" onclick="saveProductDetails();" value=" Add This Commodity Item" />
                    </div>
                </div>
                <div class="row">
                <br/>
                    <div class="col-sm-12">
                        <div class="col-sm-8">
                            <select name="commodityLine" id="commodityLine" size="5" style="width:90%;height:80px">
                            <option value="111" selected="selected">Product Description  : Package Weight  : Number of Units : Price Per Unit  </option>
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
                                 double weight = 0.0;
                              double lineWeightValue = 0.0;

                                double lineCustomValue = 0.0; 
                                double value = 0.0;
                               double weightd=0.0;
                                           double valued=0.0;


                                while(CoInfoIterator.hasNext())
                                {
                                    AascIntlCommodityInfo aascIntlCommInfo = (AascIntlCommodityInfo)CoInfoIterator.next();
                                    weight = aascIntlCommInfo.getWeight();
                                  weightd = weight;
                                  logger.info("weight"+weight);
                                    if(aascIntlCommInfo!=null)
                                    {
                                        if(!shipFlagStr.equalsIgnoreCase("Y")){
                                            if(aascIntlCommInfo.getCarrierCode()==carrierCode)
                                            {
                                                // aascIntlCommInfo = (AascIntlCommodityInfo)CoInfoIterator.next();
                                            }
                                            else
                                            {
                                            //    int opStatus = aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
                                            //    aascIntlCommInfo=null;
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
                                        weight = aascIntlCommInfo.getWeight();
                                        lineWeightValue = lineWeightValue+(weight);
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
                                    try {
									if(description!="" && description!=null)
                                    {
                            %>
                            <option value="<%=optionValue%>"><%=description%>  :  <%=weight%>  :  <%=noUnits%>  :  <%=pricePerUnit%>  </option>
                            <%
                                        if(tariffCode.equalsIgnoreCase("-------"))
                                        {
                                            tariffCode=null;
                                        }
                                    }
                                    value=Double.parseDouble(aascIntlCommInfo.getQuantity())*Double.parseDouble(aascIntlCommInfo.getUnitPrice()) ;
                                    lineCustomValue = lineCustomValue + value;
                                   }catch(Exception exp)
                                    {
                                        logger.info("exception in getting values."+exp.getMessage());
                                    }
                                }
                                logger.info("After Displaying Commodity values in Select Option");
                                logger.info("****###***#### lineCustomValue ****###***####"+lineCustomValue);
                                pageContext.setAttribute("lineCustomValue",lineCustomValue);
                            %>
                        </select>
                        <s:hidden name="commCustomValue" value="%{#attr.lineCustomValue}"/> 
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
 
                            <%
                  String  shipFromPhoneNum1 = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getShipFromPhoneNumber1();
                  pageContext.setAttribute("shipFromPhoneNum1",shipFromPhoneNum1);
                %>
                <s:set name="shipFromPhoneNum1" value="%{#attr.shipFromPhoneNum1}"/>
                <s:if test='%{#intlHeaderBean.shipFromPhone == "" && intlSaveFlag == "N" && intlSaveActionFlag == "N"}' >
                    <s:hidden name="ShipFromPhone"  cssClass="inputFields"   value="%{#shipFromPhoneNum1}"/>
                </s:if>
                <s:else>
                    <s:hidden name="ShipFromPhone"  cssClass="inputFields"   value="%{#intlHeaderBean.shipFromPhone}"/>
                </s:else>
                
                <%
                    String shipFromAttentionName1 = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getShipFromCompanyName();
                    pageContext.setAttribute("shipFromAttentionName1",shipFromAttentionName1);
                %>

       
  <div class="row" id="ComInvoiceID">
    <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%">
          <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px">
              <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;">Customs Document Details</label>
          </div>
          <div class="row" id="CMInvoice">
            <font color="Green"></font>
          </div>
        
          <div class="row" id="InvoiceDetailsID2">
          <br/>
          <div class="col-sm-12">
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('ContentType')"/>*</span>
            </div>
            <div class="col-sm-3">
               <span >
                    <s:select list="#{'Gift':'Gift','Document':'Document','Returned Goods':'Returned Goods','Merchandise':'Merchandise','Other':'Other',
                    'Humanitarian Donation':'Humanitarian Donation','Dangerous Goods':'Dangerous Goods','Commercial Sample':'Commercial Sample'}" 
                                    name="Purpose" listKey="key" listValue="value" headerValue="Select" headerKey="" cssClass="inputFields"  
                                         value="%{#intlHeaderBean.intlPurpose}" />
                </span>
            </div>
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('OtherDescribe')"/></span>
            </div>
            <div class="col-sm-3">
                <s:textfield name="otherDescribe" id="otherDescribe" cssClass="inputFields" value="%{#intlHeaderBean.otherDescribe}"/>
            </div>
            
            </div>
          </div>
          <div class="row" id="CIDetailsRow1">
            <br/>
            <div class="col-sm-12">
            <c:catch var="ex">
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('Comments')" /></span>
            </div>
            <div class="col-sm-3">
                <s:textfield name="comments" id="comments" cssClass="inputFields" value="%{#intlHeaderBean.intlComments}"/>
            </div>
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('InvoiceNumber')"/></span>
            </div>
            <c:choose>
            <div class="col-sm-3">
                <span ><s:textfield id="InvoiceNumber" cssClass="inputFields" name="InvoiceNumber" value="%{#intlHeaderBean.intlCustomerInvoiceNumber}" /></span>
            </div>
            </c:choose>
            </c:catch>
            </div>
          </div>
 
     <div class="row" id="CIDetailsRow4">
     <br/>
         <div class="col-sm-12">
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('stampsLicenseNumber')"/></span>
            </div>
            <div class="col-sm-3">
                <span>
                <s:textfield name="stampsLicenseNumber" id="stampsLicenseNumber" cssClass="inputFields" value="%{#intlHeaderBean.stampsLicenseNumber}"/>
                </span>
            </div>
            
            <div class="col-sm-3">
                <span class="dispalyFields"><s:property value="getText('stampsCertificateNumber')"/></span>
            </div>
            <div class="col-sm-3">
                <span >
                <s:textfield name="stampsCertificateNumber" id="stampsCertificateNumber" cssClass="inputFields" value="%{#intlHeaderBean.stampsCertificateNumber}"/>
                </span>
            </div>
            
         </div>
     </div>
   <br/>
      
     </div>
      <br/>
      
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
