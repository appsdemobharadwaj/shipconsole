<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@page import="java.io.*" %>
<%@page import="java.text.*" %>
<%@page import="java.sql.*"%>
<%@page import="java.util.logging.*"%>
<%@page import="java.util.*" %>
<%@page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@page import="oracle.jdbc.driver.*"%>
<%@page import="com.aasc.erp.carrier.*" %> 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page errorPage="aascShipError.jsp" %>
<html>
  <head>
   <link type="text/css" rel="stylesheet" href="aasc_ss.css">
     <link type="text/css" rel="stylesheet" href="jquery-ui.css" />  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script type="text/javascript" src="jquery-mini.js"></script>
    <!--<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script> 
    <script type="text/javascript" src="http://malsup.github.com/jquery.form.js"></script>  -->
    <style type="text/css">

.href {color: #003399}

    </style>
    <script type="text/javascript" >
function openNewWindow (URL, windowName, windowOptions)
      {
          var window = getWindow (URL, windowName, windowOptions);
      }

      function getWindow(URL, windowName, windowOptions)
      {
        //alert("URL"+URL);
        //alert("windowName"+windowName);
        //alert("windowOptions"+windowOptions);
          var newWindow = open (URL, windowName, windowOptions)
          newWindow.focus();
          return window;
      }
function changePassword(){
    tpwindow =  window.open("aascChangePassword.jsp","Post",'width=500,height=350,top=100,left=100,scrollbars=yes, resizable=yes');
    tpwindow.focus();
    }

function editProfile(){
tpwindow =  window.open("aascEditUserProfile.jsp","Post",'width=700,height=450,top=100,left=100,scrollbars=yes, resizable=yes');
    tpwindow.focus();
}
 
$(function() {
$(".dropdown").hover(
function() { $(".submenu").slideToggle(400); },
function() { $(".submenu").hide(); }
);
});
</script>
  </head>
  <body>

  <table border="0" cellspacing="0" cellpadding="0" align="right" width="100%">
    <tr>
    <td width="10%">&nbsp; </td>
    
           <td width="9%">  <img src="images/aasc_Apps_Logo.png" alt="" width="202" height="48" onClick=""/></td>
       <td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
      <td align="right" width="80%">
        <table >
        <tr align="right">
 
      </tr>
    </table>
    </td>
    </tr>
   
    
      </table></body>
</html>
