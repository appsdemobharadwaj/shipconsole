<%/*========================================================================+
@(#)aascCreateDetails.jsp 25/07/2014
* Copyright (c) 2014-2015 Apps Associates Pvt. Ltd. 
* All rights reserved.  
+===========================================================================*/
/***
* JSP For Customers Details
* @version 1
* @author Suman Gunda
* @history
*
*    15/01/2015  Y Pradeep   Merged Sunanda's code : getting title name from Application.Property file.
*    19/01/2015  Suman G     Arranged <tr> and <td> in format.
*    21/01/2015  K Sunanda   Added code to display success messages in green and errored in red for bug #2506
*    04/02/2015  K Sunanda   Added Id for display messages
*    17/02/2015    Suman G     Modified code to fix #2506.
*    23/04/2015  Y Pradeep   Removed footer.
*    26/05/2015  Suman G     Added vikas code to new UI.
*    11/06/2015  Suman G     Removed fevicon to fix #2992
*    16/07/2015    Dinakar G    Modified alignment in screen for tablet
*    16/07/2015  Rakesh K    Modified alignment.
*    21/07/2015    Suman G        Modified message id for QA
*    24/07/2015  Rakesh K    Modified Code to work application in offline.
*    04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
*    06/08/2015  Dinakar G    Modified Code to fix Bug#3354.
26/08/2015  Rakesh K       Added code to solve 3496.
*    04/11/2015  Suman G     Removed unused fields.
*/
%>


<link type="text/css" rel="stylesheet" href="aasc_ss.css">
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
  
  <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
       <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
   <link href="kendo.common-material.min.css" rel="stylesheet" />-->
   
     <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
	        <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
	    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
	        <link rel="stylesheet" type="text/css" href="css/style4.css" />
	
	  
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	    <title><s:property value="getText('SCShipIndex')" /></title>
	    <script language="javascript" type="text/javascript">
	        var button = "N";
	        function disableSubmit()
	        {
	            if(button=="Y")
	            {
	                alert("Request already submitted. Please Wait.");
	                return false;
	            }
	        }
	        function changePassword(){
	            tpwindow =  window.open("aascChangePassword.jsp","Post",'width=500,height=350,top=100,left=100,scrollbars=yes, resizable=yes');
	            tpwindow.focus();
	        }
	        function loader()
	        {
	            var pb;
	            pb = document.getElementById("indexLoad");
	            pb.innerHTML = '<img src="images/ajax-loader.gif" width="80" height ="80"/>';
	            pb.style.display = '';
	        }
	    </script>
	  
	  <style type="text/css">
	
	.href {color: #003399}
	html{height:100%}
	    </style>
  
    <script  type="text/javascript"  src="aascCustomerDetails_js.js"> </script>
    <script language="JavaScript" src="aasc_Index_Header_js.js" type="text/javascript"></script>
	  
	    
        
	 <link rel="stylesheet" href="demo.css">
	<link type="text/css" rel="stylesheet" href="login.css"/>
    <link type="text/css" rel="stylesheet" href="menu_header.css"/>
    
    <title><s:property value="getText('SCCustomerDetails')" /></title>
    <style type="text/css">
      .href {color: #003399}
    </style>
  </head>
  <body >
  <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
  <%@ include file="aascIndexHeader.jsp"%> 
    <s:form  name="CustomerDetailsForm" method="POST" action="AascCreateCustomerAction" >
       
                    <s:hidden name="actiontype" /> 
                    <s:hidden name="currentRow" /> 
                    <s:set name="customerDetailsList" value="%{#session.customerDetailsList}"/>  
                    <s:set name="roleId" value="%{#attr.role_id}"/>
                          
                    
                  
                        <div class="row">
                         <!--21/01/2015 sunanda added-->
                                <c:catch var="exception1">
                                    <s:set name="key" value="%{#attr.key}"/>
                                    <s:if test="%{#key != null}">
                                  <s:if test="%{#key=='aasc406'||#key=='aasc408'}">
                                    <div class="col-sm-8 displayMessage" width="90%"  id="displayMessage" align="center" valign="bottom">
                                      <s:property value="getText(#key)"/> 
                                    </div>
                                  </s:if>
                                  <s:else>
                                    <div class="col-sm-8 displayMessage" width="90%"  id="displayMessage" align="center" valign="bottom">
                                        <s:property value="getText(#key)"/> 
                                    </div>
                                   </s:else>
                                        <s:set name="key" value="%{'null'}"/>
                                    </s:if>
                                </c:catch> 
                                <s:if test="%{#exception1 != null}">
                                    <c:redirect url="/aascShipError.jsp">
                                        <s:param name="errorObj" value="%{#exception1.message}" />
                                    </c:redirect>
                                </s:if>
                        </div>  
                    
                
            
                    <s:hidden name="CreateButtonId" id="CreateButtonId" value="%{0}"/>
                    </br>
               <div class="row">
               <div class="col-sm-12">
                     <div class="col-sm-6" align="right">
                        <h5 style="color:white;font-size:20px;font-weight:bold;"><s:property value="getText('CustomerDetails')"/></h5>
                  </div>
                  <s:if test='%{#roleId == "1"}'>    
                         
                                  <div class="col-sm-6" align="right">
                                  <button class="btn btn-success" name="NewButton"  id="NewButton" onclick="document.CustomerDetailsForm.actiontype.value='CreateCustomer';return validateSubmit();" value="Create"  alt="Create" align="middle" >Create <span class="badge"><span class="glyphicon glyphicon-pencil"></span></span></button>
                                    </div>
                               
                            </s:if> 
                 
                  </div>
               </div> 
                    
                    
                  <br>  
            
                <div class="form-group">
                 <fieldset class="scheduler-border" style="border-color:#19b698; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;border-width:2px">              
                    
                    <table class="table"  style="overflow-x:auto;" id="table" >
                       <thead>
                        <tr>
                            <s:if test='%{#roleId == "1"}'>
                               <th align="right" nowrap style="background-color:#19b698;font-size: 13px;font-weight: bold; color:#ffffff;"  ><s:property value="getText('Edit')"/></th>
                            </s:if>
                            <s:else>
                               <th align="right" nowrap style="background-color:#19b698;font-size: 13px;font-weight: bold; color:#ffffff;"  ><s:property value="getText('View')"/></th>
                            </s:else>
                            
                            <th align="left" nowrap style="background-color:#19b698;font-size: 13px;font-weight: bold; color:#ffffff;" ><s:property value="getText('CompanyName')"/></th>
                            <th align="left" nowrap style="background-color:#19b698;font-size: 13px;font-weight: bold; color:#ffffff;" ><s:property value="getText('UserName')"/></th>
                            <th align="left" nowrap style="background-color:#19b698;font-size: 13px;font-weight: bold; color:#ffffff;" ><s:property value="getText('CustomerType')"/></th>
                            <th align="left" nowrap style="background-color:#19b698;font-size: 13px;font-weight: bold; color:#ffffff;" ><s:property value="getText('CurrentPkgBalance')"/></th>
                            <th align="left" nowrap style="background-color:#19b698;font-size: 13px;font-weight: bold; color:#ffffff;" ><s:property value="getText('Status')"/></th>
                    
                    
                        </tr>
                        </thead>
                        <s:set name="customerCount" value="%{0}"/>
                        <s:iterator id="customerDetailsInfo" value="#customerDetailsList">
                            <s:set name="customerCount" value="%{#customerCount+1}"/>
                            <s:set name="status" value="%{#customerDetailsInfo.status}"/>
                            <s:if test='%{#status == "Y"}'>
                                <s:set name="statusFlag" value="%{'ACTIVE'}"/>
                            </s:if>
                            <s:else>
                                <s:set name="statusFlag" value="%{'INACTIVE'}"/>
                            </s:else>
                        <tr>
                            <s:if test='%{#roleId == "1"}'>
                                <td width="5">   
                                    <!--<input name="customerDetailsImg<c:out value='${customerCount}'/>" type="image"  id="NewButton" onclick="document.CustomerDetailsForm.actiontype.value='EditCustomer'; methodOnEdit('<c:out value='${customerCount}'/>');return validateSubmit();" class="inputFields" value = "<c:out value='${customerCount}'/>" src="images/edit.png" alt="Edit" >-->
                                    <button class="btn btn-primary"  name="customerDetailsImg<c:out value='${customerCount}'/>" id="NewButton" onclick="document.CustomerDetailsForm.actiontype.value='EditCustomer'; methodOnEdit('<c:out value='${customerCount}'/>');return validateSubmit();" class="inputFields" value = "<c:out value='${customerCount}'/>" alt="Edit" > <span><span class="glyphicon glyphicon-pencil"></span></span></button>
                                </td>
                             </s:if>
                             <s:else>
                                <td width="5">
                                <button class="btn btn-primary"  name="customerDetailsImg<c:out value='${customerCount}'/>" id="NewButton" onclick="document.CustomerDetailsForm.actiontype.value='ViewCustomer'; methodOnView('<c:out value='${customerCount}'/>');" class="inputFields" value = "<c:out value='${customerCount}'/>" alt="Edit" > <span><span class="glyphicon glyphicon-pencil"></span></span></button>
                                 <!--<input name="customerDetailsImg<c:out value='${customerCount}'/>" type="image"  id="NewButton" onclick="document.CustomerDetailsForm.actiontype.value='ViewCustomer'; methodOnView('<c:out value='${customerCount}'/>');" class="inputFields" value = "<c:out value='${customerCount}'/>" src="images/edit.gif" alt="View" >-->
                                 </td>
                             </s:else>
                             <td nowrap class="dispalyFieldsWithOutBold" ><s:property value="%{#customerDetailsInfo.companyName}"/></td>
                             <td nowrap class="dispalyFieldsWithOutBold" ><s:property value="%{#customerDetailsInfo.emailAddress}"/></td>
                             <td nowrap class="dispalyFieldsWithOutBold" ><s:property value="%{#customerDetailsInfo.customerType}"/></td>
                             <td nowrap class="dispalyFieldsWithOutBold" ><s:property value="%{#customerDetailsInfo.currentPackageBalance}"/></td>
                             <td nowrap class="dispalyFieldsWithOutBold" ><s:property value="%{#statusFlag}"/></td>
                        </tr>
                        </s:iterator>
                    </table>
                    <br/>  <br/>
                    </fieldset>
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
