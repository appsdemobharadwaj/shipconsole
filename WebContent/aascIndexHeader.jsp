<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%

//11/06/2015  Suman G          Removed fevicon to fix #2992
//24/06/2015  Rakesh K         Modified header alignment to fix #3067
//25/06/2015  Rakesh K         Increased User icon size
//02/07/2015  Rakesh K         Changed User button alignment
//29/07/2015  Suman G          Added Padmavathi's code to fix issue #2999
//26/08/2015  Dinakar G        Added id's for Automation testing.
//31/08/2015  Suman G          Added conditions based on Trial Status for hiding carrier related links to trial users
//02/03/2016  Suman G          Added code for Make Payment link in role 3.
%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.aasc.erp.model.*"%>

<html>
<head>

<!--<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9"></meta>
 <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
 <link type="text/css" rel="stylesheet" href="menu_header.css"/>
 <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
     <link type="text/css" rel="stylesheet" href="css/jquery-ui.css" /> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
     <!-- <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">      
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
   
         <script type="text/javascript" src="js/jquery.min.js" ></script>
         <script type="text/javascript" src="js/bootstrap.min.js" ></script>
       <script type="text/javascript" src="js/jquery-ui.js" ></script>
           <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
   <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>-->
   
<style type="text/css">

.href {color: #003399}

    </style>
    

    <script language="JavaScript" src="aasc_Index_Header_js.js" type="text/javascript"></script>
  </head>
  <body style="background-color:#ADADAD;">
  
<nav class="navbar" style="margin-bottom:5px;margin-top:5px;">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar" style="background-color:#76A6CF">
        <span class="icon-bar" style="background-color:#ffffff"></span>
        <span class="icon-bar" style="background-color:#ffffff"></span>
        <span class="icon-bar" style="background-color:#ffffff"></span>                        
      </button>
      <img src="images/aasc_Apps_Logo.png" alt="" width="202" height="48" onClick=""/>
     <!--<img src="images/ship_exec_logo.png" alt="ShipExec Image" width="202" height="48">-->
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
      <s:set name="roleId" value="%{#attr.role_id}"/>   
            <s:if test='%{#attr.roleId == 1 || #attr.roleId == 2}'>
                <s:if test='%{#attr.roleId == 1}' >
                    <li > 
                        <s:url action="IndexRequestAction" id="url">
                            <s:param name="requestType" value="%{'CreateCustomer'}"/>
                        </s:url>
                        <s:a href="%{url}" name="#CreateCustomer" id="#CreateCustomer">
                            <span> <s:property value="getText('CreateCustomer')"/>  </span>  
                        </s:a>
                    </li>      
                </s:if>
                <s:if test='%{#attr.roleId == 2}' >
                    <li >    
                        <s:url action="IndexRequestAction" id="url">
                            <s:param name="requestType" value="%{'CreateCustomer'}"/>
                        </s:url>
                        <s:a href="%{url}" name="#CreateCustomer" id="#CreateCustomer">
                            <span>   <s:property value="getText('CustomerDetails')"/> </span>
                                
                        </s:a>
                    </li>
                </s:if>                                    
            </s:if>
                          
                          
                 <s:if test="%{#attr.roleId == 2 || #attr.roleId == 4}">
                
                            <s:if test="%{#attr.roleId == 2}">  
                        <li >      <s:url action="IndexRequestAction" id="url">
                                <s:param name="requestType" value="%{\'Shipment1\'}"/>
                              </s:url>
                              <s:a href="%{url}" name="#Shipping.action1" id="#Shipping.action1">
                              <span>   <s:property value="getText(\'Shipping.action1\')"/> </span>
                              </s:a>
                       </li>   </s:if>
                          <s:else>
                         <li >     <s:url action="IndexRequestAction" id="url">
                                <s:param name="requestType" value="%{\'Shipment\'}"/>
                              </s:url>
                              <s:a href="%{url}"  name="#Shipping.action1" id="#Shipping.action1">
                             <span>   <s:property value="getText(\'Shipping.action1\')"/> </span>
                           </s:a>
                      </li>
                      </s:else>
                        
              </s:if> 
              
               <s:if test="%{#attr.roleId == 2 || #attr.roleId == 4}">
                    <s:if test='%{#attr.trialStatus == "N"}' >
                            <s:if test="%{#attr.roleId == 2}">  
                       <li >       <s:url action="IndexRequestAction" id="url">
                                <s:param name="requestType" value="%{\'UploadOrders\'}"/>
                              </s:url>
                              <s:a href="%{url}" name="#index.uploadOrders" id="#index.uploadOrders">
                        <span>    <s:property value="getText(\'index.uploadOrders\')"/> </span>
                          </s:a>
                      </li>    </s:if>
                          <s:else>
                         <li >
                          <s:url action="IndexRequestAction" id="url">
                            <s:param name="requestType" value="%{\'UploadOrders\'}"/>
                          </s:url>
                          <s:a href="%{url}" name="#index.uploadOrders" id="#index.uploadOrders">
                         <span>   <s:property value="getText(\'index.uploadOrders\')"/> </span>
                          </s:a>
                         </li> </s:else>
                    </s:if> 
                </s:if>
                        
                        
                <s:if test="%{#attr.roleId == 5 || #attr.roleId == 4 || #attr.roleId == 2}">
                 <s:if test="%{#attr.roleId == 2}">  
                       <li >   <s:url action="IndexRequestAction" id="url">
                                <s:param name="requestType" value="%{\'Tracking1\'}"/>
                              </s:url>
                              <s:a href="%{url}" name="#shipTrack.action1" id="#shipTrack.action1">
                               <span> <s:property value="getText(\'ShipTrack.action1\')"/> </span>
                               </s:a>
                               </li>
                          </s:if>
                          <s:if test="%{#attr.roleId == 4 || #attr.roleId == 5}">
                             <li > <s:url action="IndexRequestAction" id="url">
                                <s:param name="requestType" value="%{\'Tracking\'}"/>
                          </s:url>
                          <s:a href="%{url}" name="#shipTrack.action1" id="#shipTrack.action1">
                       <span>     <s:property value="getText(\'ShipTrack.action1\')"/> </span>
                          </s:a>
                         </li> </s:if>
                        
                </s:if>
             
            
                         <s:if test='%{ #attr.roleId == 2 || #attr.roleId == 4 || #attr.roleId == 5 }'>
                      <li >       <s:url action="IndexRequestAction" id="url">
                                    <s:param name="requestType" value="%{\'Reports1\'}"/>
                                  </s:url>
                                  <s:a href="%{url}" name="#reports.action1" id="#reports.action1">
                           <span>         <s:property value="getText(\'reports.action1\')"/> </span>
                                  </s:a>
                          </li>  </s:if>
                 
                
                <s:if test='%{#attr.roleId == 1 || #attr.roleId == 2 || #attr.roleId == 3 || #attr.roleId == 4 }'>
                    <s:if test='%{#attr.trialStatus == "N"}' >
                        <li >   <s:url action="IndexRequestAction" id="url">
                                                <s:param name="requestType" value="%{'ProfileOptions'}"/>
                                            </s:url>
                                            <s:a href="%{url}" name="#profileOptions" id="#profileOptions" >
                                                 <span> <s:property value="getText('profile.action1')"/> </span>
                                            </s:a>    
                        </li> 
                    </s:if>
                </s:if>
                
                 
                
                <s:if test='%{#attr.roleId == 1 || #attr.roleId == 2 || #attr.roleId == 3 || #attr.roleId == 4 }'>
                    <s:if test='%{#attr.trialStatus == "N"}' >
                         <li >    <s:url action="IndexRequestAction" id="url">
                                                <s:param name="requestType" value="%{'CarrierConfiguration'}"/>
                                            </s:url>
                                            <s:a href="%{url}" name="#CarrierConfiguration" id="#CarrierConfiguration">
                                                <span> <s:property value="getText('carrierConfig.action1')"/> </span>
                                            </s:a>
                           </li>  
                    </s:if>
                </s:if>
                
                
                  <s:if test="%{#attr.roleId == 3 || #attr.roleId == 4 || #attr.roleId == 2}">
                    <s:if test='%{#attr.trialStatus == "N"}' >
                         <li >      <s:url action="IndexRequestAction" id="url">
                                    <s:param name="requestType"
                                             value="%{\'PackageDimensions\'}"/>
                                  </s:url>
                            <s:a href="%{url}" name="#dimension.action1" id="#dimension.action1">
                                <span>    <s:property value="getText(\'dimension.action1\')"/> </span>
                            </s:a>   
                          </li>
                    </s:if>
                </s:if>
                
               <s:if test='%{#attr.roleId == 3 }'>
                <li >                <s:url action="IndexRequestAction" id="url">
                                        <s:param name="requestType" value="%{'CreateUser'}"/>
                                    </s:url>
                                    <s:a href="%{url}" name="#CreateUser" id="#CreateUser">
                                        <span><s:property value="getText('CreateUser')"/> </span>
                                   </s:a>                            
             </li>   </s:if>
             
             <s:if test='%{#attr.roleId == 3 }'>
                <li >                <s:url action="IndexRequestAction" id="url">
                                        <s:param name="requestType" value="%{'MakePayment'}"/>
                                    </s:url>
                                    <s:a href="%{url}" name="#MakePayment" id="#MakePayment">
                                        <span><s:property value="getText('MakePayment')"/> </span>
                                   </s:a>                            
             </li>   </s:if>
                
             <s:if test='%{#attr.roleId == 3 }'>
                <li >     <s:url action="IndexRequestAction" id="url">
                                        <s:param name="requestType" value="%{'CustomerLocationSetup'}"/>
                                    </s:url>
                                   <s:a href="%{url}" name="#CustomerLocationSetup" id="#CustomerLocationSetup">
                                     <span>   <s:property value="getText('ShipToLocation')"/> </span>
                                    </s:a>
             </li>                
                </s:if>
                
                
                    <s:if test='%{ #attr.roleId == 2||#attr.roleId == 3 }'>
                    <li >
                     <s:url action="IndexRequestAction" id="url">
                                        <s:param name="requestType" value="%{'LocationSetup'}"/>
                                    </s:url>
                                    <s:a href="%{url}" name="#LocationSetup" id="#LocationSetup">
                                        <span> <s:property value="getText('ShipFromLocation')"/> </span>
                                    </s:a>
                      </li> 
               </s:if>
       </ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown" style="margin-top:0px;">
         <a class="dropdown-toggle" data-toggle="dropdown" id="appsUser" style="width: 40%;padding-left: 10%;"><span class="glyphicon glyphicon-user" style="font-size:20px;"></span></a>
          <ul class="dropdown-menu">
          <!--  <li><a href="#">Home</a></li>
            <li><a href="#">Edit Profile</a></li>
            <li><a href="#">Change Password</a></li>
            <li><a href="#">Sign Out</a></li>-->
            <li  ><s:a href="aascShipIndexPage.jsp" id="homeLinkId" cssStyle="color:#000;font-size:10px; vertical-align: middle;"  >Home</s:a></li>
                                    <li  ><s:a href="#" id="editProfileLinkId" onclick="editProfile();" cssStyle="color:#000;font-size:10px; vertical-align: middle;"  >Edit Profile</s:a></li>
                                    <li ><s:a href="#" id="changePasswordLinkId" onclick="changePassword();" cssStyle="color:#000;font-size:10px; vertical-align: middle;">Change Password</s:a></li>
                                   
                                     <s:if test="%{#attr.roleId == 2 || #attr.roleId == 4}">
                                        <li ><s:a href="javascript: openNewWindow('/ShippingUserHelp/index.html', 'SCHelpWindow', 'WIDTH=700,HEIGHT=500,resizable=yes' );" name="Help" id="Help" cssStyle="color:#000;font-size:10px; vertical-align: middle;">Help</s:a></li>
                                     </s:if>
                                     <s:if test="%{#attr.roleId == 3}">
                                        <li ><s:a href="javascript: openNewWindow('/AdminUserHelp/index.html', 'SCHelpWindow', 'WIDTH=700,HEIGHT=500,resizable=yes');" name="Help" id="Help" cssStyle="color:#000;font-size:10px; vertical-align: middle;">Help</s:a></li>
                                     </s:if>
                                     <s:if test="%{#attr.roleId == 1}">
                                        <li ><s:a href="javascript: openNewWindow('/AppsAdmin/index.html', 'SCHelpWindow', 'WIDTH=700,HEIGHT=500,resizable=yes');" name="Help" id="Help" cssStyle="color:#000;font-size:10px; vertical-align: middle;">Help</s:a></li>
                                     </s:if>
                                    <s:if test="%{#attr.roleId == 5}">
                                        <li ><s:a href="javascript: openNewWindow('/TrackingUserHelp/index.html', 'SCHelpWindow', 'WIDTH=700,HEIGHT=500,resizable=yes');" name="Help" id="Help" cssStyle="color:#000;font-size:10px; vertical-align: middle;">Help</s:a></li>
                                    </s:if>
                                     <li ><s:url action="login" id="urlHeader">
                                        <s:param name="actiontype" value="%{'Logout'}"/>
                                        </s:url>
                                        <s:a href="%{urlHeader}" name="#Logout" id="Logout" cssStyle="color:#000;font-size:10px; vertical-align: middle;">Sign Out</s:a></li>
          </ul>
        </li>
      </ul>
     
    </div>
  </div>
</nav>
  

</body>
</html>
