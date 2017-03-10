<%/*========================================================================+
@(#)aascUpdateCustomer.jsp 24/12/2014
* Copyright (c) 2014-2015 Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For order imports
* @version 1
* @author Jagadish Jain
* @history
* 15/01/2015  Y Pradeep     Merged Sunanda's code for getting title name from Application.Property file.
* 04/02/2015  K Sunanda     Added Id for display messages.
* 16/04/2015  Y Pradeep     Added missing id's for button.
* 15/04/2015  Suman G       Added session to fix #2743
* 13/05/2015  Suman G       Added code to fix #2771  
* 28/05/2015  Sunanda k     Modified code for bug fix #2952
* 11/06/2015  Suman G       Removed fevicon to fix #2992
* 23/06/2015  Rakesh K      Modified the code for fix #3074.
* 09/07/2015  Dinakar       Aligined UI as per tab
24/07/2015  Rakesh K    Modified Code to work application in offline.
04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
26/08/2015  Rakesh K       Added code to solve 3496.
*/
%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>

  <!--<meta name="viewport" content="width=device-width, initial-scale=1"></meta>
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->
  
  <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
   
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
    <!--<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>-->
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
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
  
  
<title><s:property value="getText('SCImportOrders')" /></title>

    <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="jquery-ui.css" />
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script type="text/javascript">


function onLoad(){

document.getElementById('loaderTagID').style.display='none';

}

function showLoader(){

var file= document.uploadOrders.orderFile.value;

if(file=="" || file==null)
{
alert("Please select csv file to upload");

return false;
}

var txtCheck = file.indexOf(".txt");
var csvCheck=file.indexOf(".csv");

if(csvCheck==-1)
{
alert("Invalid file format. Please select valid csv file.");
return false;
}


document.getElementById('loaderTagID').style.display='block';

}



function downloadfile(){
    document.uploadOrders.actiontype.value='downloadfile';
    document.uploadOrders.submit();
    }

function beforeUploading(){
    alert('Please upload the file before downloading report.');
    return false;
}


</script>
 <style type="text/css">
      html {
        height: 100%;
            }
            #downloadSampleLinkId {
    color:blue;
}

#reportLinkID{
    color:blue;
}
#sampleDownloadURL{
    color:blue;
}
    </style>
</head>
<body class="gradientbg" onload="onLoad()" style="background-color:#ADADAD; width:100%;">
<div  class="container-fluid" style="background-color:#ADADAD; width:100%;">

  <table width="100%" align="center"  border="0" cellpadding="0" cellspacing="0">
    <tr valign="top">
             <td> 
                <table width="100%">
                    <tr> 
                        <td colspan="3" align="center"><%@ include file="aascIndexHeader.jsp"%></td>
                        </tr> 
                    </table>
             </td>
            </tr>
        </table>
        
        
     
   
   <s:form action="uploadOrders" method="post" enctype="multipart/form-data" >
   <%
       int clientId = ((Integer)session.getAttribute("client_id")).intValue();
       //System.out.println("11111 clientId : "+clientId1);
       int userId=(Integer)session.getAttribute("user_id");
       //System.out.println("11111 userId1 : "+userId1);
       int locationId=((Integer)session.getAttribute("location_id")).intValue();
       //int orgID1=((Integer)session.getAttribute("orgId")).intValue();
       String uploadFlag=(String)session.getAttribute("uploadFlag");
       session.removeAttribute("uploadFlag");
       System.out.println("uploadFlag::::"+uploadFlag);
       pageContext.setAttribute("uploadFlag",uploadFlag);
       pageContext.setAttribute("client_id",clientId);
       pageContext.setAttribute("user_id",userId);
       pageContext.setAttribute("location_id",locationId);
       %>
        <s:set name="client_Id" value="%{#attr.client_id}"/>
        <s:set name="user_Id" value="%{#attr.user_id}"/>
        <s:set name="location_Id" value="%{#attr.location_id}"/>
        <s:set name="uploadFlag" value="%{#attr.uploadFlag}"/>
        <s:set name="locationDetailsList" value="%{#session.customersList}"/>  
        <s:hidden name="client_Id" /> 
        <s:hidden name="user_Id" /> 
        <s:hidden name="location_Id" /> 
        <s:hidden name="actiontype" /> 
   
    <table width="90%" align="center">
   
 
 <tr><td align="center">
 <s:set name="key" value="%{#request.key}"/>
 <s:if test="%{#key != null}">
                      <span class="displayMessage" id="displayMessage">
                        <s:property value="getText(#key)"/>
                        <br></br>
                      </span>
                    </s:if>
</td>
<td></td>
</tr>
 
 </table>
   
   <br> 
   <div class="row"><br/></div>
            <div class="row"><br/></div>
            <center>


<!-- <h1 align="center" style="font-weight:bold;font-size:x-large;color:steelblue" >Import Orders</h1>   <br>  <br> <br> <br> -->


   
    
    
    
    <!--  <img src="images/importOrders.png"   alt="" align="middle">   <br> <br> <br> -->
  
    
    
   
  <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:90%"> 
     <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
        <label style="color:#ffffff;font-size:20px;">Import Orders</label>
    </div> <br> <br>
  
  <div class="row" > 
  
  <div class="col-sm-1">
  </div>
  <div class="col-sm-4">
  <label for="orderFile" class="dispalyFields" style="font-family:inherit" ><s:property value="getText('Uploadyourfile')"/> &nbsp; </label>  
     </div>
       <div class="col-sm-3">
        <s:file labelposition="center" name="orderFile" id="orderFileId"  />  
     </div>
     <div class="col-sm-3">
        <button id="uploadImageId" class="btn btn-info"  onclick="document.uploadOrders.actiontype.value='uploadOrders'; return showLoader();">Upload <span class="badge"><span class="glyphicon glyphicon-cloud"></span></span></button>   
     </div>
     <div class="col-sm-1">
     </div>
    </div>
    <br/>
    
    <div class="row" align="center">

<s:a href="javascript: downloadfile();" id="downloadSampleLinkId" onclick="" ><s:property value="getText('ImportOrderSampleFile')"/> </s:a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <s:if test='%{#uploadFlag == "Y" || #uploadFlag == "y"}' >
        <s:a href="aascDownloadUploadOrdersReport.jsp" id="reportLinkID" ><s:property value="getText('ImportOrderDownloadReport')" /></s:a>  
    </s:if>
    <s:else>
        <s:a href="#" onclick="return beforeUploading();"  id="reportLinkID"><s:property value="getText('ImportOrderDownloadReport')"/></s:a>  <!--Sunanda modified code for bug fix #2592-->
  </s:else>
</div>
    

     <!-- <input type="image" id="uploadImageId" src="buttons/aascUpload.png" onclick="document.uploadOrders.actiontype.value='uploadOrders'; return showLoader();"/> <br> <br> -->
     
        
       
  <br/>
  



<div id="loaderTagID" align="center">
<img src="images/loader2.gif" alt=""/>Importing Orders...
</div>
 <br/>

<!--<div class="row" align="center" >

<s:a href="javascript: downloadfile();" id="downloadSampleLinkId" onclick=""  ><s:property value="getText('ImportOrderSampleFile')"/> </s:a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <s:if test='%{#uploadFlag == "Y" || #uploadFlag == "y"}' >
        <s:a href="aascDownloadUploadOrdersReport.jsp" id="reportLinkID" ><s:property value="getText('ImportOrderDownloadReport')" /></s:a>  
    </s:if>
    <s:else>
        <s:a href="#" onclick="return showLoader();"  id="reportLinkID" ><s:property value="getText('ImportOrderDownloadReport')"/></s:a>
  </s:else>
</div>--><!--Sunanda modified code for bug fix #2592-->
</div>
</center>
   </s:form>
 </div>   
  <div style="position:absolute;top:620px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
</body>
</html>
