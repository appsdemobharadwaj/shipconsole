<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%/*========================================================================+
@(#)aascUploadShipToLocations.jsp
* Copyright (c) 2014 Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Upload ShipTo Locatin details
* @version 2 
* @author Eshwari M
* @history
* 
* 07-Jan-2015   Y Pradeep    Merged Sunandas code : Removed the hard codings by getting from Properties file and added downloadfile() function.
* 20-Jan-2015   K Sunanda    Removed Scriplets and added author name.
* 10-03-2015    K Sunanda    Added code for the bug fix #2497
* 10-03-2015    K Sunanda    Renamed the javascript name to make it meaningful and changed this js filename in the jsp
* 15-04-2015    Suman G      Added session to fix #2743.
* 23-04-2015    Y Pradeep    Removed footer.
* 08-06-2015    K Sunanda    Modified code for bug fix #2987
* 11-06-2015    Suman G      Removed fevicon to fix #2992
* 23-06-2015    Rakesh K     Modified the code for fix #3060.
* 22-07-2015    Rakesh K     Modified the UI alinments of the screen.
* 24/07/2015    Rakesh K     Modified Code to work application in offline.
* 04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
* 26/08/2015  N Srisha    Added Id's for Automation testing
26/08/2015  Rakesh K       Added code to solve 3496.
* 23/12/2015 Suman G     Added code for Error report for Upload Ship To Locations.
*/
%>


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
    <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
     <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />-->
    
       
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    
<title><s:property value="getText('ShipToLocationsUpload')"/></title>
<link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="jquery-ui.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script language="JavaScript" SRC="aasc_Ship_To_Location.js"  type="text/javascript">
    </script>
    <script language="JavaScript" type="text/javascript">
 
 function onLoad(){

  document.getElementById('loaderTagID').style.display='none';

}
    
function downloadfile(){
document.uploadShipTo.actiontype.value='downloadfile';
document.uploadShipTo.submit();
}
function showLoader(){

    var file= document.uploadShipTo.myFile.value;
    
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

function beforeUploading(){
    alert('Please upload the file before downloading report.');
    return false;

}
    </script>
    
  <script language="JavaScript" src="jquery-ui.js" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
   
    <style type="text/css">
      html {
        height: 100%;
            }
    </style>
</head>

<body onload="onLoad()">
<%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
%>

<%@include file ="aascIndexHeader.jsp" %>
 <div class="container-fluid" style="background-color:#ADADAD;">
   <s:form action="uploadShipTo" method="post" enctype="multipart/form-data"> 
    <s:set name="client_Id" value="%{#session.client_id}"/>
    <s:set name="user_Id" value="%{#session.user_id}"/>
    <s:set name="location_Id" value="%{#session.location_id}"/>
    <s:set name="locationDetailsList" value="%{#session.customersList}"/>  
    <s:hidden name="client_Id" /> 
    <s:hidden name="user_Id" /> 
    <s:hidden name="location_Id" /> 
    <s:hidden name="actiontype" /> 

<table width="90%" align="center">	
	   <!-- <tr>
       <td height="50px" class="pageHeading" align="center" valign="middle">
        <s:property value="getText('UploadCustomerLocationsDetail')"/> 
      </td>
    </tr>  -->
	
     <tr><td align="center">
    <s:set name="key" value="%{#request.key}"/>
 <s:if test="%{#key != null}">
                      <span class="displayMessage" id="displayMessage">
                        <s:property value="getText(#key)"/>
                        <br></br>
                      </span>
                    </s:if>
    </td></tr>     
</table>
<%

       String uploadShipToFlag=(String)session.getAttribute("uploadShipToFlag");
       session.removeAttribute("uploadShipToFlag");
       System.out.println("uploadFlag::::"+uploadShipToFlag);
       pageContext.setAttribute("uploadShipToFlag",uploadShipToFlag);

%>
<s:set name="uploadFlag" value="%{#attr.uploadShipToFlag}"/>
<br/><br/><br/><br/>
         <div class="row">
            <div class="col-sm-12">
                <div class="col-sm-10 col-sm-push-1">
                
                    <fieldset style="border-color:#ea6153; overflow:auto;margin-top:1%;background-color:#F0F0F0;border-width:2px"> 
                        <div style="background-color:#ea6153;height:30px;margin-left:5px;width:99%" align="center">
                            <label style="color:#ffffff;font-size:20px">Upload Customer Location Details</label>
                        </div>
                        <div class="row" style="width:99%">
                            <div class="col-sm-12" style="margin-top:10px">
                                <div class="col-sm-4" align="center">
                                    <label for="myFile" style="margin-top:10px" class="dispalyFields">Upload your file &nbsp; </label>
                                </div>
                                <div class="col-sm-4" align="center">
                                    <input type="file" style="margin-top:10px" name="myFile" value="" id="uploadShipTo_myFile">
                                </div>
                                <div class="col-sm-4" align="center">
                                    <button class="btn btn-info" name="uploadShipToLocation" id="uploadShipToLocationId" onclick="document.uploadShipTo.actiontype.value='uploadShipToLocation'; return showLoader(); " >
                                    Upload <span class="badge"><span class="glyphicon glyphicon-cloud"></span></span></button>
                                </div>
                                
                            </div>
                            
                            <br/><br/>
                                <div class="row" align="center" style="width:100%;margin-top:30px">
                                    <a id="sampleDownloadURL" href="javascript: downloadfile();  ">Download Sample File</a>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <s:if test='%{#uploadFlag == "Y" || #uploadFlag == "y"}' >
                                        <s:a href="aascDownloadShipToUploadReports.jsp" id="reportLinkID" ><s:property value="getText('ImportOrderDownloadReport')" /></s:a>  
                                    </s:if>
                                    <s:else>
                                        <s:a href="#" onclick="return beforeUploading();"  id="reportLinkID"><s:property value="getText('ImportOrderDownloadReport')"/></s:a>  <!--Sunanda modified code for bug fix #2592-->
                                  </s:else>
                                </div>
                             
                        </div>
    <div id="loaderTagID" align="center">
<img src="images/loader2.gif" alt=""/>Importing Ship To Locations...
</div>
 <br/>
                        
            </fieldset> 
                    
                </div>
            </div>
         </div>

   </s:form>
   </div>
    <div style="position:absolute;top:620px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
</body>
</html>
