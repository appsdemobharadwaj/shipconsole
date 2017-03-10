<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isErrorPage="true"  %>
<%@ page import="java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="aasc_ss.css">
<title>ShipConsole Error Page</title>
<style type="text/css">
html{height:100%}
</style>
</head>
<body class="gradient" bgcolor = "#CCCCCC">
  <P>
    <FONT color="#ff3333">
      <img src="images/aasc_Apps_Logo.png" alt="" width="202" height="48" onclick="checkForm()">
    </FONT> 
  </P>
  <P>&nbsp;</P>
  <P>&nbsp;</P>
  <center>
    <s:set name="version" value="%{#session.version}"/>
    <s:set name="patchVersion" value="%{#session.patchVersion}"/>
  <P>
    <FONT color="#ff3333" class="displayMessage">ShipConsole UI (Version:<s:property value="%{#patchVersion}" />) and Database (Version:<s:property value="%{#version}" />) are incompatible</FONT>
  </P>
  <P>
    <FONT color="#ff3333" class="displayMessage">&nbsp;Please&nbsp;contact ShipConsole &nbsp;Administrator</FONT>
 &nbsp;</P></center>
  <div style="position:absolute;top:620px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
</body>
</html>
