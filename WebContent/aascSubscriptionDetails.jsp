<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%> 

<%@ page import="com.aasc.erp.bean.*"%>

<%/*========================================================================+
@(#)aascSubscriptionDetails.jsp 01/03/2016
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Showing Rate Details.
* @version 1
* @author       Suman G
* @history
*  
*/
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link href="https://kendo.cdn.telerik.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script language="javascript" src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    <script src="kendo.all.min.js" type="text/javascript"></script>
    
    <title>Subscription Details</title>
  </head>

<body class="gradientbg" style="height:100%" onload="displayAlert();">
    
    <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
    %>
    
        <table width="100%"  align="center" >
            <tr>
                <td width="400" align="left"> </td>
                <td><font color="red" size= "2"></font></td>
                <td width="100">&nbsp;&nbsp;</td>
            </tr>
        </table>
        <jsp:useBean id="ratesList" class="java.util.LinkedList"/>
        <c:catch var="exception1">
            <s:set name="ratesList" value="%{#session.ratesList}" />
        </c:catch>
        <s:if test="%{#exception1 != null}">
            <s:bean name="java.util.LinkedList" id="ratesList" >
                <s:param name="ratesList" value="%{''}" />
            </s:bean>
        </s:if>
        
        <jsp:useBean id="aascRatesTable" class="com.aasc.erp.bean.AascRatesTable" />        
        <div class="form-group">
        
        <fieldset class="scheduler-border" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%">
            <div style="background-color:#3D566D;margin-top:5px;margin-left:5px;height:30px" align="center">
                       <label style="color:#ffffff;font-size:20px; height:100%;padding-top:1px; " >Pricing List</label>
                    </div>
                    </br>

        
                <table class="table" style="overflow-x:auto;" border="1">
                <thead>
                <tr>
                    <th align="center" nowrap style="background-color:#19b698; color:#ffffff;font-size: 14px;font-weight: bold;width:15%;" ><s:property value="getText('CustomerType')"/></th>
                    <th align="center" nowrap style="background-color:#19b698; color:#ffffff;font-size: 14px;font-weight: bold;width:10%;" ><s:property value="getText('MonthlyTransactionRange')"/></th>
                    <th align="center" nowrap style="background-color:#19b698; color:#ffffff;font-size: 14px;font-weight: bold;width:10%;" ><s:property value="getText('DurationType')"/></th>
                    <th align="center" nowrap style="background-color:#19b698; color:#ffffff;font-size: 14px;font-weight: bold;width:10%;" ><s:property value="getText('TotalAmount')"/></th>
                    <th align="center" nowrap style="background-color:#19b698; color:#ffffff;font-size: 14px;font-weight: bold;width:10%;" ><s:property value="getText('PricePerPkg')"/></th>
                </tr>
                </thead>
                    
                    <s:set name="index" value="%{1}" />
                    <s:iterator id="aascRatesTable" value="%{#ratesList}">
                        <tr>
                            <td align="center" class="dispalyFieldsUPSAddrDetails">
                                <s:property value="%{#aascRatesTable.customerType}" />
                            </td>
                            <td align="center" class="dispalyFieldsUPSAddrDetails">
                                <s:property value="%{#aascRatesTable.transactionRange}" />
                            </td>
                            <td align="center" class="dispalyFieldsUPSAddrDetails">
                                <s:property value="%{#aascRatesTable.durationType}" />
                            </td>
                            <td align="center" class="dispalyFieldsUPSAddrDetails">
                                <s:if test="%{#aascRatesTable.totalAmount != 0}" >
                                    <s:property value="%{#aascRatesTable.totalAmount}" />
                                </s:if>
                            </td>
                            <td align="center" class="dispalyFieldsUPSAddrDetails">
                                <s:if test="%{#aascRatesTable.pricePerPkg != 0}" >
                                    <s:property value="%{#aascRatesTable.pricePerPkg}" />
                                </s:if>
                            </td>
                        </tr>
                        
                    </s:iterator>
                    
                </table>
                <br/>
                <center>
                <button type="button" class="btn btn-danger" name="close" id="closeId" onclick="javascript:window.close()" value="Cancel" alt="" align="middle"> Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                   </center>
                   <div class="row"><br/></div>
            
        </fieldset>
        </div>
         <div style="position:relative;top:40px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
    
</html>
