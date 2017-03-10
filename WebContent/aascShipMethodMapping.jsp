<%      
/*==========================================================================+
|  DESCRIPTION                                                              |
|    JSP aascShipMethodMapping for displaying the shipMethod                |
|     related information                                                   |
|    Author                                                                 |
|    Version   1.0                                                          |                                                                            

    10/12/2014  Y Pradeep  Added code to disable fields based on role.
    24/12/2014  Y Pradeep  Modifed code after fixing issue while deleting shipmethod mapping.
    07/01/2015  Y Pradeep  Fixed few issues found after porting in server.
    13/01/2015  Y Pradeep  Renamed DeleteChildDelivery() function to deleteShipMethod() function in aascShipMethodMapping_js.js and jsp.
    15/01/2015  Y Pradeep  Merged Sunanda's code : getting title name from Application.Property file.
    20/01/2015  Y Pradeep  Removed unncessary comments in history from SC cloud.
    10/03/2015  Jagadish         Added code for new UI changes.
    14/03/2015  Y Pradeep Modified code to close Ship method page when clicked on close button.
    16/03/2015  Y Pradeep  Modified code to Save shipmethods for fresh saving. 
    15/04/2015  Suman G    Added session to fix #2743.
    23/04/2015  Y Pradeep   Removed footer.
    11/06/2015  Suman G     Removed fevicon to fix #2992
    25/06/2015  Rakesh K    Added styles to fix #3062
    03/07/2015  Suman G     Added bold to fix #3153
    08/07/2015  Suman G     Added Padmavathi's code to fix issue #3170
    21/07/2015  Dinakar G   Added code to fix issue #3197
    24/07/2015  Rakesh K    Modified Code to work application in offline.
    28/07/2015  Dinakar G   Added code to fix issue #3258 & #3257 
    04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
    06/08/2015  Dinakar G   Added code to fix issue #3263 & #3269
    07/08/2015  Dinakar G   Added code to fix issue Bug#3353
    26/08/2015  N Srisha    Added Id's for Automation testing
    26/08/2015  Rakesh K       Added code to solve 3496.
+===========================================================================*/%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>  
<%@ taglib uri="/struts-tags" prefix="s" %>

<%@ page errorPage="aascShipError.jsp" %>
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
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><s:property value="getText('SCShipMethodMapping')" /></title>
        <link href="aasc_ss.css" rel="stylesheet" type="text/css"/>
        <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
        <script language="JavaScript" type="text/javascript" src="aascShipMethodMapping_js.js"></script>
        
    
     <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
     <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
     <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
     <link href="kendo.common-material.min.css" rel="stylesheet" />
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />   -->
        
        <style type="text/css"> 
        table{
        table-layout:fixed;
width: auto;
}
.inputs{
font-weight:bold;
}
            #ClassWidth { 
                width: 20em; 
            } 
            #ClassWidth2 { 
                width: 16em; 
            } 
            #ClassWidth3 { 
                width: 6em; 
            } 
             #PacTab{
 margin-left:3%;
 margin-right:3%;

 }

            html{ height:100%;  }          
        </style> 
        <script language="javascript" type="text/javascript">
            function showTip(oSel) {
                var theTip = document.getElementById("tooltip");
                theTip.style.top = window.event.clientY + 20;
                theTip.style.left = window.event.clientX;        
                theTip.innerText = oSel.options[oSel.selectedIndex].text;
                theTip.style.visibility = "visible";
            }

            function hideTip() {
                document.getElementById("tooltip").style.visibility = "hidden";
            }
            
            function openNewWindow (URL, windowName, windowOptions){
                var window = getWindow (URL, windowName, windowOptions);
            }

            function getWindow(URL, windowName, windowOptions){
        //alert("URL"+URL);
        //alert("windowName"+windowName);
        //alert("windowOptions"+windowOptions);
                var newWindow = open (URL, windowName, windowOptions)
                newWindow.focus();
                return window;
            }
        </script>
        <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    
    </head>
    
    <body style="background-color:#ADADAD;" onload="return DisableField();">
    <div  class="container-fluid" style="background-color:#ADADAD; width:100%;">
    <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
  <%@include file ="aascIndexHeader.jsp" %>
        <jsp:useBean id="aascShipMethodInfo" class="com.aasc.erp.bean.AascShipMethodInfo" scope="page"/>
        <jsp:useBean id="shipMethodList1" class="java.util.LinkedList"/>
        <jsp:useBean id="sortedCarriers" class="java.util.LinkedList"/>
        <jsp:useBean id="validationHashMap" class="java.util.HashMap"/>
        <jsp:useBean id="carrierServiceHashMap" class="java.util.HashMap"/>
        <jsp:useBean id="shipMethodElement" class="com.aasc.erp.bean.AascShipMethodInfo" scope="page"/>
        <s:set name="carrierServiceHashMap" value="%{#request.carrierServiceList}"/>
        <s:set name="userShipMethodHashMap" value="%{#request.shipMethodList}"/>
        <s:set name="roleId" value="%{#attr.role_id}"/>
        <s:set name="delKey" value="%{#attr.deleteKey}" />
        <center>
            <table>
                <tr>
                    <td id="displayMessage">
                        <font size="2" color="green"> 
                            <b> 
                                <s:property value="#delKey" /> 
                            </b>
                        </font>  
                    </td>
                </tr>
            </table>
        </center>
        
        <c:catch var="exception1">
            <s:set name="validationHashMap" value="%{#request.validationHashMap}"/>
        </c:catch>
        <s:if test="%{#exception1 != null}">
            <c:set target="${validationHashMap}" property="select" value="select"/>
        </s:if>
 
        <s:set name="shipMethodList1" value="%{#attr.shipMethodlist}" />
    
        <s:form   method="post" action="ShipMethodAction" name="DynaAascShipMethodMappingForm" onsubmit="return validCarrierName();" >
            <s:hidden name="roleIdHidden" id="roleIdHiddenID" value="%{#roleId}" />
            <table width="100%"  border="0" cellspacing="5" cellpadding="2">
                <tr>
                    <td width="32%" height="21">
                       <!-- <div align="left" class="pageHeading"><s:property value="getText('ShipMethodMapping')"/> </div>-->
                        
                        <s:set name="carrier" value="%{#attr.carrierName}"/>
                        <s:hidden name="carriername" id="carriernameID"  value="%{#carrier}" />

                        <s:set name="clientid" value="%{#attr.clientIdN}" />                
                        <s:hidden name="client" id="clientID"  value="%{#clientid}" />
                
                        <s:set name="carrierID" value="%{#attr.carrierID}" />                
                        <s:hidden name="carrierID" id="carrierID"  value="%{#carrierID}" />
                
                        <s:set name="locID" value="%{#attr.locationid}" />                
                        <s:hidden name="location" id="locationID"  value="%{#locID}" />
                       
                        <s:set name="user" value="%{#attr.userid}" />                
                        <s:hidden name="user" id="userID"  value="%{#user}" />
                
               <!--  <c:catch var="exception3">
             
                <s:set name="carrierName" value="%{#attr.cName}"/>
                 
                  <s:if test="%{#carrierName == null}">
                     <s:set name="carrierNameSelected" value="%{#attr.carrierNameSelected}"/>
                     
                     </s:if>
                   <s:else>
                      <s:set name="carrierNameSelected" value="%{#attr.cName}"/>
                
                      <s:set name="cName" value="%{''}"/>
                   </s:else> 
                   
                   <s:if test="%{#carrierNameSelected == null}">
                      <s:set name="exception3" value="%{'NullPointerException'}"/>
                   </s:if> 
                </c:catch> 
                <s:if test="%{#exception3 != null}">
                   <s:set name="carrierNameSelected" value="%{''}"/>
                   <s:property value="%{#exception3.message}"/>
                </s:if> -->
                    </td>
                </tr>
            </table>   
           
         
                     
                    <div class="container-fluid" style="overflow:auto;width:97% ;margin-left:30px;margin-right:30px;margin-top:5px;border:1px #ECE0F8;border-radius:5px;padding-left:0px;padding-right:0px;background-color:#F0F0F0"><!-- Changed border from 50px to 5px to fix issue #3170-->

                    <div align="center"  style="color: white;width:100%; height:35px;font-size:20px; background-color:#19b698; font:#f7f7ff; ">
<label style="margin-left:30px;margin-top:5px;font-size: 20px;margin-top: 3px;"> Ship Method Information</label>
 </div>
 <br/>
 <table  style="margin-left:2%;margin-right:3%" border="1" align="center" cellpadding="0" cellspacing="0" id="PacTab" >
                            <thead>                
                                <tr >
                                
                                <th  nowrap style="background-color:#19b698; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Sno')"/></th>
                                <th  nowrap style="background-color:#19b698; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Delete')"/></th>
                                <th  nowrap style="background-color:#19b698; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('UserDefinedShipMethod')"/></th>
                                <th  nowrap style="background-color:#19b698; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('CarrierServiceLevel')"/></th>
                                <th  nowrap style="background-color:#19b698; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Validation')"/></th>
                                <th  nowrap style="background-color:#19b698; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Enabled')"/></th>
                                <th  nowrap style="background-color:#19b698; text-align:center; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('International')"/></th>
                                </tr>
                             </thead>
                           
           
                            <s:set name="index" value="%{1}"/>      
           
                            <s:hidden name="submit" id="submitID" value="" />
                            <s:hidden name="carrierCode" id="carrierCodeID" value="%{#attr.carrierCode}" />
                            <s:if test="%{#shipMethodList1!=null}" >
                                <s:iterator id="shipMethodElement" value="%{#shipMethodList1}">
         
                                    <tr >
                                        <td width="2%">
                                            <div align="center"> <b><s:property    value="%{#index}" /></b> </div>
                                            <s:hidden name="shipmethodIndex%{#index}" id="shipmethodIndexID%{#index}" value="%{#shipMethodElement.shipmethodIndex}" />
                                        </td>
                                        <s:set name="smFlag" value="true" /> 
                                        <td width="3%">
                                            <div align="center">
                                                <s:checkbox name="shipMethod%{#index}" id="shipMethodID%{#index}" disabled="%{#smFlag}" 
                                                        onclick="checkBoxValue(this)"  value="" fieldValue=""  /> 
                                            </div>  
                                            <s:hidden name="shipMethodDelSize" id="shipMethodDelSizeID" value="" />     
                                            <s:hidden name="deliveryDeleteCount" id="deliveryDeleteCountID" value="" /> 
                                        </td>
                                        <td width="10%" height="20">
                                            <div align="center">
                                             <b>   <s:textfield name="userdefinedShipmethod%{#index}" cssClass="inputs" 
                                                        size="32" onblur="" value="%{#shipMethodElement.userdefinedShipmethod}" />    </b>
                                            </div>
                                        </td> 
                                        <td width="15%" height="20" class="dispalyDBFields">
                                            <div align="center">
                                                <s:select cssClass="inputs"   list="#carrierServiceHashMap"  name="carrierServiceLevel%{#index}" id="carrierServiceLevelID%{#index}"
                                                        listKey="value" listValue="key"  headerKey="" headerValue="Select" value="%{#shipMethodElement.carrierServiceLevel}" /> 
                                            </div>
                                        </td>
                                        <td width="15%" height="20" class="dispalyDBFields">
                                            <div align="center">     
                                                 <s:select cssClass="inputs"  list="#validationHashMap" name="validation%{#index}" value="%{#shipMethodElement.validation}" id="validationID%{#index}"
                                                        listKey="value" listValue="key"  headerKey="" headerValue="Select" /> 
                                            </div>
                                        </td>
                                        <td width="3%">
                                            <div align="center">
                                                <s:if test='%{#shipMethodElement.enabledFlag == "Y"}'>
                                                    <s:set name="checkedFlag" value="%{'true'}"/>
                                                </s:if>
                                                <s:else>
                                                    <s:set name="checkedFlag" value="%{'false'}"/>
                                                </s:else>
        
                                                <s:if test="%{#roleId == 3|| #roleId == 1}">
                                                    <s:set name="enabledFlag" value="%{#shipMethodElement.enabledFlag}" />
                                                    <s:checkbox name="enabledFlag%{#index}" 
                                                            onclick="checkBoxValue(this)"  fieldValue="%{#enabledFlag}"  value="%{#checkedFlag}"/>
                                                </s:if>
                                                <s:else>
                                                    <s:checkbox name="enabledFlag%{#index}" 
                                                            onclick="checkBoxValue(this)" disabled="disabled" fieldValue="%{#enabledFlag}"  value="%{#checkedFlag}"/>
                                                </s:else>
                                            </div>
                                        </td>
                                        <td width="3%">
                                            <div align="center">
                                                <s:if test='%{#shipMethodElement.intlShipFlag == "Y"}'>
                                                    <s:set name="checkedFlag" value="%{'true'}"/>
                                                </s:if>
                                                <s:else>
                                                    <s:set name="checkedFlag" value="%{'false'}"/>
                                                </s:else>

                                                <s:if test="%{#roleId == 3|| #roleId == 1}">
                                                    <s:set name="intlShipFlag" value="%{#shipMethodElement.intlShipFlag}" /> 
                                                    <s:checkbox name="international%{#index}" cssClass="inputFields" 
                                                            onclick="checkBoxValue(this)"  value="%{#checkedFlag}" fieldValue="%{#intlShipFlag}"/>
                                                </s:if>
                                                <s:else>
                                                    <s:checkbox name="international%{#index}" 
                                                            onclick="checkBoxValue(this)" disabled="disabled" value="%{#checkedFlag}" fieldValue="%{#intlShipFlag}" />
                                                </s:else>
                                            </div>
                                        </td>
                                    </tr>
                                    <s:set name="index" value="%{#index+1}"/>
                                </s:iterator>
                            </s:if>
                            <s:else>
                              <s:iterator var="carrierService" value="#carrierServiceHashMap">
                              <s:set var="hmvalue" value="%{key}"/>
                                
                                <tr class="tableDataCell">
                                    <td width="4%" height="20"> 
                                        <div id="sno${index}" align="center"> 
                                            <b>
                                                <s:property   value="%{#index}" />
                                            </b>
                                        </div>
                                        <s:hidden name="shipmethodIndex%{#index}" id="shipmethodIndexID%{#index}" value="0" />
                                    </td>
                                    <td width="4%" height="20">
                                        <div align="center">
                                            <s:checkbox name="shipMethod%{#index}" id="shipMethodID%{#index}" 
                                                    onclick="checkBoxValue(this)"  value="" fieldValue=""  /> 
                                        </div>
                                    </td>
                                    
                                    <td width="10%" height="20">
                                        <div align="center">
                                        <s:iterator var="userShipMethodlist" value="#userShipMethodHashMap">
                                        <s:if  test="%{#hmvalue.equalsIgnoreCase(key)}">
                                          <s:set name="userdefinedShipmethodValue" value="%{value}" />
                                          </s:if>
                                        </s:iterator>  
                                            <s:textfield name="userdefinedShipmethod%{#index}" cssClass="inputs"  size="32" onblur="" 
                                                   title="%{#userdefinedShipmethodValue}" value="%{#userdefinedShipmethodValue}" />    
                                        </div>
                                    </td> 
                                    <td width="15%" height="20" class="dispalyDBFields">
                                        <div align="center"> 
                                            <% try { %>
                                                <s:select cssClass="inputs"   list="#carrierServiceHashMap"  name="carrierServiceLevel%{#index}" id="carrierServiceLevelID%{#index}" 
                                                        listKey="value" listValue="key"  headerKey="" headerValue="" value="%{value}" /> 
                                            <%  }catch(Exception ex){
                                                    System.out.println("carrier service hm");
                                                    ex.printStackTrace();
                                            }%>
                                        </div>
                                    </td>
                                    <td width="15%" height="20" class="dispalyDBFields">
                                        <div align="center">    
                                            <% try { %>
                                                <s:select cssClass="inputs"  list="#validationHashMap" name="validation%{#index}" value="0" id="validationID%{#index}" 
                                                        listKey="value" listValue="key"  headerKey="" headerValue="" />      
                                            <%  }catch(Exception ex){
                                                    System.out.println("in validation");
                                                    ex.printStackTrace();
                                            }%>
                                        </div>
                                    </td>
                                    <td width="10%">
                                        <div align="center">
                                            <s:checkbox name="enabledFlag%{#index}" 
                                                    onclick="checkBoxValue(this)" disabled="disabled" fieldValue="Y"  value="true"/>
                                        </div>
                                    </td>
                                    <td width="11%">
                                        <div align="center">
                                            <s:checkbox name="international%{#index}" 
                                                    onclick="checkBoxValue(this)" disabled="disabled" value="" fieldValue="" />
                                        </div>
                                    </td>
                                </tr>
                                <s:set name="index" value="%{#index+1}"/>
                                </s:iterator>
                            </s:else>
                        </table>
                       
                        <br />
            <br><br>
            <table align="center" width="100%">
                <tr valign="middle">
                    <td colspan="5" align="center" class="tableDataCell" height="26">
                        <s:hidden name="totalShipMethods" id="totalShipMethodsID" value="" />
                        <s:if test="%{#roleId == 3 || #roleId == 1}">
                            <span class="btn btn-primary" onclick="return addrow(); return false;" id="addrowID">
                             Add <span class="badge" style="background-color:marun"><span class="glyphicon glyphicon-plus"></span></span></span>
                            <!-- <button   name="addrow" class="btn btn-primary btn-md"  onclick="return addrow(); return false;" value="addrow" align="middle" >Add </button>-->
                        </s:if>
                       <s:else>
                            <button disabled="disabled" class="btn btn-primary btn-md" name="addrow"   id="addrowID"   value = "addrow"  > Add <span class="badge"><span class="glyphicon glyphicon-plus"></span></span></button>
                            </s:else>    
                        &nbsp; &nbsp;

                        <s:if test="%{#roleId == 3 || #roleId == 1}">
                            <button class="btn btn-success" name="save"   id="saveID" onclick="saverows();"  value = "SaveUpdate"  > Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                            </s:if>
                        <s:else>
                            <button disabled="disabled" class="btn btn-success" name="save"   id="saveID"   value = "SaveUpdate"  > Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                            </s:else>
                        &nbsp; &nbsp;
                        <button class="btn btn-warning" name="close"   id="closeId" onclick="document.getElementById('submitID').value='GoCarrier';"  value = "GoCarrier"  > Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                        &nbsp; &nbsp; 
                 
                        <s:if test="%{#roleId == 3 || #roleId == 1}">
                            <button class="btn btn-danger" name="delete"   id="deleteId" onclick="return deleteShipMethod(); return false;"  value = "delete"  > Delete <span class="badge"><span class="glyphicon glyphicon-minus"></span></span></button>
                            </s:if>
                        <s:else>
                            <button disabled="disabled" class="btn btn-danger btn-md" name="delete"   id="deleteId"   value = "delete"  > Delete <span class="badge"><span class="glyphicon glyphicon-minus"></span></span></button>
                            </s:else>
                    </td>
                </tr>
            </table>
            <br/>
            </div>
        </s:form>
        </div>
         <div style="position:relative;top:200px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
</html>
