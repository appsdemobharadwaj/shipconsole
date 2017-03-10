
<%/*========================================================================+
@(#)aascPrinerSetup.jsp 28/11/2014
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
+===========================================================================*/
/***
* JSP For setting Printers based on type of lable.
* @version 1
* @author Y Pradeep
* history
*
* 23/06/2015        Y Pradeep       Added this file for Printer Setup.
* 13/07/2015        Y Pradeep       Modified code for saving and added Print Server IP Address field and related code.
* 21/07/2015        Rakesh K        Removed image in screen.
* 24/07/2015        Rakesh K        Modified Code to work application in offline.
* 24/07/2015        Dinakar G        Modified Code to fix bug #3244
* 04/08/2015        Rakesh K        Modified Code bootstrap css file work across all browsers.
* 05/08/2015        Dinakar G        Modified Code to fix bug #3244
* 14/08/2015        Dinakar G         Modified Code UI alignment(same as) #3406.
* 24/08/2015        Y Pradeep       Added clienId hidden variable and assiging value from request required while saving data in role 1.
*  26/08/2015       Dinakar G       Added id's for Automation testing.
26/08/2015  Rakesh K       Added code to solve 3496.
* 01/11/2015        Mahesh V        Added code for Stamps.com Integration.
* 11/03/2016        Y Pradeep       Removed Server IP Address field as it is not required.
*/
%>

<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>  
<%@ taglib uri="/struts-tags" prefix="s"%>

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
    
  <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->
    
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><s:property value="getText('SCPrinterSetup')"/></title>
        <script  type="text/javascript"  src="aascPrinterSetup_js.js"> </script>
        <link type="text/css" rel="stylesheet" href="aasc_ss.css"></link>
        <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
        <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
         
        <style type="text/css">
            html{height:100%}
           
            #ClassWidth { 
                width: 20em; 
            } 
            #ClassWidth2 { 
                width: 16em; 
            } 
            #ClassWidth3 { 
                width: 6em; 
            } 
         
        </style>
    </head>
    <body style="background-color:#BDBDBD" onload="return DisableField();">
    <div class="container-fluid">
    
       <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
    %>
           
    <% try{
    %>        
        <s:form name="PrinterSetupForm" method="POST" action="PrinterSetupAction" onsubmit="return validPrinterName();">
            <br/><br/>
            <table width="80%" border="0" cellpadding="0" cellspacing="0" align="center">
                <tr>
                    <td></td>
                    
                    <jsp:useBean id="labelFormatList" class="java.util.LinkedList"/>
                    <s:set name="aascPrinterInfoBean" value="AascPrinterInfo" />
                    <s:set name="labelFormatList" value="%{#aascPrinterInfoBean.labelFormatList}"/>
                    <c:catch var="exception1">
                        <s:set name="key" value="%{#attr.key}"/>
                    </c:catch> 
                    <s:if test="%{#exception1 != null}">
                        <c:redirect url="/aascShipError.jsp">
                            <s:param name="errorObj" value="%{#exception1.message}" />
                        </c:redirect>
                    </s:if>
                    <s:set name="error" value="%{#session.Error}" /> 
                    <c:catch var="exception2">
                        <s:set name="errorStr" value="%{#error"/>
                    </c:catch> 
                    <s:if test="%{#exception2 != null}">
                        <s:set name="error" value="%{''}"/>  
                    </s:if>
                    <s:if test='%{#error == null || #error == ""}'>
                        <s:set name="error" value="%{''}"/>  
                    </s:if> 
                    <s:else>
                        <c:remove var="Error" scope="session"/> 
                    </s:else> 
                    <s:property value="%{#error}"/>
                    <td width="60%" class="displayMessage1" id="displayMessage">
                        <div align="center">
                            <s:set name="printerKey" value="%{#attr.printerKey}" />
                            <s:property value="#printerKey" /> 
                        </div>
                    </td>
                    
                </tr>
            </table>
    <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px;overflow:auto; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%">
        <div style="background-color:#7761a7;margin-top:5px;height:30px">
            <label style="color:#ffffff;height:30px;font-size:20px;padding-left:10px"> Printer Setup Page </label>
        </div>
                
        <br/>
        <%
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        pageContext.setAttribute("locationId", locationId);
        
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        pageContext.setAttribute("roleId", roleId);
        
        String clientIdStr = request.getParameter("clientId");
        int clientId = 0;
        if(roleId == 1 || roleId == 2){
            clientId = Integer.parseInt(clientIdStr);
        }
        pageContext.setAttribute("clientId", clientId);
        %>
        <s:hidden name="totalPrinters" id="totalPrintersId" />
        <s:set name="locationId" value="%{#attr.locationId}"/>
        <s:hidden name="locationId" id="locationId" value="%{#locationId}"/>

        <s:set name="roleId" value="%{#attr.roleId}"/>
        <s:hidden name="roleId" id="roleId" value="%{#roleId}"/>
        
        <s:set name="clientId" value="%{#attr.clientId}"/>
        <s:hidden name="clientId" id="clientId" value="%{#clientId}"/>
        <div style="width:100%;overflow-x: auto;">
        <table style="margin-left:2%;margin-right:3%" border="1" align="center" cellpadding="0" cellspacing="0" id="PacTab" >
                            
            <thead>                
            <tr >
            
            <th  nowrap style="background-color:#7761a7; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Sno')"/></th>
            <th  nowrap style="background-color:#7761a7; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Delete')"/></th>
            <th  nowrap style="background-color:#7761a7; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('LabelFormat')"/></th>
            <th  nowrap style="background-color:#7761a7; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('PrinterName')"/></th>
            <th  nowrap style="background-color:#7761a7; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Enabled')"/></th>
            </tr>
            </thead>
            
            <s:set name="index" value="%{1}"/>      
            <s:hidden name="submit" id="submitId" value="" />
            <s:if test="%{#labelFormatList!=null}" >
                <s:iterator id="printerElement" value="%{#labelFormatList}">
                    <tr class="tableDataCell">
                        <td width="5%">
                            <div align="center"> <b><s:property    value="%{#index}" /></b> </div>
                            <s:hidden name="printerIndex%{#index}" id="printerIndexId%{#index}" value="%{#printerElement.printerIndex}" />
                        </td>
                        <s:set name="smFlag" value="true" /> 
                        
                        <td width="5%">
                            <div align="center">
                                <s:checkbox name="printer%{#index}" id="printerId%{#index}" disabled="%{#smFlag}" onclick="checkBoxValue(this)"  value="" fieldValue=""  /> 
                            </div>  
                        </td>
                        
                        <td width="7%" height="20">
                            <div align="center">
                                <s:select cssClass="inputs" cssStyle="width:100px;"  list="#{'ZPL/ZEBRA':'ZPL','EPL/ELTRON':'EPL','PDF':'PDF','GIF/PNG/JPG':'GIF'}" 
                                    name="labelFormat%{#index}" id="labelFormatId%{#index}" listKey="value" listValue="key"  headerKey="" headerValue="Select" 
                                        value="%{#printerElement.labelFormat}" /> 
                            </div>
                        </td>
                        
                        <td width="15%" height="20">
                            <div align="center">
                                <s:textfield name="printerName%{#index}" id="printerNameId%{#index}" cssClass="inputs" cssStyle="width:200px;" size="32" onblur="" value="%{#printerElement.printerName}" />    
                            </div>
                        </td> 
                        <td width="5%">
                            <div align="center">
                                <s:if test='%{#printerElement.enabledFlag == "Y"}'>
                                    <s:set name="checkedFlag" value="%{'true'}"/>
                                </s:if>
                                <s:else>
                                    <s:set name="checkedFlag" value="%{'false'}"/>
                                </s:else>
                                
                                <s:if test="%{#roleId == 3|| #roleId == 1}">
                                    <s:set name="enabledFlag" value="%{#printerElement.enabledFlag}" />
                                    <s:checkbox name="enabledFlag%{#index}" id="enabledFlagId%{#index}" onclick="checkBoxValue(this)"  fieldValue="%{#enabledFlag}"  value="%{#checkedFlag}"/>
                                </s:if>
                                <s:else>
                                    <s:checkbox name="enabledFlag%{#index}" id="enabledFlagId%{#index}" onclick="checkBoxValue(this)" disabled="disabled" fieldValue="%{#enabledFlag}"  value="%{#checkedFlag}"/>
                                </s:else>
                            </div>
                        </td>
                    </tr>
                    <s:set name="index" value="%{#index+1}"/>
                </s:iterator>
            </s:if>
            <s:else>            
                <tr class="tableDataCell">
                    <td width="5%" height="20"> 
                        <div align="center"> 
                            <b><s:property   value="%{#index}" /></b>
                        </div>
                        <s:hidden name="printerIndex%{#index}" id="printerIndexId%{#index}" value="0" />
                    </td>
                    
                    <td width="5%" height="20">
                        <div align="center">
                            <s:checkbox name="printer%{#index}" id="printerId%{#index}" onclick="checkBoxValue(this)"  value="" fieldValue=""  /> 
                        </div>
                    </td>
                    
                    <td width="10%" height="20">
                        <div align="center"> 
                            <% try { %>
                                <s:select cssClass="inputs" cssStyle="width:100px;" list="#{'ZPL/ZEBRA':'ZPL','EPL/ELTRON':'EPL','PDF':'PDF','GIF/PNG':'GIF'}" 
                                    name="labelFormat%{#index}" id="labelFormatId%{#index}"  listKey="value" listValue="key"  headerKey="" headerValue="Select" value="" /> 
                            <%  }catch(Exception ex){
                                    System.out.println("carrier service hm");
                                    ex.printStackTrace();
                                }
                            %>
                        </div>
                    </td>
                    
                    <td width="20%" height="20">
                        <div align="center">
                            <s:textfield name="printerName%{#index}" id="printerNameId%{#index}" cssStyle="width:200px;" cssClass="inputs" size="32" onblur="" value="" />    
                        </div>
                    </td> 
                   
                    <td width="5%">
                        <div align="center">
                            <s:checkbox name="enabledFlag%{#index}" id="enabledFlagId%{#index}" onclick="checkBoxValue(this)" disabled="disabled" fieldValue=""  value=""/>
                        </div>
                    </td>
                </tr>
            </s:else>
        </table>
        </div>
        <br><br>
        <table align="center">
            <tr valign="middle">
                <td colspan="5" align="center" class="tableDataCell" height="26">
                    
                    <s:if test="%{#roleId == 3 || #roleId == 1}">
                        <button class="btn btn-primary" type="button" id="addrowId" onclick="return addrow(); return false;">
                             Add <span class="badge" style="background-color:marun"><span class="glyphicon glyphicon-plus"></span></span></button>
                            <!-- <button   name="addrow" class="btn btn-primary btn-md"  onclick="return addrow(); return false;" value="addrow" align="middle" >Add </button>-->
                    </s:if>
                    <s:else>
                        <button type="button" disabled="disabled" class="btn btn-primary btn-md" name="addrow" id="addrowId" value="addrow"> Add <span class="badge"><span class="glyphicon glyphicon-plus"></span></span></button>
                    </s:else>    
                    &nbsp; &nbsp;

                    <s:if test="%{#roleId == 3 || #roleId == 1}">
                        <button class="btn btn-success" name="save" id="saveId" onclick="saverows();" value="SaveUpdate"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                        </s:if>
                    <s:else>
                        <button disabled="disabled" class="btn btn-success" name="save" type="button" id="saveId" value="SaveUpdate"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                        </s:else>
                    &nbsp; &nbsp;
                    <button class="btn btn-warning" name="close" type="button" id="closeId" onclick="window.close();" value="Close"> Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                    &nbsp; &nbsp;
                   
                    <s:if test="%{#roleId == 3 || #roleId == 1}">
                        <button type="button" class="btn btn-danger" name="delete"   id="deleteId" onclick="return deletePrinterRow(); return false;" value="delete"> Delete <span class="badge"><span class="glyphicon glyphicon-minus"></span></span></button>
                        </s:if>
                    <s:else>
                        <button type="button" disabled="disabled" class="btn btn-danger btn-md" name="delete" id="deleteId" value="delete"> Delete <span class="badge"><span class="glyphicon glyphicon-minus"></span></span></button>
                    </s:else>
                </td>
            </tr>
        </table>
        <br/>
    </div>
    </s:form> 
    <% }catch(Exception e){
    e.printStackTrace();
    }%>
</div>    
 <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
</html> 
