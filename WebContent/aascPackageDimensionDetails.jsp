<%/*========================================================================+
|  DESCRIPTION                                                              |
|    JSP For Package Dimensions Details                                            |                                                      |
|    Version - 1                                                            |       
|    20/11/2014   Sunanda.K      Added code from ShipConsoleCloud version1.2                        |
|    07/01/2015   Y Pradeep      Merged Sunandas code : Removed the hard codings by getting from Properties file 
|    15/01/2015   Y Pradeep      Merged Sunanda's code on 1.0 release bugs.
|    21/01/2015   K Sunanda      Added code to display success messages in green and errored in red for bug #2506
|    04/02/2015   K Sunanda      Added Id for display messages
|    17/02/2015   Suman G        Modified code to fix #2506.
|    15/04/2015   Suman G        Added session to fix #2743.
     05/05/2015   Y Pradeep      Modified code to display proper heading for Creating and Updating package dimensions details.
     11/06/2015   Suman G        Removed fevicon to fix #2992
     07/07/2015   Dinakar        Aligined UI as per tab
     21/07/2015    Suman G        Modified message id for QA
     24/07/2015   Rakesh K       Modified Code to work application in offline.
     28/07/2015   Dinakar G      Modified Code to fix bug #3256
     04/08/2015   Rakesh K       Modified Code bootstrap css file work across all browsers.
     26/08/2015  Rakesh K       Added code to solve 3496.
+===========================================================================*/%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/struts-tags" prefix="s" %>



<%@ page errorPage="aascShipError.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>

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
  
    <!--<link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->
  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('PackageDimensionDetails')"/></title>
     <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    
    <script language="javascript" type="text/javascript">
    
    function isInteger(s)        
    {
      var i;
      for (i = 0; i < s.length; i++)
      {   
       var c = s.charAt(i);
        if (((c < "0") || (c > "9")))
         return false;
      }
      return true;
    } 
   function Validation()
    {
     var DimensionName = document.PackageDimensionDetailForm.dimensionName.value;
     var width = document.PackageDimensionDetailForm.dimensionWidth.value;
     var Height = document.PackageDimensionDetailForm.dimensionHeight.value;
     var Unit=document.PackageDimensionDetailForm.dimensionUnits.options[document.PackageDimensionDetailForm.dimensionUnits.selectedIndex].text;
     var actiontype = document.PackageDimensionDetailForm.actiontype.value;
     var length=document.PackageDimensionDetailForm.dimensionLength.value;
     if(DimensionName.length>20)
      {
       alert("Dimension Name should not be greater than 20 characters");
       document.PackageDimensionDetailForm.dimensionName.focus();
       return false;
      }
     for(var index = 0; index <DimensionName.length;index++)
      {
       var c = DimensionName.charAt(index);
       if(c == '!'||c=='@'||c == '#'||c=='$'||c == '%'||c=='^'||c == '&'||c=='*'||c == '('||c==')'||c=='~')  
        {
         alert("Special Characters are not allowed for Dimension Name");
         document.PackageDimensionDetailForm.dimensionName.focus();
         return false;
        }
      }//end of if
     if(length>999999999)
       {
        alert("Length should not be greater than 999999999");
        document.PackageDimensionDetailForm.dimensionLength.focus();
        return false;
       }
     if(isInteger(document.PackageDimensionDetailForm.dimensionLength.value)==false)
       {	
         alert("Enter Numeric Value for Length");
         document.PackageDimensionDetailForm.dimensionLength.focus();
         return false;
       }
     if(width>999999999)
       {
        alert("Width should not be greater than 999999999");
        document.PackageDimensionDetailForm.dimensionWidth.focus();
        return false;
       }
    if(isInteger(document.PackageDimensionDetailForm.dimensionWidth.value)==false)
       {	
         alert("Enter Numeric Value for Width");
         document.PackageDimensionDetailForm.dimensionWidth.focus();
         return false;
       }
    if(Height>999999999)
       {
        alert("Height should not be greater than 999999999");
        document.PackageDimensionDetailForm.dimensionHeight.focus();
        return false;
       }
    if(isInteger(document.PackageDimensionDetailForm.dimensionHeight.value)==false)
       {	
         alert("Enter Numeric Value for Height");
         document.PackageDimensionDetailForm.dimensionHeight.focus();
         return false;
       }
       return true;
     }
     function checkData()
      {
          var DimensionNameVal = document.PackageDimensionDetailForm.dimensionName.value;
          var lengthVal=document.PackageDimensionDetailForm.dimensionLength.value;
          var widthVal=document.PackageDimensionDetailForm.dimensionWidth.value;
          var heightVal=document.PackageDimensionDetailForm.dimensionHeight.value;
          var UnitVal=document.PackageDimensionDetailForm.dimensionUnits.options[document.PackageDimensionDetailForm.dimensionUnits.selectedIndex].text;
          if(DimensionNameVal=="")
              {
               alert("Please Enter Dimension Name");
               document.PackageDimensionDetailForm.dimensionName.focus();
               return false;
              }
          if(lengthVal=="")
              {
               alert("Please Enter Length");
               document.PackageDimensionDetailForm.dimensionLength.focus();
               return false;
              }
          if(widthVal=="")
              {
               alert("Please Enter Width");
               document.PackageDimensionDetailForm.dimensionWidth.focus();
               return false;
              }
          if(heightVal=="")
              {
               alert("Please Enter Height");
               document.PackageDimensionDetailForm.dimensionHeight.focus();
               return false;
              }
          if(UnitVal=="-- Select Unit --")
              {	
               alert("Please Select Unit");
               document.PackageDimensionDetailForm.dimensionUnits.focus();
               return false;
              }
          if(lengthVal >0 && widthVal >0 && heightVal >0)
            {
              }else{
                  alert("Dimension Length, Width and Height should be greater than zero");
                  return false;
            }
            return true;
      }
     function loadFunction()
     {
        var validateKey=document.PackageDimensionDetailForm.validateKey.value;
        if(validateKey == 'aasc509')
        {
        var actiontype=document.PackageDimensionDetailForm.actiontype.value;
        var status1=confirm('Dimension already created with this combination. Do you want to have the same combination ?');
        if(status1)
        {
            if(actiontype == 'Update' || actiontype == 'AllowUpdate')
            {
                document.PackageDimensionDetailForm.actiontype.value='AllowUpdate';
            }else if(actiontype == 'AddNewDimension' || actiontype == 'AllowAddNewDimension')
            {
                document.PackageDimensionDetailForm.actiontype.value='AllowAddNewDimension';
            }
            document.PackageDimensionDetailForm.submit();                                    
            return true;
        }
        else
        {
            return false;
        }                            
        }
        return true;                        
     }
     function openNewWindow (URL, windowName, windowOptions)
     {
          var window = getWindow (URL, windowName, windowOptions);
     }
     function getWindow(URL, windowName, windowOptions)
     {
        var newWindow = open (URL, windowName, windowOptions)
        newWindow.focus();
        return window;
     }
    </script>
    <c:if test="${pageContext.session['new'] || (pageContext.session.id != sessionScope.SessionId)}" >
       <c:redirect url="/aascShipError.jsp">
         <c:param name="errorObj" value="SessionError" />
       </c:redirect>
    </c:if>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css">
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <style type="text/css">
      html{ height:100%;  }
    </style>
    </head>
    <body class="gradientbg" onload="return loadFunction();">
    
     <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
    
    
      <s:set name="validateKey" value="%{''}"/>
      <s:set name="locationInt" value="%{0}"/>
      <s:set name="key" value="%{#request.key}"/>
      <s:if test='%{#key == "aasc509"}' >
        <s:set name="validateKey" value="%{#key}" />
      </s:if>
      <s:set name="unitList" value="%{#session.unitList}"/>
      <s:set name="aascPackageDimensionInfo1" value="%{#session.aascPackageDimensionEditInfo}"/>
      <c:set var="actionValue" value="${requestScope.actionValue}" scope="page"/>
      <s:set name="actionValue" value="%{#attr.actionValue}" />
      <c:set var="rowNo" value="${param.currentRow}" scope="page"/>
      <c:set var="locationStr" value="${param.locationId}" scope="page"/>
      <c:set var="actionType" value="${param.actiontype}" scope="page"/>
      <s:set name="rowNo" value="%{#attr.rowNo}"/>
      <s:set name="locationStr" value="%{#attr.locationStr}"/>
      <s:set name="locationInt" value="%{#locationStr}"/>
      <s:set name="actionType" value="%{#attr.actionType}"/>
      <c:catch var="Exception1" >
        <s:set name="actionValueStr" value="%{#actionValue.substring(0,1)}"/>
      </c:catch>
      <s:if test="%{#Exception1 != null}" >
        <s:set name="actionValue" value="" />
      </s:if>
      <s:if test='%{#actionType == "Edit" || #actionValue == "ErrorCondition"}' >
         <s:set name="dimName" value="%{#aascPackageDimensionInfo1.dimensionName}" />
         <s:set name="dimLength" value="%{#aascPackageDimensionInfo1.dimensionLength}" />
         <s:set name="dimWidth" value="%{#aascPackageDimensionInfo1.dimensionWidth}" />
         <s:set name="dimHeight" value="%{#aascPackageDimensionInfo1.dimensionHeight}" />
         <s:set name="dimUnit" value="%{#aascPackageDimensionInfo1.dimensionUnits}" />
      </s:if>
      <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
      <s:form  name="PackageDimensionDetailForm" method="POST" action="AascPackageDimensionsDetails" onsubmit="return Validation();" >
      <s:hidden name="locationId" value="%{#locationStr}"/>
      <c:catch var="Exception2" >
      <!--21/01/2015 sunanda added-->
       <s:if test="%{(#key != null) && (#key != 'aasc509')}" >
          <s:if test="%{#key==aasc500||#key==aasc502||#key==aasc504||#key==aasc506}">
            <div class="col-sm-8 displayMessage1" width="90%" id="displayMessage" align="center" valign="bottom">
                    <s:property value="getText(#key)"/>
                   </div>
              </s:if>
            <s:else>
                <div class="col-sm-4 displayMessage" width="90%" id="displayMessage" align="center" valign="bottom">
                     <s:property value="getText(#key)"/> 
                   </div>
             </s:else>
          <s:set name="key" value="%{''}" />
       </s:if>
      </c:catch>
      <s:if test="%{#Exception2 != null}" >
        <c:redirect url="/aascShipError.jsp" >
          <c:param name="errorObj" value="${Exception2.message}" />
        </c:redirect>
      </s:if>
      <s:set name="error" value="%{#session.Error}" />
      <c:catch var="Exception3" >
        <s:set name="errorStr" value="%{#error.substring(0,1)}"/>
      </c:catch>
      <s:if test="%{#Exception3 != null}" >
        <s:set name="error" value="%{''}"/>
      </s:if>
      <s:if test="%{(#error == null) || (#error == '')}">
        <s:set name="error" value="%{''}" />
      </s:if>
      <s:else>
         <s:set name="Error" value="%{''}" scope="session"/>
      </s:else> 
      <s:property value="#error"/>
      
      <div class="row">
      
            <%@ include file="aascIndexHeader.jsp"%>
                 
        <s:set name="key" value="%{#attr.key}"/>
                   
           <s:if test="%{#key != null}">
           <s:if test="%{#key=='aasc502'||#key=='aasc504'}">
            <div class="col-sm-8 displayMessage1" width="90%" id="displayMessage" align="center" valign="bottom">
                    <s:property value="getText(#key)"/>
                   </div>
               </s:if>
            <s:else>
             <div class="col-sm-4 displayMessage1" width="90%" id="displayMessage" align="center" valign="bottom">
                     <s:property value="getText(#key)"/> 
                   </div>
            </s:else>
              <s:set name="key" value="%{'null'}"/>
           </s:if>
      </div>
      <br><br>
      
    <br><br>
    
       <center> 
        <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:65%">
                  <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;padding-left:10px;font-size:20px">Create Package Dimensions</label>
                    </div> 
        <div class="row" id="divSub" style="width:80%; margin-left:12%;">
        <div class="row"><br/></div>
        <div class="col-sm-12">
                <label for="inputEmail" class="control-label col-sm-6" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('DimensionName')"/></span></label>
                <div class="col-sm-6">
                    <s:if test='%{#actionType == "Edit" || #actionType == "Update" || #actionType == "AllowUpdate"}' >
                        <s:textfield name="dimensionName" id="dimensionName" cssClass="inputs" readonly="true" value="%{#dimName}"/>
                    </s:if>
                    <s:else>
                        <s:textfield name="dimensionName" id="dimensionName" cssClass="inputs" onblur="return Validation();" maxlength="20" value="%{#dimName}"/>
                    </s:else>
                   </div>
               </div>    
            </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub" style="width:80%; margin-left:12%">
            <div class="col-sm-12">
                <label for="inputEmail" class="control-label col-sm-6" style="text-align: left;"> <span >&nbsp;<s:property value="getText('Length')"/> </span></label>
                <div class="col-sm-6">
                   <s:textfield name="dimensionLength" id="dimensionLength" cssClass="inputs"  value="%{#dimLength}"  onblur="return Validation();" maxlength="9"/>
                </div>
                </div>
            </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub" style="width:80%; margin-left:12%">
              <div class="col-sm-12">
                <label for="inputEmail" class="control-label col-sm-6" style="text-align: left;"> <span >&nbsp;<s:property value="getText('Width')"/> </span></label>
                <div class="col-sm-6">
                   <s:textfield name="dimensionWidth" id="dimensionWidth"  cssClass="inputs"  value="%{#dimWidth}"  onblur="return Validation();" maxlength="9"/>
                </div>
               </div> 
            </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub" style="width:80%; margin-left:12%">
              <div class="col-sm-12">   
                <label for="inputEmail" class="control-label col-sm-6" style="text-align: left;"> <span >&nbsp;<s:property value="getText('Height')"/> </span></label>
                <div class="col-sm-6">
                   <s:textfield name="dimensionHeight" id="dimensionHeight" cssClass="inputs"  value="%{#dimHeight}"  onblur="return Validation();" maxlength="9"/>
                </div>
              </div>  
            </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub" style="width:80%; margin-left:12%">
              <div class="col-sm-12">
                <label for="inputEmail" class="control-label col-sm-6" style="text-align: left;"> <span >&nbsp;<s:property value="getText('Units')"/> </span></label>
                <div class="col-sm-6" >
                   <s:set name="dimUnitLower"  value="%{#dimUnit.toLowerCase()}"/>
                   <s:select list="unitList" headerKey="" headerValue="-- Select Unit --" name="dimensionUnits" id="dimensionUnits" cssClass="inputs"  value="#dimUnit" />
                </div>
               </div> 
            </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub">
                
                <div class=" col-sm-3" >
                </div>
                <div class=" col-sm-6" >
                    <s:hidden name="actiontype" value="%{#actionType}"/>
                    <s:hidden name="location" value="%{#locationInt}" />
                    <s:hidden name="rowNo" value="%{#rowNo}"/>
                    <s:hidden name="validateKey" value="%{#validateKey}"/>
                    <s:if test='%{#actionType.toLowerCase() == "edit" || #actionType.toLowerCase() == "update" || #actionType.toLowerCase() == "allowupdate"}'> 
                                <button class="btn btn-primary" name="Edit"  id="EditButton" onclick="document.PackageDimensionDetailForm.actiontype.value='Update';return checkData();" value="Save"  alt="Update" align="middle"> Edit <span class="badge"><span class="glyphicon glyphicon-edit"></span></span></button>
                    </s:if>
                    <s:else>
                        <button class="btn btn-success" name="Save"  id="SaveButton" onclick="document.PackageDimensionDetailForm.actiontype.value='AddNewDimension';return checkData();" value="Save"  alt="Save" align="middle"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                    </s:else>
                         <button class="btn btn-warning" name="clearButton"  id="clearButton" onclick="document.PackageDimensionDetailForm.actiontype.value='Cancel';" value="Clear"  alt="Clear" align="middle"> Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
		</div>
                <div class=" col-sm-3" >
                </div>
                
             </div>
             </br>    
             </div>
        
        
        </center> 
   
  
  
  </s:form>
  </div>
   <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
 </body>
</html>
