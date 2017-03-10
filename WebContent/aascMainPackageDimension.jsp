<%/*========================================================================+
|  DESCRIPTION                                                              |
|    JSP For Package Dimensions Details                                            |                                                      |
|    Version - 1                                                            |       
|    20/11/2014   K Sunanda      Added code from ShipConsoleCloud version1.2                        |
|    07/01/2015   Y Pradeep      Merged Sunandas code and removed the hard codings by getting from Properties file 
|    15/01/2015   Y Pradeep      Merged Sunanda's code on 1.0 release bugs.
|    20/01/2015   K Sunanda      Aligned <td> and <tr> tags properly
|    21/01/2015   K Sunanda      Added code to display success messages in green and errored in red for bug #2506
|    04/02/2015   K Sunanda      Added Id for display messages
|    18/02/2015   K Sunanda      Commented code at changeLocation() function for bug fix #2575
|    01/04/2015   K Sunanda      Modified code for bug fix #2779.
     14/04/2015   Y Pradeep      Modified code to enable location LOV and Go button. Bug #2843.
|    15/04/2015   Suman G        Added session to fix #2743.
     23/04/2015   Y Pradeep      Removed footer.
     11/06/2015   Suman G        Removed fevicon to fix #2992
     23/06/2015   Rakesh K       Modifed code to fix #3061
     21/07/2015   Suman G        Modified message id for QA
     24/07/2015   Rakesh K       Modified Code to work application in offline.
     29/07/2015   Dinakar        Modifed code to fix #3260
     04/08/2015   Rakesh K       Modified Code bootstrap css file work across all browsers.
     26/08/2015   Dinakar G      Added id's for Automation testing.
     26/08/2015   Rakesh K       Added code to solve 3496.
     16/11/2015   Shiva G       Modified cssClass from dispalyFieldsWithOutBold to dispalyFieldsWithOutWidth to fix the issue 3941
+===========================================================================*/%>
<%@ page errorPage="aascShipError.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<link type="text/css" rel="stylesheet" href="aasc_ss.css">
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
     
     <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
     <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
     <link href="kendo.common-material.min.css" rel="stylesheet" />
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />-->
    
    <script language="javascript" type="text/javascript">
    //Added by Jagadish for locationId
    function ChangeLocation()
    {
     var clientId=document.getElementById('clientIdHiddenID').value;
    }
            
    function getlocations()
    {
      document.PackageDimensionForm.actiontype.value='clientDetail';
      document.PackageDimensionForm.submit();
    }
   
    function methodOnSave()
    {     
      var dimCount=document.PackageDimensionForm.dimensionCount.value;
      for(var index = 1;index <= dimCount; index++)
      {
        if(document.PackageDimensionForm['dimensionActive'+index].checked==true)
        {
            document.PackageDimensionForm['dimensionActive'+index].value="Y";
        }
        else {
            document.PackageDimensionForm['dimensionActive'+index].value="N";
        }   
        
        if(document.PackageDimensionForm['dimensionDefault'+index].checked==true)
        {
            document.PackageDimensionForm['dimensionDefault'+index].value="Y";
        }
        else{
            document.PackageDimensionForm['dimensionDefault'+index].value="N";
        }  
       } 
            
        for(var index = 1;index <= dimCount; index++)
         {
           if((document.PackageDimensionForm['dimensionDefault'+index].value=="Y") && (document.PackageDimensionForm['dimensionActive'+index].value=="N"))
             {
               var status1=confirm('Do you want to enable Active flag for selected default Dimension ?');
               if(status1)
                {
                  document.PackageDimensionForm['dimensionActive'+index].checked=true;
                  document.PackageDimensionForm['dimensionActive'+index].value="Y";
                   return true;
                }
            else
            {
              return false;
            }
          }
        }
        return true;
      }
    
    function methodOnEdit(rowCount)
       {       
        document.PackageDimensionForm.currentRow.value=rowCount;
        if(document.PackageDimensionForm.actiontype.value == 'Edit')
           {
            //document.PackageDimensionForm.submit();
           }
        }
        
    function methodForDefault(rowCount)
      {
        var dimCount=document.PackageDimensionForm.dimensionCount.value;
        for(var index = 1;index <= dimCount; index++)
          {
            if(rowCount != index)            
              {
                document.PackageDimensionForm['dimensionDefault'+index].checked=false;
                document.PackageDimensionForm['dimensionDefault'+index].value="N";
              }
            else
              {
                document.PackageDimensionForm['dimensionDefault'+index].checked=true;
                document.PackageDimensionForm['dimensionDefault'+index].value="Y";
              }
          }
      }
        
    function selectLocationValidation()
      {
      var locationName=document.PackageDimensionForm.locationId.options[document.PackageDimensionForm.locationId.selectedIndex].text;
      var locationId=document.PackageDimensionForm.locationId.options[document.PackageDimensionForm.locationId.selectedIndex].text;
       if(locationId=="--Select--")
         {	
          alert("Please Select location ");
          document.PackageDimensionForm.locationId.focus()
          return false;
         }
       if(locationName=="Select Location")
         {	
          alert("Please Select Location");
          document.PackageDimensionForm.locationId.focus()
          return false;
         }
      }
         
    function disableLocation()
     {
      var accessValue=document.PackageDimensionForm.role_id.value;
      var dimCount=document.PackageDimensionForm.dimensionCount.value;
      if(accessValue == 4)
      {
//       document.PackageDimensionForm.locationId.disabled = true;
       for(var index = 1;index <= dimCount; index++)
        {
         document.PackageDimensionForm['dimensionActive'+index].disabled = true;
         document.PackageDimensionForm['dimensionDefault'+index].disabled = true;
        }
       }
      else 
        if(accessValue == 2)
        {
        for(var index = 1;index <= dimCount; index++)
         {
          document.PackageDimensionForm['dimensionActive'+index].disabled = true;
          document.PackageDimensionForm['dimensionDefault'+index].disabled = true;
         }
        document.PackageDimensionForm.locationId.disabled = false;
        }
      }
         
    function LoadOnChange()
    {
    var locationName=document.PackageDimensionForm.locationId.options[document.PackageDimensionForm.locationId.selectedIndex].text;
                
    if(locationName=="Select Location")
     {	
     alert("Please Select location");
     document.PackageDimensionForm.locationId.focus();
     return false;
     }
    }
    
    function trim(str)
    {
     return str.replace(/^\s*|\s*$/g,"");
    }
  
    </script>

    <s:if test="%{#pageContext.session['new'] || (#pageContext.session.id != #session.role_id)}" >
    </s:if>
    
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title> <s:property value="getText('PackageDimension')"/></title>
    <style type="text/css">
    html{ height:100%;  }
    </style>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
   
    <!--<script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>-->
    </head>
    <body class="gradientbg" onload="disableLocation();">
    <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
    <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
    <c:catch var="exception21">
     <s:set name="locationDetailsMap" value="#session.locationDetailsMap"/> 
     <jsp:useBean id="locationDetailsMap" class="java.util.HashMap"/>
    </c:catch>
    <s:if test="%{#exception21 != null}">
     <c:set target="${locationDetailsMap}" property="select" value="select"/>
    </s:if>
    <%@ include file="aascIndexHeader.jsp"%>
  <s:form  name="PackageDimensionForm" method="POST" action="AascPackageDimensions" onsubmit="return selectLocationValidation()">
    <c:catch var="Exception1" >
     <s:set name="AascGetLocInfo" value="%{#session.LocInfo}" />
    </c:catch>
    <s:if test="%{#Exception1 != null}" >
    </s:if>
    <s:set name="locList" value="%{#AascGetLocInfo.locDetails}" />
    <c:catch var="Exception2" >
     <s:set name="aascPackageDimensionInfo" value="%{#attr.aascPackageDimensionInfo}" />

    </c:catch>
    <s:if test="%{#Exception2 != null}" >
    </s:if>
     <s:set name="validateKey" value="%{''}"/>
     <s:set name="key" value="%{#request.key}"/>
     <s:if test='%{#key == "aasc506"}' >
     <s:set name="validateKey" value="%{#key}" />
        
    </s:if>
    <s:set name="packageDimensionList" value="%{#aascPackageDimensionInfo.packageDimensionList}" />
    
    <s:set name="locationId" value="%{#aascPackageDimensionInfo.locationId}" />
    <s:set name="role_id" value="%{0}" />
    <s:set name="role_idSse" value="%{#session.role_id}" />
    <s:set name="role_id" value="%{#role_idSse}" />
    <s:set name="locationIdNum" value="%{0}" />
    <s:if test="%{#role_id == 2 || #role_id == 4}">
      <s:set name="locationSess" value="%{#session.location_id}" />
      <s:set name="locationIdNum" value="%{#locationSess}" />
      <s:set name="chkCondition" value="%{'allow'}" />
     </s:if>
    <s:else>
       <s:set name="locationIdMain" value="%{#request.locationId}" /> 
       <c:catch var="Exception3"  >
        <s:if test="%{(#locationIdMain == '') || (#locationIdMain == null)}" >
          <s:set name="locationIdMain" value="%{''}" />
        </s:if>
       </c:catch>
       <s:if test="%{#Exception3 != null}" >
         <s:set name="locationIdMain" value="%{''}" />
       </s:if>
       <s:if test="%{#locationIdMain == ''}" >
         <s:set name="chkCondition" value="%{'notallow'}" />
       </s:if>
       <s:else>
         <s:set name="chkCondition" value="%{'allow'}" />
         <s:set name="locationIdNum" value="#locationIdMain" />
       </s:else>
    </s:else>
        
    <s:hidden name="role_id" value="%{#role_id}" />
    <s:set name="role_idTemp" value="%{#role_id}" />
    <s:set name="locChkCondition" value="%{''}" />
    <s:if test="%{#role_id == 0}">
      <s:set name="locationSess" value="%{#session.locationId}"/>
      <!--value <s:property value="%{#locationSess}"/>-->
      <s:set name="locationIdNum" value="%{#locationSess}" />
      <s:set name="locChkCondition" value="%{'allow'}" />
    </s:if>
    <s:if test="%{#role_id == 2 || #role_id == 3}">
      <s:set name="clientId" value="%{#attr.clientId}" />
    </s:if>
    <s:set name="locationIdMain" value="%{#request.locationId}" />
     <!--valuemain <s:property value="%{#locationIdMain}"/>-->
    <s:if test="%{#locationIdMain == null}">
      <c:catch var="exception3">
        <s:set name="locationIdMain" value="%{#session.locationIDTemp}"/>
      
      </c:catch>
      <s:if test="%{#exception3 != null}">
        <s:set name="locationIdMain" value="%{''}"/>
      
      </s:if>
    </s:if>
    <c:catch var="exception2">
     <s:if test="%{#locationIdMain == null || #locationIdMain == ''}">
       <s:set name="locationIdMain" value="%{''}" />
     </s:if>
    </c:catch>
    <s:if test="%{#exception2 != null}">
     <s:set name="locationIdMain" value="%{''}" />
    </s:if>
    <s:if test="%{#locationIdMain == ''|| #role_id == '3' }" >
      <s:set name="locationChkCondition" value='%{"notallow"}' />
    </s:if>
    <s:else>
      <s:set name="locationChkCondition" value='%{"allow}' />
      <s:set name="locationIdNum" value="%{#locationIdMain}" />
      <s:set name="locationId" value="%{#locationIdMain}" scope="session"/>
    </s:else>
 
    <c:catch var="customerException">
      <s:set name="clientDetailsHashMap" value="%{#session.clientDetailsHashMap}"/>
    </c:catch>
<!-- End -->
    <table width="100%" border="0" cellpadding="0" cellspacing="1" align="center">
      <tr valign="bottom">
         <td class="displayMessage">
           <s:set name="role_idTemp" value="%{#session.role_id}" />
             <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1" >
               <tr>
                 <td>
                        
                  </td>
                
                  
               </tr>
        
               <!--21/01/2015 sunanda added-->
          <s:set name="key" value="%{#attr.key}"/>
           <s:if test="%{#key != null}">
                <s:if test="%{#key==aasc500||#key==aasc502||#key==aasc504||#key==aasc506}">
                  <td width="90%" class="displayMessage" id="displayMessage" align="center" valign="bottom">
                    <s:property value="getText(#key)"/> 
                  </td>
                </s:if>
                <s:else>
                  <td width="90%" class="displayMessage1" id="displayMessage" align="center" valign="bottom">
              <s:property value="getText(#key)"/> 
                  </td>
                </s:else>
              <s:set name="key" value="%{'null'}"/>
           </s:if>
          
             </table>
          </td>
       </tr>
     </table>
     <br> 
     
     
    <!-- <div class="form-group" style="margin-top: 18px; ">-->
     <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%">
        <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <div style="color:#ffffff;padding-left:10px;font-size: 20px;margin-top:5px;font-weight:bold" align="center"><s:property value="getText('PackageDimensions')"/></div>
                    </div>
                    </br>
                    <div class="row" id="divSub">
        <s:if test='%{#role_id == "2" }'>
            
                <label  class="control-label col-sm-1" style="text-align: left;padding-top: 5px;"> <span class="dispalyFields" > <s:property value="getText('CustomerName')"/></span></label>
                 
             <s:set name="disableFlag" value="true"/>
                
                <div class="col-sm-2">
                    <s:select list="#clientDetailsHashMap" name="clientIdSelect" id="clientIdSelect" class="form-control" listKey="key" listValue="value"   size="1" 
                         onchange="return getlocations(); "
                         headerValue="Select" headerKey="" value="#clientId"/> 
                    <s:hidden name="clientIdHidden" id="clientIdHiddenID" value="%{#clientId}"/>  
                       </div>
                </s:if>
                <s:else>
                  <div class="col-sm-1">
                    <s:hidden name="clientIdHidden" id="clientIdHiddenID" value="%{#client_id}"/>
                  </div>
                </s:else> 
                <s:if test='%{#role_id == "2" || #role_id == "3" ||#role_id == "4" }'>
                   
                        <label  class="control-label col-sm-1" style="text-align: left;padding-top: 5px;"> <span class="dispalyFields" > <s:property value="getText('Location')"/></span></label>
                       
                    <s:set name="chkCondition"  value="%{#chkCondition.toLowerCase()}" />
                    <s:hidden name="locationIdHidden" value="%{#locationId}" />
                    <s:if test='%{#role_id == "3"}'>
                         <s:set name="locationChkCondition" value='%{"notallow"}' />
                    </s:if> 
                    <s:if test='%{#chkCondition == "notallow" }' >
                        <div class="col-sm-2" >
                            <s:select list="#locationDetailsMap" listKey="key" listValue="value" name="locationId" class="form-control" headerKey="" headerValue="--Select--" id="location"  value="#locationIdNum" onchange="ChangeLocation();"  />
                          </div>     
                    </s:if>
                    <s:else>
                        <div class="col-sm-2" >
                            <s:select list="#locationDetailsMap" listKey="key" listValue="value" class="form-control"  name="locationId"   headerKey="" headerValue="--Select--" id="location"  value="#locationIdNum" onchange="ChangeLocation();" />
                        </div>
                    </s:else>
                    
                </s:if>
                
                <s:else>
                </s:else>
                <s:if test='%{#role_id == "2"}'>
                  <div class="col-sm-1">
                    <button class="btn btn-primary" name="GoButton" id="GoButton" onclick="document.profileOptionsForm.actionType.value='Go'; enableSave();" value="Go" alt="[submit]" align="middle"> Go <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                   </div>
                   </s:if>
                   <s:if test='%{#role_id == "3" || #role_id == "4"}'>
                     <div class="col-sm-1">
                        <button class="btn btn-primary" name="GoButton"  id="GoButton" onclick="document.PackageDimensionForm.actiontype.value='Go';" value="Go"  alt="Go" align="left"> Go <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                        </div>
                        
                        </s:if>
                    <s:hidden name="actiontype" />
                    <s:hidden name="validateKey" value="%{#validateKey}"/>
                    <s:if test='%{#role_id == "3"}'>
                    <div class="col-sm-1" >  </div>
                     <div class="col-sm-1" >    
                        <button class="btn btn-success" name="CreateButton"  id="CreateButton" onclick="document.PackageDimensionForm.actiontype.value='Create';" value="Create"  alt="Create" align="middle"> Create <span class="badge"><span class="glyphicon glyphicon-pencil"></span></span></button>
                       </div>
                       <div class="col-sm-1" >  </div>
                       <div class="col-sm-1">
                        <button class="btn btn-primary" name="SaveButton"  id="SaveButton" onclick="document.PackageDimensionForm.actiontype.value='Save'; return methodOnSave();" value="Save"  alt="Update" align="middle"> Update <span class="badge"><span class="glyphicon glyphicon-cloud"></span></span></button>
                     </div>
                </s:if>
                
                
                
            </div>
            </br>
                
                </div>
                
     
     
     
     
    <!-- <table width="80%" border="0" align="center">
     <tr>
        <td  width="30%" colspan="23" class="pageHeading" > <s:property value="getText('PackageDimensions')"/>  </td>
     </tr>
     <tr>
       <s:if test='%{#role_id == "2" }'>
         <td class="dispalyFields"> <s:property value="getText('CustomerName')"/></td>
         <td align=left nowrap class="dispalyFields" >
         <s:set name="disableFlag" value="true"/>
         <s:select list="#clientDetailsHashMap" name="clientIdSelect" id="clientIdSelect" listKey="key" listValue="value" class="form-control"  cssClass="inputs" size="1" 
                         onchange="return getlocations(); "
                         headerValue="Select" headerKey="" value="#clientId"/> 
         <s:hidden name="clientIdHidden" id="clientIdHiddenID" value="%{#clientId}"/>  
         </td> 
       </s:if>
       <s:else>
         <TD>
           <s:hidden name="clientIdHidden" id="clientIdHiddenID" value="%{#client_id}"/>
         </TD>
       </s:else>             
       <s:if test='%{#role_id == "2" || #role_id == "3" ||#role_id == "4" }'>
        <td align="left" class="dispalyFields"><s:property value="getText('Location')"/></td>
        <td>
        <s:set name="chkCondition"  value="%{#chkCondition.toLowerCase()}" />
      <s:hidden name="locationIdHidden" value="%{#locationId}" />
      
     <s:if test='%{#role_id == "3"}'>
          <s:set name="locationChkCondition" value='%{"notallow"}' />
        </s:if> 
        <s:if test='%{#chkCondition == "notallow" }' >
         <div class="col-sm-3">
          <s:select list="#locationDetailsMap" listKey="key" listValue="value" name="locationId" class="form-control" headerKey="" headerValue="--Select--" id="location" cssClass="inputs" value="#locationIdNum" onchange="ChangeLocation();" height="30px" />
        </div>       
        </s:if>     
        <s:else>
         
          <div class="col-sm-3">
          <s:select list="#locationDetailsMap" listKey="key" listValue="value" class="form-control"  name="locationId"   headerKey="" headerValue="--Select--" id="location" cssClass="inputs" value="#locationIdNum" onchange="ChangeLocation();" />
          </div>
        </s:else>
        </td>
      </s:if>  
      <s:else>
      </s:else>
      <s:if test='%{#role_id == "2"}'>
       
          <button class="btn btn-primary" name="GoButton"  id="GoButton" onclick="document.PackageDimensionForm.actiontype.value='Go';" value="Go"  alt="Go" align="left"> Go <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button></td></s:if>
      <s:if test='%{#role_id == "3" || #role_id == "4"}'>
       <td align="left" width="15%" class="dispalyFields" border="0">
          <button class="btn btn-primary" name="GoButton"  id="GoButton" onclick="document.PackageDimensionForm.actiontype.value='Go';" value="Go"  alt="Go" align="left"> Go <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button></td></s:if>
          <s:hidden name="actiontype" />
            <s:hidden name="validateKey" value="%{#validateKey}"/>
          <s:if test='%{#role_id == "3"}'>
           <td align="right">    
           <button class="btn btn-success" name="NewButton"  id="NewButton" onclick="document.PackageDimensionForm.actiontype.value='Create';" value="Create"  alt="Create" align="middle"> Create <span class="badge"><span class="glyphicon glyphicon-pencil"></span></span></button>
           <button class="btn btn-primary" name="SaveButton"  id="SaveButton" onclick="document.PackageDimensionForm.actiontype.value='Save'; return methodOnSave();" value="Save"  alt="Update" align="middle"> Update <span class="badge"><span class="glyphicon glyphicon-cloud"></span></span></button>
           </td>
        </s:if>
     </tr>
    </table>-->
    <br>
    <div class="form-group">
     <fieldset style="border-color:#7761a7; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;border-width:2px"> 
     <table  class="table" style="overflow-x:auto;font-size:small;">
      <thead>
       <tr>
          <s:if test='%{ #role_id == "3" }' >
            <th align="right" nowrap style="background-color:#7761a7; color:#ffffff;font-size: 13px;font-weight: bold;"  ><s:property value="getText('Edit')"/></th>
          </s:if>
            <th align="left" nowrap style="background-color:#7761a7; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('DimensionName')"/></th>
            <th align="left" nowrap style="background-color:#7761a7; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Length')"/></th>
            <th align="left" nowrap style="background-color:#7761a7; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Width')"/></th>
            <th align="left" nowrap style="background-color:#7761a7; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Height')"/></th>
            <th align="left" nowrap style="background-color:#7761a7; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Units')"/></th>
            <th align="left" nowrap style="background-color:#7761a7; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Active')"/></th>
            <th align="left" nowrap style="background-color:#7761a7; color:#ffffff;font-size: 13px;font-weight: bold;" ><s:property value="getText('Default')"/></th>
       </tr>
       </thead>
       <s:hidden name="currentRow" value="%{''}" />
       <s:set name="dimensionCount" value="%{0}"/>
       <s:set name="dimensionId" value="%{0}" />
       <s:set name="dimensionLength" value="%{0}" /> 
       <s:set name="dimensionWidth" value="%{0}" />
       <s:set name="dimensionHeight" value="%{0}" />
       <s:iterator id="packageDimensionIterator" value="%{#packageDimensionList}" >
       <s:set name="dimensionCount" value="%{#dimensionCount+1}" />
       <s:set name="aascPackageDimensionInfo1" value="%{#packageDimensionIterator}" />
       <s:set name="dimensionId" value="%{#aascPackageDimensionInfo1.dimensionId}" />
       <s:set name="dimensionName" value="%{#aascPackageDimensionInfo1.dimensionName}" />
       <s:set name="dimensionLength" value="%{#aascPackageDimensionInfo1.dimensionLength}" />
       <s:set name="dimensionWidth" value="%{#aascPackageDimensionInfo1.dimensionWidth}" />
       <s:set name="dimensionHeight" value="%{#aascPackageDimensionInfo1.dimensionHeight}" />
       <s:set name="dimensionUnits" value="%{#aascPackageDimensionInfo1.dimensionUnits}" />
       <s:set name="dimensionActive" value="%{#aascPackageDimensionInfo1.dimensionActive}" />
       <s:set name="dimensionDefault" value="%{#aascPackageDimensionInfo1.dimensionDefault}" />
       <s:if test='%{#dimensionActive.toLowerCase() == "y"}' >
          <s:set name="activeCheckstatus" value="%{'checked'}" />
          <s:set name="dimensionActive" value="%{'Y'}" />
       </s:if>
       <s:else>
          <s:set name="activeCheckstatus" value="%{'unchecked'}" />
          <s:set name="dimensionActive" value="%{'N'}"  />
       </s:else>
       <s:if test='%{#dimensionDefault.toLowerCase() == "y"}' >
          <s:set name="defaultCheckstatus" value="%{'checked'}" />
          <s:set name="dimensionDefault" value="%{'Y'}" />
       </s:if>
       <s:else>
          <s:set name="defaultCheckstatus" value="%{'unchecked'}" />
          <s:set name="dimensionDefault" value="%{'N'}" />
       </s:else>
       <tr>
       <s:if test='%{ #role_id == "3" }' >
          <td align="left">
          <button class="btn btn-primary" name="dimensionIdImg${dimensionCount}"  id="dimensionIdImgId${dimensionCount}" onclick="document.PackageDimensionForm.actiontype.value='Edit'; methodOnEdit('${dimensionCount}');" value="${dimensionId}"  alt="Edit" align="middle">  <span><span class="glyphicon glyphicon-pencil"></span></span></button>
         </s:if>
       <s:hidden name ="dimensionName%{#dimensionCount}" cssClass="dispalyFieldsWithOutWidth"  id="%{#dimensionCount}" value = "%{#dimensionName}"  />
       <s:hidden name ="dimensionLength%{#dimensionCount}" cssClass="dispalyFieldsWithOutWidth"  id="%{#dimensionCount}" value = "%{#dimensionLength}" />
       <s:hidden name ="dimensionWidth%{#dimensionCount}" cssClass="dispalyFieldsWithOutWidth"  id="%{#dimensionCount}" value = "%{#dimensionWidth}" />
       <s:hidden name ="dimensionHeight%{#dimensionCount}"  cssClass="dispalyFieldsWithOutWidth"  id="%{#dimensionCount}" value = "%{#dimensionHeight}" />
       <s:hidden name ="dimensionUnits%{#dimensionCount}"  cssClass="dispalyFieldsWithOutWidth"  id="%{#dimensionCount}" value = "%{#dimensionUnits}" />
       <td nowrap class="dispalyFieldsWithOutWidth" id="dimensionNameId${dimensionCount}" ><s:property value="%{#dimensionName}"/></td>
       <td nowrap class="dispalyFieldsWithOutWidth" id ="dimensionLengthId${dimensionCount}"><s:property value="%{#dimensionLength}" /></td>
       <td nowrap class="dispalyFieldsWithOutWidth" id ="dimensionWidthId${dimensionCount}"><s:property value="%{#dimensionWidth}" /></td>
       <td nowrap class="dispalyFieldsWithOutWidth" id ="dimensionHeightId${dimensionCount}"><s:property value="%{#dimensionHeight}" /></td>
       <td nowrap class="dispalyFieldsWithOutWidth" id ="dimensionUnitsId${dimensionCount}"><s:property value="%{#dimensionUnits}" /></td>
       <!--still need to check on checkbox and radio button -->
       <td width="5"><input name ="dimensionActive${dimensionCount}" type = "checkbox"  ${activeCheckstatus}  id="dimensionActive${dimensionCount}" value ="${dimensionActive}" /></td>
       <td width="5"><input name ="dimensionDefault${dimensionCount}" type = "radio" onclick="methodForDefault('${dimensionCount}');" class="inputFields" ${defaultCheckstatus}  id="dimensionDefault${dimensionCount}" value ="${dimensionDefault}" /></td>
       </tr> 
       <s:hidden name="dimensionId%{#dimensionCount}" value="%{#dimensionId}" />
       </s:iterator>
       <s:hidden name="dimensionCount" value="%{#dimensionCount}" />
    </table>
    </fieldset>
    </div>
        </s:form>
    </div>
     <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
