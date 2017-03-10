<%/*========================================================================+
|  DESCRIPTION                                                              |
|    JSP to display error while tracking                                    |
|    Author |
|    Version - 1                                                            |
|    History                                                                |
|        -19/01/2015 Y Pradeep  Modified code to get label names from Property file|
+===========================================================================*/%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page errorPage="aascShipError.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="aasc_ss.css">
<title>ShipConsole Error Page</title>
</head>
<body class="gradient" bgcolor = "#CCCCCC">
  <P>
    <FONT color="#ff3333">
      <!-- <img src="images/aasc_logo.png" height="48" width="200" onclick="checkForm()" alt="">  -->
      <%@ include file="aascHeader.jsp"%>
    </FONT>
  </P>
  <P>&nbsp;</P>
  <P>&nbsp;</P>
  <center>
  <P>
    <FONT color="#ff3333" class="displayMessage"><s:property value="getText('UnableToProcessRequest')"/></FONT>
  </P>
  </center>
   <div style="position:absolute;top:620px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
</body>
</html>
